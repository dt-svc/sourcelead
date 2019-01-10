package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Query;
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
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * 
 * @author prasad K
 * @date 30th Aug 2016
 *
 */
@Repository
@Transactional(rollbackFor = { Exception.class })
@SuppressWarnings("unchecked")
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
		String query = null;
		query = "from LookUpData lookupdata where lookupdata.category = :category  ";
	    
	    List<LookUpData> educationsList = session.createQuery(query.toString()).setString("category", category).list();
      
	    return educationsList;
	}
	
	
	@Override
	@Transactional
	public LinkedHashSet<String> getLookUpDataByCategoryWithSortedOrder(String category) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "Select name from LookUpData lookupdata where lookupdata.category = :category ";
	    LinkedHashSet<String> skillsList = new LinkedHashSet<>(session.createQuery(query.toString()).setString("category", category).list());
      
	    return skillsList;
	}
	

	@Override
	@Transactional
	public List<String> getAllSkillsBySkillCategory() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select lookupdata.name from LookUpData lookupdata where lookupdata.category = 'Skills' ";
	    List<String> skillsList = session.createQuery(query.toString()).list();
      
	    return skillsList;
	}

	@Override
	@Transactional
	public List<LookUpData> getSkillsBySkillsName(List<String> skillNames) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<LookUpData> skillsList;
		if(skillNames != null && !skillNames.isEmpty()){
			String query = " from LookUpData lookupdata where lookupdata.category = 'Skills' and lookupdata.name NOT IN (:skillNames)";
		    skillsList = session.createQuery(query).setParameterList("skillNames", skillNames).list();
		}else{
			String query = " from LookUpData lookupdata where lookupdata.category = 'Skills'";
		    skillsList = session.createQuery(query).list();
		}
	    return skillsList;
	}

	@Override
	public List<String> getAllEducationsByEducationCategory()
	{
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select lookupdata.name from LookUpData lookupdata where lookupdata.category = 'Educations' ";
	    List<String> educationList = session.createQuery(query.toString()).list();
	    return educationList;
	}
	
	@Override
	public List<String> getAllSkillsByIndustryCategory()
	{
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select lookupdata.name from LookUpData lookupdata where lookupdata.category = 'Industries' ";
	    List<String> educationList = session.createQuery(query.toString()).list();
	    return educationList;
	}

	@Override
	@Transactional
	public List<String> getAllVisaByCategory() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select lookupdata.name from LookUpData lookupdata where lookupdata.category = 'VisaCategory' ";
	    List<String> visaList = session.createQuery(query.toString()).list();
	    return visaList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LookUpData getTasksById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<LookUpData> tasks = new ArrayList<LookUpData>();
		tasks = session.createQuery("from LookUpData lookupdata where lookupdata.id = :id").setLong( "id",id ).list();
		logger.debug("getUserById end");
		if (tasks.size() > 0) {
			return tasks.get(0);
		} else {
			return null;
		}		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LookUpData getLookUpdataByCategoryAndName(String category,String name) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LookUpData lookupdata where lookupdata.category = :category and lookupdata.name = :name";
		List<LookUpData> lookupList = session.createQuery(query.toString()).setString("category", category).setString("name", name).list();
		LookUpData lookUpData = null;
		if (lookupList != null && lookupList.size() > 0) {
			lookUpData = (LookUpData) lookupList.iterator().next();
		}
		return lookUpData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LookUpData getTasksTypeByUniqueKey(String uniqueKey) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<LookUpData> tasks = new ArrayList<LookUpData>();
		tasks = session.createQuery("from LookUpData lookupdata where lookupdata.uniqueKey = :uniqueKey").setString( "uniqueKey",uniqueKey ).list();
		if (tasks.size() > 0) {
			return tasks.get(0);
		} else {
			return null;
		}		
	}

	@Override
	public LookUpData getRequirementExperienceLevelyUniqueKey(String uniqueKey) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<LookUpData> tasks = new ArrayList<LookUpData>();
		tasks = session.createQuery("from LookUpData lookupdata where lookupdata.uniqueKey = :uniqueKey").setString( "uniqueKey",uniqueKey ).list();
		if (tasks.size() > 0) {
			return tasks.get(0);
		} else {
			return null;
		}		
	}

	@Override
	public LookUpData getLookUpDataByIdAndCategory(String visaType, long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LookUpData lookupdata where lookupdata.category = :category and lookupdata.id = :id";
		LookUpData lookupData = (LookUpData) session.createQuery(query.toString()).setString("category", visaType).setLong("id", id).uniqueResult();
		return lookupData;
	}
	
	@Override
	public LookUpData getLookUpDataById(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LookUpData lookupdata where lookupdata.id = :id";
		LookUpData lookupData = (LookUpData) session.createQuery(query.toString()).setLong("id", id).uniqueResult();
		return lookupData;
	}

	@Override
	public LookUpData saveLookupData(LookUpData lookUpData) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (lookUpData != null) {
				session.saveOrUpdate(lookUpData);
			}
			logger.debug("Saving lookUpData");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"LOOKUP_DATA"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return lookUpData;
	}

	@Override
	public List<LookUpData> getLookUpdataByCategoryAndOrgLocationId(String category, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "from LookUpData lookupdata where (lookupdata.category = :category and lookupdata.orgLocationId IS NULL ) or (lookupdata.category = :category and lookupdata.orgLocationId =:orgLocationId)";
	    List<LookUpData> taskTypeList = session.createQuery(query.toString()).setString("category", category).setLong("orgLocationId", orgLocationId).list();
	    return taskTypeList;
	}

	@Override
	public void deleteLookupDateByLookupId(LookUpData lookUpData) throws Exception{
		Session session =  this.sessionFactory.getCurrentSession();
		try {
			if(lookUpData != null) {
				session.delete(lookUpData);
			}
		}catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"LOOKUP_DATA"));
			 throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
	}

	@Override
	public List<LookUpData> getReferLookUpDataByCategory(String category) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "from LookUpData lookupdata where lookupdata.category = :category and  lookupdata.orgLocationId IS NULL ORDER BY lookupdata.name DESC";
	    List<LookUpData> educationsList = session.createQuery(query.toString()).setString("category", category).list();
      
	    return educationsList;
	}

	@Override
	public Boolean validateTaskTypeName(String taskTypeName,String category,Long orgLocationId) {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(taskTypeName != null){
				String query = "from LookUpData lookupdata where lookupdata.category = :category and ((lookupdata.name = :taskTypeName and  lookupdata.orgLocationId =:orgLocationId) or (lookupdata.name = :taskTypeName and  lookupdata.orgLocationId IS NULL) )";
				Collection<LookUpData> lookUpDataNoteList = session.createQuery(query.toString()).setString("taskTypeName", taskTypeName).setLong("orgLocationId", orgLocationId).setString("category", category).list();
				if (lookUpDataNoteList != null && lookUpDataNoteList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

	@Override
	public LookUpData validateTaskTypeNameByTaskNameAndLookupId(String taskTypeName, Long lookupId,String category,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LookUpData lookupdata  where lookupdata.name = :taskTypeName and lookupdata.id =:lookupId and lookupdata.category = :category and lookupdata.orgLocationId =:orgLocationId";
		List<LookUpData> lookUpDataNoteList = session.createQuery(query.toString()).setString("taskTypeName", taskTypeName).setLong("lookupId", lookupId).setLong("orgLocationId", orgLocationId).setString("category", category).list();
		LookUpData lkp = null;
		if(lookUpDataNoteList != null && !lookUpDataNoteList.isEmpty())
		{
			lkp = (LookUpData) lookUpDataNoteList.iterator().next();
		}
		return lkp;
	}

	@Override
	public List<String> getListOfStringLookUpdataByCategoryAndOrgLocationId(String category, Long orgLocationId)throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="SELECT "
				+" LD.NAME "
				+" FROM LOOKUP_DATA LD "
				+" WHERE "
				+" ( LD.CATEGORY = :category AND LD.ORG_LOCATIONID IS NULL ) OR (LD.CATEGORY = :category AND LD.ORG_LOCATIONID =:orgLocationId )";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId).setString("category", category);	
		List<String> taskTypeList = (List<String>)queryObj.list();
	    return taskTypeList;
	}

	@Override
	public List<String> getCategoriesListByCategoryType(String category) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="SELECT "
				+" LD.NAME "
				+" FROM LOOKUP_DATA LD "
				+" WHERE "
				+" LD.CATEGORY = :category ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("category", category);	
		List<String> taskTypeList = (List<String>)queryObj.list();
	    return taskTypeList;
	}
	 
}
