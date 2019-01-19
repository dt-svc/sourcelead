package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ReferContactsDaoImpl implements ReferContactsDao {

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

	private static final Logger logger = LoggerFactory.getLogger(ReferContactsDaoImpl.class);

	@Override
	@Transactional
	public ReferContacts addReferContacts(ReferContacts referContacts) throws Exception {
		logger.debug("Adding addReferContacts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (referContacts != null) {
				session.saveOrUpdate(referContacts);
			}
			logger.debug("addReferContacts Added Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "REFER_CONTACTS"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
		return referContacts;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveAllReferContacts(List<ReferContacts> referContactsList) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(referContactsList != null && !referContactsList.isEmpty()){
				for(ReferContacts referContacts : referContactsList){
					if(referContacts.getContactUserEmail() != null){
						ReferContacts refContact = getReferContactsByEmails(referContacts.getReferFromUser().getId(),referContacts.getContactUserEmail());
						if(refContact == null)
						{
							if(
								    (StringUtils.isEmpty(referContacts.getFirstName()) && StringUtils.isEmpty(referContacts.getLastName()))
							     || (StringUtils.isNotEmpty(referContacts.getFirstName()) && (isNameContainsSpecialCharacters(referContacts.getFirstName()) || referContacts.getFirstName().equalsIgnoreCase("null")))
							     || (StringUtils.isNotEmpty(referContacts.getLastName()) && (isNameContainsSpecialCharacters(referContacts.getLastName()) || referContacts.getLastName().equalsIgnoreCase("null")))
							){
								referContacts.setFirstName(getNameFromEmail(referContacts.getContactUserEmail()));
								referContacts.setLastName("");
							}
							
							session.saveOrUpdate(referContacts);
						}
					}
					
					
					
				}
				
			}
			
		}
		catch(Exception exception)	{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REFER_CONTACTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}
	
	protected boolean isNameContainsSpecialCharacters(String name){
		 Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
	      Matcher matcher = pattern.matcher(name);
	      if (!matcher.matches()) {
	    	  return true;
	      } else {
	    	  return false;
	      }
	}
	protected String getNameFromEmail(String email){
		String name = "";
		 if(StringUtils.isNotEmpty(email)){
			 String[] For_split_email=email.split("[@]");
			 if(For_split_email != null && For_split_email.length >0){
				 name = For_split_email[0];  
			 }
		 }
		 return name;
	}
	

	@Override
	@Transactional
	public ReferContacts getReferContactsByUUID(String contactUUID) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<ReferContacts> referContacts = new ArrayList<ReferContacts>();
		logger.debug("getReferContactsByUUID start");
		referContacts = session
				.createQuery(
						"from ReferContacts contacts left join fetch contacts.referFromUser where contacts.contactUserUUID =:contactUUID")
				.setString("contactUUID", contactUUID).list();
		logger.debug("getReferContactsByUUID end");
		if (referContacts.size() > 0) {
			return referContacts.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public ReferContacts getReferContactsByEmails(Long userId, String toEmail) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ReferContacts> referContacts = new ArrayList<ReferContacts>();
		logger.debug("getReferContactsByEmails start");
		referContacts = session
				.createQuery("from ReferContacts contacts left join fetch contacts.referFromUser user where user.id =:userId and contacts.contactUserEmail = :toEmail")
				.setLong("userId", userId).setString("toEmail", toEmail).list();
		logger.debug("getReferContactsByEmails end");
		if (referContacts.size() > 0) {
			return referContacts.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ResponseList<ReferContactsDTO> getReferContactsListByUserIdAndOrglocationId(ReferContactsDTO contactsDTO,Long userId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Query queryObj = null;
		String query = null;
		String subQuery="";
		Integer filterCount = 0;
		StringBuilder query1 = new StringBuilder("");
		ResponseList<ReferContactsDTO> response = new ResponseList<>();
		List<ReferContactsDTO> contactsDTOList = new ArrayList<>();
		List<Long> groupIdsList = new ArrayList<>();
		List<Long> countlist = null;
		
		if(contactsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(contactsDTO.getSearchValue())) {
			 query1.append(" contacts.contactUserEmail like '%" + contactsDTO.getSearchValue() + "%' ")
					.append("OR contacts.firstName like '%" + contactsDTO.getSearchValue() + "%' ")
					.append("OR contacts.lastName like '%" + contactsDTO.getSearchValue() + "%' ")
					.append("OR contacts.contactUserPhone like '%" + contactsDTO.getSearchValue() + "%' ")
					.append("OR (concat( contacts.firstName, contacts.lastName) like '%" + contactsDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')" )
					.append("OR contacts.companyName like '%" + contactsDTO.getSearchValue() + "%' ")
					.append("OR contacts.contactSource like '%" + contactsDTO.getSearchValue() + "%' ");
						
			}
		
		if(contactsDTO.getGroupIdsList() != null && !contactsDTO.getGroupIdsList().isEmpty()){
			String[] groupIds = contactsDTO.getGroupIdsList().split(",");
			for(int i=0; i < groupIds.length; i++){
				groupIdsList.add(Long.parseLong(groupIds[i]));
			}
			
			query = "from  ReferContacts contacts  where contacts.referFromUser.id =:userId and "
					+ "contacts.id IN (select cl.id from ContactsGroup cg join cg.contactsList cl where cg.id IN (:groupIdsList))";
		}else{
			query = "from  ReferContacts contacts  where contacts.referFromUser.id =:userId ";
		}
		
		//for filtering
		
		if(contactsDTO.getContactType() != null && !contactsDTO.getContactType().isEmpty()) {
			String[] contactType = contactsDTO.getContactType().split(",");
			if (Arrays.asList(contactType).contains("Candidate")) {
				subQuery =  "contacts.candidateType = '1' or ";
			}
			if (Arrays.asList(contactType).contains("Client")) {
				subQuery = subQuery + "contacts.clientType = '1' or ";
			}
			if (Arrays.asList(contactType).contains("Vendor")) {
				subQuery = subQuery + "contacts.vendorType = '1' or ";
			}
			if (Arrays.asList(contactType).contains("Other")) {
				subQuery = subQuery + "contacts.otherType = '1' or ";
			}
		}
		
		if(subQuery !=null && subQuery !=""){
			subQuery = "and (" + subQuery.substring(0, subQuery.length() - 4) + ")";
		query=query+subQuery;
		}
		if(query1.toString() != null && !query1.toString().isEmpty() ){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")");
		}else {
			 queryObj = session.createQuery(query + query1.toString());
		}
		
		queryObj.setFirstResult(contactsDTO.getStart());
		queryObj.setMaxResults(contactsDTO.getLength());
		
		if(groupIdsList != null && groupIdsList.size() > 0){
			queryObj.setLong("userId", userId).setParameterList("groupIdsList", groupIdsList).list();
			countlist = session.createQuery("select count(*) " + query).setLong("userId", userId).setParameterList("groupIdsList", groupIdsList).list();
		}else{
			queryObj.setLong("userId", userId).list();
			countlist = session.createQuery("select count(*) " + query).setLong("userId", userId).list();
		}
		List<ReferContacts> referContactsList = queryObj.list();
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			filterCount = referContactsList.size();
		}
		
        int totalCount = 0;

		
		if (contactsDTO != null) {
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
		
		if(referContactsList != null && referContactsList.size() > 0) {
  			for(ReferContacts contacts : referContactsList) {
  				ReferContactsDTO referContactsDTO = new ReferContactsDTO();
  				referContactsDTO.setFullName(contacts.getFullName());
  				referContactsDTO.setEmail(contacts.getContactUserEmail());
  				referContactsDTO.setCompanyName(contacts.getCompanyName());
  				if(contacts.getContactSource() != null){
  					referContactsDTO.setSource(contacts.getContactSource().toString());
  				}
  				
  				referContactsDTO.setId(contacts.getId());
  				referContactsDTO.setPhone(contacts.getContactUserPhone());
  				referContactsDTO.setContactType(contacts.getContactType());
  				List<ContactsGroup> groupList = contacts.getGroupList();
  				
  				if(groupList != null && groupList.size() > 0 ){
  					List<String> groupsList = new ArrayList<>();
  					for(ContactsGroup contactsGroup : groupList){
  	  					groupsList.add(contactsGroup.getGroupName());
  					}
  					referContactsDTO.setGroupsList(groupsList);
  				}
  				contactsDTOList.add(referContactsDTO);
  			}
  		}
		
		
		response.setData(contactsDTOList);
		return response;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ReferContacts> getUserContactsListByUserIdAndRequirementId(Long userId, Long requirementId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ReferContacts contacts  where contacts.referFromUser.id = :userId and "
				+ "contacts.contactUserEmail NOT IN (select refer.referToEmail from RequirementRefer refer where refer.referFromUser.id = :userId and refer.requirementId.id = :requirementId) ";
		List<ReferContacts> referContactsList  =   session.createQuery(query.toString()).setLong("userId", userId).setLong("requirementId", requirementId) .list();
		return referContactsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ReferContacts getReferContactsById(Long id) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<ReferContacts> referContacts = new ArrayList<ReferContacts>();
		logger.debug("getReferContactsById start");
		referContacts = session
				.createQuery(
						"from ReferContacts contacts left join fetch contacts.referFromUser where contacts.id =:id")
				.setLong("id", id).list();
		logger.debug("getReferContactsById end");
		if (referContacts.size() > 0) {
			return referContacts.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Boolean validateGoogleEamil(String email, Long loginUserId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(email != null && loginUserId != null) {
			 String query = "from ReferContacts referContacts where referContacts.referFromUser.id = :loginUserId and referContacts.contactUserEmail = :email ";
				Collection<ReferContacts> referContactsList = session.createQuery(query.toString()).setLong("loginUserId", loginUserId).setString("email", email).list();
				if (referContactsList != null && referContactsList.size() > 0) {
					flag = true;
				}
		 }
		 return flag;
	}

	@Override
	@Transactional
	public ReferContacts findByEmail(String recipientMail) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<ReferContacts> referContacts = new ArrayList<ReferContacts>();
		logger.debug("getReferContactsByEmails start");
		referContacts = session
				.createQuery("from ReferContacts contacts where contacts.contactUserEmail = :recipientMail")
				.setString("recipientMail", recipientMail).list();
		logger.debug("getReferContactsByEmails end");
		if (referContacts.size() > 0) {
			return referContacts.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public ReferContacts getReferContactsByFromUserIdAndToUserId(Long fromUserId, Long toUserId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ReferContacts> referContacts = new ArrayList<ReferContacts>();
		logger.debug("getReferContactsByFromUserIdAndToUserId start");
		referContacts = session
				.createQuery("from ReferContacts contacts left join fetch contacts.referFromUser fromUser left join fetch contacts.contactUser toUser "
						+ " where fromUser.id = :fromUserId and toUser.id = :toUserId ")
				.setLong("fromUserId", fromUserId).setLong("toUserId", fromUserId).list();
		logger.debug("getReferContactsByFromUserIdAndToUserId end");
		if (referContacts.size() > 0) {
			return referContacts.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public ReferContacts deleteContactById(ReferContacts referContacts) throws Exception {
		logger.debug("Delete ReferContacts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (referContacts != null) {
				session.delete(referContacts);
			}
			logger.debug("addReferContacts deleted Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "REFER_CONTACTS"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
		return referContacts;
	}

}
