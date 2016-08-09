package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Countries;

public interface CountriesService {

	public Countries getCountriesByCountryName(String name) throws Exception;
	
	List<Countries> getAllCountries() throws Exception;
	
}
