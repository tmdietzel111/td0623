package org.example.service;

import org.example.model.RentalCharge;
import org.example.model.Tool;
import org.example.model.ToolType;
import org.example.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    private ToolRepository toolRepository;

    @Autowired
    public CheckoutService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }
    public Tool getTool(String code) {
        return toolRepository.getToolByCode(code);
    }

    public RentalCharge getRentalCharge(ToolType toolType) {
        return toolRepository.getRentalCharges(toolType);
    }
}
