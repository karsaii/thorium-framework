package data;

import org.openqa.selenium.WebElement;

public class ElementConditionData {
    public final Data<WebElement> data;
    public final Data<Boolean> condition;
    public final String descriptor;

    public ElementConditionData(Data<WebElement> data, Data<Boolean> condition, String descriptor) {
        this.data = data;
        this.condition = condition;
        this.descriptor = descriptor;
    }
}
