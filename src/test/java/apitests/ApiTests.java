package apitests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import requests.BookingDates;
import requests.CreateBooking;
import responses.BookingIds;
import responses.CreateBookingResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {

    private int id;

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }


    private List<BookingIds> findAllIds(){
        return RestAssured.when().get("/booking")
                .then().extract().as(new TypeRef<>() {});
    }

    @Test
    public void getBookingIds(){
        Response response = RestAssured.when().get("/booking");
        //response.then().statusCode(200);
        int statusCode = response.getStatusCode();
        int resId = findAllIds().get(2).getBookingId();
        System.out.println(resId);
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
        response.then().assertThat().body(matchesJsonSchemaInClasspath("jsonSchemaGetAllBooking.json"));
    }

    private RequestSpecification getSpec(){
        return  new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
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
        LocalDate localCheckIn = LocalDate.parse("2024-02-02", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate localCheckOut = LocalDate.parse("2024-02-20", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        CreateBooking body = new CreateBooking().builder()
                .firstname("Bob")
                .lastname("Green")
                .totalPrice(357)
                .depositPaid(true)
                .bookingDates(new BookingDates(localCheckIn, localCheckOut))
                .additionalNeeds("Coffee")
                .build();


        Response response = RestAssured.given().log().all()
                .spec(getSpec()).body(body)
                .when().post("/booking");

        response.prettyPrint();
        response.then().statusCode(200);
        id = response.as(CreateBookingResponse.class).getBookingId();

        Assert.assertEquals(response.as(CreateBookingResponse.class).getBooking().getFirstname(), "Bob");
        Assert.assertEquals(response.as(CreateBookingResponse.class).getBooking().getTotalPrice(), 357);
        Assert.assertTrue(response.as(CreateBookingResponse.class).getBooking().isDepositPaid(), "deposit paid is false");
        Assert.assertEquals(response.as(CreateBookingResponse.class).getBooking().getBookingDates().getCheckIn().toString(), "2024-02-02");
        System.out.println(id);


    }


}
