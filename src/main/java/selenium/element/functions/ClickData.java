package selenium.element.functions;

import selenium.namespaces.extensions.boilers.DriverFunction;
import org.openqa.selenium.By;
import selenium.element.Element;
import selenium.enums.SingleGetter;
import selenium.records.LazyElement;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ClickData {
    public final Function<LazyElement, DriverFunction<Boolean>> clickLazy;
    public final BiFunction<By, SingleGetter, DriverFunction<Boolean>> clickByWithGetter;
    public final Function<By, DriverFunction<Boolean>> clickBy;

    public ClickData(
        Function<LazyElement, DriverFunction<Boolean>> clickLazy,
        BiFunction<By, SingleGetter, DriverFunction<Boolean>> clickByWithGetter,
        Function<By, DriverFunction<Boolean>> clickBy
    ) {
        this.clickLazy = clickLazy;
        this.clickByWithGetter = clickByWithGetter;
        this.clickBy = clickBy;
    }

    public ClickData() {
        this.clickLazy = Element::click;
        this.clickByWithGetter = Element::click;
        this.clickBy = Element::click;
    }
}
