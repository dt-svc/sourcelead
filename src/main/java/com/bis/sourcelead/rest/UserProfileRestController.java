package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.dto.AddUserProfileDTO;
import com.bis.sourcelead.dto.PasswordSettingsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
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
	
	@RequestMapping(value = "/saveUserProfileImage", method = RequestMethod.POST)
	public String saveProfileImage(AddUserProfileDTO addUserProfileDTO,	HttpServletRequest request) throws Exception{
		addUserProfileDTO.setStaticPath(staticPath);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
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
	
	
	@RequestMapping(value = "/rest/saveUserProfile", method = RequestMethod.POST)
	public String saveProfileImageForMobile(@RequestBody AddUserProfileDTO addUserProfileDTO,	HttpServletRequest request)throws Exception{
		return saveProfileImage(addUserProfileDTO, request);
	}
	@RequestMapping(value =  "/rest/saveUserProfile", method = RequestMethod.POST)
	public AddUserProfileDTO saveUserProfileForMobile(@RequestBody AddUserProfileDTO addUserProfileDTO, HttpServletRequest request) throws Exception
	{
		return saveUserProfile(addUserProfileDTO, request);
	}
	/**
	 * User profile updation 
	 * @param addUserProfileDTO
	 * @param request
	 * @return AddUserProfileDTO
	 * @throws Exception
	 */
	@RequestMapping(value =  "/saveUserProfile", method = RequestMethod.POST)
	public AddUserProfileDTO saveUserProfile(AddUserProfileDTO addUserProfileDTO, HttpServletRequest request)
			throws Exception {
		addUserProfileDTO.setStaticPath(staticPath);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user == null){
			user = userService.getUserById(addUserProfileDTO.getUserId());
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		if(orgLocations == null){
			 orgLocations = orgLocationService.getOrgLocations(Long.parseLong(addUserProfileDTO.getOrgLocationId()));
		}
		UserRole userRoleElk = null;
		AddUserProfileDTO profileDTO = null;
		try {
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

		} catch (Exception e) {
			logger.info("--exception--" + e);

		}
		if (user != null) {
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
		}
		return profileDTO;

	}
	

	@RequestMapping(value = { "/passwordSettings", "/rest/passwordSettings" }, method = RequestMethod.POST)
	public String passwordSettings(PasswordSettingsDTO passwordSettingsDTO,
			HttpServletRequest request) throws Exception {
		
		 User user = null;
		 OrgLocations orgLocations = null;
		 
		 user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		if(user == null) {
			user = userService.getUserById(passwordSettingsDTO.getUserId());
		}
		
		if(orgLocations == null) {
			orgLocations = orgLocationService.getOrgLocations(passwordSettingsDTO.getOrgLocationId());
		}

		if (sourceleadPasswordEncoder.matches(passwordSettingsDTO.getCurrentPassword(), user.getPassword())) {
			user.setPassword(sourceleadPasswordEncoder.encode(passwordSettingsDTO.getPassword()));
			try {
				userService.addUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			SourceLeadSessionManager.setSessionAttribute(request, "resetPwdMsg",
					"Your password has reset successfully");
			userService.passwordResetMailToLoginUser(user,orgLocations);
			return "success";
		} else {
			return "Invalid Password";
		}

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
	public String deleteProfileImage(HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		user.setDocFile(null);
		user.setFileUrl(null);
		user.setFileContentType(null);
		userService.addUser(user);
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);

		return "success";

	}

	@RequestMapping(value = "/displayUserImage/{userId}", method = RequestMethod.GET)
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

	}
	
	
	   @RequestMapping(value = "/displayOnboardingUserImage/{userId}" , method = RequestMethod.GET)
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
