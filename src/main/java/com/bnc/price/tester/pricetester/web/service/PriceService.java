package com.bnc.price.tester.pricetester.web.service;

import com.bnc.price.tester.pricetester.web.entity.Price;
import com.bnc.price.tester.pricetester.web.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(final PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price findFirstByBrandIdAndProductIdAndDateBetweenStartAndEndDate(Long brandId, Long productId, LocalDateTime date) {
        return priceRepository.findFirstByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(brandId, productId, date, date);
    }
}
