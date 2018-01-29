package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.JobShareCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface JobShareService {

//	JobShare saveJobShare(JobShare jobShare) throws Exception;

	JobShareCandidate saveJobShareCandidate(JobShareCandidate jobShareCandidate) throws Exception;
	
	public void jobShareEmail(User loginUser, OrgLocations orgLocation, String requirement, Candidate candidate)throws Exception;

}
