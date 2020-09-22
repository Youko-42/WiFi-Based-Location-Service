package com.example.myapplication;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import static android.content.Context.WIFI_SERVICE;

public class MyTimerTask extends TimerTask {
    private Context context;
    String id;

    public MyTimerTask (Context context, String id) {
        this.context = context;
        this.id = id;
    }

    public void run() {
        new MyAsyncTask().execute("wifi", id);
    }

    private String getConnectedMac(Context context) {
        String connectedWifiMacAddress = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        List<ScanResult> wifiList;
        if (wifiManager != null) {
            wifiList = wifiManager.getScanResults();
            WifiInfo info = wifiManager.getConnectionInfo();
            if (wifiList != null && info != null) {
                for (int i = 0; i < wifiList.size(); i++) {
                    ScanResult result = wifiList.get(i);
                    if (info.getBSSID().equals(result.BSSID)) {
                        connectedWifiMacAddress = result.BSSID;
                    }
                }
            }
        }
        return connectedWifiMacAddress;
    }

    final class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String method = (String)params[0];
                String id = (String)params[1];

                String connected_mac = getConnectedMac(context);
                if (connected_mac == null) {
                    connected_mac = "02:00:00:00:00:00";
                }

                WifiManager wifiManager=(WifiManager) context.getSystemService(WIFI_SERVICE);
                List<ScanResult> scanResults=wifiManager.getScanResults();
                String wifi_list = " ";
                for (ScanResult scanResult : scanResults) {
                    wifi_list = wifi_list + scanResult.SSID;
                }

                String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data = data + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                data = data + "&" + URLEncoder.encode("connected_mac", "UTF-8") + "=" + URLEncoder.encode(connected_mac, "UTF-8");
                data = data + "&" + URLEncoder.encode("wifi_list", "UTF-8") + "=" + URLEncoder.encode(wifi_list, "UTF-8");

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
    }
}
