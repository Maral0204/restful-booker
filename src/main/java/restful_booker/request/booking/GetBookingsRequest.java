package restful_booker.request.booking;

import io.restassured.response.Response;
import restful_booker.request.BaseRequest;
import restful_booker.util.Endpoints;

import static io.restassured.RestAssured.given;

public class GetBookingsRequest {

    public static Response getBookingRequest(){
        return given()
                .spec(BaseRequest.setUp())
                .when()
                .get(Endpoints.getBookingsUrl())
                .then()
                .extract()
                .response();
    }


}
