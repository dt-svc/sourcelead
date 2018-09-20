package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateSalaryService {

	Response<AssociateSalaryDTO> addAssociateSal(OrgLocations orgLocation, AssociateSalaryDTO associateSalaryDTO, User loginUser);

	AssociateSalaryDTO findAssociateSalaryByCandidateId(Long candidateId, OrgLocations orgLocation);

	AssociatePayItem findPayItemByPayItemId(Long payItemId);

	AssociatePayItem saveUpdatedPayItem(AssociatePayItem associatePayItem);

	List<AssociateSalary> getAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId);

	List<AssociatePayItemDTO> findAssociatePayItemByPayrollId(Long payrollId);

	List<AssociateSalaryInd> getIndAssociateSalaryListByGroupId(OrgLocations orgLocation, long parseLong);
	
}
