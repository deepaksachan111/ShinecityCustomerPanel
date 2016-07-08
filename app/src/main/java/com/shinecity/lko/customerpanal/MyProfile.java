package com.shinecity.lko.customerpanal;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.ModelData.ViewEnquiryData;

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

public class MyProfile extends AppCompatActivity {
   SharedPreferences sharedPreferences;
    private TextView name,dob,gender,fathername,maritalstatus,husbandname,contactno,email,address,
                      nomineename,nomineerelation,nomineedob,nomineeincome,nomineeoccupation,nomineeappointee,
                      bankac,bankname,branchname,accounttype,micr,ifsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ImageView iv_profile_back =(ImageView)findViewById(R.id.iv_profile_back);
        iv_profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
         findbyid();
        sharedPreferences=getSharedPreferences("MY",0);
        String fkid= sharedPreferences.getString("FK", "");
        String branchid=sharedPreferences.getString("BRANCHID","");
        String memberdetailURL =  "http://shine.quaeretech.com/ShineCityInfra.svc/GetMemberDetails/"+fkid+"/"+branchid;
        new MyProfileAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, memberdetailURL);
    }

private void findbyid(){
    name =(TextView)findViewById(R.id.user_profile_name);
    dob =(TextView)findViewById(R.id.user_profile_short_bio);
    gender =(TextView)findViewById(R.id.user_profile_gender);
    fathername =(TextView)findViewById(R.id.user_profile_fathername);
    maritalstatus =(TextView)findViewById(R.id.user_profile_maritalstatus);
    husbandname =(TextView)findViewById(R.id.user_profile_husbandname);
    contactno =(TextView)findViewById(R.id.user_profile_contactname);
    email =(TextView)findViewById(R.id.user_profile_email);
    address =(TextView)findViewById(R.id.user_profile_address);

    nomineename =(TextView)findViewById(R.id.user_profile_nomineename);
    nomineerelation =(TextView)findViewById(R.id.user_profile_nomineerelation);
    nomineedob =(TextView)findViewById(R.id.user_profile_nomineedob);
    nomineeincome =(TextView)findViewById(R.id.user_profile_nomineeanualincome);
    nomineeoccupation =(TextView)findViewById(R.id.user_profile_nomineeocupassion);
    nomineeappointee =(TextView)findViewById(R.id.user_profile_nominee_appointee);

    bankac =(TextView)findViewById(R.id.user_profile_acno);
    bankname =(TextView)findViewById(R.id.user_profile_bankname);
    branchname =(TextView)findViewById(R.id.user_profile_branchname);
    accounttype =(TextView)findViewById(R.id.user_profile_accounttype);
    micr =(TextView)findViewById(R.id.user_profile_micr);
    ifsc =(TextView)findViewById(R.id.user_profile_ifsc);




}


    private class MyProfileAsyncTask extends AsyncTask<String,Void,String>{
        ProgressDialog pDialog ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyProfile.this,ProgressDialog.THEME_TRADITIONAL);
            pDialog.setMessage("Loading");
            pDialog.show();
        }



        @Override
        protected String doInBackground(String... params) {
            String response = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                Log.v("Responce", response);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            pDialog.dismiss();
            if(s == null){
                Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_LONG).show();
            }else
            if(s != ""){

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i =0 ; i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String Name = jsonObject.getString("Name");
                        String DOB = jsonObject.getString("DOB");
                        String Email = jsonObject.getString("Email");
                        String FathersName = jsonObject.getString("FathersName");
                        String GaurdianName = jsonObject.getString("GaurdianName");
                        String ImageURL = jsonObject.getString("ImageURL");
                        String Sex = jsonObject.getString("Sex");
                        String MaritalStatus = jsonObject.getString("MaritalStatus");
                        String CorrespondenceContact = jsonObject.getString("CorrespondenceContact");
                        String PermanentAddress = jsonObject.getString("PermanentAddress");
                        String NomineeName = jsonObject.getString("NomineeName");
                        String RelationWith = jsonObject.getString("RelationWith");
                        String NomineeDOB = jsonObject.getString("NomineeDOB");
                        String NomineeAnnualIncome = jsonObject.getString("NomineeAnnualIncome");
                        String NomineeOccupation = jsonObject.getString("NomineeOccupation");
                        String Appointee = jsonObject.getString("Appointee");
                        String BankAcNo = jsonObject.getString("BankAcNo");
                        String BankName = jsonObject.getString("BankName");
                        String BranchName = jsonObject.getString("BranchName");
                        String AccountType = jsonObject.getString("AccountType");
                        String IFSC = jsonObject.getString("IFSC");
                        String MICR = jsonObject.getString("MICR");

                                name.setText(Name);
                                dob.setText(DOB);
                                gender.setText(Sex);
                                fathername.setText(FathersName);
                                maritalstatus.setText(MaritalStatus);
                                husbandname.setText(GaurdianName);
                                contactno.setText(CorrespondenceContact);
                                email.setText(Email);
                                address.setText(PermanentAddress);
                                nomineename.setText(NomineeName);
                                nomineerelation.setText(RelationWith);
                                nomineedob.setText(NomineeDOB);
                                nomineeincome.setText(NomineeAnnualIncome);
                                nomineeoccupation.setText(NomineeOccupation);
                                nomineeappointee.setText(Appointee);
                                bankac.setText(BankAcNo);
                                bankname.setText(BankName);
                                        branchname.setText(BranchName);
                                        accounttype.setText(AccountType);
                                        micr.setText(MICR);
                                        ifsc.setText(IFSC);





                    }

                    // ListViewHelper.getListViewSize(listview_viewenquiry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }
    }


}
