package data.functions;

import data.DriverFunction;
import data.enums.SingleGetter;
import data.LazyElement;
import element.Element;
import utilities.TriFunction;
import org.openqa.selenium.By;

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
