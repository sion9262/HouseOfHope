from pydantic import BaseModel

class UserClass:
    def __init__(self, db, cursor):
        self.db = db
        self.cursor = cursor

    class UserModel(BaseModel):
        user_id: str
        user_password: str

    class UserRegisterModel(UserModel):
        user_id: str
        user_password: str
        user_password2: str
        user_phone: str


    def login(self, user):

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

    def register(self, user):

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

