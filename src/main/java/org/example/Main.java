package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.entity.DiscountCard;
import org.example.DAO.entity.Product;
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
import java.util.Scanner;

public class Main {
    public static void main(String[] arg) throws IOException {
        Scanner scanner=new Scanner(System.in);
        int number= scanner.nextInt();
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
                    ArrayList<ProductFromCheckDTO> productsFromCheck = inputDataParser.getInfoProductsInCheck(arg);
                    int cardNumber = inputDataParser.getCardNumber(arg);
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
                    ArrayList<ProductFromCheckDTO> productsFromCheck = inputDataParser.getInfoProductsInCheck(arg);
                    int cardNumber = inputDataParser.getCardNumber(arg);
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