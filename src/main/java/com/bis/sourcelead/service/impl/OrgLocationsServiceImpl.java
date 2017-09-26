package com.bis.sourcelead.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
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
		orgLocations.setCoolingOffPeriod(Constants.DEFAULT_COOLINGOFF_PERIOD); //to set default cooling-off period to three months
		return orgLocationsDao.addOrgLocations(orgLocations);
	}

	@Override
	public OrgLocations updateOrgLocations(OrgLocations orgLocations) throws Exception {
		orgLocations.setUpdatedDate(commonUtil.currentDate());
		if(orgLocations.getCoolingOffPeriod() == null || orgLocations.getCoolingOffPeriod().isEmpty()){
			orgLocations.setCoolingOffPeriod(Constants.DEFAULT_COOLINGOFF_PERIOD); //to set default cooling-off period to three months
		}
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
      
        if(orgLocations.getCoolingOffPeriod() == null || orgLocations.getCoolingOffPeriod().isEmpty()){
			orgLocations.setCoolingOffPeriod(Constants.DEFAULT_COOLINGOFF_PERIOD); //to set default cooling-off period to three months
		}
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
	public OrgLocations orgLocationsListByJson(ClientDTO clientDTO, Org addOrg, OrgLocations orgLocations, String loginUserCode,String responderType) throws Exception {
		String locationCode = orgLocations.getLocationCode();
		Timezone timezone = null;
		OrgLocations clientOrgLocations = new OrgLocations();
			clientOrgLocations.setOrg(addOrg);
			if(StringUtils.isNotBlank(clientDTO.getPhoneNumber())){
				clientOrgLocations.setPhone(clientDTO.getPhoneNumber());
			}
			if(StringUtils.isNotBlank(clientDTO.getEmail())){
				clientOrgLocations.setEmail(clientDTO.getEmail());
			}
			
			if(StringUtils.isNotBlank(clientDTO.getWebSite())){
				clientOrgLocations.setWebsite(clientDTO.getWebSite());
			}
			
			if(StringUtils.isNotBlank(clientDTO.getCountry())){
				clientOrgLocations.setCountry(clientDTO.getCountry());
			}else if(StringUtils.isNotBlank(clientDTO.getOrgCountry())){
				clientOrgLocations.setCountry(clientDTO.getOrgCountry());
			}
			if(StringUtils.isNotBlank(clientDTO.getState())){
				clientOrgLocations.setState(clientDTO.getState());
			}else if(StringUtils.isNotBlank(clientDTO.getOrgState())){
				clientOrgLocations.setState(clientDTO.getOrgState());
			}
			
			if(StringUtils.isNotBlank(clientDTO.getCity())){
				clientOrgLocations.setCity(clientDTO.getCity());
			}else if(StringUtils.isNotBlank(clientDTO.getOrgCity())){
				clientOrgLocations.setCity(clientDTO.getOrgCity());
			}
			
			if(StringUtils.isNotBlank(clientDTO.getOrgZipcode())){
				clientOrgLocations.setZipCode(clientDTO.getOrgZipcode());
			}else if(StringUtils.isNotBlank(clientDTO.getZipcode())){
				clientOrgLocations.setZipCode(clientDTO.getZipcode());
			}
			
			if(StringUtils.isNotBlank(clientDTO.getOrgLocation())){
				clientOrgLocations.setLocation(clientDTO.getOrgLocation());
			}else if(StringUtils.isNotBlank(clientDTO.getLocation())){
				clientOrgLocations.setLocation(clientDTO.getLocation());
			}
			
			if(StringUtils.isNotBlank(clientDTO.getLatitude())){
				clientOrgLocations.setLatitude(clientDTO.getLatitude());
			}
			
			if(StringUtils.isNotBlank(clientDTO.getLongitude())){
				clientOrgLocations.setLongitude(clientDTO.getLongitude());
			}
			
			if(StringUtils.isNotBlank(clientDTO.getCountry())){
				if("Australia".equalsIgnoreCase(clientDTO.getCountry())){
					timezone = timeZoneService.getTimezone(298l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				}else if("India".equalsIgnoreCase(clientDTO.getCountry())){
					timezone = timeZoneService.getTimezone(246l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("dd-MM-yyyy");
				}else if("France".equalsIgnoreCase(clientDTO.getCountry())){
					timezone = timeZoneService.getTimezone(342l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				}else if("Singapore".equalsIgnoreCase(clientDTO.getCountry())){
					timezone = timeZoneService.getTimezone(272l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				}else if("United Kingdom".equalsIgnoreCase(clientDTO.getCountry())){
					timezone = timeZoneService.getTimezone(333l);
					clientOrgLocations.setTimezone(timezone);
					clientOrgLocations.setDateFormat("MM-dd-yyyy");
				}else if("United States".equalsIgnoreCase(clientDTO.getCountry())){
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
   @Override
	public List<OrgLocations> userList(Long orgId) throws Exception
	{
		return orgLocationsDao.userList(orgId);
	}
}
