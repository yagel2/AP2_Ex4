package com.example.ap2_ex4.messages;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Message.class}, version = 1)
public abstract class MessageDB extends RoomDatabase {
public abstract MessageDao messageDao();
}
