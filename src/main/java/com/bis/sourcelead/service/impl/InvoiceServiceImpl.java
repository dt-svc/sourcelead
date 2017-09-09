package com.bis.sourcelead.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.beans.BillingGraphBean;
import com.bis.sourcelead.beans.BillingReportDTO;
import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dao.InvoiceLineItemDao;
import com.bis.sourcelead.dao.InvoiceTaxDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.InvoiceLineItemsDTO;
import com.bis.sourcelead.dto.InvoiceTaxDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceAmount;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.ExpensesInvoicePdfHelper;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.InvoicePdfHelper;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
import com.bis.sourcelead.web.LoginSessionController;


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
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private TabsService tabsService;
	
	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	AddExpensesDao addExpensesDao;

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	private InvoicePdfHelper invoicePdfHelper;
	
	@Autowired
	private ProjectsService projectsService;

	@Autowired
	TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private ExpensesInvoicePdfHelper expensesInvoicePdfHelper;
	
	@Override
	public List<Invoice> invoiceListByOrgLocationId(Long orgLocationId,String invoiceStatus, String paymentStatus,Date startDateUTC,Date endDateUTC,String invoiceType) throws Exception {
		return invoiceDao.invoiceListByOrgLocationId(orgLocationId,invoiceStatus,paymentStatus,startDateUTC,endDateUTC,invoiceType);

	}
	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType) throws Exception
	{
		return invoiceDao.getSowcontractListByAssociateId(candidateId,orgLocationId,orgRelationId,contractType);
	}

	@Override
	public Invoice saveInvoice(User user, OrgLocations orgLocation,String saveType,String invoiceType,Org org,String loginOrgCurrencyCode,InvoiceDTO invoiceDTO) throws Exception {
		
        Invoice invoice = null;
        
        if(StringUtils.isNotBlank(invoiceDTO.getSavedInvoiceId())){
        	invoice = invoiceDao.getInvoicetById(Long.valueOf(invoiceDTO.getSavedInvoiceId()));
        }else{
        	invoice = new Invoice();
        	invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
        }
        
		OrgLocations vendorOrgLocation = null;;
        if(Constants.PAYABLE.equalsIgnoreCase(invoiceType)){
        	if(StringUtils.isNotBlank(invoiceDTO.getVendorOrgRelationId())){
      			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(invoiceDTO.getVendorOrgRelationId()));
      			if(orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				vendorOrgLocation = orgRelation.getLocationId();
      				invoice.setResponderLocationId(vendorOrgLocation);
      			}if(orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				vendorOrgLocation = orgRelation.getResponderLocationId();
      				invoice.setResponderLocationId(orgRelation.getResponderLocationId());
      			}
      			invoice.setOrgRelationID(orgRelation);	
      		}
      		
      		if(StringUtils.isNotBlank(invoiceDTO.getVendorContact())){
      			User contactUser = userService.getUserById(Long.parseLong(invoiceDTO.getVendorContact()));
      			invoice.setContactUser(contactUser);
      		}
      		invoice.setInvoiceType(Constants.Payables);
        }else{
        	 if(StringUtils.isNotBlank(invoiceDTO.getClientOrgRelationId())){
     			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(invoiceDTO.getClientOrgRelationId()));
     			if(orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
     				vendorOrgLocation = orgRelation.getLocationId();
     				invoice.setResponderLocationId(vendorOrgLocation);
      			}if(orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				vendorOrgLocation = orgRelation.getResponderLocationId();
      				invoice.setResponderLocationId(orgRelation.getResponderLocationId());
      			}
     			invoice.setOrgRelationID(orgRelation);	
     		}
     		
     		if(StringUtils.isNotBlank(invoiceDTO.getClientContact())){
     			User userContactName = userService.getUserById(Long.parseLong(invoiceDTO.getClientContact()));
     			invoice.setContactUser(userContactName);
     		}
     		invoice.setInvoiceType(Constants.Receivable);
        }

        //currency conversion
        String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if(!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)){
	        if(null != vendorCurrencyCode){
	      	float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, Float.parseFloat(invoiceDTO.getGrandTotal()));
	      	float exchangeAmoiunt = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode,1);
	      	float convertedAmount1 = 	(float) (Math.round(convertedAmount * 100.0) / 100.0);
	      	invoice.setConvertCurrencyCode(vendorCurrencyCode);
	      	invoice.setConvertAmount(String.valueOf(convertedAmount1));
	      	invoice.setExchangeRate(String.valueOf(exchangeAmoiunt));
	        }	
	    }
		
        invoice.setOwnerUserId(user);
		invoice.setOrgLocation(orgLocation);
		if(StringUtils.isNotBlank(invoiceDTO.getInvoiceId())){
			invoice.setInvoiceId(invoiceDTO.getInvoiceId());
		}
		if(StringUtils.isNotBlank(invoiceDTO.getBillingAddress())){
			invoice.setBillingAddress(invoiceDTO.getBillingAddress());
		}
		if(StringUtils.isNotBlank(invoiceDTO.getAccountDetails())){
			invoice.setAccountDetails(invoiceDTO.getAccountDetails());
		}
		
		if(StringUtils.isNotBlank(invoiceDTO.getInvoiceDate())){
			Date invoiceDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),invoiceDTO.getInvoiceDate());
			invoice.setInvoiceDate(invoiceDate);
		}
		if(StringUtils.isNotBlank(invoiceDTO.getDueDate())){
			Date dueDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),invoiceDTO.getDueDate());
			invoice.setDueDate(dueDate);
		}
		
		if(StringUtils.isNotBlank(invoiceDTO.getPaymentFrequency())){
			invoice.setPaymentFrequency(invoiceDTO.getPaymentFrequency());
		}
		if(StringUtils.isNotBlank(invoiceDTO.getDisCount())){
			invoice.setDiscountType(invoiceDTO.getDisCount());;
		}
		
		if(StringUtils.isNotBlank(invoiceDTO.getDiscountValue())){
			invoice.setDiscountAmount(invoiceDTO.getDiscountValue());;
		}
		if(StringUtils.isNotBlank(invoiceDTO.getDiscountTotal())){
			invoice.setDiscountValue(invoiceDTO.getDiscountTotal());;
		}
		if(StringUtils.isNotBlank(invoiceDTO.getInoviceTotal())){
			invoice.setSubTotal(invoiceDTO.getInoviceTotal());;
		}
		
		if(StringUtils.isNotBlank(invoiceDTO.getComment())){
			invoice.setInvoiceComment(invoiceDTO.getComment());
		}
		if(StringUtils.isNotBlank(invoiceDTO.getGrandTotal())){
			invoice.setTotal(invoiceDTO.getGrandTotal());
			invoice.setDueAmount(invoiceDTO.getGrandTotal());
		}
		
		if(StringUtils.isNotBlank(invoiceDTO.getAfterDiscountTotal())){
			invoice.setAfterDiscountValue(invoiceDTO.getAfterDiscountTotal());
		}
		invoice.setPaymentStatus(Constants.INVOICE_STATUS_UNPAID);
		if(StringUtils.isNotBlank(invoiceDTO.getProjectId())){
			Projects projects = projectsService.getProjectById(Long.parseLong(invoiceDTO.getProjectId()));
			invoice.setProjectID(projects);
		}
		
		if(StringUtils.isNotBlank(invoiceDTO.getFrequency())){
			invoice.setInvoiceFrequency(invoiceDTO.getFrequency());
		}
		
	    invoice.setCurrencyCode(loginOrgCurrencyCode);
		invoice.setCreatedBy(user.getUserCode());
		invoice.setStatus(Constants.RECORD_ACTIVE.toString());
		invoice.setInvoiceStatus(saveType);
		invoice.setUpdatedBy(user.getUserCode());
		invoice.setCreatedDate(commonUtil.currentDate());
	
		List<String> submissionApproverHierarchyIds = new ArrayList<>();
		List<TimesheetSubmissionsDocuments> docusList = new ArrayList<TimesheetSubmissionsDocuments>();
		
		// storing invoive line items
		List<Long> invoiceLineItemsIdsList = null;
		 if(null != invoice.getId()){
	    	  invoiceLineItemsIdsList = invoiceLineItemDao.getInvoiceLineItemsIdsByInvoiceId(invoice.getId());
	     }
		 Set<InvoiceLineItems> invoiceLineItemSet = getInvoiceLineItems(user,orgLocation,invoice,invoiceLineItemsIdsList,submissionApproverHierarchyIds,docusList,invoiceDTO);
		 invoice.setInvoiceLineItems(invoiceLineItemSet);
		 
		 invoice.setSubmissionApproverHierarchyIds(submissionApproverHierarchyIds);
		 
		//Store Tax
		List<Long> invoiceTaxItemsIdsList = null;
		 if(null != invoice.getId()){
			 invoiceTaxItemsIdsList = invoiceTaxDao.getInvoiceTaxIdsByInvoiceId(invoice.getId());
	     }
		Set<InvoiceTax> invoiceTax = getInvoiceTax(user,orgLocation,invoice,invoiceTaxItemsIdsList,invoiceDTO);
		invoice.setInvoiceTax(invoiceTax);
		
		//store attached Documents
		if(invoiceDTO.getFileInfo() !=null && !invoiceDTO.getFileInfo().isEmpty()){
			Set<InvoiceDocument> invoiceDocumentsSet = getInvoiceDocuments(invoiceDTO,user,orgLocation,invoice);	
			invoice.setInvoiceDocumentSet(invoiceDocumentsSet);
		}	
		
		//store attached Documents (after)
		File file = invoicePdfHelper.createPdfTableForInvoice(invoice,orgLocation,user,org,invoiceType,vendorCurrencyCode,loginOrgCurrencyCode);
		
		if(file.exists() && !file.isDirectory()) 
		{
		        invoice.setFileName(file.getName());
		        invoice.setFileUrl(FileUploadUtil.moveFile(staticPath, org.getId(), orgLocation.getId(), "InvoicePdf", file));
		}
		        if(null != file){
		        	file.delete();
		        }
		        if(invoice.getSubmissionApproverHierarchyIds() !=null && invoice.getSubmissionApproverHierarchyIds().size() >0){
		        	String submissionIds = invoice.getSubmissionApproverHierarchyIds().stream().map(Object::toString).collect(Collectors.joining(","));
		        	invoice.setSubmissionApproverHierarchyId(submissionIds);
		        }
		       
		invoiceDao.saveInvoce(invoice);
		if(Constants.SENT.equalsIgnoreCase(saveType)){
			//mail scenario for client accept or reject
			sendInvoiceMail(user,orgLocation,invoice,invoiceType,docusList);
			
			saveParentInvoice(invoice,loginOrgCurrencyCode,vendorOrgLocation);	
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
	
	private void saveParentInvoice(Invoice invoice, String loginOrgCurrencyCode, OrgLocations vendorOrgLocation) throws Exception {
//		Invoice parentInvoice = null;
		Invoice parentInvoice = invoiceDao.getParentInvoiceByInvoiceId(invoice.getId());
		if(parentInvoice ==null){
			parentInvoice = new Invoice();
		}
		if(invoice.getInvoiceType().equalsIgnoreCase(Constants.Payables)){
			parentInvoice.setInvoiceType(Constants.Receivable);
		}else{
 			parentInvoice.setInvoiceType(Constants.Payables);
		}
		String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if(!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)){
	        if(null != vendorCurrencyCode){
	      	float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, Float.parseFloat(invoice.getTotal()));
	      	float convertedAmount1 = 	(float) (Math.round(convertedAmount * 100.0) / 100.0);
	      	parentInvoice.setCurrencyCode(vendorCurrencyCode);
	      	parentInvoice.setTotal(String.valueOf(convertedAmount1));
	      	parentInvoice.setDueAmount(String.valueOf(convertedAmount1));
	        }	
	    }else{
	    	parentInvoice.setCurrencyCode(invoice.getCurrencyCode());
	    	parentInvoice.setDueAmount(invoice.getDueAmount());
	    	parentInvoice.setTotal(invoice.getTotal());
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
		parentInvoice.setInvoiceStatus(Constants.INVOICE_RECEIVED);
		parentInvoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		parentInvoice.setPaymentStatus(invoice.getPaymentStatus());
		parentInvoice.setDueDate(invoice.getDueDate());
		if(invoice.getProjectID() !=null){
			parentInvoice.setProjectID(invoice.getProjectID());
		}
		parentInvoice.setStatus(Constants.RECORD_ACTIVE.toString());
		parentInvoice.setCreatedDate(invoice.getCreatedDate());
		parentInvoice.setCreatedBy(invoice.getCreatedBy());
		invoiceDao.saveInvoce(parentInvoice);
	}
	/**
	 * preparing invoice Documents
	 * @param invoiceDTO
	 * @param user
	 * @param orgLocation
	 * @param invoice
	 * @param attachedfileList
	 * @return Set<InvoiceDocument>
	 * @throws Exception
	 */
	private Set<InvoiceDocument> getInvoiceDocuments(InvoiceDTO invoiceDTO, User user, OrgLocations orgLocation,
			Invoice invoice) throws Exception {

		Set<InvoiceDocument> invoiceDocumentSet = new HashSet<>();

		if (invoiceDTO.getFileInfo() != null && invoiceDTO.getFileInfo().size() > 0) {
			List<FileInfo> fileInfoList = invoiceDTO.getFileInfo();
			for (FileInfo fileInfo : fileInfoList) {
				InvoiceDocument invoiceDocument = new InvoiceDocument();
				invoiceDocument.setFileContentType(fileInfo.getContentType());
				invoiceDocument.setFileName(fileInfo.getFileName());
				invoiceDocument.setFileUrl(fileInfo.getFileURL());
				invoiceDocument.setStatus(Constants.RECORD_ACTIVE.toString());
				invoiceDocument.setCreatedBy(user.getUserCode());
				invoiceDocument.setUpdatedBy(user.getUserCode());
				invoiceDocument.setCreatedDate(new Date());
				invoiceDocument.setUpdatedDate(new Date());
				invoiceDocument.setOrgLocation(orgLocation);
				invoiceDocument.setInvoice(invoice);
				invoiceDocumentSet.add(invoiceDocument);
			}
		}
		return invoiceDocumentSet;
	}

	private Set<InvoiceTax> getInvoiceTax(User user, OrgLocations orgLocation, Invoice invoice,
			List<Long> invoiceTaxItemsIdsList, InvoiceDTO invoiceDTO) throws Exception {
		Set<InvoiceTax> invoiceTaxSet = new HashSet<>();
		List<Long> updatedtaxLineItems = new ArrayList<Long>();
		if (invoiceDTO.getInvoiceTax() != null && !invoiceDTO.getInvoiceTax().isEmpty()) {
			for (InvoiceTaxDTO invoiceTaxDTO : invoiceDTO.getInvoiceTax()) {
				if (StringUtils.isNotBlank(invoiceTaxDTO.getTaxType())) {
					if (StringUtils.isNotBlank(invoiceTaxDTO.getTaxType())
							|| StringUtils.isNotBlank(invoiceTaxDTO.getTaxdiscountType())
							|| StringUtils.isNotBlank(invoiceTaxDTO.getTaxdiscountvalue())
							|| StringUtils.isNotBlank(invoiceTaxDTO.getTaxTotal())) {

						InvoiceTax invoiceTax = new InvoiceTax();

						if (StringUtils.isNotBlank(invoiceTaxDTO.getInvoiceTaxId())) {
							invoiceTax.setId(Long.valueOf(invoiceTaxDTO.getInvoiceTaxId()));
							updatedtaxLineItems.add(Long.valueOf(invoiceTaxDTO.getInvoiceTaxId()));
						}
						if (StringUtils.isNotBlank(invoiceTaxDTO.getTaxType())) {
							invoiceTax.setTaxName(invoiceTaxDTO.getTaxType());
						}
						if (StringUtils.isNotBlank(invoiceTaxDTO.getTaxdiscountType())) {
							invoiceTax.setTaxType(invoiceTaxDTO.getTaxdiscountType());
						}
						if (StringUtils.isNotBlank(invoiceTaxDTO.getTaxdiscountvalue())) {
							invoiceTax.setTaxValue(invoiceTaxDTO.getTaxdiscountvalue());
						}
						if (StringUtils.isNotBlank(invoiceTaxDTO.getTaxTotal())) {
							invoiceTax.setTaxAmount(invoiceTaxDTO.getTaxTotal());
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

			if (null != invoice.getId()) {
				updatedtaxLineItems.retainAll(invoiceTaxItemsIdsList);
				invoiceTaxItemsIdsList.removeAll(updatedtaxLineItems);
			}
		}

		return invoiceTaxSet;
	}
	
	
	private Set<InvoiceLineItems> getInvoiceLineItems(User user, OrgLocations orgLocation, Invoice invoice,List<Long> invoiceLineItemsIdsList, List<String> submissionApproverHierarchyIds,List<TimesheetSubmissionsDocuments> docusList,InvoiceDTO invoiceDTO) throws Exception {
		 Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();
	        
		 //String pattern = "dd-MM-yyyy";
	     //SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
	     
	     List<Long> updatedInvoiceLineItems = new ArrayList<Long>();
		
//		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxTsRowNum"));
//	     if(invoiceDTO.getLineItems() !=null && !invoiceDTO.getLineItems().isEmpty()) {
		 for(InvoiceLineItemsDTO invoiceLineItemsDTO : invoiceDTO.getLineItems()){
			 
			 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceStartDate())){
			 
				 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceStartDate()) || StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceEndDate()) || StringUtils.isNotBlank(invoiceLineItemsDTO.getCandidate()) || StringUtils.isNotBlank(invoiceLineItemsDTO.getSow()) || StringUtils.isNotBlank(invoiceLineItemsDTO.getRate()) || StringUtils.isNotBlank(invoiceLineItemsDTO.getTotalHours())){
					 
					 InvoiceLineItems  invoiceLineItems  = new InvoiceLineItems();
									 
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getLineItemId())){
						 invoiceLineItems.setId(Long.valueOf(invoiceLineItemsDTO.getLineItemId()));
						 updatedInvoiceLineItems.add(Long.valueOf(invoiceLineItemsDTO.getLineItemId()));
					 }
					 
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceStartDate())){
						 Date invoiceStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),invoiceLineItemsDTO.getInvoiceStartDate());
						 invoiceLineItems.setStartDate(invoiceStartDate);
					 }else{
						 invoiceLineItems.setStartDate(null);
					 }
					 
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceEndDate())){
						 Date invoiceStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),invoiceLineItemsDTO.getInvoiceEndDate());
						 invoiceLineItems.setEndDate(invoiceStartDate);
					 }else{
						 invoiceLineItems.setEndDate(null);
					 }
					 
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getCandidate())){
						 if(!Constants.UNDEFINED.equalsIgnoreCase(invoiceLineItemsDTO.getCandidate())){
							 Candidate candidate = candidateService.getCandidateById(Long.parseLong(invoiceLineItemsDTO.getCandidate()));
							 invoiceLineItems.setCandidate(candidate);
						 }
					
						 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getUser())){
							 if(!Constants.UNDEFINED.equalsIgnoreCase(invoiceLineItemsDTO.getUser())){
								 User usr = userService.getUserById(Long.parseLong(invoiceLineItemsDTO.getUser()));
								 invoiceLineItems.setUser(usr);
							 } 
						 }
					 }
					 
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getSow())){
						 Contracts contract = contractsTemplateService.getContractById(Long.parseLong(invoiceLineItemsDTO.getSow()));
						 invoiceLineItems.setContract(contract);
					 }else{
						 invoiceLineItems.setContract(null);
					 }
					 
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getRate())){
						 invoiceLineItems.setRate(invoiceLineItemsDTO.getRate());
					 }
					 
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getTotalHours())){
						 invoiceLineItems.setTotalHours(invoiceLineItemsDTO.getTotalHours());
					 }
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getAmount())){
						 invoiceLineItems.setAmount(invoiceLineItemsDTO.getAmount());
					 }else{
						 invoiceLineItems.setAmount(null);
					 }
					 if(StringUtils.isNotBlank(invoiceLineItemsDTO.getDescription())){
						 invoiceLineItems.setInlineDescription(invoiceLineItemsDTO.getDescription());
					 }
					 // TODO dont delete Rajashekar
