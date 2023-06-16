package com.example.ap2_ex4;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ap2_ex4.enteties.SingleContactInList;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<SingleContactInList> posts;
    private final OnItemClickListener listener;

    public ContactsAdapter(List<SingleContactInList> namesList, OnItemClickListener listener) {
        this.posts = namesList;
        this.listener = listener;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (posts!=null) {
            final SingleContactInList current = posts.get(position);
            holder.bind(current, listener);
        }
    }

    @Override
    public int getItemCount() {
        if (posts!=null) {
            return posts.size();
        }
        return 0;
    }

    public void addItem(SingleContactInList newContact) {
        posts.add(newContact);
        notifyDataSetChanged();
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

        public void bind(final SingleContactInList item, final OnItemClickListener listener) {
            tvAuthor.setText(item.getAuthor());
            tvContent.setText(item.getContent());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }

    }

    public interface OnItemClickListener {
        void onItemClick(SingleContactInList item);
    }
}
