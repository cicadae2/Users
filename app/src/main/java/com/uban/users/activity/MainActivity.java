package com.uban.users.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.uban.users.R;
import com.uban.users.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    ListDataSaveTool dataSave;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button register;
    private Button login;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private List<UserBean> listBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        rememberPass = findViewById(R.id.remember_pass);
        register = findViewById(R.id.BRegister);
        login = findViewById(R.id.login);
        dataSave = new ListDataSaveTool(this);//?
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = dataSave.getDataList();
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                boolean hasUser = false;
                for (int i = 0; i < listBean.size(); i++) {
                    UserBean bean = listBean.get(i);
                    if (bean.account.equals(account) && bean.password.equals(password)) {
                        hasUser = true;
//                        editor=pref.edit();
//                        if (rememberPass.isChecked()) {
//                            editor.putBoolean("remember_password", true);
//                            editor.putString("account", account);
//                            editor.putString("password", password);
//
//                        } else {
//                            editor.clear();
//                        }
//                        editor.apply();
                        break;
                    }

                }
                if (hasUser) {
                    Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                    startActivityForResult(intent, 2);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "账号不存在或者密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;//intent 跳转的时候带数据
        switch (requestCode) {
            case 1:
                String account = data.getStringExtra("account");
                String password = data.getStringExtra("password");
                accountEdit.setText(account);
                passwordEdit.setText(password);
                break;
            case 2:

                break;
        }


    }
}

