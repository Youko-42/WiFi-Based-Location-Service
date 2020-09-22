package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ConnectConfig extends AsyncTask<String, Void, String> {
    private Context context;

    public ConnectConfig(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String method = (String)params[0];
            String id = (String)params[1];
            String user_name = (String)params[2];
            String full_name = (String)params[3];
            String interest = (String)params[4];
            String frequency = (String)params[5];

            String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
            data = data + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data = data + "&" + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
            data = data + "&" + URLEncoder.encode("full_name", "UTF-8") + "=" + URLEncoder.encode(full_name, "UTF-8");
            data = data + "&" + URLEncoder.encode("interest", "UTF-8") + "=" + URLEncoder.encode(interest, "UTF-8");
            data = data + "&" + URLEncoder.encode("frequency", "UTF-8") + "=" + URLEncoder.encode(frequency, "UTF-8");

            String link = "http://192.168.0.3:80/demo/index.php";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, SectionActivity.class);
        intent.putExtra("result",result);
        context.startActivity(intent);
    }
}
