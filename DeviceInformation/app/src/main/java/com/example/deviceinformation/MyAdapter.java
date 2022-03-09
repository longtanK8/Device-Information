package com.example.deviceinformation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        Context c;
        ArrayList<Model> models;

    public MyAdapter(Context ctx, ArrayList<Model> models) {
        this.c = ctx;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.nameTxt.setText(models.get(position).getName());
        holder.img.setImageResource(models.get(position).getImg());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                if(models.get(pos).getName().equals("General")){
                    Intent intent = new Intent(c, GeneralActivity.class);
                    c.startActivity(intent);
                }
                else if(models.get(pos).getName().equals("Device ID")){
                    Intent intent = new Intent(c, DeviceIDActivity.class);
                    c.startActivity(intent);
                }
                else if(models.get(pos).getName().equals("Display")){
                    Intent intent = new Intent(c, DisplayActivity.class);
                    c.startActivity(intent);
                }
                else if(models.get(pos).getName().equals("Battery")){
                    Intent intent = new Intent(c, BatteryActivity.class);
                    c.startActivity(intent);
                }
                else if(models.get(pos).getName().equals("User Apps")){
                    Intent intent = new Intent(c, UserAppsActivity.class);
                    c.startActivity(intent);                }
                else if(models.get(pos).getName().equals("System Apps")){
                    Intent intent = new Intent(c, SystemAppsActivity.class);
                    c.startActivity(intent);                }
                else if(models.get(pos).getName().equals("Memory")){
                    Intent intent = new Intent(c, MemoryActivity.class);
                    c.startActivity(intent);                }
                else if(models.get(pos).getName().equals("CPU")){
                    Intent intent = new Intent(c, CpuActivity.class);
                    c.startActivity(intent);                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


}
