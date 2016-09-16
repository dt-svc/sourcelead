package com.bis.sourcelead.rest;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Requirement service.
 * 
 * @author Prasad K
 * @date 13th Sept 2016
 *
 */

@RestController
public class RequirementRestController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	UserService userService;
	
	 @Autowired
	 CommonUtil commonUtil;
	
	/**
	 * @author prasad k
	 * @since  sept 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 * @param jsonObj This is the first paramter to storeRequirement method
	 * @param orgLocations This is the second paramter to storeRequirement method
	 * @param user This is the third paramter to storeRequirement method
	 * @param multiFile This is the fourth paramter to storeRequirement method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	*/
	
	@RequestMapping(value = "/storeRequirement", method = RequestMethod.POST)
	public @ResponseBody String storeRequirement(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
	    
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Requirement requirement = requirementService.storeRequirement(jsonObj,orgLocation,user,multiFile);
		return "success";
	}
	
	
	/**
	 * @author punnana sarath kumar
	 * @since  sept 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 * @param jsonObj This is the first paramter to storeRequirement method
	 * @param orgLocations This is the second paramter to storeRequirement method
	 * @param user This is the third paramter to storeRequirement method
	 * @param multiFile This is the fourth paramter to storeRequirement method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	*/
	
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public @ResponseBody String getUserList(HttpServletRequest request) throws Exception {
		
		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> usersList = userService.getOwnersByOrgLocationId(orgLocation.getId());
		resultJson = commonUtil.toJSON(usersList);
		return resultJson;
	}
	
	 
}
