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
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.LeaveTypeHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
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
		leaveTypeList = session.createQuery("from LeaveType lt where lt.orgLocations.id =:orgLocationId" ).setLong("orgLocationId", Long.parseLong(orgLocationId)).list();
		return leaveTypeList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LeaveType getLeaveTypeById(Long leaveTypeId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveType> leaveTypeList = null;
		leaveTypeList = session.createQuery("from LeaveType lt where lt.id =:leaveTypeId" ).setLong("leaveTypeId", leaveTypeId).list();
		if(leaveTypeList != null && leaveTypeList.size() > 0)
			return leaveTypeList.get(0);
		else
			return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LeaveType> getLeaveTypeListByLocationIdAndStatus(String orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveType> leaveTypeList = null;
		leaveTypeList = session.createQuery("from LeaveType lt where lt.orgLocations.id =:orgLocationId and lt.status = '1'" ).setLong("orgLocationId", Long.parseLong(orgLocationId)).list();
		return leaveTypeList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LeaveType getLeaveTypeByUUID(String leaveTypeUUID)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveType> leaveTypeList = null;
		leaveTypeList = session.createQuery("from LeaveType lt where lt.leaveTypeUUID =:leaveTypeUUID" ).setString("leaveTypeUUID", leaveTypeUUID).list();
		if(leaveTypeList != null && leaveTypeList.size() > 0){
			return leaveTypeList.get(0);
		}else{
			return null;
		}
		
	}
 
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getProjectTaskByorgLocationIdAndLeaveTypeId(Long orgLocationId, Long leaveTypeId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query="from ProjectTasks proTask left join fetch proTask.leaveType leavetype left join fetch leavetype.orgLocations orgLoc  where leavetype.id = :leaveTypeId and orgLoc.id = :orgLocationId";
		List<ProjectTasks> projectTasks = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("leaveTypeId", leaveTypeId).list();
		return projectTasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LeaveType> getLeaveTypesByTypeList(List<String> typeList, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveType> leaveTypeList = null;
		leaveTypeList = session.createQuery("from LeaveType lt where lt.orgLocations.id =:orgLocationId and lt.status = '1' and lt.name IN (:typeList)" ).setLong("orgLocationId", orgLocation.getId()).setParameterList("typeList", typeList).list();
		return leaveTypeList;
	}

	@Override
	@Transactional
	public LeaveType getLeaveTypeByLeaveTypeName(String leaveTypeName, OrgLocations orgLocation)
	{
		LeaveType leaveType = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<LeaveType> leaveTypeList = session.createQuery("from LeaveType lt where lt.orgLocations.id =:orgLocationId and lt.status = '1' and lt.name = :leaveTypeName").setLong("orgLocationId", orgLocation.getId()).setString("leaveTypeName", leaveTypeName).list();
			if (leaveTypeList != null && !leaveTypeList.isEmpty()) {
				leaveType = leaveTypeList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveType;
	}

	@Override
	@Transactional
	public List<String> getLeaveTypeNameListByLocationId(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select leaveType.name from LeaveType leaveType where leaveType.orgLocations.id = :orgLocationId"; 
		List<String> leaveTypesNamesList =  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		return leaveTypesNamesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void storeLeaveTypeHistory(LeaveTypeHistory lt) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (lt != null) {
				session.saveOrUpdate(lt);
			}
			logger.debug("LeaveType Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"LEAVETYPEHISTORY"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
