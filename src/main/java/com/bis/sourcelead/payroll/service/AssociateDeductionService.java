package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;

public interface AssociateDeductionService {

	Response<AssociateDeductionDTO> saveAssociateDeduction(AssociateDeductionDTO associateDeductionDTO, OrgLocations orgLocation, User loginUser);

	PayrollDeductionDTO getDeductionAmount(long deductionTypeId);

	ResponseList<DeductionDTO> findAllDeductionSummaryByCandidateId(Long candidateId, OrgLocations orgLocation);

	AssociateDeduction findAssociateDeductionById(Long associateDeductionId);

	AssociateDeduction getValidateDeduction(Long deductionTypeId, OrgLocations orgLocation, Long candidateId);

	AssociateDeduction getAsociateDedByUserIdDeductionId(long deductionId, Long candidateId);

}
