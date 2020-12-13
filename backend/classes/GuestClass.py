from pydantic import BaseModel
import requests
class GuestClass:

    class GuestModel(BaseModel):
        guest_name: str
        guest_car: str
        visit_dong : str
        visit_ho : str
        visit_why : str
        visit_day : str


    def __init__(self):
        self.server = "http://3.35.19.36:1337/"

    def reg_guest(self, guest):

        datas = {
            "guest_name" : guest.guest_name,
            "guest_car" : guest.guest_car,
            "visit_dong" : guest.visit_dong,
            "visit_ho" : guest.visit_ho,
            "visit_why" : guest.visit_why,
            "visit_day" : guest.visit_day,
            "status" : 0,
            "count_picture" : 0
        }
        result = requests.post(self.server + "guests", data=datas)
        print(result.json())
        if result.status_code == 200:
            data = result.json()
            return {
                "responseCode" : 200,
                "datas" : data
            }
        else:
            return {
                "responseCode" : 400
            }



    def get_guest_info(self, user):
        user_id = user.user_id

        result = requests.get(self.server + "users/" + user_id)

        if result.status_code == 200:
            data = result.json()
            print(data)
            dong = data['dong']
            ho = data['ho']

            result_sub = requests.get(self.server + "guests?visit_dong="+ str(dong) +"&visit_ho=" + str(ho))
            print(result_sub.json())
            if result_sub.status_code == 200:
                print(result_sub.json())

                return {
                    "responseCode" : 200,
                    "datas" : result_sub.json()
                }
        return {
            "responseCode" : 400
        }

    def update_guest(self, update):

        result = requests.put(self.server + "guests/" + str(update.guest_id), data={ "status" : update.status})

        if result.status_code == 200:
            return {
                "responseCode" : 200
            }
        return {
            "responseCode": 400
        }