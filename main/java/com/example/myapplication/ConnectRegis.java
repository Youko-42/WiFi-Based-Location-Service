package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ConnectRegis  extends AsyncTask<String, Void, String> {
    private Context context;

    public ConnectRegis(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String method = (String)params[0];
            String user_name = (String)params[1];
            String pass = (String)params[2];
            String device_mac = getDeviceMac(context);
            String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
            data = data + "&" + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
            data = data + "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
            data = data + "&" + URLEncoder.encode("device_mac", "UTF-8") + "=" + URLEncoder.encode(device_mac, "UTF-8");

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
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage("Registration Success.")
                .create();
        alertDialog.show();

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private String getDeviceMac(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

        return address;
    }
}
