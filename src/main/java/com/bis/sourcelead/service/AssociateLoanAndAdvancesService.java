package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateLoanAndAdvancesService {

	AssociateLoanAndAdvances saveAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances) throws Exception;
	
	AssociateLoanAndAdvances getAssociateLoanAndAdvancesById(Long id) throws Exception ;
	
	AssociateLoanAndAdvances storeAssociateLoanAndAdvances(AssociateLoanAndAdvancesDTO associateLoanAndAdvancesDTO,OrgLocations orgLocations,User user) throws Exception;
}
