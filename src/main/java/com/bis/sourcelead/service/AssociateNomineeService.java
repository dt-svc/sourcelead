package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.AssociateNomineeDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateNominee;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateNomineeService {
	
	Response<AssociateNomineeDTO> saveAssociateNomineeDetails(AssociateNomineeDTO associateNomineeDTO,OrgLocations orgLocation, Candidate candidate, User user);

	ResponseList<AssociateNomineeDTO> getAssociateNomineeDetails(AssociateNomineeDTO associateNomineeDTO,OrgLocations orgLocation, Candidate candidate, User user);

	AssociateNominee getNomineeById(Long id);

}
