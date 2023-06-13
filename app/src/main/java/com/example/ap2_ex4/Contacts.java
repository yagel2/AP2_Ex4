package com.example.ap2_ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contacts extends AppCompatActivity {
    private LinearLayout namesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        namesContainer = findViewById(R.id.names_container);

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
                builder.setTitle("Add a name");

                final EditText input = new EditText(Contacts.this);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();
                        addNameToList(name);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    private void addNameToList(String name) {
        View nameItemView = LayoutInflater.from(this).inflate(R.layout.name_item, namesContainer, false);

        TextView textView = nameItemView.findViewById(R.id.name_contact);
        textView.setText(name);

        namesContainer.addView(nameItemView);
    }
}