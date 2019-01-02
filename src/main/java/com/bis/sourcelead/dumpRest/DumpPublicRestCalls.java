package com.bis.sourcelead.dumpRest;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AppliedLeaveDateDao;
import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.dao.PayrollPaySlipIndDao;
import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectClientDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.ProjectMembersHistoryDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.payroll.dao.BisUserMappingDao;
import com.bis.sourcelead.payroll.dao.InvoiceDataDao;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.AccountDeductionsService;
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
import com.bis.sourcelead.payroll.service.HrDeductionsService;
import com.bis.sourcelead.payroll.service.UserPayrollService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.LeaveManagementUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Component
@RequestMapping("/app")
public class DumpPublicRestCalls {
	
	private static final Logger logger = LoggerFactory.getLogger(DumpPublicRestCalls.class);
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private LeaveManagementUtil leaveManagementUtil;
	
	@Autowired
	private AppliedLeaveDateDao appliedLeaveDateDao;
	
	@Autowired
	HolidayDAO holidayDao;
	
	@Autowired
	ProjectsDao projectsDao;
	
	@Autowired
	ProjectClientDao projectClientDao;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	ProjectMembersHistoryDao projectMembersHistoryDao;
	
	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
	ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	WageTaxSummaryService wageTaxSummaryService;
	
	@Autowired
	EmployerPayrollCostService employerPayrollCostService;
	
	@Autowired
	AccountDeductionsService accountDeductionsService;
	
	@Autowired
	HrDeductionsService hrDeductionsService;
	
	@Autowired
	UserPayrollService userPayrollService;
	
	@Autowired
	BisUserMappingDao bisUserMappingDao;
	
	@Autowired
	InvoiceDataDao invoiceDataDao;
	
	@Autowired
	PayrollPayheadDao payrollPayheadDao;
	
	@Autowired
	ExpensesEntriesService expensesEntriesService;
	
	@Autowired
	PayrollPaySlipIndDao payrollPaySlipIndDao;
	
	@RequestMapping(value = "/ws/dumpAllAppliedLeaveDatesData", method = RequestMethod.GET)
	@ResponseBody
	public List<AppliedLeaveDate> dumpAllAppliedLeaveDatesData() {
		
		logger.info("Start Time"+java.time.LocalDateTime.now());
		List<AppliedLeaveDate> list = new ArrayList<>();
		List<Leave> leavesList = leaveService.getAllLeave();
		leavesList.forEach(leave -> {
			boolean existed = appliedLeaveDateDao.checkForExistedLeave(leave);
			if(!existed) {
				  list.add(getAppliedLeaveDateSet(leave));
			}
		});
		logger.info("End Time"+java.time.LocalDateTime.now());
		return list;
		
	}
	
	@RequestMapping(value = "/ws/dumpExpenseCategorySubmittedAmount", method = RequestMethod.GET)
	@ResponseBody
	public String updateExpenseSubmittedAmount() {
		logger.info("Start Time" + java.time.LocalDateTime.now());
//		List<ExpensesEntries> expensesEntriesList = expensesEntriesService.getAllEntries();
		int count = expensesEntriesService.dumpExpenceCategoryAmountToOldRecords();
		return count + " Records updated";
	}
	
