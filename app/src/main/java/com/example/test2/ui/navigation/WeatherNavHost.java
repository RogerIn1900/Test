package com.example.test2.ui.navigation;

/**
 * Simple navigation contract used by fragments to ask the activity to switch screens
 * and share common weather context (e.g. current city).
 */
public interface WeatherNavHost {
    void showTodayScreen();
    void showForecastScreen();

    /**
     * 通知宿主当前选中的城市（名称和接口使用的 cityId）。
     */
    void onCitySelected(String cityName, String cityId);
}

