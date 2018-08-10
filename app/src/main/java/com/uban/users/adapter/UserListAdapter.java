package com.uban.users.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uban.users.R;
import com.uban.users.bean.UserBean;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<UserBean> mData;
    private OnItemLongClickListener longClickListener;
    private OnClickListener OnClickListener;

    public UserListAdapter(List<UserBean> data) {
        this.mData = data;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.OnClickListener = listener;
    }

    public void updateData(List<UserBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.listItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickListener.onLongClick(position);
                return false;
            }
        });
        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickListener.onClick(position);

            }
        });
        UserBean bean = mData.get(position);
        holder.aTv.setText("姓名：" + bean.name);
        holder.bTv.setText("年龄：" + String.valueOf(bean.age));
        holder.cTv.setText("性别：" + (bean.sex ? "男" : "女"));
        holder.dTv.setText("生日" + bean.getBirthday());
        holder.eTv.setText("账号" + bean.account);
        holder.aPicture.setImageResource(bean.head);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView aTv;
        TextView bTv;
        TextView cTv;
        TextView dTv;
        TextView eTv;
        TextView fTv;
        ImageView aPicture;
        LinearLayout listItem;

        public ViewHolder(View itemView) {
            super(itemView);
            listItem = itemView.findViewById(R.id.listItem);
            aTv = itemView.findViewById(R.id.tvName);
            bTv = itemView.findViewById(R.id.tvAge);
            cTv = itemView.findViewById(R.id.tvSex);
            dTv = itemView.findViewById(R.id.tvBirthday);
            eTv = itemView.findViewById(R.id.tvAccount);
            fTv = itemView.findViewById(R.id.tvPassword);
            aPicture = itemView.findViewById(R.id.ivHead);
        }


    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyDataSetChanged();//更新A
    }

    public interface OnItemLongClickListener {
        void onLongClick(int position);
    }

    public interface OnClickListener {
        void onClick(int position);
    }
}
