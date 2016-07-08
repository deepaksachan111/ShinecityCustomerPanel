package com.shinecity.lko.customerpanal.ModelData;

/**
 * Created by QServer on 6/27/2016.
 */
public class EMIPaymentRecordData {

    private String InstallmentNo;
    private String InstallmentAmnt;
    private String LateFee;
    private String InstallmentDueDate;
    private String InstallmentPaidDate;
    private String ReceiptNo;
    private String PaymentMode;
    private String BankName;
    private String ChequeDDNo;
    private String ChequeDDDate;
    private String PaidAmount;

    public EMIPaymentRecordData(String installmentNo, String installmentAmnt, String lateFee, String installmentDueDate, String installmentPaidDate, String receiptNo, String paymentMode, String bankName, String chequeDDNo, String chequeDDDate, String paidAmount) {
        InstallmentNo = installmentNo;
        InstallmentAmnt = installmentAmnt;
        LateFee = lateFee;
        InstallmentDueDate = installmentDueDate;
        InstallmentPaidDate = installmentPaidDate;
        ReceiptNo = receiptNo;
        PaymentMode = paymentMode;
        BankName = bankName;
        ChequeDDNo = chequeDDNo;
        ChequeDDDate = chequeDDDate;
        PaidAmount = paidAmount;
    }

    public String getInstallmentNo() {
        return InstallmentNo;
    }

    public void setInstallmentNo(String installmentNo) {
        InstallmentNo = installmentNo;
    }

    public String getInstallmentAmnt() {
        return InstallmentAmnt;
    }

    public void setInstallmentAmnt(String installmentAmnt) {
        InstallmentAmnt = installmentAmnt;
    }

    public String getLateFee() {
        return LateFee;
    }

    public void setLateFee(String lateFee) {
        LateFee = lateFee;
    }

    public String getInstallmentDueDate() {
        return InstallmentDueDate;
    }

    public void setInstallmentDueDate(String installmentDueDate) {
        InstallmentDueDate = installmentDueDate;
    }

    public String getInstallmentPaidDate() {
        return InstallmentPaidDate;
    }

    public void setInstallmentPaidDate(String installmentPaidDate) {
        InstallmentPaidDate = installmentPaidDate;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getChequeDDNo() {
        return ChequeDDNo;
    }

    public void setChequeDDNo(String chequeDDNo) {
        ChequeDDNo = chequeDDNo;
    }

    public String getChequeDDDate() {
        return ChequeDDDate;
    }

    public void setChequeDDDate(String chequeDDDate) {
        ChequeDDDate = chequeDDDate;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }
}
