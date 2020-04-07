package selenium.records;

public class IndexedData {
    public final boolean isIndexed;
    public final int index;

    public IndexedData(boolean isIndexed, int index) {
        this.isIndexed = isIndexed;
        this.index = index;
    }

    public IndexedData(boolean isIndexed) {
        this(isIndexed, 0);
    }

    public IndexedData(int index) {
        this(true, index);
    }

    public IndexedData() {
        this(false, 0);
    }
}
