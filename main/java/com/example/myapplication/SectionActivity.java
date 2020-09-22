package com.example.myapplication;

import java.util.Timer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SectionActivity extends AppCompatActivity {
    final Context context = this;
    String id = "";
    String fre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        final String[] result_list = result.split(";");

        TextView information = (TextView) findViewById(R.id.information);
        String show_info = "id: " + result_list[0] + "\r\n";
        show_info = show_info + "user_name: " + result_list[1] + "\r\n";
        show_info = show_info + "full_name: " + result_list[2] + "\r\n";
        show_info = show_info + "interest: " + result_list[3] + "\r\n";
        show_info = show_info + "update_frequency: " + result_list[4] + "\r\n\r\n";
        information.setText(show_info);

        Button get_news = (Button) findViewById(R.id.get_news);
        get_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectionActivity.this, NewsActivity.class);
                intent.putExtra("interest", result_list[3]);
                startActivity(intent);
            }
        });

        Button locate_friends = (Button) findViewById(R.id.locate_friends);
        locate_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectionActivity.this, FriendsActivity.class);
                startActivity(intent);
            }
        });

        Button configuration = (Button) findViewById(R.id.configuration);
        configuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectionActivity.this, ConfigActivity.class);
                intent.putExtra("id", result_list[0]);
                intent.putExtra("user_name", result_list[1]);
                startActivity(intent);
            }
        });

        id = result_list[0];
        fre = result_list[4];
        int n = 10000;
        if (fre.equals("10") || fre.equals("10 Seconds")) {
            n = 10000;
        } else if (fre.equals("1 minute")) {
            n = 60000;
        } else if (fre.equals("15 minutes")) {
            n = 900000;
        } else if (fre.equals("30 minutes")) {
            n = 1800000;
        } else if (fre.equals("60 minutes")) {
            n = 3600000;
        }

        MyTimerTask myTask = new MyTimerTask(context, id);
        Timer myTimer = new Timer();
        myTimer.schedule(myTask, n);
    }
}