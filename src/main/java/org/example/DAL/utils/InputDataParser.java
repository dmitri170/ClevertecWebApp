package org.example.DAL.utils;

import org.example.DAL.models.ProductDTO;
import org.example.DAL.models.ProductFromCheckDTO;
import org.example.DAL.services.implementations.ProductsServiceImpl;
import org.example.DAL.services.interfaces.ProductsService;
import org.example.DAL.utils.Exceptions.CardNotFoundException;
import org.example.DAL.utils.Exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputDataParser {
    ProductsService productsService;
    public InputDataParser(ArrayList<ProductDTO> productDTOS) {
        this.productsService = new ProductsServiceImpl(productDTOS);
    }
    public InputDataParser() {}
    public ArrayList<ProductFromCheckDTO> getInfoProductsInCheck(String[] params) throws ProductNotFoundException {
        ArrayList<ProductFromCheckDTO> listProd = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            boolean foundIdAndQuin = Pattern.matches("(\\d+\\-+\\d+)", params[i]);
            if (foundIdAndQuin) {
                String[] s = params[i].split("-");
                if (productsService.isProductExists(Integer.parseInt(s[0]))) {
                    double priceById = productsService.getPriceById(Integer.parseInt(s[0]));
                    ProductFromCheckDTO p = new ProductFromCheckDTO(Integer.parseInt(s[0]), Integer.parseInt(s[1]), priceById);
                    if (productsService.getStockById(Integer.parseInt(s[0])) && Integer.parseInt(s[1]) >= 5) {
                        p = new DiscountProductDecorator(p);
                    }
                    listProd.add(p);
                } else throw new ProductNotFoundException("Product with id=" + s[0] + " not found");
            }
        }
        return listProd;
    }

    public int getCardNumber(String[] arg) throws CardNotFoundException {
        int k = 0;
        int numberCard = 0;
        for (int i = 0; i < arg.length; i++) {
            Pattern pattern = Pattern.compile("card-(\\d+)");
            Matcher matcher = pattern.matcher(arg[i]);
            k++;
            if (matcher.find()) {
                String[] s = arg[i].split("-");
                numberCard = Integer.parseInt(s[1]);
                return numberCard;
            }
        }
        if (k == 0) {
            throw new CardNotFoundException("Card is not found in base");
        }
        return 0;
    }
    public ArrayList<ProductFromCheckDTO> getInfoProductsInCheck(Integer[] productIds) throws ProductNotFoundException {
        Map<Integer,Integer> productsFromCheck=getProducts(productIds);
        ArrayList<ProductFromCheckDTO> listProd = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : productsFromCheck.entrySet()) {
            if (productsService.isProductExists(entry.getKey())) {
                double priceById = productsService.getPriceById(entry.getKey());
                ProductFromCheckDTO p = new ProductFromCheckDTO(entry.getKey(), entry.getValue(), priceById);
                if (productsService.getStockById(entry.getKey()) && entry.getValue() >= 5) {
                    p = new DiscountProductDecorator(p);
                }
                listProd.add(p);
            } else throw new ProductNotFoundException("Product with id=" + entry.getKey() + " not found");
        }
        return listProd;
    }
    public Map<Integer,Integer> getProducts(Integer[] itemsId){
        Map<Integer, Integer> productsFromCheck = new HashMap<>();
        for (int i = 0; i < itemsId.length; i++)
        {
            if (productsFromCheck.containsKey(itemsId[i]))
            {
                productsFromCheck.put(itemsId[i], productsFromCheck.get(itemsId[i]) + 1);
            }
            else
            {
                productsFromCheck.put(itemsId[i], 1);
            }
        }
        return productsFromCheck;
    }
}
