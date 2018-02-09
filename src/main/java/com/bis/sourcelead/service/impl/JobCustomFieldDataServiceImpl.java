package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.JobCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.JobCustomFieldData;
import com.bis.sourcelead.service.JobCustomFieldDataService;

@Service
public class JobCustomFieldDataServiceImpl implements JobCustomFieldDataService {

	@Autowired
	private JobCustomFieldDataDao jobCustomFieldDataDao;
	
	@Override
	public JobCustomFieldData savejobCustomFieldData(JobCustomFieldData jobCustomFields) {
		return jobCustomFieldDataDao.savejobCustomFieldData(jobCustomFields);
	}

	@Override
	public JobCustomFieldData getJobCustomFieldDataById(Long jobCustomFieldsID) {
		return jobCustomFieldDataDao.getJobCustomFieldDataById(jobCustomFieldsID);
	}

	
	@Override
	public Map<Long, String[]> getJobCustomFieldDataMapByLocationId(Long orgId,List<CustomFormFieldDTO> formFieldId,Long requirementId) {
		return jobCustomFieldDataDao.getJobCustomFieldDataMapByLocationId(orgId, formFieldId,requirementId);
	}

}
