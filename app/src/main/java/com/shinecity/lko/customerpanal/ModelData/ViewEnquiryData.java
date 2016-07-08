package com.shinecity.lko.customerpanal.ModelData;

/**
 * Created by QServer on 6/9/2016.
 */
public class ViewEnquiryData {
    private String enquirydate;
    private String querystatus;
    private String enquiry;
    private String replyon;

    public ViewEnquiryData(String enquirydate, String querystatus, String enquiry, String replyon) {

        this.enquirydate = enquirydate;
        this.querystatus = querystatus;
        this.enquiry = enquiry;
        this.replyon = replyon;
    }

    public String getEnquirydate() {
        return enquirydate;
    }

    public void setEnquirydate(String enquirydate) {
        this.enquirydate = enquirydate;
    }

    public String getQuerystatus() {
        return querystatus;
    }

    public void setQuerystatus(String querystatus) {
        this.querystatus = querystatus;
    }

    public String getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(String enquiry) {
        this.enquiry = enquiry;
    }

    public String getReplyon() {
        return replyon;
    }

    public void setReplyon(String replyon) {
        this.replyon = replyon;
    }
}
