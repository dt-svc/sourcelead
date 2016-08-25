package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class VendorController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	
	@RequestMapping(value = "/vendorsList")
	public ModelAndView usersList(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
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

	@RequestMapping(value = "/addVendor")
	public ModelAndView addVendor(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("vendors/vendor");
		
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
