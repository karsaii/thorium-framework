package selenium.element.functions;

import selenium.namespaces.extensions.boilers.DriverFunction;
import org.openqa.selenium.By;
import selenium.element.Element;
import selenium.enums.SingleGetter;
import selenium.records.LazyElement;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ClearData {
    public final Function<LazyElement, DriverFunction<Boolean>> clearLazy;
    public final BiFunction<By, SingleGetter, DriverFunction<Boolean>> clearByWithGetter;
    public final Function<By, DriverFunction<Boolean>> clearBy;

    public ClearData(
        Function<LazyElement, DriverFunction<Boolean>> clearLazy,
        BiFunction<By, SingleGetter, DriverFunction<Boolean>> clearByWithGetter,
        Function<By, DriverFunction<Boolean>> clearBy
    ) {
        this.clearLazy = clearLazy;
        this.clearByWithGetter = clearByWithGetter;
        this.clearBy = clearBy;
    }

    public ClearData() {
        this.clearLazy = Element::clear;
        this.clearByWithGetter = Element::clear;
        this.clearBy = Element::clear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var clearData = (ClearData) o;
        return Objects.equals(clearLazy, clearData.clearLazy) && Objects.equals(clearByWithGetter, clearData.clearByWithGetter) && Objects.equals(clearBy, clearData.clearBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clearLazy, clearByWithGetter, clearBy);
    }
}
