package basics.Google;

import core.extensions.interfaces.DriverFunction;
import selenium.namespaces.DriverWaits;

public interface MainPage {
    static DriverFunction<Boolean> navigateTo() {
        return DriverWaits.waitNavigatedTo("https://www.google.com/", 300, 3000);
    }
}
