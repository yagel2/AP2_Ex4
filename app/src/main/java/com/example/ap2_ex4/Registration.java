package com.example.ap2_ex4;

import android.net.Uri;
import android.os.Bundle;

import java.io.IOException;

import android.util.Base64;
import android.widget.Toast;
import android.widget.Button;
import android.text.TextUtils;
import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

import com.example.ap2_ex4.api.UserAPI;

import android.graphics.drawable.BitmapDrawable;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class Registration extends AppCompatActivity {
    String username;
    String password;
    String displayName;
    String profilePicture;
    private String currentLanguage;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText displayNameInput;
    private EditText confirmPasswordInput;
    private static final int SELECT_IMAGE = 1;
    private ShapeableImageView selectedProfilePicInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.registration);
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
        selectedProfilePicInput = findViewById(R.id.selectedPic);
        displayNameInput = findViewById(R.id.editTextDisplayName);
        confirmPasswordInput = findViewById(R.id.editTextConfirmPassword);
        handleContacts();
    }

    private void handleContacts() {
        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(view -> {
            Intent selectImageIntent = new Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(selectImageIntent, SELECT_IMAGE);
        });

        Button connectButton = findViewById(R.id.register);
        connectButton.setOnClickListener(v -> {
            if (validateInputs()) {
                User user = new User(this.username, this.password, this.displayName, this.profilePicture);
                UserAPI userAPI = UserAPI.getInstance();
                userAPI.registerUser(user, success -> {
                    if (success) {
                        Intent intent = new Intent(Registration.this, Connection.class);
                        startActivity(intent);
                    }
                });
            }
        });

        TextView connectionLink = findViewById(R.id.connection_link);
        connectionLink.setOnClickListener(v -> {
            Intent intent = new Intent(Registration.this, Connection.class);
            startActivity(intent);
        });
    }

    private boolean validateInputs() {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        displayName = displayNameInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

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
        if (profilePicture == null) {
            Toast.makeText(this, "profile picture cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isAlphanumeric(String str) {
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private String convertBitmapToString() {
        Bitmap bitmap = ((BitmapDrawable) selectedProfilePicInput.getDrawable()).getBitmap();
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bas);
        byte[] imageBytes = bas.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), selectedImage);
                selectedProfilePicInput.setImageBitmap(bitmap);
                profilePicture = convertBitmapToString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
