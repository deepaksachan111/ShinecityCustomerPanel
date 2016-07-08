package com.shinecity.lko.customerpanal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    private String Fkid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ImageView image = (ImageView)findViewById(R.id.image_vew);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        image.startAnimation(animation);
        SharedPreferences preferences = getSharedPreferences("MY",0);

        Fkid = preferences.getString("FK","");
        if(!Fkid.equals("")){

            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }else {


            startactivity();

        }
    }
    private void startactivity(){

        Thread thread = new Thread(){
            public void run(){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            }

        };thread.start();
    }
}
