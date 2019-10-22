package validators;

import data.tuples.InputData;
import formatter.Formatter;

public interface Tuples {
    static <T> String validateInputData(InputData<T> data) {
        final var name = "InputData";
        return (
            Formatter.isNullMessage(data, name) +
            Formatter.isNullMessage(data.object, name + " Object") +
            Formatter.isNullMessage(data.defaultValue, name + " DefaultValue") +
            Formatter.isNullMessage(data.clazz, name + " Class")
        );
    }
}
