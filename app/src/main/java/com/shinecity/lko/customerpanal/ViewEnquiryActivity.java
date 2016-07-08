package com.shinecity.lko.customerpanal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.ModelData.AllBookingData;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ViewEnquiryActivity extends AppCompatActivity implements View.OnClickListener {
private EditText fromdate,todate;
    private Calendar calendar;
  private   SimpleDateFormat dateFormat;
    private int day;
    private int month;
    private int year;
    private  String selectedstatus;
    private  Button btnsubmit;
    private  ArrayList<ViewEnquiryData> viewEnquiryDataslist = new ArrayList<>();
    private ListView listview_viewenquiry;
    private  TextView tv_viewinquiry_norecord;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_enquiry);

        fromdate = (EditText) findViewById(R.id.edt_enquiry_fromedate);
        todate = (EditText) findViewById(R.id.edt_enquiry_fromto);
         Spinner spinnerstatus=(Spinner)findViewById(R.id.edt_enquiry_status);
         btnsubmit =(Button)findViewById(R.id.btn_viewinquiry_submit);
        listview_viewenquiry=(ListView)findViewById(R.id.listview_viewenquiry);
        tv_viewinquiry_norecord=(TextView)findViewById(R.id.tv_viewinquiry_norecord);

         adapter = new MyAdapter(ViewEnquiryActivity.this,R.layout.viewenquiry_adapter,viewEnquiryDataslist);
        ArrayList<String> statuslist = new ArrayList<>();
        statuslist.add("All");
        statuslist.add("pending");
        statuslist.add("done");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.spinner_item,statuslist);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerstatus.setAdapter(arrayAdapter);

        spinnerstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  selectedstatus= (String)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        calendar.set(year, month, day);
        String selectdate = dateFormat.format(calendar.getTime());

        fromdate.setText(selectdate);
        todate.setText(selectdate);


        fromdate.setOnClickListener(this);
        todate.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
    }

    private void setFromDate() {


        /*
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
                AlertDialog.THEME_DEVICE_DEFAULT_DARK
                AlertDialog.THEME_HOLO_DARK;
                AlertDialog.THEME_HOLO_LIGHT
                AlertDialog.THEME_TRADITIONAL   */

        // Launch Date Picker Dialog
       dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        DatePickerDialog dpd = new DatePickerDialog( this, AlertDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                   /*   addmeeting_edt_selectdate.setText(dayOfMonth + "-"
                              + dateFormatter.format (monthOfYear + 1) + "-" + year);*/
                        calendar.set(year, monthOfYear, dayOfMonth);
                        fromdate.setText(dateFormat.format(calendar.getTime()));

                    }
                }, year, month, day);
        dpd.show();
    }

    private void setTodateDate() {


        /*
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
                AlertDialog.THEME_DEVICE_DEFAULT_DARK
                AlertDialog.THEME_HOLO_DARK;
                AlertDialog.THEME_HOLO_LIGHT
                AlertDialog.THEME_TRADITIONAL   */

        // Launch Date Picker Dialog
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        DatePickerDialog dpds = new DatePickerDialog( this,  AlertDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                   /*   addmeeting_edt_selectdate.setText(dayOfMonth + "-"
                              + dateFormatter.format (monthOfYear + 1) + "-" + year);*/
                        calendar.set(year, monthOfYear, dayOfMonth);
                        fromdate.setText(dateFormat.format(calendar.getTime()));

                    }
                }, year, month, day);
        dpds.show();
    }

    @Override
    public void onClick(View v) {
        if(v== fromdate){
            setFromDate();
        }
        if(v== todate){
            setTodateDate();
        }else if
            (v== btnsubmit ){
           String from=   fromdate.getText().toString();

         String to =   todate.getText().toString();

            SharedPreferences preferences = getSharedPreferences("MY",0);
            String memid = preferences.getString("FK","");

            String URL ="http://shine.quaeretech.com/ShineCityInfra.svc/MemberWiseEnquiryList/"+memid+ "/"+from+"/"+to+"/"+selectedstatus;

            new ViewEnquiryAsyncTask().execute(URL);
            }

    }
    private   class ViewEnquiryAsyncTask extends AsyncTask<String,Void,String> {

        ProgressDialog pDialog ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewEnquiryActivity.this,ProgressDialog.THEME_TRADITIONAL);
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
            viewEnquiryDataslist.clear();
            tv_viewinquiry_norecord.setVisibility(View.GONE);
            String Enq = null;
                pDialog.dismiss();
                if(s.equals("")){
                    tv_viewinquiry_norecord.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No Record Found" + s, Toast.LENGTH_LONG).show();
                }else if(s == null){
                    Toast.makeText(getApplicationContext(), "Server not responding" + s, Toast.LENGTH_LONG).show();
            }
                else
                if(!s.equals("")){

                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i =0 ; i<jsonArray.length();i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String enquirydate = jsonObject.getString("EnqDate");
                            String Status = jsonObject.getString("Status");
                             Enq = jsonObject.getString("Enq");
                            String reply = jsonObject.getString("Reply");



                            viewEnquiryDataslist.add(new ViewEnquiryData(enquirydate, Status, Enq, reply));



                        }
                        if(Enq.equals("null")){
                            viewEnquiryDataslist.clear();
                            adapter.notifyDataSetChanged();
                            tv_viewinquiry_norecord.setVisibility(View.VISIBLE);

                        }else{

                            listview_viewenquiry.setAdapter(adapter);
                            tv_viewinquiry_norecord.setVisibility(View.GONE);
                        }


                       // ListViewHelper.getListViewSize(listview_viewenquiry);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }

        }
    private static class MyAdapter extends ArrayAdapter {

        Activity aa;

        ArrayList<ViewEnquiryData> arrayList;

        MyAdapter(Activity c,int r, ArrayList<ViewEnquiryData> dataArrayList){
            super(c,r,dataArrayList);
            arrayList = dataArrayList;
            aa = c;
        }



        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arrayList;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder viewHolder = new ViewHolder();
            ViewEnquiryData viewEnquiryData = arrayList.get(position);
            if(convertView == null) {

                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewenquiry_adapter,null);


            }
            viewHolder.date=(TextView)convertView.findViewById(R.id.view_enquiry_enquirydate);
            viewHolder.status=(TextView)convertView.findViewById(R.id.view_enquiry_qstatus);
            viewHolder.enquiry=(TextView)convertView.findViewById(R.id.view_enquiry_enquiry);
            viewHolder.reply=(TextView)convertView.findViewById(R.id.view_enquiry_repliedon);


            viewHolder.date.setText(viewEnquiryData.getEnquirydate());
            viewHolder.enquiry.setText(viewEnquiryData.getEnquiry());
            viewHolder.status.setText(viewEnquiryData.getQuerystatus());
            viewHolder.reply.setText(viewEnquiryData.getReplyon());



            return convertView;
        }

        private   static class ViewHolder{
            TextView date;
            TextView status;
            TextView enquiry;
            TextView reply;


        }
    }


}
