import socket
import cv2
import time
import base64
HOST = '127.0.0.1'
PORT = 9999

client_socket = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
client_socket.connect((HOST, PORT))

cap = cv2.VideoCapture(0)
stop = False
while True:
    if stop:
        break
    while cap.isOpened():
        ret, frame = cap.read()

        if not ret:
            continue
        #frame = base64.b64encode(frame)
        client_socket.send("1".encode())
        data = client_socket.recv(1024)
        #print('Received from the server :', repr(frame.decode()))
        time.sleep(5)
client_socket.close()


