package data.lazy;

import data.LazyElement;

public interface IdAdder {
    static void addTemporary(LazyElement element) {
        /*Before loop: var id = getLocatorValueOfElement(element, "id").object;
        Inside loop end: if (isBlank(id)) {
            id = getLocatorValueByStrategy(locatorList.last(), element.name, "id").object;
        }
        After loop: if (
            returnElement.status &&
            isNotEqual(currentKey, Strings.PRIMARY_STRATEGY) &&
            Execute.setId(returnElement, id).apply(driver).status
        ) {
            returnElement = appendMessage(returnElement, nameof, "Set element(\"" + element.name + "\") id to: " + id);
        }*/
    }
}
