package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Cities;

public interface CitiesService {
	
	public Cities addCity(Cities cities)throws Exception;
    public Cities updateCity(Cities cities)throws Exception;
    public Cities getCity(String id)throws Exception;
    public Boolean deleteCity(String id)throws Exception;
    public List<Cities> getAllCities()throws Exception; 
}