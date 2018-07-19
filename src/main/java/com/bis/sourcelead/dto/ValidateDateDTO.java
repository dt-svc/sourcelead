package com.bis.sourcelead.dto;

public class ValidateDateDTO {
	
	private String validateType;
	
	private String errorMessage;

	public String getValidateType()
	{
		return validateType;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setValidateType(String validateType)
	{
		this.validateType = validateType;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	
}
