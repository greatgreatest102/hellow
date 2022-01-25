require_relative '../spec/spec_helper'

describe 'Cards: ' do

  before(:each) do
    @card_product_data = CardData.create_card_product("#{Config.base_url}", @headers)
  end

  context '/POST' do
    ##
    # 1. To create a card, the card endpoint needs:
    # a. A valid user, using the user's token
    # b. A card product token
    ##

    it 'should create card successfully' do
      data = {
              card_product_token: @card_product_data['token'],
              user_token: 'Add-user-token'
      }
      card = RestClient.post("#{host}/cards", data.to_json, headers)
      # Write 3 assertions for the card object
    end

    # 2. Write a negative test here
    # 3. Write a test to create card with an empty user request payload
  end
end
