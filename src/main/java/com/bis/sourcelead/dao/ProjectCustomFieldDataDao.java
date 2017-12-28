package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectCustomFieldData;

public interface ProjectCustomFieldDataDao {

	List<ProjectCustomFieldData> getProjectCustomFieldDataByProjectId(Long projectId,List<CustomFormFieldDTO> formFieldId);

	ProjectCustomFieldData saveProjectCustomFieldData(ProjectCustomFieldData projectCustomFields);

}
