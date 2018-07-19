package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateSalaryService {

	Response<AssociateSalaryDTO> addAssociateSal(OrgLocations orgLocation, AssociateSalaryDTO associateSalaryDTO, User loginUser);

	AssociateSalaryDTO findAssociateSalaryByUserId(Long userId, OrgLocations orgLocation);

	AssociatePayItem findPayItemByPayItemId(Long payItemId);

	AssociatePayItem saveUpdatedPayItem(AssociatePayItem associatePayItem);
	
}
