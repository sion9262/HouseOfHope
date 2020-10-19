# 얼굴 인식 프로그램 - 박시온


## 얼굴 검출 (face_detection.py)

1. haar 모델 사용한 얼굴 검출

       #haar model를 불러옴.
       face_cascades = cv2.CascadeClassifier('./datas/haar/haarcascade_frontalface_default.xml')
       
       # 얼굴 검출하는 부분
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

![pre_detection](./readme/pre_detection.jpg)

[검출 전]

![detection](./readme/detection.jpg)

[검출 후]


## 7주차 진행사항 보고

### 얼굴 검출 3가지 분류

1. 하르 캐스케이스
2. dlib
3. CNN

3가지 비교 후 성능은 다음과 같음

하르 캐스케이스 < dlib < CNN

검출 영상 

### 얼굴 인식 

1. 이미지 유사도를 판단하는 기법으로 접근해보았습니다.

Pre-trained 된 VGG16 모델을 가지고 특징 추출

추출한 특징을 PCA를 통해 차원 축소

T-sne 를 통한 임베딩 후 결과 

