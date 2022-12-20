package org.example.DAL.models;

import org.example.DAL.services.implementations.FilePrintCheckBuilderIml;
import org.example.DAL.services.interfaces.PrintCheckBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckDTO {
    private final String TITLE ="CASH RECEIPT";
    private final String CASHIER_LABEL="CASHIER:";
    private final String DATE_LABEL="DATE:";
    private final String TIME_LABEL="TIME:";
    private final String QTY_COLUMN_HEADER_NAME="QTY";
    private final String DESCRIPTION_COLUMN_HEADER_NAME="DESCRIPTION";
    private final String PRICE_COLUMN_HEADER_NAME="PRICE";
    private final String TOTAL_COLUMN_HEADER_NAME="TOTAL";
    private final String TAXABLE_TOTAL_LABEL="TAXABLE TOT.";
    private final String VAT_LABEL="Discount card";
    private final String TOTAL_LABEL="TOTAL";
    private final static Double VAT=17.0;
    private MarketDTO marketDTO;
    private ProductDTO productDTO;
    private Integer numberCard;
    private Date printDate;
    private int cashier;
    private ArrayList<ProductFromCheckDTO> productsFromCheck;
    private ArrayList<CardDTO> cardDTOS;
    private ArrayList<ProductDTO> productDTOS;
    private DecimalFormat taxableTotal;
    private PrintCheckBuilder builder;
    private FilePrintCheckBuilderIml filePrintCheckBuilderIml;

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date shoppingDate) {
        this.printDate = printDate;
    }

    public int getCashier() {
        return cashier;
    }

    public void setCashier(int cashier) {
        this.cashier = cashier;
    }

    public DecimalFormat getTaxableTotal() {
        return taxableTotal;
    }

    public void setTaxableTotal(DecimalFormat taxableTotal) {
        this.taxableTotal = taxableTotal;
    }

    public ArrayList<ProductFromCheckDTO> getProducts() {
        return productsFromCheck;
    }

    public void setProducts(ArrayList<ProductFromCheckDTO> productsFromCheck) {
        this.productsFromCheck = productsFromCheck;
    }

    public CheckDTO(Date printDate, int cashier, ArrayList<ProductFromCheckDTO> productsFromCheck, PrintCheckBuilder builder, FilePrintCheckBuilderIml filePrintCheckBuilderIml, MarketDTO marketDTO,
                    ArrayList<CardDTO> cardDTOS, ArrayList<ProductDTO> productDTOS, int numberCard) {
        this.printDate = printDate;
        this.cashier = cashier;
        this.productsFromCheck = productsFromCheck;
        this.filePrintCheckBuilderIml=filePrintCheckBuilderIml;
        this.builder=builder;
        this.marketDTO = marketDTO;
        this.cardDTOS = cardDTOS;
        this.productDTOS = productDTOS;
        this.numberCard=numberCard;
    }
    public CheckDTO(ArrayList<ProductFromCheckDTO> productsFromCheck, ArrayList<CardDTO> cardDTOS, ArrayList<ProductDTO> productDTOS, int numberCard){
        this.productsFromCheck = productsFromCheck;
        this.cardDTOS = cardDTOS;
        this.productDTOS = productDTOS;
        this.numberCard=numberCard;

    }
    void changeBuilder(PrintCheckBuilder builder){
        this.builder=builder;
    }
    public void make(){
        double sum;
        builder.printHeader(TITLE, marketDTO,CASHIER_LABEL,10,DATE_LABEL,printDate,TIME_LABEL,QTY_COLUMN_HEADER_NAME,DESCRIPTION_COLUMN_HEADER_NAME,PRICE_COLUMN_HEADER_NAME,TOTAL_COLUMN_HEADER_NAME);
        sum=builder.printProductList(productsFromCheck, productDTOS, cardDTOS,numberCard);
        builder.printLine();
        builder.printTotals(sum, cardDTOS,numberCard, TAXABLE_TOTAL_LABEL, VAT_LABEL, TOTAL_LABEL);
    }
    public void makeCheckFile(){
        double sum;
        filePrintCheckBuilderIml.printHeader(TITLE, marketDTO,CASHIER_LABEL,10,DATE_LABEL,printDate,TIME_LABEL,QTY_COLUMN_HEADER_NAME,DESCRIPTION_COLUMN_HEADER_NAME,PRICE_COLUMN_HEADER_NAME,TOTAL_COLUMN_HEADER_NAME);
        sum=filePrintCheckBuilderIml.printProductList(productsFromCheck, productDTOS, cardDTOS,numberCard);
        filePrintCheckBuilderIml.printLine();
        filePrintCheckBuilderIml.printTotals(sum, cardDTOS,numberCard, TAXABLE_TOTAL_LABEL, VAT_LABEL, TOTAL_LABEL);
    }

}
