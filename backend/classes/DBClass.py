import pymysql
import json
import os


class DBClass:
    def __init__(self):
        self.host, self.port, self.db, self.user, self.password = self.get_config_data()
        self.config_path = os.getcwd() + "/config.json"

    def get_config_data(self):
        try:

            with open("/home/sion/Desktop/project/HouseOfHope/backend/config.json", "r") as config_file:
                datas = json.load(config_file)
            return datas['host'], datas['port'], datas['db'], datas['user'], datas['password']
        except:
            return None, None, None, None, None

    def run_db(self):
        db = pymysql.connect(host=self.host, port=self.port,
                             db=self.db, user=self.user,
                             password=self.password)
        cursor = db.cursor(pymysql.cursors.DictCursor)
        return db, cursor

if __name__ == "__main__":
    db = DBClass()
    db.get_config_data()