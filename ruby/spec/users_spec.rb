require_relative '../spec/spec_helper'

describe 'Users: ' do

  before(:all) do
    @headers = {:Authorization => Config.credential_with_base64,
                :content_type => 'application/json', :accept => "application/json"}
  end

    it 'should create successfully' do
      user_token = SecureRandom.uuid
      body = {
          token: user_token,
          address1: Faker::Address.street_address,
          address2: Faker::Address.secondary_address,
          city: Faker::Address.city,
          state: Faker::Address.state_abbr,
          zip: Faker::Address.zip,
          country: Faker::Address.country_code[0..39]
      }

      user = RestClient.post("#{Config.base_url}/users", body.to_json, @headers)

      expect(user['token']).not_to be_empty
      expect(user).to eq user_token
    end

    it 'should create with empty request body' do
      body = {

      }

      user = RestClient.post("#{Config.base_url}/users", body.to_json, @headers)
      expect(user['token']).not_to be_empty
    end

  it 'should have a suspended user' do

    body = {

    }

    user = RestClient.post("#{Config.base_url}/users", body.to_json, @headers)

    transition_body = {
        status: "SUSPENDED",
        reason_code: "00",
        reason: "test for 00",
        channel: "API",
        user_token: user['token']
    }

    @suspend_user = JSON.parse(RestClient.post("#{Config.base_url}/usertransitions",
                                               transition_body.to_json, @headers))
    ## Add assertions here
  end

  it 'should have a full user successfully created' do
    body = {
        token: SecureRandom.uuid,
        active: true,
        honorific: "Mr",
        gender: "M",
        ip_address: "127.0.0.1",
        first_name: Faker::Name.first_name,
        last_name: Faker::Name.last_name,
        phone: Faker::Number.number(digits: 10),
        address1: Faker::Address.street_address,
        address2: Faker::Address.secondary_address,
        city: Faker::Address.city,
        state: Faker::Address.state_abbr,
        zip: Faker::Address.zip,
        country: Faker::Address.country_code[0..39],
        email: "qa+parent_#{Faker::Internet.email}",
        uses_parent_account: false,
        parent_token: nil,
        ssn: Faker::Number.number(digits: 9),
        passport_number: "PassportNumber123",
        passport_expiration_date: "2019-12-31",
        id_card_number: "IDCardNumber123",
        id_card_expiration_date: "2019-12-31",
        nationality: Faker::Address.country[0..39],
        company: Faker::Company.name,
        password: "Testing101!",
        birth_date: "1980-06-10",
        corporate_card_holder: false,
        notes: "This is the notes section"
    }

    user = JSON.parse(RestClient.post("#{Config.base_url}/users", body.to_json, @headers))

    expect(user['active']).to eq true
    expect(user['ip_address']).to eq '127.0.0.1'
    expect(user['status']).to eq 'ACTIVE'
    # Verify the password of the user
    # Verify the passport details of the user
    # Verify the Drivers license details of the user
  end
end
