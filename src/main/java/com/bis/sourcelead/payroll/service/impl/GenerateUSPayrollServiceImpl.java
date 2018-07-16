package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dao.GenerateUSPayrollDao;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.EmployerPayrollTax;
import com.bis.sourcelead.payroll.entity.Paysheet;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.symmetry.HelloPayrollWorldBis;
import com.bis.sourcelead.payroll.symmetry.PayrollCalculatorDTO;
import com.bis.sourcelead.payroll.symmetry.SymmetryPaypal;
import com.bis.sourcelead.payroll.symmetry.SymmetryPayrollCalculator;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;
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
	
	 
	@Override
	public ResponseList<USPayrollDTO> generateUSPayroll(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO,User loginUser) throws Exception
	{
		String dates[] = generateUSPayrollDTO.getPayPeriod().split(",");
		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		ResponseList<USPayrollDTO> usPayrollDTOResponse = new ResponseList<>();
		ResponseList<ProjectMembers> response = projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(orgLocation.getId(), actualStartDate, actualEndDate, 1, 10);
		if (response != null && response.getData() != null && !response.getData().isEmpty()) {
			
			PaysheetFrequency paySheetFreq = new PaysheetFrequency();
			UserGroup userGroup = userGroupDao.findUserGroupById(generateUSPayrollDTO.getGroupId());
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
			paySheetFreq.setType("PAYROLL");
			paySheetFreq.setPaySheetFrequencyUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			paySheetFreq.setEmployeepayroll(calculatePayroll(paySheetFreq, loginUser, orgLocation,response.getData(),actualStartDate, actualEndDate));
			PaysheetFrequency savedPaysheetFrequency = uSPaysheetService.addPaysheetFrequency(paySheetFreq);
			if(savedPaysheetFrequency != null) {
			List<USPayrollDTO> list = getPayrollData(generateUSPayrollDTO,orgLocation,loginUser);
			usPayrollDTOResponse.setData(list);
			usPayrollDTOResponse.setRecordsFiltered(list.size());
			usPayrollDTOResponse.setRecordsTotal(list.size());
			usPayrollDTOResponse.setResponseMessage("success");
			}
		}
		return usPayrollDTOResponse;
	}

	private Set<EmployeePayroll> calculatePayroll(PaysheetFrequency paySheetFreq, User loginUser, OrgLocations orgLocation, List<ProjectMembers> projMemberData, Date actualStartDate, Date actualEndDate)
	{
		Set<EmployeePayroll> set = new HashSet<>();
		try {
			for (ProjectMembers pm : projMemberData) {
			    EmployeePayroll employeePayroll = new EmployeePayroll();
			    employeePayroll.setPaysheetFrequency(paySheetFreq);
				Map<String, Float> hoursMap = submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(pm.getId(), orgLocation.getId(), actualStartDate, actualEndDate);
				float totalHours = hoursMap.get("TOTAL_HOURS");
				employeePayroll.setRegularHours(String.valueOf(totalHours));
				employeePayroll.setEmpPayRate(BigDecimal.valueOf(pm.getPayRate()));
				double amount = getTotalAmount(totalHours,pm.getPayRate());
				User user = userService.getUserbyConsultantUserId(pm.getProjectMemberCandidteId().getConsultantUser().getId());
				employeePayroll.setUser(user);
				int invoiceFrequency = getProjectInvoiceFrequency(pm.getProjects().getInvoiceFrequency());
				AssociateTaxDedContriView associateTaxDTO = associateTaxService.findUserTaxDeatails(user.getId(), orgLocation);
				employeePayroll.setRehularHoursAmount(BigDecimal.valueOf(amount));
				   if(associateTaxDTO.getTaxType() != null) {
				    	if("posttax".equalsIgnoreCase(associateTaxDTO.getTaxType())) {
				    		employeePayroll.setPreTaxDeduction(associateTaxDTO.getDeductionAmount());
						}
				    	if("pretax".equalsIgnoreCase(associateTaxDTO.getTaxType())) {
				    		employeePayroll.setPostTaxDeduction(associateTaxDTO.getDeductionAmount());
				    	}
						
				    }
				
				employeePayroll.setGrossPay(BigDecimal.valueOf(amount));
				PayrollCalculatorDTO payrollCalculator = symmetryPayrollCalculator.runUSPayroll(user,totalHours,amount,invoiceFrequency,associateTaxDTO,STEhome,jniHome,user.getResidenceState());
				STEPayrollCalculator STE = payrollCalculator.getsTEPayrollCalculator();
				String  locationCode = payrollCalculator.getWebServiceLocationCodeV2().getLocationCode();
				BigDecimal taxes = STE.getFICACalc().add(STE.getMedicareCalc()).add(STE.getFederalCalc()).add(STE.getEICCalc()).asBigDecimal().add(STE.getStateCalc(locationCode).asBigDecimal()).add(STE.getCountyCalc(locationCode).asBigDecimal());
				employeePayroll.setTaxes(taxes);
				System.out.println("*** Taxes :"+taxes);
				BigDecimal netPay = BigDecimal.valueOf(amount).subtract(taxes);
				System.out.println("*** Net Pay :"+netPay);
				employeePayroll.setNetPay(netPay);
				if (pm.getEmployee() != null) {
					employeePayroll.setEmployee(pm.getEmployee());
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
					EmployerPayrollTax employerTaxes = new EmployerPayrollTax();
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
		EmployeePayrollTax taxes = new EmployeePayrollTax();
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
		taxes.setLocationCode(locationCode); // locationCode
		taxes.setUsEmpPayrollTaxUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		taxes.setEarnedIncCredit(STE.getEICCalc().asBigDecimal());
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
		// Weekly Bi-Weekly Monthly
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
	public List<USPayrollDTO> getPayrollData(GenerateUSPayrollDTO generateUSPayrollDTO, OrgLocations orgLocation, User loginUser)
	{
		PaysheetFrequency paysheetFrequency = generateUSPayrollDao.getPayrollData(generateUSPayrollDTO, orgLocation, loginUser);
		List<USPayrollDTO> list = null;
		try {
			if (paysheetFrequency != null && paysheetFrequency.getEmployeepayroll() != null) {
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
					payrollDTO.setOvertimeHours(pay.getOverTimeHours());
					list.add(payrollDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
}
