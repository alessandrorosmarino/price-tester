package com.bnc.price.tester.pricetester.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ConfigurationProperties("date.properties")
public class DateProperties {

    /***
     * The minimum date to be used in the application
     */
    private LocalDateTime minDate = LocalDateTime.of(1900, 1, 1, 0, 0, 0);

    /***
     * The maximum date to be used in the application
     */
    private LocalDateTime maxDate = LocalDateTime.of(2100, 1, 1, 0, 0, 0);

    public LocalDateTime getMinDate() {
        return minDate;
    }

    public void setMinDate(LocalDateTime minDate) {
        this.minDate = minDate;
    }

    public LocalDateTime getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(LocalDateTime maxDate) {
        this.maxDate = maxDate;
    }
}
