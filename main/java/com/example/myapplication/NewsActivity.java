package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent intent=getIntent();
        String interest = intent.getStringExtra("interest");

        Context context = this;
        TextView news = (TextView) findViewById(R.id.news);

        new ConnectNews(context, news).execute("news", interest);
    }
}
