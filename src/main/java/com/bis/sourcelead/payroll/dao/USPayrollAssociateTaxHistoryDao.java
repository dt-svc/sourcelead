package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.payroll.entity.USPayrollAssociateTaxHistory;

public interface USPayrollAssociateTaxHistoryDao {

	USPayrollAssociateTaxHistory storeUSPayrollAssociateTaxHistory(USPayrollAssociateTaxHistory associateTax);

	List<USPayrollAssociateTaxHistory> getAssociateTaxHistoryByCandidateId(Long candidateId);

}
