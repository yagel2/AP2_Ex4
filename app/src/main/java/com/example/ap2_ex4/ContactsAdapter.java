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
    private List<SingleContactInList> contacts;
    private final OnItemClickListener listener;

    public ContactsAdapter(List<SingleContactInList> namesList, OnItemClickListener listener) {
        this.contacts = namesList;
        this.listener = listener;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (contacts!=null) {
            final SingleContactInList current = contacts.get(position);
            holder.bind(current, listener);
        }
    }

    @Override
    public int getItemCount() {
        if (contacts!=null) {
            return contacts.size();
        }
        return 0;
    }

    public void addItem(SingleContactInList newContact) {
        contacts.add(newContact);
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
