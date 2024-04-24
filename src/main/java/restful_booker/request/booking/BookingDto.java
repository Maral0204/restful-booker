package restful_booker.request.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.path.json.JsonPath;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restful_booker.request.booking.BookingDatesDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private String firstname;
    private String lastname;
    @JsonProperty("totalprice")
    private int totalPrice;
    @JsonProperty("depositpaid")
    private boolean depositPaid;
    @JsonProperty("bookingdates")
    private BookingDatesDto bookingDates;
    @JsonProperty("additionalneeds")
    private String additionalNeeds;

}
