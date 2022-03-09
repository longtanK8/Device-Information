package com.example.deviceinformation;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private TextView mManufacturerTv, mAndroidVersionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        mRecyclerView = findViewById(R.id.myRecycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new MyAdapter(this, getModels());
        mRecyclerView.setAdapter(mAdapter);

        String mManufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String version = Build.VERSION.RELEASE;
        String verName = Build.VERSION_CODES.class.getFields() [Build.VERSION.SDK_INT].getName();
        mManufacturerTv = findViewById(R.id.name_model);
        mAndroidVersionTv = findViewById(R.id.andro_version);
        mManufacturerTv.setText(mManufacturer.toUpperCase()+ ""+model);
        mAndroidVersionTv.setText(version+""+verName);
        try {
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN){
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1){
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2){
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
                Glide.with(this).load(R.drawable.android44).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP){
                Glide.with(this).load(R.drawable.android50).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1){
                Glide.with(this).load(R.drawable.android50).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
                Glide.with(this).load(R.drawable.android60).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.N){
                Glide.with(this).load(R.drawable.android70).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1){
                Glide.with(this).load(R.drawable.android70).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.O){
                Glide.with(this).load(R.drawable.android80).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1){
                Glide.with(this).load(R.drawable.android80).into((ImageView) findViewById(R.id.backdrop));
            }

            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.P){
                Glide.with(this).load(R.drawable.android90).into((ImageView) findViewById(R.id.backdrop));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initCollapsingToolbar(){
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toobar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        collapsingToolbarLayout.setTitle("Device Info");
    }

    private ArrayList<Model> getModels(){
        ArrayList<Model>  models = new ArrayList<>();
        Model p = new Model();

        p = new Model();
        p.setName("General");
        p.setImg(R.drawable.general);
        models.add(p);

        p = new Model();
        p.setName("Device ID");
        p.setImg(R.drawable.devid);
        models.add(p);

        p = new Model();
        p.setName("Display");
        p.setImg(R.drawable.display);
        models.add(p);

        p = new Model();
        p.setName("Battery");
        p.setImg(R.drawable.battery);
        models.add(p);

        p = new Model();
        p.setName("User Apps");
        p.setImg(R.drawable.userapps);
        models.add(p);

        p = new Model();
        p.setName("System Apps");
        p.setImg(R.drawable.systemapps);
        models.add(p);

        p = new Model();
        p.setName("Memory");
        p.setImg(R.drawable.memory);
        models.add(p);

        p = new Model();
        p.setName("CPU");
        p.setImg(R.drawable.cpu);
        models.add(p);

        return models;

    }
}