package selenium.namespaces.validators;

public interface Invalids {
    static <T> boolean defaultFalseValidator(T parameters) {
        return false;
    }

}