//					if(jsonObj.has(i+"submissionApproverHierarchyId") && !"".equalsIgnoreCase(jsonObj.getString(i+"submissionApproverHierarchyId"))){
//						submissionApproverHierarchyIds.add(jsonObj.getString(i+"submissionApproverHierarchyId"));
//					 }
					if(StringUtils.isNotBlank(invoiceLineItemsDTO.getSubmissionInvoiceId()) ){
						       List<TimesheetSubmissionsDocuments> documentList = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsBySubmissionId(Long.parseLong(invoiceLineItemsDTO.getSubmissionInvoiceId()));
					           if(null != documentList && documentList.size() >0){
					        	   for(TimesheetSubmissionsDocuments document : documentList){
					        		   docusList.add(document);
					        	   }
					        	  
					           }
					}
					 invoiceLineItems.setOrgLocation(orgLocation);
					 invoiceLineItems.setStatus(Constants.RECORD_ACTIVE.toString());
					 invoiceLineItems.setCreatedBy(user.getUserCode());
					 invoiceLineItems.setCreatedDate(commonUtil.currentDate());
					 invoiceLineItems.setInvoice(invoice);
					 invoiceLineItemsSet.add(invoiceLineItems);
				 }
//			 }
			 
		 }
		 
		 if(null != invoice.getId()){
			 updatedInvoiceLineItems.retainAll(invoiceLineItemsIdsList);
			 invoiceLineItemsIdsList.removeAll(updatedInvoiceLineItems);
		 }
	}
		return invoiceLineItemsSet;
		
	}
	
	
	
	
	
	 public EmailAttachment zipFiles(List<TimesheetSubmissionsDocuments> docusList) throws MessagingException{

         EmailAttachment ema = null;
	        FileOutputStream fos = null;
	        ZipOutputStream zipOut = null;
	        FileInputStream fis = null;
	        try {
	           ByteArrayOutputStream zipBytes = new ByteArrayOutputStream();
		    	 if(docusList != null && !docusList.isEmpty()){
		    		    ema=new EmailAttachment();
		    	         ZipOutputStream outZip = new ZipOutputStream(zipBytes);
		    	         
				    	 int i= 0;
				    	 for(TimesheetSubmissionsDocuments filePath : docusList){
				    		 StringBuilder sb = new StringBuilder();
				    		 String st =filePath.getFileName();
				    		 String lastIndex = st.substring(0,st.lastIndexOf("."));
				    		 String newFilename = (sb.append(lastIndex).append("-").append(i).append(st.substring(st.lastIndexOf("."))).toString());
		    	        	 addToZipFile(filePath.getDocFile(), outZip , newFilename);
		    	        	  i++;
		    	         }
				    	 
				    	 outZip.closeEntry();
		    	         outZip.close();
		    	         DataSource source = new ByteArrayDataSource(zipBytes.toByteArray(), "application/zip" );
		    	         MimeBodyPart messageBodyPart1 = new MimeBodyPart();
		    	         messageBodyPart1.setDataHandler(new DataHandler( source ));
		    	         DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy"); 
		    	         StringBuilder fName= new StringBuilder();
		    	         String fileName = fName.append("resumes").append(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()).append(" ").append(dateFormat.format(new Date())).append(".zip").toString();
		    	         messageBodyPart1.setFileName(fileName);
		    	         
		    	         ema.setName(fileName);
		    	         ema.setDocFile(zipBytes.toByteArray());
		    	         ema.setType("application/zip");
	        	
	        	
		    	 }
	        	
	        	
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } finally{
	            try{
	                if(fos != null) fos.close();
	            } catch(Exception ex){
	                 
	            }
	        }
			return ema;
	    }
	     
	
	        
	        public static void addToZipFile(byte[] by, ZipOutputStream zos , String fileName) throws FileNotFoundException, IOException {
	    	    byte[] content = by;
	    	    if(content != null){
	    	    	 int size = content.length;
	           
	    		        ByteArrayInputStream ins = null;
	    		        ins = new ByteArrayInputStream(content);
	            
	    		        ZipEntry zipEntry = new ZipEntry(fileName); 
	    				zos.putNextEntry(zipEntry);
	    		
	    				final int BUFFER = 4096;
	    				byte[] bytes = new byte[2048];
	    				byte data[] = new byte[BUFFER];
	    		
	    				int length = 0;
	    				while ((length = ins.read(bytes)) >= 0) {
	    					zos.write(bytes, 0, length);
	    				}
	    		
	            }
	    		zos.closeEntry();
	    	}
	        
	
	
	
	public void sendInvoiceMail(User user, OrgLocations orglocations, Invoice invoice,String invoiceType,List<TimesheetSubmissionsDocuments> docusList) throws Exception{

		EmailScenario  emailScenario = null;
		EmailScenario  notificationScenario = null;
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

		 String date = (commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),invoice.getDueDate()));

		String invoiceDate= (commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),invoice.getInvoiceDate()));

		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("invoiceID",invoice.getInvoiceId());
		 globalVarsMap.put("LoginUser",user.getFirstName());
		 globalVarsMap.put("clientName",invoice.getContactUser().getFirstName());
		 globalVarsMap.put("grandTotal",invoice.getTotal());
		 globalVarsMap.put("dueDate",date); 
		 globalVarsMap.put("invoiceDate",invoiceDate); 
		 globalVarsMap.put("orgName",orglocations.getOrgLocationName());
		 //globalVarsMap.put("address",invoice.getBillingAddress());
		 
		/* StringBuilder acceptBuilder = new StringBuilder();
		 globalVarsMap.put("acceptLink",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptedInvoice?invoiceId=").append(invoice.getId()).append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
	        
		StringBuilder  rejectBuilder = new StringBuilder();
	     globalVarsMap.put("rejectLink",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/").append("rejectInvoice?invoiceId=").append(invoice.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
   */
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 if(invoiceType.equalsIgnoreCase("payable")){
			   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_PAYABLE_MAIL,Constants.ENTITY_TYPE_EMAIL);
			   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_PAYABLE_MAIL,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else{
			   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_RECEIVABLE_MAIL,Constants.ENTITY_TYPE_EMAIL);
			   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_RECEIVABLE_MAIL,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		
		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orglocations.getOrg(),orglocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 ArrayList<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
		 EmailAttachment emailAttachment = new EmailAttachment();
		 emailAttachment.setUUID(UUID);
		 EmailAttachment attachmentZip = zipFiles(docusList);
		 if(null != invoice.getInvoiceDocumentSet() && invoice.getInvoiceDocumentSet().size() > 0 && !invoice.getInvoiceDocumentSet().isEmpty()){
			 Set<InvoiceDocument> documenetSet = invoice.getInvoiceDocumentSet();
			 for(InvoiceDocument doc:documenetSet){
				 EmailAttachment docAttachment = new EmailAttachment();
				 docAttachment.setDocFile(readBytesFromFile(staticPath+doc.getFileUrl()));
				 docAttachment.setName(doc.getFileName());
				 documentList.add(docAttachment);
			 }
		 }
		 if(StringUtils.isNotBlank(invoice.getFileUrl()))
		 emailAttachment.setDocFile(readBytesFromFile(staticPath+invoice.getFileUrl()));
		 if(StringUtils.isNotBlank(invoice.getFileName()))
		 emailAttachment.setName(invoice.getFileName());
		 attachmentList.add(emailAttachment);

		 if(null != attachmentZip){
			 attachmentList.add(attachmentZip);
		 }
		 if(null != documentList && !documentList.isEmpty() && documentList.size() > 0){
		 attachmentList.addAll(documentList);
		 }
		 //calling the sendEmail method of emailScenerioUtil
		 
		 emailScenerioUtil.sendEmail(emailMessage,orglocations.getId(),iAdressArray,attachmentList);
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, user, invoice.getContactUser(), invoice.getResponderLocationId(), notificationScenario);
	}


	/*@Override
	public void getPdfFileForInvoice(Invoice invoice,OrgLocations orgLocation,User user, Org org) throws Exception {
		invoicePdfHelper.createPdfTableForInvoice(invoice,orgLocation,user,org);
	}*/
	
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
		//String pattern = "dd-MM-yyyy";
        //SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
        
        
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
			Date invoiceDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("invoiceDate"));
			invoice.setInvoiceDate(invoiceDate);
		}
		if(jsonObj.has("dueDate") && !"".equalsIgnoreCase(jsonObj.getString("dueDate"))){
			Date dueDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("dueDate"));
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
	        
		 //String pattern = "dd-MM-yyyy";
	     //SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
	     
		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxRowNum"));
		 for(int i=1;i<maxRowCount;i++){
			 
			 if(jsonObj.has(i+"sdate")){
			 
				 if(null != jsonObj.getString(i+"sdate") || null != jsonObj.getString(i+"edate") || null != jsonObj.getString(i+"candidateId") ||  null != jsonObj.getString(i+"sow") || null != jsonObj.getString(i+"rate") || null != jsonObj.getString(i+"totalHours") ||
				!"".equalsIgnoreCase(jsonObj.getString(i+"sdate")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"edate")) || !"".equalsIgnoreCase(jsonObj.getString(i+"candidateId")) || !"".equalsIgnoreCase(jsonObj.getString(i+"sow")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"rate")) ||!"".equalsIgnoreCase(jsonObj.getString(i+"totalHours"))){
					 
					 InvoiceLineItems  invoiceLineItems = new InvoiceLineItems();
					 
					 if(null != jsonObj.getString(i+"sdate") && !"".equalsIgnoreCase(jsonObj.getString(i+"sdate"))){
						 Date invoiceStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString(i+"sdate"));
						 invoiceLineItems.setStartDate(invoiceStartDate);
					 }
					 if(null != jsonObj.getString(i+"edate") && !"".equalsIgnoreCase(jsonObj.getString(i+"edate"))){
						 Date invoiceStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString(i+"edate"));
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
	public void updateSubmissionApproverHiearachy(List<String> submissionApproverHierarchyIds, String invoiceFrequency,String invoiceType) throws Exception
	{
		if(null != submissionApproverHierarchyIds && submissionApproverHierarchyIds.size() >0){
			for(String id :submissionApproverHierarchyIds){
				if(!Constants.UNDEFINED.equalsIgnoreCase(id) && id !=null && !"".equalsIgnoreCase(id)){
					 submissionApproverHierarchyService.updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long.parseLong(id), invoiceType, Constants.SENT);
				}
			}
		}
	}
	@Override
	public CurrencyConversionResponse getExchangeCurrency(String loginOrgCurrencyCode, OrgLocations orgLocation, Long orgRelationId, String grandTotal) throws Exception
	{
		CurrencyConversionResponse currencyConversionResponse = null;
		OrgLocations vendorOrgLocation = null;
		OrgRelations orgRelation = orgRelationsService.getOrRelationsById(orgRelationId);
		if (orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			vendorOrgLocation = orgRelation.getLocationId();
		}
		if (orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			vendorOrgLocation = orgRelation.getResponderLocationId();
		}
		// currency conversion
		String vendorCurrencyCode = conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if (!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)) {
			if (null != vendorCurrencyCode) {
				currencyConversionResponse = new CurrencyConversionResponse();
				float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, Float.parseFloat(grandTotal));
				float exchangeRate = conversionUtil.convert(loginOrgCurrencyCode,vendorCurrencyCode, 1);
				float exchangeCurrency = 	(float) (Math.round(convertedAmount * 100.0) / 100.0);
				currencyConversionResponse.setExchangeRate(exchangeRate);
				currencyConversionResponse.setExchangeCurrency(exchangeCurrency);
				currencyConversionResponse.setVendorCurrencyCode(vendorCurrencyCode);
			}
		}
		return currencyConversionResponse;
	}
	
	
	@Override
	public File getExportBillingFile(List<Invoice> invoiceList,String orgName, String exportType,InvoiceAmount totalStagesAmount,String exportStartDate,String exportEndDate,String invoiceType,OrgLocations orgLocations) throws Exception {
		
		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			//DateFormat dateFormat = LoginSessionController.DATEFORMAT;
			Sheet createExcelsheet = createExcelworkbook.createSheet(orgName + "_" + commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));
			Row writeRow = null;
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,totalStagesAmount,orgName,exportStartDate,exportEndDate,invoiceType);
			int column = 0;
			for (Invoice invoice : invoiceList) {
				writeRow = createExcelsheet.createRow(column + 10);
				createExcelsheet = createXlsSheet(createExcelsheet, writeRow, invoice);
				column++;
			}
			file = File.createTempFile(orgName + Constants.BILLING + commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()),".xls");
			 file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			file = invoicePdfHelper.getBillingPdf(invoiceList,orgName,totalStagesAmount,exportStartDate,exportEndDate,invoiceType,orgLocations);
		}
			return file;
	}
	
	
