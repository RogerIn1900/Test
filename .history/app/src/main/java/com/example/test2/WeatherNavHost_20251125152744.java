package com.example.test2;

/**
 * Simple navigation contract used by fragments to ask the activity to switch screens.
 */
public interface WeatherNavHost {
    void showTodayScreen();
    void showForecastScreen();
}

