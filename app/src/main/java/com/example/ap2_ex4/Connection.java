package com.example.ap2_ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        usernameInput = findViewById(R.id.editTextTextUsername);
        passwordInput = findViewById(R.id.editTextTextPassword);

        Button connectButton = findViewById(R.id.connect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Intent intent = new Intent(Connection.this, Contacts.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInputs() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 8 || !isAlphanumeric(password)) {
            Toast.makeText(this, "Password must have at least 8 characters and contain both letters and numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isAlphanumeric(String str) {
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
