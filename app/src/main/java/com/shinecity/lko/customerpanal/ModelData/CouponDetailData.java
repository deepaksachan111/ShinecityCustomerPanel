package com.shinecity.lko.customerpanal.ModelData;

/**
 * Created by QServer on 6/6/2016.
 */
public class CouponDetailData {
    private String snno;
    private String coupandetail;
    private String plotno;
    private String customername;
    private String usedon;
    private String  useddate;

    public CouponDetailData(String snno, String coupandetail, String plotno, String customername, String usedon, String useddate) {
        this.snno = snno;
        this.coupandetail = coupandetail;
        this.plotno = plotno;
        this.customername = customername;
        this.usedon = usedon;
        this.useddate = useddate;
    }

    public String getSnno() {
        return snno;
    }

    public void setSnno(String snno) {
        this.snno = snno;
    }

    public String getCoupandetail() {
        return coupandetail;
    }

    public void setCoupandetail(String coupandetail) {
        this.coupandetail = coupandetail;
    }

    public String getPlotno() {
        return plotno;
    }

    public void setPlotno(String plotno) {
        this.plotno = plotno;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getUsedon() {
        return usedon;
    }

    public void setUsedon(String usedon) {
        this.usedon = usedon;
    }

    public String getUseddate() {
        return useddate;
    }

    public void setUseddate(String useddate) {
        this.useddate = useddate;
    }
}
