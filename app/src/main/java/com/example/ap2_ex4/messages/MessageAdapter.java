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
    private final List<Message> messages;
    private final OnItemClickListener listener;
    public MessageAdapter(List<Message> messagesList, OnItemClickListener listener) {
        this.listener = listener;
        this.messages = messagesList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View sender = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.message_sender, parent, false);
        View receiver = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.message_receiver, parent, false);
        return new ViewHolder(sender);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            holder.bind(current, listener);
        }
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(Message newMessage) {
        messages.add(newMessage);
        notifyDataSetChanged();
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