package core.exceptions;

import static core.namespaces.wait.WaitExceptionFunctions.getSystemInformationMessage;

public class WaitTimeoutException extends RuntimeException {
    public WaitTimeoutException(String message) {
        super(message + getSystemInformationMessage());
    }
}
