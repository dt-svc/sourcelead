package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;

public interface AssociatePfEsiDao {

	AssociatePfEsiDetails saveAssociatePfEsi(AssociatePfEsiDetails associatePfEsiDetails);

	AssociatePfEsiDetails findAssociatePfEsiByCandidateId(Long candidateId, OrgLocations orgLocation);

	AssociatePfEsiDetails findAssociatePfEsiId(Long id);
}
