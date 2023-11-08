package com.bnc.price.tester.pricetester.exception;

import java.time.LocalDateTime;

public class ErrorResponseObject {

    private LocalDateTime dateTime;
    private String message;
    private String detail;
    private String error;
    private String path;
    private Integer status;

    public ErrorResponseObject() {
    }

    public ErrorResponseObject(LocalDateTime dateTime, String message, String detail, String error, String path, Integer status) {
        this.dateTime = dateTime;
        this.message = message;
        this.detail = detail;
        this.error = error;
        this.path = path;
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
