package com.bis.sourcelead.exception;

/**
 *
 * @author Raja Gopal Reddy P
 */
public class JobPostException extends Exception {
    
    private String message;
    public JobPostException() {
        
    }
    
    public JobPostException(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}
