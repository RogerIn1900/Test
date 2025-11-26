package com.example.test2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements WeatherNavHost {

    private View navCityTab;
    private View navForecastTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNav();

        if (savedInstanceState == null) {
            showTodayScreen();
        } else {
            syncNavSelectionWithCurrentFragment();
        }
    }

    private void initBottomNav() {
        navCityTab = findViewById(R.id.navCityTab);
        navForecastTab = findViewById(R.id.navForecastTab);

        if (navCityTab != null) {
            navCityTab.setOnClickListener(v -> {
                if (!v.isSelected()) {
                    showTodayScreen();
                }
            });
        }

        if (navForecastTab != null) {
            navForecastTab.setOnClickListener(v -> {
                if (!v.isSelected()) {
                    showForecastScreen();
                }
            });
        }
    }

    private void syncNavSelectionWithCurrentFragment() {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.weatherFragmentContainer);
        boolean isToday = !(current instanceof WeatherForecastFragment);
        updateNavSelection(isToday);
    }

    @Override
    public void showTodayScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.weatherFragmentContainer, WeatherTodayFragment.newInstance())
                .commit();
        updateNavSelection(true);
    }

    @Override
    public void showForecastScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.weatherFragmentContainer, WeatherForecastFragment.newInstance())
                .commit();
        updateNavSelection(false);
    }

    private void updateNavSelection(boolean citySelected) {
        if (navCityTab != null) {
            navCityTab.setSelected(citySelected);
        }
        if (navForecastTab != null) {
            navForecastTab.setSelected(!citySelected);
        }
    }
}