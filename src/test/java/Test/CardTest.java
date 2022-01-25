package Test;

import TestData.CardData;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static TestData.RequestSpec.requestSpecification;
import static io.restassured.RestAssured.given;

/**
 * To create a card, the card endpoint needs:
 *    1. card_product_token:  generated in the BeforeTest hook
 *    2. user_token: a valid user token => you need to create this token using the /users endpoint
 * Add at least 3 assertions to the createCardPositiveTest() method
 * Write a negative test to the createCardNegativeTest() method
 * Write a test to create card with an empty user request payload
 */

public class CardTest {
    String cardProductToken = null;
    @BeforeTest
    public String dataSetup(){
        cardProductToken = new CardData().createCardProduct();
        return cardProductToken;
    }

    @Test
    public void createCardPositiveTest(){
        Map<String, String> cardData = new HashMap<>();
        cardData.put("card_product_token", cardProductToken);
        cardData.put("user_token", "Add_user_Token_Here");
            given()
                .spec(requestSpecification())
                .when().body(cardData)
                .post("/cards");
    }
    @Test
    public void createCardNegativeTest(){
    }

    @Test
    public void createCardWithEmptyBodyTest(){
    }
}
