from numpy import load
from numpy import expand_dims
from sklearn.preprocessing import LabelEncoder
from sklearn.preprocessing import Normalizer
from sklearn.svm import SVC
from matplotlib import pyplot
from os import listdir
from os.path import isdir
from PIL import Image
from matplotlib import pyplot
from numpy import savez_compressed
from numpy import asarray
from keras.models import load_model
import os
#openCV 기준
class FaceRecognitionPredict:

    def __init__(self):

        self.model_path = os.path.join(os.getcwd(), 'model')
        self.data = load('model/usermodel.npz')
        self.trainX, self.trainy, self.testX, self.testy = self.data['arr_0'], self.data['arr_1'], self.data['arr_2'], self.data['arr_3']
        self.facenet_model = load_model('model/facenet_keras.h5')

    def extract_face(self, image, required_size=(160, 160)):

        image = Image.fromarray(image)
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

    def predict(self, image):

        self.normalizer_data()
        # 배열로 변환
        face_array = self.extract_face(image)


        # 임베딩
        face_embedding = self.get_embedding(face_array)

        samples = expand_dims(face_embedding, axis=0)
        in_encoder = Normalizer(norm='l2')
        samples = in_encoder.transform(samples)


        yhat_class = self.svc_model.predict(samples)
        yhat_prob = self.svc_model.predict_proba(samples)

        # 이름 얻기
        class_index = yhat_class[0]
        class_probability = yhat_prob[0, class_index] * 100
        predict_names = self.out_encoder.inverse_transform(yhat_class)
        print('예상: %s (%.3f)' % (predict_names[0], class_probability))

        if class_probability >= 75:
            return predict_names[0], class_probability
        else:
            return "", 0