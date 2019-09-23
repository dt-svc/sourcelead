package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.USPaysheetDao;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.dto.USPaysheetDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.Paysheet;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class USPaysheetServiceImpl implements USPaysheetService{
	
	private static final Logger logger = LoggerFactory.getLogger(USPaysheetServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	AssociateTaxService associateTaxService;
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	UserGroupDao userGroupDao;
	
	@Autowired
	USPaysheetDao uSPaysheetDao;
	
	@Autowired
	AssociateDeductionService associateDeductionService;
	
	@Override
	public ResponseList<USPaysheetDTO> generateUSPaysheet(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO)
	{

		String dates[] = generateUSPayrollDTO.getPayPeriod().split(",");
		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		ResponseList<USPaysheetDTO> paysheetResponse = new ResponseList<>();
		
		try {
			List<USPaysheetDTO> list = new ArrayList<>();
			ResponseList<ProjectMembers> response = projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(orgLocation.getId(), actualStartDate, actualEndDate, 1, 10);
			if (response != null && response.getData() != null && !response.getData().isEmpty()) {
				int i = 1;
				for (ProjectMembers pm : response.getData()) {
//					if(pm.getEmployee().getUserEmploymentType()) {
//						
//					}
					Map<String, Float> hoursMap = submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(pm.getId(), orgLocation.getId(), actualStartDate, actualEndDate,"");
					USPaysheetDTO paysheetDTO = new USPaysheetDTO();
					StringBuffer sb = new StringBuffer();
//					float billableHours = hoursMap.get("BILLABLE_HOURS");
					
//					float nonBillableHours = hoursMap.get("NONBILLABLE_HOURS");
					float totalHours = hoursMap.get("TOTAL_HOURS");
					if (StringUtils.isNoneBlank(pm.getProjectMemberCandidteId().getLastName())) {
						sb.append(pm.getProjectMemberCandidteId().getFirstName()).append(" ").append(pm.getProjectMemberCandidteId().getLastName());
					}
					else {
						sb.append(pm.getProjectMemberCandidteId().getFirstName());
					}
				
					paysheetDTO.setEmployeeName(sb.toString());
					paysheetDTO.setProjPayRate(BigDecimal.valueOf(pm.getPayRate()));
					double amount = payrollUtil.getTotalAmount(totalHours,pm.getPayRate());
					
					paysheetDTO.setHours(String.valueOf(totalHours));
					paysheetDTO.setProjBillRate(new BigDecimal(pm.getCurrentBillRate()));
					if(pm.getEmployee() != null) {
						if(pm.getEmployee().getEmployeeId() != null) {
							paysheetDTO.setEmployeeId(pm.getEmployee().getEmployeeId());
						}
					}
//					paysheetDTO.setBillRate(new BigDecimal(pm.getCurrentBillRate()));
					User user = userService.getUserbyConsultantUserId(pm.getProjectMemberCandidteId().getConsultantUser().getId());
					paysheetDTO.setUserId(String.valueOf(user.getId()));
					paysheetDTO.setProjectMemberId(String.valueOf(pm.getId()));
//					AssociateTaxDedContriView associateTaxView = associateTaxService.findUserTaxDeatails(user.getId(), orgLocation);
					// Here Need to Pass Candidate Id instead of userId
				    ResponseList<DeductionDTO> res = associateDeductionService.findAllDeductionSummaryByCandidateId(user.getId(), orgLocation);
					BigDecimal preTax = BigDecimal.ZERO;
					BigDecimal postTax = BigDecimal.ZERO;
				    if(res.getData() != null) {
						double preDeduction = res.getData().stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).mapToDouble(ded->ded.getAmount().doubleValue()).sum();
						preTax = BigDecimal.valueOf(preDeduction);
						paysheetDTO.setPreDeduction(preTax);
						double postDeduction = res.getData().stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).mapToDouble(ded->ded.getAmount().doubleValue()).sum();
						postTax = BigDecimal.valueOf(postDeduction);
						paysheetDTO.setPostDeduction(postTax);
					}
					BigDecimal gross = BigDecimal.valueOf(amount).subtract(preTax);
				    paysheetDTO.setGrossPay(gross);
//					User user = userService.getUserbyConsultantUserId(pm.getProjectMemberCandidteId().getConsultantUser().getId());
//					int invoiceFrequency = payrollUtil.getProjectInvoiceFrequency(pm.getProjects().getInvoiceFrequency());
////					AssociateTaxDTO associateTaxDTO = associateTaxService.findAssociateTaxSummaryByUserId(user.getId(), orgLocation);
//					
////					Projects projects = pm.getProjects().getInvoiceFrequency();
//					HelloPayrollWorldBis helloPayrollWorld = new HelloPayrollWorldBis();
////					SymmetryPaypal client = new SymmetryPaypal();
////					String locationCode = "18-097-452890";
////					String locationCode = "04-013-12233";       // Arizona Location Code  
////					client.hello(locationCode);
//					paysheetDTO.setRegularHrsTotalAmt(BigDecimal.valueOf(amount));
//					paysheetDTO.setPreTaxDeduction(BigDecimal.valueOf(0.00));
//					paysheetDTO.setPostTaxDeduction(BigDecimal.valueOf(0.00));
//					paysheetDTO.setGrossPay(BigDecimal.valueOf(amount));
//					STEPayrollCalculator STE = helloPayrollWorld.runUSPayroll(user,totalHours,amount,invoiceFrequency,associateTaxDTO,STEhome,jniHome);
//					
//					System.out.println("FICA Withholding:" + STE.getFICACalc());
//					System.out.println("Medicare Withholding: " + STE.getMedicareCalc());
//					System.out.println("Federal Withholding: " + STE.getFederalCalc());
//					System.out.println("Earned Inc. Credit: " + STE.getEICCalc());
////					System.out.println("State Withholding: " + STE.getStateCalc(locationCode));
////					System.out.println("County Withholding:" + STE.getCountyCalc(locationCode));
//					paysheetDTO.setTaxes(BigDecimal.valueOf(0.00));
//					paysheetDTO.setNetPay(BigDecimal.valueOf(0.00));
//					if (pm.getEmployee() != null) {
//						paysheetDTO.setEmpId(pm.getEmployee().getEmployeeId());
//					}
//					else {
//						paysheetDTO.setEmpId("--");
//					}
//					paysheetDTO.setRegularHours(totalHours);
					list.add(paysheetDTO);
				}
				paysheetResponse.setData(list);
				paysheetResponse.setRecordsFiltered(list.size());
				paysheetResponse.setRecordsTotal(list.size());
				paysheetResponse.setResponseMessage("success");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return paysheetResponse;	
	
	}

	@Override
	public Response<PaysheetFrequencyDTO> submitPaysheet(OrgLocations orgLocation, PaysheetFrequencyDTO paysheetFrequencyDTO, User loginUser)
	{
		Response<PaysheetFrequencyDTO> response = new Response<>();
		try {
			PaysheetFrequency paysheetFrequency = new PaysheetFrequency();
			UserGroup userGroup = userGroupDao.findUserGroupById(paysheetFrequencyDTO.getGroupId());
			paysheetFrequency.setUserGroup(userGroup);
			paysheetFrequency.setOrgLocation(orgLocation);
			String[] payPeriod = paysheetFrequencyDTO.getPayPeriod().split(",");
			Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[0]);
			paysheetFrequency.setFrequencyStartDate(startDate);
			Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[1]);
			paysheetFrequency.setFrequencyEndDate(endDate);
			paysheetFrequency.setCreatedDate(new Date());
			paysheetFrequency.setUpdatedDate(new Date());
			paysheetFrequency.setCreatedBy(loginUser.getId());
			paysheetFrequency.setType("PAYSHEET");
			paysheetFrequency.setUpdatedBy(loginUser.getId());
			paysheetFrequency.setStatus(1);
			paysheetFrequency.setPaysheet(getPaySheet(paysheetFrequency, loginUser, orgLocation, paysheetFrequencyDTO));
			PaysheetFrequency savedPaysheetFrequency = uSPaysheetDao.addPaysheetFrequency(paysheetFrequency);
			if (savedPaysheetFrequency != null) {
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}
			else {
				response.setResponse(Result.FAILED.name());
				response.setResponseMessage(Result.FAILED.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * prepare paysheet set
	 * @param paysheetFrequency
	 * @param loginUser
	 * @param orgLocation
	 * @param paysheetFrequencyDTO
	 * @return Set<Paysheet>
	 */
	private Set<Paysheet> getPaySheet(PaysheetFrequency paysheetFrequency, User loginUser, OrgLocations orgLocation, PaysheetFrequencyDTO paysheetFrequencyDTO)
	{
		Set<Paysheet> set = new HashSet<>();
		for (USPaysheetDTO paysheetDTO : paysheetFrequencyDTO.getuSPaysheetDTO()) {
			Paysheet paysheet = new Paysheet();
			//Need to set candidate Id
//			paysheet.setUser(Long.parseLong(paysheetDTO.getUserId()));
			paysheet.setPaysheetFrequency(paysheetFrequency);
			paysheet.setAdditionalPay(paysheetDTO.getAdditionalPay());
			paysheet.setBonusAccuralPayPeriod(paysheetDTO.getBonusAccrualPayPeriod());
			paysheet.setBonusAccuralYtd(paysheetDTO.getBonusAccrualYTD());
			paysheet.setEmployeeId(paysheetDTO.getEmployeeId());
			paysheet.setCreatedBy(loginUser.getId());
			paysheet.setCreatedDate(new Date());
			paysheet.setExpensesReimbursment(paysheetDTO.getExpensesReimbursement());
			paysheet.setGrossPay(paysheetDTO.getGrossPay());
			paysheet.setHours(paysheetDTO.getHours());
			paysheet.setLcaSalaryMonth(paysheetDTO.getLcaSalaryMonth());
			paysheet.setModeOfPay(paysheetDTO.getModeOfPay());
			paysheet.setNotes(paysheetDTO.getNotes());
			paysheet.setProjBillRate(paysheetDTO.getProjBillRate());
			paysheet.setProjectMember(Long.parseLong(paysheetDTO.getProjectMemberId()));
			paysheet.setProjPayPercent(paysheetDTO.getProjPayPercent());
			paysheet.setProjPayRate(paysheetDTO.getProjPayRate());
			paysheet.setUpdatedBy(loginUser.getId());
			paysheet.setUpdatedDate(new Date());
			if (!"".equalsIgnoreCase(paysheetDTO.getVendorLastpaidDate()) && paysheetDTO.getVendorLastpaidDate() != null) {
				paysheet.setVendorLastPaidDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), paysheetDTO.getVendorLastpaidDate()));
			}
			paysheet.setStatus(1);
			paysheet.setPreDeduction(paysheetDTO.getPreDeduction());
			paysheet.setPostDeduction(paysheetDTO.getPostDeduction());
			set.add(paysheet);
		}
		return set;
	}

	@Override
	public PaysheetFrequency addPaysheetFrequency(PaysheetFrequency paySheetFreq)
	{
		return uSPaysheetDao.addPaysheetFrequency(paySheetFreq);
	}

	@Override
	public ResponseList<PaysheetFrequencyDTO> getPaySheetFrequencyListByOrgLocationId(OrgLocations orgLocation,PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception {
		ResponseList<PaysheetFrequency> paySheetList = uSPaysheetDao.getPaySheetFrequencyListByOrgLocationId(orgLocation,paysheetFrequencyDTO);
		
		ResponseList<PaysheetFrequencyDTO> res = new ResponseList<>();
		List<PaysheetFrequencyDTO> totalList = new ArrayList<>();
		paySheetList.getData().forEach(pay -> {
			PaysheetFrequencyDTO pfd = new PaysheetFrequencyDTO();
			pfd.setPaySheetFrequencyuuid(pay.getPaySheetFrequencyUUID());
			pfd.setPayFrequency(pay.getUserGroup().getPayFrequency());
			pfd.setPayDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getPayCheckDate()));
			pfd.setPaySheetFrequencyType(pay.getType());
			pfd.setApproverStatus(pay.getApproverStatus());
			
			StringBuilder sbDate = new StringBuilder();
			pfd.setPayPeriod(sbDate.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getFrequencyStartDate())).append(" / ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getFrequencyEndDate())).toString());
		
			Set<EmployeePayroll> payRollSet = pay.getEmployeepayroll();
			double sum = payRollSet.stream().mapToDouble(i->i.getNetPay().doubleValue()).sum();
			pfd.setTotal(BigDecimal.valueOf(sum));
			pfd.setGroupId(String.valueOf(pay.getUserGroup().getId()));
			totalList.add(pfd);
		});
		res.setData(totalList);
		res.setRecordsFiltered(totalList.size());
		res.setRecordsTotal(totalList.size());
		return res;
	}

	@Override
	public PaysheetFrequency getPayFrequencyByUUID(String paySheetFrequencyUUID) {
		return uSPaysheetDao.getPayFrequencyByUUID(paySheetFrequencyUUID);
	}

	@Override
	public PaysheetFrequencyDTO getPaySheetFrequencySummary(PaysheetFrequencyDTO paysheetFrequencyDTO,OrgLocations orgLocation) {
		PaysheetFrequency pf = getPayFrequencyByUUID(paysheetFrequencyDTO.getPaySheetFrequencyuuid());
		PaysheetFrequencyDTO pfDTO = new PaysheetFrequencyDTO(); 
		pfDTO.setPayrollFrequencyUUID(pf.getPaySheetFrequencyUUID());
		pfDTO.setId(String.valueOf(pf.getId()));
		pfDTO.setPaySheetFrequencyId(pf.getId());
		List<USPayrollDTO> list = new ArrayList<>();
		try {
			if (pf != null && pf.getEmployeepayroll() != null) {
				Set<EmployeePayroll> set = null;
				if(paysheetFrequencyDTO.getApproverStatus() != null && paysheetFrequencyDTO.getApproverStatus().equalsIgnoreCase(Constants.DRAFT)) {
					set = pf.getEmployeepayroll().stream().filter(emp->emp.getPaysheetFrequency().getId().longValue() == pfDTO.getPaySheetFrequencyId().longValue() && emp.getSubmitForAproverStatus().equalsIgnoreCase(Constants.DRAFT)).collect(Collectors.toSet());
				}else if(paysheetFrequencyDTO.getApproverStatus() != null && paysheetFrequencyDTO.getApproverStatus().equalsIgnoreCase(Constants.SENT)) {
					set = pf.getEmployeepayroll().stream().filter(p->p.getPayPeriodNum() != null && Integer.parseInt(p.getPayPeriodNum()) == paysheetFrequencyDTO.getPayPeriodNum() && p.getSubmitForAproverStatus().equalsIgnoreCase(Constants.SENT)).collect(Collectors.toSet());
				}else {
					set = pf.getEmployeepayroll().stream().filter(empPayroll->empPayroll.getPayPeriodNum() != null && Integer.parseInt(empPayroll.getPayPeriodNum()) == paysheetFrequencyDTO.getPayPeriodNum() && empPayroll.getSubmitForAproverStatus().equalsIgnoreCase(Constants.APPROVED)).collect(Collectors.toSet());
				}
				
				for(EmployeePayroll pay : set) {
					USPayrollDTO payrollDTO = new USPayrollDTO();
					payrollDTO.setEmpPayrollId(pay.getId());
					payrollDTO.setEmpId(pay.getEmployee().getEmployeeId());
					StringBuilder sb = new StringBuilder();
					if (StringUtils.isNoneBlank(pay.getCandidate().getLastName())) {
						sb.append(pay.getCandidate().getFirstName()).append(" ").append(pay.getCandidate().getLastName());
					}
					else {
						sb.append(pay.getCandidate().getFirstName());
					}
					payrollDTO.setEmpName(sb.toString());
					payrollDTO.setEmpPayrollId(pay.getId());
					payrollDTO.setEmpPayrollUUID(pay.getUsEmpPayrollUUID());
					payrollDTO.setGrossPay(pay.getGrossPay());
					payrollDTO.setNetPay(pay.getNetPay());
					payrollDTO.setRegularHrsTotalAmt(pay.getRegularHoursAmount());
					payrollDTO.setPayRate(pay.getEmpPayRate());
					payrollDTO.setPayPerSal(pay.getPayPerSalary());
					payrollDTO.setPostTaxDeduction(pay.getPostTaxDeduction());
					payrollDTO.setPreTaxDeduction(pay.getPreTaxDeduction());
					String regHours = "0";
					if(StringUtils.isNotBlank(pay.getRegularHours())) {
						regHours = pay.getRegularHours();
					}
					payrollDTO.setRegularHours(regHours);
					payrollDTO.setTaxes(pay.getTaxes());
					payrollDTO.setOtHoursTotalAmt(pay.getOverTimeAmount());
					
					//passing frequency year to calculate ytd
					Date date = pay.getPaysheetFrequency().getFrequencyStartDate();
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					int year = c.get(Calendar.YEAR);
					
					YTDPayrollDTO ytdTaxes = payrollUtil.getEmployeeYTDPayroll(pay.getCandidate(), orgLocation, "APPROVED",year);
					payrollDTO.setYtdDTO(ytdTaxes);
					double overTimeHours = 0;
					if(StringUtils.isNoneBlank(pay.getOverTimeHours())) {
						overTimeHours = Double.parseDouble(pay.getOverTimeHours());
					}
					payrollDTO.setOvertimeHours(overTimeHours);
					if(pay.getPayPeriodNum() != null)
					pfDTO.setPayPeriodNum(Integer.parseInt(pay.getPayPeriodNum()));
					
					list.add(payrollDTO);
				}
				pfDTO.setPayrollDTO(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pfDTO;
	}

}
