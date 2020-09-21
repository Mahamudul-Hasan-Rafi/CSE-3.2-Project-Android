package com.example.mylenovo.testapp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    NavigationView nav;
    CardView waste_manage, staff_manage, transactions, recycling_manage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        waste_manage = findViewById(R.id.waste_manage);
        staff_manage = findViewById(R.id.staff_manage);
        transactions = findViewById(R.id.show_transactions);
        recycling_manage = findViewById(R.id.recycling_manage);

        waste_manage.setOnClickListener(this);
        staff_manage.setOnClickListener(this);
        transactions.setOnClickListener(this);
        recycling_manage.setOnClickListener(this);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar1);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentLight));
        }

        toolbar.setTitle("Admin");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#000000"));
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        nav = findViewById(R.id.nav1);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Admin_Home:{
                        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;
                    }

                    case R.id.WasteManager:{
                       Intent i = new Intent(getApplicationContext(), WasteProduct_Manager.class);
                       startActivity(i);
                       finish();

                       break;
                    }
                    case R.id.StaffManager:{
                        Intent i = new Intent(getApplicationContext(), Staff_Manager.class);
                        startActivity(i);
                        finish();

                        break;
                    }
                    case R.id.RecyclingTips:{
                        Intent i = new Intent(getApplicationContext(), RecyclingTips_Manager.class);
                        startActivity(i);
                        finish();

                        break;
                    }
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);

            builder.setMessage("Are you sure want to exit?").setCancelable(false).
                    setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancel();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog a = builder.create();
            a.show();
        }
    }

    public void cancel(){
        super.onBackPressed();
    }
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.waste_manage:
                i=new Intent(this, WasteProduct_Manager.class);
                startActivity(i);
                finish();
                break;
            case R.id.staff_manage:
                i=new Intent(this, Staff_Manager.class);
                startActivity(i);
                finish();
                break;
            case R.id.recycling_manage:
                i=new Intent(this, RecyclingTips_Manager.class);
                startActivity(i);
                finish();
                break;
            case R.id.show_transactions:
                Uri uri = Uri.parse("https://console.firebase.google.com/u/0/project/testapp2-5f5b1/database/testapp2-5f5b1/data");
                i=new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                break;
        }
    }

}
