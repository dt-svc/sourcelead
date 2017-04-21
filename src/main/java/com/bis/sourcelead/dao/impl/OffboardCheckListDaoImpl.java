package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OffboardCheckListDao;
import com.bis.sourcelead.entity.OffboardCheckList;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OffboardCheckListDaoImpl implements OffboardCheckListDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(OffboardCheckListDaoImpl.class);

	@Override
	@Transactional
	public OffboardCheckList getOffboardCheckListByTemplateId(Long templateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OffboardCheckList> offboardCheckList = session.createQuery("from OffboardCheckList checkList left join fetch checkList.offboardingTemplateId  where checkList.offboardingTemplateId.id = :templateId" ).setLong( "templateId", templateId ).setCacheable(true).list();
		if (offboardCheckList.size() > 0) {
			return offboardCheckList.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public OffboardCheckList saveOffboardCheckList(OffboardCheckList offboardCheckList) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (offboardCheckList != null) {
				session.saveOrUpdate(offboardCheckList);
			}
			logger.debug("offboardCheckList Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"OFFBOARD_CHECKLIST"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return offboardCheckList;
	}

}
