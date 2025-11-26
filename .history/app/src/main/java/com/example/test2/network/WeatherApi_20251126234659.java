package com.example.test2.network;

import android.os.Handler;
import android.os.Looper;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.test2.data.DayWeatherResponse;
import com.example.test2.data.WeekWeatherResponse;
import com.google.gson.Gson;
import java.io.IOException;

public class WeatherApi {

    private static final String TAG = "WeatherApi";

    // 共用的 OkHttpClient，设置合理的超时时间，避免请求长时间挂起
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build();
    private static final Gson gson = new Gson();

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    // 默认城市（北京）
    private static final String DEFAULT_CITY_ID = "101010100";

    private static final String APP_ID = "66444319";
    private static final String APP_SECRET = "zSFdQpE9";

    // 基础 URL（使用 HTTPS，避免 Android 9+ 对明文 HTTP 的限制）
    private static final String BASE_URL = "https://v1.yiketianqi.com/free/";

    /**
     * 通用请求方法，减少重复代码。
     */
    private static <T> void request(
            String path,
            String cityId,
            Class<T> clazz,
            ApiCallback<T> callback
    ) {
        if (cityId == null || cityId.isEmpty()) {
            cityId = DEFAULT_CITY_ID;
        }

        String url = BASE_URL + path
                + "?appid=" + APP_ID
                + "&appsecret=" + APP_SECRET
                + "&unescape=1"
                + "&cityid=" + cityId;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> callback.onError(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mainHandler.post(() ->
                            callback.onError(new IOException("HTTP " + response.code()))
                    );
                    return;
                }

                if (response.body() == null) {
                    mainHandler.post(() ->
                            callback.onError(new Exception("Empty body"))
                    );
                    return;
                }

                String json = response.body().string();
                try {
                    T resp = gson.fromJson(json, clazz);
                    mainHandler.post(() -> callback.onSuccess(resp));
                } catch (Exception e) {
                    mainHandler.post(() -> callback.onError(e));
                }
            }
        });
    }

    /**
     * 获取 7 日天气（/free/week）
     */
    public static void getWeekWeather(String cityId, ApiCallback<WeekWeatherResponse> callback) {
        request("week", cityId, WeekWeatherResponse.class, callback);
    }

    /**
     * 获取当天实时天气（/free/day）
     */
    public static void getDayWeather(String cityId, ApiCallback<DayWeatherResponse> callback) {
        request("day", cityId, DayWeatherResponse.class, callback);
    }

    /**
     * 回调接口
     */
    public interface ApiCallback<T> {
        void onSuccess(T data);
        void onError(Exception e);
    }
}

