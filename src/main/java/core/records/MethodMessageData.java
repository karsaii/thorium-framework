package core.records;

import data.constants.Strings;

import java.util.Objects;

public class MethodMessageData {
    public final String nameof;
    public final String message;

    public MethodMessageData(String nameof, String message) {
        this.nameof = nameof;
        this.message = message;
    }

    public MethodMessageData(String message) {
        this(Strings.EMPTY, message);
    }

    public MethodMessageData() {
        this(Strings.EMPTY, Strings.EMPTY);
    }

    public String getMessage() {
        return "" + nameof + ": " + message;
    }

    public String getMessage(String nameof) {
        return nameof + message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (MethodMessageData) o;
        return Objects.equals(nameof, that.nameof) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameof, message);
    }

    @Override
    public String toString() {
        return "" + nameof + ": " + message;
    }
}
