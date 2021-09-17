package com.mi.broadcastbestpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText account;
    private EditText password;
    private Button login;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        //SharedPreferences存储实现记住密码
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = findViewById(R.id.remember_pass);
        boolean isRemember = preferences.getBoolean("isRemember", false);
        if (isRemember) {
            String acc = preferences.getString("account", "");
            String pass = preferences.getString("password", "");
            account.setText(acc);
            password.setText(pass);
            rememberPass.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountEdit = account.getText().toString();
                String passwordEdit = password.getText().toString();
                if ("admin".equals(accountEdit) && "123456".equals(passwordEdit)) {
                    editor = preferences.edit();
                    //如果选中记住密码，通过SharedPreferences存储账号密码到本地
                    if (rememberPass.isChecked()) {
                        editor.putString("account", accountEdit);
                        editor.putString("password", passwordEdit);
                        editor.putBoolean("isRemember", true);
                    } else {
                        editor.clear();
                    }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}