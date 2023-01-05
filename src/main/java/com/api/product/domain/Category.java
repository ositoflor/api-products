package com.api.product.domain;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Category {

    @NotBlank
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
