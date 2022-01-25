package TestData;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

public class RequestSpec {
    private static RequestSpecification requestSpec;

    public static RequestSpecification requestSpecification(){
        PreemptiveBasicAuthScheme auth = new PreemptiveBasicAuthScheme();
        auth.setUserName("0d4e80c6-f51a-407c-acc8-3358e802ff2a");
        auth.setPassword("15628c5c-de82-4526-a0d5-3e854cada063");
        return requestSpec = new RequestSpecBuilder().setAuth(auth).setBaseUri("https://sandbox-api.marqeta.com/v3").setContentType("application/json").build();
    }
}