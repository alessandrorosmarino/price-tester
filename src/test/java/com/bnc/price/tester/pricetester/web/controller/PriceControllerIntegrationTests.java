package com.bnc.price.tester.pricetester.web.controller;

import com.bnc.price.tester.pricetester.web.entity.Price;
import com.bnc.price.tester.pricetester.exception.NoPriceFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceControllerIntegrationTests {

    @Autowired
    PriceController priceController;

    /***
     * Default test provided 1
     */
    @Test
    void test_14_6_2020_10_00_00_With_BrandId_1_And_ProductId_35455_Expected_Price_35_50(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals("35.50", 1L, 35455L, date);
    }

    /***
     * Default test provided 2
     */
    @Test
    void test_14_6_2020_16_00_00_AM_With_BrandId_1_And_ProductId_35455_Expected_Price_25_45(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0, 0);
        getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals("25.45", 1L, 35455L, date);
    }

    /***
     * Default test provided 3
     */
    @Test
    void test_14_6_2020_21_00_00_AM_With_BrandId_1_And_ProductId_35455_Expected_Price_35_50(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0, 0);
        getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals("35.50", 1L, 35455L, date);
    }

    /***
     * Default test provided 4
     */
    @Test
    void test_15_6_2020_10_00_00_With_BrandId_1_And_ProductId_35455_Expected_Price_30_50(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0, 0);
        getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals("30.50", 1L, 35455L, date);
    }

    /***
     * Default test provided 5
     */
    @Test
    void test_16_6_2020_21_00_00_With_BrandId_1_And_ProductId_35455_Expected_Price_38_95(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 16, 21, 0, 0);
        getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals("38.95", 1L, 35455L, date);
    }

    /***
     * Test with a different brand id
     * Expected: price of 44.12
     */
    @Test
    void test_14_6_2020_10_00_00_With_BrandId_2_And_ProductId_35455_Expected_Price_44_12(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals("44.12", 2L, 35455L, date);
    }

    /***
     * Test with a date that is not in the range of any price
     * Expected: NoPriceFoundException
     */
    @Test
    void test_14_6_2000_10_00_00_With_BrandId_2_And_ProductId_35455_Expected_NoPriceFoundException(){
        LocalDateTime date = LocalDateTime.of(2000, 6, 14, 10, 0, 0);
        assertThrowsExactly(NoPriceFoundException.class,
                () -> priceController.getPriorityPriceOfBrandAndProductBetweenDate(
                        2L, 35455L, date));
    }

    /***
     * Test with a different product id
     * Expected: price of 10.07
     */
    @Test
    void test_14_6_2020_10_00_00_With_BrandId_1_And_ProductId_35555_Expected_Price_10_07(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals("10.07", 1L, 35555L, date);
    }

    /***
     * Test with a different brand id and product id
     * Expected: price of 85.27
     */
    @Test
    void test_14_6_2020_10_00_00_With_BrandId_3_And_ProductId_34567_Expected_Price_85_27(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals("85.27", 3L, 34567L, date);
    }

    /***
     * Test with date that is before the minimum accepted date
     * Expected: IllegalArgumentException
     */
    @Test
    void test_with_date_before_limit(){
        LocalDateTime date = LocalDateTime.of(1200, 6, 14, 10, 0, 0);
        assertThrowsExactly(IllegalArgumentException.class,
                () -> priceController.getPriorityPriceOfBrandAndProductBetweenDate(
                        1L, 35455L, date));
    }

    /***
     * Test with date that is after the maximum accepted date
     * Expected: IllegalArgumentException
     */
    @Test
    void test_with_date_after_limit(){
        LocalDateTime date = LocalDateTime.of(2500, 6, 14, 10, 0, 0);
        assertThrowsExactly(IllegalArgumentException.class,
                () -> priceController.getPriorityPriceOfBrandAndProductBetweenDate(
                        1L, 35455L, date));
    }

    /***
     * Test with invalid brand id
     * Expected: IllegalArgumentException
     */
    @Test
    void test_with_negative_brand_id(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        assertThrowsExactly(IllegalArgumentException.class,
                () -> priceController.getPriorityPriceOfBrandAndProductBetweenDate(
                        -1L, 35455L, date));
    }

    /***
     * Test with invalid product id
     * Expected: IllegalArgumentException
     */
    @Test
    void test_with_negative_product_id(){
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        assertThrowsExactly(IllegalArgumentException.class,
                () -> priceController.getPriorityPriceOfBrandAndProductBetweenDate(
                        1L, -35455L, date));
    }

    private void getPriorityPriceOfBrandAndProductBetweenDateAndAssertEquals(
            String expected, Long brandId, Long productId, LocalDateTime date){
        Price price = priceController.getPriorityPriceOfBrandAndProductBetweenDate(brandId, productId, date);
        assertEquals(new BigDecimal(expected), price.getpriceValue());
    }

}
