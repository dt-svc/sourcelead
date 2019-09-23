package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementLocations;

public interface RequirementLocationsDao {

	RequirementLocations saveRequirementLocations(RequirementLocations requirementLocations) throws Exception;
	
	List<RequirementLocations> getRequirementLocationsListByRequirementId(Long requirementId) throws Exception;
	
	List<String> getRequirementLocationsUUIDByRequirementId(Long requirementId) throws Exception;
	
	RequirementLocations getRequirementLocationsByLocationUUID(String locationUUID) throws Exception;
	
	void deleteRequirementLocations(RequirementLocations requirementLocations) throws Exception;

	List<Object[]> getRequirementLocationsListByRequirementIdMap(List<Long> reqIdsList) throws Exception;
	
	public List<RequirementLocations> getAllRequirementByOrgLocationId(Long orgLocationId) throws Exception;
	
	List<String> getRequirementLocationsStringListByRequirementId(Long requirementId) throws Exception;

}
