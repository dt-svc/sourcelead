package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateContributionDao;
import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
import com.bis.sourcelead.payroll.dao.GenerateUSPayrollDao;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayAdjustmentDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.EmployerPayrollTax;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.DeductionHistoryService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.service.PayrollAdjustmentService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.symmetry.PayrollCalculatorDTO;
import com.bis.sourcelead.payroll.symmetry.SymmetryPayrollCalculator;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.symmetry.ste.STEPayrollCalculator;

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
	
	
	 
	@Override
	public PaysheetFrequencyDTO generateUSPayroll(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO,User loginUser) throws Exception
	{
		String dates[] = generateUSPayrollDTO.getPayPeriod().split(",");
		Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		PaysheetFrequencyDTO paysheetFrequencyDTO = null;
		
		
		String[] groupId = generateUSPayrollDTO.getGroupId().split(",");
		List<AssociateSalary> assSallist = associateSalaryService.getAssociateSalaryListByGroupId(orgLocation,Long.parseLong(groupId[1]));
		
		UserGroup userGroup = userGroupDao.findUserGroupById(groupId[0]);
		
//		List<String> usrs = Arrays.asList(userGroup.getUserId().split(","));
//		List<Long> usersList = list.stream().map(AssociateSalary::getUser).collect(Collectors.toList());
//		ResponseList<ProjectMembers> response = projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(orgLocation.getId(), actualStartDate, actualEndDate, 1, 10);
		if (assSallist != null && assSallist.size() >0) {
			PaysheetFrequency paySheetFreq = new PaysheetFrequency();
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
			Set<EmployeePayroll> payrollList = calculatePayroll(paySheetFreq, loginUser, orgLocation,assSallist,freqStartDate, freqEndDate,userGroup);
			paySheetFreq.setEmployeepayroll(payrollList);
			if(payrollList !=null && payrollList.size() > 0) {
				PaysheetFrequency savedPaysheetFrequency = uSPaysheetService.addPaysheetFrequency(paySheetFreq);
				if(savedPaysheetFrequency != null) {
				 paysheetFrequencyDTO = getPayrollData(generateUSPayrollDTO,orgLocation,loginUser);
				}	
			}
		}
		return paysheetFrequencyDTO;
	}

	private Set<EmployeePayroll> calculatePayroll(PaysheetFrequency paySheetFreq, User loginUser, OrgLocations orgLocation, List<AssociateSalary> assoSalList, Date freqStartDate, Date freqEndDate, UserGroup userGroup)
	{
		Set<EmployeePayroll> set = new HashSet<>();
		try {
			for (AssociateSalary assoSal : assoSalList) {
			    EmployeePayroll employeePayroll = new EmployeePayroll();
			    employeePayroll.setPaysheetFrequency(paySheetFreq);
			    TotalHoursDTO totalHoursDto = userGroupDao.getTotalHoursByUserId(assoSal.getUserId().getId(),freqStartDate,freqEndDate);
			    AssociateTaxDedContriView associateTaxView = associateTaxService.findUserTaxDeatails(assoSal.getUserId().getId(), orgLocation);
//				Map<String, Float> hoursMap = submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(pm.getId(), orgLocation.getId(), actualStartDate, actualEndDate,"");
				float totalHours = 0;
				BigDecimal payRate = BigDecimal.ZERO;
				BigDecimal amount = BigDecimal.ZERO;
				if(PayrollConstant.HOURLY_RATE.equalsIgnoreCase(assoSal.getPayType())) {
					payRate = assoSal.getHourlyRate();
					if(totalHoursDto !=null && StringUtils.isNotBlank(totalHoursDto.getTotalHours())) {
						totalHours = Float.parseFloat(totalHoursDto.getTotalHours());
					}
					amount = getTotalAmount(totalHours,payRate);
//					if(totalHoursDto !=null) {
//						payRate = totalHoursDto.getPayRate();
//					}else {
//						if(associateTaxView.getHourlyRate() != null) {
//							payRate = associateTaxView.getHourlyRate().doubleValue();		
//						}
//					}	
				}else if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
					amount = assoSal.getGrossSalary();
				}
				employeePayroll.setRegularHours(String.valueOf(totalHours));
				employeePayroll.setEmpPayRate(payRate);
				User user = userService.getUserById(assoSal.getUserId().getId());
//				User user = new User();
//				user.setId(assoSal.getUser());
				employeePayroll.setUser(user);
				int invoiceFrequency = getProjectInvoiceFrequency(userGroup.getPayFrequency());
				employeePayroll.setFrequency(invoiceFrequency);
				employeePayroll.setPayCheckDate(paySheetFreq.getPayCheckDate());
				
				employeePayroll.setRehularHoursAmount(amount);
				DeductionDTO dedDTO = getDeductionAmount(user,orgLocation,amount,loginUser,paySheetFreq,employeePayroll);
				BigDecimal[] dedTaxes = dedDTO.getDedTaxes();
				BigDecimal[] contributionTaxes = dedDTO.getContributionTaxes();
				BigDecimal preTax = dedTaxes[0];
				BigDecimal postTax = dedTaxes[1];
				BigDecimal preContriSum = contributionTaxes[0];
				BigDecimal postContriSum = contributionTaxes[1];
				employeePayroll.setPreTaxDeduction(preTax);
				employeePayroll.setPostTaxDeduction(postTax);
				employeePayroll.setPreTaxContribution(postContriSum);
				employeePayroll.setPostTaxContribution(postContriSum);
				BigDecimal grossAmount = amount.subtract(preTax).add(preContriSum);
				employeePayroll.setGrossPay(grossAmount);
				PayrollCalculatorDTO payrollCalculator = symmetryPayrollCalculator.runUSPayroll(user,totalHours,grossAmount,invoiceFrequency,associateTaxView,paySheetFreq.getPayCheckDate());
				STEPayrollCalculator STE = payrollCalculator.getsTEPayrollCalculator();
				String  locationCode = payrollCalculator.getWebServiceLocationCodeV2().getLocationCode();
				BigDecimal taxes = STE.getFICACalc().add(STE.getMedicareCalc()).add(STE.getFederalCalc()).add(STE.getEICCalc()).asBigDecimal().add(STE.getStateCalc(locationCode).asBigDecimal()).add(STE.getCountyCalc(locationCode).asBigDecimal()).add(STE.getCityCalc(locationCode).asBigDecimal());
				employeePayroll.setTaxes(taxes);
				logger.info("*** Taxes :"+taxes);
				BigDecimal totalPay = grossAmount.subtract(taxes);
				BigDecimal netPay = totalPay.subtract(postTax).add(postContriSum);
				logger.info("*** Net Pay :"+netPay);
				employeePayroll.setNetPay(netPay);
				Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(assoSal.getUserId().getId(),orgLocation.getId());
				if (employee != null) {
					employeePayroll.setEmployee(employee);
				}
				Set<EmployeePayrollTax> payrollTaxSet = getPayrollTaxes(employeePayroll,orgLocation,payrollCalculator,loginUser);
				employeePayroll.setUsPayrollTax(payrollTaxSet);
				Set<EmployerPayrollTax> employerTaxes = getEmployerTaxes(employeePayroll,orgLocation,payrollCalculator,loginUser,user.getResidenceState());
				employeePayroll.setEmployerpayrolltax(employerTaxes);
				employeePayroll.setDeductionHistory(dedDTO.getDedHis());
				employeePayroll.setUsEmpPayrollUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				employeePayroll.setStatus(1);
				employeePayroll.setCreatedBy(loginUser.getId());
				employeePayroll.setUpdatedBy(loginUser.getId());
				employeePayroll.setCreatedDate(new Date());
				employeePayroll.setUpdatedDate(new Date());
				employeePayroll.setSubmitForAproverStatus(PayrollConstant.DRAFT);
				
				set.add(employeePayroll);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return set;
		
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
	private DeductionDTO getDeductionAmount(User user, OrgLocations orgLocation,BigDecimal amount, User logonUser,PaysheetFrequency paySheet, EmployeePayroll employeePayroll)
	{
		BigDecimal deductions[] = new BigDecimal[2];
	    BigDecimal[] contriTaxes = new BigDecimal[2];
		DeductionDTO dedDto = new DeductionDTO();
		Set<DeductionHistory> dedHis = new HashSet<>();
		try {
			ResponseList<DeductionDTO> res = associateDeductionService.findAllDeductionSummaryByUserId(user.getId(), orgLocation);
			List<AssociateContribution> contributions = associateContributionDao.findAllContributionSummaryByUserId(user.getId(), orgLocation);
			if (res.getData() != null) {
				BigDecimal preTax = BigDecimal.ZERO;
				BigDecimal postTax = BigDecimal.ZERO;
				 BigDecimal preContriTax = BigDecimal.ZERO;
	    		 BigDecimal postContriTax = BigDecimal.ZERO;
				List<DeductionDTO> preDedList = res.getData()
						.stream()
						.filter(ded -> "pretax".equalsIgnoreCase(ded.getDedTaxType()))
						.collect(Collectors.toList());
				for (DeductionDTO ded : preDedList) {
					AssociateDeduction assoDeduction = associateDeductionService.getAsociateDedByUserIdDeductionId(Long.parseLong(ded.getId()), user.getId());
					boolean dedSchedulePay = payrollUtil.findDeductionSchedulePay(assoDeduction, paySheet.getFrequencyStartDate(), paySheet.getFrequencyEndDate());
					if (!dedSchedulePay) {
						if (PayrollConstant.FIXED.equalsIgnoreCase(ded.getMethod())) {
							preTax = preTax.add(ded.getAmount());
						}
						else {
							BigDecimal perAmount = getPercentAmount(amount, ded.getAmount());
							preTax = preTax.add(perAmount);
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
						assoDeduction.setDedEndDate(deductionDates[1]);
						associateDeductionDao.saveAssociateDeduction(assoDeduction);
						
						Long contributionId = null;
						if(contributions != null) {
							AssociateContribution assContribution = contributions
									.stream()
									.filter(con->con.getContributionTypeId().getDeductionTypeId().getId().longValue() == assoDeduction.getDeductionTypeId().getId().longValue())
									.findAny()
									.orElse(null);
							if(assContribution != null) {
								if (PayrollConstant.FIXED.equalsIgnoreCase(assContribution.getContributionMethod())) {
			    					preContriTax = preContriTax.add(assContribution.getContributionAmount());
								}
								else {
									BigDecimal perAmount = getPercentAmount(amount, assContribution.getContributionAmount());
									preContriTax = preContriTax.add(perAmount);
								}
								assContribution.setContributionStatus("CONTRIBUTED");
								assContribution.setContributionStartDate(deductionDates[0]);
								assContribution.setContributionEndDate(deductionDates[1]);
								associateContributionDao.saveAssociateContribution(assContribution);
								contributionId = assContribution.getId();
							}
							
						}
						DeductionHistory dedHistory  = populateDeductionHistory(paySheet,ded,logonUser,deductionDates,employeePayroll,contributionId);
						dedHis.add(dedHistory);
					}
				}
				deductions[0] = preTax;
				contriTaxes[0] = preContriTax;
				List<DeductionDTO> postDedList = res.getData().stream().filter(ded -> "posttax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
				
				for (DeductionDTO postDed : postDedList) {
					AssociateDeduction assoDeduction = associateDeductionService.getAsociateDedByUserIdDeductionId(Long.parseLong(postDed.getId()), user.getId());
					boolean dedSchedulePay = payrollUtil.findDeductionSchedulePay(assoDeduction, paySheet.getFrequencyStartDate(), paySheet.getFrequencyEndDate());
					if (!dedSchedulePay) {
						if (PayrollConstant.FIXED.equalsIgnoreCase(postDed.getMethod())) {
							postTax = postTax.add(postDed.getAmount());
						}
						else {
							BigDecimal perAmount = getPercentAmount(amount, postDed.getAmount());
							postTax = postTax.add(perAmount);
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
						assoDeduction.setDedEndDate(deductionDates[1]);
						associateDeductionDao.saveAssociateDeduction(assoDeduction);
						
						
						Long contributionId = null;
						if(contributions != null) {
							AssociateContribution assContribution = contributions
									.stream()
									.filter(con -> con.getContributionTypeId().getDeductionTypeId().getId().longValue() == assoDeduction.getDeductionTypeId().getId().longValue())
									.findAny()
									.orElse(null);
							if(assContribution != null) {
								if (PayrollConstant.FIXED.equalsIgnoreCase(assContribution.getContributionMethod())) {
									postContriTax = postContriTax.add(assContribution.getContributionAmount());
								}
								else {
									BigDecimal perAmount = getPercentAmount(amount, assContribution.getContributionAmount());
									postContriTax = postContriTax.add(perAmount);
								}
								assContribution.setContributionStatus(PayrollConstant.CONTRIBUTED);
								assContribution.setContributionStartDate(deductionDates[0]);
								assContribution.setContributionEndDate(deductionDates[1]);
								associateContributionDao.saveAssociateContribution(assContribution);
								contributionId = assContribution.getId();
							}
							
						}
						DeductionHistory dedHistory  = populateDeductionHistory(paySheet,postDed,logonUser,deductionDates,employeePayroll,contributionId);
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
	 * populating deduction histroy
	 * 
	 * @param paySheet
	 * @param ded
	 * @param logonUser
	 * @param deductionDates
	 * @param employeePayroll
	 * @param contributionId 
	 * @return DeductionHistory
	 */
	private DeductionHistory populateDeductionHistory(PaysheetFrequency paySheet, DeductionDTO ded, User logonUser, Date[] deductionDates, EmployeePayroll employeePayroll, Long contributionId)
	{
		DeductionHistory dedHistory = new DeductionHistory();
		dedHistory.setDeductionId(Long.parseLong(ded.getId()));
		dedHistory.setDeductionDate(new Date());
		dedHistory.setFrequencyStartDate(deductionDates[0]);
		if (deductionDates[1] != null) {
			dedHistory.setFrequencyEndDate(deductionDates[1]);
		}
		else {
			dedHistory.setFrequencyEndDate(paySheet.getFrequencyEndDate());
		}
		dedHistory.setUserId(employeePayroll.getUser().getId());
		dedHistory.setCreatedBy(logonUser.getId());
		dedHistory.setPayCheckDate(paySheet.getPayCheckDate());
		dedHistory.setUpdatedBy(logonUser.getId());
		dedHistory.setCreatedDate(new Date());
		dedHistory.setUpdatedDate(new Date());
		dedHistory.setPayroll(employeePayroll);
		if(contributionId != null) {
			dedHistory.setContributionId(contributionId);
		}
		dedHistory.setDeductionHisUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
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
		taxes.setLocationCode(locationCode +","+employeePayroll.getUser().getResidenceState()); // locationCode
		taxes.setUsEmpPayrollTaxUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		taxes.setEarnedIncCredit(STE.getEICCalc().asBigDecimal());
		taxes.setLocalTax(STE.getCityCalc(locationCode).asBigDecimal());
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
	
	/***
	 * calculating total amount by multipltying hours with rate
	 * @param totalHours
	 * @param payRate
	 * @return double
	 */
	private BigDecimal getTotalAmount(float totalHours, BigDecimal payRate)
	{
		String pRate = String.valueOf(totalHours);
		String[] rate = pRate.split("\\.");
		double hr = Double.parseDouble(rate[0]);
		double min = Double.parseDouble(rate[1]);
		double hrsToMin = hr * 60;
		double totalMin = hrsToMin + min;
		BigDecimal b = payRate.divide(PayrollConstant.ONE_MIN_SEC);
//		double perMinRate = payRate / 60;
		BigDecimal totatAmount = BigDecimal.valueOf(totalMin).multiply(payRate);
		return totatAmount;
	}

	@Override
	public PaysheetFrequencyDTO getPayrollData(GenerateUSPayrollDTO generateUSPayrollDTO, OrgLocations orgLocation, User loginUser)
	{
		PaysheetFrequency paysheetFrequency = generateUSPayrollDao.getPayrollData(generateUSPayrollDTO, orgLocation, loginUser);
		PaysheetFrequencyDTO paysheetFrequencyDTO = new PaysheetFrequencyDTO();
		List<USPayrollDTO> list = null;
		try {
			if (paysheetFrequency != null && paysheetFrequency.getEmployeepayroll() != null) {
				paysheetFrequencyDTO = new PaysheetFrequencyDTO();
				paysheetFrequencyDTO.setPayrollFrequencyUUID(paysheetFrequency.getPaySheetFrequencyUUID());
				list = new ArrayList<>();
				for(EmployeePayroll pay : paysheetFrequency.getEmployeepayroll()) {
					USPayrollDTO payrollDTO = new USPayrollDTO();
					payrollDTO.setEmpId(pay.getEmployee().getEmployeeId());
					StringBuilder sb = new StringBuilder();
					if (StringUtils.isNoneBlank(pay.getUser().getLastName())) {
						sb.append(pay.getUser().getFirstName()).append(" ").append(pay.getUser().getLastName());
					}
					else {
						sb.append(pay.getUser().getFirstName());
					}
					payrollDTO.setEmpName(sb.toString());
					payrollDTO.setEmpPayrollId(pay.getId());
					payrollDTO.setEmpPayrollUUID(pay.getUsEmpPayrollUUID());
					payrollDTO.setGrossPay(pay.getGrossPay());
					payrollDTO.setNetPay(pay.getNetPay());
					payrollDTO.setRegularHrsTotalAmt(pay.getRehularHoursAmount());
					payrollDTO.setPayRate(pay.getEmpPayRate());
					payrollDTO.setPostTaxDeduction(pay.getPostTaxDeduction());
					payrollDTO.setPreTaxDeduction(pay.getPreTaxDeduction());
					payrollDTO.setRegularHours(Float.parseFloat(pay.getRegularHours()));
					payrollDTO.setTaxes(pay.getTaxes());
					payrollDTO.setOtHoursTotalAmt(pay.getOverTimeAmount());
					double overTimeHours = 0;
					if(StringUtils.isNoneBlank(pay.getOverTimeHours())) {
						overTimeHours = Double.parseDouble(pay.getOverTimeHours());
					}
					payrollDTO.setOvertimeHours(overTimeHours);
					if(PayrollConstant.DRAFT.equalsIgnoreCase(pay.getSubmitForAproverStatus())) {
						list.add(payrollDTO);	
					}
					
				}
				paysheetFrequencyDTO.setPayrollDTO(list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paysheetFrequencyDTO;
	}

	@Override
	public Response<USPayrollDTO> getEmployeePayrollByUUID(String payrollUUID,OrgLocations orgLocation)
	{
		EmployeePayroll payroll = generateUSPayrollDao.getEmployeePayrollByUUID(payrollUUID);
		
		Response<USPayrollDTO> res = new Response<>();
		if(payroll != null ) {
			USPayrollDTO payrollDTO = new USPayrollDTO();
			payrollDTO.setPayrollId(String.valueOf(payroll.getId()));
			payrollDTO.setPayrollUUID(payroll.getUsEmpPayrollUUID());
			double totalHours = 0;
			
			payrollDTO.setEmpId(payroll.getEmployee().getEmployeeId());
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNoneBlank(payroll.getUser().getLastName())) {
				sb.append(payroll.getUser().getFirstName()).append(" ").append(payroll.getUser().getLastName());
			}
			else {
				sb.append(payroll.getUser().getFirstName());
			}
			payrollDTO.setEmpName(sb.toString());
			payrollDTO.setEmpPayrollId(payroll.getId());
			payrollDTO.setEmpPayrollUUID(payroll.getUsEmpPayrollUUID());
			payrollDTO.setGrossPay(payroll.getGrossPay());
			payrollDTO.setNetPay(payroll.getNetPay());
			payrollDTO.setRegularHrsTotalAmt(payroll.getRehularHoursAmount());
			payrollDTO.setPayRate(payroll.getEmpPayRate());
			payrollDTO.setPostTaxDeduction(payroll.getPostTaxDeduction());
			payrollDTO.setPreTaxDeduction(payroll.getPreTaxDeduction());
			payrollDTO.setRegularHours(Float.parseFloat(payroll.getRegularHours()));
			payrollDTO.setTaxes(payroll.getTaxes());
			double overTimeHours = 0;
			if(StringUtils.isNotBlank(payroll.getOverTimeHours())) {
				payrollDTO.setOvertimeHours(Double.parseDouble(payroll.getOverTimeHours()));	
				overTimeHours = Double.parseDouble(payroll.getOverTimeHours());
			}
			payrollDTO.setOvertimeHours(overTimeHours);
			totalHours = CommonUtil.getTotalRegularOverTimeHours(Double.parseDouble(payroll.getRegularHours()), overTimeHours);
			payrollDTO.setTotalHours(totalHours);
			BigDecimal overTimeAmount = BigDecimal.ZERO;
			if(payroll.getOverTimeAmount() != null) {
				payrollDTO.setOtHoursTotalAmt(payroll.getOverTimeAmount());
				overTimeAmount = payroll.getOverTimeAmount();
			}else {
				payrollDTO.setOtHoursTotalAmt(overTimeAmount);
			}
			BigDecimal totalEarnings = payroll.getRehularHoursAmount().add(overTimeAmount);
			payrollDTO.setTotalEarnings(totalEarnings);
			payrollDTO.setEmployeeAddress(payroll.getUser().getResidenceAddress());
			
			for(EmployeePayrollTax tax : payroll.getUsPayrollTax()) {
				payrollDTO.setFederal(tax.getFederalWithholding());
				payrollDTO.setFica(tax.getFicaWithholding());
				payrollDTO.setAeic(tax.getEarnedIncCredit());
				payrollDTO.setMedicare(tax.getMedicareWithholding());
				payrollDTO.setStateWithHolding(tax.getStateWithholding());
			}
			payrollDTO.setTaxes(payroll.getTaxes());
			
//			ResponseList<DeductionDTO> dedRes = associateDeductionService.findAllDeductionSummaryByUserId(payroll.getUser().getId(), orgLocation);
			List<DeductionHistoryDTO> dedContrbutionHistory = deductionHistoryService.getDeductionsByPayrollId(payroll.getId());
			List<ContributionHistoryDTO> contributionHistory = deductionHistoryService.getContributionsByPayrollId(payroll.getId());
//			ResponseList<ContributionDTO> contributionRes = associateContributionService.findAllContributionSummaryByUserId(payroll.getUser().getId(), orgLocation);
			AssociateSalaryDTO assoSal = associateSalaryService.findAssociateSalaryByUserId(payroll.getUser().getId(), orgLocation);
			if(dedContrbutionHistory != null) {
				List<DeductionHistoryDTO>  preDedList = dedContrbutionHistory
						.stream()
						.filter(ded->"pretax".equalsIgnoreCase(ded.getTaxType()))
						.collect(Collectors.toList());
				List<DeductionHistoryDTO>  postDedList = dedContrbutionHistory
						.stream()
						.filter(ded->"posttax".equalsIgnoreCase(ded.getTaxType()))
						.collect(Collectors.toList());
				
				double  preDedTotal = dedContrbutionHistory.stream().filter(ded->"pretax".equalsIgnoreCase(ded.getTaxType())).mapToDouble(ded->ded.getAmount().doubleValue()).sum();
				double postDedTotal = dedContrbutionHistory.stream().filter(ded->"posttax".equalsIgnoreCase(ded.getTaxType())).mapToDouble(ded->ded.getAmount().doubleValue()).sum();
				payrollDTO.setPostDed(postDedList);
				payrollDTO.setPreDed(preDedList);
				payrollDTO.setPreDedTotal(BigDecimal.valueOf(preDedTotal));
				payrollDTO.setPostDedTotal(BigDecimal.valueOf(postDedTotal));
			} 
			if(contributionHistory != null) {
				payrollDTO.setContribution(contributionHistory);
				double contributionTotal = contributionHistory.stream().mapToDouble(con->con.getContributionAmount().doubleValue()).sum();
				payrollDTO.setContributionTotal(BigDecimal.valueOf(contributionTotal));
			}
			BigDecimal piTotal = BigDecimal.ZERO;
			if(assoSal.getAssociatePayItemDto() != null && assoSal.getAssociatePayItemDto().size() >0) {
				payrollDTO.setPayItem(assoSal.getAssociatePayItemDto());
				double payItemTotal = assoSal.getAssociatePayItemDto().stream().mapToDouble(pitem->Double.parseDouble(pitem.getPayItemAmount())).sum();
				piTotal = BigDecimal.valueOf(payItemTotal);
				payrollDTO.setPayItemTotal(piTotal);
			}
			res.setObject(payrollDTO);
		}
		return res;
	}

	
	@Override
	public Response<PayAdjustmentDTO> updateEmployeePayroll(PayAdjustmentDTO payAdjustmentDTO, OrgLocations orgLocation, User loginUser)
	{
		Response<PayAdjustmentDTO> res = new Response<>();
		try {
			float totalHours = Float.parseFloat(payAdjustmentDTO.getRegularHours()) + Float.parseFloat(payAdjustmentDTO.getOverTimeHours());
			double amount = Double.parseDouble(payAdjustmentDTO.getRegularHoursAmt()) + Double.parseDouble(payAdjustmentDTO.getOverTimeHoursAmt());
			double preTax = 0;
			double postTax = 0;
			if(payAdjustmentDTO.getPreDeduction() != null && payAdjustmentDTO.getPreDeduction().size() >0) {
				 preTax = payAdjustmentDTO.getPreDeduction().stream().mapToDouble(pre->pre.getAmount().doubleValue()).sum();
			}
			if(payAdjustmentDTO.getPostDeduction() != null && payAdjustmentDTO.getPostDeduction().size() >0) {
				 postTax = payAdjustmentDTO.getPostDeduction().stream().mapToDouble(pre->pre.getAmount().doubleValue()).sum();	
			}
			double preTaxContriSum = 0;
			double postTaxContriSum = 0;
			if(payAdjustmentDTO.getContribution() != null && payAdjustmentDTO.getContribution().size()>0) {
				preTaxContriSum = payAdjustmentDTO.getContribution().stream().filter(con->"pretax".equalsIgnoreCase(con.getTaxType())).mapToDouble(con->con.getContributionAmount().doubleValue()).sum();
				postTaxContriSum= payAdjustmentDTO.getContribution().stream().filter(con->"posttax".equalsIgnoreCase(con.getTaxType())).mapToDouble(con->con.getContributionAmount().doubleValue()).sum();
			}
			
			BigDecimal grossAmount = BigDecimal.valueOf(amount).subtract(BigDecimal.valueOf(preTax)).add(BigDecimal.valueOf(preTaxContriSum));
			EmployeePayroll payroll = payrollAdjustmentService.getEmployeePayrollByUUID(payAdjustmentDTO.getPayrollUUID());
			payroll.setPreTaxContribution(BigDecimal.valueOf(preTaxContriSum));
			payroll.setPostTaxContribution(BigDecimal.valueOf(postTaxContriSum));
			payroll.setRegularHours(payAdjustmentDTO.getRegularHours());
			payroll.setRehularHoursAmount(new BigDecimal(payAdjustmentDTO.getRegularHoursAmt()));
			payroll.setOverTimeHours(payAdjustmentDTO.getOverTimeHours());
			payroll.setOverTimeAmount(new BigDecimal(payAdjustmentDTO.getOverTimeHoursAmt()));
			payroll.setPostTaxDeduction(BigDecimal.valueOf(postTax));
			payroll.setPreTaxDeduction(BigDecimal.valueOf(preTax));
			payroll.setGrossPay(grossAmount);
			AssociateTaxDedContriView associateTaxDTO = associateTaxService.findUserTaxDeatails(payroll.getUser().getId(), orgLocation);
			PayrollCalculatorDTO payrollCalculator = symmetryPayrollCalculator.runUSPayroll(payroll.getUser(), totalHours, grossAmount, payroll.getFrequency(), associateTaxDTO,payroll.getPayCheckDate());
			STEPayrollCalculator STE = payrollCalculator.getsTEPayrollCalculator();
			String  locationCode = payrollCalculator.getWebServiceLocationCodeV2().getLocationCode();
			BigDecimal taxes = STE.getFICACalc().add(STE.getMedicareCalc()).add(STE.getFederalCalc()).add(STE.getEICCalc()).asBigDecimal().add(STE.getStateCalc(locationCode).asBigDecimal()).add(STE.getCountyCalc(locationCode).asBigDecimal());
			payroll.setTaxes(taxes);
			BigDecimal totalPay = grossAmount.subtract(taxes);
			BigDecimal netPay = totalPay.subtract(BigDecimal.valueOf(postTax)).add(BigDecimal.valueOf(postTaxContriSum));
			payroll.setNetPay(netPay);
			Set<EmployeePayrollTax> payrollTaxSet = getPayrollTaxes(payroll,orgLocation,payrollCalculator,loginUser);
			payroll.setUsPayrollTax(payrollTaxSet);
			Set<EmployerPayrollTax> employerTaxes = getEmployerTaxes(payroll,orgLocation,payrollCalculator,loginUser,payroll.getUser().getResidenceState());
			payroll.setEmployerpayrolltax(employerTaxes);
			payroll.setUpdatedBy(loginUser.getId());
			payroll.setUpdatedDate(new Date());
			EmployeePayroll updatedPayroll = payrollAdjustmentService.updateEmployeePayroll(payroll);
			if (updatedPayroll != null) {
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
	
}