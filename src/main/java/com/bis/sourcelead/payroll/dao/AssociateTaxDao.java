package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AssociateTax;

public interface AssociateTaxDao {

	AssociateTax saveAssociateTax(AssociateTax associateTax);

	AssociateTax findAssociateTaxSummaryByUserId(Long userId, OrgLocations orgLocation);

	AssociateTax findAssociateTaxById(long associateTaxId);
}
