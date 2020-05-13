package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }


        toolbar = findViewById(R.id.toolbar_prf);
        toolbar.setTitle("My Profile");
        toolbar.setTitleTextColor(getColor(R.color.colorBlack));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String phone_no = sharedPreferences.getString("UserPhone", "DatanotFound");

        System.out.print(phone_no);
        String TABLE_NAME="User_Info";
        String PHONE="Phone";
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+PHONE+" = '"+phone_no+"'";
        dataModels.add(new DataModel("dl", "32894", "jrg", "kjr", "rh"));

        /*UserDB db = new UserDB(this);
        Cursor cursor = db.readAll(query);

        while(cursor.moveToNext()){
            String name=cursor.getString(1);
            String phone=cursor.getString(2);
            String house=cursor.getString(5);
            String street=cursor.getString(6);
            String area=cursor.getString(7);

            dataModels.add(new DataModel(name, phone, house, street, area));
        }*/
    }

    public void showUpdateDialog_contact(Activity activity){
        final Dialog dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.update_profile);
        dialog1.setTitle("Update");

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(activity.getResources().getDisplayMetrics().widthPixels*1.5);
        dialog1.getWindow().setLayout(width, height);

        dialog1.show();

        Button btn = dialog1.findViewById(R.id.update);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        dialog2.show();

        Button btn2 = dialog2.findViewById(R.id.updte);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
    }
}
