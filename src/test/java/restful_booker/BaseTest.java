package restful_booker;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import restful_booker.properties.RestfulBooker;
import restful_booker.request.auth.CreateTokenRequest;

import static restful_booker.helpers.JsonHelper.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    private static String token;

    @BeforeEach
    void auth() {
        JSONObject authData = new JSONObject();
        authData.put(USERNAME, RestfulBooker.getUsername());
        authData.put(PASSWORD, RestfulBooker.getPassword());
        Response createTokenResponse = CreateTokenRequest.createTokenRequest(authData);
        token = createTokenResponse.jsonPath().getString(TOKEN);
    }
}
