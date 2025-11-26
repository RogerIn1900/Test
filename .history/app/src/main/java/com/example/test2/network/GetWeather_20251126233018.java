package com.example.test2.network;

import com.example.test2.data.DayWeatherResponse;
import com.example.test2.data.WeatherItem;
import com.example.test2.data.WeekWeatherResponse;

public class GetWeather {

    /**
     * 获取当天实时天气（使用默认城市或传入的 cityId），结果通过回调返回。
     *
     * 说明：
     * - 之前的实现先 return 一个空对象，再在异步回调里填充数据，这样调用方拿到的永远是“旧数据/空数据”。
     * - 现在直接把 WeatherApi 的回调暴露出去，让调用方在 onSuccess/onError 里更新 UI。
     */
    public void getDayWeather(String cityId,
                              WeatherApi.ApiCallback<DayWeatherResponse> callback) {
        WeatherApi.getDayWeather(cityId, callback);
    }

    /**
     * 使用默认城市获取当天实时天气的便捷方法。
     */
    public void getDayWeather(WeatherApi.ApiCallback<DayWeatherResponse> callback) {
        getDayWeather(null, callback);
    }

    /**
     * 获取 7 日天气预报，直接把接口返回的列表透传给回调。
     */
    public void getWeekWeather(String cityId,
                               WeatherApi.ApiCallback<WeekWeatherResponse> callback) {
        WeatherApi.getWeekWeather(cityId, callback);
    }

    /**
     * 使用默认城市获取 7 日天气预报的便捷方法。
     */
    public void getWeekWeather(WeatherApi.ApiCallback<WeekWeatherResponse> callback) {
        getWeekWeather(null, callback);
    }
}
