package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrglocationFormSubFieldsDao;
import com.bis.sourcelead.entity.OrglocationFormSubFields;

@Repository
public class OrglocationFormSubFieldsDaoImpl implements OrglocationFormSubFieldsDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrglocationFormSubFields saveOrglocationFormSubField(OrglocationFormSubFields subFields) {
		if(subFields != null) {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(subFields);
			return subFields;
		}else {
			return null;
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public List<OrglocationFormSubFields> getOrgCustomSufieldsBySectionId(Long customFieldId) {
		if(customFieldId != null) {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from OrglocationFormSubFields subFields left join fetch subFields.formField formfield where formfield.id =:customFieldId";
			List<OrglocationFormSubFields> subFieldList = session.createQuery(query).setLong("customFieldId",customFieldId).list();
			return subFieldList;
		}else {
			return null;
		}
	}
}
