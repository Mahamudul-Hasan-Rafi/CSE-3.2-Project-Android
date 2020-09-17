package com.example.mylenovo.testapp2;

public class ProductInfo {
    private String p_name;
    private String p_type;
    private String price_unit;

    public ProductInfo()
    {

    }

    public ProductInfo(String p_name, String p_type, String price_unit) {
        this.p_name = p_name;
        this.p_type = p_type;
        this.price_unit = price_unit;
    }

    public String getP_name() {
        return p_name;
    }

    public String getP_type() {
        return p_type;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }
}
