
module TransactionData

  @base_clearing = lambda {
     {
      network: 'VISA',
      card_token: 'card_token',
      amount: 'amount',
      network_reference_id: 'network_reference_id',
      acquirer_reference_id: 'acquirer_reference_id',
      settlement_date: '2019-02-23',
      reason_code: 9000
  }
  }

  def clear_visa_transaction(amount, card_token, network_ref_id, acquirer_ref_id, headers)
    @modified_clearing = @base_clearing.call.clone
    @modified_clearing.update({
                                  amount: amount,
                                  card_token: card_token,
                                  network_reference_id: network_ref_id,
                                  acquirer_reference_id: acquirer_ref_id
                        })
    JSON.parse(RestClient.post("#{Config.jcard_url}/simulate/advanced/clearing", @modified_clearing.to_json, headers))
  end
end
