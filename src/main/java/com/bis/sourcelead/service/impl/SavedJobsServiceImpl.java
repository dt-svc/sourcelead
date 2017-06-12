package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SavedJobsDao;
import com.bis.sourcelead.entity.SavedJobs;
import com.bis.sourcelead.service.SavedJobsService;

@Service
public class SavedJobsServiceImpl implements SavedJobsService{
	
	@Autowired
	SavedJobsDao savedJobsDao;

	@Override
	public SavedJobs addSavedJobs(SavedJobs savedJobs) throws Exception
	{
		return savedJobsDao.addSavedJobs(savedJobs);
	}

	@Override
	public SavedJobs getSavedJobsByrequirementIdAndUserId(Long requirementId, Long userId) throws Exception
	{
		return savedJobsDao.getSavedJobsByrequirementIdAndUserId(requirementId, userId);
	}

	@Override
	public List<SavedJobs> getSavedJobsListByUserId(Long userId,String jobTitleSearch,String jobLocationSearch,Integer offset,Integer maxResults,String postedOn) throws Exception
	{
		return savedJobsDao.getSavedJobsListByUserId(userId,jobTitleSearch,jobLocationSearch,offset,maxResults,postedOn);
	}

	@Override
	public SavedJobs getSavedJobsByUUID(String savedJobUUID) throws Exception
	{
		return savedJobsDao.getSavedJobsByUUID(savedJobUUID);
	}

	@Override
	public void deleteSavedJobs(SavedJobs savedJobs) throws Exception
	{
		savedJobsDao.deleteSavedJobs(savedJobs);
		
	}
	
}
