package com.example.deviceinformation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserAppsActivity extends AppCompatActivity {

    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;

    ListView userInstalledAppLV;

    List<PackageInfo> packs;
    List<AppList> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_apps);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("User Apps");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        userInstalledAppLV = findViewById(R.id.installed_app_list);
        installedApps = getInstalledApps();
        installedAppAdapter = new AppAdapter(UserAppsActivity.this, installedApps);
        userInstalledAppLV.setAdapter(installedAppAdapter);
        userInstalledAppLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String[] options = {"Open App","App Info","Uninstall"};
                AlertDialog.Builder builder = new AlertDialog.Builder(UserAppsActivity.this);
                builder.setTitle("Choose Action");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packages);
                            if(intent != null){
                                startActivity(intent);
                            }else{
                                Toast.makeText(UserAppsActivity.this, "Error, Please try again!", Toast.LENGTH_SHORT).show();
                            }
                        }if(which == 1){
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:"+ installedApps.get(i).packages));
                            Toast.makeText(UserAppsActivity.this, installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }if(which == 2){
                            String packages = installedApps.get(i).packages;
                            Intent intent = new Intent(Intent.ACTION_DELETE);
                            intent.setData(Uri.parse("package:"+packages));
                            startActivity(intent);
                            recreate();
                        }
                    }
                });
                builder.show();
            }
        });
        String size = userInstalledAppLV.getCount()+"";
        TextView countApps = findViewById(R.id.countApps);
        countApps.setText("Total Installed Apps:"+ size);

    }

    public class AppList{
        String name;
        Drawable icon;
        String packages;
        String version;

        AppList(String name, Drawable icon, String packages, String version){
            this.name = name;
            this.icon = icon;
            this.packages = packages;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackages() {
            return packages;
        }

        public String getVersion() {
            return version;
        }
    }

    public class AppAdapter extends BaseAdapter{
        LayoutInflater layoutInflater;
        List<AppList> listStorage;

        AppAdapter(Context context, List<AppList> customizedListView){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder listViewHolder;
            if(convertView == null){
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.modelapps,parent,false);
                listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView = convertView.findViewById(R.id.app_package);
                listViewHolder.versionInListView = convertView.findViewById(R.id.version);

                convertView.setTag(listViewHolder);

            }else{
                listViewHolder = (ViewHolder) convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());
            listViewHolder.versionInListView.setText(listStorage.get(position).getVersion());

            return convertView;
        }
        class ViewHolder{
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
            TextView versionInListView;
        }

    }

    private List<AppList> getInstalledApps(){
        apps = new ArrayList<AppList>();
        packs = getPackageManager().getInstalledPackages(0);
        for(int i = 0; i < packs.size(); i++){
            PackageInfo p = packs.get(i);
            if((!isSystemPackage(p))){
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String packages = p.applicationInfo.packageName;
                String version = p.versionName;
                apps.add(new AppList(appName, icon, packages, version));
            }
        }
        return apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo){
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}