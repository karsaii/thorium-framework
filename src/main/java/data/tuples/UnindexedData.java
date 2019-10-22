package data.tuples;

import data.constants.Defaults;
import data.DriverFunction;
import data.LazyLocatorList;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Function;

public class UnindexedData {
    public final Map<String, Function<LazyLocatorList, DriverFunction<WebElement>>> getterMap;
    public final int index;

    public UnindexedData(Map<String, Function<LazyLocatorList, DriverFunction<WebElement>>> getterMap, int index) {
        this.getterMap = getterMap;
        this.index = index;
    }

    public UnindexedData(Map<String, Function<LazyLocatorList, DriverFunction<WebElement>>> getterMap) {
        this(getterMap, 0);
    }

    public UnindexedData(int index) {
        this(Defaults.singleGetterMap, index);
    }

    public UnindexedData() {
        this(Defaults.singleGetterMap, 0);
    }
}
