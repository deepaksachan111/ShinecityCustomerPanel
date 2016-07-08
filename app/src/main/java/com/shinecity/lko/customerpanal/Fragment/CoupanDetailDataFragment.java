package com.shinecity.lko.customerpanal.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.HorizontalListView;
import com.shinecity.lko.customerpanal.ListViewHelper;
import com.shinecity.lko.customerpanal.ModelData.CouponDetailData;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoupanDetailDataFragment extends Fragment {
    ListView horizontalListView;
    ArrayList<CouponDetailData> stringsdata = new ArrayList<>();

    public CoupanDetailDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_coupan_detail_data, container, false);
       LayoutInflater layoutInflater = getActivity().getLayoutInflater();
         ViewGroup viewGroup =(ViewGroup)layoutInflater.inflate(R.layout.coupandetaildata_adapter_header,horizontalListView);

         horizontalListView =(ListView)v.findViewById(R.id.listview_coupandetail);
            v.findViewById(R.id.linear_actionbar_coupobdetaiback).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
       // horizontalListView.addHeaderView(viewGroup);
        SharedPreferences preferences = getActivity().getSharedPreferences("MY",0);
        String memid=preferences.getString("FK","");

          String url ="http://shine.quaeretech.com/ShineCityInfra.svc/GetCoupanReportInvestor/"+memid;
          new CoupanDetailAsyncTask().execute(url);



        return  v;
    }


    public class CoupanDetailAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity(),ProgressDialog.THEME_TRADITIONAL);

            //  ProgressDialog.THEME_HOLO_DARK

            pDialog.setMessage("Loading Details ...");
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
       if(getActivity()!= null) {
           pDialog.dismiss();
           if (ss == null) {
               Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_LONG).show();

           } else


               try {

                   JSONArray jsonArray = new JSONArray(ss);
                   int noOfObjects = jsonArray.length();
                   Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");
                   // pd.dismiss();
                   for (int j = 0; j < jsonArray.length(); j++) {

                       JSONObject jObj = jsonArray.getJSONObject(j);
                       //   Log.v("No of times " + j, "shhhhhhhhh");
                       // int k = j + 1;
                       // detailNo = ""+k;
                       String CouponNo = jObj.getString("CouponNo");
                       String CustomerName = jObj.getString("CustomerName");
                       String PlotNo = jObj.getString("PlotNo");
                       String SiteName = jObj.getString("SiteName");
                       String UsedDate = jObj.getString("UsedDate");
                       String UsedOn = jObj.getString("UsedOn");
                       String RIndex = jObj.getString("RIndex");

                       stringsdata.add(new CouponDetailData(RIndex, CouponNo, PlotNo, CustomerName, UsedOn, UsedDate));


                       Adapter adapterheader = new Adapter(getActivity(), R.layout.coupondetaildata_adapter, stringsdata);
                       horizontalListView.setAdapter(adapterheader);
                       //ListViewHelper.getListViewSize(mLinearListView);


                   }
                /*    MyAdapter myAdapter = new MyAdapter(getActivity(), R.layout.row,mArrayListData);
                    mLinearListView.setAdapter(myAdapter);*/


               } catch (JSONException e) {
                   e.printStackTrace();
                   System.out.println(" Exception is caught here ......." + e.toString());
               }


       }
        }
    }
    private class Adapter extends ArrayAdapter {
       ArrayList<CouponDetailData> dataObjects;
        Activity activity;
        int deepColor = Color.parseColor("#fde9ec");
        int deepColor2 = Color.parseColor("#defde0");
        private int[] colors = new int[] {deepColor,deepColor2 };
        public Adapter(Activity context, int resource, ArrayList<CouponDetailData> a) {
            super(context, resource);
            this.activity = context;
            this.dataObjects = a;
        }


        @Override
        public int getCount() {
            return dataObjects.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
               convertView= LayoutInflater.from(getContext()).inflate(R.layout.coupondetaildata_adapter, null);
                int colorPos = position % colors.length;
                convertView.setBackgroundColor(colors[colorPos]);
            }


            CouponDetailData couponDetailData = dataObjects.get(position);
            TextView cd_sn = (TextView) convertView.findViewById(R.id.cd_sn);
            TextView coupanno = (TextView) convertView.findViewById(R.id.cd_coupandetail);
            TextView plotNo = (TextView) convertView.findViewById(R.id.cd_plotno);
            TextView customername = (TextView) convertView.findViewById(R.id.cd_customercode);
            TextView useon = (TextView) convertView.findViewById(R.id.cd_usedon);
            TextView usedate = (TextView) convertView.findViewById(R.id.cd_useddate);

            LayoutInflater inflaters = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // View mLinearView = inflaters.inflate(R.layout.coupandetaildata_adapter_header, null);
           // TextView title2 = (TextView) mLinearView.findViewById(R.id.title2);
           // li.addView(mLinearView);

             cd_sn.setText(couponDetailData.getSnno());
            coupanno.setText(couponDetailData.getCoupandetail());
            plotNo.setText(couponDetailData.getPlotno());
            customername.setText(couponDetailData.getCustomername());
            useon.setText(couponDetailData.getUsedon());
            usedate.setText(couponDetailData.getUseddate());
           // title.setText(dataObjects.get(position));
           // title.setText(dataObjects.get(position));

            return convertView;
        }

    };

    @Override
    public void onStop() {
        super.onStop();

       new CoupanDetailAsyncTask().cancel(true);
    }
}
