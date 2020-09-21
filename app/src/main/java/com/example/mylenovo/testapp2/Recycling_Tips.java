package com.example.mylenovo.testapp2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Recycling_Tips extends AppCompatActivity {

    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    NavigationView nav;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Upload> uploads;
    DatabaseReference databaseReference;
    MyAdapter_User myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling__tips);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar2);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progress_circular);

        uploads = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uploads.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Upload upload = dataSnapshot1.getValue(Upload.class);
                    uploads.add(upload);
                }
                myAdapter = new MyAdapter_User(Recycling_Tips.this, uploads);
                recyclerView.setAdapter(myAdapter);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }

        toolbar.setTitle("Recycling of Waste Products");
        toolbar.setTitleTextColor(Color.parseColor("#000000"));

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

                        AlertDialog.Builder builder= new AlertDialog.Builder(Selling_Transaction.this);

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
                                        Intent startMain = new Intent(Selling_Transaction.this, MainActivity.class);
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
                        Intent i = new Intent(getApplicationContext(), Purchasing_Transaction.class);
                        startActivity(i);
                        finish();
                        /*if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }*/
                        break;
                    }
                    case R.id.RecyclingTips:{
                        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;
                    }

                }
                return true;
            }
        });
    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
        finish();
    }
}
