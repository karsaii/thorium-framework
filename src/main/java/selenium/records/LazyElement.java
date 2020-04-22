package selenium.records;

import selenium.namespaces.extensions.boilers.DriverFunction;
import org.openqa.selenium.WebElement;
import selenium.abstracts.AbstractLazyElement;
import selenium.namespaces.Driver;
import selenium.records.lazy.LazyIndexedElementParameters;

import java.util.Map;
import java.util.function.Predicate;


public class LazyElement extends AbstractLazyElement<LazyIndexedElementParameters> {
    public LazyElement(String name, Map<String, LazyIndexedElementParameters> parameters, Predicate<LazyIndexedElementParameters> validator) {
        super(name, parameters, validator);
    }

    @Override
    public DriverFunction<WebElement> get() {
        return Driver.getLazyElement(this);
    }
}
