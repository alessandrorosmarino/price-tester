package com.bnc.price.tester.pricetester.web.controller;

import com.bnc.price.tester.pricetester.properties.DateProperties;
import com.bnc.price.tester.pricetester.web.entity.Price;
import com.bnc.price.tester.pricetester.web.service.PriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PriceControllerUnitTests {

    @Mock
    PriceService priceService;

    @Mock
    DateProperties dateProperties;

    @InjectMocks
    PriceController priceController;

    @Test
    void test_Controller_returning_100_price() {
        Mockito.when(dateProperties.getMinDate()).thenReturn(
                LocalDateTime.of(1800, 1, 1, 0, 0, 0));
        Mockito.when(dateProperties.getMaxDate()).thenReturn(
                LocalDateTime.of(2500, 1, 1, 0, 0, 0));
        LocalDateTime now = LocalDateTime.now();
        Price price = new Price(1, 1L, now, now,
                1L, 1, BigDecimal.valueOf(100), "EUR");
        Mockito.when(priceService.findFirstByBrandIdAndProductIdAndDateBetweenStartAndEndDate(
            1L,1L,now)).thenReturn(price);
        assertEquals(BigDecimal.valueOf(100), priceController.getPriorityPriceOfBrandAndProductBetweenDate(
                1L, 1L, now).getpriceValue());
    }
}
