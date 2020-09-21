package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up_contact extends AppCompatActivity {

    Window window;
    Button next;
    TextInputLayout reg_name, reg_email, reg_phone, reg_pass, reg_cpass;
    String username, email, phone, password, confirm_pass;
    public static Activity actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_contact);

        actv = this;
        
        reg_name = (TextInputLayout)findViewById(R.id.name);
        reg_email = (TextInputLayout)findViewById(R.id.email);
        reg_phone = (TextInputLayout)findViewById(R.id.phone);
        reg_pass = (TextInputLayout)findViewById(R.id.password);
        reg_cpass = (TextInputLayout)findViewById(R.id.confirm_pass);

        next = (Button)findViewById(R.id.button3);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**/
                regcontact();
            }
        });

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }
    }
    public void regcontact(){
        username = reg_name.getEditText().getText().toString().trim();
        email = reg_email.getEditText().getText().toString().trim();
        phone = reg_phone.getEditText().getText().toString().trim();
        password = reg_pass.getEditText().getText().toString().trim();
        confirm_pass = reg_cpass.getEditText().getText().toString().trim();

        if(username.isEmpty()){
            reg_name.setError("Username is required!!");
            reg_name.requestFocus();
            return;
        }
        else if(phone.isEmpty()){
            reg_phone.setError("Phone number is required!!");
            reg_phone.requestFocus();
            return;
        }
        else if(password.isEmpty()){
            reg_pass.setError("Password is required!!");
            reg_pass.requestFocus();
            return;
        }
        else if(confirm_pass.isEmpty()){
            reg_cpass.setError("Confirmation nedded!!");
            reg_cpass.requestFocus();
            return;
        }
        else if(!password.equals(confirm_pass)){
            reg_cpass.setError("Wrong confirmation!!");
            reg_cpass.requestFocus();
            return;
        }
        /*else if(phone.length()!=11){
            reg_phone.setError("Wrong phone number!!");
            reg_phone.requestFocus();
            return;
        }
        else if(!Patterns.PHONE.matcher(phone).matches()){
            reg_phone.setError("Wrong phone number!!");
            reg_phone.requestFocus();
            return;
        }*/
        else{
            Intent i = new Intent(getApplicationContext(), Sign_up_address.class);
            i.putExtra("Username", username);
            i.putExtra("Phone", phone);
            i.putExtra("Email", email);
            i.putExtra("Password", password);
            startActivity(i);
        }
    }
}
