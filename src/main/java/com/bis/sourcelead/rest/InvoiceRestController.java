package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.InvoicePaymentService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

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
	
	
	@RequestMapping(value = "/saveInvoice", method = RequestMethod.POST)
	public @ResponseBody String saveInvoice(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "saveType", required = false) String saveType,
			@RequestParam(value = "invoiceType", required = false) String invoiceType,MultipartHttpServletRequest request) throws Exception {

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
		Invoice invoice = invoiceService.saveInvoice(jsonObj,user,orgLocation,saveType,attachedfileList,invoiceType);
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

		for(Invoice invoice :invoiceList){
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
			invoice.setDueDateStr(Constants.DF_DMY.format(invoice.getDueDate()));
			invoice.setInvoiceDateStr(Constants.DF_DMY.format(invoice.getInvoiceDate()));
			invoice.setTotal(invoice.getTotal());
			invoice.setInvoiceStatus(invoice.getInvoiceStatus());
			invoice.setPaymentStatus(invoice.getPaymentStatus());
			
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

    
}
