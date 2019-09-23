package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadFormSectionsDao;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadFormSections;

@Repository
public class SourceleadFormSectionsDaoImpl  implements SourceleadFormSectionsDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadActions> getAllSourceleadActions() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "Select sfs.sourceleadActions from SourceleadFormSections sfs where sfs.status = 1 Group By sfs.sourceleadActions.id";
	    List<SourceleadActions> actionList = session.createQuery(query.toString()).list();
        return actionList;
        
	}  
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadFormSections> getSourceleadFormSectionsByActionId(Long actionId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
	    String query = " from SourceleadFormSections sfs left join fetch sfs.sourceleadActions actions where actions.id = :actionId and sfs.status = 1 order by sfs.id";
	    List<SourceleadFormSections> sectionList = session.createQuery(query.toString()).setLong("actionId", actionId).list();
        return sectionList;
	}
}
