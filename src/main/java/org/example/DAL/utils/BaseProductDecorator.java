package org.example.DAL.utils;

import org.example.DAL.models.ProductFromCheckDTO;

public class BaseProductDecorator extends ProductFromCheckDTO {
    private final ProductFromCheckDTO wrappee;
    public BaseProductDecorator(ProductFromCheckDTO wrappee) {
        super(wrappee);
        this.wrappee = wrappee;
    }
    @Override
     public Double getCost(){
        return super.getCost();
     }
}
