from fastapi import FastAPI
from pydantic import BaseModel


app = FastAPI()

class User(BaseModel):
    user_id : str
    user_password : str

class UserRegister(User):
    user_id : str
    user_password : str
    user_password2 : str
    user_phone : str


@app.get("/")
def root():
    return {
        "hello world"
    }

@app.post("/login")
def login(user: User):
    print(user)
    return {
        "responseCode" : 200
    }

@app.post("/register")
def register(user: UserRegister):
    print(user)
    return {
        "responseCode": 200
    }