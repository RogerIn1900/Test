package com.example.test2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Displays the upcoming forecast list UI.
 */
public class WeatherForecastFragment extends Fragment {

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance() {
        return new WeatherForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupNavigation(view);
        bindForecastRows(view);
    }

    private void setupNavigation(View root) {
        View cityTab = root.findViewById(R.id.navCityTab);
        View forecastTab = root.findViewById(R.id.navForecastTab);

        cityTab.setSelected(false);
        forecastTab.setSelected(true);

        cityTab.setOnClickListener(v -> {
            if (getActivity() instanceof WeatherNavHost) {
                ((WeatherNavHost) getActivity()).showTodayScreen();
            }
        });
    }

    private void bindForecastRows(View root) {
        ForecastEntry[] entries = new ForecastEntry[]{
                new ForecastEntry("今天", "01-06", "晴", "25°", "18°", android.R.drawable.ic_menu_day),
                new ForecastEntry("明天", "01-07", "晴", "26°", "19°", android.R.drawable.ic_menu_day),
                new ForecastEntry("星期一", "01-08", "多云", "24°", "18°", android.R.drawable.ic_menu_week),
                new ForecastEntry("星期二", "01-09", "多云", "23°", "17°", android.R.drawable.ic_menu_week),
                new ForecastEntry("星期三", "01-10", "小雨", "21°", "16°", android.R.drawable.ic_menu_compass),
                new ForecastEntry("星期四", "01-11", "阴", "22°", "17°", android.R.drawable.ic_menu_compass),
                new ForecastEntry("星期五", "01-12", "多云", "24°", "18°", android.R.drawable.ic_menu_week)
        };

        int[] rowIds = new int[]{
                R.id.rowToday,
                R.id.rowTomorrow,
                R.id.rowMonday,
                R.id.rowTuesday,
                R.id.rowWednesday,
                R.id.rowThursday
        };

        int entryIndex = 0;
        for (int rowId : rowIds) {
            View row = root.findViewById(rowId);
            if (row == null || entryIndex >= entries.length) {
                entryIndex++;
                continue;
            }
            ForecastEntry entry = entries[entryIndex];
            setText(row, R.id.forecastDayLabel, entry.dayLabel);
            setText(row, R.id.forecastDateLabel, entry.dateLabel);
            setText(row, R.id.forecastConditionLabel, entry.condition);
            setText(row, R.id.forecastHighTemp, entry.highTemp);
            setText(row, R.id.forecastLowTemp, entry.lowTemp);
            View iconView = row.findViewById(R.id.forecastIcon);
            if (iconView instanceof android.widget.ImageView) {
                ((android.widget.ImageView) iconView).setImageResource(entry.iconRes);
            }
            entryIndex++;
        }
    }

    private void setText(View parent, int viewId, String value) {
        View view = parent.findViewById(viewId);
        if (view instanceof android.widget.TextView) {
            ((android.widget.TextView) view).setText(value);
        }
    }

    private static final class ForecastEntry {
        final String dayLabel;
        final String dateLabel;
        final String condition;
        final String highTemp;
        final String lowTemp;
        final int iconRes;

        ForecastEntry(String dayLabel, String dateLabel, String condition, String highTemp, String lowTemp, int iconRes) {
            this.dayLabel = dayLabel;
            this.dateLabel = dateLabel;
            this.condition = condition;
            this.highTemp = highTemp;
            this.lowTemp = lowTemp;
            this.iconRes = iconRes;
        }
    }
}

