package data.constants;

import org.openqa.selenium.Keys;

import java.nio.file.Paths;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Strings {
    public static final String EMPTY = "";
    public static final String OPTION_NOT = "not";
    public static final String OPTION_EMPTY = "";

    public static final String CLICKABLE = "clickable";
    public static final String UNCLICKABLE = "unclickable";
    public static final String DISPLAYED = "displayed";
    public static final String HIDDEN = "hidden";
    public static final String ENABLED = "enabled";
    public static final String DISABLED = "disabled";
    public static final String PRESENT = "present";
    public static final String ABSENT = "absent";
    public static final String SELECTED = "selected";
    public static final String UNSELECTED = "unselected";


    public static final String FIND_ELEMENT = "findElement";
    public static final String DEFAULT_GETTER_ELEMENT = "getElement";
    public static final String DEFAULT_GETTER_ELEMENTS = "getElements";


    public static final String ELEMENT = "Element";
    public static final String ELEMENT_PARAMETERS = ELEMENT + " parameters";

    public static final String LAZY_ELEMENT = "Lazy " + ELEMENT;
    public static final String LOCATOR = "Locator ";
    public static final String CLASSES = "Classes";
    public static final String ELEMENTS = "Elements";
    public static final String IDS = "IDs";

    public static final String WINDOWS = "windows";
    public static final String PRIMARY_STRATEGY = "id";
    public static final String ELEMENT_TEXT = ELEMENT + "text";
    public static final String ELEMENT_ATTRIBUTE = ELEMENT + "attribute";
    public static final String ELEMENT_ATTRIBUTE_VALUE = ELEMENT_ATTRIBUTE + " value";

    public static final String TITLE_OF_WINDOW = "Title of window ";
    public static final String NEW_LINE = "\n";
    public static final String END_LINE = "." + NEW_LINE;
    public static final String WAS_NULL = " was null" + END_LINE;
    public static final String WERE_NULL = " were null" + END_LINE;
    public static final String WASNT_NULL = " wasn't null" + END_LINE;
    public static final String NULL_ELEMENT_ID = "NULL_ELEMENT_ID";

    public static final String COULDNT_EXECUTE = "Couldn't execute";
    public static final String SUCCESSFULLY_EXECUTE = "Successfully executed";

    public static final String COULDNT_SWITCH_TO = "Couldn't switch to ";
    public static final String SUCCESSFULLY_SWITCHED_TO = "Couldn't switch to ";

    public static final String NULL_DATA = "nullData.";
    public static final String EXECUTION_ENDED = "Execution ended" + Strings.END_LINE;
    public static final String EXECUTION_STATUS = "Execution status";
    public static final String PARAMETER_ISSUES = " There were parameter issue(s)";
    public static final String COLON = ":";
    public static final String COLON_NEWLINE = COLON + NEW_LINE;
    public static final String COLON_SPACE = COLON + " ";
    public static final String PARAMETER_ISSUES_LINE = PARAMETER_ISSUES + COLON_NEWLINE;
    public static final String EXECUTION_STATUS_COLON_SPACE = EXECUTION_STATUS + COLON_SPACE;

    public static final String PARAMETER_ISSUES_END = PARAMETER_ISSUES + Strings.END_LINE;

    public static final String DEFAULT_ERROR_MESSAGE_STRING = "Returning default empty string" + END_LINE;
    public static final String ELEMENT_CLICKABLE = " " + ELEMENT + "is clickable" + END_LINE;
    public static final String DATA_NULL_OR_FALSE = "Data was null or false" + END_LINE;
    public static final String PARAMETERS_WERE_WRONG = "Parameters were wrong" + END_LINE;
    public static final String ELEMENT_WAS_FOUND = "Element was found" + END_LINE;
    public static final String DRIVER_WAS_NULL = "Driver" + WAS_NULL;
    public static final String NON_EXCEPTION_MESSAGE = "No exception occurred" + END_LINE;

    public static final String EXCEPTION_WAS_NULL = "Exception" + WAS_NULL;
    public static final String LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL = LAZY_ELEMENT + " wait parameters" + WERE_NULL;
    public static final String LAZY_ELEMENT_WAS_NULL = LAZY_ELEMENT + WAS_NULL;
    public static final String GET_FORMATTED_ELEMENT_VALUE_ERROR = "Element value not found. Parameters were wrong" + END_LINE;
    public static final String RAW_WEBELEMENT_PASSED = "Raw WebElement passed, anything might happen" + END_LINE;
    public static final String WAITING_WAS_SUCCESSFUL = "Waiting was successful" + END_LINE;
    public static final String SCRIPT_RAN_SUCCESSFULLY = "Script ran successfully" + END_LINE;

    public static final String ELEMENT_LIST_EMPTY_OR_NULL = "No elements found, or element list " + WAS_NULL;

    public static final String LOCATOR_WAS_NULL = "Locator" + WAS_NULL;
    public static final String PASSED_DATA_WAS_NULL = "Passed data" + WAS_NULL;
    public static final String RESULT_WAS_NULL = "Result" + WAS_NULL;


    public static final String SELECT_ALL = Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE);

    public static final String METHOD_ALREADY_IN_MAP = "Method was already in map" + Strings.END_LINE;
    public static final String METHOD_PUT_IN_MAP = "Method put in map" + Strings.END_LINE;

    public static final String INVOCATION_SUCCESSFUL = "Invoke execution successful" + Strings.END_LINE;
    public static final String INVOCATION_EXCEPTION = "Exception occurred during invoke" + Strings.END_LINE;

    public static final String FIND_ELEMENTS_SUCCESSFUL = "Find elements execution successful" + Strings.END_LINE;
    public static final String FIND_ELEMENTS_EXCEPTION = "Exception occurred during finding elements" + Strings.END_LINE;

    public static final String VOID_CLASS_GENERIC = Void.class.toGenericString();

    public static final String DEFAULT_DRIVER_PATH = "src/test/resources/selenium_standalone_binaries/";
    public static final String PROJECT_WORKING_DIRECTORY = System.getProperty("projectdirectory");
    public static final String PROJECT_BASE_DIRECTORY = isBlank(PROJECT_WORKING_DIRECTORY) ? Paths.get(".").toAbsolutePath().normalize().toString() : PROJECT_WORKING_DIRECTORY;
}
