package apitests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OneTest {

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void getBookingIds(){
        Response response = RestAssured.when().get("/booking");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Invalid status code");
        response.prettyPrint();
    }
}
