package com.bis.sourcelead.dao.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */
@Repository
public class OrgDaoImpl implements OrgDao {
	private static final Logger logger = LoggerFactory.getLogger(OrgDaoImpl.class);
	
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
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org addOrganization(Org org) throws Exception
	{
		logger.debug("Adding Organization");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (org != null) {
				session.saveOrUpdate(org);
			}
			logger.debug("Organization Added Successfully");
		}catch(ConstraintViolationException exception)
			{
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
		return org;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org updateOrg(Org org) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		if (org != null) {
			session.saveOrUpdate(org);
		}
		if(org == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"ORG"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		
		
		return org;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org findOrgByID(Long id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("findOrgByID start");
		List<Org> orgList = session.createQuery("from Org where id=:id" ).setLong( "id", id ).setCacheable(true).list();
		return orgList.get(0);
		}
		
		
		//return org;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteOrg(Long id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		if (id != null) {
			Org org = (Org) session.get(Org.class, id);
			org.setStatus(Status.INACTIVE);
			session.saveOrUpdate(org);
			isDeleted = true;
			logger.info("Successfully deleted..!");
		}if(id == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ORG"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		return isDeleted;
	}
	
	@Override
    @Transactional(rollbackFor = { Exception.class })
    public String websiteValidation(String webSite,String sourceleadBLockedwebsites) {
        Org orgList =null;
                Session session = this.sessionFactory.getCurrentSession();
                Criteria crit = session.createCriteria(Org.class);
                  String flag = null;
                 
                 if(!webSite.startsWith("http") && !webSite.startsWith("https")){
                        webSite = "http://" + webSite;
                    }    
                    String status = null;
                    URL netUrl = null;
                    try {
                        netUrl = new URL(webSite);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();;
                    }
                    String host = netUrl.getHost();
                    if(host.startsWith("www.")){
                        if(host.length()>3){
                            host = host.substring("www".length()+1);
                        }else{
                            flag="Website Duplicated";
                        }
                    }else{
                        flag ="Invalid Website";
                    }
                   
                    //for Blocked Websites Domains
                    String[] SourceLead_BLOCKED_SOCIAL_DOMAINS = sourceleadBLockedwebsites.split(",");
                    if(Arrays.asList(SourceLead_BLOCKED_SOCIAL_DOMAINS).contains(host)){
                    	 flag ="Invalid Website";
                    	 
                     }
                    
                    return flag;
                    
                   /* boolean isFuond = isDuplicateWebsite(host);
                    if(isFuond){
                         flag ="Website Duplicated";
                         return flag;
                    }else{
                    	flag = "Valid Website";
                    	return flag;
                    }*/
        
    }   

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Org> getOrgs() throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Org.class);
		List<Org> orgList = crit.setCacheable(true).list();
		return orgList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org getOrgByOrgCode(String orgCode) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Org> orgList;
		logger.debug("getOrgByOrgCode start");
		orgList = session.createQuery("from Org where orgCode=:orgCode" ).setString( "orgCode", orgCode ).setCacheable(true).list();
		logger.debug("getOrgByOrgCode end");
		 
