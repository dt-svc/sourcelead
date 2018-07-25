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
import org.eclipse.core.internal.resources.Project;
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
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.GenerateUSPayrollDao;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayAdjustmentDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.EmployerPayrollTax;
import com.bis.sourcelead.payroll.entity.Paysheet;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.service.PayrollAdjustmentService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.symmetry.HelloPayrollWorldBis;
import com.bis.sourcelead.payroll.symmetry.PayrollCalculatorDTO;
import com.bis.sourcelead.payroll.symmetry.SymmetryPaypal;
import com.bis.sourcelead.payroll.symmetry.SymmetryPayrollCalculator;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.symmetry.ste.*;

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
	
	 
	@Override
	public PaysheetFrequencyDTO generateUSPayroll(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO,User loginUser) throws Exception
	{
		String dates[] = generateUSPayrollDTO.getPayPeriod().split(",");
		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		PaysheetFrequencyDTO paysheetFrequencyDTO = null;
		
		UserGroup userGroup = userGroupDao.findUserGroupById(generateUSPayrollDTO.getGroupId());
		List<String> usrs = Arrays.asList(userGroup.getUserId().split(","));
		List<Long> usersList = usrs.stream().map(Long::valueOf).collect(Collectors.toList());
//		ResponseList<ProjectMembers> response = projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(orgLocation.getId(), actualStartDate, actualEndDate, 1, 10);
		if (usersList != null && usersList.size() >0) {
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
			Set<EmployeePayroll> payrollList = calculatePayroll(paySheetFreq, loginUser, orgLocation,usersList,actualStartDate, actualEndDate,userGroup);
			paySheetFreq.setEmployeepayroll(payrollList);
			PaysheetFrequency savedPaysheetFrequency = uSPaysheetService.addPaysheetFrequency(paySheetFreq);
			if(savedPaysheetFrequency != null) {
			 paysheetFrequencyDTO = getPayrollData(generateUSPayrollDTO,orgLocation,loginUser);
			}
		}
		return paysheetFrequencyDTO;
	}

	private Set<EmployeePayroll> calculatePayroll(PaysheetFrequency paySheetFreq, User loginUser, OrgLocations orgLocation, List<Long> userList, Date actualStartDate, Date actualEndDate, UserGroup userGroup)
	{
		Set<EmployeePayroll> set = new HashSet<>();
		try {
			for (Long userId : userList) {
			    EmployeePayroll employeePayroll = new EmployeePayroll();
			    employeePayroll.setPaysheetFrequency(paySheetFreq);
			    TotalHoursDTO totalHoursDto = userGroupDao.getTotalHoursByUserId(userId,actualStartDate,actualEndDate);
//				Map<String, Float> hoursMap = submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(pm.getId(), orgLocation.getId(), actualStartDate, actualEndDate,"");
				float totalHours = 0;
				if(totalHoursDto !=null && StringUtils.isNotBlank(totalHoursDto.getTotalHours())) {
					totalHours = Float.parseFloat(totalHoursDto.getTotalHours());
				}
				employeePayroll.setRegularHours(String.valueOf(totalHours));
				double payRate = 0;
				if(totalHoursDto !=null) {
					payRate = totalHoursDto.getPayRate();
				}
				employeePayroll.setEmpPayRate(BigDecimal.valueOf(payRate));
				double amount = getTotalAmount(totalHours,payRate);
				User user = userService.getUserById(userId);
				employeePayroll.setUser(user);
				int invoiceFrequency = getProjectInvoiceFrequency(userGroup.getPayFrequency());
				employeePayroll.setFrequency(invoiceFrequency);
				employeePayroll.setPayCheckDate(paySheetFreq.getPayCheckDate());
				AssociateTaxDedContriView associateTaxDTO = associateTaxService.findUserTaxDeatails(user.getId(), orgLocation);
				employeePayroll.setRehularHoursAmount(BigDecimal.valueOf(amount));
				ResponseList<DeductionDTO> res = associateDeductionService.findAllDeductionSummaryByUserId(user.getId(), orgLocation);
				ResponseList<ContributionDTO> contributionRes = associateContributionService.findAllContributionSummaryByUserId(user.getId(), orgLocation);
				BigDecimal preTax = BigDecimal.ZERO;
				BigDecimal postTax = BigDecimal.ZERO;
				 if(res.getData() != null) {
						double preDeduction = res.getData().stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).mapToDouble(ded->ded.getAmount().doubleValue()).sum();
						preTax = BigDecimal.valueOf(preDeduction);
						employeePayroll.setPreTaxDeduction(preTax);
						double postDeduction = res.getData().stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).mapToDouble(ded->ded.getAmount().doubleValue()).sum();
						postTax = BigDecimal.valueOf(postDeduction);
						employeePayroll.setPostTaxDeduction(postTax);
					} 
				 BigDecimal preContriSum = BigDecimal.ZERO;
				 BigDecimal postContriSum = BigDecimal.ZERO;
				if(contributionRes.getData() != null) {
					double preTaxContribution = contributionRes.getData().stream().filter(con->"pretax".equalsIgnoreCase(con.getTaxType())).mapToDouble(con->con.getContributionAmount().doubleValue()).sum();
					preContriSum = BigDecimal.valueOf(preTaxContribution);
					employeePayroll.setPreTaxContribution(preContriSum);
					double postTaxContribution = contributionRes.getData().stream().filter(con->"posttax".equalsIgnoreCase(con.getTaxType())).mapToDouble(con->con.getContributionAmount().doubleValue()).sum();
					postContriSum = BigDecimal.valueOf(postTaxContribution);
				}
				BigDecimal grossAmount = BigDecimal.valueOf(amount).subtract(preTax).add(preContriSum);
				employeePayroll.setGrossPay(grossAmount);
				PayrollCalculatorDTO payrollCalculator = symmetryPayrollCalculator.runUSPayroll(user,totalHours,grossAmount,invoiceFrequency,associateTaxDTO,paySheetFreq.getPayCheckDate());
				STEPayrollCalculator STE = payrollCalculator.getsTEPayrollCalculator();
				String  locationCode = payrollCalculator.getWebServiceLocationCodeV2().getLocationCode();
				BigDecimal taxes = STE.getFICACalc().add(STE.getMedicareCalc()).add(STE.getFederalCalc()).add(STE.getEICCalc()).asBigDecimal().add(STE.getStateCalc(locationCode).asBigDecimal()).add(STE.getCountyCalc(locationCode).asBigDecimal()).add(STE.getCityCalc(locationCode).asBigDecimal());
				employeePayroll.setTaxes(taxes);
				System.out.println("*** Taxes :"+taxes);
				BigDecimal totalPay = grossAmount.subtract(taxes);
				BigDecimal netPay = totalPay.subtract(postTax).add(postContriSum);
				System.out.println("*** Net Pay :"+netPay);
				employeePayroll.setNetPay(netPay);
				Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(userId,orgLocation.getId());
				if (employee != null) {
					employeePayroll.setEmployee(employee);
				}
				Set<EmployeePayrollTax> payrollTaxSet = getPayrollTaxes(employeePayroll,orgLocation,payrollCalculator,loginUser);
				employeePayroll.setUsemployeepayroll(payrollTaxSet);
				Set<EmployerPayrollTax> employerTaxes = getEmployerTaxes(employeePayroll,orgLocation,payrollCalculator,loginUser,user.getResidenceState());
				employeePayroll.setEmployerpayrolltax(employerTaxes);
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
		if(employeePayroll.getUsemployeepayroll()!= null) {
			taxes = employeePayroll.getUsemployeepayroll().iterator().next();
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
	private double getTotalAmount(float totalHours, Double payRate)
	{
		String pRate = String.valueOf(totalHours);
		String[] rate = pRate.split("\\.");
		double hr = Double.parseDouble(rate[0]);
		double min = Double.parseDouble(rate[1]);
		double hrsToMin = hr * 60;
		double totalMin = hrsToMin + min;
		double perMinRate = payRate / 60;
		return totalMin * perMinRate;
	}

	@Override
	public PaysheetFrequencyDTO getPayrollData(GenerateUSPayrollDTO generateUSPayrollDTO, OrgLocations orgLocation, User loginUser)
	{
		PaysheetFrequency paysheetFrequency = generateUSPayrollDao.getPayrollData(generateUSPayrollDTO, orgLocation, loginUser);
		PaysheetFrequencyDTO paysheetFrequencyDTO = new PaysheetFrequencyDTO();
		List<USPayrollDTO> list = null;
		try {
			if (paysheetFrequency != null && paysheetFrequency.getEmployeepayroll() != null) {
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
			}
			paysheetFrequencyDTO.setPayrollDTO(list);
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
			
			for(EmployeePayrollTax tax : payroll.getUsemployeepayroll()) {
				payrollDTO.setFederal(tax.getFederalWithholding());
				payrollDTO.setFica(tax.getFicaWithholding());
				payrollDTO.setAeic(tax.getEarnedIncCredit());
				payrollDTO.setMedicare(tax.getMedicareWithholding());
				payrollDTO.setStateWithHolding(tax.getStateWithholding());
			}
			payrollDTO.setTaxes(payroll.getTaxes());
			
			ResponseList<DeductionDTO> dedRes = associateDeductionService.findAllDeductionSummaryByUserId(payroll.getUser().getId(), orgLocation);
			ResponseList<ContributionDTO> contributionRes = associateContributionService.findAllContributionSummaryByUserId(payroll.getUser().getId(), orgLocation);
			AssociateSalaryDTO assoSal = associateSalaryService.findAssociateSalaryByUserId(payroll.getUser().getId(), orgLocation);
			if(dedRes.getData() != null) {
				List<DeductionDTO>  preDedList = dedRes.getData().stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
				List<DeductionDTO>  postDedList = dedRes.getData().stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
				
				double  preDedTotal = dedRes.getData().stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).mapToDouble(ded->ded.getAmount().doubleValue()).sum();
				double postDedTotal = dedRes.getData().stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).mapToDouble(ded->ded.getAmount().doubleValue()).sum();
				payrollDTO.setPostDed(postDedList);
				payrollDTO.setPreDed(preDedList);
				payrollDTO.setPreDedTotal(BigDecimal.valueOf(preDedTotal));
				payrollDTO.setPostDedTotal(BigDecimal.valueOf(postDedTotal));
			} 
			if(contributionRes.getData() != null) {
				payrollDTO.setContribution(contributionRes.getData());
				double contributionTotal = contributionRes.getData().stream().mapToDouble(con->con.getContributionAmount().doubleValue()).sum();
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
			payroll.setUsemployeepayroll(payrollTaxSet);
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
	
		
		return null;
	}
	
}
