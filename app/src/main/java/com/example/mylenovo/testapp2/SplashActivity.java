package com.example.mylenovo.testapp2;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView img = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_anime);
        img.startAnimation(animation);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorYellow));
        }

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);
                    File f1 = getApplicationContext().getFileStreamPath("Phone.txt");
                    File f2 = getApplicationContext().getFileStreamPath("Password.txt");

                    if(f1.exists() && f2.exists()){
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
                catch (Exception e){
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread.start();
    }
}
