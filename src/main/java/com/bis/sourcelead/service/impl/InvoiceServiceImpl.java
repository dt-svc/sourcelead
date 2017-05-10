package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dao.InvoiceLineItemDao;
import com.bis.sourcelead.dao.InvoiceTaxDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class InvoiceServiceImpl implements InvoiceService{
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	InvoiceLineItemDao invoiceLineItemDao;
	
	@Autowired
	InvoiceTaxDao invoiceTaxDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override

	public List<Invoice> invoiceListByOrgLocationId(Long orgLocationId,String invoiceStatus, String paymentStatus,Date startDateUTC,Date endDateUTC,String invoiceType) throws Exception {
		return invoiceDao.invoiceListByOrgLocationId(orgLocationId,invoiceStatus,paymentStatus,startDateUTC,endDateUTC,invoiceType);

	}
	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType) throws Exception
	{
		return invoiceDao.getSowcontractListByAssociateId(candidateId,orgLocationId,orgRelationId,contractType);
	}

	@Override
	public Invoice saveInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation,String saveType,List<MultipartFile> attachedfileList,String invoiceType) throws Exception {
		
		
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
        Invoice invoice = null;
        
        if(jsonObj.has("savedInvoiceId") && !isNullOrEmpty(jsonObj.getString("savedInvoiceId"))){
        	invoice = invoiceDao.getInvoicetById(Long.valueOf(jsonObj.getString("savedInvoiceId")));
        }else{
        	invoice = new Invoice();
        	invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
        }
        
		
        if(invoiceType.equalsIgnoreCase("payable")){
        	
        	 
        	if(jsonObj.has("vendorOrgRelationId") && !"".equalsIgnoreCase(jsonObj.getString("vendorOrgRelationId"))){
      			
      			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("vendorOrgRelationId")));
      			invoice.setOrgRelationID(orgRelation);	
      		}
      		
      		if(jsonObj.has("vendorContact") && !"".equalsIgnoreCase(jsonObj.getString("vendorContact"))){
      			User contactUser = userService.getUserById(Long.parseLong(jsonObj.getString("vendorContact")));
      			invoice.setContactUser(contactUser);
      		}
      		
      		invoice.setInvoiceType(Constants.Payables);
        
        
        }else{
        	 if(jsonObj.has("clientOrgRelationId") && !"".equalsIgnoreCase(jsonObj.getString("clientOrgRelationId"))){
     			
     			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("clientOrgRelationId")));
     			invoice.setOrgRelationID(orgRelation);	
     		}
     		
     		if(jsonObj.has("clientContact") && !"".equalsIgnoreCase(jsonObj.getString("clientContact"))){
     			User userContactName = userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
     			invoice.setContactUser(userContactName);
     		}
     		
     		invoice.setInvoiceType(Constants.Receivable);
     		
        }
        
       
		invoice.setOrgLocation(orgLocation);
		if(jsonObj.has("invoiceId") && !"".equalsIgnoreCase(jsonObj.getString("invoiceId"))){
			invoice.setInvoiceId(jsonObj.getString("invoiceId"));
		}
		if(jsonObj.has("billingAddress") && !"".equalsIgnoreCase(jsonObj.getString("billingAddress"))){
			invoice.setBillingAddress(jsonObj.getString("billingAddress"));
		}else{
			invoice.setBillingAddress(null);
		}
		
		if(jsonObj.has("invoiceDate") && !"".equalsIgnoreCase(jsonObj.getString("invoiceDate"))){
			Date invoiceDate = format.parse(jsonObj.getString("invoiceDate"));
			invoice.setInvoiceDate(invoiceDate);
		}
		if(jsonObj.has("dueDate") && !"".equalsIgnoreCase(jsonObj.getString("dueDate"))){
			Date dueDate = format.parse(jsonObj.getString("dueDate"));
			invoice.setDueDate(dueDate);
		}
		
		if(jsonObj.has("paymentFrequency") && !"".equalsIgnoreCase(jsonObj.getString("paymentFrequency"))){
			invoice.setPaymentFrequency(jsonObj.getString("paymentFrequency"));
		}else{
			invoice.setPaymentFrequency(null);
		}
		
		if(jsonObj.has("disCount") && !"".equalsIgnoreCase(jsonObj.getString("disCount"))){
			invoice.setDiscountType(jsonObj.getString("disCount"));;
		}
		
		if(jsonObj.has("discountValue") && !"".equalsIgnoreCase(jsonObj.getString("discountValue"))){
			invoice.setDiscountAmount(jsonObj.getString("discountValue"));;
		}
		if(jsonObj.has("discountTotal") && !"".equalsIgnoreCase(jsonObj.getString("discountTotal"))){
			invoice.setDiscountValue(jsonObj.getString("discountTotal"));;
		}
		if(jsonObj.has("inoviceTotal") && !"".equalsIgnoreCase(jsonObj.getString("inoviceTotal"))){
			invoice.setSubTotal(jsonObj.getString("inoviceTotal"));;
		}
		
		if(jsonObj.has("comment") && !"".equalsIgnoreCase(jsonObj.getString("comment"))){
			invoice.setInvoiceComment(jsonObj.getString("comment"));
		}else{
			invoice.setInvoiceComment(null);
		}
		
		if(jsonObj.has("grandTotal") && !"".equalsIgnoreCase(jsonObj.getString("grandTotal"))){
			invoice.setTotal(jsonObj.getString("grandTotal"));
			invoice.setDueAmount(jsonObj.getString("grandTotal"));
		}
		
		if(jsonObj.has("afterDiscountTotal") && !"".equalsIgnoreCase(jsonObj.getString("afterDiscountTotal"))){
			invoice.setAfterDiscountValue(jsonObj.getString("afterDiscountTotal"));
		}
		
		invoice.setPaymentStatus("Unpaid");
		invoice.setCreatedBy(user.getUserCode());
		invoice.setStatus(Constants.RECORD_ACTIVE.toString());
		invoice.setInvoiceStatus(saveType);
		invoice.setUpdatedBy(user.getUserCode());
		invoice.setCreatedDate(commonUtil.currentDate());
	
		
		// storing invoive line items
		List<Long> invoiceLineItemsIdsList = null;
		 if(null != invoice.getId()){
	    	  invoiceLineItemsIdsList = invoiceLineItemDao.getInvoiceLineItemsIdsByInvoiceId(invoice.getId());
	     }
		
		 Set<InvoiceLineItems> invoiceLineItemSet = getInvoiceLineItems(jsonObj,user,orgLocation,invoice,invoiceLineItemsIdsList);
		 invoice.setInvoiceLineItems(invoiceLineItemSet);
		
		//Store Tax
		List<Long> invoiceTaxItemsIdsList = null;
		 if(null != invoice.getId()){
			 invoiceTaxItemsIdsList = invoiceTaxDao.getInvoiceTaxIdsByInvoiceId(invoice.getId());
	     }
		Set<InvoiceTax> invoiceTax = getInvoiceTax(jsonObj,user,orgLocation,invoice,invoiceTaxItemsIdsList);
		invoice.setInvoiceTax(invoiceTax);
		
		//store attached Documents
		if(null != attachedfileList && attachedfileList.size() >0){
			Set<InvoiceDocument> invoiceDocumentsSet = getInvoiceDocuments(jsonObj,user,orgLocation,invoice,attachedfileList);	
			invoice.setInvoiceDocumentSet(invoiceDocumentsSet);
		}
		
		invoiceDao.saveInvoce(invoice);
		
		if(null != invoiceLineItemsIdsList && invoiceLineItemsIdsList.size() > 0){
			for(Long lineItemsId : invoiceLineItemsIdsList){
				InvoiceLineItems lineItems = invoiceLineItemDao.getInvoiceLineItemsById(lineItemsId);
				invoiceLineItemDao.deleteLineItem(lineItems);
			}
		}
		
		
		if(null != invoiceTaxItemsIdsList && invoiceTaxItemsIdsList.size() > 0){
			for(Long taxItemsId : invoiceTaxItemsIdsList){
				InvoiceTax tax = invoiceTaxDao.getInvoiceTaxById(taxItemsId);
				invoiceTaxDao.deleteInvoiceTax(tax);
			}
		}
		
		
		return invoice;
	}
	private Set<InvoiceDocument> getInvoiceDocuments(JSONObject jsonObj, User user, OrgLocations orgLocation,Invoice invoice, List<MultipartFile> attachedfileList) throws Exception {
		
		Set<InvoiceDocument> invoiceDocumentSet = new HashSet<>();
		for(MultipartFile multipartFile:attachedfileList){
			InvoiceDocument invoiceDocument = new InvoiceDocument();
			invoiceDocument.setFileContentType(multipartFile.getContentType());
			invoiceDocument.setFileName(multipartFile.getOriginalFilename());
			invoiceDocument.setDocFile(multipartFile.getBytes());
			invoiceDocument.setOrgLocation(orgLocation);
			invoiceDocument.setInvoice(invoice);
			invoiceDocument.setStatus(Constants.RECORD_ACTIVE.toString());
			invoiceDocument.setCreatedBy(user.getUserCode());
			invoiceDocument.setUpdatedBy(user.getUserCode());
			invoiceDocument.setCreatedDate(new Date());
			invoiceDocument.setUpdatedDate(new Date());
			invoiceDocumentSet.add(invoiceDocument);
		}
		return invoiceDocumentSet;
	}
	private Set<InvoiceTax> getInvoiceTax(JSONObject jsonObj, User user, OrgLocations orgLocation, Invoice invoice,List<Long> invoiceTaxItemsIdsList)throws Exception {
		 Set<InvoiceTax> invoiceTaxSet = new HashSet<>();
		 List<Long> updatedtaxLineItems = new ArrayList<Long>();
		 int maxTaxRowCount = Integer.parseInt(jsonObj.getString("maxTaxRowNum"));
		 for(int i=0;i<maxTaxRowCount;i++){
			 if(jsonObj.has(i+"taxType")){
				 if(null != jsonObj.getString(i+"taxType") || null != jsonObj.getString(i+"taxdiscountType") || null != jsonObj.getString(i+"taxdiscountvalue") || null != jsonObj.getString(i+"taxTotal") ||
						 !"".equalsIgnoreCase(jsonObj.getString(i+"taxType")) ||  !"".equalsIgnoreCase(jsonObj.getString(i+"taxdiscountType")) ||  !"".equalsIgnoreCase(jsonObj.getString(i+"taxdiscountvalue")) ||  !"".equalsIgnoreCase(jsonObj.getString(i+"taxTotal")) ){
					 
					 InvoiceTax invoiceTax = new InvoiceTax();
					 
					 if(jsonObj.has(i+"invoiceTaxId") &&  null != jsonObj.getString(i+"invoiceTaxId") && !"".equalsIgnoreCase(jsonObj.getString(i+"invoiceTaxId"))){
						 invoiceTax.setId(Long.valueOf(jsonObj.getString(i+"invoiceTaxId")));
						 updatedtaxLineItems.add(Long.valueOf(jsonObj.getString(i+"invoiceTaxId")));
					 }
					 
					 if(null != jsonObj.getString(i+"taxType") && !"".equalsIgnoreCase(jsonObj.getString(i+"taxType"))){
						 invoiceTax.setTaxName(jsonObj.getString(i+"taxType"));
					 }else{
						 invoiceTax.setTaxName(null);
					 }
					 
					 
					 if(null != jsonObj.getString(i+"taxdiscountType") && !"".equalsIgnoreCase(jsonObj.getString(i+"taxdiscountType"))){
						 invoiceTax.setTaxType(jsonObj.getString(i+"taxdiscountType"));
					 }else{
						 invoiceTax.setTaxType(null);
					 }
					 
					 if(null != jsonObj.getString(i+"taxdiscountvalue") && !"".equalsIgnoreCase(jsonObj.getString(i+"taxdiscountvalue"))){
						 invoiceTax.setTaxValue(jsonObj.getString(i+"taxdiscountvalue"));
					 }else{
						 invoiceTax.setTaxValue(null);
					 }
					 
					 if(null != jsonObj.getString(i+"taxTotal") && !"".equalsIgnoreCase(jsonObj.getString(i+"taxTotal"))){
						 invoiceTax.setTaxAmount(jsonObj.getString(i+"taxTotal"));
					 }else{
						 invoiceTax.setTaxAmount(null);
					 }
					 
					 invoiceTax.setInvoice(invoice);
					 invoiceTax.setOrgLocation(orgLocation);
					 invoiceTax.setStatus(Constants.RECORD_ACTIVE.toString());
					 invoiceTax.setCreatedBy(user.getUserCode());
					 invoiceTax.setCreatedDate(CommonUtil.currentDate());
					 invoiceTaxSet.add(invoiceTax);
				 }
			}
		 }
		
		 if(null != invoice.getId()){
			 updatedtaxLineItems.retainAll(invoiceTaxItemsIdsList);
			 invoiceTaxItemsIdsList.removeAll(updatedtaxLineItems);
		 }
		
		 
		 return invoiceTaxSet;
	}
	
	
	private Set<InvoiceLineItems> getInvoiceLineItems(JSONObject jsonObj, User user, OrgLocations orgLocation, Invoice invoice,List<Long> invoiceLineItemsIdsList) throws Exception {
		 Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();
	        
		 String pattern = "dd-MM-yyyy";
	     SimpleDateFormat format = new SimpleDateFormat(pattern);
	     
	     List<Long> updatedInvoiceLineItems = new ArrayList<Long>();
		
		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxTsRowNum"));
		 for(int i=1;i<maxRowCount;i++){
			 
			 if(jsonObj.has(i+"invoiceStartDate")){
			 
				 if(null != jsonObj.getString(i+"invoiceStartDate") || null != jsonObj.getString(i+"invoiceEndDate") || null != jsonObj.getString(i+"candidate") ||  null != jsonObj.getString(i+"sow") || null != jsonObj.getString(i+"rate") || null != jsonObj.getString(i+"totalHours") ||
						  !"".equalsIgnoreCase(jsonObj.getString(i+"invoiceStartDate")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"invoiceEndDate")) || !"".equalsIgnoreCase(jsonObj.getString(i+"candidate")) || !"".equalsIgnoreCase(jsonObj.getString(i+"sow")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"rate")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"totalHours"))){
					 
					 InvoiceLineItems  invoiceLineItems  = new InvoiceLineItems();
									 
					 if(jsonObj.has(i+"lineItemId") &&  null != jsonObj.getString(i+"lineItemId") && !"".equalsIgnoreCase(jsonObj.getString(i+"lineItemId"))){
						 invoiceLineItems.setId(Long.valueOf(jsonObj.getString(i+"lineItemId")));
						 updatedInvoiceLineItems.add(Long.valueOf(jsonObj.getString(i+"lineItemId")));
					 }
					 
					 if(null != jsonObj.getString(i+"invoiceStartDate") && !"".equalsIgnoreCase(jsonObj.getString(i+"invoiceStartDate"))){
						 Date invoiceStartDate = format.parse(jsonObj.getString(i+"invoiceStartDate"));
						 invoiceLineItems.setStartDate(invoiceStartDate);
					 }else{
						 invoiceLineItems.setStartDate(null);
					 }
					 
					 if(null != jsonObj.getString(i+"invoiceEndDate") && !"".equalsIgnoreCase(jsonObj.getString(i+"invoiceEndDate"))){
						 Date invoiceStartDate = format.parse(jsonObj.getString(i+"invoiceEndDate"));
						 invoiceLineItems.setEndDate(invoiceStartDate);
					 }else{
						 invoiceLineItems.setEndDate(null);
					 }
					 
					 if(null != jsonObj.getString(i+"candidate") && !"".equalsIgnoreCase(jsonObj.getString(i+"candidate"))){
						 Candidate candidate = candidateService.getCandidateById(Long.parseLong(jsonObj.getString(i+"candidate")));
						 invoiceLineItems.setCandidate(candidate);
					 }
					 
					 if(null != jsonObj.getString(i+"sow") && !"".equalsIgnoreCase(jsonObj.getString(i+"sow"))){
						 Contracts contract = contractsTemplateService.getContractById(Long.parseLong(jsonObj.getString(i+"candidate")));
						 invoiceLineItems.setContract(contract);
					 }else{
						 invoiceLineItems.setContract(null);
					 }
					 
					 if(null != jsonObj.getString(i+"rate") && !"".equalsIgnoreCase(jsonObj.getString(i+"rate"))){
						 invoiceLineItems.setRate(jsonObj.getString(i+"rate"));
					 }else{
						 invoiceLineItems.setRate(null);
					 }
					 
					 if(null != jsonObj.getString(i+"totalHours") && !"".equalsIgnoreCase(jsonObj.getString(i+"totalHours"))){
						 invoiceLineItems.setTotalHours(jsonObj.getString(i+"totalHours"));
					 }else{
						 invoiceLineItems.setTotalHours(null);
					 }
					 
					 if(null != jsonObj.getString(i+"amount") && !"".equalsIgnoreCase(jsonObj.getString(i+"amount"))){
						 invoiceLineItems.setAmount(jsonObj.getString(i+"amount"));
					 }else{
						 invoiceLineItems.setAmount(null);
					 }
	
					if(jsonObj.has("seperateInvoice") && !"".equalsIgnoreCase(jsonObj.getString("seperateInvoice"))){
							invoiceLineItems.setSeperateInvoice(jsonObj.getString("seperateInvoice"));;
					 }
						
					 invoiceLineItems.setOrgLocation(orgLocation);
					 invoiceLineItems.setStatus(Constants.RECORD_ACTIVE.toString());
					 invoiceLineItems.setCreatedBy(user.getUserCode());
					 invoiceLineItems.setCreatedDate(commonUtil.currentDate());
					 invoiceLineItems.setInvoice(invoice);
					 invoiceLineItemsSet.add(invoiceLineItems);
				 }
			 }
			 
		 }
		 
		 if(null != invoice.getId()){
			 updatedInvoiceLineItems.retainAll(invoiceLineItemsIdsList);
			 invoiceLineItemsIdsList.removeAll(updatedInvoiceLineItems);
		 }
		 
		return invoiceLineItemsSet;
		
	}
	
	

	@Override
	public Invoice getInvoicetById(Long invoiceId) throws Exception {
		return invoiceDao.getInvoicetById(invoiceId);
	}
	@Override
	public Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception {
		return invoiceDao.getInvoicetByInvoiceUUID(invoiceUUID);
	}
	@Override
	public Invoice updateInvoice(Invoice invoice) throws Exception {
		return invoiceDao.updateInvoice(invoice);
	}
	

}
