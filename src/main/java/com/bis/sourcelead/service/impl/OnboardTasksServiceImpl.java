package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OnboardTasksDao;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.service.OnboardTasksService;

@Service
public class OnboardTasksServiceImpl implements OnboardTasksService{
	
	@Autowired
	OnboardTasksDao onboardTasksDao;

	@Override
	public OnboardTasks saveOnboardTasks(OnboardTasks onboardTasks) throws Exception {
		return onboardTasksDao.saveOnboardTasks(onboardTasks);
	}

	@Override
	public List<OnboardTasks> getOnboardTasksByTemplateId(Long templateId) throws Exception {
		return onboardTasksDao.getOnboardTasksByTemplateId(templateId);
	}

	@Override
	public Integer getOnBoardTasksCountByCandidateOnBoardingId(Long candidateOnBoardingId) throws Exception {
		return onboardTasksDao.getOnBoardTasksCountByCandidateOnBoardingId(candidateOnBoardingId);
	}

	@Override
	public List<OnboardTasks> getOnBoardTasksListByCandidateOnBoardingId(Long candidateOnBoardingId) throws Exception {
		return onboardTasksDao.getOnBoardTasksListByCandidateOnBoardingId(candidateOnBoardingId);
	}

	@Override
	public List<OnboardTasks> getOnboardTasksByTemplateIdList(List<Long> templateId) throws Exception {
		return onboardTasksDao.getOnboardTasksByTemplateIdList(templateId);
	}

	@Override
	public OnboardTasks getOnboardTasksByTaskId(Long Id) throws Exception {
		return onboardTasksDao.getOnboardTasksByTaskId(Id);
	}

	@Override
	public List<OnboardTasks> getOnBoardTasksListAssignedToCandidateByCandidateOnBoardingId(Long candidateOnBoardingId)
			throws Exception {
		return onboardTasksDao.getOnBoardTasksListAssignedToCandidateByCandidateOnBoardingId(candidateOnBoardingId);
	}

	

}
