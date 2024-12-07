package sg.edu.nus.iss.vttp5_ssf_day17workshop.model;

public class Currency {
    private String currencyName;
    private String currencySymbol;
    private String currencyId;

    public Currency() {
    }

    public Currency(String currencyName, String currencySymbol, String currencyId) {
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    
    @Override
    public String toString() {
        return currencyName + " " + currencySymbol;
    }

    

    
}
