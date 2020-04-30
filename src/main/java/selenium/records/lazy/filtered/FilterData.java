package selenium.records.lazy.filtered;

import org.openqa.selenium.WebElement;
import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.records.element.finder.ElementFilterParameters;

import java.util.Objects;
import java.util.function.Function;

public class FilterData<T> implements Function<ElementFilterParameters, DriverFunction<WebElement>> {
    public final boolean isFiltered;
    public final Function<ElementFilterParameters, Function<T, DriverFunction<WebElement>>> handler;
    public final T filterParameter;

    public FilterData(boolean isFiltered, Function<ElementFilterParameters, Function<T, DriverFunction<WebElement>>> handler, T filterParameter) {
        this.isFiltered = isFiltered;
        this.handler = handler;
        this.filterParameter = filterParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (FilterData<?>) o;
        return Objects.equals(isFiltered, that.isFiltered) && Objects.equals(handler, that.handler) && Objects.equals(filterParameter, that.filterParameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFiltered, handler, filterParameter);
    }

    @Override
    public DriverFunction<WebElement> apply(ElementFilterParameters parameters) {
        return handler.apply(parameters).apply(filterParameter);
    }
}
