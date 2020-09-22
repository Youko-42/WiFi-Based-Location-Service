package com.example.myapplication;


import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ConnectFriends  extends AsyncTask<String, Void, String> {
    private TextView friends;

    public ConnectFriends(TextView friends) {
        this.friends = friends;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String method = (String)params[0];
            String part_name = (String)params[1];
            String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
            data = data + "&" + URLEncoder.encode("part_name", "UTF-8") + "=" + URLEncoder.encode(part_name, "UTF-8");

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
        result = result.replace("@@@", "\r\n\r\n");
        friends.setText(result);
    }
}
