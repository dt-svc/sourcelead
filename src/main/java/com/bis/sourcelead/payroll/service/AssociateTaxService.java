package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;

public interface AssociateTaxService {

	Response<AssociateTaxDTO> saveAssociateTax(AssociateTaxDTO associateTaxDTO, OrgLocations orgLocation, User loginUser);

	Response<AssociateTaxDTO> isLocalTaxYes(AssociateTaxDTO associateTaxDTO, OrgLocations orgLocation, User loginUser);

	AssociateTaxDTO findAssociateTaxSummaryByUserId(Long userId, OrgLocations orgLocation);

}
