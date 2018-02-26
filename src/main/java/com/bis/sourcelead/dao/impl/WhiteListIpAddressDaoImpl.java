package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.WhiteListIpAddressDao;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.WhiteListIPAddress;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class WhiteListIpAddressDaoImpl implements WhiteListIpAddressDao {

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

	private static final Logger logger = LoggerFactory.getLogger(WhiteListIpAddressDaoImpl.class);

	@Transactional(rollbackFor = { Exception.class })
	public WhiteListIPAddress addWhiteListIps(WhiteListIPAddress whiteListIPAddress) throws Exception {
		logger.debug("Adding orgLocations");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (whiteListIPAddress != null) {
				session.saveOrUpdate(whiteListIPAddress);
			}
			logger.debug("orgLocations Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return whiteListIPAddress;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WhiteListIPAddress> getWhiteListIpsByLocationId(Long locationId,Long whiteListIpId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<WhiteListIPAddress> ipList = null;
		if(whiteListIpId != null){
			String query = "from WhiteListIPAddress wlip left join fetch wlip.orgLocation left join fetch wlip.createdBy left join fetch wlip.updatedBy where wlip.orgLocation.id = :locationId and wlip.status = 'Active' and wlip.id <> :whiteListIpId";
			ipList = session.createQuery(query).setLong("locationId", locationId).setLong("whiteListIpId", whiteListIpId).list();
		}else{
			String query = "from WhiteListIPAddress wlip left join fetch wlip.orgLocation left join fetch wlip.createdBy left join fetch wlip.updatedBy where wlip.orgLocation.id = :locationId and wlip.status = 'Active'";
			ipList = session.createQuery(query).setLong("locationId", locationId).list();
		}
		if(ipList != null && ipList.size() >0){
			return ipList;
		}
		return null;
	}

	@Override
	@Transactional
	public WhiteListIPAddress getWhiteListIpsById(Long whiteListIpId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from WhiteListIPAddress wlip left join fetch wlip.orgLocation where wlip.id = :whiteListIpId";
		List<WhiteListIPAddress> ipList = session.createQuery(query).setLong("whiteListIpId", whiteListIpId).list();
		if(ipList != null && ipList.size() >0){
			return ipList.get(0);
		}
		return null;
	}
	
}
