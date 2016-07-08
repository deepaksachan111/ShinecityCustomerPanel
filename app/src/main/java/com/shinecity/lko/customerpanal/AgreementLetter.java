package com.shinecity.lko.customerpanal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shinecity.lko.customerpanal.ModelData.AgreementLetterData;
import com.shinecity.lko.customerpanal.ModelData.EMIPaymentRecordData;

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

public class AgreementLetter extends AppCompatActivity {

    CustumAdapter arrayAdapter;

    ArrayList<AgreementLetterData> getArrayListdata = new ArrayList<>();
    private ListView listview_agreementletter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_letter);


        listview_agreementletter =(ListView)findViewById(R.id.listview_agreementletter);

        ViewGroup listheader = (ViewGroup) getLayoutInflater().inflate(R.layout.agreementletter_adapter_header,null);

        arrayAdapter = new CustumAdapter(this,R.layout.agreementletter_adapter,getArrayListdata);

        listview_agreementletter.addHeaderView(listheader);


        SharedPreferences sharedPreferences =getSharedPreferences("MY", Context.MODE_PRIVATE);
        String loginid = sharedPreferences.getString("LOGINID", "");

        String URL ="http://shine.quaeretech.com/ShineCityInfra.svc/AgreementLetter/"+loginid;
        new AgreementDataAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,URL);
    }


    private class AgreementDataAsyncTask extends AsyncTask<String ,Void,String>{
        ProgressDialog pDialog;
        private  String responce;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog= new ProgressDialog(AgreementLetter.this,ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setMessage("Loading");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient() ;
            HttpGet httpGet = new HttpGet(params[0]);
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                 responce = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return responce;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
             pDialog.dismiss();
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i =0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String RIndex = jsonObject.getString("RIndex");
                    String BookingID = jsonObject.getString("BookingID");
                    String PlotNo = jsonObject.getString("PlotNo");
                    String SiteName = jsonObject.getString("SiteName");
                    String PaymentPlan = jsonObject.getString("PaymentPlan");
                    String CustomerId = jsonObject.getString("CustomerId");
                    String CustomerName = jsonObject.getString("CustomerName");
                    String AssociateId = jsonObject.getString("AssociateId");
                    String AssociateName = jsonObject.getString("AssociateName");
                    String BSP = jsonObject.getString("BSP");


                getArrayListdata.add(new AgreementLetterData(RIndex,BookingID,PlotNo,SiteName,PaymentPlan,CustomerId,CustomerName,AssociateId,AssociateName,BSP));

                }
                listview_agreementletter.setAdapter(arrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    }

      class CustumAdapter extends ArrayAdapter {

        Context context;
        ArrayList<AgreementLetterData> arrayList;

        static class Viewholder {
            TextView index;
            TextView bookingid;
            TextView plotno;
            TextView sitename;
            TextView paymentplan;
            TextView customerid;
            TextView customername;
            TextView associateid;
            TextView associatename;
            TextView bsp;

        }
        public CustumAdapter(Context context, int resource,ArrayList<AgreementLetterData>arrayList) {
            super(context, 0,arrayList);
            this.context = context;
            this.arrayList = arrayList;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Viewholder viewholder;
            View view = convertView;
            if(view== null){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agreementletter_adapter, null);
              /*  LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.agreementletter_adapter,null);*/
                viewholder = new Viewholder();
                viewholder.index=(TextView)view.findViewById(R.id.agreementletter_sn);
                viewholder.bookingid=(TextView)view.findViewById(R.id.agreementletter_bookingid);
                viewholder.plotno=(TextView)view.findViewById(R.id.agreementletter_plotno);
                viewholder.sitename=(TextView)view.findViewById(R.id.agreementletter_sitename);
                viewholder.paymentplan =(TextView)view.findViewById(R.id.agreementletter_paymentplan);
                viewholder.customerid=(TextView)view.findViewById(R.id.agreementletter_customerid);
                viewholder.customername=(TextView)view.findViewById(R.id.agreementletter_customername);
                viewholder.associateid=(TextView)view.findViewById(R.id.agreementletter_associateid);
                viewholder.associatename=(TextView)view.findViewById(R.id.agreementletter_associatename);
                viewholder.bsp=(TextView)view.findViewById(R.id.agreementletter_bsp);

                view.setTag(viewholder);

            }else {
              viewholder =(Viewholder) view.getTag();
            }

            AgreementLetterData agreementLetterData = (AgreementLetterData)arrayList.get(position);

            viewholder.index.setText(agreementLetterData.getIndex());
            viewholder.bookingid.setText(agreementLetterData.getBookingid());
            viewholder.plotno.setText(agreementLetterData.getPlotno());
            viewholder.sitename.setText(agreementLetterData.getSitename());
            viewholder.paymentplan.setText(agreementLetterData.getPaymentplan());
            viewholder.customerid.setText(agreementLetterData.getCustomerid());
            viewholder.customername.setText(agreementLetterData.getCustomername());
            viewholder.associateid.setText(agreementLetterData.getAssociateid());
            viewholder.associatename.setText(agreementLetterData.getAssociatename());
            viewholder.bsp.setText(agreementLetterData.getBsp());
            return view;

        }
    }


