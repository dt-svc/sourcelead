package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.beans.BillingReportDTO;
import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dto.BillingDTO;
import com.bis.sourcelead.dto.CreditDebitNoteDTO;
import com.bis.sourcelead.dto.CreditDebitNoteListDTO;
import com.bis.sourcelead.dto.CustomInvoiceTemplateDTO;
import com.bis.sourcelead.dto.DashboardItemsDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.InvoicePaymentDTO;
import com.bis.sourcelead.dto.InvoiceTimelineDTO;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
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
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
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
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.InvoiceDocumentsService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.CustomInvoicePDFHelper;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.InvoicePdfHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;

@RestController
@RequestMapping("/app")
public class InvoiceRestController {
	
	@Value("${system.default.font}")
	private String systemDefaultFont;
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceRestController.class);
	@Autowired
	private OrgService orgService;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private OrgSpocService orgSpocService;
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
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
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	protected static boolean isNullOrEmpty(String str)
	{
		return (str == null || "".equals(str.trim()));
	}
	
	@RequestMapping(value = "/getClientContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody ResponseList<OrgSpoc> getClientContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<OrgSpoc> responseList = new ResponseList<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgSpoc> orgSpocList = null;
		if (orgRelationId != "" && orgRelationId != null) {
			orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
			for (OrgSpoc orgSpoc : orgSpocList) {
				StringBuilder sb = new StringBuilder();
				StringBuilder clientSb = new StringBuilder();
				if (null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName() && !"".equalsIgnoreCase(orgSpoc.getVendorUser().getLastName())) {
					sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
				}
				else {
					sb.append(orgSpoc.getVendorUser().getFirstName());
				}
				if (null != orgSpoc.getClientUser().getFirstName() && null != orgSpoc.getClientUser().getLastName()) {
					clientSb.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName());
				}
				else {
					clientSb.append(orgSpoc.getClientUser().getFirstName());
				}
				orgSpoc.setSpocName(sb.toString());
				orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
				orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
				orgSpoc.setOwnerName(clientSb.toString());
				orgSpoc.setClientCurrency(conversionUtil.getVendorOrgCurrencyCode(orgSpoc.getClientLocationId().getCountry()));
			}
		}
		responseList.setData(orgSpocList);
		return responseList;
	}
	
	/*
	 * @RequestMapping(value = "/downloadInvoiceAttachment") public void
	 * downloadInvoiceAttachment(@RequestParam(value="invoiceId",
	 * required=false) Long invoiceId ,HttpServletResponse response) throws
	 * Exception {
	 * 
	 * Invoice invoice = invoiceService.getInvoicetById(invoiceId);
	 * response.setContentType("application/octet-stream"); StringBuilder
	 * fileName = new StringBuilder(); response.setHeader("Content-Disposition",
	 * fileName.append("attachment; filename=\""
	 * ).append(invoice.getFileName()).append("\"").toString()); InputStream
	 * inputStream = new ByteArrayInputStream(invoice.getDocFile()); try {
	 * OutputStream outStream = response.getOutputStream();
	 * 
	 * byte[] buffer = new byte[4096]; int bytesRead = -1;
	 * 
	 * while ((bytesRead = inputStream.read(buffer)) != -1) {
	 * outStream.write(buffer, 0, bytesRead); }
	 * 
	 * inputStream.close(); outStream.close();
	 * 
	 * } catch (FileNotFoundException e) {
	 * logger.error(ExceptionUtils.getFullStackTrace(e)); } }
	 */
	
	@Autowired
	private CustomInvoicePDFHelper customInvoicePDFHelper;
	
	@RequestMapping(value = "/saveInvoice", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveInvoice(InvoiceDTO invoiceDTO, @RequestParam(value = "saveType", required = false) String saveType, @RequestParam(value = "invoiceType", required = false) String invoiceType, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
			try {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if (user != null) {
				if (Validator.validate(invoiceDTO)) {
					OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
					Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					invoiceDTO.setStaticPath(staticPath);
					Invoice invoice = invoiceService.saveInvoice(user, orgLocation, saveType, invoiceType, org, currencyCode, invoiceDTO, systemDefaultFont);
					if (null != invoice) {
						if (null != invoice.getProjectID()) {
							invoiceService.updateSubmissionApproverHiearachy(invoice, invoiceType);
						}
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		
		return response;
	}
	
	@RequestMapping(value = "/saveInvoiceForJobClosure", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveInvoiceForJobClosure(InvoiceDTO invoiceDTO, 
			@RequestParam(value = "saveType", required = false) String saveType, 
			@RequestParam(value = "invoiceType", required = false) String invoiceType,
			@RequestParam(value = "isJobClosure", required = false) String isJobClosure,
			HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
			try {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if (user != null) {
				if (Validator.validate(invoiceDTO)) {
					OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
					Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					invoiceDTO.setStaticPath(staticPath);
					if(StringUtils.isNotBlank(isJobClosure)){
						invoiceDTO.setIsJobClosure(isJobClosure);	
					}
					Invoice invoice = invoiceService.saveInvoice(user, orgLocation, saveType, invoiceType, org, currencyCode, invoiceDTO, systemDefaultFont);
					// update requirement submission 
					if(invoice !=null){
					Set<InvoiceLineItems> lineItemsList = invoice.getInvoiceLineItems();
					lineItemsList.forEach(list->{
						if(list.getRequirementSubmissionId() !=null){
					try {
						RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(list.getRequirementSubmissionId().getId());
						submission.setInvoiceGenerated(Constants.YES);
						requirementSubmissionService.storeRequirementSubmission(submission);
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
						}
					});
					}
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		
		return response;
	}
	
	@RequestMapping(value = "/saveBillForJobClosure", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveBillForJobClosure(InvoiceDTO invoiceDTO, 
			@RequestParam(value = "saveType", required = false) String saveType, 
			@RequestParam(value = "invoiceType", required = false) String invoiceType,
			@RequestParam(value = "isJobClosure", required = false) String isJobClosure,
			HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
			try {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if (user != null) {
				if (Validator.validate(invoiceDTO)) {
					OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
					Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					invoiceDTO.setStaticPath(staticPath);
					if(StringUtils.isNotBlank(isJobClosure)){
						invoiceDTO.setIsJobClosure(isJobClosure);	
					}
					Invoice invoice = invoiceService.saveInvoice(user, orgLocation, saveType, invoiceType, org, currencyCode, invoiceDTO, systemDefaultFont);
					// update requirement submission 
					if(invoice !=null){
					Set<InvoiceLineItems> lineItemsList = invoice.getInvoiceLineItems();
					lineItemsList.forEach(list->{
						if(list.getRequirementSubmissionId() !=null){
					try {
						RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(list.getRequirementSubmissionId().getId());
						submission.setInvoiceGenerated(Constants.YES);
						requirementSubmissionService.storeRequirementSubmission(submission);
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
						}
					});
					}
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		
		return response;
	}
	
	@RequestMapping(value = "/saveCreditDebitNote", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCreditDebitNote(CreditDebitNoteDTO creditDebitNoteDTO, 
			@RequestParam(value = "saveType", required = false) String saveType, 
			@RequestParam(value = "noteType", required = false) String noteType, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
			try {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if (user != null) {
				if (Validator.validate(creditDebitNoteDTO)) {
					OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
					Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					//Invoice invoice = invoiceService.saveInvoice(user, orgLocation, saveType, invoiceType, org, currencyCode, invoiceDTO);
					invoiceService.saveCreditDebitNote(user, orgLocation, saveType, noteType, creditDebitNoteDTO,currencyCode);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		
		return response;
	}
	
	@RequestMapping(value = "/validateInvoiceId",  method = RequestMethod.POST)
    public @ResponseBody Boolean validateInvoiceId(@RequestParam(value="invoiceId", required=false) String invoiceId, @RequestParam(value="invoiceType", required=false) String invoiceType,
    		HttpServletRequest request) throws Exception {
	       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	       Boolean flag = false;
	       if(null != invoiceId){
	        flag = invoiceService.getInvoiceByInvoiceId(invoiceId, orgLocation.getId(), invoiceType);
	       }
	      return flag;
		 
	    }
	
	@RequestMapping(value = "/getInvoiceAssociatesList", method = RequestMethod.GET)
	public @ResponseBody ResponseList<Candidate> getInvoiceAssociatesList(@RequestParam(value = "selectedAssociates", required = false) String selectedAssociates, @RequestParam(value = "orgRelationId", required = false) Long orgRelationId, @RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<Candidate> responseList = new ResponseList<>();
//		String resultJson;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Long> associateIds = new ArrayList<Long>();
		List<Candidate> associateList = null;
		if (!isNullOrEmpty(selectedAssociates)) {
			String[] selectedAssoicatesIds = selectedAssociates.split(",");
			for (int i = 0; i < selectedAssoicatesIds.length; i++) {
				associateIds.add(Long.parseLong(selectedAssoicatesIds[i]));
			}
			associateList = candidateService.getAllActiveAssociatesByLocationAndAssoicatesList(loginOrgLocation.getId(), associateIds);
			
		}
		else {
			associateList = candidateService.getAllActiveAssociatesByLocation(loginOrgLocation.getId());
		}
		for (Candidate candidate : associateList) {
			List<Contracts> contractsList = getSowContract(candidate.getId(), loginOrgLocation, orgRelationId, contractType);
			if (null != contractsList && contractsList.size() > 0) {
				candidate.setContractsList(contractsList);
			}
		}
//		resultJson = commonUtil.toJSON(associateList);
		responseList.setData(associateList);
		return responseList;
	}
	
	
	@RequestMapping(value = "/getInvoiceCandidatesListForJobClosure", method = RequestMethod.GET)
	public @ResponseBody ResponseList<RequirementSubmissionsDTO> getInvoiceCandidatesListForJobClosure(@RequestParam(value = "selectedAssociates", required = false) String selectedAssociates, 
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "contractType", required = false) String contractType,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<RequirementSubmissionsDTO> responseList = new ResponseList<>();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<RequirementSubmissionsDTO> applicantsList = requirementSubmissionService.getCandidateForInvoiceJobClosure(loginOrgLocation, user, orgRelationId,contractType,selectedAssociates);
		
		responseList.setData(applicantsList);
		return responseList;
	}
	
	@RequestMapping(value = "/getVendorInvoiceAssociatesListForJobClosure", method = RequestMethod.GET)
	public @ResponseBody ResponseList<RequirementSubmissionsDTO> getVendorInvoiceAssociatesList(@RequestParam(value = "selectedAssociates", required = false) String selectedAssociates, 
			@RequestParam(value = "vendorOrgRelationId", required = false) Long vendorOrgRelationId,
			@RequestParam(value = "contractType", required = false) String contractType,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<RequirementSubmissionsDTO> responseList = new ResponseList<>();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<RequirementSubmissionsDTO> applicantsList = requirementSubmissionService.getCandidateForBillsJobClosure(loginOrgLocation, user, vendorOrgRelationId,contractType,selectedAssociates);
		
		responseList.setData(applicantsList);
		return responseList;
	}
	
	@RequestMapping(value = "/getClientProjectsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody ResponseList<Projects> getUserList(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<Projects> responseList = new ResponseList<>();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Projects> projectsList = projectsService.getClientProjectsByOrgRelationId(orgRelationId, loginOrgLocation.getId());
		// resultJson = commonUtil.toJSON(projectsList);
		responseList.setData(projectsList);
		return responseList;
	}
	
	
@RequestMapping(value = { "/getCreDitNotesList","/rest/getCreDitNotesList" }, method = RequestMethod.POST)
public ResponseList<CreditDebitNoteListDTO> getCreDitNotesList(CreditDebitNoteDTO creditDebitNoteDTO, HttpServletRequest request) throws Exception
{

OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(creditDebitNoteDTO.getOrglocationId()));
String[] tableColumns = { "","date", "id", "referenceNumber", "clientName","invoiceId", "total", "balance", "status"};

int sortColumnIndex = 1; // by default sorting using invoice date
String sortColumnOrder = "asc"; // by default descending order
if (creditDebitNoteDTO.getOrder() != null && !creditDebitNoteDTO.getOrder().isEmpty()) {
	sortColumnIndex = Integer.parseInt(creditDebitNoteDTO.getOrder().get(0).get("column"));
	sortColumnOrder = creditDebitNoteDTO.getOrder().get(0).get("dir");
}
creditDebitNoteDTO.setSortColumn(tableColumns[sortColumnIndex]);
creditDebitNoteDTO.setSortOrder(sortColumnOrder);

Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
DateTime zoned = new DateTime(commonUtil.orgLocationCommonDate(new Date(), orgLocations)); //get date and time based on orglocation
DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date endDateUTC = null;
Date startDateUTC = null;
if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(creditDebitNoteDTO.getDayWiseType())	&& !"".equals(creditDebitNoteDTO.getDayWiseType())) {
	String endDate = outputFormatter1.print(zoned);
	endDateUTC = TZ_DF_TS.parse(endDate);
}
if (null != creditDebitNoteDTO.getDayWiseType() && !"".equals(creditDebitNoteDTO.getDayWiseType())) {
	if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(creditDebitNoteDTO.getDayWiseType())) {
		String startDate = outputFormatter.print(zoned);
		// String startDateString =
		// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
		// Constants.UTC_TIMEZONE, startDate);
		startDateUTC = TZ_DF_TS.parse(startDate);
		if (Constants.ONE_MONTH.equals(creditDebitNoteDTO.getDayWiseType())) {
			startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
		}
		else if (Constants.TWO_MONTHS.equals(creditDebitNoteDTO.getDayWiseType())) {
			startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
		}
		else if (Constants.THREE_MONTHS.equals(creditDebitNoteDTO.getDayWiseType())) {
			startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 90);
		}
		else if (Constants.SIX_MONTHS.equals(creditDebitNoteDTO.getDayWiseType())) {
			startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 180);
		}
		else if (Constants.ONE_YEAR.equals(creditDebitNoteDTO.getDayWiseType())) {
			startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 365);
		}
	}else {
		if (StringUtils.isNotBlank(creditDebitNoteDTO.getStartDate())
				&& StringUtils.isNotBlank(creditDebitNoteDTO.getEndDate())
				&& !"undefined".equalsIgnoreCase(creditDebitNoteDTO.getStartDate())
				&& !"undefined".equalsIgnoreCase(creditDebitNoteDTO.getEndDate())) {
			/*DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd");*/
			Date startTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), creditDebitNoteDTO.getStartDate());
			Date endTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), creditDebitNoteDTO.getEndDate());
			DateTime zoned1 = new DateTime(commonUtil.orgLocationCommonDate(startTime, orgLocations)); //get date and time based on orglocation
			DateTime zoned2 = new DateTime(commonUtil.orgLocationCommonDate(endTime, orgLocations)); //get date and time based on orglocation
			
			String customStartTimeStr = outputFormatter.print(zoned1);
			String customEndTimeStr = outputFormatter1.print(zoned2);
			startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
			endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
		}
		else {
			startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
		}
	}
	
}

ResponseList<CreditDebitNoteListDTO> responseList = invoiceService. creditDebtListByOrgLocationId(orgLocations, startDateUTC,endDateUTC,creditDebitNoteDTO);

return responseList;
}
	
	@RequestMapping(value = "/getSowcontractListByAssociateId", method = RequestMethod.GET)
	public @ResponseBody ResponseList<Contracts> getSowcontractListByAssociateId(@RequestParam(value = "candidateId", required = false) Long candidateId, @RequestParam(value = "orgRelationId", required = false) Long orgRelationId, @RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request) throws Exception
	{
		ResponseList<Contracts> responseList = new ResponseList<>();
//		String resultJson = "";
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Contracts> contractsList = null;
		if (null != orgRelationId) {
			if (contractType.equalsIgnoreCase("vendorSow")) {
				contractsList = invoiceService.getSowcontractListByAssociateId(candidateId, loginOrgLocation.getId(), orgRelationId, Constants.Vendor_SOW);
			}
			else {
				contractsList = invoiceService.getSowcontractListByAssociateId(candidateId, loginOrgLocation.getId(), orgRelationId, Constants.Client_SOW);
			}
			if (null != contractsList && contractsList.size() > 0) {
//				resultJson = commonUtil.toJSON(contractsList);
				responseList.setData(contractsList);
			}
		}
		return responseList;
	}
	
	@RequestMapping(value = { "/getInvoiceList",
			"/rest/getInvoiceList" }, method = RequestMethod.POST)
	public ResponseList<BillingDTO> getInvoiceList(InvoiceDTO invoiceDTO, HttpServletRequest request) throws Exception
	{
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(invoiceDTO.getOrgLocationId()));
		ResponseList<BillingDTO> responseList = new ResponseList<>();
		String[] tableColumns = { "invoiceId", "orgRelationID", "contactUser", "projectID",
				"invoiceDate", "total", "dueAmount", "dueDate", "invoiceStatus", "paymentStatus",
				"" };
		int sortColumnIndex = 1; // by default sorting using invoice date
		String sortColumnOrder = "asc"; // by default descending order
		if (invoiceDTO.getOrder() != null && !invoiceDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(invoiceDTO.getOrder().get(0).get("column"));
			sortColumnOrder = invoiceDTO.getOrder().get(0).get("dir");
		}
		invoiceDTO.setSortColumn(tableColumns[sortColumnIndex]);
		invoiceDTO.setSortOrder(sortColumnOrder);
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime();
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd");
		Date endDateUTC = null;
		Date startDateUTC = null;
		if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(invoiceDTO.getDayWisetype())
				&& !"".equals(invoiceDTO.getDayWisetype())) {
			String endDate = outputFormatter1.print(zoned);
			// String endDateString =
			// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
			// Constants.UTC_TIMEZONE, endDate);
			endDateUTC = TZ_DF_TS.parse(endDate);
		}
		if (null != invoiceDTO.getDayWisetype() && !"".equals(invoiceDTO.getDayWisetype())) {
			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(invoiceDTO.getDayWisetype())) {
				String startDate = outputFormatter.print(zoned);
				// String startDateString =
				// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
				// Constants.UTC_TIMEZONE, startDate);
				startDateUTC = TZ_DF_TS.parse(startDate);
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
			else {
				if (StringUtils.isNotBlank(invoiceDTO.getStartDate())
						&& StringUtils.isNotBlank(invoiceDTO.getEndDate())
						&& !"undefined".equalsIgnoreCase(invoiceDTO.getStartDate())
						&& !"undefined".equalsIgnoreCase(invoiceDTO.getEndDate())) {
					DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd");
					DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd");
					Date startTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), invoiceDTO.getStartDate());
					Date endTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), invoiceDTO.getEndDate());
					String customStartTimeStr = TZ_DF_CSD.format(startTime);
					String customEndTimeStr = TZ_DF_CED.format(endTime);
					// String startDateString =
					// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
					// Constants.UTC_TIMEZONE, customStartTimeStr);
					// String endDateString =
					// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
					// Constants.UTC_TIMEZONE, customEndTimeStr);
					startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
					endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
				}
				else {
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}
			}
		}
		
		ResponseList<Invoice> invoiceList = invoiceService.invoiceListByOrgLocationId(orgLocations, startDateUTC, endDateUTC, invoiceDTO);
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
		List<BillingDTO> billingDtoList = new ArrayList<>();
		for (Invoice invoice : invoiceFilterList) {
			BillingDTO billingDto = new BillingDTO();
			boolean isInvoiceDueDate = false;
                        if(invoice.getPaymentAccount() != null) {
				invoice.setPaymentAccountDTO(orgService.getPaymentAccount(invoice.getPaymentAccount().getId(), null));
			}
			OrgRelations orgRelations = orgRelationsService.getOrgRelationById(invoice.getOrgRelationID().getId());
			if (orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()) {
				billingDto.setClientName(orgRelations.getResponderLocationId().getOrgLocationName());
				billingDto.setOrgType(orgRelations.getResponderLocationId().getOrg().getOrgType());
			}
			if (orgRelations.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()) {
				billingDto.setClientName(orgRelations.getLocationId().getOrgLocationName());
				billingDto.setOrgType(orgRelations.getLocationId().getOrg().getOrgType());
			}
			if (null != invoice.getProjectID()) {
				billingDto.setProjectName(invoice.getProjectID().getProjectName());
			}
			if (orgRelations != null) {
				billingDto.setOrgRelationUUID(orgRelations.getUUID());
			}
			//StringBuilder contactName = new StringBuilder();
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
					
					 billingDto.setClientContactName(contactUserName.toString().substring(0, contactUserName.toString().length() - 2));
				}
			}
			/*if (null != invoice.getContactUser()) {
				if (invoice.getContactUser().getLastName() != null) {
					billingDto.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());
				}
				else {
					billingDto.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).toString());
				}
			}*/
			if (null != invoice.getDueDate()) {
				billingDto.setDueDateStr(dateFormat.format(invoice.getDueDate()));
			}
			if (null != invoice.getInvoiceUUID()) {
				billingDto.setInvoiceUUID(invoice.getInvoiceUUID());
			}
			if (null != invoice.getInvoiceId()) {
				billingDto.setInvoiceId(invoice.getInvoiceId());
			}
			if (null != invoice.getId()) {
				billingDto.setId(invoice.getId());
			}
			if (null != invoice.getInvoiceParentId()) {
				billingDto.setInvoiceParentId(invoice.getInvoiceParentId());
			}
			if (null != invoice.getFileUrl()) {
				billingDto.setInvoiceParentId(invoice.getInvoiceParentId());
			}
			if (null != invoice.getInvoiceDate()) {
				billingDto.setInvoiceDateStr(dateFormat.format(invoice.getInvoiceDate()));
			}
			if (null != invoice.getInvoiceType()) {
				billingDto.setInvoiceType(invoice.getInvoiceType());
			}
			if (null != invoice.getRejectReason()) {
				billingDto.setRejectReason(invoice.getRejectReason());
			}
			if(invoice.getParentInvoiceId() != null) {
				if(invoice.getParentInvoiceId().getPaymentAccount() != null) {
					billingDto.setPaymentAccountDTO(orgService.getPaymentAccount(invoice.getParentInvoiceId().getPaymentAccount().getId(), null));
				}
			}
			billingDto.setInvoiceUUID(invoice.getInvoiceUUID());
			billingDto.setTotal(String.format("%.3f", Float.valueOf(invoice.getTotal())));
			billingDto.setDueAmount(String.format("%.3f", Float.valueOf(invoice.getDueAmount())));
			billingDto.setInvoiceStatus(invoice.getInvoiceStatus());
			billingDto.setPaymentStatus(invoice.getPaymentStatus());
			if (null != invoice.getProjectID()) {
				billingDto.setProjectId(invoice.getProjectID().getId().toString());
			}
			if (!isNullOrEmpty(invoice.getInvoiceStatus())) {
				if (Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
						|| Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())) {
					draftCount = draftCount + 1;
					draftTotalAmount = draftTotalAmount + Float.parseFloat(invoice.getDueAmount());
					billingDto.setDraftInvoiceAmount(String.format("%.3f", draftTotalAmount));
				}
				if (!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
						&& !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())
						&& (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
								|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
								|| Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))
						&& !Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())) {
					paidCount = paidCount + 1;
					Float paidAmount = Float.parseFloat(invoice.getTotal())
							- Float.parseFloat(invoice.getDueAmount());
					paidTotalAmount = paidTotalAmount + paidAmount;
					billingDto.setPaidInvoiceAmount(String.format("%.3f", paidTotalAmount));
				}
				if (!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
						&& !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())
						&& (Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())
								|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
								|| Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))) {
					
					String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), invoice.getDueDate());
					Date invoiceDueDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), dueDate);
					Float dueAmount = Float.parseFloat(invoice.getDueAmount());
					if (dueAmount > 0) {
						
						if (todayDate.after(invoiceDueDate)) {
							overDueInvoiceCount = overDueInvoiceCount + 1;
							dueAfterInvoiceAmount = dueAfterInvoiceAmount
									+ Float.parseFloat(invoice.getDueAmount());
							billingDto.setDueAfterInvoiceAmount(String.format("%.3f", dueAfterInvoiceAmount));
							isInvoiceDueDate = true;
						}
						else {
							openInvoiceCount = openInvoiceCount + 1;
							dueBeforeInvoiceAmount = dueBeforeInvoiceAmount
									+ Float.parseFloat(invoice.getDueAmount());
							billingDto.setDueBeforeInvoiceAmount(String.format("%.3f", dueBeforeInvoiceAmount));
						}
					}
				}
			}
			if (null != invoice.getParentInvoiceId()) {
				billingDto.setInvoiceParentId(invoice.getParentInvoiceId().getId());
			}
			if (null != invoice.getFileUrl()) {
				billingDto.setFileUrl(invoice.getFileUrl());
			}
			billingDto.setDraftInvoiceCount(draftCount);
			billingDto.setPaidInvoiceCount(paidCount);
			billingDto.setDueAfterInvoiceCount(overDueInvoiceCount);
			billingDto.setDueBeforeInvoiceCount(openInvoiceCount);
			billingDtoList.add(billingDto);
		}
		responseList.setRecordsFiltered(invoiceList.getRecordsFiltered());
		responseList.setRecordsTotal(invoiceList.getRecordsTotal());
		responseList.setData(billingDtoList);
		return responseList;
	}
	
	@RequestMapping(value = "/saveInvoicePayment", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveInvoicePayment(InvoicePaymentDTO invoicePaymentDTO, @RequestParam(value = "invoiceId", required = false) Long invoiceId, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(invoicePaymentDTO)) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				invoicePaymentDTO.setStaticPath(staticPath);
				InvoicePayment invoicePayment = invoicePaymentService.saveInvoicePayment(invoicePaymentDTO, user, orgLocation, null, invoiceId);
				if (null != invoicePayment) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			}
			else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
 	
	@RequestMapping(value = "/saveInvoiceBulkPayment", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveInvoiceBulkPayment(InvoicePaymentDTO invoicePaymentDTO,  @RequestParam(value = "invoiceIds", required = false) List<String> invoiceIds, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
	     HashMap<Long, String> invoiceMap = new HashMap<Long, String>();
	     for(String obj: invoiceIds){
	    	 String [] arr = obj.split("_");
	    	 invoiceMap.put(Long.parseLong(arr[0]),(arr[1]));
	     }
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(invoicePaymentDTO)) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				invoicePaymentDTO.setStaticPath(staticPath);
				InvoicePayment invoicePayment = invoicePaymentService.saveInvoiceBulkPayment(invoicePaymentDTO, user, orgLocation,invoiceMap);
				if (null != invoicePayment) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			}
			else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = { "/getInvoiceListForPayment"}, method = RequestMethod.POST)
	public ResponseList<BillingDTO> getInvoiceListForPayment(InvoiceDTO invoiceDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<BillingDTO> responseList = new ResponseList<>();
		List<BillingDTO> billingDtoList = new ArrayList<>();
		if(StringUtils.isNotBlank(invoiceDTO.getOrgRelationId())){
		ResponseList<Invoice> invoiceList = invoiceService.invoiceListByOrgLocationIdAndRelationId(invoiceDTO,loginOrgLocation,Long.parseLong(invoiceDTO.getOrgRelationId()));
		List<Invoice> invoiceFilterList = invoiceList.getData();
		SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		Integer draftCount = 0;
		Integer paidCount = 0;
		Float draftTotalAmount = 0f;
		Float paidTotalAmount = 0f;
		for (Invoice invoice : invoiceFilterList) {
		BillingDTO billingDto = new BillingDTO();
                    if(invoice.getPaymentAccount() != null) {
			invoice.setPaymentAccountDTO(orgService.getPaymentAccount(invoice.getPaymentAccount().getId(), null));
		}
		OrgRelations orgRelations = orgRelationsService.getOrgRelationById(invoice.getOrgRelationID().getId());
		if (orgRelations.getLocationId().getId().longValue() == loginOrgLocation.getId().longValue()) {
			billingDto.setClientName(orgRelations.getResponderLocationId().getOrgLocationName());
		}
		if (orgRelations.getResponderLocationId().getId().longValue() == loginOrgLocation.getId().longValue()) {
			billingDto.setClientName(orgRelations.getLocationId().getOrgLocationName());
		}
		if (null != invoice.getProjectID()) {
			billingDto.setProjectName(invoice.getProjectID().getProjectName());
		}
		if (orgRelations != null) {
			billingDto.setOrgRelationUUID(orgRelations.getUUID());
		}
		StringBuilder contactName = new StringBuilder();
		if (null != invoice.getContactUser()) {
			if (invoice.getContactUser().getLastName() != null) {
				billingDto.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());
			}
			else {
				billingDto.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).toString());
			}
		}
		if (null != invoice.getDueDate()) {
			billingDto.setDueDateStr(dateFormat.format(invoice.getDueDate()));
		}
		if (null != invoice.getInvoiceUUID()) {
			billingDto.setInvoiceUUID(invoice.getInvoiceUUID());
		}
		if (null != invoice.getInvoiceId()) {
			billingDto.setInvoiceId(invoice.getInvoiceId());
		}
		if (null != invoice.getId()) {
			billingDto.setId(invoice.getId());
		}
		if (null != invoice.getInvoiceParentId()) {
			billingDto.setInvoiceParentId(invoice.getInvoiceParentId());
		}
		if (null != invoice.getFileUrl()) {
			billingDto.setInvoiceParentId(invoice.getInvoiceParentId());
		}
		if (null != invoice.getInvoiceDate()) {
			billingDto.setInvoiceDateStr(dateFormat.format(invoice.getInvoiceDate()));
		}
		if (null != invoice.getInvoiceType()) {
			billingDto.setInvoiceType(invoice.getInvoiceType());
		}
		if (null != invoice.getRejectReason()) {
			billingDto.setRejectReason(invoice.getRejectReason());
		}
		if(invoice.getParentInvoiceId() != null) {
			if(invoice.getParentInvoiceId().getPaymentAccount() != null) {
				billingDto.setPaymentAccountDTO(orgService.getPaymentAccount(invoice.getParentInvoiceId().getPaymentAccount().getId(), null));
			}
		}
		billingDto.setInvoiceUUID(invoice.getInvoiceUUID());
		billingDto.setTotal(String.format("%.3f", Float.valueOf(invoice.getTotal())));
		billingDto.setDueAmount(String.format("%.3f", Float.valueOf(invoice.getDueAmount())));
		billingDto.setInvoiceStatus(invoice.getInvoiceStatus());
		billingDto.setPaymentStatus(invoice.getPaymentStatus());
		if (null != invoice.getProjectID()) {
			billingDto.setProjectId(invoice.getProjectID().getId().toString());
		}
		if (!isNullOrEmpty(invoice.getInvoiceStatus())) {
			if (Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
					|| Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())) {
				draftCount = draftCount + 1;
				draftTotalAmount = draftTotalAmount + Float.parseFloat(invoice.getDueAmount());
				billingDto.setDraftInvoiceAmount(String.format("%.3f", draftTotalAmount));
			}
			if (!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())
					&& !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())
					&& (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
							|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
							|| Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))
					&& !Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())) {
				paidCount = paidCount + 1;
				Float paidAmount = Float.parseFloat(invoice.getTotal())
						- Float.parseFloat(invoice.getDueAmount());
				paidTotalAmount = paidTotalAmount + paidAmount;
				billingDto.setPaidInvoiceAmount(String.format("%.3f", paidTotalAmount));
			}
			
		}
		if (null != invoice.getParentInvoiceId()) {
			billingDto.setInvoiceParentId(invoice.getParentInvoiceId().getId());
		}
		if (null != invoice.getFileUrl()) {
			billingDto.setFileUrl(invoice.getFileUrl());
		}
		
		billingDtoList.add(billingDto);
		}
	}
		responseList.setData(billingDtoList);
		return responseList;
		
	}
	
	@RequestMapping(value = "/getCreditDebitNoteDetails", method = RequestMethod.GET)
	public @ResponseBody Response<CreditDebitNote> getCreditDebitNoteDetails(@RequestParam(value = "creditDebitUUID", required = false) String creditDebitUUID, HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		CreditDebitNote creditDebitNote = invoiceService.getCreditDebitNoteByUUID(creditDebitUUID);
		Response<CreditDebitNote> response = invoiceService.getCreditDebitNoteByIdAndOrglocationId(creditDebitNote.getId(), loginOrgLocation.getId());
		
	return response;	
	}
	
	@RequestMapping(value = "/getViewInvoice", method = RequestMethod.GET)
	public @ResponseBody ResponseList<Invoice> getViewInvoice(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<Invoice> responseList = new ResponseList<>();
//		String resultJson;
		OrgLocations orgLocations = null;
		Invoice invoiceId = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Long Id = null;
		Invoice parentInvoice = invoiceService.getInvoicetByParentInvoiceId(invoiceId.getId());
		if (parentInvoice.getParentInvoiceId() != null) {
			Id = parentInvoice.getParentInvoiceId().getId();
			orgLocations = parentInvoice.getParentInvoiceId().getOrgLocation();
		}
		else {
			Id = invoiceId.getId();
			orgLocations = orgLocations;
		}
		SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
		List<Invoice> viewinvoiceList = invoiceService.getViewInvoiceListByInvoiceIdAndOrglocationId(Id, orgLocations.getId());
		for (Invoice invoice : viewinvoiceList) {
			OrgLocations LoginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			//User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (invoice.getOrgRelationID().getLocationId().getId().equals(LoginOrgLocations.getId())) {
				invoice.setClientName(invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName());
				invoice.setOrgType(invoice.getOrgRelationID().getResponderLocationId().getOrg().getOrgType());
				//StringBuilder contactName = new StringBuilder();
				if(null != invoice.getContactUsers()){
					String[] contactUsers = invoice.getContactUsers().split(",");
					if(contactUsers.length != 0){
						StringBuilder contactUserName = new StringBuilder();
						StringBuilder contactUserEmail = new StringBuilder();
						for(String contactUserId : contactUsers){
							User contactUser = userService.getUserById(Long.parseLong(contactUserId.trim()));
							if(contactUser.getLastName() != null){
								contactUserName = contactUserName.append(contactUser.getFirstName()).append(" ").append(contactUser.getLastName()).append("- ").append(contactUser.getEmail()).append(", ").append("\n ");
							}else{
								contactUserName = contactUserName.append(contactUser.getFirstName()).append("- ").append(contactUser.getEmail()).append(", ").append("\n ");
							}
							if(contactUser.getEmail() != null){
								contactUserEmail = contactUserEmail.append(contactUser.getEmail()).append(", ");
							}
						}
						invoice.setClientContactEmail(contactUserEmail.toString().substring(0, contactUserEmail.toString().length() - 2));
						invoice.setClientContactName(contactUserName.toString().substring(0, contactUserName.toString().length() - 2).replaceAll("\n ", "<br/>"));
					}
				}
				/*if (invoice.getContactUser() != null) {.
					if (invoice.getContactUser().getLastName() != null) {
						invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString());
					}
					else {
						invoice.setClientContactName(contactName.append(invoice.getContactUser().getFirstName()).toString());
					}
				}
				if (null != invoice.getContactUser()) {
					invoice.setClientContactEmail(invoice.getContactUser().getEmail());
				}*/
				if (invoice.getOrgRelationID() != null) {
					invoice.setOrgRelationUUID(invoice.getOrgRelationID().getUUID());
				}
			}
		    	StringBuilder contactName = new StringBuilder();
				if(invoice.getOrgRelationID().getResponderLocationId().getId().equals(LoginOrgLocations.getId())){
					invoice.setClientName(invoice.getOrgRelationID().getLocationId().getOrgLocationName());
					invoice.setOrgType(invoice.getOrgRelationID().getLocationId().getOrg().getOrgType());
					if(null != invoice.getOwnerUsers()){
						String[] ownerUsers = invoice.getOwnerUsers().split(",");
						if(ownerUsers.length != 0){
							StringBuilder ownerUserName = new StringBuilder();
							StringBuilder ownerUserEmail = new StringBuilder();
							for(String ownerUserId : ownerUsers){
								User ownerUser = userService.getUserById(Long.parseLong(ownerUserId.trim()));
								if(ownerUser.getLastName() != null){
									ownerUserName = ownerUserName.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName()).append("- ").append(ownerUser.getEmail()).append(", ").append("\n");
								}else{
									ownerUserName = ownerUserName.append(ownerUser.getFirstName()).append("- ").append(ownerUser.getEmail()).append(", ").append("\n");
								}
								if(ownerUser.getEmail() != null){
									ownerUserEmail = ownerUserEmail.append(ownerUser.getEmail()).append(", ");
								}
							}
							invoice.setClientContactEmail(ownerUserEmail.toString().substring(0, ownerUserEmail.toString().length() - 2));
							invoice.setClientContactName(ownerUserName.toString().substring(0, ownerUserName.toString().length() - 2));
						}
					}
					/*if(invoice.getContactUser().getLastName() != null){
						invoice.setClientContactName(contactName.append(invoice.getOwnerUserId().getFirstName()).append(" ").append(invoice.getOwnerUserId().getLastName()).toString());	
					}else{
						invoice.setClientContactName(contactName.append(invoice.getOwnerUserId().getFirstName()).toString());
					}
					if(null != invoice.getContactUser()){
						invoice.setClientContactEmail(invoice.getOwnerUserId().getEmail());	
					}*/
					if(invoice.getOrgRelationID() != null){
						invoice.setOrgRelationUUID(invoice.getOrgRelationID().getUUID());
						}
				}
				/*else {
					invoice.setClientContactName(contactName.append(invoice.getOwnerUserId().getFirstName()).toString());
				}
				if (null != invoice.getContactUser()) {
					invoice.setClientContactEmail(invoice.getOwnerUserId().getEmail());
				}*/
			
			invoice.setExchangeRate(invoice.getExchangeRate());
			if (invoice.getConvertAmount() != null) {
				invoice.setConvertAmount(String.format("%.3f", Float.valueOf(invoice.getConvertAmount())));
			}
			invoice.setConvertCurrencyCode(invoice.getConvertCurrencyCode());
			invoice.setCurrencyCode(invoice.getCurrencyCode());
			invoice.setDueDateStr(formatter.format(invoice.getDueDate()));
			invoice.setInvoiceDateStr(formatter.format(invoice.getInvoiceDate()));
			if(invoice.getNotifyToOthers() !=null){
				invoice.setNotifyToOthers(invoice.getNotifyToOthers().replaceAll(",", ", "));
				}
			if(invoice.getPaymentAccount() != null) {
				invoice.setPaymentAccountDTO(orgService.getPaymentAccount(invoice.getPaymentAccount().getId(), null));
			}
			// get the Invoice Items List
			List<InvoiceLineItems> invoiceLineItemsList = invoiceLineItemService.invoiceLineItemsByInvoiceIdAndOrglocationId(Id, orgLocations.getId());
			for (InvoiceLineItems inviceLine : invoiceLineItemsList) {
				if (null != inviceLine.getCandidate()) {
					StringBuilder can = new StringBuilder();
					Candidate candi = inviceLine.getCandidate();
					if (candi.getLastName() != null) {
						inviceLine.setCandidateName(can.append(candi.getFirstName()).append(" ").append(candi.getLastName()).toString());
					}
					else {
						inviceLine.setCandidateName(can.append(candi.getFirstName()).toString());
					}
				}
				if (null != inviceLine.getContract()) {
					inviceLine.setSowName(inviceLine.getContract().getContractTitle());
				}
				if(inviceLine.getRequirementSubmissionId() !=null){
					inviceLine.setRequirementName(inviceLine.getRequirementSubmissionId().getRequirement().getJobTitle());
				}
				inviceLine.setRate(inviceLine.getRate());
				inviceLine.setInlineDescription(inviceLine.getInlineDescription());
				// inviceLine.setEmployeeId(inviceLine.getEmployeeId());
				inviceLine.setTotalHours(inviceLine.getTotalHours());
				inviceLine.setAmount(inviceLine.getAmount());
				if (null != inviceLine.getStartDate()) {
					inviceLine.setStartDateStr(formatter.format(inviceLine.getStartDate()));
				}
				if (null != inviceLine.getEndDate()) {
					inviceLine.setEndDateStr(formatter.format(inviceLine.getEndDate()));
				}
				if (null != inviceLine.getCandidate()) {
					if (null != inviceLine.getCandidate().getConsultantUser()) {
						Employees employeeId = employeesService.getEmployeeByUserId(inviceLine.getCandidate().getConsultantUser().getId());
						inviceLine.setEmployeeId(employeeId.getEmployeeId());
					}
				}
				invoice.setInvoiceLineItemsList(invoiceLineItemsList);
			}
			// get the InvoiceDocument list
			List<InvoiceDocument> invoiceDocumentList = invoiceDocumentsService.getInvoiceDocumentsListByInvoiceId(Id);
			for (InvoiceDocument invoiceDoc : invoiceDocumentList) {
				invoiceDoc.setFileName(invoiceDoc.getFileName());
				invoice.setInvoiceDocumentsList(invoiceDocumentList);
			}
			List<InvoiceTax> InvoiceTaxList = invoiceTaxService.getInvoiceTaxListByInvoiceId(Id);
			for (InvoiceTax it : InvoiceTaxList) {
				it.setTaxName(it.getTaxName());
				it.setTaxType(it.getTaxType());
				it.setTaxValue(it.getTaxValue());
				it.setTaxAmount(it.getTaxAmount());
				invoice.setInvoiceTaxList(InvoiceTaxList);
			}
		}
