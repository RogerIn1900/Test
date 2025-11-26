package com.example.test2.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test2.R;
import com.example.test2.data.DayWeatherResponse;
import com.example.test2.network.GetWeather;
import com.example.test2.network.WeatherApi;

/**
 * Shows the current-day weather overview UI.
 */
public class WeatherTodayFragment extends Fragment {

    private static final String TAG = "WeatherTodayFragment";

    // 常用城市对应的 cityId（可根据需要再扩展/调整）
    private static final String CITY_ID_BEIJING = "101010100";
    private static final String CITY_ID_SHANGHAI = "101020100";
    private static final String CITY_ID_GUANGZHOU = "101280101";
    private static final String CITY_ID_SHENZHEN = "101280601";

    private final GetWeather getWeather = new GetWeather();

    private View contentContainer;

    private TextView tvCityName;
    private TextView tvWea;
    private TextView tvTem;
    private TextView tvTemDay;
    private TextView tvTemNight;
    private TextView tvDayWea;
    private TextView tvDayDetailWea;
    private TextView tvDayDetailTemp;
    private TextView tvDayDetailWind;
    private TextView tvNightDetailWea;
    private TextView tvNightDetailTemp;
    private TextView tvNightDetailWind;

    public WeatherTodayFragment() {
        // Required empty public constructor
    }

    public static WeatherTodayFragment newInstance() {
        return new WeatherTodayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupCityChips(view);

        // 默认选中广州并请求一次天气
        selectCityChip(view, R.id.chipGuangzhou);
        loadWeatherForCity("广州", CITY_ID_GUANGZHOU);
    }

    private void initViews(@NonNull View root) {
        contentContainer = root.findViewById(R.id.today_content_container);
        tvCityName = root.findViewById(R.id.city_name);
        tvWea = root.findViewById(R.id.wea);
        tvTem = root.findViewById(R.id.tem);
        tvTemDay = root.findViewById(R.id.tem_day);
        tvTemNight = root.findViewById(R.id.tem_night);
        tvDayWea = root.findViewById(R.id.day_wea);
        tvDayDetailWea = root.findViewById(R.id.day_detail_wea);
        tvDayDetailTemp = root.findViewById(R.id.day_detail_temp);
        tvDayDetailWind = root.findViewById(R.id.day_detail_wind);
        tvNightDetailWea = root.findViewById(R.id.night_detail_wea);
        tvNightDetailTemp = root.findViewById(R.id.night_detail_temp);
        tvNightDetailWind = root.findViewById(R.id.night_detail_wind);
    }

    private void setupCityChips(@NonNull View root) {
        View chipBeijing = root.findViewById(R.id.chipBeijing);
        View chipShanghai = root.findViewById(R.id.chipShanghai);
        View chipGuangzhou = root.findViewById(R.id.chipGuangzhou);
        View chipShenzhen = root.findViewById(R.id.chipShenzhen);

        if (chipBeijing != null) {
            chipBeijing.setOnClickListener(v -> {
                selectCityChip(root, R.id.chipBeijing);
                loadWeatherForCity("北京", CITY_ID_BEIJING);
            });
        }

        if (chipShanghai != null) {
            chipShanghai.setOnClickListener(v -> {
                selectCityChip(root, R.id.chipShanghai);
                loadWeatherForCity("上海", CITY_ID_SHANGHAI);
            });
        }

        if (chipGuangzhou != null) {
            chipGuangzhou.setOnClickListener(v -> {
                selectCityChip(root, R.id.chipGuangzhou);
                loadWeatherForCity("广州", CITY_ID_GUANGZHOU);
            });
        }

        if (chipShenzhen != null) {
            chipShenzhen.setOnClickListener(v -> {
                selectCityChip(root, R.id.chipShenzhen);
                loadWeatherForCity("深圳", CITY_ID_SHENZHEN);
            });
        }
    }

    /**
     * 设置城市 tab 的选中状态，利用 selector 显示不同背景/文字颜色。
     */
    private void selectCityChip(@NonNull View root, int selectedId) {
        int[] ids = {
                R.id.chipBeijing,
                R.id.chipShanghai,
                R.id.chipGuangzhou,
                R.id.chipShenzhen
        };

        for (int id : ids) {
            View v = root.findViewById(id);
            if (v != null) {
                v.setSelected(id == selectedId);
            }
        }
    }

    /**
     * 根据城市 id 从接口获取当天的天气，并更新整个页面的展示。
     */
    private void loadWeatherForCity(String cityName, String cityId) {
        // 先微微降低整体透明度，等数据回来再统一淡入，视觉上更统一
        if (contentContainer != null) {
            contentContainer.setAlpha(0.3f);
        }

        getWeather.getDayWeather(cityId, new WeatherApi.ApiCallback<DayWeatherResponse>() {
            @Override
            public void onSuccess(DayWeatherResponse data) {
                if (!isAdded()) {
                    return;
                }
                bindDayWeather(data, cityName);

                if (contentContainer != null) {
                    contentContainer.animate()
                            .alpha(1f)
                            .setDuration(200L)
                            .start();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "loadWeatherForCity error", e);
            }
        });
    }

    /**
     * 把接口返回的数据绑定到当前 Fragment 的各个 TextView 上。
     */
    private void bindDayWeather(@NonNull DayWeatherResponse data, @NonNull String cityName) {
        if (tvCityName != null) {
            tvCityName.setText(cityName);
        }
        if (tvWea != null) {
            tvWea.setText(data.wea);
        }
        if (tvTem != null) {
            tvTem.setText(data.tem + "°");
        }
        if (tvTemDay != null) {
            tvTemDay.setText("最高：" + data.tem_day + "°");
        }
        if (tvTemNight != null) {
            tvTemNight.setText("最低：" + data.tem_night + "°");
        }
        if (tvDayWea != null) {
            tvDayWea.setText(data.wea);
        }

        // 白天卡片详细信息
        if (tvDayDetailWea != null) {
            tvDayDetailWea.setText(data.wea);
        }
        if (tvDayDetailTemp != null) {
            tvDayDetailTemp.setText(data.tem + "°");
        }
        if (tvDayDetailWind != null) {
            tvDayDetailWind.setText(data.win + " " + data.win_speed);
        }

        // 夜晚卡片目前使用同一份实时数据（如果后端有单独夜间数据可以在此替换）
        if (tvNightDetailWea != null) {
            tvNightDetailWea.setText(data.wea);
        }
        if (tvNightDetailTemp != null) {
            tvNightDetailTemp.setText(data.tem + "°");
        }
        if (tvNightDetailWind != null) {
            tvNightDetailWind.setText(data.win + " " + data.win_speed);
        }
    }
}

