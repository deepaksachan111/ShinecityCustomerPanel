package com.shinecity.lko.customerpanal.Fragment;



import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.shinecity.lko.customerpanal.AgreementLetter;
import com.shinecity.lko.customerpanal.ContactUsAcivity;
import com.shinecity.lko.customerpanal.LedgerActivity;
import com.shinecity.lko.customerpanal.R;
import com.shinecity.lko.customerpanal.ReceiptActivity;
import com.shinecity.lko.customerpanal.ViewEnquiryActivity;
import com.shinecity.lko.customerpanal.WebViewMission99;

public class DocumentFragment extends Fragment {


    public DocumentFragment() {

        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_document, container, false);
        // view.findViewById(R.id.mybutton).getBackground().setAlpha(128);

       LinearLayout linear_agreement_letter=(LinearLayout)view.findViewById(R.id.linear_agreement_letter);
        final LinearLayout sendenquiry =(LinearLayout)view.findViewById(R.id.view_enquiry);
        LinearLayout buttonwebview=(LinearLayout)view.findViewById(R.id.btn_document_shinemission);
        LinearLayout linear_recepit =(LinearLayout)view.findViewById(R.id.linear_recepit);
        LinearLayout ledger =(LinearLayout)view.findViewById(R.id.ledger);

        Button button =(Button)view.findViewById(R.id.mybutton_contactus);

        linear_agreement_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(), AgreementLetter.class));
            }
        });

        buttonwebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WebViewMission99.class));
            }
        });

        sendenquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(getActivity(), ViewEnquiryActivity.class));
            }
        });

        linear_recepit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ReceiptActivity.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactUsAcivity.class));
            }
        });

        ledger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), LedgerActivity.class));
              //getFragmentManager().beginTransaction().replace(R.id.viewpager, new AllBookingFragment()).commit();
            }
        });

        return view;
    }


}
