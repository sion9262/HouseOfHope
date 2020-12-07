from pydantic import BaseModel
import requests
class UserClass:


    class UserModel(BaseModel):
        user_email: str
        user_password: str

    class Base(BaseModel):
        user_id : str
        file: str

    class UserRegisterModel(UserModel):
        user_username : str
        user_dong : str
        user_ho : str
        user_car : str

    class GetGuestModel(BaseModel):
        user_dong : str
        user_ho : str

    def __init__(self):
        self.server = "http://3.35.19.36:1337/"

    def login(self, user):

        datas = {
            "identifier": user.user_email,
            "password": user.user_password
        }
        print(datas)
        result = requests.post(self.server + "auth/local", data=datas)

        if result.status_code == 200:
            data = result.json()
            return {
                "responseCode" : 200,
                "user_id" : data['user']['id'],
                "user_name" : data['user']['username'],
                "user_car" : data['user']['user_car'],
                "user_dong" : data['user']['user_dong'],
                "user_ho" : data['user']['user_ho'],
                "status" : data['user']['status']
            }
        else:
            return {
                "responseCode" : 400
            }

    def register(self, user):

        datas = {
            "username": user.user_username,
            "email": user.user_email,
            "password": user.user_password,
            "user_dong" : user.user_dong,
            "user_ho" : user.user_ho,
            "user_car" : user.car,
            "count_picture" : 0,
            "status" : 0
        }
        result = requests.post(self.server + "auth/local/register", data=datas)

        try:
            if result.status_code == 200:
                return {
                    "responseCode" : 200

                }
        except:
            return {
                "responseCode": 400
            }

    def get_guest_info(self, user):

        user_dong = user.user_dong
        user_ho = user.user_ho

        result = requests.get(self.server + "guests?visit_dong="+ str(user_dong) +"&visit_ho=" + str(user_ho))

        if result.status_code == 200:
            return {
                "responseCode": 200,
                "data":result.json()
            }
        else:
            return {
            "responseCode": 400
            }


