package com.example.deviceinformation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.File;
import java.text.NumberFormat;

public class MemoryActivity extends AppCompatActivity {

    TextView mTvTotalRam, mTvFreeRam, mTvUsedRam, mTvTotalRom, mTvFreeRom, mTvUsedRom, mTvTotalHeap,
    mTvPercRam, mTvPercRom;
    ProgressBar mPBRam, mPBRom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Memory");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mTvTotalRam = findViewById(R.id.totalram);
        mTvFreeRam = findViewById(R.id.freeram);
        mTvUsedRam = findViewById(R.id.usedram);
        mTvTotalRom = findViewById(R.id.totalrom);
        mTvFreeRom = findViewById(R.id.freerom);
        mTvUsedRom = findViewById(R.id.usedrom);
        mTvTotalHeap = findViewById(R.id.totalHeap);
        mPBRam = findViewById(R.id.pbRam);
        mPBRom = findViewById(R.id.pbRom);
        mTvPercRam = findViewById(R.id.tv_perc_ram);
        mTvPercRom = findViewById(R.id.tv_perc_rom);

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        float totalMem = memoryInfo.totalMem/(1024*1024);
        float freeMem = memoryInfo.availMem/(1024*1024);
        float usedMem = totalMem - freeMem;
        float freeMemPerc = freeMem/totalMem*100;
        float usedMemPerc = usedMem/totalMem*100;

        NumberFormat numFormFreePerc = NumberFormat.getNumberInstance();
        numFormFreePerc.setMinimumFractionDigits(1);
        numFormFreePerc.setMaximumFractionDigits(1);
        String mFreeMemPerc = numFormFreePerc.format(freeMemPerc);

        NumberFormat numFormUsedPerc = NumberFormat.getNumberInstance();
        numFormUsedPerc.setMinimumFractionDigits(1);
        numFormUsedPerc.setMaximumFractionDigits(1);
        String mUsedMemPerc = numFormUsedPerc.format(usedMemPerc);

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSize();
        float totalBlocks = stat.getBlockCount();
        float availableBlocks = stat.getAvailableBlocks();
        float totalROM = (totalBlocks*blockSize)/(1024*1024);
        float freeROM = (availableBlocks*blockSize)/(1024*1024);
        float usedROM = totalROM - freeROM;
        float freeRomPerc = (freeROM/totalROM)*100;
        float usedRomPerc = (usedROM/totalROM)*100;

        NumberFormat numFormatTotalRom = NumberFormat.getNumberInstance();
        numFormatTotalRom.setMinimumFractionDigits(1);
        numFormatTotalRom.setMaximumFractionDigits(1);
        String mTotalRom = numFormUsedPerc.format(totalROM);

        NumberFormat numFormatFreeRom = NumberFormat.getNumberInstance();
        numFormatFreeRom.setMinimumFractionDigits(1);
        numFormatFreeRom.setMaximumFractionDigits(1);
        String mFreeRom = numFormUsedPerc.format(freeROM);

        NumberFormat numFormatUsedRom = NumberFormat.getNumberInstance();
        numFormatUsedRom.setMinimumFractionDigits(1);
        numFormatUsedRom.setMaximumFractionDigits(1);
        String mUsedRom = numFormUsedPerc.format(usedROM);

        NumberFormat numFormatFreeRomPerc = NumberFormat.getNumberInstance();
        numFormatFreeRomPerc.setMinimumFractionDigits(1);
        numFormatFreeRomPerc.setMaximumFractionDigits(1);
        String mFreeRomPerc = numFormatFreeRomPerc.format(freeRomPerc);

        NumberFormat numFormatUsedRomPerc = NumberFormat.getNumberInstance();
        numFormatUsedRomPerc.setMinimumFractionDigits(1);
        numFormatUsedRomPerc.setMaximumFractionDigits(1);
        String mUsedRomPerc = numFormatUsedRomPerc.format(usedRomPerc);

        mTvTotalRam.setText(" "+totalMem+ "MB");
        mTvFreeRam.setText(" "+freeMem + "MB"+"("+mFreeMemPerc+"%)");
        mTvUsedRam.setText(" "+usedMem + "MB"+"("+mUsedMemPerc+"%)");

        mTvTotalRom.setText(" "+mTotalRom+ "MB");
        mTvFreeRom.setText(" "+mFreeRom + "MB"+"("+mFreeRomPerc+"%)");
        mTvUsedRom.setText(" "+mUsedRom + "MB"+"("+mUsedRomPerc+"%)");

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        mTvTotalHeap.setText(" "+maxMemory/(1024*1024)+"MB");

        mTvPercRam.setText(mUsedMemPerc+"% Used");
        mPBRam.setProgress((int)usedMemPerc);

        mTvPercRom.setText(mUsedRomPerc+"% Used");
        mPBRom.setProgress((int)((usedROM/totalROM)*100));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}