private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, Invoice invoice) {
		
	
	if(null != invoice.getInvoiceId() && !"".equalsIgnoreCase(invoice.getInvoiceId())){
		writeRow.createCell(0).setCellValue(invoice.getInvoiceId());
	}
	
	OrgLocations orgLocations = null;
	if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == invoice.getOrgLocation().getId().longValue()){
		orgLocations = invoice.getOrgRelationID().getLocationId();
	}else if(invoice.getOrgRelationID().getLocationId().getId().longValue() == invoice.getOrgLocation().getId().longValue()){
		orgLocations = invoice.getOrgRelationID().getResponderLocationId();
	}
	if(null != orgLocations){
		writeRow.createCell(1).setCellValue(orgLocations.getOrgLocationName());
	}
	
	if(null != invoice.getContactUser()){
		if(null != invoice.getContactUser().getLastName() && !"".equalsIgnoreCase(invoice.getContactUser().getLastName())){
		StringBuilder contactName = new StringBuilder();
		writeRow.createCell(2).setCellValue(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());
	}else if(null != invoice.getContactUser().getFirstName()){
		writeRow.createCell(2).setCellValue(invoice.getContactUser().getFirstName());
	}
	}
	
	if(null != invoice.getContactUser()){
		if(null != invoice.getContactUser().getEmail() && !"".equalsIgnoreCase(invoice.getContactUser().getEmail())){
		writeRow.createCell(3).setCellValue(invoice.getContactUser().getEmail()); 
	}
	}
	
	if(null != invoice.getContactUser()){
		if(null != invoice.getContactUser().getMobilePhone() && !"".equalsIgnoreCase(invoice.getContactUser().getMobilePhone())){
		 writeRow.createCell(4).setCellValue(invoice.getContactUser().getMobilePhone());
	}
	}
	
	if(null != invoice.getInvoiceDate()){
		String invoiceDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),invoice.getInvoiceDate());
		 writeRow.createCell(5).setCellValue(invoiceDate);
	}
	
	if(null != invoice.getDueDate()){
		String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),invoice.getDueDate());
		writeRow.createCell(6).setCellValue(dueDate);  
	}
	
	if(null != invoice.getTotal() && !"".equalsIgnoreCase(invoice.getTotal())){
		writeRow.createCell(7).setCellValue(invoice.getTotal());
	}
	
	if(null != invoice.getDueAmount() && !"".equalsIgnoreCase(invoice.getDueAmount())){
		writeRow.createCell(8).setCellValue(invoice.getDueAmount());
	}
	
	if(null != invoice.getDueAmount() && !"".equalsIgnoreCase(invoice.getDueAmount()) ){
		Double paidAmount = Double.parseDouble(invoice.getTotal()) - Double.parseDouble(invoice.getDueAmount());
		String convertedPaidAmount = String.valueOf(paidAmount);
		writeRow.createCell(9).setCellValue(convertedPaidAmount);
	}
	
	if(null != invoice.getInvoiceStatus() && !"".equalsIgnoreCase(invoice.getInvoiceStatus())){
		 writeRow.createCell(10).setCellValue(invoice.getInvoiceStatus());
	}
	
	if(null != invoice.getPaymentStatus() && !"".equalsIgnoreCase(invoice.getPaymentStatus())){
		writeRow.createCell(11).setCellValue(invoice.getPaymentStatus());
	}
	
	if(null != invoice.getLinkInvoiceRefInvoiceId() && !"".equalsIgnoreCase(String.valueOf(invoice.getLinkInvoiceRefInvoiceId()))){
		writeRow.createCell(12).setCellValue(String.valueOf(invoice.getLinkInvoiceRefInvoiceId()));
	}
		
	return createExcelsheet;
	}

