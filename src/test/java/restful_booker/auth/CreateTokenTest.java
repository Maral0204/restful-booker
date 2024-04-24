package restful_booker.auth;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import restful_booker.properties.RestfulBooker;
import restful_booker.request.auth.CreateTokenRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static restful_booker.helpers.JsonHelper.*;

@DisplayName("Получение токена")
public class CreateTokenTest {
    private JSONObject authData;
    private static String token;
    private static int bookingId;

    @DisplayName("Получение токена - позитивный кейс")
    @Test
    @Tag("regress")
    void createTokenWithValidUsernameAndPasswordTest() {
        JSONObject authData = new JSONObject();
        authData.put(USERNAME, RestfulBooker.getUsername());
        authData.put(PASSWORD, RestfulBooker.getPassword());
        Response createTokenResponse = CreateTokenRequest.createTokenRequest(authData);
        token = createTokenResponse.jsonPath().getString(TOKEN);
        System.out.println("RESPONSE TOKEN "+token);

        assertThat(createTokenResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(createTokenResponse.jsonPath().getString(TOKEN)).isNotEmpty();
    }

    //TODO тест проходит со статусом 200OK
    @DisplayName("Получение токена - невалидные данные")
    @Test
    @Tag("regress")
    void createTokenWithInvalidUsernameAndPasswordTest() {
        JSONObject authData = new JSONObject();
        authData.put(USERNAME, "");
        authData.put(PASSWORD, "");
        Response createErrorResponse = CreateTokenRequest.createTokenRequest(authData);
        token = createErrorResponse.jsonPath().getString(TOKEN);
        assertThat(createErrorResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(createErrorResponse.jsonPath().getString("reason")).isEqualTo("Bad credentials");
    }
}
