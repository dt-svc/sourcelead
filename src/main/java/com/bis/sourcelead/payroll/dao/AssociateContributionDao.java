package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AssociateContribution;

public interface AssociateContributionDao {

	AssociateContribution saveAssociateContribution(AssociateContribution associateContribution);

	List<AssociateContribution> findAllContributionSummaryByCandidateId(Long candidateId, OrgLocations orgLocation);

	AssociateContribution findAssociateContributionById(Long assContriId);

	AssociateContribution getValidateContribution(Long candidateId,Long contriTypId, OrgLocations orgLocation);

	AssociateContribution findContributionByUserIdContributionId(Long userId, Long contributionId);

	void deleteAssociateContributionByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId);

}
