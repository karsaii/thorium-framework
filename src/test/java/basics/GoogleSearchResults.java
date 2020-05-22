package basics;

import basics.Google.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import selenium.drivers.DriverFactory;

public class GoogleSearchResults {
    @BeforeAll
    static void setup() {
        final var driver = DriverFactory.get();
    }

    /*@DisplayName("Navigated to Google")
    @Test
    void navigateToGoogle() {
        final var result = MainPage.navigateTo().apply(DriverFactory.get());
        Assertions.assertTrue(result.status, result.message.toString());
    }*/
}
