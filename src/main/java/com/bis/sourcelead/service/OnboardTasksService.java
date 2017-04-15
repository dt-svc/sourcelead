package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OnboardTasks;

public interface OnboardTasksService {

	OnboardTasks saveOnboardTasks(OnboardTasks onboardTasks) throws Exception;
	
	List<OnboardTasks> getOnboardTasksByTemplateId(Long templateId) throws Exception;
	
	Integer getOnBoardTasksCountByCandidateOnBoardingId(Long candidateOnBoardingId) throws Exception;
	
	List<OnboardTasks> getOnBoardTasksListByCandidateOnBoardingId(Long candidateOnBoardingId) throws Exception;
	
	List<OnboardTasks> getOnboardTasksByTemplateIdList(List<Long> templateId) throws Exception;
	
	OnboardTasks getOnboardTasksByTaskId(Long Id) throws Exception;
}
