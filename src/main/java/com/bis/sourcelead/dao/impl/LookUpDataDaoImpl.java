package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.LookUpDataDao;
import com.bis.sourcelead.entity.LookUpData;

/**
 * 
 * @author prasad K
 * @date 30th Aug 2016
 *
 */
@Repository
@Transactional(rollbackFor = { Exception.class })
public class LookUpDataDaoImpl implements LookUpDataDao {
	private static final Logger logger = LoggerFactory.getLogger(LookUpDataDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * This method for to get all Educations(MCA,Btech,BSC,...etc) and Skills (java,.Net,...etc) (Auto completes)From DB
	 */
	
	@Override
	@Transactional
	public List<LookUpData> getLookUpDataByCategory(String category) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from LookUpData lookupdata where lookupdata.category = :category ";
	    List<LookUpData> educationsList = session.createQuery(query.toString()).setString("category", category).setCacheable(true).list();
      
	    return educationsList;
	}

	@Override
	@Transactional
	public List<String> getAllSkillsBySkillCategory() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select lookupdata.name from LookUpData lookupdata where lookupdata.category = 'Skills' ";
	    List<String> skillsList = session.createQuery(query.toString()).setCacheable(true).list();
      
	    return skillsList;
	}

	@Override
	@Transactional
	public List<LookUpData> getSkillsBySkillsName(List<String> skillNames) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<LookUpData> skillsList;
		if(skillNames != null && !skillNames.isEmpty()){
			String query = " from LookUpData lookupdata where lookupdata.category = 'Skills' and lookupdata.name NOT IN (:skillNames)";
		    skillsList = session.createQuery(query).setParameterList("skillNames", skillNames).setCacheable(true).list();
		}else{
			String query = " from LookUpData lookupdata where lookupdata.category = 'Skills'";
		    skillsList = session.createQuery(query).setCacheable(true).list();
		}
	    return skillsList;
	}
	
	
	
	

	

	

}
