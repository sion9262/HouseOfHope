
"""
face_detection -> 얼굴 검출

얼굴검출 알고리즘은 크게 3가지가 있다.
하르 기반 다단계 검출
고유얼굴
HOG

이 코드는 하르 기반 다단계 검출 알고리즘을 사용..
-> 이마와 볼보다 눈 근처가 어둡다.
-> 눈보다 코 근처가 밝다.
-> 모든인간의 얼굴이 다음과 같은 특징을 공유한다고 가정한다.

"""

import cv2
import os
import numpy as np
import dlib

path = os.getcwd()

# haar model 불러옴
face_cascades = cv2.CascadeClassifier('./datas/haar/haarcascade_frontalface_default.xml')
detecor = dlib.get_frontal_face_detector()
def detect_faces(img, draw_box=True):
    # 이미지를 흑색으로 바꾼다.
    img_copy = img.copy()
    grayscale_img = cv2.cvtColor(img, cv2.COLOR_BGR2BGRA)

    #얼굴을 검출한다.
    faces = face_cascades.detectMultiScale(grayscale_img, scaleFactor=1.1,
                                           minNeighbors=5,
                                           minSize=(30, 30),
                                           flags=cv2.CASCADE_SCALE_IMAGE)

    face_box, face_coords = None, []
    # 검출한 얼굴 주위의 테두리를 그린다.
    for (x, y, width, height) in faces:
        if draw_box:
            cv2.rectangle(img, (x, y), (x+width, y+height), (0, 255, 0), 5)
        face_box = img[y:y+height, x:x+width]
        face_coords = [x, y, width, height]

    return img, face_box, face_coords

img = cv2.imread('../datas/1.jpg')
detected_faces, _, _ = detect_faces(img)
detection = detecor(img)
for detect in detection:
    cv2.rectangle(img, (detect.left(), detect.top()), (detect.right(), detect.bottom()), (0, 0, 255), 2)
cv2.imwrite('../datas/1_dlib.jpg', img)
cv2.imwrite('../datas/1_haar.jpg', detected_faces)

"""
files = os.listdir('./datas/sample_face')
images = [file for file in files if 'jpg' in file]

for image in images:

    img = cv2.imread('./datas/sample_face/' + image)
    detected_faces, _, _ = detect_faces(img)
    cv2.imwrite('./datas/detected_face/' + image, detected_faces)
"""