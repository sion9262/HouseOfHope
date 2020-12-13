from pydantic import BaseModel
import requests
import os
import traceback
import base64
import numpy as np
import cv2

class FaceDetectionClass:

    def __init__(self):
        self.server = "http://3.35.19.36:1337/"
        self.path = os.getcwd()
        self.model = self.path + '/model/res10_300x300_ssd_iter_140000_fp16.caffemodel'
        self.config = self.path + '/model/deploy.prototxt'
        self.net = cv2.dnn.readNet(self.model, self.config)
        self.init_folder()

    def init_folder(self):
        try:
            # init folder
            if os.path.isdir(self.path + "/user"):
                pass
            else:
                os.mkdir(self.path + "/user")
            # init guest
            if os.path.isdir(self.path + "/guest"):
                pass
            else:
                os.mkdir(self.path + "/guest")
        except:
            pass

    # 추후 List 로 image 받아서 처리.
    class UserFace(BaseModel):
        file : str
        is_user : int
        user_id : str

    def make_user_foloder(self, user_path):

        try:
            # init folder
            if os.path.isdir(user_path):
                pass
            else:
                os.mkdir(user_path)

            # face folder
            user_face_path = os.path.join(user_path, 'face')
            if os.path.isdir(user_face_path):
                pass
            else:
                os.mkdir(user_face_path)
        except:
            print(traceback.format_exc())
            return False

        return True


    def save_user_face(self, data):

        image = data.file
        is_user = data.is_user
        user_id = data.user_id

        # 유저일떄
        if is_user == 1 :
            user_path = os.path.join(self.path, 'user', str(user_id))
        elif is_user == 2 :
            user_path = os.path.join(self.path, 'guest', str(user_id))
        else:
            return {"responseCode" : 400}
        # 폴더가 없다면 생성
        result = self.make_user_foloder(user_path=user_path)

        if not result:
            return {"responseCode" : 400}

        face_path = os.path.join(user_path, 'face')

        # user face 갯수 체크
        if is_user == 1:
            result = requests.get(self.server + 'users/?id=' + str(user_id))
        elif is_user == 2:
            result = requests.get(self.server + 'guests/?id=' + str(user_id))

        if result.status_code == 200 :
            data = result.json()
            count_picture = data[0]['count_picture']
        else:
            return {"responseCode": 400}


        # base64 to image
        try:
            data = base64.b64decode(image)
            np_data = np.fromstring(data, np.uint8)
            img = cv2.imdecode(np_data, cv2.IMREAD_UNCHANGED)
            img = cv2.rotate(img, cv2.ROTATE_90_COUNTERCLOCKWISE)

            result, detect_img = self.detection(img)
            if not result:
                return {"responseCode": 400}

            cv2.imwrite(face_path + '/'+str(count_picture) + ".jpg", detect_img)
            if is_user == 1:
                result = requests.put(self.server + 'users/'+str(user_id), data={
                    "count_picture" : count_picture + 1
                })
            elif is_user == 2:
                result = requests.put(self.server + 'guests/' + str(user_id), data={
                    "count_picture": count_picture + 1
                })

            return {"responseCode": 200}

        except:
            print(traceback.format_exc())
            return {"responseCode": 400}

    def detection(self, img):
        blob = cv2.dnn.blobFromImage(img, 1, (300, 300), (104, 177, 123))
        self.net.setInput(blob)
        out = self.net.forward()

        detect = out[0, 0, :, :]
        (h, w) = img.shape[:2]
        cnt = 0

        for i in range(detect.shape[0]):
            confidence = detect[i, 2]
            print(confidence)
            if confidence < 0.5:
                break

            # detect값는 정규화가 되어있어 실제 들어온 영상의 w, h를 곱해야함.
            x1 = int(detect[i, 3] * w)
            y1 = int(detect[i, 4] * h)
            x2 = int(detect[i, 5] * w)
            y2 = int(detect[i, 6] * h)

            cv2.rectangle(img, (x1, y1), (x2, y2), (0, 255, 0))
            crop = img[y1:y2, x1:x2]
            cnt += 1

        if cnt == 0:
            return False, ""
        else:
            return True, crop