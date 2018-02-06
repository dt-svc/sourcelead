package com.bis.sourcelead.service;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.JobCustomFieldData;

public interface JobCustomFieldDataService {

	public JobCustomFieldData savejobCustomFieldData(JobCustomFieldData jobCustomFields);

	public List<JobCustomFieldData> getJobCustomFieldDataByLocationId(Long orgLocation, List<CustomFormFieldDTO> formFieldId);
}
