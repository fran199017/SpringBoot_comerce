package com.francisconicolau.product.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "products")
@JsonSerialize()
public class Products {

    @Id
    @ApiModelProperty(value = "ID", required = true)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)  //To add prices h2 database
    int id;

    @ApiModelProperty(value = "NAME", required = true)
    @Column(name = "NAME", nullable = false)
    String name;

    @ApiModelProperty(value = "DESCRIPTION", required = true)
    @Column(name = "DESCRIPTION", nullable = false)
    String description;

    @ApiModelProperty(value = "PRICE", required = true)
    @Column(name = "PRICE", nullable = false)
    float price;

    @ApiModelProperty(value = "IMAGE_PATH", required = true)
    @Column(name = "IMAGE_PATH", nullable = false)
    String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