//		resultJson = commonUtil.toJSON(viewinvoiceList);
		responseList.setData(viewinvoiceList);
		return responseList;
	}
	
	@RequestMapping(value = "/getViewPaymentHistory", method = RequestMethod.GET)
	public @ResponseBody ResponseList<InvoicePayment> getViewPaymentHistory(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, @RequestParam(value = "locationId", required = false) Long locationId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<InvoicePayment> responseList = new ResponseList<>();
		String resultJson;
		Long Id = null;
		OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
		Invoice invoiceId = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		Invoice invoice = invoiceService.getInvoicetByParentInvoiceId(invoiceId.getId());
		if (invoice.getParentInvoiceId() != null) {
			Id = invoice.getParentInvoiceId().getId();
		}
		else {
			Id = invoiceId.getId();
		}
		List<InvoicePayment> invoicePaymentList = invoicePaymentService.getInvoicePaymentList(Id);
		for (InvoicePayment ip : invoicePaymentList) {
			User user = userService.getUserByUserCode(ip.getCreatedBy());
			ip.setPaymentDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), ip.getPaymentDate()));
			ip.setPaymentMethod(ip.getPaymentMethod());
			ip.setPaymentReferenceNumber(ip.getPaymentReferenceNumber());
			ip.setFileURL(ip.getFileURL());
			ip.setFileName(ip.getFileName());
			ip.setFileContentType(ip.getFileContentType());
			ip.setPaymentComment(ip.getPaymentComment());
			ip.setPaymentAmount(ip.getPaymentAmount());
			StringBuilder name = new StringBuilder();
			if (null != user.getLastName()) {
				ip.setCreatedByName(name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			}
			else {
				ip.setCreatedByName(name.append(user.getFirstName()).toString());
			}
			ip.setCurrencyCode(ip.getCurrencyCode());
		}
