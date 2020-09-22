package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ConnectMain extends AsyncTask<String, Void, String> {
    private Context context;
    private EditText name;
    private EditText password;

    public ConnectMain(Context context, EditText name, EditText password) {
        this.context = context;
        this.name = name;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String method = (String)params[0];
            String user_name = (String)params[1];
            String password = (String)params[2];
            String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
            data = data + "&" + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
            data = data + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
        if (result.equals("Failed")) {
            name.setText("");
            password.setText("");

            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setMessage("User name not found or incorrect password.")
                    .create();
            alertDialog.show();
        } else {
            Intent intent = new Intent(context, SectionActivity.class);
            intent.putExtra("result",result);
            context.startActivity(intent);
        }
    }
}
