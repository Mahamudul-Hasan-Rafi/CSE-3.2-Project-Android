package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Staff_Manager extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    NavigationView nav;
    TextInputLayout stuff_name, stuff_mail, stuff_phone, stuff_type, stuff_area;
    Button add_button, update_button;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Stuff_Info> stuff_infoList;
    CustomAdapter_Staff customAdapter_staff;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff__manager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Stuff");

        listView = findViewById(R.id.stuff_list);

        stuff_infoList = new ArrayList<>();

        customAdapter_staff = new CustomAdapter_Staff(this, stuff_infoList);

        floatingActionButton = findViewById(R.id.add_product);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Staff_Manager.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Staff_Manager.this);
                builder.setMessage("Do you want to add a new product?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showAddDialog(Staff_Manager.this);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar2);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentLight));
        }

        toolbar.setTitle("Staff Manager");
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
                switch (menuItem.getItemId()) {
                    case R.id.Admin_Home: {
                        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    }

                    case R.id.WasteManager: {
                        Intent i = new Intent(getApplicationContext(), WasteProduct_Manager.class);
                        startActivity(i);
                        finish();

                        break;
                    }

                    case R.id.StaffManager: {
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }

                        break;
                    }

                    case R.id.RecyclingTips: {
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

    public void showAddDialog(Activity activity){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_stuff);

        int width=(int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height=(int)(activity.getResources().getDisplayMetrics().widthPixels*1.5);

        dialog.getWindow().setLayout(width, height);

        dialog.show();

        stuff_name = dialog.findViewById(R.id.stuff_name);
        stuff_mail = dialog.findViewById(R.id.stuff_email);
        stuff_phone = dialog.findViewById(R.id.stuff_phone);
        stuff_area = dialog.findViewById(R.id.stuff_area);
        add_button = dialog.findViewById(R.id.add_stuff);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = stuff_name.getEditText().getText().toString().trim();
                String mail = stuff_mail.getEditText().getText().toString().trim();
                String contact = stuff_phone.getEditText().getText().toString().trim();
                String area = stuff_area.getEditText().getText().toString().trim();

                if(area.isEmpty()){
                    Toast.makeText(Staff_Manager.this, "Stuff Coverage Area can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {

                    Stuff_Info stuffInfo = new Stuff_Info(name, mail, contact, area);

                    databaseReference.child(name).setValue(stuffInfo);

                    Toast.makeText(Staff_Manager.this, "Added Succesfully", Toast.LENGTH_SHORT).show();

                    dialog.cancel();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(i);
        finish();
    }

    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stuff_infoList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Stuff_Info s = dataSnapshot1.getValue(Stuff_Info.class);
                    stuff_infoList.add(s);
                }
                listView.setAdapter(customAdapter_staff);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }


}
