package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;

public interface OrgLocationsDao {

	
	public OrgLocations addOrgLocations(OrgLocations orgLocations)throws Exception;
    public OrgLocations updateOrgLocations(OrgLocations orgLocations)throws Exception;
    public OrgLocations getOrgLocations(Long id)throws Exception;
    public Boolean deleteOrgLocations(Long id)throws Exception;
    public List<OrgLocations> getAllOrgLocations()throws Exception; 
    public  OrgLocations getOrgLocationsByLocationCode(String  locationCode) throws Exception;
    public  OrgLocations getOrgLocationsByOrgCode(String  orgCode) throws Exception;
    
    OrgLocations findByEmail(String email) throws Exception;
}