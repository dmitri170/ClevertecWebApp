package org.example.DAL.services.implementations;

import org.example.DAL.models.CardDTO;
import org.example.DAL.models.MarketDTO;
import org.example.DAL.models.ProductDTO;
import org.example.DAL.models.ProductFromCheckDTO;
import org.example.DAL.services.interfaces.PrintCheckBuilder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConsolePrintCheckBuilderImpl implements PrintCheckBuilder {


    @Override
    public void printHeader(String TITLE, MarketDTO marketDTO, String cashierLabel, int numberCashier, String dateLabel,
                            Date printDate, String timeLabel, String qty, String description, String price, String total) {

        System.out.println("\t\t"+TITLE);
        System.out.println("\t\t"+ marketDTO.getName());
        System.out.println("\t\t"+ marketDTO.getAddress());
        System.out.println("\t\t"+ marketDTO.getTelephone());
        System.out.print(cashierLabel);
        System.out.print(numberCashier+"\t\t");
        System.out.print(dateLabel+"\t\t\t");
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat timeNow=new SimpleDateFormat("HH:mm:ss");
        System.out.println(dateNow.format(printDate));
        System.out.print("\t\t\t\t"+timeLabel+"\t\t\t");
        System.out.println(timeNow.format(printDate));
        System.out.println("-----------------------------------------");
        System.out.println(qty+"\t\t"+description+"\t\t"+price+"\t\t"+total);

    }

    @Override
    public double printProductList(ArrayList<ProductFromCheckDTO> productFromCheckDTOS, ArrayList<ProductDTO> productDTOS, ArrayList<CardDTO> cardDTOS, int numberCard) {
        double totalSum=0;
        for(int i = 0; i< productFromCheckDTOS.size(); i++){
            int qty= productFromCheckDTOS.get(i).getQuantity();
            String nameProductById= productDTOS.get(productFromCheckDTOS.get(i).getId()-1).getName();
            double priceForOneProduct= productDTOS.get(productFromCheckDTOS.get(i).getId()-1).getPrice();
            double totalPriceForOneNameProduct=qty * productFromCheckDTOS.get(i).getCost();
            totalSum=totalSum+totalPriceForOneNameProduct;
            DecimalFormat dF = new DecimalFormat( "#.##" );
            System.out.println(qty+"\t\t"+nameProductById+"\t\t\t"+priceForOneProduct+"\t\t"+dF.format(totalPriceForOneNameProduct));
            if(qty>=5){
                System.out.println("item more than 5 and got a discount in 10%");
            }
        }
        return totalSum;
    }

    @Override
    public void printLine() {
        System.out.println("-----------------------------------------");
    }

    @Override
    public void printTotals(double totalSum, ArrayList<CardDTO> cardDTOS, int numberCard, String taxableTotalLabel, String vatLabel,
                            String totalLabel) {
        DecimalFormat dF = new DecimalFormat( "#.##" );
        System.out.println(taxableTotalLabel+"\t\t\t\t\t\t"+dF.format(totalSum));
        int precent=0;
        for(int i = 0; i< cardDTOS.size(); i++) {
            if(cardDTOS.get(i).getNumber()==numberCard) {
                System.out.println(vatLabel + " "  + "\t\t\t\t\t\t" + (cardDTOS.get(i).getDiscount()) + "%");
                precent = cardDTOS.get(i).getDiscount();
            }
        }
        System.out.println(totalLabel+"\t\t\t\t\t\t\t\t"+dF.format(totalSum*(100-precent)/100));
    }


}
