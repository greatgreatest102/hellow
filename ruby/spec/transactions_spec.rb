require_relative '../spec/spec_helper'

describe 'Transactions: ' do
    ## HINT -
    # To authorize a transaction, the transaction endpoint needs:
    # 1. A valid user, using the user's token
    # 2. A card product token
    # 3. A valid card, loaded with funds
    ##

    it 'should successfully authorize a transaction' do

      @transaction_amount = 81.20
      @gpa_amount = 200.0

      @headers = {:Authorization => Config.credential_with_base64,
                   :'cache-control' => 'no-cache',
                  :content_type => 'application/json', :accept => "application/json"}

      body = {
          token: SecureRandom.uuid,
          address1: Faker::Address.street_address,
          address2: Faker::Address.secondary_address,
          city: Faker::Address.city,
          state: Faker::Address.state_abbr,
          zip: Faker::Address.zip,
          country: Faker::Address.country_code[0..39]
      }

      @user_data = JSON.parse(RestClient.post("#{Config.base_url}/users", body.to_json, @headers))
      card_product_data = JSON.parse(RestClient.post("#{Config.base_url}/cardproducts",
                                                     {name: SecureRandom.uuid, start_date: "#{Date.today.year - 4}-03-17"}.to_json,
                                                     @headers))

      card_data = {card_product_token: card_product_data['token'], user_token: @user_data['token']}
      card = JSON.parse(RestClient.post("#{Config.base_url}/cards", card_data.to_json, @headers))

      ## Activate card
      # Using card token
      card_transition = {
          card_token: card['token'],
          state: 'ACTIVE',
          channel: 'API',
          reason: 'Activating card via data harness'
      }


      RestClient.post("#{Config.base_url}/cardtransitions", card_transition.to_json, @headers)

      ## Funding source
      funding_source = {
          name: "Program_Funding_Source_#{Faker::Number.number(digits: 6)}",
          active: true
      }

      @pfs = JSON.parse(RestClient.post("#{Config.base_url}/fundingsources/program", funding_source.to_json, @headers))

      ## GPA Load
      gpa_load = {
          user_token: @user_data['token'],
          amount: @gpa_amount,
          currency_code: 'USD',
          funding_source_token: @pfs['token']
      }

      RestClient.post("#{Config.base_url}/gpaorders", gpa_load.to_json, @headers)

      # Simulate Auth
      modified_sim = {
          card_token: card['token'],
          amount: @transaction_amount,
          mti: '0100',
          network: 'VISA',
          settlement_date: '2019-02-23'
      }
      to_clear_transact = JSON.parse(RestClient.post("#{Config.base_url}/simulate/authorization", modified_sim.to_json, @headers))

      modified_sim = {
          network: 'VISA',
          card_token: card['token'],
          amount: to_clear_transact['transaction']['amount'],
          reason_code: 9000
      }

      @parent_clearing = JSON.parse(RestClient.post("#{Config.base_url}/simulate/clearing", modified_sim.to_json, @headers))
    end
  
    ## Tasks
    # 0. Add assertions to the transaction tests
    # 1. DRY the test above
    # 2. Write a negative test here
    # 3. Write a test to create card with an empty user request payload
end