	@RequestMapping(value = "/ws/updateAllAppliedLeaveDatesDataForWeekOffs", method = RequestMethod.GET)
	@ResponseBody
	public List<AppliedLeaveDate> updateAllAppliedLeaveDatesDataForWeekOffs() {
		
		logger.info("Start Time"+java.time.LocalDateTime.now());
		List<AppliedLeaveDate> list = new ArrayList<>();
		List<Leave> leavesList = leaveService.getAllLeave();
		leavesList.forEach(leave -> {
			boolean existed = appliedLeaveDateDao.checkForExistedLeave(leave);
			if(existed) {
				  list.add(getAppliedLeaveDateSetForWeekOffs(leave.getOrgLocations(),leave));
			}
		});
		logger.info("End Time"+java.time.LocalDateTime.now());
		return list;
	}
	private AppliedLeaveDate getAppliedLeaveDateSetForWeekOffs(OrgLocations orgLocations,Leave leave)
	{
		   try {
			     String[] strArray = orgLocations.getWeekOffDays().split(",");
			     List<String> weekOffs = new ArrayList<String>(Arrays.asList(strArray));
			 	 SimpleDateFormat sf = new SimpleDateFormat("E");
				 List<Date> dateList = leaveManagementUtil.getDaysBetweenLeaveDates(leave.getFromDate(), leave.getToDate());
				 for(Date d : dateList) {
					 if(weekOffs.contains(sf.format(d))) {
						 AppliedLeaveDate ald = appliedLeaveDateDao.getAppliedLeaveDateByleaveIdAndAppliedLeaveDate(d, leave.getId());
						 ald.setAppliedHours(CommonUtil.getTimeFormat(null, 0f));
						 appliedLeaveDateDao.saveAppliedLeaveDates(ald);
					 }else {
						 continue;
					 }
				 }
		    }
			catch(Exception e) {}
		
		return null;
	}
	private AppliedLeaveDate getAppliedLeaveDateSet(Leave leave)
	{
		Set<AppliedLeaveDate> set = new HashSet<>();
		AppliedLeaveDate apld = null;
		try {
			 Calendar cal = Calendar.getInstance();
			List<Date> dateList = leaveManagementUtil.getDaysBetweenLeaveDates(leave.getFromDate(), leave.getToDate());
			for(Date d : dateList) {
				AppliedLeaveDate appliedLeaveDate = new AppliedLeaveDate();
				appliedLeaveDate.setApplyLeaveDate(d);
				appliedLeaveDate.setApplyMonthName(getMonthName(d).toUpperCase());
				appliedLeaveDate.setLeave(leave);
				
				float fromMinutes = 0;
				java.sql.Date date = new java.sql.Date(d.getTime());
				java.sql.Date lFromDate = new java.sql.Date(leave.getFromDate().getTime());
				java.sql.Date lToDate = new java.sql.Date(leave.getToDate().getTime());
				if(lFromDate.equals(date)){
					if(Constants.STATUS_YES.equalsIgnoreCase(leave.getFromHalfDay())){
						fromMinutes = leaveManagementUtil.calculateAppliedLeaveMinutes(leave.getFromDateFromHalfTime(), leave.getFromDateToHalfTime(), leave.getOrgLocations().getAverageWorkingHours(), leave.getLeaveType());
					}else{
						fromMinutes = leave.getOrgLocations().getAverageWorkingHours() * 60;
					}
				}else if(lToDate.equals(date)){
					if(Constants.STATUS_YES.equalsIgnoreCase(leave.getToHalfDay())){
						fromMinutes = leaveManagementUtil.calculateAppliedLeaveMinutes(leave.getToDateFromHalfTime(), leave.getToDateToHalfTime(), leave.getOrgLocations().getAverageWorkingHours(), leave.getLeaveType());
					}else{
						fromMinutes = leave.getOrgLocations().getAverageWorkingHours() * 60;
					}
				}else{
					fromMinutes = leave.getOrgLocations().getAverageWorkingHours() * 60;
				}
				appliedLeaveDate.setAppliedHours(CommonUtil.getTimeFormat(null, fromMinutes));
				
				
				
				appliedLeaveDate.setIsPayrollInclude(Constants.STATUS_NO);
//				appliedLeaveDate.setCreatedBy(user.getId());
				appliedLeaveDate.setCreatedDate(new Date());
//				appliedLeaveDate.setUpdatedBy(user.getId());
				appliedLeaveDate.setUpdatedDate(new Date());
				appliedLeaveDate.setYear(cal.get(Calendar.YEAR));
				set.add(appliedLeaveDate);
				apld = appliedLeaveDateDao.saveAppliedLeaveDates(appliedLeaveDate);
			}
			return apld;
		} catch (Exception e) {
			logger.error("AppliedLeaveDate failed due to " + e.getMessage());
		}
		return apld;
	}
	
