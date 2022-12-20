package org.example.DAL.models;

import org.example.entity.Product;

import java.util.concurrent.atomic.AtomicInteger;

public class ProductDTO {

    private  Integer id;
    private static final AtomicInteger counter=new AtomicInteger(0);
    private  String name;
    private  Double price;
    private  boolean stock;

    public ProductDTO() {
        id=counter.incrementAndGet();
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public boolean getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id+
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product toEntity() {

        Product product=new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        return product;
    }
}
