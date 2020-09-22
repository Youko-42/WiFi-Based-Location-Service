package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Context context = this;

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name =(EditText)findViewById(R.id.name);
                EditText password =(EditText)findViewById(R.id.password);
                EditText password_2 =(EditText)findViewById(R.id.password_2);
                String user_name = name.getText().toString();
                String pass = password.getText().toString();
                String pass_2 = password_2.getText().toString();

                if (pass.equals(pass_2) && notNull(user_name) && notNull(pass)) {
                    new ConnectRegis(context).execute("regis", user_name, pass);
                } else if (!pass.equals(pass_2)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setMessage("Password and confirm password don't match.")
                            .create();
                    alertDialog.show();

                    password_2.setText("");
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setMessage("Username and password cannot be empty.")
                            .create();
                    alertDialog.show();
                }
            }
        });
    }

    private boolean notNull(String text) {
        if (text != null && text.length() != 0) {
            return true;
        } else {
            return false;
        }
    }
}
