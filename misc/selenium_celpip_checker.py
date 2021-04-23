from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options

import time
import datetime
import requests
import json

def get_whatsapp_target_input_box(target):
    x_arg = '//span[contains(@title,' + target + ')]'
    group_title = wait.until(EC.presence_of_element_located((By.XPATH, x_arg)))
    group_title.click()
    inp_xpath = '//div[@class="input"][@dir="auto"][@data-tab="1"]'

    time.sleep(5)
    input_box = driver.switch_to.active_element
    return input_box

def send_whatsapp_message(recipient_input_box, message):
    print('Sending message to Whatsapp: {}'.format(send_message))
    recipient_input_box.send_keys(message + Keys.ENTER)
    time.sleep(1)

user_data_dir = input("user-data-dir: ")
chromedriver_executable_path = input("chromedriver_executable_path: ")

recipient_name = input("Enter recipient name: ")
target = '"{}"'.format(recipient_name)

options = webdriver.ChromeOptions() 
options.add_argument("--user-data-dir={}".format(user_data_dir))
options.add_argument("--disk-cache=true")

driver = webdriver.Chrome(executable_path=chromedriver_executable_path, options=options)
driver.get("https://web.whatsapp.com/")
wait = WebDriverWait(driver, 600)

input_box = get_whatsapp_target_input_box(target)

region_id = 9  # Ontario
url = "https://secure.celpip.ca/RegWebApp/api/TestSessions"
query_string = {
    "brandId" : "1",
    "privateOnly" : "false",
    "testPurposeId" : "1",
    "testTypeId" : "5",
    "regionId" : region_id
}

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

while True:
    try:
        response = requests.request("GET", url, headers=headers, params=query_string)

        data = response.json()
        content_size = len(data['content'])

        if response.status_code == 200 and content_size > 0:
            message_sent = False

            now = datetime.datetime.now()
            send_message = '{}: Found {} available locations in Ontario, check CELPIP Website ASAP! {}'.format(now, content_size, celpip_website)

            while not message_sent:
                try:
                    send_whatsapp_message(input_box, send_message)
                    message_sent = True
                except:
                    print("{}: Error sending the message to {}. Retrying...".format(datetime.datetime.now(), target))
                    input_box = get_whatsapp_target_input_box(target)
        else:
            print("{}: Still checking for new CELPIP available locations...".format(datetime.datetime.now()))
            time.sleep(15)
    except:
        print("{}: Unexpected error. Retrying...".format(datetime.datetime.now()))
