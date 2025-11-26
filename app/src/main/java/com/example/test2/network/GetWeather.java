package com.example.test2.network;

import com.example.test2.data.DayWeatherResponse;
import com.example.test2.data.WeekWeatherResponse;

import java.util.HashMap;
import java.util.Map;

public class GetWeather {

    // 简单内存缓存：key = cityId，value = 响应 + 时间戳
    private static final long CACHE_TTL_MILLIS = 60L * 60L * 1000L; // 1 小时

    private static final Map<String, Cached<DayWeatherResponse>> dayCache = new HashMap<>();
    private static final Map<String, Cached<WeekWeatherResponse>> weekCache = new HashMap<>();

    private static final class Cached<T> {
        final T data;
        final long timestamp;

        Cached(T data, long timestamp) {
            this.data = data;
            this.timestamp = timestamp;
        }
    }

    /**
     * 获取当天实时天气（使用默认城市或传入的 cityId），结果通过回调返回。
     *
     * 说明：
     * - 之前的实现先 return 一个空对象，再在异步回调里填充数据，这样调用方拿到的永远是“旧数据/空数据”。
     * - 现在直接把 WeatherApi 的回调暴露出去，让调用方在 onSuccess/onError 里更新 UI。
     */
    public void getDayWeather(String cityId,
                              WeatherApi.ApiCallback<DayWeatherResponse> callback) {
        final String key = cityId == null || cityId.isEmpty()
                ? WeatherApi.DEFAULT_CITY_ID
                : cityId;

        // 命中缓存且在 1 小时内，直接返回缓存数据
        Cached<DayWeatherResponse> cached = dayCache.get(key);
        long now = System.currentTimeMillis();
        if (cached != null && now - cached.timestamp <= CACHE_TTL_MILLIS) {
            callback.onSuccess(cached.data);
            return;
        }

        WeatherApi.getDayWeather(key, new WeatherApi.ApiCallback<DayWeatherResponse>() {
            @Override
            public void onSuccess(DayWeatherResponse data) {
                dayCache.put(key, new Cached<>(data, System.currentTimeMillis()));
                callback.onSuccess(data);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
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
        final String key = cityId == null || cityId.isEmpty()
                ? WeatherApi.DEFAULT_CITY_ID
                : cityId;

        Cached<WeekWeatherResponse> cached = weekCache.get(key);
        long now = System.currentTimeMillis();
        if (cached != null && now - cached.timestamp <= CACHE_TTL_MILLIS) {
            callback.onSuccess(cached.data);
            return;
        }

        WeatherApi.getWeekWeather(key, new WeatherApi.ApiCallback<WeekWeatherResponse>() {
            @Override
            public void onSuccess(WeekWeatherResponse data) {
                weekCache.put(key, new Cached<>(data, System.currentTimeMillis()));
                callback.onSuccess(data);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    /**
     * 使用默认城市获取 7 日天气预报的便捷方法。
     */
    public void getWeekWeather(WeatherApi.ApiCallback<WeekWeatherResponse> callback) {
        getWeekWeather(null, callback);
    }
}
