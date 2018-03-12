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
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<Long, String[]> getProjectCustomFieldDataByProjectId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long projectId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from ProjectCustomFieldData projectData left join fetch projectData.orglocationsFormFields formField left join fetch formField.org org left join fetch projectData.projects pro where pro.id =:projectId and formField.id IN(:formFieldId) and org.id =:orgId";
		List<ProjectCustomFieldData> projectFieldData  = session.createQuery(query).setLong("projectId", projectId).setLong("orgId", orgId).setParameterList("formFieldId", formIds).list();
		Map<Long,String[]> projectCustomFieldDataMap = new HashMap<Long,String[]>();
		if(projectFieldData != null && !projectFieldData.isEmpty()) {
			for(ProjectCustomFieldData projectData:projectFieldData) {
				String projectDataArray[] =  new String[4];
				projectDataArray[0] = projectData.getOrglocationsFormFields().getLabelName();
				projectDataArray[1] = projectData.getCustomFormFieldData();
				projectDataArray[2] = projectData.getId().toString();
				projectCustomFieldDataMap.put(projectData.getOrglocationsFormFields().getId(), projectDataArray);
			}
		}
		return projectCustomFieldDataMap;
	}


	@Override
	@Transactional
	public ProjectCustomFieldData getprojectFieldDataById(Long projectId) {
		Session session = sessionFactory.getCurrentSession();
		ProjectCustomFieldData projectsCustomFields = null;
		try {
			projectsCustomFields = (ProjectCustomFieldData) session.get(ProjectCustomFieldData.class, projectId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return projectsCustomFields;
	}

}
