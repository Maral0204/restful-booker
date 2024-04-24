package restful_booker.helpers;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restful_booker.request.booking.BookingDatesDto;
import restful_booker.request.booking.BookingDto;
import restful_booker.request.booking.GetBookingsRequest;

import java.util.List;

import static restful_booker.helpers.JsonHelper.ID;


public class BookingService {
    public BookingDto createBookingDto(String firstName, String lastName, int totalPrice, boolean depositPaid, String checkIn, String checkOut, String additionalNeeds) {
        BookingDto booking = new BookingDto();
        booking.setFirstname(firstName);
        booking.setLastname(lastName);
        booking.setTotalPrice(totalPrice);
        booking.setDepositPaid(depositPaid);

        BookingDatesDto bookingDates = new BookingDatesDto();
        bookingDates.setCheckin(checkIn);
        bookingDates.setCheckout(checkOut);
        booking.setBookingDates(bookingDates);

        booking.setAdditionalNeeds(additionalNeeds);
        return booking;
    }
    private static List<Integer> getBookingIds() {
        Response getBookingIdsResponse = GetBookingsRequest.getBookingRequest();
        JsonPath jsonPath = getBookingIdsResponse.jsonPath();
        return jsonPath.getList(ID);
    }

    public static int getBookingId(JsonPath jsonPath) {
        return Integer.parseInt(jsonPath.getString(ID));
    }
}
