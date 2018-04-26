package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;

public interface AssociateDeductionService {

	Response<AssociateDeductionDTO> saveAssociateDeduction(AssociateDeductionDTO associateDeductionDTO, OrgLocations orgLocation, User loginUser);

	PayrollDeductionDTO getDeductionAmount(long deductionTypeId);
}