		if (orgList != null && orgList.size() > 0) {
			return orgList.get(0);
		} else {
			return null;
		}
   	}
	
	private boolean isDuplicateWebsite(String host) {
        boolean isWebSiteFound = false;
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from Org org where org.webSite = :host";
         List<Org> orglist = session.createQuery(query.toString()).setString("host", host).setCacheable(true).list();
            if (orglist != null && orglist.size() >0) {
                isWebSiteFound = true;
            }
            return isWebSiteFound;
    }


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean isClientNameValid(String clientName,Long orgLocationId,String responderType) throws Exception {
		   boolean isClientFound = false;
	        Session session = this.sessionFactory.getCurrentSession();
	        /*String query = "from Org org where org.orgName = :clientName and org.id IN(Select orgRelations.responderLocationId.org.id from OrgRelations orgRelations "
	        		+ "where orgRelations.locationId.id = :orgLocationId )";*/
	        
	     String query ="from Org org where org.orgName = :clientName and (org.id IN(Select orgrelation.responderLocationId.org.id  from OrgRelations orgrelation "
	    		+"  where orgrelation.locationId.id = :orgLocationId and orgrelation.responderType = :responderType) OR "
	    		+"org.id IN(Select orl.locationId.org.id from OrgRelations orl"
	    		+" where orl.responderLocationId.id = :orgLocationId))";
	        
	        
	         List<Org> orglist = session.createQuery(query.toString()).setString("clientName", clientName).setString("responderType", responderType).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	            if (orglist != null && !orglist.isEmpty()) {
	            	isClientFound = true;
	            }
	            return isClientFound;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public  List<Org> isSpocExistedTheOwner(String clientName,Long orgLocationId,String responderType) throws Exception {
		   boolean isClientFound = false;
	        Session session = this.sessionFactory.getCurrentSession();
	        /*String query = "from Org org where org.orgName = :clientName and org.id IN(Select orgRelations.responderLocationId.org.id from OrgRelations orgRelations "
	        		+ "where orgRelations.locationId.id = :orgLocationId )";*/
	        
	     String query ="from Org org where org.orgName = :clientName and (org.id IN(Select orgrelation.responderLocationId.org.id  from OrgRelations orgrelation "
	    		+"  where orgrelation.locationId.id = :orgLocationId and orgrelation.responderType = :responderType) OR "
	    		+"org.id IN(Select orl.locationId.org.id from OrgRelations orl"
	    		+" where orl.responderLocationId.id = :orgLocationId))";
	        
	        
	     return  session.createQuery(query.toString()).setString("clientName", clientName).setString("responderType", responderType).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Org> getEnrolledOrgs(Long orgId,String userState,String searchByName) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Org> orgsList = null;
		String userStateSubQuery = "";
		if (!isNullOrEmpty(userState) && !userState.equalsIgnoreCase("ALL")) {
			userStateSubQuery = "and org.id IN (select orgLoactions.org.id from OrgLocations orgLoactions where orgLoactions.isPrimary = 'Y' and "
					+ " orgLoactions.user.id IN (select userRole.user.id from UserRole userRole where userRole.userState = '" + userState + "' ))";
		}
		
		// trim the spaces between words (escape spaces)
		String s1 = searchByName;
		String withoutWhitespace = s1.trim();
		String orgNameSubQuery = "";
		
		if (!isNullOrEmpty(withoutWhitespace)) {
			orgNameSubQuery = "and org.orgName LIKE :orgName";
			String query = "from Org org where org.id <> :orgId " + userStateSubQuery + " " + orgNameSubQuery + " ";
		    orgsList = session.createQuery(query.toString()).setLong( "orgId", orgId).setString("orgName", withoutWhitespace + "%").setCacheable(true).list();
		}else{
			String query = "from Org org where org.id <> :orgId " + userStateSubQuery + " " + orgNameSubQuery + " ";
		    orgsList = session.createQuery(query.toString()).setLong( "orgId", orgId).setCacheable(true).list();
		}
		
	    
        return orgsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean isOrgNameExisted(String orgName) throws Exception {
		boolean isOrgFound = false;
        Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Org org where org.orgName = :orgName ";
		 
		         List<Org> orglist = session.createQuery(query.toString()).setString("orgName", orgName).setCacheable(true).list();
		            if (orglist != null && !orglist.isEmpty()) {
		            	isOrgFound = true;
		            }
		            return isOrgFound;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org getOrgByOrgName(String orgName) {
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Org org where org.orgName = :orgName ";
		 
	    List<Org> orglist = session.createQuery(query.toString()).setString("orgName", orgName).setCacheable(true).list();
		Org org1 = null;
		         
		if(orglist.size()>0){
			org1 = orglist.get(0);
		}
		         
		         return org1;
	}

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isLoginUserIsSuperAdmin(Long userId,Long orgId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isSuperAdmin = false;
		List<Org> org = new ArrayList<Org>();
		try{
			String query = ("from Org org where org.user.id = :userId and org.id = :orgId");
			org = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgId", orgId).list();
			if (null != org && !org.isEmpty() && org.size() > 0) {
				isSuperAdmin = true;
			} 
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ORG"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		
		return isSuperAdmin;
	}
	
	@Override
	public List<UserDTO> getuserListForOrgChart(Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.openSession();
		List<UserDTO> userList = new ArrayList<>();
		try{
			String query = "select u.id, u.FIRST_NAME, u.last_name, u.doc_file, r.display_name, ur.reporting_manager_id, o.primary_contact_user_id " + 
					" from USER u, USER_ROLE ur, ROLES r, ORG o, ORG_LOCATIONS ol " + 
					" where r.id = ur.role_id and u.id = ur.user_id and ur.org_locations_id = "+orgLocationId.longValue() + 
					" and o.id = ol.org_id and ol.id = ur.org_locations_id order by ur.reporting_manager_id;";
			List<Object[]> result = session.createSQLQuery(query).list();
			for(Object[] record : result) {
				UserDTO user = new UserDTO();
				int i = 0;
				user.setId(Long.valueOf(record[i++].toString()));
				user.setName(record[i++].toString());
				String lastName = record[i] == null ? "" : record[i].toString();
				user.setName(user.getName()+(lastName.isEmpty() ? "" : " "+ lastName));
				i++;
				i++;
				user.setDesignation(record[i++].toString());
				String parentId = record[i] == null ? null : record[i].toString();
				i++;
				user.setParentId(Long.valueOf(parentId == null ? record[i++].toString() : parentId));
				if(user.getId() == user.getParentId()) {
					user.setParentId(0);
					userList.add(0, user);
				} else 
				userList.add(user);
			}
		}catch(Exception e){
			e.printStackTrace();
			StringBuilder message= new StringBuilder(MessageFormat.format(noRecords,"ORG"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		} finally {
			session.close();
		}
		
		return userList;
	}

}

