package restful_booker.bookings;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import restful_booker.BaseTest;
import restful_booker.helpers.BookingService;
import restful_booker.request.booking.BookingDto;
import restful_booker.request.booking.GetBookingRequest;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static restful_booker.helpers.BookingService.*;
import static restful_booker.helpers.JsonHelper.*;
import static restful_booker.request.booking.CreateBookingRequest.createBookingRequest;

@DisplayName("Получить данные по бронированию по существующему ID")
public class GetBookingTest extends BaseTest {
    private int bookingId;
    BookingDto bookingDto;

    @BeforeEach
    public void setup() {
    step ("Создание бронирования с валидными данными");
        BookingService bookingService = new BookingService();
        String firstName = "Maral";
        String lastName = "Test";
        int totalPrice = 1010;
        boolean depositPaid = true;
        String checkIn = "2024-06-23";
        String checkOut = "2024-07-23";
        String additionalNeeds = "нет";
        bookingDto=bookingService.createBookingDto(firstName, lastName, totalPrice, depositPaid, checkIn, checkOut, additionalNeeds);
        Response getBookingResponse = createBookingRequest(bookingDto);
        JsonPath getBookingJsonPath = getBookingResponse.jsonPath();
        bookingId=getBookingId(getBookingJsonPath);
    }

    @Test
    @Tag("regress")
    void getBookingById() {
        step("Получение бронирования по ID и проверка данных");
        var getBookingResponse = GetBookingRequest.getBookingRequest(bookingId);
        assertThat(getBookingResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        JsonPath getBooking = getBookingById(bookingId);
        System.out.println("  bookingId "+bookingId);
        assertThat(getBooking.getString(FIRSTNAME)).isEqualTo(bookingDto.getFirstname());
        assertThat(getBooking.getString(LASTNAME)).isEqualTo(bookingDto.getLastname());
        assertThat(getBooking.getInt(TOTAL_PRICE)).isEqualTo(bookingDto.getTotalPrice());
        assertThat(getBooking.getBoolean(DEPOSIT_PAID)).isEqualTo(bookingDto.isDepositPaid());
        assertThat(getBooking.getString(BOOKING_DATES + "." + CHECKIN)).isEqualTo(bookingDto.getBookingDates().getCheckin());
        assertThat(getBooking.getString(BOOKING_DATES + "." + CHECKOUT)).isEqualTo(bookingDto.getBookingDates().getCheckout());
        assertThat(getBooking.getString(ADDITIONAL_NEEDS)).isEqualTo(bookingDto.getAdditionalNeeds());
    }

    private JsonPath getBookingById(int bookingId) {
        Response getBookingResponse = GetBookingRequest.getBookingRequest(bookingId);
        return getBookingResponse.jsonPath();
    }
}
