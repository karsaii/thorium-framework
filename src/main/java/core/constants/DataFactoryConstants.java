package core.constants;

import core.records.MethodMessageData;
import data.constants.Strings;

public abstract class DataFactoryConstants {
    public static final boolean DEFAULT_STATE = false;
    public static final String DEFAULT_EXCEPTION_MESSAGE = Strings.NON_EXCEPTION_MESSAGE;
    public static final Exception DEFAULT_EXCEPTION = CoreConstants.EXCEPTION;
    public static final MethodMessageData DEFAULT_METHOD_MESSAGE_DATA = new MethodMessageData("getWith", "Default method message" + Strings.END_LINE);
}
