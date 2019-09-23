package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateSalaryDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dao.impl.ExpensesDaoImpl;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SalaryDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateContributionDao;
import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
import com.bis.sourcelead.payroll.dao.GenerateUSPayrollDao;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.LCAWageDTO;
import com.bis.sourcelead.payroll.dto.PayAdjustmentDTO;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollLopHisDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.AssociateTaxHistory;
import com.bis.sourcelead.payroll.entity.BankDataHistory;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.EmployerPayrollTax;
import com.bis.sourcelead.payroll.entity.PayAdjustmentDedHistory;
import com.bis.sourcelead.payroll.entity.PayAdjustmentHistory;
import com.bis.sourcelead.payroll.entity.PayAdjustmentPayItemHistory;
import com.bis.sourcelead.payroll.entity.PayitemHistory;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.DeductionHistoryService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.service.PayAdjustmentHistoryService;
import com.bis.sourcelead.payroll.service.PayrollAdjustmentService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.symmetry.PayrollCalculatorDTO;
import com.bis.sourcelead.payroll.symmetry.SymmetryPayrollCalculator;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesHistoryService;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.symmetry.ste.STEPayrollCalculator;

import opennlp.tools.parser.Cons;

@Service
public class GenerateUSPayrollServiceImpl implements GenerateUSPayrollService{

	private static final Logger logger = LoggerFactory.getLogger(GenerateUSPayrollServiceImpl.class);
	
	@Autowired
	GenerateUSPayrollDao generateUSPayrollDao;
	
	@Autowired 
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
	ProjectMembersDao  projectMembersDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociateTaxService associateTaxService;
	
	@Value("${symmetry.STEhome}")
	private String STEhome;
	
	@Value("${symmetry.jniHome}")
	private String jniHome;
	
	@Autowired
	SymmetryPayrollCalculator symmetryPayrollCalculator;
	
	@Autowired
	UserGroupDao userGroupDao;
	
	@Autowired
	USPaysheetService uSPaysheetService;
	
	@Autowired
	AssociateDeductionService associateDeductionService;
	
	@Autowired 
	AssociateContributionService associateContributionService; 
	
	@Autowired
	AssociateSalaryService associateSalaryService;
	
	@Autowired
	PayrollAdjustmentService payrollAdjustmentService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	AssociateDeductionDao associateDeductionDao;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	AssociateContributionDao associateContributionDao;
	
	@Autowired
	DeductionHistoryService deductionHistoryService;
	
	@Autowired
	private AssociateSalaryDao associateSalaryDao;
	
	@Autowired
	PayAdjustmentHistoryService payAdjustmentHistoryService;
	
	@Autowired
	PaysheetFrequencyService paysheetFrequencyService;
	
	@Autowired
	AssociateLoanAndAdvancesService associateLoanAndAdvancesService;
	
	@Autowired
	AssociateLoanAndAdvancesHistoryService associateLoanAndAdvancesHistoryService;
	
	@Autowired
	ExpensesDaoImpl expensesDaoImpl;
	
