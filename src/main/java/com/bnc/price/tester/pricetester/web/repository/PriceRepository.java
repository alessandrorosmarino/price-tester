package com.bnc.price.tester.pricetester.web.repository;

import com.bnc.price.tester.pricetester.web.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

    /***
     * This method finds the First Price using these rules:
     * the BRAND_ID must be equal to the brandId passed
     * the PRODUCT_ID  must be equal to the productId passed
     * the START_DATE must be before the startDate provided
     * the END_DATE must be after the endDate provided
     * After filtering, the result is ordered by PRIORITY in descending order
     * putting the one with the highest priority first.
     * After ordering, the first result is returned.
     * @param brandId The ID of the brand to filter by
     * @param productId The ID of the product to filter by
     * @param startDate The start date to filter by
     * @param endDate The end date to filter by
     * @return The Price with the highest priority that matches the criteria
     */
    Price findFirstByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate);
}
