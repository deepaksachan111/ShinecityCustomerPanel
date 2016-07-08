package com.shinecity.lko.customerpanal;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.internal.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.Fragment.BookingFragment;
import com.shinecity.lko.customerpanal.Fragment.DashboardFragment;
import com.shinecity.lko.customerpanal.Fragment.DocumentFragment;
import com.shinecity.lko.customerpanal.ModelData.DbHelper;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener  {
    DbHelper dbHelper;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;
    private  Dialog dialog;

    private int[] tabIcons = { R.mipmap.ic_document, R.mipmap.ic_booking, R.mipmap.ic_profile };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  startService(new Intent(MainActivity.this,Notification.class));

        Intent notificationIntent = new Intent(getApplicationContext(), Notification.class);
        PendingIntent contentIntent = PendingIntent.getService(getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.cancel(contentIntent);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + AlarmManager.INTERVAL_DAY * 2, AlarmManager.INTERVAL_DAY * 2, contentIntent);

        startService(notificationIntent);

  /*      toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        dbHelper =new DbHelper(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
        //  tab.select();
        }
        setupTabIcons();

        findViewById(R.id.ic_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.setOnMenuItemClickListener(MainActivity.this);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();*/
                addMenu();
            }
        });



    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag( new DocumentFragment(),"\t" +"Document");
        adapter.addFrag(BookingFragment.newInstance(""), "  Booking");
        adapter.addFrag(DashboardFragment.newInstance(""), "  Dashboard");
        viewPager.setAdapter(adapter);
    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_comedy:
                Toast.makeText(this, "Comedy Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_movies:
                Toast.makeText(this, "Movies Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_music:
                Toast.makeText(this, "Music Clicked", Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new DocumentFragment() ;
            }
            if (position == 1) {
                return BookingFragment.newInstance("");
            }
            if (position == 2)
                return  DashboardFragment.newInstance("");
            else
                return new BookingFragment();
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void addMenu() {

        final Dialog openDialog = new Dialog(this);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openDialog.setContentView(R.layout.custumdialoge_popupmenu);

        LinearLayout linear_profile=(LinearLayout)openDialog.findViewById(R.id.linear_menu_user);
        LinearLayout linear_changepassword=(LinearLayout)openDialog.findViewById(R.id.linear_menu_password);
        final LinearLayout linear_logout=(LinearLayout)openDialog.findViewById(R.id.linear_menu_logout);

        linear_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MyProfile.class));
                openDialog.dismiss();
            }
        });

        linear_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              logOutDetails();
                openDialog.dismiss();
            }
        });

        linear_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordDiaologe();
                openDialog.dismiss();
            }
        });

        // AlertDialog alertDialog = openDialog.create();
        Window window = openDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
      //  window.showAsDropDown(edt_custom_sharing);
        window.setGravity(Gravity.RIGHT | Gravity.TOP);
        //openDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        openDialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);


        openDialog.getWindow().getAttributes().verticalMargin = 0.08F;
        openDialog.getWindow().getAttributes().horizontalMargin = 0.01F;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        openDialog.show();
    }


    public void logOutDetails() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        // set title
        alertDialogBuilder.setTitle("Would you like to logout?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click yes to logout!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {


                                SharedPreferences preferences = getSharedPreferences("MY", 0);

                                String s = preferences.getString("FK","");

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();

                               dbHelper.deleteSingleRow();

                                startActivity(new Intent(getApplicationContext(),SplashScreen.class));
                                finish();
                                // if this button is clicked, close
                                // current activity
                               // session.logoutUser();
                               // startActivity(new Intent(HomeActivity.this, DefaultScreenActivity.class));
                                finish();

                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void changePasswordDiaologe(){

         dialog = new Dialog(MainActivity.this);
        // Include dialog.xml file

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.changepassword_dialog);

        // Set dialog title
       //
       // dialog.setTitle("Custom Dialog");


        final EditText oldpass = (EditText)dialog. findViewById(R.id.oldpass);
        final EditText newpass = (EditText)dialog. findViewById(R.id.newpass);
        final EditText confirmpass = (EditText)dialog. findViewById(R.id.confirmpass);
        Button submit = (Button)dialog. findViewById(R.id.setpass);

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String oldp = oldpass.getText().toString();
                String newp = newpass.getText().toString();
                String confirmp = confirmpass.getText().toString();
                if (oldp.length() < 4) {
                    Toast toast = Toast.makeText(MainActivity.this, "enter OldPassword", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 600);
                    View view1 = toast.getView();
                    view1.setBackgroundResource(R.drawable.toast_drawablecolor);
                    toast.show();
                } else if (newp.length() < 4) {
                    Toast toast2 = Toast.makeText(MainActivity.this, "enter newpassword atleast 4 digit", Toast.LENGTH_LONG);
                    View view1 = toast2.getView();
                    view1.setBackgroundResource(R.drawable.toast_drawablecolor);
                    toast2.setGravity(Gravity.TOP, 25, 600);
                    toast2.show();
                } else if (!newp.equals(confirmp)) {
                    Toast toast3 = Toast.makeText(MainActivity.this, "newpass && confirmpass not match", Toast.LENGTH_LONG);
                    View view1 = toast3.getView();
                    view1.setBackgroundResource(R.drawable.toast_drawablecolor);
                    toast3.setGravity(Gravity.TOP, 25, 600);
                    toast3.show();
                } else {

                    sharedPreferences = getSharedPreferences("MY", Context.MODE_PRIVATE);
                    String vendorid = sharedPreferences.getString("LOGINID","");
                    String url = "http://shine.quaeretech.com/ShineCityInfra.svc/ChangePassword/" + vendorid + "/" + oldp + "/" + newp;
                   new ChangePasswordAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
                }
            }
        });
        dialog.show();

    }

    private class ChangePasswordAsyncTask extends AsyncTask<String, Void, String> {
      //  private static final int REGISTRATION_TIMEOUT = 3 * 1000;
       // private static final int WAIT_TIMEOUT = 30 * 1000;
        private final HttpClient httpclient = new DefaultHttpClient();
       // final HttpParams params = httpclient.getParams();
        HttpResponse response;
        private String content = null;
        private boolean error = false;
        private ProgressDialog dialog2 =
                new ProgressDialog(MainActivity.this);


        protected void onPreExecute() {
            dialog2.setMessage("Getting your data... Please wait...");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        protected String doInBackground(String... urls) {

            String URL = null;

            try {

                URL = urls[0];
             /*   HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
                HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
                ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);*/

                HttpGet httpPost = new HttpGet(URL);

             /*   //add name value pair for the country code
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("start",String.valueOf(start)));
                nameValuePairs.add(new BasicNameValuePair("limit",String.valueOf(limit)));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));*/
                response = httpclient.execute(httpPost);

                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    content = out.toString();
                } else {
                    //Closes the connection.
                    Log.w("HTTP1:", statusLine.getReasonPhrase());
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                Log.w("HTTP2:", e);
                content = e.getMessage();
                error = true;
                cancel(true);
            } catch (IOException e) {
                Log.w("HTTP3:", e);
                content = e.getMessage();
                error = true;
                cancel(true);
            } catch (Exception e) {
                Log.w("HTTP4:", e);
                content = e.getMessage();
                error = true;
                cancel(true);
            }

            return content;
        }

        protected void onCancelled() {
            dialog2.dismiss();
            Toast toast = Toast.makeText(MainActivity.this,
                    "Error connecting to Server", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 600);
            View view1 = toast.getView();
            view1.setBackgroundResource(R.drawable.toast_drawablecolor);
            toast.show();

        }

        protected void onPostExecute(String content) {
            dialog2.dismiss();
            Toast toast;
            if (error) {
                toast = Toast.makeText(MainActivity.this,
                        content, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 600);
                toast.show();
            } else {
                displayCountryList(content);


            }
        }


    }

    private void displayCountryList(String response) {
        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                String rs_code = jsonObject.getString("Response");
                if (rs_code.equals("1")) {
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Successfully Password Changed ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 600);
                    View view1 = toast.getView();
                    view1.setBackgroundResource(R.drawable.toast_drawablecolor);
                    toast.show();

                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();

                }
                else {
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Incorrect fill Record", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 600);
                    View view1 = toast.getView();
                    view1.setBackgroundResource(R.drawable.toast_drawablecolor);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Toast toast = Toast.makeText(MainActivity.this,
                    "incorrect Old Password", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 600);
            View view1 = toast.getView();
            view1.setBackgroundResource(R.drawable.toast_drawablecolor);
            toast.show();
        }
    }


}
