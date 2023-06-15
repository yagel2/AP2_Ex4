package com.example.ap2_ex4;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ap2_ex4.enteties.SingleContactInList;

import java.util.ArrayList;
import java.util.List;

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.ViewHolder> {
    private List<SingleContactInList> posts;

    public NamesAdapter(ArrayList<SingleContactInList> namesList) {
        this.posts = namesList;
    }

    @Override
    public NamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (posts!=null) {
            final SingleContactInList current = posts.get(position);
            holder.tvAuthor.setText(current.getAuthor());
            holder.tvContent.setText(current.getContent());
            holder.ivPic.setImageResource(current.getPic());
        }
    }

    @Override
    public int getItemCount() {
        if (posts!=null) {
            return posts.size();
        }
        return 0;
    }
    public List<SingleContactInList> getPosts() {
        return posts;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvContent;
        public ImageView ivPic;

        public ViewHolder(View v) {
            super(v);
            tvAuthor = v.findViewById(R.id.tvAuthor);
            tvContent = v.findViewById(R.id.tvContent);
            ivPic = v.findViewById(R.id.ivPic);
        }
    }

}