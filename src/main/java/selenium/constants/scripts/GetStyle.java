package selenium.constants.scripts;

public abstract class GetStyle {
    public static final String GET_STYLES_IN_JSON =  "var getStyleProperties = function getStylePropertiesFunction(node) {" +
        "    var style = window.getComputedStyle(node)," +
        "        length = style.length," +
        "        styleMap = {}," +
        "        index = 0," +
        "        currentProp," +
        "        value;" +
        "" +
        "    for (; index < length; ++index) {" +
        "        currentProp = style[index];" +
        "        value = style.getPropertyValue(currentProp);" +
        "        styleMap[currentProp] = value;" +
        "    }" +
        "" +
        "    return styleMap;" +
        "}" +
        "" +
        "var getStyleJson = function getStyleJsonFunction(node) {" +
        "    return JSON.stringify(getStyleProperties(node), null, 4);" +
        "}" +
        "" +
        "return getStyleJson(arguments[0]);";
}
