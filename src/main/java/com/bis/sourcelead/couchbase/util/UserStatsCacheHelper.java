package com.bis.sourcelead.couchbase.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.CouchbaseClient;
import com.bis.sourcelead.couchbase.LoginUserSessionStoreDTO;
import com.bis.sourcelead.couchbase.OrgLocationCacheDTO;
import com.bis.sourcelead.couchbase.UserRoleSessionProfileDTO;
import com.bis.sourcelead.dto.UserDetailsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/** 
 * Purpose : 
 */

@Component
public class UserStatsCacheHelper {
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	TabsService tabsService;
	
	@Autowired
	CandidateService candiateService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	CouchbaseClient couchbaseClient;
	
	@Autowired
	SourceLeadSessionManager sourceLeadSessionManager;
	
	@Autowired
	UserService userService;
	
	
	
	/**
	 * @author Venkat
	 * @param orgLocationCode
	 * @return Returns the orglocation details as JSONObject
	 * 
	 * This method is used to get orglocation details document form couchbase.
	 * When user logged into the system we need some details about orglocation. Instead of going to
	 * db calls, just we pass orglocation code to this method and retrieve the orglocation details as json object.
	 */
	
	
	@SuppressWarnings("unchecked")
	public JSONObject getOrgLocationCacheDetails(String orgLocationCode) {
		orgLocationCode = "OrgLocation_"+orgLocationCode;
		JSONObject orgLocationJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getOrgLocationsDetails(orgLocationCode);
		if (sv != null) {

			JSONParser parser = new JSONParser();
			try {

				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					orgLocationJson = (JSONObject) parser.parse(jsonValue.toString());
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return orgLocationJson;
	}
	
	
	/*@SuppressWarnings("unchecked")
	public JSONObject getOrgLocationCacheDetails(String orgLocationCode) {
		orgLocationCode = "OrgLocation_"+orgLocationCode;
		JSONObject orgLocationJson = couchbaseClient.getOrgLocationDetails(orgLocationCode);
		return orgLocationJson;
		
	}*/
	

	/**
	 * @author Venkat
	 * @param userRoleAuthKey (loginUserName_<userRoleUUID>)
	 * @return Returns the UserRole details as JSONObject
	 * 
	 * This method is used to get logged in user details document form couchbase.
	 * When user logged into the system we need some details about user ie., user information, role information,
	 * orglocation information etc. Instead of going to
	 * db calls, just we pass userRoleAuthKey to this method and retrieve the logged in user details as json object.
	 */
	
	
	public JSONObject getLogInUserRoleStats(String userRoleAuthKey) {

		JSONObject userRoleJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getUserProfile(userRoleAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					userRoleJson = (JSONObject) parser.parse(jsonValue.toString());
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return userRoleJson;
	}
	
	/*public JSONObject getLogInUserRoleStats(String userRoleAuthKey) {

		JSONObject userRoleJson = new JSONObject();
		JSONObject jsonObject = couchbaseClient.getUserProfileDetails(userRoleAuthKey);
		return jsonObject;
	}*/
	
	
	/**
	 * @author Venkat
	 * @param userRoleAuthKey (loginUserName_<userRoleUUID>)
	 * @return logged in user left menu information as json object
	 * This method is used to get logged in user left menu access information.
	 */
	
	
	
	public JSONObject getLogInUserRoleMenusJson(String userRoleAuthKey) {

		JSONObject userRoleMenusListJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getUserProfile(userRoleAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						if (keyStr.equalsIgnoreCase("menuList")) {
							Object keyvalue = jsonObjVal.get(keyStr);
							userRoleMenusListJson.put(keyStr, keyvalue);
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return userRoleMenusListJson;
	}
	
	/*	public JSONObject getLogInUserRoleMenusJson(String userRoleAuthKey) {
		JSONObject userRoleMenusListJson = new JSONObject();
		JSONObject jsonObject = couchbaseClient.getUserProfileDetails(userRoleAuthKey);
		if (jsonObject != null) {
			try {
				for (Object okey : jsonObject.keySet()) {
					String keyStr = (String) okey;
					Object keyvalue = jsonObject.get(keyStr);
					if (keyStr.equalsIgnoreCase("menuList") && !keyStr.equalsIgnoreCase("rolesList")) {
						userRoleMenusListJson.put(keyStr, keyvalue);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userRoleMenusListJson;
	
		
	}*/
	
	
	
	/**
	 * @author Venkat
	 * @param userSessionAuthKey (<sessionId>_SESSION)
	 * @return logged in user session information
	 * This method is used to get logged in user session details.
	 */
	
	
	public JSONObject getLogInUserSessionJson(String userSessionAuthKey) {

		JSONObject userSessionListJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getUserLoginSession(userSessionAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						Object keyvalue = jsonObjVal.get(keyStr);
						userSessionListJson.put(keyStr, keyvalue);

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return userSessionListJson;
	}
	
	/*public JSONObject getLogInUserSessionJson(String userSessionAuthKey) {
		
		JSONObject sessionStoreJson = couchbaseClient.getUserLoginSessionStoreDetails(userSessionAuthKey);
		return sessionStoreJson;
		
	}*/
	
	
	/**
	 * @author Venkat
	 * @param userRoleAuthKey (loginUserName_<userRoleUUID>)
	 * @return logged in user left menu information as List<String>
	 * This method is used to get logged in user left menu access information.
	 */
	
	
	public List<String> getLogInUserRoleMenusList(String userRoleAuthKey) {

		Object sv = null;
		List<String> menusList = new ArrayList<>();
		sv = couchbaseClient.getUserProfile(userRoleAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						if (keyStr.equalsIgnoreCase("menuList")) {
							String keyvalue = jsonObjVal.get(keyStr).toString();
							String[] menusStringArray = keyvalue.toString().substring(1, keyvalue.toString().length()-1).split(",");
							for(int i=0; i<menusStringArray.length; i++) {
								menusList.add(menusStringArray[i].substring(1, menusStringArray[i].length()-1));
							}
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return menusList;
	}
	
	/*public List<String> getLogInUserRoleMenusList(String userRoleAuthKey) {
		
		List<String> menusList = new ArrayList<>();
		JSONObject jsonObject = couchbaseClient.getUserProfileDetails(userRoleAuthKey);
		if (jsonObject != null) {
			try {
				for (Object okey : jsonObject.keySet()) {
					String keyStr = (String) okey;
					if (keyStr.equalsIgnoreCase("menuList")) {
						String keyvalue = jsonObject.get(keyStr).toString();
						String[] menusStringArray = keyvalue.toString().substring(1, keyvalue.toString().length()-1).split(",");
						for(int i=0; i<menusStringArray.length; i++) {
							menusList.add(menusStringArray[i].substring(1, menusStringArray[i].length()-1));
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return menusList;
	}*/

	
	
	/**
	 * @author Venkat
	 * @param userRoleAuthKey (loginUserName_<userRoleUUID>)
	 * @return logged in user role access information as List<String>
	 * This method is used to get logged in user role access information.
	 */
	
	
	public List<String> getLogInUserRoleActionsList(String userRoleAuthKey) {

		Object sv = null;
		List<String> rolesList = new ArrayList<>();
		sv = couchbaseClient.getUserProfile(userRoleAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						if (keyStr.equalsIgnoreCase("rolesList")) {
							String keyvalue = jsonObjVal.get(keyStr).toString();
							String[] rolesStringArray = keyvalue.toString().substring(1, keyvalue.toString().length()-1).split(",");
							for(int i=0; i<rolesStringArray.length; i++) {
								rolesList.add(rolesStringArray[i].substring(1, rolesStringArray[i].length()-1));
							}
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return rolesList;
	}
	
	/*public List<String> getLogInUserRoleActionsList(String userRoleAuthKey) {

		List<String> rolesList = new ArrayList<>();
		JSONObject jsonObject = couchbaseClient.getUserProfileDetails(userRoleAuthKey);
		if (jsonObject != null) {
			try {
				for (Object okey : jsonObject.keySet()) {
					String keyStr = (String) okey;
					if (keyStr.equalsIgnoreCase("rolesList")) {
						String keyvalue = jsonObject.get(keyStr).toString();
						String[] rolesStringArray = keyvalue.toString().substring(1, keyvalue.toString().length()-1).split(",");
						for(int i=0; i < rolesStringArray.length; i++) {
							rolesList.add(rolesStringArray[i].substring(1, rolesStringArray[i].length()-1));
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rolesList;
	
	}*/
	
	
	
	public List<String> getCouchbaseQueueDocumentsList(String queueKey) {

		Object sv = null;
		List<String> docsList = new ArrayList<>();
		sv = couchbaseClient.getCouchbaseQueueDocumentsList(queueKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						if (keyStr.equalsIgnoreCase("queueDocumentsList")) {
							String keyvalue = jsonObjVal.get(keyStr).toString();
							String[] docsStringArray = keyvalue.toString().substring(1, keyvalue.toString().length()-1).split(",");
							for(int i=0; i<docsStringArray.length; i++) {
								docsList.add(docsStringArray[i].substring(1, docsStringArray[i].length()-1));
							}
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return docsList;
	}
	
	
	
	public JSONObject getUploadedQueueUserDetails(String queueKey) {

		JSONObject queueDetailsListJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getCouchbaseQueueDocumentsList(queueKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						if (!keyStr.equalsIgnoreCase("queueDocumentsList")) {
							Object keyvalue = jsonObjVal.get(keyStr);
							queueDetailsListJson.put(keyStr, keyvalue);
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return queueDetailsListJson;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject getCandidateDocument(String dockey) {
		JSONObject orgLocationJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getCouchbaseQueueDocumentsList(dockey);
		if (sv != null) {

			JSONParser parser = new JSONParser();
			try {

				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					orgLocationJson = (JSONObject) parser.parse(jsonValue.toString());
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return orgLocationJson;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject getBulkImportsFailureList(String dockey) {

		JSONObject failureUploadJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getCouchbaseQueueDocumentsList(dockey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						Object keyvalue = jsonObjVal.get(keyStr);
						failureUploadJson.put(keyStr, keyvalue);

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return failureUploadJson;
	
	}
	
	
	
	/**
	 * @author Venkat
	 * @param orgLocations
	 * This method is used to store orglocation information in couch.
	 */
	
	public void saveOrgLocationDetailsToCouchbase(OrgLocations orgLocations) {
		
		if(orgLocations != null &&  orgLocations.getOrg() != null) {	 
			OrgLocationCacheDTO orgLocationDTO = getOrgLocationCacheDetails(orgLocations);
			 ObjectMapper sessionObj = new ObjectMapper();
	         try {
				String sessionJsonStr = sessionObj.writeValueAsString(orgLocationDTO);
				JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
				StringBuilder orgLocationKey = new StringBuilder();
				orgLocationKey.append("OrgLocation_").append(orgLocationDTO.getOrgLocationCode());
				sourceLeadSessionManager.setOrgLocationsJsonString(orgLocationKey.toString(), "userProfile", jsonObject);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @author Venkat
	 * @param userRole
	 * This method is used to store userRole information in couch.
	 */
	public void saveUserProfileDetailsToCouchbase(UserRole userRole) {
		
		if(userRole.getUser() != null && StringUtils.isNotBlank(userRole.getUser().getUserName())) {
			 UserRoleSessionProfileDTO userRoleDTO = getUserRoleCacheDetails(userRole);
			 ObjectMapper sessionObj = new ObjectMapper();
	         try {
				String sessionJsonStr = sessionObj.writeValueAsString(userRoleDTO);
				JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
				StringBuilder userRoleKey = new StringBuilder();
				userRoleKey.append(userRole.getUser().getUserName().toLowerCase()).append("_AUTH_").append(userRole.getUuid());
				sourceLeadSessionManager.setUserProfileJsonString(userRoleKey.toString(), "userProfile", jsonObject);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public OrgLocationCacheDTO getOrgLocationCacheDetails(OrgLocations orgLocations) {
		OrgLocationCacheDTO orgLocationDTO = new OrgLocationCacheDTO();
		 orgLocationDTO.setDocumentType(Constants.COUCH_DOCUMENT_TYPE_ORGLOCATION);
		 orgLocationDTO.setOrgLocationName(orgLocations.getOrgLocationName());
		 orgLocationDTO.setOrgLocationId(String.valueOf(orgLocations.getId()));
		 orgLocationDTO.setOrgName(orgLocations.getOrg().getOrgName());
		 orgLocationDTO.setDba(orgLocations.getDba());
		 orgLocationDTO.setOrgId(String.valueOf((orgLocations.getOrg().getId())));
		 orgLocationDTO.setImgUrl(orgLocations.getOrg().getFileUrl());
		 orgLocationDTO.setRegisteredLocation(orgLocations.getOrgLocationAddress());
		 orgLocationDTO.setCountry(orgLocations.getCountry());
		 orgLocationDTO.setState(orgLocations.getState());
		 orgLocationDTO.setCity(orgLocations.getCity());
		 orgLocationDTO.setZipcode(orgLocations.getZipCode());
		 orgLocationDTO.setAdderss(orgLocations.getOrgLocationAddress());
		 orgLocationDTO.setPhone(orgLocations.getPhone());
		 orgLocationDTO.setFax(orgLocations.getFax());
		 orgLocationDTO.setDateFormat(orgLocations.getDateFormat());
		 orgLocationDTO.setWebsite(orgLocations.getWebsite());
		 
		 if(orgLocations.getCreatedDate() != null) {
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    String sDate = df.format(orgLocations.getCreatedDate());
			 orgLocationDTO.setCreatedDate(sDate);
		 }
		 
		 if(StringUtils.isNotBlank(orgLocations.getWeekOffDays())) {
			 orgLocationDTO.setWeekOffDays(orgLocations.getWeekOffDays());
		 }else {
			 orgLocationDTO.setWeekOffDays("Sat,Sun");
		 }
		
		 if(orgLocations.getWorkingDaysPerWeek() != null) {
			 orgLocationDTO.setWorkingDaysPerWeek(String.valueOf(orgLocations.getWorkingDaysPerWeek()));
		 }
		
		 if(orgLocations.getAverageWorkingHours() != null) {
			 orgLocationDTO.setAverageWorkingHours(String.valueOf(orgLocations.getAverageWorkingHours()));
		 }
		
		 orgLocationDTO.setOrgShortCode(orgLocations.getGeneratedUserId());
		 orgLocationDTO.setOrgLocationCode(orgLocations.getLocationCode());
		 orgLocationDTO.setOrgCode(orgLocations.getOrg().getOrgCode());
		 orgLocationDTO.setIsTimesheetAvgHoursRequired(orgLocations.getIsTimesheetAvgHoursRequired());
		 orgLocationDTO.setIsCompanyOnboarded(orgLocations.getIsCompanyOnboarded());
		 if(orgLocations.getBusinessType() != null) {
			 orgLocationDTO.setBusinessTypeId(String.valueOf(orgLocations.getBusinessType().getId()));
			 orgLocationDTO.setBusinessType(orgLocations.getBusinessType().getName());
		 }
		 orgLocationDTO.setOtherBusinessType(orgLocations.getOtherBusinessType());
		 orgLocationDTO.setEmailForPO(orgLocations.getPoEmail());
		 orgLocationDTO.setEmailforRemittance(orgLocations.getRemittanceEmail());
		 orgLocationDTO.setOrgDescription(orgLocations.getOrgDescription());
		 orgLocationDTO.setOrgLocationLoginSecurity(orgLocations.getLoginSecurity());
		 
	     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	     LocalDateTime now = LocalDateTime.now();  
	     orgLocationDTO.setLastUpdatedDate(dtf.format(now));
		
		 if(orgLocations.getUser() != null) {
			 orgLocationDTO.setPrimaryUserId(String.valueOf(orgLocations.getUser().getId()));
		 }
		
		 if(orgLocations.getTimezone() != null) {
			 orgLocationDTO.setTimeZoneId(String.valueOf(orgLocations.getTimezone().getId()));
			 orgLocationDTO.setTimeZone(orgLocations.getTimezone().getTimeZoneId());
		 }
		 
		 if(orgLocations.getOrg().getUser() != null) {
			 orgLocationDTO.setSuperUserId(String.valueOf(orgLocations.getOrg().getUser().getId()));
		 }
		 
	    try {
			  Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if (countries != null) {
				orgLocationDTO.setCurrencyCode(countries.getCurrency());
				orgLocationDTO.setCountryCode(countries.getCode2());
			}
		 } catch (Exception e2) {
			e2.printStackTrace();
		 }
		 
		 return orgLocationDTO;
	}
	
	
	
	public UserRoleSessionProfileDTO getUserRoleCacheDetails(UserRole userRole) {
		
		UserRoleSessionProfileDTO userRoleDTO = new UserRoleSessionProfileDTO();
		try {
			 userRoleDTO.setDocumentType(Constants.COUCH_DOCUMENT_TYPE_USER_LOGIN_ACCOUNT);
			 userRoleDTO.setUserRoleId(String.valueOf(userRole.getId()));
			 userRoleDTO.setUserId(String.valueOf(userRole.getUser().getId()));
			 userRoleDTO.setRoleId(String.valueOf(userRole.getRoles().getId()));
			 userRoleDTO.setRoleName(userRole.getRoles().getRoleName());
			 userRoleDTO.setRoleDisplayName(userRole.getRoles().getDisplayName());
			 userRoleDTO.setEmail(userRole.getUser().getEmail());
			 userRoleDTO.setFirstName(userRole.getUser().getFirstName());
			 userRoleDTO.setLastName(userRole.getUser().getLastName());
			 userRoleDTO.setMiddleName(userRole.getUser().getMiddelName());
			 userRoleDTO.setFacebook(userRole.getUser().getFacebook());
			 userRoleDTO.setTwitter(userRole.getUser().getTwitter());
			 userRoleDTO.setGooglePlus(userRole.getUser().getGooglePlus());
			 userRoleDTO.setLinkedIn(userRole.getUser().getLinkedIn());
			 userRoleDTO.setMobilePhone(userRole.getUser().getMobilePhone());
			 userRoleDTO.setLocation(userRole.getUser().getLocation());
			 userRoleDTO.setUserLoginSecurity(userRole.getUser().getLoginSecurity());
			 userRoleDTO.setNewlyLoginUser(userRole.getNewlyLoginUser());
			 
			  if (userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER") && 
					  StringUtils.isNotBlank(userRole.getUser().getRecruiterRegisterCountry())) {
				  userRoleDTO.setCountry(userRole.getUser().getRecruiterRegisterCountry());
				} else if (userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL") && 
						  StringUtils.isNotBlank(userRole.getUser().getProfessionalRegisterCountry())) {
					userRoleDTO.setCountry(userRole.getUser().getProfessionalRegisterCountry());
			  }else {
				  userRoleDTO.setCountry(userRole.getOrgLocations().getCountry()); 
			  }
				 
			 if(userRole.getReportingManager() != null) {
				 userRoleDTO.setReportingManagerId(String.valueOf(userRole.getReportingManager().getId()));
			 }
			
			 if(userRole.getResourceManager() != null) {
				 userRoleDTO.setResourceManagerId(String.valueOf(userRole.getResourceManager().getId()));
			 }
			
			 userRoleDTO.setOrgLocationId(String.valueOf(userRole.getOrgLocations().getId()));
			 userRoleDTO.setUserState(userRole.getUserState().toString());
			 userRoleDTO.setUserType(userRole.getUserType());
			 userRoleDTO.setDesignation(userRole.getDesignation());
			 userRoleDTO.setIsGlobalUser(userRole.getIsGlobal());
			 userRoleDTO.setOrgLocationCode(userRole.getOrgLocations().getLocationCode());
			 if(userRole.getRoles().getRoleHierarchy() != null) {
				 userRoleDTO.setRoleHierarchyId(String.valueOf(userRole.getRoles().getRoleHierarchy().getId()));
			 }
			 userRoleDTO.setUserCode(userRole.getUser().getUserCode());
			 Candidate candidate = candiateService.getCandidateByConsultantUserId(userRole.getUser().getId(),userRole.getOrgLocations().getId());
			 if(candidate != null) {
				 userRoleDTO.setCandidateId(String.valueOf(candidate.getId()));
				 userRoleDTO.setCandidateUUID(candidate.getCandidateUUID());
				 if(candidate.getMasterCandidateId() != null) {
					 userRoleDTO.setMasterCandidateId(String.valueOf(candidate.getMasterCandidateId().getId()));
				 }
			 }
			
			 StringBuilder userNameSB = new StringBuilder();
			 
			 if(StringUtils.isNotEmpty(userRole.getUser().getFirstName()) && StringUtils.isEmpty(userRole.getUser().getLastName()) && StringUtils.isEmpty(userRole.getUser().getMiddelName())){
				 userNameSB.append(userRole.getUser().getFirstName());
				}else if(StringUtils.isNotEmpty(userRole.getUser().getFirstName()) && StringUtils.isNotEmpty(userRole.getUser().getLastName()) && StringUtils.isEmpty(userRole.getUser().getMiddelName())){
					 userNameSB.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getLastName());
				}else if(StringUtils.isNotEmpty(userRole.getUser().getFirstName()) && StringUtils.isEmpty(userRole.getUser().getLastName()) && StringUtils.isNotEmpty(userRole.getUser().getMiddelName())){
					userNameSB.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getMiddelName());
				}else if(StringUtils.isNotEmpty(userRole.getUser().getFirstName()) && StringUtils.isNotEmpty(userRole.getUser().getLastName()) && StringUtils.isNotEmpty(userRole.getUser().getMiddelName())){
					userNameSB.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getMiddelName()).append(" ").append(userRole.getUser().getLastName());
				}
			 userRoleDTO.setLoginUserFullName(userNameSB.toString());
			 
			 userRoleDTO.setImgUrl(userRole.getUser().getFileUrl());
			 
			 boolean isSuperUser = orgLocationService.isLoginUserIsSuperUser(userRole.getUser().getId(),userRole.getOrgLocations().getId());
				if(isSuperUser == true) {
					userRoleDTO.setIsSuperUser(Constants.YES);
				}else {
					userRoleDTO.setIsSuperUser(Constants.NO);
				}
				
				boolean isSuperAdmin = orgService.isLoginUserIsSuperAdmin(userRole.getUser().getId(),userRole.getOrgLocations().getOrg().getId());
				if(isSuperAdmin == true){
					userRoleDTO.setIsSuperAdmin(Constants.YES);
				}else{
					userRoleDTO.setIsSuperAdmin(Constants.NO);
				}
			 
			 if(userRole.getShift() != null) {
				 userRoleDTO.setShiftId(String.valueOf(userRole.getShift().getId()));
			 }
			 
			 if(userRole.getGradeId() != null) {
				 userRoleDTO.setGradeId(String.valueOf(userRole.getGradeId().getId()));
			 }
			 
			 boolean isPayRollApprover = configurePayrollApproverService.isLoginUserIsPayRollApprover(userRole.getUser().getId(),userRole.getOrgLocations().getId());
			 if (isPayRollApprover == true) {
				 userRoleDTO.setIsPayRollApprover(Constants.YES);
			 }else {
				 userRoleDTO.setIsPayRollApprover(Constants.NO);
			 }
			
			 userRoleDTO.setUuid(userRole.getUuid());
			
		    List<SourceleadActions> actionsList = null;
	        List<String> tabNamesList = null;
	        List<String> actionRolesList = new ArrayList<String>();
	        List<Long> actionIdsList = new ArrayList<Long>();
			 
	        actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(null, userRole.getRoles().getId(), userRole.getOrgLocations().getId());
			    
		   	 if(actionsList != null && !actionsList.isEmpty()) {
		   		  for(SourceleadActions actions : actionsList){
		       	    	actionIdsList.add(actions.getId());
		       	    	actionRolesList.add(actions.getActionRole());
		       	    }
		   		  
		   		 tabNamesList = tabsService.getTabsNamesListByActionsIds(actionIdsList);
		   		 tabNamesList.add("Dashboard");
		    	    
		    	     userRoleDTO.setMenuList(tabNamesList);
		    	     userRoleDTO.setRolesList(actionRolesList);
		   	 	}
		   	 
		     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		     LocalDateTime now = LocalDateTime.now();  
		     userRoleDTO.setLastUpdatedDate(dtf.format(now));
	}catch (Exception e) {
			e.printStackTrace();
	}
		
		return userRoleDTO;
	    
	}
	
	
	public void saveLoginUserSessionStoreToCouchbase(String generatedCustomTocken,UserRole userRole,String actionUUID,Long actionId,HttpServletRequest request) {
		LoginUserSessionStoreDTO sessionDTO = new LoginUserSessionStoreDTO();
		
		sessionDTO.setDocumentType(Constants.COUCH_DOCUMENT_TYPE_USER_LOGIN_SESSION);
		sessionDTO.setUserRoleUUID(userRole.getUuid());
		sessionDTO.setSessionId(generatedCustomTocken);
		sessionDTO.setOrgLocationCode(userRole.getOrgLocations().getLocationCode());
		sessionDTO.setOrgLocationName(userRole.getOrgLocations().getOrgLocationName());
		sessionDTO.setOrgImgUrl(userRole.getOrgLocations().getOrg().getFileUrl());
		sessionDTO.setLoginUserName(userRole.getUser().getUserName());
		sessionDTO.setCountry(userRole.getOrgLocations().getCountry());
		sessionDTO.setState(userRole.getOrgLocations().getState());
		sessionDTO.setCity(userRole.getOrgLocations().getCity());
		sessionDTO.setActionUUID(actionUUID);
		sessionDTO.setActionId(String.valueOf(actionId));
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now();  
	    sessionDTO.setLastUpdatedDate(dtf.format(now));
	    StringBuilder accountsList = new StringBuilder();
	    String userAccounts = null;
	    try {
	    	List<Long> userIdsList = new ArrayList<>();
	    	userIdsList.add(userRole.getUser().getId());
	    	List<Long> linkedUserIdsList = userService.getUserIdsListByLinkedUserId(userRole.getUser().getId());
	    	if(linkedUserIdsList != null && !linkedUserIdsList.isEmpty()) {
	    		userIdsList.addAll(linkedUserIdsList);
	    	}
			Set<Long> loginUserIdsSet = new LinkedHashSet<>();
			loginUserIdsSet.addAll(userIdsList);
			userIdsList.addAll(loginUserIdsSet);
			
			List<UserRole> userRolesList = userRoleService.getUserAccountsListByUserId(userIdsList);
			
			if(userRolesList != null && userRolesList.size() > 1) {
				for(UserRole ur : userRolesList) {
					accountsList.append(ur.getUser().getUserName()+"^"+ur.getUuid()).append(",").toString();
				}
				 userAccounts = accountsList.deleteCharAt(accountsList.length()-1).toString(); 
				 sessionDTO.setLoginUserAccounts(userAccounts);
			}else {
				sessionDTO.setLoginUserAccounts(userAccounts);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	    ObjectMapper sessionObj = new ObjectMapper();
        try {
			String sessionJsonStr = sessionObj.writeValueAsString(sessionDTO);
			JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
			StringBuilder userSessionKey = new StringBuilder();
			userSessionKey.append(generatedCustomTocken).append("_SESSION");
			sourceLeadSessionManager.setUserLoginSessionJsonString(userSessionKey.toString(), "userLoginSessionStore", jsonObject);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void saveLoginUserSessionStoreToCouchbaseForMobiles(String generatedCustomTocken,UserDetailsDTO userDetailsDTO,HttpServletRequest request) {
	    ObjectMapper sessionObj = new ObjectMapper();
        try {
			String sessionJsonStr = sessionObj.writeValueAsString(userDetailsDTO);
			JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
		    sourceLeadSessionManager.setCouchbaseCustomTokenJsonString(generatedCustomTocken, "customTokenStore", jsonObject);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	
	public UserDetailsDTO getUserDetails(String token) {

		JSONObject userDetailsJson = new JSONObject();
		UserDetailsDTO userDetailsDTO = null;
		Object sv = null;
		sv = couchbaseClient.getUserDetails(token);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					userDetailsJson = (JSONObject) parser.parse(jsonValue.toString());
				}
				 Gson gson=new Gson();
			     userDetailsDTO=gson.fromJson(userDetailsJson.toString(),UserDetailsDTO.class);
				return userDetailsDTO;

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return userDetailsDTO;
	}
	
}
