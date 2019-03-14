package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;

public interface AssociateTaxDao {

	AssociateTax saveAssociateTax(AssociateTax associateTax);

	AssociateTax findAssociateTaxSummaryByCandidateId(Long candidateId, OrgLocations orgLocation);

	AssociateTax findAssociateTaxById(long associateTaxId);

	AssociateTaxDedContriView findUserTaxDeatails(Long candidateId, OrgLocations orgLocation);

	AssociateTax taxDetailsByOrglocationIdAndCandidateId(Long candidateId, Long orgLocation);

}
