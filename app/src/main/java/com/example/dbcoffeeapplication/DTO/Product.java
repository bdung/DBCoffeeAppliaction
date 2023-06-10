package com.example.dbcoffeeapplication.DTO;

public class Product {
    private String id,name, description,image;
    private int amount;
    private int price;

    public Product() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.image = "";
        this.amount = 0;
        this.price = 0;
    }

    public Product(String id, String name, String description, String image, int amount, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.amount = amount;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
