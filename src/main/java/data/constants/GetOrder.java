package data.constants;

import data.extensions.DecoratedList;

import java.util.Arrays;

public class GetOrder {
    public static final DecoratedList<String> ID_CSSSELECTOR = new DecoratedList<>(Arrays.asList("id", "cssSelector", "class", "xpath"), String.class);
    public static final DecoratedList<String> CSSSELECTOR_ID = new DecoratedList<>(Arrays.asList("cssSelector", "id", "class", "xpath"), String.class);
    public static final DecoratedList<String> DEFAULT = ID_CSSSELECTOR;
}
