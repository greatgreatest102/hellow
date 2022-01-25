require_relative '../../spec/spec_helper'

module CardData

  @base_card_product = lambda {
    {name: SecureRandom.uuid, start_date: "#{Date.today.year - 4}-03-17"}
  }

  def create_card_product(host, headers)
    @modified_data = @base_card_product.call.clone
    JSON.parse(RestClient.post("#{host}/cardproducts", @modified_data.to_json, headers))
  end

  def create_card(host, card_product_data, user_token, headers)
    data = {card_product_token: card_product_data, user_token: user_token}
    JSON.parse(RestClient.post("#{host}/cards", data.to_json, headers))
  end

  def transition_card(host, card_token, headers)
    card_transition = {
        card_token: card_token,
        state: 'ACTIVE',
        channel: 'API',
        reason: 'Activating card via data harness'
    }

    JSON.parse(RestClient.post("#{host}/cardtransitions", card_transition.to_json, headers))
  end

  def create_funding_data(host, headers)
    ## Funding source
    funding_source = {
        name: "Program_Funding_Source_#{Faker::Number.number(digits: 6)}",
        active: true
    }

    JSON.parse(RestClient.post("#{host}/fundingsources/program", funding_source.to_json, headers))
  end

  def create_gpa(host, user_token, gpa_amount, pfs_token, headers)
    ## GPA Load
    gpa_load = {
        user_token: user_token,
        amount: gpa_amount,
        currency_code: 'USD',
        funding_source_token: pfs_token
    }

    JSON.parse(RestClient.post("#{host}/gpaorders", gpa_load.to_json, headers))
  end

  def create_transaction_auth(host, card_token, amount, network, headers)
    modified_sim = {
        card_token: card_token,
        amount: amount,
        mti: '0100',
        network: network,
        settlement_date: '2019-02-23'
    }
    JSON.parse(RestClient.post("#{host}/simulate/advanced/authorization", modified_sim.to_json, headers))
  end

  def clear_auth_transaction(host, network, card_token, amount, net_ref_id, acq_ref_id, headers)
    modified_sim = {
        network: network,
        card_token: card_token,
        amount: amount,
        network_reference_id: net_ref_id,
        acquirer_reference_id: acq_ref_id,
        settlement_date: '2019-02-23',
        reason_code: 9000
    }

    @parent_clearing = JSON.parse(RestClient.post("#{host}/simulate/advanced/clearing", modified_sim.to_json, headers))
  end
end
