package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.Countries;

public interface CountriesDao {

	public Countries getCountriesByCountryName(String name);
}
