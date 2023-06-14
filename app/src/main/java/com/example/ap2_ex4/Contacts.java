package com.example.ap2_ex4;

import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.ImageButton;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contacts extends AppCompatActivity {
    private LinearLayout namesContainer;

    private String currentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLanguage = LocaleHelper.getSelectedLanguage(this);
        LocaleHelper.setLocale(this, currentLanguage);
        setContentView(R.layout.contacts);

        namesContainer = findViewById(R.id.names_container);

        ImageButton settingsButton = findViewById(R.id.right_icon);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Contacts.this, Settings.class);
            startActivity(intent);
        });

        RelativeLayout contactDetails = findViewById(R.id.contact_details);
        contactDetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Contacts.this, ChatScreen.class);
                startActivity(intent);
            }
        });



        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
            builder.setTitle("Add a name");
            final EditText input = new EditText(Contacts.this);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String name = input.getText().toString();
                if (name.isEmpty()) {
                    return;
                }
                addNameToList(name);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

    private void addNameToList(String name) {
        View nameItemView = LayoutInflater.from(this).inflate(
                R.layout.name_item, namesContainer, false);
        TextView textView = nameItemView.findViewById(R.id.name_contact);
        textView.setText(name);
        namesContainer.addView(nameItemView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentLanguage.equals(LocaleHelper.getSelectedLanguage(this))) {
            recreate();
        }
    }

}