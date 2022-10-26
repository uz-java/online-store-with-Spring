package com.catalina.webspringbootshop.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    public String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    List<Product> products = new ArrayList<>();

    public Category() {
    }

    public int getId() {
        return id;
    }

    public Category(String name) {
        this.name = name;
    }
}
