package com.francisconicolau.brand.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "brands")
@JsonSerialize()
public class Brands {

    @Id
    @Column(name = "ID", nullable = false)
    @ApiModelProperty(value = "ID", required = true)
    private int id;

    @Column(name = "NAME", nullable = false)
    @ApiModelProperty(value = "NAME", required = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Brands{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
