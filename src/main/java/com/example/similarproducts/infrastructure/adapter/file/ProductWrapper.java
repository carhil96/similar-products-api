package com.example.similarproducts.infrastructure.adapter.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductWrapper {
    private String path;
    private String body;
    private Integer status; // opcional, por si hay 404 o 500 en el mock
    private Map<String, String> headers;
    private Integer delay;

    public ProductWrapper(){

    }

    public String getBody() {
        return body;
    }

    public String getPath() {
        return path;
    }

    public Integer getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
