package com.api.product.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


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

    public Product(UUID id, String description, float value, Integer amount) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.amount = amount;
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
}
