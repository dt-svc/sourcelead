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

import com.bis.sourcelead.dao.UserCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.JobCustomFieldData;
import com.bis.sourcelead.entity.UserCustomFieldData;

@Repository
public class UserCustomFieldDataDaoImpl implements UserCustomFieldDataDao{
	
	private static final Logger logger = LoggerFactory.getLogger(UserCustomFieldDataDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public UserCustomFieldData saveUserCustomFieldData(UserCustomFieldData userCustomFields) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userCustomFields != null) {
				session.saveOrUpdate(userCustomFields);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return userCustomFields;
	}

	@Override
	@Transactional
	public Map<Long, String[]> getUserCustomFieldDataByUserId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from UserCustomFieldData userData left join fetch userData.orglocationsFormFields formField left join fetch formField.org org left join fetch userData.user usr where usr.id =:userId and formField.id IN(:formFieldId) and org.id =:orgId";
		List<UserCustomFieldData> userFieldData  = session.createQuery(query).setLong("userId", userId).setLong("orgId", orgId).setParameterList("formFieldId", formIds).list();
		Map<Long,String[]> userCustomFieldDataMap = new HashMap<Long,String[]>();
		if(userFieldData != null && !userFieldData.isEmpty()) {
			for(UserCustomFieldData userData:userFieldData) {
				String userDataArray[] =  new String[4];
				userDataArray[0] = userData.getOrglocationsFormFields().getLabelName();
				userDataArray[1] = userData.getCustomFormFieldData();
				userDataArray[2] = userData.getId().toString();
				userCustomFieldDataMap.put(userData.getOrglocationsFormFields().getId(), userDataArray);
			}
		}
		return userCustomFieldDataMap;
	}

	@Override
	@Transactional
	public UserCustomFieldData getUserCustomFieldDataById(Long entityId) {
		Session session = sessionFactory.getCurrentSession();
		UserCustomFieldData userCustomFields = null;
		try {
			userCustomFields = (UserCustomFieldData) session.get(UserCustomFieldData.class, entityId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return userCustomFields;
	}

}
