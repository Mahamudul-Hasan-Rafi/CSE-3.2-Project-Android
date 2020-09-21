package com.example.mylenovo.testapp2;

public class Stuff_Info {
    private String stuff_name;
    private String stuff_mail;
    private String stuff_phone;
    private String stuff_type;
    private String stuff_area;

    public Stuff_Info(){

    }

    public Stuff_Info(String stuff_name, String stuff_mail, String stuff_phone,  String stuff_area) {
        this.stuff_name = stuff_name;
        this.stuff_mail = stuff_mail;
        this.stuff_phone = stuff_phone;
        this.stuff_area = stuff_area;
    }

    public String getStuff_name() {
        return stuff_name;
    }

    public void setStuff_name(String stuff_name) {
        this.stuff_name = stuff_name;
    }

    public String getStuff_mail() {
        return stuff_mail;
    }

    public void setStuff_mail(String stuff_mail) {
        this.stuff_mail = stuff_mail;
    }

    public String getStuff_phone() {
        return stuff_phone;
    }

    public void setStuff_phone(String stuff_phone) {
        this.stuff_phone = stuff_phone;
    }

    public String getStuff_area() {
        return stuff_area;
    }

    public void setStuff_area(String stuff_area) {
        this.stuff_area = stuff_area;
    }
}