private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, InvoiceAmount totalStagesAmount, String orgName,String exportStartDate,String exportEndDate, String invoiceType) {
	
	CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

	writeRow = createExcelsheet.createRow(1);
	createExcelsheet.setColumnWidth(0, 10000);
	createExcelsheet.setColumnWidth(1, 10000);

	Cell cell1 = writeRow.createCell(0);
	cell1.setCellValue("Company name :");
	cell1.setCellStyle(style);
	Cell cell2 = writeRow.createCell(1);
    cell2.setCellValue(orgName);
	cell2.setCellStyle(style);

	writeRow = createExcelsheet.createRow(2);
	createExcelsheet.setColumnWidth(0, 10000);
	createExcelsheet.setColumnWidth(1, 10000);

	Cell cell3 = writeRow.createCell(0);
	cell3.setCellValue("Duration :");
	cell3.setCellStyle(style);

	Cell cell4 = writeRow.createCell(1);
	cell4.setCellValue(exportStartDate+ " - " +exportEndDate);
	cell4.setCellStyle(style);

	writeRow = createExcelsheet.createRow(3);
	createExcelsheet.setColumnWidth(0, 10000);
	createExcelsheet.setColumnWidth(1, 10000);

	Cell cell5 = writeRow.createCell(0);
	cell5.setCellValue("Total Unpaid Amount(Open) : ");
	cell5.setCellStyle(style);

	Cell cell6 = writeRow.createCell(1);
	cell6.setCellValue(totalStagesAmount.getTotalOpenUnpaidAmount());
	cell6.setCellStyle(style);
	
	writeRow = createExcelsheet.createRow(4);
	createExcelsheet.setColumnWidth(0, 10000);
	createExcelsheet.setColumnWidth(1, 10000);

	Cell cell7 = writeRow.createCell(0);
	cell7.setCellValue("Total Unpaid Amount(OverDue) : ");
	cell7.setCellStyle(style);

	Cell cell8 = writeRow.createCell(1);
	cell8.setCellValue(totalStagesAmount.getTotalOverDueUnpaidAmount());
	cell8.setCellStyle(style);
	
	
	writeRow = createExcelsheet.createRow(5);
	createExcelsheet.setColumnWidth(0, 10000);
	createExcelsheet.setColumnWidth(1, 10000);

	Cell cell9 = writeRow.createCell(0);
	cell9.setCellValue("Total Paid Amount : ");
	cell9.setCellStyle(style);

	Cell cell10 = writeRow.createCell(1);
	cell10.setCellValue(totalStagesAmount.getTotalPaidAmount());
	cell10.setCellStyle(style);
	
	writeRow = createExcelsheet.createRow(6);
	createExcelsheet.setColumnWidth(0, 10000);
	createExcelsheet.setColumnWidth(1, 10000);

	Cell cell11 = writeRow.createCell(0);
	cell11.setCellValue("Total Unbilled Amount : ");
	cell11.setCellStyle(style);

	Cell cell12 = writeRow.createCell(1);
	cell12.setCellValue(totalStagesAmount.getTotalUnbilledAmount());
	cell12.setCellStyle(style);
	
	String[] headers = null;
	if(Constants.Payables.equalsIgnoreCase(invoiceType)){
		 headers  = Constants.BILLING_PAYABLE_HEADERS;
	}else{
		 headers  = Constants.BILLING_HEADERS;
	}
	createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,8);
	return createExcelsheet;
}

	@Override
	public Integer getAutomaticInvoice(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType,User user,String contractType) throws Exception
	{
		int count =0;
		List<OrgRelations> clientVendorList = null;
		OrgLocations vendorOrgLocation = null;
		if(Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)){
			clientVendorList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		}else{
			clientVendorList = getVendorsList(orgLocation);
		}
		User contactUser = null;
		for (OrgRelations client : clientVendorList) {
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(client.getId());
			if (orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				vendorOrgLocation = orgRelation.getLocationId();
//				populateClientDetails(vendorOrgLocation,invoice);
			}
			if (orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				vendorOrgLocation = orgRelation.getResponderLocationId();
//				populateClientDetails(vendorOrgLocation,invoice);
			}
			List<Projects> projectsList = null;
			if(Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)){
				projectsList = projectsService.getClientProjectsByOrgRelationId(client.getId(),orgLocation.getId());
			}else{
				projectsList = projectsService.getAllProjectsByOrgLocationIdAndStatus(orgLocation.getId());
			}
			String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
  			if(null != projectsList && projectsList.size() >0){
  				for(Projects project : projectsList){
  	  				List<String> frequencyDates = getProjectsFrequency(project);
  	  				for(String frequency :frequencyDates){
  	  				   Invoice invoice = new Invoice();
  	  					List<ProjectMembers> projectMembersList = null;
  	  					if(Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)){
  	  						 projectMembersList = getClientProjectAssinedMembersAutoInvoice(frequency,orgLocation,project.getId(),client.getId(),contractType,invoice);
  	  					}else{
  	  						 projectMembersList = getVendorProjectAssinedMembersAutoInvoice(frequency,orgLocation,project.getId(),client.getId(),contractType,invoice);
  	  					}
  	  					if(null != projectMembersList && projectMembersList.size() >0){
  	  						populateClientDetails(vendorOrgLocation,invoice);
  	  						invoice.setOrgRelationID(orgRelation);
  	  						invoice.setOrgLocation(orgLocation);
  	  						invoice.setInvoiceType(invoiceType);
  	  						invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
  	  						invoice.setInvoiceId(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode());
  	  						Set<InvoiceLineItems> invoiceLineItemSet = populateInvoiceInlineItems(projectMembersList,orgLocation,client.getId(),loginOrgCurrencyCode,invoice,user);
  	  						invoice.setInvoiceLineItems(invoiceLineItemSet);
  	  						invoice.setConvertCurrencyCode(vendorCurrencyCode);
  	  						invoice.setCurrencyCode(loginOrgCurrencyCode);
  	  						invoice.setCreatedBy(user.getUserCode());
  	  						invoice.setStatus(Constants.RECORD_ACTIVE.toString());
  	  						invoice.setInvoiceStatus(Constants.DRAFT);
  	  						invoice.setUpdatedBy(user.getUserCode());
  	  						invoice.setCreatedDate(commonUtil.currentDate());
  	  						invoice.setPaymentStatus("Unpaid");
  	  						invoice.setProjectID(project);
  	  						invoice.setDiscountType(Constants.PERCENTAGE);
  	  						invoice.setIsAutomaticGenerateInvoice(Constants.YES);
  	  						float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, Float.parseFloat(invoice.getTotal()));
  	  						float exchangeRate = conversionUtil.convert(loginOrgCurrencyCode,vendorCurrencyCode,1);
  	  		  		      	float roundAmount = 	(float) (Math.round(convertedAmount * 100.0) / 100.0);
  	  		  		      	invoice.setConvertCurrencyCode(vendorCurrencyCode);
  	  		  		      	invoice.setConvertAmount(String.valueOf(roundAmount));
  	  		  		      	invoice.setExchangeRate(String.valueOf(exchangeRate));
  	  		  		      	invoice.setInvoiceFrequency(frequency);
  	  		  		        Invoice savedInvoice = invoiceDao.saveInvoce(invoice);
  	  		  		        updateSubmissionApproverHierachy(savedInvoice.getSubmissionApproverHierarchyIds(),savedInvoice.getInvoiceFrequency(),invoiceType);
  	  		  		        if(savedInvoice !=null){
  	  		  		         count++;
  	  		  		        }
  	  		  		       
  	  					}
  	  					
  	  				} 
  	  			}
  			}
		}
		return count;
	}
	
	/**
	 * used to update submission approver hieararchy
	 * 
	 * @param submissionApproverHierarchyIds
	 * @param invoiceFrequency
	 * @param invoiceType
	 * @throws Exception
	 */
	private void updateSubmissionApproverHierachy(List<String> submissionApproverHierarchyIds, String invoiceFrequency, String invoiceType) throws Exception
	{
		
		if(null != submissionApproverHierarchyIds && submissionApproverHierarchyIds.size() >0){
			for(String id :submissionApproverHierarchyIds){
				if(!Constants.UNDEFINED.equalsIgnoreCase(id) && id !=null && !"".equalsIgnoreCase(id)){
					 submissionApproverHierarchyService.updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long.parseLong(id), invoiceType, Constants.SENT);
				}
			}
		}
		/*if (null != submissionApproverHierarchyIds && submissionApproverHierarchyIds.size() > 0) {
			for (String id : submissionApproverHierarchyIds) {
				SubmissionApproverHierarchy submissionApproverHierarchy = submissionApproverHierarchyService.getSubmissionApproverHierarchy(id);
				if (Constants.PAYABLES.equalsIgnoreCase(invoiceType)) {
					submissionApproverHierarchy.setBillingStatus(Constants.SENT);
				}
				else {
					submissionApproverHierarchy.setInvoiceStatus(Constants.SENT);
				}
				submissionApproverHierarchyService.addSubmissionApproverHierarchy(submissionApproverHierarchy);
			}
		}*/
	}
	
	/**
	 * to get all vendor list
	 * 
	 * @param orgLocation
	 * @return List<OrgRelations>
	 */
	private List<OrgRelations> getVendorsList(OrgLocations orgLocation)
	{
		List<OrgRelations> vendorsList = new ArrayList<>();
		List<OrgRelations> vendorAllList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
		if (null != vendorAllList) {
			for (OrgRelations orgRelations : vendorAllList) {
				String orgName = "";
				if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					orgName = orgRelations.getLocationId().getOrgLocationName();
				}
				if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					orgName = orgRelations.getResponderLocationId().getOrgLocationName();
				}
				orgRelations.setOrgName(orgName);
				vendorsList.add(orgRelations);
			}
		}
		return vendorsList;
	}
	
	/**
	 * population Invoiceline items
	 * 
	 * @param projectMembersList
	 * @param orgLocation
	 * @param orgRelationId
	 * @param loginOrgCurrencyCode
	 * @param invoice
	 * @param user
	 * @return Set<InvoiceLineItems>
	 * @throws Exception
	 */
	private Set<InvoiceLineItems> populateInvoiceInlineItems(List<ProjectMembers> projectMembersList, OrgLocations orgLocation, Long orgRelationId, String loginOrgCurrencyCode, Invoice invoice, User user) throws Exception
	{
		Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();
		List<String> submissionApproverHiearachyIds = new ArrayList<>();
		float subTotal = 0;
		for (ProjectMembers projectMember : projectMembersList) {
			String rate = null;
			String ratePer = null;
			String rateCurrencyType = null;
			InvoiceLineItems invoiceLineItems = new InvoiceLineItems();
			invoiceLineItems.setStartDate(conversionUtil.getDateFromString(projectMember.getStartDate()));
			invoiceLineItems.setEndDate(conversionUtil.getDateFromString(projectMember.getEndDate()));
			if(projectMember.getCandidateId() !=null){
				Candidate candidate = candidateService.getCandidateByCandidateId(projectMember.getCandidateId());
				invoiceLineItems.setCandidate(candidate);
				List<Contracts> contractsList = projectMember.getContractsList();
				if (null != contractsList && contractsList.size() > 0) {
					Contracts contract = contractsList.get(0);
					invoiceLineItems.setContract(contract);
					rate = contract.getBillingAmount();
					ratePer = contract.getBillingRatePer();
					rateCurrencyType = contract.getBillingRateType();
				}
			}else{
				if (projectMember.getUserId() !=null) {
					User usr = userService.getUserById(Long.parseLong(projectMember.getUserId()));
					invoiceLineItems.setUser(usr);
				}
			}
			
			if (null != projectMember.getTotalHours()) {
				invoiceLineItems.setTotalHours(projectMember.getTotalHours());
			}
			if (null != rate && null != projectMember.getTotalHours()) {
				float totalAmount = getTotalAmount(rate, projectMember.getTotalHours(), loginOrgCurrencyCode, ratePer, rateCurrencyType,invoiceLineItems);
				invoiceLineItems.setAmount(String.valueOf(totalAmount));
				subTotal = subTotal + totalAmount;
			}
			invoiceLineItems.setOrgLocation(orgLocation);
			invoiceLineItems.setStatus(Constants.RECORD_ACTIVE.toString());
			invoiceLineItems.setCreatedBy(user.getUserCode());
			invoiceLineItems.setCreatedDate(commonUtil.currentDate());
			invoiceLineItems.setInvoice(invoice);
			invoiceLineItemsSet.add(invoiceLineItems);
			submissionApproverHiearachyIds.add(String.valueOf(projectMember.getSubmissionApproverHierarchyId()));
		}
		 if(submissionApproverHiearachyIds !=null && submissionApproverHiearachyIds.size() >0){
	        	String submissionIds = submissionApproverHiearachyIds.stream().map(Object::toString).collect(Collectors.joining(","));
	        	invoice.setSubmissionApproverHierarchyId(submissionIds);
	        }
		invoice.setSubTotal(String.valueOf(subTotal));
		invoice.setDueAmount(String.valueOf(subTotal));
		invoice.setTotal(String.valueOf(subTotal));
		invoice.setSubmissionApproverHierarchyIds(submissionApproverHiearachyIds);
		return invoiceLineItemsSet;
	}
	
	/**
	 * calculating total amount
	 * 
	 * @param billingAmount
	 * @param totalHours
	 * @param loginOrgCurrencyCode
	 * @param billingRatePer
	 * @param rateCurrencyType
	 * @param invoiceLineItems 
	 * @return float
	 */
	private float getTotalAmount(String billingAmount, String totalHours, String loginOrgCurrencyCode, String billingRatePer, String rateCurrencyType, InvoiceLineItems invoiceLineItems)
	{
		float hourlyRate = conversionUtil.getHourlyRate(billingAmount, billingRatePer);
		float roundValue = 0;
		if (!loginOrgCurrencyCode.equalsIgnoreCase(rateCurrencyType)) {
			float coversionRate = conversionUtil.convert(rateCurrencyType, loginOrgCurrencyCode, hourlyRate);
			roundValue = (float) (Math.round(coversionRate * 100.0) / 100.0);
		}
		else {
			roundValue = (float) (Math.round(hourlyRate * 100.0) / 100.0);
		}
		invoiceLineItems.setRate(String.valueOf(roundValue));
		String[] split = totalHours.split(":");
		int splitHours = Integer.parseInt(split[0]);
		int hourminits = splitHours * 60;
		int minute = Integer.parseInt(split[1]);
		int finalMin = hourminits + minute;
		float perMinit = roundValue / 60;
		float totlalAmount = (perMinit * finalMin);
		float finalRoundAmount = (float) (Math.round(totlalAmount * 100.0) / 100.0);
		return finalRoundAmount;
	}
	
	/**
	 * Get Client Projects assigned members
	 * 
	 * @param frequency
	 * @param orgLocation
	 * @param projectId
	 * @param orgRelationId
	 * @param contractType
	 * @param invoice
	 * @return List<ProjectMembers>
	 * @throws Exception
	 */
	private List<ProjectMembers> getClientProjectAssinedMembersAutoInvoice(String frequency, OrgLocations orgLocation, Long projectId, Long orgRelationId, String contractType, Invoice invoice) throws Exception
	{
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
//		String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> projectMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		invoice.setInvoiceFrequency(frequency);
		if (null != projectId) {
			sahList = submissionApproverHierarchyService.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(projectId, startDate, endDate,contractType);
		}
		if (null != sahList && sahList.size() > 0) {
			for (SubmissionApproverHierarchy sah : sahList) {
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
				projectMember.setSubmissionApproverHierarchyId(sah.getId());
				StringBuilder candidateName = new StringBuilder();
				if (null != projectMember.getEmployee().getUser().getFirstName()
						&& null != projectMember.getEmployee().getUser().getLastName()) {
					candidateName.append(projectMember.getEmployee().getUser().getFirstName()).append(" ").append(projectMember.getEmployee().getUser().getLastName());
				}
				else {
					candidateName.append(projectMember.getEmployee().getUser().getFirstName());
				}
				projectMember.setCandidateName(candidateName.toString());
				projectMember.setUserId(String.valueOf(projectMember.getEmployee().getUser().getId()));
				if (null != projectMember.getEmployee().getUser().getId()) {
					Candidate candidate = candidateService.getCandidateByConsultantUserId(projectMember.getEmployee().getUser().getId(), orgLocation.getId());
					if (null != candidate) {
						projectMember.setCandidateId(candidate.getId());
						List<Contracts> contractList = getSowContract(candidate.getId(), orgLocation, orgRelationId, contractType);
						if (null != contractList && contractList.size() > 0) {
							projectMember.setContractsList(contractList);
						}
					}
				}
				projectMember.setStartDate(startDateStr);
				projectMember.setEndDate(endDateStr);
				String billableHours = String.valueOf(sah.getBillableHours());
				StringBuilder sb = new StringBuilder();
				if(billableHours != null){
					String arr[] = billableHours.split("\\.");
					if (arr[0].length() == 2) {
						sb.append("0").append(arr[0]);
					}
					else if (arr[0].length() == 3) {
						sb.append(arr[0]);
					}
					else if (arr[0].length() == 1) {
						sb.append("00").append(arr[0]);
					}
					if (arr[1].length() == 1) {
						sb.append(":").append("0").append(arr[1]);
					}
					else {
						sb.append(":").append(arr[1]);
					}
				}else{
					sb.append("0");
				}
				
				projectMember.setTotalHours(sb.toString());
				projectMembersList.add(projectMember);
			}
		}
		return projectMembersList;
	}
	
	/**
	 * To get all vendor projects assigned members
	 * 
	 * @param frequency
	 * @param orgLocation
	 * @param projectId
	 * @param orgRelationId
	 * @param contractType
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	private List<ProjectMembers> getVendorProjectAssinedMembersAutoInvoice(String frequency, OrgLocations orgLocation, Long projectId, Long orgRelationId, String contractType, Invoice invoice) throws Exception
	{
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
//		String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> filteredMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		if (null != projectId) {
			sahList = submissionApproverHierarchyService.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(projectId, startDate, endDate,contractType);
		}
		if (null != sahList && sahList.size() > 0) {
			for (SubmissionApproverHierarchy sah : sahList) {
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
				projectMember.setSubmissionApproverHierarchyId(sah.getId());
				projectMember.setSubmissionInvoiceId(sah.getSubmissionId().getId());
				Employees employee = employeesService.getEmployeByUserIdAndOrgRelationId(projectMember.getEmployee().getUser().getId(),orgRelationId);
//				Candidate candidate = candidateService.getCandidateByConsultantUserIdOrgRelationId(projectMember.getEmployee().getUser().getId(), orgLocation.getId(), orgRelationId);
				if (null != employee) {
					StringBuilder empName = new StringBuilder();
					if (null != projectMember.getEmployee().getUser().getFirstName()
							&& null != projectMember.getEmployee().getUser().getLastName()) {
						empName.append(projectMember.getEmployee().getUser().getFirstName()).append(" ").append(projectMember.getEmployee().getUser().getLastName());
					}
					else {
						empName.append(projectMember.getEmployee().getUser().getFirstName());
					}
					projectMember.setCandidateName(empName.toString());
					projectMember.setUserId(employee.getUser().getId().toString());
					projectMember.setStartDate(startDateStr);
					projectMember.setEndDate(endDateStr);
					String billableHours = String.valueOf(sah.getBillableHours());;
					String arr[] = billableHours.split("\\.");
					StringBuilder sb = new StringBuilder();
					if (arr[0].length() == 2) {
						sb.append("0").append(arr[0]);
					}
					else if (arr[0].length() == 3) {
						sb.append(arr[0]);
					}
					else if (arr[0].length() == 1) {
						sb.append("00").append(arr[0]);
					}
					if (arr[1].length() == 1) {
						sb.append(":").append("0").append(arr[1]);
					}
					else {
						sb.append(":").append(arr[1]);
					}
					projectMember.setTotalHours(sb.toString());
					Candidate candidate = candidateService.getCandidateByConsultantUserIdOrgRelationId(projectMember.getEmployee().getUser().getId(), orgLocation.getId(), orgRelationId);
					if (null != candidate) {
						projectMember.setCandidateId(candidate.getId());
						List<Contracts> contractList = getSowContract(candidate.getId(), orgLocation, orgRelationId, contractType);
						if (null != contractList && contractList.size() > 0) {
							projectMember.setContractsList(contractList);
						}
					}
				
					filteredMembersList.add(projectMember);
				}
			}
		}
		return filteredMembersList;
	}
	
	/**
	 * Get projects frequency
	 * 
	 * @param projects
	 * @return List<String>
	 * @throws Exception
	 */
	private List<String> getProjectsFrequency(Projects projects) throws Exception
	{
		// Projects projects = projectsService.getProjectById(projectId);
		List<String> frequencyDates = new ArrayList<>();
		Map<String, String> map = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projects);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			StringBuffer sb = new StringBuffer();
			sb.append(entry.getKey().replaceAll("/", "-")).append("/").append(entry.getValue().replaceAll("/", "-"));
			frequencyDates.add(sb.toString());
		}
		return frequencyDates;
	}
	
	/**
	 * check give org is client or vendor
	 * 
	 * @param vendorOrgLocation
	 * @param invoice
	 */
	private void populateClientDetails(OrgLocations vendorOrgLocation, Invoice invoice)
	{
		invoice.setResponderLocationId(vendorOrgLocation);
		if (null != vendorOrgLocation.getOrg().getPaymentTerm()
				&& !"".equalsIgnoreCase(vendorOrgLocation.getOrg().getPaymentTerm())) {
			invoice.setPaymentFrequency(vendorOrgLocation.getOrg().getPaymentTerm());
			invoice.setInvoiceDate(new Date());
			Date dueDate = getDueDateByPaymentTerm(new Date(), vendorOrgLocation.getOrg().getPaymentTerm());
			invoice.setDueDate(dueDate);
		}
		if (null != vendorOrgLocation.getOrg().getClientBillingAddress()
				&& !"".equalsIgnoreCase(vendorOrgLocation.getOrg().getClientBillingAddress())) {
			invoice.setBillingAddress(vendorOrgLocation.getOrg().getClientBillingAddress());
		}
	}
	
	/**
	 * Get due date frequency based on invoice date
	 * 
	 * @param invoiceDate
	 * @param paymentTerm
	 * @return Date
	 */
	private Date getDueDateByPaymentTerm(Date invoiceDate, String paymentTerm)
	{
		String[] frequency = paymentTerm.split(" ");
		Calendar c = Calendar.getInstance();
		c.setTime(invoiceDate);
		c.add(Calendar.DATE, Integer.parseInt(frequency[0]));
		return c.getTime();
	}
	
	/**
	 * Get contracts sow based on candidate id
	 * 
	 * @param candidateId
	 * @param orgLocation
	 * @param orgRelationId
	 * @param contractType
	 * @return List<Contracts>
	 * @throws Exception
	 */
	private List<Contracts> getSowContract(Long candidateId, OrgLocations orgLocation, Long orgRelationId, String contractType) throws Exception
	{
		List<Contracts> contractsList = null;
		if (null != orgRelationId) {
			if (Constants.VENDOR_SOW.equalsIgnoreCase(contractType)) {
				contractsList = invoiceDao.getSowcontractListByAssociateId(candidateId, orgLocation.getId(), orgRelationId, Constants.Vendor_SOW);
			}
			else {
				contractsList = invoiceDao.getSowcontractListByAssociateId(candidateId, orgLocation.getId(), orgRelationId, Constants.Client_SOW);
			}
		}
		return contractsList;
	}
	
	@Override
	public List<Invoice> getInvoiceByOrgLocationId(Long orgLocationId,Date startDateUTC, Date endDateUTC,String invoiceTYpe) throws Exception
	{
		return invoiceDao.getInvoiceByOrgLocationId(orgLocationId,startDateUTC, endDateUTC,invoiceTYpe);

	}
	
	@Override
	public List<Invoice> getClientBasedInvoiceLists(Long orgRelationId,Long orgLocationId,String invoiceType)
	{
		return invoiceDao.getClientBasedInvoiceLists(orgRelationId,orgLocationId,invoiceType);
	}
	@Override
	public  Invoice linkInvoice(Invoice invoice) throws Exception
	{
      return invoiceDao.saveInvoce(invoice);		
	}
	
	@Override
	public List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId,Long clientLocationId,String invoiceStatus, String paymentStatus,String invoiceType,OrgLocations orgLocations,String dayWisetype, String customStartDate, String customEndDate) throws Exception
	{
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDateUTC = null;
		Date startDateUTC = null;

		if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(dayWisetype)) {
			String endDate = outputFormatter1.print(zoned);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDate);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		if (null != dayWisetype && !"".equals(dayWisetype)) {
			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(dayWisetype)) {
				String startDate = outputFormatter.print(zoned);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				startDateUTC = TZ_DF_TS.parse(startDateString);
				if (Constants.ONE_MONTH.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}
				else if (Constants.TWO_MONTHS.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
				}
				else if (Constants.THREE_MONTHS.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 90);
				}
				else if (Constants.SIX_MONTHS.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 180);
				}
				else if (Constants.ONE_YEAR.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 365);
				}
			}
		}else {
			if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)
					&& !"undefined".equalsIgnoreCase(customStartDate)
					&& !"undefined".equalsIgnoreCase(customEndDate)) {
				DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
				DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");

				Date startTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
				Date endTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);

				String customStartTimeStr = TZ_DF_CSD.format(startTime);
				String customEndTimeStr = TZ_DF_CED.format(endTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
				startDateUTC = TZ_DF_TS.parse(startDateString);
				endDateUTC = TZ_DF_TS.parse(endDateString);
			}else{
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
			}
		}
		
		List<Invoice> invoiceList =  invoiceDao.getInvoiceByOrgRelationId(orgRelationId,clientLocationId,startDateUTC,endDateUTC,invoiceStatus,paymentStatus,invoiceType);
