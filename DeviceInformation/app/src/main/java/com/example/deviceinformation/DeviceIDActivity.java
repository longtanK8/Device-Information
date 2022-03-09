package com.example.deviceinformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceIDActivity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];
    private static final int READ_PHONE_STATE_PERMISSION = 1;
    private TelephonyManager tm;
    private String imei, simCardSerial, simSubscriberID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_idactivity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Device ID");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        String deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions, READ_PHONE_STATE_PERMISSION);
            }else{
                imei = tm.getDeviceId();
                simCardSerial = tm.getSimSerialNumber();
                simSubscriberID = tm.getSubscriberId();
            }
        }else{
            imei = tm.getDeviceId();
            simCardSerial = tm.getSimSerialNumber();
            simSubscriberID = tm.getSubscriberId();
        }


        String ipAddress = "No Internet Connection";
        ConnectivityManager connManager = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnable = false;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Network[] networks = connManager.getAllNetworks();
            for (Network network: networks){
                NetworkInfo info = connManager.getNetworkInfo(network);
                if(info != null){
                    if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                        ipAddress = getMobileIPAddress();
                    }
                }
            }
        }else{
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(mMobile != null){
                ipAddress = is3GEnable+"";
            }
        }


        String wifiAddress = "No WiFi Connection";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Network[] networks = connManager.getAllNetworks();
            for (Network network: networks){
                NetworkInfo info = connManager.getNetworkInfo(network);
                if(info != null){
                    if(info.getType() == ConnectivityManager.TYPE_WIFI){
                        wifiAddress = getWifiIPAddress();
                    }
                }
            }
        }
        else{
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(mMobile != null){
                ipAddress = is3GEnable+"";
            }
        }

        String bt = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");

        titles = new String[]{"Android Device ID", "IMEI, MEID or ESN","Hardware Serial Number","SIM Card Serial No","SIM Subscriber ID","IP Address","Wi-Fi Mac Address","Bluetooth Mac Address","Build Fingerprint"};
        descriptions = new String[]{deviceid, imei, Build.SERIAL, simCardSerial, simSubscriberID, ipAddress , wifiAddress, bt, Build.FINGERPRINT};


        ListView listView = findViewById(R.id.devIdList);
        MyAdapter adapter = new MyAdapter(this, titles, descriptions);
        listView.setAdapter(adapter);
    }

    private String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    private static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf: interfaces){
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for(InetAddress addr: addrs){
                    if(!addr.isLoopbackAddress()){
                        return addr.getHostAddress();
                    }
                }
            }
        }catch (Exception e){

        }
        return "";
    }

    private class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        String myDescriptions[];

        public MyAdapter(@NonNull Context c, String titles[], String descriptions[] ) {
            super(c, R.layout.towrow, R.id.title, titles );
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.towrow, parent, false);
            TextView myTitle = row.findViewById(R.id.titleTv);
            TextView myDes = row.findViewById(R.id.descTv);
            myTitle.setText(titles[position]);
            myDes.setText(descriptions[position]);
            return row;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case READ_PHONE_STATE_PERMISSION:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    recreate();
                    imei = tm.getDeviceId();
                    simCardSerial = tm.getSimSerialNumber();
                    simSubscriberID = tm.getSubscriberId();
                }else {
                    Toast.makeText(this, "Enable READ_PHONE_STATE Permission from settings", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}