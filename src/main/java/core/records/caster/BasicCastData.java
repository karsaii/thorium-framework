package core.records.caster;

import core.abstracts.AbstractCastData;

import java.util.function.Function;

public class BasicCastData<T> extends AbstractCastData<T, T> {
    public BasicCastData(T object, Function<Object, T> caster) {
        super(object, caster);
    }
}
