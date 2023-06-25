package com.example.ap2_ex4.contacts;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 3)
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactDao contactDao();
}