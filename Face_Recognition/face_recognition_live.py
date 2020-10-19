import cv2

model = 'datas/dnn_face_detector/res10_300x300_ssd_iter_140000_fp16.caffemodel'
config = 'datas/dnn_face_detector/deploy.prototxt'

import sys
import numpy as np
import cv2
import face_recognition_predict
model = 'datas/dnn_face_detector/res10_300x300_ssd_iter_140000_fp16.caffemodel'
config = 'datas/dnn_face_detector/deploy.prototxt'

face_predict = face_recognition_predict.FaceRecognitionPredict()
cap = cv2.VideoCapture(0)

if not cap.isOpened():
    print('Camera open failed!')
    sys.exit()

net = cv2.dnn.readNet(model, config)

if net.empty():
    print('Net open failed!')
    sys.exit()
while True:

    ret, frame = cap.read()

    if not ret:
        break

    blob = cv2.dnn.blobFromImage(frame, 1, (300, 300), (104, 177, 123))
    net.setInput(blob)
    out = net.forward()

    detect = out[0, 0, :, :]
    (h, w) = frame.shape[:2]

    for i in range(detect.shape[0]):
        confidence = detect[i, 2]
        if confidence < 0.6:
            break

        # detect값는 정규화가 되어있어 실제 들어온 영상의 w, h를 곱해야함.
        x1 = int(detect[i, 3] * w)
        y1 = int(detect[i, 4] * h)
        x2 = int(detect[i, 5] * w)
        y2 = int(detect[i, 6] * h)

        crop = frame[y1:y2, x1:x2]
        name, class_probability = face_predict.predict(crop)

        cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 255, 0))

        if class_probability :
            label = f'Name: {name} Probability {class_probability:4.2f}'
            cv2.putText(frame, label, (x1, y1 - 1), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 1, cv2.LINE_AA)

        else:

            label = f'wait!!'
            cv2.putText(frame, label, (x1, y1 - 1), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 1, cv2.LINE_AA)

    cv2.imshow('frame', frame)

    if cv2.waitKey(1) == 27:
        break

cv2.destroyAllWindows()

