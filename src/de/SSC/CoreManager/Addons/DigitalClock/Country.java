package de.SSC.CoreManager.Addons.DigitalClock;

public class Country
{
    private String code;
    private String name;
    
    public Country(final String code, final String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getName() {
        return this.name;
    }
}
