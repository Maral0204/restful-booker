package restful_booker.properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestfulBooker {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    public static String getUsername() {
        return getProperty(USERNAME);
    }
    public static String getPassword() {
        return getProperty(PASSWORD);
    }
    private static String getProperty(String key) {
        return ResourceBundle.getBundle("restful-booker").getString(key);
    }
}
