package com.example.mylenovo.testapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Sign_up_address extends AppCompatActivity {

    Window window;
    Button save;
    TextInputLayout plot, road, sector;
    AutoCompleteTextView region;

    String house, street, block, territory, username, password, phone, email;

    FirebaseDatabase contactInfo = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = contactInfo.getReference();
    SharedPreferences sharedPreferences, sharedPreferences2;

    private final String[] area = new String[]{"Motijheel", "Adabar", "Azampur", "Badda", "Bangshal", "Bimanbandar",
    "Cantonment", "Chowkbazar", "Dhanmondi", "Gulshan", "Hazaribagh", "Kalabagan", "Khilgao", "Khilkhet", "Kotwali",
    "Lalbagh", "Mirpur", "Mohammadpur","Newmarket","Pallabi", "Paltan", "Panthapath", "Ramna", "Rampura", "Shahbagh", "Sher-e-Bangla",
    "Tejgaon", "Uttara", "Wari", "Jigatola", "Firmgate", "Bashundhara", "Banani", "Mohakhali", "Bnagla Motor", "Lalmatia"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_address);

        plot = (TextInputLayout) findViewById(R.id.house);
        road = (TextInputLayout) findViewById(R.id.street);
        sector = (TextInputLayout) findViewById(R.id.block);
        region = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ImageView d_arrow = (ImageView)findViewById(R.id.imageView3);

        save = (Button)findViewById(R.id.button3);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**/
                saveData();
            }
        });

        ArrayAdapter<String> adaptar = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, area);
        actv.setAdapter(adaptar);

        d_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actv.showDropDown();
            }
        });

        if(Build.VERSION.SDK_INT>=21){
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }
    }
    public void saveData(){
        //address info
        house = plot.getEditText().getText().toString().trim();
        street = road.getEditText().getText().toString().trim();
        block = sector.getEditText().getText().toString().trim();
        territory = region.getText().toString().trim();

        //contact info
        username = getIntent().getStringExtra("Username");
        phone = getIntent().getStringExtra("Phone");
        password = getIntent().getStringExtra("Password");
        email = getIntent().getStringExtra("Email");

        if(house.isEmpty()){
            plot.setError("House/plot is required!!");
            plot.requestFocus();
            return;
        }
        else if(street.isEmpty()){
            road.setError("Road name/no is required!!");
            road.requestFocus();
            return;
        }
        else if(territory.isEmpty()){
            region.setError("Area needed!!");
            region.requestFocus();
            return;
        }
        else{
            //System.out.print(un+" "+phn+" "+pss+" "+eml+"\n");
            ContactInfo info = new ContactInfo(username, email, phone, house, street, territory, block);

            //FirebaseDatabase
            /*String key = databaseReference.push().getKey();
            databaseReference.child(key).setValue(info);*/

            /*try {
                FileOutputStream fout1 = openFileOutput("Phone.txt", Context.MODE_PRIVATE);
                FileOutputStream fout2 = openFileOutput("Password.txt", Context.MODE_PRIVATE);
                FileOutputStream fout3 = openFileOutput("Key.txt", Context.MODE_PRIVATE);

                fout1.write(phone.getBytes());
                fout2.write(password.getBytes());
                fout3.write(key.getBytes());


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            //SharedPreferences
            sharedPreferences = getSharedPreferences("AppData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Phone", phone);
            editor.putString("Password", password);

            editor.commit();

            sharedPreferences2 = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
            editor2.putString("Name", username);
            editor2.putString("ContactNo.", phone);
            editor2.putString("Email", email);
            editor2.putString("Address", "House: "+house+", Road: "+street+", Block: "+block+", Area: "+territory);

            editor2.commit();

           //SQLiteDatabase
            UserDB user = new UserDB(this);
            SQLiteDatabase sq = user.getWritableDatabase();
            user.insertData(username, phone, email, password, house, street, territory, block);

            Toast.makeText(this,"Registered successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            Sign_up_contact.actv.finish();
            finish();
        }
    }
}
