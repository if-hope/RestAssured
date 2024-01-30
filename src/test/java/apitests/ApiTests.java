package apitests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void getBookingIds(){
        Response response = RestAssured.when().get("/booking");
        //response.then().statusCode(200);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Invalid status code");
        response.prettyPrint();
    }

    @Test
    public void getBookingById(){
        Response response = RestAssured.given().spec(getSpec()).log().all()
                .get("/booking/425");
        response.prettyPrint();
        response.then().statusCode(200);
        //response.then().body("firstname", equalTo("John"));
        //response.then().body("firstname", emptyString());
        response.then().body("bookingdates.checkin", equalTo("2018-01-01"));
    }

    private RequestSpecification getSpec(){
        return  new RequestSpecBuilder()
                .addHeader("Authentication", "token=fhgfjgf575")
                .addCookie("Cookie1", "cookieValue")
                .build();
    }

    @Test
    public void createEmployee(){
        //{"name":"test","salary":"123","age":"23"}
        JSONObject body = new JSONObject();
        body.put("name", "Cate");
        body.put("salary", "20000");
        body.put("age", "27");

        Response response = RestAssured.given().body(body).when().post("https://dummy.restapiexample.com/api/v1/create");
        response.prettyPrint();

        JSONObject responseBody = new JSONObject(response.asString());
        responseBody.get("status");
        ((JSONObject)responseBody.get("data")).get("id");
    }

    @Test
    public void createBooking(){


        Response response = RestAssured.given().body()
                .when().post("/booking");

    }


}
