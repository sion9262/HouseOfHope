import cv2

import RPi.GPIO as GPIO  # RPi.GPIO 라이브러리를 GPIO로 사용

from time import sleep  # time 라이브러리의 sleep함수 사용

servoPin = 12  # 서보 핀

SERVO_MAX_DUTY = 12  # 서보의 최대(180도) 위치의 주기

SERVO_MIN_DUTY = 3  # 서보의 최소(0도) 위치의 주기

GPIO.setmode(GPIO.BOARD)  # GPIO 설정

GPIO.setup(servoPin, GPIO.OUT)  # 서보핀 출력으로 설정

servo = GPIO.PWM(servoPin, 50)  # 서보핀을 PWM 모드 50Hz로 사용하기 (50Hz > 20ms)

servo.start(0)  # 서보 PWM 시작 duty = 0, duty가 0이면 서보는 동작하지 않는다.


def setServoPos(degree):
    # 각도는 180도를 넘을 수 없다.

    if degree > 180:
        degree = 180

    # 각도(degree)를 duty로 변경한다.

    duty = SERVO_MIN_DUTY + (degree * (SERVO_MAX_DUTY - SERVO_MIN_DUTY) / 180.0)

    # duty 값 출력

    print("Degree: {} to {}(Duty)".format(degree, duty))

    # 변경된 duty값을 서보 pwm에 적용

    servo.ChangeDutyCycle(duty)


model = 'models/dnn_face_detector/res10_300x300_ssd_iter_140000_fp16.caffemodel'

config = 'models/dnn_face_detector/deploy.prototxt'

import sys

import numpy as np

import cv2

import face_recognition_predict

model = 'models/dnn_face_detector/res10_300x300_ssd_iter_140000_fp16.caffemodel'

config = 'models/dnn_face_detector/deploy.prototxt'

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

        if class_probability:

            label = f'Name: {name} Probability {class_probability:4.2f}'

            cv2.putText(frame, label, (x1, y1 - 1), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 1, cv2.LINE_AA)

            setServoPos(0)

            sleep(1)  # 1초 대기

            # 90도에 위치

            setServoPos(90)

            sleep(5)

            setServoPos(0)

            sleep(1)  # 1초 대기



        else:

            label = f'wait!!'

            cv2.putText(frame, label, (x1, y1 - 1), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 1, cv2.LINE_AA)

    cv2.imshow('frame', frame)

    if cv2.waitKey(1) == 27:
        break

# 서보 PWM 정지

servo.stop()

# GPIO 모드 초기화

GPIO.cleanup()

cv2.destroyAllWindows()