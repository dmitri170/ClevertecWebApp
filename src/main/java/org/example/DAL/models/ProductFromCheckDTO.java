package org.example.DAL.models;

public class ProductFromCheckDTO {
    private final Integer id;
    private final Integer quantity;
    private final Double cost;

    public Double getCost() {
        return cost;
    }

    public ProductFromCheckDTO(ProductFromCheckDTO productFromCheckDTO) {
        this.id = productFromCheckDTO.id;
        this.quantity = productFromCheckDTO.quantity;
        this.cost = productFromCheckDTO.cost;
    }

    public ProductFromCheckDTO(Integer id, Integer quantity, Double cost) {
        this.id = id;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ShoppingCheck{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
