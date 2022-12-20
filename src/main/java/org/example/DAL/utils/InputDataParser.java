package org.example.DAL.utils;

import org.example.DAL.models.ProductDTO;
import org.example.DAL.models.ProductFromCheckDTO;
import org.example.DAL.services.implementations.ProductsServiceImpl;
import org.example.DAL.services.interfaces.ProductsService;
import org.example.DAL.utils.Exceptions.CardNotFoundException;
import org.example.DAL.utils.Exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputDataParser {
    ProductsService productsService;

    public InputDataParser(ArrayList<ProductDTO> productDTOS) {
        this.productsService = new ProductsServiceImpl(productDTOS);
    }

    public ArrayList<ProductFromCheckDTO> getInfoProductsInCheck(String params) throws ProductNotFoundException {
       String[] mass=params.split(" ");
       ArrayList<ProductFromCheckDTO> listProd=new ArrayList<>();
       for(int i=0;i<mass.length;i++){
           boolean foundIdAndQuin = Pattern.matches("(\\d+\\-+\\d+)", mass[i]);
           if (foundIdAndQuin) {
               String[] s=mass[i].split("-");
               if(productsService.isProductExists(Integer.parseInt(s[0]))) {
                   double priceById=productsService.getPriceById(Integer.parseInt(s[0]));
                   ProductFromCheckDTO p = new ProductFromCheckDTO(Integer.parseInt(s[0]), Integer.parseInt(s[1]),priceById);
                   if(productsService.getStockById(Integer.parseInt(s[0]))&&Integer.parseInt(s[1])>=5){
                   p = new DiscountProductDecorator(p);
                   }
                   listProd.add(p);
               }
               else throw new ProductNotFoundException("Product with id="+s[0]+" not found");
           }
       }
       return listProd;
    }
    public int getCardNumber(String params) throws CardNotFoundException {
        String[] mass=params.split(" ");
        int k=0;
        int numberCard=0;
        for(int i=0;i<mass.length;i++){
            Pattern pattern = Pattern.compile("card-(\\d+)");
            Matcher matcher = pattern.matcher(mass[i]);
            k++;
            if(matcher.find()) {
                String[] s = mass[i].split("-") ;
                numberCard=Integer.parseInt(s[1]);

                return numberCard;
            }
        }
        if(k==0) {
            throw new CardNotFoundException("Card is not found in base");
        }
        return 0;
    }
}
