package com.bis.sourcelead.immigration.dao.impl;

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

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.entity.ImmigrationActivityHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.immigration.dao.ImmigrationActivityDao;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Srinivas.Sabbu
 *
 */
@Repository
public class ImmigrationActivityDaoImpl implements ImmigrationActivityDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	CandidateDao candidateDao;

	@Autowired
	private ImmigrationTemplateDao immigrationTemplateDao;

	@Autowired
	private CommonUtil commonUtil;

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

	private static final Logger logger = LoggerFactory.getLogger(ImmigrationActivityDaoImpl.class);

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationActivityHistory storeImmigrationActivities(ImmigrationActivityHistory immigrationActivityHistory) throws Exception {
		logger.debug("Storing ImmigrationActivityHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationActivityHistory != null) {
				session.saveOrUpdate(immigrationActivityHistory);
			}
			logger.debug("ImmigrationActivityHistory Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ImmigrationActivityHistory"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return immigrationActivityHistory;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationActivityHistory> getImmigrationActivitiesList(Long immigrationId, Long orgLocationId) throws Exception
	{
		logger.debug("Fetching Candidate Activities");
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationActivityHistory> immigrationActivityHistory = null;
		if (immigrationId != null) {
			String query = "from ImmigrationActivityHistory iah  where iah.orgLocationId.id =:orgLocationId and iah.immigrationId.id =:immigrationId ORDER BY iah.createdDate DESC";
			immigrationActivityHistory = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("immigrationId", immigrationId).list();
		}
		logger.debug("Successfully Fetched Candidate Activities");
		return immigrationActivityHistory;
	}

	
}
