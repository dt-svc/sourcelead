package com.bis.sourcelead.web;

import java.awt.GraphicsEnvironment;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CreditDebitNoteListDTO;
import com.bis.sourcelead.dto.CustomInvoiceTemplateDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.CreditDebitNote;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.InvoiceDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.log.SysoCounter;

@Controller
public class InvoiceController {
	private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${system.default.font}")
	private String systemDefaultFont;
	
	@Autowired
	 CandidateService candidateService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
        
    @Autowired
	private OrgService orgService;
	
	@Autowired
	CommonUtil commonUtil;
	 
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	private InvoiceService invoiceService;
	 
	@Autowired
	private InvoiceDocumentsService invoiceDocumentsService;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SourceleadTagsService sourceleadTagsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
/**
 * @author Laxman
 * @param type
 * @param request
 * @return model
 * @throws Exception
 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_INVOICE','ROLE_VIEW_EDIT_INVOICE')")
	@RequestMapping(value = "/createInvoice")
	public ModelAndView invoiceList(@RequestParam(value="type", required=false) String type , @RequestParam(value="orgRelationId", required=false) Long orgRelationId ,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
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
		//SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
		model.setViewName("billing/createInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		Date currentDate = new Date();
		String convertedDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),currentDate);
        model.addObject("paymentAccountList", orgService.getPaymentAccountList(org.getId(), orgLocations.getId(),true));
		model.addObject("currentDate", convertedDate);
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	
	
	@RequestMapping(value = "/addPaymentInvoice")
	public ModelAndView addPayment(HttpServletRequest request) throws Exception{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		model.setViewName("billing/addPaymentInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
		for(OrgRelations vendors:orgClientList){
			Long responderLocationId = 0L;
			if(vendors.getLocationId().getId().equals(orgLocations.getId())){
				vendors.setOrgName(vendors.getResponderLocationId().getOrgLocationName());
				responderLocationId = vendors.getResponderLocationId().getId();
			}
			if(vendors.getResponderLocationId().getId().equals(orgLocations.getId())){
				vendors.setOrgName(vendors.getLocationId().getOrgLocationName());
				responderLocationId = vendors.getLocationId().getId();
			}
			vendors.setResponderId(responderLocationId + "");
			
		}
		model.addObject("clientsList", orgClientList);
		return model;
		
	}
	
	@RequestMapping(value = "/addCreditNote")
	public ModelAndView addCreditNote(HttpServletRequest request,
			@RequestParam(value="creditDebitUUID", required=false)String creditDebitUUID) throws Exception{
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLocId);
		ModelAndView model = new ModelAndView();
		model.setViewName("billing/addCreditNote");
		model.addObject("sourceleadUrl", sourceleadUrl);
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
		List<User> approversList = userService.getOwnersByOrgLocationId(orgLocations.getId());
		orgClientList.forEach(clients->{
			if(clients.getLocationId().getId().equals(orgLocations.getId())){
				clients.setOrgName(clients.getResponderLocationId().getOrgLocationName());
			}
			if(clients.getResponderLocationId().getId().equals(orgLocations.getId())){
				clients.setOrgName(clients.getLocationId().getOrgLocationName());
			}
		});
		if(StringUtils.isNotBlank(creditDebitUUID)){
			CreditDebitNote creditDebitNote = invoiceService.getCreditDebitNoteByUUID(creditDebitUUID);
			CreditDebitNoteListDTO creditDebitNoteListDTO = invoiceService.editCreditDebitNote(creditDebitNote.getId(), orgLocations, null);
			model.addObject("creditDebitNote", creditDebitNoteListDTO);
		}
		model.addObject("clientsList", orgClientList);
		model.addObject("paymentAccountList", orgService.getPaymentAccountList(orgLocations.getOrg().getId(), orgLocations.getId(),true));
		model.addObject("approversList",approversList);
		return model;
		
	}
	
	@RequestMapping(value = "/addDebitNote")
	public ModelAndView addDebitNote(HttpServletRequest request,
			@RequestParam(value="creditDebitUUID", required=false)String creditDebitUUID) throws Exception{
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLocId);
		ModelAndView model = new ModelAndView();
		model.setViewName("billing/addDebitNote");
		model.addObject("sourceleadUrl", sourceleadUrl);
		Collection<OrgRelations> orgVendorsList =orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId()); 
		List<User> approversList = userService.getOwnersByOrgLocationId(orgLocations.getId());
		orgVendorsList.forEach(vendors->{
			if(vendors.getLocationId().getId().equals(orgLocations.getId())){
				vendors.setOrgName(vendors.getResponderLocationId().getOrgLocationName());
			}
			if(vendors.getResponderLocationId().getId().equals(orgLocations.getId())){
				vendors.setOrgName(vendors.getLocationId().getOrgLocationName());
			}
		});
		if(StringUtils.isNotBlank(creditDebitUUID)){
			CreditDebitNote creditDebitNote = invoiceService.getCreditDebitNoteByUUID(creditDebitUUID);
			CreditDebitNoteListDTO creditDebitNoteListDTO = invoiceService.editCreditDebitNote(creditDebitNote.getId(), orgLocations, null);
			model.addObject("creditDebitNote", creditDebitNoteListDTO);
		}
		model.addObject("vendorsList", orgVendorsList);
		model.addObject("paymentAccountList", orgService.getPaymentAccountList(orgLocations.getOrg().getId(), orgLocations.getId(),true));
		model.addObject("approversList",approversList);
		return model;
		
	}
	
	@RequestMapping(value = "/addPaymentBill")
	public ModelAndView addPaymentBill(HttpServletRequest request) throws Exception{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		model.setViewName("billing/addPaymentBill");
		model.addObject("sourceleadUrl", sourceleadUrl);
		Collection<OrgRelations> vendorsList =orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId()); 
		for(OrgRelations vendors:vendorsList){
			if(vendors.getLocationId().getId().equals(orgLocations.getId())){
				vendors.setOrgName(vendors.getResponderLocationId().getOrgLocationName());
			}
			if(vendors.getResponderLocationId().getId().equals(orgLocations.getId())){
				vendors.setOrgName(vendors.getLocationId().getOrgLocationName());
			}
			
		}
		model.addObject("vendorsList", vendorsList);
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
		
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLocId);
		ModelAndView model = new ModelAndView();
		model.addObject("paymentAccountList", orgService.getPaymentAccountList(orgLocations.getOrg().getId(), orgLocations.getId(),true));

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
		
		Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		if(invoice.getParentInvoiceId() != null) {
			List<InvoiceDocument> docs = invoiceDocumentsService.getInvoiceDocumentsListByInvoiceId(invoice.getParentInvoiceId().getId());
			model.addObject("documents" , docs);
		}
		if(invoice.getInvoiceCategory() !=null && Constants.EXPENSES.equalsIgnoreCase(invoice.getInvoiceCategory())) {
			model.setViewName("billing/createExpensesReceivableInvoice");
		}else if((invoice.getInvoiceCategory() !=null && Constants.EXPENSES.equalsIgnoreCase(invoice.getInvoiceCategory())) && invoice.getProjectID() !=null) {
			model.setViewName("billing/createExpensesReceivableInvoice");
		}else if(projectId !=null && !"".equalsIgnoreCase(projectId) && !"null".equalsIgnoreCase(projectId) && !"undefined".equalsIgnoreCase(projectId) && invoice.getInvoiceCategory() ==null){
			model.setViewName("billing/createProjectReceivableInvoice");
		}else if(invoice.getIsJobClosure() !=null && invoice.getIsJobClosure().equals("Y")){
			model.setViewName("billing/createInvoiceForJobClosure");
		}else{
			model.setViewName("billing/createInvoice");
		}
		model.addObject("isGenerateAutomaticInvoice",invoice.getIsAutomaticGenerateInvoice());
		model.addObject("isDraft",invoice.getId());
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
//	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_INVOICE')")
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
	
	@RequestMapping(value = "/viewCreditDebitNote")
	public ModelAndView viewCreditDebitNote(@RequestParam(value="creditDebitUUID", required=false) String creditDebitUUID,
			@RequestParam(value="noteType", required=false) String noteType,HttpServletRequest request) throws Exception {
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
		if(noteType.equalsIgnoreCase("Credit")){
			sessionData.setNav(Constants.CREDIT_NOTE);
		}else{
			sessionData.setNav(Constants.DEBIT_NOTE);
		}
		model.setViewName("billing/viewCreditDebitNote");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("creditDebitUUID", creditDebitUUID);
		model.addObject("noteType", noteType);
		
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADD_INVOICE')")
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
		
		List<String> invoiceTagsList = sourceleadTagsService.sourceleadTagsListByEntitypeAndOrgloactionId(TagEntites.INVOICES.toString(), orgLocations.getId());
        model.addObject("invoiceTagsList", invoiceTagsList);
        
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	@RequestMapping(value = "/creditNoteList")
	public ModelAndView creditNoteList(@RequestParam(value="type", required=false) String type ,HttpServletRequest request) throws Exception {
		
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
		model.setViewName("billing/creditNoteList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		request.getSession().setAttribute("sessionData",sessionData);
		
	return model;
	}
	
	@RequestMapping(value = "/debitNoteList")
	public ModelAndView debitNoteList(@RequestParam(value="type", required=false) String type ,HttpServletRequest request) throws Exception {
		
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
		model.setViewName("billing/debitNoteList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		request.getSession().setAttribute("sessionData",sessionData);
		
	return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CLIENT','ROLE_VIEW_CLIENT','ROLE_VIEW_EDIT_CLIENT','ROLE_CLIENT_BULK_IMPORTS','ROLE_CREATE_CLIENT_CONTACT','ROLE_VIEW_CLIENT_CONTACT','ROLE_VIEW_EDIT_CLIENT_CONTACT')")
	@RequestMapping(value = "/getClientList", method = RequestMethod.GET)
	public @ResponseBody ResponseList<OrgRelations> getClientList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		ResponseList<OrgRelations> responseList = new ResponseList<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
		if(null != clientList){
			for (OrgRelations orgRelations : clientList) {
				String orgName = "";
				Long responderLocationId = 0L;
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgName = orgRelations.getLocationId().getOrgLocationName();
					responderLocationId = orgRelations.getLocationId().getId();
					orgRelations.setClientCurrency(conversionUtil.getVendorOrgCurrencyCode(orgRelations.getLocationId().getCountry()));
				}if(orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgName = orgRelations.getResponderLocationId().getOrgLocationName();
					responderLocationId = orgRelations.getResponderLocationId().getId();
					orgRelations.setClientCurrency(conversionUtil.getVendorOrgCurrencyCode(orgRelations.getResponderLocationId().getCountry()));
				}
				orgRelations.setOrgName(orgName);
				orgRelations.setResponderId(responderLocationId + "");
			}
		}
//		return commonUtil.toJSON(clientList);
		responseList.setData(clientList);
		return responseList;
	} 
	
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADD_INVOICE')")
	@RequestMapping(value = "/createInvoiceProjectBased")
	public ModelAndView createInvoiceProjectBased(HttpServletRequest request,@RequestParam(value="orgRelationId", required=false) Long orgRelationId) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
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
		String convertedDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),currentDate);
        model.addObject("paymentAccountList", orgService.getPaymentAccountList(org.getId(), orgLocations.getId(),true));
		model.addObject("currentDate", convertedDate);
		model.setViewName("billing/createProjectReceivableInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADD_INVOICE')")
	@RequestMapping(value = "/createInvoiceProjectPayableBased")
	public ModelAndView createInvoiceProjectPayableBased(@RequestParam(value="orgRelationId", required=false) Long orgRelationId,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
	//	SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
		
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
		String convertedDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),currentDate);
		model.addObject("currentDate", convertedDate);
		model.setViewName("billing/createProjectPayableInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	/**
	 * returns jsp to create expenses based invoice in receivables 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/createReceivableExpensesBill")
	public ModelAndView createReceivableExpenseBill(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLocId);
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		model.setViewName("billing/createExpensesReceivableInvoice");
		model.addObject("currentDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));
		model.addObject("sourceleadUrl", sourceleadUrl);
                model.addObject("paymentAccountList", orgService.getPaymentAccountList(orgLocations.getOrg().getId(), orgLocations.getId(),true));
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	/**
	 * returns jsp to create expenses based bill in payables 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createExpensesPayableBill")
	public ModelAndView createExpensesPayableBill(HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		model.setViewName("billing/createExpensesPayableBill");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("currentDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	@RequestMapping(value = "/createInvoiceForJobClosure")
	public ModelAndView createInvoiceForJobClosure(HttpServletRequest request,@RequestParam(value="orgRelationId", required=false) Long orgRelationId) throws Exception {
		
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLocId);
		ModelAndView model = new ModelAndView();
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		//SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
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
		String convertedDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),currentDate);
        model.addObject("paymentAccountList", orgService.getPaymentAccountList(orgLocations.getOrg().getId(), orgLocations.getId(),true));
		model.addObject("currentDate", convertedDate);
		model.setViewName("billing/createInvoiceForJobClosure");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Receivable);
		request.getSession().setAttribute("sessionData",sessionData);
	return model;
	}
	
	 /*@RequestMapping(value = "/viewInvoiceDocView/{title}", method = RequestMethod.GET)
	    public void viewInvoiceDocView(@RequestParam(value="documentId", required=false) String documentId,
	    		@PathVariable(value="title") String title
	    		,HttpServletResponse response) throws Exception {
		 
		  InvoiceDocument invoiceDocument = invoiceDocumentsService.getInvoiceDocumentById(Long.parseLong(documentId));
		  
		  byte[] pdfBytes = null;
			if("application/pdf".equalsIgnoreCase(invoiceDocument.getFileContentType())){
				pdfBytes = invoiceDocument.getDocFile();
			}
			else{
				 pdfBytes = pdfConversionUtil.convertDocToPdf(invoiceDocument.getDocFile());
			}
			 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
		  	    response.setContentType("application/pdf");
		  	    ServletOutputStream sos = response.getOutputStream();
		  	    if(null != pdfBytes){
		  	    	response.setContentLength(pdfBytes.length);
		  	    	sos.write(pdfBytes);
		  	    }
		          sos.flush();
		          sos.close();

		}*/ 
	@RequestMapping(value = "/customizeInvoice")
	public ModelAndView customizeInvoice(HttpServletRequest request,
			@RequestParam(defaultValue="0") Long orgLocationId,
			@RequestParam(defaultValue="0") Long orgRelationId) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null) {
				modelAndView.addObject("sourceleadUrl", sourceleadUrl);
//				modelAndView.addObject("invoiceTemplateList", invoiceService.getCustomInvoiceTemplateListByOrgRelationIdAndOrgLocationId(orgRelationId, orgLocationId));
				modelAndView.addObject("orgLocationId", orgLocationId);
				modelAndView.addObject("orgRelationId", orgRelationId);
				modelAndView.setViewName("billing/customInvoiceList");
			} else {
				modelAndView.setViewName("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping(value = { "/customInvoice/addTemplate", "/customInvoice/editTemplate" })
	public ModelAndView addInvoice(HttpServletRequest request, @RequestParam(defaultValue = "0") Long orgLocationId,
			@RequestParam(defaultValue = "0") Long orgRelationId, @RequestParam(defaultValue = "") String uuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loggedInOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				modelAndView.addObject("sourceleadUrl", sourceleadUrl);
				modelAndView.addObject("orgLocationId", orgLocationId);
				modelAndView.addObject("orgRelationId", orgRelationId);
				if (request.getRequestURI().contains("editTemplate")) {
					modelAndView.addObject("invoiceTemplate", invoiceService.getCustomInvoiceTemplateListByUUIDAndOrgRelationIdAndOrgLocationId(uuid, orgRelationId, orgLocationId, loggedInOrgLocations));
					// modelAndView.addObject("invoiceTemplate", invoiceService.getCustomInvoiceTemplateListByOrgRelationIdAndOrgLocationId(orgRelationId, orgLocationId));
				}
				
				List<String> fontList = new LinkedList<>();
				GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
				for(String font : g.getAvailableFontFamilyNames()) {
					fontList.add(font);
				}
				modelAndView.addObject("fonts", fontList);
				modelAndView.addObject("systemDefaultFont", systemDefaultFont);
				modelAndView.setViewName("billing/customInvoice");
			} else {
				modelAndView.setViewName("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
/*	@RequestMapping(value="/getAvailableFonts")
	@ResponseBody
	public void getAvailableSystemFonts(HttpServletRequest request) {
		try {
			System.out.println("Available Fonts");
			GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
			for(String font : g.getAvailableFontFamilyNames()) {
				System.out.println(font);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
