package com.example.ap2_ex4.contacts;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAllContacts();
    @Query("SELECT username FROM contact WHERE username = :username")
    String findContactByUsername(String username);
    @Insert
    void insert(Contact... contacts);
    @Update
    void update(Contact... contacts);
    @Delete
    void delete(Contact... contacts);
}