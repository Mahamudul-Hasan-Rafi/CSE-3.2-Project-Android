 package com.example.mylenovo.testapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

 public class MyItemsList extends AppCompatActivity {

    ListView my_list;
    Mylist_Adapter mylistAdapter;
    List<ProductInfo> productInfoList;
    DrawerLayout drawerLayout;
    NavigationView nav;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences, sharedPreferences1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_items_list);

        final String sell = getIntent().getStringExtra("SELL");
        System.out.println("MyListItems:    "+sell);

        databaseReference = FirebaseDatabase.getInstance().getReference("UserRequests");

        my_list = findViewById(R.id.waste_products);

        productInfoList = new ArrayList<>();

        if(sell.equals("TRUE")){
            ProductDB productDB = new ProductDB(this);
            Cursor cursor = productDB.readAll();

            while(cursor.moveToNext()){
                int columnIndex1=cursor.getColumnIndex("Product_Name");
                int columnIndex2=cursor.getColumnIndex("Product_Type");
                int columnIndex3=cursor.getColumnIndex("Price_Unit");

                String name = cursor.getString(columnIndex1);
                String type = cursor.getString(columnIndex2);
                String p_u = cursor.getString(columnIndex3);

                productInfoList.add(new ProductInfo(name, type, p_u));
            }

        }
        else{
            ProductDB_Purchase productDB_purchase = new ProductDB_Purchase(this);
            Cursor cursor = productDB_purchase.readAll();

            while(cursor.moveToNext()){
                int columnIndex1=cursor.getColumnIndex("Product_Name");
                int columnIndex2=cursor.getColumnIndex("Product_Type");
                int columnIndex3=cursor.getColumnIndex("Price_Unit");

                String name = cursor.getString(columnIndex1);
                String type = cursor.getString(columnIndex2);
                String p_u = cursor.getString(columnIndex3);

                productInfoList.add(new ProductInfo(name, type, p_u));
            }
        }

        mylistAdapter = new Mylist_Adapter(this, productInfoList, sell);
        my_list.setAdapter(mylistAdapter);

        floatingActionButton = findViewById(R.id.next);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Key = databaseReference.push().getKey();
                sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

                sharedPreferences1 = getSharedPreferences("UserKey",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("Key", Key);
                editor.commit();

                for(int i=0; i<productInfoList.size(); i++){
                    databaseReference.child(Key).child("Product").child(productInfoList.get(i).getP_name()).setValue(productInfoList.get(i));
                }
                databaseReference.child(Key).child("User").child("Name").setValue(sharedPreferences.getString("Name","N/A"));
                databaseReference.child(Key).child("User").child("ContactNo").setValue(sharedPreferences.getString("ContactNo.","N/A"));
                databaseReference.child(Key).child("User").child("Email").setValue(sharedPreferences.getString("Email","N/A"));
                databaseReference.child(Key).child("User").child("Address").setValue(sharedPreferences.getString("Address","N/A"));

                Toast.makeText(MyItemsList.this, "Succesfully Sent Request!!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MyItemsList.this, WorkerSelection.class);
                intent.putExtra("SELL",sell);
                startActivity(intent);
            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar2);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }

        toolbar.setTitle("My List of Items");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
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

     public void onBackPressed() {
         super.onBackPressed();
         //finish();
     }

}
