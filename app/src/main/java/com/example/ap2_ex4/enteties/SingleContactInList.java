package com.example.ap2_ex4.enteties;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class SingleContactInList {

    public SingleContactInList(int id, String author, String content, int pic) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }


    public int getPic() {
        return pic;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String author;
    private String content;
    private int pic;
}
