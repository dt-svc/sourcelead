package com.bis.sourcelead.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.beans.BillingGraphBean;
import com.bis.sourcelead.beans.BillingReportDTO;
import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dao.InvoiceLineItemDao;
import com.bis.sourcelead.dao.InvoiceTaxDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.CreditDebitNoteDTO;
import com.bis.sourcelead.dto.CreditDebitNoteLineItemsDTO;
import com.bis.sourcelead.dto.CreditDebitNoteListDTO;
import com.bis.sourcelead.dto.CustomInvoiceTemplateDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.InvoiceExpensesDTO;
import com.bis.sourcelead.dto.InvoiceLineItemsDTO;
import com.bis.sourcelead.dto.InvoiceTaxDTO;
import com.bis.sourcelead.dto.InvoiceTimelineDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.CreditDebitLineItems;
import com.bis.sourcelead.entity.CreditDebitNote;
import com.bis.sourcelead.entity.CustomInvoiceTemplate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceAmount;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.InvoiceTimelineHistory;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.InvoicePaymentService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.CustomInvoicePDFHelper;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.ExpensesInvoicePdfHelper;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.InvoicePdfHelper;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;

import edu.emory.mathcs.backport.java.util.Arrays;


@Service
public class InvoiceServiceImpl implements InvoiceService {

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
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RequirementSubmissionDao requirementSubmissionDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;
	
	@Value("${vmsUrl}")
	private String vmsUrl;

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

	@Autowired
	InvoicePaymentService invoicePaymentService;

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Autowired
	private PdfUtil pdfUtil;

	@Autowired
	private InvoicePdfHelper invoicePdfHelper;
	
