package selenium.enums;

import java.util.HashMap;
import java.util.Map;

public enum SingleGetter {
    GET_ELEMENT("getElement"),
    GET_ROOT_ELEMENT("getRootElement"),
    GET_NESTED_ELEMENT("getNestedElement"),
    GET_FRAME_NESTED_ELEMENT("getFrameNestedElement"),
    GET_SHADOW_ROOT_ELEMENT("getShadowRootElement"),
    GET_SHADOW_ROOT_NESTED_ELEMENT("getShadowNestedElement"),
    DEFAULT("getElement");

    private static final Map<String, SingleGetter> VALUES = new HashMap<>();
    private String name;

    SingleGetter(String name) {
        this.name = name;
    }

    static {
        for(SingleGetter getter : values()) {
            VALUES.putIfAbsent(getter.name, getter);
        }
    }
    public String getName() {
        return name;
    }

    public static SingleGetter getValueOf(String name) {
        return VALUES.getOrDefault(name, SingleGetter.GET_ELEMENT);
    }
}
