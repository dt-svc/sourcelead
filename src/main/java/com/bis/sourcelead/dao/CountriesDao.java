package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Countries;

public interface CountriesDao {

	public Countries getCountriesByCountryName(String name);

	public List<Countries> getAllCountries();
}
