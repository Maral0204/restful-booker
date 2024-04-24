package restful_booker.request.booking;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restful_booker.request.BaseRequest;
import restful_booker.util.Endpoints;

import static io.restassured.RestAssured.given;

public class GetBookingRequest {

    public static Response getBookingRequest(int bookingId){
        return given()
                .spec(BaseRequest.setUp())
                .when()
                .get(Endpoints.getBookingUrl(bookingId))
                .then()
                .extract()
                .response();
    }


}
