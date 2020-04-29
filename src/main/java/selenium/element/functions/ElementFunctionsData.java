package selenium.element.functions;

import java.util.Objects;

public class ElementFunctionsData {
    public final ClickData clickData;
    public final ClearData clearData;
    public final SendKeysData sendKeysData;

    public ElementFunctionsData(ClickData clickData, ClearData clearData, SendKeysData sendKeysData) {
        this.clickData = clickData;
        this.clearData = clearData;
        this.sendKeysData = sendKeysData;
    }

    public ElementFunctionsData() {
        this.clickData = new ClickData();
        this.clearData = new ClearData();
        this.sendKeysData = new SendKeysData();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ElementFunctionsData) o;
        return Objects.equals(clickData, that.clickData) && Objects.equals(clearData, that.clearData) && Objects.equals(sendKeysData, that.sendKeysData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clickData, clearData, sendKeysData);
    }
}
