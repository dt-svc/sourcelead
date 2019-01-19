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

import com.bis.sourcelead.dao.ProjectTaskCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ProjectTaskCustomFieldData;

@Repository
public class ProjectTaskCustomFieldDataDaoImpl implements ProjectTaskCustomFieldDataDao {

	private static final Logger logger = LoggerFactory.getLogger(UserCustomFieldDataDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	

	
	@Override
	@Transactional
	public ProjectTaskCustomFieldData saveProjectTaskCustomFieldData(ProjectTaskCustomFieldData projectTaskCustomFields) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectTaskCustomFields != null) {
				session.saveOrUpdate(projectTaskCustomFields);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return projectTaskCustomFields;
	}

	@Override
	@Transactional
	public ProjectTaskCustomFieldData getProjectTaskCustomFieldDataById(Long entityId) {
		Session session = sessionFactory.getCurrentSession();
		ProjectTaskCustomFieldData projectTaskCustomFields = null;
		try {
			projectTaskCustomFields = (ProjectTaskCustomFieldData) session.get(ProjectTaskCustomFieldData.class, entityId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return projectTaskCustomFields;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<Long, String[]> getProjectTaskCustomFieldDataByUserId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long projectTaskId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from ProjectTaskCustomFieldData projecTaskData left join fetch projecTaskData.orglocationsFormFields formField left join fetch formField.org org left join fetch projecTaskData.projectTask proTask where proTask.id =:projectTaskId and formField.id IN(:formFieldId) and org.id =:orgId";
		List<ProjectTaskCustomFieldData> projectTaskFieldData  = session.createQuery(query).setLong("projectTaskId", projectTaskId).setLong("orgId", orgId).setParameterList("formFieldId", formIds).list();
		Map<Long,String[]> projectTaskCustomFieldDataMap = new HashMap<Long,String[]>();
		if(projectTaskFieldData != null && !projectTaskFieldData.isEmpty()) {
			for(ProjectTaskCustomFieldData projectTaskData:projectTaskFieldData) {
				String projectTaskDataArray[] =  new String[4];
				projectTaskDataArray[0] = projectTaskData.getOrglocationsFormFields().getLabelName();
				projectTaskDataArray[1] = projectTaskData.getCustomFormFieldData();
				projectTaskDataArray[2] = projectTaskData.getId().toString();
				projectTaskCustomFieldDataMap.put(projectTaskData.getOrglocationsFormFields().getId(), projectTaskDataArray);
			}
		}
		return projectTaskCustomFieldDataMap;
	}

	
	
	
}
