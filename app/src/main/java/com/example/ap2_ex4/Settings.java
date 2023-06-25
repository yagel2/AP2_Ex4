package com.example.ap2_ex4;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.content.res.Configuration;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.ap2_ex4.api.UserAPI;
import com.example.ap2_ex4.contacts.Contacts;
import com.example.ap2_ex4.messages.Messages;

public class Settings extends AppCompatActivity {
    private EditText editServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(this, LocaleHelper.getSelectedLanguage(this));
        setContentView(R.layout.settings);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchLanguage = findViewById(R.id.switchLanguage);
        switchLanguage.setChecked(LocaleHelper.getSelectedLanguage(this).equals("he"));
        switchLanguage.setText(LocaleHelper.getSelectedLanguage(this).equals("he") ? R.string.Hebrew : R.string.English);
        switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                LocaleHelper.setLocale(this, "he");
            } else {
                LocaleHelper.setLocale(this, "en");
            }
            updateLanguage();
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchDayNightMode = findViewById(R.id.switchDayNightMode);
        switchDayNightMode.setChecked((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES);
        switchDayNightMode.setText(switchDayNightMode.isChecked() ? R.string.nightMode : R.string.dayMode);
        switchDayNightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                switchDayNightMode.setText(R.string.nightMode);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                switchDayNightMode.setText(R.string.dayMode);
            }
            recreate();
        });
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            if (Contacts.getDb() != null) {
                deleteDatabase("contactsDB");
            }
            if (Messages.getDb() != null) {
                deleteDatabase("messagesDB");
            }
            Intent intent = new Intent(Settings.this, Connection.class);
            startActivity(intent);
            finish();
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this, Contacts.class);
            startActivity(intent);
            finish();
        });

        editServer = findViewById(R.id.editServer);
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> {
            String serverText = editServer.getText().toString();
            UserAPI.getInstance().setRetrofit(serverText);
        });
    }

    private void updateLanguage() {
        LocaleHelper.setLocale(this, LocaleHelper.getSelectedLanguage(this));
        recreate();
    }
}
