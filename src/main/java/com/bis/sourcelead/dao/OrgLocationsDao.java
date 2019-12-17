package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

public interface OrgLocationsDao {

	
	public OrgLocations addOrgLocations(OrgLocations orgLocations)throws Exception;
   
	public OrgLocations updateOrgLocations(OrgLocations orgLocations)throws Exception;
    
	public OrgLocations getOrgLocations(Long id)throws Exception;
    
	public Boolean deleteOrgLocations(Long id)throws Exception;
    
	public Boolean isLoginUserIsSuperUser(Long userId,Long orgLocationId) throws Exception;
	
	public OrgLocations getOrgLocationsByOrgLocationName(String orgLocationName) throws Exception;
    
	public List<OrgLocations> getAllOrgLocations()throws Exception; 
	
	public List<OrgLocations> getAllOrgLocationsByOrgTypeCompany() throws Exception;
    
	public  OrgLocations getOrgLocationsByLocationCode(String  locationCode) throws Exception;
    
    OrgLocations findByEmail(String email, Long orgLocationId) throws Exception;
    
    public List<OrgLocations> getOrgsListForClientAndVendorOrgSearch(String orgCode) throws Exception;
    
    public List<OrgLocations> getAllOrgLocationsByOrgId(Long orgId) throws Exception;
    
	public List<OrgLocations> getAllOrgsStartsWithAlphabet(String alphabet);
	
	public List<OrgLocations> getAllRegisteredOrgLocationsForClientProject(List<Long> orgLocationId) throws Exception;
	
	public List<OrgLocations> getAllRegisteredOrgLocations() throws Exception;
	
	public List<OrgLocations> getAllRegisteredOrgLocationsByOrgId(Long orgId,Long orgLocationId) throws Exception;
	
	public OrgLocations getPrimaryOrgLocationByOrgId(Long orgId) throws Exception;

	public OrgLocations saveOrgLocationsOrgShortCode(OrgLocations orgLocation) throws SourceLeadConstraintViolationException;

	public boolean validateOrgShortCode(String orgShortId);
    
	public List<OrgLocations> userList(Long orgId) throws Exception;

	public Boolean isTwoStepVerificationEnabledForAllEnrolledOrgsByUserId(Long userId);
	
	public List<OrgLocations> getAllRegisteredOrgLocationsExceptLoggedInLocation(Long orgLocationId,Long orgId) throws Exception;
	
	Integer getRegisteredOrgLoctionsCountByOrgId(Long orgId) throws Exception;
	
	List<Long> getOrgLocationsIdsListByOrgIdAndCountry(Long orgId,String country) throws Exception;
	
	public List<OrgLocations> getAllCurrentRegisteredOrgLocationsByOrgIdAndCountry(Long orgId,String country) throws Exception;
	
	public List<OrgLocations> getAllRegisteredWorkLocationsByOrgId(Long orgId) throws Exception;
	
	public OrgLocations getOrgLocationById(Long id) throws Exception;
	
	public OrgLocations getOrgLocationByOrgIdAndCountry(Long orgId,String country) throws Exception;
}