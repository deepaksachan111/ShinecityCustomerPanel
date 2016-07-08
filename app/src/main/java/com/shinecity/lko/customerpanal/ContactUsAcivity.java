package com.shinecity.lko.customerpanal;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shinecity.lko.customerpanal.Fragment.AllBookingFragment;
import com.shinecity.lko.customerpanal.Fragment.LoginFragment;

import java.util.Locale;

public class ContactUsAcivity extends AppCompatActivity {
    // Google Map
    private GoogleMap googleMap;
    private double longitude;
    private double latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_acivity);

       Button  btn_view_lagegermap =(Button)findViewById(R.id.btn_view_lagegermap);

        btn_view_lagegermap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webviewmapDialoge();
            }
        });
        Button  googlemap =(Button)findViewById(R.id.btn_googlemap);

        googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = String.format(Locale.ENGLISH, "https://www.google.com/maps/place/Vipul+Khand,+Vipul+Khand+4,+Gomti+Nagar,+Lucknow,+Uttar+Pradesh+226010,+India/@26.832394,80.946654,13z/data=!4m5!3m4!1s0x399bfd2c72537c83:0x3749b5e0a29bba83!8m2!3d26.844825!4d80.9832006?hl=en-GB", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

              /*  Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);*/

              //  startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });


        try {
            // Loading map
            initilizeMap();

            // Changing map type
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            // googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

            // Showing / hiding your current location
            googleMap.setMyLocationEnabled(true);

            // Enable / Disable zooming controls
            googleMap.getUiSettings().setZoomControlsEnabled(false);

            // Enable / Disable my location button
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Enable / Disable Compass icon
            googleMap.getUiSettings().setCompassEnabled(true);

            // Enable / Disable Rotate gesture
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            // Enable / Disable zooming functionality
            googleMap.getUiSettings().setZoomGesturesEnabled(true);

            latitude = 26.843806;
            longitude = 80.985814;

            // lets place some 10 random markers
            for (int i = 0; i < 10; i++) {
                // random latitude and logitude
                double[] randomLocation = createRandLocation(latitude,
                        longitude);

                // Adding a marker
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(randomLocation[0], randomLocation[1]))
                        .title("Hello Maps " + i);

                Log.e("Random", "> " + randomLocation[0] + ", "
                        + randomLocation[1]);

                // changing marker color
                if (i == 0)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                if (i == 1)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                if (i == 2)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                if (i == 3)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                if (i == 4)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                if (i == 5)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                if (i == 6)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED));
                if (i == 7)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                if (i == 8)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                if (i == 9)
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                googleMap.addMarker(marker);

                // Move the camera to last position with a zoom level
                if (i == 9) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(randomLocation[0],
                                    randomLocation[1])).zoom(15).build();

                    googleMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(cameraPosition));

                    String msg2 = latitude + ", "+longitude;

                    //Displaying current coordinates in toast
                    Toast.makeText(this, msg2, Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    /**
     * function to load map If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {



                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /*
     * creating random postion around a location for testing purpose only
     */
    private double[] createRandLocation(double latitude, double longitude) {

        return new double[] { latitude + ((Math.random() - 0.5) / 500),
                longitude + ((Math.random() - 0.5) / 500),
                150 + ((Math.random() - 0.5) * 10) };

    }

    public void webviewmapDialoge(){
        // Create custom dialog object
        final Dialog dialog = new Dialog(ContactUsAcivity.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoge_map_iframe);
        // Set dialog title
       // dialog.setTitle("Map");

        // set values for custom dialog components - text, image and button
        WebView webview = (WebView) dialog.findViewById(R.id.webview);

        String html = "<iframe width=\"450\" height=\"260\" style=\"border: 1px solid #cccccc;\" src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d7119.841832080647!2d80.98445507802569!3d26.842467557712315!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x399bfd2c72537c83%3A0x3749b5e0a29bba83!2sVipul+Khand!5e0!3m2!1sen!2sin!4v1398774705117\" ></iframe>";





        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadData(html, "text/html", null);

        dialog.show();

        Button declineButton = (Button) dialog.findViewById(R.id.dialoge_cancel);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_us_acivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
