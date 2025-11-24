package com.example.test2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    private EditText etInputEmail,etInputPassword;
    LinearLayout btWechat,btApple;
    MaterialButton LoginBotton;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);

        init();
        saveDefaultUserInfo();

        etInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() < 6) {
                    etInputPassword.setError("密码⻓度不能少于6位");
                } else {
                    etInputPassword.setError(null); // 清除错误提⽰
                }
                Toast.makeText(LoginActivity.this, "密码长度正确", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        LoginBotton.setOnClickListener(v -> {
            String inputEmail = etInputEmail.getText().toString();
            String inputPassword = etInputPassword.getText().toString();

            String email = sp.getString("email", "");
            String password = sp.getString("password", "");

            if (inputEmail.equals(email) && inputPassword.equals(password)) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userEmail", email);
                intent.putExtras(bundle);
                startActivity(intent);

            } else {
                Toast.makeText(LoginActivity.this, "登录失败,请检查邮箱 密码是否正确", Toast.LENGTH_SHORT).show();
            }

        });


        btWechat.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "微信登录", Toast.LENGTH_SHORT).show();
        });

        btApple.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "苹果登录", Toast.LENGTH_SHORT).show();
        });
    }

    private void init() {
        sp = getApplicationContext().getSharedPreferences("UserInfoSP", Context.MODE_PRIVATE);

        etInputEmail = findViewById(R.id.etInputEmail);
        etInputPassword = findViewById(R.id.etInputPassword);
        btWechat = findViewById(R.id.btWechat);
        btApple = findViewById(R.id.btApple);
        LoginBotton = findViewById(R.id.btLogin);
    }

    private void saveDefaultUserInfo() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", "1750193717@qq.com");
        editor.putString("password", "123456");
        editor.apply();
        boolean isSaveSuccess = editor.commit();
        System.out.println("存储是否成功：" + isSaveSuccess); // true则存储成功
    }
}