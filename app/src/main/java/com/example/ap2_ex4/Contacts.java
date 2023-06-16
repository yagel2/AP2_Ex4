package com.example.ap2_ex4;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ap2_ex4.enteties.SingleContactInList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class Contacts extends AppCompatActivity implements ContactsAdapter.OnItemClickListener {
    private String currentLanguage;
    private RecyclerView namesRecyclerView;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        namesRecyclerView = findViewById(R.id.names_recycler_view);
        namesRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // set LayoutManager here
        contactsAdapter = new ContactsAdapter(new ArrayList<>(), this);
        namesRecyclerView.setAdapter(contactsAdapter);

        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);

        ImageButton settingsButton = findViewById(R.id.right_icon);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Contacts.this, Settings.class);
            startActivity(intent);
        });

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
            builder.setTitle("Add a name");
            final EditText input = new EditText(Contacts.this);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String name = input.getText().toString();
                if (!name.isEmpty()) {
                    addNameToList(name);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addNameToList(String name) {
        SingleContactInList newContact = new SingleContactInList(0, name, "", R.drawable.person_circle);
        contactsAdapter.addItem(newContact);
    }

    @Override
    public void onItemClick(SingleContactInList item) {
        Intent intent = new Intent(Contacts.this, ChatScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentLanguage.equals(LocaleHelper.getSelectedLanguage(this))) {
            recreate();
        }
    }
}
