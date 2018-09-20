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

import com.bis.sourcelead.dao.UserMailBoxDao;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class UserMailBoxDaoImpl implements UserMailBoxDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(UserMailBoxDaoImpl.class);

	@Override
	@Transactional
	public UserMailBox saveUserMailBox(UserMailBox userMailBox) throws Exception {
		
		logger.debug("Storing saveUserMailBox");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userMailBox != null) {
				session.saveOrUpdate(userMailBox);
			}
			logger.debug("userMailBox Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"userMailBox"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return userMailBox;
		
	}

	@Override
	@Transactional
	public List<UserMailBox> getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(Long userId, Long orgLocationId,
			String mailStatus) throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from UserMailBox mailbox where mailbox.userId.id = :userId and mailbox.orgLocationId.id = :orgLocationId "
		 		+ "and mailbox.mailStatus = :mailStatus and mailbox.status = '1' ORDER BY mailbox.id DESC ";
		 List<UserMailBox> mailBoxList = session.createQuery(query.toString()).setLong( "userId", userId)
				 .setLong( "orgLocationId", orgLocationId).setString("mailStatus", mailStatus).list();
		 return mailBoxList;
	}

	@Override
	@Transactional
	public UserMailBox getUserMailBoxByUUID(String UUID) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserMailBox> mailBox = new ArrayList<UserMailBox>();
		logger.debug("getUserMailBoxByUUID start");
		mailBox = session.createQuery("from UserMailBox mailBox where mailBox.uuid = :uuid" ).setString("uuid", UUID).list();
		logger.debug("getUserMailBoxByUUID end");
		if (mailBox.size() > 0) {
			return mailBox.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public UserMailBox getUserMailBoxById(String Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserMailBox> mailBox = new ArrayList<UserMailBox>();
		logger.debug("getUserMailBoxById start");
		mailBox = session.createQuery("from UserMailBox mailBox where mailBox.id = :id" ).setString("id", Id).list();
		logger.debug("getUserMailBoxById end");
		if (mailBox.size() > 0) {
			return mailBox.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public List<UserMailBox> getUserFavouriteMailsByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from UserMailBox mailbox where mailbox.userId.id = :userId and mailbox.orgLocationId.id = :orgLocationId "
		 		+ "and mailbox.isFavourite = '1' and mailbox.status = '1' ORDER BY mailbox.id DESC ";
		 List<UserMailBox> mailBoxList = session.createQuery(query.toString()).setLong( "userId", userId)
				 .setLong( "orgLocationId", orgLocationId).list();
		 return mailBoxList;
		
	}

	@Override
	@Transactional
	public Integer getUnReadMessagesByUserIdAndLocationIdAndType(Long userId, Long orgLocationId, String type)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from UserMailBox mailbox where mailbox.userId.id = :userId and mailbox.orgLocationId.id = :orgLocationId and mailbox.mailStatus = :mailStatus and mailbox.isMailRead = '0' ";
		List<Long> unreadMailsCount = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setString("mailStatus", type).list();
			Integer count = 0;
			if(unreadMailsCount != null && !unreadMailsCount.isEmpty()){
					count = unreadMailsCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public UserMailBox getUserPreviousMail(Long Id, Long userId, Long orgLocationId,String type,String mailType) throws Exception {
		
		String selectedMode = "";
		String subQuery = "";
		
		if(mailType.equalsIgnoreCase("mails")){
			subQuery = " and mb.mailType = '1'";
		}else{
			subQuery = " and mb.mailType = '2'";
		}
		
		if(!type.equalsIgnoreCase("Starred")){
			selectedMode = "select max(mb.id) from UserMailBox mb where mb.id < :id "
					+ "and mb.userId.id = :userId and mb.orgLocationId.id = :orgLocationId and "
					+ "mb.mailStatus = '"+type+"'  "+subQuery+" ";
		}else{
			selectedMode = "select max(mb.id) from UserMailBox mb where mb.id < :id "
					+ "and mb.userId.id = :userId and mb.orgLocationId.id = :orgLocationId and "
					+ "mb.isFavourite = '1'  "+subQuery+" ";
		}
		
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserMailBox> mailBox = new ArrayList<UserMailBox>();
		logger.debug("getUserMailBoxById start");
		mailBox = session.createQuery("from UserMailBox mailBox where mailBox.id IN ("+selectedMode+")" ).setLong("id", Id).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
		logger.debug("getUserMailBoxById end");
		if (mailBox.size() > 0) {
			return mailBox.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional
	public UserMailBox getUserNextMail(Long Id, Long userId, Long orgLocationId,String type,String mailType) throws Exception {
		
		String selectedMode = "";
		String subQuery = "";
		
		if(mailType.equalsIgnoreCase("mails")){
			subQuery = " and mb.mailType = '1'";
		}else{
			subQuery = " and mb.mailType = '2'";
		}
		
		if(!type.equalsIgnoreCase("Starred")){
		selectedMode = "select min(mb.id) from UserMailBox mb where mb.id > :id "
				+ "and mb.userId.id = :userId and mb.orgLocationId.id = :orgLocationId and "
				+ "mb.mailStatus = '"+type+"' "+subQuery+" ";
		}else{
			selectedMode = "select min(mb.id) from UserMailBox mb where mb.id > :id "
					+ "and mb.userId.id = :userId and mb.orgLocationId.id = :orgLocationId and "
					+ "mb.isFavourite = '1' "+subQuery+" ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserMailBox> mailBox = new ArrayList<UserMailBox>();
		logger.debug("getUserMailBoxById start");
		mailBox = session.createQuery("from UserMailBox mailBox where mailBox.id IN ("+selectedMode+")" ).setLong("id", Id).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
		logger.debug("getUserMailBoxById end");
		if (mailBox.size() > 0) {
			return mailBox.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Long getMailBoxCountByUserIdAndOrgLocationid(Long userId, Long orgLocationId, String type) throws Exception {
		logger.debug("..getMailBoxCountByUserIdAndOrgLocationid() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();
		
		if(!type.equalsIgnoreCase("Starred")){
		
			String query = "select count(*) from UserMailBox mailbox where mailbox.userId.id = :userId and mailbox.orgLocationId.id = :orgLocationId "
			 		+ "and mailbox.mailStatus = :mailStatus and mailbox.status = '1' ";
		List<Long> results = session.createQuery(query).setLong( "orgLocationId", orgLocationId).setLong("userId", userId).setString("mailStatus", type).list();
		logger.debug("..getMailBoxCountByUserIdAndOrgLocationid() Method Ended..");
		return results.get(0);
			 
		}else{
			String query = "select count(*) from UserMailBox mailbox where mailbox.userId.id = :userId and mailbox.orgLocationId.id = :orgLocationId "
		 		+ "and mailbox.isFavourite = '1' and mailbox.status = '1' ";
			List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
			logger.debug("..getMailBoxCountByUserIdAndOrgLocationid() Method Ended..");
			return results.get(0);
		}
		
		
		
	}

}
