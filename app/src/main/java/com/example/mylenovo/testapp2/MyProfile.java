package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyProfile extends AppCompatActivity {

    /*ListView listView1, listView2, listView3;
    MyCustomAdapter myCustomAdapter;
    MyCustomAdapter2 myCustomAdapter2;*/
    ArrayList<DataModel> dataModels;
    Toolbar toolbar;
    //ImageView imageView1, imageView2;
    Context context;
    TextView text_name, text_phone, text_house, text_road, text_block, text_area;
    TextInputLayout userName, userEmail, userPhone, userPassword;
    String username, userphone, useremail, userpass, phone_no, userhouse, userroad, userarea, usersector;
    String query, TABLE_NAME, PHONE;
    UserDB db;
    SharedPreferences sharedPreferences;
    String id, name, email, phone, password, house, street, block, area;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if(item.getItemId()== R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }

        text_name = findViewById(R.id.sample_name);
        text_phone=findViewById(R.id.sample_mobile);
        text_house=findViewById(R.id.sample_house);
        text_road=findViewById(R.id.sample_road);
        text_block=findViewById(R.id.sample_sector);
        text_area=findViewById(R.id.sample_area);

        toolbar = findViewById(R.id.toolbar_prf);
        toolbar.setTitle("My Profile");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        dataModels = new ArrayList<>();

        ImageView imageView1 = (ImageView)findViewById(R.id.img_edit1);
        ImageView imageView2 = (ImageView)findViewById(R.id.img_edit12);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                builder.setMessage("Do you want to update Contact Info?").setCancelable(true).
                        setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.cancel();
                                showUpdateDialog_contact(MyProfile.this);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alt = builder.create();
                alt.show();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                builder.setMessage("Do you want to update Contact Info?").setCancelable(true).
                        setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.cancel();
                                showUpdateDialog_address(MyProfile.this);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alt = builder.create();
                alt.show();
            }
        });

        sharedPreferences = getSharedPreferences("AppData", Context.MODE_PRIVATE);
        phone_no = sharedPreferences.getString("Phone", "DatanotFound");

        System.out.print(phone_no);
        TABLE_NAME="User_Info";
        PHONE="Phone";
        query = "SELECT * FROM "+TABLE_NAME+" WHERE "+PHONE+" = '"+phone_no+"'";

        db = new UserDB(this);
        Cursor cursor = db.readAll(query);

        while(cursor.moveToNext()){
            name=cursor.getString(1);
            phone=cursor.getString(2);
            email=cursor.getString(3);
            password=cursor.getString(4);
            house=cursor.getString(5);
            street=cursor.getString(6);
            block=cursor.getString(8);
            area=cursor.getString(7);

            dataModels.add(new DataModel(name, phone, house, street, block, area));
        }

        text_name.setText("Name::  "+dataModels.get(0).getName_user());
        text_phone.setText("Phone::  "+dataModels.get(0).getMobile_no());
        text_house.setText("House::  "+dataModels.get(0).getHouse_no());
        text_road.setText("Road::  "+dataModels.get(0).getStreet_name());
        text_block.setText("Sector/Block::  "+dataModels.get(0).getBlock());
        text_area.setText("Area::  "+dataModels.get(0).getArea());

    }

    public void showUpdateDialog_contact(Activity activity){
        final Dialog dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.update_profile);
        dialog1.setTitle("Update");

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(activity.getResources().getDisplayMetrics().widthPixels*1.5);
        dialog1.getWindow().setLayout(width, height);

        dialog1.show();

        userName = dialog1.findViewById(R.id.userName);
        userEmail = dialog1.findViewById(R.id.userEmail);
        userPhone = dialog1.findViewById(R.id.userPhone);
        userPassword = dialog1.findViewById(R.id.userPassword);

        phone_no = sharedPreferences.getString("Phone", "DatanotFound");

        query = "SELECT * FROM "+TABLE_NAME+" WHERE "+PHONE+" = '"+phone_no+"'";

        db = new UserDB(this);
        Cursor cursor = db.readAll(query);

        while(cursor.moveToNext()){
            id=cursor.getString(0);
            name=cursor.getString(1);
            phone=cursor.getString(2);
            email=cursor.getString(3);
            password=cursor.getString(4);
        }

        userName.getEditText().setText(name);
        userEmail.getEditText().setText("N/A");
        userPhone.getEditText().setText(phone);
        userPassword.getEditText().setText(password);

        Button btn = dialog1.findViewById(R.id.update);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=userName.getEditText().getText().toString().trim();
                userphone=userPhone.getEditText().getText().toString().trim();
                useremail=userEmail.getEditText().getText().toString().trim();
                userpass=userPassword.getEditText().getText().toString().trim();

                int val=db.updateTB(username, userphone, useremail, userpass, phone_no);

                if(val>0){
                    query = "SELECT * FROM "+TABLE_NAME+" WHERE "+PHONE+" = '"+userphone+"'";
                    Cursor cursor = db.readAll(query);
                    String name=null, phone=null, email=null;

                    while(cursor.moveToNext()){
                        name=cursor.getString(1);
                        phone=cursor.getString(2);
                        email=cursor.getString(3);
                        password=cursor.getString(4);
                    }

                    text_name.setText("Name::  "+name);
                    text_phone.setText("Phone::  "+phone);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Phone", userphone);
                    editor.putString("Password",password);

                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                }

                else
                    Toast.makeText(getApplicationContext(), "Updation Failed!!", Toast.LENGTH_SHORT).show();

                dialog1.dismiss();
            }
        });
    }


    public void showUpdateDialog_address(Activity activity){
        final Dialog dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.update_profile_address);
        dialog2.setTitle("Update");

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(activity.getResources().getDisplayMetrics().widthPixels*1.5);
        dialog2.getWindow().setLayout(width, height);

        final TextInputLayout userHouse = dialog2.findViewById(R.id.house);
        final TextInputLayout userStreet = dialog2.findViewById(R.id.street);
        final AutoCompleteTextView userArea = dialog2.findViewById(R.id.area);
        final TextInputLayout userSector = dialog2.findViewById(R.id.block);

        userHouse.getEditText().setText(dataModels.get(0).getHouse_no());
        userStreet.getEditText().setText(dataModels.get(0).getStreet_name());
        userArea.setText(dataModels.get(0).getArea());
        userSector.getEditText().setText(dataModels.get(0).getBlock());

        dialog2.show();

        Button btn2 = dialog2.findViewById(R.id.updte);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userhouse=userHouse.getEditText().getText().toString().trim();
                userroad=userStreet.getEditText().getText().toString().trim();
                usersector=userSector.getEditText().getText().toString().trim();
                userarea=userArea.getText().toString().trim();

                String phn = sharedPreferences.getString("Phone","DataNotFound");
                int val = db.updateTB2(userhouse, userroad, usersector, userarea, phn);

                if(val>0){
                    String q="SELECT * FROM "+TABLE_NAME+" WHERE "+PHONE+" = '"+phn+"'";

                    Cursor cr = db.readAll(query);

                    while(cr.moveToNext()){
                        house=cr.getString(5);
                        street=cr.getString(6);
                        block=cr.getString(8);
                        area=cr.getString(7);
                    }

                    text_house.setText("House::  "+house);
                    text_road.setText("Road::  "+street);
                    text_block.setText("Sector/Block::  "+block);
                    text_area.setText("Area::  "+area);

                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Updation Failed !!", Toast.LENGTH_SHORT).show();
                }

                dialog2.dismiss();
            }
        });
    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
        finish();
    }
}
