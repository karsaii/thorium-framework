package selenium.namespaces.validators;

import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

import static validators.TypeMethod.isTypeMethod;

public interface SeleniumTypeMethods {
    static boolean isWebElementMethod(Method method, String methodName) {
        return isTypeMethod(method, methodName, WebElement.class);
    }
}
