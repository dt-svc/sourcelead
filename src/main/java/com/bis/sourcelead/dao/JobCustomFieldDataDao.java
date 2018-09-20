package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.JobCustomFieldData;

public interface JobCustomFieldDataDao {

	public JobCustomFieldData savejobCustomFieldData(JobCustomFieldData jobCustomFields);

	public JobCustomFieldData getJobCustomFieldDataById(Long jobCustomFieldsID);
	
	public Map<Long, String[]> getJobCustomFieldDataMapByLocationId(Long orgId,List<CustomFormFieldDTO> formFieldId, Long requirementId);

	

}
