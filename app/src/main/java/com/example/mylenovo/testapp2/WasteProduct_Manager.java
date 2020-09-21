package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class WasteProduct_Manager extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    NavigationView nav;
    TextInputLayout product_name, product_type, price_unit;
    Button add_button, update_button;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<ProductInfo> productInfoList;
    CustomAdapter_Product customAdapter_product;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_product__manager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Product");

        productInfoList = new ArrayList<>();
        customAdapter_product = new CustomAdapter_Product(this, productInfoList);

        listView = findViewById(R.id.waste_products);

        //update_button = (Button) findViewById(R.id.product_delete);


        floatingActionButton = findViewById(R.id.add_product);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WasteProduct_Manager.this);
                builder.setMessage("Do you want to add a new product?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showAddDialog(WasteProduct_Manager.this);
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

        toolbar.setTitle("Waste Manager");
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
                        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    }

                    case R.id.WasteManager:{
                        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }

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

    public void showAddDialog(Activity activity){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_waste_product);

        int width=(int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height=(int)(activity.getResources().getDisplayMetrics().widthPixels*1.5);

        dialog.getWindow().setLayout(width, height);

        dialog.show();

        product_name = dialog.findViewById(R.id.product_name);
        product_type = dialog.findViewById(R.id.product_type);
        price_unit = dialog.findViewById(R.id.price_unit);
        add_button = dialog.findViewById(R.id.add_product);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = product_name.getEditText().getText().toString().trim();
                String type = product_type.getEditText().getText().toString().trim();
                String p_unit = price_unit.getEditText().getText().toString().trim();

                //Toast.makeText(WasteProduct_Manager.this, "Succesfully", Toast.LENGTH_SHORT).show();

                /*ProductDB productDB = new ProductDB(WasteProduct_Manager.this);
                int id=(int)productDB.insertData(name, type, p_unit);*/

                ProductInfo productInfo = new ProductInfo(name, type, p_unit);

                databaseReference.child(name).setValue(productInfo);

                Toast.makeText(WasteProduct_Manager.this, "Added Succesfully", Toast.LENGTH_SHORT).show();

                dialog.cancel();

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productInfoList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    ProductInfo p = dataSnapshot1.getValue(ProductInfo.class);
                    productInfoList.add(p);
                }
                listView.setAdapter(customAdapter_product);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
