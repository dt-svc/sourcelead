package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SavedJobs;

public interface SavedJobsDao {
	
	SavedJobs addSavedJobs(SavedJobs savedJobs) throws Exception;
	
	SavedJobs getSavedJobsByrequirementIdAndUserId(Long requirementId,Long userId) throws Exception;
	
	List<SavedJobs> getSavedJobsListByUserId(Long userId,String jobTitleSearch,String jobLocationSearch,Integer offset,Integer maxResults,String postedOn) throws Exception;
	
	SavedJobs getSavedJobsByUUID(String savedJobUUID) throws Exception;
	
	public void deleteSavedJobs(SavedJobs savedJobs) throws Exception;
}
