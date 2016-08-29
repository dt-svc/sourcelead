package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
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

import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrgSpocDaoImpl  implements OrgSpocDao{
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
	
	private static final Logger logger = LoggerFactory.getLogger(OrgSpocDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc addOrgSpoc(OrgSpoc orgSpoc) throws SourceLeadConstraintViolationException {

		logger.debug("Adding OrgSpoc");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgSpoc != null) {
				session.saveOrUpdate(orgSpoc);
			}
			logger.debug("OrgSpoc Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_SPOC"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgSpoc;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId) throws Exception {
		logger.debug("In OrgSpocDaoImpl getAllSpocsByOrgRelationId method");
		Session session = this.sessionFactory.getCurrentSession();
		Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser where spoc.orgRelations.id = :orgRelationId");
		List<OrgSpoc> spocsList = query.setLong("orgRelationId", orgRelationId).setCacheable(true).list();
		return spocsList;
	}

}
