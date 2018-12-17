package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.States;

/**
 * 
 * @author Prasad Salina
 *
 */
public interface StatesDao {

	List<States> getAllStatesByCountryCode(String countryCode) throws Exception;
	
	States getStatesByStateName(String name) throws Exception;
	
	List<String> getStatesCodesByStateName(String name) throws Exception;

	States getStateById(long stateId);

	String getStateCodeByCountryCodeAndState(String countryCode, String state);

}