package selenium.namespaces.extensions.boilers;

import selenium.records.lazy.LazyElement;

import java.util.function.Function;

@FunctionalInterface
public interface LazyElementFunction<T> extends Function<LazyElement, DriverFunction<T>> {}
