package com.example.test2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements WeatherNavHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showTodayScreen();
        }
    }

    @Override
    public void showTodayScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.weatherFragmentContainer, WeatherTodayFragment.newInstance())
                .commit();
    }

    @Override
    public void showForecastScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.weatherFragmentContainer, WeatherForecastFragment.newInstance())
                .commit();
    }
}