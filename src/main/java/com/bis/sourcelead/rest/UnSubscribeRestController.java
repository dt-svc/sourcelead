package com.bis.sourcelead.rest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserMailUnsubscribeDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailUnsubscribe;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserMailUnsubscribeService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class UnSubscribeRestController {
	private static final Logger logger = LoggerFactory.getLogger(UnSubscribeRestController.class);
	 
	@Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 UserMailUnsubscribeService userMailUnsubscribeService;
	 
	 @Autowired
	 CandidateService candidateService;
	 
	 @Autowired
	 SourceleadPasswordEncoder sourceleadPasswordEncoder;
	 
	 @Value("${GoogleMaps_Location_API_KEY}")
	 private String googleMapsLocationApiKey;
	  
	    
	@RequestMapping(value = "/app/ws/unsubscribe", method = RequestMethod.GET)
	public @ResponseBody ModelAndView unsubscribe(HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		/*User user = userService.getUserByVerficationUUID(token);
		if(user != null){
			
			
			
			
			UserMailUnsubscribe userMailUnsubscribe = userMailUnsubscribeService.getUserMailUnsubscriptionByUserId(user.getId());
 			if(userMailUnsubscribe != null && StringUtils.isNotEmpty(userMailUnsubscribe.getTabIds())) {
 				model.addObject("unsubscriptionTabIds", userMailUnsubscribe.getTabIds());
 			}else {
 				model.addObject("unsubscriptionTabIds", "");
 			}
			
			model.addObject("userId",user.getId());
			model.addObject("token",token);
		}*/
		model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.setViewName("unSubscribe");
		return model;
	}
	
	@RequestMapping(value = "/app/isActiveUserByUserEmail" , method = RequestMethod.GET)
	 public @ResponseBody  UserDTO isActiveUserByUserEmail(@RequestParam(value="EmailId", required=false) String EmailId) throws Exception {
		 	User user = userService.findByEmail(EmailId);
		 	UserDTO userDTO = null;
		 	if(user != null) {
		 		UserMailUnsubscribe unsubscriptionUser = userMailUnsubscribeService.getUserMailUnsubscriptionByUserEmail(EmailId);
		 		userDTO = new UserDTO();
		 		userDTO.setFirstName(user.getFirstName());
			 	userDTO.setLastName(user.getLastName());
			 	userDTO.setEmail(user.getEmail());
			 	userDTO.setFullName(user.getFullName());
			 	if(unsubscriptionUser != null) {
			 		userDTO.setUnSubscribeTabIds(unsubscriptionUser.getTabIds());
			 	}
		 	}
			return userDTO;
	   }
	
	
	
	
	@RequestMapping(value = "/app/storeUnsubscriptionPublic", method = RequestMethod.GET)
	public String storeUnsubscriptionPublic(UserMailUnsubscribeDTO userMailUnsubscribeDTO) throws Exception
	{
		try {
				User user = userService.getUserByEmail(userMailUnsubscribeDTO.getUniqueToken());
				if(user != null){
					UserMailUnsubscribe userMailUnsubscribe = userMailUnsubscribeService.getUserMailUnsubscriptionByUserId(user.getId());
					if(userMailUnsubscribe != null) {
						userMailUnsubscribe.setTabIds(userMailUnsubscribeDTO.getTabIds());
						userMailUnsubscribe.setUserId(user);
						userMailUnsubscribe.setStatus(Constants.RECORD_ACTIVE);
						userMailUnsubscribe.setUpdatedBy(user);
						userMailUnsubscribe.setUpdatedDate(commonUtil.currentDate());
						userMailUnsubscribeService.saveOrUpdateUserMailUnsubscription(userMailUnsubscribe);
					}else{
						userMailUnsubscribe = new UserMailUnsubscribe();
						userMailUnsubscribe.setTabIds(userMailUnsubscribeDTO.getTabIds());
						userMailUnsubscribe.setUserId(user);
						userMailUnsubscribe.setStatus(Constants.RECORD_ACTIVE);
						userMailUnsubscribe.setUpdatedBy(user);
						userMailUnsubscribe.setUpdatedDate(commonUtil.currentDate());
						userMailUnsubscribe.setCreatedDate(commonUtil.currentDate());
						userMailUnsubscribeService.saveOrUpdateUserMailUnsubscription(userMailUnsubscribe);
					}
					return "Success";
				}else {
					return "Failure";
				}
			}catch (Exception e) {
				return "Failure";
			}
	}
	
	@RequestMapping(value = "/app/userVerification", method = RequestMethod.GET)
	public Boolean userVerification(UserMailUnsubscribeDTO userMailUnsubscribeDTO) throws Exception
	{
		try {
				User user = userService.getUserByEmail(userMailUnsubscribeDTO.getEmail());
				
				if(sourceleadPasswordEncoder.matches(userMailUnsubscribeDTO.getCredential(), user.getPassword())){
					return true;
				}else {
					return false;
				}
				
			}catch (Exception e) {
				return false;
			}
	}
	
	
	 protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
}