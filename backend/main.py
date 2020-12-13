# -*- coding: utf-8 -*-
from fastapi import FastAPI, File, UploadFile, Form
from fastapi.middleware.cors import CORSMiddleware
import classes.UserClass
import classes.GuestClass
import classes.FaceDectionClass
import uvicorn
import base64
import requests
server = "http://3.35.19.36:1337/"
app = FastAPI()

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
FaceClass = classes.FaceDectionClass.FaceDetectionClass()
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
def reg_user_face(user: FaceClass.UserFace):
    datas = FaceClass.save_user_face(user)
    return datas

# 방문 신청
@app.post("/regguest")
def reg_guest(guest: GuestClass.GuestModel):
    datas = GuestClass.reg_guest(guest)
    return datas

@app.post("/updateguest")
def update_guest(data: UserClass.UpdateGuest):
    data = UserClass.update_guest_visit(data)
    return data
@app.get("/parking")
def parking():
    result = requests.get(server + "parkings")
    if result.status_code == 200:
        return {
            "responseCode" : 200,
            "datas" : result.json()
        }
    else:
        return {
            "responseCode" : 400,
            "datas" : ""
        }
if __name__=="__main__":
    uvicorn.run("main:app", host="0.0.0.0", port="3000", log_level='info', access_log=False)