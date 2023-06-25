package com.example.ap2_ex4.messages;

import java.util.Locale;
import java.util.Calendar;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String sender;
    private String content;
    private String created;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Ignore
    private int type; // 0 for sender, 1 for receiver



    public Message(String sender, String content, String created) {
        this.id = 0;
        this.sender = sender;
        this.content = content;
        if (created == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            this.created = sdf.format(Calendar.getInstance().getTime());
        } else {
            this.created = created;
        }
    }

    public int getId() {
        return this.id;
    }
    public String getSender() {
        return this.sender;
    }

    public String getContent() {
        return this.content;
    }

    public String getCreated() {
        return this.created;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCreated(String created) {
        this.created = created;
    }
}
