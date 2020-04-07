package core.records.caster;

import core.abstracts.AbstractCastData;
import core.records.Data;

import java.util.function.Function;

public class WrappedCastData<T> extends AbstractCastData<Data<T>, T> {
    public WrappedCastData(Data<T> object, Function<Object, T> caster) {
        super(object, caster);
    }
}
