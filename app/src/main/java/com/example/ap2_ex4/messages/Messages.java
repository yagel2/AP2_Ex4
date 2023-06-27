package com.example.ap2_ex4.messages;

import java.util.List;
import android.os.Bundle;
import java.util.Calendar;
import androidx.room.Room;
import android.util.Base64;
import java.util.ArrayList;
import com.example.ap2_ex4.R;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.ImageButton;
import java.text.SimpleDateFormat;
import android.graphics.BitmapFactory;
import android.annotation.SuppressLint;
import com.example.ap2_ex4.api.UserAPI;
import com.example.ap2_ex4.LocaleHelper;
import com.example.ap2_ex4.contacts.Contact;
import com.example.ap2_ex4.contacts.Contacts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ap2_ex4.api.MessageFromServer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.imageview.ShapeableImageView;

public class Messages extends AppCompatActivity implements MessageAdapter.OnItemClickListener {
    private UserAPI userApi;
    private static MessageDB db;
    private String currentLanguage;
    private Contact currentContact;
    private MessageAdapter messageAdapter;
    private RecyclerView messagesRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.chat_screen);
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
        currentContact = Contacts.getCurrentContact();
        if (db == null) {
            db = Room.databaseBuilder(getApplicationContext(),
                    MessageDB.class, "messagesDB").allowMainThreadQueries().build();
        }
        if (this.userApi.isFirstMessages()) {
            this.userApi.setFirstMessages(false);
            db.messageDao().deleteAllMessages();
        }
        messageAdapter = new MessageAdapter(new ArrayList<>(), this, db);
        messagesRecyclerView = findViewById(R.id.messages_recycler_view);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setAdapter(messageAdapter);
        getMessages();
        handleMessages();
    }

    public static Bitmap convertToBitmap(String picture) {
        byte[] decodedBytes = Base64.decode(picture, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void handleMessages() {
        TextView displayName = findViewById(R.id.textName);
        ImageButton sendButton = findViewById(R.id.buttonSend);
        ImageButton settingsButton = findViewById(R.id.backButton);
        ShapeableImageView contactProfilePic = findViewById(R.id.imageProfile);
        RecyclerView messagesRecyclerView = findViewById(R.id.messages_recycler_view);

        displayName.setText(currentContact.getDisplayName());
        contactProfilePic.setImageBitmap(convertToBitmap(currentContact.getProfilePic()));

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

    public static MessageDB getDb() {
        return db;
    }

    public static String extractTime(String date) {
        String time = date.substring(11, 16);
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(time));
            calendar.add(Calendar.HOUR_OF_DAY, 3);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getMessages() {
        new Thread(() -> userApi.getMessages(currentContact.getServerId(), success -> {
            if (success) {
                db.messageDao().deleteAllMessages();
                String lastTime = "";
                List<MessageFromServer> messages = userApi.getCurrentMessages();
                for (int i = messages.size() - 1; i >= 0; i--) {
                    String sender = "sender";
                    int type;
                    if (!messages.get(i).getSender().getUsername().equals(currentContact.getUsername())) {
                        type = 0;
                    } else {
                        type = 1;
                        sender = "receiver";
                    }
                    lastTime = extractTime(messages.get(i).getCreated());
                    Message message = new Message(sender, messages.get(i).getContent(), lastTime);
                    message.setType(type);
                    messageAdapter.addMessage(message);
                    messageAdapter.notifyDataSetChanged();
                }
                currentContact.setLastTime(lastTime);
                messagesRecyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
            }
        })).start();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addMessage(String message) {
        userApi.addMessage(message, currentContact.getServerId(), success -> {
            if (success) {
                Message newMessage = new Message("sender", message, null);
                messageAdapter.addMessage(newMessage);
                currentContact.setLastTime(newMessage.getCreated());
                messageAdapter.notifyDataSetChanged();
                messagesRecyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onItemClick(Message item) {
    }
}
