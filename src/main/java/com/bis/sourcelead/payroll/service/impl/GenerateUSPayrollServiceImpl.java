package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.internal.resources.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.bis.sourcelead.payroll.symmetry.HelloPayrollWorld;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

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
	
	@Override
	public ResponseList<USPayrollDTO> generateUSPayroll(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO) throws Exception
	{
		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), generateUSPayrollDTO.getPayrollStartDate().replaceAll("/", "-"));
		Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), generateUSPayrollDTO.getPayrollEndDate().replaceAll("/", "-"));
		ResponseList<USPayrollDTO> usPayrollDTOResponse = new ResponseList<>();
		List<USPayrollDTO> list = new ArrayList<>();
		ResponseList<ProjectMembers> response = projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(orgLocation.getId(), actualStartDate, actualEndDate, 1, 10);
		if (response != null && response.getData() != null && !response.getData().isEmpty()) {
			int i = 1;
			for (ProjectMembers pm : response.getData()) {
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
				double amount = getTotalAmount(totalHours,pm.getPayRate());
				
				User user = userService.getUserbyConsultantUserId(pm.getProjectMemberCandidteId().getConsultantUser().getId());
				int invoiceFrequency = getProjectInvoiceFrequency(pm.getProjects().getInvoiceFrequency());
				AssociateTaxDTO associateTaxDTO = associateTaxService.findAssociateTaxSummaryByUserId(user.getId(), orgLocation);
//				Projects projects = pm.getProjects().getInvoiceFrequency();
				HelloPayrollWorld helloPayrollWorld = new HelloPayrollWorld();
				helloPayrollWorld.runUSPayroll(user,totalHours,amount,invoiceFrequency,associateTaxDTO);
//				helloPayrollWorld.hello();
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
		String[] rate = pRate.split(":");
		double hr = Double.parseDouble(rate[0]);
		double min = Double.parseDouble(rate[1]);
		double hrsToMin = hr * 60;
		double totalMin = hrsToMin + min;
		double perMinRate = payRate / 60;
		return totalMin * perMinRate;
	}
}
