package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectCustomFieldData;
import com.bis.sourcelead.service.ProjectCustomFieldDataService;



@Service
public class ProjectCustomFieldDataServiceImpl implements ProjectCustomFieldDataService {
	@Autowired
	private ProjectCustomFieldDataDao projectCustomFieldDataDao;
	
	@Override
	public ProjectCustomFieldData saveProjectCustomFieldData(ProjectCustomFieldData projectCustomFields) {
		return projectCustomFieldDataDao.saveProjectCustomFieldData(projectCustomFields);
	}
	@Override
	public Map<Long, String[]> getProjectCustomFieldDataByProjectId(Long orgId, List<CustomFormFieldDTO> formFieldId, Long projectId) {
		return projectCustomFieldDataDao.getProjectCustomFieldDataByProjectId(orgId,formFieldId,projectId);
	}
	@Override
	public ProjectCustomFieldData getprojectFieldDataById(Long projectId) {
		return projectCustomFieldDataDao.getprojectFieldDataById(projectId);
	}

}
