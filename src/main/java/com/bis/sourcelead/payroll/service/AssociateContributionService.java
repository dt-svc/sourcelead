package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateContributionDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;

public interface AssociateContributionService {

	Response<AssociateContributionDTO> saveAssociateContribution(AssociateContributionDTO associateContributionDTO, OrgLocations orgLocation, User loginUser);

	ResponseList<ContributionDTO> findAllContributionSummaryByUserId(Long userId, OrgLocations orgLocation);
}
