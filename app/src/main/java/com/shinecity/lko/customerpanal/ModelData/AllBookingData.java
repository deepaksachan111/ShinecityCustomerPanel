package com.shinecity.lko.customerpanal.ModelData;

/**
 * Created by QServer on 6/7/2016.
 */
public class AllBookingData {

    private String snno;
    private String bookingid;
    private String plotno;
    private String sitename;
    private String paymentplan;
    private String  customer;
    private  String sales;
    private  String bookingdate;
    private  String bsp;

    public AllBookingData(){

    }

    public AllBookingData(String snno, String plotno, String sitename, String bsp) {
        this.snno = snno;
        this.plotno = plotno;
        this.sitename = sitename;
        this.bsp = bsp;
    }

    public AllBookingData(String snno, String bookingid, String plotno, String sitename, String paymentplan, String customer, String sales, String bookingdate, String bsp) {
        this.snno = snno;
        this.bookingid = bookingid;
        this.plotno = plotno;
        this.sitename = sitename;
        this.paymentplan = paymentplan;
        this.customer = customer;
        this.sales = sales;
        this.bookingdate = bookingdate;
        this.bsp = bsp;
    }

    public String getSnno() {
        return snno;
    }

    public void setSnno(String snno) {
        this.snno = snno;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getBsp() {
        return bsp;
    }

    public void setBsp(String bsp) {
        this.bsp = bsp;
    }
}
