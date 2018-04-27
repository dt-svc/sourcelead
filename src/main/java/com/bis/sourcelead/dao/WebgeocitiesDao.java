package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Webgeocities;

/**
 * 
 * @author Prasad Salina
 *
 */
public interface WebgeocitiesDao {

	List<Webgeocities> getAllCitiesByStateCodeCountryCode(List<String> stateCode, String countryCode) throws Exception;
	
	List<Webgeocities> getAllCitiesByCountryCode(String countryCode) throws Exception;
}
