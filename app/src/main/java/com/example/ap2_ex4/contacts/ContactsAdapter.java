package com.example.ap2_ex4.contacts;

import java.util.List;
import android.view.View;
import com.example.ap2_ex4.R;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private final ContactDB db;
    private List<Contact> contacts;
    private final OnItemClickListener listener;

    public ContactsAdapter(List<Contact> contacts, OnItemClickListener listener, ContactDB db) {
        this.db = db;
        this.contacts = contacts;
        this.listener = listener;
    }

    public List<Contact> getContacts() {
        return this.contacts;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(contacts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addContact(Contact newContact) {
        new Thread(() -> {
            db.contactDao().insert(newContact);
            contacts.add(newContact);
            notifyItemInserted(contacts.size() - 1);
        }).start();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteContact(Contact contact) {
        new Thread(() -> {
            db.contactDao().delete(contact);
            contacts.remove(contact);
            notifyItemRemoved(contacts.indexOf(contact));
        }).start();

//        new Thread(() -> {
//            new Thread(() -> db.contactDao().delete(contact)).start();
//            contacts.remove(contact);
//            notifyItemRemoved(contacts.indexOf(contact));
//        }).start();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactLastTime;
        public TextView contactDisplayName;
        public ImageView contactProfilePic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactLastTime = itemView.findViewById(R.id.contactLastTime);
            contactProfilePic = itemView.findViewById(R.id.contactProfilePic);
            contactDisplayName = itemView.findViewById(R.id.contactDisplayName);
        }

        public void bind(final Contact contact, final OnItemClickListener listener) {
            contactDisplayName.setText(contact.getDisplayName());
            contactLastTime.setText(contact.getLastTime());
            itemView.setOnClickListener(v -> listener.onItemClick(contact));
            itemView.setOnLongClickListener(v -> {
                listener.onItemLongClick(contact);
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
        void onItemLongClick(Contact contact);
    }
}
