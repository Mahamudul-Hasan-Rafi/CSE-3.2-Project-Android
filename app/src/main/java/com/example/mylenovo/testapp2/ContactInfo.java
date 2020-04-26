package com.example.mylenovo.testapp2;

public class ContactInfo {
    private String username;
    private String email;
    private String phone;
    private String house;
    private String road;
    private String area;
    private String sector;

    public ContactInfo(String username, String email, String phone, String house, String road, String area, String sector) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.house = house;
        this.road = road;
        this.area = area;
        this.sector = sector;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
