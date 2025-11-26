package com.example.test2.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test2.R;
import com.example.test2.ui.fragment.WeatherForecastFragment;
import com.example.test2.ui.navigation.WeatherNavHost;
import com.example.test2.ui.fragment.WeatherTodayFragment;

public class MainActivity extends AppCompatActivity implements WeatherNavHost {

    private View navCityTab;
    private View navForecastTab;

    // 当前选中的城市（名称与接口 cityId），默认为广州
    private String currentCityName = "广州";
    private String currentCityId = "101280101";

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
                .replace(R.id.weatherFragmentContainer,
                        WeatherTodayFragment.newInstance(currentCityId, currentCityName))
                .commit();
        updateNavSelection(true);
    }

    @Override
    public void showForecastScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.weatherFragmentContainer,
                        WeatherForecastFragment.newInstance(currentCityId, currentCityName))
                .commit();
        updateNavSelection(false);
    }

    @Override
    public void onCitySelected(String cityName, String cityId) {
        if (cityName != null && !cityName.isEmpty()) {
            this.currentCityName = cityName;
        }
        if (cityId != null && !cityId.isEmpty()) {
            this.currentCityId = cityId;
        }
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