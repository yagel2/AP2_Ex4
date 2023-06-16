package com.example.ap2_ex4.enteties;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Entity
public class SingleMessageInList {
    @PrimaryKey(autoGenerate = true)
    private String author;
    private String content;
    private String date;

    public SingleMessageInList(String author, String content) {
        this.author = author;
        this.content = content;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault()); // Hour:Minute format
        this.date = sdf.format(Calendar.getInstance().getTime());
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
