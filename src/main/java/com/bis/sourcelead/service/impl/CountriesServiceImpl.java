package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CountriesDao;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.service.CountriesService;

@Service
public class CountriesServiceImpl implements CountriesService{
	
	@Autowired
	CountriesDao countriesDao;

	@Override
	public Countries getCountriesByCountryName(String name) {
		return null;
	}

}
