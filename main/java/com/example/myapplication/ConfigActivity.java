package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
public class ConfigActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent intent=getIntent();
        final String id = intent.getStringExtra("id");
        String user_name = intent.getStringExtra("user_name");

        final EditText edit_text_1 = (EditText) findViewById(R.id.edit_text_1);
        edit_text_1.setText(user_name);

        final Context context = this;

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_user_name = edit_text_1.getText().toString();
                EditText edit_text_2 =(EditText)findViewById(R.id.edit_text_2);
                String full_name = edit_text_2.getText().toString();
                Spinner spinner_1 = findViewById(R.id.spinner_1);
                String interest = spinner_1.getSelectedItem().toString();
                Spinner spinner_2 = findViewById(R.id.spinner_2);
                String frequency = spinner_2.getSelectedItem().toString();

                if (new_user_name != null && new_user_name.length() != 0) {
                    new ConnectConfig(context).execute("config", id, new_user_name, full_name, interest, frequency);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setMessage("Empty name.")
                            .create();
                    alertDialog.show();
                }
            }
        });
    }
}
