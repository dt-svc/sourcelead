package com.bis.sourcelead.service;

import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.form.OrgSettings;

public interface OrgLocationService {
	
	public OrgLocations addOrgLocations(OrgLocations orgLocations) throws Exception;

	public OrgLocations updateOrgLocations(OrgLocations orgLocations) throws Exception;

	public OrgLocations getOrgLocations(Long id) throws Exception;

	public Boolean deleteOrgLocations(Long id) throws Exception;

	public List<OrgLocations> getAllOrgLocations() throws Exception;

	public OrgLocations getOrgLocationsByLocationCode(String locationCode) throws Exception;

	public OrgLocations getOrgLocationsByOrgCode(String orgCode) throws Exception;

	public OrgLocations saveOrgLocations(OrgSettings orgSettings, OrgLocations orgLocations) throws Exception;

	public OrgLocations findByEmail(String email,Long orgLocationId) throws Exception;

	OrgLocations orgLocationsListByJson(JSONObject jsonObj, Org addOrg, OrgLocations orgLocations,String loginUserCode,String responderType) throws Exception;
	
	public List<OrgLocations> getOrgsListForClientAndVendorOrgSearch(String orgCode) throws Exception;
	
	public OrgLocations getOrgLocationByOrgId(Long orgId) throws Exception;

}