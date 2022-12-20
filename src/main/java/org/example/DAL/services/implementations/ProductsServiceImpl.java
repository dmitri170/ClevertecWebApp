package org.example.DAL.services.implementations;

import org.example.DAL.models.ProductDTO;
import org.example.DAL.services.interfaces.ProductsService;

import java.util.ArrayList;

public class ProductsServiceImpl implements ProductsService {
    private ArrayList<ProductDTO> productDTOS;

    public ProductsServiceImpl(ArrayList<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }

    @Override
    public boolean isProductExists(int id){

            for (int j = 0; j < productDTOS.size(); j++) {
                if(productDTOS.get(j).getId()==id){
                    return true;
                }
            }
        return false;
    }
    public double getPriceById(int id){

        return productDTOS.get(id-1).getPrice();
    }
    public boolean getStockById(int id){
        return productDTOS.get(id-1).getStock();
    }


}
