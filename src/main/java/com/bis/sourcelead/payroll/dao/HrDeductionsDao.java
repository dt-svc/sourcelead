package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;

public interface HrDeductionsDao {

	HrDeductions saveHRDeductions(HrDeductions hrDeductions)throws Exception;

	List<HrDeductions> getHrDeductionsListByOrgLocationid(OrgLocations orgLocation);

	List<HrDeductionsDetails> getHrDeductionsDetailsbyId(long hrDeductionId);

	List<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

}
