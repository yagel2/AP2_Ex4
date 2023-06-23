package com.example.ap2_ex4.contacts;

import java.util.List;

import android.os.Bundle;

import androidx.room.Room;

import java.util.ArrayList;

import com.example.ap2_ex4.Connection;
import com.example.ap2_ex4.MyApplication;
import com.example.ap2_ex4.R;

import android.content.Intent;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.ImageButton;

import com.example.ap2_ex4.Registration;
import com.example.ap2_ex4.Settings;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.ap2_ex4.User;
import com.example.ap2_ex4.api.CallbackResponse;
import com.example.ap2_ex4.api.Chat;
import com.example.ap2_ex4.api.LastAddedContact;
import com.example.ap2_ex4.api.UserAPI;
import com.example.ap2_ex4.LocaleHelper;
import com.example.ap2_ex4.messages.Messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contacts extends AppCompatActivity implements ContactsAdapter.OnItemClickListener {
    private ContactDB db;
    private UserAPI userApi;
    private String currentLanguage;
    private static Contact currentContact;
    private ContactsAdapter contactsAdapter;
    private RecyclerView contactsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.contacts);
        initFields();
        handleContacts();
        loadContacts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentLanguage.equals(LocaleHelper.getSelectedLanguage(this))) {
            recreate();
        }
    }

    private void initFields() {
        if (UserAPI.getInstance().isFirst()) {
            deleteDatabase("contactsDB");
            UserAPI.getInstance().setFirst(false);
        }
        this.userApi = UserAPI.getInstance();
        TextView usernameHeading = findViewById(R.id.usernameHeading);
        usernameHeading.setText(this.userApi.getConnectedUser().getUsername());
        db = Room.databaseBuilder(getApplicationContext(),
                ContactDB.class, "contactsDB").allowMainThreadQueries().build();
        contactsAdapter = new ContactsAdapter(new ArrayList<>(), this, db);
        contactsRecyclerView = findViewById(R.id.contacts_recycler_view);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactsAdapter);
    }

    private void handleContacts() {
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
                    addContact(name);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadContacts() {
        new Thread(() -> {
            List<Contact> contacts = db.contactDao().getAllContacts();
            runOnUiThread(() -> {
                contactsAdapter.getContacts().clear();
                contactsAdapter.setContacts(contacts);
                contactsAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    public static Contact getCurrentContact() {
        return currentContact;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addContact(String username) {
        if (db.contactDao().findContactByUsername(username) != null) {
            Toast.makeText(MyApplication.context,
                    "The user already exists in your contact list",
                    Toast.LENGTH_LONG).show();
            return;
        }
        userApi.addContact(username, success -> {
            if (success) {
                LastAddedContact contactDetails = userApi.getLastAdded();
                Contact newContact = new Contact(contactDetails.getContact().getUsername(),
                        contactDetails.getContact().getDisplayName(),
                        contactDetails.getId(), R.drawable.person_circle);
                contactsAdapter.addContact(newContact);
                contactsAdapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteContact(Contact contact) {
//        runOnUiThread(() -> {
//            userApi.deleteContact(contact.getServerId(), success -> {
//                if (success) {
//                    contactsAdapter.deleteContact(contact);
//                    contactsAdapter.notifyDataSetChanged();
//                }
//            });
//        });

        userApi.deleteContact(contact.getServerId(), success -> {
            if (success) {
                contactsAdapter.deleteContact(contact);
                contactsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(Contact contact) {
        currentContact = contact;
        Intent intent = new Intent(Contacts.this, Messages.class);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
        builder.setTitle("Delete Contact");
        builder.setMessage("Are you sure you want to delete this contact?");
        builder.setPositiveButton("Yes", (dialog, which) -> deleteContact(contact));
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
