package com.eminetekcan.BlogApp.exception;

public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String resourceField;
    private String resourceValue;
    public ResourceNotFoundException(String resourceName, String resourceField, int resourceValue) {
        super(String.format("%s not found with %s : %d ", resourceName,resourceField,Long.valueOf(resourceValue)));
        this.resourceName=resourceName;
        this.resourceField=resourceField;
        this.resourceValue=Integer.toString(resourceValue);
    }
}
