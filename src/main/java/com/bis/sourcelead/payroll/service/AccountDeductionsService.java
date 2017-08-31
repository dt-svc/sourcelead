package com.bis.sourcelead.payroll.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.entity.PayrollBalance;

public interface AccountDeductionsService {

	Integer saveAccountDeductions(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception;

	List<AccountDeductions> getAccountDeductionsListByOrgLocationId(OrgLocations orgLocation) throws Exception;

	List<AccountDeductionsDetails> getDeductionsDetailsbyId(Long deductionId);

	AccountDeductions getDeductionsbyId(Long deductionId);

	AccountDeductions saveAccountDeductions(AccountDeductions accountDeductions) throws Exception;

	List<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

	PayrollBalance getBalance(Long empId, OrgLocations orgLocations)throws Exception;

	int getAccountDeductionsVersionByEmpId(String EmpId, Long locationId);
}
