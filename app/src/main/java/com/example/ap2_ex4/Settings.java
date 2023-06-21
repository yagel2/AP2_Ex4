package com.example.ap2_ex4;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Button;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTheme();
        LocaleHelper.setLocale(this, LocaleHelper.getSelectedLanguage(this));
        setContentView(R.layout.settings);

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

        Switch switchDayNightMode = findViewById(R.id.switchDayNightMode);
        switchDayNightMode.setText(R.string.dayMode);
        switchDayNightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                switchDayNightMode.setText(R.string.nightMode);
            } else {
                switchDayNightMode.setText(R.string.dayMode);
            }
        });

        Button buttonThemeColor = findViewById(R.id.buttonThemeColor);
        buttonThemeColor.setOnClickListener(v -> {
            final String[] themes = {
                    getString(R.string.theme_light),
                    getString(R.string.theme_dark),
                    getString(R.string.theme_default)
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.choose_theme))
                    .setItems(themes, (dialog, which) -> {
                        LocaleHelper.setTheme(this, themes[which]);
                        updateTheme();
                    });
            builder.create().show();
        });
    }

    private void updateLanguage() {
        LocaleHelper.setLocale(this, LocaleHelper.getSelectedLanguage(this));
        recreate();
    }

    private void updateTheme() {
        String theme = LocaleHelper.getTheme(this);
        if ("Light".equals(theme)) {
            setTheme(R.style.AppTheme_Light);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if ("Dark".equals(theme)) {
            setTheme(R.style.AppTheme_Dark);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            setTheme(R.style.AppTheme_Default);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
