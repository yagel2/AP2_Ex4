package com.example.ap2_ex4.messages;

import java.util.Locale;
import java.util.Calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import androidx.annotation.NonNull;


@Entity
public class Message {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    private String sender;
    private String content;
    private String created;

    public Message(String sender, String content) {
        this.id = content;
        this.sender = sender;
        this.content = content;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        this.created = sdf.format(Calendar.getInstance().getTime());
    }

    @NonNull
    public String getId() {
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

    public void setId(@NonNull String id) {
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
