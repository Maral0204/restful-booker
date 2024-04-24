package restful_booker.request.auth;

import io.restassured.response.Response;
import restful_booker.request.BaseRequest;
import restful_booker.util.Endpoints;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class CreateTokenRequest {

    public static Response createTokenRequest(JSONObject authData) {
        return given()
                .spec(BaseRequest.setUp())
                .body(authData.toString())
                .when()
                .post(Endpoints.getAuthUrl())
                .then()
                .extract()
                .response();
    }
}
