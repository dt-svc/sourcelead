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

import com.bis.sourcelead.dao.OrglocationSignatoryDao;
import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.OrglocationSignatory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrglocationSignatoryDaoImpl implements OrglocationSignatoryDao{

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

	private static final Logger logger = LoggerFactory.getLogger(OrglocationSignatoryDaoImpl.class);

	@Override
	@Transactional
	public OrglocationSignatory saveOrglocationSignatory(OrglocationSignatory orglocationSignatory) throws Exception {
	
		logger.debug("Adding orglocationSignatory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationSignatory != null) {
				session.saveOrUpdate(orglocationSignatory);
			}
			logger.debug("orglocationSignatory Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_SIGNATORY"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orglocationSignatory;
	}
	
	
	@Override
	@Transactional
	public boolean getUserByUserId(Long userId, Long orgLocationsId) throws Exception {
		
			Session session = this.sessionFactory.getCurrentSession();
			
			String query = "from OrglocationSignatory signatory where signatory.orgLocationId.id = :orgLocationsId and signatory.userId = :userId and signatory.status=1";
			List<OrglocationSignatory> userList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong("userId", userId).list();

					if(userList.size() > 0) {
						return true;
					} else {
						return false;
					}
			 
		}
	
	@Override
	@Transactional
	public OrglocationSignatory getUserBySignatoryUuId(Long userId, String uuid, Long orgLocationsId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from OrglocationSignatory signatory where signatory.userId = :userId and signatory.orgLocationId.id = :orgLocationsId and signatory.uuid = :uuid and signatory.status = 1";
		List<OrglocationSignatory> userList =  session.createQuery(query).setString("uuid", uuid).setLong("userId", userId).setLong("orgLocationsId", orgLocationsId).list();
		OrglocationSignatory signatory = null;
		if(userList != null && !userList.isEmpty())
		{
			signatory = (OrglocationSignatory) userList.iterator().next();
		}
		return signatory;
	}

	@Override
	@Transactional
	public OrglocationSignatory getOrglocationSignatoryByOrglocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationSignatory signatory left join fetch signatory.userRoleId userRole left join fetch userRole.user left join fetch userRole.roles "
				+ " where signatory.orgLocationId.id = :orgLocationId and signatory.status = '1' ";  
		List<OrglocationSignatory> orglocationSignatoryList =  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		OrglocationSignatory orglocationSignatory = null;
		if (orglocationSignatoryList != null && orglocationSignatoryList.size() > 0) {
			orglocationSignatory = (OrglocationSignatory) orglocationSignatoryList.iterator().next();
		}
		return orglocationSignatory;
	}

	@Override
	@Transactional
	public OrglocationSignatory getOrglocationSignatoryByUUID(String uuid) throws Exception {
		

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationSignatory signatory left join fetch signatory.userRoleId userRole left join fetch userRole.user left join fetch userRole.roles "
				+ " where signatory.uuid = :uuid ";  
		List<OrglocationSignatory> orglocationSignatoryList =  session.createQuery(query).setString("uuid", uuid).list();
		OrglocationSignatory orglocationSignatory = null;
		if (orglocationSignatoryList != null && orglocationSignatoryList.size() > 0) {
			orglocationSignatory = (OrglocationSignatory) orglocationSignatoryList.iterator().next();
		}
		return orglocationSignatory;
	}
	
	@Override
	@Transactional
	public List<OrglocationSignatory> getAllSignatoriesByLocationId(Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		//String query = "from OrglocationSignatory signatory where signatory.orgLocationId.id = :locationId";
		String query="SELECT * FROM ORGLOCATION_SIGNATORY os where os.ORGLOCATION_ID=:locationId ";
		List<OrglocationSignatory> orglocationSignatoryList =  session.createSQLQuery(query).addEntity(OrglocationSignatory.class).setLong("locationId", locationId).list();
		//List<OrglocationSignatory> orglocationSignatoryList =  session.createQuery(query).setLong("locationId", locationId).list();
		return orglocationSignatoryList;
	}
}
