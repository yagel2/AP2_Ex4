package com.example.ap2_ex4;

import android.os.Bundle;
import android.widget.Switch;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(this, LocaleHelper.getSelectedLanguage(this));
        setContentView(R.layout.settings);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchLanguage = findViewById(R.id.switchLanguage);
        switchLanguage.setChecked(LocaleHelper.getSelectedLanguage(this).equals("he"));
        switchLanguage.setText(LocaleHelper.getSelectedLanguage(
                this).equals("he") ? R.string.Hebrew : R.string.English);
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
    }

    private void updateLanguage() {
        LocaleHelper.setLocale(this, LocaleHelper.getSelectedLanguage(this));
        recreate();
    }
}
