package com.example.ap2_ex4.messages;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface MessageDao {
    @Query("DELETE FROM message")
    void deleteAllMessages();
    @Query("SELECT * FROM message")
    List<Message> getAllMessages();
    @Insert
    void insert(Message... messages);
    @Update
    void update(Message... messages);
    @Delete
    void delete(Message... messages);
}