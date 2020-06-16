package com.example.mylenovo.testapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private final String DEFAULT="N/A";
    TextView textView10;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView10=findViewById(R.id.textView10);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_anim);
        textView10.startAnimation(animation);

        linearLayout=findViewById(R.id.layout_1);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.down_anim);
        linearLayout.setAnimation(animation1);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellow));
        }

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);

                    File f = new File("/data/data/com.example.mylenovo.testapp2/shared_prefs/AppData.xml");
                    if (f.exists()){
                        System.out.print("LOLOLL\n");
                        sharedPreferences = getSharedPreferences("AppData", Context.MODE_PRIVATE);

                        String phone=sharedPreferences.getString("Phone", DEFAULT);
                        String password=sharedPreferences.getString("Password",DEFAULT);

                        if(phone.compareTo(DEFAULT)!=0 && password.compareTo(DEFAULT)!=0){
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else{
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        System.out.print("KOKOKK\n");
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread.start();
    }
}
