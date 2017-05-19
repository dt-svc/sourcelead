package com.bis.sourcelead.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dao.InvoiceLineItemDao;
import com.bis.sourcelead.dao.InvoiceTaxDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.InvoicePdfHelper;
import com.bis.sourcelead.util.PdfUtil;
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
	private EmailScenarioService emailScenarioService;
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	InvoiceLineItemDao invoiceLineItemDao;
	
	@Autowired
	InvoiceTaxDao invoiceTaxDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	private InvoicePdfHelper invoicePdfHelper;
	
	@Autowired
	private ProjectsService projectsService;

	
	@Override

	public List<Invoice> invoiceListByOrgLocationId(Long orgLocationId,String invoiceStatus, String paymentStatus,Date startDateUTC,Date endDateUTC,String invoiceType) throws Exception {
		return invoiceDao.invoiceListByOrgLocationId(orgLocationId,invoiceStatus,paymentStatus,startDateUTC,endDateUTC,invoiceType);

	}
	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType) throws Exception
	{
		return invoiceDao.getSowcontractListByAssociateId(candidateId,orgLocationId,orgRelationId,contractType);
	}

	@Override
	public Invoice saveInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation,String saveType,List<MultipartFile> attachedfileList,String invoiceType,Org org) throws Exception {
		
		
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
      			if(orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				invoice.setResponderLocationId(orgRelation.getLocationId());
      			}if(orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				invoice.setResponderLocationId(orgRelation.getResponderLocationId());
      			}
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
     			if(orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				invoice.setResponderLocationId(orgRelation.getLocationId());
      			}if(orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				invoice.setResponderLocationId(orgRelation.getResponderLocationId());
      			}
     			invoice.setOrgRelationID(orgRelation);	
     		}
     		
     		if(jsonObj.has("clientContact") && !"".equalsIgnoreCase(jsonObj.getString("clientContact"))){
     			User userContactName = userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
     			invoice.setContactUser(userContactName);
     		}
     		invoice.setInvoiceType(Constants.Receivable);
        }
        
        invoice.setOwnerUserId(user);
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
		invoice.setPaymentStatus(Constants.INVOICE_STATUS_UNPAID);
		if(jsonObj.has("projectId") && !"".equalsIgnoreCase(jsonObj.getString("projectId"))){
			Projects projects = projectsService.getProjectById(Long.parseLong(jsonObj.getString("projectId")));
			invoice.setProjectID(projects);
		}
		
		if(jsonObj.has("frequency") && !"".equalsIgnoreCase(jsonObj.getString("frequency"))){
			invoice.setInvoiceFrequency(jsonObj.getString("frequency"));
		}
		invoice.setCreatedBy(user.getUserCode());
		invoice.setStatus(Constants.RECORD_ACTIVE.toString());
		invoice.setInvoiceStatus(saveType);
		invoice.setUpdatedBy(user.getUserCode());
		invoice.setCreatedDate(commonUtil.currentDate());
	
		List<String> submissionApproverHierarchyIds = new ArrayList<>();
		// storing invoive line items
		List<Long> invoiceLineItemsIdsList = null;
		 if(null != invoice.getId()){
	    	  invoiceLineItemsIdsList = invoiceLineItemDao.getInvoiceLineItemsIdsByInvoiceId(invoice.getId());
	     }
		 Set<InvoiceLineItems> invoiceLineItemSet = getInvoiceLineItems(jsonObj,user,orgLocation,invoice,invoiceLineItemsIdsList,submissionApproverHierarchyIds);
		 invoice.setInvoiceLineItems(invoiceLineItemSet);
		 
		 invoice.setSubmissionApproverHierarchyIds(submissionApproverHierarchyIds);
		 
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
		
		//store attached Documents (after)
		File file = invoicePdfHelper.createPdfTableForInvoice(invoice,orgLocation,user,org);
		        invoice.setFileName(file.getName());
