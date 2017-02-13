package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementRateSubmissionNegotiationDao;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class RequirementRateSubmissionNegotiationServiceImpl implements RequirementRateSubmissionNegotiationService{

	@Autowired
	RequirementRateSubmissionNegotiationDao requirementRateSubmissionNegotiationDao;
	
	@Autowired
	 private CommonUtil commonUtil;
	
	/*@Override
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation) {
		requirementRateSubmissionNegotiationDao.saveRateNegotiation(rateNegotiation);		
	}*/

	@Override
	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionById(Long ratesubmissionId) {
		return requirementRateSubmissionNegotiationDao.getRequirementRateSubmissionById(ratesubmissionId);
	}

	@Override
	public List<RequirementRateSubmissionNegotiation> getRequirementRateSubmissionByOrgLocationsId(Long orgLocationsId)
			throws Exception {
		return requirementRateSubmissionNegotiationDao.getRequirementRateSubmissionByOrgLocationsId(orgLocationsId);
	}
	
	
	@Override
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation) {
		rateNegotiation.setUpdatedDate(commonUtil.currentDate());
		requirementRateSubmissionNegotiationDao.saveRateNegotiation(rateNegotiation);		
	}
	
	@Override
	public RequirementRateSubmissionNegotiation getLatestRequirementRateSubmissionNegotiationBySubmissionId(Long submissionId) {
		return requirementRateSubmissionNegotiationDao.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
	}
	
	@Override
	public List<RequirementRateSubmissionNegotiation> getAllRateHistoryBySubmissionIdAndCandidateId(Long submissionId, Long candidateId) throws Exception {
		return requirementRateSubmissionNegotiationDao.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, candidateId) ;
	}

}