	@Autowired
	private CustomInvoicePDFHelper customInvoicePDFHelper;

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
	public ResponseList<Invoice> invoiceListByOrgLocationId(OrgLocations orgLocations, Date startDateUTC,
			Date endDateUTC, InvoiceDTO invoiceDTO) throws Exception {
		return invoiceDao.invoiceListByOrgLocationId(orgLocations, startDateUTC, endDateUTC, invoiceDTO);

	}

	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,
			String contractType) throws Exception {
		return invoiceDao.getSowcontractListByAssociateId(candidateId, orgLocationId, orgRelationId, contractType);
	}

	@Override
	public Boolean getInvoiceByInvoiceId(String invoiceId, Long orgLocationsId, String invoiceType) throws Exception {

		return invoiceDao.getInvoiceByInvoiceId(invoiceId, orgLocationsId, invoiceType);
	}

	@Override
	public Invoice saveInvoice(User user, OrgLocations orgLocation,String saveType,String invoiceType,Org org,String loginOrgCurrencyCode,InvoiceDTO invoiceDTO, String defaultFont) throws Exception {
		
        Invoice invoice = null;
        String savedInvoiceId = null;
        if(StringUtils.isNotBlank(invoiceDTO.getSavedInvoiceId())){
        	invoice = invoiceDao.getInvoicetById(Long.valueOf(invoiceDTO.getSavedInvoiceId()));
        	savedInvoiceId = invoiceDTO.getSavedInvoiceId();
        }else{
        	invoice = new Invoice();
        	invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
        	invoice.setCreatedBy(user.getUserCode());
        	invoice.setCreatedDate(commonUtil.currentDate());
        }
        String generatedId = null;
		OrgLocations vendorOrgLocation = null;
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
//      			User contactUser = userService.getUserById(Long.parseLong(invoiceDTO.getVendorContact()));
      			//invoice.setContactUser(contactUser);
      			invoice.setContactUsers(invoiceDTO.getVendorContact());
      		}
      		invoice.setInvoiceType(Constants.Payables);
      		 //generatedId = getInvoiceOrBillGeneratedId(orgLocation, vendorOrgLocation, Constants.Receivable);
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
     			//User userContactName = userService.getUserById(Long.parseLong(invoiceDTO.getClientContact()));
     			//invoice.setContactUser(userContactName);
     			invoice.setContactUsers(invoiceDTO.getClientContact());
     		}
     		invoice.setInvoiceType(Constants.Receivable);
     		 //generatedId = getInvoiceOrBillGeneratedId(orgLocation, vendorOrgLocation, Constants.Receivable);
        }
       
        //currency conversion
        String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if(!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)){
	        if(null != vendorCurrencyCode){
	      	float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, Float.parseFloat(invoiceDTO.getGrandTotal()));
			float exchangeRate = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, 1);
			//Accept three decimal points
	      	float convertedAmount1 = 	Float.parseFloat((new DecimalFormat(".###").format(((convertedAmount * 100.0) / 100.0))));
	      	invoice.setConvertCurrencyCode(vendorCurrencyCode);
	      	invoice.setConvertAmount(String.valueOf(convertedAmount1));
	      	exchangeRate = convertedAmount1 * Float.valueOf(invoiceDTO.getGrandTotal());
	      //Accept three decimal points
	      	DecimalFormat decimalFormat = new DecimalFormat("#.000");
	        String afterConvert = decimalFormat.format(exchangeRate);
	      	invoice.setExchangeRate(String.valueOf(afterConvert));
	        }	
	    }
		invoice.setOwnerUsers(user.getId().toString());
        //invoice.setOwnerUserId(user);
		invoice.setOrgLocation(orgLocation);
		if (StringUtils.isNotBlank(invoiceDTO.getInvoiceId())) {
			invoice.setInvoiceId(invoiceDTO.getInvoiceId());
		}
		if (StringUtils.isNotBlank(invoiceDTO.getBillingAddress())) {
			invoice.setBillingAddress(invoiceDTO.getBillingAddress());
		}
		
		if (StringUtils.isNotBlank(invoiceDTO.getIsJobClosure())) {
			invoice.setIsJobClosure(invoiceDTO.getIsJobClosure());
		}
		/*
		 * if(StringUtils.isNotBlank(invoiceDTO.getAccountDetails())){
		 * invoice.setAccountDetails(invoiceDTO.getAccountDetails()); }
		 */
		if(StringUtils.isNotBlank(invoiceDTO.getNotifyToOthers())){
			invoice.setNotifyToOthers(invoiceDTO.getNotifyToOthers());	
		}else{
			invoice.setNotifyToOthers(null);	
		}
		if (StringUtils.isNotBlank(invoiceDTO.getPaymentAccount())) {
			PaymentAccount paymentaccount = new PaymentAccount();
			paymentaccount.setId(Long.valueOf(invoiceDTO.getPaymentAccount()));
			invoice.setPaymentAccount(paymentaccount);
		}else{
			invoice.setPaymentAccount(null);
		}

		if (StringUtils.isNotBlank(invoiceDTO.getInvoiceDate())) {
			Date invoiceDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
					invoiceDTO.getInvoiceDate());
			invoice.setInvoiceDate(invoiceDate);
		}
		Date dueDate = null;
		if (StringUtils.isNotBlank(invoiceDTO.getDueDate())) {
			dueDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), invoiceDTO.getDueDate());
			invoice.setDueDate(dueDate);
		}

		if (StringUtils.isNotBlank(invoiceDTO.getPaymentFrequency())) {
			invoice.setPaymentFrequency(invoiceDTO.getPaymentFrequency());
		} else {
			invoice.setPaymentFrequency(null);
		}

		if (StringUtils.isNotBlank(invoiceDTO.getDisCount())) {
			invoice.setDiscountType(invoiceDTO.getDisCount());
		}

		if (StringUtils.isNotBlank(invoiceDTO.getDiscountValue())) {
			invoice.setDiscountAmount(invoiceDTO.getDiscountValue());
		}
		if (StringUtils.isNotBlank(invoiceDTO.getDiscountTotal())) {
			invoice.setDiscountValue(invoiceDTO.getDiscountTotal());
		}
		if (StringUtils.isNotBlank(invoiceDTO.getInoviceTotal())) {
			invoice.setSubTotal(invoiceDTO.getInoviceTotal());
		}

		if (StringUtils.isNotBlank(invoiceDTO.getComment())) {
			invoice.setInvoiceComment(invoiceDTO.getComment());
		}
		if (StringUtils.isNotBlank(invoiceDTO.getGrandTotal())) {
			invoice.setTotal(invoiceDTO.getGrandTotal());
			invoice.setDueAmount(invoiceDTO.getGrandTotal());
		}

		if (StringUtils.isNotBlank(invoiceDTO.getAfterDiscountTotal())) {
			invoice.setAfterDiscountValue(invoiceDTO.getAfterDiscountTotal());
		}

		if (commonUtil.currentDate().after(dueDate)) {
			invoice.setPaymentStatus(Constants.OVER_DUE);
		} else {
			invoice.setPaymentStatus(Constants.INVOICE_STATUS_UNPAID);
		}
		if (StringUtils.isNotBlank(invoiceDTO.getProjectId())) {
			Projects projects = projectsService.getProjectById(Long.parseLong(invoiceDTO.getProjectId()));
			invoice.setProjectID(projects);
		}

		if (StringUtils.isNotBlank(invoiceDTO.getFrequency())) {
			invoice.setInvoiceFrequency(invoiceDTO.getFrequency());
		}
		
	    invoice.setCurrencyCode(loginOrgCurrencyCode);
		invoice.setStatus(Constants.RECORD_ACTIVE.toString());
		invoice.setInvoiceStatus(saveType);
		invoice.setUpdatedBy(user.getUserCode());
		invoice.setUpdatedDate(commonUtil.currentDate());
		List<String> submissionApproverHierarchyIds = new ArrayList<>();
		List<TimesheetSubmissionsDocuments> docusList = new ArrayList<TimesheetSubmissionsDocuments>();

		// storing invoice line items
		List<Long> invoiceLineItemsIdsList = null;
		if (null != invoice.getId()) {
			invoiceLineItemsIdsList = invoiceLineItemDao.getInvoiceLineItemsIdsByInvoiceId(invoice.getId());
		}
		Set<InvoiceLineItems> invoiceLineItemSet = getInvoiceLineItems(user, orgLocation, invoice,
				invoiceLineItemsIdsList, submissionApproverHierarchyIds, docusList, invoiceDTO);
		invoice.setInvoiceLineItems(invoiceLineItemSet);

		invoice.setSubmissionApproverHierarchyIds(submissionApproverHierarchyIds);

		// Store Tax
		List<Long> invoiceTaxItemsIdsList = null;
		if (null != invoice.getId()) {
			invoiceTaxItemsIdsList = invoiceTaxDao.getInvoiceTaxIdsByInvoiceId(invoice.getId());
		}
		Set<InvoiceTax> invoiceTax = getInvoiceTax(user, orgLocation, invoice, invoiceTaxItemsIdsList, invoiceDTO);
		invoice.setInvoiceTax(invoiceTax);

		// store attached Documents
		if (invoiceDTO.getFileInfo() != null && !invoiceDTO.getFileInfo().isEmpty()) {
			Set<InvoiceDocument> invoiceDocumentsSet = getInvoiceDocuments(invoiceDTO, user, orgLocation, invoice);
			invoice.setInvoiceDocumentSet(invoiceDocumentsSet);
		}

		// store attached Documents (after)
		boolean invoiceTemplateConfigured = false;
		File file = null;
		if(invoiceDTO.getInvoiceTemplateId() > 0) {
			CustomInvoiceTemplate customInvoiceTemplate = invoiceDao.getCustomInvoiceTemplateById(invoiceDTO.getInvoiceTemplateId());
			if(customInvoiceTemplate != null) {
				file = customInvoicePDFHelper.createCustomInvoicePDF(invoice, orgLocation, user, org, vendorCurrencyCode, loginOrgCurrencyCode, customInvoiceTemplate, "client/project", defaultFont, sourceleadUrl);
				invoiceTemplateConfigured = true;
				
				invoice.setCustomInvoiceTemplate(customInvoiceTemplate);
			}
		} 
		if(!invoiceTemplateConfigured)
			file = invoicePdfHelper.createPdfTableForInvoice(invoice, orgLocation, user, org, invoiceType, vendorCurrencyCode, loginOrgCurrencyCode);
		
		if (file != null && file.exists() && !file.isDirectory()) {
			invoice.setFileName(file.getName());
			invoice.setFileUrl(FileUploadUtil.moveFile(staticPath, org.getId(), orgLocation.getId(), "InvoicePdf", file));
		}
		if (null != file) {
			file.delete();
		}
		if (invoice.getSubmissionApproverHierarchyIds() != null
				&& invoice.getSubmissionApproverHierarchyIds().size() > 0) {
			String submissionIds = invoice.getSubmissionApproverHierarchyIds().stream().map(Object::toString)
					.collect(Collectors.joining(","));
			invoice.setSubmissionApproverHierarchyId(submissionIds);
		}

		invoiceDao.saveInvoce(invoice);
		Map<String, String> returnMap = new HashMap<>();
		if (Constants.SENT.equalsIgnoreCase(saveType)) {
			// mail scenario for client accept or reject
			Invoice parentInvoice = saveParentInvoice(invoice, loginOrgCurrencyCode, vendorOrgLocation, user);
			returnMap = sendInvoiceMail(user, orgLocation, invoice, invoiceType, docusList);
			//send notifyTo others email 
			if(StringUtils.isNotBlank(invoiceDTO.getNotifyToOthers())) {
				for(String notifyToEmail : invoiceDTO.getNotifyToOthers().split(",")) {
					sendInvoiceMailToOthers(user, orgLocation, invoice, invoiceType, docusList,notifyToEmail);
				}
			}
			if (parentInvoice.getId() != null) {
				saveInvoiceTimelineHistory(parentInvoice, user, null, null, null, returnMap);
			}

		}
		saveInvoiceTimelineHistory(invoice, user, null, savedInvoiceId, null, returnMap);

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

		/*
		 * //mail scenario for client accept or reject
		 * if(saveType.equalsIgnoreCase("SENT")){
		 * invoiceClientAcceptMail(user,orgLocation,invoice); }
		 */

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
		} finally {
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

	private Invoice saveParentInvoice(Invoice invoice, String loginOrgCurrencyCode, OrgLocations vendorOrgLocation,
			User user) throws Exception {
		// Invoice parentInvoice = null;
		Invoice parentInvoice = invoiceDao.getParentInvoiceByInvoiceId(invoice.getId());
		if (parentInvoice == null) {
			parentInvoice = new Invoice();
			parentInvoice.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), vendorOrgLocation));
			parentInvoice.setCreatedBy(user.getUserCode());
		}
		if (invoice.getInvoiceType().equalsIgnoreCase(Constants.Payables)) {
			parentInvoice.setInvoiceType(Constants.Receivable);
		} else {
			parentInvoice.setInvoiceType(Constants.Payables);
		}
		String vendorCurrencyCode = conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if (!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)) {
			if (null != vendorCurrencyCode) {
				float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode,
						Float.parseFloat(invoice.getTotal()));
				float convertedAmount1 = Float.parseFloat((new DecimalFormat(".###").format(((convertedAmount * 100.0) / 100.0))));
				convertedAmount1 = convertedAmount1 * Float.valueOf(invoice.getTotal());
				parentInvoice.setCurrencyCode(vendorCurrencyCode);
				DecimalFormat decimalFormat = new DecimalFormat("#.000");
		        String afterConvert = decimalFormat.format(convertedAmount1);
				parentInvoice.setTotal(afterConvert);
				parentInvoice.setDueAmount(afterConvert);
			}
		} else {
			parentInvoice.setCurrencyCode(invoice.getCurrencyCode());
			parentInvoice.setDueAmount(invoice.getDueAmount());
			parentInvoice.setTotal(invoice.getTotal());
		}

		parentInvoice.setInvoiceId(invoice.getInvoiceId());
		parentInvoice.setParentInvoiceId(invoice);
		parentInvoice.setOrgRelationID(invoice.getOrgRelationID());
		parentInvoice.setContactUsers(invoice.getOwnerUsers());
		//parentInvoice.setContactUser(invoice.getOwnerUserId());
		parentInvoice.setOrgLocation(invoice.getResponderLocationId());
		parentInvoice.setOwnerUsers(invoice.getContactUsers());
		//parentInvoice.setOwnerUserId(invoice.getContactUser());
		parentInvoice.setResponderLocationId(invoice.getOrgLocation());
		parentInvoice.setInvoiceDate(invoice.getInvoiceDate());
		parentInvoice.setBillingAddress(invoice.getBillingAddress());
		parentInvoice.setDiscountAmount(invoice.getDiscountAmount());
		parentInvoice.setDiscountValue(invoice.getDiscountValue());
		parentInvoice.setAfterDiscountValue(invoice.getAfterDiscountValue());
		parentInvoice.setSubTotal(invoice.getSubTotal());
		parentInvoice.setIsJobClosure(invoice.getIsJobClosure());
		parentInvoice.setInvoiceComment(invoice.getInvoiceComment());
		parentInvoice.setPaymentFrequency(invoice.getPaymentFrequency());
		parentInvoice.setDiscountType(invoice.getDiscountType());
		parentInvoice.setInvoiceStatus(Constants.INVOICE_RECEIVED);
		parentInvoice.setInvoiceCategory(invoice.getInvoiceCategory());
		parentInvoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		parentInvoice.setPaymentStatus(invoice.getPaymentStatus());
		parentInvoice.setDueDate(invoice.getDueDate());
		parentInvoice.setFileName(invoice.getFileName());
		parentInvoice.setFileUrl(invoice.getFileUrl());
		if (invoice.getProjectID() != null) {
			parentInvoice.setProjectID(invoice.getProjectID());
		}
		parentInvoice.setStatus(Constants.RECORD_ACTIVE.toString());
		parentInvoice.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), vendorOrgLocation));
		parentInvoice.setUpdatedBy(user.getUserCode());
		invoiceDao.saveInvoce(parentInvoice);
		return parentInvoice;
	}

	/**
	 * preparing invoice Documents
	 * 
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
				if (fileInfo != null) {
					FileUploadUtil.moveFile(staticPath, fileInfo, "invoiceDocuments");
				}
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

	private Set<InvoiceLineItems> getInvoiceLineItems(User user, OrgLocations orgLocation, Invoice invoice,
			List<Long> invoiceLineItemsIdsList, List<String> submissionApproverHierarchyIds,
			List<TimesheetSubmissionsDocuments> docusList, InvoiceDTO invoiceDTO) throws Exception {
		Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();

		// String pattern = "dd-MM-yyyy";
		// SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();

		List<Long> updatedInvoiceLineItems = new ArrayList<Long>();

		for (InvoiceLineItemsDTO invoiceLineItemsDTO : invoiceDTO.getLineItems()) {

			if (StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceStartDate())
					|| StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceEndDate())
					|| StringUtils.isNotBlank(invoiceLineItemsDTO.getCandidate())
					|| StringUtils.isNotBlank(invoiceLineItemsDTO.getSow())
					|| StringUtils.isNotBlank(invoiceLineItemsDTO.getRate())
					|| StringUtils.isNotBlank(invoiceLineItemsDTO.getTotalHours())) {

				InvoiceLineItems invoiceLineItems = new InvoiceLineItems();

				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getLineItemId())) {
					invoiceLineItems.setId(Long.valueOf(invoiceLineItemsDTO.getLineItemId()));
					updatedInvoiceLineItems.add(Long.valueOf(invoiceLineItemsDTO.getLineItemId()));
				}

				if(StringUtils.isNotBlank(invoiceLineItemsDTO.getBillRateType())){
					invoiceLineItems.setBillRateType(invoiceLineItemsDTO.getBillRateType());
				}else{
					invoiceLineItems.setBillRateType(null);	
				}
				
				if(StringUtils.isNotBlank(invoiceLineItemsDTO.getRequirementSubmissionId())){
					RequirementSubmission requirementSubmission = requirementSubmissionDao.getRequirementSubmissionById(Long.parseLong(invoiceLineItemsDTO.getRequirementSubmissionId()));
					invoiceLineItems.setRequirementSubmissionId(requirementSubmission);
				}
				
				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceStartDate())) {
					Date invoiceStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
							invoiceLineItemsDTO.getInvoiceStartDate());
					invoiceLineItems.setStartDate(invoiceStartDate);
				} else {
					invoiceLineItems.setStartDate(null);
				}

				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getInvoiceEndDate())) {
					Date invoiceStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
							invoiceLineItemsDTO.getInvoiceEndDate());
					invoiceLineItems.setEndDate(invoiceStartDate);
				} else {
					invoiceLineItems.setEndDate(null);
				}

				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getCandidate())) {
					if (!Constants.UNDEFINED.equalsIgnoreCase(invoiceLineItemsDTO.getCandidate())) {
						Candidate candidate = candidateService
								.getCandidateById(Long.parseLong(invoiceLineItemsDTO.getCandidate()));
						invoiceLineItems.setCandidate(candidate);
					}

					if (StringUtils.isNotBlank(invoiceLineItemsDTO.getUser())) {
						if (!Constants.UNDEFINED.equalsIgnoreCase(invoiceLineItemsDTO.getUser())) {
							User usr = userService.getUserById(Long.parseLong(invoiceLineItemsDTO.getUser()));
							invoiceLineItems.setUser(usr);
						}
					}
				}

				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getSow())) {
					Contracts contract = contractsTemplateService
							.getContractById(Long.parseLong(invoiceLineItemsDTO.getSow()));
					invoiceLineItems.setContract(contract);
				} else {
					invoiceLineItems.setContract(null);
				}
				
				if(StringUtils.isNotBlank(invoiceLineItemsDTO.getQuantity())){
					invoiceLineItems.setQuantity(invoiceLineItemsDTO.getQuantity());	
				}
				
				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getRate())) {
					invoiceLineItems.setRate(invoiceLineItemsDTO.getRate());
				}

				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getTotalHours())) {
					invoiceLineItems.setTotalHours(invoiceLineItemsDTO.getTotalHours());
				}
				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getAmount())) {
					invoiceLineItems.setAmount(invoiceLineItemsDTO.getAmount());
				} else {
					invoiceLineItems.setAmount(null);
				}
				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getDescription())) {
					invoiceLineItems.setInlineDescription(invoiceLineItemsDTO.getDescription());
				}
				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getSubmissionApproverHierarchyId())) {
					submissionApproverHierarchyIds.add(invoiceLineItemsDTO.getSubmissionApproverHierarchyId());
				}
				if (StringUtils.isNotBlank(invoiceLineItemsDTO.getSubmissionInvoiceId())) {
					List<TimesheetSubmissionsDocuments> documentList = timesheetSubmissionsDocumentsService
							.getAllTimesheetSubmissionsDocumentsBySubmissionId(
									Long.parseLong(invoiceLineItemsDTO.getSubmissionInvoiceId()));
					if (null != documentList && documentList.size() > 0) {
						for (TimesheetSubmissionsDocuments document : documentList) {
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
			// }

			if (null != invoice.getId()) {
				updatedInvoiceLineItems.retainAll(invoiceLineItemsIdsList);
				invoiceLineItemsIdsList.removeAll(updatedInvoiceLineItems);
			}
		}
		return invoiceLineItemsSet;

	}

	public EmailAttachment zipFiles(List<TimesheetSubmissionsDocuments> docusList) throws MessagingException {

		EmailAttachment ema = null;
		FileOutputStream fos = null;
		ZipOutputStream zipOut = null;
		FileInputStream fis = null;
		try {
			ByteArrayOutputStream zipBytes = new ByteArrayOutputStream();
			if (docusList != null && !docusList.isEmpty()) {
				ema = new EmailAttachment();
				ZipOutputStream outZip = new ZipOutputStream(zipBytes);

				int i = 0;
				for (TimesheetSubmissionsDocuments filePath : docusList) {
					StringBuilder sb = new StringBuilder();
					String st = filePath.getFileName();
					String lastIndex = st.substring(0, st.lastIndexOf("."));
					String newFilename = (sb.append(lastIndex).append("-").append(i)
							.append(st.substring(st.lastIndexOf("."))).toString());
					addToZipFile(filePath.getDocFile(), outZip, newFilename);
					i++;
				}

				outZip.closeEntry();
				outZip.close();
				DataSource source = new ByteArrayDataSource(zipBytes.toByteArray(), "application/zip");
				MimeBodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setDataHandler(new DataHandler(source));
				DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
				StringBuilder fName = new StringBuilder();
				String fileName = fName.append("resumes")
						.append(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()).append(" ")
						.append(dateFormat.format(new Date())).append(".zip").toString();
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
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {

			}
		}
		return ema;
	}

	public static void addToZipFile(byte[] by, ZipOutputStream zos, String fileName)
			throws FileNotFoundException, IOException {
		byte[] content = by;
		if (content != null) {
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

	public Map<String, String> sendInvoiceMail(User user, OrgLocations orglocations, Invoice invoice,
			String invoiceType, List<TimesheetSubmissionsDocuments> docusList) throws Exception {
		Map<String, String> returnMap = null;
		EmailScenario emailScenario = null;
		// EmailScenario notificationScenario = null;
		EmailMessage emailMessage = null;
		StringBuilder sb1 = new StringBuilder();
		if(null != invoice.getContactUsers()){
			String[] contactUsers = invoice.getContactUsers().split(",");
			if(contactUsers.length != 0){
				for(String contactUserId : contactUsers){
					User contactUser = userService.getUserById(Long.parseLong(contactUserId.trim()));
		String UUID = SourceLeadRandomCodeHelper.generateUUID();
		emailMessage=new EmailMessage();
		emailMessage.setFirstName(user.getFirstName());
		emailMessage.setLastName(user.getLastName());
		emailMessage.setToEmail(contactUser.getEmail());
		emailMessage.setFromEmail(user.getEmail());
		emailMessage.setCreatedBy(user.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage
				.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		String date = (commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(), invoice.getDueDate()));

		String invoiceDate = (commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),
				invoice.getInvoiceDate()));

		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("invoiceID", invoice.getInvoiceId());
		globalVarsMap.put("LoginUser", user.getFirstName());
		if (invoice.getInvoiceType().equalsIgnoreCase(Constants.RECEIVABLE)) {
			StringBuilder vendorName = new StringBuilder();
			if (contactUser.getLastName() != null) {
				globalVarsMap.put("vendorName", vendorName.append(contactUser.getFirstName()).append(" ")
						.append(contactUser.getLastName()).toString());
			} else {
				globalVarsMap.put("vendorName", contactUser.getFirstName());
			}
		} else {
			StringBuilder clientName = new StringBuilder();
			if (contactUser.getLastName() != null) {
				globalVarsMap.put("clientName", clientName.append(contactUser.getFirstName()).append(" ")
						.append(contactUser.getLastName()).toString());
			} else {
				globalVarsMap.put("clientName", contactUser.getFirstName());
			}
		}
		globalVarsMap.put("grandTotal", invoice.getTotal());
		globalVarsMap.put("dueDate", date);
		globalVarsMap.put("invoiceDate", invoiceDate);
		globalVarsMap.put("orgName", orglocations.getOrgLocationName());
		// globalVarsMap.put("address",invoice.getBillingAddress());

		/*
		 * StringBuilder acceptBuilder = new StringBuilder();
		 * globalVarsMap.put("acceptLink",acceptBuilder.append("<a href='").append(
		 * sourceleadUrl).append("/").append("acceptedInvoice?invoiceId=").append(
		 * invoice.getId()).
		 * append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>"
		 * ).toString());
		 * 
		 * StringBuilder rejectBuilder = new StringBuilder();
		 * globalVarsMap.put("rejectLink",rejectBuilder.append("<a href='").append(
		 * sourceleadUrl).append("/").append("rejectInvoice?invoiceId=").append(invoice.
		 * getId()).
		 * append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>"
		 * ).toString());
		 */

		// send mail logic

		ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		ArrayList<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
		EmailAttachment emailAttachment = new EmailAttachment();
		emailAttachment.setUUID(UUID);
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		EmailAttachment attachmentZip = zipFiles(docusList);
		if (null != invoice.getInvoiceDocumentSet() && invoice.getInvoiceDocumentSet().size() > 0
				&& !invoice.getInvoiceDocumentSet().isEmpty()) {
			Set<InvoiceDocument> documenetSet = invoice.getInvoiceDocumentSet();
			for (InvoiceDocument doc : documenetSet) {
				EmailAttachment docAttachment = new EmailAttachment();
				if (StringUtils.isNotBlank(doc.getFileUrl()))

					docAttachment.setDocFile(commonUtil.readBytesFromFile(ac + doc.getFileUrl()));
				docAttachment.setName(doc.getFileName());
				documentList.add(docAttachment);
			}
		}
		if (StringUtils.isNotBlank(invoice.getFileUrl()))
			emailAttachment.setDocFile(commonUtil.readBytesFromFile(ac + invoice.getFileUrl()));
		if (StringUtils.isNotBlank(invoice.getFileName()))
			emailAttachment.setName(invoice.getFileName());
		attachmentList.add(emailAttachment);

		if (null != attachmentZip) {
			attachmentList.add(attachmentZip);
		}
		if (null != documentList && !documentList.isEmpty() && documentList.size() > 0) {
			attachmentList.addAll(documentList);
		}

		// send notification logic
		EmailScenario notificationScenario = null;
		if (invoiceType.equalsIgnoreCase("payable")) {
			returnMap = commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orglocations.getOrg(), orglocations,
					Constants.INVOICE_CLIENT_PAYABLE_MAIL, attachmentList);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.INVOICE_CLIENT_PAYABLE_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);
		} else {
			returnMap = commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orglocations.getOrg(), orglocations,
					Constants.INVOICE_CLIENT_RECEIVABLE_MAIL, attachmentList);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.INVOICE_CLIENT_RECEIVABLE_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		
		sb1.append(emailMessage.getToEmail()).append(",").toString();
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		String actionLink = "";
		StringBuilder notificationLink = new StringBuilder();
		// Invoice invoice2=getInvoicetByParentId(invoice.getId());
		Invoice notificationInvoice = getInvoicetByParentId(invoice.getId());
		if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
			actionLink = notificationLink.append(vmsUrl).append("/")
					.append("viewInvoice?invoiceUUID=" + notificationInvoice.getInvoiceUUID() + "&invoiceType=Payables")
					.toString();
		} else {
			actionLink = notificationLink.append(vmsUrl).append("/").append(
					"viewInvoice?invoiceUUID=" + notificationInvoice.getInvoiceUUID() + "&invoiceType=Receivable")
					.toString();

		}
		emailScenerioUtil.sendNotification(actionLink, user, contactUser, invoice.getResponderLocationId(),
				notificationScenario, new HashMap<String, String>());
				}
				returnMap.put("toEmail", sb1.toString());
				returnMap.put("fromEmail", emailMessage.getFromEmail());
			}
		}
		/*
		 * if(invoiceType.equalsIgnoreCase("payable")){ emailScenario =
		 * emailScenarioService.getEmailScenarioByUniqueName(Constants.
		 * INVOICE_CLIENT_PAYABLE_MAIL,Constants.ENTITY_TYPE_EMAIL);
		 * notificationScenario =
		 * emailScenarioService.getEmailScenarioByUniqueName(Constants.
		 * INVOICE_CLIENT_PAYABLE_MAIL,Constants.ENTITY_TYPE_NOTIFICATION); }else{
		 * emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.
		 * INVOICE_CLIENT_RECEIVABLE_MAIL,Constants.ENTITY_TYPE_EMAIL);
		 * notificationScenario =
		 * emailScenarioService.getEmailScenarioByUniqueName(Constants.
		 * INVOICE_CLIENT_RECEIVABLE_MAIL,Constants.ENTITY_TYPE_NOTIFICATION); }
		 * 
		 * 
		 * //covert subject,body and signature String subject =
		 * emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 * String body =
		 * emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 * String signature =
		 * emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 * String url = sourceleadUrl;
		 * 
		 * String notificationbody =
		 * emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap)
		 * ; notificationScenario.setBody(notificationbody);
		 * 
		 * //construct HTML template using required data String emailTemplate =
		 * emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject,
		 * body, signature, url, orglocations.getOrg(),orglocations);
		 * emailMessage.setSubject(subject); emailMessage.setMessageBody(emailTemplate);
		 * 
		 * ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 * ArrayList<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
		 * EmailAttachment emailAttachment = new EmailAttachment();
		 * emailAttachment.setUUID(UUID); String ac = StringUtils.removeEnd(staticPath,
		 * "/slead-static"); EmailAttachment attachmentZip = zipFiles(docusList);
		 * if(null != invoice.getInvoiceDocumentSet() &&
		 * invoice.getInvoiceDocumentSet().size() > 0 &&
		 * !invoice.getInvoiceDocumentSet().isEmpty()){ Set<InvoiceDocument>
		 * documenetSet = invoice.getInvoiceDocumentSet(); for(InvoiceDocument
		 * doc:documenetSet){ EmailAttachment docAttachment = new EmailAttachment();
		 * if(StringUtils.isNotBlank(doc.getFileUrl()))
		 * 
		 * docAttachment.setDocFile(commonUtil.readBytesFromFile(ac+doc.getFileUrl()));
		 * docAttachment.setName(doc.getFileName()); documentList.add(docAttachment); }
		 * } if(StringUtils.isNotBlank(invoice.getFileUrl()))
		 * emailAttachment.setDocFile(commonUtil.readBytesFromFile(ac+invoice.getFileUrl
		 * ())); if(StringUtils.isNotBlank(invoice.getFileName()))
		 * emailAttachment.setName(invoice.getFileName());
		 * attachmentList.add(emailAttachment);
		 * 
		 * if(null != attachmentZip){ attachmentList.add(attachmentZip); } if(null !=
		 * documentList && !documentList.isEmpty() && documentList.size() > 0){
		 * attachmentList.addAll(documentList); } //calling the sendEmail method of
		 * emailScenerioUtil
		 * 
		 * emailScenerioUtil.sendEmail(emailMessage,orglocations.getId(),iAdressArray,
		 * attachmentList); StringBuilder notificationLink = new StringBuilder(); String
		 * actionLink = ""; Invoice notificationInvoice =
		 * getInvoicetByParentId(invoice.getId());
		 * if(Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)){ actionLink =
		 * notificationLink.append(sourceleadUrl).append("/").append(
		 * "viewInvoice?invoiceUUID="+notificationInvoice.getInvoiceUUID()+
		 * "&invoiceType=Payables").toString(); }else{ actionLink =
		 * notificationLink.append(sourceleadUrl).append("/").append(
		 * "viewInvoice?invoiceUUID="+notificationInvoice.getInvoiceUUID()+
		 * "&invoiceType=Receivable").toString();
		 * 
		 * } emailScenerioUtil.sendNotification(actionLink, user,
		 * invoice.getContactUser(), invoice.getResponderLocationId(),
		 * notificationScenario,new HashMap<String,String>());
		 */
		return returnMap;
	}
	
	
	
	public Map<String, String> sendInvoiceMailToOthers(User user, OrgLocations orglocations, Invoice invoice,
			String invoiceType, List<TimesheetSubmissionsDocuments> docusList,String otherMails) throws Exception {
		Map<String, String> returnMap = null;
		EmailScenario emailScenario = null;
		// EmailScenario notificationScenario = null;
		EmailMessage emailMessage = null;
		StringBuilder sb1 = new StringBuilder();
				
		String UUID = SourceLeadRandomCodeHelper.generateUUID();
		emailMessage=new EmailMessage();
		emailMessage.setFirstName(user.getFirstName());
		emailMessage.setLastName(user.getLastName());
		emailMessage.setToEmail(otherMails);
		emailMessage.setFromEmail(user.getEmail());
		emailMessage.setCreatedBy(user.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage
				.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		String date = (commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(), invoice.getDueDate()));

		String invoiceDate = (commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),
				invoice.getInvoiceDate()));

		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("invoiceID", invoice.getInvoiceId());
		globalVarsMap.put("LoginUser", user.getFirstName());
		if (invoice.getInvoiceType().equalsIgnoreCase(Constants.RECEIVABLE)) {
			
				globalVarsMap.put("vendorName", "");
		} else {
			
				globalVarsMap.put("clientName", "");
		}
		globalVarsMap.put("grandTotal", invoice.getTotal());
		globalVarsMap.put("dueDate", date);
		globalVarsMap.put("invoiceDate", invoiceDate);
		globalVarsMap.put("orgName", orglocations.getOrgLocationName());
		// globalVarsMap.put("address",invoice.getBillingAddress());


		// send mail logic

		ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		ArrayList<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
		EmailAttachment emailAttachment = new EmailAttachment();
		emailAttachment.setUUID(UUID);
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		EmailAttachment attachmentZip = zipFiles(docusList);
		if (null != invoice.getInvoiceDocumentSet() && invoice.getInvoiceDocumentSet().size() > 0
				&& !invoice.getInvoiceDocumentSet().isEmpty()) {
			Set<InvoiceDocument> documenetSet = invoice.getInvoiceDocumentSet();
			for (InvoiceDocument doc : documenetSet) {
				EmailAttachment docAttachment = new EmailAttachment();
				if (StringUtils.isNotBlank(doc.getFileUrl()))

					docAttachment.setDocFile(commonUtil.readBytesFromFile(ac + doc.getFileUrl()));
				docAttachment.setName(doc.getFileName());
				documentList.add(docAttachment);
			}
		}
		if (StringUtils.isNotBlank(invoice.getFileUrl()))
			emailAttachment.setDocFile(commonUtil.readBytesFromFile(ac + invoice.getFileUrl()));
		if (StringUtils.isNotBlank(invoice.getFileName()))
			emailAttachment.setName(invoice.getFileName());
		attachmentList.add(emailAttachment);

		if (null != attachmentZip) {
			attachmentList.add(attachmentZip);
		}
		if (null != documentList && !documentList.isEmpty() && documentList.size() > 0) {
			attachmentList.addAll(documentList);
		}

		// send notification logic
		EmailScenario notificationScenario = null;
		if (invoiceType.equalsIgnoreCase("payable")) {
			returnMap = commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orglocations.getOrg(), orglocations,
					Constants.INVOICE_CLIENT_PAYABLE_MAIL, attachmentList);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.INVOICE_CLIENT_PAYABLE_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);
		} else {
			returnMap = commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orglocations.getOrg(), orglocations,
					Constants.INVOICE_CLIENT_RECEIVABLE_MAIL, attachmentList);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.INVOICE_CLIENT_RECEIVABLE_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		
		sb1.append(emailMessage.getToEmail());
		
				returnMap.put("toEmail", sb1.toString());
				returnMap.put("fromEmail", emailMessage.getFromEmail());

		return returnMap;
	}

	/*
	 * @Override public void getPdfFileForInvoice(Invoice invoice,OrgLocations
	 * orgLocation,User user, Org org) throws Exception {
	 * invoicePdfHelper.createPdfTableForInvoice(invoice,orgLocation,user,org); }
	 */

	@Override
	public Invoice getInvoicetById(Long invoiceId) throws Exception {
		return invoiceDao.getInvoicetById(invoiceId);
	}

	@Override
	public List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId)
			throws Exception {
		return invoiceDao.getViewInvoiceListByInvoiceIdAndOrglocationId(invoiceId, orgLocationId);
	}

	@Override
	public Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception {
		return invoiceDao.getInvoicetByInvoiceUUID(invoiceUUID);
	}

	@Override
	public Invoice updateInvoice(Invoice invoice) throws Exception {
		return invoiceDao.updateInvoice(invoice);
	}
	/*
	 * @Override public Invoice saveProjectInvoice(JSONObject jsonObj, User user,
	 * OrgLocations orgLocation, String saveType, List<MultipartFile>
	 * attachedfileList, String invoiceType) throws Exception { Invoice invoice =
	 * new Invoice();
	 * 
	 * //--------------------------------
	 * if(invoiceType.equalsIgnoreCase("payable")){
	 * 
	 * 
	 * if(jsonObj.has("vendorOrgRelationId") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("vendorOrgRelationId"))){
	 * 
	 * OrgRelations orgRelation =
	 * orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString(
	 * "vendorOrgRelationId"))); invoice.setOrgRelationID(orgRelation); }
	 * 
	 * if(jsonObj.has("vendorContact") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("vendorContact"))){ String[] contact =
	 * jsonObj.getString("vendorContact").split(","); User contactUser =
	 * userService.getUserById(Long.parseLong(contact[1]));
	 * invoice.setContactUser(contactUser); }
	 * 
	 * invoice.setInvoiceType(Constants.Payables);
	 * 
	 * 
	 * }else{ if(jsonObj.has("orgRelationId") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("orgRelationId"))){
	 * 
	 * OrgRelations orgRelation =
	 * orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString(
	 * "orgRelationId"))); invoice.setOrgRelationID(orgRelation); }
	 * 
	 * if(jsonObj.has("clientContact") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("clientContact"))){ User
	 * userContactName =
	 * userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
	 * invoice.setContactUser(userContactName); }
	 * 
	 * invoice.setInvoiceType(Constants.Receivable);
	 * 
	 * }
	 * 
	 * 
	 * invoice.setOrgLocation(orgLocation); if(jsonObj.has("invoiceId") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("invoiceId"))){
	 * invoice.setInvoiceId(jsonObj.getString("invoiceId")); }
	 * if(jsonObj.has("projectId") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("projectId"))){ Projects projects =
	 * projectsService.getProjectById(Long.parseLong(jsonObj.getString("projectId"))
	 * ); invoice.setProjectID(projects); } if(jsonObj.has("invoiceAddress") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("invoiceAddress"))){
	 * invoice.setBillingAddress(jsonObj.getString("invoiceAddress"));
	 * 
	 * } if(jsonObj.has("invoiceDate") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("invoiceDate"))){ Date invoiceDate =
	 * commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.
	 * getString("invoiceDate")); invoice.setInvoiceDate(invoiceDate); }
	 * if(jsonObj.has("dueDate") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("dueDate"))){ Date dueDate =
	 * commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.
	 * getString("dueDate")); invoice.setDueDate(dueDate); }
	 * 
	 * if(jsonObj.has("paymentTerm") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("paymentTerm"))){
	 * invoice.setPaymentFrequency(jsonObj.getString("paymentTerm"));; }
	 * 
	 * if(jsonObj.has("disCountType") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("disCountType"))){
	 * invoice.setDiscountType(jsonObj.getString("disCountType"));; }
	 * 
	 * if(jsonObj.has("discountValue") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("discountValue"))){
	 * invoice.setDiscountAmount(jsonObj.getString("discountValue"));; }
	 * if(jsonObj.has("totalDiscountAmt") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("totalDiscountAmt"))){
	 * invoice.setDiscountValue(jsonObj.getString("totalDiscountAmt"));; }
	 * if(jsonObj.has("subTotal") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("subTotal"))){
	 * invoice.setSubTotal(jsonObj.getString("subTotal"));; }
	 * 
	 * if(jsonObj.has("comment") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("comment"))){
	 * invoice.setInvoiceComment(jsonObj.getString("comment"));; }
	 * 
	 * if(jsonObj.has("grandTotal") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("grandTotal"))){
	 * invoice.setTotal(jsonObj.getString("grandTotal"));
	 * invoice.setDueAmount(jsonObj.getString("grandTotal")); }
	 * 
	 * if(jsonObj.has("afterDiscountTotal") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("afterDiscountTotal"))){
	 * invoice.setAfterDiscountValue(jsonObj.getString("afterDiscountTotal")); }
	 * if(jsonObj.has("invoiceFerquency") &&
	 * !"".equalsIgnoreCase(jsonObj.getString("invoiceFerquency"))){
	 * invoice.setInvoiceFrequency(jsonObj.getString("invoiceFerquency")); }
	 * invoice.setPaymentStatus("Unpaid"); invoice.setCreatedBy(user.getUserCode());
	 * invoice.setStatus(Constants.RECORD_ACTIVE.toString());
	 * invoice.setInvoiceStatus(saveType); invoice.setUpdatedBy(user.getUserCode());
	 * invoice.setCreatedDate(commonUtil.currentDate());
	 * 
	 * 
	 * 
	 * // storing invoive line items Set<InvoiceLineItems> invoiceLineItemSet =
	 * getProjectInvoiceLineItems(jsonObj,user,orgLocation,invoice);
	 * invoice.setInvoiceLineItems(invoiceLineItemSet);
	 * 
	 * //Store Tax Set<InvoiceTax> invoiceTax =
	 * getProjectInvoiceTax(jsonObj,user,orgLocation,invoice);
	 * invoice.setInvoiceTax(invoiceTax);
	 * 
	 * //store attached Documents if(null != attachedfileList &&
	 * attachedfileList.size() >0){ Set<InvoiceDocument> invoiceDocumentsSet =
	 * getInvoiceDocuments(jsonObj,user,orgLocation,invoice,attachedfileList);
	 * invoice.setInvoiceDocumentSet(invoiceDocumentsSet); }
	 * 
	 * return invoiceDao.saveInvoce(invoice); }
	 */

	private Set<InvoiceLineItems> getProjectInvoiceLineItems(JSONObject jsonObj, User user, OrgLocations orgLocation,
			Invoice invoice) throws Exception {
		Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();

		// String pattern = "dd-MM-yyyy";
		// SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();

		int maxRowCount = Integer.parseInt(jsonObj.getString("maxRowNum"));
		for (int i = 1; i < maxRowCount; i++) {

			if (jsonObj.has(i + "sdate")) {

				if (null != jsonObj.getString(i + "sdate") || null != jsonObj.getString(i + "edate")
						|| null != jsonObj.getString(i + "candidateId") || null != jsonObj.getString(i + "sow")
						|| null != jsonObj.getString(i + "rate") || null != jsonObj.getString(i + "totalHours")
						|| !"".equalsIgnoreCase(jsonObj.getString(i + "sdate"))
						|| !"".equalsIgnoreCase(jsonObj.getString(i + "edate"))
						|| !"".equalsIgnoreCase(jsonObj.getString(i + "candidateId"))
						|| !"".equalsIgnoreCase(jsonObj.getString(i + "sow"))
						|| !"".equalsIgnoreCase(jsonObj.getString(i + "rate"))
						|| !"".equalsIgnoreCase(jsonObj.getString(i + "totalHours"))) {

					InvoiceLineItems invoiceLineItems = new InvoiceLineItems();

					if (null != jsonObj.getString(i + "sdate")
							&& !"".equalsIgnoreCase(jsonObj.getString(i + "sdate"))) {
						Date invoiceStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
								jsonObj.getString(i + "sdate"));
						invoiceLineItems.setStartDate(invoiceStartDate);
					}
					if (null != jsonObj.getString(i + "edate")
							&& !"".equalsIgnoreCase(jsonObj.getString(i + "edate"))) {
						Date invoiceStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
								jsonObj.getString(i + "edate"));
						invoiceLineItems.setEndDate(invoiceStartDate);
					}
					if (null != jsonObj.getString(i + "candidateId")
							&& !"".equalsIgnoreCase(jsonObj.getString(i + "candidateId"))) {
						Candidate candidate = candidateService
								.getCandidateById(Long.parseLong(jsonObj.getString(i + "candidateId")));
						invoiceLineItems.setCandidate(candidate);
					}
					if (null != jsonObj.getString(i + "sow") && !"".equalsIgnoreCase(jsonObj.getString(i + "sow"))) {
						Contracts contract = contractsTemplateService
								.getContractById(Long.parseLong(jsonObj.getString(i + "candidateId")));
						invoiceLineItems.setContract(contract);
					}
					if (null != jsonObj.getString(i + "rate") && !"".equalsIgnoreCase(jsonObj.getString(i + "rate"))) {
						invoiceLineItems.setRate(jsonObj.getString(i + "rate"));
					}
					if (null != jsonObj.getString(i + "totalHours")
							&& !"".equalsIgnoreCase(jsonObj.getString(i + "totalHours"))) {
						invoiceLineItems.setTotalHours(jsonObj.getString(i + "totalHours"));
					}

					if (null != jsonObj.getString(i + "totalAmount")
							&& !"".equalsIgnoreCase(jsonObj.getString(i + "totalAmount"))) {
						invoiceLineItems.setAmount(jsonObj.getString(i + "totalAmount"));
					}

					if (jsonObj.has("isSeparateInvoice")
							&& !"".equalsIgnoreCase(jsonObj.getString("isSeparateInvoice"))) {
						invoiceLineItems.setSeperateInvoice(jsonObj.getString("isSeparateInvoice"));
						;
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

	private Set<InvoiceTax> getProjectInvoiceTax(JSONObject jsonObj, User user, OrgLocations orgLocation,
			Invoice invoice) throws Exception {
		Set<InvoiceTax> invoiceTaxSet = new HashSet<>();
		int maxTaxRowCount = Integer.parseInt(jsonObj.getString("maxTaxRowNum"));
		for (int i = 0; i < maxTaxRowCount; i++) {
			if (null != jsonObj.getString(i + "taxType") || null != jsonObj.getString(i + "taxdiscountType")
					|| null != jsonObj.getString(i + "taxdiscountvalue") || null != jsonObj.getString(i + "taxTotal")
					|| !"".equalsIgnoreCase(jsonObj.getString(i + "taxType"))
					|| !"".equalsIgnoreCase(jsonObj.getString(i + "taxdiscountType"))
					|| !"".equalsIgnoreCase(jsonObj.getString(i + "taxdiscountvalue"))
					|| !"".equalsIgnoreCase(jsonObj.getString(i + "taxTotal"))) {
				InvoiceTax invoiceTax = new InvoiceTax();
				if (null != jsonObj.getString(i + "taxType")
						&& !"".equalsIgnoreCase(jsonObj.getString(i + "taxType"))) {
					invoiceTax.setTaxName(jsonObj.getString(i + "taxType"));
				}
				if (null != jsonObj.getString(i + "taxdiscountType")
						&& !"".equalsIgnoreCase(jsonObj.getString(i + "taxdiscountType"))) {
					invoiceTax.setTaxType(jsonObj.getString(i + "taxdiscountType"));
				}
				if (null != jsonObj.getString(i + "taxdiscountvalue")
						&& !"".equalsIgnoreCase(jsonObj.getString(i + "taxdiscountvalue"))) {
					invoiceTax.setTaxValue(jsonObj.getString(i + "taxdiscountvalue"));
				}
				if (null != jsonObj.getString(i + "taxTotal")
						&& !"".equalsIgnoreCase(jsonObj.getString(i + "taxTotal"))) {
					invoiceTax.setTaxAmount(jsonObj.getString(i + "taxTotal"));
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
	public void updateSubmissionApproverHiearachy(Invoice invoice, String invoiceType) throws Exception {
		if (null != invoice.getSubmissionApproverHierarchyIds()
				&& invoice.getSubmissionApproverHierarchyIds().size() > 0) {
			List<String> submissionApproverHierarchyIds = invoice.getSubmissionApproverHierarchyIds();// invoice.getInvoiceFrequency()
			for (String id : submissionApproverHierarchyIds) {
				if (!Constants.UNDEFINED.equalsIgnoreCase(id) && id != null && !"".equalsIgnoreCase(id)) {
					submissionApproverHierarchyService
							.updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(
									Long.parseLong(id), invoiceType, Constants.SENT, invoice);
				}
			}
		}
	}

	@Override
	public CurrencyConversionResponse getExchangeCurrency(String loginOrgCurrencyCode, OrgLocations orgLocation,
			Long orgRelationId, String grandTotal) throws Exception {
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
				float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode,
						Float.parseFloat(grandTotal));
				float exchangeRate = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, 1);
				float exchangeCurrency = Float.parseFloat((new DecimalFormat(".###").format(((convertedAmount * 100.0) / 100.0))));
				exchangeRate = exchangeCurrency * Float.valueOf(grandTotal);
				currencyConversionResponse.setExchangeRate(exchangeRate);
				currencyConversionResponse.setExchangeCurrency(exchangeCurrency);
				currencyConversionResponse.setVendorCurrencyCode(vendorCurrencyCode);
			}
		}
		return currencyConversionResponse;
	}

	@Override
	public File getExportBillingFile(ResponseList<Invoice> invoiceList, String orgName, String exportType,
			InvoiceAmount totalStagesAmount, String exportStartDate, String exportEndDate, String invoiceType,
			OrgLocations orgLocations) throws Exception {

		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			// DateFormat dateFormat = LoginSessionController.DATEFORMAT;
			Sheet createExcelsheet = createExcelworkbook.createSheet(
					orgName + "_" + commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()));
			Row writeRow = null;
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,
					totalStagesAmount, orgName, exportStartDate, exportEndDate, invoiceType);
			int column = 0;
			for (Invoice invoice : invoiceList.getData()) {
				writeRow = createExcelsheet.createRow(column + 10);
				createExcelsheet = createXlsSheet(createExcelsheet, writeRow, invoice);
				column++;
			}
			file = File.createTempFile(orgName + Constants.BILLING
					+ commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()), ".xls");
			file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file = invoicePdfHelper.getBillingPdf(invoiceList, orgName, totalStagesAmount, exportStartDate,
					exportEndDate, invoiceType, orgLocations);
		}
		return file;
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, Invoice invoice) {

		if (null != invoice.getInvoiceId() && !"".equalsIgnoreCase(invoice.getInvoiceId())) {
			writeRow.createCell(0).setCellValue(invoice.getInvoiceId());
		}

		OrgLocations orgLocations = null;
		if (invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == invoice.getOrgLocation().getId()
				.longValue()) {
			orgLocations = invoice.getOrgRelationID().getLocationId();
		} else if (invoice.getOrgRelationID().getLocationId().getId().longValue() == invoice.getOrgLocation().getId()
				.longValue()) {
			orgLocations = invoice.getOrgRelationID().getResponderLocationId();
		}
		if (null != orgLocations) {
			writeRow.createCell(1).setCellValue(orgLocations.getOrgLocationName());
		}
		List<User> contactUsersList = null;
		try {
			if(null != invoice.getContactUsers()){
			contactUsersList = userDao.getUsersListUsingMultipleStringIds(invoice.getContactUsers());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != invoice.getContactUsers()) {
			StringBuilder sbName = new StringBuilder();
			for(User contactUser : contactUsersList){
				if(null != contactUser.getFullName() && !"".equalsIgnoreCase(contactUser.getFullName())) {
					sbName.append(contactUser.getFullName()).append(",").toString();
				}
			}
			writeRow.createCell(2).setCellValue(sbName.deleteCharAt(sbName.length()-1).toString());
			
	}

		if (null != invoice.getContactUsers()) {
			StringBuilder sbEmail = new StringBuilder();
			for(User contactUser : contactUsersList){
				if(null != contactUser.getEmail() && !"".equalsIgnoreCase(contactUser.getEmail())) {
					sbEmail.append(contactUser.getEmail()).append(",").toString();
				}
			}
			writeRow.createCell(3).setCellValue(sbEmail.deleteCharAt(sbEmail.length()-1).toString());
		}

		if (null != invoice.getContactUsers()) {
			StringBuilder sbPhone = new StringBuilder();
			int count = contactUsersList.size();
			String contactMobile = "";
			for(User contactUser : contactUsersList){
				if(null != contactUser.getMobilePhone() && !"".equalsIgnoreCase(contactUser.getMobilePhone())) {
					contactMobile = contactMobile + contactUser.getMobilePhone();
				}
				count--;
				if(count != 0 && null != contactUser.getMobilePhone() && !"".equalsIgnoreCase(contactUser.getMobilePhone())){
					contactMobile = contactMobile + ",";
				}
			}
			writeRow.createCell(4).setCellValue(contactMobile.toString());
		}

		if (null != invoice.getInvoiceDate()) {
			String invoiceDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					invoice.getInvoiceDate());
			writeRow.createCell(5).setCellValue(invoiceDate);
		}

		if (null != invoice.getDueDate()) {
			String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), invoice.getDueDate());
			writeRow.createCell(6).setCellValue(dueDate);
		}

		if (null != invoice.getTotal() && !"".equalsIgnoreCase(invoice.getTotal())) {
			writeRow.createCell(7).setCellValue(invoice.getTotal());
		}

		if (null != invoice.getDueAmount() && !"".equalsIgnoreCase(invoice.getDueAmount())) {
			writeRow.createCell(8).setCellValue(invoice.getDueAmount());
		}

		if (null != invoice.getDueAmount() && !"".equalsIgnoreCase(invoice.getDueAmount())) {
			Double paidAmount = Double.parseDouble(invoice.getTotal()) - Double.parseDouble(invoice.getDueAmount());
			String convertedPaidAmount = String.valueOf(paidAmount);
			writeRow.createCell(9).setCellValue(convertedPaidAmount);
		}

		if (null != invoice.getInvoiceStatus() && !"".equalsIgnoreCase(invoice.getInvoiceStatus())) {
			if (Constants.SENT.equalsIgnoreCase(invoice.getInvoiceStatus())) {
				writeRow.createCell(10).setCellValue("Sent");
			} else if (Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())) {
				writeRow.createCell(10).setCellValue("Draft");
			} else {
				writeRow.createCell(10).setCellValue(invoice.getInvoiceStatus());
			}
		}
		if (null != invoice.getPaymentStatus() && !"".equalsIgnoreCase(invoice.getPaymentStatus())) {
			writeRow.createCell(11).setCellValue(invoice.getPaymentStatus());
		}

		if (null != invoice.getLinkInvoiceRefInvoiceId()
				&& !"".equalsIgnoreCase(String.valueOf(invoice.getLinkInvoiceRefInvoiceId()))) {
			writeRow.createCell(12).setCellValue(String.valueOf(invoice.getLinkInvoiceRefInvoiceId()));
		}

		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			InvoiceAmount totalStagesAmount, String orgName, String exportStartDate, String exportEndDate,
			String invoiceType) {

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
		cell4.setCellValue(exportStartDate + " - " + exportEndDate);
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
		if (Constants.Payables.equalsIgnoreCase(invoiceType)) {
			headers = Constants.BILLING_PAYABLE_HEADERS;
		} else {
			headers = Constants.BILLING_HEADERS;
		}
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 8);
		return createExcelsheet;
	}

	@Override
	public Integer getAutomaticInvoice(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType,
			User user, String contractType, String globalUser) throws Exception {
		int count = 0;
		List<OrgRelations> clientVendorList = null;
		OrgLocations vendorOrgLocation = null;
		if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
			clientVendorList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		} else {
			clientVendorList = getVendorsList(orgLocation);
		}
		User contactUser = null;
		for (OrgRelations client : clientVendorList) {
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(client.getId());

			if (orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelation.getId());
				vendorOrgLocation = orgRelation.getLocationId();
				if (orgSpocList != null && !orgSpocList.isEmpty()) {
					if (Constants.VENDOR.equalsIgnoreCase(orgRelation.getResponderType())) {
						contactUser = orgSpocList.get(0).getClientUser();
					} else {
						contactUser = orgSpocList.get(0).getVendorUser();
					}

				}
				// populateClientDetails(vendorOrgLocation,invoice);
			}
			if (orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelation.getId());
				vendorOrgLocation = orgRelation.getResponderLocationId();
				if (orgSpocList != null && !orgSpocList.isEmpty()) {
					if (Constants.VENDOR.equalsIgnoreCase(orgRelation.getResponderType())) {
						contactUser = orgSpocList.get(0).getVendorUser();
					} else {
						contactUser = orgSpocList.get(0).getClientUser();
					}

				}
			}
			List<Projects> projectsList = projectsService.getClientProjectsByOrgRelationId(client.getId(),
					orgLocation.getId());
			/*if(Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)){
				projectsList = projectsService.getClientProjectsByOrgRelationId(client.getId(),orgLocation.getId());
			}else{
				projectsList = projectsService.getAllProjectsByOrgLocationIdAndStatus(orgLocation.getId());
			}*/
			String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
  			if(null != projectsList && projectsList.size() >0){
  				for(Projects project : projectsList){
  	  				List<String> frequencyDates = getProjectsFrequency(project,orgLocation);
  	  				for(String frequency :frequencyDates){
  	  				   Invoice invoice = new Invoice();
  	  					List<ProjectMembers> projectMembersList = null;
  	  				if(Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)){
  	  					projectMembersList = getClientProjectAssinedMembersAutoInvoice(frequency,orgLocation,project.getId(),client.getId(),contractType,invoice);
  	  					if(orgRelation.getGenerateInvoice() != null && orgRelation.getGenerateInvoice().equalsIgnoreCase(Constants.FINANCE_TYPE_INDIVIDUAL) && projectMembersList!=null && projectMembersList.size() >0){
  	  					List<ProjectMembers> projectMem = new ArrayList<ProjectMembers>();
  	  				    projectMem.add(projectMembersList.get(0));
  	  				    projectMembersList = projectMem;
  	  					}
  	  					}else{
  						 projectMembersList = getVendorProjectAssinedMembersAutoInvoice(frequency,orgLocation,project.getId(),client.getId(),contractType,invoice,globalUser);
  						if(orgRelation.getGenerateBill().equalsIgnoreCase(Constants.FINANCE_TYPE_INDIVIDUAL) && projectMembersList!=null && projectMembersList.size() >0){
  	  	  					List<ProjectMembers> projectMem = new ArrayList<ProjectMembers>();
    	  				    projectMem.add(projectMembersList.get(0));
    	  				    projectMembersList = projectMem;
    	  					}
  	  					}
  	  					if(null != projectMembersList && projectMembersList.size() >0){
  	  						populateClientDetails(vendorOrgLocation,invoice,orgRelation);
  	  						invoice.setOrgRelationID(orgRelation);
  	  						invoice.setOrgLocation(orgLocation);
  	  						invoice.setInvoiceType(invoiceType);
  	  						invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
  	  						invoice.setInvoiceId(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode());
  	  						Set<InvoiceLineItems> invoiceLineItemSet = populateInvoiceInlineItems(projectMembersList,orgLocation,client.getId(),loginOrgCurrencyCode,invoice,user);
  	  						invoice.setInvoiceLineItems(invoiceLineItemSet);
  	  						invoice.setCurrencyCode(loginOrgCurrencyCode);
  	  						invoice.setContactUser(contactUser);
  	  						invoice.setContactUsers(contactUser.getId().toString());
  	  						invoice.setOwnerUsers(user.getId().toString());
  	  						invoice.setCreatedBy(user.getUserCode());
  	  						invoice.setStatus(Constants.RECORD_ACTIVE.toString());
  	  						invoice.setInvoiceStatus(Constants.DRAFT);
  	  						invoice.setUpdatedBy(user.getUserCode());
  	  						invoice.setCreatedDate(commonUtil.currentDate());
  	  						invoice.setUpdatedDate(commonUtil.currentDate());
  	  						invoice.setPaymentStatus("Unpaid");
  	  						invoice.setProjectID(project);
  	  						invoice.setDiscountType(Constants.PERCENTAGE);
  	  						invoice.setIsAutomaticGenerateInvoice(Constants.YES);
  	  						float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, Float.valueOf(invoice.getTotal()));
  	  						float exchangeRate = conversionUtil.convert(loginOrgCurrencyCode,vendorCurrencyCode,1f);
  	  		  		      	float roundAmount = Float.parseFloat((new DecimalFormat(".###").format(((convertedAmount * 100.0) / 100.0))));
  	  		  		      	exchangeRate = roundAmount *Float.valueOf(invoice.getTotal());
  	  		  		      	if(!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)){
  	  		  		      	invoice.setConvertCurrencyCode(vendorCurrencyCode);
  	  		  		      	invoice.setConvertAmount(String.valueOf(roundAmount));
  	  		  		      	invoice.setExchangeRate(String.valueOf(exchangeRate));
  	  		  		      	}
  	  		  		      	invoice.setInvoiceFrequency(frequency);
  	  		  		        Invoice savedInvoice = invoiceDao.saveInvoce(invoice);
  	  		  		        updateSubmissionApproverHierachy(savedInvoice,invoiceType);
  	  		  		        saveInvoiceTimelineHistory(invoice, user, null, null, null, null);
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
	
	
	@Override
	public Integer getAutomaticInvoiceForJobClosure(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType,
			User user,String contractType, String globalUser) throws Exception {
		int count = 0;
		List<OrgRelations> clientVendorList = null;
		OrgLocations vendorOrgLocation = null;
		if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
			clientVendorList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		} else {
			clientVendorList = getVendorsList(orgLocation);
		}
		User contactUser = null;
		for (OrgRelations client : clientVendorList) {
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(client.getId());

			if (orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelation.getId());
				vendorOrgLocation = orgRelation.getLocationId();
				if (orgSpocList != null && !orgSpocList.isEmpty()) {
					if (Constants.VENDOR.equalsIgnoreCase(orgRelation.getResponderType())) {
						contactUser = orgSpocList.get(0).getClientUser();
					} else {
						contactUser = orgSpocList.get(0).getVendorUser();
					}

				}
				// populateClientDetails(vendorOrgLocation,invoice);
			}
			if (orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelation.getId());
				vendorOrgLocation = orgRelation.getResponderLocationId();
				if (orgSpocList != null && !orgSpocList.isEmpty()) {
					if (Constants.VENDOR.equalsIgnoreCase(orgRelation.getResponderType())) {
						contactUser = orgSpocList.get(0).getVendorUser();
					} else {
						contactUser = orgSpocList.get(0).getClientUser();
					}

				}
			}
			List<RequirementSubmission> applicantsList = null;
			String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
			if(Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)){
			 applicantsList = requirementSubmissionDao.getCandidateForInvoiceJobClosure(orgLocation, user, client.getId());
			 if(orgRelation.getGenerateInvoice() !=null && orgRelation.getGenerateInvoice().equalsIgnoreCase(Constants.FINANCE_TYPE_INDIVIDUAL) && applicantsList!=null && applicantsList.size() >0){
 					List<RequirementSubmission> applicant = new ArrayList<RequirementSubmission>();
 					applicant.add(applicantsList.get(0));
 				   applicantsList = applicant;
 				}	
			}else{
				applicantsList = requirementSubmissionDao.getCandidateForBillJobClosure(orgLocation, user, client.getId());
				 if(orgRelation.getGenerateBill() !=null && orgRelation.getGenerateBill().equalsIgnoreCase(Constants.FINANCE_TYPE_INDIVIDUAL) && applicantsList!=null && applicantsList.size() >0){
	 					List<RequirementSubmission> applicant = new ArrayList<RequirementSubmission>();
	 					applicant.add(applicantsList.get(0));
	 				   applicantsList = applicant;
	 				}
			}
			 
			if(applicantsList.size()>0){
				Invoice invoice = new Invoice();
				populateClientDetails(vendorOrgLocation,invoice,orgRelation);
					invoice.setOrgRelationID(orgRelation);
					invoice.setOrgLocation(orgLocation);
					invoice.setInvoiceType(invoiceType);
					invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					invoice.setInvoiceId(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode());
					Set<InvoiceLineItems> invoiceLineItemSet = populateInvoiceInlineItemsForJobClosure(applicantsList,orgLocation,client.getId(),loginOrgCurrencyCode,invoice,user,contractType);
					invoice.setInvoiceLineItems(invoiceLineItemSet);
					invoice.setCurrencyCode(loginOrgCurrencyCode);
					invoice.setContactUser(contactUser);
					invoice.setContactUsers(contactUser.getId().toString());
					invoice.setOwnerUsers(user.getId().toString());
					invoice.setCreatedBy(user.getUserCode());
					invoice.setStatus(Constants.RECORD_ACTIVE.toString());
					invoice.setInvoiceStatus(Constants.DRAFT);
					invoice.setUpdatedBy(user.getUserCode());
					invoice.setCreatedDate(commonUtil.currentDate());
					invoice.setUpdatedDate(commonUtil.currentDate());
					invoice.setInvoiceDate(commonUtil.currentDate());
					invoice.setPaymentStatus("Unpaid");
					invoice.setIsJobClosure(Constants.YES);
					invoice.setDiscountType(Constants.PERCENTAGE);
					invoice.setIsAutomaticGenerateInvoice(Constants.YES);
					float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, Float.valueOf(invoice.getTotal()));
					float exchangeRate = conversionUtil.convert(loginOrgCurrencyCode,vendorCurrencyCode,1f);
	  		      	float roundAmount = Float.parseFloat((new DecimalFormat(".###").format(((convertedAmount * 100.0) / 100.0))));
	  		      	exchangeRate = roundAmount *Float.valueOf(invoice.getTotal());
	  		      	if(!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)){
	  		      	invoice.setConvertCurrencyCode(vendorCurrencyCode);
	  		      	invoice.setConvertAmount(String.valueOf(roundAmount));
	  		      	invoice.setExchangeRate(String.valueOf(exchangeRate));
	  		      	}
	  		        Invoice savedInvoice = invoiceDao.saveInvoce(invoice);
	  		   // update requirement submission 
					if(savedInvoice !=null){
					Set<InvoiceLineItems> lineItemsList = savedInvoice.getInvoiceLineItems();
					lineItemsList.forEach(list->{
						if(list.getRequirementSubmissionId() !=null){
					try {
						RequirementSubmission submission = requirementSubmissionDao.getRequirementSubmissionById(list.getRequirementSubmissionId().getId());
						submission.setInvoiceGenerated(Constants.YES);
						requirementSubmissionDao.storeRequirementSubmission(submission);
					} catch (Exception e) {
						e.printStackTrace();
					}
						}
					});
					}
					saveInvoiceTimelineHistory(invoice, user, null, null, null, null);
	  		      if(savedInvoice !=null){
 		  		         count++;
 		  		        }
			}
  			
		}
		return count;
	}
	
	
	@Override
	public Integer generateInvoiceJobClosureFromDashboard(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType,
			User user,String contractType, Long orgrelationId,Long submissionId,Long candidateId) throws Exception {
		int count = 0;
		
		OrgRelations orgRelation = orgRelationsService.getOrRelationsById(orgrelationId);
		User contactUser = null;
		OrgLocations vendorOrgLocation = null;
		if (orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelation.getId());
			vendorOrgLocation = orgRelation.getLocationId();
			if (orgSpocList != null && !orgSpocList.isEmpty()) {
				if (Constants.VENDOR.equalsIgnoreCase(orgRelation.getResponderType())) {
					contactUser = orgSpocList.get(0).getClientUser();
				} else {
					contactUser = orgSpocList.get(0).getVendorUser();
				}

			}
		}if (orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelation.getId());
			vendorOrgLocation = orgRelation.getResponderLocationId();
			if (orgSpocList != null && !orgSpocList.isEmpty()) {
				if (Constants.VENDOR.equalsIgnoreCase(orgRelation.getResponderType())) {
					contactUser = orgSpocList.get(0).getVendorUser();
				} else {
					contactUser = orgSpocList.get(0).getClientUser();
				}

			}
		}
		
			List<RequirementSubmission> applicants = null;
			String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
			 
			applicants = requirementSubmissionDao.getCandidateInvoiceJobClosureDashboard(orgLocation, orgrelationId,submissionId,candidateId);
			 
			for(RequirementSubmission reqSubmission : applicants){
				 if (reqSubmission.getRequirement().getClientId() != null && reqSubmission.getJoinedDate() != null) {
						String payment[] = reqSubmission.getRequirement().getClientId().getPaymentTerm().split(" ");
					 	int result = Integer.parseInt(payment[0]);
						Date generatedInvoiceDate = CalendarUtil.addDaysForGenerateInvoice(reqSubmission.getJoinedDate(), result);
						DateTime dtOrg = new DateTime(generatedInvoiceDate);
						reqSubmission.setInvoiceGenerateDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), dtOrg.toDate()));
						reqSubmission.setJoinedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),reqSubmission.getJoinedDate()));
				
						if(StringUtils.isNotBlank(reqSubmission.getInvoiceGenerateDate())){
							 if(applicants.size()>0){
									Invoice invoice = new Invoice();
									populateClientDetails(vendorOrgLocation,invoice,orgRelation);
										invoice.setOrgRelationID(orgRelation);
										invoice.setOrgLocation(orgLocation);
										invoice.setInvoiceType(invoiceType);
										invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
										invoice.setInvoiceId(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode());
										Set<InvoiceLineItems> invoiceLineItemSet = populateInvoiceInlineItemsForJobClosure(applicants,orgLocation,orgrelationId,loginOrgCurrencyCode,invoice,user,contractType);
										invoice.setInvoiceLineItems(invoiceLineItemSet);
										invoice.setCurrencyCode(loginOrgCurrencyCode);
										invoice.setContactUsers(contactUser.getId().toString());
										invoice.setOwnerUsers(user.getId().toString());
										invoice.setCreatedBy(user.getUserCode());
										invoice.setStatus(Constants.RECORD_ACTIVE.toString());
										invoice.setInvoiceStatus(Constants.DRAFT);
										invoice.setUpdatedBy(user.getUserCode());
										invoice.setCreatedDate(commonUtil.currentDate());
										invoice.setUpdatedDate(commonUtil.currentDate());
										invoice.setInvoiceDate(commonUtil.currentDate());
										invoice.setPaymentStatus("Unpaid");
										invoice.setIsJobClosure(Constants.YES);
										invoice.setDiscountType(Constants.PERCENTAGE);
										invoice.setIsAutomaticGenerateInvoice(Constants.YES);
										float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, Float.valueOf(invoice.getTotal()));
										float exchangeRate = conversionUtil.convert(loginOrgCurrencyCode,vendorCurrencyCode,1f);
						  		      	float roundAmount = Float.parseFloat((new DecimalFormat(".###").format(((convertedAmount * 100.0) / 100.0))));
						  		      	exchangeRate = roundAmount *Float.valueOf(invoice.getTotal());
						  		      	invoice.setConvertCurrencyCode(vendorCurrencyCode);
						  		      	invoice.setConvertAmount(String.valueOf(roundAmount));
						  		      	invoice.setExchangeRate(String.valueOf(exchangeRate));
						  		        Invoice savedInvoice = invoiceDao.saveInvoce(invoice);
						  		   // update requirement submission 
										if(savedInvoice !=null){
										Set<InvoiceLineItems> lineItemsList = savedInvoice.getInvoiceLineItems();
										lineItemsList.forEach(list->{
											if(list.getRequirementSubmissionId() !=null){
										try {
											RequirementSubmission submission = requirementSubmissionDao.getRequirementSubmissionById(list.getRequirementSubmissionId().getId());
											submission.setInvoiceGenerated(Constants.YES);
											requirementSubmissionDao.storeRequirementSubmission(submission);
										} catch (Exception e) {
											e.printStackTrace();
										}
											}
										});
										}
						  		      if(savedInvoice !=null){
					 		  		         count++;
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
	private void updateSubmissionApproverHierachy(Invoice invoice, String invoiceType) throws Exception {

		if (null != invoice.getSubmissionApproverHierarchyIds()
				&& invoice.getSubmissionApproverHierarchyIds().size() > 0) {
			List<String> submissionApproverHierarchyIds = invoice.getSubmissionApproverHierarchyIds();// ,invoice.getInvoiceFrequency()
			for (String id : submissionApproverHierarchyIds) {
				if (!Constants.UNDEFINED.equalsIgnoreCase(id) && id != null && !"".equalsIgnoreCase(id)) {
					submissionApproverHierarchyService
							.updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(
									Long.parseLong(id), invoiceType, Constants.SENT, invoice);
				}
			}
		}
		/*
		 * if (null != submissionApproverHierarchyIds &&
		 * submissionApproverHierarchyIds.size() > 0) { for (String id :
		 * submissionApproverHierarchyIds) { SubmissionApproverHierarchy
		 * submissionApproverHierarchy =
		 * submissionApproverHierarchyService.getSubmissionApproverHierarchy(id); if
		 * (Constants.PAYABLES.equalsIgnoreCase(invoiceType)) {
		 * submissionApproverHierarchy.setBillingStatus(Constants.SENT); } else {
		 * submissionApproverHierarchy.setInvoiceStatus(Constants.SENT); }
		 * submissionApproverHierarchyService.addSubmissionApproverHierarchy(
		 * submissionApproverHierarchy); } }
		 */
	}

	/**
	 * to get all vendor list
	 * 
	 * @param orgLocation
	 * @return List<OrgRelations>
	 */
	private List<OrgRelations> getVendorsList(OrgLocations orgLocation) {
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
	private Set<InvoiceLineItems> populateInvoiceInlineItems(List<ProjectMembers> projectMembersList,
			OrgLocations orgLocation, Long orgRelationId, String loginOrgCurrencyCode, Invoice invoice, User user)
			throws Exception {
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
			if (projectMember.getCandidateId() != null) {
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
			} else {
				if (projectMember.getUserId() != null) {
					User usr = userService.getUserById(Long.parseLong(projectMember.getUserId()));
					invoiceLineItems.setUser(usr);
				}
			}

			if (null != projectMember.getTotalHours()) {
				invoiceLineItems.setTotalHours(projectMember.getTotalHours());
			}
			if (null != rate && null != projectMember.getTotalHours() && projectMember.getTotalHours().contains(":")) {
				float totalAmount = getTotalAmount(rate, projectMember.getTotalHours(), loginOrgCurrencyCode, ratePer,
						rateCurrencyType, invoiceLineItems);
				invoiceLineItems.setAmount(String.valueOf(totalAmount));
				subTotal = subTotal + totalAmount;
			}
			invoiceLineItems.setOrgLocation(orgLocation);
			invoiceLineItems.setStatus(Constants.RECORD_ACTIVE.toString());
			invoiceLineItems.setCreatedBy(user.getUserCode());
			invoiceLineItems.setCreatedDate(commonUtil.currentDate());
			invoiceLineItems.setUpdatedBy(user.getUserCode());
			invoiceLineItems.setUpdatedDate(commonUtil.currentDate());
			invoiceLineItems.setInvoice(invoice);
			invoiceLineItemsSet.add(invoiceLineItems);
			submissionApproverHiearachyIds.add(String.valueOf(projectMember.getSubmissionApproverHierarchyId()));
		}
		if (submissionApproverHiearachyIds != null && submissionApproverHiearachyIds.size() > 0) {
			String submissionIds = submissionApproverHiearachyIds.stream().map(Object::toString)
					.collect(Collectors.joining(","));
			invoice.setSubmissionApproverHierarchyId(submissionIds);
		}
		invoice.setSubTotal(String.valueOf(subTotal));
		invoice.setDueAmount(String.valueOf(subTotal));
		invoice.setTotal(String.valueOf(subTotal));
		invoice.setSubmissionApproverHierarchyIds(submissionApproverHiearachyIds);
		return invoiceLineItemsSet;
	}
	
	private Set<InvoiceLineItems> populateInvoiceInlineItemsForJobClosure(List<RequirementSubmission> submissionList,
			OrgLocations orgLocation, Long orgRelationId, String loginOrgCurrencyCode, Invoice invoice, User user,String contratType)
			throws Exception {
		Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();
		float subTotal = 0;
		for (RequirementSubmission submission : submissionList) {
			InvoiceLineItems invoiceLineItems = new InvoiceLineItems();
			
			if (submission.getCandidate() != null) {
				Candidate candidate = candidateService.getCandidateByCandidateId(submission.getCandidate().getId());
				invoiceLineItems.setCandidate(candidate);
				
				invoiceLineItems.setRequirementSubmissionId(submission);
				
				if(submission.getRequirementOffersList().size()>0){
					for(RequirementOffers offer : submission.getRequirementOffersList()){
						if(offer.getOfferStatus().equals("OFFERACCEPTED")){
							invoiceLineItems.setRate(offer.getAmount());	
						}
					}
					
				}
				try{
					List<Contracts> contractsList = getSowContract(submission.getCandidate().getId(), orgLocation, orgRelationId, contratType);
					if (null != contractsList && contractsList.size() > 0) {
						Contracts contract = contractsList.get(0);
						invoiceLineItems.setContract(contract);
					}	
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			invoiceLineItems.setOrgLocation(orgLocation);
			invoiceLineItems.setBillRateType("Flat");
			invoiceLineItems.setStatus(Constants.RECORD_ACTIVE.toString());
			invoiceLineItems.setCreatedBy(user.getUserCode());
			invoiceLineItems.setCreatedDate(commonUtil.currentDate());
			invoiceLineItems.setInvoice(invoice);invoiceLineItems.setUpdatedBy(user.getUserCode());
			invoiceLineItems.setUpdatedDate(commonUtil.currentDate());
			invoiceLineItemsSet.add(invoiceLineItems);
		}
		
		invoice.setSubTotal(String.valueOf(subTotal));
		invoice.setDueAmount(String.valueOf(subTotal));
		invoice.setTotal(String.valueOf(subTotal));
		}
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
	private float getTotalAmount(String billingAmount, String totalHours, String loginOrgCurrencyCode,
			String billingRatePer, String rateCurrencyType, InvoiceLineItems invoiceLineItems) {
		float hourlyRate = conversionUtil.getHourlyRate(billingAmount, billingRatePer);
		float roundValue = 0;
		if (!loginOrgCurrencyCode.equalsIgnoreCase(rateCurrencyType)) {
			float coversionRate = conversionUtil.convert(rateCurrencyType, loginOrgCurrencyCode, hourlyRate);
			roundValue = (float) (Math.round(coversionRate * 100.0) / 100.0);
		} else {
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
	private List<ProjectMembers> getClientProjectAssinedMembersAutoInvoice(String frequency, OrgLocations orgLocation,
			Long projectId, Long orgRelationId, String contractType, Invoice invoice) throws Exception {
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		// String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> projectMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		invoice.setInvoiceFrequency(frequency);
		if (null != projectId) {
			sahList = submissionApproverHierarchyService
					.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(projectId,
							orgLocation.getId(), startDate, endDate, contractType);
		}
		if (null != sahList && sahList.size() > 0) {
			for (SubmissionApproverHierarchy sah : sahList) {
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
				projectMember.setSubmissionApproverHierarchyId(sah.getId());
				StringBuilder candidateName = new StringBuilder();
				if (projectMember.getProjectMemberCandidteId() != null) {
					if (null != projectMember.getProjectMemberCandidteId().getFirstName()
							&& null != projectMember.getProjectMemberCandidteId().getLastName()) {
						candidateName.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ")
								.append(projectMember.getProjectMemberCandidteId().getLastName());
					} else {
						candidateName.append(projectMember.getProjectMemberCandidteId().getFirstName());
					}
				}

				projectMember.setCandidateName(candidateName.toString());
				projectMember.setUserId(String.valueOf(projectMember.getProjectMemberCandidteId().getId()));
				if (null != projectMember.getProjectMemberCandidteId().getId()) {
					Candidate candidate = projectMember.getProjectMemberCandidteId();
					if (null != candidate) {
						projectMember.setCandidateId(candidate.getId());
						List<Contracts> contractList = getSowContract(candidate.getId(), orgLocation, orgRelationId,
								contractType);
						if (null != contractList && contractList.size() > 0) {
							projectMember.setContractsList(contractList);
						}
					}
				}
				projectMember.setStartDate(startDateStr);
				projectMember.setEndDate(endDateStr);
				StringBuilder sb = new StringBuilder();
				if (sah.getBillableHours() != null) {
					String billableHours = String.valueOf(sah.getBillableHours());
					String arr[] = billableHours.split("\\.");
					if (arr[0].length() == 2) {
						sb.append("0").append(arr[0]);
					} else if (arr[0].length() == 3) {
						sb.append(arr[0]);
					} else if (arr[0].length() == 1) {
						sb.append("00").append(arr[0]);
					}
					if (arr[1].length() == 1) {
						sb.append(":").append("0").append(arr[1]);
					} else {
						sb.append(":").append(arr[1]);
					}
				} else {
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
	private List<ProjectMembers> getVendorProjectAssinedMembersAutoInvoice(String frequency, OrgLocations orgLocation,
			Long projectId, Long orgRelationId, String contractType, Invoice invoice, String globalUser)
			throws Exception {
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		// String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> filteredMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		if (null != projectId) {
			sahList = submissionApproverHierarchyService
					.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(projectId,
							orgLocation.getId(), startDate, endDate, contractType);
		}
		if (null != sahList && sahList.size() > 0) {
			for (SubmissionApproverHierarchy sah : sahList) {
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
				projectMember.setSubmissionApproverHierarchyId(sah.getId());
				projectMember.setSubmissionInvoiceId(sah.getSubmissionId().getId());
				// Employees employee =
				// employeesService.getEmployeByUserIdAndOrgRelationId(projectMember.getProjectMemberCandidteId(),orgRelationId);
				// Candidate candidate =
				// candidateService.getCandidateByConsultantUserIdOrgRelationId(projectMember.getEmployee().getUser().getId(),
				// orgLocation.getId(), orgRelationId);

				ProjectMembers pm = projectMembersService.getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForFinance(
						projectMember.getMasterCandidateId().getId(), projectId, orgLocation.getId(), globalUser);

				Candidate candidate = pm.getProjectMemberCandidteId();
				if (candidate != null && ("C2C".equalsIgnoreCase(candidate.getDesignationType())
						|| "C2C-1099".equalsIgnoreCase(candidate.getDesignationType()))) {

					// if (null != projectMember.getProjectMemberCandidteId()) {

					StringBuilder empName = new StringBuilder();
					if (null != projectMember.getProjectMemberCandidteId().getFirstName()
							&& null != projectMember.getProjectMemberCandidteId().getLastName()) {
						empName.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ")
								.append(projectMember.getProjectMemberCandidteId().getLastName());
					} else {
						empName.append(projectMember.getProjectMemberCandidteId().getFirstName());
					}
					projectMember.setCandidateName(empName.toString());
					projectMember.setUserId(projectMember.getProjectMemberCandidteId().toString());
					projectMember.setStartDate(startDateStr);
					projectMember.setEndDate(endDateStr);
					String billableHours = String.valueOf(sah.getBillableHours());
					StringBuilder sb = new StringBuilder();
					if (!"null".equalsIgnoreCase(billableHours)) {
						String arr[] = billableHours.split("\\.");
						if (arr[0].length() == 2) {
							sb.append("0").append(arr[0]);
						} else if (arr[0].length() == 3) {
							sb.append(arr[0]);
						} else if (arr[0].length() == 1) {
							sb.append("00").append(arr[0]);
						}
						if (arr[1].length() == 1) {
							sb.append(":").append("0").append(arr[1]);
						} else {
							sb.append(":").append(arr[1]);
						}
					} else {
						sb.append("0");
					}
					projectMember.setTotalHours(sb.toString());
					// Candidate candidate = projectMember.getProjectMemberCandidteId();
					if (null != candidate) {
						projectMember.setCandidateId(candidate.getId());
						List<Contracts> contractList = getSowContract(candidate.getId(), orgLocation, orgRelationId,
								contractType);
						if (null != contractList && contractList.size() > 0) {
							projectMember.setContractsList(contractList);
						}
					}

					filteredMembersList.add(projectMember);
					// }
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
	private List<String> getProjectsFrequency(Projects projects,OrgLocations orgLocations) throws Exception {
		// Projects projects = projectsService.getProjectById(projectId);
		List<String> frequencyDates = new ArrayList<>();
		Map<String, String> map = timesheetFrequencyGeneratorUtil
				.getTimesheetFrequencyListFromProjectInvoiceFrequency(projects,orgLocations);
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
	 * @param orgRelation
	 */
	private void populateClientDetails(OrgLocations vendorOrgLocation, Invoice invoice, OrgRelations orgRelation) {
		invoice.setResponderLocationId(vendorOrgLocation); // check whether the string start with number
		if (null != orgRelation.getPaymentTerm() && !"".equalsIgnoreCase(orgRelation.getPaymentTerm())
				&& orgRelation.getPaymentTerm().matches("^[0-9].*$")) {
			invoice.setPaymentFrequency(orgRelation.getPaymentTerm());
			invoice.setInvoiceDate(new Date());
			Date dueDate = getDueDateByPaymentTerm(new Date(), orgRelation.getPaymentTerm());
			invoice.setDueDate(dueDate);
		}
		if (null != orgRelation.getBillingAddress() && !"".equalsIgnoreCase(orgRelation.getBillingAddress())) {
			invoice.setBillingAddress(orgRelation.getBillingAddress());
		}
	}

	/**
	 * Get due date frequency based on invoice date
	 * 
	 * @param invoiceDate
	 * @param paymentTerm
	 * @return Date
	 */
	private Date getDueDateByPaymentTerm(Date invoiceDate, String paymentTerm) {
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
	private List<Contracts> getSowContract(Long candidateId, OrgLocations orgLocation, Long orgRelationId,
			String contractType) throws Exception {
		List<Contracts> contractsList = null;
		if (null != orgRelationId) {
			if (Constants.VENDOR_SOW.equalsIgnoreCase(contractType)) {
				contractsList = invoiceDao.getSowcontractListByAssociateId(candidateId, orgLocation.getId(),
						orgRelationId, Constants.Vendor_SOW);
			} else {
				contractsList = invoiceDao.getSowcontractListByAssociateId(candidateId, orgLocation.getId(),
						orgRelationId, Constants.Client_SOW);
			}
		}
		return contractsList;
	}

	@Override
	public List<Invoice> getInvoiceByOrgLocationId(Long orgLocationId, Date startDateUTC, Date endDateUTC,
			String invoiceTYpe) throws Exception {
		return invoiceDao.getInvoiceByOrgLocationId(orgLocationId, startDateUTC, endDateUTC, invoiceTYpe);

	}

	@Override
	public List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocationId, String invoiceType) {
		return invoiceDao.getClientBasedInvoiceLists(orgRelationId, orgLocationId, invoiceType);
	}

	@Override
	public Invoice linkInvoice(Invoice invoice) throws Exception {
		return invoiceDao.saveInvoce(invoice);
	}

	@Override
	public List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId, Long clientLocationId, String invoiceStatus,
			String paymentStatus, String invoiceType, OrgLocations orgLocations, String dayWisetype,
			String customStartDate, String customEndDate) throws Exception {

		Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocations.getTimeZoneId()));
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDateUTC = null;
		Date startDateUTC = null;

		if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(dayWisetype)) {
			String endDate = outputFormatter1.print(zoned);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
					endDate);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		if (null != dayWisetype && !"".equals(dayWisetype)) {
			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(dayWisetype)) {
				String startDate = outputFormatter.print(zoned);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
						startDate);
				startDateUTC = TZ_DF_TS.parse(startDateString);
				if (Constants.ONE_MONTH.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				} else if (Constants.TWO_MONTHS.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
				} else if (Constants.THREE_MONTHS.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 90);
				} else if (Constants.SIX_MONTHS.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 180);
				} else if (Constants.ONE_YEAR.equals(dayWisetype)) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 365);
				}
			}
		} else {
			if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)
					&& !"undefined".equalsIgnoreCase(customStartDate) && !"undefined".equalsIgnoreCase(customEndDate)) {
				DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
				DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");

				Date startTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), customStartDate);
				Date endTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), customEndDate);

				String customStartTimeStr = TZ_DF_CSD.format(startTime);
				String customEndTimeStr = TZ_DF_CED.format(endTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
						customStartTimeStr);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
						customEndTimeStr);
				startDateUTC = TZ_DF_TS.parse(startDateString);
				endDateUTC = TZ_DF_TS.parse(endDateString);
			} else {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
			}
		}

		List<Invoice> invoiceList = invoiceDao.getInvoiceByOrgRelationId(orgRelationId, clientLocationId, startDateUTC,
				endDateUTC, invoiceStatus, paymentStatus, invoiceType);
		// String currentDate = sdf.format(new Date());
		Date todayDate = new Date();
		if (null != invoiceList && invoiceList.size() > 0) {
			for (Invoice invoice : invoiceList) {
				Integer draftCount = 0;
				Integer paidCount = 0;
				Integer overDueInvoiceCount = 0;
				Integer openInvoiceCount = 0;
				if (invoice.getOrgRelationID().getLocationId().getId().longValue() == orgLocations.getId()
						.longValue()) {
					invoice.setClientName(invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName());
				}
				if (invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocations.getId()
						.longValue()) {
					invoice.setClientName(invoice.getOrgRelationID().getLocationId().getOrgLocationName());
				}
				
				if(null != invoice.getContactUsers()){
					String[] contactUsers = invoice.getContactUsers().split(",");
					if(contactUsers.length != 0){
						StringBuilder contactUserName = new StringBuilder();
						for(String contactUserId : contactUsers){
							User contactUser = userService.getUserById(Long.parseLong(contactUserId.trim()));
							if(contactUser.getLastName() != null){
								contactUserName = contactUserName.append(contactUser.getFirstName()).append(" ").append(contactUser.getLastName()).append(", ");
							}else{
								contactUserName = contactUserName.append(contactUser.getFirstName()).append(", ");
							}
						}
						
						invoice.setClientContactName(contactUserName.toString().substring(0, contactUserName.toString().length() - 2));
					}
				}
				if (null != invoice.getInvoiceDate()) {
					invoice.setInvoiceDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
							invoice.getInvoiceDate()));
				}
				if (null != invoice.getDueDate()) {
					invoice.setDueDateStr(
							commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), invoice.getDueDate()));
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
							&& (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
									|| Constants.INVOICE_STATUS_PARTIAL_PAID
											.equalsIgnoreCase(invoice.getPaymentStatus())
									|| Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))
							&& !Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())) {
						paidCount = paidCount + 1;
						Float paidAmount = Float.parseFloat(invoice.getTotal())
								- Float.parseFloat(invoice.getDueAmount());
						invoice.setPaidInvoiceAmount(paidAmount.toString());
					}
					if (!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
							&& !Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())
							&& (Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())
									|| Constants.INVOICE_STATUS_PARTIAL_PAID
											.equalsIgnoreCase(invoice.getPaymentStatus())
									|| Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))) {
						Date invoiceDueDate = invoice.getDueDate();
						Float dueAmount = Float.parseFloat(invoice.getDueAmount());
						if (dueAmount > 0) {
							if (todayDate.after(invoiceDueDate)) {
								overDueInvoiceCount = overDueInvoiceCount + 1;
								invoice.setDueAfterInvoiceAmount(invoice.getDueAmount());
							} else {
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
	public List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long orgLocationId, Date startDateUTC, Date endDateUTC)
			throws Exception {
		return invoiceDao.getInvoiceByOrgLocationIdNetIncome(orgLocationId, startDateUTC, endDateUTC);
	}

	@Override
	public List<Invoice> getDueDateCompletedInvoicesList() throws Exception {
		return invoiceDao.getDueDateCompletedInvoicesList();
	}

	@Override
	public void saveInvocePojo(Invoice invoice) throws Exception {
		invoiceDao.saveInvoce(invoice);
	}

	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS,
			Timezone timezone, int numberOfDays) throws ParseException {
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
				yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}

	@Override
	public BillingReportDTO getBillingReports(String billingReportType, String invoiceType, String startDate,
			String endDate, OrgLocations orgLocation, User user, Long loginUserRoleId, String loginOrgCurrencyCode)
			throws Exception {

		DateTimeFormatter startDateOutputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateTimeFormatter endDateOutputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");

		LocalDate today = LocalDate.now();
		LocalDate monthStartDate = new LocalDate().withDayOfMonth(1);
		LocalDate monthEndDate = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
		Date startDateUTC = null;
		Date endDateUTC = null;
		LocalDate date1 = null;
		LocalDate date2 = null;
		List<BillingGraphBean> incomeExpenseList = new ArrayList<>();
		switch (billingReportType) {
		case Constants.THIS_MONTH:
			date1 = monthStartDate;
			date2 = monthEndDate;
			break;
		case Constants.THIS_QUARTER:
			LocalDate[] dates = conversionUtil.getThisMonthQuarterDates(monthStartDate);
			date1 = dates[0];
			date2 = dates[1];
			break;
		case Constants.THIS_YEAR:
			LocalDate[] currentYearDates = conversionUtil.getThisYearStartDateEndDate();
			startDateUTC = currentYearDates[0].toDate();
			endDateUTC = currentYearDates[1].toDate();
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
			date1 = loastMonthStartDate;
			date2 = lastMonthEndDate;
			break;
		case Constants.LAST_QUARTER:
			LocalDate lastQuarterDate = today.minusMonths(3); // 2015-10-24
			LocalDate[] quarterDates = conversionUtil.getThisMonthQuarterDates(lastQuarterDate);
			date1 = quarterDates[0];
			date2 = quarterDates[1];
			break;
		case Constants.LAST_YEAR:
			if (Constants.COUNTRY_INDIA.equalsIgnoreCase(orgLocation.getCountry())) {
				LocalDate finStartDate = conversionUtil.getFinMonthFromDate();
				if (null != finStartDate) {
					LocalDate finEndDate = conversionUtil.getFinMonthEndDate();
					date1 = new LocalDate(finStartDate);
					date2 = new LocalDate(finEndDate);
					;
				}
			} else {
				LocalDate[] financialDates = conversionUtil.getOtherCountryFinYeraDates();
				date1 = financialDates[0];
				date2 = financialDates[1];
			}
			break;
		case Constants.CUSTOM:
			Date localStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), startDate);
			Date localEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), endDate);
			date1 = new LocalDate(localStartDate);
			date2 = new LocalDate(localEndDate);
			break;
		default:
			logger.info("No Frequency");
			;
		}
		// End Time conversion

		BigDecimal receivedAmt = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal payableAmt = BigDecimal.ZERO;
		Date sDate = date1.toDate();
		Date eDate = date2.toDate();

		List<InvoicePayment> paymentList = invoicePaymentService
				.getInvoicePaymentListByOrgloactionIdAndDates(orgLocation.getId(), sDate, eDate);
		BillingReportDTO billingReportDTO = new BillingReportDTO();
		boolean isReceivableAccessYes = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocation.getId(),
				21L);
		boolean isPayableAccessYes = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocation.getId(),
				22L);

		for (InvoicePayment invoicePayment : paymentList) {
			if ((Constants.RECEIVABLE.equalsIgnoreCase(invoicePayment.getInvoiceId().getInvoiceType())
					&& (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoicePayment.getInvoiceId().getPaymentStatus())
							|| Constants.INVOICE_STATUS_PARTIAL_PAID
									.equalsIgnoreCase(invoicePayment.getInvoiceId().getPaymentStatus())))
					&& isReceivableAccessYes) {
				try {
					if (!loginOrgCurrencyCode.equalsIgnoreCase(invoicePayment.getCurrencyCode())) {
						double recievableAmount = conversionUtil.convert(invoicePayment.getCurrencyCode(),
								loginOrgCurrencyCode, Float.parseFloat(invoicePayment.getPaymentAmount()));
						BigDecimal value = new BigDecimal(recievableAmount);
						receivedAmt = receivedAmt.add(value);
					} else {
						BigDecimal recievableAmount = new BigDecimal(invoicePayment.getPaymentAmount());
						receivedAmt = receivedAmt.add(recievableAmount);
					}

				} catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}

			} else if (isPayableAccessYes) {
				try {
					if (!loginOrgCurrencyCode.equalsIgnoreCase(invoicePayment.getCurrencyCode())) {
						double payableAmount = conversionUtil.convert(invoicePayment.getCurrencyCode(),
								loginOrgCurrencyCode, Float.parseFloat(invoicePayment.getPaymentAmount()));
						BigDecimal value = new BigDecimal(payableAmount);
						payableAmt = payableAmt.add(value);
					} else {
						BigDecimal payableAmount = new BigDecimal(invoicePayment.getPaymentAmount());
						payableAmt = payableAmt.add(payableAmount);
					}
				} catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
			}
		}
		List<LocalDate> dates = conversionUtil.getAllDatesBetweenGivenDates(date1, date2);
		for (LocalDate localStartDate : dates) {
			BillingGraphBean billingGraphBean = new BillingGraphBean();
			billingGraphBean.setDate(localStartDate.toString("MMM-yyyy"));
			BigDecimal expensesAmount = BigDecimal.ZERO;
			BigDecimal incomeAmount = BigDecimal.ZERO;
			for (InvoicePayment invoicePayment : paymentList) {
				LocalDate invoiceDate = new LocalDate(invoicePayment.getPaymentDate());
				if ((Constants.RECEIVABLE.equalsIgnoreCase(invoicePayment.getInvoiceId().getInvoiceType())
						&& (Constants.INVOICE_STATUS_PAID
								.equalsIgnoreCase(invoicePayment.getInvoiceId().getPaymentStatus())
								|| Constants.INVOICE_STATUS_PARTIAL_PAID
										.equalsIgnoreCase(invoicePayment.getInvoiceId().getPaymentStatus())))
						&& isReceivableAccessYes) {
					try {
						LocalDate localEndDate = localStartDate.dayOfMonth().withMaximumValue();
						if ((invoiceDate.isAfter(localStartDate) && invoiceDate.isBefore(localEndDate))
								|| (invoiceDate.equals(localStartDate) || invoiceDate.equals(localEndDate))) {

							if (!loginOrgCurrencyCode.equalsIgnoreCase(invoicePayment.getCurrencyCode())) {
								double income = conversionUtil.convert(invoicePayment.getCurrencyCode(),
										loginOrgCurrencyCode, Float.parseFloat(invoicePayment.getPaymentAmount()));
								BigDecimal value = new BigDecimal(income);
								payableAmt = incomeAmount.add(value);

							} else {
								BigDecimal income = new BigDecimal(invoicePayment.getPaymentAmount());
								incomeAmount = incomeAmount.add(income);
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

							if (!loginOrgCurrencyCode.equalsIgnoreCase(invoicePayment.getCurrencyCode())) {
								double expenses = conversionUtil.convert(invoicePayment.getCurrencyCode(),
										loginOrgCurrencyCode, Float.parseFloat(invoicePayment.getPaymentAmount()));
								BigDecimal value = new BigDecimal(expenses);
								expensesAmount = expensesAmount.add(value);
							} else {
								BigDecimal expenses = new BigDecimal(invoicePayment.getPaymentAmount());
								expensesAmount = expensesAmount.add(expenses);
							}
						}
					} catch (Exception e) {
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
				}
			}
			billingGraphBean.setIncome(incomeAmount);
			billingGraphBean.setExpenses(expensesAmount);
			incomeExpenseList.add(billingGraphBean);
		}

		netAmount = receivedAmt.subtract(payableAmt);
		billingReportDTO.setNetAmount(String.valueOf(netAmount));
		billingReportDTO.setReceivedAmount(String.valueOf(receivedAmt));
		billingReportDTO.setPayableAmount(String.valueOf(payableAmt));
		billingReportDTO.setBillingGraphBean(incomeExpenseList);
		return billingReportDTO;
	}

	public void sendInvoiceReminderMail(User user, OrgLocations orgLocation, Invoice invoice, String invoiceType,
			List<TimesheetSubmissionsDocuments> docusList) throws Exception {
		EmailScenario emailScenario = null;
		Map<String, String> returnMap = null;
		// EmailScenario notificationScenario = null;
		if(null != invoice.getContactUsers()){
			String[] contactUsers = invoice.getContactUsers().split(",");
			if(contactUsers.length != 0){
				for(String contactUserId : contactUsers){
					User contactUser = userService.getUserById(Long.parseLong(contactUserId.trim()));
					String UUID = SourceLeadRandomCodeHelper.generateUUID();
					EmailMessage emailMessage = new EmailMessage();
					emailMessage.setFirstName(user.getFirstName());
					emailMessage.setLastName(user.getLastName());
					emailMessage.setToEmail(contactUser.getEmail());
					emailMessage.setFromEmail(user.getEmail());
					emailMessage.setCreatedBy(user.getUserCode());
					StringBuilder msgBuild = new StringBuilder();
					emailMessage
							.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			
					// SimpleDateFormat simpleDateFormat =
					// orgLocation.getLocFullFormDateFormatter();
					String date = (commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoice.getDueDate()));
			
					// setting the class filed values
					Map<String, String> globalVarsMap = new HashMap<String, String>();
					globalVarsMap.put("invoiceID", invoice.getInvoiceId());
					globalVarsMap.put("LoginUser", user.getFirstName());
					if (invoice.getInvoiceType().equalsIgnoreCase(Constants.RECEIVABLE)) {
						globalVarsMap.put("vendorName", contactUser.getFirstName());
					} else {
						globalVarsMap.put("clientName", contactUser.getFirstName());
					}
					globalVarsMap.put("grandTotal", invoice.getDueAmount());
					globalVarsMap.put("dueDate", date);
					globalVarsMap.put("orgName", orgLocation.getOrgLocationName());
					// globalVarsMap.put("address",invoice.getBillingAddress());
			
					InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
					// send mail logic
					List<EmailAttachment> attachmentList1 = new ArrayList<>();
			
					// send notification logic
					ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
					ArrayList<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
					EmailAttachment emailAttachment = new EmailAttachment();
					emailAttachment.setUUID(UUID);
					EmailAttachment attachmentZip = zipFiles(docusList);
					if (null != invoice.getInvoiceDocumentSet() && invoice.getInvoiceDocumentSet().size() > 0
							&& !invoice.getInvoiceDocumentSet().isEmpty()) {
						Set<InvoiceDocument> documenetSet = invoice.getInvoiceDocumentSet();
						for (InvoiceDocument doc : documenetSet) {
							EmailAttachment docAttachment = new EmailAttachment();
							String fileUrl = doc.getFileUrl();
							if (StringUtils.isNotBlank(fileUrl)) {
								docAttachment.setDocFile(CommonUtil.readBytesFromFile(fileUrl));
								docAttachment.setName(doc.getFileName());
								documentList.add(docAttachment);
							}
			
						}
			
					}
					String invoiceFileUrl = invoice.getFileUrl();
					if (StringUtils.isNotBlank(invoiceFileUrl)) {
						emailAttachment.setDocFile(CommonUtil.readBytesFromFile(invoiceFileUrl));
						emailAttachment.setName(invoice.getFileName());
						attachmentList.add(emailAttachment);
					}
					if (attachmentZip != null) {
						attachmentList.add(attachmentZip);
					}
					if (documentList != null && !documentList.isEmpty() && documentList.size() > 0) {
						attachmentList.addAll(documentList);
					}
			
					EmailScenario notificationScenario = null;
					if (invoiceType.equalsIgnoreCase("payables")) {
						
						returnMap = commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation,
								Constants.INVOICE_CLIENT_PAYABLE_REMINDER_MAIL, attachmentList1);
						notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
								Constants.INVOICE_CLIENT_PAYABLE_REMINDER_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);
					} else {
						returnMap = commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation,
								Constants.INVOICE_CLIENT_RECEIVABLE_REMINDER_MAIL, attachmentList1);
						notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
								Constants.INVOICE_CLIENT_RECEIVABLE_REMINDER_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);
					}
					returnMap.put("toEmail", emailMessage.getToEmail());
					returnMap.put("fromEmail", emailMessage.getFromEmail());
					String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
							notificationScenario);
					notificationScenario.setBody(notificationbody);
					System.out.println("************** :" + notificationbody);
					String actionLink = "";
					StringBuilder notificationLink = new StringBuilder();
					// Invoice invoice2=getInvoicetByParentId(invoice.getId());
					Invoice notificationInvoice = getInvoicetByParentId(invoice.getId());
					if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
						actionLink = notificationLink.append(vmsUrl).append("/")
								.append("viewInvoice?invoiceUUID=" + notificationInvoice.getInvoiceUUID() + "&invoiceType=Payables")
								.toString();
					} else {
						actionLink = notificationLink.append(vmsUrl).append("/").append(
								"viewInvoice?invoiceUUID=" + notificationInvoice.getInvoiceUUID() + "&invoiceType=Receivable")
								.toString();
			
					}
					emailScenerioUtil.sendNotification(actionLink, user, invoice.getContactUser(), invoice.getResponderLocationId(),
							notificationScenario, new HashMap<String, String>());
				}
				invoice.setTimelineHistoryReminder("Reminder");
				saveInvoiceTimelineHistory(invoice, user, null, null, null, returnMap);
				Invoice childInvoice = invoiceDao.getInvoicetBySelfJoinInvoiceId(invoice.getId());
				if (childInvoice != null) {
					childInvoice.setTimelineHistoryReminder("Reminder");
					saveInvoiceTimelineHistory(childInvoice, user, null, null, null, returnMap);
				}
			}
		}
	}
	
	public void sendInvoiceReminderMailToNotifyOthers(User user, OrgLocations orgLocation, Invoice invoice, String invoiceType,
			List<TimesheetSubmissionsDocuments> docusList,String emailtoOthers) throws Exception {
		EmailScenario emailScenario = null;
		Map<String, String> returnMap = null;
		// EmailScenario notificationScenario = null;
		
					String UUID = SourceLeadRandomCodeHelper.generateUUID();
					EmailMessage emailMessage = new EmailMessage();
					emailMessage.setFirstName(user.getFirstName());
					emailMessage.setLastName(user.getLastName());
					emailMessage.setToEmail(emailtoOthers);
					emailMessage.setFromEmail(user.getEmail());
					emailMessage.setCreatedBy(user.getUserCode());
					StringBuilder msgBuild = new StringBuilder();
					emailMessage
							.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			
					// SimpleDateFormat simpleDateFormat =
					// orgLocation.getLocFullFormDateFormatter();
					String date = (commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoice.getDueDate()));
			
					// setting the class filed values
					Map<String, String> globalVarsMap = new HashMap<String, String>();
					globalVarsMap.put("invoiceID", invoice.getInvoiceId());
					globalVarsMap.put("LoginUser", user.getFirstName());
					if (invoice.getInvoiceType().equalsIgnoreCase(Constants.RECEIVABLE)) {
						globalVarsMap.put("vendorName", "");
					} else {
						globalVarsMap.put("clientName", "");
					}
					globalVarsMap.put("grandTotal", invoice.getDueAmount());
					globalVarsMap.put("dueDate", date);
					globalVarsMap.put("orgName", orgLocation.getOrgLocationName());
					// globalVarsMap.put("address",invoice.getBillingAddress());
			
					InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
					// send mail logic
					List<EmailAttachment> attachmentList1 = new ArrayList<>();
			
					// send notification logic
					ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
					ArrayList<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
					EmailAttachment emailAttachment = new EmailAttachment();
					emailAttachment.setUUID(UUID);
					EmailAttachment attachmentZip = zipFiles(docusList);
					if (null != invoice.getInvoiceDocumentSet() && invoice.getInvoiceDocumentSet().size() > 0
							&& !invoice.getInvoiceDocumentSet().isEmpty()) {
						Set<InvoiceDocument> documenetSet = invoice.getInvoiceDocumentSet();
						for (InvoiceDocument doc : documenetSet) {
							EmailAttachment docAttachment = new EmailAttachment();
							String fileUrl = doc.getFileUrl();
							if (StringUtils.isNotBlank(fileUrl)) {
								docAttachment.setDocFile(CommonUtil.readBytesFromFile(fileUrl));
								docAttachment.setName(doc.getFileName());
								documentList.add(docAttachment);
							}
			
						}
			
					}
					String invoiceFileUrl = invoice.getFileUrl();
					if (StringUtils.isNotBlank(invoiceFileUrl)) {
						emailAttachment.setDocFile(CommonUtil.readBytesFromFile(invoiceFileUrl));
						emailAttachment.setName(invoice.getFileName());
						attachmentList.add(emailAttachment);
					}
					if (attachmentZip != null) {
						attachmentList.add(attachmentZip);
					}
					if (documentList != null && !documentList.isEmpty() && documentList.size() > 0) {
						attachmentList.addAll(documentList);
					}
			
					EmailScenario notificationScenario = null;
					if (invoiceType.equalsIgnoreCase("payables")) {
						
						returnMap = commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation,
								Constants.INVOICE_CLIENT_PAYABLE_REMINDER_MAIL, attachmentList1);
						notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
								Constants.INVOICE_CLIENT_PAYABLE_REMINDER_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);
					} else {
						returnMap = commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation,
								Constants.INVOICE_CLIENT_RECEIVABLE_REMINDER_MAIL, attachmentList1);
						notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
								Constants.INVOICE_CLIENT_RECEIVABLE_REMINDER_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);
					}
					returnMap.put("toEmail", emailMessage.getToEmail());
					returnMap.put("fromEmail", emailMessage.getFromEmail());
					
				invoice.setTimelineHistoryReminder("Reminder");
				saveInvoiceTimelineHistory(invoice, user, null, null, null, returnMap);
				Invoice childInvoice = invoiceDao.getInvoicetBySelfJoinInvoiceId(invoice.getId());
				if (childInvoice != null) {
					childInvoice.setTimelineHistoryReminder("Reminder");
					saveInvoiceTimelineHistory(childInvoice, user, null, null, null, returnMap);
				}
	}

	@Override
	public Invoice getInvoicetByParentId(Long invoiceId) throws Exception {
		return invoiceDao.getInvoicetByParentId(invoiceId);
	}

	@Override
	public boolean deleteInvoice(String invoiceUUID, String reason, String invoiceType, User user) throws Exception {
		boolean result = false;
		Invoice invoice = invoiceDao.getInvoicetByInvoiceUUID(invoiceUUID);
		Date date = new Date();
		// for jobclosure invoice deleted.
		List<InvoiceLineItems> lineItemsList = invoiceLineItemDao.getInvoiceLineItemsByInvoiceId(invoice.getId());
		lineItemsList.forEach(items->{
			if(items.getRequirementSubmissionId() !=null){
			try {
				RequirementSubmission submission = requirementSubmissionDao.getRequirementSubmissionById(items.getRequirementSubmissionId().getId());
				submission.setInvoiceGenerated(null);
				requirementSubmissionDao.storeRequirementSubmission(submission);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		});
		
		if(invoice.getProjectID() != null){
				invoice.setRejectReason(reason);
				invoice.setStatus(Constants.MEMBER_IN_ACTIVE);
				invoice.setUpdatedDate(date);
				invoiceDao.updateInvoice(invoice);
						 try {
							 if(invoice.getSubmissionApproverHierarchyId() !=null){
								 String[] subIds = invoice.getSubmissionApproverHierarchyId().split(",");
								 for(String sIds :subIds ){
									 submissionApproverHierarchyService.updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long.parseLong(sIds), invoiceType, null,invoice); 
								 }
								 	 
							 }
							
						} catch (Exception e) {
							e.printStackTrace();
						}
		}else{
			invoice.setRejectReason(reason);
			invoice.setStatus(Constants.MEMBER_IN_ACTIVE);
			invoice.setUpdatedDate(date);
			Invoice invoiceData = invoiceDao.updateInvoice(invoice);
			if (invoiceData != null) {
				result = true;
			}
		}
		saveInvoiceTimelineHistory(invoice, user, null,
				null, null, null);
		return result;
	}

	@Override
	public Invoice saveExpenseInvoice(InvoiceDTO invoiceDTO, User user, OrgLocations orgLocation, String saveType,
			String invoiceType, Org org, String loginOrgCurrencyCode, String invoiceCategory, String defaultFont) throws Exception {

		Invoice invoice = null;
		String savedInvoiceId = null;
		if (StringUtils.isNotBlank(invoiceDTO.getSavedInvoiceId())) {
			savedInvoiceId = invoiceDTO.getSavedInvoiceId();
			invoice = invoiceDao.getInvoicetById(Long.valueOf(invoiceDTO.getSavedInvoiceId()));
		} else {
			invoice = new Invoice();
			invoice.setInvoiceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			invoice.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocation));
			invoice.setCreatedBy(user.getUserCode());
		}

		OrgLocations vendorOrgLocation = null;
		if (Constants.PAYABLE.equalsIgnoreCase(invoiceType)) {
			if (StringUtils.isNotBlank(invoiceDTO.getVendorOrgRelationId())) {
				OrgRelations orgRelation = orgRelationsService
						.getOrRelationsById(Long.parseLong(invoiceDTO.getVendorOrgRelationId()));
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

			if (StringUtils.isNotBlank(invoiceDTO.getVendorContact())) {
				/*User contactUser = userService.getUserById(Long.parseLong(invoiceDTO.getVendorContact()));
				invoice.setContac(contactUser);*/
				invoice.setContactUsers(invoiceDTO.getVendorContact());

			}
			invoice.setInvoiceType(Constants.Payables);
		} else {
			if (StringUtils.isNotBlank(invoiceDTO.getClientOrgRelationId())) {
				OrgRelations orgRelation = orgRelationsService
						.getOrRelationsById(Long.parseLong(invoiceDTO.getClientOrgRelationId()));
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

			if (StringUtils.isNotBlank(invoiceDTO.getClientContact())) {
				/*User userContactName = userService.getUserById(Long.parseLong(invoiceDTO.getClientContact()));
				invoice.setContactUser(userContactName);*/
     			invoice.setContactUsers(invoiceDTO.getClientContact());

			}
			invoice.setInvoiceType(Constants.Receivable);
		}

		// currency conversion
		String vendorCurrencyCode = conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if (!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)) {
			if (null != vendorCurrencyCode) {
				float convertedAmount = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode,
						Float.parseFloat(invoiceDTO.getGrandTotal()));
				float exchangeRate = conversionUtil.convert(loginOrgCurrencyCode, vendorCurrencyCode, 1);
				 //Accept three decimal points
				float convertedAmount1 = Float.parseFloat((new DecimalFormat(".###").format(((convertedAmount * 100.0) / 100.0))));
				invoice.setConvertCurrencyCode(vendorCurrencyCode);
				invoice.setConvertAmount(String.valueOf(convertedAmount1));
				exchangeRate = convertedAmount1 * Float.valueOf(invoiceDTO.getGrandTotal());
				 //Accept three decimal points
		      	DecimalFormat decimalFormat = new DecimalFormat("#.000");
		        String afterConvert = decimalFormat.format(exchangeRate);
				invoice.setExchangeRate(afterConvert);
			}
		}

		invoice.setOwnerUsers(user.getId().toString());
		invoice.setOrgLocation(orgLocation);
		if (StringUtils.isNotBlank(invoiceDTO.getInvoiceId())) {
			invoice.setInvoiceId(invoiceDTO.getInvoiceId());
		}
		if (StringUtils.isNotBlank(invoiceDTO.getBillingAddress())) {
			invoice.setBillingAddress(invoiceDTO.getBillingAddress());
		}
		if (StringUtils.isNotBlank(invoiceDTO.getAccountDetails())) {
			invoice.setAccountDetails(invoiceDTO.getAccountDetails());
		}

		if (StringUtils.isNotBlank(invoiceDTO.getInvoiceDate())) {
			Date invoiceDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
					invoiceDTO.getInvoiceDate());
			invoice.setInvoiceDate(invoiceDate);
		}

		Date dueDate = null;
		if (StringUtils.isNotBlank(invoiceDTO.getDueDate())) {
			dueDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), invoiceDTO.getDueDate());
			invoice.setDueDate(dueDate);
		}

		if (StringUtils.isNotBlank(invoiceDTO.getPaymentFrequency())) {
			invoice.setPaymentFrequency(invoiceDTO.getPaymentFrequency());
		} else {
			invoice.setPaymentFrequency(null);
		}

		if(StringUtils.isNotBlank(invoiceDTO.getNotifyToOthers())){
			invoice.setNotifyToOthers(invoiceDTO.getNotifyToOthers());	
		}else{
			invoice.setNotifyToOthers(null);
		}
		if (StringUtils.isNotBlank(invoiceDTO.getPaymentAccount())) {
			PaymentAccount paymentaccount = new PaymentAccount();
			paymentaccount.setId(Long.valueOf(invoiceDTO.getPaymentAccount()));
			invoice.setPaymentAccount(paymentaccount);
		}else{
			invoice.setPaymentAccount(null);
		}

		if (StringUtils.isNotBlank(invoiceDTO.getDisCount())) {
			invoice.setDiscountType(invoiceDTO.getDisCount());

		}

		if (StringUtils.isNotBlank(invoiceDTO.getDiscountTotal())) {
			invoice.setDiscountAmount(invoiceDTO.getDiscountTotal());
		}
		if (StringUtils.isNotBlank(invoiceDTO.getDiscountTotal())) {
			invoice.setDiscountValue(invoiceDTO.getDiscountTotal());

		}
		if (StringUtils.isNotBlank(invoiceDTO.getInoviceTotal())) {
			invoice.setSubTotal(invoiceDTO.getInoviceTotal());

		}

		if (StringUtils.isNotBlank(invoiceDTO.getComment())) {
			invoice.setInvoiceComment(invoiceDTO.getComment());
		}

		if (StringUtils.isNotBlank(invoiceDTO.getGrandTotal())) {
			invoice.setTotal(invoiceDTO.getGrandTotal());
			invoice.setDueAmount(invoiceDTO.getGrandTotal());
		}

		if (StringUtils.isNotBlank(invoiceDTO.getAfterDiscountTotal())) {
			invoice.setAfterDiscountValue(invoiceDTO.getAfterDiscountTotal());
		}

		if (commonUtil.currentDate().after(dueDate)) {
			invoice.setPaymentStatus(Constants.OVER_DUE);
		} else {
			invoice.setPaymentStatus(Constants.INVOICE_STATUS_UNPAID);
		}

		if (StringUtils.isNotBlank(invoiceDTO.getProjectId())) {
			Projects projects = projectsService.getProjectById(Long.parseLong(invoiceDTO.getProjectId()));
			invoice.setProjectID(projects);
		}

		if (StringUtils.isNotBlank(invoiceDTO.getFrequency())) {
			invoice.setInvoiceFrequency(invoiceDTO.getFrequency());
		}

		if (StringUtils.isNotBlank(invoiceDTO.getProjectMemberId())) {
			ProjectMembers pm = projectMembersService
					.getProjectMemberById(Long.parseLong(invoiceDTO.getProjectMemberId()));
			invoice.setProjectMemberId(pm);
		}
		if (StringUtils.isNotBlank(invoiceDTO.getExpensesId())) {
			Expenses expenses = expensesService.getExpenseById(Long.parseLong(invoiceDTO.getExpensesId()));
			invoice.setExpensesId(expenses);
		}

		invoice.setCurrencyCode(loginOrgCurrencyCode);
		invoice.setStatus(Constants.RECORD_ACTIVE.toString());
		invoice.setInvoiceStatus(saveType);
		invoice.setUpdatedBy(user.getUserCode());
		invoice.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocation));
		

		List<String> submissionApproverHierarchyIds = new ArrayList<>();
		List<TimesheetSubmissionsDocuments> docusList = new ArrayList<TimesheetSubmissionsDocuments>();

		// storing invoive line items
		List<Long> invoiceLineItemsIdsList = null;
		if (null != invoice.getId()) {
			invoiceLineItemsIdsList = invoiceLineItemDao.getInvoiceLineItemsIdsByInvoiceId(invoice.getId());
		}
		Set<InvoiceLineItems> invoiceLineItemsSet = getExpensesInvoiceLineItems(invoiceDTO, user, orgLocation, invoice,
				invoiceLineItemsIdsList, submissionApproverHierarchyIds, docusList);
		invoice.setInvoiceLineItems(invoiceLineItemsSet);

		invoice.setSubmissionApproverHierarchyIds(submissionApproverHierarchyIds);

		// Store Tax
		List<Long> invoiceTaxItemsIdsList = null;
		if (null != invoice.getId()) {
			invoiceTaxItemsIdsList = invoiceTaxDao.getInvoiceTaxIdsByInvoiceId(invoice.getId());
		}
		Set<InvoiceTax> invoiceTax = getInvoiceTax(user, orgLocation, invoice, invoiceTaxItemsIdsList, invoiceDTO);
		invoice.setInvoiceTax(invoiceTax);

		// store attached Documents
		if (invoiceDTO.getFileInfo() != null && !invoiceDTO.getFileInfo().isEmpty()) {
			Set<InvoiceDocument> invoiceDocumentsSet = getInvoiceDocuments(invoiceDTO, user, orgLocation, invoice);
			invoice.setInvoiceDocumentSet(invoiceDocumentsSet);
		}

		// store attached Documents (after)
		boolean invoiceTemplateConfigured = false;
		File file = null;
		if(invoiceDTO.getInvoiceTemplateId() > 0) {
			CustomInvoiceTemplate customInvoiceTemplate = invoiceDao.getCustomInvoiceTemplateById(invoiceDTO.getInvoiceTemplateId());
			if(customInvoiceTemplate != null) {
				file = customInvoicePDFHelper.createCustomInvoicePDF(invoice, orgLocation, user, org, vendorCurrencyCode, loginOrgCurrencyCode, customInvoiceTemplate, "other", defaultFont, sourceleadUrl);
				invoiceTemplateConfigured = true;
			}
		}
		if(!invoiceTemplateConfigured)
			file = expensesInvoicePdfHelper.createPdfTableForExpensesInvoice(invoice, orgLocation, user, org, invoiceType, vendorCurrencyCode, loginOrgCurrencyCode);
		
		invoice.setFileName(file.getName());
		if (file.exists() && !file.isDirectory()) {
			invoice.setFileName(file.getName());
			invoice.setFileUrl(FileUploadUtil.moveFile(staticPath, org.getId(), orgLocation.getId(), "InvoicePdf", file));
		}
		if (null != file) {
			file.delete();
		}

		// byte[] bytesArray = getByteArray(file);
		// invoice.setDocFile(bytesArray);
		// invoiceDao.saveInvoce(invoice);
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
		if (StringUtils.isNotBlank(invoiceDTO.getInvoiceforexpenses())) {
			invoice.setExpensesInvoices(invoiceDTO.getInvoiceforexpenses());
		}
		

		invoiceDao.saveInvoce(invoice);
		Map<String, String> returnMap = new HashMap<>();
		if (Constants.SENT.equalsIgnoreCase(saveType)) {
			Invoice parentInvoice = saveParentInvoice(invoice, loginOrgCurrencyCode, vendorOrgLocation, user);
			returnMap = sendInvoiceMail(user, orgLocation, invoice, invoiceType, docusList);
			//send notifyTo others email 
			if(StringUtils.isNotBlank(invoiceDTO.getNotifyToOthers())) {
				for(String notifyToEmail : invoiceDTO.getNotifyToOthers().split(",")) {
					sendInvoiceMailToOthers(user, orgLocation, invoice, invoiceType, docusList,notifyToEmail);
				}
			}
			if (parentInvoice.getId() != null) {
				saveInvoiceTimelineHistory(parentInvoice, user, null, null, null, returnMap);
			}
		}
		saveInvoiceTimelineHistory(invoice, user, null, savedInvoiceId, null,returnMap);
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
	private Set<InvoiceLineItems> getExpensesInvoiceLineItems(InvoiceDTO invoiceDTO, User user,
			OrgLocations orgLocation, Invoice invoice, List<Long> invoiceLineItemsIdsList,
			List<String> submissionApproverHierarchyIds, List<TimesheetSubmissionsDocuments> docusList)
			throws NumberFormatException, Exception {
		Set<InvoiceLineItems> expensesinvoicelineitemsSet = new HashSet<>();

		/*
		 * String pattern = "dd-MM-yyyy"; SimpleDateFormat format = new
		 * SimpleDateFormat(pattern);
		 */

		List<Long> updatedInvoiceLineItems = new ArrayList<Long>();
		List<InvoiceExpensesDTO> invoiceExpensesDtoList = invoiceDTO.getExpenseInvoice();
		if (invoiceExpensesDtoList != null && !invoiceExpensesDtoList.isEmpty()) {
			for (InvoiceExpensesDTO invoiceExpensesDTO : invoiceExpensesDtoList) {
				// expensesDate invoice; orgLocation; description; quantity; total;
				// status;
				if (StringUtils.isNotBlank(invoiceExpensesDTO.getDescription())) {
					InvoiceLineItems invoiceLineItems = new InvoiceLineItems();

					if (StringUtils.isNotBlank(invoiceExpensesDTO.getLineItemId())) {
						// expensesInvoiceLineItems.setId(Long.valueOf(jsonObj.
						// getString(i+"lineItemId")));
						updatedInvoiceLineItems.add(Long.valueOf(invoiceExpensesDTO.getLineItemId()));
					}

					if (StringUtils.isNotBlank(invoiceExpensesDTO.getEdate())) {
						/*Date expenseDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
								invoiceExpensesDTO.getEdate());*/
						Date expenseDate=new SimpleDateFormat("dd-MM-yyyy").parse(invoiceExpensesDTO.getEdate());  
						invoiceLineItems.setStartDate(expenseDate);
					}
					if (StringUtils.isNotBlank(invoiceExpensesDTO.getDescription())) {
						String description = invoiceExpensesDTO.getDescription();
						invoiceLineItems.setInlineDescription(description);
						;
					}
					if (StringUtils.isNotBlank(invoiceExpensesDTO.getQuantity())) {
						invoiceLineItems.setQuantity(invoiceExpensesDTO.getQuantity());
					}

					if (StringUtils.isNotBlank(invoiceExpensesDTO.getRate())) {
						invoiceLineItems.setRate(invoiceExpensesDTO.getRate());
					}

					if (StringUtils.isNotBlank(invoiceExpensesDTO.getTotal())) {
						invoiceLineItems.setAmount(invoiceExpensesDTO.getTotal());
					}
					if (StringUtils.isNotBlank(invoiceExpensesDTO.getCategory())) {
						invoiceLineItems.setExpenseCategory(invoiceExpensesDTO.getCategory());
					}

					invoiceLineItems.setOrgLocation(orgLocation);
					invoiceLineItems.setStatus(Constants.RECORD_ACTIVE.toString());
					invoiceLineItems.setCreatedBy(user.getUserCode());
					invoiceLineItems.setCreatedDate(commonUtil.currentDate());
					invoiceLineItems.setInvoice(invoice);
					expensesinvoicelineitemsSet.add(invoiceLineItems);
					// }
				}

			}

			if (null != invoice.getId()) {
				updatedInvoiceLineItems.retainAll(invoiceLineItemsIdsList);
				// invoiceLineItemsIdsList.removeAll(updatedInvoiceLineItems);
			}
		}

		return expensesinvoicelineitemsSet;

	}

	@Override
	public void updateExpensesForinvoice(Invoice invoice, String invoiceType) throws Exception {
		Expenses expenses = invoice.getExpensesId();
		if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
			expenses.setBillingRecievableStatus("SENT");
		} else {
			expenses.setBillingPayableStatus("SENT");
		}
		addExpensesDao.storeAddExpensesData(expenses);
	}

	@Override
	public List<Invoice> getAllBillsAndInvoicesBYProjectIdOrgLocationIdInvoiceStatusStartDateAndEndDate(Long projectId,
			Long orgLocationId, String invoiceStatus, Date startDate, Date endDate) throws Exception {
		return invoiceDao.getAllBillsAndInvoicesBYProjectIdOrgLocationIdInvoiceStatusStartDateAndEndDate(projectId,
				orgLocationId, invoiceStatus, startDate, endDate);
	}

	@Override
	public ResponseList<Invoice> invoiceListByOrgLocationIdAndRelationId(InvoiceDTO invoiceDTO,
			OrgLocations orgLocations, Long orgRelationId) throws Exception {
		return invoiceDao.invoiceListByOrgLocationIdAndRelationId(invoiceDTO, orgLocations, orgRelationId);
	}

	@Override
	public void saveInvoiceTimelineHistory(Invoice invoice, User user, Invoice linkedChildInvoice,
			String savedInvoiceId, Long invoicePaymentId, Map<String, String> returnMap) {
		InvoiceTimelineHistory timelineHistory = new InvoiceTimelineHistory();
		timelineHistory.setInvoice(invoice);
		timelineHistory.setOrgLocation(invoice.getOrgLocation());
		timelineHistory.setOrgRelation(invoice.getOrgRelationID());
		timelineHistory.setContactUsers(invoice.getContactUsers());
		timelineHistory.setOwnerUsers(invoice.getOwnerUsers());
		timelineHistory.setCreatedBy(user);
		String invoiceStatus = null;
		if(invoice.getInvoiceStatus().equalsIgnoreCase("DRAFT")){
			invoice.setInvoiceStatus(Constants.INVOICE_TIMELINE_STATUS_CREATED);
		}
		if ((savedInvoiceId != null && invoice.getInvoiceStatus().equalsIgnoreCase(Constants.INVOICE_TIMELINE_STATUS_CREATED))
				|| (savedInvoiceId != null && invoice.getInvoiceStatus().equalsIgnoreCase("Rejected"))) {
			invoiceStatus = "Updated";
		} else if (invoice.getInvoiceStatus().equalsIgnoreCase("Sent")) {
			invoiceStatus = invoice.getInvoiceStatus();
			if (returnMap != null) {
				timelineHistory.setInvoicePaymentReminderEmailBody(returnMap.get("emailBody"));
				timelineHistory.setInvoicePaymentReminderEmailFrom(returnMap.get("fromEmail"));
				timelineHistory.setInvoicePaymentReminderEmailTo(returnMap.get("toEmail"));
				timelineHistory.setInvoicePaymentReminderEmailSubject(returnMap.get("emailSubject"));
			}
		}else if (invoice.getInvoiceStatus().equalsIgnoreCase("Received")) {
			invoiceStatus = invoice.getInvoiceStatus();
			if (returnMap != null) {
				timelineHistory.setInvoicePaymentReminderEmailBody(returnMap.get("emailBody"));
				timelineHistory.setInvoicePaymentReminderEmailFrom(returnMap.get("fromEmail"));
				timelineHistory.setInvoicePaymentReminderEmailTo(returnMap.get("toEmail"));
				timelineHistory.setInvoicePaymentReminderEmailSubject(returnMap.get("emailSubject"));
			}
		} else if(invoice.getStatus().equalsIgnoreCase("0")){
			invoiceStatus = "Deleted";
			timelineHistory.setRejectReason(invoice.getRejectReason());
		}else if(invoice.getInvoiceStatus().equalsIgnoreCase("Rejected")){
			invoiceStatus = invoice.getInvoiceStatus();
			timelineHistory.setRejectReason(invoice.getRejectReason());			
		}else {
			invoiceStatus = invoice.getInvoiceStatus();
		}
		timelineHistory.setInvoiceStatus(invoiceStatus);
		timelineHistory.setCreatedDate(new Date());
		if (linkedChildInvoice != null) {
			timelineHistory.setLinkedChildinvoice(linkedChildInvoice);
			timelineHistory.setInvoiceStatus(linkedChildInvoice.getInvoiceStatus());
		}else if (invoice.getPaymentStatus().equalsIgnoreCase("Paid")) {
			timelineHistory.setInvoicePaymentReminderId(invoicePaymentId);
			timelineHistory.setInvoicePaymentReminderStatus(invoice.getPaymentStatus());
			timelineHistory.setInvoiceStatus(Constants.INVOICE_TIMELINE_STATUS_PAID);
		}else if (invoice.getPaymentStatus().equalsIgnoreCase(Constants.INVOICE_STATUS_PARTIAL_PAID) && invoicePaymentId != null) {
			timelineHistory.setInvoicePaymentReminderId(invoicePaymentId);
			timelineHistory.setInvoicePaymentReminderStatus(invoice.getPaymentStatus());
			timelineHistory.setInvoiceStatus(invoice.getPaymentStatus());
		} else if (invoice.getTimelineHistoryReminder() != null) {
			timelineHistory.setInvoicePaymentReminderEmailBody(returnMap.get("emailBody"));
			timelineHistory.setInvoicePaymentReminderEmailFrom(returnMap.get("fromEmail"));
			timelineHistory.setInvoicePaymentReminderEmailTo(returnMap.get("toEmail"));
			timelineHistory.setInvoicePaymentReminderEmailSubject(returnMap.get("emailSubject"));
			timelineHistory.setInvoicePaymentReminderStatus(invoice.getTimelineHistoryReminder());
			timelineHistory.setInvoiceStatus(invoice.getTimelineHistoryReminder());
		}
		invoiceDao.saveInvoiceTimelineHistory(timelineHistory);
	}

	@Override
	public List<InvoiceTimelineDTO> getInvoiceTimelineHistoryList(Long id, User user, String searchVal, String orderVal, Date[] datesRange, String[] checkedStatus, String[] namesStatus) throws Exception {
		List<InvoiceTimelineDTO> timelineDTOs = new ArrayList<>();
		List<InvoiceTimelineHistory> timelineHistories = invoiceDao.getInvoiceTimelineHistoryList(id, searchVal, orderVal, datesRange, checkedStatus);
		for (InvoiceTimelineHistory timelineHistory : timelineHistories) {
			InvoiceTimelineDTO timelineDTO = new InvoiceTimelineDTO();
			String status = timelineHistory.getInvoiceStatus();
			Date createdDate = timelineHistory.getCreatedDate();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateWithoutTime = formatter.parse(formatter.format(createdDate));
			Date currentDateWithoutTime = formatter.parse(formatter.format(commonUtil.currentDate()));
			String dateValue = null;
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDateWithoutTime);
			cal.add(Calendar.DATE, -1);
			Date currentDateMinueOneDay = cal.getTime();
			if (dateWithoutTime.equals(currentDateWithoutTime)) {
				dateValue = "Today";
			} else if (dateWithoutTime.equals(currentDateMinueOneDay)) {
				dateValue = "Yesterday";
			} else {
				dateValue = formatter.format(dateWithoutTime);
			}
			timelineDTO.setDateValue(dateValue);
			SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm a");
			String timeValue = localDateFormat.format(createdDate);
			timelineDTO.setTimeValue(timeValue);
			timelineDTO.setStatus(status);
			String statusValue = null;
			String statusVal = null;
			String statusBy = null;
			if (timelineHistory.getLinkedChildinvoice() != null) {
				statusVal = "Linked to ";
				timelineDTO.setInvoiceId(timelineHistory.getLinkedChildinvoice().getInvoiceId());
				timelineDTO.setInvoiceUUID(timelineHistory.getLinkedChildinvoice().getInvoiceUUID());
				List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getLinkedChildinvoice().getOwnerUsers());
				for(User u  :usersList){
					statusValue = u.getFullName();	
				}
				//statusValue = timelineHistory.getLinkedChildinvoice().getOwnerUserId().getFullName();
				//statusValue = " to " + timelineHistory.getLinkedChildinvoice().getInvoiceId()+" by "+timelineHistory.getLinkedChildinvoice().getOwnerUserId().getFullName();
			} else {
				if (timelineHistory.getInvoicePaymentReminderStatus() != null) {
					timelineDTO.setInvoicePaymentReminderStatus(timelineHistory.getInvoicePaymentReminderStatus());
					if (timelineHistory.getInvoicePaymentReminderStatus().equalsIgnoreCase(Constants.INVOICE_STATUS_PAID)) {
						InvoicePayment invoicePayment = invoicePaymentService
								.invoicePaymentById(timelineHistory.getInvoicePaymentReminderId());
						timelineDTO.setPaymentAmount(invoicePayment.getPaymentAmount());
						timelineDTO.setPaymentCurrency(invoicePayment.getCurrencyCode());
						timelineDTO.setPaymentMethod(invoicePayment.getPaymentMethod());
						timelineDTO.setPaymentReferenceNum(invoicePayment.getPaymentReferenceNumber());
						timelineDTO.setPaymentDate(formatter.format(invoicePayment.getPaymentDate()));
						timelineDTO.setPaymentComment(invoicePayment.getPaymentComment());
						timelineDTO.setInvoicePaymentReminderStatus(timelineHistory.getInvoicePaymentReminderStatus());
						if(invoicePayment.getFileName() != null) {
							EmailAttachment emailAttachment = new EmailAttachment();
							emailAttachment.setFileUrl(invoicePayment.getFileURL());
							emailAttachment.setName(invoicePayment.getFileName());
							emailAttachment.setContent(invoicePayment.getFileContentType());
							timelineDTO.setEmailAttachment(emailAttachment);
						}
						List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getOwnerUsers());
						 
						for(User ownerUser: usersList){
						if(ownerUser.getId().equals(timelineHistory.getCreatedBy().getId())){

							statusVal = "Payment updated";
							statusBy = " by ";
							statusValue =timelineHistory.getCreatedBy().getFullName();	
							}
						else if (!timelineHistory.getCreatedBy().getId()
								.equals(ownerUser.getId())) {
							statusVal = "Payment updated";
							statusBy = " by ";
							statusValue = timelineHistory.getCreatedBy().getFullName();
						}
					}
					}
					else if (timelineHistory.getInvoicePaymentReminderStatus().equalsIgnoreCase(Constants.INVOICE_STATUS_PARTIAL_PAID)) {
						InvoicePayment invoicePayment = invoicePaymentService
								.invoicePaymentById(timelineHistory.getInvoicePaymentReminderId());
						timelineDTO.setPaymentAmount(invoicePayment.getPaymentAmount());
						timelineDTO.setPaymentCurrency(invoicePayment.getCurrencyCode());
						timelineDTO.setPaymentMethod(invoicePayment.getPaymentMethod());
						timelineDTO.setPaymentReferenceNum(invoicePayment.getPaymentReferenceNumber());
						timelineDTO.setPaymentDate(formatter.format(invoicePayment.getPaymentDate()));
						timelineDTO.setPaymentComment(invoicePayment.getPaymentComment());
						timelineDTO.setInvoicePaymentReminderStatus(timelineHistory.getInvoicePaymentReminderStatus());
						if(invoicePayment.getFileName() != null) {
							EmailAttachment emailAttachment = new EmailAttachment();
							emailAttachment.setFileUrl(invoicePayment.getFileURL());
							emailAttachment.setName(invoicePayment.getFileName());
							emailAttachment.setContent(invoicePayment.getFileContentType());
							timelineDTO.setEmailAttachment(emailAttachment);
						}
						List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getOwnerUsers());
						 
						for(User ownerUser: usersList){
							if (ownerUser.getId().equals(timelineHistory.getCreatedBy().getId())) {
								statusVal = "Partial Paid updated";
								statusBy = " by ";
								statusValue =timelineHistory.getCreatedBy().getFullName();
							} else if (!timelineHistory.getCreatedBy().getId()
									.equals(ownerUser.getId())) {
								statusVal = "Partial Paid updated";
								statusBy = " by ";
								statusValue = timelineHistory.getCreatedBy().getFullName();
							}
					}
					}else if (timelineHistory.getInvoicePaymentReminderStatus().equalsIgnoreCase("Reminder")) {

						timelineDTO.setInvoiceReminderEmailBody(timelineHistory.getInvoicePaymentReminderEmailBody());
						timelineDTO.setInvoiceReminderEmailTo(timelineHistory.getInvoicePaymentReminderEmailTo());
						timelineDTO.setInvoiceReminderEmailFrom(timelineHistory.getInvoicePaymentReminderEmailFrom());
						timelineDTO.setInvoiceReminderEmailSubject(
								timelineHistory.getInvoicePaymentReminderEmailSubject());
						timelineDTO.setInvoicePaymentReminderStatus(timelineHistory.getInvoicePaymentReminderStatus());
						List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getOwnerUsers());
						StringBuilder sb=new StringBuilder();
						String conUser1=null;
						for(User ownerUser: usersList){
							if (ownerUser.getId().equals(timelineHistory.getCreatedBy().getId())) {
								statusVal = "Reminder";
								statusBy = " to ";
								List<User> contactUsersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getContactUsers());
								for(User contactUsers :contactUsersList){
									conUser1 = sb.append(contactUsers.getFullName()).append(",").toString();
									statusValue = contactUsers.getFullName();
								
								}
								
								statusValue = conUser1.replace(conUser1.substring(conUser1.length()-1), "");
								EmailAttachment emailAttachment = null;
								String invoiceFileUrl = timelineHistory.getInvoice().getFileUrl();
								if (StringUtils.isNotBlank(invoiceFileUrl)) {
									emailAttachment = new EmailAttachment();
									emailAttachment.setFileUrl(invoiceFileUrl);
									emailAttachment.setName(timelineHistory.getInvoice().getFileName());
									emailAttachment.setContent("application/pdf");
								}
								timelineDTO.setEmailAttachment(emailAttachment);	
							}
							if (!timelineHistory.getCreatedBy().getId()
									.equals(ownerUser.getId())) {
								statusVal = "Reminder";
								statusBy = " from ";
								List<User> contactUsersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getContactUsers());
								for(User contactUsers :contactUsersList){
									statusValue = contactUsers.getFullName();
								
								}
								EmailAttachment emailAttachment = null;
								String invoiceFileUrl = timelineHistory.getInvoice().getFileUrl();
								if (StringUtils.isNotBlank(invoiceFileUrl)) {
									emailAttachment = new EmailAttachment();
									emailAttachment.setFileUrl(invoiceFileUrl);
									emailAttachment.setName(timelineHistory.getInvoice().getFileName());
									emailAttachment.setContent("application/pdf");
								}
								timelineDTO.setEmailAttachment(emailAttachment);
							}

						}
					}
				} else {
					if (status.equalsIgnoreCase(Constants.INVOICE_TIMELINE_STATUS_CREATED)) {
						statusVal = "Created";
						statusBy = " by ";
						List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getOwnerUsers());
						 
						for(User ownerUser: usersList){
							statusValue = ownerUser.getFullName();	
						}
					} else if (status.equalsIgnoreCase("Sent")) {
						statusVal = "Sent";
						statusBy = " by ";
						List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getOwnerUsers());
						 
						for(User ownerUser: usersList){
							statusValue = ownerUser.getFullName();	
						}
						timelineDTO.setInvoiceReminderEmailBody(timelineHistory.getInvoicePaymentReminderEmailBody());
						timelineDTO.setInvoiceReminderEmailTo(timelineHistory.getInvoicePaymentReminderEmailTo());
						timelineDTO.setInvoiceReminderEmailFrom(timelineHistory.getInvoicePaymentReminderEmailFrom());
						timelineDTO.setInvoiceReminderEmailSubject(
								timelineHistory.getInvoicePaymentReminderEmailSubject());
					} else if (status.equalsIgnoreCase("Received")) {
						statusVal = "Received";
						statusBy = " from ";
						
						List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getContactUsers());
					 
					for(User contactUser: usersList){
//					if(contactUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
						statusValue = contactUser.getFullName();	
//					}
					}
