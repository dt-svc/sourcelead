package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectCustomFieldData;

public interface ProjectCustomFieldDataService {

	Map<Long, String[]> getProjectCustomFieldDataByProjectId(Long orgId,List<CustomFormFieldDTO> formFieldId, Long projrctID);
	
	ProjectCustomFieldData saveProjectCustomFieldData(ProjectCustomFieldData projectCustomFields);

	ProjectCustomFieldData getprojectFieldDataById(Long projectId);

}
 