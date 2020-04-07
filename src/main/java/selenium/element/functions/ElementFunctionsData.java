package selenium.element.functions;

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
}