//						statusValue = timelineHistory.getInvoice().getContactUser().getFullName();
						timelineDTO.setInvoiceReminderEmailBody(timelineHistory.getInvoicePaymentReminderEmailBody());
						timelineDTO.setInvoiceReminderEmailTo(timelineHistory.getInvoicePaymentReminderEmailTo());
						timelineDTO.setInvoiceReminderEmailFrom(timelineHistory.getInvoicePaymentReminderEmailFrom());
						timelineDTO.setInvoiceReminderEmailSubject(
								timelineHistory.getInvoicePaymentReminderEmailSubject());
					} else if (status.equalsIgnoreCase("Accepted")) {
						List<User> ownerUsersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getOwnerUsers());
						List<User> contactUsersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getContactUsers());
						for(User ownerUser: ownerUsersList){
							if (timelineHistory.getCreatedBy().getId().equals(ownerUser.getId())) {
								statusVal = "Accepted";
								statusBy = " by ";
								//if(ownerUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
									statusValue = ownerUser.getFullName();	
								//}
//								statusValue = timelineHistory.getInvoice().getOwnerUserId().getFullName();
							} 
							else if (!timelineHistory.getCreatedBy().getId()
									.equals(ownerUser.getId())) {
								statusVal = "Accepted";
								statusBy = " by ";
							for(User contactUser: contactUsersList){
							//if(contactUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
								statusValue = contactUser.getFullName();	
							//}
							}
//								statusValue = timelineHistory.getInvoice().getContactUser().getFullName();
							}
						}
						
					} else if (status.equalsIgnoreCase("Rejected")) {
						List<User> ownerUsersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getOwnerUsers());
						List<User> contactUsersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getContactUsers());
						for(User ownerUser: ownerUsersList){
							if (timelineHistory.getCreatedBy().getId().equals(ownerUser.getId())) {
								statusVal = "Rejected";
								statusBy = " by ";
								//if(ownerUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
									statusValue = ownerUser.getFullName();	
								//}

								
//								statusValue = timelineHistory.getInvoice().getOwnerUserId().getFullName();
								timelineDTO.setReason(timelineHistory.getRejectReason());
							}

							else if (!timelineHistory.getCreatedBy().getId()
									.equals(ownerUser.getId())) {
								statusVal = "Rejected";
								statusBy = " by ";
							 
							for(User contactUser: contactUsersList){
							//if(contactUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
								statusValue = contactUser.getFullName();	
							//}
							}
//								statusValue = timelineHistory.getInvoice().getContactUser().getFullName();
								timelineDTO.setReason(timelineHistory.getRejectReason());

							}
						}

					} else if (status.equalsIgnoreCase("Updated")) {
						statusVal = "Updated";
						statusBy = " by ";
						List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getOwnerUsers());
						 
						for(User ownerUser: usersList){
						if(ownerUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
							statusValue = ownerUser.getFullName();	
						}
						}
