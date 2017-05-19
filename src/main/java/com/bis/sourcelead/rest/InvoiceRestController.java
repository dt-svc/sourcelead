package com.bis.sourcelead.rest;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.InvoiceLineItemService;
import com.bis.sourcelead.service.InvoicePaymentService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.InvoiceTaxService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.InvoiceDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

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
	public @ResponseBody String saveInvoice(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "saveType", required = false) String saveType,
			@RequestParam(value = "invoiceType", required = false) String invoiceType,MultipartHttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		String invoiceSaved = "failed";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		List<MultipartFile> attachedfileList = new ArrayList<MultipartFile>();
		int attcahmentMaxRowNumber = Integer.parseInt(jsonObj.getString("invoiceAttcahmentMaxRowNumber"));
		 final Map files = request.getFileMap();
		for(int i=0;i<attcahmentMaxRowNumber;i++){
			MultipartFile  multiFile = (MultipartFile)files.get(i+"invoicedocFile");
			if(multiFile != null && !multiFile.isEmpty()){
				attachedfileList.add(multiFile); 
			}
		}

		Invoice invoice = invoiceService.saveInvoice(jsonObj,user,orgLocation,saveType,attachedfileList,invoiceType,org);
		invoiceService.getPdfFileForInvoice(invoice,orgLocation,user,org);
		if(null !=invoice){
			invoiceSaved = "success";
		}
		return invoiceSaved;
	} 
	
    @RequestMapping(value = "/getInvoiceAssociatesList", method = RequestMethod.GET)
 	public @ResponseBody  String getInvoiceAssociatesList(@RequestParam(value = "selectedAssociates", required = false) String selectedAssociates,HttpServletRequest request, HttpServletResponse response) throws Exception
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
         	associateList = candidateService.getAllActiveAssociatesByLocationAndAssoicatesList(loginOrgLocation.getId(),associateIds);
     	}else{
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
     		 	
     		if(null != contractsList){
         		resultJson = commonUtil.toJSON(contractsList);	
         	}
     	}
     	
     	
 	  
 	  return resultJson;
 	}
    
    @RequestMapping(value = "/getInvoiceList")
	public String getInvoiceList(@RequestParam(value = "invoiceStatus", required = false) String invoiceStatus,
			@RequestParam(value = "paymentStatus", required = false) String paymentStatus,
			@RequestParam(value = "dayWisetype", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate,
			@RequestParam(value = "invoiceType", required = false) String invoiceType,HttpServletRequest request) throws Exception {
		
    	String resultJson;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDateUTC = null;
		String filterDate = null;
		Date startDateUTC = null;
		if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)) {
			String endDate = outputFormatter1.print(zoned);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDate);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		if (null != reportType && !"".equals(reportType)) {
			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)) {
			String startDate = outputFormatter.print(zoned);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				startDateUTC = TZ_DF_TS.parse(startDateString);
			 if (Constants.ONE_MONTH.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
			}
			else if (Constants.TWO_MONTHS.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
			}
			else if (Constants.THREE_MONTHS.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 90);
			}
			else if (Constants.SIX_MONTHS.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 180);
			}
			else if (Constants.ONE_YEAR.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 365);
			}
			}else {
				if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate) && !"undefined".equalsIgnoreCase(customStartDate) && !"undefined".equalsIgnoreCase(customEndDate)) {
					DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
					DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
					Date startTime = commonUtil.getDateFormat(customStartDate);
					Date endTime = commonUtil.getDateFormat(customEndDate);
					String customStartTimeStr = TZ_DF_CSD.format(startTime);
					String customEndTimeStr = TZ_DF_CED.format(endTime);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
					String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
					startDateUTC = TZ_DF_TS.parse(startDateString);
					endDateUTC = TZ_DF_TS.parse(endDateString);
				}
			}
		}
		
		
		List<Invoice> invoiceList = invoiceService.invoiceListByOrgLocationId(orgLocations.getId(),invoiceStatus,paymentStatus,startDateUTC,endDateUTC,invoiceType);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(new Date());
		Date todayDate = sdf.parse(currentDate);
		
		
		for(Invoice invoice :invoiceList){
			Integer draftCount = 0;
			Integer paidCount = 0;
			Integer overDueInvoiceCount = 0;
			Integer openInvoiceCount = 0;
			
			if(invoice.getOrgRelationID().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				invoice.setClientName(invoice.getOrgRelationID().getResponderLocationId().getOrg().getOrgName());
			}
			if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
				invoice.setClientName(invoice.getOrgRelationID().getLocationId().getOrg().getOrgName());
			}
				
			StringBuilder contactName = new StringBuilder();
			if(invoice.getContactUser().getLastName() != null){
				invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());	
			}else{
				invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).toString());
			}
			if(null != invoice.getDueDate()){
				invoice.setDueDateStr(Constants.DF_DMY.format(invoice.getDueDate()));	
			}
			if(null != invoice.getInvoiceDate()){
				invoice.setInvoiceDateStr(Constants.DF_DMY.format(invoice.getInvoiceDate()));	
			}
			
			invoice.setTotal(invoice.getTotal());
			invoice.setInvoiceStatus(invoice.getInvoiceStatus());
			invoice.setPaymentStatus(invoice.getPaymentStatus());
			if(null != invoice.getProjectID()){
				invoice.setProjectId(invoice.getProjectID().getId().toString());	
			}
			
			
			if(!isNullOrEmpty(invoice.getInvoiceStatus())){
				if(invoice.getInvoiceStatus().equalsIgnoreCase("DRAFT") || invoice.getInvoiceStatus().equalsIgnoreCase("Rejected")){
					draftCount = draftCount+1;
					invoice.setDraftInvoiceAmount(invoice.getDueAmount());
				}
				
				if(!invoice.getInvoiceStatus().equalsIgnoreCase("DRAFT") && !invoice.getInvoiceStatus().equalsIgnoreCase("Rejected") && (invoice.getPaymentStatus().equalsIgnoreCase("Paid") || invoice.getPaymentStatus().equalsIgnoreCase("Partial Paid")) && 
						!invoice.getPaymentStatus().equalsIgnoreCase("Unpaid")){
					paidCount = paidCount+1;
					Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
					invoice.setPaidInvoiceAmount(paidAmount.toString());
				}
				
				if(!invoice.getInvoiceStatus().equalsIgnoreCase("DRAFT") && !invoice.getInvoiceStatus().equalsIgnoreCase("Rejected") &&
						(invoice.getPaymentStatus().equalsIgnoreCase("Unpaid") || invoice.getPaymentStatus().equalsIgnoreCase("Partial Paid"))){
					
					String dueDate = sdf.format(invoice.getDueDate());
					Date invoiceDueDate = sdf.parse(dueDate);
					
					Float dueAmount = Float.parseFloat(invoice.getDueAmount());
					
					if(dueAmount > 0){
					
						if(todayDate.after(invoiceDueDate)){
							overDueInvoiceCount = overDueInvoiceCount+1;
							invoice.setDueAfterInvoiceAmount(invoice.getDueAmount());
						}else{
							openInvoiceCount = openInvoiceCount+1;
							invoice.setDueBeforeInvoiceAmount(invoice.getDueAmount());
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
	    resultJson = commonUtil.toJSON(invoiceList);
	  return resultJson;
	}
	
    

    private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
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
	     	
	     	List<Invoice> viewinvoiceList= invoiceService.getViewInvoiceListByInvoiceIdAndOrglocationId(Id, orgLocations.getId());
	     	for(Invoice invoice :viewinvoiceList){
	     		OrgLocations  LoginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	     		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	     		 if(invoice.getOrgRelationID().getLocationId().getId().longValue() == LoginOrgLocations.getId().longValue()){
					invoice.setClientName(invoice.getOrgRelationID().getResponderLocationId().getOrg().getOrgName());
					StringBuilder contactName = new StringBuilder();
					if(invoice.getContactUser().getLastName() != null){
						invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());	
					}else{
						invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).toString());
					}
					if(null != invoice.getContactUser()){
						invoice.setClientContactEmail(invoice.getContactUser().getEmail());	
					}
				}
				if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == LoginOrgLocations.getId().longValue()){
					invoice.setClientName(invoice.getOrgRelationID().getLocationId().getOrg().getOrgName());
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
				
				invoice.setDueDateStr(Constants.DF_DMY.format(invoice.getDueDate()));
				invoice.setInvoiceDateStr(Constants.DF_DMY.format(invoice.getInvoiceDate()));
				
				
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
						inviceLine.setStartDateStr(Constants.DF_DMY.format(inviceLine.getStartDate()));	
					}
					if(null !=inviceLine.getEndDate()){
						inviceLine.setEndDtaeStr(Constants.DF_DMY.format(inviceLine.getEndDate()));	
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
	 	public @ResponseBody  String getViewPaymentHistory(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
	 	{
	     	String resultJson;
	     	Long Id = null;
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
	     		ip.setPaymentDateStr(Constants.DF_DMY.format(ip.getPaymentDate()));
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
	public @ResponseBody String getAllProjectAssignedMembers(@RequestParam(value = "projectId", required = false) String projectId, @RequestParam(value = "frequency", required = false) String frequency, HttpServletRequest request) throws Exception
	{
		
		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> projectMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		if (null != projectId && !"".equalsIgnoreCase(projectId)) {
			sahList = submissionApproverHierarchyService.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long.parseLong(projectId), startDate, endDate);
		}
		if (null != sahList && sahList.size() > 0) {
			for (SubmissionApproverHierarchy sah : sahList) {
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
				StringBuilder candidateName = new StringBuilder();
				if (null != projectMember.getEmployee().getUser().getFirstName()
						&& null != projectMember.getEmployee().getUser().getLastName()) {
					candidateName.append(projectMember.getEmployee().getUser().getFirstName()).append(" ").append(projectMember.getEmployee().getUser().getLastName());
				}
				else {
					candidateName.append(projectMember.getEmployee().getUser().getFirstName());
				}
				projectMember.setCandidateName(candidateName.toString());
				if (null != projectMember.getEmployee().getUser().getId()) {
					Candidate candidate = candidateService.getCandidateByConsultantUserId(projectMember.getEmployee().getUser().getId(), orgLocation.getId());
					projectMember.setCandidateId(candidate.getId());
				}
				projectMember.setStartDate(startDateStr);
				projectMember.setEndDate(endDateStr);
				String billableHours = String.valueOf(sah.getBillableHours());;
				String arr[] = billableHours.split("\\.");
				StringBuilder sb = new StringBuilder();
				if (arr[0].length() == 2) {
					sb.append("0").append(arr[0]);
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
				projectMembersList.add(projectMember);
			}
			resultJson = commonUtil.toJSON(projectMembersList);
		}
		return resultJson;
//		return commonUtil.toJSON(projectMembersList);
		
		/*List<ProjectMembers> projectMembersList = projectMembersService.getProjectMembersListByOrgLocationsIdAndProjectId(startDate,endDate,orgLocation.getId(),Long.parseLong(projectId));
		
		for(ProjectMembers projectMember :projectMembersList){
			Map<String,Float> totalHours = projectMembersService.getTotalHours(projectMember.getId(),  startDate,  endDate);
			StringBuilder empName =new StringBuilder();
			if(null != projectMember.getEmployee().getUser().getFirstName() && null != projectMember.getEmployee().getUser().getLastName()){
				empName.append(projectMember.getEmployee().getUser().getFirstName()).append(" ").append(projectMember.getEmployee().getUser().getLastName());
			}else{
				empName.append(projectMember.getEmployee().getUser().getFirstName());
			}
			projectMember.setCandidateName(empName.toString());
			if(null != projectMember.getEmployee().getUser().getId()){
				Candidate candidate = candidateService.getCandidateByConsultantUserId(projectMember.getEmployee().getUser().getId(),orgLocation.getId());
				projectMember.setCandidateId(candidate.getId());
			}
			projectMember.setStartDate(startDateStr);
			projectMember.setEndDate(endDateStr);
			String billableHours = String.valueOf(totalHours.get("BILLABLE_HOURS"));;
			String arr[] = billableHours.split("\\.");
			StringBuilder sb = new StringBuilder();
			if(arr[0].length()==2){
				sb.append("0").append(arr[0]);
			}else if(arr[0].length()==1){
				sb.append("00").append(arr[0]);	
			}
			if(arr[1].length()==1){
				sb.append(":").append("0").append(arr[1]);
			}else{
				sb.append(":").append(arr[1]);
			}
			projectMember.setTotalHours(sb.toString());
		}*/
	} 
	
//	commented by Rajashekar if if everything goes fine will delete later
	
	/*@RequestMapping(value = "/saveProjectInvoice", method = RequestMethod.POST)
	public @ResponseBody String saveProjectInvoice(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "saveType", required = false) String saveType,
			@RequestParam(value = "invoiceType", required = false) String invoiceType,MultipartHttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		String invoiceSaved = "failed";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		List<MultipartFile> attachedfileList = new ArrayList<MultipartFile>();
		int attcahmentMaxRowNumber = Integer.parseInt(jsonObj.getString("invoiceAttcahmentMaxRowNumber"));
		 final Map files = request.getFileMap();
		for(int i=0;i<attcahmentMaxRowNumber;i++){
			MultipartFile  multiFile = (MultipartFile)files.get(i+"invoicedocFile");
			if(multiFile != null && !multiFile.isEmpty()){
				attachedfileList.add(multiFile); 
			}
		}
		Invoice invoice = invoiceService.saveProjectInvoice(jsonObj,user,orgLocation,saveType,attachedfileList,invoiceType);
		invoiceService.getPdfFileForInvoice(invoice,orgLocation,user,org);
		if(null !=invoice){
			invoiceSaved = "success";
		}
		return "";
}*/
	
	@RequestMapping(value = "/getAllProjectVendorAssignedMembers", method = RequestMethod.GET)
	public @ResponseBody String getAllProjectVendorAssignedMembers(@RequestParam(value = "projectId", required = false) String projectId,@RequestParam(value = "frequency", required = false) String frequency,@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,HttpServletRequest request) throws Exception {
		
		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
        Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> filteredMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		if(null != projectId && !"".equalsIgnoreCase(projectId)){
		sahList =submissionApproverHierarchyService.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long.parseLong(projectId), startDate, endDate);
		}
		if(null !=sahList && sahList.size() >0){
			for(SubmissionApproverHierarchy sah : sahList){
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
				Candidate candidate = candidateService.getCandidateByConsultantUserIdOrgRelationId(projectMember.getEmployee().getUser().getId(),orgLocation.getId(),orgRelationId);
				if(null != candidate){
					StringBuilder empName =new StringBuilder();
					if(null != projectMember.getEmployee().getUser().getFirstName() && null != projectMember.getEmployee().getUser().getLastName()){
						empName.append(projectMember.getEmployee().getUser().getFirstName()).append(" ").append(projectMember.getEmployee().getUser().getLastName());
					}else{
						empName.append(projectMember.getEmployee().getUser().getFirstName());
					}
					projectMember.setCandidateName(empName.toString());
					if(null != candidate){
						projectMember.setCandidateId(candidate.getId());
					}
					projectMember.setStartDate(startDateStr);
					projectMember.setEndDate(endDateStr);
					String billableHours = String.valueOf(sah.getBillableHours());;
					String arr[] = billableHours.split("\\.");
					StringBuilder sb = new StringBuilder();
					if(arr[0].length()==2){
						sb.append("0").append(arr[0]);
					}else if(arr[0].length()==1){
						sb.append("00").append(arr[0]);	
					}
					if(arr[1].length()==1){
						sb.append(":").append("0").append(arr[1]);
					}else{
						sb.append(":").append(arr[1]);
					}
					projectMember.setTotalHours(sb.toString());
					filteredMembersList.add(projectMember);
				}
				
			}
			resultJson =  commonUtil.toJSON(filteredMembersList);
		}
		return resultJson;
		
		/*List<ProjectMembers> projectMembersList = projectMembersService.getProjectMembersListByOrgLocationsIdAndProjectId(startDate,endDate,orgLocation.getId(),Long.parseLong(projectId));
		for(ProjectMembers projectMember :projectMembersList){
			Candidate candidate = candidateService.getCandidateByConsultantUserIdOrgRelationId(projectMember.getEmployee().getUser().getId(),orgLocation.getId(),orgRelationId);
			if(null != candidate){
				Map<String,Float> totalHours = projectMembersService.getTotalHours(projectMember.getId(),  startDate,  endDate);
				StringBuilder empName =new StringBuilder();
				if(null != projectMember.getEmployee().getUser().getFirstName() && null != projectMember.getEmployee().getUser().getLastName()){
					empName.append(projectMember.getEmployee().getUser().getFirstName()).append(" ").append(projectMember.getEmployee().getUser().getLastName());
				}else{
					empName.append(projectMember.getEmployee().getUser().getFirstName());
				}
				projectMember.setCandidateName(empName.toString());
				if(null != candidate){
					projectMember.setCandidateId(candidate.getId());
				}
				projectMember.setStartDate(startDateStr);
				projectMember.setEndDate(endDateStr);
				String billableHours = String.valueOf(totalHours.get("BILLABLE_HOURS"));;
				String arr[] = billableHours.split("\\.");
				StringBuilder sb = new StringBuilder();
				if(arr[0].length()==2){
					sb.append("0").append(arr[0]);
				}else if(arr[0].length()==1){
					sb.append("00").append(arr[0]);	
				}
				if(arr[1].length()==1){
					sb.append(":").append("0").append(arr[1]);
				}else{
					sb.append(":").append(arr[1]);
				}
				projectMember.setTotalHours(sb.toString());
				filteredMembersList.add(projectMember);
			}
		}*/
		
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
	
	
	
	@RequestMapping(value = "/getSowRateByContractId", method = RequestMethod.GET)
 	public @ResponseBody  String getSowRateByContractId(@RequestParam(value = "contractId", required = false) Long contractId,
 			HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
		  String resultJson;
         Contracts contractsList = contractsTemplateService.getContractById(contractId);
         resultJson = commonUtil.toJSON(contractsList);	
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
		return "success";
	} 
	
	@RequestMapping(value = "/rejectInvoice", method = RequestMethod.POST)
	public @ResponseBody String rejectInvoice(@RequestParam(value = "invoiceId", required = false) String invoiceId,HttpServletRequest request) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceId));
		invoice.setInvoiceStatus(Constants.INVOICE_REJECTED);
		invoice.setCreatedBy(user.getUserCode());
		invoice.setCreatedDate(commonUtil.currentDate());
		Invoice childInvoice = invoiceDao.saveInvoce(invoice);
		if(null != childInvoice){
			if(null != invoice.getParentInvoiceId()){
				Invoice parentInvoice =  invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
				parentInvoice.setInvoiceStatus(Constants.INVOICE_REJECTED);
				invoice.setCreatedBy(user.getUserCode());
				parentInvoice.setCreatedDate(commonUtil.currentDate());
				invoiceDao.saveInvoce(parentInvoice);
			}
		}
		return "success";
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
		invoiceService.sendInvoiceMail(user,orgLocation,invoice);
		return "success";
	} 	
	
	/**
	 * @author Laxman
	 * @param invoiceId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPrint/{id}", method = RequestMethod.GET)
	public @ResponseBody  void getPrint(@PathVariable("id") String invoiceId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long Id = null;
     	Invoice parentInvoice  = invoiceService.getInvoicetByParentInvoiceId(Long.parseLong(invoiceId));
     	if(parentInvoice.getParentInvoiceId() != null){
     		Id = parentInvoice.getParentInvoiceId().getId();
     	}else{
     		Id = Long.parseLong(invoiceId); 
     	}
		Invoice invoice = invoiceService.getInvoicetById(Id);
  	    byte[] pdfBytes = invoice.getDocFile();
  	    response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
  	    response.setContentType("application/pdf");
  	    response.setContentLength(pdfBytes.length);
        ServletOutputStream sos = response.getOutputStream();
        sos.write(pdfBytes);
        sos.flush();
        sos.close();
	}
	
	
	/**
	 * @author Laxman
	 * @param invoiceId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/downloadInvoice", method = RequestMethod.GET)
	public void downloadInvoice(@RequestParam(value="invoiceId", required=false) String invoiceId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long Id = null;
     	Invoice parentInvoice  = invoiceService.getInvoicetByParentInvoiceId(Long.parseLong(invoiceId));
     	if(parentInvoice.getParentInvoiceId() != null){
     		Id = parentInvoice.getParentInvoiceId().getId();
     	}else{
     		Id = Long.parseLong(invoiceId); 
     	}
		Invoice invoice = invoiceService.getInvoicetById(Id);
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

	@RequestMapping(value = "/getDueDateByFrequency", method = RequestMethod.GET)
	public @ResponseBody String getDueDateByFrequency(@RequestParam(value = "paymentFrequency", required = false) String paymentFrequency,
			@RequestParam(value = "invoiceDate", required = false) String invoiceDate,HttpServletRequest request) throws Exception {
		
		
		String[] frequency = paymentFrequency.split(" ");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
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
	
	
}
