# -*- coding: utf-8 -*-
from fastapi import FastAPI, File, UploadFile, Form
from fastapi.middleware.cors import CORSMiddleware
import classes.UserClass
import classes.DBClass
import classes.GuestClass
import uvicorn
import base64
app = FastAPI()
import re
# db 연동
"""
DBClass = classes.DBClass.DBClass()
db, cursor = DBClass.run_db()
print("db 연동완료")
"""
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

#UserClass = classes.UserClass.UserClass(db, cursor)
UserClass = classes.UserClass.UserClass()
GuestClass = classes.GuestClass.GuestClass()
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

import cv2
# 세대주 얼굴 등록
@app.post("/reguserface")
def reg_user_face(file: UserClass.Base):
    image = file.file

    data = base64.b64decode(image)

    with open('./1.jpg', 'wb') as f:
        f.write(data)
    img = cv2.imread('./1.jpg')
    img180 = cv2.rotate(img, cv2.ROTATE_180)
    cv2.imwrite('./1.jpg', img180)
    return {"responseCode" : 200}

# 방문 신청
@app.post("/regguest")
def reg_guest(guest: GuestClass.GuestModel):
    datas = GuestClass.reg_guest(guest)
    return datas

# 방문자 내용 받기
@app.post("/getguestinfo")
def get_guest_info(user: GuestClass.UserModel):
    datas = GuestClass.get_guest_info(user)
    return datas

# 방문자 수락 시 업데이트
@app.post("/updateguest")
def update_guest(update: GuestClass.UpdateModel):
    datas = GuestClass.update_guest(update)
    return datas


if __name__=="__main__":
    uvicorn.run("main:app", host="0.0.0.0", port="3000", log_level='info', access_log=False)