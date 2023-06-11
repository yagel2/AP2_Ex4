package com.example.ap2_ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Connection extends AppCompatActivity {
    private static final int SELECT_IMAGE = 1;  // This is the line that defines the constant

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            // Do something with the selected image Uri here
        }
    }
}