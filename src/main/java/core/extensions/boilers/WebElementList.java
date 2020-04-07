package core.extensions.boilers;

import core.extensions.DecoratedList;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class WebElementList extends DecoratedList<WebElement> {
    public WebElementList() {
        super(new ArrayList<>(), WebElement.class);
    }

    public WebElementList(List<WebElement> list) {
        super(list, WebElement.class);
    }

    public WebElementList(WebElement element) {
        super(element);
    }

    public WebElementList subList(int fromIndex, int toIndex) {
        return subList(WebElementList.class, fromIndex, toIndex);
    }

    public WebElementList tail() {
        return tail(WebElementList.class);
    }

    public WebElementList initials() {
        return initials(WebElementList.class);
    }
}
