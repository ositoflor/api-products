package com.api.product.domain;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "description")
    @NotBlank
    private String description;
    @Column(name = "value")
    private float value;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "category")
    @Valid
    private Category category;

    public Product(UUID id, String description, float value, Integer amount, Category category) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.amount = amount;
        this.category = category;
    }

    public Product(){}
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Category getCategory() {return category;}

    public void setCategory(Category category) {this.category = category;}
}
