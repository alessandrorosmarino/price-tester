package com.bnc.price.tester.pricetester.web.controller;

import com.bnc.price.tester.pricetester.exception.ErrorResponseObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.ServiceNotFoundException;

/***
 * This class is used to handle all APIs that are not implemented by the other controllers
 */
@RestController
public class PriceErrorController implements ErrorController {

    @RequestMapping("/error")
    public ErrorResponseObject handleError() throws ServiceNotFoundException {
        throw new ServiceNotFoundException("Incorrect API");
    }
}
