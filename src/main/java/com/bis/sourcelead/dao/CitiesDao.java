package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Cities;

public interface CitiesDao {

	
	public Cities addCity(Cities cities)throws Exception;
    public Cities updateCity(Cities cities)throws Exception;
    public Cities getCity(String id)throws Exception;
    public Boolean deleteCity(String id)throws Exception;
    public List<Cities> getAllCities()throws Exception; 
}