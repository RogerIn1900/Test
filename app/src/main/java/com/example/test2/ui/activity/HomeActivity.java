package com.example.test2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test2.R;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    TextView tvUserName;
    MaterialCardView userInfo,userSetting,userLike,userHistory,aboutUs,feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ainit();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String email = bundle.getString("userEmail", "Guest");
            tvUserName.setText(email);
        }

        userInfo.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "userInfo", Toast.LENGTH_SHORT).show();
        });

        userSetting.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "userSetting", Toast.LENGTH_SHORT).show();
        });

        userLike.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "userLike", Toast.LENGTH_SHORT).show();
        });

        userHistory.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "userHistory", Toast.LENGTH_SHORT).show();
        });

        aboutUs.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "aboutUs", Toast.LENGTH_SHORT).show();
        });

        feedback.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "feedback", Toast.LENGTH_SHORT).show();
        });

    }

    private void ainit() {
        tvUserName = findViewById(R.id.tvUserName);
        userInfo = findViewById(R.id.userInfo);
        userSetting = findViewById(R.id.userSetting);
        userLike = findViewById(R.id.userLike);
        userHistory = findViewById(R.id.userHistory);
        aboutUs = findViewById(R.id.aboutUs);
        feedback = findViewById(R.id.feedback);
    }
}
