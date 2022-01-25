package TestData;

import java.util.HashMap;
import java.util.Map;

public class TransactionData {
    public Map<Object, Object> authorizationData(float amount, String cardToken){
        Map<Object, Object> transactionData = new HashMap<>();
        transactionData.put("network","VISA");
        transactionData.put("card_token", cardToken);
        transactionData.put("amount", amount);
        transactionData.put("settlement_date", "2019-02-23");
        transactionData.put("mti", "0100");
        return transactionData;
    }

    public Map<Object, Object> transactionClearingData( float amount, String cardToken){
        Map<Object, Object> transactionData = new HashMap<>();
        transactionData.put("network","VISA");
        transactionData.put("card_token", cardToken);
        transactionData.put("amount", amount);
        transactionData.put("reason_code", 9000);
        return transactionData;
    }
}
