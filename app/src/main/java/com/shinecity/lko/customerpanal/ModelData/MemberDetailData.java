package com.shinecity.lko.customerpanal.ModelData;

/**
 * Created by QServer on 6/2/2016.
 */
public class MemberDetailData {
  //  "( dasbordid integer primary key autoincrement, panno TEXT  ,  memid TEXT, fathername TEXT, loginid TEXT, mobileno TEXT, Name TEXT, email TEXT, title TEXT )";
   private  String panno;
    private String memid;
    private String fathername;
    private String loginid;
    private String mobileno;
    private String name;
    private String email;
    private String title;

    public MemberDetailData() {
    }

    public MemberDetailData(String panno, String memid, String fathername,String loginid, String mobileno, String name, String email, String title) {
        this.panno = panno;
        this.memid = memid;
        this.fathername = fathername;
        this.loginid= loginid;
        this.mobileno = mobileno;
        this.name = name;
        this.email = email;
        this.title = title;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getMemid() {
        return memid;
    }

    public void setMemid(String memid) {
        this.memid = memid;
    }

    public String getFathername() {
        return fathername;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
