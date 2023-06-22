package com.example.ap2_ex4.messages;

import java.util.List;
import android.os.Bundle;
import androidx.room.Room;
import java.util.ArrayList;
import com.example.ap2_ex4.R;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.annotation.SuppressLint;
import com.example.ap2_ex4.LocaleHelper;
import com.example.ap2_ex4.contacts.Contact;
import com.example.ap2_ex4.contacts.Contacts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class Messages extends AppCompatActivity implements MessageAdapter.OnItemClickListener {
    private MessageDB db;
    private MessageDao messageDao;
    private Contact currentContact;
    private String currentLanguage;
    private MessageAdapter messageAdapter;
    private RecyclerView messagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.chat_screen);
        initFields();
        handleMessages();
        loadMessages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentLanguage.equals(LocaleHelper.getSelectedLanguage(this))) {
            recreate();
        }
    }

    private void initFields() {
        currentContact = Contacts.getCurrentContact();
        db = Room.databaseBuilder(getApplicationContext(),
                MessageDB.class, currentContact.getDisplayName()).build();
        messageDao = db.messageDao();
        messageAdapter = new MessageAdapter(new ArrayList<>(), this, db);
        messagesRecyclerView = findViewById(R.id.messages_recycler_view);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setAdapter(messageAdapter);
    }

    private void handleMessages() {
        TextView displayName = findViewById(R.id.textName);
        ImageButton sendButton = findViewById(R.id.buttonSend);
        ImageButton settingsButton = findViewById(R.id.backButton);
        ImageView contactProfilePic = findViewById(R.id.imageProfile);
        RecyclerView messagesRecyclerView = findViewById(R.id.messages_recycler_view);

        displayName.setText(currentContact.getDisplayName());
        contactProfilePic.setImageResource(currentContact.getProfilePic());

        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        messagesRecyclerView.setAdapter(messageAdapter);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Messages.this, Contacts.class);
            startActivity(intent);
        });

        sendButton.setOnClickListener(v -> {
            EditText input = findViewById(R.id.editMessage);
            String message = input.getText().toString();
            if (!message.isEmpty()) {
                addMessage(message);
                input.setText("");
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadMessages() {
        new Thread(() -> {
            List<Message> messages = messageDao.getAllMessages();
            runOnUiThread(() -> {
                messageAdapter.getMessages().clear();
                messageAdapter.setMessages(messages);
                messageAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addMessage(String message) {
        String sender = "sender";
        String receiver = "receiver";
        Message newMessage = new Message(sender, message);
        new Thread(() -> {
            messageDao.insert(newMessage);
            List<Message> updatedMessages = messageDao.getAllMessages();
            runOnUiThread(() -> {
                messageAdapter.setMessages(updatedMessages);
                messageAdapter.notifyDataSetChanged();
                messagesRecyclerView.scrollToPosition(updatedMessages.size() - 1);
            });
        }).start();
        currentContact.setLastTime(newMessage.getCreated());
    }

    @Override
    public void onItemClick(Message item) {}
}
