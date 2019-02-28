package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ProfileDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailUnsubscribe;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserMailUnsubscribeService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class UserProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserMailUnsubscribeService userMailUnsubscribeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;
	

    @RequestMapping(value = "/userProfile")
 	 public ModelAndView userProfile(HttpServletRequest request) throws Exception {
 			ModelAndView model = new ModelAndView();
 			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
 			
 			UserMailUnsubscribe userMailUnsubscribe = userMailUnsubscribeService.getUserMailUnsubscriptionByUserId(user.getId());
 			if(userMailUnsubscribe != null && StringUtils.isNotEmpty(userMailUnsubscribe.getTabIds())) {
 				model.addObject("unsubscriptionTabIds", userMailUnsubscribe.getTabIds());
 			}else {
 				model.addObject("unsubscriptionTabIds", "");
 			}
 			
 			 List<UserDTO> userDTOList  = userService.getLinkedUserListByUserId(user.getId()); 
 			 model.addObject("userDTOList", userDTOList);
 			model.addObject("userDTOListSize", userDTOList.size());
 			
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			model.setViewName("userProfile");
 			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
 	 }

    @RequestMapping(value = {"/getLoginUserProfile","rest/getLoginUserProfile"}, method = RequestMethod.GET)
    public @ResponseBody ProfileDTO getLoginUserProfile(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    	UserRole userRole = userRoleService.getUserByUserId(user.getId());
    	ProfileDTO profileDTO = new ProfileDTO();
    	if(user != null) {
    		profileDTO.setFirstName(user.getFirstName());
    		if(StringUtils.isNotBlank(user.getLastName())) {
    			profileDTO.setLastName(user.getLastName());
    		}
    		profileDTO.setEmail(user.getEmail());
    		if(StringUtils.isNotBlank(user.getMobilePhone())) {
    			profileDTO.setMobileNumber(user.getMobilePhone());
    		}
    		if(StringUtils.isNotBlank(user.getLocation())) {
    			profileDTO.setLocation(user.getLocation());
    		}
    		if(StringUtils.isNotBlank(userRole.getDesignation())) {
    			profileDTO.setDesignation(userRole.getDesignation());
    		}
    		
    		if(StringUtils.isNotBlank(user.getLinkedIn())) {
    			profileDTO.setLinkedinURL(user.getLinkedIn());
    		}
    		if(StringUtils.isNotBlank(user.getFacebook())) {
    			profileDTO.setFacebookURL(user.getFacebook());
    		}
    		if(StringUtils.isNotBlank(user.getTwitter())) {
    			profileDTO.setTwitterURL(user.getTwitter());
    		}
    		if(StringUtils.isNotBlank(user.getGooglePlus())) {
    			profileDTO.setGooglePlusURL(user.getGooglePlus());
    		}
    		if(StringUtils.isNotBlank(user.getFileUrl())) {
    			profileDTO.setImageURL(user.getFileUrl());
    		}
    	}
    	return profileDTO;
    }
    
    @RequestMapping(value = {"/downloadLoginUserProfile","rest/downloadLoginUserProfile"}, method = RequestMethod.GET)
    public @ResponseBody ProfileDTO downloadLoginUserProfile(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    	UserRole userRole = userRoleService.getUserByUserId(user.getId());
    	UsersUsageReportDTO usersUsageReportDTO=new UsersUsageReportDTO();
    	usersUsageReportDTO.setId(user.getId());
    	ProfileDTO profileDTO = new ProfileDTO();
    	List<String> ipaddressList=userLoginLogoutSessionService.getUsersByUserId(usersUsageReportDTO);
    	if(user != null) {
    		if(ipaddressList !=null){
    			profileDTO.setIpaddresses(ipaddressList);	
    		}
    		profileDTO.setFirstName(user.getFirstName());
    		if(StringUtils.isNotBlank(user.getLastName())) {
    			profileDTO.setLastName(user.getLastName());
    		}
    		profileDTO.setEmail(user.getEmail());
    		if(StringUtils.isNotBlank(user.getMobilePhone())) {
    			profileDTO.setMobileNumber(user.getMobilePhone());
    		}
    		if(StringUtils.isNotBlank(user.getLocation())) {
    			profileDTO.setLocation(user.getLocation());
    		}
    		if(StringUtils.isNotBlank(userRole.getDesignation())) {
    			profileDTO.setDesignation(userRole.getDesignation());
    		}
    		
    		if(StringUtils.isNotBlank(user.getLinkedIn())) {
    			profileDTO.setLinkedinURL(user.getLinkedIn());
    		}
    		if(StringUtils.isNotBlank(user.getFacebook())) {
    			profileDTO.setFacebookURL(user.getFacebook());
    		}
    		if(StringUtils.isNotBlank(user.getTwitter())) {
    			profileDTO.setTwitterURL(user.getTwitter());
    		}
    		if(StringUtils.isNotBlank(user.getGooglePlus())) {
    			profileDTO.setGooglePlusURL(user.getGooglePlus());
    		}
    		if(StringUtils.isNotBlank(user.getFileUrl())) {
    			profileDTO.setImageURL(user.getFileUrl());
    		}
    	}
    	return profileDTO;
    }
}
