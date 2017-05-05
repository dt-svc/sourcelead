package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class BillingPayableController {

	private static final Logger logger = LoggerFactory.getLogger(BillingPayableController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	 CandidateService candidateService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	 @Autowired
	 CommonUtil commonUtil;
	
	
	
	@RequestMapping(value = "/payablesList")
	public ModelAndView billingPayablesList(@RequestParam(value="type", required=false) String type ,HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());

		if(null != clientList){
			for (OrgRelations orgRelations : clientList) {
				String orgName = "";
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgName = orgRelations.getLocationId().getOrg().getOrgName();
				}if(orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgName = orgRelations.getResponderLocationId().getOrg().getOrgName();
				}
				orgRelations.setOrgName(orgName);
			}
			 model.addObject("clientList", clientList);
		}
		if(null != countries){
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		model.setViewName("billing/payablesList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Payables);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	
}
