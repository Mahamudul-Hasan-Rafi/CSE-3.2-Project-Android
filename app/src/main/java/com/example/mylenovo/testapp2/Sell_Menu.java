package com.example.mylenovo.testapp2;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Sell_Menu extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<ProductInfo> productInfoList;
    CustomAdapter_User_Product customAdapter_product;
    ListView listView;
    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    NavigationView nav;
    SearchView searchView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell__menu);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Product");

        final String sell = getIntent().getStringExtra("SELL");
        System.out.println("SELLLLL  MENU:  "+sell);

        productInfoList = new ArrayList<>();
        customAdapter_product = new CustomAdapter_User_Product(this, productInfoList, sell);

        listView = findViewById(R.id.waste_products);
        searchView = findViewById(R.id.search_product);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar2);

        floatingActionButton = findViewById(R.id.next);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sell_Menu.this, MyItemsList.class);
                intent.putExtra("SELL", sell);
                startActivity(intent);
            }
        });


        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }

        toolbar.setTitle("Waste Products");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
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

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        customAdapter_product.getFilter().filter(s);
                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }

}

