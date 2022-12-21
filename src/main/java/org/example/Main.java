package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.DiscountCard;
import org.example.entity.Product;
import org.example.DAL.models.*;
import org.example.DAL.services.implementations.ConsolePrintCheckBuilderImpl;
import org.example.DAL.services.implementations.FilePrintCheckBuilderIml;
import org.example.DAL.services.interfaces.PrintCheckBuilder;
import org.example.DAL.utils.HibernateUtil;
import org.example.DAL.utils.InputDataParser;
import org.hibernate.Session;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        int number=2;
        String inputArg = "2-5 1-5 3-5 4-6 7-5 8-3 9-2 10-5 card-1000";
        File marketInfoFile = new File("./src/main/resources/marketInfo.json");
        ObjectMapper objectMapper = new ObjectMapper();
        MarketDTO marketDTO = objectMapper.readValue(marketInfoFile, MarketDTO.class);
        switch (number) {
            case 1:
                try {
                    File productsInfoFile = new File("./src/main/resources/productsInfo.json");
                    File cardsInfoFile = new File("./src/main/resources/cardsInfo.json");

                    ArrayList<ProductDTO> productDTOS = objectMapper.readValue(productsInfoFile, new TypeReference<ArrayList<ProductDTO>>() {});
                    ArrayList<CardDTO> cardDTOS = objectMapper.readValue(cardsInfoFile, new TypeReference<ArrayList<CardDTO>>() {});
                    InputDataParser inputDataParser = new InputDataParser(productDTOS);
                    ArrayList<ProductFromCheckDTO> productsFromCheck = inputDataParser.getInfoProductsInCheck(inputArg);
                    int cardNumber = inputDataParser.getCardNumber(inputArg);
                    PrintCheckBuilder builder = new ConsolePrintCheckBuilderImpl();
                    FilePrintCheckBuilderIml filePrintCheckBuilderIml = new FilePrintCheckBuilderIml();
                    CheckDTO checkDTO = new CheckDTO(new Date(), 10, productsFromCheck, builder, filePrintCheckBuilderIml, marketDTO, cardDTOS, productDTOS, cardNumber);
                    checkDTO.make();
                    checkDTO.makeCheckFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                Session session= HibernateUtil.getSessionFactory().openSession();
                session.getTransaction().begin();
                List<Product> productList =session.createQuery("from Product", Product.class).list();
                List<DiscountCard> discountCardList=session.createQuery("from DiscountCard ",DiscountCard.class).list();
                ArrayList<ProductDTO> productDTOs=new ArrayList<>();
                ArrayList<CardDTO> cardDTOs=new ArrayList<>();
                for (int i = 0; i < productList.size(); i++) {
                    productDTOs.add(productList.get(i).toDTO());
                }
                for (int i = 0; i < discountCardList.size(); i++) {
                    cardDTOs.add(discountCardList.get(i).toDTO());
                }
                System.out.println(productDTOs.toString());
                try {
                    InputDataParser inputDataParser = new InputDataParser(productDTOs);
                    ArrayList<ProductFromCheckDTO> productsFromCheck = inputDataParser.getInfoProductsInCheck(inputArg);
                    int cardNumber = inputDataParser.getCardNumber(inputArg);
                    PrintCheckBuilder builder = new ConsolePrintCheckBuilderImpl();
                    FilePrintCheckBuilderIml filePrintCheckBuilderIml = new FilePrintCheckBuilderIml();
                    CheckDTO checkDTO = new CheckDTO(new Date(), 10, productsFromCheck, builder, filePrintCheckBuilderIml, marketDTO, cardDTOs, productDTOs, cardNumber);
                    checkDTO.make();
                    checkDTO.makeCheckFile();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                session.getTransaction().commit();
                session.close();
                HibernateUtil.close();
                break;
        }

    }

}