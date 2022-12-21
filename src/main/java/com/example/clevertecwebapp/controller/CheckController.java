package com.example.clevertecwebapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.example.DAL.models.*;
import org.example.DAL.services.implementations.CheckServiceImpl;
import org.example.DAL.services.interfaces.CheckService;
import org.example.DAL.utils.InputDataParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class CheckController {
    CheckService service;
    public CheckController() {
        service=new CheckServiceImpl();
    }
    @RequestMapping("/check")
    @ResponseBody
    public CheckDTO getCheck(Integer[] itemId, String card){

        Integer cardNumber;
        try {
           cardNumber =  Integer.parseInt(card);
        }
        catch(Exception e) {
            cardNumber = 0;
        }
        File marketInfoFile = new File("./src/main/resources/marketInfo.json");
        ObjectMapper objectMapper = new ObjectMapper();
        MarketDTO marketDTO = null;
        ArrayList<ProductDTO> productDTOS;
        ArrayList<CardDTO> cardDTOS;
        ArrayList<ProductFromCheckDTO> productFromCheckDTOS;
        File productsInfoFile = new File("./src/main/resources/productsInfo.json");
        File cardsInfoFile = new File("./src/main/resources/cardsInfo.json");

        try {
            marketDTO = objectMapper.readValue(marketInfoFile, MarketDTO.class);
            productDTOS = objectMapper.readValue(productsInfoFile, new TypeReference<ArrayList<ProductDTO>>() {});
            cardDTOS = objectMapper.readValue(cardsInfoFile, new TypeReference<ArrayList<CardDTO>>() {});
            InputDataParser inputDataParser=new InputDataParser(productDTOS);
            productFromCheckDTOS =  inputDataParser.getInfoProductsInCheck(itemId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CheckDTO checkDTO = new CheckDTO(new Date(), 10, productFromCheckDTOS, null, null, marketDTO, cardDTOS, productDTOS, cardNumber);
        return checkDTO;
    }


}
