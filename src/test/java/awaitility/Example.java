package awaitility;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.awaitility.core.ThrowingRunnable;
import org.testng.Assert;
import org.testng.annotations.Test;
import requests.CreateBooking;
import responses.CreateBookingResponse;

import java.util.concurrent.TimeUnit;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class Example {


    public CreateBooking getBookingById() {
        Response response = RestAssured.given().log().all()
                .get("https://restful-booker.herokuapp.com/booking/95");
        //response.prettyPrint();
        return response.as(CreateBooking.class);
    }

    public void waitUntilConditions(int atMost, int pollInterval, ThrowingRunnable condition){
        Awaitility.await()
                .atMost(atMost, TimeUnit.MINUTES)
                .pollInterval(pollInterval, TimeUnit.SECONDS)
                //.untilAsserted(() -> Assert.assertEquals(response.as(CreateBooking.class).getAdditionalNeeds(), "Breakfast"));
                .untilAsserted(condition);
    }

    @Test
    public void getBookingByIdTest(){
        waitUntilConditions(1, 5, () -> assertEquals(getBookingById().getAdditionalNeeds(), "Extra pillows please"));
    }
}