//		resultJson = commonUtil.toJSON(invoicePaymentList);
		responseList.setData(invoicePaymentList);
		return responseList;
	}
	
	/*
	 * @RequestMapping(value = "/downloadInvoicePaymentDocument", method =
	 * RequestMethod.GET) public void
	 * downloadInvoicePaymentDocument(@RequestParam(value="paymentHistoryId",
	 * required=false) Long paymentHistoryId ,HttpServletResponse response)
	 * throws Exception { InvoicePayment payment
	 * =invoicePaymentService.invoicePaymentById(paymentHistoryId);
	 * response.setContentType("application/octet-stream"); StringBuilder
	 * fileName = new StringBuilder(); response.setHeader("Content-Disposition",
	 * fileName.append("attachment; filename=\""
	 * ).append(payment.getFileName()).append("\"").toString()); InputStream
	 * inputStream = new ByteArrayInputStream(payment.getDocFile()); try {
	 * OutputStream outStream = response.getOutputStream();
	 * 
	 * byte[] buffer = new byte[4096]; int bytesRead = -1;
	 * 
	 * while ((bytesRead = inputStream.read(buffer)) != -1) {
	 * outStream.write(buffer, 0, bytesRead); }
	 * 
	 * inputStream.close(); outStream.close();
	 * 
	 * } catch (FileNotFoundException e) {
	 * logger.error(ExceptionUtils.getFullStackTrace(e)); }
	 * 
	 * }
	 */
	@RequestMapping(value = "/getProjectFrequency", method = RequestMethod.GET)
	public @ResponseBody ResponseList<String> getProjectFrequency(@RequestParam(value = "projectId", required = false) Long projectId, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<String> responseList = new ResponseList<>();
		Projects projects = projectsService.getProjectById(projectId);
		List<String> frequencyDates = new ArrayList<>();
		Map<String, String> map = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projects,orgLocation);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			StringBuffer sb = new StringBuffer();
			sb.append(entry.getKey().replaceAll("/", "-")).append("/").append(entry.getValue().replaceAll("/", "-"));
			frequencyDates.add(sb.toString());
		}
