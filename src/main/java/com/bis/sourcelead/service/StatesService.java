package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.States;

public interface StatesService {

	
	List<States> getAllStatesByCountry(String countryName) throws Exception;
	
}