package com.example.mylenovo.testapp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Purchasing_Transaction extends AppCompatActivity {

    int record_length = 0;
    TableLayout tableLayout;
    DrawerLayout drawerLayout;
    NavigationView nav;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing__transaction);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar2);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }

        toolbar.setTitle("Workers Selection");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#000000"));
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        nav = findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:{
                        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(i);
                        finish();
                        /*if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }*/
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
                    /*case R.id.logout:{
                        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }

                        AlertDialog.Builder builder= new AlertDialog.Builder(Purchasing_Transaction.this);

                        builder.setMessage("Are you sure want to Log Out?").setCancelable(false).
                                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sharedPreferences = getSharedPreferences("AppData", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.remove("Phone");
                                        editor.remove("Password");
                                        editor.clear();
                                        editor.commit();
                                        Intent startMain = new Intent(Purchasing_Transaction.this, MainActivity.class);
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
                    }*/
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
                        /*Intent i = new Intent(getApplicationContext(), Purchasing_Transaction.class);
                        startActivity(i);*/
                        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
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
        tableLayout = findViewById(R.id.table);

        Purchased_ItemsDB purchased_itemsDB = new Purchased_ItemsDB(this);
        Cursor cursor = purchased_itemsDB.readAll();

        while (cursor.moveToNext()) {
            TableRow tableRow = new TableRow(this);

            tableRow.setPadding(5, 5, 5, 5);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT);
            tableRow.setLayoutParams(layoutParams);

            TextView textView_name = new TextView(this);
            textView_name.setPadding(2, 3, 3, 2);
            textView_name.setGravity(Gravity.CENTER);
            textView_name.setText(cursor.getString(1));
            textView_name.setBackground(getResources().getDrawable(R.drawable.background_column));
            tableRow.addView(textView_name);

            TextView textView_university = new TextView(this);
            textView_university.setPadding(2, 3, 3, 2);
            textView_university.setGravity(Gravity.CENTER);
            textView_university.setText(cursor.getString(2));
            textView_university.setBackground(getResources().getDrawable(R.drawable.background_column));
            tableRow.addView(textView_university);

            TextView textView_department = new TextView(this);
            textView_department.setPadding(2, 3, 3, 2);
            textView_department.setGravity(Gravity.CENTER);
            textView_department.setText(cursor.getString(3));
            textView_department.setBackground(getResources().getDrawable(R.drawable.background_column));
            tableRow.addView(textView_department);

            tableLayout.addView(tableRow);

            record_length++;
        }
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
        finish();
    }
}
