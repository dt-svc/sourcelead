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
import com.bis.sourcelead.entity.BoPaymentTypes;
import com.bis.sourcelead.service.BoPaymentTypesService;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class BackOfficeSummaryController {
	
private static final Logger logger = LoggerFactory.getLogger(BackOfficeSummaryController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	BoPaymentTypesService boPaymentTypesService;
	
	
	@RequestMapping(value = "/financialInstituteSummary")
	public ModelAndView financialInstituteSummary(HttpServletRequest request,@RequestParam(value="financialInstitute", required=false) String financialInstitute) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/financialInstituteSummary");
		model.addObject("financialInstitute",financialInstitute);
		
		List<BoPaymentTypes> boPaymentTypesList = boPaymentTypesService.getAllBoPaymentTypes();
		model.addObject("boPaymentTypesList",boPaymentTypesList);
		model.addObject("boPaymentTypesListSize",boPaymentTypesList.size());
		
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Finance);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}

}
