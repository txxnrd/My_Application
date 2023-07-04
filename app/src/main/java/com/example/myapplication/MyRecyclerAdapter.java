package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.bumptech.glide.Glide;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private ArrayList<FriendItem> mFriendList;

    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;
    // 생성자를 추가합니다.
    public MyRecyclerAdapter() {
        mFriendList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }
    public FriendItem getItem(int position) {
        if (position < mFriendList.size()) {
            return mFriendList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }
    public void setFriendList(ArrayList<FriendItem> list) {
        if (list != null) {
            this.mFriendList = list;
        } else {
            this.mFriendList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }
    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        ImageView profile;
        TextView name;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        void onBind(FriendItem item) {
            Glide.with(profile.getContext())
                    .load(item.getImageUrl())
                    .into(profile);
            name.setText(item.getName());
            message.setText(item.getNumber());
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {  // 롱 클릭 이벤트 핸들링
            if (mLongClickListener != null) {mLongClickListener.onItemLongClick(view, getAdapterPosition()); return true;} else return false;
        }


    }
}