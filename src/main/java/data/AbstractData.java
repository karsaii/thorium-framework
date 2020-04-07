package data;

import data.constants.DataDefaults;
import data.constants.Strings;
import org.apache.commons.lang3.exception.ExceptionUtils;

public abstract class AbstractData<T> {
    public final T object;
    public final boolean status;
    public final String message;
    public final Exception exception;
    public final String exceptionMessage;

    public AbstractData(T object, boolean status, String message, Exception exception, String exceptionMessage) {
        this.object = object;
        this.status = status;
        this.message = message;
        this.exception = exception;
        this.exceptionMessage = exceptionMessage;
    }

    public AbstractData(T object, boolean status, String message, Exception exception) {
        this(object, status, message, exception, ExceptionUtils.getRootCauseMessage(exception));
    }

    public AbstractData(T object, boolean status, String message) {
        this(object, status, message, DataDefaults.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    public AbstractData(T status, String message) {
        this(status, (Boolean) status, message);
    }

    public AbstractData(Data<T> data, String message) {
        this(data.object, data.status, message, data.exception, data.exceptionMessage);
    }

    public AbstractData() {
        this.object = null;
        this.status = false;
        this.message = null;
        this.exception = null;
        this.exceptionMessage = null;
    }
}
