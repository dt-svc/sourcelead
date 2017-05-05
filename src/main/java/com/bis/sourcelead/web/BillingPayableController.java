package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class BillingPayableController {

	private static final Logger logger = LoggerFactory.getLogger(BillingPayableController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	
	@RequestMapping(value = "/payablesList")
	public ModelAndView billingPayablesList(@RequestParam(value="type", required=false) String type ,HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		
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
