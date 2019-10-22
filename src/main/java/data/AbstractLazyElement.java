package data;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Predicate;

public abstract class AbstractLazyElement<T> implements IElement {
    public final String name;
    public final Map<String, T> parameters;
    public final Predicate<T> validator;

    public AbstractLazyElement(String name, Map<String, T> parameters, Predicate<T> validator) {
        this.name = name;
        this.parameters = parameters;
        this.validator = validator;
    }

    @Override
    public abstract DriverFunction<WebElement> get();
}
