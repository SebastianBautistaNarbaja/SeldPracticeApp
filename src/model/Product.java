package model;

import java.math.BigDecimal;


public class Product {
    private int product_id;
    private String name;
    private BigDecimal price;
    private int stock;
    private int category_id;

    public Product(){}

    public Product(int product_id, String name, BigDecimal price, int stock, int category_id){
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category_id = category_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "( id : "+ product_id + " name : "+ name + " price : "+ price + ")";
    }
}
