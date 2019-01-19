package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.AssociateSalaryPayheadInd;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.PayrollPayheadSalarySetup;


public interface PayrollPayheadDao {
	
	PayrollPayhead savePayrollPayhead(PayrollPayhead payrollPayhead) throws Exception;
	
	List<PayrollPayhead> getPayrollPayheadList(OrgLocations orgLocations) throws Exception;
	
	List<PayrollPayhead> getPayrollPayheadListByPayheadComponentType(OrgLocations orgLocations, String component) throws Exception;
	
	ResponseList<PayrollPayheadDTO>  getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO,OrgLocations orgLocations) throws Exception;
	
	PayrollPayhead getPayrollPayheadByUUID(String uuid) throws Exception;
	
	Boolean findPayrollPayheadByNameAndLocationId(PayrollPayheadDTO payrollPayheadDTO) throws Exception;
	
	PayrollPayheadSalarySetup savePayrollPayheadSalarySetup(PayrollPayheadSalarySetup payrollPayheadSalarySetup) throws Exception;
	
	/*ResponseList<PayrollPayheadSalarySetupDTO> getPayrollPayheadSalarySetupListByPayheadUuidAndLocationId(String uuid, OrgLocations orgLocations) throws Exception;*/
	
	AssociateSalaryInd saveAssociateSalaryInd(AssociateSalaryInd associateSalaryInd) throws Exception;
	
	AssociateSalaryInd getAssociateSalaryIndByUUID(String uuid) throws Exception;
	
	AssociateSalaryInd getAssociateSalaryIndByCandidateId(Long id) throws Exception;
	
	AssociateSalaryPayheadInd getAssociateSalaryPayheadIndById(Long id) throws Exception;
	
	PayrollPayhead getPayrollPayheadByShortCodeAndOrglocation(String shortCode, Long orgLocationId);
}
