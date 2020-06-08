package com.github.karsaii.selenium.records.scripter;

import java.util.Objects;

public class ExecuteCoreFunctionData<HandlerType> {
    public final String nameof;
    public final HandlerType handler;

    public ExecuteCoreFunctionData(String nameof, HandlerType handler) {
        this.nameof = nameof;
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecuteCoreFunctionData) o;
        return Objects.equals(nameof, that.nameof) && Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameof, handler);
    }
}
