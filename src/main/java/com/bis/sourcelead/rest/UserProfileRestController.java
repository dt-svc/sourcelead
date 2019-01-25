package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AddUserProfileDTO;
import com.bis.sourcelead.dto.PasswordSettingsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.UserMailUnsubscribeDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailUnsubscribe;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserMailUnsubscribeService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class UserProfileRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;

	@Autowired
	UserService userService;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;

	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	UserMailUnsubscribeService userMailUnsubscribeService;
	
	@RequestMapping(value = "/saveUserProfileImage", method = RequestMethod.POST)
	public String saveProfileImage(AddUserProfileDTO addUserProfileDTO,	
			@RequestParam(defaultValue = "0") long userId,
			HttpServletRequest request) throws Exception{
		addUserProfileDTO.setStaticPath(staticPath);
		User user = null;
		if(userId > 0){
			user = userService.getUserById(userId);		
		} else {
			user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		}
		
		if(user == null){
			user = userService.getUserById(addUserProfileDTO.getUserId());	
		}
		
		if(user != null){
			user = userService.saveUserProfileImage(addUserProfileDTO, user);
			return "success";
		}
		else
			return "failure";
		
	}
	
	
	@RequestMapping(value = "/rest/saveUserProfileImage", method = RequestMethod.POST)
	public String saveProfileImageForMobile(@RequestBody AddUserProfileDTO addUserProfileDTO,HttpServletRequest request)throws Exception{
		return saveProfileImage(addUserProfileDTO,0, request);
	}

	/**
	 * User profile updation 
	 * @param addUserProfileDTO
	 * @param request
	 * @return AddUserProfileDTO
	 * @throws Exception
	 */
	@RequestMapping(value =  {"/saveUserProfile","/rest/saveUserProfile"}, method = RequestMethod.POST)
	public Response<AddUserProfileDTO> saveUserProfile(AddUserProfileDTO addUserProfileDTO, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Response<AddUserProfileDTO> response=new Response<>();
		addUserProfileDTO.setStaticPath(staticPath);
		if(user == null){
			user = userService.getUserById(addUserProfileDTO.getUserId());
		}
		if(orgLocations == null){
			 orgLocations = orgLocationService.getOrgLocations(Long.parseLong(addUserProfileDTO.getOrgLocationId()));
		}
		UserRole userRoleElk = null;
		AddUserProfileDTO profileDTO = null;
		
		try{
			if(user !=null){
				if(Validator.validate(addUserProfileDTO)){
					user = userService.saveUserProfile(addUserProfileDTO, user);
					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), orgLocations.getId());
					userRole.setDesignation(addUserProfileDTO.getDesignation());
					userRoleElk = userRoleService.addUserRole(userRole);
					/*
					 * update common user (single user different orgs) details in ElasticSearch
					 * Documents (User)
					 */
					commonUtil.commonUserUpdate(user);
					/*
					 * Save or update the user in Elk.
					 */
					userService.saveOrUpdateUserElkDocument(userRoleElk, null, user.getUserCode());

					/*
					 * updating user in the Spock
					 * 
					 */

					commonUtil.updateSpockDetails(user, orgLocations);

					SourceLeadSessionManager.setSessionAttribute(request, "user", user);
					StringBuilder userNameSB = new StringBuilder();
					SourceLeadSessionManager.setSessionAttribute(request, "userName",
							userNameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					SourceLeadSessionManager.setSessionAttribute(request, "designation", userRole.getDesignation());	
					
					profileDTO = new AddUserProfileDTO();
					profileDTO.setId(String.valueOf(user.getId()));
					profileDTO.setFirstName(user.getFirstName());
					profileDTO.setLastName(user.getLastName());
					profileDTO.setStaticPath(staticPath);
					profileDTO.setMobilePhone(user.getMobilePhone());
					profileDTO.setJobTitle(user.getJobTitle());
					profileDTO.setLocation(user.getLocation());
					profileDTO.setLinkedIn(user.getLinkedIn());
					profileDTO.setFacebook(user.getFacebook());
					profileDTO.setTwitter(user.getTwitter());
					profileDTO.setGooglePlus(user.getGooglePlus());
					profileDTO.setDesignation(addUserProfileDTO.getDesignation());
					profileDTO.setUserId(addUserProfileDTO.getUserId());
					profileDTO.setOrgLocationId(addUserProfileDTO.getOrgLocationId());
					response.setObject(profileDTO);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else{
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}else{
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		
		return response;

	}
	

	@RequestMapping(value = { "/passwordSettings", "/rest/passwordSettings" }, method = RequestMethod.POST)
	public Response<String> passwordSettings(PasswordSettingsDTO passwordSettingsDTO,
			HttpServletRequest request) throws Exception {
		
		 User user = null;
		 OrgLocations orgLocations = null;
		 Response<String> response=new Response<>();
		 user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		if(user == null) {
			user = userService.getUserById(passwordSettingsDTO.getUserId());
		}
		
		if(orgLocations == null) {
			orgLocations = orgLocationService.getOrgLocations(passwordSettingsDTO.getOrgLocationId());
		}
		
		try{
		if(user !=null){
			if(Validator.validate(passwordSettingsDTO)){

				if (sourceleadPasswordEncoder.matches(passwordSettingsDTO.getCurrentPassword(), user.getPassword())) {
					user.setPassword(sourceleadPasswordEncoder.encode(passwordSettingsDTO.getPassword()));
					userService.addUser(user);
					SourceLeadSessionManager.setSessionAttribute(request, "resetPwdMsg",
							"Your password has reset successfully");
					userService.passwordResetMailToLoginUser(user,orgLocations);
					
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}else{
				response.setObject("DataMissing");
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		}else{
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}	
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
			return response;
		

	}

	@RequestMapping(value = "/validateCurrentPassword", method = RequestMethod.POST)
	public String validateCurrentPassword(
			@RequestParam(value = "currentPassword", required = false) String currentPassword,
			HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (sourceleadPasswordEncoder.matches(currentPassword, user.getPassword())) {
			return "valid";
		} else {
			return "invalid";
		}
	}

	@RequestMapping(value = "/deleteProfileImage", method = RequestMethod.GET)
	public String deleteProfileImage(@RequestParam(defaultValue = "0") long userId,HttpServletRequest request) throws Exception {
		User user = null;
		if(userId>0){
			user = userService.getUserById(userId);
		}else{
	    user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		}
		user.setFileUrl(null);
		user.setFileContentType(null);
		userService.addUser(user);
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);

		return "success";

	}

	/*@RequestMapping(value = "/displayUserImage/{userId}", method = RequestMethod.GET)
	public void displayUserImage(@PathVariable Long userId, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		User user = null;
		try {
			user = userService.getUserById(userId);
			user.setFileContentType("image/jpeg");
			if (user != null && user.getDocFile() != null) {
				InputStream in = new ByteArrayInputStream(user.getDocFile());
				BufferedImage bi = ImageIO.read(in);
				OutputStream out = response.getOutputStream();
				if (null != bi) {
					ImageIO.write(bi, "jpg", out);
				}
				out.close();

			}  else {
				InputStream in = new FileInputStream(new File(getClass().getResource("/images/userprofile.jpg").toURI()));
				BufferedImage bi = ImageIO.read(in);
				OutputStream out = response.getOutputStream();
				ImageIO.write(bi, "jpg", out);
				out.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		catch (Exception e1) {
			e1.printStackTrace();
		}

	}*/
	
	
	   /*@RequestMapping(value = "/displayOnboardingUserImage/{userId}" , method = RequestMethod.GET)
	     public void displayOnboardingUserImage(@PathVariable Long userId,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
	    	 
		   User user = null;
				try {
					 user = userService.getUserById(userId);
					
					if(user != null && user.getDocFile() != null)
					{
						user.setFileContentType("image/jpeg");
						InputStream in = new ByteArrayInputStream(user.getDocFile());
						BufferedImage bi = ImageIO.read(in);
						OutputStream out = response.getOutputStream();
						ImageIO.write(bi, "jpg", out);
						out.close();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
				
				catch (Exception e1) {
					e1.printStackTrace();
				}
				
	  }*/
	   
	   
	@RequestMapping(value = { "/updateUserLoginSecurity","/rest/updateUserLoginSecurity" }, method = RequestMethod.GET)
	public String passwordSettings(AddUserProfileDTO addUserProfileDTO, HttpServletRequest request) throws Exception
	{
		User user = null;
		user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (Constants.YES.equalsIgnoreCase(addUserProfileDTO.getLoginSecurity())) {
			user.setLoginSecurity(Constants.YES);
			userService.addUser(user);
		}else {
			user.setLoginSecurity(Constants.NO);
			userService.addUser(user);
		}
		return "Success";
	}
	
	@RequestMapping(value = { "/updateUserEmailUnsubscription" }, method = RequestMethod.POST)
	public String updateUserEmailUnsubscription(UserMailUnsubscribeDTO userMailUnsubscribeDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		//UserMailUnsubscribe userMailUnsubscribe = userMailUnsubscribeService.getUserMailUnsubscription(userMailUnsubscribeDTO);
		
		UserMailUnsubscribe userMailUnsubscribe = userMailUnsubscribeService.getUserMailUnsubscriptionByUserId(user.getId());
		if(userMailUnsubscribe != null) {
			userMailUnsubscribe.setTabIds(userMailUnsubscribeDTO.getTabIds());
			userMailUnsubscribe.setOrgLocationId(orgLocations);
			userMailUnsubscribe.setUserId(user);
			userMailUnsubscribe.setStatus(Constants.RECORD_ACTIVE);
			userMailUnsubscribe.setUpdatedBy(user);
			userMailUnsubscribe.setUpdatedDate(commonUtil.currentDate());
			userMailUnsubscribeService.saveOrUpdateUserMailUnsubscription(userMailUnsubscribe);
		}else{
			userMailUnsubscribe = new UserMailUnsubscribe();
			userMailUnsubscribe.setTabIds(userMailUnsubscribeDTO.getTabIds());
			userMailUnsubscribe.setOrgLocationId(orgLocations);
			userMailUnsubscribe.setUserId(user);
			userMailUnsubscribe.setStatus(Constants.RECORD_ACTIVE);
			userMailUnsubscribe.setUpdatedBy(user);
			userMailUnsubscribe.setUpdatedDate(commonUtil.currentDate());
			userMailUnsubscribe.setCreatedDate(commonUtil.currentDate());
			userMailUnsubscribeService.saveOrUpdateUserMailUnsubscription(userMailUnsubscribe);
		}
		
		
		return "Success";
	}


	private static BufferedImage colorImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		WritableRaster raster = image.getRaster();

		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				pixels[0] = 0;
				pixels[1] = 0;
				pixels[2] = 255;
				raster.setPixel(xx, yy, pixels);
			}
		}
		return image;
	}

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

}
