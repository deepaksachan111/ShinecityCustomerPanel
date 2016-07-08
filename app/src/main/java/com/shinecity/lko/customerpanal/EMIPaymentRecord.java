package com.shinecity.lko.customerpanal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shinecity.lko.customerpanal.ModelData.EMIPaymentRecordData;
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

public class EMIPaymentRecord extends AppCompatActivity {
    private ArrayList<EMIPaymentRecordData> emiPaymentRecordDataArrayList = new ArrayList<>();
    private String URL;
    private String responce;

    private ProgressDialog pDialog ;

    private static ArrayAdapter adapter;
    private static ListView recyclerView;
    TextView tvanme,tv_emi_plotholder_id,tv_memberid,tv_installtype,tv_product,tv_installmentamount,tv_bookindate,tv_plotstatus,tv_plotamount,tv_plotsize,tv_noof_installment,tv_allotmentdate,tv_paymentphase;
      String PlotHolderId, MemberId,InstallmentType,Product, InstallmentAmount, BookingDate, TotalPaidAmount,Name,PlotStatus, PlotAmount, PlotSize, No_of_Installment, AllotmentDate,PaymentPhase ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emipayment_record);
      pDialog = new ProgressDialog(EMIPaymentRecord.this,ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setMessage("Loading");
        pDialog.show();
        recyclerView = (ListView) findViewById(R.id.my_recycler_view);


         tvanme =(TextView) findViewById(R.id.emi_name);
        tv_emi_plotholder_id =(TextView) findViewById(R.id.emi_plotholder_id);
        tv_memberid =(TextView) findViewById(R.id.emi_memberid);
        tv_installtype =(TextView) findViewById(R.id.emi_installmettype);
        tv_product=(TextView) findViewById(R.id.emi_product);
        tv_installmentamount =(TextView) findViewById(R.id.emi_installmentamount);
        tv_bookindate =(TextView) findViewById(R.id.emi_bookingdate);
        tv_plotstatus =(TextView) findViewById(R.id.emi_plotstatus);
        tv_plotamount =(TextView) findViewById(R.id.emi_plotamount);
        tv_plotsize =(TextView) findViewById(R.id.emi_plotsize);
        tv_noof_installment =(TextView) findViewById(R.id.emi_no_of_installment);
        tv_allotmentdate =(TextView) findViewById(R.id.emi_allotmentdate);
        tv_paymentphase =(TextView) findViewById(R.id.emi_paymentphase);


        adapter = new CustomAdapter(this, R.layout.activity_emipayment_record_adadpter,emiPaymentRecordDataArrayList);


        String selectedText = getIntent().getStringExtra("selecteed");
        String BOOKING = getIntent().getStringExtra("BOOKING");
        String PLOT = getIntent().getStringExtra("PLOT");

       SharedPreferences sharedPreferences =getSharedPreferences("MY", Context.MODE_PRIVATE);
        String loginid = sharedPreferences.getString("LOGINID", "");
        URL = "http://shine.quaeretech.com/ShineCityInfra.svc/GetEMIPayment/"+BOOKING +"/53/InstallmentPayment/"+ loginid;
        thred();
    }
    private void  thred() {

        Thread t = new Thread() {

            public void run(){
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URL);
                HttpResponse httpResponse = null;
                try {
                    httpResponse = httpClient.execute(httpGet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpEntity httpEntity = httpResponse.getEntity();
                try {
                    responce = EntityUtils.toString(httpEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                parseJSON(responce);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String re = responce .toString();

                                 tvanme.setText(Name);
                                 tv_emi_plotholder_id.setText(PlotHolderId);
                                 tv_memberid.setText(MemberId);
                                 tv_installtype.setText(InstallmentType);
                                 tv_product.setText(Product);
                                 tv_installmentamount.setText(InstallmentAmount);
                                 tv_bookindate.setText(BookingDate);
                                 tv_plotstatus.setText(PlotStatus);
                                 tv_plotamount.setText(PlotAmount);
                                 tv_plotsize.setText(PlotSize);
                                 tv_noof_installment.setText(No_of_Installment);
                                 tv_allotmentdate .setText(AllotmentDate);
                                 tv_paymentphase.setText(PaymentPhase);





                        pDialog.dismiss();
                        recyclerView.setAdapter(adapter);
                        ListViewHelper.getListViewSize(recyclerView);
                       /* spinnersite.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();*/
                    }
                });



            }



        };t.start();

    }

    private void parseJSON(String s){

        try {
            JSONArray jsonArray = new JSONArray(s);

            for(int j= 0; j<1;j++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                  PlotHolderId= jsonObject.getString("PlotHolderId");
                 MemberId= jsonObject.getString("MemberId");
                  InstallmentType= jsonObject.getString("InstallmentType");
                  Product= jsonObject.getString("Product");
                 InstallmentAmount= jsonObject.getString("InstallmentAmount");
                 BookingDate= jsonObject.getString("BookingDate");
                 TotalPaidAmount= jsonObject.getString("TotalPaidAmount");
                Name= jsonObject.getString("Name");
                 PlotStatus= jsonObject.getString("PlotStatus");
                  PlotAmount= jsonObject.getString("PlotAmount");
                 PlotSize= jsonObject.getString("PlotSize");
                  No_of_Installment= jsonObject.getString("No_of_Installment");
                 AllotmentDate= jsonObject.getString("AllotmentDate");
                 PaymentPhase= jsonObject.getString("PaymentPhase");
            }
            for(int i = 1; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                //.............................textview.....................




                //....................................endfor textview..........

                //..............................for Listview.............
                String InstallmentNo= jsonObject.getString("InstallmentNo");
                String  InstallmentAmnt= jsonObject.getString("InstallmentAmnt");
                String LateFee= jsonObject.getString("LateFee");
                String    InstallmentDueDate= jsonObject.getString("InstallmentDueDate");
                String  InstallmentPaidDate= jsonObject.getString("InstallmentPaidDate");
                String         ReceiptNo= jsonObject.getString("ReceiptNo");
                String PaymentMode= jsonObject.getString("PaymentMode");
                String      BankName= jsonObject.getString("BankName");
                String  ChequeDDNo= jsonObject.getString("ChequeDDNo");
                String        ChequeDDDate= jsonObject.getString("ChequeDDDate");
                String PaidAmount= jsonObject.getString("PaidAmount");
                //..............................for Listview.............

                emiPaymentRecordDataArrayList.add(new EMIPaymentRecordData(InstallmentNo,InstallmentAmnt,LateFee,InstallmentDueDate,InstallmentPaidDate,ReceiptNo,PaymentMode,BankName,ChequeDDNo,ChequeDDDate,PaidAmount));

           /*     sitelist.add(PK_SiteId);

                getSitelist.add(new SiteData(SiteName,PK_SiteId));*/


            }




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   private  class CustomAdapter extends ArrayAdapter{
       Activity aa;
        private ArrayList<EMIPaymentRecordData> dataSet;

          class MyViewHolder  {

            TextView  InstallmentNo;
            TextView      InstallmentAmnt;
            TextView  LateFee;
            TextView        InstallmentDueDate;
            TextView InstallmentPaidDate;
            TextView         ReceiptNo;
            TextView PaymentMode;
            TextView     BankName;
            TextView ChequeDDNo;
            TextView       ChequeDDDate;
            TextView PaidAmount;

            }


        public CustomAdapter(Activity context, int res,ArrayList<EMIPaymentRecordData> data) {
            super(context, 0, data);
            this.dataSet = data;
            this .aa = context;
        }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           // super.getView(position, convertView, parent);
           View view = convertView;
           MyViewHolder myViewHolder ;
           if(view == null) {
               view = LayoutInflater.from(getContext())
                       .inflate(R.layout.activity_emipayment_record_adadpter, parent,false);
            /*   LayoutInflater inflater = (LayoutInflater)aa.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               view = inflater.inflate(R.layout.activity_emipayment_record_adadpter,null,false);*/

               myViewHolder  = new MyViewHolder();
               myViewHolder.InstallmentNo = (TextView) view.findViewById(R.id.emi_installmentno);
               myViewHolder.InstallmentAmnt = (TextView) view.findViewById(R.id.emi_installmentamt);
               myViewHolder.LateFee = (TextView) view.findViewById(R.id.emi_latefee);
               myViewHolder.InstallmentDueDate = (TextView) view.findViewById(R.id.emi_duedate);
               myViewHolder.InstallmentPaidDate = (TextView) view.findViewById(R.id.emi_installment_paiddate);
               myViewHolder.ReceiptNo = (TextView) view.findViewById(R.id.emi_recepitno);
               myViewHolder.PaymentMode = (TextView) view.findViewById(R.id.emi_paymentmode);
               myViewHolder.BankName = (TextView) view.findViewById(R.id.emi_bankname);
               myViewHolder.ChequeDDNo = (TextView) view.findViewById(R.id.emi_chequeddno);
               myViewHolder.ChequeDDDate = (TextView) view.findViewById(R.id.emi_chequedd_date);
               myViewHolder.PaidAmount = (TextView) view.findViewById(R.id.emi_paidamount);

               view.setTag(myViewHolder);
           }else {
               myViewHolder = (MyViewHolder)view.getTag();

           }

           myViewHolder.InstallmentNo.setText(dataSet.get(position).getInstallmentNo());
           myViewHolder.InstallmentAmnt.setText(dataSet.get(position).getInstallmentAmnt());
           myViewHolder.LateFee.setText(dataSet.get(position).getLateFee());

           myViewHolder.InstallmentDueDate.setText(dataSet.get(position).getInstallmentDueDate());
           myViewHolder.InstallmentPaidDate.setText(dataSet.get(position).getInstallmentPaidDate());
           myViewHolder.ReceiptNo.setText(dataSet.get(position).getReceiptNo());
           myViewHolder.PaymentMode.setText(dataSet.get(position).getPaymentMode());
           myViewHolder.BankName.setText(dataSet.get(position).getBankName());
           myViewHolder.ChequeDDNo.setText(dataSet.get(position).getChequeDDNo());
           myViewHolder.ChequeDDDate.setText(dataSet.get(position).getChequeDDDate());
           myViewHolder.PaidAmount.setText(dataSet.get(position).getPaidAmount());

           return view;

       }




    /*    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView  InstallmentNo = holder.InstallmentNo;
            TextView      InstallmentAmnt = holder.InstallmentAmnt;
            TextView  LateFee = holder.LateFee;
            TextView        InstallmentDueDate = holder.InstallmentDueDate;
            TextView InstallmentPaidDate = holder.InstallmentPaidDate;
            TextView         ReceiptNo= holder.ReceiptNo;
            TextView PaymentMode= holder.PaymentMode;
            TextView     BankName = holder.BankName;
            TextView ChequeDDNo = holder.ChequeDDNo;
            TextView       ChequeDDDate = holder.ChequeDDDate;
            TextView PaidAmount = holder.PaidAmount;



        }*/


    }

}
