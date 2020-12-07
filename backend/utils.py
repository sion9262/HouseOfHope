from numpy import load
from numpy import expand_dims
from sklearn.preprocessing import LabelEncoder
from sklearn.preprocessing import Normalizer
from sklearn.svm import SVC
from PIL import Image
from numpy import asarray
from keras.models import load_model

class Util:

    def __init__(self):
        self.facenet_model = load_model('./model/facenet_keras.h5')

    # 주어진 사진에서 하나의 얼굴 추출
    def extract_face(self, file, required_size=(160, 160)):
        # 파일에서 이미지 불러오기
        image = Image.open(file)
        # 배열로 변환
        pixels = asarray(image)
        # 모델 사이즈로 픽셀 재조정
        image = Image.fromarray(pixels)
        image = image.resize(required_size)
        face_array = asarray(image)
        return face_array


    # 하나의 얼굴의 얼굴 임베딩 얻기
    def get_embedding(self, face_pixels):
        # 픽셀 값의 척도
        face_pixels = face_pixels.astype('int32')
        # 채널 간 픽셀값 표준화(전역에 걸쳐)
        mean, std = face_pixels.mean(), face_pixels.std()
        face_pixels = (face_pixels - mean) / std
        print(face_pixels)
        # 얼굴을 하나의 샘플로 변환
        samples = expand_dims(face_pixels, axis=0)
        # 임베딩을 갖기 위한 예측 생성
        yhat = self.facenet_model.predict(samples)

        return yhat[0]

    def normalizer_data(self):
        in_encoder = Normalizer(norm='l2')
        trainX = in_encoder.transform(self.trainX)
        # 목표 레이블 암호화
        self.out_encoder = LabelEncoder()
        self.out_encoder.fit(self.trainy)
        trainy = self.out_encoder.transform(self.trainy)
        # 모델 맞추기(적합시키기)
        self.svc_model = SVC(kernel='linear', probability=True)
        self.svc_model.fit(trainX, trainy)

if __name__ == "__main__":

    start = Util()
    datas = ""