package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssociateGradesDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class AssociateGradesDaoImpl implements AssociateGradesDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
    OrgLocationService orgLocationService;
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(AssociateGradesDaoImpl.class);


	@Override
	@Transactional
	public AssociateGrades saveAssociateGrades(AssociateGrades associateGrades) throws Exception {
		
		logger.debug("Adding AssociateGrades");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateGrades != null) {
				session.saveOrUpdate(associateGrades);
			}
			logger.debug("AssociateGrades Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ASSOCIATE_GRADES"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return associateGrades;
	}


	@Override
	@Transactional
	public AssociateGrades getAssociateGradesByUUID(String uuid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateGrades grades where grades.uuid = :uuid and grades.status = '1' ";  
		List<AssociateGrades> associateGradesList =  session.createQuery(query).setString("uuid", uuid).list();
		AssociateGrades associateGrades = null;
		if (associateGradesList != null && associateGradesList.size() > 0) {
			associateGrades = (AssociateGrades) associateGradesList.iterator().next();
		}
		return associateGrades;
	}


	@Override
	@Transactional
	public List<AssociateGrades> getAssociateGradesListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateGrades> associateGradesList = null;
		Query gradesList = session.createQuery("from AssociateGrades grades left join fetch grades.orgLocationId  where grades.orgLocationId = :orgLocationId and grades.status = '1' ");
		associateGradesList = gradesList.setLong("orgLocationId", orgLocationId).list();
		return associateGradesList;
	}
}
