package com.bis.sourcelead.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class OrgLocationsServiceImpl implements OrgLocationService {

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationsServiceImpl.class);
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;

	@Override
	public OrgLocations addOrgLocations(OrgLocations orgLocations) throws Exception {
		return orgLocationsDao.addOrgLocations(orgLocations);
	}

	@Override
	public OrgLocations updateOrgLocations(OrgLocations orgLocations) throws Exception {
		return orgLocationsDao.updateOrgLocations(orgLocations);
	}

	@Override
	public OrgLocations getOrgLocations(Long id) throws Exception {
		return orgLocationsDao.getOrgLocations(id);
	}

	@Override
	public Boolean deleteOrgLocations(Long id) throws Exception {
		return orgLocationsDao.deleteOrgLocations(id);
		
	}

	@Override
	public List<OrgLocations> getAllOrgLocations() throws Exception {
		return orgLocationsDao.getAllOrgLocations();
	}
	
	
	@Override
	public OrgLocations getOrgLocationsByLocationCode(String  locationCode) throws Exception {
		return orgLocationsDao.getOrgLocationsByLocationCode(locationCode);
	}
	

	@Override
	public OrgLocations getOrgLocationsByOrgCode(String  orgCode) throws Exception {
		return orgLocationsDao.getOrgLocationsByOrgCode(orgCode);
	}
	
	
	@Override
	public OrgLocations findByEmail(String email, Long orgLocationId) throws Exception {
		logger.debug("In OrgLocationService class...");
		return orgLocationsDao.findByEmail(email,orgLocationId);
	}

	@Override
	public OrgLocations saveOrgLocations(OrgSettings orgSettings, OrgLocations orgLocations) throws Exception {
		
		orgLocations.setState(orgSettings.getState());
		orgLocations.setCity(orgSettings.getCity());
		orgLocations.setZipCode(orgSettings.getZipcode());
		orgLocations.setPhone(orgSettings.getPhone());
		orgLocations.setFax(orgSettings.getFax());
		orgLocationsDao.updateOrgLocations(orgLocations);
		
		return orgLocations;
	}
	
	/**
	 * @author VenkatKesav
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is for org locations saving.
	 * 
	 * @param jsonObj, addOrg, orgLocations, loginUserCode
	 *  
	 * @return Set<OrgLocations> 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public OrgLocations orgLocationsListByJson(JSONObject jsonObj, Org addOrg, OrgLocations orgLocations, String loginUserCode,String responderType) throws Exception {
		String locationCode = orgLocations.getLocationCode();
		OrgLocations clientOrgLocations = new OrgLocations();
			clientOrgLocations.setOrg(addOrg);
			if(jsonObj.has("phone")){
				clientOrgLocations.setPhone(jsonObj.getString("phone"));
			}
			if(jsonObj.has("emailId")){
				clientOrgLocations.setEmail(jsonObj.getString("emailId"));
			}
			
			if(jsonObj.has("country")){
				clientOrgLocations.setCountry(jsonObj.getString("country"));
			}
			if(jsonObj.has("state")){
				clientOrgLocations.setState(jsonObj.getString("state"));
			}
			
			if(jsonObj.has("city")){
				clientOrgLocations.setCity(jsonObj.getString("city"));
			}
			String clientCountry = locationCode.substring(Math.max(locationCode.length() - 2, 0));
			clientOrgLocations.setLocationCode(addOrg.getOrgCode() + clientCountry);
			clientOrgLocations.setCreatedDate(commonUtil.currentDate());
			clientOrgLocations.setCreatedBy(loginUserCode);
			clientOrgLocations.setUpdatedBy(loginUserCode);
			clientOrgLocations.setUpdatedDate(commonUtil.currentDate());
		
		return clientOrgLocations;
	}

	@Override
	public List<OrgLocations> getOrgsListForClientAndVendorOrgSearch(String orgCode)
			throws Exception {
		
		return orgLocationsDao.getOrgsListForClientAndVendorOrgSearch(orgCode);
	}
	
}
