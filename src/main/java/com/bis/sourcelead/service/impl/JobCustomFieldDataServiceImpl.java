package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.JobCustomFieldDataDao;
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

}
