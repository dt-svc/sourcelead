package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;

public interface AssociateDeductionDao {

	AssociateDeduction saveAssociateDeduction(AssociateDeduction associateDeduction);

	List<AssociateDeduction> findAllDeductionSummaryByUserId(Long userId, OrgLocations orgLocation);

	AssociateDeduction findAssociateDeductionById(Long associateDeductionId);
}
