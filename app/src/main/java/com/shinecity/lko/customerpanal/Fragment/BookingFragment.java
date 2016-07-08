package com.shinecity.lko.customerpanal.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shinecity.lko.customerpanal.BookingActivity;
import com.shinecity.lko.customerpanal.EMIPaymentActivity;
import com.shinecity.lko.customerpanal.R;
import com.shinecity.lko.customerpanal.WebViewMission99;

public class BookingFragment extends Fragment {

    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static final BookingFragment newInstance(String message)
    {
        BookingFragment f = new BookingFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        TextView txt_allbooking=(TextView)view.findViewById(R.id.txt_allbooking);
        TextView txt_emipay=(TextView)view.findViewById(R.id.txt_emipayment);
        TextView txt_copandetail=(TextView)view.findViewById(R.id.txt_coupandetail);
        TextView txt_request=(TextView)view.findViewById(R.id.txt_request_registry);
        TextView txt_report=(TextView)view.findViewById(R.id.txt_register_report);

        LinearLayout linear_allbooking =(LinearLayout)view.findViewById(R.id.linear_allbooking);
        LinearLayout linear_emipayments =(LinearLayout)view.findViewById(R.id.linear_emipayments);
        LinearLayout linear_cupondetails=(LinearLayout)view.findViewById(R.id.linear_cupondetails);
        LinearLayout linear_request_registry=(LinearLayout)view.findViewById(R.id.linear_request_registry);
        LinearLayout linear_registr_request=(LinearLayout)view.findViewById(R.id.linear_registr_request);


        final LinearLayout buttonwebview = (LinearLayout)view.findViewById(R.id.linearview_booking_webview);

        buttonwebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  buttonwebview.setBackgroundColor(Color.parseColor("#3B5998"));
                startActivity(new Intent(getActivity(), WebViewMission99.class));
            }
        });

        linear_allbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getFragmentManager().beginTransaction().replace(R.id.viewpager,new LoginFragment()).commit();
                Intent intent = new Intent(getActivity(), BookingActivity.class);
                Bundle b = new Bundle();
                b.putString("NAME","All");
                intent.putExtras(b);

                startActivity(intent);

            }
        });

        linear_emipayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), EMIPaymentActivity.class);
                intent.putExtra("EMI", "EMI");
                startActivity(intent);
            }
        });


        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"OstrichSans-Bold.otf");
        txt_allbooking.setTypeface(typeface);
        txt_emipay.setTypeface(typeface);
        txt_copandetail.setTypeface(typeface);
        txt_request.setTypeface(typeface);
        txt_report.setTypeface(typeface);

        linear_cupondetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookingActivity.class);
                intent.putExtra("COUPON", "coupon");
                startActivity(intent);
            }
        });

        linear_request_registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Functionality will be as soon",Toast.LENGTH_LONG).show();
            }
        });

        linear_registr_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Functionality will be as soon",Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }




}
