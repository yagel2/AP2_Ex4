package com.example.ap2_ex4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ap2_ex4.api.UserAPI;

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

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            UserAPI.getInstance().setToken(null);
            Intent intent = new Intent(Settings.this, Connection.class);
            startActivity(intent);
        });
    }

    private void updateLanguage() {
        LocaleHelper.setLocale(this, LocaleHelper.getSelectedLanguage(this));
        recreate();
    }
}