//      		String currentDate = sdf.format(new Date());
      		Date todayDate = new Date();
      		if (null != invoiceList && invoiceList.size() > 0) {
      			for (Invoice invoice : invoiceList) {
      				Integer draftCount = 0;
      				Integer paidCount = 0;
      				Integer overDueInvoiceCount = 0;
      				Integer openInvoiceCount = 0;
      				if (invoice.getOrgRelationID().getLocationId().getId().longValue() == orgLocations.getId().longValue()) {
      					invoice.setClientName(invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName());
      				}
      				if (invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()) {
      					invoice.setClientName(invoice.getOrgRelationID().getLocationId().getOrgLocationName());
      				}
      				StringBuilder contactName = new StringBuilder();
      				if (null != invoice.getContactUser()) {
      					if (invoice.getContactUser().getLastName() != null) {
      						invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());
      					}
      					else {
      						invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).toString());
      					}
      				}
      				if (null != invoice.getInvoiceDate()) {
      					invoice.setInvoiceDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),invoice.getInvoiceDate()));
      				}
      				if (null != invoice.getDueDate()) {
      					invoice.setDueDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),invoice.getDueDate()));
      				}
      				invoice.setTotal(invoice.getTotal());
      				invoice.setInvoiceStatus(invoice.getInvoiceStatus());
      				invoice.setPaymentStatus(invoice.getPaymentStatus());
      				if (null != invoice.getProjectID()) {
      					invoice.setProjectId(invoice.getProjectID().getId().toString());
      				}
      				if (!isNullOrEmpty(invoice.getInvoiceStatus())) {
      					if (Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
      							|| Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())) {
      						draftCount = draftCount + 1;
      						invoice.setDraftInvoiceAmount(invoice.getDueAmount());
      					}
      					if (!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
      							&& !Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())
      							&& Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
      									|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
      							&& !Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())) {
      						paidCount = paidCount + 1;
      						Float paidAmount = Float.parseFloat(invoice.getTotal())
      								- Float.parseFloat(invoice.getDueAmount());
      						invoice.setPaidInvoiceAmount(paidAmount.toString());
      					}
      					if (!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
      							&& !Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())
      							&& (Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())
      									|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()))) {
      						Date invoiceDueDate = invoice.getDueDate();
      						Float dueAmount = Float.parseFloat(invoice.getDueAmount());
      						if (dueAmount > 0) {
      							if (todayDate.after(invoiceDueDate)) {
      								overDueInvoiceCount = overDueInvoiceCount + 1;
      								invoice.setDueAfterInvoiceAmount(invoice.getDueAmount());
      							}
      							else {
      								openInvoiceCount = openInvoiceCount + 1;
      								invoice.setDueBeforeInvoiceAmount(invoice.getDueAmount());
      							}
      						}
      					}
      				}
      				if (null != invoice.getParentInvoiceId()) {
      					invoice.setInvoiceParentId(invoice.getParentInvoiceId().getId());
      				}
      				invoice.setDraftInvoiceCount(draftCount);
      				invoice.setPaidInvoiceCount(paidCount);
      				invoice.setDueAfterInvoiceCount(overDueInvoiceCount);
      				invoice.setDueBeforeInvoiceCount(openInvoiceCount);
      			}
      		}
			return invoiceList;
	}

	@Override
	public List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long orgLocationId, Date startDateUTC, Date endDateUTC) throws Exception
	{
		return invoiceDao.getInvoiceByOrgLocationIdNetIncome(orgLocationId,startDateUTC,endDateUTC);
	}
	@Override
	public List<Invoice> getDueDateCompletedInvoicesList() throws Exception {
		return invoiceDao.getDueDateCompletedInvoicesList();
	}
	@Override
	public void saveInvocePojo(Invoice invoice) throws Exception {
		invoiceDao.saveInvoce(invoice);
	}
	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}

	@Override
	public BillingReportDTO getBillingReports(String billingReportType, String invoiceType, String startDate,
			String endDate, OrgLocations orgLocation, User user, Long loginUserRoleId) throws Exception {
		DateTimeFormatter startDateOutputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateTimeFormatter endDateOutputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");

		LocalDate today = LocalDate.now();
		LocalDate monthStartDate = new LocalDate().withDayOfMonth(1);
		LocalDate monthEndDate = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
		List<Invoice> invoiceList = null;
		Date startDateUTC = null;
		Date endDateUTC = null;
		LocalDate date1 = null;
		LocalDate date2 = null;
		List<BillingGraphBean> incomeExpenseList = new ArrayList<>();
		switch (billingReportType) {
		case Constants.THIS_MONTH:
			startDateUTC = conversionUtil.getUtcDateFormat(orgLocation, monthStartDate, startDateOutputFormatter);
			endDateUTC = conversionUtil.getUtcDateFormat(orgLocation, monthEndDate, endDateOutputFormatter);
			date1 = new LocalDate(startDateUTC);
			date2 = new LocalDate(endDateUTC);
			break;
		case Constants.THIS_QUARTER:
			LocalDate[] dates = conversionUtil.getThisMonthQuarterDates(monthStartDate);
			startDateUTC = conversionUtil.getUtcDateFormat(orgLocation, dates[0], startDateOutputFormatter);
			endDateUTC = conversionUtil.getUtcDateFormat(orgLocation, dates[1], endDateOutputFormatter);
			date1 = dates[0];
			date2 = dates[1];
			break;
		case Constants.THIS_YEAR:
			LocalDate[] currentYearDates = conversionUtil.getThisYearStartDateEndDate();
			startDateUTC = conversionUtil.getUtcDateFormat(orgLocation, currentYearDates[0], startDateOutputFormatter);
			endDateUTC = conversionUtil.getUtcDateFormat(orgLocation, currentYearDates[1], endDateOutputFormatter);
			date1 = currentYearDates[0];
			date2 = currentYearDates[1];
			break;
		case Constants.LAST_30DAYS:
			Date[] last30Days = conversionUtil.getPreviousDates(30, orgLocation, startDateOutputFormatter,
					endDateOutputFormatter);
			startDateUTC = last30Days[0];
			endDateUTC = last30Days[1];
			date1 = new LocalDate(last30Days[0]);
			date2 = new LocalDate(last30Days[1]);
			;
			break;
		case Constants.LAST_MONTH:
			LocalDate firstOfThisMonth = today.withDayOfMonth(1);
			LocalDate loastMonthStartDate = firstOfThisMonth.minusMonths(1);
			LocalDate lastMonthEndDate = firstOfThisMonth.minusDays(1);
			startDateUTC = conversionUtil.getUtcDateFormat(orgLocation, loastMonthStartDate, startDateOutputFormatter);
			endDateUTC = conversionUtil.getUtcDateFormat(orgLocation, lastMonthEndDate, endDateOutputFormatter);
			date1 = loastMonthStartDate;
			date2 = lastMonthEndDate;
			break;
		case Constants.LAST_QUARTER:
			LocalDate lastQuarterDate = today.minusMonths(3); // 2015-10-24
			LocalDate[] quarterDates = conversionUtil.getThisMonthQuarterDates(lastQuarterDate);
			startDateUTC = conversionUtil.getUtcDateFormat(orgLocation, quarterDates[0], startDateOutputFormatter);
			endDateUTC = conversionUtil.getUtcDateFormat(orgLocation, quarterDates[1], endDateOutputFormatter);
			date1 = quarterDates[0];
			date2 = quarterDates[1];
			break;
		case Constants.LAST_YEAR:
			if (Constants.COUNTRY_INDIA.equalsIgnoreCase(orgLocation.getCountry())) {
				LocalDate finStartDate = conversionUtil.getFinMonthFromDate();
				if (null != finStartDate) {
					LocalDate finEndDate = conversionUtil.getFinMonthEndDate();
					startDateUTC = conversionUtil.getUtcDateFormat(orgLocation, finStartDate, startDateOutputFormatter);
					endDateUTC = conversionUtil.getUtcDateFormat(orgLocation, finEndDate, endDateOutputFormatter);
					date1 = new LocalDate(startDateUTC);
					date2 = new LocalDate(endDateUTC);
					;
				}
			} else {
				LocalDate[] financialDates = conversionUtil.getOtherCountryFinYeraDates();
				startDateUTC = conversionUtil.getUtcDateFormat(orgLocation, financialDates[0],
						startDateOutputFormatter);
				endDateUTC = conversionUtil.getUtcDateFormat(orgLocation, financialDates[1], endDateOutputFormatter);
				date1 = financialDates[0];
				date2 = financialDates[1];
			}
			break;
		case Constants.CUSTOM:
			LocalDate localStartDate = LocalDate.fromDateFields(conversionUtil.getDateFromString(startDate));
			LocalDate localEndDate = LocalDate.fromDateFields(conversionUtil.getDateFromString(endDate));
			startDateUTC = conversionUtil.getUtcDateFormat(orgLocation, localStartDate, startDateOutputFormatter);
			endDateUTC = conversionUtil.getUtcDateFormat(orgLocation, localEndDate, endDateOutputFormatter);
			date1 = localStartDate;
			date2 = localEndDate;
			break;
		default:
			logger.info("No Frequency");
			;
		}
		// End Time conversion

		double receivedAmt = 0;
		double payableAmt = 0;
		double netAmount = 0;
		invoiceList = invoiceDao.getInvoiceByOrgLocationIdNetIncome(orgLocation.getId(), startDateUTC, endDateUTC);
		BillingReportDTO billingReportDTO = new BillingReportDTO();
		boolean isReceivableAccessYes = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocation.getId(),
				21L);
		boolean isPayableAccessYes = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocation.getId(),
				22L);

		for (Invoice invoice : invoiceList) {
			if ((Constants.RECEIVABLE.equalsIgnoreCase(invoice.getInvoiceType())
					&& (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
							|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())))
					&& isReceivableAccessYes) {

				try {
					List<InvoicePayment> invoicePaymentList = invoiceDao.getPaymentHistroyByInvoiceId(invoice.getId());
					if (invoicePaymentList != null && invoicePaymentList.size() > 0) {
						double recievableAmount = invoicePaymentList.stream()
								.mapToDouble(o -> Double.parseDouble(o.getPaymentAmount())).sum();
						receivedAmt = receivedAmt + recievableAmount;
					}

				} catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}

			} else if (isPayableAccessYes) {
				try {
					List<InvoicePayment> invoicePaymentList = invoiceDao.getPaymentHistroyByInvoiceId(invoice.getId());
					if (invoicePaymentList != null && invoicePaymentList.size() > 0) {
						double payableAmount = invoicePaymentList.stream()
								.mapToDouble(o -> Double.parseDouble(o.getPaymentAmount())).sum();
						payableAmt = payableAmt + payableAmount;
					}
				} catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
			}
		}
		List<LocalDate> dates = conversionUtil.getAllDatesBetweenGivenDates(date1, date2);
		for (LocalDate localStartDate : dates) {
			BillingGraphBean billingGraphBean = new BillingGraphBean();
			billingGraphBean.setDate(localStartDate.toString("MMM-yy"));
			double incomeAmount = 0;
			double expensesAmount = 0;
			for (Invoice invoice : invoiceList) {
				LocalDate invoiceDate = new LocalDate(invoice.getInvoiceDate());
				if ((Constants.RECEIVABLE.equalsIgnoreCase(invoice.getInvoiceType())
						&& (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
								|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())))
						&& isReceivableAccessYes) {
					try {
						LocalDate localEndDate = localStartDate.dayOfMonth().withMaximumValue();
						if ((invoiceDate.isAfter(localStartDate) && invoiceDate.isBefore(localEndDate))
								|| (invoiceDate.equals(localStartDate) || invoiceDate.equals(localEndDate))) {
							List<InvoicePayment> invoicePaymentList = invoiceDao
									.getPaymentHistroyByInvoiceId(invoice.getId());
							if (invoicePaymentList != null && invoicePaymentList.size() > 0) {
								double income = invoicePaymentList.stream()
										.mapToDouble(o -> Double.parseDouble(o.getPaymentAmount())).sum();
								incomeAmount = incomeAmount + income;
							}
						}

					} catch (Exception e) {
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
				} else if (isPayableAccessYes) {
					try {
						LocalDate localEndDate = localStartDate.dayOfMonth().withMaximumValue();
						if (invoiceDate.isAfter(localStartDate) && invoiceDate.isBefore(localEndDate)
								|| invoiceDate.equals(localStartDate) || invoiceDate.equals(localEndDate)) {
							List<InvoicePayment> invoicePaymentList = invoiceDao
									.getPaymentHistroyByInvoiceId(invoice.getId());
							if (invoicePaymentList != null && invoicePaymentList.size() > 0) {
								double expenses = invoicePaymentList.stream()
										.mapToDouble(o -> Double.parseDouble(o.getPaymentAmount())).sum();
								expensesAmount = expensesAmount + expenses;
							}
						}
					} catch (Exception e) {
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
				}
			}
			billingGraphBean.setIncome(Math.round(incomeAmount * 100.0) / 100.0);
			billingGraphBean.setExpenses(Math.round(expensesAmount * 100.0) / 100.0);
			incomeExpenseList.add(billingGraphBean);
		}

		netAmount = receivedAmt - payableAmt;
		billingReportDTO.setNetAmount(String.valueOf(Math.round(netAmount * 100.0) / 100.0));
		billingReportDTO.setReceivedAmount(String.valueOf(Math.round(receivedAmt * 100.0) / 100.0));
		billingReportDTO.setPayableAmount(String.valueOf(Math.round(payableAmt * 100.0) / 100.0));
		billingReportDTO.setBillingGraphBean(incomeExpenseList);
		return billingReportDTO;
	}
	public void sendInvoiceReminderMail(User user, OrgLocations orgLocation, Invoice invoice, String invoiceType, List<TimesheetSubmissionsDocuments> docusList) throws Exception
	{
		EmailScenario  emailScenario = null;
		EmailScenario  notificationScenario = null;
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

		 //SimpleDateFormat simpleDateFormat = orgLocation.getLocFullFormDateFormatter();
		 String date = (commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate()));

		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("invoiceID",invoice.getInvoiceId());
		 globalVarsMap.put("LoginUser",user.getFirstName());
		 globalVarsMap.put("clientName",invoice.getContactUser().getFirstName());
		 globalVarsMap.put("grandTotal",invoice.getTotal());
		 globalVarsMap.put("dueDate",date); 
		 globalVarsMap.put("orgName",orgLocation.getOrgLocationName());
		 //globalVarsMap.put("address",invoice.getBillingAddress());
		 
		/* StringBuilder acceptBuilder = new StringBuilder();
		 globalVarsMap.put("acceptLink",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptedInvoice?invoiceId=").append(invoice.getId()).append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
	        
		StringBuilder  rejectBuilder = new StringBuilder();
	     globalVarsMap.put("rejectLink",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/").append("rejectInvoice?invoiceId=").append(invoice.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
   */
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 if(invoiceType.equalsIgnoreCase("payable")){
			   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_PAYABLE_REMINDER_MAIL,Constants.ENTITY_TYPE_EMAIL);
			   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_PAYABLE_REMINDER_MAIL,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else{
			   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_RECEIVABLE_REMINDER_MAIL,Constants.ENTITY_TYPE_EMAIL);
			   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_CLIENT_RECEIVABLE_REMINDER_MAIL,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		
		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 ArrayList<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
		 EmailAttachment emailAttachment = new EmailAttachment();
		 emailAttachment.setUUID(UUID);
		 EmailAttachment attachmentZip = zipFiles(docusList);
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

		 if(attachmentZip !=null){
			 attachmentList.add(attachmentZip);
		 }
		 if(documentList !=null && !documentList.isEmpty() && documentList.size() > 0){
		 attachmentList.addAll(documentList);
		 }
		 //calling the sendEmail method of emailScenerioUtil
		 
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,attachmentList);
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, user, invoice.getContactUser(), invoice.getResponderLocationId(), notificationScenario);
		
	}
	@Override
	public Invoice getInvoicetByParentId(Long invoiceId) throws Exception {
		return invoiceDao.getInvoicetByParentId(invoiceId);
	}
	@Override
	public boolean deleteInvoice(Long invoiceId,String reason,String invoiceType) throws Exception
	{
		boolean result = false;
		Invoice invoice = invoiceDao.getInvoicetById(invoiceId);
		
		if(invoice.getProjectID() != null){
				invoice.setRejectReason(reason);
				invoice.setStatus(Constants.MEMBER_IN_ACTIVE);
				invoiceDao.updateInvoice(invoice);
						 try {
							 if(invoice.getSubmissionApproverHierarchyId() !=null){
								 String[] subIds = invoice.getSubmissionApproverHierarchyId().split(",");
								 for(String sIds :subIds ){
									 submissionApproverHierarchyService.updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long.parseLong(sIds), invoiceType, null); 
								 }
								 	 
							 }
							
						} catch (Exception e) {
							e.printStackTrace();
						}
		}else{
			invoice.setRejectReason(reason);
			invoice.setStatus(Constants.MEMBER_IN_ACTIVE);
			Invoice invoiceData = invoiceDao.updateInvoice(invoice);
			if (invoiceData != null) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public Invoice saveExpenseInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation, String saveType,
			List<MultipartFile> attachedfileList, String invoiceType, Org org, String loginOrgCurrencyCode, String invoiceCategory)
					throws Exception {

		

		Invoice invoice = null;

		if (jsonObj.has("savedInvoiceId") && !isNullOrEmpty(jsonObj.getString("savedInvoiceId"))) {
			invoice = invoiceDao.getInvoicetById(Long.valueOf(jsonObj.getString("savedInvoiceId")));
		} else {
			invoice = new Invoice();
			invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}

		OrgLocations vendorOrgLocation = null;
		;
		if (Constants.PAYABLE.equalsIgnoreCase(invoiceType)) {
			if (jsonObj.has("vendorOrgRelationId") && !"".equalsIgnoreCase(jsonObj.getString("vendorOrgRelationId"))) {
				OrgRelations orgRelation = orgRelationsService
						.getOrRelationsById(Long.parseLong(jsonObj.getString("vendorOrgRelationId")));
				if (orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorOrgLocation = orgRelation.getLocationId();
					invoice.setResponderLocationId(vendorOrgLocation);
				}
				if (orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorOrgLocation = orgRelation.getResponderLocationId();
					invoice.setResponderLocationId(orgRelation.getResponderLocationId());
				}
				invoice.setOrgRelationID(orgRelation);
			}

			if (jsonObj.has("vendorContact") && !"".equalsIgnoreCase(jsonObj.getString("vendorContact"))) {
				User contactUser = userService.getUserById(Long.parseLong(jsonObj.getString("vendorContact")));
				invoice.setContactUser(contactUser);
			}
			invoice.setInvoiceType(Constants.Payables);
		} else {
			if (jsonObj.has("clientOrgRelationId") && !"".equalsIgnoreCase(jsonObj.getString("clientOrgRelationId"))) {
				OrgRelations orgRelation = orgRelationsService
						.getOrRelationsById(Long.parseLong(jsonObj.getString("clientOrgRelationId")));
				if (orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorOrgLocation = orgRelation.getLocationId();
					invoice.setResponderLocationId(vendorOrgLocation);
				}
				if (orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorOrgLocation = orgRelation.getResponderLocationId();
					invoice.setResponderLocationId(orgRelation.getResponderLocationId());
				}
				invoice.setOrgRelationID(orgRelation);
			}

			if (jsonObj.has("clientContact") && !"".equalsIgnoreCase(jsonObj.getString("clientContact"))) {
				User userContactName = userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
				invoice.setContactUser(userContactName);
			}
			invoice.setInvoiceType(Constants.Receivable);
		}

		// currency conversion
		String vendorCurrencyCode = conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if (!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)) {
			if (null != vendorCurrencyCode) {
				float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode,
						Float.parseFloat(jsonObj.getString("grandTotal")));
				float exchangeAmoiunt = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, 1);
				float convertedAmount1 = (float) (Math.round(convertedAmount * 100.0) / 100.0);
				invoice.setConvertCurrencyCode(vendorCurrencyCode);
				invoice.setConvertAmount(String.valueOf(convertedAmount1));
				invoice.setExchangeRate(String.valueOf(exchangeAmoiunt));
			}
		}

		invoice.setOwnerUserId(user);
		invoice.setOrgLocation(orgLocation);
		if (jsonObj.has("invoiceId") && !"".equalsIgnoreCase(jsonObj.getString("invoiceId"))) {
			invoice.setInvoiceId(jsonObj.getString("invoiceId"));
		}
		if (jsonObj.has("billingAddress") && !"".equalsIgnoreCase(jsonObj.getString("billingAddress"))) {
			invoice.setBillingAddress(jsonObj.getString("billingAddress"));
		} else {
			invoice.setBillingAddress(null);
		}

		if (jsonObj.has("accountDetails") && !"".equalsIgnoreCase(jsonObj.getString("accountDetails"))) {
			invoice.setAccountDetails(jsonObj.getString("accountDetails"));
		}

		if (jsonObj.has("invoiceDate") && !"".equalsIgnoreCase(jsonObj.getString("invoiceDate"))) {
			Date invoiceDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("invoiceDate"));
			invoice.setInvoiceDate(invoiceDate);
		}
		if (jsonObj.has("dueDate") && !"".equalsIgnoreCase(jsonObj.getString("dueDate"))) {
			Date dueDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("dueDate"));
			invoice.setDueDate(dueDate);
		}

		if (jsonObj.has("paymentFrequency") && !"".equalsIgnoreCase(jsonObj.getString("paymentFrequency"))) {
			invoice.setPaymentFrequency(jsonObj.getString("paymentFrequency"));
		} else {
			invoice.setPaymentFrequency(null);
		}

		if (jsonObj.has("disCount") && !"".equalsIgnoreCase(jsonObj.getString("disCount"))) {
			invoice.setDiscountType(jsonObj.getString("disCount"));
			;
		}

		if (jsonObj.has("discountValue") && !"".equalsIgnoreCase(jsonObj.getString("discountValue"))) {
			invoice.setDiscountAmount(jsonObj.getString("discountValue"));
			;
		}
		if (jsonObj.has("discountTotal") && !"".equalsIgnoreCase(jsonObj.getString("discountTotal"))) {
			invoice.setDiscountValue(jsonObj.getString("discountTotal"));
			;
		}
		if (jsonObj.has("inoviceTotal") && !"".equalsIgnoreCase(jsonObj.getString("inoviceTotal"))) {
			invoice.setSubTotal(jsonObj.getString("inoviceTotal"));
			;
		}

		if (jsonObj.has("comment") && !"".equalsIgnoreCase(jsonObj.getString("comment"))) {
			invoice.setInvoiceComment(jsonObj.getString("comment"));
		} else {
			invoice.setInvoiceComment(null);
		}

		if (jsonObj.has("grandTotal") && !"".equalsIgnoreCase(jsonObj.getString("grandTotal"))) {
			invoice.setTotal(jsonObj.getString("grandTotal"));
			invoice.setDueAmount(jsonObj.getString("grandTotal"));
		}

		if (jsonObj.has("afterDiscountTotal") && !"".equalsIgnoreCase(jsonObj.getString("afterDiscountTotal"))) {
			invoice.setAfterDiscountValue(jsonObj.getString("afterDiscountTotal"));
		}
		invoice.setPaymentStatus(Constants.INVOICE_STATUS_UNPAID);
		if (jsonObj.has("projectId") && !"".equalsIgnoreCase(jsonObj.getString("projectId"))) {
			Projects projects = projectsService.getProjectById(Long.parseLong(jsonObj.getString("projectId")));
			invoice.setProjectID(projects);
		}

		if (jsonObj.has("frequency") && !"".equalsIgnoreCase(jsonObj.getString("frequency"))) {
			invoice.setInvoiceFrequency(jsonObj.getString("frequency"));
		}
		
		if (jsonObj.has("projectMemberId") && !"".equalsIgnoreCase(jsonObj.getString("projectMemberId"))) {
			ProjectMembers pm = projectMembersService.getProjectMemberById(Long.parseLong(jsonObj.getString("projectMemberId")));
			invoice.setProjectMemberId(pm);
		}
		if (jsonObj.has("expensesId") && !"".equalsIgnoreCase(jsonObj.getString("expensesId"))) {
			Expenses expenses = expensesService.getExpenseById(Long.parseLong(jsonObj.getString("expensesId")));
			invoice.setExpensesId(expenses);;
		}
		

		invoice.setCurrencyCode(loginOrgCurrencyCode);
		invoice.setCreatedBy(user.getUserCode());
		invoice.setStatus(Constants.RECORD_ACTIVE.toString());
		invoice.setInvoiceStatus(saveType);
		invoice.setUpdatedBy(user.getUserCode());
		invoice.setCreatedDate(commonUtil.currentDate());

		List<String> submissionApproverHierarchyIds = new ArrayList<>();
		List<TimesheetSubmissionsDocuments> docusList = new ArrayList<TimesheetSubmissionsDocuments>();

		// storing invoive line items
		List<Long> invoiceLineItemsIdsList = null;
		if (null != invoice.getId()) {
			invoiceLineItemsIdsList = invoiceLineItemDao.getInvoiceLineItemsIdsByInvoiceId(invoice.getId());
		}
		Set<InvoiceLineItems> invoiceLineItemsSet = getExpensesInvoiceLineItems(jsonObj, user, orgLocation, invoice,invoiceLineItemsIdsList, submissionApproverHierarchyIds, docusList);
		invoice.setInvoiceLineItems(invoiceLineItemsSet);

		invoice.setSubmissionApproverHierarchyIds(submissionApproverHierarchyIds);

		// Store Tax
		List<Long> invoiceTaxItemsIdsList = null;
		if (null != invoice.getId()) {
			invoiceTaxItemsIdsList = invoiceTaxDao.getInvoiceTaxIdsByInvoiceId(invoice.getId());
		}
		Set<InvoiceTax> invoiceTax = getInvoiceTax(jsonObj, user, orgLocation, invoice, invoiceTaxItemsIdsList);
		invoice.setInvoiceTax(invoiceTax);

		// store attached Documents
		if (null != attachedfileList && attachedfileList.size() > 0) {
			Set<InvoiceDocument> invoiceDocumentsSet = getInvoiceDocuments(jsonObj, user, orgLocation, invoice,
					attachedfileList);
			invoice.setInvoiceDocumentSet(invoiceDocumentsSet);
		}

		//store attached Documents (after)
		File file = expensesInvoicePdfHelper.createPdfTableForExpensesInvoice(invoice, orgLocation, user, org, invoiceType,	vendorCurrencyCode, loginOrgCurrencyCode);
		invoice.setFileName(file.getName());
		byte[] bytesArray = getByteArray(file);
		invoice.setDocFile(bytesArray);
