package com.francisconicolau.prices.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@JsonSerialize()
public class Prices {

    @Id
    @ApiModelProperty(value = "ID", required = true)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)  //To add prices h2 database
    int id;

    @ApiModelProperty(value = "BRAND_ID", required = true)
    @Column(name = "BRAND_ID", nullable = false)
    int brandId;

    @ApiModelProperty(value = "START_DATE", required = true)
    @Column(name = "START_DATE", nullable = false)
    LocalDateTime startDate;

    @ApiModelProperty(value = "END_DATE", required = true)
    @Column(name = "END_DATE", nullable = false)
    LocalDateTime endDate;

    @ApiModelProperty(value = "PRICE_LIST", required = true)
    @Column(name = "PRICE_LIST", nullable = false)
    int priceList;

    @ApiModelProperty(value = "PRODUCT_ID", required = true)
    @Column(name = "PRODUCT_ID", nullable = false)
    int productId;

    @ApiModelProperty(value = "PRIORITY", required = true)
    @Column(name = "PRIORITY", nullable = false)
    int priority;

    @ApiModelProperty(value = "PRICE", required = true)
    @Column(name = "PRICE", nullable = false)
    float price;

    @ApiModelProperty(value = "CURR", required = true)
    @Column(name = "CURR", nullable = false)
    String curr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getPriceList() {
        return priceList;
    }

    public void setPriceList(int priceList) {
        this.priceList = priceList;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceList=" + priceList +
                ", productId=" + productId +
                ", priority=" + priority +
                ", price=" + price +
                ", curr='" + curr + '\'' +
                '}';
    }
}
