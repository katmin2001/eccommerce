package com.vti.ecommerce.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


public class Result {
    private String message;

    private String status;
    private Object data;

    public Result(String message, String status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
