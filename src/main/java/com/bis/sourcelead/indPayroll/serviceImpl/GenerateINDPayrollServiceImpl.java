package com.bis.sourcelead.indPayroll.serviceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.IndPayrollPayheadUtilDTO;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.AssociateSalaryPayheadInd;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.indPayroll.dao.GenerateINDPayrollDao;
import com.bis.sourcelead.indPayroll.dto.INDPayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.dto.PreCheckINDPayrollDTO;
import com.bis.sourcelead.indPayroll.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.indPayroll.service.GenerateINDPayrollService;
import com.bis.sourcelead.indPayroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.PayrollPaySlipIndService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.timesheet.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class GenerateINDPayrollServiceImpl implements GenerateINDPayrollService{
	
	@Autowired
	GenerateINDPayrollDao generateINDPayrollDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
	@Autowired
	AssociateSalaryService associateSalaryService;
	
	@Autowired
	UserGroupDao userGroupDao;
	
	@Autowired
	IndPaysheetFrequencyService indPaysheetFrequencyService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	PayrollPayheadService payrollPayheadService;
	
	@Autowired
	TimeSheetDayEntriesService timeSheetDayEntriesService;
	
	@Autowired
	ExpensesDao expensesDao;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	TaxService taxService;
	
	@Autowired
	PayrollPaySlipIndService payrollPaySlipIndService;
	
	@Autowired
	LeaveService leaveService;

	private static final Logger logger = LoggerFactory.getLogger(GenerateINDPayrollServiceImpl.class);

	@Override
	public IndPaysheetFrequencyDTO getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO,IndPaysheetFrequency paysheetFrequency, OrgLocations orgLocation,User loginUser) {
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		if (paysheetFrequency != null && paysheetFrequency.getIndEmployeepayroll() != null) {
			try {
					indPaysheetFrequencyDTO = iNDPayrollCommonUtil.getIndPaysheetFrequencyDTOFromIndPaysheetFrequency(paysheetFrequency, orgLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return indPaysheetFrequencyDTO;
	}

	@Override
	public IndPaysheetFrequencyDTO generateINDPayroll(OrgLocations orgLocation,GenerateINDPayrollDTO generateINDPayrollDTO, User loginUser) throws Exception {

		String dates[] = generateINDPayrollDTO.getPayPeriod().split(",");
		Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		
		
		String[] groupId = generateINDPayrollDTO.getGroupId().split(",");
		List<AssociateSalaryInd> assSallist = associateSalaryService.getIndAssociateSalaryListByGroupIdForInitiatePayRoll(orgLocation,Long.parseLong(groupId[1]),generateINDPayrollDTO.getPaytype(),freqStartDate,freqEndDate);
		UserGroup userGroup = userGroupDao.findUserGroupById(groupId[0]);
		if (assSallist != null && assSallist.size() >0) {
		     IndPaysheetFrequency paySheetFreq = new IndPaysheetFrequency();
			 Long payRunId = generateINDPayrollDao.getMaxPayRunIdByOrgLocationId(orgLocation.getId());
			 if(payRunId == null) {
				payRunId = 1l;
			 }else {
				payRunId = payRunId+1;
			 }
			 paySheetFreq.setPayRunId(payRunId);
			paySheetFreq.setUserGroup(userGroup);
			paySheetFreq.setOrgLocation(orgLocation);
			String[] payPeriod = generateINDPayrollDTO.getPayPeriod().split(",");
			Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[0]);
			paySheetFreq.setFrequencyStartDate(startDate);
			Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[1]);
			paySheetFreq.setFrequencyEndDate(endDate);
			paySheetFreq.setPayCheckDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),generateINDPayrollDTO.getPayrollPayDate()));
			paySheetFreq.setCreatedDate(new Date());
			paySheetFreq.setUpdatedDate(new Date());
			paySheetFreq.setCreatedBy(loginUser.getId());
			paySheetFreq.setUpdatedBy(loginUser.getId());
			paySheetFreq.setStatus(1);
			paySheetFreq.setApproverStatus(PayrollConstant.NEW);
			paySheetFreq.setActionPerformedBy(""+loginUser.getFullName());
			paySheetFreq.setPaySheetFrequencyUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			
			
			Set<IndEmployeePayroll> payrollList = calculatePayroll(paySheetFreq, loginUser, orgLocation,assSallist,freqStartDate, freqEndDate,userGroup, generateINDPayrollDTO.getPreCheckINDPayrollDTO());
			if(payrollList !=null && payrollList.size() > 0) {
				paySheetFreq.setIndEmployeepayroll(payrollList);
			}
			indPaysheetFrequencyDTO = getPayrollData(generateINDPayrollDTO,paySheetFreq,orgLocation,loginUser);
				if(indPaysheetFrequencyDTO != null && indPaysheetFrequencyDTO.getIndPayrollDTO() != null) {
					List<INDPayrollDTO> draftPayroll = null;
					if(Constants.ALL_RECORDS.equalsIgnoreCase(generateINDPayrollDTO.getPaytype())) {
						draftPayroll = indPaysheetFrequencyDTO.getIndPayrollDTO()
								.stream()
								.filter(p->p.getEmployeeDesignationType() != null && (PayrollConstant.NEW.equalsIgnoreCase(p.getApproverStatus()) || PayrollConstant.DRAFT.equalsIgnoreCase(p.getApproverStatus()))&& !PayrollConstant.C2C.equalsIgnoreCase(p.getEmployeeDesignationType()))
								.collect(Collectors.toList());
					}else {
						draftPayroll = indPaysheetFrequencyDTO.getIndPayrollDTO()
								.stream()
								.filter(p->p.getEmployeeDesignationType() != null && (PayrollConstant.NEW.equalsIgnoreCase(p.getApproverStatus()) || PayrollConstant.DRAFT.equalsIgnoreCase(p.getApproverStatus())) && p.getPayType().equalsIgnoreCase(generateINDPayrollDTO.getPaytype()) && !PayrollConstant.C2C.equalsIgnoreCase(p.getEmployeeDesignationType()))
								.collect(Collectors.toList());
					}
					indPaysheetFrequencyDTO.setIndPayrollDTO(draftPayroll);
				}
		}
		return indPaysheetFrequencyDTO;
	}
	
	@SuppressWarnings({ "static-access", "null" })
	private Set<IndEmployeePayroll> calculatePayroll(IndPaysheetFrequency paySheetFreq, User loginUser, OrgLocations orgLocation, List<AssociateSalaryInd> assoSalList, Date freqStartDate, Date freqEndDate, UserGroup userGroup, PreCheckINDPayrollDTO preCheckINDPayrollDTO)
	{
		Set<IndEmployeePayroll> set = new HashSet<>();
		try {
			for (AssociateSalaryInd assoSal : assoSalList) {
				Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(assoSal.getCandidate().getConsultantUser().getId(),orgLocation.getId());
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(assoSal.getCandidate().getConsultantUser().getId(), orgLocation.getId());
				long frequencyPayrollDays = TimeUnit.MILLISECONDS.toDays(paySheetFreq.getFrequencyEndDate().getTime() - paySheetFreq.getFrequencyStartDate().getTime()) + 1;
				long applincablePayrollDays = frequencyPayrollDays;
				if(employee.getHireDate().after(freqEndDate) || (userRole.getTerminateDate() != null && userRole.getUserState().toString().equalsIgnoreCase("INACTIVE") &&  userRole.getTerminateDate().before(freqStartDate))) {
					continue;
				}else if(employee.getHireDate() != null && paySheetFreq.getFrequencyStartDate().compareTo(employee.getHireDate()) * employee.getHireDate().compareTo(paySheetFreq.getFrequencyEndDate()) >= 0){
					applincablePayrollDays =  TimeUnit.MILLISECONDS.toDays(paySheetFreq.getFrequencyEndDate().getTime() - employee.getHireDate().getTime()) + 1;
				}else if(userRole.getUserState().toString().equalsIgnoreCase("INACTIVE") && userRole.getTerminateDate() != null && paySheetFreq.getFrequencyStartDate().compareTo(userRole.getTerminateDate()) * userRole.getTerminateDate().compareTo(paySheetFreq.getFrequencyEndDate()) >= 0){
					applincablePayrollDays =  TimeUnit.MILLISECONDS.toDays(userRole.getTerminateDate().getTime() - paySheetFreq.getFrequencyStartDate().getTime()) + 1;
				}
				
				    IndEmployeePayroll employeePayroll = new IndEmployeePayroll();
				    employeePayroll.setIndPaysheetFrequency(paySheetFreq);
					double totalHours = 0;
					BigDecimal payRate = BigDecimal.ZERO;
					BigDecimal amount = BigDecimal.ZERO;
					BigDecimal regularHoursAmount = BigDecimal.ZERO;
					List<TotalHoursDTO> totalHoursDto = userGroupDao.getTotalHoursByCandidateId(assoSal.getCandidate().getId(),freqStartDate,freqEndDate);
					try {
						Map<BigInteger, List<TotalHoursDTO>> map = totalHoursDto.stream().collect(Collectors.groupingBy(TotalHoursDTO::getProjectId));
						for (Map.Entry<BigInteger, List<TotalHoursDTO>> entry : map.entrySet()) {
							BigDecimal projectGross = BigDecimal.ZERO;
							double projectTotalHours = 0;
							BigDecimal projectPayRate = BigDecimal.ZERO;
							if(entry.getValue() !=null ) {
								for(TotalHoursDTO totalHoursDTO : entry.getValue()) {
									projectPayRate = new BigDecimal(totalHoursDTO.getPayRate());
									if(StringUtils.isNotBlank(totalHoursDTO.getBillableHours())) {
										projectTotalHours  = commonUtil.getTotalRegularOverTimeHours(Double.parseDouble(totalHoursDTO.getBillableHours()),projectTotalHours);
									}
								}
							}
							projectGross = getTotalAmount(projectTotalHours,projectPayRate);
							amount = amount.add(projectGross);
							payRate = payRate.add(projectPayRate);
							totalHours = totalHours + projectTotalHours;
						}
					}catch (Exception ex) {
						ex.printStackTrace();
					}
					 
					regularHoursAmount = amount;
				    if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
						 amount = assoSal.getGrossSalary();
					}
					employeePayroll.setPayType(assoSal.getPayType());
					employeePayroll.setPayPerSalary(amount);
					employeePayroll.setRegularHours(String.valueOf(totalHours));
					employeePayroll.setEmpPayRate(payRate);
					employeePayroll.setCandidate(assoSal.getCandidate());
					employeePayroll.setPayrollPaySlipInd(payrollPaySlipIndService.getActivePayslipByOrglocationId(orgLocation.getId()));
					employeePayroll.setPayCheckDate(commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), paySheetFreq.getPayCheckDate()));
					employeePayroll.setRegularHoursAmount(regularHoursAmount);
					employeePayroll.setType(Constants.REGULAR);
					if (employee != null) {
						employeePayroll.setEmployee(employee);
					}
					
					Set<AssociateSalaryPayheadInd> salaryPayheadList = new HashSet<AssociateSalaryPayheadInd>();
					for(AssociateSalaryPayheadInd associateSalaryPayheadInd : assoSal.getAssociateSalaryPayheadIndList().stream().filter(phead -> phead.getStatus() > 0).collect(Collectors.toList())) {
						//verification for each salary payhead is eligible in this frequency
						if(!"NO".equalsIgnoreCase(associateSalaryPayheadInd.getPayrollPayhead().getActive())) {
							boolean isPayheadApplincable = true;
							if((StringUtils.isNotBlank(associateSalaryPayheadInd.getPayrollPayhead().getApplicableFor()) && associateSalaryPayheadInd.getPayrollPayhead().getApplicableFor().equalsIgnoreCase("Experienced"))) {
								Date joinDate = null;
								if(employee.getHireDate() != null) {
									joinDate = employee.getHireDate();
								}else {
									joinDate = employee.getCreatedDate();
								}
								Date eligibleDate = null;
								eligibleDate = DateUtils.addYears(joinDate, Integer.parseInt(StringUtils.isNotBlank(associateSalaryPayheadInd.getPayrollPayhead().getExperienceYears()) ? associateSalaryPayheadInd.getPayrollPayhead().getExperienceYears() : "0" ));
								eligibleDate = DateUtils.addMonths(eligibleDate, Integer.parseInt(StringUtils.isNotBlank(associateSalaryPayheadInd.getPayrollPayhead().getExperienceMonths()) ? associateSalaryPayheadInd.getPayrollPayhead().getExperienceMonths() : "0" ));
								if(eligibleDate != null && eligibleDate.before(freqStartDate)) {
									isPayheadApplincable = false;
								}
							}
							if(associateSalaryPayheadInd.getPayrollPayhead() != null && associateSalaryPayheadInd.getPayrollPayhead().getEffectiveDate() != null && associateSalaryPayheadInd.getPayrollPayhead().getEffectiveDate().before(freqEndDate) 
									&& isPayheadApplincable ) {
								salaryPayheadList.add(associateSalaryPayheadInd);
							}else if(associateSalaryPayheadInd.getPayrollPayhead() != null && associateSalaryPayheadInd.getPayrollPayhead().getEffectiveDate() == null
									&& isPayheadApplincable ) {
								salaryPayheadList.add(associateSalaryPayheadInd);
							}
						}
					}
					
					
					// Calculating LOP
					BigDecimal lopAmount = BigDecimal.ZERO;
					Set<PayrollLopHis> lopList = iNDPayrollCommonUtil.getLOPAmountInd(employee,assoSal,freqStartDate,freqEndDate,orgLocation,amount,employeePayroll);
					if(lopList != null && lopList.size() > 0) {
						Double lopAmont = lopList.stream().mapToDouble(lop->lop.getLopAmount().doubleValue()).sum();
						lopAmount = new BigDecimal(lopAmont);
						employeePayroll.setPayrollLopHis(lopList);
					}
					employeePayroll.setLopAmount(lopAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN));
					
					employeePayroll.setNoOfDays(new BigDecimal(applincablePayrollDays));
					employeePayroll.setNoOfLeaves( lopList != null ? new BigDecimal(lopList.stream().mapToDouble(lop->lop.getTotalAppliedLeaves()).sum()): BigDecimal.ZERO );
					
					//payAdjustmentConst is used to adjust the lop and no of working days while calculation payhead per frequency
					BigDecimal payAdjustmentConst = (new BigDecimal(applincablePayrollDays).subtract(employeePayroll.getNoOfLeaves())).divide(new BigDecimal(frequencyPayrollDays), 2, RoundingMode.HALF_UP);
					
					
					IndPayrollPayheadUtilDTO utilDTO = iNDPayrollCommonUtil.getAssociatePayrollPayHeadHistoryFromAssociateSalaryPayHeads(salaryPayheadList, employeePayroll, loginUser,userGroup.getPayFrequency(), payAdjustmentConst);
					
					AssociateLoanAndAdvancesDTO assLoanAndAdvDTO = iNDPayrollCommonUtil.getAssLoanAndAdvanceDedAmountInd(orgLocation,paySheetFreq,loginUser,assoSal.getCandidate(),employeePayroll);
					//calculate L&D
					boolean[] isLD = {false};
					Double[] loanAdvTotal = {0.0};
					
					//calculate PT
					boolean[] isPT = {false};
					Double[] ptAmount = {0.0};
					
					//calculate ARR
					boolean[] isARR = {false};
					
					if(utilDTO.getAssociatePayrollPayheadHistory() != null) {
						//if L&D in Associate Payroll Payheads
						utilDTO.getAssociatePayrollPayheadHistory().forEach(ph -> {
							if(ph.getPayrollPayhead().getShortCode().equalsIgnoreCase("L&D")) {
								loanAdvTotal[0] = iNDPayrollCommonUtil.calculateSumOfAllLoanAdvOfEmp(assLoanAndAdvDTO);
								ph.setMonthly(new BigDecimal(loanAdvTotal[0]).setScale(2, BigDecimal.ROUND_HALF_EVEN));
								ph.setAnnualy(new BigDecimal(loanAdvTotal[0]*12).setScale(2, BigDecimal.ROUND_HALF_EVEN));
								isLD[0] = true;
							}
						});
						
						//if L&D not found in Associate Payroll Payheads
						if(!isLD[0]) {
							PayrollPayhead ppla = payrollPayheadService.getPayrollPayheadByShortCodeAndOrglocation("L&D", orgLocation.getId());
 							if(!"NO".equalsIgnoreCase(ppla.getActive())) {
								loanAdvTotal[0] = iNDPayrollCommonUtil.calculateSumOfAllLoanAdvOfEmp(assLoanAndAdvDTO);
								AssociatePayrollPayheadHistory ph = new AssociatePayrollPayheadHistory();
								ph.setIndEmployeePayrollId(employeePayroll);
								ph.setPayrollPayhead(ppla);
								ph.setMonthly(new BigDecimal(loanAdvTotal[0]).setScale(2, BigDecimal.ROUND_HALF_EVEN));
								ph.setAnnualy(new BigDecimal(loanAdvTotal[0]*12).setScale(2, BigDecimal.ROUND_HALF_EVEN));
								ph.setStatus(Constants.RECORD_ACTIVE);
								ph.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
								ph.setCreatedBy(loginUser);
								ph.setUpdatedBy(loginUser);
								ph.setCreatedDate(new Date());
								ph.setUpdatedDate(new Date());
								if(utilDTO.getAssociatePayrollPayheadHistory() != null) {
									utilDTO.getAssociatePayrollPayheadHistory().add(ph);
								}
 							}
						}
						employeePayroll.setLoanHistory(assLoanAndAdvDTO.getLoanHis());
						
						
						//if PT in Associate Payroll Payheads
						utilDTO.getAssociatePayrollPayheadHistory().forEach(ph -> {
							if(ph.getPayrollPayhead().getShortCode().equalsIgnoreCase("PT")) {
								try {
									ptAmount[0] = iNDPayrollCommonUtil.calculatePTforAssociate(assoSal, paySheetFreq.getFrequencyStartDate(), paySheetFreq.getFrequencyEndDate(), preCheckINDPayrollDTO.getStateTaxDTO());
								} catch (ParseException e) {
									ptAmount[0] = 0.0;
								}
								ph.setMonthly(new BigDecimal(ptAmount[0]).multiply("Y".equalsIgnoreCase(ph.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
								ph.setAnnualy(new BigDecimal(ptAmount[0]*12).setScale(2, BigDecimal.ROUND_HALF_EVEN));
								isPT[0] = true;
							}
						});
						
						//if PT not found in Associate Payroll Payheads
						if(!isPT[0]) {
							PayrollPayhead pppt = payrollPayheadService.getPayrollPayheadByShortCodeAndOrglocation("PT", orgLocation.getId());
 							if(!"NO".equalsIgnoreCase(pppt.getActive())) {
 								ptAmount[0] = iNDPayrollCommonUtil.calculatePTforAssociate(assoSal, paySheetFreq.getFrequencyStartDate(), paySheetFreq.getFrequencyEndDate(), preCheckINDPayrollDTO.getStateTaxDTO());
 								AssociatePayrollPayheadHistory ph = new AssociatePayrollPayheadHistory();
 								ph.setIndEmployeePayrollId(employeePayroll);
 								ph.setPayrollPayhead(pppt);
 								ph.setMonthly(new BigDecimal(ptAmount[0]).multiply("Y".equalsIgnoreCase(ph.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
 								ph.setAnnualy(new BigDecimal(ptAmount[0]*12).setScale(2, BigDecimal.ROUND_HALF_EVEN));
 								ph.setStatus(Constants.RECORD_ACTIVE);
 								ph.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
 								ph.setCreatedBy(loginUser);
 								ph.setUpdatedBy(loginUser);
 								ph.setCreatedDate(new Date());
 								ph.setUpdatedDate(new Date());
 								if(utilDTO.getAssociatePayrollPayheadHistory() != null) {
 									utilDTO.getAssociatePayrollPayheadHistory().add(ph);
 								}
 							}
						}
						
						
						//if ARR in Associate Payroll Payheads
						utilDTO.getAssociatePayrollPayheadHistory().forEach(ph -> {
							if(ph.getPayrollPayhead().getShortCode().equalsIgnoreCase("ARR")) {
								BigDecimal expenses = expensesDao.getExpenseAmountByCandidateIdAndExpenseStartAndEndDateForClaimedExpenses(assoSal.getCandidate().getId(),freqStartDate,freqEndDate);
								ph.setMonthly(expenses.setScale(2, BigDecimal.ROUND_HALF_EVEN));
								ph.setAnnualy(expenses.multiply(new BigDecimal(12)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
								isARR[0] = true;
							}
						});
						
						//if ARR not found in Associate Payroll Payheads
						if(!isARR[0]) {
							PayrollPayhead pparr = payrollPayheadService.getPayrollPayheadByShortCodeAndOrglocation("ARR", orgLocation.getId());
 							if(!"NO".equalsIgnoreCase(pparr.getActive())) {
 								AssociatePayrollPayheadHistory ph = new AssociatePayrollPayheadHistory();
 								ph.setIndEmployeePayrollId(employeePayroll);
 								ph.setPayrollPayhead(pparr);
 								BigDecimal expenses = expensesDao.getExpenseAmountByCandidateIdAndExpenseStartAndEndDateForClaimedExpenses(assoSal.getCandidate().getId(),freqStartDate,freqEndDate);
 								ph.setMonthly(expenses.setScale(2, BigDecimal.ROUND_HALF_EVEN));
 								ph.setAnnualy(expenses.multiply(new BigDecimal(12)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
 								ph.setStatus(Constants.RECORD_ACTIVE);
 								ph.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
 								ph.setCreatedBy(loginUser);
 								ph.setUpdatedBy(loginUser);
 								ph.setCreatedDate(new Date());
 								ph.setUpdatedDate(new Date());
 								if(utilDTO.getAssociatePayrollPayheadHistory() != null) {
 									utilDTO.getAssociatePayrollPayheadHistory().add(ph);
 								}
 							}
						}
					}
					
					
					if(utilDTO != null && utilDTO.getAssociatePayrollPayheadHistory() != null && utilDTO.getAssociatePayrollPayheadHistory().size() > 0) {
						employeePayroll.setAssociatePayrollPayheadHistory(utilDTO.getAssociatePayrollPayheadHistory().stream().filter(app -> app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toSet()));
					}
					
					
					//TDS process initialization
					Double tdsMonthlyTotal = 0.0;
					if(utilDTO.getAssociatePayrollPayheadHistory() != null) {
						AssociatePayrollPayheadHistory ph = iNDPayrollCommonUtil.calculateTDSForEmployee(assoSal, employee, orgLocation, freqStartDate, preCheckINDPayrollDTO, employeePayroll, loginUser);
						tdsMonthlyTotal = ph.getMonthly().doubleValue();
						if(tdsMonthlyTotal > 0) {
							utilDTO.getAssociatePayrollPayheadHistory().add(ph);
							employeePayroll.getAssociatePayrollPayheadHistory().add(ph);
						}
					}
					
					employeePayroll.setGrossPay(utilDTO.getGrossPay() != null ? utilDTO.getGrossPay().multiply(payAdjustmentConst).setScale(2, BigDecimal.ROUND_HALF_EVEN) : new BigDecimal(0));
					employeePayroll.setNetPay(((utilDTO.getNetPay() != null && utilDTO.getNetPay().intValue() > 0 ? utilDTO.getNetPay() : new BigDecimal(0)).subtract(new BigDecimal(tdsMonthlyTotal)).subtract(new BigDecimal(loanAdvTotal[0])).subtract(lopAmount).subtract(new BigDecimal(ptAmount[0])).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
					employeePayroll.setProfessionalTax(new BigDecimal(ptAmount[0]));
				    employeePayroll.setTds(new BigDecimal(tdsMonthlyTotal).setScale(2, BigDecimal.ROUND_HALF_EVEN));
				    employeePayroll.setSubmitForAproverStatus(PayrollConstant.NEW);
					employeePayroll.setIndEmpPayrollUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					employeePayroll.setStatus(Constants.RECORD_ACTIVE);
					employeePayroll.setCreatedBy(loginUser.getId());
					employeePayroll.setUpdatedBy(loginUser.getId());
					employeePayroll.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), new Date()));
					employeePayroll.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), new Date()));
					
					Double overTimeHours =  timeSheetDayEntriesService.getOverTimeHoursByMasterCandidateIdAndOrgLocationWithStartEndDates( assoSal.getCandidate().getMasterCandidateId().getId(), paySheetFreq.getFrequencyStartDate(), paySheetFreq.getFrequencyEndDate(), orgLocation);
					employeePayroll.setOverTimeHours(overTimeHours.toString());
					
					set.add(employeePayroll);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return set;
		
	}
	
		/***
		 * calculating total amount by multipltying hours with rate
		 * @param totalHours	
		 * @param payRate
		 * @return double
		 */
		private BigDecimal getTotalAmount(double totalHours, BigDecimal payRate)
		{
			String pRate = String.valueOf(totalHours);
			String[] rate = pRate.split("\\.");
			double hr = Double.parseDouble(rate[0]);
			double min = Double.parseDouble(rate[1]);
			double hrsToMin = hr * 60;
			double totalMin = hrsToMin + min;
			double perMinRate = payRate.doubleValue() / 60;
			double totatAmount = totalMin * perMinRate;
			return BigDecimal.valueOf(totatAmount);
		}

		@Override
		public Long getMaxPayRunIdByOrgLocationId(Long orgLocationId) {
			return generateINDPayrollDao.getMaxPayRunIdByOrgLocationId(orgLocationId);
		}
	
}