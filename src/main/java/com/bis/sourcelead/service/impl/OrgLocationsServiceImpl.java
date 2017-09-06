package com.bis.sourcelead.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class OrgLocationsServiceImpl implements OrgLocationService {

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationsServiceImpl.class);
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	private CommonUtil commonUtil;

	@Override
	public OrgLocations addOrgLocations(OrgLocations orgLocations) throws Exception {
		orgLocations.setCoolingOffPeriod("3 Months"); //to set default cooling-off period to three months
		return orgLocationsDao.addOrgLocations(orgLocations);
	}

	@Override
	public OrgLocations updateOrgLocations(OrgLocations orgLocations) throws Exception {
		orgLocations.setUpdatedDate(commonUtil.currentDate());
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
	public Boolean isLoginUserIsSuperUser(Long userId,Long orgLocationId) throws Exception {
		return orgLocationsDao.isLoginUserIsSuperUser(userId,orgLocationId);
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
	public OrgLocations findByEmail(String email, Long orgLocationId) throws Exception {
		logger.debug("In OrgLocationService class...");
		return orgLocationsDao.findByEmail(email,orgLocationId);
	}

	@Override
	public OrgLocations saveOrgLocations(OrgSettings orgSettings, OrgLocations orgLocations) throws Exception {
		Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgSettings.getTimeZone()));
		orgLocations.setState(orgSettings.getState());
		orgLocations.setCity(orgSettings.getCity());
		orgLocations.setZipCode(orgSettings.getZipcode());
		orgLocations.setPhone(orgSettings.getPhone());
		orgLocations.setFax(orgSettings.getFax());
		orgLocations.setTimezone(timezone);
		orgLocations.setDateFormat(orgSettings.getDateFormat1());
	    orgLocations.setOrgLocationName(orgSettings.getCompanyName());
        orgLocations.setOrgLocationAddress(orgSettings.getAddress());
        orgLocations.setWebsite(convertWebsite(orgSettings.getWebsite()));
        orgLocations.setOrgDescription(orgSettings.getAboutCompany());
      
        
		
        orgLocationsDao.updateOrgLocations(orgLocations);
		
		return orgLocations;
	}
	
	
	private String convertWebsite(String website){
		if(!website.startsWith("http") && !website.startsWith("https")){
			 website = "http://" + website;
	    }        
	    URL netUrl = null;
		try {
			netUrl = new URL(website);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	    String host = netUrl.getHost();
	    if(host.startsWith("www")){
	        host = host.substring("www".length()+1);
	    }
	    return host;
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
		Timezone timezone = null;
		OrgLocations clientOrgLocations = new OrgLocations();
			clientOrgLocations.setOrg(addOrg);
			if(jsonObj.has("phone")){
				clientOrgLocations.setPhone(jsonObj.getString("phone"));
			}
			if(jsonObj.has("emailId")){
				clientOrgLocations.setEmail(jsonObj.getString("emailId"));
			}
			
			if(jsonObj.has("webSite") && !isNullOrEmpty(jsonObj.getString("webSite"))){
				clientOrgLocations.setWebsite(jsonObj.getString("webSite"));
			}
			
			if(jsonObj.has("country") && !isNullOrEmpty(jsonObj.getString("country"))){
				clientOrgLocations.setCountry(jsonObj.getString("country"));
			}else if(jsonObj.has("orgCountry") && !isNullOrEmpty(jsonObj.getString("orgCountry"))){
				clientOrgLocations.setCountry(jsonObj.getString("orgCountry"));
			}
			if(jsonObj.has("state") && !isNullOrEmpty(jsonObj.getString("state"))){
				clientOrgLocations.setState(jsonObj.getString("state"));
			}else if(jsonObj.has("orgState") && !isNullOrEmpty(jsonObj.getString("orgState"))){
				clientOrgLocations.setState(jsonObj.getString("orgState"));
			}
			
			if(jsonObj.has("city") && !isNullOrEmpty(jsonObj.getString("city"))){
				clientOrgLocations.setCity(jsonObj.getString("city"));
			}else if(jsonObj.has("orgCity") && !isNullOrEmpty(jsonObj.getString("orgCity"))){
				clientOrgLocations.setCity(jsonObj.getString("orgCity"));
			}
			
			if(jsonObj.has("orgZipcode") && !isNullOrEmpty(jsonObj.getString("orgZipcode"))){
				clientOrgLocations.setZipCode(jsonObj.getString("orgZipcode"));
			}else if(jsonObj.has("zipcode") && !isNullOrEmpty(jsonObj.getString("zipcode"))){
				clientOrgLocations.setZipCode(jsonObj.getString("zipcode"));
			}
			
			if(jsonObj.has("orgLocation") && !isNullOrEmpty(jsonObj.getString("orgLocation"))){
				clientOrgLocations.setLocation(jsonObj.getString("orgLocation"));
			}else if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
				clientOrgLocations.setLocation(jsonObj.getString("location"));
			}
			
			if(jsonObj.has("latitude") && !isNullOrEmpty(jsonObj.getString("latitude"))){
				clientOrgLocations.setLatitude(jsonObj.getString("latitude"));
			}
			
			if(jsonObj.has("longitude") && !isNullOrEmpty(jsonObj.getString("longitude"))){
				clientOrgLocations.setLongitude(jsonObj.getString("longitude"));
			}
			
			if(jsonObj.has("country") && !isNullOrEmpty(jsonObj.getString("country"))){
				if("Australia".equalsIgnoreCase(jsonObj.getString("country"))){
					timezone = timeZoneService.getTimezone(298l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				}else if("India".equalsIgnoreCase(jsonObj.getString("country"))){
					timezone = timeZoneService.getTimezone(246l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("dd-MM-yyyy");
				}else if("France".equalsIgnoreCase(jsonObj.getString("country"))){
					timezone = timeZoneService.getTimezone(342l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				}else if("Singapore".equalsIgnoreCase(jsonObj.getString("country"))){
					timezone = timeZoneService.getTimezone(272l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				}else if("United Kingdom".equalsIgnoreCase(jsonObj.getString("country"))){
					timezone = timeZoneService.getTimezone(333l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				}else if("United States".equalsIgnoreCase(jsonObj.getString("country"))){
					timezone = timeZoneService.getTimezone(87l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				} 
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
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public List<OrgLocations> getAllOrgLocationsByOrgId(Long orgId)throws Exception {
		return orgLocationsDao.getAllOrgLocationsByOrgId(orgId);
	}


	@Override
	public List<OrgLocations> getAllOrgsStartsWithAlphabet(String alphabet) {
		return orgLocationsDao.getAllOrgsStartsWithAlphabet(alphabet);
	}

	@Override
	public List<OrgLocations> getAllRegisteredOrgLocations() throws Exception
	{
		return orgLocationsDao.getAllRegisteredOrgLocations();
	}

	@Override
	public List<OrgLocations> getAllRegisteredOrgLocationsByOrgId(Long orgId,Long orgLocationId) throws Exception{
		return orgLocationsDao.getAllRegisteredOrgLocationsByOrgId(orgId,orgLocationId);
	}

	@Override
	public OrgLocations getPrimaryOrgLocationByOrgId(Long orgId) throws Exception
	{
		return orgLocationsDao.getPrimaryOrgLocationByOrgId(orgId);
	}

	@Override
	public OrgLocations saveOrgLocationsOrgShortCode(OrgLocations orgLocation,JSONObject jsonObj) throws NumberFormatException, Exception {
			if(jsonObj.has("generatedUserId") && !"".equalsIgnoreCase(jsonObj.getString("generatedUserId"))){
				orgLocation.setGeneratedUserId(jsonObj.getString("generatedUserId").toUpperCase());
			}
			if(jsonObj.has("timeZone") && !"".equalsIgnoreCase(jsonObj.getString("timeZone"))){
				Timezone timezone = timeZoneService.getTimezone(Long.parseLong(jsonObj.getString("timeZone")));
				orgLocation.setTimezone(timezone);
			}
			if(jsonObj.has("dateFormatForOrg") && !"".equalsIgnoreCase(jsonObj.getString("dateFormatForOrg"))){
				orgLocation.setDateFormat(jsonObj.getString("dateFormatForOrg"));
			}
			if(jsonObj.has("averageWorkingHours") && !"".equalsIgnoreCase(jsonObj.getString("averageWorkingHours"))){
				orgLocation.setAverageWorkingHours(Integer.parseInt(jsonObj.getString("averageWorkingHours")));
			}
		return orgLocationsDao.saveOrgLocationsOrgShortCode(orgLocation);
		
	}

	@Override
	public String getDateFormatByOrgLocation(OrgLocations orgLocation) {
			return orgLocation.getDateFormat();
	}

	@Override
	public boolean validateOrgShortCode(String orgShortId) {
		if(!orgShortId.isEmpty()){
			return orgLocationsDao.validateOrgShortCode(orgShortId);
		}else{
			return false;
		}
	}

	@Override
	public List<OrgLocations> getAllRegisteredOrgLocationsForClientProject(List<Long> orgLocationId) throws Exception {
		return orgLocationsDao.getAllRegisteredOrgLocationsForClientProject(orgLocationId);
	}
}
