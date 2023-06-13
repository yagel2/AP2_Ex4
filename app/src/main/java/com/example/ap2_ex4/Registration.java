package com.example.ap2_ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    private static final int SELECT_IMAGE = 1;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private EditText displayNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        usernameInput = findViewById(R.id.editTextTextUsername);
        passwordInput = findViewById(R.id.editTextTextPassword);
        confirmPasswordInput = findViewById(R.id.editTextTextConfirmPassword);
        displayNameInput = findViewById(R.id.editTextDisplayName);

        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectImageIntent, SELECT_IMAGE);
            }
        });

        Button connectButton = findViewById(R.id.register);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Intent intent = new Intent(Registration.this, Connection.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInputs() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        String displayName = displayNameInput.getText().toString();

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
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(displayName)) {
            Toast.makeText(this, "Display name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isAlphanumeric(String str) {
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher m = p.matcher(str);
        return m.matches();
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
