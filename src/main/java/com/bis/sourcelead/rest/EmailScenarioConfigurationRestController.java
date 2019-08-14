package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmailScenarioConfigurationDao;
import com.bis.sourcelead.dto.EmailConfigurationDTO;
import com.bis.sourcelead.dto.EmailConfigurationFromMailDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PasswordSecurity;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class EmailScenarioConfigurationRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailScenarioConfigurationRestController.class);
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	EmailScenarioConfigurationService emailScenarioconService;
	
	@Autowired
	TabsService tabsService;
	
	@Autowired
	private EmailScenarioConfigurationDao emailScenarioConfigurationDao;
	
	@Autowired
	CommonUtil commonUtil;
	
		 
	 @RequestMapping(value = "/saveEmailNotification" , method = RequestMethod.POST)
		public @ResponseBody String saveEmailNotification(EmailConfigurationDTO emailConfigurationDTO,HttpServletRequest request)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String returnValue="";
		 EmailScenarioConfiguration emailConfig=null;
		 if(emailConfigurationDTO.getEmailId() != null){
			 emailConfig= emailScenarioconService.getEmailConfigOrgLocId( orgLocation.getId());
		 }
		 if(emailConfig != null){
			 if(emailConfigurationDTO.getEmailId() != null){
				 emailConfig.setEmailId(emailConfigurationDTO.getEmailId()); 
			 }
			 if(emailConfigurationDTO.getEmailPassword() != null){
				 emailConfig.setEmailPassword(emailConfigurationDTO.getEmailPassword()); 
			 }
			 if(emailConfigurationDTO.getServerName() != null){
				 emailConfig.setServerName(emailConfigurationDTO.getServerName()); 
			 }
			 if(emailConfigurationDTO.getPortNumber() != null){
				 emailConfig.setPortNumber( Long.parseLong(emailConfigurationDTO.getPortNumber())); 
			 }
			 if(emailConfigurationDTO.getEncryptType() != null){
				 emailConfig.setEncryptionType(emailConfigurationDTO.getEncryptType()); 
			 }
			 emailConfig.setUpdatedBy(user.getFirstName());
			 emailConfig.setUpdatedDate(new Date());
		 }else{
			 emailConfig=new EmailScenarioConfiguration();
			 if(emailConfigurationDTO.getEmailId() != null){
				 emailConfig.setEmailId(emailConfigurationDTO.getEmailId()); 
			 }
			 if(emailConfigurationDTO.getEmailPassword() != null){
				 emailConfig.setEmailPassword(emailConfigurationDTO.getEmailPassword()); 
			 }
			 if(emailConfigurationDTO.getServerName() != null){
				 emailConfig.setServerName(emailConfigurationDTO.getServerName()); 
			 }
			 if(emailConfigurationDTO.getPortNumber() != null){
				 emailConfig.setPortNumber( Long.parseLong(emailConfigurationDTO.getPortNumber())); 
			 }
			 if(emailConfigurationDTO.getEncryptType() != null){
				 emailConfig.setEncryptionType(emailConfigurationDTO.getEncryptType()); 
			 }
			 emailConfig.setOrgLocationId(orgLocation);
			 emailConfig.setEmail_UUID(SourceLeadRandomCodeHelper.generateRandomOrgCode());
			 emailConfig.setCreatedBy(user.getFirstName());
			 emailConfig.setCreatedDate(new Date());
		 }
		 
		
		 EmailScenarioConfiguration emailconfigur=emailScenarioconService.save(emailConfig);
		 if(emailconfigur != null){
			 String fromMail=emailScenarioconService.verifyConfgFromMail(orgLocation.getId(),emailconfigur);
			 if(Constants.EMAIL_SUCCESS.equalsIgnoreCase(fromMail)){
				 emailconfigur.setVerifiedStatus(Constants.YES);
				 emailconfigur.setIsActive(Constants.YES);
				 emailScenarioConfigurationDao.save(emailconfigur);
				 returnValue= "success";
			 }else{
				 emailconfigur.setVerifiedStatus(Constants.NO);
				 emailconfigur.setIsActive(Constants.NO);
				 emailScenarioConfigurationDao.save(emailconfigur);
				 returnValue= "failure";
			 }
		 }
		 return returnValue;
		 
			
		}
	 @RequestMapping(value = "/saveEmailParser" , method = RequestMethod.POST)
		public @ResponseBody String saveEmailParser(EmailConfigurationDTO emailConfigurationDTO,HttpServletRequest request)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String returnValue="";
		 EmailScenarioConfiguration emailConfig = null;
				 if(StringUtils.isNotBlank(emailConfigurationDTO.getEmailUUID())){
					 emailConfig = emailScenarioconService.getParserEmailsDetailsByUUID(emailConfigurationDTO.getEmailUUID());// loads existed record to upate.
				 }else {
					 emailConfig = new EmailScenarioConfiguration();
				 }
				
			 if(emailConfigurationDTO.getEmailId() != null){
				 emailConfig.setEmailId(emailConfigurationDTO.getEmailId()); 
			 }
			 if(emailConfigurationDTO.getEmailPassword() != null){
				 emailConfig.setEmailPassword(emailConfigurationDTO.getEmailPassword()); 
			 }
			 if(emailConfigurationDTO.getServerName() != null){
				 emailConfig.setServerName(emailConfigurationDTO.getServerName()); 
			 }
			 if(emailConfigurationDTO.getPortNumber() != null){
				 emailConfig.setPortNumber( Long.parseLong(emailConfigurationDTO.getPortNumber())); 
			 }
			 if(emailConfigurationDTO.getEncryptType() != null){
				 emailConfig.setEncryptionType(emailConfigurationDTO.getEncryptType()); 
			 }
			 emailConfig.setOrgLocationId(orgLocation);
			 emailConfig.setEmail_UUID(SourceLeadRandomCodeHelper.generateRandomOrgCode());
			 emailConfig.setCreatedBy(user.getFirstName());
			 emailConfig.setCreatedDate(new Date());
			 emailConfig.setUpdatedDate(new Date());
		
		
		 if(emailConfig != null){
			 String fromMail=emailScenarioconService.verifyConfgParserMail(orgLocation.getId(),emailConfig);
			 if(Constants.EMAIL_SUCCESS.equalsIgnoreCase(fromMail)){
				 emailConfig.setVerifiedStatus(Constants.YES);
				 emailConfig.setIsActive(Constants.YES);
				 emailConfig.setEmailPassword(new PasswordSecurity().encrypt(emailConfig.getEmailPassword()));
				 emailConfig.setUsageType(emailConfigurationDTO.getUsageType());
				 emailScenarioconService.save(emailConfig); // Saving record when successfully validated.
				 returnValue= "success";
			 }else{
				 returnValue= "failure";
			 }
		 }
		 return returnValue;
		 
			
		}
	
	 	@RequestMapping(value = "/editEmailParsing", method = RequestMethod.POST)
		public @ResponseBody EmailScenarioConfiguration editEmailParsingDetails(HttpServletRequest request, 
				                                        @RequestParam(value = "emailUUID", required = true) String emailUUID) 
		{
			EmailScenarioConfiguration emailConfig = null;
			try {
				emailConfig =  emailScenarioconService.getParserEmailsDetailsByUUID(emailUUID);
			} catch (Exception e) {
				logger.error("Exception caught in while loading email parsing details--->" + e.getMessage());
				e.printStackTrace();
			}
			return emailConfig;
		}
	 @RequestMapping(value = "/saveConfigurationFromMail" , method = RequestMethod.POST)
		public @ResponseBody String saveConfigurationFromMail(EmailConfigurationFromMailDTO emailConfigurationFromMailDTO,HttpServletRequest request)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 EmailConfigurationFromMail emailConfigFromMail=null;
		 if(emailConfigurationFromMailDTO.getId()!=null && StringUtils.isNotBlank(emailConfigurationFromMailDTO.getId())){
			   emailConfigFromMail=emailScenarioconService.getFromMailById(Long.parseLong(emailConfigurationFromMailDTO.getId()));
			   if(emailConfigurationFromMailDTO.getEmail() != null){
					 emailConfigFromMail.setEmail(emailConfigurationFromMailDTO.getEmail());
				 }
				 if(emailConfigurationFromMailDTO.getEmailModules() != null){
					 emailConfigFromMail.setEmailModules(emailConfigurationFromMailDTO.getEmailModules());
				 }
				 emailConfigFromMail.setUpdatedBy(user.getFirstName());
				 emailConfigFromMail.setUpdatedDate(new Date());
		 }else{
			 emailConfigFromMail=new EmailConfigurationFromMail();
			 if(emailConfigurationFromMailDTO.getEmail() != null){
				 emailConfigFromMail.setEmail(emailConfigurationFromMailDTO.getEmail());
			 }
			 if(emailConfigurationFromMailDTO.getEmailModules() != null){
				 emailConfigFromMail.setEmailModules(emailConfigurationFromMailDTO.getEmailModules());
			 }
			 emailConfigFromMail.setStatus(0);
			 emailConfigFromMail.setOrgLocationId(orgLocation);
			 emailConfigFromMail.setCreatedBy(user.getFirstName());
			 emailConfigFromMail.setCreatedDate(new Date());
			 emailConfigFromMail.setEmail_UUID(SourceLeadRandomCodeHelper.generateRandomOrgCode());
		 }
		 
		 
		 emailScenarioconService.saveFromMail(emailConfigFromMail);
		  return "success";
			
		}
	 @SuppressWarnings("unused")
	@RequestMapping(value = "/getResumeParserEmailCountForOrg" , method = RequestMethod.POST)
		public @ResponseBody int getResumeParserEmailCountForOrg(HttpServletRequest request,@RequestParam(value = "emailId", required = false) String emailId)
				throws Exception {
		 int count = 0;
		 try{
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    		if (user != null) {
	    			 count =emailScenarioconService.getResumeParserEmailCountForOrg(orgLocation.getId());
	       }
			 
		 }catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		 
		
		  return count;
			
		}
	 @SuppressWarnings("unused")
	@RequestMapping(value = "/getConfigFromMailList" , method = RequestMethod.POST)
		public @ResponseBody ResponseList<EmailConfigurationFromMail> getConfigFromMailList(HttpServletRequest request,@RequestParam(value = "emailId", required = false) String emailId)
				throws Exception {
		 ResponseList<EmailConfigurationFromMail> response = new ResponseList<>();
		 try{
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    		if (user != null) {
	    			List<EmailConfigurationFromMail> fromMail=emailScenarioconService.getConfFromMailList(orgLocation.getId());
	    			for(EmailConfigurationFromMail emailconf:fromMail){
	    				String moduleNames="";
	    				if( emailconf.getEmailModules() != null){
	    					String[] jobIds = emailconf.getEmailModules().split(",");
							for(int i=0; i<jobIds.length; i++) {
							Tabs tab=tabsService.getTabListByTabId(Long.valueOf(jobIds[i]));
							moduleNames=moduleNames+tab.getTabName()+",";
							}
		    				String modules=emailconf.getEmailModules();
		    				emailconf.setEmailModules(moduleNames.substring(0, moduleNames.length()-1));
	    				}
	    				
	    			}
	    			if(fromMail != null){
	    				response.setData(fromMail);
	    				response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
	    		}else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			 
		 }catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		 
		
		  return response;
			
		}
	 @SuppressWarnings("unused")
		@RequestMapping(value = "/getConfigParserMailList" , method = RequestMethod.POST)
			public @ResponseBody ResponseList<EmailScenarioConfiguration> getConfigParserMailList(HttpServletRequest request,@RequestParam(value = "emailId", required = false) String emailId)
					throws Exception {
			 ResponseList<EmailScenarioConfiguration> response = new ResponseList<>();
			 logger.debug("***********************inside getConfigParserMailList****************");
			 try{
				 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    		if (user != null) {
		    			List<EmailScenarioConfiguration> parserMailList=emailScenarioconService.getConfParserMailList(orgLocation.getId(),Constants.RESUME_PARSING , false);
		    			if(parserMailList != null){
		    				response.setData(parserMailList);
		    				response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						} else {
							response.setResponse(Result.FAILED.name());
							response.setResponseMessage(Result.FAILED.getValue());
						}
		    		}else if (user == null) {
						response.setResponse(Result.SESSION_TIMEDOUT.name());
						response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
					} else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				 
			 }catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
			 
			
			  return response;
				
			}
	 
	 @SuppressWarnings("unused")
	@RequestMapping(value = "/deleteEmailFromMail" , method = RequestMethod.POST)
		public @ResponseBody Response<String> deleteEmailFromMail(@RequestParam(value = "emailId", required = false) Long emailId,HttpServletRequest request)
				throws Exception {
		 Response<String> response=new Response<String>();
		 
		 try{
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 if(user!= null){
				 EmailConfigurationFromMail fromMail=emailScenarioconService.deleteFromMail(emailId, orgLocation.getId());
				 if(fromMail!= null){
	    				response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
		 }catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		  return response;
			
		}
	 
	 @SuppressWarnings("unused")
		@RequestMapping(value = "/deleteParserEmail" , method = RequestMethod.POST)
			public @ResponseBody Response<String> deleteParserEmail(@RequestParam(value = "emailId", required = false) Long emailId,HttpServletRequest request)
					throws Exception {
			 Response<String> response=new Response<String>();
			 logger.debug("***********************inside deleteParserEmail****************");
			 try{
				 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 if(user!= null){
					 String status=emailScenarioconService.deleteParserEMail(emailId);
					 if(status!= null){
		    				response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						} else {
							response.setResponse(Result.FAILED.name());
							response.setResponseMessage(Result.FAILED.getValue());
						}
				 }else if (user == null) {
						response.setResponse(Result.SESSION_TIMEDOUT.name());
						response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
					} else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
			 }catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
			  return response;
				
			}
	 @SuppressWarnings("unused")
		@RequestMapping(value = "/getEmailConfigById" , method = RequestMethod.POST)
			public @ResponseBody Response<EmailConfigurationFromMailDTO> getEmailConfigById(@RequestParam(value = "emailUUID", required = false) String emailUUID,HttpServletRequest request)
					throws Exception {
			 Response<EmailConfigurationFromMailDTO> response=new Response<EmailConfigurationFromMailDTO>();
			 
			 try{
				 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 if(user!= null){
					 EmailConfigurationFromMail fromMail=emailScenarioconService.getFromMailByUUID(emailUUID);
					 EmailConfigurationFromMailDTO fromMailDTO=new EmailConfigurationFromMailDTO();
					 if(fromMail != null){
						 fromMailDTO.setEmail(fromMail.getEmail());
						 fromMailDTO.setEmail_UUID(fromMail.getEmail_UUID());
						String moduleNames="";
		    				String[] jobIds = fromMail.getEmailModules().split(",");
							for(int i=0; i<jobIds.length; i++) {
							Tabs tab=tabsService.getTabListByTabId(Long.valueOf(jobIds[i]));
							moduleNames=tab.getTabName()+",";
							}
						 fromMailDTO.setEmailModules(moduleNames);
					 }
					 if(fromMailDTO!= null){
						 response.setObject(fromMailDTO);
		    				response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						} else {
							response.setResponse(Result.FAILED.name());
							response.setResponseMessage(Result.FAILED.getValue());
						}
				 }else if (user == null) {
						response.setResponse(Result.SESSION_TIMEDOUT.name());
						response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
					} else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
			 }catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
			  return response;
				
			}
	 
	 /*@SuppressWarnings("unused")
	@RequestMapping(value = "/verifyConfigurationFromMail" , method = RequestMethod.POST)
		public @ResponseBody Response<String> verifyConfigurationFromMail(HttpServletRequest request)
				throws Exception {
		 Response<String> response=new Response<String>();
		 
		 try{
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 if(user!= null){
				 String fromMail=emailScenarioconService.verifyConfgFromMail(orgLocation.getId());
				 if(Constants.EMAIL_SUCCESS.equalsIgnoreCase(fromMail)){
					 EmailScenarioConfiguration confMail=emailScenarioConfigurationDao.getEmailConfigOrgLocId(orgLocation.getId());
					 confMail.setStatus("Y");
					 emailScenarioConfigurationDao.save(confMail);
	    				response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
		 }catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		  return response;
			
		}*/
	 
		@RequestMapping(value = "/getConfiguredMail" , method = RequestMethod.POST)
		public @ResponseBody EmailScenarioConfiguration getConfiguredMail(HttpServletRequest request)
				throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			EmailScenarioConfiguration configuredMail=emailScenarioconService.getEmailConfigOrgLocId(orgLocation.getId());
			return configuredMail;
		}
				
		@RequestMapping(value = "/changeDefaultEmailConfig" , method = RequestMethod.POST)
		public @ResponseBody int changeDefaultEmailConfig(long id, boolean defaultAccount,HttpServletRequest request)
				throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 int flag=emailScenarioconService.changeDefaultEmailFromMail(id, orgLocation.getId(), defaultAccount);
			return flag;
		}
		@RequestMapping(value = "/changeDefaultParserEmailConfig" , method = RequestMethod.POST)
		public @ResponseBody int changeDefaultParserEmailConfig(String uuid, boolean activeFlag, HttpServletRequest request)
				throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 Date updatedDate = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(),new Date()); //parsing to org specific date format
			 int flag=emailScenarioconService.changeDefaultParserEmailConfig(uuid, orgLocation.getId(), activeFlag, updatedDate);
			return flag;
		}
						
				
		@RequestMapping(value = "/validateParserEmail" , method = RequestMethod.POST)		
		public Boolean validateParserEmail (@RequestParam(value = "parserEmailId", required = true) String parserEmailId,
											@RequestParam(value = "emailUUID", required = true) String emailUUID,HttpServletRequest request)throws Exception {
			
			EmailScenarioConfiguration emailconf = null;
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(emailUUID != null && parserEmailId !=null){	
				emailconf = emailScenarioconService.validateParserByEmail(parserEmailId,emailUUID,orgLocation.getId());
			}
			Boolean flag = false;
			if(emailconf == null){
			flag = emailScenarioconService.validateParser(parserEmailId,orgLocation.getId());
			}
			return flag;
		}

			
}