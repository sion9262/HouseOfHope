# -*- coding: utf-8 -*-
from typing import List
from fastapi import FastAPI, File, UploadFile, Form
import uvicorn
import io
import face_recognition_train
app = FastAPI()

# 이미지를 받는 부분
@app.post("/uploadfiles/")
async def create_upload_files(files: List[UploadFile] = File(...), username: str = Form(...)):
    filenames = []
    contents = []

    for idx, file in enumerate(files):
        filenames.append(file.filename)
        content =  await file.read()
        contents.append(io.BytesIO(content))

    datas = face_recognition_train.add_user(username, contents)

    return datas

if __name__=="__main__":
    uvicorn.run("main:app", host="0.0.0.0", port="3000", log_level='info', access_log=False)