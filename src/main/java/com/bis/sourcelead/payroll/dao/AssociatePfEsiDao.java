package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;

public interface AssociatePfEsiDao {

	AssociatePfEsiDetails saveAssociatePfEsi(AssociatePfEsiDetails associatePfEsiDetails);

	AssociatePfEsiDetails findAssociatePfEsiByUserId(Long userId, OrgLocations orgLocation);

	AssociatePfEsiDetails findAssociatePfEsiId(Long id);
}
