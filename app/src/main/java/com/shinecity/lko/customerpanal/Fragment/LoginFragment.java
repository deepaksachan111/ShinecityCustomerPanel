package com.shinecity.lko.customerpanal.Fragment;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.ListViewHelper;
import com.shinecity.lko.customerpanal.MainActivity;
import com.shinecity.lko.customerpanal.ModelData.DashboardData;
import com.shinecity.lko.customerpanal.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

private String response;
    private String ResponseCode;
    private EditText edt_loginname,edt_password;
  private SharedPreferences sharedPreferences;
    private SharedPreferences loginpref;

    SharedPreferences.Editor editor;
    SharedPreferences.Editor logineditor;
    private String FK_MemId ;
    private String FK_brabchId;

    private CheckBox chk_remberme;

    private boolean saveme;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        Button btn_login =(Button)v.findViewById(R.id.btn_login);
        btn_login.setTextColor(Color.parseColor("#FFFFFF"));

        edt_loginname=(EditText)v.findViewById(R.id.edt_loginid);
        edt_password=(EditText)v.findViewById(R.id.edt_loginpassword);
        chk_remberme=(CheckBox)v.findViewById(R.id.chk_remberme);

        sharedPreferences = getActivity().getSharedPreferences("MY",0);
        editor = sharedPreferences.edit();

        loginpref = getActivity().getSharedPreferences("SAVE", Context.MODE_PRIVATE);
        logineditor = loginpref.edit();



    saveme =   loginpref.getBoolean("saveme", false);
        if(saveme == true){

            edt_loginname.setText(loginpref.getString("saveloginid", ""));
            edt_password.setText(loginpref.getString("saveloginpassword", ""));
            chk_remberme.setChecked(true);
        }

        btn_login.setOnClickListener(this);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return v;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login :

                String loginnames= edt_loginname.getText().toString().trim();
                String loginpassword= edt_password.getText().toString().trim();

                if(loginnames.equals("")){
                    Toast.makeText(getActivity(), "Please enter login name ", Toast.LENGTH_LONG).show();

                }
               else if(loginpassword.equals("")){
                    Toast.makeText(getActivity(), "enter thepassword  ", Toast.LENGTH_LONG).show();
                }

              else {

                    if(chk_remberme.isChecked()){

                        logineditor.putBoolean("saveme",true);
                        logineditor.putString("saveloginid", loginnames);
                        logineditor.putString("saveloginpassword",loginpassword);
                        logineditor.commit();

                    }else {

                        logineditor.remove("saveme");
                        logineditor.remove("saveloginid");
                        logineditor.remove("saveloginpassword");
                        logineditor.commit();
                    }
                    String url = "http://shine.quaeretech.com/ShineCityInfra.svc/Login/" + loginnames + "/" + loginpassword;
                    new LoginAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

                }
                break;
        }

    }



    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    public class LoginAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity(),ProgressDialog.THEME_TRADITIONAL);

            //  ProgressDialog.THEME_HOLO_DARK

            pDialog.setMessage("Authentication  ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String response = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(url);

            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                Log.v("Contact  Response ", response);
                // String response = jsonResponse;


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(" Exception is caught here ......." + e.toString());
            }
            return response;
        }


        @Override
        protected void onPostExecute(String ss) {
            response = ss;
            pDialog.dismiss();
            if (response == null) {
                Toast.makeText(getActivity(), "Internet not available ", Toast.LENGTH_LONG).show();

            } else


                try {

                    JSONArray jsonArray = new JSONArray(ss);
                    int noOfObjects = jsonArray.length();
                    Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");
                    // pd.dismiss();
                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject jObj = jsonArray.getJSONObject(j);
                         FK_brabchId=jObj.getString("FK_BranchID");
                         FK_MemId=jObj.getString("FK_MemId");
                        String FK_UserTypeID=jObj.getString("FK_UserTypeID");
                        ResponseCode=jObj.getString("Response");
                        savedataOnShered();

                    }if(ResponseCode.equals("1")){
                        startActivity(new Intent(getActivity(),MainActivity.class));
                        getActivity().finish();
                    }else{

                        Toast.makeText(getActivity(), "Invalid UserName or Password ", Toast.LENGTH_LONG).show();

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(" Exception is caught here ......." + e.toString());
                }








        }
    }

    public void savedataOnShered(){




        if(!FK_MemId.equals("")){

            editor.putString("FK", FK_MemId);
            editor.putString("BRANCHID",FK_brabchId);
            editor.commit();
        }else{
            FK_MemId = "";
        }

// give key value as "sound" you mentioned and value what you // to want give as "1" in you mentioned

    }
}
