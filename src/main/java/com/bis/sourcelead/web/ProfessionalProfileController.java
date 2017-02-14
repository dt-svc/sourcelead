package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ProfessionalProfileController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CommonUtil commonUtil;
	
	 @PreAuthorize("hasRole('ROLE_CROWDSOURCING_PROFILE')")
	 @RequestMapping(value = "/professionalProfile")
		public ModelAndView professionalProfile(HttpServletRequest request) throws Exception {
		    
		        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				ModelAndView model = new ModelAndView();
				
				SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
				if(sessionData == null)
				{
					sessionData = new SessionMaintainanceData();
				}
				sessionData.setNav(Constants.Profile);
				
				request.getSession().setAttribute("sessionData",sessionData);
				
				model.setViewName("professional/profile");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 
	 @RequestMapping(value = "/getProfessionalInfo" , method = RequestMethod.GET)
		public String getProfessionalInfo(@RequestParam(value="candidateStatus", required=false) String  candidateStatus,HttpServletRequest request) throws Exception {
		        String resultJson;
		        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		        Candidate candidate =	candidateService.getProfessionalInfoByLoginUserIdAndCandidateType(loginUser.getId(), candidateStatus);
		        resultJson = commonUtil.toJSON(candidate);
				return resultJson;
	}
	 
	 
}
