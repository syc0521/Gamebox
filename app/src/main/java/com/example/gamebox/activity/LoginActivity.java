package com.example.gamebox.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gamebox.R;
import com.example.gamebox.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity
{
    private Button loginButton, registerButton;
    private EditText userText, password;
    private final int LOGIN_RESULT = 0;
    private SharedPreferences userPreferences;
    private List<User> userList;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            insetsController.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        }else{
            // API30以下
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initialize();
    }

    private void initialize()
    {
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        userText = findViewById(R.id.login_user);
        password = findViewById(R.id.login_password);
        readUser();
        loginButton.setOnClickListener(view ->
        {
            String userStr = userText.getText().toString();
            String passwordStr = password.getText().toString();
            if (userStr.equals(""))
                Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            if (passwordStr.equals(""))
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            else
            {
                if (userList.size() != 0)
                {
                    boolean loginFlag = false;
                    for (User user : userList)
                    {
                        if (userStr.equals(user.getUserId()) && passwordStr.equals(user.getPassword()))
                        {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            loginFlag = true;
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            String userRaw = gson.toJson(user);
                            bundle.putString("user", userRaw);
                            intent.putExtra("bundle", bundle);
                            intent.putExtra("fragment", "UserFragment");
                            startActivity(intent);
                            break;
                        }
                        else if (userStr.equals(user.getUserId()) && !passwordStr.equals(user.getPassword()))
                        {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            loginFlag = true;
                            break;
                        }
                    }
                    if (!loginFlag)
                    {
                        Toast.makeText(LoginActivity.this, "用户信息不存在", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "用户信息不存在", Toast.LENGTH_SHORT).show();
                }
            }

        });
        registerButton.setOnClickListener(view ->
        {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, LOGIN_RESULT);
        });
    }

    private void readUser()
    {
        userPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String userRaw = userPreferences.getString("userRaw", "");
        if (!userRaw.equals(""))
        {
            userList = gson.fromJson(userRaw, new TypeToken<List<User>>(){}.getType());
        }
        else
        {
            userList = new ArrayList<>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_RESULT && resultCode == RESULT_OK)
        {
            User user = new User();
            user.setUserId(data.getStringExtra("user"));
            user.setPassword(data.getStringExtra("password"));
            userList.add(user);
            String userRaw = gson.toJson(userList);
            SharedPreferences.Editor editor = userPreferences.edit();
            editor.putString("userRaw", userRaw);
            editor.apply();
        }
    }
}