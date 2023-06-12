package com.example.ap2_ex4;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.ViewHolder> {
    private ArrayList<String> namesList;

    public NamesAdapter(ArrayList<String> namesList) {
        this.namesList = namesList;
    }

    @Override
    public NamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(namesList.get(position));
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.name);
        }
    }
}
