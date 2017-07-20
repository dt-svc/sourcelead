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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectsService;
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
	OrgRelationsService orgRelationsService;
	
	@Autowired
	CommonUtil commonUtil;
	 
	@Autowired
	private ProjectsService projectsService;
	 
/**
 * @author Laxman
 * @param type
 * @param request
 * @return model
 * @throws Exception
 */
	@RequestMapping(value = "/createInvoice")
	public ModelAndView invoiceList(@RequestParam(value="type", required=false) String type , @RequestParam(value="orgRelationId", required=false) Long orgRelationId ,HttpServletRequest request) throws Exception {
		
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
		SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
		model.setViewName("billing/createInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		Date currentDate = new Date();
		String convertedDate = formatter.format(currentDate);
		model.addObject("currentDate", convertedDate);
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	
	/**
	 * @author Laxman
	 * @param invoiceUUID
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getReceivableInvoiceByUUID")
	public ModelAndView getReceivableInvoiceByUUID(@RequestParam(value="invoiceUUID", required=false) String invoiceUUID,@RequestParam(value="projectId", required=false) String projectId,HttpServletRequest request) throws Exception {
		
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
		if(null != projectId && !"".equalsIgnoreCase(projectId) && !"undefined".equalsIgnoreCase(projectId)){
			model.setViewName("billing/createProjectReceivableInvoice");
		}else{
			model.setViewName("billing/createInvoice");
		}
		model.addObject("invoiceUUID", invoiceUUID);
		model.addObject("projectId", projectId);
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	    return model;
	}
	
	
	/**
	 * @author Laxman 
	 * @param request
	 * @return modelView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInvoice")
	public ModelAndView viewInvoice(@RequestParam(value="invoiceUUID", required=false) String invoiceUUID,@RequestParam(value="invoiceType", required=false) String invoiceType,HttpServletRequest request) throws Exception {
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
		model.setViewName("billing/viewInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("invoiceUUID", invoiceUUID);
		model.addObject("invoiceType", invoiceType);
		if(invoiceType.equalsIgnoreCase("Receivable")){
			sessionData.setNav(Constants.Receivable);
		}else{
			sessionData.setNav(Constants.Payables);
		}
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	
	@RequestMapping(value = "/invoiceList")
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
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "invoiceCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "invoiceCreateMsg");
		}
		model.setViewName("billing/invoiceList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	
	@RequestMapping(value = "/getClientList", method = RequestMethod.GET)
	public @ResponseBody String getClientList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
		if(null != clientList){
			for (OrgRelations orgRelations : clientList) {
				String orgName = "";
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgName = orgRelations.getLocationId().getOrgLocationName();
				}if(orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgName = orgRelations.getResponderLocationId().getOrgLocationName();
				}
				orgRelations.setOrgName(orgName);
			}
		}
		return commonUtil.toJSON(clientList);
	} 
	
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	
	

	@RequestMapping(value = "/createInvoiceProjectBased")
	public ModelAndView createInvoiceProjectBased(HttpServletRequest request,@RequestParam(value="orgRelationId", required=false) Long orgRelationId) throws Exception {
		
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
		
		Date currentDate = new Date();
		String convertedDate = Constants.DMY.format(currentDate);
		model.addObject("currentDate", convertedDate);
		model.setViewName("billing/createProjectReceivableInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}

	
	@RequestMapping(value = "/createInvoiceProjectPayableBased")
	public ModelAndView createInvoiceProjectPayableBased(@RequestParam(value="orgRelationId", required=false) Long orgRelationId,HttpServletRequest request) throws Exception {
		
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
		Date currentDate = new Date();
		String convertedDate = Constants.DMY.format(currentDate);
		model.addObject("currentDate", convertedDate);
		model.setViewName("billing/createProjectPayableInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}

}
