package core.namespaces.wait;

import core.constants.SystemIdentityConstants;
import data.constants.Strings;

public interface WaitExceptionFunctions {
    static String getSystemInformationMessage() {
        return (
            Strings.NEW_LINE +
            "System info: host: \"" +
            SystemIdentityConstants.HOST_NAME +
            "\", ip: \"" +
            SystemIdentityConstants.HOST_ADDRESS +
            "\", os.name: \"" +
            System.getProperty("os.name") +
            ", os.arch: \"" +
            System.getProperty("os.arch") +
            "\", os.version: \"" +
            System.getProperty("os.version") +
            "\", java.version: \"" +
            System.getProperty("java.version") +
            "\"."
        );
    }
}
