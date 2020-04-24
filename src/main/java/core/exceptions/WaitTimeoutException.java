package core.exceptions;

import static core.namespaces.WaitExceptionFunctions.getSystemInformationMessage;

public class WaitTimeoutException extends RuntimeException {
    public WaitTimeoutException(String message) {
        super(message + getSystemInformationMessage());
    }
}
