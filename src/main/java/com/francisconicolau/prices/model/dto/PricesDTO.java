package com.francisconicolau.prices.model.dto;

public class PricesDTO {

    String date;
    int productId;
    int brandId;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return "PricesDTO{" +
                "date='" + date + '\'' +
                ", productId=" + productId +
                ", brandId=" + brandId +
                '}';
    }
}
