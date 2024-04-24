package restful_booker.request.booking;

import io.restassured.response.Response;
import restful_booker.request.BaseRequest;
import restful_booker.util.Endpoints;

import static io.restassured.RestAssured.given;

public class CreateBookingRequest {

    public static Response createBookingRequest(BookingDto bookingDto){
        return given()
                .spec(BaseRequest.setUp())
                .body(bookingDto)
                .when()
                .post(Endpoints.createBookingUrl())
                .then()
                .extract()
                .response();
    }
}
