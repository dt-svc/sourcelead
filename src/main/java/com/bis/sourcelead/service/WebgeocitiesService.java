package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Webgeocities;
/**
 * 
 * @author Prasad Salina
 *
 */

public interface WebgeocitiesService {

	
	List<Webgeocities> getAllCitiesByStateAndCountry(String stateName, String countryName) throws Exception;
	
}