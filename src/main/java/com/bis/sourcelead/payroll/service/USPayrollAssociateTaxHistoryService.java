package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.payroll.dto.USPayrollAssociateTaxHistoryDTO;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.entity.USPayrollAssociateTaxHistory;

public interface USPayrollAssociateTaxHistoryService {

	USPayrollAssociateTaxHistory storeUSPayrollAssociateTaxHistory(AssociateTax savedAssociateTax, AssociateTax previousAssociateTax);

	List<USPayrollAssociateTaxHistoryDTO> getAssociateTaxHistoryByCandidateId(Long candidateId, String dateFormatString);

}
