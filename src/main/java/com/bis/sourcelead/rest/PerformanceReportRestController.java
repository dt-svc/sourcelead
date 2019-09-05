package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ApplicantBySourceCountDTO;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.DashboardItemsDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.reports.dao.ClientReportDao;
import com.bis.sourcelead.reports.service.ApplicantDeclineService;
import com.bis.sourcelead.reports.service.ApplicantsBackoutReportService;
import com.bis.sourcelead.reports.service.ApplicantsBySourceService;
import com.bis.sourcelead.reports.service.ClientReportService;
import com.bis.sourcelead.reports.service.JobsPipelineReportService;
import com.bis.sourcelead.reports.service.TimeToFillService;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBySourceDTO;
import com.bis.sourcelead.reportsDTO.ClientReportsDTO;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;
import com.bis.sourcelead.reportsDTO.InvoiceReportDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.reportsDTO.JobPipelineDTO;
import com.bis.sourcelead.reportsDTO.TimeToFillDTO;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.dynamicReport.DynamicReport;

@RestController
@RequestMapping("/app")
public class PerformanceReportRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceReportRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	TimeToFillService timeToFillService;

	@Autowired
	UserService userService;

	@Autowired
	ProjectsService projectsService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	RequirementService requirementService;

	@Autowired
	TeamService teamService;

	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;
    
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	 private RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;

	@Autowired
	RolesService rolesService;

	@Autowired
	TabsService tabsService;
	
	@Autowired
	JobsPipelineReportService jobsPipelineReportService;
	
	@Autowired
	OrgRelationsService orgRelationService;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementReportService requirementReportService;
	
	@Autowired
	TimeZoneService timeZoneService;

	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	ClientVendorContactsIndexHelper clientVendorContactsIndexHelper;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	@Autowired
	private ReportsUtil reportsUtil;
	
	@Autowired
	private LetterHeadService letterHeadService;
	
	@Autowired
	private EmployeeVerificationTemplateService employeeVerificationTemplateService;
	
	@Autowired
	ActionHistoryService actionHistoryService;
	
	@Autowired 
	OrgLocationService orgLocationService;
	
	@Autowired
	ProjectAssignTasksService projectAssignTasksService;
	
	@Autowired
	ApplicantsBackoutReportService applicantsBackoutReportService;
	
	@Autowired
	DynamicReport dynamicReport;
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private RequirementOffersService requirementOffersService;
	
	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	 private ConversionUtil conversionUtil;
	
	@Autowired
	 private RequirementSubmissionDao requirementSubmissionDao;
	
	 @Autowired
	 private RequirementLocationsService requirementLocationsService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 private ApplicantDeclineService applicantDeclineService;
	 
	 @Autowired
	 private ApplicantsBySourceService applicantsBySourceService;
	 
	 @Autowired
	 ClientReportService clientReportService;
	 
	 @Autowired
	 ClientReportDao clientReportDao;
	 
	 @Autowired
	 RequirementOfferHistoryService requirementOfferHistoryService;
	 
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/getTopFiveConracts", method = RequestMethod.GET)
		public List<ContractReportsDTO> getTopTenFiveConracts(ContractReportsDTO contractReportsDTO, HttpServletRequest request) throws Exception {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			List<ContractReportsDTO> contractList = new ArrayList<>();
			Map<String, Integer> clientcontractList = clientReportService.getContractsListOFAllClients(orgLocation,user,userRoleName,contractReportsDTO);
			
			for(Map.Entry<String,Integer> entry : clientcontractList.entrySet()){
				ContractReportsDTO conDto = new ContractReportsDTO();
				if(!"MsaCount".equalsIgnoreCase(entry.getKey()) && !"SowCount".equalsIgnoreCase(entry.getKey())
						&& !"NdaCount".equalsIgnoreCase(entry.getKey()) && !"NsaCount".equalsIgnoreCase(entry.getKey())){
					conDto.setClientName(entry.getKey());
					conDto.setContractsCount(entry.getValue());
				}
				contractList.add(conDto);
			}
			
			Collections.sort(contractList, new Comparator() {
				@Override
				public int compare(Object obj1, Object obj2)
				{
					ContractReportsDTO condto1 = (ContractReportsDTO) obj1;
					ContractReportsDTO condto2 = (ContractReportsDTO) obj2;
					if(condto1.getContractsCount() > condto2.getContractsCount()) return -1;
					if(condto1.getContractsCount() < condto2.getContractsCount()) return 1;
					return 0;
				}
			});
			
			if(clientcontractList != null){
				ContractReportsDTO conDto = new ContractReportsDTO();
				if(clientcontractList.get("MsaCount") != null)
					conDto.setMsaCount(clientcontractList.get("MsaCount"));
				else
					conDto.setMsaCount(0);
				if(clientcontractList.get("SowCount") != null)
					conDto.setSowCount(clientcontractList.get("SowCount"));
				else
					conDto.setSowCount(0);
				if(clientcontractList.get("NdaCount") != null)
					conDto.setNdaCount(0);
				else
					conDto.setNdaCount(0);
				if(clientcontractList.get("NsaCount") != null)
					conDto.setNsaCount(0);
				else
					conDto.setNsaCount(0);	
				contractList.add(conDto);
			}
			
			return contractList;
		}
		
		@RequestMapping(value = "/getTopFiveReceivablesReports", method = RequestMethod.GET)
		public InvoiceReportDTO getTopFiveReceivsblesReports(@RequestParam(value = "type", required = false) String type,@RequestParam(value = "dateFrequency", required = false) String dateFrequency,@RequestParam(value = "fromDate", required = false) String fromDate,@RequestParam(value = "toDate", required = false) String toDate,HttpServletRequest request) throws Exception {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			return clientReportService.getTopFiveReceivsblesReports(orgLocation,user,type,dateFrequency,fromDate,toDate);
			
		}
		
		@RequestMapping(value = "/getTopTenClientReports", method = RequestMethod.GET)

		public List<ClientReportsDTO> getTopTenClientReports(@RequestParam(value = "frequency", required = false) String frequency,@RequestParam(value = "fromDate", required = false) String fromDate,@RequestParam(value = "toDate", required = false) String toDate,HttpServletRequest request) throws Exception {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			List<ClientReportsDTO> clientDtoList = new ArrayList<>();
			Date[] freqDates = new Date[2];
			if("Custom".equalsIgnoreCase(frequency)){
				freqDates[0] = commonUtil.getDateFormat(fromDate);
				freqDates[1] = commonUtil.getDateFormat(toDate);
			}else{
				freqDates = reportsUtil.getJobFilterDate(frequency,orgLocation);
			}
			List<OrgRelations> clientList =  clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), user.getId());
			if(clientList != null && clientList.size() > 0) {
				for(OrgRelations orgRelation : clientList){
					ClientReportsDTO clientDto = new ClientReportsDTO();
					if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocation.getId().longValue()){
						clientDto.setClientName(orgRelation.getResponderLocationId().getOrgLocationName());
					}else{
						clientDto.setClientName(orgRelation.getLocationId().getOrgLocationName());
					}
					clientDto.setOrgRelationUUID(orgRelation.getUUID());
					List<OrgSpoc> spocList = clientReportService.getClientSpocListByOrgRelationid(orgRelation.getId(),orgLocation.getId(),user.getId(),freqDates);
					if(spocList != null && spocList.size() > 0)
						clientDto.setNoOfContacts(spocList.size());
					else
						clientDto.setNoOfContacts(0);
					
					List<Requirement> reqList = clientReportService.getClientJobsByOrgRelationId(orgRelation.getId(),orgLocation.getId(),user.getId(),freqDates,null);
					if(reqList != null && reqList.size() > 0)
						clientDto.setReleasedJobs(reqList.size());
					else
						clientDto.setReleasedJobs(0);
					for(Requirement req : reqList){
						Map<String,Integer> subListCount = clientReportService.getSubmissionListByRequirementId(req.getId(),orgLocation.getId(),user.getId(),freqDates);
						clientDto.setSubmissionCount(subListCount.get("submissionCount"));
						clientDto.setSubmissionCount(subListCount.get("hiredCount"));
					}
					ContractReportsDTO contractReportsDTO = new ContractReportsDTO();
					contractReportsDTO.setFrequency(frequency);
					if("Custom".equalsIgnoreCase(frequency)){
						contractReportsDTO.setFromDate(commonUtil.dateStrFromDate(freqDates[0]));
						contractReportsDTO.setToDate(commonUtil.dateStrFromDate(freqDates[1]));
					}
					
					List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocation, user,orgRelation.getId(),userRoleName,contractReportsDTO);
					if(conList != null && conList.size() > 0){
						clientDto.setTotalContracts(conList.size());
					}else{
						clientDto.setTotalContracts(0);
					}
					
					List<Invoice> invoiceList = clientReportService.getClientBasedInvoiceLists(orgRelation.getId(), orgLocation.getId(), Constants.RECEIVABLE,null,freqDates);
					if(invoiceList != null && invoiceList.size() > 0) {
						float paidTotalAmount = 0f;
						for (Invoice invoice : invoiceList) {
							if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus())) && 
									!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
								Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
								paidTotalAmount = paidTotalAmount + paidAmount;
								clientDto.setTotalPaidAmount(paidTotalAmount);
							}
						}
						if(clientDto.getTotalPaidAmount() != 0)
						clientDtoList.add(clientDto);
					}
					
					
				}
			}
			
			clientDtoList.sort(new Comparator<ClientReportsDTO>() {
				@Override
				public int compare(ClientReportsDTO o1, ClientReportsDTO o2) {
					if(o1 != null && (Float)o1.getTotalPaidAmount() != null) {
						return ((Float)o2.getTotalPaidAmount()).compareTo((Float)o1.getTotalPaidAmount());
					}
					return 0;
				}
			});
			return clientDtoList;
		}

	   
		
		@RequestMapping(value = "/getTopTenClientContactReports", method = RequestMethod.GET)
		public List<ClientReportsDTO> getTopTenClientContactReports(@RequestParam(value = "frequency", required = false) String frequency,@RequestParam(value = "fromDate", required = false) String fromDate,@RequestParam(value = "toDate", required = false) String toDate,HttpServletRequest request) throws Exception {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			List<ClientReportsDTO> clientDtoList = new ArrayList<>();
			Date[] freqDates = new Date[2];
			if("Custom".equalsIgnoreCase(frequency)){
				freqDates[0] = commonUtil.getDateFormat(fromDate);
				freqDates[1] = commonUtil.getDateFormat(toDate);
			}else{
				freqDates = reportsUtil.getJobFilterDate(frequency,orgLocation);
			}
			List<OrgRelations> clientList =  clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), loginUser.getId());
			if(clientList != null && clientList.size() > 0) {
				for(OrgRelations orgRelation : clientList){
					ClientReportsDTO clientDto = new ClientReportsDTO();
					if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocation.getId().longValue()){
						clientDto.setClientName(orgRelation.getResponderLocationId().getOrgLocationName());
					}else{
						clientDto.setClientName(orgRelation.getLocationId().getOrgLocationName());
					}
					clientDto.setOrgRelationUUID(orgRelation.getUUID());
					List<OrgSpoc> spocList = clientReportService.getClientSpocListByOrgRelationid(orgRelation.getId(),orgLocation.getId(),loginUser.getId(),freqDates);
					if(spocList != null && spocList.size() > 0)
						clientDto.setNoOfContacts(spocList.size());
					else
						clientDto.setNoOfContacts(0);
					

					for(OrgSpoc spoc : spocList){
						StringBuilder sb = new StringBuilder();
						if(StringUtils.isNotBlank(spoc.getClientUser().getFirstName()) && StringUtils.isNotBlank(spoc.getClientUser().getLastName()))
							sb.append(spoc.getClientUser().getFirstName()).append(" ").append(spoc.getClientUser().getLastName());
						else
							sb.append(spoc.getClientUser().getFirstName());
						clientDto.setClientContactName(sb.toString());
						clientDto.setOrgSpocId(spoc.getId());
						clientDto.setOrgSpocUUID(spoc.getUUID());
						
						List<Requirement> reqList = clientReportService.getClientJobsByOrgRelationId(orgRelation.getId(),orgLocation.getId(),loginUser.getId(),freqDates,spoc.getId());
						if(reqList != null && reqList.size() > 0)
							clientDto.setReleasedJobs(reqList.size());
						else
							clientDto.setReleasedJobs(0);
						
						for(Requirement req : reqList){
							Map<String,Integer> subListCount = clientReportService.getSubmissionListByRequirementId(req.getId(),orgLocation.getId(),loginUser.getId(),freqDates);
							clientDto.setSubmissionCount(subListCount.get("submissionCount"));
							clientDto.setSubmissionCount(subListCount.get("hiredCount"));
						}
						ContractReportsDTO contractReportsDTO = new ContractReportsDTO();
						contractReportsDTO.setFrequency(frequency);
						if("Custom".equalsIgnoreCase(frequency)){
							contractReportsDTO.setFromDate(commonUtil.dateStrFromDate(freqDates[0]));
							contractReportsDTO.setToDate(commonUtil.dateStrFromDate(freqDates[1]));
						}
						contractReportsDTO.setContactUser(spoc.getClientUser().getId());
						List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocation, loginUser,orgRelation.getId(),userRoleName,contractReportsDTO);
						if(conList != null && conList.size() > 0){
							clientDto.setTotalContracts(conList.size());
						}else{
							clientDto.setTotalContracts(0);
						}
						
						List<Invoice> invoiceList = clientReportService.getClientBasedInvoiceLists(orgRelation.getId(), orgLocation.getId(), Constants.RECEIVABLE,spoc.getClientUser().getId(),freqDates);
						float paidTotalAmount = 0f;
						for (Invoice invoice : invoiceList) {
							if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus())) && 
									!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
								Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
								paidTotalAmount = paidTotalAmount + paidAmount;
								clientDto.setTotalPaidAmount(paidTotalAmount);
							}
						}
						if(clientDto.getTotalPaidAmount() != 0)
						clientDtoList.add(clientDto);
					}
					
				}
			}
			
			clientDtoList.sort(new Comparator<ClientReportsDTO>() {
				@Override
				public int compare(ClientReportsDTO o1, ClientReportsDTO o2) {
					if(o1 != null && (Float)o1.getTotalPaidAmount() != null) {
						return ((Float)o2.getTotalPaidAmount()).compareTo((Float)o1.getTotalPaidAmount());
					}
					return 0;
				}
			});
			return clientDtoList;
		}
		
		@RequestMapping(value = "/getClientreportSummary", method = RequestMethod.POST)
		public ClientReportsDTO getClientreportSummary(@RequestParam(value = "orgRelationUUID", required = false) String orgRelationUUID,@RequestParam(value = "orgSpocUUID", required = false) String orgSpocUUID,@RequestParam(value = "invoiceType", required = false) String invoiceType,HttpServletRequest request) throws Exception {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			
			Float totalPaidAmount = 0f;
			Float totalDraftAmount = 0f;
			Float dueAfterInvoiceAmount = 0f;
			Float openInvoiceTotal = 0f;
			int totalPaidCount = 0;
			
			int draftCount = 0;
			int waitingCount = 0;
			int signCount = 0;
			int counterSignCount = 0;
			int finalizedCount = 0;
			int expiredCount = 0;
			int rejectedCount = 0;
			
			int openCount = 0;
			int onholdCount = 0;
			int cancelledCount = 0;
			int filledClosedCount = 0;
			int unfilledClosedCount = 0;
			int closedCount = 0;
			int totalPostedCount = 0;
			
			Date todayDate = new Date();
			ClientReportsDTO clientReportDTO = new ClientReportsDTO();
			OrgRelations orgRel = null;
			if(orgRelationUUID != null && orgRelationUUID !="") {
				orgRel = orgRelationsService.getOrgRelationsByUUID(orgRelationUUID);
				if(orgRel.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
					clientReportDTO.setClientName(orgRel.getResponderLocationId().getOrgLocationName());
				}
				if(orgRel.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					clientReportDTO.setClientName(orgRel.getLocationId().getOrgLocationName());
				}
			}
			OrgSpoc spoc = null;
			if(orgSpocUUID != null && orgSpocUUID != "") {
				 spoc = orgSpocService.getOrgSpocByUUID(orgSpocUUID);
				StringBuilder spocName = new StringBuilder();
					if(spoc.getClientUser().getLastName() != null) {
						clientReportDTO.setClientContactName(spocName.append(spoc.getClientUser().getFirstName()).append(" ").append(spoc.getClientUser().getLastName()).toString());
					}else {
						clientReportDTO.setClientContactName(spoc.getClientUser().getFirstName());
					}
			}
			
			List<Invoice> clientInvoiceList = null;
			if(orgRel != null) {
				 clientInvoiceList = clientReportService.getClientBasedInvoiceLists(orgRel.getId(), orgLocation.getId(), invoiceType,null,null);
			}else if(spoc != null) {
				 clientInvoiceList = clientReportService.getClientBasedInvoiceLists(spoc.getOrgRelations().getId(), orgLocation.getId(), invoiceType,spoc.getClientUser().getId(),null);
			}
			
			if(clientInvoiceList != null && clientInvoiceList.size() >0) {
				for(Invoice invoice : clientInvoiceList) {
					if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus())) && 
							!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
						Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
						totalPaidAmount += paidAmount;
						if(Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus())) {
							totalPaidCount += 1;
						}
					}
					
					if(Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) || Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())){
						totalDraftAmount += Float.parseFloat(invoice.getDueAmount()) ;
					}
					
					
					if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) &&
							(Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))){
						
						String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate());
						Date invoiceDueDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),dueDate);
						
						Float dueAmount = Float.parseFloat(invoice.getDueAmount());
						
						if(dueAmount > 0){
						
							if(todayDate.after(invoiceDueDate)){
								dueAfterInvoiceAmount += Float.parseFloat(invoice.getDueAmount());
							}else{
								openInvoiceTotal += Float.parseFloat(invoice.getDueAmount());
							}
						}
						
						
					}
				}
			}
			
			ContractReportsDTO contractReportsDTO = new ContractReportsDTO();
			List<Contracts> clientContractsList = null;
			if(orgRelationUUID != null && orgRelationUUID !="") {
				 clientContractsList = clientReportService.getClientBasedContractsLists(orgLocation,user,userRoleName,orgRel.getId(),contractReportsDTO);
			}else if(orgSpocUUID != null && orgSpocUUID != "") {
				contractReportsDTO.setContactUser(spoc.getClientUser().getId());
				 clientContractsList = clientReportService.getClientBasedContractsLists(orgLocation,user,userRoleName,spoc.getOrgRelations().getId(),contractReportsDTO);
			}
			
			if(clientContractsList != null && clientContractsList.size() > 0) {
				for (Contracts contracts : clientContractsList) {
					
					if(Constants.DRAFT.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
						draftCount += 1;
					}else if(Constants.WAITING_FOR_APPROVER.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
						waitingCount += 1;
					}else if(Constants.WAITING_TO_BE_SIGNED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus()) || Constants.PENDING.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
						signCount += 1;
					}else if(Constants.WAITING_TO_BE_VERIFY.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
						counterSignCount += 1;
					}else if(Constants.FINALIZED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
						finalizedCount =+ 1;
					}else if(Constants.EXPIRED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
						expiredCount += 1;
					}else if(Constants.REJECTED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
						rejectedCount += 1;
					}
				}
			}
			
			List<Requirement> reqList = null;
			if(orgRelationUUID != null && orgRelationUUID !="") {
				 reqList = clientReportService.getClientJobsByOrgRelationId(orgRel.getId(),orgLocation.getId(),user.getId(),null,null);
			}else if(orgSpocUUID != null && orgSpocUUID != "") {
				reqList = clientReportService.getClientJobsByOrgRelationId(spoc.getOrgRelations().getId(),orgLocation.getId(),user.getId(),null,spoc.getClientUser().getId());
			}
			if(reqList != null && reqList.size() > 0) {
				int submissionCount = 0;
				int shortListCount  = 0;
				int submitToClientCount  = 0;
				int interviewedCount  = 0;
				int offermadeCount  = 0;
				int offeracceptedCount  = 0;
				int rejectCount  = 0;
				for(Requirement req : reqList) {
					Map<String, Integer> reqSubmissionCount = clientReportService.getSubmissionListByRequirementId(req.getId(), orgLocation.getId(), user.getId(), null);
					
					submissionCount = submissionCount + reqSubmissionCount.get("submissionCount");
					shortListCount = shortListCount + reqSubmissionCount.get("shortListCount");
					submitToClientCount = submitToClientCount + reqSubmissionCount.get("submitToClientCount");
					interviewedCount = interviewedCount + reqSubmissionCount.get("interviewedCount");
					offermadeCount = offermadeCount + reqSubmissionCount.get("offermadeCount");
					offeracceptedCount = offeracceptedCount + reqSubmissionCount.get("offeracceptedCount");
					rejectCount = rejectCount + reqSubmissionCount.get("rejectedCount");
					
					if("open".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
						openCount += 1;
					}else if("onhold".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
						onholdCount +=1;
					}else if("cancel".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
						cancelledCount +=1;
					}else if("filledClosed".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
						filledClosedCount +=1;
					}else if("unFilledClosed".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
						unfilledClosedCount +=1;
					}else if("closed".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
						closedCount += 1;
					}
					if(Constants.YES.equalsIgnoreCase(req.getIsClientJob())) {
						totalPostedCount += 1;
					}
					}
					clientReportDTO.setApplicantsCount(submissionCount);
					clientReportDTO.setShortlistedCount(shortListCount);
					clientReportDTO.setSubmitToClientCount(submitToClientCount);
					clientReportDTO.setInterviewsCount(interviewedCount);
					clientReportDTO.setOfferedCount(offermadeCount);
					clientReportDTO.setOfferacceptedCount(offeracceptedCount);
					clientReportDTO.setRejectedCount(rejectCount);
				}

			clientReportDTO.setTotalPaidAmount(totalPaidAmount);
			clientReportDTO.setTotalPaidbillCount(totalPaidCount);
			clientReportDTO.setTotalDraftAmount(totalDraftAmount);
			clientReportDTO.setTotalOverdueAmount(dueAfterInvoiceAmount);
			clientReportDTO.setTotalSentAmount(openInvoiceTotal);
			clientReportDTO.setTotalReceivableAmount(totalDraftAmount+totalPaidAmount+dueAfterInvoiceAmount+openInvoiceTotal);
			
			clientReportDTO.setDraftCount(draftCount);
			clientReportDTO.setWaitingCount(waitingCount);
			clientReportDTO.setSignCount(signCount);
			clientReportDTO.setCounterSignCount(counterSignCount);
			clientReportDTO.setFinalizedCount(finalizedCount);
			clientReportDTO.setExpiredCount(expiredCount);
			clientReportDTO.setRejectedCount(rejectedCount);
			
			clientReportDTO.setOpenCount(openCount);
			clientReportDTO.setOnholdCount(onholdCount);
			clientReportDTO.setCancelledCount(cancelledCount);
			clientReportDTO.setFilledClosedCount(filledClosedCount);
			clientReportDTO.setUnfilledClosedCount(unfilledClosedCount);
			clientReportDTO.setClosedCount(closedCount);
			clientReportDTO.setTotalPostedCount(totalPostedCount);
			
			return clientReportDTO;
		}
	 
	 @RequestMapping(value = "/getAllRecruiterReportsExport")
		public File getAllRecruiterReportsExport(UserRoleDTO userRoleDto,UserDTO userDTO,
				@RequestParam(value="timePeriod", required=false) String timePeriod,
				@RequestParam(value="customStartDate", required=false) String customStartDate,
				@RequestParam(value="customEndDate", required=false) String customEndDate,
				HttpServletRequest request,HttpServletResponse response) throws Exception {

		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		      Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		      String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		      String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				
				Date[] searchDates = null;
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
					String[] orgTeams = null;
					List<Long> teamsList = new ArrayList<>();
					if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
					   orgTeams = userRoleDto.getOrgTeams().split(",");
						for(int i=0; i < orgTeams.length; i++) {
							teamsList.add(Long.parseLong(orgTeams[i]));
						}
					}
					//Roles List
					String[] roleNames = null;
					List<Long> rolesList = new ArrayList<>();
					if(userRoleDto.getUserRoleName() != null && !userRoleDto.getUserRoleName().isEmpty()) {
					   roleNames = userRoleDto.getUserRoleName().split(",");
						for(int i=0; i < roleNames.length; i++) {
							rolesList.add(Long.parseLong(roleNames[i]));
						}
					}
					String type=null;
				if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
					type=userRoleDto.getType();
				}
				ResponseList<UserDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,rolesList);
				
				StringBuilder sbRoles = new StringBuilder();
				String roleName = null;
				if(roleNames !=null){
				List<Roles> roles = rolesService.getRolesListUsingMultipleIds(roleNames);
				for(Roles r: roles){
					sbRoles.append(r.getDisplayName()).append(",").toString();
				}
				roleName = sbRoles.deleteCharAt(sbRoles.length()-1).toString();
				}
				
				StringBuilder sbTeams = new StringBuilder();
				String teamName = null;
				if(orgTeams !=null){
				List<Team> teams = teamService.getTeamListByMultipleIds(orgTeams,orgLocations.getId());
				for(Team t: teams){
					sbTeams.append(t.getName()).append(",").toString();
				}
				teamName = sbTeams.deleteCharAt(sbTeams.length()-1).toString();
				}
				
				File file = null;
					file = pdfUtil.recruiterReprtsListPdfTable(userDTOList, orgLocations,searchDates,roleName,teamName);
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);

				StringBuilder filename = new StringBuilder();
				response.setHeader("Content-Disposition",
						filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();
				if (file != null) {
					file.delete();
				}
				return file;
		}
	 
	 @RequestMapping(value = "/exportAllApplicantJoinedList")
		public void exportAllApplicantJoinedList(ApplicantsJoinedReportDTO userRoleDto,@RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
				@RequestParam(value="exportType", required=false) String exportType,
				ApplicantsJoinedReportDTO userDTO,HttpServletRequest request, HttpServletResponse response) throws Exception {
		      Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			  OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocId);
		      Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		      String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		      String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		      Workbook createExcelworkbook =  new XSSFWorkbook();
		      StringBuilder excelSB = new StringBuilder();
				
				Date[] searchDates = null;
				SimpleDateFormat sdf = new SimpleDateFormat(orgLocations.getDateFormat());
				
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
					List<Long> teamsList = new ArrayList<>();
					if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
						String[] orgTeams = userRoleDto.getOrgTeams().split(",");
						for(int i=0; i < orgTeams.length; i++) {
							teamsList.add(Long.parseLong(orgTeams[i]));
						}
					}
					List<Long> clientIdsList = new ArrayList<>();
					if(userRoleDto.getClients() != null && !userRoleDto.getClients().isEmpty()) {
						String[] clients = userRoleDto.getClients().split(",");
						for(int i=0; i < clients.length; i++) {
							clientIdsList.add(Long.parseLong(clients[i]));
						}
					}
					String type=null;
				if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
					type=userRoleDto.getType();
				}
				String[] tableColumns = { "name", "requirementCount", "offerCount", "joinedCount", "incentives"};
				int sortColumnIndex = 1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default descending order
				if (userDTO.getOrder() != null && !userDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(userDTO.getOrder().get(0).get("column"));
					sortColumnOrder = userDTO.getOrder().get(0).get("dir");
				}
				userDTO.setSortColumn(tableColumns[sortColumnIndex]);
				userDTO.setSortOrder(sortColumnOrder);
		         Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("applicantsjoioinedList ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
//				ResponseList<UserDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,clientIdsList);
				ResponseList<ApplicantsJoinedReportDTO> responseList = userService.getApplicantsJoinedReport(userRoleDto,orgLocations.getId(),searchDates,teamsList,type,clientIdsList);
		
		if (responseList.getData() != null && !responseList.getData().isEmpty()) {
			for (ApplicantsJoinedReportDTO ajrDTO : responseList.getData()) {
				Set<String> datesSet = new LinkedHashSet<>();
				if (timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
					datesSet.add("Date<<>>" + sdf.format(Calendar.getInstance().getTime()));
				} else if (timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)
						|| timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_15_DAYS)
						|| timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_1_MONTH)) {
					Map<String, Long> datesMap = userService.getApplicantsJoinedCountByDates(ajrDTO.getCreatedBy(), searchDates[0], searchDates[1], orgLocations.getDateFormat());
					Calendar c = Calendar.getInstance();
					c.setTime(searchDates[0]);
					while (c.getTime().before(searchDates[1])) {
						String cDate = sdf.format(c.getTime());
						datesSet.add(cDate + "<<>>" + (datesMap.containsKey(cDate) ? datesMap.get(cDate) : "0"));
						c.add(Calendar.DATE, 1);
					}
				} else if (timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_3_MONTHS)
						|| timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_YEAR)
						|| timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_YTD)
						|| "custom".equalsIgnoreCase(timePeriod)) {
					Map<String, Long> datesMap = userService.getApplicantsJoinedCountByDates(ajrDTO.getCreatedBy(), searchDates[0], searchDates[1], orgLocations.getDateFormat());
					Map<String, Long> datesMapNew = new HashMap<>();
					SimpleDateFormat monthsSDF = new SimpleDateFormat("MMMM");
					for(String date : datesMap.keySet()) {
						String date1 = monthsSDF.format(new Date(sdf.parse(date).getTime()));
						long value = 0;
						if(datesMapNew.containsKey(date1)) {
							value = datesMapNew.get(date1);
						}
						value += datesMap.get(date);
						datesMapNew.put(date1, value);
					}
//					LinkedHashSet<String> months = CalendarUtil.getMonthStringsFromStartDateAndEndDate(searchDates[0], searchDates[1]);
					Calendar fromC = Calendar.getInstance();
		            fromC.setTime(searchDates[0]);
		            Calendar toC = Calendar.getInstance();
		            toC.setTime(searchDates[1]);
		            Set<String> months = new LinkedHashSet<>();
		            while (fromC.get(Calendar.MONTH) <= toC.get(Calendar.MONTH)) {
//		                System.out.println(fromC.get(Calendar.MONTH) + " " + monthsSDF.format(fromC.getTime()));
		                months.add(monthsSDF.format(fromC.getTime()));
		                fromC.roll(Calendar.MONTH, true);
		            }
					for(String month : months) {
						if(datesMapNew.containsKey(month)) {
							datesSet.add(month + "<<>>" + datesMapNew.get(month));
						} else {
							datesSet.add(month + "<<>>0");
						}
					}
				} else {
					// custom dates
				}
				ajrDTO.setDatesList(datesSet);
			}
		}
				
				
				File file = null;
	             if(!"pdf".equalsIgnoreCase(exportType)){
	                 createExcelsheet = userService.exportAllApplicantJoinedList(createExcelsheet,createExcelworkbook,responseList,orgLocations);
	                   StringBuilder templateSB = new StringBuilder();
	                    file = File.createTempFile(templateSB.append("Applicants_Joioined_List").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
	                    file.setReadable(true);
	                    
	                  FileOutputStream fos = new FileOutputStream(file);
	                  try {
	                     createExcelworkbook.write(fos);
	                     fos.close();
	                 } catch (IOException e) {
	                     e.printStackTrace();
	                 }         
	             }
	             else{
	                 file = userService.exportAllApplicantJoinedListPdf(responseList,orgLocations,searchDates);
	             
	             }
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
	                if(file!= null){
	                    file.delete();
	                }			
	    }
		
	 
		@SuppressWarnings("unused")
		@RequestMapping(value = "/getApplicantBySourceDetails", method = RequestMethod.GET)
		public ResponseList<ApplicantsBySourceDTO> getApplicantBySourceDetails(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
			
			ResponseList<ApplicantsBySourceDTO> response = new ResponseList<>();
			List<ApplicantsBySourceDTO> applicantBySourceList = new ArrayList<>();
			try{
				User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(loginUser != null){
					Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
					List<Long> jobIdList = new ArrayList<>();
					DecimalFormat df = new DecimalFormat("#.00");
					
					if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
						String[] jobIds = jobFilterDTO.getJobIds().split(",");
						for(int i=0; i<jobIds.length; i++) {
							jobIdList.add(Long.valueOf(jobIds[i]));
						}
					}else {
						jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
					}
					
					Date[] searchDates = null;
					
					if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
						if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
							searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
						}else {
							Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
				    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
				    	   
				    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
				    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
				    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
				    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
				    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
				    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
				    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
				    	    fromDate = TZ_DF_TS.parse(startDateString);
				    	    toDate = TZ_DF_TS.parse(endDateString);
				    	    
				    	    searchDates = new Date[2];
				    	    //
				    	  
				    	    //
				    	    searchDates[0] = fromDate;
				    	    searchDates[1] = toDate;
						}
					}
					
					
					if(jobIdList != null && jobIdList.size() > 0) {
					
					// Crowd Sourcing Reports Start
						
					ApplicantsBySourceDTO crowdSourcingApplicantsDTO = new ApplicantsBySourceDTO();
					
					ApplicantBySourceCountDTO applicantBySourceCountDTO =  applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates);
					
					Integer crowdsourcingApplicantsCount = applicantBySourceCountDTO.getSubmissionCount() == null ? 0 :applicantBySourceCountDTO.getSubmissionCount().intValue();
					crowdSourcingApplicantsDTO.setApplicantsCount(crowdsourcingApplicantsCount);
					Integer crowdsourcingSortlistedCount = applicantBySourceCountDTO.getShortlistCount() == null ? 0 :applicantBySourceCountDTO.getShortlistCount().intValue();
					crowdSourcingApplicantsDTO.setShortlistedCount(crowdsourcingSortlistedCount);
					
					Integer crowdsourcingSubmitToClientCount = applicantBySourceCountDTO.getSubmitToClientCount() == null ? 0 :applicantBySourceCountDTO.getSubmitToClientCount().intValue();
					crowdSourcingApplicantsDTO.setSubmitToClientCount(crowdsourcingSubmitToClientCount);	
					
					Integer crowdsourcingInterviewCount = applicantBySourceCountDTO.getInterviewCount() == null ? 0 : applicantBySourceCountDTO.getInterviewCount().intValue();
					Integer crowdsourcingClientInterviewCount = applicantBySourceCountDTO.getClientInterviewCount() == null ? 0 : applicantBySourceCountDTO.getClientInterviewCount().intValue();
					crowdsourcingInterviewCount = crowdsourcingInterviewCount+crowdsourcingClientInterviewCount;
					crowdSourcingApplicantsDTO.setInterviewsCount(crowdsourcingInterviewCount);
					
					Integer crowdsourcingRejectedCount = applicantBySourceCountDTO.getRejectedCount() == null ? 0 : applicantBySourceCountDTO.getRejectedCount().intValue();
					crowdSourcingApplicantsDTO.setRejectedCount(crowdsourcingRejectedCount);
					
					Integer crowdsourcingOfferedCount = applicantBySourceCountDTO.getOffermadeCount() == null ? 0 : applicantBySourceCountDTO.getOffermadeCount().intValue();
					Integer crowdsourcingClientOfferedCount = applicantBySourceCountDTO.getClientOffermadeCount() == null ? 0 :applicantBySourceCountDTO.getClientOffermadeCount().intValue();
					crowdsourcingOfferedCount = crowdsourcingOfferedCount+crowdsourcingClientOfferedCount;
					crowdSourcingApplicantsDTO.setOfferedCount(crowdsourcingOfferedCount);
					
					Integer crowdsourcingOfferAcceptedCount =applicantBySourceCountDTO.getOfferAcceptedCount() == null ? 0 : applicantBySourceCountDTO.getOfferAcceptedCount().intValue();
					Integer crowdsourcingClientOfferAcceptedCount =applicantBySourceCountDTO.getClientOfferAcceptedCount() == null ? 0 :applicantBySourceCountDTO.getClientOfferAcceptedCount().intValue();
					crowdsourcingOfferAcceptedCount = crowdsourcingOfferAcceptedCount+crowdsourcingClientOfferAcceptedCount;
					crowdSourcingApplicantsDTO.setOfferAcceptedCount(crowdsourcingOfferAcceptedCount);
					
					Integer crowdsourcingOfferRejectedCount = applicantBySourceCountDTO.getOfferRejectedCount() == null ? 0 :applicantBySourceCountDTO.getOfferRejectedCount().intValue();
					crowdSourcingApplicantsDTO.setOfferRejectedCount(crowdsourcingOfferRejectedCount);
					
					Integer crowdsourcingOfferBackoutCount = applicantBySourceCountDTO.getBackoutCount() == null ? 0 :applicantBySourceCountDTO.getBackoutCount().intValue();
					crowdSourcingApplicantsDTO.setBackoutCount(crowdsourcingOfferBackoutCount);
					
					crowdSourcingApplicantsDTO.setCategory("Job Seeker");
					crowdSourcingApplicantsDTO.setApplicantsCount(crowdsourcingApplicantsCount);
					crowdSourcingApplicantsDTO.setShortlistedCount(crowdsourcingSortlistedCount);
					crowdSourcingApplicantsDTO.setSubmitToClientCount(crowdsourcingSubmitToClientCount);
					crowdSourcingApplicantsDTO.setInterviewsCount(crowdsourcingInterviewCount);
					crowdSourcingApplicantsDTO.setRejectedCount(crowdsourcingRejectedCount);
					crowdSourcingApplicantsDTO.setOfferedCount(crowdsourcingOfferedCount);
					crowdSourcingApplicantsDTO.setOfferAcceptedCount(crowdsourcingOfferAcceptedCount);
					crowdSourcingApplicantsDTO.setOfferRejectedCount(crowdsourcingOfferRejectedCount);
					crowdSourcingApplicantsDTO.setBackoutCount(crowdsourcingOfferBackoutCount);
					
					// // Crowd Sourcing Reports End.
					
					// vendors Reports
					
					ApplicantsBySourceDTO vendorSourcingApplicantsDTO = new ApplicantsBySourceDTO();
					
					ApplicantBySourceCountDTO vendorApplicantBySourceCountDTO = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates);
					
					Integer vendorSourcingApplicantCount = vendorApplicantBySourceCountDTO.getSubmissionCount() == null ? 0 : vendorApplicantBySourceCountDTO.getSubmissionCount().intValue();
					vendorSourcingApplicantsDTO.setApplicantsCount(vendorSourcingApplicantCount);		
					
					Integer vendorSourcingShortlistCount = vendorApplicantBySourceCountDTO.getShortlistCount() == null ? 0 : vendorApplicantBySourceCountDTO.getShortlistCount().intValue();
					vendorSourcingApplicantsDTO.setShortlistedCount(vendorSourcingShortlistCount);
					
					Integer vendorSourcingSubmitToClientCount = vendorApplicantBySourceCountDTO.getSubmitToClientCount() == null ? 0 : vendorApplicantBySourceCountDTO.getSubmitToClientCount().intValue();
					vendorSourcingApplicantsDTO.setSubmitToClientCount(vendorSourcingSubmitToClientCount);
					
					Integer vendorSourcingInterviewCount = vendorApplicantBySourceCountDTO.getInterviewCount() == null ? 0 : vendorApplicantBySourceCountDTO.getInterviewCount().intValue();
					Integer vendorSourcingClientInterviewCount = vendorApplicantBySourceCountDTO.getClientInterviewCount() == null ? 0 : vendorApplicantBySourceCountDTO.getClientInterviewCount().intValue();
					vendorSourcingInterviewCount = vendorSourcingInterviewCount+vendorSourcingClientInterviewCount;
					vendorSourcingApplicantsDTO.setInterviewsCount(vendorSourcingInterviewCount);	
					
					Integer vendorSourcingRejectedCount = vendorApplicantBySourceCountDTO.getRejectedCount() == null ? 0 :vendorApplicantBySourceCountDTO.getRejectedCount().intValue();
					vendorSourcingApplicantsDTO.setRejectedCount(vendorSourcingRejectedCount);
					
					Integer vendorSourcingOfferedCount = vendorApplicantBySourceCountDTO.getOffermadeCount() == null ? 0 : vendorApplicantBySourceCountDTO.getOffermadeCount().intValue();
					Integer vendorSourcingClientOfferedCount = vendorApplicantBySourceCountDTO.getClientOffermadeCount() == null ? 0 : vendorApplicantBySourceCountDTO.getClientOffermadeCount().intValue();
					vendorSourcingOfferedCount = vendorSourcingOfferedCount+vendorSourcingClientOfferedCount;
					vendorSourcingApplicantsDTO.setOfferedCount(vendorSourcingOfferedCount);
					
					Integer vendorSourcingOfferAcceptedCount = vendorApplicantBySourceCountDTO.getOfferAcceptedCount() == null ? 0 : vendorApplicantBySourceCountDTO.getOfferAcceptedCount().intValue();
					Integer vendorSourcingClientOfferAcceptedCount = vendorApplicantBySourceCountDTO.getClientOfferAcceptedCount() == null ? 0 : vendorApplicantBySourceCountDTO.getClientOfferAcceptedCount().intValue();
					vendorSourcingOfferAcceptedCount = vendorSourcingOfferAcceptedCount+vendorSourcingClientOfferAcceptedCount;
					vendorSourcingApplicantsDTO.setOfferAcceptedCount(vendorSourcingOfferAcceptedCount);
					
					Integer vendorSourcingOfferRejectedCount = vendorApplicantBySourceCountDTO.getOfferRejectedCount() == null ? 0 : vendorApplicantBySourceCountDTO.getOfferRejectedCount().intValue();
					vendorSourcingApplicantsDTO.setOfferRejectedCount(vendorSourcingOfferRejectedCount);
					
					Integer vendorSourcingOfferBackoutCount = vendorApplicantBySourceCountDTO.getBackoutCount() == null ? 0 :vendorApplicantBySourceCountDTO.getBackoutCount().intValue();
					vendorSourcingApplicantsDTO.setBackoutCount(vendorSourcingOfferBackoutCount);
					
					vendorSourcingApplicantsDTO.setCategory("Vendors");
					vendorSourcingApplicantsDTO.setApplicantsCount(vendorSourcingApplicantCount);
					vendorSourcingApplicantsDTO.setShortlistedCount(vendorSourcingShortlistCount);
					vendorSourcingApplicantsDTO.setSubmitToClientCount(vendorSourcingSubmitToClientCount);
					vendorSourcingApplicantsDTO.setInterviewsCount(vendorSourcingInterviewCount);
					vendorSourcingApplicantsDTO.setRejectedCount(vendorSourcingRejectedCount);
					vendorSourcingApplicantsDTO.setOfferedCount(vendorSourcingOfferedCount);
					vendorSourcingApplicantsDTO.setOfferAcceptedCount(vendorSourcingOfferAcceptedCount);
					vendorSourcingApplicantsDTO.setOfferRejectedCount(vendorSourcingOfferRejectedCount);
					vendorSourcingApplicantsDTO.setBackoutCount(vendorSourcingOfferBackoutCount);
					
					// vendors end
					
					// FreelanceRecruiter Report Start
					
					ApplicantsBySourceDTO recruiterSourcingApplicantsDTO = new ApplicantsBySourceDTO();
					
					ApplicantBySourceCountDTO freelanceRecruiterApplicantBySourceCountDTO = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates);
					
					Integer recruiterSourcingApplicantCount = freelanceRecruiterApplicantBySourceCountDTO.getSubmissionCount() == null ? 0 : freelanceRecruiterApplicantBySourceCountDTO.getSubmissionCount().intValue();
					recruiterSourcingApplicantsDTO.setApplicantsCount(recruiterSourcingApplicantCount);
					
					Integer recruiterSourcingShortlistCount = freelanceRecruiterApplicantBySourceCountDTO.getShortlistCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getShortlistCount().intValue();
					recruiterSourcingApplicantsDTO.setShortlistedCount(recruiterSourcingShortlistCount);
					
					Integer recruiterSourcingSubmitToClientCount =freelanceRecruiterApplicantBySourceCountDTO.getSubmitToClientCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getSubmitToClientCount().intValue();
					recruiterSourcingApplicantsDTO.setSubmitToClientCount(recruiterSourcingSubmitToClientCount);
					
					Integer recruiterSourcingInterviewCount = freelanceRecruiterApplicantBySourceCountDTO.getInterviewCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getInterviewCount().intValue();
					Integer recruiterSourcingClientInterviewCount =freelanceRecruiterApplicantBySourceCountDTO.getClientInterviewCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getClientInterviewCount().intValue();
					recruiterSourcingInterviewCount = recruiterSourcingInterviewCount+recruiterSourcingClientInterviewCount;
					recruiterSourcingApplicantsDTO.setInterviewsCount(recruiterSourcingInterviewCount);
					
					Integer recruiterSourcingRejectedCount =  freelanceRecruiterApplicantBySourceCountDTO.getRejectedCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getRejectedCount().intValue();
					recruiterSourcingApplicantsDTO.setRejectedCount(recruiterSourcingRejectedCount);
					
					Integer recruiterrSourcingOfferedCount = freelanceRecruiterApplicantBySourceCountDTO.getOffermadeCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getOffermadeCount().intValue();
					Integer recruiterrSourcingClientOfferedCount =freelanceRecruiterApplicantBySourceCountDTO.getClientOffermadeCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getClientOffermadeCount().intValue();
					recruiterrSourcingOfferedCount = recruiterrSourcingOfferedCount+recruiterrSourcingClientOfferedCount;
					recruiterSourcingApplicantsDTO.setOfferedCount(recruiterrSourcingOfferedCount);
					
					Integer recruiterSourcingOfferAcceptedCount =freelanceRecruiterApplicantBySourceCountDTO.getOfferAcceptedCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getOfferAcceptedCount().intValue();
					Integer recruiterSourcingClientOfferAcceptedCount =freelanceRecruiterApplicantBySourceCountDTO.getClientOfferAcceptedCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getClientOfferAcceptedCount().intValue();
					recruiterSourcingOfferAcceptedCount = recruiterSourcingOfferAcceptedCount+recruiterSourcingClientOfferAcceptedCount;
					recruiterSourcingApplicantsDTO.setOfferAcceptedCount(recruiterSourcingOfferAcceptedCount);
					
					Integer recruiterSourcingOfferRejectedCount =freelanceRecruiterApplicantBySourceCountDTO.getOfferRejectedCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getOfferRejectedCount().intValue();
					recruiterSourcingApplicantsDTO.setOfferRejectedCount(recruiterSourcingOfferRejectedCount);
							
					Integer recruiterSourcingOfferBackoutCount = freelanceRecruiterApplicantBySourceCountDTO.getBackoutCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getBackoutCount().intValue();
					recruiterSourcingApplicantsDTO.setBackoutCount(recruiterSourcingOfferBackoutCount);
					
					recruiterSourcingApplicantsDTO.setCategory("Freelance Recruiters");
					recruiterSourcingApplicantsDTO.setApplicantsCount(recruiterSourcingApplicantCount);
					recruiterSourcingApplicantsDTO.setShortlistedCount(recruiterSourcingShortlistCount);
					recruiterSourcingApplicantsDTO.setSubmitToClientCount(recruiterSourcingSubmitToClientCount);
					recruiterSourcingApplicantsDTO.setInterviewsCount(recruiterSourcingInterviewCount);
					recruiterSourcingApplicantsDTO.setRejectedCount(recruiterSourcingRejectedCount);
					recruiterSourcingApplicantsDTO.setOfferedCount(recruiterrSourcingOfferedCount);
					recruiterSourcingApplicantsDTO.setOfferAcceptedCount(recruiterSourcingOfferAcceptedCount);
					recruiterSourcingApplicantsDTO.setOfferRejectedCount(recruiterSourcingOfferRejectedCount);
					recruiterSourcingApplicantsDTO.setBackoutCount(recruiterSourcingOfferBackoutCount);
					
					// FreelanceRecruiter End 
					
					// Internal DB Stage Counts
					
					ApplicantsBySourceDTO internalSourcingApplicantsDTO = new ApplicantsBySourceDTO();
					ApplicantBySourceCountDTO internalSourcingApplicantCountDTO = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates);
					
					internalSourcingApplicantsDTO.setCategory("Internal DB");
					if(internalSourcingApplicantCountDTO != null) {
					Integer internalSourcingApplicantCount = internalSourcingApplicantCountDTO.getSubmissionCount()== null ? 0 : internalSourcingApplicantCountDTO.getSubmissionCount().intValue();
					
					internalSourcingApplicantsDTO.setApplicantsCount(internalSourcingApplicantCount);
					
					Integer internalSourcingShortlistCount = internalSourcingApplicantCountDTO.getShortlistCount() == null ? 0 : internalSourcingApplicantCountDTO.getShortlistCount().intValue();
					internalSourcingApplicantsDTO.setShortlistedCount(internalSourcingShortlistCount);
					
					Integer internalSourcingSubmitToClientCount = internalSourcingApplicantCountDTO.getSubmitToClientCount() == null ? 0 : internalSourcingApplicantCountDTO.getSubmitToClientCount().intValue();
					internalSourcingApplicantsDTO.setSubmitToClientCount(internalSourcingSubmitToClientCount);
					
					Integer internalSourcingInterviewCount = internalSourcingApplicantCountDTO.getInterviewCount() == null ? 0 : internalSourcingApplicantCountDTO.getInterviewCount().intValue();
					Integer internalSourcingClientInterviewCount = internalSourcingApplicantCountDTO.getClientInterviewCount() == null ? 0 : internalSourcingApplicantCountDTO.getClientInterviewCount().intValue();
					internalSourcingInterviewCount = internalSourcingInterviewCount + internalSourcingClientInterviewCount;
					internalSourcingApplicantsDTO.setInterviewsCount(internalSourcingInterviewCount);
					
					Integer internalSourcingRejectedCount = internalSourcingApplicantCountDTO.getRejectedCount() == null ? 0 : internalSourcingApplicantCountDTO.getRejectedCount().intValue();
					internalSourcingApplicantsDTO.setRejectedCount(internalSourcingRejectedCount);
					
					Integer internalSourcingOfferedCount =internalSourcingApplicantCountDTO.getOffermadeCount() == null ? 0 : internalSourcingApplicantCountDTO.getOffermadeCount().intValue();
					Integer internalSourcingClientOfferedCount = internalSourcingApplicantCountDTO.getClientOffermadeCount() == null ? 0 : internalSourcingApplicantCountDTO.getClientOffermadeCount().intValue();
					internalSourcingOfferedCount = internalSourcingOfferedCount+internalSourcingClientOfferedCount;
					internalSourcingApplicantsDTO.setOfferedCount(internalSourcingOfferedCount);
					
					Integer internalSourcingOfferAcceptedCount = internalSourcingApplicantCountDTO.getOfferAcceptedCount() == null ? 0 : internalSourcingApplicantCountDTO.getOfferAcceptedCount().intValue();
					Integer internalSourcingClientOfferAcceptedCount = internalSourcingApplicantCountDTO.getClientOfferAcceptedCount() == null ? 0 : internalSourcingApplicantCountDTO.getClientOfferAcceptedCount().intValue();
					internalSourcingOfferAcceptedCount = internalSourcingOfferAcceptedCount+internalSourcingClientOfferAcceptedCount;
					internalSourcingApplicantsDTO.setOfferAcceptedCount(internalSourcingOfferAcceptedCount);
					
					Integer internalSourcingOfferRejectedCount = internalSourcingApplicantCountDTO.getOfferRejectedCount() == null ? 0 : internalSourcingApplicantCountDTO.getOfferRejectedCount().intValue();
					internalSourcingApplicantsDTO.setOfferRejectedCount(internalSourcingOfferRejectedCount);
					
					Integer internalSourcingBackoutCount = internalSourcingApplicantCountDTO.getBackoutCount() == null ? 0 : internalSourcingApplicantCountDTO.getBackoutCount().intValue();
					internalSourcingApplicantsDTO.setBackoutCount(internalSourcingBackoutCount);
					
					 Double crowdSoruceApplicantCount = ((double)(crowdsourcingApplicantsCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
					 Double crowdSoruceShortlistedCount = ((double)(crowdsourcingSortlistedCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
					 Double crowdSoruceSubmitToClientCount = ((double)(crowdsourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
					 Double crowdSoruceInterviewsCount = ((double)(crowdsourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
					 Double crowdSoruceRejectedCount = ((double)(crowdsourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingRejectedCount))*100;
					 Double crowdSoruceOfferedCount = ((double)(crowdsourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
					 Double crowdSoruceOfferAcceptedCount = ((double)(crowdsourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
					 Double crowdSoruceOfferRejectedCount = ((double)(crowdsourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
					 Double crowdSoruceOfferBackoutCount = ((double)(crowdsourcingOfferBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
					 
					// Internal DB Stage Counts End
					 
					 crowdSourcingApplicantsDTO.setApplicantsAvgCount(df.format(crowdSoruceApplicantCount));
					 crowdSourcingApplicantsDTO.setShortlistedAvgCount(df.format(crowdSoruceShortlistedCount));
					 crowdSourcingApplicantsDTO.setSubmitToClientAvgCount(df.format(crowdSoruceSubmitToClientCount));
					 crowdSourcingApplicantsDTO.setInterviewsAvgCount(df.format(crowdSoruceInterviewsCount));
					 crowdSourcingApplicantsDTO.setRejectedAvgCount(df.format(crowdSoruceRejectedCount));
					 crowdSourcingApplicantsDTO.setOfferedAvgCount(df.format(crowdSoruceOfferedCount));
					 crowdSourcingApplicantsDTO.setOfferAcceptedAvgCount(df.format(crowdSoruceOfferAcceptedCount));
					 crowdSourcingApplicantsDTO.setOfferRejectedAvgCount(df.format(crowdSoruceOfferRejectedCount));
					 crowdSourcingApplicantsDTO.setBackoutAvgCount(df.format(crowdSoruceOfferBackoutCount));
					 applicantBySourceList.add(crowdSourcingApplicantsDTO);
					 
					 Double vendorApplicantCount = ((double)(vendorSourcingApplicantCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
					 Double vendorShortlistedCount = ((double)(vendorSourcingShortlistCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
					 Double vendorSubmitToClientCount = ((double)(vendorSourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
					 Double vendorInterviewsCount = ((double)(vendorSourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
					 Double vendorRejectedCount = ((double)(vendorSourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingInterviewCount))*100;
					 Double vendorOfferedCount = ((double)(vendorSourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
					 Double vendorOfferAcceptedCount = ((double)(vendorSourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
					 Double vendorOfferRejectedCount = ((double)(vendorSourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
					 Double vendorOfferBackoutCount = ((double)(vendorSourcingOfferBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
					 
					 
					 vendorSourcingApplicantsDTO.setApplicantsAvgCount(df.format(vendorApplicantCount));
					 vendorSourcingApplicantsDTO.setShortlistedAvgCount(df.format(vendorShortlistedCount));
					 vendorSourcingApplicantsDTO.setSubmitToClientAvgCount(df.format(vendorSubmitToClientCount));
					 vendorSourcingApplicantsDTO.setInterviewsAvgCount(df.format(vendorInterviewsCount));
					 vendorSourcingApplicantsDTO.setRejectedAvgCount(df.format(vendorRejectedCount));
					 vendorSourcingApplicantsDTO.setOfferedAvgCount(df.format(vendorOfferedCount));
					 vendorSourcingApplicantsDTO.setOfferAcceptedAvgCount(df.format(vendorOfferAcceptedCount));
					 vendorSourcingApplicantsDTO.setOfferRejectedAvgCount(df.format(vendorOfferRejectedCount));
					 vendorSourcingApplicantsDTO.setBackoutAvgCount(df.format(vendorOfferBackoutCount));
					 applicantBySourceList.add(vendorSourcingApplicantsDTO);
					 
					 
					 Double recruiterApplicantCount = ((double)(recruiterSourcingApplicantCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
					 Double recruiterShortlistedCount = ((double)(recruiterSourcingShortlistCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
					 Double recruiterSubmitToClientCount = ((double)(recruiterSourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
					 Double recruiterInterviewsCount = ((double)(recruiterSourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
					 Double recruiterRejectedCount = ((double)(recruiterSourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingInterviewCount))*100;
					 Double recruiterOfferedCount = ((double)(recruiterrSourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
					 Double recruiterOfferAcceptedCount = ((double)(recruiterSourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
					 Double recruiterOfferRejectedCount = ((double)(recruiterSourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
					 Double recruiterOfferBackoutCount = ((double)(recruiterSourcingOfferBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
					 
					 recruiterSourcingApplicantsDTO.setApplicantsAvgCount(df.format(recruiterApplicantCount));
					 recruiterSourcingApplicantsDTO.setShortlistedAvgCount(df.format(recruiterShortlistedCount));
					 recruiterSourcingApplicantsDTO.setSubmitToClientAvgCount(df.format(recruiterSubmitToClientCount));
					 recruiterSourcingApplicantsDTO.setInterviewsAvgCount(df.format(recruiterInterviewsCount));
					 recruiterSourcingApplicantsDTO.setRejectedAvgCount(df.format(recruiterRejectedCount));
					 recruiterSourcingApplicantsDTO.setOfferedAvgCount(df.format(recruiterOfferedCount));
					 recruiterSourcingApplicantsDTO.setOfferAcceptedAvgCount(df.format(recruiterOfferAcceptedCount));
					 recruiterSourcingApplicantsDTO.setOfferRejectedAvgCount(df.format(recruiterOfferRejectedCount));
					 recruiterSourcingApplicantsDTO.setBackoutAvgCount(df.format(recruiterOfferBackoutCount));
					 applicantBySourceList.add(recruiterSourcingApplicantsDTO);
					 
					 
					 Double internalApplicantCount = ((double)(internalSourcingApplicantCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
					 Double internalShortlistedCount = ((double)(internalSourcingShortlistCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
					 Double internalSubmitToClientCount = ((double)(internalSourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
					 Double internalInterviewsCount = ((double)(internalSourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
					 Double internalRejectedCount = ((double)(internalSourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingRejectedCount))*100;
					 Double internalOfferedCount = ((double)(internalSourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
					 Double internalOfferAcceptedCount = ((double)(internalSourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
					 Double internalOfferRejectedCount = ((double)(internalSourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
					 Double internalOfferBackoutCount = ((double)(internalSourcingBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
					 
					 internalSourcingApplicantsDTO.setApplicantsAvgCount(df.format(internalApplicantCount));
					 internalSourcingApplicantsDTO.setShortlistedAvgCount(df.format(internalShortlistedCount));
					 internalSourcingApplicantsDTO.setSubmitToClientAvgCount(df.format(internalSubmitToClientCount));
					 internalSourcingApplicantsDTO.setInterviewsAvgCount(df.format(internalInterviewsCount));
					 internalSourcingApplicantsDTO.setRejectedAvgCount(df.format(internalRejectedCount));
					 internalSourcingApplicantsDTO.setOfferedAvgCount(df.format(internalOfferedCount));
					 internalSourcingApplicantsDTO.setOfferAcceptedAvgCount(df.format(internalOfferAcceptedCount));
					 internalSourcingApplicantsDTO.setOfferRejectedAvgCount(df.format(internalOfferRejectedCount));
					 internalSourcingApplicantsDTO.setBackoutAvgCount(df.format(internalOfferBackoutCount));
					 
					 applicantBySourceList.add(internalSourcingApplicantsDTO);
					}
					 if(applicantBySourceList != null){
						 response.setData(applicantBySourceList);
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						} else {
							response.setResponse(Result.FAILED.name());
							response.setResponseMessage(Result.FAILED.getValue());
						}
					 
					}else if(jobIdList == null || jobIdList.isEmpty()) {
					    	response.setData(applicantBySourceList); // returning empty data List if jobIdList is empty or null.
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
				    } 
				}
				else if (loginUser == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
				else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
					
			return response;
		}

	 
	 @RequestMapping(value = "/getAllCandidateListForRecruiterReports")
		public @ResponseBody ResponseList<Candidate> getAllCandidateListForRecruiterReports(CandidateDTO candidateDTO, 
				@RequestParam(value="userCode", required=false) String userCode, 
				@RequestParam(value="timePeriod", required=false) String timePeriod,
				@RequestParam(value="customStartDate", required=false) String customStartDate,
				@RequestParam(value="customEndDate", required=false) String customEndDate,
				HttpServletRequest request) throws Exception {

		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				Date[] searchDates = null;
				
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
						if(timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
							Date newDate = DateUtils.addHours(searchDates[0], 24);
							searchDates[1] = newDate;
						}
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
					/*List<Long> teamsList = new ArrayList<>();
					if(candidateDTO.getOrgTeams() != null && !candidateDTO.getOrgTeams().isEmpty()) {
						String[] orgTeams = candidateDTO.getOrgTeams().split(",");
						for(int i=0; i < orgTeams.length; i++) {
							teamsList.add(Long.parseLong(orgTeams[i]));
						}
					}*/
				
				String[] tableColumns = { "candidateName", "email", "phoneNumber", "empType", "location", "rate", "experience", "noOfJobs","resume", "createdOn"};
				int sortColumnIndex = 1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default descending order
				if (candidateDTO.getOrder() != null && !candidateDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(candidateDTO.getOrder().get(0).get("column"));
					sortColumnOrder = candidateDTO.getOrder().get(0).get("dir");
				}
				candidateDTO.setSortColumn(tableColumns[sortColumnIndex]);
				candidateDTO.setSortOrder(sortColumnOrder);
				candidateDTO.setOrgLocationId(orgLocations.getId());
				
				ResponseList<Candidate> list = candidateService.getAllCandidatesByCreatedByUserCode(candidateDTO,orgLocations,userCode,searchDates);
				
				return list;
			}
	 
	 @SuppressWarnings({ "unchecked", "unused" })
		@RequestMapping(value = "/getApplicantBackoutReport", method = RequestMethod.GET)
	 public Response<ApplicantsBackoutDTO> getApplicantBackoutReport(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
			
			Response<ApplicantsBackoutDTO> response = new Response<>();
			ApplicantsBackoutDTO applicantsBackoutDTOEmpty = new ApplicantsBackoutDTO();
			try{
				User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(loginUser != null){
					Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
					List<Long> jobIdList = new ArrayList<>();
					if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
						String[] jobIds = jobFilterDTO.getJobIds().split(",");
						for(int i=0; i<jobIds.length; i++) {
							jobIdList.add(Long.valueOf(jobIds[i]));
						}
					}else{
						jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
					}
					Date[] searchDates = null;

					if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
						if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
							searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
						}else {
							Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
				    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
				    	   
				    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
				    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
				    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
				    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
				    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
				    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
				    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
				    	    fromDate = TZ_DF_TS.parse(startDateString);
				    	    toDate = TZ_DF_TS.parse(endDateString);
				    	    
				    	    searchDates = new Date[2];
				    	    searchDates[0] = fromDate;
				    	    searchDates[1] = toDate;
						}
					}
					//Map<String, Integer> ApplicantsBackoutCount = null;
					ApplicantsBackoutDTO applicantsBackoutDTO = null;
					if ( jobIdList != null && !jobIdList.isEmpty()) {
						 applicantsBackoutDTO = applicantsBackoutReportService.getBackoutApplicantCount(jobIdList, searchDates,orgLocation);
						/*for (Entry<String, Integer> entry : ApplicantsBackoutCount.entrySet()){
							if(entry.getKey().equalsIgnoreCase("Better Offer")) {
								applicantsBackoutDTO.setBetterOffer(entry.getValue());
							}
							if(entry.getKey().equalsIgnoreCase("Current Company")) {
								applicantsBackoutDTO.setCurrentCompany(entry.getValue());
							}
							if(entry.getKey().equalsIgnoreCase("Compensation")) {
								applicantsBackoutDTO.setCompensation(entry.getValue());
							}
							if(entry.getKey().equalsIgnoreCase("Cold Feet")) {
								applicantsBackoutDTO.setColdFeet(entry.getValue());
							}
							if(entry.getKey().equalsIgnoreCase("Personal")) {
								applicantsBackoutDTO.setPersonal(entry.getValue());
							}
							if(entry.getKey().equalsIgnoreCase("other")) {
								applicantsBackoutDTO.setOther(entry.getValue());
							}
						}*/
					}

					if (applicantsBackoutDTO != null) {
						response.setObject(applicantsBackoutDTO);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}else if (applicantsBackoutDTO == null && jobIdList.isEmpty()) {
						if (response.getObject() == null) { // if jobIdList is empty then sending empty list as a response to service.
							response.setObject(applicantsBackoutDTOEmpty);
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						}
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
					
				}else if (loginUser == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
				
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return response;
		}

		@SuppressWarnings({ "unchecked", "unused" })
		@RequestMapping(value = "/getApplicantBackoutReportForInduvidualJob", method = RequestMethod.GET)
		public ResponseList<ApplicantsBackoutDTO> getApplicantBackoutReportForInduvidualJob(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {

			ResponseList<ApplicantsBackoutDTO> response = new ResponseList<>();
			try{
				User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(loginUser != null){
					
					Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);

					List<ApplicantsBackoutDTO> applicantsBackoutList = new ArrayList<>();
					ApplicantsBackoutDTO applicantsBackoutDTO=new ApplicantsBackoutDTO();
					List<Long> jobIdList = new ArrayList<>();

					Date[] searchDates = null;

					if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
						if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
							searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
						}else {
							Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
				    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
				    	   
				    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
				    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
				    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
				    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
				    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
				    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
				    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
				    	    fromDate = TZ_DF_TS.parse(startDateString);
				    	    toDate = TZ_DF_TS.parse(endDateString);
				    	    
				    	    searchDates = new Date[2];
				    	    searchDates[0] = fromDate;
				    	    searchDates[1] = toDate;
						}
					}

					if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
						String[] jobIds = jobFilterDTO.getJobIds().split(",");
						for(int i=0; i<jobIds.length; i++) {
							jobIdList.add(Long.valueOf(jobIds[i]));
						}
					}else {
						jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
					}


					for (Long jobId : jobIdList) {
						if(jobId!=null){
							Requirement requirement = requirementService.getRequirementByRequirementIdAndSearchValue(jobId,jobFilterDTO.getSearchValue());
							List<Long> jobids = new ArrayList<>();
							jobids.add(jobId);
							applicantsBackoutDTO =  applicantsBackoutReportService.getBackoutApplicantCount(jobids, searchDates,orgLocation);
							if(requirement != null){
							applicantsBackoutDTO.setJobName(requirement.getJobTitle());
							applicantsBackoutDTO.setGeneratedId(requirement.getGeneratedId());
							applicantsBackoutList.add(applicantsBackoutDTO);
							}
						}

					}
					if(applicantsBackoutList != null){
						response.setData(applicantsBackoutList);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
					
				}else if (loginUser == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return response;

		}
	 
	 @RequestMapping(value = "/getAllSubmissionApplicantsForRecruiterReportsExport")
		public File getAllSubmissionApplicantsForRecruiterReportsExport(RequirementSubmissionHistoryDTO sumissionHistoryDTO, 
				@RequestParam(value="userCode", required=false) String userCode, 
				@RequestParam(value="timePeriod", required=false) String timePeriod,
				@RequestParam(value="customStartDate", required=false) String customStartDate,
				@RequestParam(value="customEndDate", required=false) String customEndDate,
				HttpServletRequest request,HttpServletResponse response) throws Exception {

		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				Date[] searchDates = null;
				
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
						if(timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
							Date newDate = DateUtils.addHours(searchDates[0], 24);
							searchDates[1] = newDate;
						}
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
				
				ResponseList<RequirementSubmissionHistory> list = requirementSubmissionHistoryService.getAllSubmissionHistoryApplicantsByOwner(sumissionHistoryDTO,userCode,sumissionHistoryDTO.getToStage(),searchDates,orgLocations.getId());
				
				File file = null;
				file = pdfUtil.recruiterReprtsApplicantsListPdfTable(list, orgLocations,searchDates,sumissionHistoryDTO.getToStage());
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);

			StringBuilder filename = new StringBuilder();
			response.setHeader("Content-Disposition",
					filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();
			if (file != null) {
				file.delete();
			}
			return file;
			}
	 
	 @RequestMapping(value = "/getVendorRequirementReports")
		public @ResponseBody String getVendorRequirementReports(@RequestParam(value="reportType", required=false) String reportType,
				@RequestParam(value="customStartDate", required=false) String customStartDate, @RequestParam(value="customEndDate", required=false) String customEndDate,
				HttpServletRequest request) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    
		    
		    Date[] searchDates = null;
		    Date startDateUTC = null;
		    Date endDateUTC = null;
			
			if(!"custom".equalsIgnoreCase(reportType)){
				searchDates = reportsUtil.getJobFilterDate(reportType,orgLocations);
				startDateUTC =searchDates[0];
				endDateUTC = searchDates[1];
			}else if("custom".equalsIgnoreCase(reportType) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
	    	    
	    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
				DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				
				Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
				Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
				String customStartTimeStr = TZ_DF_CSD.format(startTime);  
				String customEndTimeStr = TZ_DF_CED.format(endTime);  
				 startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
				 endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
				 startDateUTC = new java.sql.Date(startDateUTC.getTime());
				 endDateUTC = new java.sql.Date(endDateUTC.getTime());
			}
		    
		    List<OrgRelations> vendorslist = orgRelationService.getAllVendorsListByOrgLocationId(orgLocations.getId());
		    
		    for(OrgRelations orgrel : vendorslist){
		    	int applicantsSum = 0;
				int requestSentSum = 0;
				int shortlistedSum = 0;
				int submitToClientSum = 0;
				int interviewsSum = 0;
				int offeredSum = 0;
				int rejectedSum = 0;
				int backoutSum = 0;
				long vendorLocationId = 0L;
		    	List<Requirement> reqList = requirementService.getAllReleasedJobsForVendorBasedOnStartAndEndDates(orgLocations.getId(), orgrel.getId(),startDateUTC,endDateUTC);
		    	if(orgrel.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
		    		vendorLocationId = orgrel.getLocationId().getId();
				}if(orgrel.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					vendorLocationId = orgrel.getResponderLocationId().getId();
				}
		    	List<RequirementSubmission> reqSubmissionList = requirementSubmissionService.getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(orgLocations.getId(),vendorLocationId,startDateUTC,endDateUTC);
		    	orgrel.setSubmittedCount(reqSubmissionList.size());
		    	if(null != reqSubmissionList && reqSubmissionList.size() > 0){
			    	for(RequirementSubmission reqSub : reqSubmissionList){
						int applicantsCount = 0;
						int requestSentCount = 0;
						int shortlistedCount = 0;
						int submitToClientCount = 0;
						int interviewsCount = 0;
						int offeredCount = 0;
						int rejectedCount = 0;
						int backoutCount = 0;
						List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
						if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
							for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
								if("submission".equalsIgnoreCase(rsh.getToStage())){
									applicantsCount++;
								}else if("shortList".equalsIgnoreCase(rsh.getToStage())){
									shortlistedCount++;
								}else if("interview".equalsIgnoreCase(rsh.getToStage())){
									interviewsCount++;
								}else if("offermade".equalsIgnoreCase(rsh.getToStage())){
									offeredCount++;
								}else if("request_sent".equalsIgnoreCase(rsh.getToStage())){
									requestSentCount++;
								}else if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
									submitToClientCount++;
								}else if("rejected".equalsIgnoreCase(rsh.getToStage())){
									rejectedCount++;
								}else if("backout".equalsIgnoreCase(rsh.getToStage())){
									backoutCount++;
								}
							}
							 applicantsSum = applicantsSum+applicantsCount;
							 requestSentSum = requestSentSum+requestSentCount;
							 shortlistedSum = shortlistedSum+shortlistedCount;
							 submitToClientSum = submitToClientSum+submitToClientCount;
							 interviewsSum = interviewsSum+interviewsCount;
							 offeredSum = offeredSum+offeredCount;
							 rejectedSum = rejectedSum+rejectedCount;
							 backoutSum = backoutSum + backoutCount;
						}
					}
		    	}
		    	
		    	orgrel.setReqReleasedCount(reqList.size());
		    	if(orgrel.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
		    		orgrel.setVendorName(orgrel.getLocationId().getOrgLocationName());
				}if(orgrel.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgrel.setVendorName(orgrel.getResponderLocationId().getOrgLocationName());
				}
		    	orgrel.setShortlistedCount(shortlistedSum);
		    	orgrel.setInterviewedCount(interviewsSum);
		    	orgrel.setOfferedCount(offeredSum);
		    	orgrel.setRejectedCount(rejectedSum);
		    	orgrel.setBackoutCount(backoutSum);
		    }
		    
			return commonUtil.toJSON(vendorslist);
		}
	 
	 
	 
	 @RequestMapping(value = "/getVendorSpocsReports")
		public @ResponseBody String getVendorSpocsReports(@RequestParam(value="reportType", required=false) String reportType,@RequestParam(value="relationId", required=false) Long relationId,
				@RequestParam(value="customStartDate", required=false) String customStartDate, @RequestParam(value="customEndDate", required=false) String customEndDate,
				HttpServletRequest request) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
		    Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
			 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			 //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();

			 Date endDateUTC =null ;
			 Date startDateUTC = null;
			 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)&&!"".equalsIgnoreCase(reportType)){
				 String endDate = outputFormatter1.print(zoned);
				 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
				 endDateUTC = TZ_DF_TS.parse(endDateString);
			 }
				
				if(null != reportType && !"".equals(reportType)){
					if("today".equals(reportType)){
						String startDate = outputFormatter.print(zoned);
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
						startDateUTC = TZ_DF_TS.parse(startDateString);
					}else if(Constants.YESTER_DAY.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
					}else if(Constants.ONE_WEEK.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
					}else if(Constants.TWO_WEEKS.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
					}else if(Constants.ONE_MONTH.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
					}else if(Constants.TWO_MONTHS.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
					}else{
						 if(!"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
							DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
							DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
							Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
							Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
							String customStartTimeStr = TZ_DF_CSD.format(startTime);  
							String customEndTimeStr = TZ_DF_CED.format(endTime);  
							String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
							String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
							startDateUTC = TZ_DF_TS.parse(startDateString);
							endDateUTC = TZ_DF_TS.parse(endDateString);
						 }
						
					}
				}
		    
			List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(relationId, loginUser.getId(),orgLocations.getId(),"Vendor");
			
//		    List<OrgSpoc> vendorSpocsList = orgSpocService.getSpoclistByDomain(relationId,"");
		    for(OrgSpoc spoc : orgSpocsList){
		    	int applicantsSum = 0;
				int requestSentSum = 0;
				int shortlistedSum = 0;
				int submitToClientSum = 0;
				int interviewsSum = 0;
				int offeredSum = 0;
				int rejectedSum = 0;
				int backoutSum = 0;
		    	List<Requirement> reqList = requirementService.getTotalRequirementsReleasedToSpoc(relationId,spoc.getId(),startDateUTC,endDateUTC);
		    	spoc.setReqReleasedCount(reqList.size());
		    	   List<RequirementSubmission> reqSubmissionList = requirementSubmissionService.getVendorSpocSubmissions(spoc.getVendorUser().getUserCode(),orgLocations.getId(),startDateUTC,endDateUTC);
		    	   spoc.setSubmittedCount(reqSubmissionList.size());
			    	if(null != reqSubmissionList && reqSubmissionList.size() > 0){
				    	for(RequirementSubmission reqSub : reqSubmissionList){
							int applicantsCount = 0;
							int requestSentCount = 0;
							int shortlistedCount = 0;
							int submitToClientCount = 0;
							int interviewsCount = 0;
							int offeredCount = 0;
							int rejectedCount = 0;
							int backoutCount = 0;
							List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
							if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
								for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
									if("submission".equalsIgnoreCase(rsh.getToStage())){
										applicantsCount++;
									}else if("shortList".equalsIgnoreCase(rsh.getToStage())){
										shortlistedCount++;
									}else if("interview".equalsIgnoreCase(rsh.getToStage())){
										interviewsCount++;
									}else if("offermade".equalsIgnoreCase(rsh.getToStage())){
										offeredCount++;
									}else if("request_sent".equalsIgnoreCase(rsh.getToStage())){
										requestSentCount++;
									}else if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
										submitToClientCount++;
									}else if("rejected".equalsIgnoreCase(rsh.getToStage())){
										rejectedCount++;
									}else if("backout".equalsIgnoreCase(rsh.getToStage())){
										backoutCount++;
									}
								}
								 applicantsSum = applicantsSum+applicantsCount;
								 requestSentSum = requestSentSum+requestSentCount;
								 shortlistedSum = shortlistedSum+shortlistedCount;
								 submitToClientSum = submitToClientSum+submitToClientCount;
								 interviewsSum = interviewsSum+interviewsCount;
								 offeredSum = offeredSum+offeredCount;
								 rejectedSum = rejectedSum+rejectedCount;
								 backoutSum = backoutSum + backoutCount;
							}
						}
			    	}
			    	if(null != spoc.getVendorUser().getLastName()){
			    		StringBuilder sb = new StringBuilder();
			    		spoc.setSpocName(sb.append(spoc.getVendorUser().getFirstName()).append(" ").append(spoc.getVendorUser().getLastName()).toString());
			    	}else{
			    		spoc.setSpocName(spoc.getVendorUser().getFirstName());
			    	}
			    	spoc.setShortlistedCount(shortlistedSum);
			    	spoc.setInterviewedCount(interviewsSum);
			    	spoc.setOfferedCount(offeredSum);
			    	spoc.setRejectedCount(rejectedSum);
			    	spoc.setBackoutCount(backoutSum);
		    }
		    
			return commonUtil.toJSON(orgSpocsList);
		}
	 
	 
	 
	 @SuppressWarnings("unused")
		@RequestMapping(value = "/getDeclinedApplicantsCount", method = RequestMethod.GET)
		public Response<ApplicantDeclineDTO> getDeclinedApplicantsCount(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
			Response<ApplicantDeclineDTO> response = new Response<>();
			try{
				User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(loginUser != null){


					Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
					
					List<ApplicantDeclineDTO> applicantDeclineList = new ArrayList<>();
					ApplicantDeclineDTO applicantDeclineDTO=new ApplicantDeclineDTO();
					List<Long> jobIdList = new ArrayList<>();

					if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
						String[] jobIds = jobFilterDTO.getJobIds().split(",");
						for(int i=0; i<jobIds.length; i++) {
							jobIdList.add(Long.valueOf(jobIds[i]));
						}
					}else {
						jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
					}

					Date[] searchDates = null;

					if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
						if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
							searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
						}else {
							Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
				    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
				    	   
				    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
				    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
				    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
				    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
				    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
				    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
				    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
				    	    fromDate = TZ_DF_TS.parse(startDateString);
				    	    toDate = TZ_DF_TS.parse(endDateString);
				    	    
				    	    searchDates = new Date[2];
				    	    searchDates[0] = fromDate;
				    	    searchDates[1] = toDate;
						}
					}

					if(jobIdList.size() > 0){
						applicantDeclineDTO =  applicantDeclineService.getDeclineApplicantSkillCount(jobIdList, searchDates,orgLocation); 
					}else{
						response.setResponse(Result.DATA_NOT_FOUND.name());
						response.setResponseMessage(Result.DATA_NOT_FOUND.getValue());
					}
					
					if (applicantDeclineDTO != null) {
						response.setObject(applicantDeclineDTO);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
					
				}else if (loginUser == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}

			return response;
		}

		@SuppressWarnings({ "unused", "unchecked" })
		@RequestMapping(value = "/getDeclinedApplicantsList", method = RequestMethod.GET)
		public ResponseList<ApplicantDeclineDTO> getDeclinedApplicantsList(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
			
			ResponseList<ApplicantDeclineDTO> response = new ResponseList<>();
			
			try{
				User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(loginUser != null){

					Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);

					List<ApplicantDeclineDTO> applicantDeclineList = new ArrayList<>();
					ApplicantDeclineDTO applicantDeclineDTO=new ApplicantDeclineDTO();
					List<Long> jobIdList = new CopyOnWriteArrayList<>();

					Date[] searchDates = null;

					if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
						if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
							searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
						}else {
							Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
				    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
				    	    
				    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
				    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
				    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
				    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
				    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
				    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
				    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
				    	    fromDate = TZ_DF_TS.parse(startDateString);
				    	    toDate = TZ_DF_TS.parse(endDateString);
				    	   
				    	    searchDates = new Date[2];
				    	    searchDates[0] = fromDate;
				    	    searchDates[1] = toDate;
						}
					}

					if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
						String[] jobIds = jobFilterDTO.getJobIds().split(",");
						for(int i=0; i<jobIds.length; i++) {
							jobIdList.add(Long.valueOf(jobIds[i]));
						}
					}else {
						jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
					}
					

					 for (Long jobId : jobIdList) {
						if(jobId!=null){
						Requirement requirement = requirementService.getRequirementByRequirementIdAndSearchValue(jobId,jobFilterDTO.getSearchValue());
						List<Long> jobids = new ArrayList<>();
						jobids.add(jobId);
						applicantDeclineDTO =  applicantDeclineService.getDeclineApplicantSkillCount(jobids, searchDates,orgLocation);
						if(requirement != null){
						applicantDeclineDTO.setJobName(requirement.getJobTitle());
						applicantDeclineDTO.setGeneratedId(requirement.getGeneratedId());
						applicantDeclineList.add(applicantDeclineDTO);
						}
						if(applicantDeclineList != null){
							response.setData(applicantDeclineList);
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						} else {
							response.setResponse(Result.FAILED.name());
							response.setResponseMessage(Result.FAILED.getValue());
							}
						
						}
						
					}
					}else if (loginUser == null) {
						response.setResponse(Result.SESSION_TIMEDOUT.name());
						response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
					} else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return response;
		}
	 
	 private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
		{
			DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
			String yesterDayDate = outputFormatter.print(yesterDayZoned);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
			return TZ_DF_TS.parse(startDateString);
		}
	 
	 @RequestMapping(value = "/getAllSubmissionApplicantsForRecruiterReports")
		public @ResponseBody ResponseList<RequirementSubmissionHistory> getAllSubmissionApplicantsForRecruiterReports(RequirementSubmissionHistoryDTO sumissionHistoryDTO, 
				@RequestParam(value="userCode", required=false) String userCode, 
				@RequestParam(value="timePeriod", required=false) String timePeriod,
				@RequestParam(value="customStartDate", required=false) String customStartDate,
				@RequestParam(value="customEndDate", required=false) String customEndDate,
				HttpServletRequest request) throws Exception {

		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				Date[] searchDates = null;
				
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
						if(timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
							Date newDate = DateUtils.addHours(searchDates[0], 24);
							searchDates[1] = newDate;
						}
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
				
				String[] tableColumns = { "candidateName", "email", "jobTitle", "orgName", "stage"};
				int sortColumnIndex = 1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default descending order
				if (sumissionHistoryDTO.getOrder() != null && !sumissionHistoryDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(sumissionHistoryDTO.getOrder().get(0).get("column"));
					sortColumnOrder = sumissionHistoryDTO.getOrder().get(0).get("dir");
				}
				sumissionHistoryDTO.setSortColumn(tableColumns[sortColumnIndex]);
				sumissionHistoryDTO.setSortOrder(sortColumnOrder);
				
				ResponseList<RequirementSubmissionHistory> list = requirementSubmissionHistoryService.getAllSubmissionHistoryApplicantsByOwner(sumissionHistoryDTO,userCode,sumissionHistoryDTO.getToStage(),searchDates,orgLocations.getId());
				
				return list;
			}
	
	 @RequestMapping(value = "/getAllCandidateListForRecruiterReportsExport")
		public File getAllCandidateListForRecruiterReportsExport(CandidateDTO candidateDTO, 
				@RequestParam(value="userCode", required=false) String userCode, 
				@RequestParam(value="timePeriod", required=false) String timePeriod,
				@RequestParam(value="customStartDate", required=false) String customStartDate,
				@RequestParam(value="customEndDate", required=false) String customEndDate,
				HttpServletRequest request,HttpServletResponse response) throws Exception {

		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				Date[] searchDates = null;
				
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
						if(timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
							Date newDate = DateUtils.addHours(searchDates[0], 24);
							searchDates[1] = newDate;
						}
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
				
				ResponseList<Candidate> list = candidateService.getAllCandidatesByCreatedByUserCode(candidateDTO,orgLocations,userCode,searchDates);
				File file = null;
				file = pdfUtil.recruiterReprtsCandidateListPdfTable(list, orgLocations,searchDates);
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);

			StringBuilder filename = new StringBuilder();
			response.setHeader("Content-Disposition",
					filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();
			if (file != null) {
				file.delete();
			}
			return file;
			}
		
	 
	 @RequestMapping(value = "/getTimeToFillDetails", method = RequestMethod.GET)
		public List<TimeToFillDTO> getTimeToFillDetailsForReports(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
			
			List<TimeToFillDTO> timeToFillList = new ArrayList<>();
			int year = 0;
			if(StringUtils.isNotBlank(jobFilterDTO.getYear())) {
				 year = Integer.parseInt(jobFilterDTO.getYear());
			}else {
				 year = Calendar.getInstance().get(Calendar.YEAR);
			}
			DecimalFormat df = new DecimalFormat("#.00");
			List<Long> jobIdList = new ArrayList<>();
			
			if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
				String[] jobIds = jobFilterDTO.getJobIds().split(",");
				for(int i=0; i<jobIds.length; i++) {
					jobIdList.add(Long.valueOf(jobIds[i]));
				}
			}else {
				jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
			}
			
			
			for(int i=0; i<12; i++) {
				
				  Calendar Q1StartDate=Calendar.getInstance();
				  Q1StartDate.set(Calendar.YEAR,year);
			      Q1StartDate.set(Calendar.MONTH,i);
			      Q1StartDate.set(Calendar.DATE,1);
				   
				  Calendar Q1EndDate =Calendar.getInstance();
				  Q1EndDate.set(Calendar.YEAR,year);
				  Q1EndDate.set(Calendar.MONTH, i);
				  Q1EndDate.set(Calendar.DATE, Q1EndDate.getActualMaximum(Calendar.DAY_OF_MONTH));
				  java.sql.Date fromDate = new java.sql.Date(Q1StartDate.getTime().getTime());
				  java.sql.Date toDate = new java.sql.Date(Q1EndDate.getTime().getTime());
				  Date[] searchDates = new Date[2];
	    	      searchDates[0] = fromDate;
	    	      searchDates[1] = toDate;
	    	      
	    	      Set<Long> requirementIds = new HashSet<>();
	    	      Long timeToFillDays = 0L;
	    	      TimeToFillDTO timeToFillDTO = new TimeToFillDTO();
	    	      
	    	      String month = Constants.Y_M.format(Q1EndDate.getTime());
	    	      String monthStr = Constants.M.format(Q1EndDate.getTime());
	    	      
	    	  	  List<RequirementSubmissionHistory> offerAcceptedList = timeToFillService.getRequirementHistoryByJobIdsAndStageForTimeToFill(jobIdList, searchDates);
	    	      
	    	      
	        	      if(offerAcceptedList != null && offerAcceptedList.size() > 0) {
	        	    	  for(RequirementSubmissionHistory rh : offerAcceptedList) {
	        	    		  RequirementSubmissionHistory rsHisory = timeToFillService.getRequirementHistoryBySubmissionIdsAndStage(rh.getSubmissionId().getId(), "SUBMISSION");
	        	    		  requirementIds.add(rsHisory.getSubmissionId().getRequirement().getId());
	        	    		  
	        	    		  Long dayDiff = CalendarUtil.getNumberOfDaysForReports(rsHisory.getCreatedDate(), rh.getCreatedDate());
	        	    		  timeToFillDays = timeToFillDays+dayDiff;
	        	    	  }
	        	      }
	        	    //  timeToFillDays
	        	      Double timeToFillCount = 0d;
	        	      if(requirementIds.size() > 0) {
	        	    	  timeToFillDays = (timeToFillDays/offerAcceptedList.size());
	        	    	  timeToFillCount = (double) ((double)timeToFillDays/((double)requirementIds.size()));
	        	      }else {
	        	    	  timeToFillDTO.setFillCount(String.valueOf(timeToFillCount));
	        	      }
	        	      
	        	      timeToFillDTO.setMonthStr(monthStr);
	        	      timeToFillDTO.setFillCount(df.format(timeToFillCount));
	        	      timeToFillDTO.setFillCountValue(timeToFillCount);
	        	      timeToFillDTO.setMonth(month);
	        	      timeToFillList.add(timeToFillDTO);
	    			  
	    		}
	    		
	    		return timeToFillList;
	    	}
	 
	 
	 
	 @RequestMapping(value = "/getAllUsersByOrgLocationsId")
		public @ResponseBody ResponseList<UserDTO> getAllUsersByOrgLocationsId(UserRoleDTO userRoleDto,@RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
				UserDTO userDTO,HttpServletRequest request) throws Exception {

		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		      Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		      String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		      String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				
				Date[] searchDates = null;
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
					List<Long> teamsList = new ArrayList<>();
					if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
						String[] orgTeams = userRoleDto.getOrgTeams().split(",");
						for(int i=0; i < orgTeams.length; i++) {
							teamsList.add(Long.parseLong(orgTeams[i]));
						}
					}
					//Roles List
					List<Long> rolesList = new ArrayList<>();
					if(userRoleDto.getUserRoleName() != null && !userRoleDto.getUserRoleName().isEmpty()) {
						String[] roleNames = userRoleDto.getUserRoleName().split(",");
						for(int i=0; i < roleNames.length; i++) {
							rolesList.add(Long.parseLong(roleNames[i]));
						}
					}
					String type=null;
				if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
					type=userRoleDto.getType();
				}
				String[] tableColumns = { "firstName", "jobs", "candidates", "applicants", "shortlisted", "submitted", "Interviewed", "offered", "rejected","joined", "backout" };
				int sortColumnIndex = 1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default descending order
				if (userDTO.getOrder() != null && !userDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(userDTO.getOrder().get(0).get("column"));
					sortColumnOrder = userDTO.getOrder().get(0).get("dir");
				}
				userDTO.setSortColumn(tableColumns[sortColumnIndex]);
				userDTO.setSortOrder(sortColumnOrder);
				ResponseList<UserDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,rolesList);
				return userDTOList;
			}

	 @RequestMapping(value = "/getClientList", method = RequestMethod.POST)
		public List<OrgRelationDTO> getClientList(HttpServletRequest request, DashboardItemsDTO dashboardItemsDTO) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(orgLocations.getId());
			return orgClientList; 
		}
	 
	 @RequestMapping(value = "/getTeamsListForLoginUser", method = RequestMethod.POST)
		public List<Team> getTeamsListForLoginUser(HttpServletRequest request, DashboardItemsDTO dashboardItemsDTO) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), orgLocations.getId());
			return teamsList; 
		}
	 
	@RequestMapping(value = "/getAllApplicantJoinedListByOrgLocationsId")
	public @ResponseBody ResponseList<ApplicantsJoinedReportDTO> getAllApplicantJoinedListByOrgLocationsId(ApplicantsJoinedReportDTO userRoleDto,@RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
			ApplicantsJoinedReportDTO userDTO,HttpServletRequest request) throws Exception {
	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			Date[] searchDates = null;
			
				if(!"custom".equalsIgnoreCase(timePeriod)){
					searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
				}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
		    	    
		    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
					DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					
					Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
					Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
					String customStartTimeStr = TZ_DF_CSD.format(startTime);  
					String customEndTimeStr = TZ_DF_CED.format(endTime);  
					Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
					Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
		    	   
		    	    searchDates = new Date[2];
		    	    searchDates[0] = startDateUTC;
		    	    searchDates[1] = endDateUTC;
				}
				List<Long> teamsList = new ArrayList<>();
				if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
					String[] orgTeams = userRoleDto.getOrgTeams().split(",");
					for(int i=0; i < orgTeams.length; i++) {
						teamsList.add(Long.parseLong(orgTeams[i]));
					}
				}
				List<Long> clientIdsList = new ArrayList<>();
				if(userRoleDto.getClients() != null && !userRoleDto.getClients().isEmpty()) {
					String[] clients = userRoleDto.getClients().split(",");
					for(int i=0; i < clients.length; i++) {
						clientIdsList.add(Long.parseLong(clients[i]));
					}
				}
				String type=null;
			if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
				type=userRoleDto.getType();
			}
			String[] tableColumns = { "name", "requirementCount", "offerCount", "joinedCount", "incentives"};
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (userDTO.getOrder() != null && !userDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(userDTO.getOrder().get(0).get("column"));
				sortColumnOrder = userDTO.getOrder().get(0).get("dir");
			}
			userDTO.setSortColumn(tableColumns[sortColumnIndex]);
			userDTO.setSortOrder(sortColumnOrder);

			ResponseList<ApplicantsJoinedReportDTO> userDTOList = userService.getApplicantsJoinedReport(userRoleDto,orgLocations.getId(),searchDates,teamsList,type,clientIdsList);

			return userDTOList;
		}
	
	
	@RequestMapping(value = "/applicantList")
	public @ResponseBody ResponseList<RequirementSubmissionHistoryDTO> applicantList(RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO, HttpServletRequest request)throws Exception
   {
	ResponseList<RequirementSubmissionHistoryDTO> responseList = new ResponseList<>();
		try {
				OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
				
				Date[] freqDates = new Date[2];
				String userType = null;
				Date startUTCDate = null;
				Date endUTCDate = null;
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				 DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
				 DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
				 if("Custom".equalsIgnoreCase(requirementSubmissionHistoryDTO.getFrequency())){
					 Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),requirementSubmissionHistoryDTO.getFromDate());
						Date endTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),requirementSubmissionHistoryDTO.getToDate());
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
						endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
				 }else {
					 freqDates = reportsUtil.getJobFilterDate(requirementSubmissionHistoryDTO.getFrequency(),loginOrgLocations);
					 String customStartTimeStr = TZ_DF_CSD.format(freqDates[0]);  
					 String customEndTimeStr = TZ_DF_CED.format(freqDates[1]);  
					 startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
					 endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
				 }
				 
				
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
				} 
				
			   ResponseList<RequirementSubmissionHistory> toDayReqApplicantscount =requirementSubmissionDao.getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(requirementSubmissionHistoryDTO, loginOrgLocations.getId(),requirementSubmissionHistoryDTO.getSubmissionStatus(),requirementSubmissionHistoryDTO.getTabType(),userType,user,startUTCDate,endUTCDate,loginOrgLocations);
				
			   List<RequirementSubmissionHistoryDTO> rshDTOList = new ArrayList<>();
			   if(toDayReqApplicantscount.getData() != null)
				for(RequirementSubmissionHistory rsh: toDayReqApplicantscount.getData()){
					RequirementSubmissionHistoryDTO rshDTO = new RequirementSubmissionHistoryDTO();
					StringBuilder sb = new StringBuilder();
					if(StringUtils.isNotBlank(rsh.getSubmissionId().getCandidate().getLastName())){
						rshDTO.setCandidateName(sb.append(rsh.getSubmissionId().getCandidate().getFirstName()).append(" ").append(rsh.getSubmissionId().getCandidate().getLastName()).toString());
					}else{
						rshDTO.setCandidateName(rsh.getSubmissionId().getCandidate().getFirstName());
					}
					
					rshDTO.setRequirementUUID(rsh.getSubmissionId().getRequirement().getRequirementUUID());
					rshDTO.setJobTitle(rsh.getSubmissionId().getRequirement().getJobTitle());
					if(rsh.getSubmissionId().getRequirement().getGeneratedId() != null) {
						rshDTO.setJobGeneratedId(rsh.getSubmissionId().getRequirement().getGeneratedId());
					}
					if(rsh.getSubmissionId().getRequirement().getClientId() != null){
                        if(StringUtils.isNotBlank(rsh.getSubmissionId().getRequirement().getClientId().getPaymentTerm())){
                        String payTerm=rsh.getSubmissionId().getRequirement().getClientId().getPaymentTerm();
                        Integer result = 0;
                        try {
                        	if(StringUtils.isNotBlank(payTerm)){
                            payTerm=payTerm.replaceAll("\\D", "");
                            result = Integer.parseInt(payTerm);
                        	}
                        } catch (Exception e) {
                            result = 0;
                        }
                        rshDTO.setPayTerm(result);
                        
                        }

                    }
					if(rsh.getSubmissionId().getRequirement().getJobRate() != null){
						rshDTO.setJobRate(rsh.getSubmissionId().getRequirement().getJobRate().toString());
					}
					
					if(rshDTO.getPayTerm() > 0 &&  rsh.getSubmissionId().getJoinedDate()  != null){
//						Integer result = Integer.parseInt(rshDTO.getPayOutPeriod());
						if(rshDTO.getPayTerm() != 0){
							Date generatedInvoiceDate=CalendarUtil.addDaysForGenerateInvoice(rsh.getSubmissionId().getJoinedDate(), rshDTO.getPayTerm());
							DateTime dtOrg = new DateTime(generatedInvoiceDate);
							DateTime generatedInvoiceDatePlusOne = dtOrg.plusDays(1);
							rshDTO.setGenerateInvoice(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),generatedInvoiceDatePlusOne.toDate()));
							
						}
}
					
					if(rsh.getSubmissionId().getRequirement().getParentRequirement() != null){
						rshDTO.setOrgLocationName(rsh.getSubmissionId().getRequirement().getParentRequirement().getOrgLocationId().getOrgLocationName());
					}
					
					if(rsh.getSubmissionId().getRequirement().getClientId() != null) {
						if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
							if (rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId() != null) {
								rshDTO.setOrgLocationName(rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
							}
						}
						if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
							if (rsh.getSubmissionId().getRequirement().getClientId().getLocationId() != null) {
							rshDTO.setOrgLocationName(rsh.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
							}
						}
					}
					
					List<String> requirementLocationsList = new ArrayList<>();
					List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(rsh.getSubmissionId().getRequirement().getId());
					if(locationsList != null && !locationsList.isEmpty()) {
						for(RequirementLocations location : locationsList) {
							requirementLocationsList.add(location.getLocation());
						}
					}
					rshDTO.setLocationsList(requirementLocationsList);
					
					
					rshDTO.setEmail(rsh.getSubmissionId().getCandidate().getEmail());
		
					if(rsh.getSubmissionId().getBackOutCategoryType() != null) {
						rshDTO.setBackoutType(rsh.getSubmissionId().getBackOutCategoryType());
						rshDTO.setBackoutReason(rsh.getSubmissionId().getBackOutReason());
					}
					if(rsh.getSubmissionId().getRejectedCategoryType() != null) {
						rshDTO.setRejectedType(rsh.getSubmissionId().getRejectedCategoryType());
						rshDTO.setRejectedReason(rsh.getSubmissionId().getRejectedReason());
					}
					if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
						rshDTO.setJobRating(rsh.getSubmissionId().getRequirement().getJobRate());
					}
					if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
						rshDTO.setIncentivize(rsh.getSubmissionId().getRequirement().getIncentivized());
					}
					if(rsh.getSubmissionId().getCandidate().getMobilePhone() != null){
						rshDTO.setMobilePhone(rsh.getSubmissionId().getCandidate().getMobilePhone());
					}
					
					if(rsh.getSubmissionId().getJoinedDate()  != null){
						rshDTO.setJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getSubmissionId().getJoinedDate()));
					}
					
					if(rsh.getSubmissionId().getCandidateResumes() != null){
						rshDTO.setResumeUUID(rsh.getSubmissionId().getCandidateResumes().getResumeUUID());
						rshDTO.setFileUrl(rsh.getSubmissionId().getCandidateResumes().getFileURL());
						rshDTO.setFileName(rsh.getSubmissionId().getCandidateResumes().getFileName());
						rshDTO.setContentType(rsh.getSubmissionId().getCandidateResumes().getFileContentType());
					}
					rshDTO.setPrimarySkill(rsh.getSubmissionId().getCandidate().getPrimarySkill());
					rshDTO.setCreatedUser(rsh.getSubmissionId().getCreatedUser());
					rshDTO.setCandidateScore(rsh.getSubmissionId().getCandidateScore());
					rshDTO.setSubmissionStatus(rsh.getSubmissionId().getSubmissionStatus().toString());
					rshDTO.setCandidateUUID(rsh.getSubmissionId().getCandidate().getCandidateUUID());
					rshDTO.setSubmissionId(rsh.getSubmissionId().getId());
					rshDTO.setExperienceYears(rsh.getSubmissionId().getCandidate().getExperienceYears());
					rshDTO.setExperienceMonths(rsh.getSubmissionId().getCandidate().getExperienceMonths());
					rshDTO.setFinalizedRate(rsh.getSubmissionId().getFinalizedRate());
					rshDTO.setSubmittedBy(rsh.getSubmissionId().getSubmittedBy());
					rshDTO.setToStage(rsh.getToStage());
					rshDTO.setAppliedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate()));
					
					//rshDTO.setAppliedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getSubmissionId().getCreatedDate()));
					rshDTO.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate()));
					rshDTO.setSubmittedBy(rsh.getCreatedUser());
		//					}
						
					/*RequirementRateSubmissionNegotiation rateNegotiation = requirementRateSubmissionNegotiationService.getRequirementRateSubmissionByMaxId(rsh.getSubmissionId().getId());
					if(rateNegotiation !=null && rateNegotiation.getCurrentRate()!=null){
						float currentRate = conversionUtil.rateConvertToYear(rateNegotiation.getCurrentRate(),rateNegotiation.getCurrentRateType(),loginOrgLocations);
						float expectedRate = conversionUtil.rateConvertToYear(rateNegotiation.getExpectedRate(),rateNegotiation.getExpectedRateType(),loginOrgLocations);
						float finalRatePerYear = expectedRate-currentRate;
						rshDTO.setMarginRateYearly(String.format("%.3f", Float.valueOf(finalRatePerYear)));
					}*/
					
					List<RequirementOfferHistory> offerHistorList = requirementOfferHistoryService.getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(rsh.getSubmissionId().getId(), loginOrgLocations.getId());
					if(offerHistorList != null && !offerHistorList.isEmpty()) {
						float clientOfferRate = 0;
						float internalOfferRate = 0;
						for(RequirementOfferHistory roh : offerHistorList) {
//							System.out.println("OfferID:"+roh.getOfferId().getId() + " SubmissionID:"+roh.getSubmissionId().getId()+ " Amount:" + roh.getOfferId().getAmount() + " Status:" + roh.getOfferId().getOfferStatus());
							if(roh.getOfferId().getOfferStatus().equalsIgnoreCase("CLIENT_OFFERACCEPTED")) {
								clientOfferRate = conversionUtil.rateConvertToYear(roh.getOfferId().getAmount(),roh.getOfferId().getPaymentMode(),loginOrgLocations);
							} else if(roh.getOfferId().getOfferStatus().equalsIgnoreCase("OFFERACCEPTED")) {
								internalOfferRate = conversionUtil.rateConvertToYear(roh.getOfferId().getAmount(),roh.getOfferId().getPaymentMode(),loginOrgLocations);
							}
						}
						if(clientOfferRate > 0) {
							float finalRatePerYear = clientOfferRate-internalOfferRate;
							rshDTO.setMarginRateYearly(String.format("%.3f", Float.valueOf(finalRatePerYear)));
						}
					}
					
					int interviewedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocations.getId(), rsh.getSubmissionId().getRequirement().getId(), "INTERVIEWED");
					rshDTO.setNumOfInterview(interviewedCount);
					
						List<RequirementOffers> offersList = requirementOffersService.getRequirementOfferSubmissionByIdAndOrgLocationId(rsh.getSubmissionId().getId(), loginOrgLocations.getId());
						for (RequirementOffers requirementOffers : offersList) {
							rshDTO.setOfferId(requirementOffers.getId());
							float rate = conversionUtil.rateConvertToYear(requirementOffers.getAmount(),requirementOffers.getPaymentMode(),loginOrgLocations);
							rshDTO.setOfferRateforYearly(String.format("%.3f", Float.valueOf(rate)));
							rshDTO.setOfferRate(requirementOffers.getAmount());
							rshDTO.setPaymentType(requirementOffers.getPaymentType());
							rshDTO.setPaymentMode(requirementOffers.getPaymentMode());
							rshDTO.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),requirementOffers.getDateOfJoining()));
							if(null != requirementOffers.getPositionType()){
								rshDTO.setPositionType(requirementOffers.getPositionType());
							}
							if(null != requirementOffers.getFileName()){
								rshDTO.setAttachment(requirementOffers.getFileName());
							}
							if("OFFERMADE".equalsIgnoreCase(rsh.getToStage())){
								rshDTO.setAppliedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate()));
							}
						}
							
					
					
					rshDTOList.add(rshDTO);
				}
				responseList.setRecordsFiltered(toDayReqApplicantscount.getRecordsFiltered());
				responseList.setRecordsTotal(toDayReqApplicantscount.getRecordsTotal());
				responseList.setData(rshDTOList);
		
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
		
    }

	
	@Async
	@RequestMapping(value = "/getAllJobsPipelineReoportsList", method = RequestMethod.POST)
	@ResponseBody
	Response<JobPipelineDTO> getAllJobsPipelineReoportsList(JobFilterDTO jobFilterDTO, HttpServletRequest request, @RequestParam(defaultValue = "0") long orgRelationId, @RequestParam(defaultValue = "0") long spocId) throws Exception
	{
		Response<JobPipelineDTO> response = new Response<>();
		try{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null){
				Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
				OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				List<Long> jobIdList = new ArrayList<>();
				Date[] searchDates = null;
				if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
					if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
						searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
					}else {
						Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
						DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
						Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
			    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
						String customStartTimeStr = TZ_DF_CSD.format(fromDate);
						String customEndTimeStr = TZ_DF_CED.format(toDate);
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
						fromDate = TZ_DF_TS.parse(startDateString);
						toDate = TZ_DF_TS.parse(endDateString);
						
			    	    searchDates = new Date[2];
			    	    searchDates[0] = fromDate;
			    	    searchDates[1] = toDate;
					}
				}
				
				if(jobFilterDTO.getFromtype() != null && jobFilterDTO.getFromtype().equalsIgnoreCase(Constants.CLIENT_REPORT)) {
					
					jobIdList = getClientRequirementList(orgLocation,user,searchDates);
				}else{
					if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
						String[] jobIds = jobFilterDTO.getJobIds().split(",");
						for(int i=0; i<jobIds.length; i++) {
							jobIdList.add(Long.valueOf(jobIds[i]));
						}
					}else {
						jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
						if(orgRelationId > 0) {							
							//above jobIdList is released to vendor job id's
							//now get the parentJobId based on vendorReleasedJob and get the jobStages for that vendor resubmitted job('submit to client')
							jobIdList = requirementReportService.getParentRequirementIdListByRequirementIds(jobIdList);
						}
					}
				 }
//				System.out.println("PerformanceReportRestController : JobId - " + jobIdList.size());
			    	int applicantsSum = 0;
					int requestSentSum = 0;
					int shortlistedSum = 0;
					int submitToClientSum = 0;
					int interviewsSum = 0;
					int offeredSum = 0;
					int offeracceptedSum = 0;
					int rejectedSum = 0;
					int backoutSum = 0;
					Map<Integer,Long> applicantMap = new HashMap<>();
					Map<Integer,Long> shortlistedMap = new HashMap<>();
					Map<Integer,Long> interviewMap = new HashMap<>();
					Map<Integer,Long> offerMadeMap = new HashMap<>();
					Map<Integer,Long> offerAcceptedMap = new HashMap<>();
					Map<Integer,Long> submitToClientMap = new HashMap<>();
					Map<Integer,Long> backOutMap = new HashMap<>();
					Map<Integer,Long> rejectedMap = new HashMap<>();
					
					double applicantDuration    = 0l;
					double shortlistedDuration    = 0l;
					double interviewDuration     = 0l;
					double offerMadeDuration      = 0l;
					double offerAcceptedDuration = 0l;
					double submitToClientDuration = 0l;
					double backOutDuration        = 0l;
					double rejectDuration        = 0l;
					
					int rejectedFromSubmissionCount = 0;
		            int rejectedFromShortlistedCount = 0;
		            int rejectedFromInterviewCount = 0;
		            int rejectedFromOffermadeCount = 0;
		            int rejectedFromRequestSentCount = 0;
		            int rejectedFromOfferAccepetCount = 0;
		            int rejectedFromSubmitTOClientCount = 0;
		            int rejectedFromNewCount = 0;
		            
					JobPipelineDTO report = new JobPipelineDTO();
					
					Integer openJobcount = new Integer(0);
					Integer onholdJobcount = new Integer(0);
					Integer closedJobcount = new Integer(0);
					Integer unfilledclosedJobcount = new Integer(0);
					Integer filledclosedJobcount = new Integer(0);
					Integer cancelJobcount = new Integer(0);
					
					List<Requirement> requirementList = null;
					
					if(orgRelationId > 0) {
						requirementList = jobsPipelineReportService.vendorRequirementListStatusCountByJobIdsAndDates(jobIdList, searchDates);
					} else {
						requirementList = jobsPipelineReportService.requirementListStatusCountByJobIdsAndDates(jobIdList, searchDates);
					}
					
					if(requirementList != null && !requirementList.isEmpty()) {
						openJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("OPEN")).collect(Collectors.toList()).size();
						onholdJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("ONHOLD")).collect(Collectors.toList()).size();
						closedJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("CLOSED")).collect(Collectors.toList()).size();
						unfilledclosedJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("UNFILLEDCLOSED")).collect(Collectors.toList()).size();
						filledclosedJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("FILLEDCLOSED")).collect(Collectors.toList()).size();
						cancelJobcount = requirementList.stream().filter(r->r.getRequirementSubmissionStatus().toString().equalsIgnoreCase("CANCEL")).collect(Collectors.toList()).size();
					}

					int count = 1;
					int dupCount = 0;
					
					List<RequirementSubmission> reqSubmissionList = jobsPipelineReportService.getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(jobIdList, searchDates);
			    	
			    	if(null != reqSubmissionList && reqSubmissionList.size() > 0){
				    	for(RequirementSubmission reqSub : reqSubmissionList){
							int applicantsCount = 0;
							int requestSentCount = 0;
							int shortlistedCount = 0;
					 		int submitToClientCount = 0;
							int interviewsCount = 0;
							int offeredCount = 0;
							int offeracceptedCount = 0;
							int rejectedCount = 0;
							int backoutCount = 0;
							
							List<RequirementSubmissionHistory> requirementSubmissionHistoryList = jobsPipelineReportService.getAllJobsReportsCountsBySubmmisonId(reqSub.getId());
							
							if(orgRelationId > 0) {
					    		String masterUserCode = "";
					    		if (spocId > 0) {
					    			OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
					    			if(orgSpoc != null) {
					    				masterUserCode = "'" + orgSpoc.getVendorUser().getUserCode() + "'";
					    			}
					    		} else {
					    			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelationId);
					    			if (orgSpocList != null && !orgSpocList.isEmpty()) {
					    				for (OrgSpoc orgSpoc : orgSpocList) {
					    					masterUserCode += (masterUserCode.isEmpty() ? "" : ",") + "'" + orgSpoc.getVendorUser().getUserCode() + "'";
					    				}
					    			}
					    		}
					    		requirementSubmissionHistoryList = jobsPipelineReportService.getAllJobsReportsCountsBySubmmisonIdAndMasterUserCode(reqSub.getId(), masterUserCode);
					    	} else {
					    		requirementSubmissionHistoryList = jobsPipelineReportService.getAllJobsReportsCountsBySubmmisonId(reqSub.getId());
					    	}
							
							if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
								Date requirementCreatedDate = reqSub.getRequirement().getCreatedDate();
								Date applicantCreatedDate = null;
								Date shortlistCreatedDate = null;
								Date interviewCreatedDate = null;
								Date offerMadeCreatedDate = null;
								Date offerAcceptCreatedDate = null;
								Date submitToClientCreatedDate = null;
								Date backOutCreatedDate = null;
								Date rejectedCreatedDate = null;
								Date rejectedFromCreatedDate = null;
								Map<String,Date> createdDatesMap = new LinkedHashMap<>();
								for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
									createdDatesMap.put(rsh.getToStage(), rsh.getCreatedDate());
								}
								for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
									
									if("submission".equalsIgnoreCase(rsh.getToStage())){
										applicantsCount++;
										dupCount++;
										applicantCreatedDate= rsh.getCreatedDate();
									} if("shortList".equalsIgnoreCase(rsh.getToStage()) || (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade"))|| 
											(rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("interview"))){
										shortlistedCount++;
										dupCount++;
										if((rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade"))|| (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("interview")) 
												|| (rsh.getFromStage().equalsIgnoreCase("shortList") && rsh.getToStage().equalsIgnoreCase("interview"))) {
											shortlistCreatedDate = createdDatesMap.get("SUBMISSION");
										}else {
											shortlistCreatedDate = rsh.getCreatedDate();
										}
										
									} if("interview".equalsIgnoreCase(rsh.getToStage()) || (rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade")) || (rsh.getFromStage().equalsIgnoreCase("shortList") && ((rsh.getToStage().equalsIgnoreCase("offermade") || rsh.getToStage().equalsIgnoreCase("client_offermade")) || rsh.getToStage().equalsIgnoreCase("client_interview")))
											|| ((rsh.getFromStage().equalsIgnoreCase("submit_to_client") || rsh.getFromStage().equalsIgnoreCase("client_interview")) && rsh.getToStage().equalsIgnoreCase("client_interview"))){
										interviewsCount++;
										dupCount++;
										if(rsh.getFromStage().equalsIgnoreCase("submission") && rsh.getToStage().equalsIgnoreCase("offermade")) {
											interviewCreatedDate = createdDatesMap.get("SUBMISSION");
										}else if(rsh.getFromStage().equalsIgnoreCase("shortList") && rsh.getToStage().equalsIgnoreCase("offermade")) {
											interviewCreatedDate = createdDatesMap.get("SHORTLIST");
										}else {
											interviewCreatedDate = rsh.getCreatedDate();
										}
										
									} if("offermade".equalsIgnoreCase(rsh.getToStage())  ||  "client_offermade".equalsIgnoreCase(rsh.getToStage())){
										offeredCount++;
										dupCount++;
										offerMadeCreatedDate = rsh.getCreatedDate();
									} if("request_sent".equalsIgnoreCase(rsh.getToStage())){
										requestSentCount++;
										dupCount++;
									} if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
										submitToClientCount++;
										dupCount++;
										submitToClientCreatedDate = rsh.getCreatedDate();
									} if("rejected".equalsIgnoreCase(rsh.getToStage())){
										rejectedCount++;
										dupCount++;
										rejectedCreatedDate = rsh.getCreatedDate();
										if(rsh.getFromStage().equalsIgnoreCase("submission")) {
											rejectedFromSubmissionCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("SUBMISSION");
										}else if(rsh.getFromStage().equalsIgnoreCase("shortlist")) {
											rejectedFromShortlistedCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("SHORTLIST");
										}else if(rsh.getFromStage().equalsIgnoreCase("interview")){
											rejectedFromInterviewCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("INTERVIEW");
										}else if(rsh.getFromStage().equalsIgnoreCase("offermade")){
											rejectedFromOffermadeCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("OFFERMADE");
										}else if(rsh.getFromStage().equalsIgnoreCase("request_sent")){
											rejectedFromRequestSentCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("REQUEST_SENT");
										}else if(rsh.getFromStage().equalsIgnoreCase("offeraccepted")){
											rejectedFromOfferAccepetCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("OFFERACCEPTED");
										}else if(rsh.getFromStage().equalsIgnoreCase("submit_to_client")){
											rejectedFromSubmitTOClientCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("SUBMIT_TO_CLIENT");
										}else if(rsh.getFromStage().equalsIgnoreCase("NEW")){
											rejectedFromNewCount++;
											rejectedFromCreatedDate =  createdDatesMap.get("NEW");
										}
									} if("backout".equalsIgnoreCase(rsh.getToStage())){
										backoutCount++;
										backOutCreatedDate = rsh.getCreatedDate();
									} if("offeraccepted".equalsIgnoreCase(rsh.getToStage())){
										offeracceptedCount++;
										offerAcceptCreatedDate = rsh.getCreatedDate();
									}
								}
								
								if(applicantCreatedDate != null && requirementCreatedDate != null) {
									applicantMap.put(count, CalendarUtil.getNumberOfDays(requirementCreatedDate,applicantCreatedDate));
								}
								
								if(shortlistCreatedDate != null && applicantCreatedDate != null) {
									shortlistedMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,shortlistCreatedDate));
								}
								if(interviewCreatedDate != null && shortlistCreatedDate != null) {
									interviewMap.put(count, CalendarUtil.getNumberOfDays(shortlistCreatedDate,interviewCreatedDate));
								}
								if(submitToClientCreatedDate != null && applicantCreatedDate != null) {
									submitToClientMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,submitToClientCreatedDate));
								}
								if(offerMadeCreatedDate != null && interviewCreatedDate != null) {
									offerMadeMap.put(count, CalendarUtil.getNumberOfDays(interviewCreatedDate, offerMadeCreatedDate));
								}
								if(backOutCreatedDate != null && applicantCreatedDate != null) {
									backOutMap.put(count, CalendarUtil.getNumberOfDays(applicantCreatedDate,backOutCreatedDate));
								}
								if(offerAcceptCreatedDate != null && offerMadeCreatedDate != null) {
									offerAcceptedMap.put(count, CalendarUtil.getNumberOfDays(offerMadeCreatedDate,offerAcceptCreatedDate));
								}
								if(rejectedCreatedDate != null && rejectedFromCreatedDate != null) {
									rejectedMap.put(count, CalendarUtil.getNumberOfDays(rejectedFromCreatedDate,rejectedCreatedDate));
								}
								
								 applicantsSum = applicantsSum+applicantsCount;
								 requestSentSum = requestSentSum+requestSentCount;
								 shortlistedSum = shortlistedSum+shortlistedCount;
								 submitToClientSum = submitToClientSum+submitToClientCount;
								 interviewsSum = interviewsSum+interviewsCount;
								 offeredSum = offeredSum+offeredCount;
								 offeracceptedSum = offeracceptedSum+offeracceptedCount;
								 rejectedSum = rejectedSum+rejectedCount;
								 backoutSum = backoutSum + backoutCount;
							}
							count++;
						}
				    	if(applicantMap != null && !applicantMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :applicantMap.keySet()) {
				    			totalDays = totalDays+applicantMap.get(flag);
				    		}
				    		applicantDuration = Math.round((double)totalDays/(double)(applicantMap.size()));
				    	}
				    	
				    	if(shortlistedMap != null && !shortlistedMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :shortlistedMap.keySet()) {
				    			totalDays = totalDays+shortlistedMap.get(flag);
				    		}
				    		shortlistedDuration = Math.round((double)totalDays/(double)(shortlistedMap.size()));
				    	}
				    	if(interviewMap != null && !interviewMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :interviewMap.keySet()) {
				    			totalDays = totalDays+interviewMap.get(flag);
				    		}
				    		interviewDuration = totalDays/(interviewMap.size());
				    	}
				    	if(offerMadeMap != null && !offerMadeMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :offerMadeMap.keySet()) {
				    			totalDays = totalDays+offerMadeMap.get(flag);
				    		}
				    		offerMadeDuration = Math.round((double)totalDays/(double)(offerMadeMap.size()));
				    	}
				    	if(offerAcceptedMap != null && !offerAcceptedMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :offerAcceptedMap.keySet()) {
				    			totalDays = totalDays+offerAcceptedMap.get(flag);
				    		}
				    		offerAcceptedDuration = (double)totalDays/(double)(offerAcceptedMap.size());
				    	}

				    	if(submitToClientMap != null && !submitToClientMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :submitToClientMap.keySet()) {
				    			totalDays = totalDays+submitToClientMap.get(flag);
				    		}
				    		submitToClientDuration = Math.round((double)totalDays/(double)(submitToClientMap.size()));
				    	}
				    	
				    	if(backOutMap != null && !backOutMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :backOutMap.keySet()) {
				    			totalDays = totalDays+backOutMap.get(flag);
				    		}
				    		backOutDuration = Math.round((double)totalDays/(double)(backOutMap.size()));
				    	}
				    	
				    	if(rejectedMap != null && !rejectedMap.isEmpty()) {
				    		Long totalDays = 0l;
				    		for(Integer flag :rejectedMap.keySet()) {
				    			totalDays = totalDays+rejectedMap.get(flag);
				    		}
				    		rejectDuration = Math.round((double)totalDays/(double)(rejectedMap.size()));
				    	}
				    	//days
				    	report.setApplicantsDays(applicantDuration);
				    	report.setShortlistedDays(shortlistedDuration);
				    	report.setInterviewsDays(interviewDuration);
				    	report.setOfferedDays(offerMadeDuration);
				    	report.setOfferedAcceptedDays(offerAcceptedDuration);
				    	report.setSubmitToClientDays(submitToClientDuration);
				    	report.setBackoutDays(backOutDuration);
				    	report.setRejectedDays(rejectDuration);
				    	
				    	//count 
				    	report.setApplicantsCount(applicantsSum);
				    	report.setRequestSentCount(requestSentSum);
				    	report.setShortlistedCount(shortlistedSum);
				    	report.setSubmitToClientCount(submitToClientSum);
				    	report.setInterviewsCount(interviewsSum);
				    	report.setOfferedAcceptedCount(offeracceptedSum);
				    	report.setOfferedCount(offeredSum);
				    	report.setRejectedCount(rejectedSum);
				    	report.setBackoutCount(backoutSum);
				    	
				    	
				    	//Moveforward percentage
				    	report.setShortlistedMove(Math.round(((double)shortlistedSum/(double)applicantsSum)*100));
				    	report.setSubmitToClientMove(Math.round(((double)submitToClientSum/(double)shortlistedSum)*100));
				    	report.setInterviewsMove(Math.round(((double)interviewsSum/(double)shortlistedSum)*100));
				    	if(interviewsSum<offeredSum) {
					    	report.setOfferedMove(Math.round((((double)offeredSum/(double)interviewsSum)*100)/offeredSum));
				    	}else {
					    	report.setOfferedMove(Math.round(((double)offeredSum/(double)interviewsSum)*100));
				    	}
				    	report.setOfferedAcceptedMove(Math.round(((double)offeracceptedSum/(double)offeredSum)*100));
				    	report.setBackoutMove(Math.round(((double)backoutSum /(double)offeracceptedSum)*100));
				    	
				    	Double rejectedMove = 0d;
				    	int rejctMoveCount = 0;
				    	if(rejectedFromNewCount > 0 & applicantsSum >0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromNewCount/(double)applicantsSum)*100);
				    	}
				    	if(rejectedFromSubmissionCount > 0 && applicantsSum >0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromSubmissionCount/(double)applicantsSum)*100);
				    	}
				    	if(rejectedFromShortlistedCount > 0 && shortlistedSum >0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromShortlistedCount/(double)shortlistedSum)*100);
				    	}
				    	if(rejectedFromInterviewCount > 0 && interviewsSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromInterviewCount/(double)interviewsSum)*100);
				    	}
				    	if(rejectedFromOffermadeCount > 0 && offeredSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromOffermadeCount/(double)offeredSum)*100);
				    	}
				    	if(rejectedFromRequestSentCount > 0 && applicantsSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromRequestSentCount/(double)applicantsSum)*100);
				    	}
				    	if(rejectedFromOfferAccepetCount > 0 && offeracceptedSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromOfferAccepetCount/(double)offeracceptedSum)*100);
				    	}
				    	if(rejectedFromSubmitTOClientCount > 0 && submitToClientSum > 0) {
				    		rejctMoveCount++;
				    		rejectedMove = rejectedMove+(((double)rejectedFromSubmitTOClientCount/(double)submitToClientSum)*100);
				    	}
				    	report.setRejectedMove(Math.round(rejectedMove/rejctMoveCount));
				    	 
				    	//jobs count
				    	report.setAllJobsCount(openJobcount + onholdJobcount + closedJobcount + cancelJobcount + unfilledclosedJobcount + filledclosedJobcount);
				    	report.setOpenJobsCount(openJobcount);
				    	report.setOnHoldJobsCount(onholdJobcount);
				    	report.setClosedJonbsCount(closedJobcount + unfilledclosedJobcount + filledclosedJobcount);
				    	report.setCancelledjobsCount(cancelJobcount);
			    	
			    	}
			    	
			    	if(dupCount > 0) {
			    		report.setApplicantsMove(100);
			    	}else {
			    		report.setApplicantsMove(0);
			    	}
			    	
			    	if(report != null){
			    		response.setObject(report);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}	
			    	
			}else if (loginUser == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
			
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
		 
	}


	private List<Long> getClientRequirementList(OrgLocations orgLocation, User user, Date[] searchDates) {
		List<Long> jobIds = new ArrayList<>();
		List<OrgRelations> clientList = clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), user.getId());
		if(clientList != null && clientList.size() > 0) {
			for(OrgRelations orgRelation : clientList) {
				List<Requirement> jobsList = clientReportService.getClientJobsByOrgRelationId(orgRelation.getId(), orgLocation.getId(), user.getId(), searchDates, null);
				if(jobsList != null && jobsList.size() > 0) {
					for(Requirement req :jobsList) {
						jobIds.add(req.getId());
					}
				}
			}
		}
		return jobIds;
	}

}
