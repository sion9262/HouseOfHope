# -*- coding: utf-8 -*-
from fastapi import FastAPI, File, UploadFile, Form
from fastapi.middleware.cors import CORSMiddleware
import classes.UserClass
import classes.DBClass
import classes.GuestClass
import uvicorn
import base64
app = FastAPI()

a = [1, 2, 3]
# cors 문제 해결
origins = [
    "http://localhost.tiangolo.com",
    "https://localhost.tiangolo.com",
    "http://localhost",
    "http://localhost:3000",
]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

UserClass = classes.UserClass.UserClass()
GuestClass = classes.GuestClass.GuestClass()
@app.get("/")
def root():
    a.append("1")
    print(a)
    return {"append"}

@app.post("/login")
def login(user: UserClass.UserModel):
    datas = UserClass.login(user)
    return datas

@app.post("/register")
def register(user: UserClass.UserRegisterModel):
    datas = UserClass.register(user)
    return datas
@app.post("/getuserguest")
def get_user_guest(user: UserClass.GetGuestModel):
    datas = UserClass.get_guest_info(user)
    return datas
@app.post("/reguserface")
def reg_user_face(file: UserClass.Base):
    image = file.file
    print(a)


    return {"responseCode" : 200}

# 방문 신청
@app.post("/regguest")
def reg_guest(guest: GuestClass.GuestModel):
    datas = GuestClass.reg_guest(guest)
    return datas


if __name__=="__main__":
    uvicorn.run("main:app", host="0.0.0.0", port="3000", log_level='info', access_log=False)