package com.example.deviceinformation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class CpuActivity extends AppCompatActivity {

    ProcessBuilder mProcessBuilder ;
    String holder ="";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream mInputStream;
    Process mProcess;
    byte[] mByteArray;
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("CPU");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mListView = findViewById(R.id.cpuListView);
        mByteArray = new byte[1024];
        try{
            mProcessBuilder = new ProcessBuilder(DATA);
            mProcess = mProcessBuilder.start();
            mInputStream = mProcess.getInputStream();
            while(mInputStream.read(mByteArray) != -1){
                holder = holder + new String(mByteArray);
            }
            mInputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                Collections.singletonList(holder));
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}