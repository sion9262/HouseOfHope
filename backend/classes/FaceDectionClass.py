from pydantic import BaseModel
import requests
import os
import traceback
import base64
class FaceDetectionClass:

    def __init__(self):
        self.server = "http://13.125.207.134:1337/"
        self.path = os.getcwd()
    # 추후 List 로 image 받아서 처리.
    class UserFace(BaseModel):
        image : str
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

        image = data.image
        user_id = data.user_id
        user_path = os.path.join(self.path, 'user', str(user_id))

        # 폴더가 없다면 생성
        result = self.make_user_foloder(user_path=user_path)

        if not result:
            return {"responseCode" : 400}

        # user face 갯수 체크
        result = requests.get(self.server + 'user')
        # base64 to image
        try:
            data = base64.b64decode(image)

            save_path = os.path.join(user_path, 'face', '', '.jpg')
            with open(save_path, 'wb') as f:
                f.write(data)

        except:
            print(traceback.format_exc())
            return {"responseCode": 400}

    def

