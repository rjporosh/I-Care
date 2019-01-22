package com.example.rjporosh.icare;

/**
 * Created by HP on 1/2/2016.
 */
public class HealthInformation {
    private String profileName;
    private String heart_Rate;
    private String sleep;
    private String bloodPressure;
    private String bmi;
    private String calorie;
    private String temperature;
    private String date;

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setHeart_Rate(String heart_Rate) {
        this.heart_Rate = heart_Rate;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getHeart_Rate() {
        return heart_Rate;
    }

    public String getSleep() {
        return sleep;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public String getBmi() {
        return bmi;
    }

    public String getCalorie() {
        return calorie;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDate() {
        return date;
    }
}

