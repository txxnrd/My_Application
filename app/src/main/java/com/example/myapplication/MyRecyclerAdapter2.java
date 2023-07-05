package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MyRecyclerAdapter2 extends RecyclerView.Adapter<MyRecyclerAdapter2.ViewHolder> {

    private ArrayList<BlogItem> mBlogList;

    private ItemClickListener mClickListener;

    // 생성자를 추가합니다.
    public MyRecyclerAdapter2() {
        mBlogList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyRecyclerAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_list_item, parent, false);
        return new ViewHolder(view);
    }
    public BlogItem getItem(int position) {
        if (position < mBlogList.size()) {
            return mBlogList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter2.ViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }
    public void setBlogList(ArrayList<BlogItem> list) {
        if (list != null) {
            this.mBlogList = list;
        } else {
            this.mBlogList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return mBlogList.size();
    }
    public interface OnDialogListener {
        void onFinish(int position, BlogItem blogItem);
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView day_image;
        TextView day;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day_image = itemView.findViewById(R.id.day_image);
            day = itemView.findViewById(R.id.day);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(this);

        }
        void onBind(BlogItem item) {
            if (item != null) {
                Glide.with(day_image.getContext())
                        .load(item.getDay_image())
                        .error(R.drawable.default_image)
                        .into(day_image);
                day.setText(item.getDay());
                title.setText(item.getTitle());
            }
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }
}