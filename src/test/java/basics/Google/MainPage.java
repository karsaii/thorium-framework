package basics.Google;

import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.namespaces.DriverWaits;

public interface MainPage {
    static DriverFunction<Boolean> navigateTo() {
        return DriverWaits.waitNavigatedTo("https://www.google.com/", 300, 3000);
    }
}
