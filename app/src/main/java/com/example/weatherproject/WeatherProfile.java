package com.example.weatherproject;

public class WeatherProfile {

    private long ID ;
    private String profileName ;
    private String cityName ;
    private String APIkey ;
    private String unit;
    private boolean isDefault ;

    public WeatherProfile() {
    }

    public WeatherProfile(long ID, String profileName, String cityName, String APIkey, String unit) {
        this.ID = ID;
        this.profileName = profileName;
        this.cityName = cityName;
        this.APIkey = APIkey;
        this.unit = unit;
        this.isDefault = false;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAPIkey() {
        return APIkey;
    }

    public void setAPIkey(String APIkey) {
        this.APIkey = APIkey;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        this.isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "WeatherProfile{" +
                "profileName='" + profileName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", APIkey='" + APIkey + '\'' +
                ", unit='" + unit + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