//		invoiceDao.saveInvoce(invoice);
		if (null != file) {
			file.delete();
		}

		if (invoice.getSubmissionApproverHierarchyIds() != null
				&& invoice.getSubmissionApproverHierarchyIds().size() > 0) {
			String submissionIds = invoice.getSubmissionApproverHierarchyIds().stream().map(Object::toString)
					.collect(Collectors.joining(","));
			invoice.setSubmissionApproverHierarchyId(submissionIds);
		}
		invoice.setInvoiceCategory(invoiceCategory);
		if(jsonObj.has("invoiceforexpenses")){
			invoice.setExpensesInvoices(jsonObj.getString("invoiceforexpenses"));
		}
		
		invoiceDao.saveInvoce(invoice);
		if (Constants.SENT.equalsIgnoreCase(saveType)) {
			sendInvoiceMail(user, orgLocation, invoice, invoiceType, docusList);
			saveParentInvoice(invoice, loginOrgCurrencyCode, vendorOrgLocation);
		}

		if (null != invoiceLineItemsIdsList && invoiceLineItemsIdsList.size() > 0) {
			for (Long lineItemsId : invoiceLineItemsIdsList) {
				InvoiceLineItems lineItems = invoiceLineItemDao.getInvoiceLineItemsById(lineItemsId);
				invoiceLineItemDao.deleteLineItem(lineItems);
			}
		}

		if (null != invoiceTaxItemsIdsList && invoiceTaxItemsIdsList.size() > 0) {
			for (Long taxItemsId : invoiceTaxItemsIdsList) {
				InvoiceTax tax = invoiceTaxDao.getInvoiceTaxById(taxItemsId);
				invoiceTaxDao.deleteInvoiceTax(tax);
			}
		}

		return invoice;

	}
	
	/**
	 * preaparing expenses inline items
	 * 
	 * @param jsonObj
	 * @param user
	 * @param orgLocation
	 * @param invoice
	 * @param invoiceLineItemsIdsList
	 * @param submissionApproverHierarchyIds
	 * @param docusList
	 * @return Set<ExpensesInvoiceLineItems>
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private Set<InvoiceLineItems> getExpensesInvoiceLineItems(JSONObject jsonObj, User user,
			OrgLocations orgLocation, Invoice invoice, List<Long> invoiceLineItemsIdsList,
			List<String> submissionApproverHierarchyIds, List<TimesheetSubmissionsDocuments> docusList)
					throws NumberFormatException, Exception {
		Set<InvoiceLineItems> expensesinvoicelineitemsSet = new HashSet<>();

		/*String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);*/

		List<Long> updatedInvoiceLineItems = new ArrayList<Long>();

		int maxRowCount = Integer.parseInt(jsonObj.getString("invoiceExpMaxRowNumber"));
		for (int i =0; i < maxRowCount; i++) {
			// expensesDate invoice; orgLocation; description; quantity; total;
			// status;
			if (jsonObj.has(i + "description")) {

//				if (null != jsonObj.getString(i + "quantity") || null != jsonObj.getString(i + "rate") || !"".equalsIgnoreCase(jsonObj.getString(i + "quantity"))
//		|| !"".equalsIgnoreCase(jsonObj.getString(i + "rate"))) {

					InvoiceLineItems invoiceLineItems = new InvoiceLineItems();
					/*
					 * if(jsonObj.has(i+"lineItemId") && null !=
					 * jsonObj.getString(i+"lineItemId") &&
					 * !"".equalsIgnoreCase(jsonObj.getString(i+"lineItemId"))){
					 * expensesInvoiceLineItems.setId(Long.valueOf(jsonObj.
					 * getString(i+"lineItemId")));
					 * updatedInvoiceLineItems.add(Long.valueOf(jsonObj.
					 * getString(i+"lineItemId"))); }
					 */
					if(jsonObj.has(i + "edate")){
						if (null != jsonObj.getString(i + "edate")
								&& !"".equalsIgnoreCase(jsonObj.getString(i + "edate"))) {
							Date expenseDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString(i + "edate"));
							invoiceLineItems.setStartDate(expenseDate);
						}	
					}
					if(jsonObj.has(i + "description")){
						if (null != jsonObj.getString(i + "description")
								&& !"".equalsIgnoreCase(jsonObj.getString(i + "description"))) {
							String description = jsonObj.getString(i + "description");
							invoiceLineItems.setInlineDescription(description);;
						}	
					}
					
					
					if(jsonObj.has(i + "quantity")){
						if (null != jsonObj.getString(i + "quantity")
								&& !"".equalsIgnoreCase(jsonObj.getString(i + "quantity"))) {
							String quantity = jsonObj.getString(i + "quantity");
							invoiceLineItems.setQuantity(quantity);
						}
					}
					
					if(jsonObj.has(i + "rate")){
						if (null != jsonObj.getString(i + "rate") && !"".equalsIgnoreCase(jsonObj.getString(i + "rate"))) {
							invoiceLineItems.setRate(jsonObj.getString(i + "rate"));
						}	
					}
					
					if (null != jsonObj.getString(i + "total")
							&& !"".equalsIgnoreCase(jsonObj.getString(i + "total"))) {
						invoiceLineItems.setAmount(jsonObj.getString(i + "total"));
					}
					if(jsonObj.has(i + "category")){
						if (null != jsonObj.getString(i + "category")
								&& !"".equalsIgnoreCase(jsonObj.getString(i + "category"))) {
							invoiceLineItems.setExpenseCategory(jsonObj.getString(i + "category"));
						}
					}
					
					invoiceLineItems.setOrgLocation(orgLocation);
					invoiceLineItems.setStatus(Constants.RECORD_ACTIVE.toString());
					invoiceLineItems.setCreatedBy(user.getUserCode());
					invoiceLineItems.setCreatedDate(commonUtil.currentDate());
					invoiceLineItems.setInvoice(invoice);
					expensesinvoicelineitemsSet.add(invoiceLineItems);
//				}
			}

		}

		if (null != invoice.getId()) {
			updatedInvoiceLineItems.retainAll(invoiceLineItemsIdsList);
			invoiceLineItemsIdsList.removeAll(updatedInvoiceLineItems);
		}

		return expensesinvoicelineitemsSet;

	}
	@Override
	public void updateExpensesForinvoice(Invoice invoice,String invoiceType) throws Exception {
		Expenses expenses = invoice.getExpensesId();
		if(Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
		   expenses.setBillingRecievableStatus("SENT");
		}else {
			expenses.setBillingPayableStatus("SENT");	
		}
		addExpensesDao.storeAddExpensesData(expenses);
	}
	
	private static byte[] readBytesFromFile(String filePath)
	{
	FileInputStream fileInputStream = null;
	byte[] bytesArray = null;
	try {
	File file = new File(filePath);
	bytesArray = new byte[(int) file.length()];
	// read file into bytes[]
	fileInputStream = new FileInputStream(file);
	fileInputStream.read(bytesArray);
	} catch (IOException e) {
	e.printStackTrace();
	}
	finally {
	if (fileInputStream != null) {
	try {
	fileInputStream.close();
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	}
	return bytesArray;
	}
	
}
