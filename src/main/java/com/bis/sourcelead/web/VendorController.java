package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;

import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class VendorController {
	
	private static final Logger logger = LoggerFactory.getLogger(VendorController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	OrgSpocService orgSpocService;

	
	@RequestMapping(value = "/vendorsList")
	public ModelAndView vendorsList(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
	
	/*	for(OrgRelations orgRelation : vandorsListByOrgLocationId){
			List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(), loginUser.getId(),orgLoc.getId());
			orgRelation.setNoOfContatcs(orgSpocs.size());
		}*/
		
	
		model.addObject("loginOrgLocId",orgLoc.getId());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("vendors/vendorsList");
		Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
		model.addObject("loginCountryName", countries.getName());
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Vendors);
		request.getSession().setAttribute("sessionData",sessionData);

		return model;

	}
/**
 * Diplay vendor jsp to enter vendor details to save
 * @author Rajashekar
 * @param request
 * @return ModelAndView
 * @throws Exception
 */
	@RequestMapping(value = "/addVendor")
	public ModelAndView addVendor(HttpServletRequest request)  {
		
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("vendors/vendor");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
			model.addObject("userList", userList);
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Vendors);
			request.getSession().setAttribute("sessionData",sessionData);
			
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			model.addObject("loginCountryName", countries.getName());	
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e)); 
		}
		return model;
	}
	/**
	 * Diplay vendor contact jsp
	 * @author Rajashekar
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addVendorContact")
	public ModelAndView addVendorContac(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("vendors/addVendorContact");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
			List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
			model.addObject("userList", userList);
			model.addObject("loginOrgLocId", orgLocations.getId());
			model.addObject("vendorList", vandorsListByOrgLocationId);
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Vendors);
			request.getSession().setAttribute("sessionData",sessionData);
			
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return model;
	}
}
