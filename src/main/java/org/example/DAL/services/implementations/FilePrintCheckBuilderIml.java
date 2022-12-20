package org.example.DAL.services.implementations;

import org.example.DAL.models.CardDTO;
import org.example.DAL.models.MarketDTO;
import org.example.DAL.models.ProductDTO;
import org.example.DAL.models.ProductFromCheckDTO;
import org.example.DAL.services.interfaces.PrintCheckBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FilePrintCheckBuilderIml implements PrintCheckBuilder{
    private final String nameFile="./src/main/resources/check.txt";

    PrintWriter printWriter;
    @Override
    public void printHeader(String title, MarketDTO marketDTO, String cashierLabel, int numberCashier, String dateLabel, Date printDate, String timeLabel, String qty, String description, String price, String total)  {
        try {
            FileWriter file = new FileWriter(nameFile,false);
            printWriter=new PrintWriter(file);
            printWriter.println("\t\t"+title);
            printWriter.println("\t\t"+ marketDTO.getName());
            printWriter.println("\t\t"+ marketDTO.getAddress());
            printWriter.print(cashierLabel);
            printWriter.print(numberCashier+"\t\t");
            printWriter.print(dateLabel+"\t\t\t");
            SimpleDateFormat dateNow = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat timeNow=new SimpleDateFormat("HH:mm:ss");
            printWriter.println(dateNow.format(printDate));
            printWriter.print("\t\t\t\t"+timeLabel+"\t\t\t");
            printWriter.println(timeNow.format(printDate));
            printWriter.println("-----------------------------------------");
            printWriter.println(qty+"\t\t"+description+"\t\t"+price+"\t\t"+total);
            printWriter.flush();
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public double printProductList(ArrayList<ProductFromCheckDTO> productFromCheckDTOS, ArrayList<ProductDTO> productDTOS, ArrayList<CardDTO> cardDTOS, int numberCard) {
            double totalSum=0;
            try {
                FileWriter file = new FileWriter(nameFile,true);
                printWriter = new PrintWriter(file);
                for(int i = 0; i< productFromCheckDTOS.size(); i++) {
                    int qty = productFromCheckDTOS.get(i).getQuantity();
                    String nameProductById = productDTOS.get(productFromCheckDTOS.get(i).getId() - 1).getName();
                    double priceForOneProduct = productDTOS.get(productFromCheckDTOS.get(i).getId() - 1).getPrice();
                    double totalPriceForOneNameProduct = qty * productFromCheckDTOS.get(i).getCost();
                    totalSum=totalSum+totalPriceForOneNameProduct;
                    DecimalFormat dF = new DecimalFormat( "#.##" );
                    printWriter.println(qty+"\t\t"+nameProductById+"\t\t\t"+priceForOneProduct+"\t\t"+dF.format(totalPriceForOneNameProduct));
                    if(qty>=5){
                        printWriter.println("item more than 5 and got a discount in 10%");
                    }
                }
                printWriter.flush();
                printWriter.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }


        return totalSum;

    }

    @Override
    public void printLine() {
        try {
            FileWriter file = new FileWriter(nameFile,true);
            printWriter = new PrintWriter(file);
            printWriter.println("-----------------------------------------");
            file.flush();
            file.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void printTotals(double totalSum, ArrayList<CardDTO> cardDTOS, int numberCard, String taxableTotalLabel, String vatLabel, String totalLabel) {
            try {
                FileWriter file = new FileWriter(nameFile,true);
                printWriter = new PrintWriter(file);
                DecimalFormat dF = new DecimalFormat( "#.##" );
                printWriter.println(taxableTotalLabel+"\t\t\t\t\t\t"+dF.format(totalSum));
                int precent=0;
                for(int i = 0; i< cardDTOS.size(); i++) {
                    if(cardDTOS.get(i).getNumber()==numberCard)
                        printWriter.println(vatLabel + " " + "\t\t\t\t\t\t" + (cardDTOS.get(i).getDiscount())+"%");
                    precent= cardDTOS.get(i).getDiscount();
                }

                printWriter.println(totalLabel+"\t\t\t\t\t\t\t\t"+dF.format(totalSum*(100-precent)/100));
                file.flush();
                file.close();
            }catch (IOException e){
                e.printStackTrace();
            }
    }
}
