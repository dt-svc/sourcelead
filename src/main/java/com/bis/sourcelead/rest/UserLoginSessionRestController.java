package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bis.sourcelead.SourceleadUserDetailsService;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dto.MobilePushNotificationDTO;
import com.bis.sourcelead.dto.OrgLocationDTO;
import com.bis.sourcelead.dto.UserDetailsDTO;
import com.bis.sourcelead.dto.UserProfileDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.form.RestResponsetUtil;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.MobilePushNotificationService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@RequestMapping("/app")
public class UserLoginSessionRestController {
	
private static final Logger logger = LoggerFactory.getLogger(UserLoginSessionRestController.class);

private static final String HEADER_TOKEN = "X-CustomToken";
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	 
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ModulesService modulesService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private TabsService tabsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private MobilePushNotificationService mobilePushNotificationService;
	
	@Autowired
	private UserStatsCacheHelper userStatsCacheHelper;

	@Autowired
	private SourceleadUserDetailsService sourceleadUserDetailsService;
		 
	 @RequestMapping(value = "/rest/", method = RequestMethod.POST)
	 public void authenticate() {
	 }
		 
	 @RequestMapping(value = "/rest/loginUserProfile", method = RequestMethod.GET)
	 public UserProfileDTO getLoginUserProfile(@RequestParam(value = "deviceToken", required = false) String deviceToken,
			 @RequestParam(value = "deviceType", required = false) String deviceType) throws Exception {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 User  user = userService.findByUserName(auth.getName());
		 List<UserRole> userRoleList = null;
		 List<OrgLocationDTO> orgLocationsList = new ArrayList<>();
		 
         UserProfileDTO userProfileDTO = new UserProfileDTO();
         userProfileDTO.setFirstName(user.getFirstName());
 	     userProfileDTO.setLastName(user.getLastName());
 	     userProfileDTO.setUserId(user.getId());
 	  
 	     if(StringUtils.isNoneBlank(user.getFileUrl())){
    	    userProfileDTO.setProfileImageUrl(user.getFileUrl());
		 }else{
			userProfileDTO.setProfileImageUrl("/resources/images/userprofile-img.png");
		 }
 	     
         try {
        	    if(user != null){
        		     userRoleList = userRoleService.getUserSpocsListByUserId(user.getId());
        		     if(null != userRoleList && userRoleList.size() > 0){
        		    	 for(UserRole userRole : userRoleList){
        		    		 
	        		    		List<SourceleadActions> actionsList = null;
	        			        List<String> tabNamesList = null;
	        			        List<String> tabIconsList = new ArrayList<String>();
	        			        List<String> actionRolesList = new ArrayList<String>();
	        			        List<Long> actionIdsList = new ArrayList<Long>();
	        			        List<Long> modulesId = null;
	        			        
	        			        
        		    		    OrgLocations orgLocations = userRole.getOrgLocations();
        		        	    modulesId = modulesService.getOrgRegisteredModulesByOrgLocationId(orgLocations);
        		        	    actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
        		        	    
        		        	    for(SourceleadActions actions : actionsList){
        		        	    	actionIdsList.add(actions.getId());
        		        	    	//actionRolesList.add(actions.getActionRole());
        		        	    }
        		        	    
        		        	    tabNamesList = tabsService.getTabsNamesListByActionsIds(actionIdsList);
        		        	    for(String tabName : tabNamesList){
        		        	    	
        		        	    	tabName = tabName.substring(0, 1).toLowerCase() + tabName.substring(1);
        		        	    	StringBuilder name = new StringBuilder();
        		        	    	name.append("ic_").append(tabName).toString();
        		        	    	tabIconsList.add(name.toString().replaceAll(" ", "_").toLowerCase());
        		        	    }
        		        	    
        		        	    OrgLocationDTO orgLocationDTO =  getOrgLocationDetails(orgLocations,userRole);
        		        	    orgLocationDTO.setMenuList(tabNamesList);
        		        	    orgLocationDTO.setMenuIconsList(tabIconsList);
        		        	    Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
        		        	    orgLocationDTO.setCandidateId(candidate.getId());
        		        	    //orgLocationDTO.setRoleActionsList(actionRolesList);
        		        	    orgLocationDTO.setUserType(userRole.getUserType());
        		        	    orgLocationsList.add(orgLocationDTO);
        		    	 }
        		     }
        		     
        		    
        	     }
        	    
               } catch (Exception e) {
				e.printStackTrace();
			 }
         
         userProfileDTO.setOrgLocationsList(orgLocationsList);
         //Mobile device token information saving  
         if(deviceToken != null && deviceToken != "" && deviceToken != "undefined" && deviceType != null && deviceType != "" && deviceType != "undefined") {
        	 Boolean flag = mobilePushNotificationService.validateMobileDevicceTokenByUserIdAndTokeIndAndDeviceType(user.getId(), deviceToken, deviceType);
        	if(flag == false) {
        		 MobilePushNotificationDTO mpntd = new MobilePushNotificationDTO();
	        	 mpntd.setDeviceToken(deviceToken);
	        	 mpntd.setDeviceType(deviceType);
	        	 mobilePushNotificationService.saveMobileDeviceTokenInformation(mpntd);
        	  }
         }
		return userProfileDTO;
         
	 }
		 
		 
	 private OrgLocationDTO getOrgLocationDetails(OrgLocations orgLocations,UserRole useRole){
		 OrgLocationDTO orgLocationDTO = new OrgLocationDTO();
		 orgLocationDTO.setOrgName(orgLocations.getOrgLocationName());
		 orgLocationDTO.setOrgLocationId(orgLocations.getId());
		 orgLocationDTO.setOrgId(orgLocations.getOrg().getId());
		 orgLocationDTO.setLocationCode(orgLocations.getLocationCode());
		 orgLocationDTO.setOrgCode(orgLocations.getOrg().getOrgCode());
		 if(orgLocations.getOrg().getDocFile() != null && orgLocations.getOrg().getFileContentType() != null){
			 orgLocationDTO.setOrgImageUrl(sourceleadUrl+"/displayOrgImage/"+orgLocations.getOrg().getOrgCode());
		 }else{
			 orgLocationDTO.setOrgImageUrl(sourceleadUrl+"/resources/images/office-image.jpg");
		 }
		 orgLocationDTO.setCountry(orgLocations.getCountry());
		 orgLocationDTO.setState(orgLocations.getState());
		 orgLocationDTO.setCity(orgLocations.getCity());
		 orgLocationDTO.setOrgLocationAddress(orgLocations.getOrgLocationAddress());
		 orgLocationDTO.setWebsite(orgLocations.getWebsite());
		 orgLocationDTO.setDateFormat(orgLocations.getDateFormat());
		 if(useRole.getIsPrimary().equals(1)){
			 orgLocationDTO.setIsPrimaryOrg(Constants.YES);
		 }else{
			 orgLocationDTO.setIsPrimaryOrg(Constants.NO);
		 }
		 return orgLocationDTO;
	 }
		 
