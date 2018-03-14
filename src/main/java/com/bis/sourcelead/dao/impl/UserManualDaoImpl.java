package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
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

import com.bis.sourcelead.dao.UserManualDao;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserManualsDTO;
import com.bis.sourcelead.entity.UserManualAttachment;
import com.bis.sourcelead.entity.UserManualLevelOne;
import com.bis.sourcelead.entity.UserManualLevelThree;
import com.bis.sourcelead.entity.UserManualLevelTwo;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class UserManualDaoImpl implements UserManualDao {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailAccountSettingsDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
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
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void storeUserManuals(UserManualLevelOne userManualLevelOne) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (userManualLevelOne != null) {
				session.saveOrUpdate(userManualLevelOne);
			}
			logger.debug("userManualLevelOne Stored Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"userManualLevelOne"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
	}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void storeUserManualstwo(UserManualLevelTwo userManualLevelTwo) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (userManualLevelTwo != null) {
				session.saveOrUpdate(userManualLevelTwo);
			}
			logger.debug("userManualLevelTwo Stored Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"userManualLevelTwo"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
	}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserManualLevelThree storeUserManualsThree(UserManualLevelThree userManualLevelThree) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (userManualLevelThree != null) {
				session.saveOrUpdate(userManualLevelThree);
			}
			logger.debug("userManualLevelThree Stored Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"userManualLevelThree"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
	}
		return userManualLevelThree;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserManualAttachment storeUserManualAttachment(UserManualAttachment userManualAttachment) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (userManualAttachment != null) {
				session.saveOrUpdate(userManualAttachment);
			}
			logger.debug("userManualAttachment Stored Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"userManualAttachment"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return userManualAttachment;
	}



	@Override
	@Transactional
	public UserManualLevelOne getUserManualByTitle(Long orgLocationsId, String title) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelOne userManualLevelOne where  userManualLevelOne.title = :title ";
		Collection<UserManualLevelOne> userManualLevelOneList = session.createQuery(query.toString()).setString("title", title).list();

		UserManualLevelOne userManualLevelOne = null;
		if (userManualLevelOneList != null && userManualLevelOneList.size() > 0) {
			userManualLevelOne = (UserManualLevelOne) userManualLevelOneList.iterator().next();
		}
		return userManualLevelOne;
	}



	@Override
	@Transactional
	public List<UserManualLevelOne> getAllUserManualls() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelOne userManualLevelOne  "; 
		List<UserManualLevelOne> UserManualLevelOneList =session.createQuery(query).list();
		return UserManualLevelOneList;
	}



	@Override
	@Transactional
	public UserManualLevelOne getUserManualById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelOne userManualLevelOne where  userManualLevelOne.id = :id ";
		Collection<UserManualLevelOne> userManualLevelOneList = session.createQuery(query.toString()).setLong("id", id).list();

		UserManualLevelOne userManualLevelOne = null;
		if (userManualLevelOneList != null && userManualLevelOneList.size() > 0) {
			userManualLevelOne = (UserManualLevelOne) userManualLevelOneList.iterator().next();
		}
		return userManualLevelOne;
	}

	@Override
	@Transactional
	public List<UserManualLevelTwo> getAllUserManuallsTwoById(Long id) throws Exception {
		//String query = "from EmailAccountSettings emailAccount  where emailAccount.userId.id =:loginUserId "; 
		//List<EmailAccountSettings> emailAccountsList =session.createQuery(query).setLong("loginUserId", loginUserId).list();
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelTwo userManualLevelTwo where userManualLevelTwo.UserManualLevelOneId.id=:id"; 
		List<UserManualLevelTwo> UserManualLevelTwoList =session.createQuery(query).setLong("id", id).list();
		return UserManualLevelTwoList;
	}

	@Override
	@Transactional
	public UserManualLevelTwo getUserManual2ByIds(Long userMan1Id, Long userMan2Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelTwo userManualLevelTwo where  userManualLevelTwo.id = :userMan2Id and userManualLevelTwo.UserManualLevelOneId.id=:userMan1Id";
		Collection<UserManualLevelTwo> userManualLevelTwoList = session.createQuery(query.toString()).setLong("userMan1Id", userMan1Id).setLong("userMan2Id", userMan2Id).list();

		UserManualLevelTwo userManualLevelTwo = null;
		if (userManualLevelTwoList != null && userManualLevelTwoList.size() > 0) {
			userManualLevelTwo = (UserManualLevelTwo) userManualLevelTwoList.iterator().next();
		}
		return userManualLevelTwo;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<UserManualLevelOne> getAllUserManuallsForDynamicList(UserManualsDTO userManualsDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<UserManualLevelOne> response = new ResponseList<>();
		
		StringBuilder query1 = new StringBuilder("");
		if(userManualsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(userManualsDTO.getSearchValue())) {
		 query1.append("and (pt.title like '%" + userManualsDTO.getSearchValue() + "%' )");
		}
		
		 String queryStr = "from UserManualLevelOne manual1" ;
		 String queryStrForCount = "Select count (*) from UserManualLevelOne  " ;
		 
		 Query query = session.createQuery(queryStr+query1);
		 List usermanualList = query.list();

		 Query countQuery = session.createQuery(queryStrForCount+query1);
		 countlist = countQuery.list();
				 
		int totalCount = 0;
		if (userManualsDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(usermanualList);
		
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<UserManualLevelTwo> getAllLevelTwoDynamicList(UserManualsDTO userManualsDTO,Long id) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<UserManualLevelTwo> response = new ResponseList<>();
		
		StringBuilder query1 = new StringBuilder("");
		if(userManualsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(userManualsDTO.getSearchValue())) {
		 query1.append("and (pt.title like '%" + userManualsDTO.getSearchValue() + "%' )");
		}
		
		 String queryStr = "from UserManualLevelTwo manual2 where manual2.UserManualLevelOneId.id=:id";
		 String queryStrForCount = "Select count (*) from UserManualLevelTwo manual2 where manual2.UserManualLevelOneId.id=:id " ;
		 
		 Query query = session.createQuery(queryStr+query1);
		 List usermanualList = query.setLong("id", id).list();

		 Query countQuery = session.createQuery(queryStrForCount+query1);
		 countlist = countQuery.setLong("id", id).list();
				 
		int totalCount = 0;
		if (userManualsDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(usermanualList);
		
		return response;
	
	}

	@Override
	@Transactional
	public ResponseList<UserManualLevelThree> getAllLevelThreeDynamicList(UserManualsDTO userManualsDTO, Long id)
			throws Exception {


		Session session = this.sessionFactory.getCurrentSession();
		
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<UserManualLevelThree> response = new ResponseList<>();
		
		StringBuilder query1 = new StringBuilder("");
		if(userManualsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(userManualsDTO.getSearchValue())) {
		 query1.append("and (pt.title like '%" + userManualsDTO.getSearchValue() + "%' )");
		}
		
		 String queryStr = "from UserManualLevelThree manual3 where manual3.UserManualLevelTwoId.id=:id";
		 String queryStrForCount = "Select count (*) from UserManualLevelThree manual3 where manual3.UserManualLevelTwoId.id=:id " ;
		 
		 Query query = session.createQuery(queryStr+query1);
		 List usermanualList = query.setLong("id", id).list();

		 Query countQuery = session.createQuery(queryStrForCount+query1);
		 countlist = countQuery.setLong("id", id).list();
				 
		int totalCount = 0;
		if (userManualsDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(usermanualList);
		
		return response;
	
	
	}

	@Override
	@Transactional
	public UserManualLevelOne getLevelOneByUUId(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelOne userManualLevelOne where  userManualLevelOne.levelOneUUID = :id ";
		Collection<UserManualLevelOne> userManualLevelOneList = session.createQuery(query.toString()).setString("id", id).list();

		UserManualLevelOne userManualLevelOne = null;
		if (userManualLevelOneList != null && userManualLevelOneList.size() > 0) {
			userManualLevelOne = (UserManualLevelOne) userManualLevelOneList.iterator().next();
		}
		return userManualLevelOne;
	}

	@Override
	@Transactional
	public UserManualLevelTwo getLevelTwoByUUId(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelTwo userManualLevelTwo where  userManualLevelTwo.levelTwoUUID = :id ";
		Collection<UserManualLevelTwo> userManualLevelTwoList = session.createQuery(query.toString()).setString("id", id).list();

		UserManualLevelTwo userManualLevelTwo = null;
		if (userManualLevelTwoList != null && userManualLevelTwoList.size() > 0) {
			userManualLevelTwo = (UserManualLevelTwo) userManualLevelTwoList.iterator().next();
		}
		return userManualLevelTwo;
	}

	@Override
	@Transactional
	public UserManualLevelThree getLevelThreeByUUId(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelThree userManualLevelThree where  userManualLevelThree.levelThreeUUID = :id ";
		Collection<UserManualLevelThree> userManualLevelThreeList = session.createQuery(query.toString()).setString("id", id).list();

		UserManualLevelThree userManualLevelThree = null;
		if (userManualLevelThreeList != null && userManualLevelThreeList.size() > 0) {
			userManualLevelThree = (UserManualLevelThree) userManualLevelThreeList.iterator().next();
		}
		return userManualLevelThree;
	}

	@Override
	@Transactional
	public UserManualLevelTwo getUserManualTwoById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelTwo userManualLevelTwo where  userManualLevelTwo.id = :id ";
		Collection<UserManualLevelTwo> userManualLevelTwoList = session.createQuery(query.toString()).setLong("id", id).list();

		UserManualLevelTwo userManualLevelTwo = null;
		if (userManualLevelTwoList != null && userManualLevelTwoList.size() > 0) {
			userManualLevelTwo = (UserManualLevelTwo) userManualLevelTwoList.iterator().next();
		}
		return userManualLevelTwo;
	}

	@Override
	@Transactional
	public UserManualLevelThree getUserManualThreeById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualLevelThree userManualLevelThree where  userManualLevelThree.id = :id ";
		Collection<UserManualLevelThree> userManualLevelThreeList = session.createQuery(query.toString()).setLong("id", id).list();

		UserManualLevelThree userManualLevelThree = null;
		if (userManualLevelThreeList != null && userManualLevelThreeList.size() > 0) {
			userManualLevelThree = (UserManualLevelThree) userManualLevelThreeList.iterator().next();
		}
		return userManualLevelThree;
	}

	@Override
	@Transactional
	public void deleteUserManualThree(UserManualLevelThree userManualLevelThree) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (userManualLevelThree != null) {
				session.delete(userManualLevelThree);
			}
			logger.debug("userManualLevelThree deleted Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"userManualLevelThree"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}

	}

	@Override
	@Transactional
	public void deleteUserManualTwo(UserManualLevelTwo userManualLevelTwo) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (userManualLevelTwo != null) {
				session.delete(userManualLevelTwo);
			}
			logger.debug("userManualLevelTwo deleted Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"userManualLevelTwo"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@Override
	@Transactional
	public void deleteUserManualOne(UserManualLevelOne UserManualLevelOne) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (UserManualLevelOne != null) {
				session.delete(UserManualLevelOne);
			}
			logger.debug("UserManualLevelOne deleted Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"UserManualLevelOne"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserManualAttachment> getUsermanualAttachmentList(Long levelThreeId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualAttachment uma where uma.userManualId =:levelThreeId  "; 
		List<UserManualAttachment> userManualAttachmentList =session.createQuery(query).setLong("levelThreeId", levelThreeId).list();
		return userManualAttachmentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public UserManualAttachment getUserManualAttchListById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserManualAttachment uma where uma.id =:id  "; 
		List<UserManualAttachment> userManualAttachmentList =session.createQuery(query).setLong("id", id).list();
		UserManualAttachment userManualAttachment = null;
		if (userManualAttachmentList != null && userManualAttachmentList.size() > 0) {
			userManualAttachment = (UserManualAttachment) userManualAttachmentList.iterator().next();
		}
		return userManualAttachment;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void deleteUserManualAttachments(UserManualAttachment UserManualAttachments) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (UserManualAttachments != null) {
				session.delete(UserManualAttachments);
			}
			logger.debug("UserManualAttachments deleted Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"UserManualAttachments"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}

		
	}

	
	
}
