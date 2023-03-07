package com.eminetekcan.BlogApp.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, String resourceField, int resourceValue) {
        super(String.format("%s not found with %s : %d ", resourceName,resourceField,Long.valueOf(resourceValue)));
    }
}
