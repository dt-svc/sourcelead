package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.LeaveTypeDao;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class LeaveTypeDaoImpl implements LeaveTypeDao {

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

	private static final Logger logger = LoggerFactory.getLogger(LeaveTypeDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LeaveType addLeaveType(LeaveType leaveType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (leaveType != null) {
				session.saveOrUpdate(leaveType);
			}
			logger.debug("LeaveType Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"LEAVETYPE"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return leaveType;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LeaveType> getLeaveTypeListByLocationId(String orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveType> leaveTypeList = null;
		leaveTypeList = session.createQuery("from LeaveType lt where lt.orgLocations.id =:orgLocationId and lt.status = '1'" ).setLong("orgLocationId", Long.parseLong(orgLocationId)).setCacheable(true).list();
		return leaveTypeList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LeaveType getLeaveTypeById(String leaveTypeId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveType> leaveTypeList = null;
		leaveTypeList = session.createQuery("from LeaveType lt where lt.id =:leaveTypeId" ).setString("leaveTypeId", leaveTypeId).setCacheable(true).list();
		if(leaveTypeList != null && leaveTypeList.size() > 0)
			return leaveTypeList.get(0);
		else
			return null;
	}


}
