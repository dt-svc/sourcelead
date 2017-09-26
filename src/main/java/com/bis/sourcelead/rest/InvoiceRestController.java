package com.bis.sourcelead.rest;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.beans.BillingReportDTO;
import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.InvoicePaymentDTO;

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
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.InvoiceLineItemService;
import com.bis.sourcelead.service.InvoicePaymentService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.InvoiceTaxService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.InvoiceDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
import com.bis.sourcelead.web.LoginSessionController;

@RestController
public class InvoiceRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceRestController.class);
	
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private TimeZoneService timeZoneService;

	@Autowired
	private InvoicePaymentService invoicePaymentService;
	
	@Autowired

	private InvoiceLineItemService invoiceLineItemService;
	
	@Autowired
	private InvoiceDocumentsService invoiceDocumentsService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private InvoiceTaxService invoiceTaxService;

	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;

	@Autowired
	private InvoiceDao invoiceDao;

	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private ExpensesService expensesService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	
	@RequestMapping(value = "/getClientContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody String getClientContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
		
		for(OrgSpoc orgSpoc : orgSpocList){
			StringBuilder sb = new StringBuilder();
			StringBuilder clientSb = new StringBuilder();
			if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName() && !"".equalsIgnoreCase(orgSpoc.getVendorUser().getLastName())){
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
			orgSpoc.setClientCurrency(conversionUtil.getVendorOrgCurrencyCode(orgSpoc.getClientLocationId().getCountry()));
			}
		return commonUtil.toJSON(orgSpocList);
	} 
	
	@RequestMapping(value = "/downloadInvoiceAttachment")
    public void downloadInvoiceAttachment(@RequestParam(value="invoiceId", required=false) Long invoiceId ,HttpServletResponse response) throws Exception {
		
		    Invoice invoice = invoiceService.getInvoicetById(invoiceId);
		    response.setContentType("application/octet-stream");
		    StringBuilder fileName = new StringBuilder();
	        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(invoice.getFileName()).append("\"").toString());
			InputStream inputStream = new ByteArrayInputStream(invoice.getDocFile());
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
 }
	
	@RequestMapping(value = "/saveInvoice", method = RequestMethod.POST)
	public @ResponseBody String saveInvoice(InvoiceDTO invoiceDTO,@RequestParam(value = "saveType", required = false) String saveType,
			@RequestParam(value = "invoiceType", required = false) String invoiceType,HttpServletRequest request) throws Exception {

		String invoiceSaved = "failed";
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        invoiceDTO.setStaticPath(staticPath);
		Invoice invoice = invoiceService.saveInvoice(user,orgLocation,saveType,invoiceType,org,currencyCode,invoiceDTO);
		
		if(invoiceType.equals("payable") && saveType.equals("DRAFT")){
		 SourceLeadSessionManager.setSessionAttribute(request, "payableInvoiceCreateMsg", "Successfully you have created a Bill");
		 
		}
		if(invoiceType.equals("payable") && saveType.equals("SENT")){
			 SourceLeadSessionManager.setSessionAttribute(request, "payableInvoiceCreateMsg", "Successfully you have sent a Bill");
			 
			}
		if(invoiceType.equals("Receivable") && saveType.equals("DRAFT"))
		{
			
			 SourceLeadSessionManager.setSessionAttribute(request, "invoiceCreateMsg", "Successfully you have created a Invoice");	
		}
		
		if(invoiceType.equals("Receivable") && saveType.equals("SENT")){
			 SourceLeadSessionManager.setSessionAttribute(request, "invoiceCreateMsg", "Successfully you have sent a Invoice");
			 
			}
		if(null !=invoice){
			if(null != invoice.getProjectID()){
				invoiceService.updateSubmissionApproverHiearachy(invoice.getSubmissionApproverHierarchyIds(),invoice.getInvoiceFrequency(),invoiceType);
			}
			invoiceSaved = "success";
		}
		return invoiceSaved;	
	} 
	
	@RequestMapping(value = "/getInvoiceAssociatesList", method = RequestMethod.GET)
	public @ResponseBody String getInvoiceAssociatesList(@RequestParam(value = "selectedAssociates", required = false) String selectedAssociates, @RequestParam(value = "orgRelationId", required = false) Long orgRelationId, @RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultJson;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Long> associateIds = new ArrayList<Long>();
		List<Candidate> associateList = null;
		if (!isNullOrEmpty(selectedAssociates)) {
			String[] selectedAssoicatesIds = selectedAssociates.split(",");
			for (int i = 0; i < selectedAssoicatesIds.length; i++) {
				associateIds.add(Long.parseLong(selectedAssoicatesIds[i]));
			}
			associateList = candidateService.getAllActiveAssociatesByLocationAndAssoicatesList(loginOrgLocation.getId(), associateIds);
			for (Candidate candidate : associateList) {
				List<Contracts> contractsList = getSowContract(candidate.getId(), loginOrgLocation, orgRelationId, contractType);
				if(null != contractsList && contractsList.size() >0){
					candidate.setContractsList(contractsList);
				}
			}
		}
		else {
			associateList = candidateService.getAllActiveAssociatesByLocation(loginOrgLocation.getId());
		}
		resultJson = commonUtil.toJSON(associateList);
		return resultJson;
	}
    
    
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
 			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
 			@RequestParam(value = "contractType", required = false) String contractType,
 			HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
     	String resultJson = "";
     	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	List<Contracts> contractsList = null;
     	if(null != orgRelationId){
     		if(contractType.equalsIgnoreCase("vendorSow")){
     			 contractsList = invoiceService.getSowcontractListByAssociateId(candidateId,loginOrgLocation.getId(),orgRelationId,Constants.Vendor_SOW);
     		}else{
     			contractsList = invoiceService.getSowcontractListByAssociateId(candidateId,loginOrgLocation.getId(),orgRelationId,Constants.Client_SOW);	
     		}
     		 	
     		if(null != contractsList && contractsList.size() >0){
         		resultJson = commonUtil.toJSON(contractsList);	
         	}
     	}
     	
     	
 	  
 	  return resultJson;
 	}
    
    @RequestMapping(value = "/getInvoiceList",method = RequestMethod.POST)
	public ResponseList<Invoice> getInvoiceList(InvoiceDTO invoiceDTO,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		// TODO do not delete until sorting  work @Rajashekar
	/*	String[] tableColumns = {"invoiceId", "clientName", "clientContact", "projectName", "invoiceDate", "inoviceTotal","inoviceDue","dueDate","inoviceStatus","paymentStatus","action"};
	     int sortColumnIndex = 1; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(invoiceDTO.getOrder() != null && !invoiceDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(invoiceDTO.getOrder().get(0).get("column"));
			sortColumnOrder = invoiceDTO.getOrder().get(0).get("dir");
		}
		invoiceDTO.setSortColumn(tableColumns[sortColumnIndex]);
		invoiceDTO.setSortOrder(sortColumnOrder);*/
		
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDateUTC = null;
		Date startDateUTC = null;
		if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(invoiceDTO.getDayWisetype()) && !"".equals(invoiceDTO.getDayWisetype())) {
			String endDate = outputFormatter1.print(zoned);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDate);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		if (null != invoiceDTO.getDayWisetype() && !"".equals(invoiceDTO.getDayWisetype())) {
			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(invoiceDTO.getDayWisetype())) {
			String startDate = outputFormatter.print(zoned);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				startDateUTC = TZ_DF_TS.parse(startDateString);
			 if (Constants.ONE_MONTH.equals(invoiceDTO.getDayWisetype())) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
			}
			else if (Constants.TWO_MONTHS.equals(invoiceDTO.getDayWisetype())) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
			}
			else if (Constants.THREE_MONTHS.equals(invoiceDTO.getDayWisetype())) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 90);
			}
			else if (Constants.SIX_MONTHS.equals(invoiceDTO.getDayWisetype())) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 180);
			}
			else if (Constants.ONE_YEAR.equals(invoiceDTO.getDayWisetype())) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 365);
			}
			}
		}else {
			if (StringUtils.isNotBlank(invoiceDTO.getStartDate()) && StringUtils.isNotBlank(invoiceDTO.getEndDate()) && !"undefined".equalsIgnoreCase(invoiceDTO.getStartDate()) && !"undefined".equalsIgnoreCase(invoiceDTO.getEndDate())) {
				DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
				DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
				Date startTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),invoiceDTO.getStartDate());
				Date endTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),invoiceDTO.getEndDate());
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
		
		ResponseList<Invoice>  invoiceList = invoiceService.invoiceListByOrgLocationId(orgLocations,startDateUTC,endDateUTC,invoiceDTO);
		Date todayDate = new Date();
		SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
		List<Invoice> invoiceFilterList = invoiceList.getData();
		Integer draftCount = 0;
		Integer paidCount = 0;
		Integer overDueInvoiceCount = 0;
		Integer openInvoiceCount = 0;
		
		Float draftTotalAmount = 0f;
		Float paidTotalAmount = 0f;
		Float dueAfterInvoiceAmount = 0f;
		Float dueBeforeInvoiceAmount = 0f;
		for(Invoice invoice :invoiceFilterList){
			
			boolean isInvoiceDueDate = false;
			
			if(invoice.getOrgRelationID().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				invoice.setClientName(invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName());
			}
			if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
				invoice.setClientName(invoice.getOrgRelationID().getLocationId().getOrgLocationName());
			}
			if(null != invoice.getProjectID()){
				invoice.setProjectName(invoice.getProjectID().getProjectName());	
			}
			StringBuilder contactName = new StringBuilder();
			if(null != invoice.getContactUser()){
				if(invoice.getContactUser().getLastName() != null){
					invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());	
				}else{
					invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).toString());
				}
			}
			if(null != invoice.getDueDate()){
				invoice.setDueDateStr(dateFormat.format(invoice.getDueDate()));	
			}
			if(null != invoice.getInvoiceDate()){
				invoice.setInvoiceDateStr(dateFormat.format(invoice.getInvoiceDate()));	
			}
			
			invoice.setTotal(invoice.getTotal());
			invoice.setInvoiceStatus(invoice.getInvoiceStatus());
			invoice.setPaymentStatus(invoice.getPaymentStatus());
			if(null != invoice.getProjectID()){
				invoice.setProjectId(invoice.getProjectID().getId().toString());
			}
			
			if(!isNullOrEmpty(invoice.getInvoiceStatus())){
				if(Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) || Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())){
					draftCount = draftCount+1;
					draftTotalAmount = draftTotalAmount + Float.parseFloat(invoice.getDueAmount()) ;
					invoice.setDraftInvoiceAmount(String.valueOf(draftTotalAmount));
				}
				
				if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())) && 
						!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
					paidCount = paidCount+1;
					Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
					paidTotalAmount = paidTotalAmount + paidAmount;
					invoice.setPaidInvoiceAmount(String.valueOf(paidTotalAmount));
				}
				
				if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) &&
						(Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))){
					
					String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),invoice.getDueDate());
					Date invoiceDueDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),dueDate);
					
					Float dueAmount = Float.parseFloat(invoice.getDueAmount());
					
					if(dueAmount > 0){
					
						if(todayDate.after(invoiceDueDate)){
							overDueInvoiceCount = overDueInvoiceCount+1;
							dueAfterInvoiceAmount = dueAfterInvoiceAmount + Float.parseFloat(invoice.getDueAmount());
							invoice.setDueAfterInvoiceAmount(String.valueOf(dueAfterInvoiceAmount));
							isInvoiceDueDate = true;
						}else{
							openInvoiceCount = openInvoiceCount+1;
							dueBeforeInvoiceAmount = dueBeforeInvoiceAmount + Float.parseFloat(invoice.getDueAmount());
							invoice.setDueBeforeInvoiceAmount(String.valueOf(dueBeforeInvoiceAmount));
						}
					}
					
					
				}
				
			}
			if(null != invoice.getParentInvoiceId()){
				invoice.setInvoiceParentId(invoice.getParentInvoiceId().getId());	
			}
			invoice.setDraftInvoiceCount(draftCount);
			invoice.setPaidInvoiceCount(paidCount);
			invoice.setDueAfterInvoiceCount(overDueInvoiceCount);
			invoice.setDueBeforeInvoiceCount(openInvoiceCount);
		}
		invoiceList.setData(invoiceFilterList);
		return invoiceList;
		
	}
	
    

	@RequestMapping(value = "/saveInvoicePayment", method = RequestMethod.POST)
	public @ResponseBody String saveInvoicePayment(InvoicePaymentDTO invoicePaymentDTO,@RequestParam(value = "json", required = false) String json,@RequestParam(value = "invoiceId", required = false) Long invoiceId,HttpServletRequest request) throws Exception {

//		String json1 = "{" + json + "}";
		String invoicePaymentSaved = "failed";
//		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
//		MultipartFile multiFile = null;
//		Iterator<String> itrator = request.getFileNames();
//		if(itrator.hasNext()){
//	        multiFile = request.getFile(itrator.next());
//		}
		InvoicePayment invoicePayment = invoicePaymentService.saveInvoicePayment(invoicePaymentDTO,null, user, orgLocation, null,invoiceId);
		if(null !=invoicePayment){
			invoicePaymentSaved = "success";
		}
		return invoicePaymentSaved;
	} 


	
	  @RequestMapping(value = "/getViewInvoice", method = RequestMethod.GET)
	 	public @ResponseBody  String getViewInvoice(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
	 	{
	     	String resultJson;
	     	OrgLocations orgLocations = null;
	     	Invoice invoiceId =  invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
	     	orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	     	Long Id = null;
	     	Invoice parentInvoice  = invoiceService.getInvoicetByParentInvoiceId(invoiceId.getId());
	     	if(parentInvoice.getParentInvoiceId() != null){
	     		Id = parentInvoice.getParentInvoiceId().getId();
	     		orgLocations = parentInvoice.getParentInvoiceId().getOrgLocation();
	     	}else{
	     		Id = invoiceId.getId();
	     		orgLocations = orgLocations;
	     	}
	     	SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
	     	List<Invoice> viewinvoiceList= invoiceService.getViewInvoiceListByInvoiceIdAndOrglocationId(Id, orgLocations.getId());
	     	for(Invoice invoice :viewinvoiceList){
	     		OrgLocations  LoginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	     		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	     		 if(invoice.getOrgRelationID().getLocationId().getId().longValue() == LoginOrgLocations.getId().longValue()){
					invoice.setClientName(invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName());
					StringBuilder contactName = new StringBuilder();
					if(invoice.getContactUser() != null){
						if(invoice.getContactUser().getLastName() != null){
						invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());	
					}else{
						invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).toString());
					}
					}
						
					if(null != invoice.getContactUser()){
						invoice.setClientContactEmail(invoice.getContactUser().getEmail());	
					}
				}
				if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == LoginOrgLocations.getId().longValue()){
					invoice.setClientName(invoice.getOrgRelationID().getLocationId().getOrgLocationName());
					StringBuilder contactName = new StringBuilder();
					if(invoice.getContactUser().getLastName() != null){
						invoice.setClientContactName(contactName.append(invoice.getOwnerUserId().getFirstName()).append(" ").append(invoice.getOwnerUserId().getLastName()).toString());	
					}else{
						invoice.setClientContactName(contactName.append(invoice.getOwnerUserId().getFirstName()).toString());
					}
					if(null != invoice.getContactUser()){
						invoice.setClientContactEmail(invoice.getOwnerUserId().getEmail());	
					}
				}
				invoice.setExchangeRate(invoice.getExchangeRate());
				invoice.setConvertAmount(invoice.getConvertAmount());
				invoice.setConvertCurrencyCode(invoice.getConvertCurrencyCode());
				invoice.setCurrencyCode(invoice.getCurrencyCode());
				
				invoice.setDueDateStr(formatter.format(invoice.getDueDate()));
				invoice.setInvoiceDateStr(formatter.format(invoice.getInvoiceDate()));
				
				
				//get the Invoice Items List
				List<InvoiceLineItems> invoiceLineItemsList= invoiceLineItemService.invoiceLineItemsByInvoiceIdAndOrglocationId(Id, orgLocations.getId());
				for(InvoiceLineItems inviceLine:invoiceLineItemsList){
					if(null != inviceLine.getCandidate()){
						StringBuilder  can = new StringBuilder();
						if(inviceLine.getCandidate().getLastName() != null){
							inviceLine.setCandidateName(can.append(inviceLine.getCandidate().getFirstName()).append(" ").append(inviceLine.getCandidate().getLastName()).toString());	
						}else{
							inviceLine.setCandidateName(can.append(inviceLine.getCandidate().getFirstName()).toString());
						}	
					}
					if(null != inviceLine.getContract()){
						inviceLine.setSowName(inviceLine.getContract().getContractTitle());	
					}
					inviceLine.setRate(inviceLine.getRate());
					inviceLine.setTotalHours(inviceLine.getTotalHours());
					inviceLine.setAmount(inviceLine.getAmount());
					if(null != inviceLine.getStartDate()){
						inviceLine.setStartDateStr(formatter.format(inviceLine.getStartDate()));	
					}
					if(null !=inviceLine.getEndDate()){
						inviceLine.setEndDtaeStr(formatter.format(inviceLine.getEndDate()));	
					}
					
					if(null != inviceLine.getCandidate()){
						if(null != inviceLine.getCandidate().getConsultantUser()){
							Employees employeeId=employeesService.getEmployeeByUserId(inviceLine.getCandidate().getConsultantUser().getId());
							inviceLine.setEmployeeId(employeeId.getEmployeeId());
						}
					}
					
					
					invoice.setInvoiceLineItemsList(invoiceLineItemsList);
				}
				
				//get the InvoiceDocument list 
				List<InvoiceDocument> invoiceDocumentList =invoiceDocumentsService.getInvoiceDocumentsListByInvoiceId(Id);
				for(InvoiceDocument invoiceDoc :invoiceDocumentList)
				{
					invoiceDoc.setFileName(invoiceDoc.getFileName());
					invoice.setInvoiceDocumentsList(invoiceDocumentList);
				}
				List<InvoiceTax> InvoiceTaxList = invoiceTaxService.getInvoiceTaxListByInvoiceId(Id);
				for(InvoiceTax it:InvoiceTaxList)
				{
					it.setTaxName(it.getTaxName());
					it.setTaxType(it.getTaxType());
					it.setTaxValue(it.getTaxValue());
					it.setTaxAmount(it.getTaxAmount());
					invoice.setInvoiceTaxList(InvoiceTaxList);
				}
				
			}
	 	  resultJson = commonUtil.toJSON(viewinvoiceList);
	 	  return resultJson;
	 	}
	  @RequestMapping(value = "/getViewPaymentHistory", method = RequestMethod.GET)
	 	public @ResponseBody  String getViewPaymentHistory(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID,@RequestParam(value = "locationId", required = false) Long locationId,HttpServletRequest request, HttpServletResponse response) throws Exception
	 	{
	     	String resultJson;
	     	Long Id = null;
	     	OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
	     	Invoice invoiceId =  invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
	     	Invoice invoice  = invoiceService.getInvoicetByParentInvoiceId(invoiceId.getId());
	     	if(invoice.getParentInvoiceId() != null){
	     		Id = invoice.getParentInvoiceId().getId();
	     	}else{
	     		Id = invoiceId.getId();
	     	}
	     	List<InvoicePayment> invoicePaymentList = invoicePaymentService.getInvoicePaymentList(Id);
	     	for(InvoicePayment ip :invoicePaymentList){
	     		User user = userService.getUserByUserCode(ip.getCreatedBy());
	     		ip.setPaymentDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),ip.getPaymentDate()));
	     		ip.setPaymentMethod(ip.getPaymentMethod());
	     		ip.setPaymentReferenceNumber(ip.getPaymentReferenceNumber());
	     		ip.setFileName(ip.getFileName());
	     		ip.setPaymentComment(ip.getPaymentComment());
	     		ip.setPaymentAmount(ip.getPaymentAmount());
	     		StringBuilder name = new StringBuilder();
	     		if(null != user.getLastName()){
	     			ip.setCreatedByName(name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());	
	     		}else{
	     			ip.setCreatedByName(name.append(user.getFirstName()).toString());
	     		}
	     		ip.setCurrencyCode(ip.getCurrencyCode());
	     	}
	     	resultJson = commonUtil.toJSON(invoicePaymentList);
		 	  return resultJson;
	     	
	 	}
    
	  @RequestMapping(value = "/downloadInvoicePaymentDocument", method = RequestMethod.GET)
	    public void downloadInvoicePaymentDocument(@RequestParam(value="paymentHistoryId", required=false) Long paymentHistoryId ,HttpServletResponse response) throws Exception {
		        InvoicePayment payment =invoicePaymentService.invoicePaymentById(paymentHistoryId);
			    response.setContentType("application/octet-stream");
			    StringBuilder fileName = new StringBuilder();
		        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(payment.getFileName()).append("\"").toString());
				InputStream inputStream = new ByteArrayInputStream(payment.getDocFile());
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

		}  

	
	@RequestMapping(value = "/getProjectFrequency", method = RequestMethod.GET)
	public @ResponseBody String getProjectFrequency(@RequestParam(value = "projectId", required = false) Long projectId,HttpServletRequest request) throws Exception {
		
		Projects projects = projectsService.getProjectById(projectId);
		List<String> frequencyDates = new ArrayList<>();
		
		Map<String, String> map = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projects);
		
				for (Map.Entry<String, String> entry : map.entrySet())
				{
					StringBuffer sb = new StringBuffer();
				    sb.append(entry.getKey().replaceAll("/", "-")).append("/").append(entry.getValue().replaceAll("/", "-"));
				    frequencyDates.add(sb.toString());
				}
		return commonUtil.toJSON(frequencyDates);
	} 
	
	
	
	@RequestMapping(value = "/getAllProjectAssignedMembers", method = RequestMethod.GET)
	public @ResponseBody String getAllProjectAssignedMembers(@RequestParam(value = "projectId", required = false) String projectId, 
			@RequestParam(value = "frequency", required = false) String frequency, 
			@RequestParam(value = "orgRelationId", required = false) String orgRelationId,
			@RequestParam(value = "contractType", required = false) String contractType,HttpServletRequest request) throws Exception
	{
		
		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		//String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> projectMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		if (null != projectId && !"".equalsIgnoreCase(projectId)) {
			sahList = submissionApproverHierarchyService.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long.parseLong(projectId), startDate, endDate,contractType);
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
					if(null != candidate){
						projectMember.setCandidateId(candidate.getId());
						List<Contracts> contractList = getSowContract(candidate.getId(),orgLocation,Long.parseLong(orgRelationId),contractType);
						if(null != contractList && contractList.size() >0){
							projectMember.setContractsList(contractList);
						}	
					}
					
				}
				projectMember.setStartDate(startDateStr);
				projectMember.setEndDate(endDateStr);
				StringBuilder sb = new StringBuilder();
				if(null != sah.getBillableHours()){
					String billableHours = String.valueOf(sah.getBillableHours());;
					String arr[] = billableHours.split("\\.");
					if (arr[0].length() == 2) {
						sb.append("0").append(arr[0]);
					}else if(arr[0].length() == 3){
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
			if(projectMembersList.size() >0){
				resultJson = commonUtil.toJSON(projectMembersList);
			}
		}
		return resultJson;
	} 
	

	
	private List<Contracts> getSowContract(Long candidateId, OrgLocations orgLocation, Long orgRelationId, String contractType) throws Exception
	{
		List<Contracts> contractsList = null;
     	if(null != orgRelationId){
     		if(contractType.equalsIgnoreCase("vendorSow")){
     			 contractsList = invoiceService.getSowcontractListByAssociateId(candidateId,orgLocation.getId(),orgRelationId,Constants.Vendor_SOW);
     		}else{
     			contractsList = invoiceService.getSowcontractListByAssociateId(candidateId,orgLocation.getId(),orgRelationId,Constants.Client_SOW);	
     		}
     		 	
     	}
		return contractsList;
		
	}


	@RequestMapping(value = "/getAllProjectVendorAssignedMembers", method = RequestMethod.GET)
	public @ResponseBody String getAllProjectVendorAssignedMembers(@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "frequency", required = false) String frequency,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "contractType", required = false) String contractType,HttpServletRequest request) throws Exception {
		
		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		//String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
        
        Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> filteredMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		if(null != projectId && !"".equalsIgnoreCase(projectId)){
		sahList =submissionApproverHierarchyService.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long.parseLong(projectId), startDate, endDate,contractType);
		}
		if(null !=sahList && sahList.size() >0){
			for(SubmissionApproverHierarchy sah : sahList){
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
				projectMember.setSubmissionApproverHierarchyId(sah.getId());
				projectMember.setSubmissionInvoiceId(sah.getSubmissionId().getId());
				Employees employee = employeesService.getEmployeByUserIdAndOrgRelationId(projectMember.getEmployee().getUser().getId(),orgRelationId);
//				Candidate candidate = candidateService.getCandidateByConsultantUserIdOrgRelationId(projectMember.getEmployee().getUser().getId(),orgLocation.getId(),orgRelationId);
				if(null != employee){
					StringBuilder empName =new StringBuilder();
					if(null != projectMember.getEmployee().getUser().getFirstName() && null != projectMember.getEmployee().getUser().getLastName()){
						empName.append(projectMember.getEmployee().getUser().getFirstName()).append(" ").append(projectMember.getEmployee().getUser().getLastName());
					}else{
						empName.append(projectMember.getEmployee().getUser().getFirstName());
					}
					projectMember.setCandidateName(empName.toString());
					projectMember.setStartDate(startDateStr);
					projectMember.setEndDate(endDateStr);
					projectMember.setUserId(employee.getUser().getId().toString());
					String billableHours = String.valueOf(sah.getBillableHours());
					StringBuilder sb = new StringBuilder();
					if(sah.getBillableHours() !=null){
						String arr[] = billableHours.split("\\.");
						if(arr[0].length()==2){
							sb.append("0").append(arr[0]);
						}else if(arr[0].length()==3){
							sb.append(arr[0]);	
						}
						else if(arr[0].length()==1){
							sb.append("00").append(arr[0]);	
						}
						if(arr[1].length()==1){
							sb.append(":").append("0").append(arr[1]);
						}else{
							sb.append(":").append(arr[1]);
						}
					}else{
						sb.append("0");
					}
					projectMember.setTotalHours(sb.toString());
					Candidate candidate = candidateService.getCandidateByConsultantUserIdOrgRelationId(projectMember.getEmployee().getUser().getId(),orgLocation.getId(),orgRelationId);
					if(candidate !=null){
						projectMember.setCandidateId(candidate.getId());
						List<Contracts> contractList = getSowContract(candidate.getId(),orgLocation,orgRelationId,contractType);
						if(null != contractList && contractList.size() >0){
							projectMember.setContractsList(contractList);
						}
						projectMember.setCandidateId(candidate.getId());
					}
					
					filteredMembersList.add(projectMember);
				}
				
			}
			if(filteredMembersList.size() >0){
				resultJson =  commonUtil.toJSON(filteredMembersList);
			}
		}
		return resultJson;
	} 
	
	
	@RequestMapping(value = "/getAllProjectsByOrgRelationId", method = RequestMethod.GET)
 	public @ResponseBody  String getAllProjectsByOrgRelationId(HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
     	String resultJson;
     	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	List<Projects> projectList = projectsService.getAllProjectsByOrgLocationIdAndStatus(orgLocations.getId());
 	    resultJson = commonUtil.toJSON(projectList);
 	    return resultJson;
 	}
	
	/**
	 * Getting rate based on contract
	 * @param contractId
	 * @param request
	 * @param response
	 * @return @ResponseBody
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getSowRateByContractId", method = RequestMethod.GET)
 	public @ResponseBody  String getSowRateByContractId(@RequestParam(value = "contractId", required = false) Long contractId,
 			HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
		  String resultJson = "";
         Contracts contracts = contractsTemplateService.getContractById(contractId);
         String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
         if(null != contracts.getBillingAmount() && null != contracts.getBillingRatePer()){
        	 float hourlyRate = conversionUtil.getHourlyRate(contracts.getBillingAmount(), contracts.getBillingRatePer());
             
             if(!currencyCode.equalsIgnoreCase(contracts.getBillingRateType())){
            	 float coversionRate = conversionUtil.convert(contracts.getBillingRateType(),currencyCode,hourlyRate);
            	 double roundValue = (Math.round(coversionRate * 100.0) / 100.0);
            	 contracts.setBillingAmount(String.valueOf(roundValue));
             }else{
            	 double roundValue = (Math.round(hourlyRate * 100.0) / 100.0);
            	 contracts.setBillingAmount(String.valueOf(roundValue));
             }
             resultJson = commonUtil.toJSON(contracts);		 
         }
        
         return resultJson;
 	}

	/**
	 * @author Laxman
	 * @param invoiceId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/acceptedInvoice", method = RequestMethod.POST)
	public @ResponseBody String acceptedInvoice(@RequestParam(value = "invoiceId", required = false) String invoiceId,HttpServletRequest request) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceId));
		invoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
		invoice.setCreatedBy(user.getUserCode());
		invoice.setCreatedDate(commonUtil.currentDate());
		Invoice childInvoice = invoiceDao.saveInvoce(invoice);
		if(null != childInvoice){
			if(null != invoice.getParentInvoiceId()){
				Invoice parentInvoice =  invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
				parentInvoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
				invoice.setCreatedBy(user.getUserCode());
				parentInvoice.setCreatedDate(commonUtil.currentDate());
				invoiceDao.saveInvoce(parentInvoice);
			}
		}
		// Accept invoice/bill mail
		invoiceAcceptMailtoVendor(user,invoice,orgLocation,invoice.getInvoiceType());
		return "success";
	} 
	
	
	private void invoiceAcceptMailtoVendor(User loginUser, Invoice invoice, OrgLocations loginOrgLocation, String invoiceType) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(invoice.getContactUser().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();

		 String createdDate = (dateFormat.format(invoice.getCreatedDate()));
		 String dueDate = (dateFormat.format(invoice.getDueDate()));
		 
		 StringBuilder stringBuildername = new StringBuilder();
			globalVarsMap.put("clientorVendorName", stringBuildername.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());
			globalVarsMap.put("invoiceID", invoice.getInvoiceId());
			globalVarsMap.put("totalAmouprojectMembersListnt", invoice.getTotal());
			globalVarsMap.put("date", createdDate);
			globalVarsMap.put("dueDate", dueDate);
			globalVarsMap.put("orgName", loginOrgLocation.getOrgLocationName());
			StringBuilder stringBuilderLoginname = new StringBuilder(); 
			globalVarsMap.put("LoginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 
			StringBuilder stringBuildforLink = new StringBuilder();
			globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());

		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 EmailScenario  emailScenario = null ;
		 EmailScenario  notificationScenario = null;
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 if(invoiceType.equalsIgnoreCase("Payables")){
		  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_ACCEPT_MAIL_VENDOR_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_ACCEPT_MAIL_VENDOR_EMAIL,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else{
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_ACCEPT_MAIL_CLIENT_EMAIL,Constants.ENTITY_TYPE_EMAIL);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_ACCEPT_MAIL_CLIENT_EMAIL,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser,  invoice.getContactUser(), invoice.getResponderLocationId(), notificationScenario);
		}
	
	@RequestMapping(value = "/rejectInvoice", method = RequestMethod.POST)
	public @ResponseBody String rejectInvoice(@RequestParam(value = "invoiceId", required = false) String invoiceId,
			@RequestParam(value = "reason", required = false) String reason,HttpServletRequest request) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceId));
		invoice.setInvoiceStatus(Constants.INVOICE_REJECTED);
		invoice.setRejectReason(reason);
		invoice.setCreatedBy(user.getUserCode());
		invoice.setCreatedDate(commonUtil.currentDate());
		Invoice childInvoice = invoiceDao.saveInvoce(invoice);
		if(null != childInvoice){
			if(null != invoice.getParentInvoiceId()){
				Invoice parentInvoice =  invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
				parentInvoice.setInvoiceStatus(Constants.INVOICE_REJECTED);
				parentInvoice.setRejectReason(reason);
				invoice.setCreatedBy(user.getUserCode());
				parentInvoice.setCreatedDate(commonUtil.currentDate());
				invoiceDao.saveInvoce(parentInvoice);
			}
		}
		// Reject invoice/bill mail
		invoiceRejectMailtoVendor(user,invoice,orgLocation,invoice.getInvoiceType());
		return "success";
	} 

	private void invoiceRejectMailtoVendor(User loginUser, Invoice invoice, OrgLocations loginOrgLocation, String invoiceType) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(invoice.getContactUser().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 
		 StringBuilder stringBuildername = new StringBuilder();
			globalVarsMap.put("clientorVendorName", stringBuildername.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());
			globalVarsMap.put("invoiceID", invoice.getInvoiceId());
			globalVarsMap.put("totalAmount", invoice.getTotal());
			globalVarsMap.put("reason", invoice.getRejectReason());
			globalVarsMap.put("orgName", loginOrgLocation.getOrgLocationName());
			StringBuilder stringBuilderLoginname = new StringBuilder(); 
			globalVarsMap.put("LoginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 
			StringBuilder stringBuildforLink = new StringBuilder();
			globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());

		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 EmailScenario  emailScenario = null ;
		 EmailScenario  notificationScenario = null;
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 if(invoiceType.equalsIgnoreCase("Payables")){
		  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_REJECT_MAIL_VENDOR_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_REJECT_MAIL_VENDOR_EMAIL,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else{
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_REJECT_MAIL_CLIENT_EMAIL,Constants.ENTITY_TYPE_EMAIL);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_REJECT_MAIL_CLIENT_EMAIL,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser,  invoice.getContactUser(), invoice.getResponderLocationId(), notificationScenario);
		}
	
	/**
	 * @author Laxman
	 * @param invoiceId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendReminder", method = RequestMethod.POST)
	public @ResponseBody String sendReminder(@RequestParam(value = "invoiceId", required = false) String invoiceId,HttpServletRequest request) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceId));
		invoiceService.sendInvoiceReminderMail(user,orgLocation,invoice,invoice.getInvoiceType(),null);
		return "success";
	} 	
	

	@RequestMapping(value = "/getDueDateByFrequency", method = RequestMethod.GET)
	public @ResponseBody String getDueDateByFrequency(@RequestParam(value = "paymentFrequency", required = false) String paymentFrequency,
			@RequestParam(value = "invoiceDate", required = false) String invoiceDate,
			@RequestParam("locationCode") String locationCode) throws Exception {
		
		OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
		String[] frequency = paymentFrequency.split(" ");
		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dueDateStr = null;
		
		try {

			Date date =  formatter.parse(invoiceDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, Integer.parseInt(frequency[0]));
			dueDateStr =  formatter.format(c.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		return dueDateStr;
	} 
	
	/**
	 * Get exchange currency
	 * @param orgRelationId
	 * @param grandTotal
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return CurrencyConversionResponse
	 */
	
	@RequestMapping(value = "/getConversionExchangeRate", method = RequestMethod.GET)
	@ResponseBody
	public  String getConversionExchangeRate(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId, @RequestParam(value = "grandTotal", required = false) String grandTotal, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultJson ="";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginOrgCurrencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		CurrencyConversionResponse currencyConversionResponse = invoiceService.getExchangeCurrency(loginOrgCurrencyCode,orgLocation,orgRelationId,grandTotal);
		if(null != currencyConversionResponse){
			resultJson = commonUtil.toJSON(currencyConversionResponse);	
		}
        return resultJson;
	}
	
	
	@RequestMapping(value = "/getBillingExportList")
	public void getBillingExportList(InvoiceDTO invoiceDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			    Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
				DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
				DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
				DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date endDateUTC = null;
				String filterDate = null;
				Date startDateUTC = null;
				
				if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(invoiceDTO.getDurationType())) {
					String endDate = outputFormatter1.print(zoned);
					String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDate);
					endDateUTC = TZ_DF_TS.parse(endDateString);
				}
				if (null != invoiceDTO.getDurationType() && !"".equals(invoiceDTO.getDurationType())) {
					if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(invoiceDTO.getDurationType())) {
				String startDate = outputFormatter.print(zoned);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				startDateUTC = TZ_DF_TS.parse(startDateString);
			    if (Constants.ONE_MONTH.equals(invoiceDTO.getDurationType())) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}
				else if (Constants.TWO_MONTHS.equals(invoiceDTO.getDurationType())) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
				}
				else if (Constants.THREE_MONTHS.equals(invoiceDTO.getDurationType())) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 90);
				}
				else if (Constants.SIX_MONTHS.equals(invoiceDTO.getDurationType())) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 180);
				}
				else if (Constants.ONE_YEAR.equals(invoiceDTO.getDurationType())) {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 365);
				}
				}else {
					if (!"".equalsIgnoreCase(invoiceDTO.getExportStartDate()) && !"".equalsIgnoreCase(invoiceDTO.getExportEndDate()) && !"undefined".equalsIgnoreCase(invoiceDTO.getExportStartDate()) && !"undefined".equalsIgnoreCase(invoiceDTO.getExportEndDate())) {
							DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
							DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
							Date startTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),invoiceDTO.getExportStartDate());
							Date endTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),invoiceDTO.getExportEndDate());
							String customStartTimeStr = TZ_DF_CSD.format(startTime);
							String customEndTimeStr = TZ_DF_CED.format(endTime);
							String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
							String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
							startDateUTC = TZ_DF_TS.parse(startDateString);
							endDateUTC = TZ_DF_TS.parse(endDateString);
						}
					}
				}
			
			
			ResponseList<Invoice>  invoiceList = invoiceService.invoiceListByOrgLocationId(orgLocations,startDateUTC,endDateUTC,invoiceDTO);
			
			InvoiceAmount totalStagesAmount = getTotalAmountInBilling(invoiceList);
			
			File file= null;
			String convertedExportStartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDateUTC);
			String convertedExportEndDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDateUTC);
			file = invoiceService.getExportBillingFile(invoiceList,orgLocations.getOrgLocationName(),invoiceDTO.getExportType(),totalStagesAmount,convertedExportStartDate,convertedExportEndDate,invoiceDTO.getInvoiceType(),orgLocations);
	
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
			mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
			if(file != null){
			file.delete();
		}
		}


	private InvoiceAmount getTotalAmountInBilling(ResponseList<Invoice> invoiceList) throws ParseException {

		InvoiceAmount invoiceAmount = new InvoiceAmount();
		
		double totalUnpaidOverDueAmount = 0;
		double totalUnpaidOpenAmount = 0;
		double totalPaidAmount = 0;
		double totalUnbilledAmount = 0;
		
		for(Invoice invoice : invoiceList.getData()){
			
			if(!Constants.REQUIREMENT_STATUS.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) &&
					Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())){
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String currentDate = sdf.format(new Date());
				Date todayDate = sdf.parse(currentDate);
				
				String dueDate = sdf.format(invoice.getDueDate());
				Date invoiceDueDate = sdf.parse(dueDate);
				
				double dueAmount = Double.parseDouble(invoice.getDueAmount());
				
				if(dueAmount > 0){
				
					if(todayDate.after(invoiceDueDate)){
						totalUnpaidOverDueAmount = totalUnpaidOverDueAmount + Double.parseDouble(invoice.getDueAmount());
					}else{
						totalUnpaidOpenAmount = totalUnpaidOpenAmount + Double.parseDouble(invoice.getDueAmount());
					}
				}
				
				
			}
			
			
			if(!Constants.REQUIREMENT_STATUS.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && Constants.INVOICE_STATUS_PAID.equalsIgnoreCase((invoice.getPaymentStatus())) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())){
				Double paidAmount1 = Double.parseDouble(invoice.getTotal()) - Double.parseDouble(invoice.getDueAmount());
				String convertedPaidAmount = String.valueOf(paidAmount1);
				totalPaidAmount = totalPaidAmount + Double.parseDouble(convertedPaidAmount);
			}
			if(Constants.REQUIREMENT_STATUS.equalsIgnoreCase(invoice.getInvoiceStatus()) || Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())){
				totalUnbilledAmount = totalUnbilledAmount + Double.parseDouble(invoice.getDueAmount());
			}
		}
		invoiceAmount.setTotalOverDueUnpaidAmount(totalUnpaidOverDueAmount);
		invoiceAmount.setTotalOpenUnpaidAmount(totalUnpaidOpenAmount);
		invoiceAmount.setTotalPaidAmount(totalPaidAmount);
		invoiceAmount.setTotalUnbilledAmount(totalUnbilledAmount);
		return invoiceAmount;
	}
	
    /**
     * Gnearate auto genarated invoices
     * @param invoiceType
     * @param contractType
     * @param request
     * @param response
     * @return String
     * @throws Exception
     */
	@RequestMapping(value = "/generateAutomaticInvoice", method = RequestMethod.POST)
	@ResponseBody
	public String generateAutomaticInvoice(@RequestParam(value = "invoiceType", required = false) String invoiceType, @RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginOrgCurrencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Integer savedInvoiceCount = invoiceService.getAutomaticInvoice(orgLocation, loginOrgCurrencyCode, invoiceType, user,contractType);
		resultJson = commonUtil.toJSON(savedInvoiceCount);
		return resultJson;
	}


	@RequestMapping(value = "/getBillingReportByFrequency", method = RequestMethod.GET)
	@ResponseBody
	public String getBillingReportByFrequency(
			@RequestParam(value = "billingReportType", required = false) String billingReportType,
			@RequestParam(value = "invoiceType", required = false) String invoiceType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resultJson = "";
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		BillingReportDTO invoiceList = invoiceService.getBillingReports(billingReportType, invoiceType, startDate, endDate,orgLocation,user,loginUserRoleId);
		if(invoiceList !=null){
			resultJson = commonUtil.toJSON(invoiceList);	
		}
		return resultJson;
		
		
	}

	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}
	@RequestMapping(value = "/getClientBasedInvoiceList", method = RequestMethod.POST)
	@ResponseBody
	public String getClientBasedInvoiceList(@RequestParam(value = "invoiceType", required = false) String invoiceType,@RequestParam(value = "invoiceId", required = false) String invoiceId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultJson ="";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceId));
		OrgRelations orgRelId = invoice.getOrgRelationID();
		
		List<Invoice> clientinvoiceList = invoiceService.getClientBasedInvoiceLists(orgRelId.getId(),orgLocation.getId(),invoiceType);
		List<Invoice> clientList = new ArrayList<Invoice>();
		for(Invoice invce : clientinvoiceList){
			if(invce.getId().longValue() != Long.parseLong(invoiceId)){
				if(invce.getInvoiceDate() !=null){
					invce.setInvoiceDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invce.getInvoiceDate()));
				}
				if(invce.getDueDate() != null){
					invce.setDueDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invce.getDueDate()));	
				}
				
				
				if(null != invce.getContactUser()){
					StringBuilder username = new StringBuilder();
					if(null != invce.getContactUser().getLastName()){
						username.append(invce.getContactUser().getFirstName()).append(" ").append(invce.getContactUser().getLastName()).toString();
					}else{
						username.append(invce.getContactUser().getFirstName());
					}
					invce.setClientContactName(username.toString());
				}
				clientList.add(invce);
			}
		}
		if (clientList !=null && clientList.size() > 0){
		resultJson = commonUtil.toJSON(clientList);	
		}
		return resultJson;
	}
	
	
	@RequestMapping(value = "/linkInvoice", method = RequestMethod.POST)
	@ResponseBody
	public String linkInvoice(@RequestParam(value = "invoiceType", required = false) String invoiceType,
			@RequestParam(value = "checkVal", required = false) Long  id, 
			@RequestParam(value = "masterInvoiceId", required = false) Long  masterInvoiceId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultJson ="";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Invoice masterInvoice = invoiceService.getInvoicetById(masterInvoiceId);
		Invoice invoice = invoiceService.getInvoicetById(id);
		invoice.setLinkInvoiceReferenceId(masterInvoice.getId());
		invoice.setLinkInvoiceRefInvoiceId(masterInvoice.getInvoiceId());
		invoice.setPreviousInvoiceStatus(invoice.getInvoiceStatus());
		invoice.setInvoiceStatus(Constants.LINKED_STATUS);
		invoiceService.linkInvoice(invoice);
		if(masterInvoice.getId() !=null){
			Invoice  childInvoice = invoiceService.getInvoicetByParentId(invoice.getId());
			if(childInvoice !=null){
				childInvoice.setPreviousInvoiceStatus(childInvoice.getInvoiceStatus());
				childInvoice.setInvoiceStatus(Constants.LINKED_STATUS);
				invoiceService.linkInvoice(childInvoice);		
			}else{
				Invoice  parentInvoice = invoiceService.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
				parentInvoice.setPreviousInvoiceStatus(parentInvoice.getInvoiceStatus());
				parentInvoice.setInvoiceStatus(Constants.LINKED_STATUS);
				invoiceService.linkInvoice(parentInvoice);
			}
			
		}
		resultJson = commonUtil.toJSON(invoice);	
		return resultJson;

	}
	/**
	 * Dipalay billing summary in clients summary tabs
	 * @param orgRelationId
	 * @param clientLocationId
	 * @param dayWisetype
	 * @param customStartDate
	 * @param customEndDate
	 * @param invoiceStatus
	 * @param paymentStatus
	 * @param invoiceType
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getInvoiceListBasedOnOrgrelationId", method = RequestMethod.POST)
	public @ResponseBody String getInvoiceListBasedOnOrgrelationId(
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "clientLocationId", required = false) Long clientLocationId,
			@RequestParam(value = "dayWisetype", required = false) String dayWisetype,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate,
			@RequestParam(value = "invoiceStatus", required = false) String invoiceStatus,
			@RequestParam(value = "paymentStatus", required = false) String paymentStatus,
			@RequestParam(value = "invoiceType", required = false) String invoiceType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Invoice> invoiceList = invoiceService.getInvoiceByOrgRelationId(orgRelationId, clientLocationId,invoiceStatus, paymentStatus, invoiceType,orgLocations,dayWisetype,customStartDate,customEndDate);

		return commonUtil.toJSON(invoiceList);
	}
	
	/**
	 * Unlinking the Linked Invoice/Bill
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unLinkInvoice", method = RequestMethod.POST)
	@ResponseBody
	public boolean unLinkInvoice(@RequestParam(value = "invoiceId", required = false) Long invoiceId) throws Exception
	{
		boolean result = false;
		Invoice invoice = invoiceService.getInvoicetById(invoiceId);
		invoice.setLinkInvoiceReferenceId(null);
		invoice.setLinkInvoiceRefInvoiceId(null);
		invoice.setInvoiceStatus(invoice.getPreviousInvoiceStatus());
		invoice.setPreviousInvoiceStatus(invoice.getInvoiceStatus());
		invoiceService.linkInvoice(invoice);
		
		Invoice childInvoice = invoiceService.getInvoicetByParentId(invoice.getId());
		if(childInvoice !=null){
			childInvoice.setInvoiceStatus(childInvoice.getPreviousInvoiceStatus());
			childInvoice.setPreviousInvoiceStatus(childInvoice.getInvoiceStatus());
			Invoice invoiceData = invoiceService.linkInvoice(childInvoice);
			if (invoiceData != null) {
				result = true;
			}
		}else{
			Invoice parentInvoice = invoiceService.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
			parentInvoice.setInvoiceStatus(parentInvoice.getPreviousInvoiceStatus());
			parentInvoice.setPreviousInvoiceStatus(parentInvoice.getInvoiceStatus());
			Invoice invoiceData = invoiceService.linkInvoice(parentInvoice);
			if (invoiceData != null) {
				result = true;
			}
		}
		
		
		return result;
	}
	
	/**
	 * Deleteing Invoice/bill of draft stage
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInvoice", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteInvoice(@RequestParam(value = "invoiceId", required = false) Long invoiceId,
			@RequestParam(value = "reason", required = false) String reason,
			@RequestParam(value = "invoiceType", required = false) String invoiceType) throws Exception
	{
		boolean isDeleted = invoiceService.deleteInvoice(invoiceId,reason,invoiceType);
		return isDeleted;
	}
	
	@RequestMapping(value = "/getTotalHosursForBilling", method = RequestMethod.GET)
	public @ResponseBody String getTotalHosursForBilling(@RequestParam(value = "startDate", required = false) String startDate,@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "averageWorkingHours", required = false) Integer averageWorkingHours,@RequestParam(value = "locationId", required = false) Long locationId) throws Exception {
			
			OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
			StringBuilder sb = new StringBuilder();
			String estimatedHours = null;
			try {
				Date requestStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate);
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(requestStartDate); 
				
				Date requestEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate);
				Calendar endCal = Calendar.getInstance();
				endCal.setTime(requestEndDate); 
				
				int workDays = 0;
			    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			        startCal.setTime(requestEndDate);
			        endCal.setTime(requestStartDate);
			    }
			    do {
//			        if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			            ++workDays;
//			        }
			        startCal.add(Calendar.DATE, 1);
			    } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()); 

			    estimatedHours = String.valueOf((workDays*averageWorkingHours));

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			return sb.append(estimatedHours).append(":00").toString();
		} 
	
	@RequestMapping(value = "/getTotalHosursForPayable", method = RequestMethod.GET)
	public @ResponseBody String getTotalHosursForPayable(@RequestParam(value = "startDate", required = false) String startDate,@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "averageWorkingHours", required = false) Integer averageWorkingHours,@RequestParam(value = "locationId", required = false) Long locationId) throws Exception {
			
		OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
			StringBuilder sb = new StringBuilder();
			String estimatedHours = null;
			try {
				Date requestStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate);
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(requestStartDate); 
				
				Date requestEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate);
				Calendar endCal = Calendar.getInstance();
				endCal.setTime(requestEndDate); 
				
				int workDays = 0;
			    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			        startCal.setTime(requestEndDate);
			        endCal.setTime(requestStartDate);
			    }
			    do {
//			        if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			            ++workDays;
//			        }
			        startCal.add(Calendar.DATE, 1);
			    } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()); 

			    estimatedHours = String.valueOf((workDays*averageWorkingHours));

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			return sb.append(estimatedHours).append(":00").toString();
		} 
	 
	
	/**
	 * to save invoice/bill based on expenses
	 * 
	 * @param json
	 * @param saveType
	 * @param invoiceType
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExpensesInvoice", method = RequestMethod.POST)
	public @ResponseBody String saveExpensesInvoice(InvoiceDTO invoiceDTO,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "orgLocationId", required = false) String orgLocationId,
			@RequestParam(value = "saveType", required = false) String saveType,
			@RequestParam(value = "currencyCode", required = false) String currencyCode,
			@RequestParam(value = "invoiceType", required = false) String invoiceType,
			@RequestParam(value = "type", required = false) String invoiceCategory, HttpServletRequest request)
					throws Exception {

		String invoiceSaved = "failed";

		User user = userService.getUserById(Long.parseLong(userId));
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(orgLocationId));
		Org org = orgLocation.getOrg();

		Invoice invoice = invoiceService.saveExpenseInvoice(invoiceDTO, user, orgLocation, saveType,invoiceType, org, currencyCode,invoiceCategory);

		if(invoice !=null && invoice.getExpensesId() !=null && Constants.SENT.equalsIgnoreCase(saveType)) {
			invoiceService.updateExpensesForinvoice(invoice,invoiceType);
		}
		
		if (Constants.PAYABLE.equals(invoiceType) && Constants.DRAFT.equals(saveType)) {
			SourceLeadSessionManager.setSessionAttribute(request, "payableInvoiceCreateMsg",
					"Successfully you have created a Bill");

		}
		if (Constants.PAYABLE.equals(invoiceType) && Constants.INVOICE_SENT.equals(saveType)) {
			SourceLeadSessionManager.setSessionAttribute(request, "payableInvoiceCreateMsg",
					"Successfully you have sent a Bill");

		}
		if (Constants.RECEIVABLE.equals(invoiceType) && Constants.DRAFT.equals(saveType)) {

			SourceLeadSessionManager.setSessionAttribute(request, "invoiceCreateMsg",
					"Successfully you have created a Invoice");
		}

		if (Constants.RECEIVABLE.equals(invoiceType) && Constants.INVOICE_SENT.equals(saveType)) {
			SourceLeadSessionManager.setSessionAttribute(request, "invoiceCreateMsg",
					"Successfully you have sent a Invoice");

		}
		if (null != invoice) {
			if (null != invoice.getProjectID()) {
				invoiceService.updateSubmissionApproverHiearachy(invoice.getSubmissionApproverHierarchyIds(),
						invoice.getInvoiceFrequency(), invoiceType);
			}
			invoiceSaved = "success";
		}
		return invoiceSaved;
	}
	

/**
	 * To get project assigned members based on project id
	 * 
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProjectAssignMembers", method = RequestMethod.GET)
	@ResponseBody
	public Collection<ProjectMembers> getProjectAssignMembers(
			@RequestParam(value = "projectId", required = false) Long projectId) throws Exception {

		List<ProjectMembers> associatesList = projectMembersService.getProjectsAssignedMembersForExpenses(projectId);
		return associatesList;

	}
	
	/**
	 * Gte Expenses Frequencies by Expense Id
	 * @param projectMemberId
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExpensesByProjectMemberId", method = RequestMethod.GET)
	@ResponseBody
	public List<Expenses> getExpensesByProjectMemberId(
			@RequestParam(value = "memberId", required = false) Long projectMemberId,
			@RequestParam(value = "invoiceType", required = false) String invoiceType) throws Exception {

		List<Expenses> expensesList = expensesService.getExpensesByProjectMemberId(projectMemberId,invoiceType);
		return expensesList;

	}
	
	/**
	 * Get Expenses Details By expense id
	 * @param expenseId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExpensesEntries", method = RequestMethod.GET)
	@ResponseBody
	public  List<ExpensesEntriesDTO> getExpensesEntries(@RequestParam(value = "expensesId", required = false) Long expenseId)throws Exception {
		List<ExpensesEntriesDTO> expensesEntriesDTOList = expensesService.getApprovedExpenses(expenseId);
		return expensesEntriesDTOList;
	}
	
	/**
	 * To get peoject assigned vendor members
	 * @param projectId
	 * @param orgRelationId
	 * @param userId
	 * @param orgLocationId
	 * @return List<ProjectMembers>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVendorAssignedMembersForProjects", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectMembers> getVendorAssignedMembersForProjects(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {

		List<ProjectMembers> associatesList = projectMembersService.getVendorAssignedMembersForProjects(projectId,orgLocationId,orgRelationId);
		return associatesList;
	}
	
}