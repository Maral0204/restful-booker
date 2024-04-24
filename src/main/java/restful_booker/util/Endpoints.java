package restful_booker.util;

public final class Endpoints {
    public static final String BASE_URL = "https://restful-booker.herokuapp.com/";
    public static final String AUTH = "auth";
    public static final String BOOKING = "booking";

    public static String getAuthUrl(){
        return AUTH;
    }
    public static String getBookingsUrl(){
        return BOOKING;
    }

    public static String getBookingUrl(int bookingId){
        return getBookingsUrl() + "/" + bookingId;
    }
    public static String createBookingUrl(){
        return BOOKING;
    }

}
