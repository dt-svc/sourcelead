package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.JobShareCandidate;

public interface JobShareDao {
	
	/**
	 * @author Laxman
	 * @param department
	 * @return
	 * @throws Exception
	 */
//	JobShare saveJobShare(JobShare jobShare) throws Exception;

	JobShareCandidate saveJobShareCandidate(JobShareCandidate jobShareCandidate) throws Exception;
}
