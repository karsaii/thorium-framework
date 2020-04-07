package selenium.constants;

import core.extensions.DecoratedList;

import java.util.Arrays;

public abstract class GetOrderConstants {
    public static final DecoratedList<String> ID_CSSSELECTOR = new DecoratedList<>(Arrays.asList("id", "cssSelector", "class", "xpath"), String.class);
    public static final DecoratedList<String> CSSSELECTOR_ID = new DecoratedList<>(Arrays.asList("cssSelector", "id", "class", "xpath"), String.class);
    public static final DecoratedList<String> DEFAULT = ID_CSSSELECTOR;
}
