package Test;

import TestData.CardData;
import TestData.TransactionData;
import TestData.UserData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;

import static TestData.RequestSpec.requestSpecification;
import static io.restassured.RestAssured.given;

public class TransactionTest {
    /**
     * HINT -
     * To authorize a transaction, the transaction endpoint needs:
     * 1. A valid user, using the user's token
     * 2. A card product token
     * 3. A valid card, loaded with funds
     */

    float transactionAmount = 81.20f;
    float gpaAmount = 200f;
    String userToken = null;
    String cardProductToken = null;
    String cardFundingToken = null;
    String cardToken = null;

    @Test
    public void transactionClearingTest() {
        UserData userData = new UserData();
        CardData cardData = new CardData();

        userToken = userData.getUserToken();
        cardProductToken = cardData.createCardProduct();
        cardToken = cardData.createCard(cardProductToken, userToken);
        cardData.transitionCard(cardToken);
        cardFundingToken = cardData.createFundingData();
        cardData.createGPA(userToken, gpaAmount, cardFundingToken);

        Map<Object, Object> transactionAuthorization = new TransactionData().authorizationData(transactionAmount,cardToken);
        Response authorizationResponse =
            given()
                .spec(requestSpecification())
                .when().body(transactionAuthorization)
                .post("/simulate/authorization")
                .then().extract().response();

        Map<Object, Object> transactionClearing = new TransactionData().transactionClearingData(transactionAmount, cardToken);
        Response clearingResponse =
            given()
                .spec(requestSpecification())
                .when().body(transactionClearing)
                .post("/simulate/clearing")
                .then().extract().response();
    }

    /**
     * ## Tasks
     * # 0. Add assertions to the transaction tests
     * # 1. DRY the test above
     * # 2. Write a negative test here
     * # 3. Write a test to create card with an empty user request payload
     */
}
