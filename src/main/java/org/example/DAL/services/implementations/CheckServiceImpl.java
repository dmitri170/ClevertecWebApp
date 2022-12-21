package org.example.DAL.services.implementations;

import org.example.DAL.models.CheckDTO;
import org.example.DAL.services.interfaces.CheckService;
import org.hibernate.annotations.Check;

public class CheckServiceImpl implements CheckService {

    @Override
    public CheckDTO getCheck(Integer[] productIds, Integer cardNumber) {
        return new CheckDTO(null,null,0);
    }
}
