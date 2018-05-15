package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectCustomFieldData;

public interface ProjectCustomFieldDataDao {

	ProjectCustomFieldData saveProjectCustomFieldData(ProjectCustomFieldData projectCustomFields);

	Map<Long, String[]> getProjectCustomFieldDataByProjectId(Long orgId,List<CustomFormFieldDTO> formFieldId, Long projectId);

	ProjectCustomFieldData getprojectFieldDataById(Long projectId);
}