//						statusValue = timelineHistory.getInvoice().getOwnerUserId().getFullName();
					} else if (status.equalsIgnoreCase("Deleted")) {
						statusVal = "Deleted";
						statusBy = " by ";
						timelineDTO.setReason(timelineHistory.getRejectReason());
						List<User> usersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getOwnerUsers());
						 
						for(User ownerUser: usersList){
						if(ownerUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
							statusValue = ownerUser.getFullName();	
						}
						}
//						statusValue = timelineHistory.getInvoice().getOwnerUserId().getFullName();
					} else if (status.equalsIgnoreCase("Void")) {
						List<User> ownerUsersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getOwnerUsers());
						List<User> contactUsersList = userDao.getUsersListUsingMultipleStringIds(timelineHistory.getInvoice().getContactUsers());
						for(User ownerUser: ownerUsersList){
							if (timelineHistory.getCreatedBy().getId().equals(ownerUser.getId())) {
								statusVal = "Void";
								statusBy = " by ";
								if(ownerUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
									statusValue = ownerUser.getFullName();	
								}
//								statusValue = timelineHistory.getInvoice().getOwnerUserId().getFullName();
							}	
							 else if (!timelineHistory.getCreatedBy().getId()
										.equals(ownerUser.getId())) {
									statusVal = "Void";
									statusBy = " by ";
									 
									for(User contactUser: contactUsersList){
									if(contactUser.getUserCode().equals(timelineHistory.getInvoice().getUpdatedBy())){
										statusValue = contactUser.getFullName();	
									}
									}
//									statusValue = timelineHistory.getInvoice().getContactUser().getFullName();
								}
						}
						
					}
				}
			}	
			timelineDTO.setStatus(statusVal);
			timelineDTO.setStatusValue(statusValue);
			timelineDTO.setStatusBy(statusBy);
			timelineDTOs.add(timelineDTO);
		}
		
		if(namesStatus.length != 0 ) {
			if( namesStatus[0] != null && !namesStatus[0].equals("null")) {
				List<InvoiceTimelineDTO> filterList = new ArrayList<>();
				for(String name: namesStatus){
					List<InvoiceTimelineDTO> timelineList = timelineDTOs.stream()
						    .filter(p -> p.getStatusValue().equalsIgnoreCase(name)).collect(Collectors.toList());
					filterList.addAll(timelineList);
				}
				return filterList;
			}
		}
		return timelineDTOs;
	}
	
	@Override
	public String getInvoiceOrBillGeneratedId(OrgLocations loginOrgLoc, OrgLocations orgLoc, String invoiceType) {
		String companyShortCode = null;
    	Long number = 1L;
		String expId = null;
		String type = "";
		Integer generatedIdcount =0;
		if(invoiceType.equalsIgnoreCase(Constants.Payables)){
			type = Constants.BILL_ENTITY_SHORTCODE;
		}else{
			type = Constants.INVOICE_ENTITY_SHORTCODE;
		}
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(loginOrgLoc.getGeneratedUserId()).append(type);
		String generatedUsreId = sbuilder.toString();
		for(int i=0;i< generatedUsreId.length();i++){
			char ch = generatedUsreId.charAt(i);
			if(Character.isAlphabetic(ch)){
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount+1;
		Long checkNumber = invoiceDao.checkInvoiceOrBillIdNumberInDatebase(loginOrgLoc.getId(),generatedIdcount,invoiceType);
		if(checkNumber == null){
			expId = number.toString();
		}else{
		Long reqId = checkNumber+1;
		expId = reqId.toString();
		}
		//final DecimalFormat decimalFormat = new DecimalFormat("0"); /*OLD CODE*/  
		//final DecimalFormat decimalFormat = new DecimalFormat("000000");                
        //String convetredExpId = decimalFormat.format(Long.parseLong(expId));
		Calendar cal = Calendar.getInstance();
		int year = cal.getWeekYear();
        StringBuffer sb = new StringBuffer();
        if(loginOrgLoc.getGeneratedUserId() != null && !"".equalsIgnoreCase(loginOrgLoc.getGeneratedUserId())){
        	 companyShortCode = loginOrgLoc.getGeneratedUserId();
        }
		return sb.append(companyShortCode).append("-").append(type).append("-").append(orgLoc.getGeneratedUserId()).append("-").append(year).append("-").append(expId).toString();
	}
	
	private List<EmailAttachment> ReminderInvoiceAttachDocList(Invoice invoice) throws MessagingException{
		List<EmailAttachment> documentList = new ArrayList<EmailAttachment>();
		List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		EmailAttachment emailAttachment = new EmailAttachment();
		
		/*if (null != invoice.getInvoiceDocumentSet() && invoice.getInvoiceDocumentSet().size() > 0
				&& !invoice.getInvoiceDocumentSet().isEmpty()) {
			Set<InvoiceDocument> documenetSet = invoice.getInvoiceDocumentSet();
			for (InvoiceDocument doc : documenetSet) {
				EmailAttachment docAttachment = new EmailAttachment();
				String fileUrl = doc.getFileUrl();
				if (StringUtils.isNotBlank(fileUrl)) {
					docAttachment.setDocFile(CommonUtil.readBytesFromFile(fileUrl));
					docAttachment.setName(doc.getFileName());
					documentList.add(docAttachment);
				}

			}
			EmailAttachment attachmentZip = zipFileReminderInvoiceAttachedDoc(documentList);
			attachmentList.add(attachmentZip);
		}*/
		String invoiceFileUrl = invoice.getFileUrl();
		if (StringUtils.isNotBlank(invoiceFileUrl)) {
			emailAttachment.setFileUrl(invoiceFileUrl);
			emailAttachment.setName(invoice.getFileName());
			emailAttachment.setContent("application/pdf");
			attachmentList.add(emailAttachment);
		}
		return attachmentList;
	}

	@Override
	public CreditDebitNote saveCreditDebitNote(User user, OrgLocations orgLocation, String saveType, String noteType,
			CreditDebitNoteDTO creditDebitNoteDTO,String loginCurrencyCode) throws Exception {
		
		CreditDebitNote creditDebitNote = null;
		if(StringUtils.isNotBlank(creditDebitNoteDTO.getSavedNoteId())){
			creditDebitNote = invoiceDao.getCreditDebitNoteById(Long.parseLong(creditDebitNoteDTO.getSavedNoteId()));
		}else{
			creditDebitNote = new CreditDebitNote();
			creditDebitNote.setCreditDebitUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			creditDebitNote.setCreatedBy(user);
			creditDebitNote.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocation));
			creditDebitNote.setStatus(Constants.RECORD_ACTIVE.toString());
			creditDebitNote.setIsNewRecord(Constants.YES);
		}
		
		OrgLocations vendorOrgLocation = null;
		creditDebitNote.setOrgLocations(orgLocation);
		creditDebitNote.setInvoice(creditDebitNoteDTO.getInvoceId());
        if(Constants.CREDIT_NOTE.equalsIgnoreCase(noteType)){
        	// client details
        	if(StringUtils.isNotBlank(creditDebitNoteDTO.getOrgRelationId())){
     			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(creditDebitNoteDTO.getOrgRelationId()));
     			if(orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
     				vendorOrgLocation = orgRelation.getLocationId();
     				creditDebitNote.setResponderLocation(vendorOrgLocation);
      			}if(orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				vendorOrgLocation = orgRelation.getResponderLocationId();
      				creditDebitNote.setResponderLocation(orgRelation.getResponderLocationId());
      			}
      			creditDebitNote.setOrgRelations(orgRelation);	
     		}
     		
     		if(StringUtils.isNotBlank(creditDebitNoteDTO.getSpocUser())){
     			//User userContactName = userService.getUserById(Long.parseLong(creditDebitNoteDTO.getSpocUser()));
     			creditDebitNote.setContactUser(creditDebitNoteDTO.getSpocUser());
     		}
     		creditDebitNote.setNoteType(Constants.CREDIT_NOTE);
        }else{
        	//vendor details
        	if(StringUtils.isNotBlank(creditDebitNoteDTO.getOrgRelationId())){
      			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(creditDebitNoteDTO.getOrgRelationId()));
      			if(orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				vendorOrgLocation = orgRelation.getLocationId();
      				creditDebitNote.setResponderLocation(vendorOrgLocation);
      			}if(orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				vendorOrgLocation = orgRelation.getResponderLocationId();
      				creditDebitNote.setResponderLocation(orgRelation.getResponderLocationId());
      			}
      			creditDebitNote.setOrgRelations(orgRelation);	
      		}
        	creditDebitNote.setNoteStatus(saveType);
      		if(StringUtils.isNotBlank(creditDebitNoteDTO.getSpocUser())){
      			//User contactUser = userService.getUserById(Long.parseLong(creditDebitNoteDTO.getSpocUser()));
      			creditDebitNote.setContactUser(creditDebitNoteDTO.getSpocUser());
      		}
      		creditDebitNote.setNoteType(Constants.DEBIT_NOTE);
        }
        creditDebitNote.setOwnerUser(user.getId().toString());
        creditDebitNote.setNoteStatus(saveType);
        creditDebitNote.setSubTotal(new BigDecimal(creditDebitNoteDTO.getSubTotalAmount()));
        creditDebitNote.setTotal(new BigDecimal(creditDebitNoteDTO.getGrandTotal()));
        
        String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if(!loginCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)){
	        if(null != vendorCurrencyCode){
	      	float convertedAmount = conversionUtil.convert(loginCurrencyCode, vendorCurrencyCode, Float.parseFloat(creditDebitNoteDTO.getGrandTotal()));
	      	float convertedAmount1 = 	(float) (Math.round(convertedAmount * 100.0) / 100.0);
	      	creditDebitNote.setExchangeCurrencyRate(new BigDecimal(convertedAmount1));
	      	creditDebitNote.setVendorCurrencyCode(vendorCurrencyCode);
	      	creditDebitNote.setExchangeCurrencyRate(new BigDecimal(creditDebitNoteDTO.getExchangeCurrencyRate()));
	      	creditDebitNote.setAfterExchangeGrandTotal(new BigDecimal(creditDebitNoteDTO.getAfterExchangeGrandTotal()));
	        }	
	    }
        
        if(StringUtils.isEmpty(creditDebitNoteDTO.getSavedNoteId())){
        creditDebitNote.setGeneratedId(getCreditDebitGeneratedId(orgLocation,vendorOrgLocation,noteType));
        }
        creditDebitNote.setLoginCurrencyCode(loginCurrencyCode);
        
        if(StringUtils.isNotBlank(creditDebitNoteDTO.getDiscountValue())){
        	creditDebitNote.setDiscountType(creditDebitNoteDTO.getDiscountType());
            creditDebitNote.setDiscountValue(new BigDecimal(creditDebitNoteDTO.getDiscountValue()));
            creditDebitNote.setDiscountTotal(new BigDecimal(creditDebitNoteDTO.getDiscountTotal()));
        }
		if(StringUtils.isNotBlank(creditDebitNoteDTO.getBillingAddress())){
			creditDebitNote.setBillingAddress(creditDebitNoteDTO.getBillingAddress());
		}else{
			creditDebitNote.setBillingAddress(null);
		}
		if(StringUtils.isNotBlank(creditDebitNoteDTO.getShippingAddress())){
			creditDebitNote.setShippingAddress(creditDebitNoteDTO.getShippingAddress());
		}else{
			creditDebitNote.setShippingAddress(null);
		}
		if(StringUtils.isNotBlank(creditDebitNoteDTO.getReferenceNumber())){
			creditDebitNote.setReferenceNumber(creditDebitNoteDTO.getReferenceNumber());
		}else{
			creditDebitNote.setReferenceNumber(null);
		}
		if(StringUtils.isNotBlank(creditDebitNoteDTO.getReason())){
			creditDebitNote.setReason(creditDebitNoteDTO.getReason());
		}else{
			creditDebitNote.setReason(null);
		}
		if(StringUtils.isNotBlank(creditDebitNoteDTO.getAccount())){
			PaymentAccount paymentaccount = new PaymentAccount();
			paymentaccount.setId(Long.valueOf(creditDebitNoteDTO.getAccount()));
			creditDebitNote.setPaymentAccount(paymentaccount);
		}
		if(StringUtils.isNotBlank(creditDebitNoteDTO.getCreditDebitNotedate())){
			creditDebitNote.setCreditNoteDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), creditDebitNoteDTO.getCreditDebitNotedate()));
		}else{
			creditDebitNote.setCreditNoteDate(null);
		}
		if(StringUtils.isNotBlank(creditDebitNoteDTO.getApproverCheckBox()) && "Y".equalsIgnoreCase(creditDebitNoteDTO.getApproverCheckBox())){
			creditDebitNote.setApproverCheckBox(Constants.YES);
			creditDebitNote.setApprovers(creditDebitNoteDTO.getApprovers());
		}else{
			creditDebitNote.setApproverCheckBox(Constants.NO);
			creditDebitNote.setApprovers(null);
		}
		creditDebitNote.setUpdatedBy(user);
		creditDebitNote.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocation));
		
		// storing Credit or Debit line items
		List<CreditDebitLineItems> creditDebitLineItemsIdsList = null;
		if (null != creditDebitNote.getId()) {
			creditDebitLineItemsIdsList = invoiceDao.getCreditDebitLineItemsListByNoteIdAndLocationId(creditDebitNote.getId(), orgLocation.getId());
		}
		Set<CreditDebitLineItems> invoiceLineItemSet = getCreditDebitLineItems(user, orgLocation, creditDebitNote,creditDebitNoteDTO,creditDebitLineItemsIdsList);
		creditDebitNote.setCreditDebitLineItems(invoiceLineItemSet);

		invoiceDao.saveCreditDebitNote(creditDebitNote);
		// save child record
		if(saveType.equalsIgnoreCase("Send")){
			saveChildCreditDebitNote(creditDebitNote,user,vendorOrgLocation,loginCurrencyCode);
		}
		return creditDebitNote;
	}

	private CreditDebitNote saveChildCreditDebitNote(CreditDebitNote parentCreditDebitNote, User user, OrgLocations vendorOrgLocation,String vendorOrgCurrencyCode) throws Exception {
		
		CreditDebitNote childCreditDebitNote = invoiceDao.getParentCreditDebitNoteById(parentCreditDebitNote.getId());
		
		if(childCreditDebitNote == null){
			childCreditDebitNote = new CreditDebitNote();
			childCreditDebitNote.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), vendorOrgLocation));
			childCreditDebitNote.setCreatedBy(user);
		}
		if(parentCreditDebitNote.getNoteType().equalsIgnoreCase(Constants.CREDIT_NOTE)){
			childCreditDebitNote.setNoteType(Constants.DEBIT_NOTE);
		}else{
			childCreditDebitNote.setNoteType(Constants.CREDIT_NOTE);
		}
		childCreditDebitNote.setOrgRelations(parentCreditDebitNote.getOrgRelations());
		childCreditDebitNote.setOrgLocations(parentCreditDebitNote.getResponderLocation());
		childCreditDebitNote.setResponderLocation(parentCreditDebitNote.getOrgLocations());
		childCreditDebitNote.setOwnerUser(parentCreditDebitNote.getContactUser());
		childCreditDebitNote.setNoteStatus(Constants.INVOICE_RECEIVED);
		childCreditDebitNote.setCreditNoteDate(parentCreditDebitNote.getCreditNoteDate());
		childCreditDebitNote.setStatus(parentCreditDebitNote.getStatus());
		childCreditDebitNote.setContactUser(parentCreditDebitNote.getOwnerUser());
		childCreditDebitNote.setParentCreditDebitNote(parentCreditDebitNote);
		childCreditDebitNote.setReferenceNumber(parentCreditDebitNote.getReferenceNumber());
		childCreditDebitNote.setReason(parentCreditDebitNote.getReason());
		childCreditDebitNote.setBillingAddress(parentCreditDebitNote.getBillingAddress());
		childCreditDebitNote.setShippingAddress(parentCreditDebitNote.getShippingAddress());
		List<Invoice> childInvoiceList = null;
		StringBuilder sbChild = new StringBuilder();
		// get child record using parentIds
		 childInvoiceList = invoiceDao.getInvoiceListUsingMultipleIdsForChild(parentCreditDebitNote.getInvoice());
		 if(childInvoiceList.size()>0){
		childInvoiceList.forEach(childInvoice1->{
			sbChild.append(childInvoice1.getId()).append(",").toString();	
		});
		 }else{
			// get parent record using childIds
			 childInvoiceList = invoiceDao.getInvoiceListUsingMultipleIds(parentCreditDebitNote.getInvoice()); 
			 childInvoiceList.forEach(childInvoice1->{
					sbChild.append(childInvoice1.getParentInvoiceId().getId()).append(",").toString();	
				});
		 }
		 String invoiceIds = sbChild.deleteCharAt(sbChild.length() - 1).toString();
		childCreditDebitNote.setInvoice(invoiceIds);
		childCreditDebitNote.setGeneratedId(parentCreditDebitNote.getGeneratedId());
		childCreditDebitNote.setCreditDebitUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		childCreditDebitNote.setUpdatedBy(user);
		childCreditDebitNote.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), vendorOrgLocation));
		childCreditDebitNote.setSubTotal(parentCreditDebitNote.getSubTotal());
		String loginCurrencyCode = conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
		if (!vendorOrgCurrencyCode.equalsIgnoreCase(loginCurrencyCode)) {
			if (null != loginCurrencyCode) {
				float convertedAmount = conversionUtil.convert(vendorOrgCurrencyCode, loginCurrencyCode,parentCreditDebitNote.getTotal());
				float convertedAmount1 = (float) (Math.round(convertedAmount * 100.0) / 100.0);
				childCreditDebitNote.setExchangeCurrencyRate(new BigDecimal(convertedAmount1));
				childCreditDebitNote.setVendorCurrencyCode(parentCreditDebitNote.getLoginCurrencyCode());
				childCreditDebitNote.setTotal(parentCreditDebitNote.getAfterExchangeGrandTotal());
			}
		} else {
			childCreditDebitNote.setTotal(parentCreditDebitNote.getTotal());
		}
		childCreditDebitNote.setLoginCurrencyCode(loginCurrencyCode);
		childCreditDebitNote.setDiscountType(parentCreditDebitNote.getDiscountType());
		childCreditDebitNote.setDiscountValue(parentCreditDebitNote.getDiscountValue());
		childCreditDebitNote.setDiscountTotal(parentCreditDebitNote.getDiscountTotal());
		
		invoiceDao.saveCreditDebitNote(childCreditDebitNote);
		
		return childCreditDebitNote;
		
	}

	private Set<CreditDebitLineItems> getCreditDebitLineItems(User user, OrgLocations orgLocation,
			CreditDebitNote creditDebitNote, CreditDebitNoteDTO creditDebitNoteDTO,List<CreditDebitLineItems> creditDebitLineItemsSavedList) {
		
		Set<CreditDebitLineItems> creditDebitLineItemsList = new HashSet<CreditDebitLineItems>();
		
		List<CreditDebitLineItems> updatedLineItems = new ArrayList<CreditDebitLineItems>();
		
		for(CreditDebitNoteLineItemsDTO creditDebitLineItemsDTO :creditDebitNoteDTO.getCreditDebitNoteLineItems())
		{
			if (creditDebitLineItemsDTO.getPrice() == null) {
                continue;
            }
			CreditDebitLineItems creditDebitLineItems = new CreditDebitLineItems();
			
			if(StringUtils.isNotBlank(creditDebitLineItemsDTO.getLineItemId())){
				creditDebitLineItems.setId(Long.parseLong(creditDebitLineItemsDTO.getLineItemId()));
			}
			
			if(StringUtils.isNotBlank(creditDebitLineItemsDTO.getInlineDescription())){
			creditDebitLineItems.setInlineDescription(creditDebitLineItemsDTO.getInlineDescription());
			}else{
				creditDebitLineItems.setInlineDescription(null);	
			}
			if(StringUtils.isNotBlank(creditDebitLineItemsDTO.getLineItemDate())){
			creditDebitLineItems.setLineItemDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), creditDebitLineItemsDTO.getLineItemDate()));
			}else{
			creditDebitLineItems.setLineItemDate(null);	
			}
			creditDebitLineItems.setPrice(new BigDecimal(creditDebitLineItemsDTO.getPrice()));
			creditDebitLineItems.setOrgLocation(orgLocation);
			creditDebitLineItems.setAmount(new BigDecimal(creditDebitLineItemsDTO.getRowTotal()));
			creditDebitLineItems.setTotalAmount(new BigDecimal(creditDebitLineItemsDTO.getRowTotalAmount()));
			creditDebitLineItems.setQuantity(creditDebitLineItemsDTO.getQuantity());
			if(StringUtils.isNotBlank(creditDebitLineItemsDTO.getTaxType())){
			creditDebitLineItems.setTaxType(creditDebitLineItemsDTO.getTaxType());
			}else{
			creditDebitLineItems.setTaxType(null);	
			}
			if(StringUtils.isNotBlank(creditDebitLineItemsDTO.getTaxAmount())){
			creditDebitLineItems.setTaxAmount(new BigDecimal(creditDebitLineItemsDTO.getTaxAmount()));
			}else{
			creditDebitLineItems.setTaxAmount(null);	
			}
			if(StringUtils.isNotBlank(creditDebitLineItemsDTO.getAssociates())){
				try {
					User associate = userService.getUserById(Long.parseLong(creditDebitLineItemsDTO.getAssociates()));
					creditDebitLineItems.setAssociate(associate);	
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			creditDebitLineItems.setStatus(Constants.RECORD_ACTIVE.toString());
			creditDebitLineItems.setCreatedBy(user);
			creditDebitLineItems.setUpdatedBy(user);
			creditDebitLineItems.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocation));
			creditDebitLineItems.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocation));
			creditDebitLineItems.setCreditDebitNote(creditDebitNote);
			creditDebitLineItemsList.add(creditDebitLineItems);
		}
		if (null != creditDebitNote.getId()) {
			updatedLineItems.retainAll(creditDebitLineItemsSavedList);
			creditDebitLineItemsSavedList.removeAll(updatedLineItems);
		}
		return creditDebitLineItemsList;
	}

	@Override
	public ResponseList<CreditDebitNoteListDTO> creditDebtListByOrgLocationId(OrgLocations orgLocations,
			Date startDateUTC, Date endDateUTC, CreditDebitNoteDTO creditDebitNoteDTO) throws Exception {
		
		List<CreditDebitNoteListDTO> creditDebitList = new ArrayList<>();
		ResponseList<CreditDebitNoteListDTO> creditDebitDTOList = new ResponseList<CreditDebitNoteListDTO>();
		
		ResponseList<CreditDebitNote> creditDebitNoteList = invoiceDao.creditDebtListByOrgLocationId(orgLocations, startDateUTC, endDateUTC, creditDebitNoteDTO);
		List<CreditDebitNote> notesFilterList = creditDebitNoteList.getData();
			notesFilterList.forEach(creditDebitNote->{
			CreditDebitNoteListDTO creditDebitNoteListDTO = new CreditDebitNoteListDTO();
			creditDebitNoteListDTO.setReferenceNumber(creditDebitNote.getReferenceNumber());
			creditDebitNoteListDTO.setGrandTotal(creditDebitNote.getTotal().toString());
			OrgRelations orgRelations = null;
			try {
				orgRelations = orgRelationsService.getOrgRelationById(creditDebitNote.getOrgRelations().getId());
				if (orgRelations.getLocationId().getId().equals(orgLocations.getId())) {
					creditDebitNoteListDTO.setClientName(orgRelations.getResponderLocationId().getOrgLocationName());
				}
				if (orgRelations.getResponderLocationId().getId().equals(orgLocations.getId())) {
					creditDebitNoteListDTO.setClientName(orgRelations.getLocationId().getOrgLocationName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			creditDebitNoteListDTO.setGeneratedId(creditDebitNote.getGeneratedId());
			// get Invoice List
			List<Invoice> listInvoice = null;
			StringBuilder sbInvoice = new StringBuilder();
			try {
				listInvoice = invoiceDao.getInvoiceListUsingMultipleIds(creditDebitNote.getInvoice());
				listInvoice.forEach(invoice->{
					sbInvoice.append(invoice.getInvoiceId()).append(" , ").toString();
				});
				if(listInvoice !=null){
				creditDebitNoteListDTO.setInvoceId(sbInvoice.deleteCharAt(sbInvoice.length() - 2).toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			creditDebitNoteListDTO.setCreditDebitNotedate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), creditDebitNote.getCreditNoteDate()));
			creditDebitNoteListDTO.setCreditDebitNoteStatus(creditDebitNote.getNoteStatus());
			creditDebitNoteListDTO.setCreditDebitUUID(creditDebitNote.getCreditDebitUUID());
			creditDebitNoteListDTO.setSavedNoteId(creditDebitNote.getId().toString());
			creditDebitNoteListDTO.setContactUser(creditDebitNote.getContactUser());
			creditDebitNoteListDTO.setApproverCheckBox(creditDebitNote.getApproverCheckBox());
			creditDebitNoteListDTO.setApprovers(creditDebitNote.getApprovers());
			creditDebitNoteListDTO.setStatus(creditDebitNote.getStatus());
			if(creditDebitNote.getRejectReason() !=null){
				creditDebitNoteListDTO.setRejectReason(creditDebitNote.getRejectReason());	
			}
			if(StringUtils.isNotBlank(creditDebitNote.getApprovers())){
			List<String> appUserList = Arrays.asList(creditDebitNoteListDTO.getApprovers().split(","));
				creditDebitNoteListDTO.setApproverUserSplitId(appUserList);
				System.out.println(appUserList.size());
			}
			creditDebitNoteListDTO.setOwnerUser(creditDebitNote.getOwnerUser());
			creditDebitList.add(creditDebitNoteListDTO);	
		});
		
		creditDebitDTOList.setData(creditDebitList);
		creditDebitDTOList.setRecordsFiltered(creditDebitNoteList.getRecordsFiltered());
		creditDebitDTOList.setRecordsTotal(creditDebitNoteList.getRecordsTotal());
		return creditDebitDTOList;
	}

	@Override
	public boolean deleteCreditDebitNote(String noteUUID,String noteType,User user,OrgLocations orgLocations)throws Exception {
		boolean result = false;
		CreditDebitNote note = invoiceDao.getCreditDebitNoteByUUID(noteUUID);
		Date date = new Date();
		if(note!= null){
				note.setStatus(Constants.MEMBER_IN_ACTIVE);
				note.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
				note.setUpdatedBy(user);
				CreditDebitNote savedNote = invoiceDao.saveCreditDebitNote(note);
				if ((null != savedNote) && ((savedNote.getNoteStatus().equalsIgnoreCase("Received")) || (savedNote.getNoteStatus().equalsIgnoreCase("Send")))) {
					if (null != note.getParentCreditDebitNote()) {
						// parent invoice update
						CreditDebitNote parentnote = invoiceDao.getCreditDebitNoteById(savedNote.getParentCreditDebitNote().getId());
						parentnote.setStatus(Constants.MEMBER_IN_ACTIVE);
						parentnote.setUpdatedBy(user);
						parentnote.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
						invoiceDao.saveCreditDebitNote(parentnote);
					}else{
						// update child invoice
						CreditDebitNote childInvoice = invoiceDao.getParentCreditDebitNoteById(savedNote.getId());
						childInvoice.setStatus(Constants.MEMBER_IN_ACTIVE);
						childInvoice.setUpdatedBy(user);
						childInvoice.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
						invoiceDao.saveCreditDebitNote(childInvoice);
					}
				}
				result = true;	
		}
		return result;
	}
	
	@Override
	public boolean reactiveCreditDebitNote(String noteUUID,String noteType,User user,OrgLocations orgLocations)throws Exception {
		boolean result = false;
		CreditDebitNote note = invoiceDao.getCreditDebitNoteByUUID(noteUUID);
		Date date = new Date();
		if(note!= null){
				note.setStatus(Constants.MEMBER_ACTIVE);
				note.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
				note.setUpdatedBy(user);
				CreditDebitNote savedNote = invoiceDao.saveCreditDebitNote(note);
				if ((null != savedNote) && ((savedNote.getNoteStatus().equalsIgnoreCase("Received")) || (savedNote.getNoteStatus().equalsIgnoreCase("Send")))) {
					if (null != note.getParentCreditDebitNote()) {
						// parent invoice update
						CreditDebitNote parentnote = invoiceDao.getCreditDebitNoteById(savedNote.getParentCreditDebitNote().getId());
						parentnote.setStatus(Constants.MEMBER_ACTIVE);
						parentnote.setUpdatedBy(user);
						parentnote.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
						invoiceDao.saveCreditDebitNote(parentnote);
					}else{
						// update child invoice
						CreditDebitNote childInvoice = invoiceDao.getParentCreditDebitNoteById(savedNote.getId());
						childInvoice.setStatus(Constants.MEMBER_ACTIVE);
						childInvoice.setUpdatedBy(user);
						childInvoice.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
						invoiceDao.saveCreditDebitNote(childInvoice);
					}
				}
				result = true;	
		}
		return result;
	}
	
	@Override
	public CreditDebitNote getCreditDebitNoteByUUID(String noteUUID) throws Exception {
		return invoiceDao.getCreditDebitNoteByUUID(noteUUID);
	}
	
	@Override
	public Response<CreditDebitNote> getCreditDebitNoteByIdAndOrglocationId(Long id, Long orgLocationId)
			throws Exception {
		Response<CreditDebitNote> response = new Response<CreditDebitNote>();
		
		Long newLocationId = null;
		Long newId = null;
		CreditDebitNote parentNote = invoiceDao.getCreditDebitNoteById(id);
		if (parentNote.getParentCreditDebitNote() != null) {
			newId = parentNote.getParentCreditDebitNote().getId();
			newLocationId = parentNote.getParentCreditDebitNote().getOrgLocations().getId();
		}
		else {
			newId = id;
			newLocationId = orgLocationId;
		}
		
		CreditDebitNote creditDebitNote = invoiceDao.getCreditDebitNoteByIdAndOrglocationId(newId, newLocationId);
		// get CreditDebit Line Items 
		List<CreditDebitLineItems> lineItemsList = invoiceDao.getCreditDebitLineItemsListByNoteIdAndLocationId(newId, newLocationId);
		lineItemsList.forEach(lineItem->{
			lineItem.setLineItemDateStr(commonUtil.dateStrFromOrgDateFormatter(lineItem.getOrgLocation().getDateFormat(), lineItem.getLineItemDate()));	
			if(lineItem.getAssociate() !=null){
			lineItem.setAssociateName(lineItem.getAssociate().getFullName());
			}
		});
		creditDebitNote.setListOfCreditDebitLineItems(lineItemsList);
		// get Invoice List
		List<Invoice> listInvoice = invoiceDao.getInvoiceListUsingMultipleStringIdsAndLocationId(creditDebitNote.getInvoice(),creditDebitNote.getOrgLocations().getId());
		StringBuilder sbInvoice = new StringBuilder();
		listInvoice.forEach(invoice->{
			sbInvoice.append(invoice.getInvoiceId()).append(" , ").toString();
		});
		creditDebitNote.setInvoiceMultipleIds(sbInvoice.deleteCharAt(sbInvoice.length() - 2).toString());
		// get users list 
		StringBuilder appSb = new StringBuilder();
		if(StringUtils.isNotBlank(creditDebitNote.getApprovers())){
		List<User> approversList = userDao.getUsersListUsingMultipleStringIdsWithLocationId(creditDebitNote.getApprovers(),creditDebitNote.getOrgLocations().getId());
		approversList.forEach(approver->{
			appSb.append(approver.getFullName()).append(" , ").toString();	
		});
		creditDebitNote.setApproversMultipleNames(appSb.deleteCharAt(appSb.length() - 2).toString());
		}
		creditDebitNote.setCreditDebitNoteDateStr(commonUtil.dateStrFromOrgDateFormatter(creditDebitNote.getOrgLocations().getDateFormat(), creditDebitNote.getCreditNoteDate()));
		StringBuilder contactName = new StringBuilder();
		StringBuilder contactEmails = new StringBuilder();
		if (creditDebitNote.getOrgRelations().getLocationId().getId().equals(orgLocationId)) {
			creditDebitNote.setClientOrVendorName(creditDebitNote.getOrgRelations().getResponderLocationId().getOrgLocationName());
			List<User> contactsUserList = userDao.getUsersListUsingMultipleStringIds(creditDebitNote.getContactUser());
			contactsUserList.forEach(contactUser->{
				contactName.append(contactUser.getFullName()).append(" , ").toString();	
				contactEmails.append(contactUser.getEmail()).append(" , ").toString();
			});
			creditDebitNote.setContactsMultipleNames(contactName.deleteCharAt(contactName.length() - 2).toString());
			creditDebitNote.setContactsMultipleEmails(contactEmails.deleteCharAt(contactEmails.length() - 2).toString());
		}
		
		if(creditDebitNote.getOrgRelations().getResponderLocationId().getId().equals(orgLocationId)){
			creditDebitNote.setClientOrVendorName(creditDebitNote.getOrgRelations().getLocationId().getOrgLocationName());
			List<User> contactsUserList = userDao.getUsersListUsingMultipleStringIds(creditDebitNote.getOwnerUser());
			contactsUserList.forEach(contactUser->{
				contactName.append(contactUser.getFullName()).append(" , ").toString();	
				contactEmails.append(contactUser.getEmail()).append(" , ").toString();
			});
			creditDebitNote.setContactsMultipleNames(contactName.deleteCharAt(contactName.length() - 2).toString());
			creditDebitNote.setContactsMultipleEmails(contactEmails.deleteCharAt(contactEmails.length() - 2).toString());
		}
		
		response.setObject(creditDebitNote);
		
		return response;
	}
	/*public EmailAttachment zipFileReminderInvoiceAttachedDoc(List<EmailAttachment> docusList) throws MessagingException {

		EmailAttachment ema = null;
		FileOutputStream fos = null;
		ZipOutputStream zipOut = null;
		FileInputStream fis = null;
		try {
			ByteArrayOutputStream zipBytes = new ByteArrayOutputStream();
			if (docusList != null && !docusList.isEmpty()) {
				ema = new EmailAttachment();
				ZipOutputStream outZip = new ZipOutputStream(zipBytes);

				int i = 0;
				for (EmailAttachment filePath : docusList) {
					StringBuilder sb = new StringBuilder();
					String st = filePath.getName();
					String lastIndex = st.substring(0, st.lastIndexOf("."));
					String newFilename = (sb.append(lastIndex).append("-").append(i)
							.append(st.substring(st.lastIndexOf("."))).toString());
					addToZipFile(filePath.getDocFile(), outZip, newFilename);
					i++;
				}

				outZip.closeEntry();
				outZip.close();
				DataSource source = new ByteArrayDataSource(zipBytes.toByteArray(), "application/zip");
				MimeBodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setDataHandler(new DataHandler(source));
				DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
				StringBuilder fName = new StringBuilder();
				String fileName = fName.append("resumes")
						.append(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()).append(" ")
						.append(dateFormat.format(new Date())).append(".zip").toString();
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
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {

			}
		}
		return ema;
	}*/

	@Override
	public CreditDebitNote saveAcceptedCreditDebitNote(User user, OrgLocations orgLocation, String noteType,
			CreditDebitNote creditDebitNote) throws Exception {
		
		creditDebitNote.setNoteStatus("Send");
		creditDebitNote.setUpdatedBy(user);
		creditDebitNote.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(),orgLocation));
		CreditDebitNote creditDebitNoteSave = invoiceDao.saveCreditDebitNote(creditDebitNote);
		
		OrgLocations vendorOrgLocation = null;
		if(Constants.CREDIT_NOTE.equalsIgnoreCase(noteType)){
        	// client details
     			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(creditDebitNoteSave.getOrgRelations().getId());
     			if(orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
     				vendorOrgLocation = orgRelation.getLocationId();
     			}if(orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				vendorOrgLocation = orgRelation.getResponderLocationId();
			}
     		}else{
        	//vendor details
      			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(creditDebitNoteSave.getOrgRelations().getId());
      			if(orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				vendorOrgLocation = orgRelation.getLocationId();
      			}
      			if(orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
      				vendorOrgLocation = orgRelation.getResponderLocationId();
      			}
        }
        String currencyCode =  conversionUtil.getVendorOrgCurrencyCode(orgLocation.getCountry());

		// save child record
				if(creditDebitNote.getNoteStatus().equalsIgnoreCase("Send")){
					saveChildCreditDebitNote(creditDebitNote,user,vendorOrgLocation,currencyCode);
				}
		
		return creditDebitNote;

	
		}
	
	public String getCreditDebitGeneratedId(OrgLocations loginOrgLoc,OrgLocations vendorOrgLocation,String noteType) {
		String type = "";
		if(noteType.equalsIgnoreCase(Constants.CREDIT_NOTE)){
			type = Constants.CREDIT_ENTITY_SHORTCODE;
		}else{
			type = Constants.DEBIT_ENTITY_SHORTCODE;
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.getWeekYear();
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(loginOrgLoc.getGeneratedUserId()).append("-").append(type).append("-").append(vendorOrgLocation.getGeneratedUserId()).append("-").append(year).append("-");
		String generatedUsreId = sbuilder.toString();
		return generatedUsreId;
	}

	@Override
	public CreditDebitNoteListDTO editCreditDebitNote(Long noteId, OrgLocations orgLocation, String noteType)throws Exception {
		CreditDebitNoteListDTO creditDebitNoteListDTO = new CreditDebitNoteListDTO();
		CreditDebitNote note = invoiceDao.getCreditDebitNoteByIdAndOrglocationId(noteId, orgLocation.getId());
		
		creditDebitNoteListDTO.setReferenceNumber(note.getReferenceNumber());
		creditDebitNoteListDTO.setBillingAddress(note.getBillingAddress());
		creditDebitNoteListDTO.setShippingAddress(note.getShippingAddress());
		creditDebitNoteListDTO.setCreditDebitNotedate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), note.getCreditNoteDate()));
		creditDebitNoteListDTO.setReason(note.getReason());
		creditDebitNoteListDTO.setApproverCheckBox(note.getApproverCheckBox());
		creditDebitNoteListDTO.setApprovers(note.getApprovers());
		creditDebitNoteListDTO.setInvoceId(note.getInvoice());
		creditDebitNoteListDTO.setContactUser(note.getContactUser());
		creditDebitNoteListDTO.setOrgRelationId(note.getOrgRelations().getId().toString());
		creditDebitNoteListDTO.setGrandTotal(note.getTotal().toString());
		creditDebitNoteListDTO.setSubTotalAmount(note.getSubTotal().toString());
		if(note.getPaymentAccount() !=null){
		creditDebitNoteListDTO.setAccount(note.getPaymentAccount().getId().toString());
		}
		if(note.getDiscountValue() !=null){
		creditDebitNoteListDTO.setDiscountValue(note.getDiscountValue().toString());
		creditDebitNoteListDTO.setDiscountTotal(note.getDiscountTotal().toString());
		creditDebitNoteListDTO.setDiscountType(note.getDiscountType());
		}
		creditDebitNoteListDTO.setSavedNoteId(note.getId().toString());
		creditDebitNoteListDTO.setLoginCurrencyCode(note.getLoginCurrencyCode());
		if(StringUtils.isNotBlank(note.getVendorCurrencyCode())){
			creditDebitNoteListDTO.setAfterExchangeGrandTotal(note.getAfterExchangeGrandTotal().toString());	
			creditDebitNoteListDTO.setExchangeCurrencyRate(note.getExchangeCurrencyRate().toString());
			creditDebitNoteListDTO.setVendorCurrencyCode(note.getVendorCurrencyCode());
			//creditDebitNoteListDTO.setConversionFrom(note.);
		}
		creditDebitNoteListDTO.setLoginCurrencyCode(note.getLoginCurrencyCode());
		//Credit Debit Note Line Items
		List<CreditDebitLineItems> lineItemsList = invoiceDao.getCreditDebitLineItemsListByNoteIdAndLocationId(noteId, orgLocation.getId());
		List<CreditDebitNoteLineItemsDTO> lineItemsDTOList = new ArrayList<CreditDebitNoteLineItemsDTO>();
		lineItemsList.forEach(lineItems->{
		CreditDebitNoteLineItemsDTO	 listDto = new CreditDebitNoteLineItemsDTO();
		listDto.setInlineDescription(lineItems.getInlineDescription());
		listDto.setPrice(lineItems.getPrice().toString());
		listDto.setQuantity(lineItems.getQuantity());
		listDto.setLineItemDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), lineItems.getLineItemDate()));
		if(lineItems.getAssociate() !=null){
			listDto.setAssociates(lineItems.getAssociate().getId().toString());	
		}
		if(lineItems.getTaxAmount() !=null){
		listDto.setTaxAmount(lineItems.getTaxAmount().toString());
		listDto.setTaxType(lineItems.getTaxType());
		}
		listDto.setRowTotalAmount(lineItems.getTotalAmount().toString());
		listDto.setRowTotal(lineItems.getAmount().toString());
		listDto.setLineItemId(lineItems.getId().toString());
			
		lineItemsDTOList.add(listDto);	 	
		});
		creditDebitNoteListDTO.setCreditDebitNoteLineItems(lineItemsDTOList);
		
		return creditDebitNoteListDTO;
	}

	@Override
	public CreditDebitLineItems getCreditDebitLineItemById(Long id) {
		return invoiceDao.getCreditDebitLineItemById(id);
	}

	@Override
	public void deleteCreditDebitLineItem(CreditDebitLineItems creditDebitLineItems) {
		invoiceDao.deleteCreditDebitLineItem(creditDebitLineItems);
	}

	@Override
	public Response<String> saveCustomInvoiceTemplate(CustomInvoiceTemplateDTO customInvoiceTemplateDTO, OrgRelations orgRelations, OrgLocations orgLocations, User loggedInUser, OrgLocations loggedInOrgLocations) {
		Response<String> response = new Response<>();
		try {
			CustomInvoiceTemplate customInvoiceTemplate = null;
			CustomInvoiceTemplate savedTemplate = invoiceDao.getCustomInvoiceTemplateByNameAndOrgRelationIdAndOrgLocationId(customInvoiceTemplateDTO.getName(), orgRelations.getId(), orgLocations.getId());
			if(savedTemplate != null && customInvoiceTemplateDTO.getId() != savedTemplate.getId()) {
				response.setResponse(Result.DUPLICATE_NAME.name());
				response.setResponseMessage(Result.DUPLICATE_NAME.getValue());
				return response;
			}
			if (customInvoiceTemplateDTO.getId() > 0) {
				customInvoiceTemplate = invoiceDao.getCustomInvoiceTemplateById(customInvoiceTemplateDTO.getId());
				customInvoiceTemplate.setUpdatedBy(loggedInUser);
				customInvoiceTemplate.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), loggedInOrgLocations));
			} else {
				customInvoiceTemplate = new CustomInvoiceTemplate();
				customInvoiceTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				customInvoiceTemplate.setCreatedBy(loggedInUser);
				customInvoiceTemplate.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), loggedInOrgLocations));
			}
			customInvoiceTemplate.setName(customInvoiceTemplateDTO.getName());
			customInvoiceTemplate.setDescription(customInvoiceTemplateDTO.getDescription());
			customInvoiceTemplate.setItems(customInvoiceTemplateDTO.getJsonData());
			customInvoiceTemplate.setStatus(1);
			customInvoiceTemplate.setOrgRelations(orgRelations);
			customInvoiceTemplate.setOrgLocations(orgLocations);
			
			long result = invoiceDao.saveCustomInvoiceTemplate(customInvoiceTemplate);
			if (result > 0) {
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.FAILED.name());
				response.setResponseMessage(Result.FAILED.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@Override
	public ResponseList<CustomInvoiceTemplateDTO> getCustomInvoiceTemplateListByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId, SearchDTO searchDTO, OrgLocations loggedInOrgLocations) {
		ResponseList<CustomInvoiceTemplateDTO> responseList = new ResponseList<>();
		try {
			List<CustomInvoiceTemplateDTO> customInvoiceTemplateDTOList = new LinkedList<>();
			ResponseList<CustomInvoiceTemplate> customInvoiceTemplateList = invoiceDao.getCustomInvoiceTemplateByOrgRelationIdAndOrgLocationId(orgRelationId, orgLocationId, searchDTO);
			if (customInvoiceTemplateList != null &&  customInvoiceTemplateList.getData() != null && !customInvoiceTemplateList.getData().isEmpty()) {
				for(CustomInvoiceTemplate customInvoiceTemplate : customInvoiceTemplateList.getData()) {
					customInvoiceTemplateDTOList.add(getCustomInvoiceTemplateDetails(customInvoiceTemplate, loggedInOrgLocations));
				}
			}
			responseList.setData(customInvoiceTemplateDTOList);
			responseList.setRecordsFiltered(customInvoiceTemplateList.getRecordsFiltered());
			responseList.setRecordsTotal(customInvoiceTemplateList.getRecordsTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	@Override
	public CustomInvoiceTemplateDTO getCustomInvoiceTemplateListByUUIDAndOrgRelationIdAndOrgLocationId(String uuid, Long orgRelationId, Long orgLocationId, OrgLocations loggedInOrgLocations) {
		try {
			CustomInvoiceTemplate customInvoiceTemplate = invoiceDao.getCustomInvoiceTemplateListByUUIDAndOrgRelationIdAndOrgLocationId(uuid, orgRelationId, orgLocationId);
			if(customInvoiceTemplate != null) {
				return getCustomInvoiceTemplateDetails(customInvoiceTemplate, loggedInOrgLocations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private CustomInvoiceTemplateDTO getCustomInvoiceTemplateDetails(CustomInvoiceTemplate customInvoiceTemplate, OrgLocations loggedInOrgLocations) {
		CustomInvoiceTemplateDTO customInvoiceTemplateDTO = new CustomInvoiceTemplateDTO();
		try {
			customInvoiceTemplateDTO.setId(customInvoiceTemplate.getId());
			customInvoiceTemplateDTO.setUuid(customInvoiceTemplate.getUuid());
			customInvoiceTemplateDTO.setName(customInvoiceTemplate.getName());
			customInvoiceTemplateDTO.setDescription(customInvoiceTemplate.getDescription());
			customInvoiceTemplateDTO.setStatus(customInvoiceTemplate.getStatus());
			customInvoiceTemplateDTO.setOrgLocationId(customInvoiceTemplate.getOrgLocations().getId());
			customInvoiceTemplateDTO.setOrgRelationId(customInvoiceTemplate.getOrgRelations().getId());
			if(customInvoiceTemplate.getCreatedBy() != null) {
				customInvoiceTemplateDTO.setCreatedByUserId(customInvoiceTemplate.getCreatedBy().getId() + "");
				customInvoiceTemplateDTO.setCreatedByUserName(customInvoiceTemplate.getCreatedBy().getFullName());
			}
			if(customInvoiceTemplate.getCreatedDate() != null) {
				customInvoiceTemplateDTO.setCreatedDate(customInvoiceTemplate.getCreatedDate().toString());
			}
			if(customInvoiceTemplate.getUpdatedBy() != null) {
				customInvoiceTemplateDTO.setUpdatedByUserId(customInvoiceTemplate.getUpdatedBy().getId() + "");
				customInvoiceTemplateDTO.setUpdatedByUserName(customInvoiceTemplate.getUpdatedBy().getFullName());
			}
			if(customInvoiceTemplate.getUpdatedDate() != null) {
				customInvoiceTemplateDTO.setUpdatedDate(customInvoiceTemplate.getUpdatedDate().toString());
			}
			
			JSONArray jsonArray = new JSONArray(customInvoiceTemplate.getItems());
			if (jsonArray != null && jsonArray.length() > 0) {
				List<JSONObject> itemsObjectList = new LinkedList<>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					itemsObjectList.add(jsonObject.getInt("order") - 1, jsonObject);
				}
				customInvoiceTemplateDTO.setItemObjectList(itemsObjectList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customInvoiceTemplateDTO;
	}

	@Override
	public boolean changeCustomInvoiceTemplateStatus(Long templateId, int status, User user, OrgLocations orgLocations) {
		try {
			CustomInvoiceTemplate customInvoiceTemplate = invoiceDao.getCustomInvoiceTemplateById(templateId);
			if(customInvoiceTemplate != null) {
				customInvoiceTemplate.setStatus(status);
				customInvoiceTemplate.setUpdatedBy(user);
				customInvoiceTemplate.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
			}
			long result = invoiceDao.saveCustomInvoiceTemplate(customInvoiceTemplate);;
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteCustomInvoiceTemplate(Long templateId) {
		try {
			CustomInvoiceTemplate customInvoiceTemplate = invoiceDao.getCustomInvoiceTemplateById(templateId);
			if(customInvoiceTemplate != null) {
				return invoiceDao.deleteCustomInvoiceTemplate(customInvoiceTemplate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
