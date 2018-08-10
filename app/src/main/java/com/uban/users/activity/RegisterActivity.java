package com.uban.users.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uban.users.R;
import com.uban.users.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity {
    ListDataSaveTool dataSave;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button register;
    private List<UserBean> listBean;
    private TextView back;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        accountEdit = findViewById(R.id.register_account);
        passwordEdit = findViewById(R.id.register_password);
        register = findViewById(R.id.register);
        back = findViewById(R.id.tvBack);
        dataSave = new ListDataSaveTool(this);
        listBean = dataSave.getDataList();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                if (listBean.size() == 0) {
//                    UserBean user = new UserBean();
//                    user.setAccount(account);
//                    user.setPassword(password);
//                    listBean.add(user);
//                    dataSave.setDataList(listBean);
//                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    save();
                } else {
                    boolean hasUser = false;
                    for (int i = 0; i < listBean.size(); i++) {
                        UserBean bean = listBean.get(i);
                        if (bean.account.equals(account)) {
                            hasUser = true;
                            break;
                        }
                    }
                    if (hasUser) {
                        Toast.makeText(RegisterActivity.this, "该账号已经存在", Toast.LENGTH_SHORT).show();
                    } else {
//                        UserBean user = new UserBean();
//                        user.setAccount(account);
//                        user.setPassword(password);
//                        listBean.add(user);
//                        dataSave.setDataList(listBean);
//                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        save();
                    }

                }

            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    public void save() {
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        UserBean user = new UserBean();
        user.setAccount(account);
        user.setPassword(password);
        listBean.add(user);
        dataSave.setDataList(listBean);
        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent();
        intent.putExtra("account", account);
        intent.putExtra("password", password);
        setResult(RESULT_OK, intent);
        finish();

    }
}
