package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${SourceLead.100}")
	private String notFound;
	
	@Value("${SourceLead.101}")
	private String noRecords;
	
	@Value("${SourceLead.102}")
	private String statusUpdate;
	
	@Value("${SourceLead.103}")
	private String statusDelete;
	
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

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
	public LinkedHashSet<String> getLookUpDataByCategoryWithSortedOrder(String category) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "Select name from LookUpData lookupdata where lookupdata.category = :category ";
	    LinkedHashSet<String> skillsList = new LinkedHashSet<>(session.createQuery(query.toString()).setString("category", category).setCacheable(true).list());
      
	    return skillsList;
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

	@Override
	public List<String> getAllEducationsByEducationCategory()
	{
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select lookupdata.name from LookUpData lookupdata where lookupdata.category = 'Educations' ";
	    List<String> educationList = session.createQuery(query.toString()).setCacheable(true).list();
	    return educationList;
	}
	
	@Override
	public List<String> getAllSkillsByIndustryCategory()
	{
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select lookupdata.name from LookUpData lookupdata where lookupdata.category = 'Industries' ";
	    List<String> educationList = session.createQuery(query.toString()).setCacheable(true).list();
	    return educationList;
	}

	@Override
	@Transactional
	public List<String> getAllVisaByCategory() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select lookupdata.name from LookUpData lookupdata where lookupdata.category = 'VisaCategory' ";
	    List<String> visaList = session.createQuery(query.toString()).setCacheable(true).list();
	    return visaList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LookUpData getTasksById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<LookUpData> tasks = new ArrayList<LookUpData>();
		tasks = session.createQuery("from LookUpData lookupdata where lookupdata.id = :id").setCacheable(true).setLong( "id",id ).setCacheable(true).list();
		logger.debug("getUserById end");
		if (tasks.size() > 0) {
			return tasks.get(0);
		} else {
			return null;
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public LookUpData getTasksTypeByUniqueKey(String uniqueKey) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<LookUpData> tasks = new ArrayList<LookUpData>();
		tasks = session.createQuery("from LookUpData lookupdata where lookupdata.uniqueKey = :uniqueKey").setCacheable(true).setString( "uniqueKey",uniqueKey ).setCacheable(true).list();
		if (tasks.size() > 0) {
			return tasks.get(0);
		} else {
			return null;
		}		
	}
 
}
