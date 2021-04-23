import requests
import json
import pywhatkit
import datetime
import time

regionId = 9  # Ontario

url = "https://secure.celpip.ca/RegWebApp/api/TestSessions"

querystring = {"brandId":"1","privateOnly":"false","testPurposeId":"1","testTypeId":"5","regionId":regionId}

headers = {
    'authority': "secure.celpip.ca",
    'sec-ch-ua': """Not A;Brand";v="99", "Chromium";v="90", "Google Chrome";v="90""",
    'pragma': "no-cache",
    'sec-ch-ua-mobile': "?0",
    'user-agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36",
    'accept': "application/json, text/plain, */*",
    'cache-control': "no-cache,no-cache",
    'authorization': "null",
    'expires': "Mon, 14 Feb 2000 05:00:00 GMT",
    'sec-fetch-site': "same-origin",
    'sec-fetch-mode': "cors",
    'sec-fetch-dest': "empty",
    'referer': "https://secure.celpip.ca/RegWebApp/",
    'accept-language': "en,pt;q=0.9",
    'cookie': "__cfduid=d5ccf7ed982b9335674d504dbbc152b881619049588; _ga=GA1.2.782867364.1619049618; _gid=GA1.2.962604958.1619049618; AnonymousSessionWebId=ea981509-7fe4-436c-9869-56b1177c379e; shopping-cart-count=0",
    'Postman-Token': "ed47116b-73de-4231-8fe5-f4316d4ee417"
    }

celpip_website = 'https://secure.celpip.ca/RegWebApp/#/registration/sitting-selection'
recipient_number = input("Enter recipient number: ")

while True:
    response = requests.request("GET", url, headers=headers, params=querystring)

    data = response.json()
    content_size = len(data['content'])

    if response.status_code == 200 and content_size > 0:
        message_sent = False

        now = datetime.datetime.now()
        next_run = now + datetime.timedelta(seconds=70)

        send_message = '{}: Found {} available locations in Ontario, check CELPIP Website ASAP! {}'.format(now, content_size, celpip_website)

        print('Sending message to Whatsapp: {}'.format(send_message))
        print(send_message)

        try:
            pywhatkit.sendwhatmsg(recipient_number, send_message, next_run.hour, next_run.minute)
            message_sent = True
        except:
            print("{}: Error sending the message to {}. Retrying...".format(datetime.datetime.now(), recipient_number))
    else:
        print("{}: Still checking for new CELPIP available locations...".format(datetime.datetime.now()))
        time.sleep(15)
