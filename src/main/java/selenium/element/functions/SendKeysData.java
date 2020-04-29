package selenium.element.functions;

import selenium.namespaces.extensions.boilers.DriverFunction;
import core.extensions.interfaces.functional.TriFunction;
import org.openqa.selenium.By;
import selenium.element.Element;
import selenium.enums.SingleGetter;
import selenium.records.LazyElement;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (SendKeysData) o;
        return Objects.equals(sendKeysLazy, that.sendKeysLazy) &&
                Objects.equals(sendKeysByWithGetter, that.sendKeysByWithGetter) &&
                Objects.equals(sendKeysBy, that.sendKeysBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sendKeysLazy, sendKeysByWithGetter, sendKeysBy);
    }
}
