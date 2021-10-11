package com.example.gamebox.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gamebox.R;

public class RegisterActivity extends Activity
{
    private Button register, back;
    private EditText user, password, secondPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        register = findViewById(R.id.register_button);
        back = findViewById(R.id.registerBack);
        user = findViewById(R.id.registerUser);
        password = findViewById(R.id.registerPassword);
        secondPassword = findViewById(R.id.registerPasswordAgain);
        register.setOnClickListener(view ->
        {
            if (password.getText().toString().equals(secondPassword.getText().toString())
                && !user.getText().toString().equals("") && !password.getText().toString().equals("")
                && !secondPassword.getText().toString().equals(""))
            {
                Intent intent = new Intent();
                intent.putExtra("user", user.getText().toString());
                intent.putExtra("password", password.getText().toString());
                setResult(RESULT_OK, intent);
                RegisterActivity.this.finish();
            }
            else if (user.getText().toString().equals(""))
            {
                Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            }
            else if (password.getText().toString().equals(""))
            {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            }
            else if (secondPassword.getText().toString().equals(""))
            {
                Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "请检查密码是否正确", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(view ->
        {
            setResult(RESULT_CANCELED);
            RegisterActivity.this.finish();
        });
    }
}