package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        MaterialButton LoginBotton = findViewById(R.id.btLogin);
        LoginBotton.setOnClickListener(v -> {

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        });
    }
}