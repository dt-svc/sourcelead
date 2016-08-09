package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Countries;

public interface CountriesDao {

	public Countries getCountriesByCountryName(String name) throws Exception;

	public List<Countries> getAllCountries() throws Exception;
}
