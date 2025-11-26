package com.example.test2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Shows the current-day weather overview UI.
 */
public class WeatherTodayFragment extends Fragment {

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
        setupNavigation(view);
        highlightDefaultCity(view);
    }

    private void setupNavigation(View root) {
        View cityTab = root.findViewById(R.id.navCityTab);
        View forecastTab = root.findViewById(R.id.navForecastTab);

        cityTab.setSelected(true);
        forecastTab.setSelected(false);

        forecastTab.setOnClickListener(v -> {
            if (getActivity() instanceof WeatherNavHost) {
                ((WeatherNavHost) getActivity()).showForecastScreen();
            }
        });
    }

    private void highlightDefaultCity(View root) {
        View gzChip = root.findViewById(R.id.chipGuangzhou);
        if (gzChip != null) {
            gzChip.setSelected(true);
        }
    }
}

