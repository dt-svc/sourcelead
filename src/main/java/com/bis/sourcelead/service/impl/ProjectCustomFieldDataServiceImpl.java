package com.bis.sourcelead.service.impl;

import java.util.List;

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
	public List<ProjectCustomFieldData> getProjectCustomFieldDataByProjectId(Long projectId,List<CustomFormFieldDTO> formFieldId) {
		return projectCustomFieldDataDao.getProjectCustomFieldDataByProjectId(projectId,formFieldId);
	}
	@Override
	public ProjectCustomFieldData saveProjectCustomFieldData(ProjectCustomFieldData projectCustomFields) {
		return projectCustomFieldDataDao.saveProjectCustomFieldData(projectCustomFields);
	}

}
