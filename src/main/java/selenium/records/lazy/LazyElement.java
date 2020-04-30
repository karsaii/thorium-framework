package selenium.records.lazy;

import selenium.namespaces.extensions.boilers.DriverFunction;
import org.openqa.selenium.WebElement;
import selenium.abstracts.AbstractLazyElement;
import selenium.namespaces.Driver;
import selenium.records.lazy.filtered.LazyFilteredElementParameters;

import java.util.Map;
import java.util.function.Predicate;


public class LazyElement extends AbstractLazyElement<LazyFilteredElementParameters> {
    public LazyElement(String name, Map<String, LazyFilteredElementParameters> parameters, Predicate<LazyFilteredElementParameters> validator) {
        super(name, parameters, validator);
    }

    @Override
    public DriverFunction<WebElement> get() {
        return Driver.getLazyElement(this);
    }
}
