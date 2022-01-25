package TestData;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static TestData.RequestSpec.requestSpecification;
import static io.restassured.RestAssured.given;

public class CardData {

    String name = UUID.randomUUID().toString();
    String date = java.time.LocalDate.now().minusYears(4).toString();
    Faker faker = new Faker();

    public String createCardProduct(){
        Map<String, String> cardProduct = new HashMap<>();
        cardProduct.put("name",name);
        cardProduct.put("start_date", date);
        String cardProductToken =
            given()
                .spec(requestSpecification())
                .when().body(cardProduct).log().all()
                .post("/cardproducts")
                .then().extract().response().andReturn().jsonPath().get("token").toString();
        return cardProductToken;
    }

    public String createCard(String cardProductToken, String userToken){
        Map<String, String> cardData = new HashMap<>();
        cardData.put("card_product_token", cardProductToken);
        cardData.put("user_token", userToken);
        String cardToken =
            given()
                .spec(requestSpecification())
                .when().body(cardData).log().all()
                .post("https://sandbox-api.marqeta.com/v3/cards")
                .then()
                .extract().response().andReturn().jsonPath().get("token").toString();
        return cardToken;
    }

    public void transitionCard(String cardToken){
        Map<String, String> cardTransition = new HashMap<>();
        cardTransition.put("card_token", cardToken);
        cardTransition.put("state", "ACTIVE");
        cardTransition.put("channel", "API");
        cardTransition.put("reason", "Card Activation");
           given()
                .spec(requestSpecification())
                .when().body(cardTransition)
                .post("https://sandbox-api.marqeta.com/v3/cardtransitions");
    }

    public String createFundingData(){
        Map<Object, Object> cardFundingData = new HashMap<>();
        cardFundingData.put("name", faker.letterify("program_Funding_Source_????"));
        cardFundingData.put("active", true);
        String cardFundingToken =
            given()
                .spec(requestSpecification())
                .when().body(cardFundingData)
                .post("https://sandbox-api.marqeta.com/v3/fundingsources/program")
                .then()
                .extract().response().andReturn().jsonPath().get("token").toString();
        return cardFundingToken;
    }

    public void createGPA(String userToken, float gpaAmount, String cardFundingToken){
        Map<Object, Object> gpaData = new HashMap<>();
        gpaData.put("user_token", userToken);
        gpaData.put("amount", gpaAmount);
        gpaData.put("currency_code", "USD");
        gpaData.put("funding_source_token", cardFundingToken);
        given()
                .spec(requestSpecification())
                .when().body(gpaData)
                .post("https://sandbox-api.marqeta.com/v3/gpaorders");
    }

}