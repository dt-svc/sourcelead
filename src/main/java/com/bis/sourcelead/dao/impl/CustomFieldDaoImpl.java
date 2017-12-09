package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CustomFieldDao;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;

@Repository
public class CustomFieldDaoImpl implements CustomFieldDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SourceleadFormSections> getAllCustomFieldServices() {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadFormSections SFormSections";
		List<SourceleadFormSections>  slFormSections = session.createQuery(query).setCacheable(true).list();
		return slFormSections;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SourceleadFormFields> getFormFieldsBysectionId(String sectionFieldId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadFormFields formFields where formFields.sourceleadFormSections.id =:sectionFieldId";
		List<SourceleadFormFields>  slFormFields = session.createQuery(query).setLong("sectionFieldId", Long.parseLong(sectionFieldId)).setCacheable(true).list();
		return slFormFields;
	}

}
