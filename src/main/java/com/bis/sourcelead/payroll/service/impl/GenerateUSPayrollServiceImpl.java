package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.payroll.dao.GenerateUSPayrollDao;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
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
	
}
