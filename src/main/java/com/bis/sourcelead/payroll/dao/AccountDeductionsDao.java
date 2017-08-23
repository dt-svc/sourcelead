package com.bis.sourcelead.payroll.dao;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;

public interface AccountDeductionsDao {

	AccountDeductions saveAccountDeductions(AccountDeductions payCycle)throws Exception;

	List<AccountDeductions> getAccountDeductionsListByOrgLocationId(OrgLocations orgLocation) throws Exception;

	List<AccountDeductionsDetails> getDeductionsDetailsbyId(Long deductionId);

	AccountDeductions getDeductionsbyId(Long deductionId);

	List<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

}
