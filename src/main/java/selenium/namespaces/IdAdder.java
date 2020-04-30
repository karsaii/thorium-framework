package selenium.namespaces;

import selenium.records.lazy.LazyElement;

public interface IdAdder {
    static void addTemporary(LazyElement element) {
        /*Before loop: var id = getLocatorValueOfElement(selenium.element, "id").object;
        Inside loop end: if (isBlank(id)) {
            id = getLocatorValueByStrategy(locatorList.last(), selenium.element.name, "id").object;
        }
        After loop: if (
            returnElement.status &&
            isNotEqual(currentKey, Strings.PRIMARY_STRATEGY) &&
            Execute.setId(returnElement, id).apply(driver).status
        ) {
            returnElement = appendMessage(returnElement, nameof, "Set selenium.element(\"" + selenium.element.name + "\") id to: " + id);
        }*/
    }
}
