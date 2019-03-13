package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ReleaseNoteDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReleaseNote;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.ReleaseNoteService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@RequestMapping("/app")
public class ReleaseNoteRestController {

	private static final Logger logger = LoggerFactory.getLogger(ReleaseNoteRestController.class);
	
	@Autowired
	public ReleaseNoteService releaseNoteService;
	
	@Autowired
	public UserService userService;
	
	 @Autowired
	 CommonUtil commonUtil;
	

    @RequestMapping(value = "/storeReleaseNote", method = RequestMethod.POST)
    @ResponseBody
	public   Response<String> storeReleaseNote(ReleaseNoteDTO  releaseNoteDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    	Response<String> respons=new Response<>();
    	try{
    		if(user !=null){
    			if(Validator.validate(releaseNoteDTO)){
    				releaseNoteService.saveReleaseNoteDTO(releaseNoteDTO,orgLocations);
    				respons.setResponse(Result.SUCCESS.name());
    				respons.setResponseMessage(Result.SUCCESS.getValue());
    			}else{
    				respons.setObject("DataMissing");
					respons.setResponse(Result.INVALID_DATA.name());
					respons.setResponseMessage(Result.INVALID_DATA.getValue());
    			}
    		}else{
    			respons.setResponse(Result.SESSION_TIMEDOUT.name());
				respons.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
    		}
    		
    	}catch (Exception e) {
    		respons.setResponse(Result.AWKWARD.name());
			respons.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return respons;
  
	}
   
    @RequestMapping(value = "/getReleaseNoteList", method = RequestMethod.GET)
	public @ResponseBody  List<ReleaseNote> getReleaseNoteList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
    	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	List<ReleaseNote> releaseNoteList = releaseNoteService.getAllReleaseNote();
		for(ReleaseNote rn : releaseNoteList) {
			rn.setReleaseDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), rn.getReleaseDate()));
			rn.setPublishDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), rn.getPublishDate()));
		}
		return releaseNoteList;
	}
    
    @RequestMapping(value = "/deleteReleaseNote", method = RequestMethod.GET)
   	public Response<String> deleteReleaseNote(@RequestParam(value = "releaseUUID", required = false) String releaseUUID,HttpServletRequest request,HttpServletResponse response) throws Exception
   	{
    	Response<String> respons = new Response<>();
		try {
			if(releaseUUID != null) {
				ReleaseNote rn =  releaseNoteService.getReleaseNoteByUUId(releaseUUID);
				rn.setStatus(Constants.RECORD_INACTIVE);
				releaseNoteService.saveReleaseNote(rn);
				
				respons.setResponse(Result.SUCCESS.name());
				respons.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				respons.setResponse(Result.INVALID_DATA.name());
				respons.setResponseMessage(Result.INVALID_DATA.getValue());	
			} 
		}catch (Exception e) {
			respons.setResponse(Result.AWKWARD.name());
			respons.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return respons;
		 
   	}
    
 // to validate Project SequeneNumber
 	@RequestMapping(value = "/validateReleaseNoteName", method = RequestMethod.POST)
 	public @ResponseBody Boolean validateReleaseNoteName(
 			@RequestParam(value = "ReleaseNoteName", required = false) String releaseNoteName,@RequestParam(value = "releaseUUID", required = false) String releaseUUID) throws Exception {
 		ReleaseNote rn = null;
 		if(releaseUUID != null) {
 			rn = releaseNoteService.validateReleaseNoteNameByReleaseUUIDAndReleaseNoteName(releaseNoteName,releaseUUID);
 		}
 		 Boolean flag = false;
 		 if(rn == null) {
 			flag = releaseNoteService.validateReleaseNoteName(releaseNoteName);
 		 }
 		
 		return flag;
 	}
 	
}
