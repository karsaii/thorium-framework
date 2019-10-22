package data.tuples;

import data.constants.Strings;
import data.Data;

public class IndexedElementData<T> {
    public final Data<T> object;
    public final int index;

    public IndexedElementData(Data<T> object, int index) {
        this.object = object;
        this.index = index;
    }

    public IndexedElementData(Data<T> object) {
        this(object, 0);
    }

    public IndexedElementData(int index) {
        this(new Data<T>(null, false, "Null data" + Strings.END_LINE), index);
    }
}
