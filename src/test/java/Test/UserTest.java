package Test;

import TestData.UserData;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static TestData.RequestSpec.requestSpecification;
import static io.restassured.RestAssured.given;

public class UserTest {

    @Test
    public void createUserPositiveTest(){
        Map<Object, Object> userData = new UserData().createUserData();
        given()
            .spec(requestSpecification())
            .when().body(userData)
            .post("/users");
    }

    @Test
    public void createUserEmptyBodyTest(){
        Map<Object, Object> userData = new HashMap<>();
        given()
            .spec(requestSpecification())
            .when().body(userData)
            .post("/users");
    }

    @Test
    public void suspendedUserTest(){
        Map<Object, Object> userData1 = new UserData().createUserData();

        String userToken =
        given()
            .spec(requestSpecification())
            .when().body(userData1)
            .post("/users").then()
                .extract().response().asString();
        JsonPath js = new JsonPath(userToken);
        String ust = js.getString("token");
        System.out.println("token------>" +ust);


        Map<Object, Object> userData = new UserData().userTransitionData(ust);
        System.out.println("RequestBody------->"+userData);
        String ls=given().log().all()
            .spec(requestSpecification())
            .when().body(userData)
            .post("/usertransitions")
                .then().assertThat().statusCode(201).extract().response().asString();

            JsonPath js1 = new JsonPath(ls);
          String toBeAsserted = js1.getString("reason_code");
          Assert.assertTrue(toBeAsserted.equalsIgnoreCase("00"));
        System.out.println("love"+toBeAsserted);



    }

    @Test
    public void createUserWithFullDataTest(){
        Map<Object, Object> userData = new UserData().createFullUserData();
        given()
            .spec(requestSpecification())
            .when().body(userData)
            .post("/users");

        /**
         * Verify the password of the user
         * Verify the passport details of the user
         * Verify the Drivers license details of the user
         */
    }
}
