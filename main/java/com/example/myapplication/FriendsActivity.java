package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FriendsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Button friend_search = (Button) findViewById(R.id.friend_search);
        friend_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText friend_name =(EditText)findViewById(R.id.friend_name);
                String part_name = friend_name.getText().toString();
                TextView friends = (TextView) findViewById(R.id.friends);

                new ConnectFriends(friends).execute("friend", part_name);
            }
        });
    }

}
