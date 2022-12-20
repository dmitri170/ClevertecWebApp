package org.example.DAL.services.interfaces;

import org.example.DAL.models.CardDTO;
import org.example.DAL.models.MarketDTO;
import org.example.DAL.models.ProductDTO;
import org.example.DAL.models.ProductFromCheckDTO;

import java.util.ArrayList;
import java.util.Date;

public interface PrintCheckBuilder {
    void printHeader(String title, MarketDTO marketDTO, String cashierLabel, int numberCashier, String dateLabel, Date printDate, String timeLabel, String qty, String description, String price, String total);
    double printProductList(ArrayList<ProductFromCheckDTO> productFromCheckDTOS, ArrayList<ProductDTO> productDTOS, ArrayList<CardDTO> cardDTOS, int numberCard);
    void printLine();
    void printTotals(double totalSum, ArrayList<CardDTO> cardDTOS, int numberCard, String taxableTotalLabel, String vatLabel,
                     String totalLabel);

}
