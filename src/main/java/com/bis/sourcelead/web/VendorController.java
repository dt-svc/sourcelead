package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class VendorController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@RequestMapping(value = "/vendorsList")
	public ModelAndView vendorsList(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLoc.getId());
        model.addObject("vandorsListByOrgLocationId", vandorsListByOrgLocationId);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("vendors/vendorsList");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Vendors);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		
		return model;

	}

}
