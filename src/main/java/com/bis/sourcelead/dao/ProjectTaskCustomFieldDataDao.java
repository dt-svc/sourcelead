package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectTaskCustomFieldData;

public interface ProjectTaskCustomFieldDataDao {

	ProjectTaskCustomFieldData getProjectTaskCustomFieldDataById(Long entityId);

	ProjectTaskCustomFieldData saveProjectTaskCustomFieldData(ProjectTaskCustomFieldData projectTaskCustomFields);

	Map<Long, String[]> getProjectTaskCustomFieldDataByUserId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long projectTaskId);



}
