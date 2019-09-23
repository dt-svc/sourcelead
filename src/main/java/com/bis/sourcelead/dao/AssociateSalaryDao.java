package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SalaryDTO;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryHistory;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;

public interface AssociateSalaryDao {

	AssociateSalary addAssociateSal(AssociateSalary associateSalary);

	AssociateSalary findAssociateSalaryByCandidateId(Long candidateId);

	AssociateSalary findAssociateSalaryById(String id);

	AssociateSalaryHistory updaetAssociateSalHis(AssociateSalaryHistory associateSalHis);

	AssociatePayItem findPayItemByPayItemId(Long payItemId);

	AssociatePayItem saveUpdatedPayItem(AssociatePayItem associatePayItem);

	List<AssociateSalary> getAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId, String payType);

	List<AssociatePayItemDTO> findAssociatePayItemByPayrollId(Long payrollId);

	AssociateSalary getAssociateSalaryByCandidateIdAndOrgLocationId(Long candidateId, Long locationId);

	ResponseList<AssociateSalary> getAssociateSalaryListByLocationIdAndGroupId(OrgLocations orgLocation, Long groupId, CandidateDTO dto);
	
	ResponseList<AssociateSalaryInd> getAssociateSalaryIndListByLocationIdAndGroupId(OrgLocations orgLocation, Long groupId, CandidateDTO dto);

	List<AssociateSalaryInd> getIndAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId);

	SalaryDTO getSalaryByGroupId(OrgLocations orgLocation, Long candidateId, Date freqStartDate, Date freqEndDate);
	
    List<AssociateSalaryInd> getIndAssociateSalaryListByGroupIdForInitiatePayRoll(OrgLocations orgLocation, long groupId,String payType,Date fqStartDate,Date fqEndDate);
	
	List<AssociateSalaryInd> getIndAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId,String payType);

	AssociateSalary getAssociateSalaryBySalaryId(Long salaryId);


}
