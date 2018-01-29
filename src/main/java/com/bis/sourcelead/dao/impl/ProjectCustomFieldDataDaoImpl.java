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

import com.bis.sourcelead.dao.ProjectCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectCustomFieldData;
import com.bis.sourcelead.entity.UserCustomFieldData;

@Repository
public class ProjectCustomFieldDataDaoImpl implements ProjectCustomFieldDataDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectCustomFieldDataDaoImpl.class);
	
	@Override
	@Transactional
	public ProjectCustomFieldData saveProjectCustomFieldData(ProjectCustomFieldData projectCustomFields) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectCustomFields != null) {
				session.saveOrUpdate(projectCustomFields);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return projectCustomFields;
	}
	
	
	@Override
	@Transactional
	public List<ProjectCustomFieldData> getProjectCustomFieldDataByProjectId(Long projectId,List<CustomFormFieldDTO> formFieldId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from ProjectCustomFieldData projectData left join fetch projectData.orglocationsFormFields formField left join fetch projectData.projects proj where proj.id =:projectId and formField.id IN(:formFieldId)";
		List<ProjectCustomFieldData> projectFieldData  = session.createQuery(query).setLong("projectId", projectId).setParameterList("formFieldId", formIds).list();
		return projectFieldData;
	}

}
