package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.payroll.entity.UsMyPayrollForms;

public interface UsMyPayrollFormDao {
	
	UsMyPayrollForms saveUsMyPayrollForm(UsMyPayrollForms myPayrollForm) throws Exception;

	List<UsMyPayrollForms> getUsMyPayrollFormByLocationIdAndCandidateId(Long locationId, Long candidateId);

	UsMyPayrollForms getUsMyPayrollFormByUUID(String uuid);
}
