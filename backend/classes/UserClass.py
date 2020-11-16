from pydantic import BaseModel
import requests
class UserClass:


    class UserModel(BaseModel):
        user_email: str
        user_password: str

    class UserRegisterModel(UserModel):
        user_username : str
        user_dong : str
        user_ho : str
    """
    def __init__(self):
        self.db = db
        self.cursor = cursor
    """
    def __init__(self):
        self.server = "http://13.125.207.134:1337/"

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
                "user_name" : data['user']['username']
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
            "dong" : user.user_dong,
            "ho" : user.user_ho
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


    def login_db(self, user):

        sql = """
            SELECT * FROM user WHERE user_id = %s
        """
        try:
            rows = self.cursor.execute(sql, user.user_id)
            result = self.cursor.fetchall()
            # 등록되지 않은 아이디
            if rows == 0:
                return {
                    "response_code" : 404,
                    "message" : "아이디를 확인하세요"
                }
            # 비밀번호 확인

            if user.user_password == result[0]['user_password']:
                return {
                    "response_code" : 200,
                    "message" : "로그인 성공"
                }
            else:
                return {
                    "response_code" : 404,
                    "message" : "비밀번호를 확인하세요"
                }
        except:
            return {
                "response_code" : 405,
                "message" : "서버 오류"
            }

    def register_db(self, user):

        sql = """
            INSERT INTO user (user_id, user_password) VALUES (%s, %s)
        """

        try:
            self.cursor.execute(sql, (user.user_id, user.user_password))
            self.db.commit()
            return {
                "response_code": 200,
                "message": "회원가입 성공"
            }
        except:
            return {
                "response_code": 404,
                "message": "이미 존재하는 아이디입니다."
            }

