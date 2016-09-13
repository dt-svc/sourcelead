package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Requirement;

public interface RequirementDao {

	
	public Requirement storeRequirement(Requirement requirement) throws Exception;
	
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId) throws Exception;
	
}