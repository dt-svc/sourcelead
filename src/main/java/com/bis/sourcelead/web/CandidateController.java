package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class CandidateController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	 CandidateService candidateService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	/**
	 * Handles to display candidate jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/candidate")
		public ModelAndView candidate(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the candiade page");
		 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ModelAndView model = new ModelAndView();
			model.setViewName("candidates/candidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
            
            List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);
			
			return model;
	    }
	
	@RequestMapping(value = "/candidatesList")
	public ModelAndView usersList(@RequestParam(value="candidateStatus", required=false) String  candidateStatus,HttpServletRequest request) throws Exception {
		logger.debug("--------candidatesList------");
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("candidates/candidatesList");
		
		if(orgLocations != null && StringUtils.isNotEmpty(orgLocations.getCountry())){
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
	        model.addObject("loginCountryName", countries.getName());
		}
		
        
		
	/*	List<Candidate>   candidatesList = candidateService.getCandidatesListByOrgLocationID(orgLocations.getId(),org.getId(),Constants.CANDIDATE_ACTIVE,"");
		model.addObject("candidatesList", candidatesList);*/
		
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);
		
		List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		model.addObject("educationsList", educationsList);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Candidates);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "candidateCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "candidateCreateMsg");
		}
		
		return model;

	}

	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
