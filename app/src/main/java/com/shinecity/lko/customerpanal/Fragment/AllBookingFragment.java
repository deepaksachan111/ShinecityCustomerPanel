package com.shinecity.lko.customerpanal.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.ModelData.AllBookingData;
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

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllBookingFragment extends Fragment {
         private SharedPreferences sharedPreferences;
    static   ArrayList<AllBookingData> allBookingDataslist = new ArrayList<>();
    private  ListView listView;
    public AllBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_booking, container, false);

        LinearLayout back = (LinearLayout)view.findViewById(R.id.linear_allbooking_back);
        sharedPreferences = getActivity().getSharedPreferences("MY", Context.MODE_PRIVATE);
        String loginid = sharedPreferences.getString("LOGINID","");
         listView =(ListView)view.findViewById(R.id.listview_allbooking);
        String url ="http://shine.quaeretech.com/ShineCityInfra.svc/wspAllBookingsUP/"+loginid;

        new AllBookingAsyncTask().execute(url);
     /*   Intent i = getActivity().getIntent();
        Bundle b = i.getExtras();
        String t = b.getString("NAME");*/



        return view;
    }


    private   class AllBookingAsyncTask extends AsyncTask<String,Void,String>{

        ProgressDialog pDialog ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity(),ProgressDialog.THEME_TRADITIONAL);
            pDialog.setMessage("Loading");
            pDialog.show();
        }



        @Override
        protected String doInBackground(String... params) {
            String response = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet  httpGet = new HttpGet(params[0]);

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                 response = EntityUtils.toString(httpEntity);
                Log.v("Responce",response);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (getActivity() != null) {
                allBookingDataslist.clear();
                pDialog.dismiss();
                if(s == null){
                    Toast.makeText(getActivity(),""+s,Toast.LENGTH_LONG).show();
                }else
                if(s != ""){

                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i =0 ; i<jsonArray.length();i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String sno = jsonObject.getString("RIndex");
                            String bookingid = jsonObject.getString("BookingID");
                            String plotno = jsonObject.getString("PlotNo");
                            String sitename = jsonObject.getString("SiteName");
                            String paymentplan = jsonObject.getString("PaymentPlan");
                            String customer = jsonObject.getString("CustomerName");
                            String sales = jsonObject.getString("AssociateName");
                            String bookingdate = jsonObject.getString("BookingDate");
                            String bsp = jsonObject.getString("BSP");

                            allBookingDataslist.add(new AllBookingData(sno,bookingid,plotno,sitename,paymentplan,customer,sales,bookingdate,bsp));
                        }

                        MyAdapter adapter = new MyAdapter(getActivity(),R.layout.allbookingdata_adapter,allBookingDataslist);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }

        }
    }


    private static class MyAdapter extends ArrayAdapter {

        Activity aa;

        ArrayList<AllBookingData> arrayList;

        MyAdapter(Activity c,int r, ArrayList<AllBookingData> dataArrayList){
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

                 AllBookingData allBookingData = arrayList.get(position);
            if(convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.allbookingdata_adapter,null);


              /*
               LayoutInflater inflater= (LayoutInflater)aa.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row,null);*/

                viewHolder.txtsn=(TextView)convertView.findViewById(R.id.allbooking_sn);
                viewHolder.bookinid=(TextView)convertView.findViewById(R.id.allbooking_bookingid);
                viewHolder.plot=(TextView)convertView.findViewById(R.id.allbooking_plotno);
                viewHolder.sales=(TextView)convertView.findViewById(R.id.allbooking_sales);
                viewHolder.customer=(TextView)convertView.findViewById(R.id.allbooking_customer);
                viewHolder.sitename=(TextView)convertView.findViewById(R.id.allbooking_sitename);
                viewHolder.paymentpaln=(TextView)convertView.findViewById(R.id.allbooking_paymentplan);
                viewHolder.bookingdate=(TextView)convertView.findViewById(R.id.allbooking_bookingdate);
                viewHolder.bsp=(TextView)convertView.findViewById(R.id.allbooking_bsp);

                convertView.setTag(viewHolder);

            }else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
                viewHolder.txtsn.setText(allBookingData.getSnno());
                viewHolder.bookinid.setText(allBookingData.getBookingid());
                viewHolder.sales.setText(allBookingData.getSales());



           // String text = "This text is white. <font color=\"blue\">.</font>";
            String string = allBookingData.getCustomer();
            string = string.substring(0, string.length()-14);
            String newString = allBookingData.getCustomer().substring(allBookingData.getCustomer().length() - 14);


            StringBuffer buffer = new StringBuffer();

            char[] s =string.toCharArray() ;
            int counter = 0;
            for( int i=0; i<s.length; i++ ) {

                char  c = s[i];
                  buffer.append(c);
                    counter++;
            }

            String bff = buffer.toString();
            if(counter > 12){
                viewHolder.customer.setText(Html.fromHtml(string+  "<font color='#009900'  size:'2'>"+ newString+"</font>"));
            }else{
                viewHolder.customer.setText(allBookingData.getCustomer());
            }

           // int color = getContext().getResources().getColor(android.R.color.holo_blue_light);

             //  String text = newString + "<font color=\"#FFFFFF\">" + newString + "</font>";


                viewHolder.sitename.setText(allBookingData.getSitename());
               viewHolder.paymentpaln.setText(allBookingData.getPaymentplan());
                viewHolder.bookingdate.setText(allBookingData.getBookingdate());
                viewHolder.bsp.setText(allBookingData.getBsp());




            return convertView;
        }

private static class ViewHolder{
    TextView txtsn;
    TextView bookinid;
    TextView plot;
    TextView sales;
    TextView customer;
    TextView sitename;
    TextView paymentpaln;
    TextView bookingdate;
    TextView bsp;

        }
    }


    @Override
    public void onStop() {
        super.onStop();

        new AllBookingAsyncTask().cancel(true);
    }
}
