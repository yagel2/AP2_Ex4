package com.example.ap2_ex4.contacts;

import android.os.Bundle;
import java.util.ArrayList;
import com.example.ap2_ex4.R;
import android.content.Intent;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.ImageButton;
import com.example.ap2_ex4.Settings;
import android.annotation.SuppressLint;
import com.example.ap2_ex4.LocaleHelper;
import com.example.ap2_ex4.messages.Messages;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contacts extends AppCompatActivity implements ContactsAdapter.OnItemClickListener {
    private Contact currentContact;
    private String currentLanguage;

    private RecyclerView namesRecyclerView;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.contacts);

        namesRecyclerView = findViewById(R.id.names_recycler_view);
        namesRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // set LayoutManager here
        contactsAdapter = new ContactsAdapter(new ArrayList<>(), this);
        namesRecyclerView.setAdapter(contactsAdapter);

        ImageButton settingsButton = findViewById(R.id.right_icon);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Contacts.this, Settings.class);
            startActivity(intent);
        });

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
            builder.setTitle("Add a new contact");
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
        Contact newContact = new Contact(0, name, "00:00", R.drawable.person_circle);
        contactsAdapter.addItem(newContact);
    }

    @Override
    public void onItemClick(Contact item) {
        currentContact = item;
        Intent intent = new Intent(Contacts.this, Messages.class);
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
