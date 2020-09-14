package com.example.mylenovo.testapp2;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class Main2Activity extends AppCompatActivity {

    Window window;
    TextInputLayout admin_name, admin_password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if(Build.VERSION.SDK_INT>=21){
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

        admin_name = findViewById(R.id.admin_name);
        admin_password = findViewById(R.id.admin_pass);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean auth = checkAuthenticity();

                if(auth){
                    Intent intent = new Intent(Main2Activity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    StyleableToast.makeText(Main2Activity.this, "Wrong Name/Password!", R.style.styleToast).show();
                }
            }
        });
    }

    private boolean checkAuthenticity() {
        String name = admin_name.getEditText().getText().toString().trim();
        String pass = admin_password.getEditText().getText().toString().trim();

        if(name.isEmpty() || pass.isEmpty()){
            return false;
        }
        else if(name.equals("admin") && pass.equals("admin")){
            return true;
        }
        else
            return false;
    }
}
