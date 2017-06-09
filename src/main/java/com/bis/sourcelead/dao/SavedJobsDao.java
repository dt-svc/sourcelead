package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SavedJobs;

public interface SavedJobsDao {
	
	SavedJobs addSavedJobs(SavedJobs savedJobs) throws Exception;
	
	SavedJobs getSavedJobsByrequirementIdAndUserId(Long requirementId,Long userId) throws Exception;
	
	List<SavedJobs> getSavedJobsListByUserId(Long userId) throws Exception;
	
	SavedJobs getSavedJobsByUUID(String savedJobUUID) throws Exception;
	
	public void deleteSavedJobs(SavedJobs savedJobs) throws Exception;
}
