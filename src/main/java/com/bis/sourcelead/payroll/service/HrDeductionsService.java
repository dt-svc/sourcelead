package com.bis.sourcelead.payroll.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.HrDeductionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;

public interface HrDeductionsService {

	Integer saveHRDeductions(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception;

	ResponseList<HrDeductions> getHrDeductionsListByOrgLocationid(OrgLocations orgLocation, HrDeductionsDTO hrDeductionsDTO) throws Exception;

	List<HrDeductionsDetails> getHrDeductionsDetailsbyId(long hrDeductionId);

	List<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;
	
	/*
	 *this service is used to get HrDeductionss details of particular employee on particular date  
	 */
	HrDeductions getHrDeductionsByEmpIdandDuration(String EmpId,Long locationId,Date startDate, Date endDate);
	
	int getHrDeductionsVersionByLocationId(Long locationId);

	HrDeductions getDeductionsbyId(Long deductionId);

	HrDeductions saveHrDeductions(HrDeductions hrDeductions) throws Exception;

	ResponseList<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocation, HrDeductionsDTO hrDeductionsDTO);

}
