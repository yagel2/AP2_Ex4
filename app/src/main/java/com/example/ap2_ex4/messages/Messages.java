package com.example.ap2_ex4.messages;

import android.os.Bundle;
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
    private String currentLanguage;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.chat_screen);

        Contact currentContact = Contacts.getCurrentContact();
        TextView displayName = findViewById(R.id.textName);
        ImageButton sendButton = findViewById(R.id.buttonSend);
        ImageButton settingsButton = findViewById(R.id.backButton);
        ImageView contactProfilePic = findViewById(R.id.imageProfile);
        RecyclerView messagesRecyclerView = findViewById(R.id.messages_recycler_view);

        messageAdapter = new MessageAdapter(new ArrayList<>(), this);
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
                addMessageToList(message);
                input.setText("");
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addMessageToList(String message) {
        String sender = "sender";
        String receiver = "receiver";
        Message newMessage = new Message(sender, message);
        messageAdapter.addItem(newMessage);
    }

    @Override
    public void onItemClick(Message item) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentLanguage.equals(LocaleHelper.getSelectedLanguage(this))) {
            recreate();
        }
    }
}
