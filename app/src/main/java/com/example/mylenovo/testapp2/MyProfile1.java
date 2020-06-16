package com.example.mylenovo.testapp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

public class MyProfile1 extends AppCompatActivity implements View.OnClickListener {

   Toolbar toolbar;
   DrawerLayout drawerLayout;
   CardView personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile1);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }

        personal = (CardView)findViewById(R.id.personal);
        personal.setOnClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_profile);

        toolbar = findViewById(R.id.toolbar_profile);
        toolbar.setTitle("My Profile");
        toolbar.setTitleTextColor(Color.parseColor("#000000"));

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.getDrawerArrowDrawable().setColor(this.getResources().getColor(R.color.colorPrimaryDark));
        drawerToggle.syncState();
    }

    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal:{
                startActivity(new Intent(this, MyProfile.class));
                break;
            }
        }

    }
}
