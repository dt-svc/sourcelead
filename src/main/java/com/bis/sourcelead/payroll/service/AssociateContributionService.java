package com.bis.sourcelead.payroll.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateContributionDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.entity.AssociateContribution;

public interface AssociateContributionService {

	Response<AssociateContributionDTO> saveAssociateContribution(AssociateContributionDTO associateContributionDTO, OrgLocations orgLocation, User loginUser);

	ResponseList<ContributionDTO> findAllContributionSummaryByCandidateId(Long candidate, OrgLocations orgLocation);

	AssociateContribution findAssociateContributionById(Long associateContributionId);

	AssociateContribution getValidateContribution(Long candidateId,Long contriTypId, OrgLocations orgLocation);

	AssociateContribution findContributionByUserIdContributionId(Long userId, Long contributionId);

	String saveAssociateDedAndContriData(MultipartFile multipartFile, OrgLocations orgLocations) throws Exception;
	
}
