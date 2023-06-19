package com.example.ap2_ex4;
import com.example.ap2_ex4.api.CallbackRegistration;
import com.example.ap2_ex4.api.UserAPI;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.text.TextUtils;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {
    private String currentLanguage;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText displayNameInput;
    private EditText confirmPasswordInput;
    private static final int SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.registration);
        usernameInput = findViewById(R.id.editTextTextUsername);
        passwordInput = findViewById(R.id.editTextTextPassword);
        confirmPasswordInput = findViewById(R.id.editTextTextConfirmPassword);
        displayNameInput = findViewById(R.id.editTextDisplayName);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectImageIntent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectImageIntent, SELECT_IMAGE);
            }
        });
        Button connectButton = findViewById(R.id.register);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    User user = new User(usernameInput.getText().toString(), passwordInput.getText().toString(), displayNameInput.getText().toString());
                    UserAPI userAPI = UserAPI.getInstance();
                    userAPI.registerUser(user, new CallbackRegistration() {
                        @Override
                        public void onResponse(boolean success) {
                            if(success){
                                Intent intent = new Intent(Registration.this, Connection.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
//                if (validateInputs()) {
//                    Intent intent = new Intent(Registration.this, Connection.class);
//                    User user = new User(usernameInput.getText().toString(), passwordInput.getText().toString(), displayNameInput.getText().toString());
//                    UserAPI userAPI = UserAPI.getInstance();
//                    userAPI.registerUser(user);
//                    startActivity(intent);
//                }
            }
        });

        TextView connectionLink = findViewById(R.id.connection_link);
        connectionLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Connection.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentLanguage.equals(LocaleHelper.getSelectedLanguage(this))) {
            recreate();
        }
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
            // Do something with the selected image here
        }
    }
}