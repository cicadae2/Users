package com.uban.users.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.uban.users.R;
import com.uban.users.adapter.UserListAdapter;
import com.uban.users.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends Activity {
    ListDataSaveTool dataSave;
    private List<UserBean> listBean;
    private UserListAdapter adapter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_userlist);

        dataSave = new ListDataSaveTool(this);


        initViews();
        ImageView imageView = findViewById(R.id.ivBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.updateData(getContent());
        }

    }

    private void initViews() {
        adapter = new UserListAdapter(getContent());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.setOnClickListener(new UserListAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                UserBean userBean = listBean.get(position);
                Intent intent = new Intent(UserListActivity.this, UserInformationActivity.class);
                intent.putExtra("userBean", userBean);
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new UserListAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(final int position) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserListActivity.this);
                dialog.setTitle("删除");
                dialog.setMessage("确定要删除吗？");
                dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.removeItem(position);
                        dataSave.setDataList(listBean);
                        Toast.makeText(UserListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
    }

    private List<UserBean> getContent() {
        listBean = dataSave.getDataList();
        return listBean;

    }


}