	private String getMonthName(Date date) {
		  Format formatter = new SimpleDateFormat("MMM"); 
		    String monthName = formatter.format(date);
		    return monthName;
	}

	@RequestMapping(value = "/ws/dumpOrgAvgHrsForAllHolidays", method = RequestMethod.GET)
	@ResponseBody
	public List<Holiday> dumpOrgAvgHrsForAllHolidays() throws Exception {
		logger.info("Start Time"+java.time.LocalDateTime.now());
		List<Holiday> list = holidayDao.getAllHoliday();
		for(Holiday day : list){
			if(day.getOrgLocations().getAverageWorkingHours() != null) {
				day.setOrgAvgHrs(CommonUtil.getTimeFormat(Float.valueOf(day.getOrgLocations().getAverageWorkingHours()), null));
			}else {
				day.setOrgAvgHrs(CommonUtil.getTimeFormat(8f, null));
			}
			holidayDao.storeHoliday(day);
		}
		logger.info("End Time"+java.time.LocalDateTime.now());
		return list;
	}
	
	
	@RequestMapping(value = "/ws/dumpProjectMembersForProjectClients", method = RequestMethod.GET)
	@ResponseBody
	public String dumpProjectMembersForProjectClients() {
		
		logger.info("Start Time"+java.time.LocalDateTime.now());
		List<Projects> projectsList;
		try {
			projectsList = projectsDao.getAllProjects();
			for(Projects projects : projectsList) {
				try {
					List<Long> projectClientsIdsList =  projectClientDao.getProjectClientsIDsByProjectId(projects.getId());
					if(projectClientsIdsList != null && !projectClientsIdsList.isEmpty()) {
						
								try {
									List<ProjectMembers> projectMembersList = projectMembersDao.getAssignedProjectMembersGroupListByProjectClientId(projectClientsIdsList);
									if(projectMembersList != null && !projectMembersList.isEmpty()) {
										for(ProjectMembers projectMembers : projectMembersList) {
											
											for(Long pcId : projectClientsIdsList) {
												Boolean flag = projectMembersDao.isProjectMembersExisted(projects.getId(), projectMembers.getMasterCandidateId().getId(), pcId);
												   if(flag != null && flag == false) {
													   ProjectMembers pm = new ProjectMembers();
													   ProjectClients pc = projectClientDao.getProjectClientsById(pcId);
													   Candidate candidate = candidateService.findByCandidateEmail(projectMembers.getMasterCandidateId().getEmail(), pc.getOrgLocationsId().getId());
												       if(candidate == null) {
												    	   candidate = new Candidate();
												    	   OrgSpoc orgSpoc = null;
															candidate.setMasterCandidateId(projectMembers.getMasterCandidateId());
															if(projectMembers.getProjectMemberCandidteId().getConsultantUser() != null) {
																candidate.setConsultantUser(projectMembers.getProjectMemberCandidteId().getConsultantUser());
															}
															
												    	   
												    	   ProjectClients vendorPc = projectClientDao.getProjectClientsByProjectIdAndLevel(projects.getId(), pc.getLevel()+1);
												    	   if(vendorPc != null && vendorPc.getOrgRelationId() != null) {
												    		   Set<OrgSpoc> orgSpocList = vendorPc.getOrgRelationId().getOrgSpocList();
																 for(OrgSpoc spoc : orgSpocList){
																	 orgSpoc =  spoc;
																 }
																 candidate.setOrgRelationId(vendorPc.getOrgRelationId());
																 candidate.setSpocId(orgSpoc);
																 Candidate parentCandidate = candidateService.findByCandidateEmail(projectMembers.getMasterCandidateId().getEmail(), vendorPc.getOrgLocationsId().getId());
																 if(parentCandidate != null) {
																	 candidate.setParentCandidateId(parentCandidate);
																	 candidate.setCountry(parentCandidate.getCountry());
																 }
																 
												    	   }
												    	   candidate.setCandidateType("Candidate");
												    	   candidate.setFirstName(projectMembers.getMasterCandidateId().getFirstName());
												    	   candidate.setLastName(projectMembers.getMasterCandidateId().getLastName());
												    	   candidate.setEmail(projectMembers.getMasterCandidateId().getEmail());
														   candidate.setOrgLocationId(pc.getOrgLocationsId());
														   candidate.setStatus(1);
														   candidate.setIsAssociate(Constants.NO);
														   candidate.setDesignationType("C2C");
														   candidate = candidateService.addCandidate(candidate);
									    				   candidateService.saveOrUpdateCandidateElkDocument(candidate);
												       }
												        pm.setProjects(projects);
												        ProjectClients pclient = new ProjectClients();
												        pclient.setId(pcId);
												        pm.setMasterCandidateId(projectMembers.getMasterCandidateId());
												        pm.setProjectMemberCandidteId(candidate);
												        pm.setProjectClientsId(pclient);
												        pm.setProjectAssignedDate(projectMembers.getProjectAssignedDate());
														pm.setProjectEndDate(projectMembers.getProjectEndDate());
														pm.setCreatedDate(projectMembers.getCreatedDate());
														pm.setUpdatedDate(projectMembers.getUpdatedDate());
														pm.setCreatedBy(projectMembers.getCreatedBy());
														pm.setUpdatedBy(projectMembers.getUpdatedBy());
														pm.setPayRateCurrency(projectMembers.getPayRateCurrency());
														pm.setRateType(projectMembers.getRateType());
														pm.setPayRateType(projectMembers.getPayRateType());
														pm.setEstimatedHours(projectMembers.getEstimatedHours());
														pm.setEstimatedBudget(projectMembers.getEstimatedBudget());
														pm.setStatus(projectMembers.getStatus());
														pm.setIsManuallyTerminated(projectMembers.getIsManuallyTerminated());
														pm.setTerminateReason(projectMembers.getTerminateReason());
														pm = projectMembersDao.saveProjectMember(pm);
														
														ProjectMembersHistory pmh = new ProjectMembersHistory();
														pmh.setProjectMemberId(pm);
														pmh.setProjectAssignedDate(projectMembers.getProjectAssignedDate());
														pmh.setProjectTerminationDate(projectMembers.getProjectEndDate());
														if(projectMembers.getProjectEndDate() != null) {
															pmh.setStatus("CLOSED");
														}else {
															pmh.setStatus("OPEN");
														}
														pmh.setCreatedDate(projectMembers.getCreatedDate());
														pmh.setUpdatedDate(projectMembers.getUpdatedDate());
														pmh.setCreatedBy(projectMembers.getCreatedBy());
														pmh.setUpdatedBy(projectMembers.getUpdatedBy());
														projectMembersHistoryDao.updateProjectMembersHistory(pmh);
														
														
														List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyDao.getSubmissionApproverHierarchyByProjectIdAndMasterCandidateId(projects.getId(), projectMembers.getMasterCandidateId().getId());
													   if(sahList != null && !sahList.isEmpty()) {
														for(SubmissionApproverHierarchy sah : sahList) {
														if(sah != null) {
															TimesheetSubmissions timesheetSubmissions = sah.getSubmissionId();
														    Date frequencyStartDate = sah.getFrequencyStartDate();
														    Date frequencyEndDate =sah.getFrequencyEndDate();
														    Submission_Status submissionStatus = sah.getSubmissionStatus();
														    Submission_ApprovalStatus submissionApprovalStatus = sah.getSubmissionApprovalStatus();
															ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
															HashSet<SubmissionApproverHierarchy> freshSaHierarchyList = new HashSet<SubmissionApproverHierarchy>();
															List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyDao
																	.getProjectsHierarchyListByProjectIdAndOrgLocationId(projects.getId(),pc.getOrgLocationsId().getId());
																			
															if (hierarchyList != null && !hierarchyList.isEmpty()) {
																for (ProjectApproverHierarchy hierarchy : hierarchyList) {
																	SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
																	saHierarchy.setHeirarchyId(hierarchy);
																	saHierarchy.setSubmissionId(timesheetSubmissions);
																	saHierarchy.setSubmissionStatus(submissionStatus);
																	saHierarchy.setSubmissionApprovalStatus(submissionApprovalStatus);
																	saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
																	if (frequencyStartDate != null)
																		saHierarchy.setFrequencyStartDate(frequencyStartDate);
																	if (frequencyEndDate != null)
																		saHierarchy.setFrequencyEndDate(frequencyEndDate);
																	saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
																	saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
																	saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
																	saHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
																	freshSaHierarchyList.add(saHierarchy);
																}
																timesheetSubmissions.setApproverHierarchyList(freshSaHierarchyList);
															}
															timesheetSubmissionsList.add(timesheetSubmissions);
															timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
												     }
												   }
													   }
												   }
											}
										}
									
									}
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		logger.info("End Time"+java.time.LocalDateTime.now());
		return "success";
	}

	@RequestMapping(value = "/ws/dumpUUIdforPayrollEntities", method = RequestMethod.GET)
	@ResponseBody
	public void dumpUUIdforPayrollEntities() throws Exception {
		logger.info("Start Time"+java.time.LocalDateTime.now());
		
		List<OrgLocations> orgLocList = orgLocationService.getAllOrgLocations();
		for(OrgLocations orgLoc : orgLocList){
			List<WageTaxSummary> wtsList = wageTaxSummaryService.getWagesAndTaxListByEmployeeId(orgLoc, null);
			for(WageTaxSummary wts : wtsList){
				wts.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				wageTaxSummaryService.saveWageTaxSummary(wts);
			}
			logger.info("dumping uuid for WageTaxSummary completed");
			
			List<EmployerPayrollCost> payrollCostList = employerPayrollCostService.getEmployerPayrollCostByLocationId(orgLoc.getId());
			for(EmployerPayrollCost emp : payrollCostList){
				emp.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				employerPayrollCostService.saveEmpPayrollCost(emp);
			}
			logger.info("dumping uuid for EmployerPayrollCost completed");
			
			List<AccountDeductions> accountDeductions = accountDeductionsService.getAccountDeductionsByEmployeeId(orgLoc, 0l);
			for(AccountDeductions ad : accountDeductions){
				ad.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				accountDeductionsService.saveAccountDeductions(ad);
			}
			logger.info("dumping uuid for AccountDeductions completed");
			
			List<HrDeductions> hrdList = hrDeductionsService.getHrDeductionsByEmployeeId(orgLoc, 0l);
			for(HrDeductions hrd : hrdList){
				hrd.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				hrDeductionsService.saveHrDeductions(hrd);
			}
			logger.info("dumping uuid for HrDeductions completed");
			
			List<Checks> checkList = userPayrollService.getChecksListByEmployeeId(null, orgLoc);
			for(Checks che : checkList){
				che.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				bisUserMappingDao.saveChecks(che);
			}
			logger.info("dumping uuid for checks completed");
			
			List<InvoiceData> invoiceDataList = invoiceDataDao.getInvoiceDetailsByEmpId(null, orgLoc);
			for(InvoiceData id : invoiceDataList){
				id.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				invoiceDataDao.saveInvoiceData(id);
			}
			logger.info("dumping uuid for InvoiceData completed");
		}
		
		logger.info("End Time"+java.time.LocalDateTime.now());
	}
	
	
	/*@RequestMapping(value = "/ws/generateINDPayrollPayheadForOldOrgs", method = RequestMethod.GET)
	@ResponseBody
	public void generateINDPayrollPayheadForOldOrgs() throws Exception {
		logger.info("Start Time"+java.time.LocalDateTime.now());
		OrgLocations orgLoc = new OrgLocations();
		orgLoc.setId(1l);
		List<PayrollPayhead> payheadList = payrollPayheadDao.getPayrollPayheadList(orgLoc);
		List<OrgLocations> orgLocList = orgLocationService.getAllOrgLocations();
		for(OrgLocations loc : orgLocList){
			List<PayrollPayhead> existed = payrollPayheadDao.getPayrollPayheadList(loc);
			for(PayrollPayhead payHead : payheadList) {
				boolean phNew = true;
				if(existed.size() > 0) {
					for(PayrollPayhead pHeadOld : existed) {
						if(payHead.getShortCode().equalsIgnoreCase(pHeadOld.getShortCode())) {
							phNew = false;
						}
					}
				}
				if(phNew) {
					PayrollPayhead newPayhead = new PayrollPayhead();
					newPayhead.setName(payHead.getName());
					newPayhead.setShortCode(payHead.getShortCode());
					newPayhead.setEffectiveDate(payHead.getEffectiveDate());
					newPayhead.setType(payHead.getType());
					newPayhead.setPayType(payHead.getPayType());
					newPayhead.setForm16(payHead.getForm16());
					newPayhead.setTax(payHead.getTax());
					newPayhead.setApplicableFor(payHead.getApplicableFor());
					newPayhead.setExperienceYears(payHead.getExperienceYears());
					newPayhead.setExperienceMonths(payHead.getExperienceMonths());
					newPayhead.setFbpComponent(payHead.getFbpComponent());
					newPayhead.setPaidComponent(payHead.getPaidComponent());
					newPayhead.setComponent(payHead.getComponent());
					newPayhead.setPartOfGratitutiy(payHead.getPartOfGratitutiy());
					newPayhead.setLeaveDependent(payHead.getLeaveDependent());
					newPayhead.setShowInPayslip(payHead.getShowInPayslip());
					newPayhead.setPfApplicable(payHead.getPfApplicable());
					newPayhead.setEsiApplicable(payHead.getEsiApplicable());
					newPayhead.setMethod(payHead.getMethod());
					newPayhead.setFormula(payHead.getFormula());
					newPayhead.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					newPayhead.setStatus(payHead.getStatus());
					newPayhead.setOrgLocationId(loc);
					payrollPayheadDao.savePayrollPayhead(newPayhead);
				}
			}
			logger.info("Org Payhead>> "+java.time.LocalDateTime.now());
		}
		logger.info("End Time"+java.time.LocalDateTime.now());
	}*/
	
	
	@RequestMapping(value = "/ws/dumpDefaultPayslipAllOrgs", method = RequestMethod.GET)
    public void dumpSubmissionTemplateFileds(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Start the method");
		List<OrgLocations> locationsList = orgLocationService.getAllOrgLocations();
        for (OrgLocations orgLocation : locationsList) {
    	 
        	     PayrollPaySlipInd	 pps = new PayrollPaySlipInd();
    			 pps.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
    			 pps.setIsActive(Constants.YES);
    			 pps.setDeletePermission(Constants.NO);
    		
    		     pps.setPaySlipName("Default Payslip");
    		     pps.setPaySlipDescription("Default Payslip");
    		
    	     	//Company releated code
    			pps.setCompanyName(Constants.YES);
    			pps.setCompanyAddress(Constants.YES);
    			pps.setCompanyLogo(Constants.YES);
    		
    		    //Employee Reated code
    			pps.setEmployeeId(Constants.YES);
    			pps.setEmployeeType(Constants.YES);
    			pps.setEmployeeUanNumber(Constants.YES);
    			pps.setEmployeePfNumber(Constants.YES);
    			pps.setEmployeeBankDetails(Constants.YES);
    			pps.setEmployeeDesignation(Constants.YES);
    			pps.setEmployeeName(Constants.YES);
    			pps.setEmployeeContactDetails(Constants.YES);
    			pps.setEmployeeJoinDate(Constants.YES);
    			pps.setEmployeeLocation(Constants.YES);
    			pps.setEmployeePanNumber(Constants.YES);
    		
    		    //Leave Related code
    			pps.setLeaveAttended(Constants.YES);
    			pps.setLeaveLeaves(Constants.YES);
    			
    			pps.setOrgLocationId(orgLocation);
    			pps.setStatus(Constants.RECORD_ACTIVE);
    			pps.setCreatedDate(new Date());
    		
    		payrollPaySlipIndDao.savePayrollPaySlipInd(pps);
        }
		System.out.println("End the method");

    }
	
}
