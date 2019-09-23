package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectTaskCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectTaskCustomFieldData;
import com.bis.sourcelead.service.ProjectTaskCustomFieldDataService;

@Service
public class ProjectTaskCustomFieldDataServiceImpl implements ProjectTaskCustomFieldDataService{

	@Autowired
	private ProjectTaskCustomFieldDataDao projectTaskCustomFieldDataDao;
	
	@Override
	public ProjectTaskCustomFieldData getProjectTaskCustomFieldDataById(Long entityId) {
		return projectTaskCustomFieldDataDao.getProjectTaskCustomFieldDataById(entityId);
	}

	@Override
	public ProjectTaskCustomFieldData saveProjectTaskCustomFieldData(ProjectTaskCustomFieldData projectTaskCustomFields) {
		return projectTaskCustomFieldDataDao.saveProjectTaskCustomFieldData(projectTaskCustomFields);
	}

	
	@Override
	public Map<Long, String[]> getProjectTaskCustomFieldDataByUserId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long projectTaskId) {
		return projectTaskCustomFieldDataDao.getProjectTaskCustomFieldDataByUserId(orgId,formFieldId,projectTaskId);
	}

}
