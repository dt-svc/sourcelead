package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.JobCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.JobCustomFieldData;

@Repository
public class JobCustomFieldDataDaoImpl implements  JobCustomFieldDataDao{

	private static final Logger logger = LoggerFactory.getLogger(JobCustomFieldDataDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public JobCustomFieldData savejobCustomFieldData(JobCustomFieldData jobCustomFields) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (jobCustomFields != null) {
				session.saveOrUpdate(jobCustomFields);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return jobCustomFields;
	}

	@Override
	@Transactional
	public JobCustomFieldData getJobCustomFieldDataById(Long jobCustomFieldsID) {
		Session session = sessionFactory.getCurrentSession();
		JobCustomFieldData jobCustomFields = null;
		try {
			jobCustomFields = (JobCustomFieldData) session.get(JobCustomFieldData.class, jobCustomFieldsID);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jobCustomFields;
	}
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<Long,String[]> getJobCustomFieldDataMapByLocationId(Long orgId,List<CustomFormFieldDTO> formFieldId,Long requirementId) {
		Session session = sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from JobCustomFieldData jobData left join fetch jobData.orglocationsFormFields formField left join fetch formField.org org left join fetch jobData.requirement req where org.id =:orgId and req.id =:requirementId and formField.id IN(:formFieldId)";
		List<JobCustomFieldData> jobCustomFieldData  = session.createQuery(query).setLong("orgId", orgId).setLong("requirementId", requirementId).setParameterList("formFieldId", formIds).list();
		Map<Long,String[]> jobCustomFieldDataMap = new HashMap<Long,String[]>();
		if(jobCustomFieldData != null && !jobCustomFieldData.isEmpty()) {
			for(JobCustomFieldData jobData:jobCustomFieldData) {
				String jobDataArray[] =  new String[4];
				jobDataArray[0] = jobData.getOrglocationsFormFields().getLabelName();
				jobDataArray[1] = jobData.getCustomFormFieldData();
				jobDataArray[2] = jobData.getId().toString();
				jobCustomFieldDataMap.put(jobData.getOrglocationsFormFields().getId(), jobDataArray);
			}
		}
		return jobCustomFieldDataMap;
	}
	
}
