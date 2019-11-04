package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateTimelineActivityService {
	public List<CandidateTimelineActivity> getCandidateTimelineActivities(Long candidateId, Long orgLocationId) throws Exception;
	
	HashSet<CandidateTimelineActivity> getCandidateTimelineActivityDetails(User user, OrgLocations orgLocations, Long candidateId, String actionOn, String actionDesc, String actionSummary, String actionIcon) throws Exception;
}
