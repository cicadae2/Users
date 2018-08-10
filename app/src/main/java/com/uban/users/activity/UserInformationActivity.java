package com.uban.users.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.uban.users.R;
import com.uban.users.bean.UserBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserInformationActivity extends Activity {
    Context mContext;
    ListDataSaveTool dataSave;
    private List<UserBean> listBean;
    private TextView tvName;
    private TextView tvBirthday;
    private TextView tvAge;
    private TextView tvSex;
    private UserBean userBean;
    private Button save;
    private boolean sex;
    private ImageView head;

    @Override
    public void onBackPressed() {
        updateUserData();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_userinformation);
        userBean = (UserBean) getIntent().getSerializableExtra("userBean");//获取Actvity之间的传值
        dataSave = new ListDataSaveTool(this);

        ImageView imageView = findViewById(R.id.ivBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });
        String url = "http://img4.duitang.com/uploads/item/201511/18/20151118192130_edwQ5.thumb.700_0.jpeg";
        head = findViewById(R.id.ivHead);
        Glide.with(UserInformationActivity.this).load(url).into(head);
        tvName = findViewById(R.id.tvName);
        tvBirthday = findViewById(R.id.tvBirthday);
        tvAge = findViewById(R.id.tvAge);
        tvSex = findViewById(R.id.tvSex);
        sex = userBean.getSex();
        tvSex.setText("性别：" + (sex ? "男" : "女"));
        tvName.setText("姓名："+userBean.name);
        tvBirthday.setText("生日：" + userBean.getBirthday());
        tvAge.setText("年龄：" + userBean.getAge());
        TextView textView = findViewById(R.id.tvName);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override//在dialog里面写东西
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UserInformationActivity.this);
                final View view = LayoutInflater.from(UserInformationActivity.this).inflate(R.layout.act_dialogname, null);
                final EditText etName = view.findViewById(R.id.etName);
                etName.setText(userBean.getName());
                save = view.findViewById(R.id.bSave);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvName.setText("姓名：" + etName.getText().toString());
                        userBean.setName(etName.getText().toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        tvBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        tvSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();

            }
        });
    }

    public void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();//获取当前日历
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (userBean.year > 0) {

            year = userBean.year;
            month = userBean.month - 1;
            day = userBean.day;
        }
        new DatePickerDialog(this, 0, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvBirthday.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                userBean.year = year;
                userBean.month = month + 1;
                userBean.day = dayOfMonth;

                tvAge.setText("年龄：" + userBean.getAge());
            }
        }, year, month, day).show();
    }

    private void showPopwindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.act_pop, null);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#ffffff"));
        window.setBackgroundDrawable(dw);
        window.setAnimationStyle(R.style.popwindow_anim_style);
        window.showAtLocation(UserInformationActivity.this.findViewById(R.id.tvSex), Gravity.BOTTOM, 0, 70);
        TextView male = view.findViewById(R.id.tvMale);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = true;
                tvSex.setText("性别：" + (sex ? "男" : "女"));
                userBean.setSex(sex);
                window.dismiss();

            }
        });
        TextView female = view.findViewById(R.id.tvFemale);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = false;
                tvSex.setText("性别：" + (sex ? "男" : "女"));
                userBean.setSex(sex);
                window.dismiss();

            }
        });
        TextView cancel = view.findViewById(R.id.tvCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
    }

    private void updateUserData() {
        dataSave.setUserBean(userBean);
        finish();
    }
}
