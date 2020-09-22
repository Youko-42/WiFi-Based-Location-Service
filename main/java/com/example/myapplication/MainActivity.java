package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name =(EditText)findViewById(R.id.name);
                EditText password =(EditText)findViewById(R.id.password);
                String user_name = name.getText().toString();
                String pass = password.getText().toString();

                new ConnectMain(context, name, password).execute("login", user_name, pass);
            }
        });

        TextView register = findViewById(R.id.register);
        SpannableString style = new SpannableString("Register?");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        };
        style.setSpan(clickableSpan, 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        register.setText(style);
        register.setMovementMethod(LinkMovementMethod.getInstance());
    }
}