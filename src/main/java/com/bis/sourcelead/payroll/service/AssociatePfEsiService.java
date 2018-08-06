package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;

public interface AssociatePfEsiService {

	Response<AssociatePfEsiDetailsDTO> saveAssociatePfEsi(AssociatePfEsiDetailsDTO associatePfEsiDetailsDTO, OrgLocations orgLocation, User loginUser);

	AssociatePfEsiDetails findAssociatePfEsiByUserId(Long userId, OrgLocations orgLocation);
}
