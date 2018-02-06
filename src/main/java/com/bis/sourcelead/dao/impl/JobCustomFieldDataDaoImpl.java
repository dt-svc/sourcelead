package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.bis.sourcelead.entity.ClientCustomFieldData;
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
	public List<JobCustomFieldData> getJobCustomFieldDataByLocationId(Long orgId,List<CustomFormFieldDTO> formFieldId) {
		Session session = sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from JobCustomFieldData jobData left join fetch jobData.orglocationsFormFields formField left join fetch formField.org org where org.id =:orgId and formField.id IN(:formFieldId)";
		List<JobCustomFieldData> jobCustomFieldData  = session.createQuery(query).setLong("orgId", orgId).setParameterList("formFieldId", formIds).list();
		return jobCustomFieldData;
	}
	
}
