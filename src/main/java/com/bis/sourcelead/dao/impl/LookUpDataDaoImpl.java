package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
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
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

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
	
	
	/**
	 * @author prasad k
	 * @since  sep 1 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save lookUpDataSkills in LookUpData table.
	 *  
	 * @param lookUpDataCategory This is the first paramter to storeLookUpDataSkills method
	 * 
	 * @return LookUpData 
	 * 
	 * @throws NullPointer
	 */

	@Override
	@Transactional
	public LookUpData storeLookUpDataByCategory(LookUpData lookUpDataCategory) throws Exception {
		logger.debug("Storing lookUpDataCategory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (lookUpDataCategory != null) {
				session.saveOrUpdate(lookUpDataCategory);
			}
			logger.debug("LookUpData(Skills,Educations) Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"lookUpDataCategory"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return lookUpDataCategory;
	}

	
	/**
	 * @author prasad k
	 * @since  jan 23 2017
	 * @version 0.0.1
	 * 
	 * This method is used to Get lookUpData(Skills,Educations) from LookUpData table.
	 *  
	 * @param name This is the first paramter to lookUpData(Skills,Educations) method
	 * @param category This is the 2nd paramter to lookUpData(Skills,Educations) method
	 * 
	 * @return LookUpData 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional
	public LookUpData getLookUpDataByNameAndCategory(String name, String category) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<LookUpData> lookUpDataList = new ArrayList<LookUpData>();
		logger.debug("getLookUpDataByNameAndCategory start");
		lookUpDataList = session.createQuery("from LookUpData ld where ld.name =:name and ld.category = :category").setCacheable(true)
				 .setString( "name", name).setString( "category", category).setCacheable(true).list();
		logger.debug("getLookUpDataByNameAndCategory end");
		if (lookUpDataList.size() > 0) {
			return lookUpDataList.get(0);
		} else {
			return null;
		}
	}
	
	
	
	

	

	

}
