package com.shinecity.lko.customerpanal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.ModelData.SiteData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EMIPaymentActivity extends AppCompatActivity {

    private Spinner spinnersite;
    private EditText edt_bookingid;
    private EditText edt_plotno;
     private  Button btn_display;
    private  String URL, responce;
    private String spinnerSelectedText;
    ArrayAdapter arrayAdapter;


    ArrayList<String> sitelist = new ArrayList<>();
    ArrayList<SiteData> getSitelist = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emipayment);

        spinnersite =(Spinner)findViewById(R.id.spiner_emipayments_site);


         arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,sitelist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

       // spinnersite.setAdapter(arrayAdapter);



        spinnersite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long ids) {

            SiteData data = getSitelist.get(position);
                spinnerSelectedText = data.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edt_bookingid=(EditText)findViewById(R.id.edt__emipayments_bookingid);
        edt_plotno =(EditText)findViewById(R.id.edt__emipayments_plotno);
        btn_display=(Button)findViewById(R.id.btn__emipayments_show);

         URL ="http://shine.quaeretech.com/ShineCityInfra.svc/GetSiteList";
        thred();
        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String booking =edt_bookingid.getText().toString().trim();
                String plotno =edt_plotno.getText().toString().trim();

                if(booking.equals("")|| plotno.equals("")){

                    Snackbar.make(v, "Please Enter All Fields ", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifiManager.setWifiEnabled(true);
                            thred();
                           // startActivity(new Intent(getApplicationContext(),EMIPaymentActivity.class ));
                           // wifiManager.setWifiEnabled(false);
                        }
                    }).setActionTextColor(Color.RED).show();

// Changing message text color


                }else{

                    Intent intent = new Intent(getApplicationContext(),EMIPaymentRecord.class);
                    intent.putExtra("PLOT",plotno);
                    intent.putExtra("BOOKING",booking);
                    intent.putExtra("selecteed",spinnerSelectedText);
                    startActivity(intent);
                   // Toast.makeText(getApplicationContext()," Success ",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void  thred() {

            Thread t = new Thread() {

                public void run(){
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(URL);
                    HttpResponse httpResponse = null;
                    try {
                        httpResponse = httpClient.execute(httpGet);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        responce = EntityUtils.toString(httpEntity);
                    } catch ( IOException e) {
                        e.printStackTrace();

                    }

                 /*   try {
                        HttpEntity httpEntity = httpResponse.getEntity();
                       responce = EntityUtils.toString(httpEntity);
                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();

                    }
                    });
                    }*/



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            parseJSON(responce);
                         // String re = responce .toString();

                            spinnersite.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });



                }



            };t.start();

    }

    private void parseJSON(String s) {
        if (s == null) {
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();

        } else {

        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String PK_SiteId = jsonObject.getString("PK_SiteId");
                String SiteName = jsonObject.getString("SiteName");

                sitelist.add(SiteName);

                getSitelist.add(new SiteData(SiteName, PK_SiteId));


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    }

}
