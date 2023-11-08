package com.bnc.price.tester.pricetester.web.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "PRICE")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Long brandId;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer priority;
    @Column(nullable = false, columnDefinition = "decimal(19,2) default 0.00")
    private BigDecimal priceValue;
    @Column(nullable = false, columnDefinition = "varchar(3) default 'EUR'")
    private String curr;

    public Price() {
    }

    public Price(Integer id, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
                 Long productId, Integer priority, BigDecimal priceValue, String curr) {
        this.id = id;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
        this.priority = priority;
        this.priceValue = priceValue;
        this.curr = curr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public BigDecimal getpriceValue() {
        return priceValue;
    }

    public void setpriceValue(BigDecimal price) {
        this.priceValue = price;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }


}
