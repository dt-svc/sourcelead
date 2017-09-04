package com.bis.sourcelead.payroll.service;

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

	ResponseList<HrDeductions> getHrDeductionsListByOrgLocationid(OrgLocations orgLocation, HrDeductionsDTO hrDeductionsDTO);

	List<HrDeductionsDetails> getHrDeductionsDetailsbyId(long hrDeductionId);

	List<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

	HrDeductions getDeductionsbyId(Long deductionId);

	HrDeductions saveHrDeductions(HrDeductions hrDeductions) throws Exception;

}
