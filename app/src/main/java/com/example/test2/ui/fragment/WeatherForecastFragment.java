package com.example.test2.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.R;
import com.example.test2.data.WeatherDataOfDay;
import com.example.test2.data.WeatherItem;
import com.example.test2.data.WeekWeatherResponse;
import com.example.test2.network.GetWeather;
import com.example.test2.network.WeatherApi;
import com.example.test2.ui.Adapter.WeatherOfDayAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Displays the upcoming forecast list UI.
 */
public class WeatherForecastFragment extends Fragment {
    private RecyclerView recyclerView;
    private WeatherOfDayAdapter weatherOfDayAdapter;
    private List<WeatherDataOfDay> weatherDataOfDayList;
    private View tvForecastCityName;

    private static final String ARG_CITY_ID = "arg_city_id";
    private static final String ARG_CITY_NAME = "arg_city_name";

    private final GetWeather getWeather = new GetWeather();
    private String cityId;
    private String cityName;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(String cityId, String cityName) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_ID, cityId);
        args.putString(ARG_CITY_NAME, cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            cityId = getArguments().getString(ARG_CITY_ID);
            cityName = getArguments().getString(ARG_CITY_NAME);
        }
        if (cityId == null || cityId.isEmpty()) {
            cityId = "101010100"; // 默认北京
        }

        setupRecycler(view);
        loadWeekWeather();
    }

    private void setupRecycler(@NonNull View view) {
        recyclerView = view.findViewById(R.id.weather_of_day_item);
        tvForecastCityName = view.findViewById(R.id.forecast_city_name);

        // 更新标题中的城市名称
        if (tvForecastCityName instanceof android.widget.TextView && cityName != null) {
            ((android.widget.TextView) tvForecastCityName).setText(cityName);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        weatherDataOfDayList = new ArrayList<>();
        weatherOfDayAdapter = new WeatherOfDayAdapter(weatherDataOfDayList);
        recyclerView.setAdapter(weatherOfDayAdapter);
    }

    private void loadWeekWeather() {
        getWeather.getWeekWeather(cityId, new WeatherApi.ApiCallback<WeekWeatherResponse>() {
            @Override
            public void onSuccess(WeekWeatherResponse data) {
                if (!isAdded()) {
                    return;
                }
                bindForecastRows(data);
            }

            @Override
            public void onError(Exception e) {
                // 简单忽略错误或在此添加 Toast 提示
            }
        });
    }

    private void bindForecastRows(@NonNull WeekWeatherResponse response) {
        weatherDataOfDayList.clear();
        if (response.data != null) {
            int index = 0;
            for (WeatherItem item : response.data) {
                String dayLabel;
                if (index == 0) {
                    dayLabel = "今天";
                } else if (index == 1) {
                    dayLabel = "明天";
                } else {
                    dayLabel = getWeekdayLabel(item.date);
                }

                String dateLabel = formatMonthDay(item.date);
                String condition = item.wea;
                String high = item.tem_day + "°";
                String low = item.tem_night + "°";
                weatherDataOfDayList.add(
                        new WeatherDataOfDay(dayLabel, dateLabel, condition, high, low, null)
                );
                index++;
            }
        }
        weatherOfDayAdapter.notifyDataSetChanged();
    }

    /**
     * 把 yyyy-MM-dd 转成 MM-dd，如果解析失败就原样返回。
     */
    private String formatMonthDay(String dateStr) {
        if (dateStr == null) return "";
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat out = new SimpleDateFormat("MM-dd", Locale.getDefault());
        try {
            Date d = in.parse(dateStr);
            if (d != null) {
                return out.format(d);
            }
        } catch (ParseException ignored) {
        }
        return dateStr;
    }

    /**
     * 根据日期计算星期几（星期一、星期二...），解析失败则返回原始日期。
     */
    private String getWeekdayLabel(String dateStr) {
        if (dateStr == null) return "";
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date d = in.parse(dateStr);
            if (d == null) return dateStr;
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int dow = cal.get(Calendar.DAY_OF_WEEK); // 1=周日, 2=周一...
            switch (dow) {
                case Calendar.MONDAY:
                    return "星期一";
                case Calendar.TUESDAY:
                    return "星期二";
                case Calendar.WEDNESDAY:
                    return "星期三";
                case Calendar.THURSDAY:
                    return "星期四";
                case Calendar.FRIDAY:
                    return "星期五";
                case Calendar.SATURDAY:
                    return "星期六";
                case Calendar.SUNDAY:
                default:
                    return "星期日";
            }
        } catch (ParseException e) {
            return dateStr;
        }
    }
}