	@Autowired
	LeaveService  leaveService;
	
	
	@Override
	public PaysheetFrequencyDTO generateUSPayroll(PaysheetFrequencyDTO existedData,OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO,User loginUser) throws Exception
	{
		PaysheetFrequencyDTO paysheetFrequencyDTO = null;
		try {
			String dates[] = generateUSPayrollDTO.getPayPeriod().split(",");
			Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
			Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
			String[] groupId = generateUSPayrollDTO.getGroupId().split(",");
			List<AssociateSalary> totalAssSallist = associateSalaryService.getAssociateSalaryListByGroupId(orgLocation,Long.parseLong(groupId[1]),generateUSPayrollDTO.getPayType());
			List<AssociateSalary> assSallist = new ArrayList<>();
			for(AssociateSalary ass : totalAssSallist) {
				Employees emp = employeesService.getEmployeeByUserAndOrgLocationId(ass.getCandidate().getConsultantUser().getId(), orgLocation.getId());
				List<Long> list = Arrays.stream(ass.getGroupId().getDepartmentId().split(",")).map(Long::parseLong).collect(Collectors.toList());
				if(list.contains(emp.getDepartmentId().getId())) {
					assSallist.add(ass);
				}
			}
			
			UserGroup userGroup = userGroupDao.findUserGroupById(groupId[0]);
			
			if (assSallist != null && assSallist.size() >0) {
				PaysheetFrequency paySheetFreq = null;
				if(existedData != null) {
					 paySheetFreq = new PaysheetFrequency();
					 paySheetFreq.setId(existedData.getPaySheetFrequencyId());
				}else {
					 paySheetFreq = new PaysheetFrequency();
				}
				
				paySheetFreq.setUserGroup(userGroup);
				paySheetFreq.setOrgLocation(orgLocation);
				String[] payPeriod = generateUSPayrollDTO.getPayPeriod().split(",");
				Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[0]);
				paySheetFreq.setFrequencyStartDate(startDate);
				Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[1]);
				paySheetFreq.setFrequencyEndDate(endDate);
				paySheetFreq.setPayCheckDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),generateUSPayrollDTO.getPayrollPayDate()));
				paySheetFreq.setCreatedDate(new Date());
				paySheetFreq.setUpdatedDate(new Date());
				paySheetFreq.setCreatedBy(loginUser.getId());
				paySheetFreq.setUpdatedBy(loginUser.getId());
				paySheetFreq.setStatus(1);
				paySheetFreq.setType(PayrollConstant.PAYROLL);
				paySheetFreq.setApproverStatus(PayrollConstant.DRAFT);
				paySheetFreq.setPaySheetFrequencyUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				Set<EmployeePayroll> payrollList = calculatePayroll(existedData,paySheetFreq, loginUser, orgLocation,assSallist,freqStartDate, freqEndDate,userGroup);
				if(payrollList !=null && payrollList.size() > 0) {
					paySheetFreq.setEmployeepayroll(payrollList);
					uSPaysheetService.addPaysheetFrequency(paySheetFreq);
				}
					paysheetFrequencyDTO = getPayrollData(generateUSPayrollDTO,orgLocation,loginUser);
					if(paysheetFrequencyDTO != null && paysheetFrequencyDTO.getPayrollDTO() != null) {
						List<USPayrollDTO> draftPayroll = null;
						if(Constants.ALL_RECORDS.equalsIgnoreCase(generateUSPayrollDTO.getPayType())) {
							draftPayroll = paysheetFrequencyDTO.getPayrollDTO()
									.stream()
									.filter(p->p.getEmployeeDesignationType() != null && PayrollConstant.DRAFT.equalsIgnoreCase(p.getApproverStatus()) && !PayrollConstant.C2C.equalsIgnoreCase(p.getEmployeeDesignationType()))
									.collect(Collectors.toList());
						}else {
							draftPayroll = paysheetFrequencyDTO.getPayrollDTO()
									.stream()
									.filter(p->p.getEmployeeDesignationType() != null && PayrollConstant.DRAFT.equalsIgnoreCase(p.getApproverStatus()) && p.getPayType().equalsIgnoreCase(generateUSPayrollDTO.getPayType()) && !PayrollConstant.C2C.equalsIgnoreCase(p.getEmployeeDesignationType()))
									.collect(Collectors.toList());
						}
						paysheetFrequencyDTO.setNoOfGeneratedPayroll(draftPayroll.size());
						paysheetFrequencyDTO.setTotalGeneratedAmount(draftPayroll.stream().map(USPayrollDTO::getNetPay).reduce(BigDecimal.ZERO, BigDecimal::add));
						paysheetFrequencyDTO.setPayrollDTO(draftPayroll);
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return paysheetFrequencyDTO;
	}

	@SuppressWarnings("static-access")
	public Set<EmployeePayroll> calculatePayroll(PaysheetFrequencyDTO existedData, PaysheetFrequency paySheetFreq, User loginUser, OrgLocations orgLocation, List<AssociateSalary> assoSalList, Date freqStartDate, Date freqEndDate, UserGroup userGroup)
	{
		Set<EmployeePayroll> set = new HashSet<>();
		try {
			for (AssociateSalary assoSal : assoSalList) {
				if(assoSal.getCandidate().getDesignationType() != null && !PayrollConstant.C2C.equalsIgnoreCase(assoSal.getCandidate().getDesignationType())){
					boolean isExisted = isPayrollRunForSameFrequency(existedData,assoSal);
					if(!isExisted) {
						SalaryDTO sal = checkCandidateTermination(orgLocation,assoSal.getCandidate(),freqStartDate,freqEndDate);
						if(("NO".equalsIgnoreCase(sal.getIsTerminated()) && sal.getJoiningDate().before(freqEndDate)) && (sal.getTerminatedDate() == null || sal.getTerminatedDate().after(freqStartDate))) {
							   EmployeePayroll employeePayroll = new EmployeePayroll();
							    employeePayroll.setPaysheetFrequency(paySheetFreq);
							    AssociateTaxDedContriView associateTaxView = associateTaxService.findUserTaxDeatails(assoSal.getCandidate().getId(), orgLocation);
								String totalHours = "0";
								BigDecimal payRate = BigDecimal.ZERO;
								BigDecimal amount = BigDecimal.ZERO;
								BigDecimal regularHoursAmount = BigDecimal.ZERO;
								List<TotalHoursDTO> totalHoursDto = null;
								if(PayrollConstant.HOURLY_RATE.equalsIgnoreCase(assoSal.getPayType())) {
									 totalHoursDto = userGroupDao.getTotalHoursByCandidateId(assoSal.getCandidate().getId(),freqStartDate,freqEndDate);
									Map<BigInteger, List<TotalHoursDTO>> map = totalHoursDto.stream().collect(Collectors.groupingBy(TotalHoursDTO::getProjectId));
									
									for (Map.Entry<BigInteger, List<TotalHoursDTO>> entry : map.entrySet()) {
										BigDecimal projectGross = BigDecimal.ZERO;
										String projectTotalHours = "0";
										BigDecimal projectPayRate = BigDecimal.ZERO;
										if(entry.getValue() !=null ) {
											for(TotalHoursDTO totalHoursDTO : entry.getValue()) {
//												projectPayRate = new BigDecimal(totalHoursDTO.getPayRate());
												projectPayRate = payrollUtil.getHourlyRate(new BigDecimal(totalHoursDTO.getPayRate()), totalHoursDTO.getPayRateType());
												if(StringUtils.isNotBlank(totalHoursDTO.getBillableHours())) {
													projectTotalHours  = payrollUtil.getTotalRegularOverTimeHours(Double.parseDouble(totalHoursDTO.getBillableHours()),Double.parseDouble(projectTotalHours));
												}
											}
										}
										projectGross = payrollUtil.getTotalAmount(projectTotalHours,projectPayRate);
										amount = amount.add(projectGross);
										payRate = payRate.add(projectPayRate);
										totalHours = projectTotalHours;
									} 
									regularHoursAmount = amount;
								}else if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
									if(sal.getNoOfDays() >0) {
										amount = payrollUtil.calculateSalaryUptoTerminationDate(assoSal.getGroupId().getPayFrequency(),sal.getNoOfDays(),assoSal.getGrossSalary());
									}else {
										amount = assoSal.getGrossSalary();
									}
								}else if(PayrollConstant.LCA_WAGE.equalsIgnoreCase(assoSal.getPayType())) {
									LCAWageDTO lcaWageDTO  = generateUSPayrollDao.getLcaWageByCandidateIdLcationId(assoSal.getCandidate(),orgLocation);
									if(lcaWageDTO != null) {
										if(sal.getNoOfDays() >0) {
											amount = payrollUtil.calculateSalaryUptoTerminationDateLcaWage(new BigDecimal(lcaWageDTO.getLcaWage()),lcaWageDTO.getLcaWageType(),sal.getNoOfDays(),orgLocation);
										}else {
											amount  = payrollUtil.getActualRateByPayFrequency(new BigDecimal(lcaWageDTO.getLcaWage()),lcaWageDTO.getLcaWageType(),assoSal.getGroupId().getPayFrequency());
										}
									}
								}
								if(associateTaxView.getAccountType() != null) {
									employeePayroll.setDepositType(associateTaxView.getAccountType());
								}
								employeePayroll.setPayType(assoSal.getPayType());
								employeePayroll.setPayPerSalary(amount);
								employeePayroll.setRegularHours(totalHours);
								employeePayroll.setEmpPayRate(payRate);
//								User user = userService.getUserById(assoSal.getUserId().getId());
								employeePayroll.setCandidate(assoSal.getCandidate());
								int invoiceFrequency = getProjectInvoiceFrequency(userGroup.getPayFrequency());
								employeePayroll.setFrequency(invoiceFrequency);
								employeePayroll.setPayCheckDate(paySheetFreq.getPayCheckDate());
								
								employeePayroll.setRegularHoursAmount(regularHoursAmount);
								DeductionDTO dedDTO = getDeductionAmount(assoSal.getCandidate(),orgLocation,amount,loginUser,paySheetFreq,employeePayroll);
								BigDecimal[] dedTaxes = dedDTO.getDedTaxes();
								BigDecimal[] contributionTaxes = dedDTO.getContributionTaxes();
								BigDecimal preTax = dedTaxes[0];
								BigDecimal postTax = dedTaxes[1];
								BigDecimal preContriSum = contributionTaxes[0];
								BigDecimal postContriSum = contributionTaxes[1];
								PayItemDTO payItemDTO = getPayItemSum(orgLocation,assoSal.getAssociatePayItem(),paySheetFreq,employeePayroll,loginUser);
								
								//for loan and advances
								AssociateLoanAndAdvancesDTO assLoanAndAdvDTO = getAssLoanAndAdvanceDedAmount(orgLocation,paySheetFreq,loginUser,assoSal.getCandidate(),employeePayroll);
								
								BigDecimal payItemSum = payItemDTO.getPayItemSum();
								employeePayroll.setPayitemSum(payItemSum);
								employeePayroll.setPreTaxDeduction(preTax);
								employeePayroll.setPostTaxDeduction(postTax);
								employeePayroll.setPreTaxContribution(postContriSum);
								employeePayroll.setPostTaxContribution(postContriSum);
								employeePayroll.setGrossPay(amount);
								employeePayroll.setType(Constants.REGULAR);
								Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(assoSal.getCandidate().getConsultantUser().getId(),orgLocation.getId());
								if (employee != null) {
									employeePayroll.setEmployee(employee);
								}
								BigDecimal lopAmount = BigDecimal.ZERO;
								// calculating LOP
								if(Constants.MONTHLY.equalsIgnoreCase(assoSal.getPayFrequency()) && PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
									Set<PayrollLopHis> lopList = payrollUtil.getLOPAmount(employee,assoSal,freqStartDate,freqEndDate,orgLocation,amount,employeePayroll);
									 if(lopList != null) {
										 Double lopAmont = lopList.stream().mapToDouble(lop->lop.getLopAmount().doubleValue()).sum();
										 lopAmount = new BigDecimal(lopAmont);
										 employeePayroll.setPayrollLopHis(lopList);
										 employeePayroll.setLopAmount(lopAmount);
									 }
							    }
								BigDecimal grossAmount = (amount.subtract(preTax)).add(preContriSum);
								
								//passing frequency year to calculate ytd
								Date date = paySheetFreq.getFrequencyStartDate();
								Calendar c = Calendar.getInstance();
								c.setTime(date);
								int year = c.get(Calendar.YEAR);
								
								YTDPayrollDTO ytdTaxes= payrollUtil.getEmployeeYTDPayroll(assoSal.getCandidate(),orgLocation,"APPROVED",year);
								PayrollCalculatorDTO payrollCalculator = symmetryPayrollCalculator.runUSPayroll(assoSal.getCandidate(),Double.parseDouble(totalHours),grossAmount,invoiceFrequency,associateTaxView,paySheetFreq.getPayCheckDate(),ytdTaxes);
								if(payrollCalculator != null) {
									STEPayrollCalculator STE = payrollCalculator.getsTEPayrollCalculator();
									String  locationCode = payrollCalculator.getWebServiceLocationCodeV2().getLocationCode();
									BigDecimal taxes = STE.getFICACalc().add(STE.getMedicareCalc()).add(STE.getFederalCalc()).add(STE.getEICCalc()).asBigDecimal()
											                            .add(STE.getStateCalc(locationCode).asBigDecimal()).add(STE.getCountyCalc(locationCode).asBigDecimal())
											                            .add(STE.getCityCalc(locationCode).asBigDecimal()).add(payrollCalculator.getSUI().asBigDecimal())
											                            .add(payrollCalculator.getSDI().asBigDecimal()).add(payrollCalculator.getFLI().asBigDecimal())
											                            .add(payrollCalculator.getHCSF().asBigDecimal()).add(payrollCalculator.getWC().asBigDecimal());
									employeePayroll.setTaxes(taxes);
									logger.info("*** Taxes :"+taxes);
									BigDecimal totalPay = grossAmount.subtract(taxes);
									BigDecimal netPay = (totalPay.subtract(postTax)).add(postContriSum).add(payItemSum);
									//to deduct loan and advance amount from net pay
									if(assLoanAndAdvDTO.getTotalLoanAmount() != null && assLoanAndAdvDTO.getTotalAdvanceAmount() != null)
									netPay = netPay.subtract((assLoanAndAdvDTO.getTotalLoanAmount().add(assLoanAndAdvDTO.getTotalAdvanceAmount())));
									netPay = netPay.subtract(lopAmount);
									logger.info("*** Net Pay :"+netPay);
									BigDecimal expenses = expensesDaoImpl.getExpenseAmountByCandidateIdAndExpenseStartAndEndDateForClaimedExpenses(assoSal.getCandidate().getId(),freqStartDate,freqEndDate);
									employeePayroll.setExpensesAmount(expenses);
									employeePayroll.setNetPay(netPay.add(expenses));
									employeePayroll.setAdvanceAmount(assLoanAndAdvDTO.getTotalAdvanceAmount());
									employeePayroll.setLoanAmount(assLoanAndAdvDTO.getTotalLoanAmount());
									Set<EmployeePayrollTax> payrollTaxSet = getPayrollTaxes(employeePayroll,orgLocation,payrollCalculator,loginUser);
									employeePayroll.setUsPayrollTax(payrollTaxSet);
									Set<EmployerPayrollTax> employerTaxes = getEmployerTaxes(employeePayroll,orgLocation,payrollCalculator,loginUser,assoSal.getCandidate().getResidenceState());
									employeePayroll.setEmployerpayrolltax(employerTaxes);
									employeePayroll.setSubmitForAproverStatus(PayrollConstant.DRAFT);
								}else {
									employeePayroll.setSubmitForAproverStatus(PayrollConstant.PAYROLL_MISSED);	
								}
								
								Set<BankDataHistory> bankDataHistory = getBankDataHistory(associateTaxView,employeePayroll,loginUser);
								employeePayroll.setBankDataHistory(bankDataHistory);
								
								Set<AssociateTaxHistory> associateTaxHistory = getAssociateTaxHistory(associateTaxView,employeePayroll,loginUser);
								employeePayroll.setAssociateTaxHistory(associateTaxHistory);
								
								if(assLoanAndAdvDTO.getLoanHis() != null && assLoanAndAdvDTO.getLoanHis().size() >0) {
									employeePayroll.setLoanHistory(assLoanAndAdvDTO.getLoanHis());
								}
								employeePayroll.setDeductionHistory(dedDTO.getDedHis());
								employeePayroll.setPayitemHistory(payItemDTO.getPayitemHistory());
								employeePayroll.setUsEmpPayrollUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
								employeePayroll.setStatus(1);
								employeePayroll.setCreatedBy(loginUser.getId());
								employeePayroll.setUpdatedBy(loginUser.getId());
								employeePayroll.setCreatedDate(new Date());
								employeePayroll.setUpdatedDate(new Date());
								if(PayrollConstant.HOURLY_RATE.equalsIgnoreCase(assoSal.getPayType())) {
									if(totalHoursDto != null) {
										set.add(employeePayroll);
									}
								}else {
									set.add(employeePayroll);
								}
						}

					}
					
				}
				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return set;
		
	}
	
	

	/**
	 * Get Employee Terminated Date
	 * @param orgLocation
	 * @param candidate
	 * @param freqStartDate
	 * @param freqEndDate
	 */
   private SalaryDTO checkCandidateTermination(OrgLocations orgLocation,Candidate candidate, Date freqStartDate, Date freqEndDate)
	{
	   SalaryDTO salary = new SalaryDTO();
	   String isTerminated = "NO";
	   int noOfDays = 0;
	   try {
		   SalaryDTO salaryDto = associateSalaryService.getSalaryByGroupId(orgLocation,candidate.getId(),freqStartDate,freqEndDate);
		   SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
			String joiningMonth = month_date.format(salaryDto.getJoiningDate());
			String payrollRunningMonth = month_date.format(freqStartDate);
			String terminatingMonth = "";
			if(salaryDto.getTerminatedDate() != null)
			 terminatingMonth = month_date.format(salaryDto.getTerminatedDate());
		   
		   if(PayrollConstant.N.equalsIgnoreCase(candidate.getIsAssociate())) {
				if(salaryDto != null) {
					if(salaryDto.getTerminatedDate() !=null) {
						boolean terminated = isEmployeeTerminated(salaryDto.getTerminatedDate(),freqStartDate,freqEndDate,candidate.getIsAssociate());
						if(terminated) {
							if(PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(salaryDto.getPayFrequency())){
								if(joiningMonth.equals(payrollRunningMonth)) {
									noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(salaryDto.getJoiningDate(),freqEndDate);
								}else if(terminatingMonth.equals(payrollRunningMonth)) {
									noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(freqStartDate,salaryDto.getTerminatedDate());
								}else {
									noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(freqStartDate,freqEndDate);
								}
								
							}else if(PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(salaryDto.getPayFrequency()) || PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(salaryDto.getPayFrequency())) {
								
								if(salaryDto.getJoiningDate().after(freqStartDate) && salaryDto.getJoiningDate().before(freqEndDate)) {
									noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(salaryDto.getJoiningDate(),freqEndDate);
								}else if(salaryDto.getTerminatedDate().after(freqStartDate) && salaryDto.getTerminatedDate().before(freqEndDate)) {
									noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(freqStartDate,salaryDto.getTerminatedDate());
								}else {
									noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(freqStartDate,freqEndDate);
								}
							}
						}else {
							isTerminated = "YES";	
						}
					}
				}
			} else {
					if(salaryDto.getJoiningDate() != null) {
					    	boolean terminated = isEmployeeTerminated(salaryDto.getJoiningDate(),freqStartDate,freqEndDate,candidate.getIsAssociate());
					    	if(terminated) {
					    		if(PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(salaryDto.getPayFrequency())){
					    		if(joiningMonth.equals(payrollRunningMonth)) {
									noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(salaryDto.getJoiningDate(),freqEndDate);
								}else {
									noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(freqStartDate,freqEndDate);
								}
					    		}else if(PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(salaryDto.getPayFrequency()) || PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(salaryDto.getPayFrequency())) {
					    			if(salaryDto.getJoiningDate().after(freqStartDate) && salaryDto.getJoiningDate().before(freqEndDate)) {
										noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(salaryDto.getJoiningDate(),freqEndDate);
					    			}else {
										noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(freqStartDate,freqEndDate);
									}
					    		}
					    	}else {
					    		if(salaryDto.getJoiningDate().before(freqStartDate)) {
					    			isTerminated = "NO";
					    		}else{
					    			if(salaryDto.getJoiningDate().after(freqStartDate)) {
					    				isTerminated = "YES";
					    			}
					    		}
					    	}
					}
			}
		   salary.setIsTerminated(isTerminated);
		   salary.setNoOfDays(noOfDays);
		   salary.setJoiningDate(salaryDto.getJoiningDate());
		   salary.setTerminatedDate(salaryDto.getTerminatedDate());
	   }catch (Exception e) {
		   e.printStackTrace();
	    }
	return salary;
	}

   
   /**
    * Check employee terminated or not 
    * @param slaryDto
    * @param freqStartDate
    * @param freqEndDate
 * @param isAssociate 
    * @return boolean
    */
   private boolean isEmployeeTerminated(Date jdate, Date freqStartDate, Date freqEndDate, String isAssociate)
	{
	   boolean isTerminated = false;
//	   if((date.equals(freqStartDate) ||date.after(freqStartDate)) && (date.equals(freqEndDate) || date.before(freqEndDate))) {
	   if(PayrollConstant.N.equalsIgnoreCase(isAssociate)) {
		   if(jdate.after(freqStartDate) || jdate.before(freqEndDate)) {
			   isTerminated = true;
			}   
	   }else {
		   if(jdate.after(freqStartDate) && jdate.before(freqEndDate)) {
			   isTerminated = true;
			}   
	   }
	   
		return isTerminated;
	}

private Set<AssociateTaxHistory> getAssociateTaxHistory(AssociateTaxDedContriView associateTaxView,
			EmployeePayroll employeePayroll, User loginUser) {
	   Set<AssociateTaxHistory> taxHis = new HashSet<>();
	   try {
		   AssociateTaxHistory assTaxHis = null;
		   if(associateTaxView.getAssociateTaxId() != null) {
			   assTaxHis = new AssociateTaxHistory();
			   assTaxHis.setAssociateTaxId(associateTaxView.getAssociateTaxId());
			   assTaxHis.setEmployeePayroll(employeePayroll);
			   
			   assTaxHis.setFederalTaxFilliingStatus(associateTaxView.getFederalTaxFilliingStatus());
			   assTaxHis.setFederalTaxAllowances(associateTaxView.getFederalTaxAllowances());
			   assTaxHis.setFederalTaxAdditionnalWithHolding(associateTaxView.getFederalTaxAdditionnalWithHolding());
			   
			   assTaxHis.setResidenceTaxFilliingStatus(associateTaxView.getResidenceTaxFilliingStatus());
			   assTaxHis.setResidenceTaxAllowances(associateTaxView.getResidenceTaxAllowances());
			   assTaxHis.setResidenceTaxAdditionnalWithHolding(associateTaxView.getResidenceTaxAdditionnalWithHolding());
			   
			   assTaxHis.setLocalTax(associateTaxView.getLocalTax());
			   assTaxHis.setLocalTaxState(associateTaxView.getLocalTaxState());
			   
			   assTaxHis.setFederalExempt(associateTaxView.getFederalExempt());
			   assTaxHis.setFutaExempt(associateTaxView.getFutaExempt());
			   assTaxHis.setMediacareExempt(associateTaxView.getMediacareExempt());
			   assTaxHis.setSocialSecurityExempt(associateTaxView.getSocialSecurityExempt());
			   assTaxHis.setSutaExempt(associateTaxView.getSutaExempt());
			   
			   assTaxHis.setSupplementType(associateTaxView.getSupplementType());
			   assTaxHis.setStatus(1);
			   assTaxHis.setAssociateTaxHisUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			   assTaxHis.setCreatedDate(new Date());
			   assTaxHis.setCreatedBy(loginUser.getId());
		   }
		   
		   taxHis.add(assTaxHis);
	} catch (Exception e) {
		e.printStackTrace();
	}
	   
		return taxHis;
	}

private Set<BankDataHistory> getBankDataHistory(AssociateTaxDedContriView associateTaxView, EmployeePayroll employeePayroll, User loginUser) {
	   
	   Set<BankDataHistory> his = new HashSet<>();
	   try {
		   BankDataHistory bankHis = null;
		   if(associateTaxView.getAssociateBankId() != null) {
			   bankHis = new BankDataHistory();
			   bankHis.setAssociateBankId(associateTaxView.getAssociateBankId());
			   bankHis.setPayroll(employeePayroll);
			   bankHis.setAccountNumber(associateTaxView.getAccountNumber());
			   bankHis.setAccountType(associateTaxView.getAccountType());
			   bankHis.setBankName(associateTaxView.getBankName());
			   bankHis.setRoutingNumber(associateTaxView.getRoutingNumber());
			   bankHis.setBankDataHisuuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			   bankHis.setStatus(1);
			   bankHis.setCreatedDate(new Date());
			   bankHis.setCreatedBy(loginUser.getId());
		   }
		   his.add(bankHis);
	} catch (Exception e) {
		e.printStackTrace();
	}
	   
		return his;
	}

/**
    * check same frequency or not 
    * @param existedData
    * @param assoSal
    * @return boolean
    */
	private boolean isPayrollRunForSameFrequency(PaysheetFrequencyDTO existedData, AssociateSalary assoSal) {
		
		boolean isExisted = false;
		if(existedData != null) {
		for (USPayrollDTO uSPayrollDTO : existedData.getPayrollDTO()) {
			if((uSPayrollDTO.getCandidateId().longValue() == assoSal.getCandidate().getId().longValue()) && (existedData.getPaySheetFrequencyId().longValue() == uSPayrollDTO.getPaySheetFrequencyId().longValue())) {
				return true;
			}
		}
		}
		return isExisted;
	}
	
	private AssociateLoanAndAdvancesDTO getAssLoanAndAdvanceDedAmount(OrgLocations orgLocation, PaysheetFrequency paySheetFreq, User loginUser, Candidate candidate, EmployeePayroll employeePayroll)
	{
		AssociateLoanAndAdvancesDTO assLoanAdvDto = new AssociateLoanAndAdvancesDTO();
		try {
			List<AssociateLoanAndAdvances> loanAndAdvList = associateLoanAndAdvancesService.getAssociateLoanAndAdvancesListByCandidateId(candidate.getId(), orgLocation.getId());
			BigDecimal totalLoanAmount = BigDecimal.ZERO, totalAdvanceAmount = BigDecimal.ZERO; 
			Set<AssociateLoanAndAdvanceHistory> his = new HashSet<>();
			for(AssociateLoanAndAdvances assLoanAndAdv : loanAndAdvList) {
				boolean thisPayCheck = false;
				if(assLoanAndAdv.getDeductStartDate() == null) {
					thisPayCheck = true;
				}
				if(paySheetFreq.getFrequencyStartDate().after(assLoanAndAdv.getEffectiveDate()) || paySheetFreq.getFrequencyStartDate().equals(assLoanAndAdv.getEffectiveDate())) {
					thisPayCheck = true;
				}
				/*if(assLoanAndAdv.getDeductStartDate() != null) {
					thisPayCheck = payrollUtil.isDateBetweenEffectiveDate(assLoanAndAdv.getDeductStartDate(), assLoanAndAdv.getDeductEndDate(), paySheetFreq.getFrequencyStartDate(), paySheetFreq.getFrequencyEndDate());
				}*/
				
				AssociateLoanAndAdvances assLoan = assLoanAndAdv;
				int scheduleCount = 0;
				AssociateLoanAndAdvanceHistory assLoanAndAdvHis = associateLoanAndAdvancesHistoryService.getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(assLoan.getId());
				if(assLoanAndAdvHis == null || assLoanAndAdvHis.getSchedule() < assLoan.getNoOfSchedules()) {
					if(thisPayCheck) {
						scheduleCount = 0;
						if(assLoanAndAdvHis != null && assLoanAndAdvHis.getSchedule() != null) {
							scheduleCount = assLoanAndAdvHis.getSchedule() + 1;
						}else {
							scheduleCount += 1;
						}
					}
				}else {
					thisPayCheck = false;
				}
				
				if(thisPayCheck) {
						
						Date[] dedFreqDates = payrollUtil.getLoanAndAdvancesScheduleDates(paySheetFreq.getFrequencyStartDate(), assLoanAndAdv.getScheduler());
						if("Loan".equalsIgnoreCase(assLoanAndAdv.getLoanAdvanceType())){
							totalLoanAmount = totalLoanAmount.add(new BigDecimal(assLoanAndAdv.getDeductAmount()));
						}else if("Advance".equalsIgnoreCase(assLoanAndAdv.getLoanAdvanceType())){
							totalAdvanceAmount = totalAdvanceAmount.add(new BigDecimal(assLoanAndAdv.getDeductAmount()));
						}
						assLoan.setDeductStartDate(dedFreqDates[0]);
						if(dedFreqDates[1] != null)
							assLoan.setDeductEndDate(dedFreqDates[1]);
						else
							assLoan.setDeductEndDate(paySheetFreq.getFrequencyEndDate());
						assLoan.setDeductionStatus(Constants.DEDUCTED);
						assLoan.setUpdatedDate(new Date());
						associateLoanAndAdvancesService.saveAssociateLoanAndAdvances(assLoan);
						
						AssociateLoanAndAdvanceHistory assLoanHistory = new AssociateLoanAndAdvanceHistory();
						assLoanHistory.setAssociateLoanAndAdvances(assLoan);
						assLoanHistory.setDeductAmount(assLoanAndAdv.getDeductAmount());
						assLoanHistory.setSchedule(scheduleCount);
						assLoanHistory.setScheduleStartDate(assLoan.getDeductStartDate());
						assLoanHistory.setScheduleEndDate(assLoan.getDeductEndDate());
						assLoanHistory.setCreatedDate(new Date());
						assLoanHistory.setPayroll(employeePayroll);
						assLoanHistory.setCreatedBy(loginUser.getUserCode());
						assLoanHistory.setUpdatedBy(loginUser.getUserCode());
						assLoanHistory.setUpdatedDate(new Date());
						his.add(assLoanHistory);
//						assLoanHistory = associateLoanAndAdvancesHistoryService.saveAssociateLoanAndAdvancesHistory(assLoanHistory);
						
//					}
 				}
			}
			assLoanAdvDto.setTotalAdvanceAmount(totalAdvanceAmount);
			assLoanAdvDto.setTotalLoanAmount(totalLoanAmount);
			assLoanAdvDto.setLoanHis(his);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assLoanAdvDto;
	}


	/**
	 * Calculating Deductions and Contributions By Schedule
	 * 
	 * @param user
	 * @param orgLocation
	 * @param amount
	 * @param logonUser
	 * @param paySheet
	 * @param employeePayroll
	 * @return DeductionDTO
	 */
	private DeductionDTO getDeductionAmount(Candidate candidate, OrgLocations orgLocation,BigDecimal amount, User logonUser,PaysheetFrequency paySheet, EmployeePayroll employeePayroll)
	{
		BigDecimal deductions[] = new BigDecimal[2];
	    BigDecimal[] contriTaxes = new BigDecimal[2];
		DeductionDTO dedDto = new DeductionDTO();
		Set<DeductionHistory> dedHis = new HashSet<>();
		BigDecimal preTax = BigDecimal.ZERO;
		BigDecimal postTax = BigDecimal.ZERO;
		 BigDecimal preContriTax = BigDecimal.ZERO;
		 BigDecimal postContriTax = BigDecimal.ZERO;
		try {
			ResponseList<DeductionDTO> res = associateDeductionService.findAllDeductionSummaryByCandidateId(candidate.getId(), orgLocation);
			List<AssociateContribution> contributions = associateContributionDao.findAllContributionSummaryByCandidateId(candidate.getId(), orgLocation);
			if (res.getData() != null) {
				List<DeductionDTO> preDedList = res.getData()
						.stream()
						.filter(ded -> "pretax".equalsIgnoreCase(ded.getDedTaxType()))
						.collect(Collectors.toList());
				for (DeductionDTO ded : preDedList) {
					 BigDecimal preDedTax = BigDecimal.ZERO;
					 BigDecimal preDedContriTax = BigDecimal.ZERO;
					AssociateDeduction assoDeduction = associateDeductionService.getAsociateDedByUserIdDeductionId(Long.parseLong(ded.getId()), candidate.getId());
					boolean dedSchedulePay = payrollUtil.findDeductionSchedulePay(assoDeduction, paySheet.getFrequencyStartDate(), paySheet.getFrequencyEndDate());
					if (!dedSchedulePay) {
						if (PayrollConstant.FIXED.equalsIgnoreCase(ded.getMethod())) {
							preTax = preTax.add(ded.getAmount());
							preDedTax = ded.getAmount();
						}
						else {
							BigDecimal perAmount = getPercentAmount(amount, ded.getAmount());
							preTax = preTax.add(perAmount);
							preDedTax = perAmount;
						}
						Date[] deductionDates = null;
						if (StringUtils.isNoneBlank(assoDeduction.getDedStatus()) && !PayrollConstant.EVERY_PAY.equalsIgnoreCase(assoDeduction.getDeductionTypeId().getPayrollScheduled())) {
							deductionDates = payrollUtil.getDeductionDates(assoDeduction.getDedEndDate(), assoDeduction.getDeductionTypeId().getPayrollScheduled());
						}
						else {
							assoDeduction.setDedStatus("DEDUCTED");
							deductionDates = payrollUtil.getDeductionDates(paySheet.getFrequencyStartDate(), assoDeduction.getDeductionTypeId().getPayrollScheduled());
						}
						assoDeduction.setDedStartDate(deductionDates[0]);
						if(deductionDates[1] != null) {
							assoDeduction.setDedEndDate(deductionDates[1]);
						}else {
							assoDeduction.setDedEndDate(paySheet.getFrequencyEndDate());
						}
					
						associateDeductionDao.saveAssociateDeduction(assoDeduction);
						
						AssociateContribution savedAassContribution = null;
						if(contributions != null) {
							AssociateContribution assContribution = contributions
									.stream()
									.filter(con->con.getContributionTypeId().getDeductionTypeId().getId().longValue() == assoDeduction.getDeductionTypeId().getId().longValue())
									.findAny()
									.orElse(null);
							if(assContribution != null) {
								if (PayrollConstant.FIXED.equalsIgnoreCase(assContribution.getContributionMethod())) {
			    					preContriTax = preContriTax.add(assContribution.getContributionAmount());
			    					preDedContriTax = assContribution.getContributionAmount();
								}
								else {
									BigDecimal perAmount = getPercentAmount(amount, assContribution.getContributionAmount());
									preContriTax = preContriTax.add(perAmount);
									preDedContriTax = perAmount;
								}
								assContribution.setContributionStatus("CONTRIBUTED");
								assContribution.setContributionStartDate(deductionDates[0]);
								if(deductionDates[1] != null) {
									assContribution.setContributionEndDate(deductionDates[1]);	
								}else {
									assContribution.setContributionEndDate(paySheet.getFrequencyEndDate());
								}
								
								savedAassContribution =  associateContributionDao.saveAssociateContribution(assContribution);
							}
							
						}
						DeductionHistory dedHistory  = populateDeductionHistory(paySheet,assoDeduction,logonUser,deductionDates,employeePayroll,savedAassContribution,preDedTax,preDedContriTax,BigDecimal.ZERO,BigDecimal.ZERO);
						dedHis.add(dedHistory);
					}
				}
				deductions[0] = preTax;
				contriTaxes[0] = preContriTax;
				List<DeductionDTO> postDedList = res.getData().stream().filter(ded -> "posttax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
				
				for (DeductionDTO postDed : postDedList) {
					BigDecimal postDedTax = BigDecimal.ZERO;
					 BigDecimal postDedContriTax = BigDecimal.ZERO;
					AssociateDeduction assoDeduction = associateDeductionService.getAsociateDedByUserIdDeductionId(Long.parseLong(postDed.getId()), candidate.getId());
					boolean dedSchedulePay = payrollUtil.findDeductionSchedulePay(assoDeduction, paySheet.getFrequencyStartDate(), paySheet.getFrequencyEndDate());
					if (!dedSchedulePay) {
						if (PayrollConstant.FIXED.equalsIgnoreCase(postDed.getMethod())) {
							postTax = postTax.add(postDed.getAmount());
							postDedTax = postDed.getAmount();
						}
						else {
							BigDecimal perAmount = getPercentAmount(amount, postDed.getAmount());
							postTax = postTax.add(perAmount);
							postDedTax = perAmount;
						}
						Date[] deductionDates = null;
						if (StringUtils.isNoneBlank(assoDeduction.getDedStatus()) && !PayrollConstant.EVERY_PAY.equalsIgnoreCase(assoDeduction.getDeductionTypeId().getPayrollScheduled())) {
							deductionDates = payrollUtil.getDeductionDates(assoDeduction.getDedEndDate(), assoDeduction.getDeductionTypeId().getPayrollScheduled());
						}
						else {
							 assoDeduction.setDedStatus(PayrollConstant.DEDUCTED);
							 deductionDates = payrollUtil.getDeductionDates(paySheet.getFrequencyStartDate(), assoDeduction.getDeductionTypeId().getPayrollScheduled());
						}
						assoDeduction.setDedStartDate(deductionDates[0]);
						if(deductionDates[1] != null) {
							assoDeduction.setDedEndDate(deductionDates[1]);
						}else {
							assoDeduction.setDedEndDate(paySheet.getFrequencyEndDate());
						}
						
						associateDeductionDao.saveAssociateDeduction(assoDeduction);
						
						
						AssociateContribution savedAssContribution= null;
						if(contributions != null) {
							AssociateContribution assContribution = contributions
									.stream()
									.filter(con -> con.getContributionTypeId().getDeductionTypeId().getId().longValue() == assoDeduction.getDeductionTypeId().getId().longValue())
									.findAny()
									.orElse(null);
							if(assContribution != null) {
								if (PayrollConstant.FIXED.equalsIgnoreCase(assContribution.getContributionMethod())) {
									postContriTax = postContriTax.add(assContribution.getContributionAmount());
									postDedContriTax = assContribution.getContributionAmount();
								}
								else {
									BigDecimal perAmount = getPercentAmount(amount, assContribution.getContributionAmount());
									postContriTax = postContriTax.add(perAmount);
									postDedContriTax = perAmount;
								}
								assContribution.setContributionStatus(PayrollConstant.CONTRIBUTED);
								assContribution.setContributionStartDate(deductionDates[0]);
								if(deductionDates[1] != null) {
									assContribution.setContributionEndDate(deductionDates[1]);
								}else {
									assContribution.setContributionEndDate(paySheet.getFrequencyEndDate());
								}
								savedAssContribution = associateContributionDao.saveAssociateContribution(assContribution);
							}
							
						}
						
						DeductionHistory dedHistory  = populateDeductionHistory(paySheet,assoDeduction,logonUser,deductionDates,employeePayroll,savedAssContribution,BigDecimal.ZERO,BigDecimal.ZERO,postDedTax,postDedContriTax);
						dedHis.add(dedHistory);
					}
				
				}
				deductions[1] = postTax;
				contriTaxes[1] = postContriTax;
				dedDto.setDedTaxes(deductions);
				dedDto.setDedHis(dedHis);
				dedDto.setContributionTaxes(contriTaxes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dedDto;
	}

	
	/**
	 * Calculation payItemSums
	 * 
	 * @param orgLocation
	 * @param associatePayItem
	 * @param paySheet
	 * @param loginUser
	 * @param freqStartDate
	 * @param freqEndDate
	 * @return BigDecimal
	 */
	private PayItemDTO getPayItemSum(OrgLocations orgLocation, Set<AssociatePayItem> associatePayItem, PaysheetFrequency paySheet, EmployeePayroll payroll, User loginUser)
	{
		PayItemDTO payItemDTO = new PayItemDTO();
		BigDecimal payItemSum = BigDecimal.ZERO;
		Set<PayitemHistory> historySet = new HashSet<>();
		try {
			if (associatePayItem != null && associatePayItem.size() > 0) {
				for (AssociatePayItem payItem : associatePayItem) {
					boolean match = payrollUtil.isDateBetweenEffectiveDate(payItem.getEffectiveDate(), payItem.getEndDate(), paySheet.getFrequencyStartDate(), paySheet.getFrequencyEndDate());
					if (match) {
						boolean schedulePay = payrollUtil.findPayItemSchedulePay(payItem, paySheet.getFrequencyStartDate(), paySheet.getFrequencyEndDate());
						if (!schedulePay) {
							payItemSum = payItemSum.add(payItem.getPayItemAmount());
							Date[] payItemDate = null;
							if (StringUtils.isNotBlank(payItem.getPayitemStatus()) && !PayrollConstant.EVERY_PAY.equalsIgnoreCase(payItem.getPayItemSchedule())) {
								payItemDate = payrollUtil.getDeductionDates(payItem.getPayitemEndDate(), payItem.getPayItemSchedule());
							}
							else {
								payItem.setPayitemStatus(PayrollConstant.ADDED);
								payItemDate = payrollUtil.getDeductionDates(paySheet.getFrequencyStartDate(), payItem.getPayItemSchedule());
							}
							payItem.setPayitemStartDate(payItemDate[0]);
							if (payItemDate[1] != null) {
								payItem.setPayitemEndDate(payItemDate[1]);
							}
							else {
								payItem.setPayitemEndDate(paySheet.getFrequencyEndDate());
							}
							AssociatePayItem assPayItem = associateSalaryDao.saveUpdatedPayItem(payItem);
							if (assPayItem != null) {
								PayitemHistory payHis = populatePayHistory(payroll, payItem, payItemDate, paySheet, loginUser);
								historySet.add(payHis);
							}
						}
					}
				}
			}	
			payItemDTO.setPayItemSum(payItemSum);
			payItemDTO.setPayitemHistory(historySet);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return payItemDTO;
	}
	
	/**
	 * Populating PayItem History
	 * @param payroll
	 * @param payItem
	 * @param payItemDate
	 * @param paySheet 
	 * @param loginUser 
	 * @return PayitemHistory
	 */
	private PayitemHistory populatePayHistory(EmployeePayroll payroll, AssociatePayItem payItem, Date[] payItemDate, PaysheetFrequency paySheet, User loginUser)
	{
		PayitemHistory payItemHistory = new PayitemHistory();
		payItemHistory.setPayrollId(payroll);
		payItemHistory.setFrequencyStartDate(payItemDate[0]);
		if(payItemDate[1] != null) {
		payItemHistory.setFrequencyEndDate(payItemDate[0]);
		}else {
			payItemHistory.setFrequencyEndDate(paySheet.getFrequencyEndDate());
		}
		payItemHistory.setCreatedDate(new Date());
		payItemHistory.setUpdatedDate(new Date());
		payItemHistory.setPayItemDate(new Date());
		payItemHistory.setCreatedBy(loginUser.getId());
		payItemHistory.setUpdatedBy(loginUser.getId());
		payItemHistory.setPayCheckDate(paySheet.getPayCheckDate());
		payItemHistory.setAssociatePayItem(payItem);
		payItemHistory.setAmount(payItem.getPayItemAmount());
		payItemHistory.setPayItemHisUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		return payItemHistory;
	}

	/**
	 * populating deduction histroy
	 * 
	 * @param paySheet
	 * @param assoDeduction
	 * @param logonUser
	 * @param deductionDates
	 * @param employeePayroll
	 * @param savedAassContribution 
	 * @param preContriTax 
	 * @param preTax 
	 * @param contriTaxes 
	 * @param deductions 
	 * @return DeductionHistory
	 */
	private DeductionHistory populateDeductionHistory(PaysheetFrequency paySheet, AssociateDeduction assoDeduction, User logonUser, Date[] deductionDates, EmployeePayroll employeePayroll, AssociateContribution savedAassContribution, BigDecimal preDedTax, BigDecimal preContriTax,BigDecimal postDedTax, BigDecimal postContriTax)
	{
		DeductionHistory dedHistory = new DeductionHistory();
		dedHistory.setDeductionId(assoDeduction);
		dedHistory.setDeductionDate(new Date());
		dedHistory.setFrequencyStartDate(deductionDates[0]);
		if (deductionDates[1] != null) {
			dedHistory.setFrequencyEndDate(deductionDates[1]);
		}
		else {
			dedHistory.setFrequencyEndDate(paySheet.getFrequencyEndDate());
		}
		dedHistory.setCandidateId(employeePayroll.getCandidate().getId());
		dedHistory.setCreatedBy(logonUser.getId());
		dedHistory.setPayCheckDate(paySheet.getPayCheckDate());
		dedHistory.setUpdatedBy(logonUser.getId());
		dedHistory.setCreatedDate(new Date());
		dedHistory.setUpdatedDate(new Date());
		dedHistory.setPayroll(employeePayroll);
		if(savedAassContribution != null) {
			dedHistory.setContributionId(savedAassContribution);
		}
		dedHistory.setDeductionHisUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		dedHistory.setPreDeductionAmount(preDedTax);
		dedHistory.setPreContributionAmount(preContriTax);
		dedHistory.setPostDeductionAmount(postDedTax);
		dedHistory.setPostContributionAmount(postContriTax);
		return dedHistory;
	}

	/**
	 * Calcualting deduction % amount
	 * @param valueOf
	 * @param amount
	 * @return BigDecimal
	 */
	private BigDecimal getPercentAmount(BigDecimal gross, BigDecimal pct)
	{
		 return gross.multiply(pct).divide(PayrollConstant.ONE_HUNDRED);
	}

	/**'
	 * populating employer taxes
	 * @param employeePayroll
	 * @param orgLocation
	 * @param payrollCalculator
	 * @param loginUser
	 * @param string 
	 * @return Set<EmployerPayrollTax>
	 */
	private Set<EmployerPayrollTax> getEmployerTaxes(EmployeePayroll employeePayroll, OrgLocations orgLocation, PayrollCalculatorDTO payrollCalculator, User loginUser, String empState)
	{
				Set<EmployerPayrollTax> set = new HashSet<>(); 
				try {
					EmployerPayrollTax employerTaxes = null;
					
					if(employeePayroll.getEmployerpayrolltax() != null) {
						 employerTaxes = employeePayroll.getEmployerpayrolltax().iterator().next();
					}else {
						 employerTaxes = new EmployerPayrollTax();
					}
					STEPayrollCalculator STE = payrollCalculator.getsTEPayrollCalculator();
					String locationCode = payrollCalculator.getWebServiceLocationCodeV2().getLocationCode();
					employerTaxes.setEmployeePayroll(employeePayroll);
		    		employerTaxes.setEmployerFICA(STE.getEmployerFICACalc().asBigDecimal());//Employer FICA calc	
		            employerTaxes.setEmployerMedicare(STE.getEmployerMedicareCalc().asBigDecimal()); // Employer Medicare
					employerTaxes.setEmployerFUTA(payrollCalculator.getEmployerFUTA().asBigDecimal()); // Employer FUTA
					employerTaxes.setEmployerSUTA(payrollCalculator.getEmployerSUTA().asBigDecimal()); // Employer SUTA
					employerTaxes.setEmployerRUIA(payrollCalculator.getEmployerRUIACalculation().getCalculatedUITax().asBigDecimal()); // Employer RUIA Withholding
					employerTaxes.setEmployerTransitTaxes(payrollCalculator.getEmployerOregonTransitTAX().asBigDecimal()); // Employer Oregon Transit Tax
					employerTaxes.setEmployerUHI(payrollCalculator.getEmployerMassachusettsUHI().asBigDecimal()); // UHI
					employerTaxes.setEmployerEMAC(payrollCalculator.getEmployerMassachusettsEMAC().asBigDecimal()); // EMAC
					employerTaxes.setEmployerEmacSupplemental(payrollCalculator.getEmployerMassachusettsEMACSupplemental().asBigDecimal()); // EMAC Supplemental
					if(payrollCalculator.getEmployerMetropolitanMCTMT() != null) {
						employerTaxes.setEmployerMCTMT(payrollCalculator.getEmployerMetropolitanMCTMT().asBigDecimal()); // New York Metropolitan Commuter Transportation Mobility Tax (MCTMT)	
					}
					employerTaxes.setEmployerWyomingWcTax(payrollCalculator.getEmployerWyomingWC().asBigDecimal()); // Employer Wyoming Workers Compensation Tax (WC)
					employerTaxes.setEmployerOregonWcTax(payrollCalculator.getEmployerOregonWC().asBigDecimal()); // Employer Oregon Workers Compensation Tax
					employerTaxes.setEmployerSutaSurchargeTax(payrollCalculator.getEmployerSUTASurcharge().asBigDecimal()); // Employer SUTA Surcharge Taxes-
					if(payrollCalculator.getEmployerSDI() != null) {
						employerTaxes.setEmployerSDI(payrollCalculator.getEmployerSDI().asBigDecimal());	
					}
					employerTaxes.setEmployerHeadTax(STE.getHeadTax(locationCode).asBigDecimal());
					employerTaxes.setUsEmployerPayrollTaxUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					employerTaxes.setStatus(1);
					employerTaxes.setCreatedBy(loginUser.getId());
					employerTaxes.setUpdatedBy(loginUser.getId());
					employerTaxes.setCreatedDate(new Date());
					employerTaxes.setUpdatedDate(new Date());
					set.add(employerTaxes);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
		return set;
	}

	/**
	 * Get Federal,FICA,Medicare,County,State withholding taxes etc.
	 * 
	 * @param employeePayroll
	 * @param orgLocation
	 * @param payrollCalculator
	 * @param loginUser 
	 * @return Set<EmployeePayrollTax>
	 */
	private Set<EmployeePayrollTax> getPayrollTaxes(EmployeePayroll employeePayroll, OrgLocations orgLocation, PayrollCalculatorDTO payrollCalculator, User loginUser)
	{
		Set<EmployeePayrollTax> set = new HashSet<>();
		EmployeePayrollTax taxes = null;
		if(employeePayroll.getUsPayrollTax()!= null) {
			taxes = employeePayroll.getUsPayrollTax().iterator().next();
		}else {
			taxes = new EmployeePayrollTax();
		}
		
		STEPayrollCalculator STE = payrollCalculator.getsTEPayrollCalculator();
		String locationCode = payrollCalculator.getWebServiceLocationCodeV2().getLocationCode();
		taxes.setEmployeePayroll(employeePayroll);
		taxes.setSteVersion(STE.getSTEVersion()); // STEVersion
		taxes.setFicaWithholding(STE.getFICACalc().asBigDecimal()); // FICA Withholding
		taxes.setMedicareWithholding(STE.getMedicareCalc().asBigDecimal()); // Medicare Withholding
		taxes.setFederalWithholding(STE.getFederalCalc().asBigDecimal()); // Federal Withholding
		taxes.setEarnedIncCredit(STE.getEICCalc().asBigDecimal()); // Earned Inc. Credit
		taxes.setStateWithholding(STE.getStateCalc(locationCode).asBigDecimal()); // State Withholding
		taxes.setCountyWithholding(STE.getCountyCalc(locationCode).asBigDecimal()); // County Withholding
		taxes.setLocationCode(locationCode +","+employeePayroll.getCandidate().getResidenceState()); // locationCode
		taxes.setUsEmpPayrollTaxUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		taxes.setEarnedIncCredit(STE.getEICCalc().asBigDecimal());
		taxes.setLocalTax(STE.getCityCalc(locationCode).asBigDecimal());
		taxes.setSDI(payrollCalculator.getSDI().asBigDecimal());
		taxes.setSUI(payrollCalculator.getSUI().asBigDecimal());
		taxes.setFLI(payrollCalculator.getFLI().asBigDecimal());
		taxes.setHCSF(payrollCalculator.getHCSF().asBigDecimal());
		taxes.setWC(payrollCalculator.getWC().asBigDecimal());
		taxes.setStatus(1);
		taxes.setCreatedBy(loginUser.getId());
		taxes.setUpdatedBy(loginUser.getId());
		taxes.setCreatedDate(new Date());
		taxes.setUpdatedDate(new Date());
		set.add(taxes);
		return set;
	}
	



	

	private int getProjectInvoiceFrequency(String invoiceFrequency)
	{
		// Weekly Bi-Weekly Monthly Annually
		int payPeriod = 0;
		if ("Weekly".equalsIgnoreCase(invoiceFrequency)) {
			payPeriod = 52;
		}
		if ("Bi-Weekly".equalsIgnoreCase(invoiceFrequency)) {
			payPeriod = 26;
		}
		if ("Monthly".equalsIgnoreCase(invoiceFrequency)) {
			payPeriod = 12;
		}
		return payPeriod;
	}
	
	

	@Override
	public PaysheetFrequencyDTO getPayrollData(GenerateUSPayrollDTO generateUSPayrollDTO, OrgLocations orgLocation, User loginUser)
	{
		PaysheetFrequency paysheetFrequency = generateUSPayrollDao.getPayrollData(generateUSPayrollDTO, orgLocation, loginUser);
		PaysheetFrequencyDTO paysheetFrequencyDTO = null;
		List<USPayrollDTO> list = null;
		try {
			if (paysheetFrequency != null && paysheetFrequency.getEmployeepayroll() != null) {
				paysheetFrequencyDTO = new PaysheetFrequencyDTO();
				paysheetFrequencyDTO.setPayrollFrequencyUUID(paysheetFrequency.getPaySheetFrequencyUUID());
				paysheetFrequencyDTO.setPaySheetFrequencyId(paysheetFrequency.getId());
				list = new ArrayList<>();
				for(EmployeePayroll pay : paysheetFrequency.getEmployeepayroll()) {
					USPayrollDTO payrollDTO = new USPayrollDTO();
					payrollDTO.setEmpId(pay.getEmployee().getEmployeeId());
					StringBuilder sb = new StringBuilder();
					if (StringUtils.isNoneBlank(pay.getCandidate().getLastName())) {
						sb.append(pay.getCandidate().getFirstName()).append(" ").append(pay.getCandidate().getLastName());
					}
					else {
						sb.append(pay.getCandidate().getFirstName());
					}
					if(pay.getCandidate().getDesignationType() != null)
					payrollDTO.setEmployeeDesignationType(pay.getCandidate().getDesignationType());
					payrollDTO.setEmpName(sb.toString());
					payrollDTO.setEmpPayrollId(pay.getId());
					payrollDTO.setEmpPayrollUUID(pay.getUsEmpPayrollUUID());
					payrollDTO.setGrossPay(pay.getGrossPay());
					payrollDTO.setNetPay(pay.getNetPay());
					payrollDTO.setRegularHrsTotalAmt(pay.getRegularHoursAmount());
//					payrollDTO.setPayRate(pay.getEmpPayRate());
					payrollDTO.setPayPerSal(pay.getPayPerSalary());
					payrollDTO.setPostTaxDeduction(pay.getPostTaxDeduction());
					payrollDTO.setPreTaxDeduction(pay.getPreTaxDeduction());
					String regHrs = "0.00";
					if(StringUtils.isNotBlank(pay.getRegularHours())) {
						regHrs = pay.getRegularHours();
					}
					payrollDTO.setRegularHours(regHrs);
					payrollDTO.setTaxes(pay.getTaxes());
					payrollDTO.setOtHoursTotalAmt(pay.getOverTimeAmount());
					payrollDTO.setCandidateId(pay.getCandidate().getId());
					payrollDTO.setPaySheetFrequencyId(pay.getPaysheetFrequency().getId());
					double overTimeHours = 0;
					if(StringUtils.isNoneBlank(pay.getOverTimeHours())) {
						overTimeHours = Double.parseDouble(pay.getOverTimeHours());
					}
					payrollDTO.setOvertimeHours(overTimeHours);
					if(pay.getPayType() != null) {
						payrollDTO.setPayType(pay.getPayType());
					}
					payrollDTO.setApproverStatus(pay.getSubmitForAproverStatus());
//					if(PayrollConstant.DRAFT.equalsIgnoreCase(pay.getSubmitForAproverStatus())) {
						list.add(payrollDTO);	
//					}
					
				}
				paysheetFrequencyDTO.setPayrollDTO(list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paysheetFrequencyDTO;
	}

	@Override
	public Response<USPayrollDTO> getEmployeePayrollByUUID(String payrollUUID,OrgLocations orgLocation,String approverStatus)
	{
		EmployeePayroll payroll = generateUSPayrollDao.getEmployeePayrollByUUID(payrollUUID);
		double totalDeduction = 0;
		Response<USPayrollDTO> res = new Response<>();
		if(payroll != null ) {
			USPayrollDTO payrollDTO = new USPayrollDTO();
			payrollDTO.setPayrollId(String.valueOf(payroll.getId()));
			payrollDTO.setPayrollUUID(payroll.getUsEmpPayrollUUID());
			String totalHours = "0";
			String regHrs = "0";
			double otHrs = 0;
			if(StringUtils.isNoneBlank(payroll.getRegularHours())) {
				regHrs = payroll.getRegularHours();
			}
			if(StringUtils.isNoneBlank(payroll.getOverTimeHours())) {
				otHrs = Double.parseDouble(payroll.getOverTimeHours());
			}
			payrollDTO.setEmpId(payroll.getEmployee().getEmployeeId());
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNoneBlank(payroll.getCandidate().getLastName())) {
				sb.append(payroll.getCandidate().getFirstName()).append(" ").append(payroll.getCandidate().getLastName());
			}
			else {
				sb.append(payroll.getCandidate().getFirstName());
			}
			payrollDTO.setEmpName(sb.toString());
			if(StringUtils.isNotBlank(payroll.getDepositType())) {
				payrollDTO.setDepositType(payroll.getDepositType());
			}else {
				payrollDTO.setDepositType("");
			}
			payrollDTO.setPayType(payroll.getPayType());
			payrollDTO.setEmpPayrollId(payroll.getId());
			payrollDTO.setEmpPayrollUUID(payroll.getUsEmpPayrollUUID());
			payrollDTO.setGrossPay(payroll.getGrossPay());
			payrollDTO.setNetPay(payroll.getNetPay());
			if(payroll.getRegularHoursAmount() != null) {
				payrollDTO.setRegularHrsTotalAmt(payroll.getRegularHoursAmount());	
			}else {
				payrollDTO.setRegularHrsTotalAmt(BigDecimal.ZERO);
			}
			
			payrollDTO.setPayRate(payroll.getEmpPayRate());
			payrollDTO.setPostTaxDeduction(payroll.getPostTaxDeduction());
			payrollDTO.setPreTaxDeduction(payroll.getPreTaxDeduction());
			payrollDTO.setRegularHours(regHrs);
			payrollDTO.setTaxes(payroll.getTaxes());
			payrollDTO.setPayPerSal(payroll.getPayPerSalary());
			payrollDTO.setOvertimeHours(otHrs);
			totalHours = payrollUtil.getTotalRegularOverTimeHours(Double.parseDouble(regHrs), otHrs);
			payrollDTO.setTotalHours(Double.parseDouble(totalHours));
			BigDecimal overTimeAmount = BigDecimal.ZERO;
			if(payroll.getOverTimeAmount() != null) {
				payrollDTO.setOtHoursTotalAmt(payroll.getOverTimeAmount());
				overTimeAmount = payroll.getOverTimeAmount();
			}else {
				payrollDTO.setOtHoursTotalAmt(overTimeAmount);
			}
			BigDecimal totalEarnings = payroll.getRegularHoursAmount().add(overTimeAmount);
			payrollDTO.setTotalEarnings(totalEarnings);
			payrollDTO.setEmployeeAddress(payroll.getCandidate().getResidenceAddress());
			
			AssociateSalary assSal = associateSalaryService.getAssociateSalaryByCandidateIdAndOrgLocationId(payroll.getCandidate().getId(), orgLocation.getId());
			if(assSal != null) {
				payrollDTO.setAssSalary(assSal.getGrossSalary());
				payrollDTO.setAssSalType(assSal.getPayType());
			}
			Map<String, BigDecimal> map = new HashMap<>();
			for(EmployeePayrollTax tax : payroll.getUsPayrollTax()) {
				map.put("Federal Withholding", tax.getFederalWithholding());
				map.put("SOC SEC",tax.getFicaWithholding());
				map.put("Medicare",tax.getMedicareWithholding());
				map.put("Advanced Earned Income Credit",tax.getEarnedIncCredit());
				map.put("State Withholding",tax.getStateWithholding());
				map.put("LocalTax",tax.getLocalTax());
				map.put("SDI",tax.getSDI());
				map.put("SUI", tax.getSUI());
				map.put("FLI", tax.getFLI());
				map.put("HCSF", tax.getHCSF());
				map.put("WC", tax.getWC());
				payrollDTO.setFederal(tax.getFederalWithholding());
				payrollDTO.setFica(tax.getFicaWithholding());
				payrollDTO.setAeic(tax.getEarnedIncCredit());
				payrollDTO.setMedicare(tax.getMedicareWithholding());
				payrollDTO.setStateWithHolding(tax.getStateWithholding());
				payrollDTO.setLocalTax(tax.getLocalTax());
			}
//			for (Map.Entry<String, BigDecimal> entry : map.entrySet())
//			{
//			    logger.info(entry.getKey() + "/" + entry.getValue());
//			}
			payrollDTO.setTax(map);
			payrollDTO.setTaxes(payroll.getTaxes());
			
			// Deductions 
			List<DeductionDTO> dlist = payrollUtil.getDeductionData(payroll);
			List<DeductionDTO> preDedList = dlist.stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
		    List<DeductionDTO> postDedList = dlist.stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
		    double  preDedTotal = preDedList.stream().mapToDouble(ded->ded.getDedAmount()).sum();
			double postDedTotal = postDedList.stream().mapToDouble(ded->ded.getDedAmount()).sum();		    
			payrollDTO.setPreDedTotal(BigDecimal.valueOf(preDedTotal));
			payrollDTO.setPostDedTotal(BigDecimal.valueOf(postDedTotal));
		    totalDeduction = dlist.stream().mapToDouble(ded->ded.getDedAmount()).sum();
		    payrollDTO.setPostDed(postDedList);
			payrollDTO.setPreDed(preDedList);
			payrollDTO.setTotalDeduction(new BigDecimal(totalDeduction));
			
			// Contributions
			List<ContributionDTO> clist = payrollUtil.getContributionData(payroll);
			
			double contributionTotal =0;
			double payItemTotal = 0;
			double loanAmountTotal = 0;
			if(clist != null && clist.size() >0) {
				payrollDTO.setContribution(clist);
				contributionTotal = clist.stream().mapToDouble(con->con.getContributionAmt()).sum();
			}
			payrollDTO.setContributionTotal(BigDecimal.valueOf(contributionTotal));
			// pay Items
			BigDecimal piTotal = BigDecimal.ZERO;
			List<AssociatePayItemDTO> payItem = payrollUtil.getPayItemData(payroll);
			if(payItem != null && payItem.size() >0) {
				payrollDTO.setPayItem(payItem);
				payItemTotal = payItem.stream().mapToDouble(pitem->pitem.getPayItemAmt()).sum();
			}
			piTotal = BigDecimal.valueOf(payItemTotal);
			payrollDTO.setPayItemTotal(piTotal);
			
			//Loan and advances
			BigDecimal lATotal = BigDecimal.ZERO;
			List<AssociateLoanAndAdvancesDTO> loanAdvance = payrollUtil.getLoanAndAdvancesData(payroll);
			if(loanAdvance != null && loanAdvance.size() >0) {
				payrollDTO.setLoanAdvance(loanAdvance);
				loanAmountTotal = loanAdvance.stream().mapToDouble(lAmt->Double.valueOf(lAmt.getLoanAmount())).sum();
			}
			lATotal = BigDecimal.valueOf(loanAmountTotal);
			payrollDTO.setLoanAmountTotal(lATotal);
			
			
			
			// Calculating YTD
			
			//passing frequency year to calculate ytd
			Date date = payroll.getPaysheetFrequency().getFrequencyStartDate();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			
			
			YTDPayrollDTO ytdDTO = payrollUtil.getEmployeeYTDPayroll(payroll.getCandidate(),orgLocation,approverStatus,year);
			payrollDTO.setYtdDTO(ytdDTO);
			
			List<PayrollLopHisDTO> lopHisList = new ArrayList<>();
			Set<PayrollLopHis> lopHis = payroll.getPayrollLopHis();
//			List<PayrollLopHis> lopHisLis1t = payrollLopHisService.getPayrollLoveHisByPayrollId(payroll.getId());
//			System.out.println(lopHisLis1t);
			BigDecimal totalLOP = BigDecimal.ZERO;
			if(lopHis != null && lopHis.size() > 0) {
				for(PayrollLopHis payrollLopHis : payroll.getPayrollLopHis()) {
					PayrollLopHisDTO payrollLopHisDTO = new PayrollLopHisDTO();
					payrollLopHisDTO.setLeaveTypeName(payrollLopHis.getLeaveTypeName());
					payrollLopHisDTO.setId(payrollLopHis.getId().toString());
					payrollLopHisDTO.setLopLeaves(payrollLopHis.getLopLeaves());
					payrollLopHisDTO.setLopAmount(payrollLopHis.getLopAmount());
					lopHisList.add(payrollLopHisDTO);
				}
				totalLOP = payroll.getLopAmount();
			}
			payrollDTO.setLopAmount(totalLOP);
			payrollDTO.setLopHis(lopHisList);
			res.setObject(payrollDTO);
		}
		return res;
	}

	
	@Override
	public Response<PayAdjustmentDTO> updateEmployeePayroll(PayAdjustmentDTO payAdjustmentDTO, OrgLocations orgLocation, User loginUser)
	{
		Response<PayAdjustmentDTO> res = new Response<>();
		try {
			String payRollRegularHoursAmt = null;
			BigDecimal regularHoursAmtBigDecimal = null;
			PayAdjustmentHistory payAdjHistory = new PayAdjustmentHistory();
			double regHrs = 0;
			double otHrs = 0;
			double regHrsAmt = 0;
			double otHrsAmt = 0;
			double totalHours = 0;
			BigDecimal amount = BigDecimal.ZERO;
			if(!PayrollConstant.SALARY.equalsIgnoreCase(payAdjustmentDTO.getAssSalType()) && !PayrollConstant.LCA_WAGE.equalsIgnoreCase(payAdjustmentDTO.getAssSalType())) {
				if(StringUtils.isNotBlank(payAdjustmentDTO.getRegularHours())) {
					regHrs = Double.parseDouble(payAdjustmentDTO.getRegularHours());
				}
				if(StringUtils.isNotBlank(payAdjustmentDTO.getOverTimeHours())) {
					otHrs = Double.parseDouble(payAdjustmentDTO.getOverTimeHours());
				}
				
				if(StringUtils.isNotBlank(payAdjustmentDTO.getRegularHoursAmt())) {
					regHrsAmt = Double.parseDouble(payAdjustmentDTO.getRegularHoursAmt());
				}
				
				if(StringUtils.isNotBlank(payAdjustmentDTO.getOverTimeHoursAmt())) {
					otHrsAmt = Double.parseDouble(payAdjustmentDTO.getOverTimeHoursAmt());
				}
				 totalHours = CommonUtil.getTotalRegularOverTimeHours(regHrs, otHrs);
				double totAmount =  regHrsAmt+ otHrsAmt;
				amount = new BigDecimal(totAmount);
			}else {
				amount = payAdjustmentDTO.getAssSalaryAmount();
			}
			double preTaxDed = 0;
			double postTaxDed = 0;
			List<DeductionDTO> preDedList = null;
			List<DeductionDTO> postDedList = null;
			List<ContributionDTO> preContribution = null;
			List<ContributionDTO> postContribution = null;
			if(payAdjustmentDTO.getPreDeduction() != null && payAdjustmentDTO.getPreDeduction().size() >0) {
				preDedList = payAdjustmentDTO.getPreDeduction();
				preTaxDed = payAdjustmentDTO.getPreDeduction().stream().filter(pre->pre.getAmount() != null).mapToDouble(pre->pre.getAmount().doubleValue()).sum();
			}
			if(payAdjustmentDTO.getPostDeduction() != null && payAdjustmentDTO.getPostDeduction().size() >0) {
				postDedList =  payAdjustmentDTO.getPostDeduction();
				postTaxDed = payAdjustmentDTO.getPostDeduction().stream().filter(pre->pre.getAmount() != null).mapToDouble(pre->pre.getAmount().doubleValue()).sum();	
			}
			payAdjHistory.setRegularHours(String.valueOf(regHrs));
			payAdjHistory.setOvertimeHours(String.valueOf(otHrs));
			payAdjHistory.setRegularHoursAmt(new BigDecimal(regHrsAmt));
			payAdjHistory.setOvertimeHoursAmt(new BigDecimal(otHrsAmt));
			payAdjHistory.setPrePayType(payAdjustmentDTO.getAssSalType());
			payAdjHistory.setPrePayPerSalary(payAdjustmentDTO.getPreAssSalaryAmount());
			payAdjHistory.setPayType(payAdjustmentDTO.getAssSalType());
			payAdjHistory.setPayPerSalary(amount);
			double preTaxContriSum = 0;
			double postTaxContriSum = 0;
			if(payAdjustmentDTO.getContribution() != null && payAdjustmentDTO.getContribution().size()>0) {
				preContribution = payAdjustmentDTO.getContribution().stream().filter(con->"pretax".equalsIgnoreCase(con.getTaxType())).collect(Collectors.toList());
				postContribution = payAdjustmentDTO.getContribution().stream().filter(con->"posttax".equalsIgnoreCase(con.getTaxType())).collect(Collectors.toList());;
				preTaxContriSum = payAdjustmentDTO.getContribution().stream().filter(con-> con.getContributionAmount() != null && "pretax".equalsIgnoreCase(con.getTaxType())).mapToDouble(con->con.getContributionAmount().doubleValue()).sum();
				postTaxContriSum= payAdjustmentDTO.getContribution().stream().filter(con-> con.getContributionAmount() != null &&"posttax".equalsIgnoreCase(con.getTaxType())).mapToDouble(con->con.getContributionAmount().doubleValue()).sum();
			}
			
			
			BigDecimal grossAmount = (amount.subtract(BigDecimal.valueOf(preTaxDed))).add(BigDecimal.valueOf(preTaxContriSum));
			EmployeePayroll payroll = payrollAdjustmentService.getEmployeePayrollByUUID(payAdjustmentDTO.getPayrollUUID());
			payroll.setGrossPay(amount);
			payroll.setPreTaxContribution(BigDecimal.valueOf(preTaxContriSum));
			payroll.setPostTaxContribution(BigDecimal.valueOf(postTaxContriSum));
			if(!PayrollConstant.SALARY.equalsIgnoreCase(payAdjustmentDTO.getAssSalType()) && !PayrollConstant.LCA_WAGE.equalsIgnoreCase(payAdjustmentDTO.getAssSalType())) {
				payroll.setRegularHours(payAdjustmentDTO.getRegularHours());
				
				// June-27-2019 -> NumberFormatException occurs while editing hourly draft payroll for 
				// regularHoursAmt when it is given blank.
				// payroll.setRegularHoursAmount(new BigDecimal(payAdjustmentDTO.getRegularHoursAmt()));
				// Added ternary operator to check for blank value for payAdjustmentDTO.getRegularHoursAmt().
				payRollRegularHoursAmt = payAdjustmentDTO.getRegularHoursAmt();
				regularHoursAmtBigDecimal =
				new BigDecimal(payRollRegularHoursAmt = StringUtils.isBlank(payRollRegularHoursAmt) ? new BigDecimal("0.00", MathContext.DECIMAL64).toString() : payRollRegularHoursAmt);
				payroll.setRegularHoursAmount(regularHoursAmtBigDecimal);
				//.
				
				payroll.setOverTimeHours(payAdjustmentDTO.getOverTimeHours());
				payroll.setOverTimeAmount(new BigDecimal(payAdjustmentDTO.getOverTimeHoursAmt()));
			}else {
				payroll.setPayPerSalary(payAdjustmentDTO.getAssSalary());
			}
			payroll.setPayType(payAdjustmentDTO.getAssSalType());
			payroll.setPostTaxDeduction(BigDecimal.valueOf(postTaxDed));
			payroll.setPreTaxDeduction(BigDecimal.valueOf(preTaxDed));
			
			AssociateTaxDedContriView associateTaxDTO = associateTaxService.findUserTaxDeatails(payroll.getCandidate().getId(), orgLocation);
			
			//passing frequency year to calculate ytd
			Date date = payroll.getPaysheetFrequency().getFrequencyStartDate();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			
			YTDPayrollDTO ytdTaxes= payrollUtil.getEmployeeYTDPayroll(payroll.getCandidate(),orgLocation,"APPROVED",year);
			PayrollCalculatorDTO payrollCalculator = symmetryPayrollCalculator.runUSPayroll(payroll.getCandidate(), totalHours, grossAmount, payroll.getFrequency(), associateTaxDTO,payroll.getPayCheckDate(),ytdTaxes);
			STEPayrollCalculator STE = payrollCalculator.getsTEPayrollCalculator();
			String  locationCode = payrollCalculator.getWebServiceLocationCodeV2().getLocationCode();
			BigDecimal taxes = STE.getFICACalc().add(STE.getMedicareCalc()).add(STE.getFederalCalc()).add(STE.getEICCalc()).asBigDecimal().add(STE.getStateCalc(locationCode).asBigDecimal()).add(STE.getCountyCalc(locationCode).asBigDecimal()).add(STE.getCityCalc(locationCode).asBigDecimal());
			payroll.setTaxes(taxes);
			BigDecimal totalPay = grossAmount.subtract(taxes);
			BigDecimal totPay = totalPay.subtract(BigDecimal.valueOf(postTaxDed)).add(BigDecimal.valueOf(postTaxContriSum));
			double payItemSum = 0;
			// PayItems 
			if(payAdjustmentDTO.getPayItem() != null && payAdjustmentDTO.getPayItem().size()>0) {
		       payItemSum = payAdjustmentDTO.getPayItem().stream().filter(payItem->payItem.getPayItemCheck()== null && payItem.getAmount() != null).mapToDouble(p->p.getAmount().doubleValue()).sum();
			   Set<PayitemHistory> payItemHis = getUpdatedPayItemData(payroll,payAdjustmentDTO.getPayItem());
			   payroll.setPayitemHistory(payItemHis);
			}
			
			
			// Loan And Advances
		/*	double loanAmountSum = 0;
			if(payAdjustmentDTO.getLoanAdvance() != null) {
				loanAmountSum = payAdjustmentDTO.getLoanAdvance().stream().mapToDouble(p->Double.parseDouble(p.getLoanAmount())).sum();
			}*/
			
			BigDecimal netPay = (totPay.add(new BigDecimal(payItemSum))).subtract(payroll.getAdvanceAmount().add(payroll.getLoanAmount()));
			BigDecimal totalLopAmount = BigDecimal.ZERO;
			if(payAdjustmentDTO.getLopHis() != null && payAdjustmentDTO.getLopHis().size() > 0) {
				BigDecimal lopAmount = BigDecimal.ZERO;
				for(PayrollLopHisDTO lopHis : payAdjustmentDTO.getLopHis()) {
					if(StringUtils.isNotBlank(lopHis.getLopExempt())) {
						lopAmount = lopAmount.add(lopHis.getLopAmount());
					}
				}
				totalLopAmount = payAdjustmentDTO.getTotLopAmount().subtract(lopAmount);
				Set<PayrollLopHis> lopList = getUpdatedPayrollLopHistroyData(payAdjustmentDTO.getLopHis(),payroll);
				payroll.setPayrollLopHis(lopList);
				payAdjHistory.setPreLopAmount(payAdjustmentDTO.getTotLopAmount());
				payAdjHistory.setLopAmount(totalLopAmount);
				payroll.setLopAmount(totalLopAmount);
			}
			payroll.setNetPay(netPay.subtract(totalLopAmount));
			Set<EmployeePayrollTax> payrollTaxSet = getPayrollTaxes(payroll,orgLocation,payrollCalculator,loginUser);
			payroll.setUsPayrollTax(payrollTaxSet);
			Set<EmployerPayrollTax> employerTaxes = getEmployerTaxes(payroll,orgLocation,payrollCalculator,loginUser,payroll.getCandidate().getResidenceState());
			payroll.setEmployerpayrolltax(employerTaxes);
			payroll.setUpdatedBy(loginUser.getId());
			payroll.setUpdatedDate(new Date());
			Set<DeductionHistory> updatedDedHis = new HashSet<>();
			if(preDedList != null) {
				Set<DeductionHistory>  updatedPreDed = getUpdatedDeductionHistroyData(preDedList,payroll,PayrollConstant.PRE_TAX);
				updatedDedHis.addAll(updatedPreDed);
			}
			if(postDedList != null) {
				Set<DeductionHistory>  updatedPostDed = getUpdatedDeductionHistroyData(postDedList,payroll,PayrollConstant.POST_TAX);
				updatedDedHis.addAll(updatedPostDed);
			}
			if(updatedDedHis.size() >0) {
				payroll.setDeductionHistory(updatedDedHis);	
				if(preContribution != null && preContribution.size() >0) {
					updatedDedHis = getUpdatedContribution(updatedDedHis,preContribution,PayrollConstant.PRE_TAX);
				}
				if(postContribution != null && postContribution.size() >0) {
					updatedDedHis = getUpdatedContribution(updatedDedHis,postContribution,PayrollConstant.POST_TAX);
				}
			}
			
			EmployeePayroll updatedPayroll = payrollAdjustmentService.updateEmployeePayroll(payroll);
			if (updatedPayroll != null) {
				updatePayAdjustmentHistory(payAdjHistory,updatedPayroll,payAdjustmentDTO,loginUser);
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
				res.setObject(payAdjustmentDTO);
			}
			
		}catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return res;
	}

	/**
	 * Get Updated PayItem History Data
	 * @param payroll
	 * @param list
	 * @return Set<PayAdjustmentDedHistory>
	 */
	private Set<PayitemHistory> getUpdatedPayItemData(EmployeePayroll payroll, List<AssociatePayItemDTO> payItemDto)
	{
		Set<PayitemHistory> his  = new HashSet<>();
		Set<PayitemHistory> payItemHis = payroll.getPayitemHistory();
		for(AssociatePayItemDTO assPayItemDTO : payItemDto) {
			Long dtoPayItemId = assPayItemDTO.getPayItemId().longValue();
			for(PayitemHistory paiHis : payItemHis) {
				if(dtoPayItemId.longValue() == paiHis.getId().longValue()) {
					paiHis.setAmount(assPayItemDTO.getAmount());
					if(assPayItemDTO.getPayItemCheck() != null) {
						paiHis.setIsBlocked(PayrollConstant.YES);
					}else {
						paiHis.setIsBlocked(null);
					}
					his.add(paiHis);
				}
			}
			
		}
		return his;
	}

	/**
	 * Get Updated Contribution
	 * @param updatedDedHis
	 * @param preContribution
	 * @param preTax
	 * @return Set<DeductionHistory>
	 */
	private Set<DeductionHistory> getUpdatedContribution(Set<DeductionHistory> updatedDedHis, List<ContributionDTO> preContribution, String taxType)
	{
		for (ContributionDTO dto : preContribution) {
			Long dedHisId = Long.parseLong(dto.getId());
			for (DeductionHistory his : updatedDedHis) {
				if (dedHisId.longValue() == his.getId().longValue()) {
					if (PayrollConstant.PRE_TAX.equalsIgnoreCase(taxType)) {
						his.setPreContributionAmount(dto.getContributionAmount());
					}
					else {
						his.setPostContributionAmount(dto.getContributionAmount());
					}
				}
			}
		}
		return updatedDedHis;
	}

	/**
	 * Get Upadted Deduction Histroy Data
	 * @param postDedList
	 * @param payroll 
	 * @param preTax 
	 * @return Set<DeductionHistory>
	 */
	private Set<DeductionHistory> getUpdatedDeductionHistroyData(List<DeductionDTO> dedList, EmployeePayroll payroll, String taxType)
	{
		Set<DeductionHistory> set = new HashSet<>();
		Set<DeductionHistory> dedHis = payroll.getDeductionHistory();
		for (DeductionDTO dto : dedList) {
			Long dtoHisId = Long.parseLong(dto.getId());
			for (DeductionHistory his : dedHis) {
				if (dtoHisId.longValue() == his.getId().longValue()) {
					if(PayrollConstant.PRE_TAX.equalsIgnoreCase(taxType)) {
						his.setPreDeductionAmount(dto.getAmount());
					}else {
						his.setPostDeductionAmount(dto.getAmount());
					}
					set.add(his);
				}
			}
		}
		return dedHis;
	}
	
	/**
	 * Updated Payment History
	 * @param payAdjHistory
	 * @param updatedPayroll
	 * @param payAdjustmentDTO
	 * @param loginUser
	 */
	private void updatePayAdjustmentHistory(PayAdjustmentHistory payAdjHistory, EmployeePayroll updatedPayroll, PayAdjustmentDTO payAdjustmentDTO, User loginUser)
	{
		float preRegHrs = 0;
		float preOtHrs = 0;
		BigDecimal preRegHrsAmt = BigDecimal.ZERO;
		BigDecimal preOtHrsAmt = BigDecimal.ZERO;
		payAdjHistory.setPayrollId(updatedPayroll);
		if(StringUtils.isNotBlank(payAdjustmentDTO.getPreRregularHours())) {
			preRegHrs = Float.parseFloat(payAdjustmentDTO.getPreRregularHours());
		}
		if(StringUtils.isNotBlank(payAdjustmentDTO.getPreOverTimeHours())) {
			preOtHrs = Float.parseFloat(payAdjustmentDTO.getPreOverTimeHours());
		}
		if(StringUtils.isNotBlank(payAdjustmentDTO.getPreRegularHoursAmt())) {
			preRegHrsAmt = new BigDecimal(payAdjustmentDTO.getPreRegularHoursAmt());
		}
		if(StringUtils.isNotBlank(payAdjustmentDTO.getPreOverTimeHoursAmt())) {
			preOtHrsAmt = new BigDecimal(payAdjustmentDTO.getPreOverTimeHoursAmt());
		}
		payAdjHistory.setPreRegularHours(String.valueOf(preRegHrs));
		payAdjHistory.setOvertimeHours(String.valueOf(preOtHrs));
		payAdjHistory.setPreRegularHoursAmt(preRegHrsAmt);
		payAdjHistory.setPreOvertimeHoursAmt(preOtHrsAmt);
		Set<PayAdjustmentDedHistory> payAdjDedHisSet = new HashSet<>();
		payAdjDedHisSet.addAll(getPayAdjDedHis(payAdjustmentDTO.getPreDeduction(),payAdjHistory));
		payAdjDedHisSet.addAll(getPayAdjDedHis(payAdjustmentDTO.getPostDeduction(),payAdjHistory));
		payAdjDedHisSet.addAll(getContributionHis(payAdjustmentDTO.getContribution(),payAdjHistory));
		payAdjHistory.setPayAdjustmentDedHistory(payAdjDedHisSet);	
		
		if(payAdjustmentDTO.getPayItem() !=null) {
			Set<PayAdjustmentPayItemHistory> set = new HashSet<>();
			for(AssociatePayItemDTO dto : payAdjustmentDTO.getPayItem()) {
				PayAdjustmentPayItemHistory  payItemHis = new PayAdjustmentPayItemHistory();
				payItemHis.setPayitemId(dto.getPayItemId().longValue());
				payItemHis.setPrePayItemAmt(new BigDecimal(dto.getPrePayItemAmt()));
				payItemHis.setPrePayItemName(dto.getPrePayItemName());
				payItemHis.setPayItemAmt(dto.getAmount());
				payItemHis.setPayItemName(dto.getPrePayItemName());
				payItemHis.setPayAdjustmentHistory(payAdjHistory);
				if(dto.getPayItemCheck() != null) {
					payItemHis.setPayItemBlock("BLOCKED");
				}else {
					payItemHis.setPrePayItemBlock("NO");
				}
				set.add(payItemHis);
			}
			payAdjHistory.setPayAdjustmentPayItemHistory(set);
		}
		payAdjHistory.setCreatedDate(new Date());
		payAdjHistory.setUpdatedDate(new Date());
		payAdjHistory.setCreatedBy(loginUser.getId());
		payAdjHistory.setUpdatedBy(loginUser.getId());
		payAdjHistory.setStatus(1);
		payAdjustmentHistoryService.savePayAdjustmentHistory(payAdjHistory);
	}

	/**
	 * populate contribution history
	 * 
	 * @param contribution
	 * @param payAdjHistory 
	 * @return Set<PayAdjustmentDedHistory>
	 */
	private Set<PayAdjustmentDedHistory> getContributionHis(List<ContributionDTO> contribution, PayAdjustmentHistory payAdjHistory)
	{
		Set<PayAdjustmentDedHistory> payAdjDedHisSet = new HashSet<>();
		if (contribution != null && contribution.size() > 0) {
			for (ContributionDTO dto : contribution) {
				PayAdjustmentDedHistory payDedHis = new PayAdjustmentDedHistory();
				payDedHis.setContributionId(Long.parseLong(dto.getId()));
				if(StringUtils.isNotBlank(dto.getPreContributionType())) {
					payDedHis.setPreContributionType(dto.getPreContributionType());
					payDedHis.setContributionType(dto.getPreContributionType());
				}
				payDedHis.setPreContributionAmt(dto.getPreContributionAmt());
				payDedHis.setContributionAmt(dto.getContributionAmount());
				payDedHis.setPayAdjustmentHistory(payAdjHistory);
				payAdjDedHisSet.add(payDedHis);
			}
		}
		return payAdjDedHisSet;
	}

	/**
	 * populate deduction history
	 * 
	 * @param preded
	 * @param payAdjHistory 
	 * @return Set<PayAdjustmentDedHistory>
	 */
	private Set<PayAdjustmentDedHistory> getPayAdjDedHis(List<DeductionDTO> preded, PayAdjustmentHistory payAdjHistory)
	{
		Set<PayAdjustmentDedHistory> payAdjDedHisSet = new HashSet<>();
		if (preded != null && preded.size() > 0) {
			for (DeductionDTO preDed : preded) {
				PayAdjustmentDedHistory payDedHis = new PayAdjustmentDedHistory();
				if(StringUtils.isNoneBlank(preDed.getPreDeductionType())) {
					payDedHis.setPreDeductionType(preDed.getPreDeductionType());
					payDedHis.setDeductionType(preDed.getPreDeductionType());
				}
				payDedHis.setPreDeductionAmt(new BigDecimal(preDed.getPreDeductionAmt()));
				payDedHis.setDeductionId(Long.parseLong(preDed.getId()));
				payDedHis.setDeductionAmt(preDed.getAmount());
				payDedHis.setPayAdjustmentHistory(payAdjHistory);
				payAdjDedHisSet.add(payDedHis);
			}
		}
		return payAdjDedHisSet;
	}
	
	
	/**
	 * Get Upadted Payroll LOP History
	 * @param postDedList
	 * @param payroll 
	 * @param preTax 
	 * @return Set<DeductionHistory>
	 */
	private Set<PayrollLopHis> getUpdatedPayrollLopHistroyData(List<PayrollLopHisDTO> lopHisList, EmployeePayroll payroll)
	{
		Set<PayrollLopHis> set = new HashSet<>();
		Set<PayrollLopHis> lopList = payroll.getPayrollLopHis();
		for (PayrollLopHisDTO dto : lopHisList) {
			Long lopHisId = Long.parseLong(dto.getId());
			for (PayrollLopHis hisLop : lopList) {
				if (lopHisId.longValue() == hisLop.getId().longValue()) {
					hisLop.setLopExempt("YES");
					set.add(hisLop);
				}
			}
		}
		return set;
	}
	
}