package com.shinecity.lko.customerpanal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.ModelData.AllBookingData;
import com.shinecity.lko.customerpanal.ModelData.ReceiptData;

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

public class ReceiptActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    private View mFab;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
   private static   ArrayList<ReceiptData> allreceiptlist = new ArrayList<>();
     private SharedPreferences sharedPreferences;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        mFab = findViewById(R.id.flexible_example_fab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_example_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.flexible_example_appbar);
        appbar.addOnOffsetChangedListener(this);


    /*    ImageView back = (ImageView)findViewById(R.id.ivrecipt_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        sharedPreferences =getSharedPreferences("MY", Context.MODE_PRIVATE);
        String loginid = sharedPreferences.getString("LOGINID","");
        listView =(ListView)findViewById(R.id.listview_receipt);
      //  String url ="http://shine.quaeretech.com/ShineCityInfra.svc/wspAllBookingsUP/"+loginid;
        String url = "http://shine.quaeretech.com/ShineCityInfra.svc/ReceiptDetails/"+loginid;

        new AllBookingAsyncTask().execute(url);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(i)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;

                ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
            }
        }


    }

    private   class AllBookingAsyncTask extends AsyncTask<String,Void,String> {

        ProgressDialog pDialog ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReceiptActivity.this,ProgressDialog.THEME_TRADITIONAL);
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

                allreceiptlist.clear();
                pDialog.dismiss();
                if(s == null){
                    Toast.makeText(ReceiptActivity.this, "" + s, Toast.LENGTH_LONG).show();
                }else
                if(s != ""){

                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i =0 ; i<jsonArray.length();i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String sno = jsonObject.getString("RIndex");
                            String InstAmount = jsonObject.getString("InstAmount");
                            String LateFee = jsonObject.getString("LateFee");
                            String DueDate = jsonObject.getString("DueDate");
                            String PaidOn = jsonObject.getString("PaidOn");
                            String ReceiptNo = jsonObject.getString("ReceiptNo");
                            String PmtMode = jsonObject.getString("PmtMode");
                            String Bank = jsonObject.getString("Bank");
                            String Date = jsonObject.getString("Date");
                            String ChqNo = jsonObject.getString("ChqNo");
                            String Status = jsonObject.getString("Status");
                            String PaidAmt = jsonObject.getString("PaidAmt");
                            String PaymentType = jsonObject.getString("PaymentType");

                            allreceiptlist.add(new ReceiptData(sno, InstAmount, LateFee, DueDate, PaidOn, ReceiptNo, PmtMode, Bank, Date,ChqNo,Status,PaidAmt,PaymentType));

                        }

                        MyAdapter adapter = new MyAdapter(ReceiptActivity.this,R.layout.recepit_detail_adapter, allreceiptlist);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        ListViewHelper.getListViewSize(listView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





            }

        }
    }


    private static class MyAdapter extends ArrayAdapter {

        Activity aa;

        ArrayList<ReceiptData> arrayList;

        MyAdapter(Activity c,int r, ArrayList<ReceiptData> dataArrayList){
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

            ReceiptData allBookingData = arrayList.get(position);
            if(convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recepit_detail_adapter,null);
                // LayoutInflater inflater =(LayoutInflater)aa.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

              /*
               LayoutInflater inflater= (LayoutInflater)aa.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row,null);*/

                viewHolder.sno=(TextView)convertView.findViewById(R.id.recepidetail_instalmentno);
                viewHolder.InstAmount=(TextView)convertView.findViewById(R.id.recepidetail_instamount);
                viewHolder.LateFee=(TextView)convertView.findViewById(R.id.recepidetail_latefee);
                viewHolder.DueDate=(TextView)convertView.findViewById(R.id.recepidetail_duedate);
                viewHolder.PaidOn=(TextView)convertView.findViewById(R.id.recepidetail_paidon);
                viewHolder.ReceiptNo=(TextView)convertView.findViewById(R.id.recepidetail_recepitno);
                viewHolder.PmtMode=(TextView)convertView.findViewById(R.id.recepidetail_paymentmode);
                viewHolder.Bank=(TextView)convertView.findViewById(R.id.recepidetail_bank);
                viewHolder.Date=(TextView)convertView.findViewById(R.id.recepidetail_date);

                viewHolder.ChqNo=(TextView)convertView.findViewById(R.id.recepidetail_chequeno);
                viewHolder.Status=(TextView)convertView.findViewById(R.id.recepidetail_status);
                viewHolder.PaidAmt=(TextView)convertView.findViewById(R.id.recepidetail_paidamount);
                viewHolder.PaymentType=(TextView)convertView.findViewById(R.id.recepidetail_paymenttype);

                convertView.setTag(viewHolder);

            }else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            viewHolder.sno.setText(allBookingData.getSno());
            viewHolder.InstAmount.setText(allBookingData.getInstAmount());
            viewHolder.LateFee.setText(allBookingData.getLateFee());
            viewHolder.DueDate.setText(allBookingData.getDueDate());
            viewHolder.PaidOn.setText(allBookingData.getPaidOn());
            viewHolder.ReceiptNo.setText(allBookingData.getReceiptNo());
            viewHolder.PmtMode.setText(allBookingData.getPmtMode());
            viewHolder.Bank.setText(allBookingData.getBank());
            viewHolder.Date.setText(allBookingData.getDate());
            viewHolder.ChqNo.setText(allBookingData.getChqNo());
            viewHolder.Status.setText(allBookingData.getStatus());
            viewHolder.PaidAmt.setText(allBookingData.getPaidAmt());
            viewHolder.PaymentType.setText(allBookingData.getPaymentType());



            return convertView;
        }

        private static class ViewHolder{
            TextView sno ;
            TextView InstAmount ;
            private TextView LateFee;
            private TextView DueDate ;
            private TextView PaidOn  ;
            private TextView ReceiptNo;
            private TextView PmtMode ;
            private TextView Bank ;
            private TextView Date ;
            private TextView ChqNo;
            private  TextView Status;
            private  TextView PaidAmt ;
            private TextView PaymentType ;
        }
    }

}
