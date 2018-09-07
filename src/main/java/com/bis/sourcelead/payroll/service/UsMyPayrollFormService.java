package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UsMyPayrollFormsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.UsMyPayrollForms;

public interface UsMyPayrollFormService {

	UsMyPayrollForms saveUsMyPayrollForm(UsMyPayrollFormsDTO usMyPayrollFormsDTO, OrgLocations orgLocation, User user);

	ResponseList<UsMyPayrollForms> getUsMyPayrollFormByLocationIdAndCandidateId(Long id, Long candidateId);

	Response<String> deleteUsMyPayrollFormByUUID(UsMyPayrollFormsDTO usMyPayrollFormsDTO);
	
	UsMyPayrollForms getUsMyPayrollFormByUUID(String uuid);

}
