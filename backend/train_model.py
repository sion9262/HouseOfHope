import os
import utils
import numpy as np
from numpy import load
from numpy import asarray
from numpy import savez_compressed
from numpy import expand_dims
from keras.models import load_model
import requests

class FaceTrainUser:

    def __init__(self):
        self.model = load('./model/usermodel.npz')
        self.server = "http://3.35.19.36:1337/"
        self.util = utils.Util()
    def get_trian_data(self):
        result = requests.get(self.server + "users/?count_picture_gte=5&train_face=0")

        if result.status_code == 200:
            return result.json()
        else:
            return []
    def run(self):
        datas = self.get_trian_data()
        path = os.getcwd() + "/user/"
        print(datas)
        for data in datas[:1]:
            user_path = path + str(data['id']) + "/face/"
            face_list = os.listdir(user_path)
            labels = [str(data['id']) for _ in range(len(face_list))]
            face = []

            # 이미지 특징 추출
            for face_img in face_list:
                face.append(self.util.extract_face(user_path+face_img))

            #print(face)

            X, Y = asarray(face), asarray(labels)
            newTrainX = list()
            for face_pixels in X:
                embedding = self.util.get_embedding(face_pixels)
                newTrainX.append(embedding)

            newTrainX = asarray(newTrainX)
            trainX, trainY = self.model['arr_0'], self.model['arr_1']
            trainX = np.append(trainX, newTrainX)
            trainY = np.append(trainY, Y)

            savez_compressed('./model/usermodel.npz', trainX, trainY)

            result = requests.put(self.server + "users/" + str(data['id']),
                                  data={"train_face" : 1})



if __name__ == "__main__":
    F = FaceTrainUser()
    F.run()
