package com.example.ap2_ex4;

import androidx.annotation.NonNull;

import com.example.ap2_ex4.contacts.Contacts;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FireBase extends FirebaseMessagingService {
    public FireBase() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Contacts.getDb();
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}