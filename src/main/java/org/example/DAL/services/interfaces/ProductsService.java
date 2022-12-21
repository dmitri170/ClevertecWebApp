package org.example.DAL.services.interfaces;

public interface ProductsService {
    boolean isProductExists(int id) ;
    public double getPriceById(int id);
    public boolean getStockById(int id);
}
