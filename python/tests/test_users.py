from datetime import date
import requests
import json
import pytest
from testdata.marqeta_data import MarqetaData

### Users Tests ###
class TestUser:

    @staticmethod
    def test_create_user():
        first_name = MarqetaData.gen_random_word()
        last = MarqetaData.gen_random_word()
        
        body = json.dumps({
            "first_name": first_name,
            "last_name": last 
        })

        url = MarqetaData.users_endpoint
        response = requests.post(url, data=body, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        assert response.status_code == 201, f'Bad Request Code: {response.status_code}'

    @staticmethod
    def test_create_user_with_empty():
        body = json.dumps({

        })
        url = MarqetaData.users_endpoint
        response = requests.post(url, data=body, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        assert response.status_code == 201, f'Bad Request Code: {response.status_code}'
        # add assertions

    @staticmethod
    def test_create_suspend_user():
        body = json.dumps({

            })
        url = MarqetaData.users_endpoint
        user = requests.post(url, data=body, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        user = user.json()

        data = json.dumps({
            "status": 'SUSPENDED',
            "reason_code": '00',
            "reason": 'True',
            "channel": 'test for 00',
            "user_token": user['token']
        })

        url = MarqetaData.base_url+'usertransitions'
        suspend_user = requests.post(url, data=data, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        # add assertions

    @staticmethod
    def test_create_user_full_payload():
        body = json.dumps({
        "active": 'true',
        "honorific": "Mr",
        "gender": "M",
        "ip_address": "127.0.0.1",
        "first_name": MarqetaData.gen_random_word(),
        "last_name": MarqetaData.gen_random_word(),
        "phone": "1234567890",
        "address1": 'No 123'+MarqetaData.gen_random_word(),
        "address2": MarqetaData.gen_random_word(),
        "city": 'Oakland',
        "state": 'CA',
        "zip": '94612',
        "country": 'US',
        "email": MarqetaData.gen_random_word()+'@mqtest123.com',
        "uses_parent_account": 'false',
        "ssn": '098765432',
        "passport_number": "PassportNumber123",
        "passport_expiration_date": "2019-12-31",
        "id_card_number": "IDCardNumber123",
        "id_card_expiration_date": "2019-12-31",
        "nationality": 'Nigerian',
        "company": MarqetaData.gen_random_word(),
        "password": "Testing101!",
        "birth_date": "1980-06-10",
        "corporate_card_holder": 'false',
        "notes": 'This is the notes section'})

        print(body)

        url = MarqetaData.users_endpoint
        user_data = requests.post(url, data=body, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        user_data = user_data.json()
        assert(user_data['status']) == 'ACTIVE'
        assert(user_data['honorific']) == 'Mr', 'Could not verify Honorific'
        assert(user_data['ip_address']) == '127.0.0.1'

    # Verify the password of the user
    # Verify the passport details of the user
    # Verify the Drivers license details of the user
