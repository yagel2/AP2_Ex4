package com.example.ap2_ex4.messages;

import java.util.Locale;
import java.util.Calendar;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    private final String created;
    private final String author;
    private final String content;

    public Message(String author, String content) {
        this.author = author;
        this.content = content;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        this.created = sdf.format(Calendar.getInstance().getTime());
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        return created;
    }
}
