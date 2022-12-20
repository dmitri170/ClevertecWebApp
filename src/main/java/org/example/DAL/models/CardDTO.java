package org.example.DAL.models;

import org.example.entity.DiscountCard;
import org.example.entity.Product;

public class CardDTO {

    private  Integer number;
    private  Integer discount;
    public int getNumber() {
        return number;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                "discount=" + discount + "%" +
                '}';
    }
    public DiscountCard toEntity() {

        DiscountCard discountCard=new DiscountCard();
        discountCard.setNumber(number);
        discountCard.setDiscount(discount);
        return discountCard;
    }
}
