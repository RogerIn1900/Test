package com.example.test2.data;

public class WeatherDataOfDay {
    private String dayLabel;
    private String dateLabel;
    private String condition;
    private String highTemp;
    private String lowTemp;
    private Runnable action;

    public WeatherDataOfDay(){};

    public WeatherDataOfDay(
            String dayLabel, String dateLabel, String condition,
            String highTemp, String lowTemp, Runnable action) {
        this.dayLabel = dayLabel;
        this.dateLabel = dateLabel;
        this.condition = condition;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.action = action;
    }

    public Runnable getAction() {
        return action;
    }
    public String getDayLabel() {
        return dayLabel;
    }
    public String getDateLabel() {
        return dateLabel;
    }
    public String getCondition() {
        return condition;
    }
    public String getHighTemp() {
        return highTemp;
    }
    public String getLowTemp() {
        return lowTemp;
    }
}
