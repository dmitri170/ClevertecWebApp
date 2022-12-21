package org.example.DAL.utils;

import org.example.DAL.models.ProductFromCheckDTO;

public class DiscountProductDecorator extends BaseProductDecorator{
    public DiscountProductDecorator(ProductFromCheckDTO wrappee) {
        super(wrappee);
    }
    @Override
    public Double getCost(){

        return super.getCost()*0.9;
    }
}
