from datetime import date
import requests
import json
import pytest
from testdata.marqeta_data import MarqetaData



### Cards
##
    # 1. To create a card, the card endpoint needs:
    # a. A valid user, using the user's token
    # b. A card product token
##

class TestCards():
    
    @staticmethod
    def test_card_creation():
        card_product = MarqetaData.create_card_products_data(MarqetaData.gen_random_word())
        body = json.dumps({
              "card_product_token": card_product['token'],
              "user_token": 'Add-user-token'
        })
      
        url = MarqetaData.users_endpoint
        card_product = requests.post(url, data=body, headers=MarqetaData.create_headers(), auth=MarqetaData.create_auth())

      # Write 3 assertions for the card object

    # 2. Write a negative test here
    # 3. Write a test to create card with an empty user request payload