package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectTaskCustomFieldData;

public interface ProjectTaskCustomFieldDataService {

	public ProjectTaskCustomFieldData getProjectTaskCustomFieldDataById(Long entityId);

	public ProjectTaskCustomFieldData saveProjectTaskCustomFieldData(ProjectTaskCustomFieldData projectTaskCustomFields);
	
	public Map<Long, String[]> getProjectTaskCustomFieldDataByUserId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long projectTaskId);

}
