package core.namespaces;

import core.constants.SystemIdentityConstants;

public interface WaitExceptionFunctions {
    static String getSystemInformationMessage() {
        return (
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
