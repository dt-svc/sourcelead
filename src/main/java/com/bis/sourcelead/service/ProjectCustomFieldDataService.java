package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectCustomFieldData;

public interface ProjectCustomFieldDataService {

	List<ProjectCustomFieldData> getProjectCustomFieldDataByProjectId(Long projectId,List<CustomFormFieldDTO> formFieldId);
	
	ProjectCustomFieldData saveProjectCustomFieldData(ProjectCustomFieldData projectCustomFields);

}
