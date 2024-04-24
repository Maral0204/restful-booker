package restful_booker.bookings;

import groovy.util.logging.Slf4j;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import restful_booker.BaseTest;
import restful_booker.helpers.BookingService;
import restful_booker.request.booking.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Получить и отфильтровать список броней")
public class GetBookingIdsTest extends BaseTest {
    List<BookingDto> bookings;
    @BeforeEach
    public void setup() {
        step("Создать 5 бронирований с рандомными данными и 1 для фильтрации");
        BookingService bookingService = new BookingService();
        bookings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String firstName = "Name" + i;
            String lastName = "lastName" + i;
            int totalPrice = 1000 + i * 100;
            boolean depositPaid = i < 2;
            String checkIn = "2024-0" + (5 + i % 2) + "-01";
            String checkOut = "2024-0" + (5 + i % 2) + "-10";
            String additionalNeeds = "comment " + i;
            bookings.add(bookingService.createBookingDto(firstName, lastName, totalPrice, depositPaid, checkIn, checkOut, additionalNeeds));
        }
        String firstName = "Maral";
        String lastName = "Test";
        int totalPrice = 1010;
        boolean depositPaid = true;
        String checkIn = "2024-06-23";
        String checkOut = "2024-07-23";
        String additionalNeeds = "нет";
        bookings.add(bookingService.createBookingDto(firstName, lastName, totalPrice, depositPaid, checkIn, checkOut, additionalNeeds));
        bookings.forEach(this::createBooking);
    }
    private void createBooking(BookingDto booking) {
        step("Проверить создание бронирований");
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        assertThat(createBookingResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    @Test
    @Tag("regress")
    void filterBookingsByNameAndDates() {
        step("Фильтруем бронирования по имени и датам");
        String filterName = "Maral";
        String filterCheckIn = "2024-06-23";
        String filterCheckOut = "2024-07-23";
        List<BookingDto> filteredBookings = bookings.stream()
                .filter(b -> b.getFirstname().equals(filterName)
                        && b.getBookingDates().getCheckin().equals(filterCheckIn)
                        && b.getBookingDates().getCheckout().equals(filterCheckOut))
                .collect(Collectors.toList());

        step("Проверяем, что найдены бронирования с заданными параметрами");
        assertThat(filteredBookings).isNotEmpty();
        assertThat(filteredBookings).allMatch(booking ->
                booking.getFirstname().equals(filterName)
                        && booking.getBookingDates().getCheckin().equals(filterCheckIn)
                        && booking.getBookingDates().getCheckout().equals(filterCheckOut));
        System.out.println("Result after filter" +filteredBookings);
    }


    @DisplayName("Негативный кейс - Получить и отфильтровать список броней по неверным данным")
    @Test
    @Tag("regress")
    void filterBookingsByInvalidDate() {
        step("Фильтруем бронирования по имя одной брони, даты - другой");
        String filterName = "Maral";
        String filterCheckIn = "2024-05-23";
        String filterCheckOut = "2024-05-23";
        List<BookingDto> filteredBookings = bookings.stream()
                .filter(b -> b.getFirstname().equals(filterName)
                        && b.getBookingDates().getCheckin().equals(filterCheckIn)
                        && b.getBookingDates().getCheckout().equals(filterCheckOut))
                .collect(Collectors.toList());

        step("Проверяем, что бронирования с заданными параметрами НЕ найдены");
        assertThat(filteredBookings).isEmpty();
    }
}
