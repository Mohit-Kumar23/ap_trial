package com.hp.appoindone;

public class doctorclass {
    String address;
    String contact_no;
    String hname;
    String mf;
    String name;
    String purl;
    String rating;
    String sat;
    String specialist;
    String sun;
    String mon_fri;
    String sat_sun;
    String fee;

    doctorclass(){

    }

    public doctorclass(String address, String contact_no, String hname, String mf, String name, String purl, String rating, String sat, String specialist, String sun,String mon_fri,String sat_sun,String fee) {
        this.address = address;
        this.contact_no = contact_no;
        this.hname = hname;
        this.mf = mf;
        this.name = name;
        this.purl = purl;
        this.rating = rating;
        this.sat = sat;
        this.specialist = specialist;
        this.sun = sun;
        this.mon_fri=mon_fri;
        this.sat_sun=sat_sun;
        this.fee=fee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getMf() {
        return mf;
    }

    public void setMf(String mf) {
        this.mf = mf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getMon_fri() {
        return mon_fri;
    }

    public void setMon_fri(String mon_fri) {
        this.mon_fri = mon_fri;
    }

    public String getSat_sun() {
        return sat_sun;
    }

    public void setSat_sun(String sat_sun) {
        this.sat_sun = sat_sun;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

}