//		        byte[] bytesArray = new byte[(int) file.length()];
		        byte[] bytesArray = getByteArray(file);
		        invoice.setDocFile(bytesArray);
		        invoiceDao.saveInvoce(invoice);
		     // tem file deletion
		        if(file != null){
					file.delete();
				}
		        
		invoiceDao.saveInvoce(invoice);
		if(saveType.equalsIgnoreCase("SENT")){
			//mail scenario for client accept or reject
			sendInvoiceMail(user,orgLocation,invoice);
			saveParentInvoice(invoice);	
		}
		
		
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
		
		/*//mail scenario for client accept or reject
		if(saveType.equalsIgnoreCase("SENT")){
			invoiceClientAcceptMail(user,orgLocation,invoice);
		}*/

		return invoice;
	}
	private byte[] getByteArray(File file) throws IOException {
		    ByteArrayOutputStream ous = null;
		    InputStream ios = null;
		    try {
		        byte[] buffer = new byte[4096];
		        ous = new ByteArrayOutputStream();
		        ios = new FileInputStream(file);
		        int read = 0;
		        while ((read = ios.read(buffer)) != -1) {
		            ous.write(buffer, 0, read);
		        }
		    }finally {
		        try {
		            if (ous != null)
		                ous.close();
		        } catch (IOException e) {
		        }

		        try {
		            if (ios != null)
		                ios.close();
		        } catch (IOException e) {
		        }
		    }
		    return ous.toByteArray();
		
	}
	private void saveParentInvoice(Invoice invoice) throws Exception {
		Invoice parentInvoice = new Invoice();
		if(invoice.getInvoiceType().equalsIgnoreCase(Constants.Payables)){
			parentInvoice.setInvoiceType(Constants.Receivable);
		}else{
			parentInvoice.setInvoiceType(Constants.Payables);
		}
		parentInvoice.setInvoiceId(invoice.getInvoiceId());
		parentInvoice.setParentInvoiceId(invoice);
		parentInvoice.setOrgRelationID(invoice.getOrgRelationID());
		parentInvoice.setContactUser(invoice.getOwnerUserId());
		parentInvoice.setOrgLocation(invoice.getResponderLocationId());
		parentInvoice.setOwnerUserId(invoice.getContactUser());
		parentInvoice.setResponderLocationId(invoice.getOrgLocation());
		parentInvoice.setInvoiceDate(invoice.getInvoiceDate());
		parentInvoice.setBillingAddress(invoice.getBillingAddress());
		parentInvoice.setDiscountAmount(invoice.getDiscountAmount());
		parentInvoice.setDiscountValue(invoice.getDiscountValue());
		parentInvoice.setAfterDiscountValue(invoice.getAfterDiscountValue());
		parentInvoice.setSubTotal(invoice.getSubTotal());
		parentInvoice.setInvoiceComment(invoice.getInvoiceComment());
		parentInvoice.setPaymentFrequency(invoice.getPaymentFrequency());
		parentInvoice.setDiscountType(invoice.getDiscountType());
		parentInvoice.setTotal(invoice.getTotal());
		parentInvoice.setInvoiceStatus(Constants.INVOICE_RECEIVED);
		parentInvoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		parentInvoice.setPaymentStatus(invoice.getPaymentStatus());
		parentInvoice.setDueDate(invoice.getDueDate());
		parentInvoice.setDueAmount(invoice.getDueAmount());
		parentInvoice.setStatus(Constants.RECORD_ACTIVE.toString());
		parentInvoice.setCreatedDate(invoice.getCreatedDate());
		parentInvoice.setCreatedBy(invoice.getCreatedBy());
		invoiceDao.saveInvoce(parentInvoice);
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
		 for(int i=1;i<maxTaxRowCount;i++){
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
	
	
	private Set<InvoiceLineItems> getInvoiceLineItems(JSONObject jsonObj, User user, OrgLocations orgLocation, Invoice invoice,List<Long> invoiceLineItemsIdsList, List<String> submissionApproverHierarchyIds) throws Exception {
		 Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();
	        
		 String pattern = "dd-MM-yyyy";
	     SimpleDateFormat format = new SimpleDateFormat(pattern);
	     
	     List<Long> updatedInvoiceLineItems = new ArrayList<Long>();
		
		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxTsRowNum"));
		 for(int i=0;i<maxRowCount;i++){
			 
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
	              // As of Now we are not storing seperateInvoice status
//					if(jsonObj.has("seperateInvoice") && !"".equalsIgnoreCase(jsonObj.getString("seperateInvoice"))){
//							invoiceLineItems.setSeperateInvoice(jsonObj.getString("seperateInvoice"));;
//					 }
					if(jsonObj.has(i+"submissionApproverHierarchyId") && !"".equalsIgnoreCase(jsonObj.getString(i+"submissionApproverHierarchyId"))){
						submissionApproverHierarchyIds.add(jsonObj.getString(i+"submissionApproverHierarchyId"));
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
	
	public void sendInvoiceMail(User user, OrgLocations orglocations, Invoice invoice) throws Exception{

		 String UUID = SourceLeadRandomCodeHelper.generateUUID();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(invoice.getContactUser().getEmail());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
		 String date = (Constants.DF_DMY.format(invoice.getDueDate()));
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("invoiceID",invoice.getInvoiceId());
		 globalVarsMap.put("LoginUser",user.getFirstName());
		 globalVarsMap.put("clientName",invoice.getContactUser().getFirstName());
		 globalVarsMap.put("grandTotal",invoice.getTotal());
		 globalVarsMap.put("dueDate",date); 
		 globalVarsMap.put("orgName",orglocations.getOrg().getOrgName());
		 globalVarsMap.put("address",invoice.getBillingAddress());
		 /*StringBuilder attachmentLinkBuilder=new StringBuilder();
	        if(invoice.getFileName() != null){
	            globalVarsMap.put("attachement",attachmentLinkBuilder.append("<a href='").append(sourceleadUrl).append("/").append("/downloadInvoiceAttachment?invoiceId=").append(invoice.getId()).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment").append("<br/></div></a>").toString());
	        }
		 */
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_ACCEPT_MAIL,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_ACCEPT_MAIL,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orglocations.getOrg());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 ArrayList<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
		 EmailAttachment emailAttachment = new EmailAttachment();
		 emailAttachment.setUUID(UUID);
		 
		 if(null != invoice.getInvoiceDocumentSet() && invoice.getInvoiceDocumentSet().size() > 0 && !invoice.getInvoiceDocumentSet().isEmpty()){
		 Set<InvoiceDocument> documenetSet = invoice.getInvoiceDocumentSet();
		 for(InvoiceDocument doc:documenetSet){
			 EmailAttachment docAttachment = new EmailAttachment();
			 docAttachment.setDocFile(doc.getDocFile());
			 docAttachment.setName(doc.getFileName());
			 documentList.add(docAttachment);
		 }
		 
		 }
		 emailAttachment.setDocFile(invoice.getDocFile());
		 emailAttachment.setName(invoice.getFileName());
		 attachmentList.add(emailAttachment);
		 if(null != documentList && !documentList.isEmpty() && documentList.size() > 0){
		 attachmentList.addAll(documentList);
		 }
		 //calling the sendEmail method of emailScenerioUtil
		 
		 emailScenerioUtil.sendWithAttachmentEmail(emailMessage,orglocations.getId(),iAdressArray,attachmentList,null);
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, user, invoice.getContactUser(), orglocations, notificationScenario);
	}


	@Override
	public void getPdfFileForInvoice(Invoice invoice,OrgLocations orgLocation,User user, Org org) throws Exception {
		invoicePdfHelper.createPdfTableForInvoice(invoice,orgLocation,user,org);
	}
	
	@Override
	public Invoice getInvoicetById(Long invoiceId) throws Exception {
		return invoiceDao.getInvoicetById(invoiceId);
	}
	@Override
	public List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception {
		return invoiceDao.getViewInvoiceListByInvoiceIdAndOrglocationId(invoiceId,orgLocationId);
	}
    @Override
	public Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception {
		return invoiceDao.getInvoicetByInvoiceUUID(invoiceUUID);
	}
	@Override
	public Invoice updateInvoice(Invoice invoice) throws Exception {
		return invoiceDao.updateInvoice(invoice);
	}
	@Override
	public Invoice saveProjectInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation, String saveType, List<MultipartFile> attachedfileList, String invoiceType) throws Exception
	{
		Invoice invoice = new Invoice();
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
        
        //-----------------------------
        
        
       /* "":"5",
        "":"12",
        "":"23456",
        "":"31-05-2017",
        "":"30 days",
        "":"31-05-2017",
        "projectId":"9",
        "":"Hyderabad",
        "maxRowNum":"7"
        
        "rowTotal":"2800.00",
        "":"Y",
        "":"project invoice",
        "invoiceAttcahmentMaxRowNumber":"1",
        "":"2620",
        "_csrf":"1a98ab78-21f6-4db8-b933-9091179589db"*/
        
        
        
        
		//--------------------------------
        if(invoiceType.equalsIgnoreCase("payable")){
        	
        	 
        	if(jsonObj.has("vendorOrgRelationId") && !"".equalsIgnoreCase(jsonObj.getString("vendorOrgRelationId"))){
      			
      			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("vendorOrgRelationId")));
      			invoice.setOrgRelationID(orgRelation);	
      		}
      		
      		if(jsonObj.has("vendorContact") && !"".equalsIgnoreCase(jsonObj.getString("vendorContact"))){
      			String[] contact = jsonObj.getString("vendorContact").split(",");
      			User contactUser = userService.getUserById(Long.parseLong(contact[1]));
      			invoice.setContactUser(contactUser);
      		}
      		
      		invoice.setInvoiceType(Constants.Payables);
        
        
        }else{
        	 if(jsonObj.has("orgRelationId") && !"".equalsIgnoreCase(jsonObj.getString("orgRelationId"))){
     			
     			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("orgRelationId")));
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
		if(jsonObj.has("projectId") && !"".equalsIgnoreCase(jsonObj.getString("projectId"))){
			Projects projects = projectsService.getProjectById(Long.parseLong(jsonObj.getString("projectId")));
			invoice.setProjectID(projects);
		}
		if(jsonObj.has("invoiceAddress") && !"".equalsIgnoreCase(jsonObj.getString("invoiceAddress"))){
			invoice.setBillingAddress(jsonObj.getString("invoiceAddress"));
			
		}
		if(jsonObj.has("invoiceDate") && !"".equalsIgnoreCase(jsonObj.getString("invoiceDate"))){
			Date invoiceDate = format.parse(jsonObj.getString("invoiceDate"));
			invoice.setInvoiceDate(invoiceDate);
		}
		if(jsonObj.has("dueDate") && !"".equalsIgnoreCase(jsonObj.getString("dueDate"))){
			Date dueDate = format.parse(jsonObj.getString("dueDate"));
			invoice.setDueDate(dueDate);
		}
		
		if(jsonObj.has("paymentTerm") && !"".equalsIgnoreCase(jsonObj.getString("paymentTerm"))){
			invoice.setPaymentFrequency(jsonObj.getString("paymentTerm"));;
		}
		
		if(jsonObj.has("disCountType") && !"".equalsIgnoreCase(jsonObj.getString("disCountType"))){
			invoice.setDiscountType(jsonObj.getString("disCountType"));;
		}
		
		if(jsonObj.has("discountValue") && !"".equalsIgnoreCase(jsonObj.getString("discountValue"))){
			invoice.setDiscountAmount(jsonObj.getString("discountValue"));;
		}
		if(jsonObj.has("totalDiscountAmt") && !"".equalsIgnoreCase(jsonObj.getString("totalDiscountAmt"))){
			invoice.setDiscountValue(jsonObj.getString("totalDiscountAmt"));;
		}
		if(jsonObj.has("subTotal") && !"".equalsIgnoreCase(jsonObj.getString("subTotal"))){
			invoice.setSubTotal(jsonObj.getString("subTotal"));;
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
		if(jsonObj.has("invoiceFerquency") && !"".equalsIgnoreCase(jsonObj.getString("invoiceFerquency"))){
			invoice.setInvoiceFrequency(jsonObj.getString("invoiceFerquency"));
		}
		invoice.setPaymentStatus("Unpaid");
		invoice.setCreatedBy(user.getUserCode());
		invoice.setStatus(Constants.RECORD_ACTIVE.toString());
		invoice.setInvoiceStatus(saveType);
		invoice.setUpdatedBy(user.getUserCode());
		invoice.setCreatedDate(commonUtil.currentDate());

	
		
		// storing invoive line items
		Set<InvoiceLineItems> invoiceLineItemSet = getProjectInvoiceLineItems(jsonObj,user,orgLocation,invoice);
		invoice.setInvoiceLineItems(invoiceLineItemSet);
		
		//Store Tax
		Set<InvoiceTax> invoiceTax = getProjectInvoiceTax(jsonObj,user,orgLocation,invoice);
		invoice.setInvoiceTax(invoiceTax);
		
		//store attached Documents
		if(null != attachedfileList && attachedfileList.size() >0){
			Set<InvoiceDocument> invoiceDocumentsSet = getInvoiceDocuments(jsonObj,user,orgLocation,invoice,attachedfileList);	
			invoice.setInvoiceDocumentSet(invoiceDocumentsSet);
		}
		
		return invoiceDao.saveInvoce(invoice);
	}

	
	
	private Set<InvoiceLineItems> getProjectInvoiceLineItems(JSONObject jsonObj, User user, OrgLocations orgLocation, Invoice invoice) throws Exception {
		 Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();
	        
		 String pattern = "dd-MM-yyyy";
	     SimpleDateFormat format = new SimpleDateFormat(pattern);
	     
		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxRowNum"));
		 for(int i=1;i<maxRowCount;i++){
			 
			 if(jsonObj.has(i+"sdate")){
			 
				 if(null != jsonObj.getString(i+"sdate") || null != jsonObj.getString(i+"edate") || null != jsonObj.getString(i+"candidateId") ||  null != jsonObj.getString(i+"sow") || null != jsonObj.getString(i+"rate") || null != jsonObj.getString(i+"totalHours") ||
				!"".equalsIgnoreCase(jsonObj.getString(i+"sdate")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"edate")) || !"".equalsIgnoreCase(jsonObj.getString(i+"candidateId")) || !"".equalsIgnoreCase(jsonObj.getString(i+"sow")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"rate")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"totalHours"))){
					 
					 InvoiceLineItems  invoiceLineItems = new InvoiceLineItems();
					 
					 if(null != jsonObj.getString(i+"sdate") && !"".equalsIgnoreCase(jsonObj.getString(i+"sdate"))){
						 Date invoiceStartDate = format.parse(jsonObj.getString(i+"sdate"));
						 invoiceLineItems.setStartDate(invoiceStartDate);
					 }
					 if(null != jsonObj.getString(i+"edate") && !"".equalsIgnoreCase(jsonObj.getString(i+"edate"))){
						 Date invoiceStartDate = format.parse(jsonObj.getString(i+"edate"));
						 invoiceLineItems.setEndDate(invoiceStartDate);
					 }
					 if(null != jsonObj.getString(i+"candidateId") && !"".equalsIgnoreCase(jsonObj.getString(i+"candidateId"))){
						 Candidate candidate = candidateService.getCandidateById(Long.parseLong(jsonObj.getString(i+"candidateId")));
						 invoiceLineItems.setCandidate(candidate);
					 }
					 if(null != jsonObj.getString(i+"sow") && !"".equalsIgnoreCase(jsonObj.getString(i+"sow"))){
						 Contracts contract = contractsTemplateService.getContractById(Long.parseLong(jsonObj.getString(i+"candidateId")));
						 invoiceLineItems.setContract(contract);
					 }
					 if(null != jsonObj.getString(i+"rate") && !"".equalsIgnoreCase(jsonObj.getString(i+"rate"))){
						 invoiceLineItems.setRate(jsonObj.getString(i+"rate"));
					 }
					 if(null != jsonObj.getString(i+"totalHours") && !"".equalsIgnoreCase(jsonObj.getString(i+"totalHours"))){
						 invoiceLineItems.setTotalHours(jsonObj.getString(i+"totalHours"));
					 }
					 
					 if(null != jsonObj.getString(i+"totalAmount") && !"".equalsIgnoreCase(jsonObj.getString(i+"totalAmount"))){
						 invoiceLineItems.setAmount(jsonObj.getString(i+"totalAmount"));
					 }
	
					if(jsonObj.has("isSeparateInvoice") && !"".equalsIgnoreCase(jsonObj.getString("isSeparateInvoice"))){
						invoiceLineItems.setSeperateInvoice(jsonObj.getString("isSeparateInvoice"));;
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
		return invoiceLineItemsSet;
		
	}
	
	private Set<InvoiceTax> getProjectInvoiceTax(JSONObject jsonObj, User user, OrgLocations orgLocation, Invoice invoice)throws Exception {
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
	
	@Override
	public Invoice getInvoicetByParentInvoiceId(Long parentInvoiceId) throws Exception {
		return invoiceDao.getInvoicetByParentInvoiceId(parentInvoiceId);
	}
	@Override
	public void updateSubmissionApproverHiearachy(List<String> submissionApproverHierarchyIds, String invoiceFrequency) throws Exception
	{
		String frequencyArray[] = invoiceFrequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		for(String id :submissionApproverHierarchyIds){
			SubmissionApproverHierarchy submissionApproverHierarchy = submissionApproverHierarchyService.getSubmissionApproverHierarchy(id);
			submissionApproverHierarchy.setInvoiceStatus(Constants.SENT);
			submissionApproverHierarchyService.addSubmissionApproverHierarchy(submissionApproverHierarchy);
		}
	
	}
}