	 @RequestMapping(value = "/restAuthenticate", method = RequestMethod.POST)
	 public @ResponseBody RestResponsetUtil restAuthenticate(HttpServletRequest request) throws Exception {
	 
		 String json = IOUtils.toString(request.getInputStream());
		 RestResponsetUtil resResponsetUtil = new RestResponsetUtil();
		 if(StringUtils.isNotEmpty(json)){
			 String sourceleadUrl = request.getRequestURL().toString().replace("/restAuthenticate","");
			 //TODO remove this sysout. Just for rest call testing purpose
			
			 JSONObject jsonObject = new JSONObject(json);
			 if(jsonObject != null && jsonObject.has("username")&& jsonObject.has("password") 
			    && StringUtils.isNotEmpty(jsonObject.getString("username"))&& StringUtils.isNotEmpty(jsonObject.getString("password")))
			 { 		
				 final String uri = sourceleadUrl+"/rest/";
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("X-Username", jsonObject.getString("username"));
                    headers.add("X-Password", jsonObject.getString("password"));
                    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
                    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
                     int status = response.getStatusCode().value();

				 if(status == HttpServletResponse.SC_OK){
					 String header = response.getHeaders().get("X-CustomToken").toString();
					 String token = header.replaceAll("\\[", "").replaceAll("\\]", "");
					 resResponsetUtil.setToken(token);
					 resResponsetUtil.setLoginResponse("Valid User");
					 //TODO remove this sysout. Just for rest call testing purpose
					 System.out.println("##### in ServletRESPONSE OK =======> ");
					 return resResponsetUtil;
				 }
				
				 else if(status == HttpServletResponse.SC_NOT_FOUND){
					 resResponsetUtil.setLoginResponse("User Not Registered");
					 return resResponsetUtil;
				 }
				 else if(status == HttpServletResponse.SC_MOVED_TEMPORARILY){
					 resResponsetUtil.setLoginResponse("In-Active User");
					 return resResponsetUtil;
				 }
				 else if(status == HttpServletResponse.SC_UNAUTHORIZED){
					 resResponsetUtil.setLoginResponse("Invalid UserName Or Password");
					 return  resResponsetUtil;
				 }
			 }
		 }
		 resResponsetUtil.setLoginResponse("Invalid Json String");
		 return resResponsetUtil;
	 }
	 
