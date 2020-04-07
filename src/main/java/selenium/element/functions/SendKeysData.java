package selenium.element.functions;

import core.extensions.interfaces.DriverFunction;
import core.extensions.interfaces.functional.TriFunction;
import org.openqa.selenium.By;
import selenium.element.Element;
import selenium.enums.SingleGetter;
import selenium.records.LazyElement;

import java.util.function.BiFunction;

public class SendKeysData {
    public final BiFunction<LazyElement, String, DriverFunction<Boolean>> sendKeysLazy;
    public final TriFunction<By, String, SingleGetter, DriverFunction<Boolean>> sendKeysByWithGetter;
    public final BiFunction<By, String, DriverFunction<Boolean>> sendKeysBy;

    public SendKeysData(
        BiFunction<LazyElement, String, DriverFunction<Boolean>> sendKeysLazy,
        TriFunction<By, String, SingleGetter, DriverFunction<Boolean>> sendKeysByWithGetter,
        BiFunction<By, String, DriverFunction<Boolean>> sendKeysBy
    ) {
        this.sendKeysLazy = sendKeysLazy;
        this.sendKeysByWithGetter = sendKeysByWithGetter;
        this.sendKeysBy = sendKeysBy;
    }

    public SendKeysData() {
        this.sendKeysLazy = Element::sendKeys;
        this.sendKeysByWithGetter = Element::sendKeys;
        this.sendKeysBy = Element::sendKeys;
    }
}
