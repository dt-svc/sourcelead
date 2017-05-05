package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.InvoicePaymentService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.mchange.v2.cfg.PropertiesConfigSource.Parse;

@RestController
public class InvoiceRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceRestController.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private InvoicePaymentService invoicePaymentService;
	
	@RequestMapping(value = "/getClientContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody String getVendorContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
		
		for(OrgSpoc orgSpoc : orgSpocList){
			StringBuilder sb = new StringBuilder();
			StringBuilder clientSb = new StringBuilder();
			if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()){
			sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
			}else{
			sb.append(orgSpoc.getVendorUser().getFirstName());
			}
			if(null != orgSpoc.getClientUser().getFirstName() && null != orgSpoc.getClientUser().getLastName()){
			clientSb.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName());
			}else{
			clientSb.append(orgSpoc.getClientUser().getFirstName());
			}
			orgSpoc.setSpocName(sb.toString());
			orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
			orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
			orgSpoc.setOwnerName(clientSb.toString());
			}
		return commonUtil.toJSON(orgSpocList);
	} 
	
	
	@RequestMapping(value = "/saveInvoice", method = RequestMethod.POST)
	public @ResponseBody String saveClient(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "saveType", required = false) String saveType,MultipartHttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		String invoiceSaved = "failed";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<MultipartFile> attachedfileList = new ArrayList<MultipartFile>();
		int attcahmentMaxRowNumber = Integer.parseInt(jsonObj.getString("invoiceAttcahmentMaxRowNumber"));
		 final Map files = request.getFileMap();
		for(int i=0;i<attcahmentMaxRowNumber;i++){
			MultipartFile  multiFile = (MultipartFile)files.get(i+"invoicedocFile");
			if(multiFile != null && !multiFile.isEmpty()){
				attachedfileList.add(multiFile); 
			}
		}
		Invoice invoice = invoiceService.saveInvoice(jsonObj,user,orgLocation,saveType,attachedfileList);
		if(null !=invoice){
			invoiceSaved = "success";
		}
		return invoiceSaved;
	} 
	
    @RequestMapping(value = "/getAssociatesList", method = RequestMethod.GET)
 	public @ResponseBody  String getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
     	String resultJson;
     	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	List<Candidate> associateList = candidateService.getAllActiveAssociatesByLocation(loginOrgLocation.getId());
 	  resultJson = commonUtil.toJSON(associateList);
 	  return resultJson;
 	}
    
//    /getClientProjectsByOrgRelationId 
    
    @RequestMapping(value = "/getClientProjectsByOrgRelationId", method = RequestMethod.GET)
 	public @ResponseBody  String getUserList(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
     	String resultJson;
     	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	List<Projects> projectsList = projectsService.getClientProjectsByOrgRelationId(orgRelationId,loginOrgLocation.getId());
 	  resultJson = commonUtil.toJSON(projectsList);
 	  return resultJson;
 	}
    
    @RequestMapping(value = "/getSowcontractListByAssociateId", method = RequestMethod.GET)
 	public @ResponseBody  String getSowcontractListByAssociateId(@RequestParam(value = "candidateId", required = false) Long candidateId,
 			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
     	String resultJson = "";
     	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	if(null != orgRelationId){
     		List<Contracts> contractsList = invoiceService.getSowcontractListByAssociateId(candidateId,loginOrgLocation.getId(),orgRelationId);	
     		if(null != contractsList){
         		resultJson = commonUtil.toJSON(contractsList);	
         	}
     	}
     	
     	
 	  
 	  return resultJson;
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
			invoice.setDueDateStr(Constants.DF_DMY.format(invoice.getDueDate()));
			invoice.setInvoiceDateStr(Constants.DF_DMY.format(invoice.getInvoiceDate()));
			invoice.setTotal(invoice.getTotal());
			invoice.setInvoiceStatus(invoice.getInvoiceStatus());
			invoice.setPaymentStatus(invoice.getPaymentStatus());
			
		}
	    resultJson = commonUtil.toJSON(invoiceList);
	  return resultJson;
	}
	
    
	@RequestMapping(value = "/saveInvoicePayment", method = RequestMethod.POST)
	public @ResponseBody String saveInvoicePayment(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "invoiceId", required = false) Long invoiceId,MultipartHttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		String invoicePaymentSaved = "failed";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		InvoicePayment invoicePayment = invoicePaymentService.saveInvoicePayment(jsonObj, user, orgLocation, multiFile,invoiceId);
		if(null !=invoicePayment){
			invoicePaymentSaved = "success";
		}
		return invoicePaymentSaved;
	} 
    
}
