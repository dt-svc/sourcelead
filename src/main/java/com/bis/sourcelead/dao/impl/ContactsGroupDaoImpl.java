package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ContactsGroupDao;
import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ContactsGroupDaoImpl implements ContactsGroupDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ReferContactsDao referContactsDao;

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

	private static final Logger logger = LoggerFactory.getLogger(ContactsGroupDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContactsGroup saveContactsGroup(ContactsGroup contactsGroup) throws Exception
	{
		logger.debug("Storing ContactsGroup");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contactsGroup != null) {
				session.saveOrUpdate(contactsGroup);
			}
			logger.debug("ContactsGroup Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ContactsGroup"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return contactsGroup;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ContactsGroup> getContactsGroupListByUserId(Long userId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ContactsGroup cg  left join fetch cg.createdUser user where user.id =:userId and cg.status ='1'";
		List<ContactsGroup> contactsGroupList  =   session.createQuery(query.toString()).setLong("userId", userId) .list();
		return contactsGroupList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContactsGroup getContactsGroupByGroupId(Long groupId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ContactsGroup cg  where cg.id =:groupId";
		List cgList = session.createQuery(query).setLong("groupId", groupId).list();
		ContactsGroup contactsGroup = null;
		if(cgList != null && !cgList.isEmpty())
		{
			contactsGroup = (ContactsGroup) cgList.iterator().next();
		}
		return contactsGroup;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContactsGroup deleteContactsGroupId(Long groupId) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveContactsReferContactGroup(Long groupId, String contactsList) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		String[] contactIds = contactsList.split(",");
		ContactsGroup contactsGroup = getContactsGroupByGroupId(groupId);
		
		for (int i = 0; i < contactIds.length; i++) {
			
			ReferContacts referContacts = referContactsDao.getReferContactsById(Long.valueOf(contactIds[i]));
			List<ReferContacts> refersList = contactsGroup.getContactsList();
			if(!refersList.contains(referContacts)){
				refersList.add(referContacts);
				contactsGroup.setContactsList(refersList);
			}
		}
		
		try {
			if (contactsGroup != null) {
				session.saveOrUpdate(contactsGroup);
			}
			logger.debug("ContactsGroup Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ContactsGroup"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteContactsGroup(ContactsGroup contactsGroup) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (contactsGroup != null) {
				session.delete(contactsGroup);
			}
			logger.debug("ContactsGroup Deleted Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ContactsGroup"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContactsGroup findByContactsGroupAndUserId(String groupName, Long userId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<ContactsGroup> contactsGroup = new ArrayList<ContactsGroup>();
		logger.debug("findByContactsGroupAndUserId start");
		try{
			contactsGroup = session.createQuery("from ContactsGroup cg where cg.groupName = :groupName and cg.createdUser.id = :userId")
					.setString( "groupName", groupName ).setLong("userId", userId ).list();
			
			logger.debug("findByCandidateEmail end");
			if (contactsGroup.size() > 0) {
				return contactsGroup.get(0);
			} else {
				return null;
			}
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContactsGroup validateGrouppNameByGroupNameAndGroupId(String groupName, Long groupid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ContactsGroup cg  where cg.id =:groupid and cg.groupName =:groupName ";
		List<ContactsGroup> contactsGroupList =session.createQuery(query) .setString("groupName", groupName).setLong("groupid", groupid).list();
		ContactsGroup contactsGroup = null;
		if(contactsGroupList != null && !contactsGroupList.isEmpty())
		{
			contactsGroup = (ContactsGroup) contactsGroupList.iterator().next();
		}
		return contactsGroup;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateGroupNameByGroupNameAndUserId(String groupName, Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from ContactsGroup cg  where cg.groupName =:groupName and cg.createdUser.id =:userId ";
				Collection<ContactsGroup> contactsGroupList = session.createQuery(query) .setString("groupName", groupName).setLong("userId", userId).list();
				if (contactsGroupList != null && contactsGroupList.size() > 0) {
					flag = true;
				}
		return flag;
	}


}
