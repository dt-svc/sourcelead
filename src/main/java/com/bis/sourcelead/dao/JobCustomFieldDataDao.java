package com.bis.sourcelead.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.JobCustomFieldData;

public interface JobCustomFieldDataDao {

	public JobCustomFieldData savejobCustomFieldData(JobCustomFieldData jobCustomFields);

	public List<JobCustomFieldData> getJobCustomFieldDataByLocationId(Long orgId, List<CustomFormFieldDTO> formFieldId);

}
