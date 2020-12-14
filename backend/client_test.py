import socket
import cv2
import time
import base64
import requests

cap = cv2.VideoCapture(0)

while cap.isOpened():
    ret, frame = cap.read()

    if not ret:
        continue
    retval, buffer = cv2.imencode('.jpg', frame)
    jpg_as_text = base64.b64encode(buffer)

    base64_buffer = base64.b64encode(buffer)

    result = requests.post("http://3.35.19.36:3000/predictface", data={"file", base64_buffer})
    time.sleep(5)
    if cv2.waitKey(1) == ord("q"):
        break

