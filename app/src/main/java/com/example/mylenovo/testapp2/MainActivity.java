package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.Window;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Window window;
    Button button;
    EditText phone, password;
    public static Activity actv;
    SharedPreferences sharedPreferences;
    private final String DEFAULT="N/A";

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Are you sure want to exit?").setCancelable(false).
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startMain.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actv=this;

        phone = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);

        text = (TextView)findViewById(R.id.textView);
        text.setPaintFlags(text.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        sharedPreferences=getSharedPreferences("AppData", Context.MODE_PRIVATE);
        String phone=sharedPreferences.getString("Phone",DEFAULT);
        String password=sharedPreferences.getString("Password",DEFAULT);

        if(phone.compareTo(DEFAULT)==0 && password.compareTo(DEFAULT)==0){
            signUp();
        }
        else{
           text.setText(null);
        }


        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate();
            }
        });

        if(Build.VERSION.SDK_INT>=21){
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellowlight));
        }
    }

    private void signUp() {
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Sign_up_contact.class);
                startActivity(i);
            }
        });
    }

    public void authenticate()
    {
        String user_phone, user_password, f_phone=null, f_password=null, f_id=null;

        user_phone = phone.getText().toString().trim();
        user_password = password.getText().toString().trim();

        if(user_phone.isEmpty() || user_password.isEmpty()){
            System.out.print("Give phone no. and password !!"+"\n");
        }

        else if(user_phone.equals("admin") && user_password.equals("admin")){
            Intent i = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(i);
            finish();
        }

        else{

            UserDB usdb = new UserDB(this);
            Cursor cursor = usdb.readData(user_phone);

            System.out.print(cursor.getCount()+"\n");

            if(cursor.getCount()==0) {
                Toast.makeText(this, "Wrong Phone no.", Toast.LENGTH_SHORT).show();
            }
            else {
                while(cursor.moveToNext()){
                    f_phone = cursor.getString(2);
                    f_password = cursor.getString(4);
                }

                System.out.print(f_phone+" "+f_password+"\n");

                if(!user_password.equals(f_password)){
                    Toast.makeText(this, "Incorrect phone no. or password !!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);

                    /*SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UserID", f_id);
                    editor.putString("UserPhone", f_phone);
                    editor.putString("UserPassword", f_password);
                    editor.apply();*/

                    startActivity(i);
                    finish();
                }
            }

            /**/
        }

    }

}
