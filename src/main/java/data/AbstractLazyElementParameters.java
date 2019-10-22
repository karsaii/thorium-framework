package data;

public abstract class AbstractLazyElementParameters {
    private static final String defaultGetter = "getElement";
    public final double probability;
    public final LazyLocatorList lazyLocators;
    public final String getter;

    public AbstractLazyElementParameters(double probability, LazyLocatorList lazyLocators, String getter) {
        this.probability = probability;
        this.lazyLocators = lazyLocators;
        this.getter = getter;
    }

    public AbstractLazyElementParameters(double probability, LazyLocatorList lazyLocators) {
        this(probability, lazyLocators, defaultGetter);
    }

    public AbstractLazyElementParameters(double probability, LazyLocator lazyLocator) {
        this(probability, new LazyLocatorList(lazyLocator), defaultGetter);
    }

    public AbstractLazyElementParameters(double probability, LazyLocator lazyLocator, String getter) {
        this(probability, new LazyLocatorList(lazyLocator), getter);
    }

    public AbstractLazyElementParameters(LazyLocatorList lazyLocators) {
        this(100.0, lazyLocators, defaultGetter);
    }

    public AbstractLazyElementParameters(LazyLocator lazyLocator) {
        this(100.0, new LazyLocatorList(lazyLocator), defaultGetter);
    }

    public AbstractLazyElementParameters(LazyLocator lazyLocator, String getter) {
        this(100.0, new LazyLocatorList(lazyLocator), getter);
    }
}
