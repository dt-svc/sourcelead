package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.AssociateSalaryIndDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.PayrollPayheadSalarySetup;
import com.bis.sourcelead.entity.User;

public interface PayrollPayheadService {

	PayrollPayhead savePayrollPayhead(PayrollPayheadDTO payrollPayheadDTO, User user, OrgLocations orgLocations) throws Exception;
	
	ResponseList<PayrollPayheadDTO> getPayrollPayheadList(OrgLocations orgLocations) throws Exception;
	
	ResponseList<PayrollPayheadDTO> getPayrollPayheadListByPayheadComponentType(OrgLocations orgLocations, String component) throws Exception;
	
	ResponseList<PayrollPayheadDTO> getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO, OrgLocations orgLocations) throws Exception;
	
	PayrollPayhead getPayrollPayheadByUUID(String uuid) throws Exception;
	
	Boolean findPayrollPayheadByNameAndLocationId(PayrollPayheadDTO payrollPayheadDTO) throws Exception;
	
	PayrollPayheadSalarySetup savePayrollPayheadSalarySetup(PayrollPayheadSalarySetup payrollPayheadSalarySetup) throws Exception;
	
	/*ResponseList<PayrollPayheadSalarySetupDTO> getPayrollPayheadSalarySetupListByPayheadUuidAndLocationId(String uuid, OrgLocations orgLocations) throws Exception;*/
	
	AssociateSalaryInd saveAssociateSalaryInd(AssociateSalaryIndDTO associateSalaryIndDTO, User user, OrgLocations orgLocations) throws Exception;
	
	AssociateSalaryInd getAssociateSalaryIndByUUID(String uuid) throws Exception;
	
	AssociateSalaryInd getAssociateSalaryIndByCandidateId(Long id) throws Exception;
}
