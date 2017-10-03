package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.CommonUtil;

@Controller
public class VendorOnboardController {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	VendorOnboardService vendorOnboardService;
	
	
	@RequestMapping(value = "/acceptVendorOnboardInvitation")
 	public ModelAndView vendorOnboard(@RequestParam(value="onboardUUID", required = true)String onboardUUID,HttpServletRequest request) throws Exception {
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.Vendors, request);
 			model.addObject("onboardUUID",onboardUUID);
 			VendorOnboarding vendorOnboarding = vendorOnboardService.getVendorOnboardingByUUID(onboardUUID);
 			model.addObject("vendorOnboardId",vendorOnboarding.getId());
 			model.setViewName("vendors/vendorOnboard");
 			return model;
 	}

}
