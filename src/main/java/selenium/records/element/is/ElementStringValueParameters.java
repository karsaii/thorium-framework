package selenium.records.element.is;

import core.extensions.interfaces.functional.TriFunction;
import core.records.Data;
import selenium.abstracts.ElementValueParameters;
import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.records.lazy.LazyElement;

import java.util.function.Function;

public class ElementStringValueParameters<ReturnType> extends ElementValueParameters<String, ReturnType> {
    public ElementStringValueParameters(
        TriFunction<DriverFunction<String>, Function<Data<String>, Data<ReturnType>>, Data<ReturnType>, DriverFunction<ReturnType>> handler,
        ElementFormatData<ReturnType> formatData,
        Function<LazyElement, DriverFunction<ReturnType>> function
    ) {
        super(handler, formatData, function);
    }
}
