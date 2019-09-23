package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.InvoiceLineItemService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.InvoiceTaxService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.InvoiceDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class BillingPayableRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(BillingPayableRestController.class);
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	InvoiceLineItemService invoiceLineItemService;
	
	@Autowired
	InvoiceDocumentsService invoiceDocumentsService;
	
	@Autowired
	InvoiceTaxService invoiceTaxService;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	private UserService userService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	
	 @RequestMapping(value = "/getVendorInvoiceAssociatesList", method = RequestMethod.GET)
	 	public @ResponseBody  String getVendorInvoiceAssociatesList(@RequestParam(value = "selectedAssociates", required = false) String selectedAssociates,
	 			@RequestParam(value = "vendorOrgRelationId", required = false) Long vendorOrgRelationId,
	 			@RequestParam(value = "contractType", required = false) String contractType,
	 			HttpServletRequest request, HttpServletResponse response) throws Exception
	 	{
	     	String resultJson;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	     	List<Long> associateIds = new ArrayList<Long>();
	     	List<Candidate> associateList = null;
	     	if(!isNullOrEmpty(selectedAssociates)){
	     		String[] selectedAssoicatesIds =  selectedAssociates.split(",");
	         	for(int i=0; i < selectedAssoicatesIds.length; i++){
	         		associateIds.add(Long.parseLong(selectedAssoicatesIds[i]));
	         	}
	         	associateList = candidateService.getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(vendorOrgRelationId,associateIds);
	     	}else{
	     		associateList = candidateService.getAllActiveVendorAssociatesByOrgLocation(vendorOrgRelationId);
	     	}
	     	
	     	for (Candidate candidate : associateList) {
				List<Contracts> contractsList = getSowContract(candidate.getId(), loginOrgLocation, vendorOrgRelationId, contractType);
				if (null != contractsList && contractsList.size() > 0) {
					candidate.setContractsList(contractsList);
				}
			}
	     	
	 	    resultJson = commonUtil.toJSON(associateList);
	 	    return resultJson;
	 	}
	 
	 
	 
	 @RequestMapping(value = "/getInvoiceDetailsByInvoiceUUID")
	 public String getInvoiceDetailsByInvoiceUUID(@RequestParam(value="invoiceUUID", required=false) String invoiceUUID,HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    String resultJson;
		 
		    Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		  //SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
		    if(null != invoice){
		    	if(invoice.getPaymentAccount() != null) {
		    		invoice.setPaymentAccountId(invoice.getPaymentAccount().getId());
		    	}
		    	invoice.setVendorOrgRelationId(invoice.getOrgRelationID().getId());
		    	
		    	if(invoice.getOrgRelationID().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					invoice.setOrgName(invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName());
					invoice.setGenerateInvoice(invoice.getOrgRelationID().getGenerateInvoice());
					invoice.setGenerateBill(invoice.getOrgRelationID().getGenerateBill());
					invoice.setDataResponderLocationId(invoice.getOrgRelationID().getResponderLocationId().getId());
				}
				if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					invoice.setOrgName(invoice.getOrgRelationID().getLocationId().getOrgLocationName());
					invoice.setGenerateInvoice(invoice.getOrgRelationID().getGenerateInvoice());
					invoice.setGenerateBill(invoice.getOrgRelationID().getGenerateBill());
					invoice.setDataResponderLocationId(invoice.getOrgRelationID().getLocationId().getId());
				}
				/*if(invoice.getContactUser() != null){
					//invoice.setVendorContactId(invoice.getContactUser().getId());
					invoice.setVendorContactId(invoice.getContactUser().getId());
				}*/
		    	invoice.setInvoiceId(invoice.getInvoiceId());
		    	if(invoice.getInvoiceDate() != null){
		    		invoice.setInvoiceDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),invoice.getInvoiceDate()));	
		    	}
		    	if(invoice.getDueDate() != null){
		    		invoice.setDueDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),invoice.getDueDate()));	
		    	}
		    	
		    	if(invoice.getParentInvoiceId() != null) {
		    		invoice.setInvoiceParentId(invoice.getParentInvoiceId().getId());
		    	}else
		    		invoice.setInvoiceParentId(null);
		    	List<InvoiceLineItems> invoiceLineItemsList = invoiceLineItemService.getInvoiceLineItemsByInvoiceId((invoice.getParentInvoiceId() != null)?invoice.getParentInvoiceId().getId():invoice.getId());
		    	List<InvoiceLineItems> lineItemsList = new ArrayList<InvoiceLineItems>();
		    	
		    	for(InvoiceLineItems lineItems : invoiceLineItemsList){
		    		StringBuilder sb = new StringBuilder();
		    		if(lineItems.getCandidate() !=null){
		    			Candidate candidate = candidateService.getCandidateByCandidateId(lineItems.getCandidate().getId());
		    			if(null != candidate.getLastName()){
			    			sb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
		    			  }else{
			    			sb.append(candidate.getFirstName());
		    			 }
		    			lineItems.setCandidateId(lineItems.getCandidate().getId());
		    			
		    			if(lineItems.getRequirementSubmissionId() !=null){
		    				lineItems.setSubmissionId(lineItems.getRequirementSubmissionId().getId());	
		    			}
		    			
		    			if(null != lineItems.getContract()){
			    			lineItems.setContractId(lineItems.getContract().getId());
			    			lineItems.setSowContractName(lineItems.getContract().getContractTitle());
			    			String contractType = "";
			    			if(Constants.RECEIVABLE.equalsIgnoreCase(invoice.getInvoiceType())){
			    				contractType = "clientSow";
			    			}else{
			    				contractType = "vendorSow";
			    			}
			    			List<Contracts> contractList = getSowContract(candidate.getId(),lineItems.getOrgLocation(),invoice.getOrgRelationID().getId(),"contractType");
			    			lineItems.setContractList(contractList);
			    			//if(null != contractList && contractList.size() >0){
//								projectMember.setContractsList(contractList);
//							}
			    		}
		    		}else{
		    			if(lineItems.getUser() !=null) {
		    				User user = userService.getUserById(lineItems.getUser().getId());
			    			if(null != user.getLastName()){
				    			sb.append(user.getFirstName()).append(" ").append(user.getLastName());
			    			  }else{
				    			sb.append(user.getFirstName());
			    			 }
			    			lineItems.setUserId(lineItems.getUser().getId());
		    			}
		    		}
		    		
		    		
		    		if(null != lineItems.getStartDate()){
		    			lineItems.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lineItems.getStartDate()));
		    		}
		    		
		    		if(null != lineItems.getEndDate()){
		    			lineItems.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lineItems.getEndDate()));
		    		}
		    		
		    		lineItems.setCandidateName(sb.toString());
		    		
		    	
		    		lineItemsList.add(lineItems);
		    	}
		    	if(null != invoice.getProjectID()){
		    		invoice.setProjectName(invoice.getProjectID().getProjectName());
		    		invoice.setInvoiceFrequency(invoice.getInvoiceFrequency());
		    		invoice.setProjectId(String.valueOf(invoice.getProjectID().getId()));
		    	}
		    	if(invoice.getProjectMemberId() !=null) {
		    		StringBuilder sb = new StringBuilder();
		    		if(invoice.getProjectMemberId().getMasterCandidateId().getLastName() != null) {
		    			sb.append(invoice.getProjectMemberId().getMasterCandidateId().getFirstName()).append(invoice.getProjectMemberId().getMasterCandidateId().getLastName());
		    		}else {
		    			sb.append(invoice.getProjectMemberId().getMasterCandidateId().getFirstName());	
		    		}
		    		invoice.setProjectMemberName(sb.toString());
		    		invoice.setMemberId(String.valueOf(invoice.getProjectMemberId().getId()));
		    	}
		    	if(invoice.getExpensesId() !=null) {
		    		StringBuilder sb = new StringBuilder();
		    		String startDate  = conversionUtil.getDateAsString(invoice.getExpensesId().getStartDate());
					String endDate  = conversionUtil.getDateAsString(invoice.getExpensesId().getEndDate());
					sb.append(startDate).append(" / ").append(endDate);
		    		invoice.setExpensesFrequency(sb.toString());
		    		invoice.setExpensesInvoices(invoice.getExpensesInvoices());
		    		invoice.setExpId(String.valueOf(invoice.getExpensesId().getId()));
		    	}
		    	
		    	invoice.setInvoiceLineItemsList(lineItemsList);
		    	
		    	List<InvoiceDocument> invoiceDocumentsList = invoiceDocumentsService.getInvoiceDocumentsListByInvoiceId(invoice.getId());
		    	invoice.setInvoiceDocumentsList(invoiceDocumentsList);
		    	
		    	List<InvoiceTax> invoiceTaxList = null;
		    	if(invoice.getParentInvoiceId() != null)
		    	 invoiceTaxList = invoiceTaxService.getInvoiceTaxListByInvoiceId(invoice.getParentInvoiceId().getId());
		    	else
		    	 invoiceTaxList = invoiceTaxService.getInvoiceTaxListByInvoiceId(invoice.getId());
		    	
		    	invoice.setInvoiceTaxList(invoiceTaxList);
		    	
		    	String currency = "";
		    	if(invoice.getOrgRelationID().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
		    		currency = conversionUtil.getVendorOrgCurrencyCode(invoice.getOrgRelationID().getResponderLocationId().getCountry());
				}
				if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					currency = conversionUtil.getVendorOrgCurrencyCode(invoice.getOrgRelationID().getLocationId().getCountry());
				}
				
		    	invoice.setClientCurrency(currency);
		    	invoice.setVendorCurrency(currency);
		    	
		    	if(invoice.getCustomInvoiceTemplate() != null) {
		    		invoice.setCustomInvoiceId(invoice.getCustomInvoiceTemplate().getId());
		    		invoice.setCustomInvoiceName(invoice.getCustomInvoiceTemplate().getName());
		    	}
		    	
		    }
		    resultJson = commonUtil.toJSON(invoice);
			
			return resultJson;
	 }
	 
	/* @RequestMapping(value = "/downloadInvoiceDocument", method = RequestMethod.GET)
	    public void downloadInvoiceDocument(@RequestParam(value="documentId", required=false) Long documentId ,HttpServletResponse response) throws Exception {
			
			
				InvoiceDocument	document =	invoiceDocumentsService.getInvoiceDocumentById(documentId);	
			    
			    response.setContentType("application/octet-stream");
			    StringBuilder fileName = new StringBuilder();
		        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(document.getFileName()).append("\"").toString());
				InputStream inputStream = new ByteArrayInputStream(document.getDocFile());
				try {
					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();

				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				} 

		} */ 
	 
	 
	 @RequestMapping(value = "/deleteInvoiceDocumentBydocumentId")
	 public String deleteInvoiceDocumentBydocumentId(@RequestParam(value="documentId", required=false) Long documentId,HttpServletRequest request) throws Exception {

		 InvoiceDocument document =	invoiceDocumentsService.getInvoiceDocumentById(documentId);	
		 if(null != document){
			 document.setStatus("0");
			 invoiceDocumentsService.saveInvoiceDocument(document);
		 }
		 
		 
		 return "success";
	 }
	
	 
	 @RequestMapping(value = "/deleteInvoiceLineItem")
	 public String deleteInvoiceLineItem(@RequestParam(value="lineItemId", required=false) Long lineItemId,
			 @RequestParam(value="invoiceTotal", required=false) String invoiceTotal,
			 @RequestParam(value="discountTotal", required=false) String discountTotal,
			 @RequestParam(value="afterDiscountToatal", required=false) String afterDiscountToatal,
			 @RequestParam(value="grandTotal", required=false) String grandTotal,
			 HttpServletRequest request) throws Exception {

		 InvoiceLineItems lineItem = invoiceLineItemService.getInvoiceLineItemsById(lineItemId);	
		 if(null != lineItem){
			 
			 Invoice invoice = invoiceService.getInvoicetById(lineItem.getInvoice().getId());
			 invoice.setSubTotal(invoiceTotal);
			 invoice.setDiscountValue(discountTotal);
			 invoice.setAfterDiscountValue(afterDiscountToatal);
			 invoice.setTotal(grandTotal);
			 invoiceService.updateInvoice(invoice);
			 
			 lineItem.setStatus("0");
			 invoiceLineItemService.saveInvoiceLineItems(lineItem);
		 }
		 
		 return "success";
	 }
	 
	 
	 private List<Contracts> getSowContract(Long candidateId, OrgLocations orgLocation, Long orgRelationId, String contractType) throws Exception
		{
			List<Contracts> contractsList = null;
	     	if(null != orgRelationId){
	     		if(Constants.VENDOR_SOW.equalsIgnoreCase(contractType)){
	     			 contractsList = invoiceService.getSowcontractListByAssociateId(candidateId,orgLocation.getId(),orgRelationId,Constants.Vendor_SOW);
	     		}else{
	     			contractsList = invoiceService.getSowcontractListByAssociateId(candidateId,orgLocation.getId(),orgRelationId,Constants.Client_SOW);	
	     		}
	     		 	
//	     		if(null != contractsList && contractsList.size() >0){
//	         		resultJson = commonUtil.toJSON(contractsList);	
//	         	}
	     	}
			return contractsList;
			
		}
	    

}
