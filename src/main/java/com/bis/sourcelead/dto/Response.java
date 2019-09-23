package com.bis.sourcelead.dto;

import java.util.List;

public class Response<T> {

    private T object;
    private String error;
    private String response;
    private String responseMessage;
    private List<CandidateDTO> responseObject;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

	public List<CandidateDTO> getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(List<CandidateDTO> responseObject) {
		this.responseObject = responseObject;
	}


	
}
