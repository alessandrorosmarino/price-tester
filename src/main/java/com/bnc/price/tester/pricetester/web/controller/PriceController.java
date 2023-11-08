package com.bnc.price.tester.pricetester.web.controller;

import com.bnc.price.tester.pricetester.properties.DateProperties;
import com.bnc.price.tester.pricetester.web.entity.Price;
import com.bnc.price.tester.pricetester.exception.NoPriceFoundException;
import com.bnc.price.tester.pricetester.web.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PriceController {

    private final PriceService priceService;

    private final DateProperties dateProperties;

    @Autowired
    public PriceController(final PriceService priceService, final DateProperties dateProperties) {
        this.priceService = priceService;
        this.dateProperties = dateProperties;
    }

    @GetMapping("/prices/brands/{brandId}/products/{productId}")
    public Price getPriorityPriceOfBrandAndProductBetweenDate(@PathVariable(name = "brandId") Long brandId,
                                @PathVariable(name = "productId") Long productId,
                                @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if(brandId <= 0){
            throw new IllegalArgumentException("brandId must be greater than 0");
        }
        if(productId <= 0){
            throw new IllegalArgumentException("productId must be greater than 0");
        }
        if (date.isBefore(dateProperties.getMinDate()) || date.isAfter(dateProperties.getMaxDate())){
            throw new IllegalArgumentException("date must be between " + dateProperties.getMinDate() + " and " + dateProperties.getMaxDate());
        }
        Price price = priceService.findFirstByBrandIdAndProductIdAndDateBetweenStartAndEndDate(brandId, productId, date);
        if (price == null){
            throw new NoPriceFoundException("No Price found");
        }
        return price;
    }
}
