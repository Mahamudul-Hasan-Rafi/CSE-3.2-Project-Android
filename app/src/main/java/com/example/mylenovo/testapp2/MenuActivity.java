package com.example.mylenovo.testapp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.collection.LLRBNode;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    NavigationView nav;
    CardView my_profile, recycling_product, selling, purchase;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Context context=this;

        my_profile = findViewById(R.id.profile_menu);
        recycling_product = findViewById(R.id.recycle_menu);
        selling = findViewById(R.id.sell_menu);
        purchase = findViewById(R.id.purchase_menu);

        my_profile.setOnClickListener(this);
        recycling_product.setOnClickListener(this);
        selling.setOnClickListener(this);
        purchase.setOnClickListener(this);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }

        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                                        R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        nav = findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:{
                        //Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                        //startActivity(i);
                        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;
                    }

                    case R.id.profile:{
                        Intent i = new Intent(getApplicationContext(), MyProfile.class);
                        startActivity(i);
                        finish();
                        /*if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }*/
                        break;
                    }
                    case R.id.logout:{
                        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }

                        AlertDialog.Builder builder= new AlertDialog.Builder(MenuActivity.this);

                        builder.setMessage("Are you sure want to Log Out?").setCancelable(false).
                                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sharedPreferences = context.getSharedPreferences("AppData", Context.MODE_PRIVATE);
                                        sharedPreferences.edit().remove("Phone").commit();
                                        sharedPreferences.edit().remove("Password").commit();

                                        Intent startMain = new Intent(MenuActivity.this, MainActivity.class);
                                        startActivity(startMain);
                                        finish();

                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog a = builder.create();
                        a.show();
                        break;
                    }
                    case R.id.selling_transaction:{
                        Intent i = new Intent(getApplicationContext(), Selling_Transaction.class);
                        startActivity(i);
                        finish();
                        /*if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }*/
                        break;
                    }
                    case R.id.purchasing_transaction:{
                        Intent i = new Intent(getApplicationContext(), Purchasing_Transaction.class);
                        startActivity(i);
                        finish();
                        /*if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }*/
                        break;
                    }
                    case R.id.RecyclingTips:{
                        Intent i = new Intent(getApplicationContext(), Recycling_Tips.class);
                        startActivity(i);
                        finish();
                        /*if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }*/
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
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startMain.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(startMain);
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.profile_menu:
                    i=new Intent(this, MyProfile.class);
                    startActivity(i);
                    break;
            case R.id.sell_menu:
                i=new Intent(this, Sell_Menu.class);
                i.putExtra("SELL", "TRUE");
                startActivity(i);
                break;
            case R.id.purchase_menu:
                i=new Intent(this, Sell_Menu.class);
                i.putExtra("SELL", "FALSE");
                startActivity(i);
                break;
            case R.id.recycle_menu:
                i=new Intent(this, Recycling_Tips.class);
                startActivity(i);
                break;

        }
    }
}
