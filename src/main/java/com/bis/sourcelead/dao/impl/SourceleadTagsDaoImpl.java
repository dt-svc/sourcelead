package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadTagsDao;
import com.bis.sourcelead.dto.SourceleadTagsDTO;
import com.bis.sourcelead.entity.SourceleadTags;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class SourceleadTagsDaoImpl implements SourceleadTagsDao{

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

	private static final Logger logger = LoggerFactory.getLogger(SourceleadTagsDaoImpl.class);

	@Override
	public SourceleadTags saveSourceleadTags(SourceleadTags sourceleadTags) throws Exception {
		logger.debug("Storing Tags");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (sourceleadTags != null) {
				session.saveOrUpdate(sourceleadTags);
			}
			logger.debug("Candidate Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SourceleadTags"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return sourceleadTags;
	}

	@Override
	public Set<String> getSourceleadTagsByOrgLocationId(Long orgLocationId) throws Exception {
		
		Set<String> tagsList = null;
		List<SourceleadTagsDTO> tagsDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
				query ="SELECT "
				+" TAGS.TAG_NAME AS tagName "
				+" FROM SOURCELEAD_TAGS TAGS "
				+" WHERE "
				+" TAGS.ORG_LOCATION_ID = :orgLocationId ";
		    
            Query queryObj = session.createSQLQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(SourceleadTagsDTO.class));
			tagsDTOList = queryObj.list();
			tagsList =  tagsDTOList.stream().map(SourceleadTagsDTO::getTagName).collect(Collectors.toSet());
			return tagsList;
	}

	@Override
	public Set<String> getSourceleadTagsByOrgLocationIdAndEntityName(Long orgLocationId, String entityType,
			Long entityId) throws Exception {
		
		Set<String> tagsList = null;
		List<SourceleadTagsDTO> tagsDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
				query ="SELECT "
				+" TAGS.TAG_NAME AS tagName "
				+" FROM SOURCELEAD_TAGS TAGS "
				+" WHERE "
				+" TAGS.ORG_LOCATION_ID = :orgLocationId "
				+" AND TAGS.ENTITY_TYPE = :entityType "
				+" AND TAGS.ENTITY_ID = :entityId "
				+" AND TAGS.STATUS = '1' ";
		    
            Query queryObj = session.createSQLQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId).setString("entityType", entityType).setLong("entityId", entityId);
			queryObj.setResultTransformer(Transformers.aliasToBean(SourceleadTagsDTO.class));
			tagsDTOList = queryObj.list();
			tagsList = tagsDTOList.stream().map(SourceleadTagsDTO::getTagName).collect(Collectors.toSet());
			return tagsList;
	}

	@Override
	public SourceleadTags getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(Long orgLocationId, String entityType,
			Long entityId,String tagName) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadTags tags  where tags.orgLocationId.id = :orgLocationId and "
				+ "tags.entityType = :entityType and tags.entityId = :entityId and tags.tagName = :tagName and tags.status = 1 ";  
		List<SourceleadTags> sourceleadTagsList =  session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("entityType", entityType).setLong("entityId", entityId).setString("tagName", tagName).list();
		SourceleadTags sourceleadTags = null;
		if (sourceleadTagsList != null && sourceleadTagsList.size() > 0) {
			sourceleadTags = (SourceleadTags) sourceleadTagsList.iterator().next();
		}
		return sourceleadTags;
	
	}

	@Override
	public List<SourceleadTags> SourceleadTagsListByLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadTags st  where st.orgLocationId.id =:orgLocationId and st.status = '1' "; 
		List<SourceleadTags> sourceleadTagsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return sourceleadTagsList;
	}

	
	@Override
	public Integer individualTagsCount(String entiteyTpe, String tagName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String  query = "select  count(*) from SourceleadTags st  where st.entityType =:entiteyTpe  and st.tagName =:tagName and st.status = '1'";
		List<Long> tagsCount = session.createQuery(query).setString("entiteyTpe", entiteyTpe).setString("tagName", tagName).list();
		Integer count = 0;
		if(tagsCount != null && !tagsCount.isEmpty()){
			count = tagsCount.get(0).intValue();
	}
	return count;
	}

	@Override
	public void deleteTag(Long orgLocationId, String tagName,String entityType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadTags st where st.entityType  =:entityType and st.orgLocationId.id =:orgLocationId and st.tagName =:tagName";
		List<SourceleadTags> tagsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("entityType", entityType).setString("tagName", tagName).list();
       if(tagsList != null && !tagsList.isEmpty()) {
    	   for(SourceleadTags st :tagsList) {
    		   session.delete(st);
    	   }
       }
	}

	@Override
	public List<String> sourceleadTagsListByEntitypeAndOrgloactionId(String entityType, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select distinct(st.tagName) from SourceleadTags st  where st.entityType =:entityType and st.orgLocationId.id =:orgLocationId and st.status = '1' "; 
		List<String> sourceleadTagsList = session.createQuery(query).setString("entityType", entityType).setLong("orgLocationId", orgLocationId).list();
		return sourceleadTagsList;
	}

	@Override
	public List<SourceleadTags> getAllSourceleadTagsListByEntitypeAndOrgloactionId(String entityType,
			Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadTags st  where st.entityType =:entityType and st.orgLocationId.id =:orgLocationId and st.status = '1' "; 
		List<SourceleadTags> sourceleadTagsList = session.createQuery(query).setString("entityType", entityType).setLong("orgLocationId", orgLocationId).list();
		return sourceleadTagsList;
	}

}
