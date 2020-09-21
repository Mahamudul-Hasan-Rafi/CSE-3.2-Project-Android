package com.example.mylenovo.testapp2;

public class Upload {

    private String ImageName;
    private String ImageUrl;
    private String Required_products;

    public Upload(){

    }

    public Upload(String imageName, String imageUrl, String required_products) {
        ImageName = imageName;
        ImageUrl = imageUrl;
        Required_products = required_products;
    }

    public String getRequired_products() {
        return Required_products;
    }

    public void setRequired_products(String required_products) {
        Required_products = required_products;
    }

    public String getImageName() {

        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
