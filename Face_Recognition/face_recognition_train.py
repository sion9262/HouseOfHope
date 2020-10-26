import os
import utils
from numpy import load
from numpy import asarray
from numpy import savez_compressed
from numpy import expand_dims
from keras.models import load_model

util = utils.Util()

def add_user(username, contents):
    data = load('./datas/5-celebrity-faces-dataset.npz')
    trainX, trainy = data['arr_0'], data['arr_1']
    # 특징과 라벨 추출
    face = []
    labels = [username for _ in range(len(contents))]
    for content in contents:
        face.append(util.extract_face(content))


    X, Y = asarray(face), asarray(labels)
    newTrainX = list()
    for face_pixels in X:
        embedding = util.get_embedding(face_pixels)
        newTrainX.append(embedding)

    newTrainX = asarray(newTrainX)
    trainX.append(newTrainX)
    trainy.append(Y)

    savez_compressed('user_model.npz', newTrainX, trainy)

    return {
        "resultcode" : 200
    }