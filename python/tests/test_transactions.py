from datetime import date
import requests
import json
import pytest
from testdata.marqeta_data import *
from testdata.marqeta_data import HTTPBasicAuth
    
    ### Transactions

    ## HINT -
    # To authorize a transaction, the transaction endpoint needs:
    # 1. A valid user, using the user's token
    # 2. A card product token
    # 3. A valid card, loaded with funds
    ## Tasks
    # 0. Add assertions to the transaction tests
    # 1. DRY the test above
    # 2. Write a negative test here
    # 3. Write a test to create card with an empty user request payload
    ##

class TestTransactions():

    def test_transaction_creation(self):

        transaction_amount = 81.20
        gpa_amount = 200.0

        auth = HTTPBasicAuth(os.getenv('application_token'), os.getenv('admin_access_token'))
        headers = {
            "Content-Type": "application/json"
        }

        user_body = json.dumps({
          "address1": '123'+ MarqetaData.gen_random_word() + 'ave',
          "address2": MarqetaData.gen_random_word(),
          "city": 'Oakland',
          "state": "CA",
          "zip": "94612",
          "country": "US"
        })


        url = MarqetaData.users_endpoint
        user_data = requests.post(url, data=user_body, headers=headers, auth=auth)
        user_data = user_data.json()

        current_date = str(date.today())
        card_product_body = json.dumps({
            "name": MarqetaData.gen_random_word(), 
            "start_date": current_date
            })

        url = MarqetaData.card_product_endpoint
        card_product_data = requests.post(url, data=card_product_body, headers=headers, auth=auth)
        card_product_data = card_product_data.json()

        card_body = json.dumps({
          "card_product_token": card_product_data['token'], 
          "user_token": user_data['token']
          })

        url = MarqetaData.cards_endpoint
        card = requests.post(url, data=card_body, headers=headers, auth=auth)
        card = card.json()

        ## Activate card
        # Using card token
        card_transition_body = json.dumps({
          "card_token": card['token'],
          "state": 'ACTIVE',
          "channel": 'API',
          "reason": 'Activating card via data harness'
        })

        url = MarqetaData.card_transitions_endpoint
        card_transitioned = requests.post(url, data=card_transition_body, headers=headers, auth=auth)
        card_transitioned = card_transitioned.json()

        ## Funding source
        funding_source_body = json.dumps({
          "name": MarqetaData.gen_random_word(),
          "active": 'true'
        })

        url = MarqetaData.funding_endpoint
        funding_source = requests.post(url, data=funding_source_body, headers=headers, auth=auth)
        funding_source = funding_source.json()

        ## GPA Load
        gpa_load_body = json.dumps({
          "user_token": user_data['token'],
          "amount": gpa_amount,
          "currency_code": 'USD',
          "funding_source_token": funding_source['token']
        })

        url = MarqetaData.gpaorder_endpoint
        requests.post(url, data=gpa_load_body, headers=headers, auth=auth)

        # Simulate Transaction Auth
        simulate_trans_body = json.dumps({
          "card_token": card['token'],
          "amount": transaction_amount,
          "mid": '12342'
        })

        url = MarqetaData.transaction_endpoint
        simulate_trans_data = requests.post(url, data=simulate_trans_body, headers=headers, auth=auth)
        simulate_trans_data = simulate_trans_data.json()


        clear_trans_body = json.dumps({
          "amount": transaction_amount,
          "mid": '12342',
          "original_transaction_token": simulate_trans_data['transaction']['token']
        })

        url = MarqetaData.clear_transaction_endpoint
        cleared_trans_data = requests.post(url, data=clear_trans_body, headers=headers, auth=auth)
        