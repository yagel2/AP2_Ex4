package com.example.ap2_ex4.contacts;

import java.util.List;
import android.os.Bundle;
import androidx.room.Room;
import java.util.ArrayList;
import com.example.ap2_ex4.R;
import android.content.Intent;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.ImageButton;
import com.example.ap2_ex4.Settings;
import android.annotation.SuppressLint;
import com.example.ap2_ex4.LocaleHelper;
import com.example.ap2_ex4.api.AllMessagesFromChat;
import com.example.ap2_ex4.api.CallbackResponse;
import com.example.ap2_ex4.api.MessageFormatFromServer;
import com.example.ap2_ex4.api.UserAPI;
import com.example.ap2_ex4.messages.Messages;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;

public class Contacts extends AppCompatActivity implements ContactsAdapter.OnItemClickListener {
    private ContactDB db;
    private ContactDao contactDao;
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
        db = Room.databaseBuilder(getApplicationContext(),
                ContactDB.class, "contactsDB").build();
        contactDao = db.contactDao();
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
//                UserAPI.getInstance().addContact(name, new CallbackResponse() {
//                    @Override
//                    public void onResponse(boolean success) {
//                    }
//                });
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadContacts() {
        new Thread(() -> {
            List<Contact> contacts = contactDao.getAllContacts();
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
        Contact newContact = new Contact(username, "", R.drawable.person_circle);
        contactsAdapter.addContact(newContact);
        contactsAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteContact(Contact contact) {
        new Thread(() -> contactDao.delete(contact)).start();
        runOnUiThread(() -> {
            contactsAdapter.deleteContact(contact);
            contactsAdapter.notifyDataSetChanged();
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
