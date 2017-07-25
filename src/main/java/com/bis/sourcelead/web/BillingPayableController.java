package com.bis.sourcelead.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
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
	private OrgSpocService orgSpocService;
	
    @Autowired
    CommonUtil commonUtil;
    
    @Autowired
    ConversionUtil conversionUtil;
	
	
	
	@RequestMapping(value = "/payablesList")
	public ModelAndView billingPayablesList(@RequestParam(value="type", required=false) String type ,HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "payableInvoiceCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "payableInvoiceCreateMsg");
		}
		model.setViewName("billing/payablesList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Payables);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	@RequestMapping(value = "/createPayables")
	public ModelAndView createPayables(@RequestParam(value="type", required=false) String type ,HttpServletRequest request,@RequestParam(value="orgRelationId", required=false) Long orgRelationId) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		
		if(null != countries){
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
		}
		if(orgRelationId != null){
			model.addObject("orgRelationId", orgRelationId);
		}
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		SimpleDateFormat dateFormatter = orgLocations.getLocFullFormDateFormatter();
		Date currentDate = new Date();
		String convertedDate = dateFormatter.format(currentDate);
		model.addObject("currentDate", convertedDate);
		model.setViewName("billing/createPayables");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Payables);
		request.getSession().setAttribute("sessionData",sessionData);
	    return model;
	}
	
	
	@RequestMapping(value = "/getInvoiceByUUID")
	public ModelAndView getInvoiceByUUID(@RequestParam(value="invoiceUUID", required=false) String invoiceUUID ,@RequestParam(value="projectId", required=false) String projectId,HttpServletRequest request) throws Exception {
		
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
		if(null != projectId && !"".equals(projectId) && !projectId.equalsIgnoreCase("undefined")){
			model.setViewName("billing/createProjectPayableInvoice");
		}else{
			model.setViewName("billing/createPayables");			
		}
		model.addObject("invoiceUUID", invoiceUUID);
		model.addObject("projectId", projectId);
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Payables);
		request.getSession().setAttribute("sessionData",sessionData);
	    return model;
	}
	
	
	
	@RequestMapping(value = "/getPayableVendorsList", method = RequestMethod.GET)
	public @ResponseBody String getPayableVendorsList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgRelations> vendorList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
		List<OrgRelations> vendorsList = new ArrayList<OrgRelations>();

		if(null != vendorList){
			for (OrgRelations orgRelations : vendorList) {
				String orgName = "";
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgName = orgRelations.getLocationId().getOrgLocationName();
				}
				
				if(orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgName = orgRelations.getResponderLocationId().getOrgLocationName();
				}
				orgRelations.setOrgName(orgName);
				vendorsList.add(orgRelations);
			}
		}
		
		return commonUtil.toJSON(vendorsList);
	} 
	
	
	
	@RequestMapping(value = "/getPayableVendorContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody String getPayableVendorContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
		
		for(OrgSpoc orgSpoc : orgSpocList){
			StringBuilder sb = new StringBuilder();
			
			if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()){
			sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
			}else{
			sb.append(orgSpoc.getVendorUser().getFirstName());
			}
			
			orgSpoc.setOwnerName(sb.toString());
			orgSpoc.setSpocUserId(orgSpoc.getVendorUser().getId());
			orgSpoc.setVendorCurrency(conversionUtil.getVendorOrgCurrencyCode(orgSpoc.getVendorLocationId().getCountry()));
			}
		return commonUtil.toJSON(orgSpocList);
	} 
	
	
	@RequestMapping(value = "/getPaymentTermsAndBillindAddress", method = RequestMethod.GET)
	public @ResponseBody String getPaymentTermsAndBillindAddress(@RequestParam(value = "orgRelationId", required = false) String orgRelationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
		for(OrgSpoc orgSpoc : orgSpocList){
			if(orgSpoc.getOrgRelations().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
				
				orgSpoc.setPaymentTerms(orgSpoc.getOrgRelations().getLocationId().getOrg().getPaymentTerm());
				orgSpoc.setBillingAddress(orgSpoc.getOrgRelations().getLocationId().getOrg().getClientBillingAddress());
				
			}
			
			if(orgSpoc.getOrgRelations().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				orgSpoc.setPaymentTerms(orgSpoc.getOrgRelations().getResponderLocationId().getOrg().getPaymentTerm());
				orgSpoc.setBillingAddress(orgSpoc.getOrgRelations().getResponderLocationId().getOrg().getClientBillingAddress());
			}
			
			
			}
		return commonUtil.toJSON(orgSpocList);
	} 
}
