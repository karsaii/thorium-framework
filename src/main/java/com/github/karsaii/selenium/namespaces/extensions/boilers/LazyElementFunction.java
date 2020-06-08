package com.github.karsaii.selenium.namespaces.extensions.boilers;

import com.github.karsaii.selenium.records.lazy.LazyElement;

import java.util.function.Function;

@FunctionalInterface
public interface LazyElementFunction<T> extends Function<LazyElement, DriverFunction<T>> {}
