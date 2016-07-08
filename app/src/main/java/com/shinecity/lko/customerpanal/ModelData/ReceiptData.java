package com.shinecity.lko.customerpanal.ModelData;

/**
 * Created by QServer on 6/18/2016.
 */
public class ReceiptData {

   private String sno ;
    private String InstAmount ;
    private String LateFee;
    private String DueDate ;
    private String PaidOn  ;
    private String ReceiptNo;
    private String PmtMode ;
     private String Bank ;
    private String Date ;
    private String ChqNo;
    private  String Status;
    private  String PaidAmt ;
    private String PaymentType ;

    public ReceiptData(String sno, String instAmount, String lateFee, String dueDate, String paidOn, String pmtMode, String receiptNo, String bank, String date, String chqNo, String status, String paidAmt, String paymentType) {
        this.sno = sno;
        InstAmount = instAmount;
        LateFee = lateFee;
        DueDate = dueDate;
        PaidOn = paidOn;
        PmtMode = pmtMode;
        ReceiptNo = receiptNo;
        Bank = bank;
        Date = date;
        ChqNo = chqNo;
        Status = status;
        PaidAmt = paidAmt;
        PaymentType = paymentType;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getInstAmount() {
        return InstAmount;
    }

    public void setInstAmount(String instAmount) {
        InstAmount = instAmount;
    }

    public String getLateFee() {
        return LateFee;
    }

    public void setLateFee(String lateFee) {
        LateFee = lateFee;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getPaidOn() {
        return PaidOn;
    }

    public void setPaidOn(String paidOn) {
        PaidOn = paidOn;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getPmtMode() {
        return PmtMode;
    }

    public void setPmtMode(String pmtMode) {
        PmtMode = pmtMode;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getChqNo() {
        return ChqNo;
    }

    public void setChqNo(String chqNo) {
        ChqNo = chqNo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPaidAmt() {
        return PaidAmt;
    }

    public void setPaidAmt(String paidAmt) {
        PaidAmt = paidAmt;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }
}
