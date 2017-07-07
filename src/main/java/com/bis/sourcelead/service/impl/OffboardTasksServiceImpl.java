package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OffboardTasksDao;
import com.bis.sourcelead.entity.OffboardTasks;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.service.OffboardTasksService;

@Service
public class OffboardTasksServiceImpl implements OffboardTasksService {
	
	@Autowired
	OffboardTasksDao offboardTasksDao;

	@Override
	public OffboardTasks saveOffboardTasks(OffboardTasks offboardTasks) throws Exception {
		return offboardTasksDao.saveOffboardTasks(offboardTasks);
	}

	@Override
	public List<OffboardTasks> getOffboardTasksByTemplateId(Long templateId) throws Exception {
		return offboardTasksDao.getOffboardTasksByTemplateId(templateId);
	}

	@Override
	public Integer getOffBoardTasksCountByCandidateOffBoardingId(Long candidateOffBoardingId) throws Exception {
		return offboardTasksDao.getOffBoardTasksCountByCandidateOffBoardingId(candidateOffBoardingId);
	}

	@Override
	public List<OffboardTasks> getOffBoardTasksListByCandidateOffBoardingId(Long candidateOffBoardingId)
			throws Exception {
		return offboardTasksDao.getOffBoardTasksListByCandidateOffBoardingId(candidateOffBoardingId);
	}

	@Override
	public List<OffboardTasks> getOffboardTasksByTemplateIdList(List<Long> templateId) throws Exception {
		return offboardTasksDao.getOffboardTasksByTemplateIdList(templateId);
	}

	@Override
	public OffboardTasks getOffboardTasksByTaskId(Long Id) throws Exception {
		return offboardTasksDao.getOffboardTasksByTaskId(Id);
	}

	@Override
	public List<OffboardTasks> getOffBoardTasksListAssignedToCandidateByCandidateOffBoardingId(
			Long candidateOffBoardingId) throws Exception {
		return offboardTasksDao.getOffBoardTasksListAssignedToCandidateByCandidateOffBoardingId(candidateOffBoardingId);
	}

	@Override
	public List<OffboardTasks> getOffBoardAddedTasksListByCandidateOffBoardingId(Long candidateOffBoardingId)
			throws Exception {
		return offboardTasksDao.getOffBoardAddedTasksListByCandidateOffBoardingId(candidateOffBoardingId);
	}

}
