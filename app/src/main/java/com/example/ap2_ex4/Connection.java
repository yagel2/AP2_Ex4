package com.example.ap2_ex4;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.text.TextUtils;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.ap2_ex4.api.UserAPI;
import com.example.ap2_ex4.contacts.Contacts;
import androidx.appcompat.app.AppCompatActivity;

public class Connection extends AppCompatActivity {
    private String currentLanguage;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.connection);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentLanguage.equals(LocaleHelper.getSelectedLanguage(this))) {
            recreate();
        }
    }

    private void init() {
        usernameInput = findViewById(R.id.editTextUsername);
        passwordInput = findViewById(R.id.editTextPassword);
        handleConnection();
    }

    private void handleConnection() {
        Button connectButton = findViewById(R.id.connect);
        connectButton.setOnClickListener(v -> {
            if (validateInputs()) {
                ConnectionDetails connectionDetails = new ConnectionDetails(
                        usernameInput.getText().toString(), passwordInput.getText().toString());
                UserAPI.getInstance().loginUser(connectionDetails, success -> {
                    if (success) {
                        Intent intent = new Intent(Connection.this, Contacts.class);
                        startActivity(intent);
                    }
                });
            }
        });

        TextView registerLink = findViewById(R.id.register_link);
        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(Connection.this, Registration.class);
            startActivity(intent);
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