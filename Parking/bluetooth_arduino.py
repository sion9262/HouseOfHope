from bluetooth import *
import requests
 
#######################################################
# Connect
#######################################################
 
# establishing a bluetooth connection
target_address = "00:20:04:32:45:2D"
port = 1

# server
def update_server(status):
    server_url = "http://3.35.19.36:1337/"
    result = requests.put(server_url + "parkings/2", data={"status":status, "user_id" : ""})
    print (result.status_code)
try:
    sock=BluetoothSocket( RFCOMM )
    sock.connect((target_address, port))
 
    while True:         
        try:
            recv_data = sock.recv(1024)
            decode_str = recv_data.decode()
            print(decode_str)
            
            if decode_str == "1" or decode_str == "0":
                update_server(int(decode_str))
                
            
        except KeyboardInterrupt:
            print("disconnected")
            sock.close()
            print("all done")
except btcommon.BluetoothError as err:
    print('An error occurred : %s ' % err)
    pass