package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Requirement;

public interface RecommendationsService {

	List<Candidate> getAllCandidatesByLocation(Long id)throws Exception;

	Requirement getRequirementByRequirementId(Long requirementId) throws Exception;

	List<Candidate> getRecommendationsWithScore(List<Candidate> candidateList, Requirement requirement)throws Exception;

}
