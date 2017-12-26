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

import com.bis.sourcelead.dao.UserCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
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
	public List<UserCustomFieldData> getUserCustomFieldDataByUserId(Long userId, List<CustomFormFieldDTO> formFieldId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from UserCustomFieldData userData left join fetch userData.orglocationsFormFields formField left join fetch userData.user usr where usr.id =:userId and formField.id IN(:formFieldId)";
		List<UserCustomFieldData> userFieldData  = session.createQuery(query).setLong("userId", userId).setParameterList("formFieldId", formIds).setCacheable(true).list();
		return userFieldData;
	}

}