	 @RequestMapping(value = "/rest/loginUserImageUrl", method = RequestMethod.GET)
     public String getLoginUserImageUrl(@RequestParam(value="userId", required=false) Long userId,HttpServletRequest request) throws Exception {
         User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
         String imageUrl = "";
         if(loginUser == null){
                loginUser = userService.getUserById(userId);
                imageUrl = loginUser.getFileUrl();
         }else{
             imageUrl = loginUser.getFileUrl();
         }
         if(StringUtils.isBlank(imageUrl))
             imageUrl ="/resources/images/userprofile-img.png";
         return imageUrl;
     }
	 
	@RequestMapping(value = "/rest/refreshSessionAttributes", method = RequestMethod.GET)
	public int refreshSessionAttributes(@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "locationCode", required = true) String locationCode, HttpServletRequest request)
			throws Exception {
		System.out.println(" swith user details refreshSessionAttributes method is called >>>>>>>>>>>>>>");
		int output = HttpServletResponse.SC_NOT_FOUND;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		System.out.println(" userId:::is " + userId + ", locationCode is " + locationCode);
		System.out.println(" loginUser:::is " + loginUser.getId());
		if (userId != null) {
			loginUser = userService.getUserById(userId);
		}
		String token = request.getHeader(HEADER_TOKEN);
		OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);

		System.out.println("orgLocation id latest passed by mobile team :::is " + orgLocation.getId());

		UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(), orgLocation.getId());
		UserDetailsDTO userDetailsDTO = userStatsCacheHelper.getUserDetails(token);

		UserDetails userDetails = sourceleadUserDetailsService.loadUserByUsernameForMobile(userRole);
		userDetailsDTO.setOrgLocationCode(locationCode);
		if (userDetails.getAuthorities() != null) {
			List<String> roles = userDetails.getAuthorities().stream().map(ga -> ga.getAuthority())
					.collect(Collectors.toList());
			userDetailsDTO.setRoles(roles);
		}
		if (userRole != null && orgLocation != null) {
			userStatsCacheHelper.saveLoginUserSessionStoreToCouchbaseForMobiles(token, userDetailsDTO, request);
			output = HttpServletResponse.SC_OK;
		}
		System.out.println(" swith user details are updated for the user id " + loginUser.getId() + ", Orglocation Id :"
				+ orgLocation.getId() + ", for user role id : " + userRole.getId());
		return output;
	}
}
