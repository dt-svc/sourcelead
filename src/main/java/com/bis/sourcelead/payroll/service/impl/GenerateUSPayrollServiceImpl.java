package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.internal.resources.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.GenerateUSPayrollDao;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.symmetry.HelloPayrollWorldBis;
import com.bis.sourcelead.payroll.symmetry.SymmetryPaypal;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
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
	
	 
	@Override
	public ResponseList<USPayrollDTO> generateUSPayroll(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO) throws Exception
	{
		String dates[] = generateUSPayrollDTO.getPayPeriod().split(",");
		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		ResponseList<USPayrollDTO> usPayrollDTOResponse = new ResponseList<>();
		List<USPayrollDTO> list = new ArrayList<>();
		ResponseList<ProjectMembers> response = projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(orgLocation.getId(), actualStartDate, actualEndDate, 1, 10);
		if (response != null && response.getData() != null && !response.getData().isEmpty()) {
			int i = 1;
			for (ProjectMembers pm : response.getData()) {
				System.out.println("**********************"+pm.getProjectMemberCandidteId().getConsultantUser().getId());
				Map<String, Float> hoursMap = submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(pm.getId(), orgLocation.getId(), actualStartDate, actualEndDate);
				USPayrollDTO usPayrollDTO = new USPayrollDTO();
				StringBuffer sb = new StringBuffer();
				float billableHours = hoursMap.get("BILLABLE_HOURS");
				float nonBillableHours = hoursMap.get("NONBILLABLE_HOURS");
				float totalHours = hoursMap.get("TOTAL_HOURS");
				if (StringUtils.isNoneBlank(pm.getProjectMemberCandidteId().getLastName())) {
					sb.append(pm.getProjectMemberCandidteId().getFirstName()).append(" ").append(pm.getProjectMemberCandidteId().getLastName());
				}
				else {
					sb.append(pm.getProjectMemberCandidteId().getFirstName());
				}
				usPayrollDTO.setEmpName(sb.toString());
				usPayrollDTO.setPayRate(BigDecimal.valueOf(pm.getPayRate()));
				double amount = getTotalAmount(totalHours,pm.getPayRate());
				
				User user = userService.getUserbyConsultantUserId(pm.getProjectMemberCandidteId().getConsultantUser().getId());
				int invoiceFrequency = getProjectInvoiceFrequency(pm.getProjects().getInvoiceFrequency());
				AssociateTaxDTO associateTaxDTO = associateTaxService.findAssociateTaxSummaryByUserId(user.getId(), orgLocation);
//				Projects projects = pm.getProjects().getInvoiceFrequency();
				HelloPayrollWorldBis helloPayrollWorld = new HelloPayrollWorldBis();
//				SymmetryPaypal client = new SymmetryPaypal();
//				String locationCode = "18-097-452890";
//				String locationCode = "04-013-12233";       // Arizona Location Code  
//				client.hello(locationCode);
				usPayrollDTO.setRegularHrsTotalAmt(BigDecimal.valueOf(amount));
				usPayrollDTO.setPreTaxDeduction(BigDecimal.valueOf(0.00));
				usPayrollDTO.setPostTaxDeduction(BigDecimal.valueOf(0.00));
				usPayrollDTO.setGrossPay(BigDecimal.valueOf(amount));
				STEPayrollCalculator STE = helloPayrollWorld.runUSPayroll(user,totalHours,amount,invoiceFrequency,associateTaxDTO,STEhome,jniHome);
				
				System.out.println("FICA Withholding:" + STE.getFICACalc());
				System.out.println("Medicare Withholding: " + STE.getMedicareCalc());
				System.out.println("Federal Withholding: " + STE.getFederalCalc());
				System.out.println("Earned Inc. Credit: " + STE.getEICCalc());
//				System.out.println("State Withholding: " + STE.getStateCalc(locationCode));
//				System.out.println("County Withholding:" + STE.getCountyCalc(locationCode));
				usPayrollDTO.setTaxes(BigDecimal.valueOf(0.00));
				usPayrollDTO.setNetPay(BigDecimal.valueOf(0.00));
				if (pm.getEmployee() != null) {
					usPayrollDTO.setEmpId(pm.getEmployee().getEmployeeId());
				}
				else {
					usPayrollDTO.setEmpId("--");
				}
				usPayrollDTO.setRegularHours(totalHours);
				list.add(usPayrollDTO);
			}
			usPayrollDTOResponse.setData(list);
			usPayrollDTOResponse.setRecordsFiltered(list.size());
			usPayrollDTOResponse.setRecordsTotal(list.size());
			usPayrollDTOResponse.setResponseMessage("success");
		}
		return usPayrollDTOResponse;
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
	
	
}
