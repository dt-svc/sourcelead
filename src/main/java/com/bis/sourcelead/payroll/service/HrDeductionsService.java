package com.bis.sourcelead.payroll.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;

public interface HrDeductionsService {

	Integer saveHRDeductions(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception;

	List<HrDeductions> getHrDeductionsListByOrgLocationid(OrgLocations orgLocation);

	List<HrDeductionsDetails> getHrDeductionsDetailsbyId(long hrDeductionId);

	List<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;
	
	/*
	 *this service is used to get HrDeductionss details of particular employee on particular date  
	 */
	HrDeductions getHrDeductionsByEmpIdandDuration(String EmpId,Long locationId,Date startDate, Date endDate);
	
	int getHrDeductionsVersionByEmpId(String EmpId,Long locationId);

}
