package com.example.ap2_ex4.contacts;

import java.util.List;
import android.os.Bundle;
import androidx.room.Room;
import java.util.ArrayList;
import android.widget.Toast;
import com.example.ap2_ex4.R;
import android.content.Intent;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.ImageButton;
import com.example.ap2_ex4.Settings;
import com.example.ap2_ex4.api.Chat;
import com.example.ap2_ex4.api.UserAPI;
import android.annotation.SuppressLint;
import com.example.ap2_ex4.LocaleHelper;
import com.example.ap2_ex4.MyApplication;
import com.example.ap2_ex4.messages.Messages;
import com.example.ap2_ex4.api.LastAddedContact;
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
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentLanguage.equals(LocaleHelper.getSelectedLanguage(this))) {
            recreate();
        }
    }

    private void init() {
        this.userApi = UserAPI.getInstance();
        TextView usernameHeading = findViewById(R.id.usernameHeading);
        usernameHeading.setText(this.userApi.getConnectedUser().getUsername());
        this.db = Room.databaseBuilder(getApplicationContext(),
                ContactDB.class, "contactsDB").allowMainThreadQueries().build();
        if (this.userApi.isFirstContacts()) {
            this.userApi.setFirstMessages(true);
            this.userApi.setFirstContacts(false);
            this.db.contactDao().deleteAllContacts();
        }
        contactsAdapter = new ContactsAdapter(new ArrayList<>(), this, db);
        contactsRecyclerView = findViewById(R.id.contacts_recycler_view);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactsAdapter);
        getChats();
        handleContacts();
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
    private void getChats() {
        new Thread(() -> userApi.getChats(success -> {
            if (success) {
                List<Chat> chats = userApi.getAllChatsAfterServer();
                for (int i = 0; i < chats.size(); i ++) {
                    Contact contact = new Contact(chats.get(i).getUser().getUsername(),
                            chats.get(i).getUser().getDisplayName(),
                            chats.get(i).getId(), R.drawable.person_circle);
                    contactsAdapter.addContact(contact);
                    contactsAdapter.notifyDataSetChanged();
                }
            }
        })).start();
    }

    public static Contact getCurrentContact() {
        return currentContact;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addContact(String username) {
        if (this.db.contactDao().findContactByUsername(username) != null) {
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
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteContact(Contact contact) {
        userApi.deleteContact(contact.getServerId(), success -> {
            if (success) {
                contactsAdapter.deleteContact(contact);
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
