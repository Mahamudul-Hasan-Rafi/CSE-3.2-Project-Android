package com.example.mylenovo.testapp2;

public class DataModel {
    private String name_user;
    private String mobile_no;
    private String house_no;
    private String street_name;
    private String area;
    private String block;

    public DataModel(String name_user, String mobile_no, String house_no, String street_name, String sector, String area) {
        this.name_user = name_user;
        this.mobile_no = mobile_no;
        this.house_no = house_no;
        this.street_name = street_name;
        this.block=sector;
        this.area = area;
    }

    public String getBlock() {
        return block;
    }

    /*public void setBlock(String block) {
        this.block = block;
    }*/

    public String getName_user() {
        return name_user;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getHouse_no() {
        return house_no;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getArea() {
        return area;
    }
}
