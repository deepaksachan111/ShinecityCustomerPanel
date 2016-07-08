package com.shinecity.lko.customerpanal.ModelData;

import android.widget.TextView;

/**
 * Created by QServer on 6/29/2016.
 */
public class AgreementLetterData {

   private String index;
    private String bookingid;
    private String plotno;
    private String sitename;
    private String paymentplan;
    private String customerid;
    private String customername;
    private String associateid;
    private String associatename;
    private String bsp;

    public AgreementLetterData(String index, String bookingid, String plotno, String sitename, String paymentplan, String customerid, String customername, String associateid, String associatename, String bsp) {
        this.index = index;
        this.bookingid = bookingid;
        this.plotno = plotno;
        this.sitename = sitename;
        this.paymentplan = paymentplan;
        this.customerid = customerid;
        this.customername = customername;
        this.associateid = associateid;
        this.associatename = associatename;
        this.bsp = bsp;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getPlotno() {
        return plotno;
    }

    public void setPlotno(String plotno) {
        this.plotno = plotno;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getPaymentplan() {
        return paymentplan;
    }

    public void setPaymentplan(String paymentplan) {
        this.paymentplan = paymentplan;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getAssociateid() {
        return associateid;
    }

    public void setAssociateid(String associateid) {
        this.associateid = associateid;
    }

    public String getAssociatename() {
        return associatename;
    }

    public void setAssociatename(String associatename) {
        this.associatename = associatename;
    }

    public String getBsp() {
        return bsp;
    }

    public void setBsp(String bsp) {
        this.bsp = bsp;
    }
}
