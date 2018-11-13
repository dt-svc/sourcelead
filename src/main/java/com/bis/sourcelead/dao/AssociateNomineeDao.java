package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociateNominee;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;

public interface AssociateNomineeDao {
	
	AssociateNominee saveAssociateNomineeDetails(AssociateNominee associateNominee);

	List<AssociateNominee> getAssociateNomineeDetails(Candidate candidate, OrgLocations orgLocation);

	AssociateNominee getNomineeById(Long id);

}
