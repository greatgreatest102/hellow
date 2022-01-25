require_relative '../../spec/spec_helper'

module UserData

  @base_user = lambda {
    {

        address1: Faker::Address.street_address,
        address2: Faker::Address.secondary_address,
        city: Faker::Address.city,
        state: Faker::Address.state_abbr,
        zip: Faker::Address.zip,
        country: Faker::Address.country_code[0..39]
    }
  }

  def create_user(host, options = {:active => true}, headers)
    @modified_parent = @base_user.call.clone
    unless options[:active]
      @modified_parent.update(
          active: false,
          identifications: [{
                                type: "SSN",
                                value: Faker::Number.number(digits: 9)
                            }]
      )
    end
    response = JSON.parse(RestClient.post("#{host}/users", @modified_parent.to_json, headers))
  end
end
