from fastapi import FastAPI
import classes.UserClass
import classes.DBClass

app = FastAPI()
# db 연동
DBClass = classes.DBClass.DBClass()
db, cursor = DBClass.run_db()
print("db 연동완료")


UserClass = classes.UserClass.UserClass(db, cursor)

@app.get("/")
def root():
    return {
        "hello world"
    }

@app.post("/login")
def login(user: UserClass.UserModel):

    datas = UserClass.login(user)
    return datas

@app.post("/register")
def register(user: UserClass.UserRegisterModel):
    datas = UserClass.register(user)
    return datas

