package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.performance.PerformanceBadgesService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class PerformanceManagementRestController {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceManagementRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	PerformanceBadgesService performanceBadgesService;
	
	
    @RequestMapping(value = "/savePerformanceBudgetDetails", method = RequestMethod.POST)
	public Response<PerformanceBadgesDTO> savePerformanceBudgetDetails(HttpServletRequest request, 
			PerformanceBadgesDTO performanceBadgesDTO){
		 Response<PerformanceBadgesDTO> resObj = new Response<>();
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 try {
			 if(Validator.validate(performanceBadgesDTO)){
				 performanceBadgesDTO.setStaticPath(staticPath);
				 performanceBadgesService.storePerformanceBadges(performanceBadgesDTO, orgLocation.getId(), user.getId());
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
		    }
			 else {
				 resObj.setResponse(Result.INVALID_DATA.name());
				 resObj.setResponseMessage(Result.INVALID_DATA.getValue());
				
	    	 }
		 }catch (Exception e) {
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return resObj;
    }
    
    
    
    @RequestMapping(value = "/getPerformanceRewardsAndBadgeDetailsById", method = RequestMethod.GET)
	public Response<PerformanceBadgesDTO> getPerformanceRewardsAndBadgeDetailsById(HttpServletRequest request, 
			@RequestParam(value = "badgeId", required = true) Long badgeId){
		 Response<PerformanceBadgesDTO> resObj = new Response<>();
		 try {
			 PerformanceBadgesDTO badgesDTO =  performanceBadgesService.getPerformanceBadgesById(badgeId);
			 resObj.setObject(badgesDTO);
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
		    
		 }catch (Exception e) {
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return resObj;
    }
    
    
    @RequestMapping(value = "/deletePerformanceRewardsAndBadgeById", method = RequestMethod.POST)
  	public String deletePerformanceRewardsAndBadgeById(HttpServletRequest request, 
  			@RequestParam(value = "badgeId", required = true) Long badgeId){
  		 String resObj = null;
  		 try {
  			 PerformanceBadges badges =  performanceBadgesService.getPerformanceBadgesDetailsById(badgeId);
  			 badges.setStatus(Constants.RECORD_INACTIVE);
  			 performanceBadgesService.savePerformanceBadges(badges);
  			 resObj = Result.SUCCESS.name();
  		    
  		 }catch (Exception e) {
  			resObj = Result.AWKWARD.name();
  		}
  		 return resObj;
    }
}
