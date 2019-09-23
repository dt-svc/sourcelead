package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadFormFieldsDao;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.SourceleadFormFields;

@Repository
public class SourceleadFormFieldsDaoImpl implements SourceleadFormFieldsDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadFormFields> getAllFormFieldsBySectionId(Long sectionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = " from SourceleadFormFields sff left join fetch sff.sourceleadFormSections sfs where sfs.id = :sectionId";
	    List<SourceleadFormFields> fieldList = session.createQuery(query.toString()).setLong("sectionId", sectionId).list();
        return fieldList;
        
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadFormFields> getSourceleadSectionDetailsById(Long section,Long sourceleadFormId, Long locationId) {
		Session session = this.sessionFactory.getCurrentSession();
		 String query = " from SourceleadFormFields sff left join fetch sff.sourceleadFormSections sfs where sfs.id = :sectionId and sff.id = :sourceleadFormId";
		 List<SourceleadFormFields> fieldList = session.createQuery(query.toString()).setLong("sectionId", section).setLong("sourceleadFormId", sourceleadFormId).list();
		 return fieldList;
	}

}
