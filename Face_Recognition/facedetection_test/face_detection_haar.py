import cv2

def detect_faces(img, draw_box=True):
    # 이미지를 흑색으로 바꾼다.
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

face_cascades = cv2.CascadeClassifier('./datas/haar/haarcascade_frontalface_default.xml')

cap = cv2.VideoCapture(0)

while(cap.isOpened()):
    ret, frame = cap.read()

    if (ret):


        # haar 방식
        detection, _, _ = detect_faces(frame)
        cv2.imshow('frame', detection)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

cap.release()
cv2.destroyAllWindows()