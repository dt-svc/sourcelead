package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Requirement;

public interface RecommendationsDao {

	Requirement getRequirementByRequirementId(Long requirementId) throws Exception;

	List<Candidate> getAllCandidatesByLocation(Long orgLocationId)throws Exception;

}
