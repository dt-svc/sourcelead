package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementLocationsDao;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.service.RequirementLocationsService;

@Service
public class RequirementLocationsServiceImpl implements RequirementLocationsService{
	
	@Autowired
	RequirementLocationsDao requirementLocationsDao;

	@Override
	public RequirementLocations saveRequirementLocations(RequirementLocations requirementLocations) throws Exception {
		return requirementLocationsDao.saveRequirementLocations(requirementLocations);
	}

	@Override
	public List<RequirementLocations> getRequirementLocationsListByRequirementId(Long requirementId) throws Exception {
		return requirementLocationsDao.getRequirementLocationsListByRequirementId(requirementId);
	}

	@Override
	public List<String> getRequirementLocationsUUIDByRequirementId(Long requirementId) throws Exception {
		return requirementLocationsDao.getRequirementLocationsUUIDByRequirementId(requirementId);
	}

	@Override
	public RequirementLocations getRequirementLocationsByLocationUUID(String locationUUID) throws Exception {
		return requirementLocationsDao.getRequirementLocationsByLocationUUID(locationUUID);
	}

	@Override
	public void deleteRequirementLocations(RequirementLocations requirementLocations) throws Exception {
		requirementLocationsDao.deleteRequirementLocations(requirementLocations);
	}

	@Override
	public List<Object[]> getRequirementLocationsListByRequirementIdMap(List<Long> reqIdsList) throws Exception {
		return requirementLocationsDao.getRequirementLocationsListByRequirementIdMap(reqIdsList);
	}

	@Override
	public List<RequirementLocations> getAllRequirementByOrgLocationId(Long orgLocationId) throws Exception
	{
		return requirementLocationsDao.getAllRequirementByOrgLocationId(orgLocationId);

	}

	@Override
	public List<String> getRequirementLocationsStringListByRequirementId(Long requirementId) throws Exception {
		return requirementLocationsDao.getRequirementLocationsStringListByRequirementId(requirementId);
	}

	

}
