package restful_booker.request;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import restful_booker.util.Endpoints;

public class BaseRequest {
    public static RequestSpecification setUp(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(Endpoints.BASE_URL);
        requestSpecBuilder.setContentType(ContentType.JSON);

        return requestSpecBuilder.build();
    }
}
