package com.shinecity.lko.customerpanal.ModelData;

/**
 * Created by QServer on 5/26/2016.
 */
public class DashboardData {

   private String contactName;
    private String contact_id;
    private String contact_type;
    private String contact_mobile;
    private String contact_email;
    private String contact_response_code ;

    public DashboardData(String contactName, String contact_id, String contact_type, String contact_mobile, String contact_email, String contact_response_code) {
        this.contactName = contactName;
        this.contact_id = contact_id;
        this.contact_type = contact_type;
        this.contact_mobile = contact_mobile;
        this.contact_email = contact_email;
        this.contact_response_code = contact_response_code;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getContact_response_code() {
        return contact_response_code;
    }

    public void setContact_response_code(String contact_response_code) {
        this.contact_response_code = contact_response_code;
    }
}
