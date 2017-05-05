package com.bis.sourcelead.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.h2.schema.Constant;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.OnboardStatus;
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
	private CommonUtil commonUtil;
	
	@Override
	public List<Invoice> invoiceListByOrgLocationId(Long orgLocationId) throws Exception {
		return invoiceDao.invoiceListByOrgLocationId(orgLocationId);
	}
	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId) throws Exception
	{
		return invoiceDao.getSowcontractListByAssociateId(candidateId,orgLocationId,orgRelationId);
	}

	@Override
	public Invoice saveInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation,String saveType,List<MultipartFile> attachedfileList) throws Exception {
		
		Invoice invoice = new Invoice();
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
		if(jsonObj.has("clientOrgRelationId") && !"".equalsIgnoreCase(jsonObj.getString("clientOrgRelationId"))){
			
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("clientOrgRelationId")));
			invoice.setClientOrgRelationID(orgRelation);	
		}
		
		if(jsonObj.has("clientContact") && !"".equalsIgnoreCase(jsonObj.getString("clientContact"))){
			User userContactName = userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
			invoice.setContactContactUser(userContactName);
		}
		
		invoice.setOrgLocation(orgLocation);
		if(jsonObj.has("invoiceId") && !"".equalsIgnoreCase(jsonObj.getString("invoiceId"))){
			invoice.setInvoiceId(jsonObj.getString("invoiceId"));
		}
		if(jsonObj.has("billingAddress") && !"".equalsIgnoreCase(jsonObj.getString("billingAddress"))){
			invoice.setBillingAddress(jsonObj.getString("billingAddress"));
			
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
			invoice.setPaymentFrequency(jsonObj.getString("paymentFrequency"));;
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
			invoice.setInvoiceComment(jsonObj.getString("comment"));;
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
		Set<InvoiceLineItems> invoiceLineItemSet = getInvoiceLineItems(jsonObj,user,orgLocation,invoice);
		invoice.setInvoiceLineItems(invoiceLineItemSet);
		
		//Store Tax
		Set<InvoiceTax> invoiceTax = getInvoiceTax(jsonObj,user,orgLocation,invoice);
		invoice.setInvoiceTax(invoiceTax);
		
		//store attached Documents
		if(null != attachedfileList && attachedfileList.size() >0){
			Set<InvoiceDocument> invoiceDocumentsSet = getInvoiceDocuments(jsonObj,user,orgLocation,invoice,attachedfileList);	
			invoice.setInvoiceDocumentSet(invoiceDocumentsSet);
		}
		
		return invoiceDao.saveInvoce(invoice);
//		return null;
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
	private Set<InvoiceTax> getInvoiceTax(JSONObject jsonObj, User user, OrgLocations orgLocation, Invoice invoice)throws Exception {
		 Set<InvoiceTax> invoiceTaxSet = new HashSet<>();
		 int maxTaxRowCount = Integer.parseInt(jsonObj.getString("maxTaxRowNum"));
		 for(int i=0;i<maxTaxRowCount;i++){
			 if(null != jsonObj.getString(i+"taxType") || null != jsonObj.getString(i+"taxdiscountType") || null != jsonObj.getString(i+"taxdiscountvalue") || null != jsonObj.getString(i+"taxTotal") ||
					 !"".equalsIgnoreCase(jsonObj.getString(i+"taxType")) ||  !"".equalsIgnoreCase(jsonObj.getString(i+"taxdiscountType")) ||  !"".equalsIgnoreCase(jsonObj.getString(i+"taxdiscountvalue")) ||  !"".equalsIgnoreCase(jsonObj.getString(i+"taxTotal")) ){
				 InvoiceTax invoiceTax = new InvoiceTax();
				 if(null != jsonObj.getString(i+"taxType") && !"".equalsIgnoreCase(jsonObj.getString(i+"taxType"))){
					 invoiceTax.setTaxName(jsonObj.getString(i+"taxType"));
				 }
				 if(null != jsonObj.getString(i+"taxdiscountType") && !"".equalsIgnoreCase(jsonObj.getString(i+"taxdiscountType"))){
					 invoiceTax.setTaxType(jsonObj.getString(i+"taxdiscountType"));
				 }
				 if(null != jsonObj.getString(i+"taxdiscountvalue") && !"".equalsIgnoreCase(jsonObj.getString(i+"taxdiscountvalue"))){
					 invoiceTax.setTaxValue(jsonObj.getString(i+"taxdiscountvalue"));
				 }
				 if(null != jsonObj.getString(i+"taxTotal") && !"".equalsIgnoreCase(jsonObj.getString(i+"taxTotal"))){
					 invoiceTax.setTaxAmount(jsonObj.getString(i+"taxTotal"));
				 }
				 invoiceTax.setInvoice(invoice);
				 invoiceTax.setOrgLocation(orgLocation);
				 invoiceTax.setStatus(Constants.RECORD_ACTIVE.toString());
				 invoiceTax.setCreatedBy(user.getUserCode());
				 invoiceTax.setCreatedDate(CommonUtil.currentDate());
				 invoiceTaxSet.add(invoiceTax);
			 }
		 }
		return invoiceTaxSet;
	}
	
	
	private Set<InvoiceLineItems> getInvoiceLineItems(JSONObject jsonObj, User user, OrgLocations orgLocation, Invoice invoice) throws Exception {
		 Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();
	        
		 String pattern = "dd-MM-yyyy";
	     SimpleDateFormat format = new SimpleDateFormat(pattern);
		
		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxTsRowNum"));
		 for(int i=0;i<maxRowCount;i++){
			 if(null != jsonObj.getString(i+"invoiceStartDate") || null != jsonObj.getString(i+"invoiceEndDate") || null != jsonObj.getString(i+"candidate") ||  null != jsonObj.getString(i+"sow") || null != jsonObj.getString(i+"rate") || null != jsonObj.getString(i+"totalHours") ||
					  !"".equalsIgnoreCase(jsonObj.getString(i+"invoiceStartDate")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"invoiceEndDate")) || !"".equalsIgnoreCase(jsonObj.getString(i+"candidate")) || !"".equalsIgnoreCase(jsonObj.getString(i+"sow")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"rate")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"totalHours"))){
				 
				 InvoiceLineItems  invoiceLineItems = new InvoiceLineItems();
				 
				 if(null != jsonObj.getString(i+"invoiceStartDate") && !"".equalsIgnoreCase(jsonObj.getString(i+"invoiceStartDate"))){
					 Date invoiceStartDate = format.parse(jsonObj.getString(i+"invoiceStartDate"));
					 invoiceLineItems.setStartDate(invoiceStartDate);
				 }
				 if(null != jsonObj.getString(i+"invoiceEndDate") && !"".equalsIgnoreCase(jsonObj.getString(i+"invoiceEndDate"))){
					 Date invoiceStartDate = format.parse(jsonObj.getString(i+"invoiceEndDate"));
					 invoiceLineItems.setEndDate(invoiceStartDate);
				 }
				 if(null != jsonObj.getString(i+"candidate") && !"".equalsIgnoreCase(jsonObj.getString(i+"candidate"))){
					 Candidate candidate = candidateService.getCandidateById(Long.parseLong(jsonObj.getString(i+"candidate")));
					 invoiceLineItems.setCandidate(candidate);
				 }
				 if(null != jsonObj.getString(i+"sow") && !"".equalsIgnoreCase(jsonObj.getString(i+"sow"))){
					 Contracts contract = contractsTemplateService.getContractById(Long.parseLong(jsonObj.getString(i+"candidate")));
					 invoiceLineItems.setContract(contract);
				 }
				 if(null != jsonObj.getString(i+"rate") && !"".equalsIgnoreCase(jsonObj.getString(i+"rate"))){
					 invoiceLineItems.setRate(jsonObj.getString(i+"rate"));
				 }
				 if(null != jsonObj.getString(i+"totalHours") && !"".equalsIgnoreCase(jsonObj.getString(i+"totalHours"))){
					 invoiceLineItems.setTotalHours(jsonObj.getString(i+"totalHours"));
				 }
				 
				 if(null != jsonObj.getString(i+"amount") && !"".equalsIgnoreCase(jsonObj.getString(i+"amount"))){
					 invoiceLineItems.setAmount(jsonObj.getString(i+"amount"));
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
		return invoiceLineItemsSet;
		
	}
	
}
