package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.CommonUtil;

@Controller
public class VendorOnboardController {
	
	@Autowired
	CommonUtil commonUtil;
	
	
	@RequestMapping(value = "/acceptVendorOnboardInvitation")
 	public ModelAndView vendorOnboard(@RequestParam(value="onboardUUID", required = true)String onboardUUID,HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.Vendors, request);
 			model.addObject("onboardUUID",onboardUUID);
 			model.setViewName("vendors/vendorOnboard");
 			return model;
 	}

}
