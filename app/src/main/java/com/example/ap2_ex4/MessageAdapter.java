package com.example.ap2_ex4;

            import androidx.recyclerview.widget.RecyclerView;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.TextView;

            import com.example.ap2_ex4.enteties.SingleContactInList;
            import com.example.ap2_ex4.enteties.SingleMessageInList;

            import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<SingleMessageInList> messages;
    private final OnItemClickListener listener;

    public MessageAdapter(List<SingleMessageInList> messagesList, OnItemClickListener listener) {
        this.messages = messagesList;
        this.listener = listener;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (messages!=null) {
            final SingleMessageInList current = messages.get(position);
            holder.bind(current, listener);
        }
    }

    @Override
    public int getItemCount() {
        if (messages!=null) {
            return messages.size();
        }
        return 0;
    }

    public void addItem(SingleMessageInList newContact) {
        messages.add(newContact);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvContent;
        public ViewHolder(View v) {
            super(v);
            tvAuthor = v.findViewById(R.id.tvAuthor);
            tvContent = v.findViewById(R.id.tvContent);
        }

        public void bind(final SingleMessageInList item, final OnItemClickListener listener) {
            tvAuthor.setText(item.getAuthor());
            tvContent.setText(item.getContent());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }

    }

    public interface OnItemClickListener {
        void onItemClick(SingleMessageInList item);
    }
}
