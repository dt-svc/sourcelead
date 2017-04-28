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
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class InvoiceController {
	private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	 CandidateService candidateService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	InvoiceService invoiceService;
	
	 @Autowired
	 CommonUtil commonUtil;
	 
/**
 * @author Laxman
 * @param type
 * @param request
 * @return model
 * @throws Exception
 */
	@RequestMapping(value = "/invoiceList")
	public ModelAndView invoiceList(@RequestParam(value="type", required=false) String type ,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(null != countries){
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
		}
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		model.setViewName("billing/invoiceList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Billing);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	
	@RequestMapping(value = "/createInvoice")
	public ModelAndView createInvoice(@RequestParam(value="type", required=false) String type ,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(null != countries){
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
		}
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		model.setViewName("billing/createInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Billing);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	
	@RequestMapping(value = "/getInvoiceList")
	public String getInvoiceList(HttpServletRequest request) throws Exception {
		String resultJson;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Invoice> invoiceList = invoiceService.invoiceListByOrgLocationId(orgLocations.getId());
		for(Invoice invoice :invoiceList){
			if(invoice.getClientOrgRelationID().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				invoice.setClientName(invoice.getClientOrgRelationID().getResponderLocationId().getOrg().getOrgName());
			}
			if(invoice.getClientOrgRelationID().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
				invoice.setClientName(invoice.getClientOrgRelationID().getLocationId().getOrg().getOrgName());
			}
				
			StringBuilder contactName = new StringBuilder();
			if(invoice.getContactContactUser().getLastName() != null){
				invoice.setClientContactName(contactName.append(invoice.getContactContactUser().getFirstName()).append(" ").append(invoice.getContactContactUser().getLastName()).toString());	
			}else{
				invoice.setClientContactName(contactName.append(invoice.getContactContactUser().getFirstName()).toString());
			}
			
		}
	    resultJson = commonUtil.toJSON(invoiceList);
	  return resultJson;
	}
	
	
	
	
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
