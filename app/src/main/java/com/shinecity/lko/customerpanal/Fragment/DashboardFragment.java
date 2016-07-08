package com.shinecity.lko.customerpanal.Fragment;

/**
 * Created by QServer on 5/28/2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import com.shinecity.lko.customerpanal.ListViewHelper;
import com.shinecity.lko.customerpanal.ModelData.AllBookingData;
import com.shinecity.lko.customerpanal.ModelData.DashboardData;
import com.shinecity.lko.customerpanal.ModelData.DbHelper;
import com.shinecity.lko.customerpanal.ModelData.MemberDetailData;
import com.shinecity.lko.customerpanal.R;
import com.shinecity.lko.customerpanal.WebViewMission99;

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
import java.util.List;

/**
 * Created by Ratan on 7/29/2015.
 */
public class DashboardFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private  TextView username,userid,panno,mobileno,email;
    SharedPreferences.Editor editor;
    private ListView mLinearListView;
    private    DbHelper dbHelper ;

    public   static  String res;

    private  ArrayList<AllBookingData> allBookingDataslist = new ArrayList<AllBookingData>();
    private  ArrayList<AllBookingData> getAllBookingDataslist= new ArrayList<>();
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static final DashboardFragment newInstance(String message)
    {
        DashboardFragment f = new DashboardFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_layout, null);
        sharedPreferences = getActivity().getSharedPreferences("MY", 0);
        mLinearListView = (ListView)view. findViewById(R.id.gallarty);
        username =(TextView)view.findViewById(R.id.dashboard_username);
        userid=(TextView)view.findViewById(R.id.dashboard_userid);
        panno =(TextView)view.findViewById(R.id.dasboard_panno);
        mobileno=(TextView)view.findViewById(R.id.dasboard_mobile);
        email =(TextView)view.findViewById(R.id.dasboard_email);

        LinearLayout buttonwebview=(LinearLayout)view.findViewById(R.id.btn_dsahboard_shinemission);

        buttonwebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WebViewMission99.class));
            }
        });


    /*   LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View  viewGroup = (View)layoutInflater.inflate(R.layout.row_header,null);
        mLinearListView.addHeaderView(viewGroup);*/

        getAllBookingDataslist.clear();
         dbHelper  = new DbHelper(getActivity());
        List<MemberDetailData> datas = dbHelper.getAllMemberData();
        if(datas.size()!=0) {
            for (MemberDetailData memberDetailData : datas){

                String name = memberDetailData.getName();
                String loginid = memberDetailData.getLoginid();
                username.setText(name);
                userid.setText(loginid);
                panno.setText(memberDetailData.getPanno());
                mobileno.setText(memberDetailData.getMobileno());
                email.setText(memberDetailData.getEmail());


                editor = sharedPreferences.edit();
                editor.putString("LOGINID",loginid);
                editor.commit();

            }

        }


        else {
            String fkid= sharedPreferences.getString("FK", "");
            String branchid=sharedPreferences.getString("BRANCHID","");
            String memberdetailURL =  "http://shine.quaeretech.com/ShineCityInfra.svc/GetMemberDetails/"+fkid+"/"+branchid;
            new MemberdataAsynkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,memberdetailURL);

          // AllBookingFragment allBookingFragment = new AllBookingFragment();

         //   AllBookingFragment.AllBookingAsyncTask sd= allBookingFragment.new AllBookingAsyncTask();
          //  new AllBookingFragment().new AllBookingAsyncTask().execute();

           // AllBookingFragment.AllBookingAsyncTask oi = new AllBookingFragment().new AllBookingAsyncTask();
            //oi.execute(url);
        }

        List<AllBookingData> allBookingDataslist = dbHelper.getAllBookingData();
        if(allBookingDataslist.size() != 0){
          for(AllBookingData allBookingData : allBookingDataslist){

              String sn = allBookingData.getSnno();
              String plot = allBookingData.getPlotno();
              String sitename = allBookingData.getSitename();
              String bsp = allBookingData.getBsp();

              getAllBookingDataslist.add(new AllBookingData(sn, plot, sitename, bsp));

          }


            MyAdapter adapter = new MyAdapter(getActivity(), R.layout.row, getAllBookingDataslist);
            mLinearListView.setAdapter(adapter);
            ListViewHelper.getListViewSize(mLinearListView);

            adapter.notifyDataSetChanged();
        }else{
            String loginids = sharedPreferences.getString("LOGINID","");
            String url ="http://shine.quaeretech.com/ShineCityInfra.svc/wspAllBookingsUP/"+loginids;
            new AllBookingAsyncTask().execute(url);

        }

        return view;
    }




    public class MemberdataAsynkTask extends AsyncTask<String, Void, String> {
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
            res = ss;
            pDialog.dismiss();
            if (res == null) {
                Toast.makeText(getActivity(), "Internet not available ", Toast.LENGTH_LONG).show();

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
                        String Fkid = jObj.getString("FK_MemId");
                        String Name = jObj.getString("Name");
                        String PanNo = jObj.getString("PanNo");
                        String FathersName = jObj.getString("FathersName");
                        String LoginId = jObj.getString("LoginId");
                        String PermanentContact = jObj.getString("PermanentContact");
                        String Email = jObj.getString("Email");
                        String Title = jObj.getString("Title");
                        String contact_response_code = jObj.getString("Response");

                        sharedPreferences = getActivity().getSharedPreferences("MY", Context.MODE_PRIVATE);
                        SharedPreferences.Editor  editor= sharedPreferences.edit();
                        editor.putString("LOGINID", LoginId);
                        editor.commit();

                        username.setText(Name);
                        userid.setText(LoginId);
                        panno.setText(PanNo);
                        mobileno.setText(PermanentContact);
                        email.setText(Email);
                        dbHelper.insertMemberData(new MemberDetailData(PanNo,Fkid,FathersName,LoginId,PermanentContact,Name,Email,Title));


                    }
                }

                 catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(" Exception is caught here ......." + e.toString());
                }
        }
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
                            dbHelper.insertAllBookingData(new AllBookingData(sno, plotno, sitename, bsp));


                        }

                        MyAdapter adapter = new MyAdapter(getActivity(),R.layout.row,allBookingDataslist);
                         mLinearListView.setAdapter(adapter);
                        ListViewHelper.getListViewSize(mLinearListView);
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

                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null);


            }
            viewHolder.txtsn=(TextView)convertView.findViewById(R.id.dashboard_sno);
            viewHolder.plot=(TextView)convertView.findViewById(R.id.dashboard_plotno);
            viewHolder.sitename=(TextView)convertView.findViewById(R.id.dashboard_sitename);
            viewHolder.bsp=(TextView)convertView.findViewById(R.id.dashboard_bsp);


            viewHolder.txtsn.setText(allBookingData.getSnno());
            viewHolder.plot.setText(allBookingData.getPlotno());
            viewHolder.sitename.setText(allBookingData.getSitename());
            viewHolder.bsp.setText(allBookingData.getBsp());



            return convertView;
        }

      private   static class ViewHolder{
            TextView txtsn;
            TextView plot;
            TextView sitename;
            TextView bsp;


    }
    }

}










    /*public class Adapter extends ArrayAdapter {
        Context context;
        LayoutInflater inflater;
        private String[] mSimpleListValues = new String[]{"Android", "iPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2"};
        int count = 0;

        public Adapter(Context context,ArrayList<String> arrayList) {
            super(context,0,arrayList);
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mSimpleListValues.length;
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
            Holder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.dashboard_layout_listview, null);
                holder = new Holder();
                holder.tvSSID = (TextView) view.findViewById(R.id.textView1);


                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            holder.tvSSID.setText(mSimpleListValues[position]);
            view.setLayoutParams(new LinearLayout.LayoutParams(135, 60));
            return view;
        }

        class Holder {
            TextView tvSSID;
        }
    }*/
