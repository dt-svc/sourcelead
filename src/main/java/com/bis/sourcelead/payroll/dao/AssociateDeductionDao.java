package com.bis.sourcelead.payroll.dao;

import java.util.List;

import org.hibernate.Query;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;

public interface AssociateDeductionDao {

	AssociateDeduction saveAssociateDeduction(AssociateDeduction associateDeduction);

	List<AssociateDeduction> findAllDeductionSummaryByCandidateId(Long candidateId, OrgLocations orgLocation);

	AssociateDeduction findAssociateDeductionById(Long associateDeductionId);

	AssociateDeduction getValidateDeduction(Long deductionTypeId, OrgLocations orgLocation, Long candidateId);

	AssociateDeduction getAsociateDedByUserIdDeductionId(long deductionId, Long candidateId);

	void deleteAssociateDeductionByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId);

}
