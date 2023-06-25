package com.example.ap2_ex4;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ap2_ex4.contacts.Contacts;

public class Settings extends AppCompatActivity {

    private int themeIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getThemeId());
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
        switchDayNightMode.setText(R.string.dayMode);
        switchDayNightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                switchDayNightMode.setText(R.string.nightMode);
            } else {
                switchDayNightMode.setText(R.string.dayMode);
            }
        });

        Button buttonChangeTheme = findViewById(R.id.buttonThemeColor);
        buttonChangeTheme.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Choose Theme")
                    .setItems(new String[]{"Theme 1", "Theme 2", "Theme 3"}, (dialog, which) -> {
                        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
                        sharedPreferences.edit().putInt("themeIndex", which).apply();
                        recreate();
                    })
                    .create().show();
        });

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
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
    }

    private void updateLanguage() {
        LocaleHelper.setLocale(this, LocaleHelper.getSelectedLanguage(this));
        recreate();
    }

    private int getThemeId() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        themeIndex = sharedPreferences.getInt("themeIndex", 0);
        switch (themeIndex) {
            case 1:
                return R.style.Theme_AP2_Ex4_Theme2;
            case 2:
                return R.style.Theme_AP2_Ex4_Theme3;
            default:
                return R.style.Theme_AP2_Ex4_Theme1;
        }
    }

}
