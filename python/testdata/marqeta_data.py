import os
import json
import requests
from requests.auth import HTTPBasicAuth
import pytest

from datetime import date
from dotenv import load_dotenv
load_dotenv()
from RandomWordGenerator import RandomWord


class MarqetaData:

    base_url = os.getenv('base_url')
    users_endpoint = base_url+'users'
    card_product_endpoint = base_url+'cardproducts'
    cards_endpoint = base_url+'cards'
    transaction_endpoint = base_url+'simulate/authorization'
    funding_endpoint = base_url+'fundingsources/program'
    gpaorder_endpoint = base_url+'gpaorders'
    card_transitions_endpoint = base_url+'cardtransitions'
    clear_transaction_endpoint = base_url+'simulate/clearing'
    


    @staticmethod
    def create_auth():
        auth = HTTPBasicAuth(os.getenv('application_token'), os.getenv('admin_access_token'))
        return auth

    @staticmethod
    def create_headers():
        headers = {
            "Content-Type": "application/json"
        }
        return headers
    
    @staticmethod
    def create_users_data(first, last):
        data = json.dumps({
            "first_name": first,
            "last_name": last,
            "active": True
        })
        url = MarqetaData.users_endpoint
        response = requests.post(url, data=data, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        assert response.status_code == 201, f'Bad Request Code: {response.status_code}'
        response_content_dict = json.loads(response.content)
        return response_content_dict

    @staticmethod
    def create_card_products_data(card_product_name):
        curr_date = str(date.today())
        data = json.dumps({
            "start_date": curr_date,
            "name": card_product_name,
            "config": {
                "fulfillment": {
                    "payment_instrument": "VIRTUAL_PAN"
                },
                "poi": {
                    "ecommerce": True
                },
                "card_life_cycle": {
                    "activate_upon_issue": True
                }
            }
        })
        url = MarqetaData.card_product_endpoint
        response = requests.post(url, data=data, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        assert response.status_code == 201, f'Bad Request Code: {response.status_code}'
        response_content_dict = json.loads(response.content)
        return response_content_dict

    @staticmethod
    def create_cards_data(user_tok, card_product_tok):
        data = json.dumps({
                "user_token": user_tok,
                "card_product_token": card_product_tok
        })
        url = MarqetaData.cards_endpoint
        response = requests.post(url, data=data, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        assert response.status_code == 201, f'Bad Request Code: {response.status_code}'
        response_content_dict = json.loads(response.content)
        return response_content_dict

    @staticmethod
    def create_transaction_data(amount, mid, token):
        data = json.dumps({
            "amount": amount,
            "mid": mid,
            "card_token": token
        })
        url = MarqetaData.transaction_endpoint
        response = requests.post(url, data=data, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        assert response.status_code == 201, f'Bad Request Code: {response.status_code}'
        response_content_dict = json.loads(response.content)
        return response_content_dict

    @staticmethod
    def create_funding_source_data(card_product_name):
        data = json.dumps({
            "name": card_product_name,
        })
        url = MarqetaData.funding_endpoint
        response = requests.post(url, data=data, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        assert response.status_code == 201, f'Bad Request Code: {response.status_code}'
        response_content_dict = json.loads(response.content)
        return response_content_dict

    @staticmethod
    def create_gpaorder_data(user_tok, amount, funding_token, currency_code):
        data = json.dumps({
            "user_token": user_tok,
            "amount": str(amount),
            "currency_code": currency_code,
            "funding_source_token": funding_token
        })
        url = MarqetaData.gpaorder_endpoint
        response = requests.post(url, data=data, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())
        assert response.status_code == 201, f'Bad Request Code: {response.status_code}'
        response_content_dict = json.loads(response.content)
        return response_content_dict

    
    @staticmethod
    def gen_random_word():
        random_words = RandomWord()
        return random_words.generate()


