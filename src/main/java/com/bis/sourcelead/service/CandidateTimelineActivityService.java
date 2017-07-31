package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.CandidateTimelineActivity;

public interface CandidateTimelineActivityService {
	public List<CandidateTimelineActivity> getCandidateTimelineActivities(Long candidateId, Long orgLocationId) throws Exception;
}
