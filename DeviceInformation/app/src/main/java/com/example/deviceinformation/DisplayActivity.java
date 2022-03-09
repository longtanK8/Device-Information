package com.example.deviceinformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;

public class DisplayActivity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Display");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        String resolution = width+"x"+ height+" pixel";
        double x = Math.pow(width/dm.xdpi, 2);
        double y = Math.pow(height/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        NumberFormat form = NumberFormat.getNumberInstance();
        form.setMinimumFractionDigits(2);
        form.setMaximumFractionDigits(2);
        String screenInchesOutput = form.format(screenInches);

        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshRating = display.getRefreshRate();
        NumberFormat form1 = NumberFormat.getNumberInstance();
        form1.setMinimumFractionDigits(1);
        form1.setMaximumFractionDigits(1);
        String outputRefreshRating = form1.format(refreshRating);

        titles = new String[]{"Resolution","Density","Physical Size", "Refresh Rate","Orientation"};
        descriptions = new String[]{resolution, DisplayMetrics.DENSITY_XHIGH+" dpi (xhdpi)", screenInchesOutput+ " inch",outputRefreshRating+" Hz", this.getResources().getConfiguration().orientation+""};

        ListView list = findViewById(R.id.displayList);

        MyAdapter adapter = new MyAdapter(this, titles, descriptions);
        list.setAdapter(adapter);
    }

    private class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        String myDescriptions[];

        MyAdapter(Context c, String[] titles, String[] descriptions){
            super(c, R.layout.towrow, R.id.title, titles );
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.towrow, parent,false);
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
}