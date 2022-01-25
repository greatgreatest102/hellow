package TestData;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static TestData.RequestSpec.requestSpecification;
import static io.restassured.RestAssured.given;

public class UserData {

    private static Faker faker = new Faker();

    public Map<Object, Object> createUserData(){
        Map<Object, Object> userData = new HashMap<>();
        userData.put("address1", faker.address().streetAddress());
        userData.put("address2", faker.address().streetAddress());
        userData.put("city", faker.address().city());
        userData.put("state", "GA");
        userData.put("zip", faker.address().zipCode());
        userData.put("country", "USA");
        userData.put("passport_number", "PassportNumber123");
        userData.put("passport_expiration_date", "2019-12-31");
        userData.put("id_card_number", "IDCardNumber123");
        userData.put("id_card_expiration_date","2019-12-31");
        userData.put("nationality", "Earthlings");
        userData.put("company", "BetterWorld inc");
        userData.put("password", "Testing101!");
       return userData;
    }

    public Map<Object, Object> userTransitionData(String userToken){
        Map<Object, Object> transitionData = new HashMap<>();
        transitionData.put("status","SUSPENDED");
        transitionData.put("reason_code","00");
        transitionData.put("reason", "Test for 00");
        transitionData.put("channel", "API");
        transitionData.put("user_token", userToken);
       return transitionData;
    }

    // API documentation https://www.marqeta.com/docs/api-explorer/
    public Map<Object, Object> createFullUserData(){
        Map<Object, Object> userData = new HashMap<>();
        userData.put("Address1", faker.address().streetAddress());
        userData.put("Address2", faker.address().streetAddress());
        userData.put("City", faker.address().city());
        userData.put("State", "GA");
        userData.put("Zip", faker.address().zipCode());
        userData.put("Country", "USA");
        userData.put("passport_number", "PassportNumber123");
        userData.put("passport_expiration_date", "2019-12-31");
        userData.put("id_card_number", "IDCardNumber123");
        userData.put("id_card_expiration_date","2019-12-31");
        userData.put("nationality", "Earthlings");
        userData.put("company", "BetterWorld inc");
        userData.put("password", "Testing101!");
        userData.put("token", new Random().nextInt());
        userData.put("active", true);
        userData.put("honorific", "Mr");
        userData.put("gender", "M");
        userData.put("ip_address", "127.0.0.1");
        userData.put("first_name", faker.name().firstName());
        userData.put("last_name", faker.name().lastName());
        userData.put("phone", faker.phoneNumber().cellPhone());
        userData.put("email", "interview@marqetas.com");
        userData.put("uses_parent_account", false);
        userData.put("parent_token", null);
        userData.put("ssn", "111222334");
        userData.put("birth_date", "1980-06-10");
        userData.put("corporate_card_holder", false);
        userData.put("notes", "This is the notes section");
      return userData;
    }

    public String getUserToken(){
        Map<Object, Object> userData = createUserData();
        String userToken =
            given()
                .spec(requestSpecification())
                .when().body(userData)
                .post("/users").then()
                .extract().response().andReturn().jsonPath().get("token").toString();
        return userToken;
    }
}