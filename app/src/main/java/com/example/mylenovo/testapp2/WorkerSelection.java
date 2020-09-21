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
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkerSelection extends AppCompatActivity {

    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    NavigationView nav;
    DatabaseReference databaseReference;
    ListView listView;
    List<Stuff_Info> stuff_infoList;
    CustomWorkerAdapter customWorkerAdapter;
    SearchView searchView;
    //Stuff_Info stuff_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_selection);

        final String sell = getIntent().getStringExtra("SELL");
        System.out.println("Worker Selection: "+sell);

        listView = findViewById(R.id.workers);
        searchView = findViewById(R.id.search_worker);

        stuff_infoList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Stuff");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stuff_infoList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Stuff_Info stuff_info = dataSnapshot1.getValue(Stuff_Info.class);
                    stuff_infoList.add(stuff_info);
                }
                customWorkerAdapter = new CustomWorkerAdapter(stuff_infoList,WorkerSelection.this, sell);
                listView.setAdapter(customWorkerAdapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        customWorkerAdapter.getFilter().filter(s);
                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar2);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }

        toolbar.setTitle("Workers Selection");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#000000"));
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();*/

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
