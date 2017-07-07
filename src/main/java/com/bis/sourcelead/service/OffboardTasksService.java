package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OffboardTasks;
import com.bis.sourcelead.entity.OnboardTasks;

public interface OffboardTasksService {
	
	OffboardTasks saveOffboardTasks(OffboardTasks offboardTasks) throws Exception;
	
	List<OffboardTasks> getOffboardTasksByTemplateId(Long templateId) throws Exception;
	
	Integer getOffBoardTasksCountByCandidateOffBoardingId(Long candidateOffBoardingId) throws Exception;
	
	List<OffboardTasks> getOffBoardTasksListByCandidateOffBoardingId(Long candidateOffBoardingId) throws Exception;
	
	List<OffboardTasks> getOffboardTasksByTemplateIdList(List<Long> templateId) throws Exception;
	
	OffboardTasks getOffboardTasksByTaskId(Long Id) throws Exception;
	
	List<OffboardTasks> getOffBoardTasksListAssignedToCandidateByCandidateOffBoardingId(Long candidateOffBoardingId) throws Exception;
	
	List<OffboardTasks> getOffBoardAddedTasksListByCandidateOffBoardingId(Long candidateOffBoardingId) throws Exception;

}
