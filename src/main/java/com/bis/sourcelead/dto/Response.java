package com.bis.sourcelead.dto;

public class Response<T> {
	private T object;
	private String error;
	
	public T getObject()
	{
		return object;
	}
	
	public void setObject(T object)
	{
		this.object = object;
	}
	
	public String getError()
	{
		return error;
	}
	
	public void setError(String error)
	{
		this.error = error;
	}
}
