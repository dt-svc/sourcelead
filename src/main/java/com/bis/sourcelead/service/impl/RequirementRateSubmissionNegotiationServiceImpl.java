package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementRateSubmissionNegotiationDao;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;

@Service
public class RequirementRateSubmissionNegotiationServiceImpl implements RequirementRateSubmissionNegotiationService{

	@Autowired
	RequirementRateSubmissionNegotiationDao requirementRateSubmissionNegotiationDao;
	
	@Override
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation) {
		requirementRateSubmissionNegotiationDao.saveRateNegotiation(rateNegotiation);		
	}

	@Override
	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionById(Long ratesubmissionId) {
		return requirementRateSubmissionNegotiationDao.getRequirementRateSubmissionById(ratesubmissionId);
	}

	@Override
	public List<RequirementRateSubmissionNegotiation> getRequirementRateSubmissionByOrgLocationsId(Long orgLocationsId)
			throws Exception {
		return requirementRateSubmissionNegotiationDao.getRequirementRateSubmissionByOrgLocationsId(orgLocationsId);
	}

}
