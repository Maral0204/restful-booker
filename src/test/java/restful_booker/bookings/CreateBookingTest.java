package restful_booker.bookings;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import restful_booker.BaseTest;
import restful_booker.request.booking.BookingDatesDto;
import restful_booker.request.booking.BookingDto;
import restful_booker.request.booking.CreateBookingRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static restful_booker.helpers.JsonHelper.*;

@DisplayName("Создание брони")
public class CreateBookingTest extends BaseTest {

    @Test
    @Tag("regress")
    @DisplayName("Позитивный кейс - бронь с валидными данными")
    void createBookingWithValidDataTest() {
        BookingDto booking = new BookingDto();
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        assertThat(createBookingResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(createBookingJsonPath.getString(BOOKING_BOOKING_DATES + CHECKIN)).isEqualTo(booking.getBookingDates().getCheckin());
        assertThat(createBookingJsonPath.getString(BOOKING_BOOKING_DATES + CHECKOUT)).isEqualTo(booking.getBookingDates().getCheckout());
    }

    //TODO тест проходит с невалидными данными - завести баг
    @Test
    @Tag("regress")
    @DisplayName("Негативный кейс - бронь с невалидными данными")
    void createBookingWithInvalidDatesTest() {
        BookingDto booking = new BookingDto();
        BookingDatesDto bookingDates = new BookingDatesDto();
        bookingDates.setCheckin("20240506");
        bookingDates.setCheckout("07052024");
        booking.setBookingDates(bookingDates);

        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        assertThat(createBookingResponse.htmlPath().getString("body")).isEqualTo("Invalid date");
    }

    @Test
    @Tag("regress")
    @DisplayName( "Создать бронь при НЕ внесенном депозите")
    void createBookingWithDepositUnpaidTest() {
        BookingDto booking = new BookingDto();
        booking.setDepositPaid(false);
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);

        assertThat(createBookingResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}

