package com.example.ap2_ex4.messages;

import java.util.List;
import android.view.View;
import com.example.ap2_ex4.R;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final MessageDB db;
    private List<Message> messages;
    private final OnItemClickListener listener;
    public MessageAdapter(List<Message> messages, OnItemClickListener listener, MessageDB db) {
        this.db = db;
        this.listener = listener;
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        if (viewType == 0) { // Sender view
            itemView = inflater.inflate(R.layout.message_sender, parent, false);
        } else { // Receiver view
            itemView = inflater.inflate(R.layout.message_receiver, parent, false);
        }

        return new ViewHolder(itemView);
    }
    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSender().equals("sender")) {
            return 0; // Sender view type
        } else {
            return 1; // Receiver view type
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(messages.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addMessage(Message newMessage) {
        new Thread(() -> {
            db.messageDao().insert(newMessage);
        }).start();
        messages.add(newMessage);
        notifyDataSetChanged();
        notifyItemInserted(messages.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView content;

        public ViewHolder(View v) {
            super(v);
            time = v.findViewById(R.id.hour);
            content = v.findViewById(R.id.textMessage);
        }

        public void bind(final Message item, final OnItemClickListener listener) {
            time.setText(item.getCreated());
            content.setText(item.getContent());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Message item);
    }
}