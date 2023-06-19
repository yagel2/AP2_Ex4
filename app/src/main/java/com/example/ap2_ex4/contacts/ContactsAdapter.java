package com.example.ap2_ex4.contacts;

import java.util.List;
import android.view.View;
import com.example.ap2_ex4.R;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private final List<Contact> contacts;
    private final OnItemClickListener listener;

    public ContactsAdapter(List<Contact> namesList, OnItemClickListener listener) {
        this.contacts = namesList;
        this.listener = listener;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (contacts!=null) {
            final Contact current = contacts.get(position);
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

    public void addItem(Contact newContact) {
        contacts.add(newContact);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvContent;
        public ImageView ivPic;

        public ViewHolder(View v) {
            super(v);
            ivPic = v.findViewById(R.id.ivPic);
            tvAuthor = v.findViewById(R.id.tvAuthor);
            tvContent = v.findViewById(R.id.tvContent);
        }

        public void bind(final Contact item, final OnItemClickListener listener) {
            tvContent.setText(item.getLastTime());
            tvAuthor.setText(item.getDisplayName());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Contact item);
    }
}
