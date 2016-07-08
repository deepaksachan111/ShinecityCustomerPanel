package com.shinecity.lko.customerpanal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.Fragment.AllBookingFragment;
import com.shinecity.lko.customerpanal.Fragment.CoupanDetailDataFragment;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);



        Intent in  = getIntent();
        Bundle b = in.getExtras();
        String name = b.getString("NAME");

        if(name == null){
            Toast.makeText(getApplicationContext(),"No data",1000).show();
        }else {

            if (name.equals("All")) {

                getSupportFragmentManager().beginTransaction().replace(R.id.booking_activity, new AllBookingFragment()).commit();
            }

        }

        String emi = getIntent().getStringExtra("EMI");
                if(emi == null){

                }else {
                    if (emi.equals("emi")) {

                        getSupportFragmentManager().beginTransaction().replace(R.id.booking_activity, new AllBookingFragment()).commit();
                    }
                }

        String coupon= getIntent().getStringExtra("COUPON");
        if(coupon == null){

        }else {
            if (coupon.equals("coupon")) {

                getFragmentManager().beginTransaction().replace(R.id.booking_activity, new CoupanDetailDataFragment()).commit();
            }
        }
    }


}
