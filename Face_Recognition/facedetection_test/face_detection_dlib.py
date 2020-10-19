import cv2
import dlib


detecor = dlib.get_frontal_face_detector()
cap = cv2.VideoCapture(0)

while(cap.isOpened()):
    ret, frame = cap.read()

    if (ret):

        # dlib 방식 
        detection = detecor(frame)
        for detect in detection:
            cv2.rectangle(frame, (detect.left(), detect.top()), (detect.right(), detect.bottom()), (0, 0, 255), 2)
        cv2.imshow('frame', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

cap.release()
cv2.destroyAllWindows()