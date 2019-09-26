package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CandidateTimelineActivity;

public interface CandidateTimelineActivityDao {
	public List<CandidateTimelineActivity> getCandidateTimelineActivities(Long candidateId, Long orgLocationId) throws Exception;
	
	public CandidateTimelineActivity storeCandidateTimelineActivity(CandidateTimelineActivity candidateTimelineActivity) throws Exception;
	
	public List<CandidateTimelineActivity> getCandidateGlobalTimelineActivities(Long candidateId, Long orgLocationId) throws Exception;
}