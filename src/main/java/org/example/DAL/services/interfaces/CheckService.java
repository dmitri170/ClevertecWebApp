package org.example.DAL.services.interfaces;

import org.example.DAL.models.CheckDTO;
import org.hibernate.annotations.Check;

public interface CheckService {
    public CheckDTO getCheck(Integer[] productIds, Integer cardNumber);
}
