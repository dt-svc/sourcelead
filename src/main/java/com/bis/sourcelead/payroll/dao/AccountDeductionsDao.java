package com.bis.sourcelead.payroll.dao;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.AccountDeductionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.entity.HrDeductions;

public interface AccountDeductionsDao {

	AccountDeductions saveAccountDeductions(AccountDeductions payCycle)throws Exception;

	ResponseList<AccountDeductions> getAccountDeductionsListByOrgLocationId(OrgLocations orgLocation, AccountDeductionsDTO accountDeductionsDTO) throws Exception;

	List<AccountDeductionsDetails> getDeductionsDetailsbyId(Long deductionId);

	AccountDeductions getDeductionsbyId(Long deductionId);

	List<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

	int getAccountDeductionsVersionByLocationId(Long locationId);

	ResponseList<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocation,
			AccountDeductionsDTO accountDeductionsDTO);

}