//		return commonUtil.toJSON(frequencyDates);
		responseList.setData(frequencyDates);
		return responseList;
	}
	
	@RequestMapping(value = "/getAllProjectAssignedMembers", method = RequestMethod.GET)
	public @ResponseBody ResponseList<ProjectMembers> getAllProjectAssignedMembers(@RequestParam(value = "projectId", required = false) String projectId, @RequestParam(value = "frequency", required = false) String frequency, @RequestParam(value = "orgRelationId", required = true) Long orgRelationId, @RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request) throws Exception
	{
		ResponseList<ProjectMembers> responseList = new ResponseList<>();
//		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrgRelations orgRelations = orgRelationsService.getOrRelationsById(orgRelationId);
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		// String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> projectMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		if (null != projectId && !"".equalsIgnoreCase(projectId)) {
			sahList = submissionApproverHierarchyService.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long.parseLong(projectId), orgLocation.getId(), startDate, endDate, contractType);
		}
		if (null != sahList && sahList.size() > 0) {
			for (SubmissionApproverHierarchy sah : sahList) {
				String status = sah.getSubmissionStatus().getValue();
				if (!status.equalsIgnoreCase("APPROVED")) {
					continue;
				}
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
				// Candidate candidate =
				// candidateService.getCandidateByParentCandidateId(projectMember.getProjectMemberCandidteId().getId());
				Candidate candidate = projectMember.getProjectMemberCandidteId();
				if (candidate != null) {
					projectMember.setSubmissionApproverHierarchyId(sah.getId());
					StringBuilder candidateName = new StringBuilder();
					if (null != projectMember.getProjectMemberCandidteId().getFirstName()
							&& null != projectMember.getProjectMemberCandidteId().getLastName()) {
						candidateName.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ").append(projectMember.getProjectMemberCandidteId().getLastName());
					}
					else {
						candidateName.append(projectMember.getProjectMemberCandidteId().getFirstName());
					}
					projectMember.setCandidateName(candidateName.toString());
					projectMember.setUserId(String.valueOf(projectMember.getProjectMemberCandidteId().getId()));
					if (null != projectMember.getProjectMemberCandidteId().getId()) {
						// Candidate candidate =
						// candidateService.getCandidateByConsultantUserId(projectMember.getEmployee().getUser().getId(),
						// orgLocation.getId());
						if (null != projectMember.getProjectMemberCandidteId()) {
							projectMember.setCandidateId(projectMember.getProjectMemberCandidteId().getId());
							List<Contracts> contractList = getSowContract(projectMember.getProjectMemberCandidteId().getId(), orgLocation, orgRelationId, contractType);
							if (null != contractList && contractList.size() > 0) {
								projectMember.setContractsList(contractList);
							}
						}
					}
					projectMember.setStartDate(startDateStr);
					projectMember.setEndDate(endDateStr);
					StringBuilder sb = new StringBuilder();
					if (null != sah.getBillableHours()) {
						String billableHours = String.valueOf(sah.getBillableHours());;
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
					}
					else {
						sb.append("0");
					}
					projectMember.setTotalHours(sb.toString());
					projectMembersList.add(projectMember);
				}
			}
			if (projectMembersList.size() > 0) {
				if(orgRelations.getGenerateInvoice() !=null && orgRelations.getGenerateInvoice().equalsIgnoreCase(Constants.FINANCE_TYPE_INDIVIDUAL)){
					List<ProjectMembers> projectMembersList1 = new ArrayList<>();
					projectMembersList1.add(projectMembersList.get(0));
					responseList.setData(projectMembersList1);	
				}else{
					responseList.setData(projectMembersList);	
				}
//				resultJson = commonUtil.toJSON(projectMembersList);
				
			}
		}
		return responseList;
	}
	
	private List<Contracts> getSowContract(Long candidateId, OrgLocations orgLocation, Long orgRelationId, String contractType) throws Exception
	{
		List<Contracts> contractsList = null;
		if (null != orgRelationId) {
			if (contractType.equalsIgnoreCase("vendorSow")) {
				contractsList = invoiceService.getSowcontractListByAssociateId(candidateId, orgLocation.getId(), orgRelationId, Constants.Vendor_SOW);
			}
			else {
				contractsList = invoiceService.getSowcontractListByAssociateId(candidateId, orgLocation.getId(), orgRelationId, Constants.Client_SOW);
			}
		}
		return contractsList;
	}
	
	@RequestMapping(value = "/getAllProjectVendorAssignedMembers", method = RequestMethod.GET)
	public @ResponseBody ResponseList<ProjectMembers> getAllProjectVendorAssignedMembers(@RequestParam(value = "projectId", required = false) String projectId, @RequestParam(value = "frequency", required = false) String frequency, @RequestParam(value = "orgRelationId", required = true) Long orgRelationId, @RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request) throws Exception
	{
		ResponseList<ProjectMembers> responseList = new ResponseList<>();
//		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		OrgRelations orgRelations = orgRelationsService.getOrRelationsById(orgRelationId);
		String frequencyArray[] = frequency.split("/");
		String startDateStr = frequencyArray[0];
		String endDateStr = frequencyArray[1];
		// String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = orgLocation.getLocFullFormDateFormatter();
		Date startDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		List<ProjectMembers> filteredMembersList = new ArrayList<>();
		List<SubmissionApproverHierarchy> sahList = null;
		if (null != projectId && !"".equalsIgnoreCase(projectId)) {
			sahList = submissionApproverHierarchyService.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long.parseLong(projectId), orgLocation.getId(), startDate, endDate, contractType);
		}
		if (null != sahList && sahList.size() > 0) {
			for (SubmissionApproverHierarchy sah : sahList) {
				ProjectMembers subPm = sah.getSubmissionId().getProjectMembers();
				ProjectMembers projectMember = projectMembersService.getProjectMembersByMasterCandidateIdAndProjectIdLocationId(subPm.getMasterCandidateId().getId(), Long.parseLong(projectId), orgLocation.getId(),isGlobalAccess);
				// Candidate candidate =
				// candidateService.getCandidateByParentCandidateId(projectMember.getProjectMemberCandidteId().getId());
				Candidate candidate = projectMember.getProjectMemberCandidteId();
				if (candidate != null && ("C2C".equalsIgnoreCase(candidate.getDesignationType()) || "C2C-1099".equalsIgnoreCase(candidate.getDesignationType()))) {
					projectMember.setSubmissionApproverHierarchyId(sah.getId());
					projectMember.setSubmissionInvoiceId(sah.getSubmissionId().getId());
					/*
					 * Employees employee =
					 * employeesService.getEmployeByUserIdAndOrgRelationId(
					 * projectMember.getEmployee().getUser().getId(),
					 * orgRelationId); if(null != employee){
					 */
					StringBuilder empName = new StringBuilder();
					if (null != projectMember.getProjectMemberCandidteId()
							&& StringUtils.isNotBlank(projectMember.getProjectMemberCandidteId().getLastName())) {
						empName.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ").append(projectMember.getProjectMemberCandidteId().getLastName());
					}
					else {
						empName.append(projectMember.getProjectMemberCandidteId().getFirstName());
					}
					projectMember.setCandidateName(empName.toString());
					projectMember.setStartDate(startDateStr);
					projectMember.setEndDate(endDateStr);
					// projectMember.setUserId(employee.getUser().getId().toString());
					String billableHours = String.valueOf(sah.getBillableHours());
					StringBuilder sb = new StringBuilder();
					if (sah.getBillableHours() != null) {
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
					}
					else {
						sb.append("0");
					}
					projectMember.setTotalHours(sb.toString());
					// Candidate candidate =
					// candidateService.getCandidateByConsultantUserIdOrgRelationId(projectMember.getMasterCandidateId().getId(),orgLocation.getId(),orgRelationId);
					if (projectMember.getProjectMemberCandidteId() != null) {
						projectMember.setCandidateId(projectMember.getProjectMemberCandidteId().getId());
						List<Contracts> contractList = getSowContract(projectMember.getProjectMemberCandidteId().getId(), orgLocation, orgRelationId, contractType);
						if (null != contractList && contractList.size() > 0) {
							projectMember.setContractsList(contractList);
						}
						projectMember.setCandidateId(projectMember.getProjectMemberCandidteId().getId());
					}
					filteredMembersList.add(projectMember);
				}
			}
		}
		if (filteredMembersList.size() > 0) {
//			resultJson = commonUtil.toJSON(filteredMembersList);
			
			if(orgRelations.getGenerateBill() !=null && orgRelations.getGenerateBill().equalsIgnoreCase(Constants.FINANCE_TYPE_INDIVIDUAL)){
				List<ProjectMembers> projectMembersList1 = new ArrayList<>();
				projectMembersList1.add(filteredMembersList.get(0));
				responseList.setData(projectMembersList1);	
			}else{
				responseList.setData(filteredMembersList);	
			}
			
		}
		// }
		return responseList;
	}
	
	@RequestMapping(value = "/getAllProjectsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody ResponseList<Projects> getAllProjectsByOrgRelationId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<Projects> responseList = new ResponseList<>();
		// String resultJson;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Projects> projectList = projectsService.getAllProjectsByOrgLocationIdAndStatus(orgLocations.getId());
		// resultJson = commonUtil.toJSON(projectList);
		responseList.setData(projectList);
		return responseList;
	}
	
	/**
	 * Getting rate based on contract
	 * 
	 * @param contractId
	 * @param request
	 * @param response
	 * @return @ResponseBody
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSowRateByContractId", method = RequestMethod.GET)
	public @ResponseBody Response<Contracts> getSowRateByContractId(@RequestParam(value = "contractId", required = false) Long contractId, HttpServletRequest request) throws Exception
	{
		Response<Contracts> response = new Response<>();
//		String resultJson = "";
		Contracts contracts = contractsTemplateService.getContractById(contractId);
		String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		if (null != contracts.getBillingAmount() && null != contracts.getBillingRatePer()) {
			float hourlyRate = conversionUtil.getHourlyRate(contracts.getBillingAmount(), contracts.getBillingRatePer());
			if (!currencyCode.equalsIgnoreCase(contracts.getBillingRateType())) {
				float coversionRate = conversionUtil.convert(contracts.getBillingRateType(), currencyCode, hourlyRate);
				double roundValue = (Math.round(coversionRate * 100.0) / 100.0);
				contracts.setBillingAmount(String.valueOf(roundValue));
			}
			else {
				double roundValue = (Math.round(hourlyRate * 100.0) / 100.0);
				contracts.setBillingAmount(String.valueOf(roundValue));
			}
//			resultJson = commonUtil.toJSON(contracts);
			response.setObject(contracts);
		}
		return response;
	}
	
	/**
	 * @author Laxman
	 * @param invoiceId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/acceptedInvoice", method = RequestMethod.POST)
	public @ResponseBody Response<String> acceptedInvoice(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (invoiceUUID != null) {
					// Invoice invoice =
					// invoiceService.getInvoicetById(Long.parseLong(invoiceId));
					Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
					if (invoice != null) {
						
						invoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
						invoice.setUpdatedBy(user.getUserCode());
						invoice.setUpdatedDate(commonUtil.currentDate());
						Invoice childInvoice = invoiceDao.saveInvoce(invoice);
						invoiceService.saveInvoiceTimelineHistory(invoice, user, null, null, null, null);
						if (null != childInvoice) {
							if (null != invoice.getParentInvoiceId()) {
								Invoice parentInvoice = invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
								parentInvoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
								parentInvoice.setUpdatedBy(user.getUserCode());
								parentInvoice.setUpdatedDate(commonUtil.currentDate());
								invoiceDao.saveInvoce(parentInvoice);
								invoiceService.saveInvoiceTimelineHistory(parentInvoice, user, null, null, null, null);
							}
						}
						// Accept invoice/bill mail
						invoiceAcceptMailtoVendor(user, invoice, orgLocation, invoice.getInvoiceType());
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
					else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				}
			}
			else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	private void invoiceAcceptMailtoVendor(User loginUser, Invoice invoice, OrgLocations loginOrgLocation, String invoiceType) throws Exception
	{
		if(null != invoice.getContactUsers()){
			String[] contactUsers = invoice.getContactUsers().split(",");
			if(contactUsers.length != 0){
				for(String contactUserId : contactUsers){
					User contactUser = userService.getUserById(Long.parseLong(contactUserId.trim()));
					EmailMessage emailMessage = new EmailMessage();
					emailMessage.setFirstName(loginUser.getFirstName());
					emailMessage.setLastName(loginUser.getLastName());
					emailMessage.setToEmail(contactUser.getEmail());
					emailMessage.setFromEmail(loginUser.getEmail());
					emailMessage.setCreatedBy(loginUser.getUserCode());
					emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
					StringBuilder msgBuild = new StringBuilder();
					emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
					// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
					// setting the class filed values
					Map<String, String> globalVarsMap = new HashMap<String, String>();
					SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
					String createdDate = (dateFormat.format(invoice.getCreatedDate()));
					String dueDate = (dateFormat.format(invoice.getDueDate()));
					StringBuilder stringBuildername = new StringBuilder();
					
					if(invoice.getInvoiceType().equalsIgnoreCase(Constants.RECEIVABLE)){
						globalVarsMap.put("vendorName", stringBuildername.append(contactUser.getFirstName()).append(" ").append(contactUser.getLastName()).toString());
			
					}else{
						globalVarsMap.put("clientName", stringBuildername.append(contactUser.getFirstName()).append(" ").append(contactUser.getLastName()).toString());
			
					}
					globalVarsMap.put("invoiceID", invoice.getInvoiceId());
					globalVarsMap.put("totalAmouprojectMembersListnt", invoice.getTotal());
					globalVarsMap.put("date", createdDate);
					globalVarsMap.put("dueDate", dueDate);
					globalVarsMap.put("orgName", loginOrgLocation.getOrgLocationName());
					StringBuilder stringBuilderLoginname = new StringBuilder();
					globalVarsMap.put("LoginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
					StringBuilder stringBuildforLink = new StringBuilder();
					globalVarsMap.put("loginLink", stringBuildforLink.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
					InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
					
					//send mail logic
					List<EmailAttachment> attachmentList = new ArrayList<>();
					
					//send notification logic
					EmailScenario notificationScenario = null;
					if (invoiceType.equalsIgnoreCase("Payables")) {
						commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation, Constants.INVOICE_ACCEPT_MAIL_VENDOR_EMAIL,attachmentList);
						notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_ACCEPT_MAIL_VENDOR_EMAIL, Constants.ENTITY_TYPE_NOTIFICATION);
					}
					else {
						commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation, Constants.INVOICE_ACCEPT_MAIL_CLIENT_EMAIL,attachmentList);
						notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_ACCEPT_MAIL_CLIENT_EMAIL, Constants.ENTITY_TYPE_NOTIFICATION);
					}
					String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
					notificationScenario.setBody(notificationbody);
					String actionLink = "";
					StringBuilder notificationLink = new StringBuilder();
					if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
						actionLink = notificationLink.append(sourceleadUrl).append("/").append("viewInvoice?invoiceUUID="
								+ invoice.getInvoiceUUID() + "&invoiceType=Payables").toString();
					}
					else {
						actionLink = notificationLink.append(sourceleadUrl).append("/").append("viewInvoice?invoiceUUID="
								+ invoice.getInvoiceUUID() + "&invoiceType=Receivable").toString();
					}
					emailScenerioUtil.sendNotification(actionLink, loginUser, contactUser, invoice.getResponderLocationId(), notificationScenario, new HashMap<String, String>());
				}
			}
		}
		
	}
	
	
	@RequestMapping(value = "/acceptCreditDebitNote", method = RequestMethod.POST)
	public @ResponseBody Response<String> acceptCreditDebitNote(@RequestParam(value = "creditDebitUUID", required = false) String creditDebitUUID, 
			@RequestParam(value = "noteType", required = false) String noteType, 
			 HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (creditDebitUUID != null) {
					CreditDebitNote creditDebitNote = invoiceService.getCreditDebitNoteByUUID(creditDebitUUID);
					
					 invoiceService.saveAcceptedCreditDebitNote(user, orgLocation, noteType, creditDebitNote);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
				}
			}
			else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	
	@RequestMapping(value = "/rejectCreditDebitNote", method = RequestMethod.POST)
	public @ResponseBody Response<String> rejectCreditDebitNote(@RequestParam(value = "creditDebitUUID", required = false) String creditDebitUUID, 
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (creditDebitUUID != null) {
					CreditDebitNote creditDebitNote = invoiceService.getCreditDebitNoteByUUID(creditDebitUUID);
					if (creditDebitNote != null) {
						creditDebitNote.setNoteStatus(Constants.CREDIT_DEBIT_NOTE_REJECTED);
						creditDebitNote.setRejectReason(reason);
						creditDebitNote.setUpdatedBy(user);
						creditDebitNote.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(),orgLocation));
						invoiceDao.saveCreditDebitNote(creditDebitNote);
						
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
				}
			}
			else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = "/rejectInvoice", method = RequestMethod.POST)
	public @ResponseBody Response<String> rejectInvoice(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, @RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (invoiceUUID != null) {
//					Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceUUID));
					Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
					if (invoice != null) {
						invoice.setInvoiceStatus(Constants.INVOICE_REJECTED);
						invoice.setRejectReason(reason);
						invoice.setUpdatedBy(user.getUserCode());
						invoice.setUpdatedDate(commonUtil.currentDate());
						Invoice childInvoice = invoiceDao.saveInvoce(invoice);
						invoiceService.saveInvoiceTimelineHistory(invoice, user, null, null, null, null);
						if (null != childInvoice) {
							if (null != invoice.getParentInvoiceId()) {
								Invoice parentInvoice = invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
								parentInvoice.setInvoiceStatus(Constants.INVOICE_REJECTED);
								parentInvoice.setRejectReason(reason);
								parentInvoice.setUpdatedBy(user.getUserCode());
								parentInvoice.setUpdatedDate(commonUtil.currentDate());
								invoiceDao.saveInvoce(parentInvoice);
								invoiceService.saveInvoiceTimelineHistory(parentInvoice, user, null, null, null, null);
							}
						}
						// Reject invoice/bill mail
						invoiceRejectMailtoVendor(user, invoice, orgLocation, invoice.getInvoiceType());
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
				}
			}
			else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = "/voidInvoiceOrBill", method = RequestMethod.POST)
	public @ResponseBody Response<String> voidInvoiceOrBill(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (invoiceUUID != null) {
//					Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceUUID));
					Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
					if (invoice != null) {
						// update child/parent invoice
						invoice.setInvoiceStatus(Constants.INVOICE_BILL_VOID);
						invoice.setUpdatedBy(user.getUserCode());
						invoice.setDueAmount("0.0");
						invoice.setUpdatedDate(commonUtil.currentDate());
						Invoice childOrParentInvoice = invoiceDao.saveInvoce(invoice);
						invoiceService.saveInvoiceTimelineHistory(invoice, user, null, null, null, null);
						if (null != childOrParentInvoice) {
							if (null != invoice.getParentInvoiceId()) {
								// parent invoice update
								Invoice parentInvoice = invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
								parentInvoice.setInvoiceStatus(Constants.INVOICE_BILL_VOID);
								parentInvoice.setDueAmount("0.0");
								parentInvoice.setUpdatedBy(user.getUserCode());
								parentInvoice.setUpdatedDate(commonUtil.currentDate());
								invoiceDao.saveInvoce(parentInvoice);
								invoiceService.saveInvoiceTimelineHistory(parentInvoice, user, null, null, null, null);
							}else{
								// update child invoice
								Invoice childInvoice = invoiceDao.getInvoicetBySelfJoinInvoiceId(invoice.getId());
								childInvoice.setInvoiceStatus(Constants.INVOICE_BILL_VOID);
								childInvoice.setDueAmount("0.0");
								childInvoice.setUpdatedBy(user.getUserCode());
								childInvoice.setUpdatedDate(commonUtil.currentDate());
								invoiceDao.saveInvoce(childInvoice);
								invoiceService.saveInvoiceTimelineHistory(childInvoice, user, null, null, null, null);
							}
						}
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
				}
			}
			else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	private void invoiceRejectMailtoVendor(User loginUser, Invoice invoice, OrgLocations loginOrgLocation, String invoiceType) throws Exception
	{
		if(null != invoice.getContactUsers()){
			String[] contactUsers = invoice.getContactUsers().split(",");
			if(contactUsers.length != 0){
				for(String contactUserId : contactUsers){
					User contactUser = userService.getUserById(Long.parseLong(contactUserId.trim()));
					EmailMessage emailMessage = new EmailMessage();
					emailMessage.setFirstName(loginUser.getFirstName());
					emailMessage.setLastName(loginUser.getLastName());
					emailMessage.setToEmail(contactUser.getEmail());
					emailMessage.setFromEmail(loginUser.getEmail());
					emailMessage.setCreatedBy(loginUser.getUserCode());
					emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
					StringBuilder msgBuild = new StringBuilder();
					emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
					// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
					// setting the class filed values
					Map<String, String> globalVarsMap = new HashMap<String, String>();
					StringBuilder stringBuildername = new StringBuilder();
					if(invoice.getInvoiceType().equalsIgnoreCase(Constants.PAYABLES)){
						globalVarsMap.put("vendorName", stringBuildername.append(contactUser.getFirstName()).append(" ").append(contactUser.getLastName()).toString());
					}
					else{
					globalVarsMap.put("clientName", stringBuildername.append(contactUser.getFirstName()).append(" ").append(contactUser.getLastName()).toString());
					}
					globalVarsMap.put("invoiceID", invoice.getInvoiceId());
					globalVarsMap.put("totalAmount", invoice.getTotal());
					globalVarsMap.put("reason", invoice.getRejectReason());
					globalVarsMap.put("orgName", loginOrgLocation.getOrgLocationName());
					StringBuilder stringBuilderLoginname = new StringBuilder();
					globalVarsMap.put("LoginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
					StringBuilder stringBuildforLink = new StringBuilder();
					globalVarsMap.put("loginLink", stringBuildforLink.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
					InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
					
						    //send mail logic
					        List<EmailAttachment> attachmentList = new ArrayList<>();
					
					        //send notification logic
							EmailScenario notificationScenario = null;
							if (invoiceType.equalsIgnoreCase("Payables")) {
							    commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation, Constants.INVOICE_REJECT_MAIL_VENDOR_EMAIL,attachmentList);
								notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_REJECT_MAIL_VENDOR_EMAIL, Constants.ENTITY_TYPE_NOTIFICATION);
							}
							else {
							    commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation, Constants.INVOICE_REJECT_MAIL_CLIENT_EMAIL,attachmentList);
								notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVOICE_REJECT_MAIL_CLIENT_EMAIL, Constants.ENTITY_TYPE_NOTIFICATION);
							}
							String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
							notificationScenario.setBody(notificationbody);
							String actionLink = "";
							StringBuilder notificationLink = new StringBuilder();
							if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
								actionLink = notificationLink.append(sourceleadUrl).append("/").append("viewInvoice?invoiceUUID="
										+ invoice.getInvoiceUUID() + "&invoiceType=Payables").toString();
							}
							else {
								actionLink = notificationLink.append(sourceleadUrl).append("/").append("viewInvoice?invoiceUUID="
										+ invoice.getInvoiceUUID() + "&invoiceType=Receivable").toString();
							}
							emailScenerioUtil.sendNotification(actionLink, loginUser, contactUser, invoice.getResponderLocationId(), notificationScenario, new HashMap<String, String>());
				}
			}
		}
	}
	
	/**
	 * @author Laxman
	 * @param invoiceId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendReminder", method = RequestMethod.POST)
	public @ResponseBody Response<String> sendReminder(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//				Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceUUID));
				Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
				if(invoice != null){
				invoiceService.sendInvoiceReminderMail(user, orgLocation, invoice, invoice.getInvoiceType(), null);
					// notify other email
					if (StringUtils.isNotBlank(invoice.getNotifyToOthers())) {
						for (String notifyToEmail : invoice.getNotifyToOthers().split(",")) {
							invoiceService.sendInvoiceReminderMailToNotifyOthers(user, orgLocation, invoice,invoice.getInvoiceType(), null, notifyToEmail);
						}
					}
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else{
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}else{
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return response;
	}
	
	@RequestMapping(value = "/getDueDateByFrequency", method = RequestMethod.GET)
	public @ResponseBody String getDueDateByFrequency(@RequestParam(value = "paymentFrequency", required = false) String paymentFrequency, @RequestParam(value = "invoiceDate", required = false) String invoiceDate, @RequestParam("locationCode") String locationCode) throws Exception
	{
		
		OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
		String[] frequency = paymentFrequency.split(" ");
		// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dueDateStr = null;
		try {
			
			Date date = formatter.parse(invoiceDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, Integer.parseInt(frequency[0]));
			dueDateStr = formatter.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dueDateStr;
	}
	
	/**
	 * Get exchange currency
	 * 
	 * @param orgRelationId
	 * @param grandTotal
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return CurrencyConversionResponse
	 */
	@RequestMapping(value = "/getConversionExchangeRate", method = RequestMethod.GET)
	@ResponseBody
	public Response<CurrencyConversionResponse> getConversionExchangeRate(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId, @RequestParam(value = "grandTotal", required = false) String grandTotal, HttpServletRequest request) throws Exception
	{
		Response<CurrencyConversionResponse> response = new Response<>();
//		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginOrgCurrencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		if(orgRelationId != null){
		CurrencyConversionResponse currencyConversionResponse = invoiceService.getExchangeCurrency(loginOrgCurrencyCode, orgLocation, orgRelationId, grandTotal);
		if (null != currencyConversionResponse) {
//			resultJson = commonUtil.toJSON(currencyConversionResponse);
			response.setObject(currencyConversionResponse);
		}
	}
		return response;
	}
	
	@RequestMapping(value = "/getBillingExportList")
	public void getBillingExportList(InvoiceDTO invoiceDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		invoiceDTO.setSearchValue("");
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime();
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd");
		Date endDateUTC = null;
		String filterDate = null;
		Date startDateUTC = null;
		if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(invoiceDTO.getDurationType())) {
			String endDate = outputFormatter1.print(zoned);
			// String endDateString =
			// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
			// Constants.UTC_TIMEZONE, endDate);
			endDateUTC = TZ_DF_TS.parse(endDate);
		}
		if (null != invoiceDTO.getDurationType() && !"".equals(invoiceDTO.getDurationType())) {
			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(invoiceDTO.getDurationType())) {
				String startDate = outputFormatter.print(zoned);
				// String startDateString =
				// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
				// Constants.UTC_TIMEZONE, startDate);
				filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				startDateUTC = TZ_DF_TS.parse(startDate);
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
			}
			else {
				if (!"".equalsIgnoreCase(invoiceDTO.getExportStartDate())
						&& !"".equalsIgnoreCase(invoiceDTO.getExportEndDate())
						&& !"undefined".equalsIgnoreCase(invoiceDTO.getExportStartDate())
						&& !"undefined".equalsIgnoreCase(invoiceDTO.getExportEndDate())) {
					DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd");
					DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd");
					Date startTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), invoiceDTO.getExportStartDate());
					Date endTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), invoiceDTO.getExportEndDate());
					String customStartTimeStr = TZ_DF_CSD.format(startTime);
					String customEndTimeStr = TZ_DF_CED.format(endTime);
					// String startDateString =
					// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
					// Constants.UTC_TIMEZONE, customStartTimeStr);
					// String endDateString =
					// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
					// Constants.UTC_TIMEZONE, customEndTimeStr);
					startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
					endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
				}
			}
		}
		ResponseList<Invoice> invoiceList = invoiceService.invoiceListByOrgLocationId(orgLocations, startDateUTC, endDateUTC, invoiceDTO);
		InvoiceAmount totalStagesAmount = getTotalAmountInBilling(invoiceList);
		File file = null;
		String convertedExportStartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), startDateUTC);
		String convertedExportEndDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), endDateUTC);
		file = invoiceService.getExportBillingFile(invoiceList, orgLocations.getOrgLocationName(), invoiceDTO.getExportType(), totalStagesAmount, convertedExportStartDate, convertedExportEndDate, invoiceDTO.getInvoiceType(), orgLocations);
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
		if (file != null) {
			file.delete();
		}
	}
	
	private InvoiceAmount getTotalAmountInBilling(ResponseList<Invoice> invoiceList) throws ParseException
	{
		
		InvoiceAmount invoiceAmount = new InvoiceAmount();
		double totalUnpaidOverDueAmount = 0;
		double totalUnpaidOpenAmount = 0;
		double totalPaidAmount = 0;
		double totalUnbilledAmount = 0;
		for (Invoice invoice : invoiceList.getData()) {
			
			if (!Constants.REQUIREMENT_STATUS.equalsIgnoreCase(invoice.getInvoiceStatus())
					&& !Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())
					&& (Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())
							|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
							|| Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String currentDate = sdf.format(new Date());
				Date todayDate = sdf.parse(currentDate);
				String dueDate = sdf.format(invoice.getDueDate());
				Date invoiceDueDate = sdf.parse(dueDate);
				double dueAmount = Double.parseDouble(invoice.getDueAmount());
				if (dueAmount > 0) {
					
					if (todayDate.after(invoiceDueDate)) {
						totalUnpaidOverDueAmount = totalUnpaidOverDueAmount
								+ Double.parseDouble(invoice.getDueAmount());
					}
					else {
						totalUnpaidOpenAmount = totalUnpaidOpenAmount
								+ Double.parseDouble(invoice.getDueAmount());
					}
				}
			}
			if (!Constants.REQUIREMENT_STATUS.equalsIgnoreCase(invoice.getInvoiceStatus())
					&& !Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())
					&& (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase((invoice.getPaymentStatus()))
							|| Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus())
							|| Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))) {
				Double paidAmount1 = Double.parseDouble(invoice.getTotal())
						- Double.parseDouble(invoice.getDueAmount());
				String convertedPaidAmount = String.valueOf(paidAmount1);
				totalPaidAmount = totalPaidAmount + Double.parseDouble(convertedPaidAmount);
			}
			if (Constants.REQUIREMENT_STATUS.equalsIgnoreCase(invoice.getInvoiceStatus())
					|| Constants.INVOICE_REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())) {
				totalUnbilledAmount = totalUnbilledAmount
						+ Double.parseDouble(invoice.getDueAmount());
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
	 * 
	 * @param invoiceType
	 * @param contractType
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/generateAutomaticInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Response<InvoiceDTO> generateAutomaticInvoice(@RequestParam(value = "invoiceType", required = false) String invoiceType, @RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request) throws Exception
	{
		Response<InvoiceDTO> response = new Response<>();
		InvoiceDTO invoiceDTO = new InvoiceDTO();
		
//		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginOrgCurrencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Integer savedInvoiceCount = invoiceService.getAutomaticInvoice(orgLocation, loginOrgCurrencyCode, invoiceType, user, contractType,isGlobalAccess);
//		resultJson = commonUtil.toJSON(savedInvoiceCount);
		invoiceDTO.setCount(String.valueOf(savedInvoiceCount));
		response.setObject(invoiceDTO);
		return response;
	}
	
	@RequestMapping(value = "/generateAutomaticInvoiceForJobClosure", method = RequestMethod.POST)
	@ResponseBody
	public Response<InvoiceDTO> generateAutomaticInvoiceForJobClosure(@RequestParam(value = "invoiceType", required = false) String invoiceType, 
			@RequestParam(value = "contractType", required = false) String contractType,
			HttpServletRequest request) throws Exception
	{
		Response<InvoiceDTO> response = new Response<>();
		InvoiceDTO invoiceDTO = new InvoiceDTO();
		
//		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginOrgCurrencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Integer savedInvoiceCount = invoiceService.getAutomaticInvoiceForJobClosure(orgLocation, loginOrgCurrencyCode, invoiceType, user,contractType,isGlobalAccess);
//		resultJson = commonUtil.toJSON(savedInvoiceCount);
		invoiceDTO.setCount(String.valueOf(savedInvoiceCount));
		response.setObject(invoiceDTO);
		return response;
	}
	
	@RequestMapping(value = "/getBillingReportByFrequency", method = RequestMethod.GET)
	@ResponseBody
	public Response<BillingReportDTO> getBillingReportByFrequency(@RequestParam(value = "billingReportType", required = false) String billingReportType, @RequestParam(value = "invoiceType", required = false) String invoiceType, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) throws Exception
	{
		Response<BillingReportDTO> response = new Response<>();
//		String resultJson = "";
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginOrgCurrencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		BillingReportDTO invoiceList = invoiceService.getBillingReports(billingReportType, invoiceType, startDate, endDate, orgLocation, user, loginUserRoleId, loginOrgCurrencyCode);
		if (invoiceList != null) {
//			resultJson = commonUtil.toJSON(invoiceList);
			response.setObject(invoiceList);;
		}
		return response;
	}
	
	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		// String startDateString =
		// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
		// Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(yesterDayDate);
	}
	
	@RequestMapping(value = "/getInvoiceListForCreditDebitNote", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<Invoice> getInvoiceListForCreditDebitNote(@RequestParam(value = "orgRelationId", required = false) String orgRelationId, 
			@RequestParam(value = "invoiceType", required = false) String invoiceType, 
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<Invoice> responseList = new ResponseList<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Invoice> invoiceList = null;
		if(StringUtils.isNotBlank(orgRelationId)){
		invoiceList = invoiceService.getClientBasedInvoiceLists(Long.parseLong(orgRelationId), orgLocation.getId(), invoiceType);
		}
			responseList.setData(invoiceList);
		return responseList;
	}
	
	@RequestMapping(value = "/getApproversListForCreditDebitNote", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<User> getApproversListForCreditDebitNote(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<User> responseList = new ResponseList<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> approversList = userService.getOwnersByOrgLocationId(orgLocation.getId());
		responseList.setData(approversList);
		return responseList;
	}
	
	@RequestMapping(value = "/getClientBasedInvoiceList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<Invoice> getClientBasedInvoiceList(@RequestParam(value = "invoiceType", required = false) String invoiceType, @RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<Invoice> responseList = new ResponseList<>();
//		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(invoiceUUID));
		Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		OrgRelations orgRelId = invoice.getOrgRelationID();
		List<Invoice> clientinvoiceList = invoiceService.getClientBasedInvoiceLists(orgRelId.getId(), orgLocation.getId(), invoiceType);
		List<Invoice> clientList = new ArrayList<Invoice>();
		for (Invoice invce : clientinvoiceList) {
			if (!invoiceUUID.equalsIgnoreCase(invce.getInvoiceUUID())) {
				if (invce.getInvoiceDate() != null) {
					invce.setInvoiceDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invce.getInvoiceDate()));
				}
				if (invce.getDueDate() != null) {
					invce.setDueDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invce.getDueDate()));
				}
				if(null != invce.getContactUsers()){
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
						
						invce.setClientContactName(contactUserName.toString().substring(0, contactUserName.toString().length() - 2));
					}
				}
				/*if (null != invce.getContactUser()) {
					StringBuilder username = new StringBuilder();
					if (null != invce.getContactUser().getLastName()) {
						username.append(invce.getContactUser().getFirstName()).append(" ").append(invce.getContactUser().getLastName()).toString();
					}
					else {
						username.append(invce.getContactUser().getFirstName());
					}
					invce.setClientContactName(username.toString());
				}*/
				clientList.add(invce);
			}
		}
		if (clientList != null && clientList.size() > 0) {
//			resultJson = commonUtil.toJSON(clientList);
			responseList.setData(clientList);
		}
		return responseList;
	}
	
	@RequestMapping(value = "/linkInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Response<Invoice> linkInvoice(@RequestParam(value = "invoiceType", required = false) String invoiceType, @RequestParam(value = "checkVal", required = false) Long id, @RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, HttpServletRequest request) throws Exception
	{
		Response<Invoice> response = new Response<>();
//		String resultJson = "";
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Invoice masterInvoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		Invoice invoice = invoiceService.getInvoicetById(id);
		invoice.setLinkInvoiceReferenceId(masterInvoice.getId());
		invoice.setLinkInvoiceRefInvoiceId(masterInvoice.getInvoiceId());
		invoice.setPreviousInvoiceStatus(invoice.getInvoiceStatus());
		invoice.setInvoiceStatus(Constants.LINKED_STATUS);
		invoice.setUpdatedDate(commonUtil.currentDate());
		invoice.setUpdatedBy(user.getUserCode());
		masterInvoice.setLinkInvoiceReferenceId(invoice.getId());
		masterInvoice.setLinkInvoiceRefInvoiceId(invoice.getInvoiceId());
		invoiceService.linkInvoice(invoice);
		invoiceService.linkInvoice(masterInvoice);
		invoiceService.saveInvoiceTimelineHistory(masterInvoice, user, invoice, null, null, null);
		if (masterInvoice.getId() != null) {
			Invoice childInvoice = invoiceService.getInvoicetByParentId(invoice.getId());
			if (childInvoice != null) {
				childInvoice.setPreviousInvoiceStatus(childInvoice.getInvoiceStatus());
				childInvoice.setInvoiceStatus(Constants.LINKED_STATUS);
				childInvoice.setUpdatedDate(commonUtil.currentDate());
				childInvoice.setUpdatedBy(user.getUserCode());
				invoiceService.linkInvoice(childInvoice);
			}
			else {
				Invoice parentInvoice = invoiceService.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
				parentInvoice.setPreviousInvoiceStatus(parentInvoice.getInvoiceStatus());
				parentInvoice.setInvoiceStatus(Constants.LINKED_STATUS);
				parentInvoice.setUpdatedDate(commonUtil.currentDate());
				parentInvoice.setUpdatedBy(user.getUserCode());
				invoiceService.linkInvoice(parentInvoice);
			}
		}
//		resultJson = commonUtil.toJSON(invoice);
		response.setObject(invoice);
		return response;
	}
	
	/**
	 * Dipalay billing summary in clients summary tabs
	 * 
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
	public @ResponseBody ResponseList<Invoice> getInvoiceListBasedOnOrgrelationId(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId, @RequestParam(value = "clientLocationId", required = false) Long clientLocationId, @RequestParam(value = "dayWisetype", required = false) String dayWisetype, @RequestParam(value = "startDate", required = false) String customStartDate, @RequestParam(value = "endDate", required = false) String customEndDate, @RequestParam(value = "invoiceStatus", required = false) String invoiceStatus, @RequestParam(value = "paymentStatus", required = false) String paymentStatus, @RequestParam(value = "invoiceType", required = false) String invoiceType, HttpServletRequest request) throws Exception
	{
		ResponseList<Invoice> responseList = new ResponseList<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Invoice> invoiceList = invoiceService.getInvoiceByOrgRelationId(orgRelationId, clientLocationId, invoiceStatus, paymentStatus, invoiceType, orgLocations, dayWisetype, customStartDate, customEndDate);
//		return commonUtil.toJSON(invoiceList);
		responseList.setData(invoiceList);
        
        return responseList;
	}
	
	/**
	 * Unlinking the Linked Invoice/Bill
	 * 
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unLinkInvoice", method = RequestMethod.POST)
	@ResponseBody
	public boolean unLinkInvoice(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID) throws Exception
	{
		boolean result = false;
		Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		invoice.setLinkInvoiceReferenceId(null);
		invoice.setLinkInvoiceRefInvoiceId(null);
		invoice.setInvoiceStatus(invoice.getPreviousInvoiceStatus());
		invoice.setPreviousInvoiceStatus(invoice.getInvoiceStatus());
		invoiceService.linkInvoice(invoice);
		Invoice childInvoice = invoiceService.getInvoicetByParentId(invoice.getId());
		if (childInvoice != null) {
			childInvoice.setInvoiceStatus(childInvoice.getPreviousInvoiceStatus());
			childInvoice.setPreviousInvoiceStatus(childInvoice.getInvoiceStatus());
			Invoice invoiceData = invoiceService.linkInvoice(childInvoice);
			if (invoiceData != null) {
				result = true;
			}
		}
		else {
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
	 * 
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInvoice", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteInvoice(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID, @RequestParam(value = "reason", required = false) String reason, @RequestParam(value = "invoiceType", required = false) String invoiceType, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		boolean isDeleted = invoiceService.deleteInvoice(invoiceUUID, reason, invoiceType, user);
		
		return isDeleted;
	}
	
	@RequestMapping(value = "/getTotalHosursForBilling", method = RequestMethod.GET)
	public @ResponseBody String getTotalHosursForBilling(@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "averageWorkingHours", required = false) Integer averageWorkingHours, @RequestParam(value = "locationId", required = false) Long locationId) throws Exception
	{
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
		StringBuilder sb = new StringBuilder();
		String estimatedHours = null;
		try {
			
			Date requestStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(requestStartDate);
			Date requestEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(requestEndDate);
			int workDays = 0;
			if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
				startCal.setTime(requestEndDate);
				endCal.setTime(requestStartDate);
			}
			do {
				if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					++workDays;
				}
				startCal.add(Calendar.DATE, 1);
			}
			while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());
			estimatedHours = String.valueOf((workDays * averageWorkingHours));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.append(estimatedHours).append(":00").toString();
	}
	
	@RequestMapping(value = "/getTotalHosursForPayable", method = RequestMethod.GET)
	public @ResponseBody String getTotalHosursForPayable(@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "averageWorkingHours", required = false) Integer averageWorkingHours, @RequestParam(value = "locationId", required = false) Long locationId) throws Exception
	{
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
		StringBuilder sb = new StringBuilder();
		String estimatedHours = null;
		try {
			
			Date requestStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(requestStartDate);
			Date requestEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(requestEndDate);
			int workDays = 0;
			if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
				startCal.setTime(requestEndDate);
				endCal.setTime(requestStartDate);
			}
			do {
				if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					++workDays;
				}
				startCal.add(Calendar.DATE, 1);
			}
			while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());
			estimatedHours = String.valueOf((workDays * averageWorkingHours));
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
	public @ResponseBody String saveExpensesInvoice(InvoiceDTO invoiceDTO, @RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "orgLocationId", required = false) String orgLocationId, @RequestParam(value = "saveType", required = false) String saveType, @RequestParam(value = "currencyCode", required = false) String currencyCode, @RequestParam(value = "invoiceType", required = false) String invoiceType, @RequestParam(value = "type", required = false) String invoiceCategory, HttpServletRequest request) throws Exception
	{
		
		String invoiceSaved = "failed";
		User user = userService.getUserById(Long.parseLong(userId));
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(orgLocationId));
		Org org = orgLocation.getOrg();
		Invoice invoice = invoiceService.saveExpenseInvoice(invoiceDTO, user, orgLocation, saveType, invoiceType, org, currencyCode, invoiceCategory, systemDefaultFont);
		if (invoice != null && invoice.getExpensesId() != null) {
			invoiceService.updateExpensesForinvoice(invoice, invoiceType);
		}
		if (Constants.PAYABLE.equals(invoiceType) && Constants.DRAFT.equals(saveType)) {
			SourceLeadSessionManager.setSessionAttribute(request, "payableInvoiceCreateMsg", "Successfully you have created a Bill");
		}
		if (Constants.PAYABLE.equals(invoiceType) && Constants.INVOICE_SENT.equals(saveType)) {
			SourceLeadSessionManager.setSessionAttribute(request, "payableInvoiceCreateMsg", "Successfully you have sent a Bill");
		}
		if (Constants.RECEIVABLE.equals(invoiceType) && Constants.DRAFT.equals(saveType)) {
			
			SourceLeadSessionManager.setSessionAttribute(request, "invoiceCreateMsg", "Successfully you have created a Invoice");
		}
		if (Constants.RECEIVABLE.equals(invoiceType) && Constants.INVOICE_SENT.equals(saveType)) {
			SourceLeadSessionManager.setSessionAttribute(request, "invoiceCreateMsg", "Successfully you have sent a Invoice");
		}
		if (null != invoice) {
			if (null != invoice.getProjectID()) {
				invoiceService.updateSubmissionApproverHiearachy(invoice, invoiceType);
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
	public ResponseList<ProjectMembers> getProjectAssignMembers(@RequestParam(value = "projectId", required = false) Long projectId, HttpServletRequest request) throws Exception
	{
		ResponseList<ProjectMembers> responseList = new ResponseList<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		List<ProjectMembers> associatesList = projectMembersService.getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(projectId, orgLocation.getId(),isGlobalAccess);
		responseList.setData(associatesList);
		return responseList;
	}
	
	/**
	 * Gte Expenses Frequencies by Expense Id
	 * 
	 * @param projectMemberId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExpensesByProjectMemberId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<Expenses> getExpensesByProjectMemberId(@RequestParam(value = "memberId", required = false) Long projectMemberId,
			@RequestParam(value = "expenseId", required = false) Long expenseId,
			@RequestParam(value = "invoiceType", required = false) String invoiceType) throws Exception
	{
		ResponseList<Expenses> responseList = new ResponseList<>();
		List<Expenses> expensesList = expensesService.getExpensesByProjectMemberId(projectMemberId, invoiceType,expenseId);
		responseList.setData(expensesList);
		return responseList;
	}
	
	/**
	 * Get Expenses Details By expense id
	 * 
	 * @param expenseId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExpensesEntries", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<ExpensesEntriesDTO> getExpensesEntries(@RequestParam(value = "expensesId", required = false) Long expenseId, HttpServletRequest request) throws Exception
	{
		ResponseList<ExpensesEntriesDTO> responseList = new ResponseList<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesEntriesDTO> expensesEntriesDTOList = expensesService.getApprovedExpenses(expenseId, orgLocation.getId());
		responseList.setData(expensesEntriesDTOList);
		return responseList;
	}
	
	/**
	 * To get peoject assigned vendor members
	 * 
	 * @param projectId
	 * @param orgRelationId
	 * @param userId
	 * @param orgLocationId
	 * @return List<ProjectMembers>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVendorAssignedMembersForProjects", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<ProjectMembers> getVendorAssignedMembersForProjects(@RequestParam(value = "projectId", required = false) Long projectId, @RequestParam(value = "orgRelationId", required = false) Long orgRelationId, @RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId,HttpServletRequest request) throws Exception
	{
		ResponseList<ProjectMembers> responseList = new ResponseList<>();
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		List<ProjectMembers> associatesList = projectMembersService.getVendorAssignedMembersForProjects(projectId, orgLocationId, orgRelationId,isGlobalAccess);
		responseList.setData(associatesList);
		return responseList;
	}

	@ResponseBody
	@RequestMapping(value = "/getAllPaymentAccountList", method = RequestMethod.GET)
	public List<PaymentAccountDTO> getAllPaymentAccountList(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception {
		OrgRelations orgRealation =  orgRelationsService.getOrgRelationById(orgRelationId); 
		List<PaymentAccountDTO> paymentAccountList = orgService.getPaymentAccountList(orgRealation.getResponderLocationId().getOrg().getId(), orgRealation.getResponderLocationId().getId(),true);
		return paymentAccountList;
	}
	
	@RequestMapping(value = "/getInvoiceTimelineHistory", method = RequestMethod.GET)
	public @ResponseBody List<InvoiceTimelineDTO> getInvoiceTimelineHistory(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID,@RequestParam(value = "dateRange", required = false) String[] dateRange, 
			@RequestParam(value = "searchVal", required = false) String searchVal,@RequestParam(value = "orderVal", required = false) String orderVal,
			@RequestParam(value = "locationId", required = false) String locationId,
			@RequestParam(value = "checkedStatus") String[] checkedStatus,
			@RequestParam(value = "namesStatus") String[] namesStatus,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<InvoiceTimelineDTO> timelineHistories = new ArrayList<>();
		Date datesRange[] = new Date[2];
		if(dateRange[0] != null && !dateRange[0].equals("null") && !dateRange[0].equalsIgnoreCase("") && !dateRange[1].equalsIgnoreCase("")) {
				OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(locationId));
				Date fromDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dateRange[0]);
				Date toDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dateRange[1]);
				/*Calendar cal = Calendar.getInstance();
			    cal.setTime(fromDate);
			    cal.add(Calendar.DATE, 1);
			    cal.add(Calendar.SECOND, -1);
			    cal.getTime();*/
			    Calendar cal1 = Calendar.getInstance();
			    cal1.setTime(toDate);
			    cal1.add(Calendar.DATE, 1);
			    cal1.add(Calendar.SECOND, -1);
			    cal1.getTime();
			System.out.println("from date : "+fromDate);
			System.out.println("to date : "+cal1.getTime());
			datesRange[0] = fromDate;
			datesRange[1] = cal1.getTime();
		}
		timelineHistories = invoiceService.getInvoiceTimelineHistoryList(invoice.getId(), user, searchVal, orderVal, datesRange, checkedStatus,namesStatus);
		return timelineHistories;
	}
	
	@RequestMapping(value = "/deleteCreditDebitNote", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteCreditDebitNote(@RequestParam(value = "creditDebitUUID", required = false) String creditDebitUUID, 
			@RequestParam(value = "noteType", required = false) String noteType, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orglocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isDeleted = invoiceService.deleteCreditDebitNote(creditDebitUUID, noteType, user,orglocations);
		
		return isDeleted;
	}
	
	@RequestMapping(value = "/reactiveCreditDebitNote", method = RequestMethod.POST)
	@ResponseBody
	public boolean reactiveCreditDebitNote(@RequestParam(value = "creditDebitUUID", required = false) String creditDebitUUID, 
			@RequestParam(value = "noteType", required = false) String noteType, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orglocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean reactive = invoiceService.reactiveCreditDebitNote(creditDebitUUID, noteType, user,orglocations);
		
		return reactive;
	}
	
	@RequestMapping(value = "/deleteLineItemEditModeId", method = RequestMethod.POST)
	@ResponseBody
	public String deleteLineItemEditModeId(@RequestParam(value = "id", required = false) String id, HttpServletRequest request) throws Exception
	{
		OrgLocations orglocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		CreditDebitLineItems lineItem = invoiceService.getCreditDebitLineItemById(Long.parseLong(id));
		invoiceService.deleteCreditDebitLineItem(lineItem);
		return "Success";
	}
	
	/**
	 * @author Mahaveer Manoj
	 */
	/*@RequestMapping(value="/generateCustomInvoicePDF")
	public void generateCustomInvoicePDF(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String loginOrgCurrencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(36L);
			OrgLocations vendorOrgLocation = null;
			if (orgRelation.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				vendorOrgLocation = orgRelation.getLocationId();
			}
			if (orgRelation.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				vendorOrgLocation = orgRelation.getResponderLocationId();
			}
			Invoice invoice =  invoiceDao.getInvoicetById(149L);
			
			String vendorCurrencyCode =  conversionUtil.getVendorOrgCurrencyCode(vendorOrgLocation.getCountry());
			CustomInvoiceTemplate customInvoiceTemplate = invoiceDao.getCustomInvoiceTemplateById(8L);
			File file = customInvoicePDFHelper.createCustomInvoicePDF(invoice, orgLocation, user, org, vendorCurrencyCode, loginOrgCurrencyCode, customInvoiceTemplate, "client/project", systemDefaultFont, sourceleadUrl);
			if(file!= null){
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
				mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@RequestMapping(value = "/invoiceTemplateList", method = RequestMethod.POST)
	public ResponseList<CustomInvoiceTemplateDTO> customizeInvoice(HttpServletRequest request, SearchDTO searchDTO,
			@RequestParam(defaultValue = "0") Long orgLocationId, @RequestParam(defaultValue = "0") Long orgRelationId) {
		ResponseList<CustomInvoiceTemplateDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loggedInOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				responseList = invoiceService.getCustomInvoiceTemplateListByOrgRelationIdAndOrgLocationId(orgRelationId, orgLocationId, searchDTO, loggedInOrgLocations);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}
	
	@RequestMapping(value = "/saveCustomInvoiceTemplate", method = RequestMethod.POST)
	public Response<String> saveDashboardItems(HttpServletRequest request,
			@RequestParam(defaultValue="0") Long orgLocationId,
			@RequestParam(defaultValue="0") Long orgRelationId,
			CustomInvoiceTemplateDTO customInvoiceTemplateDTO) {
		Response<String> response = new Response<>();
		try {
			User loggedInUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (loggedInUser != null) {
				JSONArray jsonArray = new JSONArray(customInvoiceTemplateDTO.getJsonData());
				if(jsonArray!=null) {
					for(int i=0;i<jsonArray.length();i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						/*if (!jsonObject.has("name") || !jsonObject.has("checked") || !jsonObject.has("order") || !jsonObject.has("size")) {
							response.setResponse(Result.INVALID_DATA.name());
							response.setResponseMessage(Result.INVALID_DATA.getValue());
							return response;
						}*/
					}
				}
				OrgLocations loggedInOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
				OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsByResponderLocationdIdAndOrgRelationId(orgLocationId,orgRelationId,loggedInOrgLocations.getId());
				if(orgLocation != null && orgRelation != null) {
					if (Validator.validate(customInvoiceTemplateDTO)) {
						response = invoiceService.saveCustomInvoiceTemplate(customInvoiceTemplateDTO, orgRelation, orgLocation, loggedInUser, loggedInOrgLocations);
					} else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				} else {
					response.setResponse(Result.INVALID_USER.name());
					response.setResponseMessage(Result.INVALID_USER.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/changeCustomInvoiceTemplateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> changeJobFairStatus(HttpServletRequest request, 
			@RequestParam(defaultValue = "0") long templateId,
			@RequestParam(defaultValue="0") Long orgLocationId,
			@RequestParam(defaultValue="0") Long orgRelationId,
			@RequestParam(defaultValue = "0") int status) {
		Response<Boolean> response = new Response<>();
		try {
			User loggedInUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (loggedInUser != null) {
				boolean result = false;
				if(status == -1) {
					result = invoiceService.deleteCustomInvoiceTemplate(templateId);
				} else {
					OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
					result = invoiceService.changeCustomInvoiceTemplateStatus(templateId, status, loggedInUser, orgLocation);
				}
				response.setObject(result);
				response.setResponse(result ? Result.SUCCESS.name() : Result.FAILED.name());
				response.setResponseMessage(result ? Result.SUCCESS.getValue() : Result.FAILED.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
}