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

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dto.OrgDTO;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.PaymentAccountType;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import java.util.Base64;
import java.util.Collection;
import java.util.Objects;
import org.hibernate.Query;
import org.jgroups.util.UUID;

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
	
	@Autowired
	CandidateDao candidateDao;
	
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
		List<Org> orgList = session.createQuery("from Org where id=:id" ).setLong( "id", id ).list();
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
		List<Org> orgList = crit.list();
		return orgList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Org> getOpenJobsOrgs() throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Org> orgList;
		Criteria crit = session.createCriteria(Org.class);
		orgList = crit.setMaxResults(5).list();
		return orgList;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org getOrgByOrgCode(String orgCode) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Org> orgList;
		logger.debug("getOrgByOrgCode start");
		orgList = session.createQuery("from Org where orgCode=:orgCode" ).setString( "orgCode", orgCode ).list();
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
         List<Org> orglist = session.createQuery(query.toString()).setString("host", host).list();
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
	        
	        
	         List<Org> orglist = session.createQuery(query.toString()).setString("clientName", clientName).setString("responderType", responderType).setLong("orgLocationId", orgLocationId).list();
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
	        
	        
	     return  session.createQuery(query.toString()).setString("clientName", clientName).setString("responderType", responderType).setLong("orgLocationId", orgLocationId).list();
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Org> getEnrolledOrgs(Long orgId,String userState,String searchByName,Integer offset) throws Exception {

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
		    orgsList = session.createQuery(query.toString()).setLong( "orgId", orgId).setString("orgName", withoutWhitespace + "%").list();
		}else{
			String query = "from Org org where org.id <> :orgId " + userStateSubQuery + " " + orgNameSubQuery + " ";
		    orgsList = session.createQuery(query.toString()).setLong( "orgId", orgId).list();
		}
		
		if(orgsList.size() >= offset){
			if(orgsList.size() > offset + 20){
				return   orgsList.subList(offset, (offset+20));
			}else{
				return   orgsList.subList(offset, (  offset + (orgsList.size() - offset)));
			}
		}else{
			return  null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean isOrgNameExisted(String orgName) throws Exception {
		boolean isOrgFound = false;
        Session session = this.sessionFactory.getCurrentSession();
		 String query ="from Org org where org.orgName = :orgName ";
		 
		         List<Org> orglist = session.createQuery(query.toString()).setString("orgName", orgName).list();
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
		 
	    List<Org> orglist = session.createQuery(query.toString()).setString("orgName", orgName).list();
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
			String query = "SELECT U.ID,U.USER_CODE,U.FIRST_NAME, U.LAST_NAME, U.EMAIL, U.FILE_URL, R.DISPLAY_NAME, UR.REPORTING_MANAGER_ID, O.PRIMARY_CONTACT_USER_ID " + 
					" FROM USER U, USER_ROLE UR, ROLES R, ORG O, ORG_LOCATIONS OL " + 
					" WHERE R.ID = UR.ROLE_ID AND U.ID = UR.USER_ID AND UR.ORG_LOCATIONS_ID = :orgLocationId AND R.ID NOT IN (95,96) "+ 
					" AND O.ID = OL.ORG_ID AND OL.ID = UR.ORG_LOCATIONS_ID AND UR.USER_STATE !='INACTIVE' ORDER BY UR.REPORTING_MANAGER_ID;";
			
			List<Object[]> result = session.createSQLQuery(query).setLong("orgLocationId", orgLocationId).list();
			for(Object[] record : result) {
				UserDTO user = new UserDTO();
				int i = 0;
				user.setId(Long.valueOf(record[i++].toString()));
				user.setUserCode(record[i++].toString());
				user.setName(record[i++].toString());
				String lastName = record[i] == null ? "" : record[i].toString();
				user.setName(user.getName()+(lastName.isEmpty() ? "" : " "+ lastName));
				i++;
				user.setEmail(record[i] == null ? "" : record[i].toString());
				i++;
				user.setFileURL(record[i] == null ? "" : record[i].toString());
				i++;
				user.setDesignation(record[i] == null ? "" : record[i].toString());
				i++;
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
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org getOrgByCareerPageKey(String careerPageKey) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Org> orgList;
		logger.debug("getOrgByCareerPageKey start");
		orgList = session.createQuery("from Org where careerPageKey=:careerPageKey" ).setString( "careerPageKey", careerPageKey ).setCacheable(true).list();
		logger.debug("getOrgByCareerPageKey end");
		 
		if (orgList != null && orgList.size() > 0) {
			return orgList.get(0);
		} else {
			return null;
		}
   	}

        @Override
	@Transactional(rollbackFor = { Exception.class })
        public String generateCareerPageKey(Long id, String orgName) throws Exception{
            String name = orgName + UUID.randomUUID().toString();
            String key = Base64.getEncoder().encodeToString(name.getBytes());
            Session session = this.sessionFactory.getCurrentSession();
            Org org = session.get(Org.class, id);
            key = key.replaceAll("=", "");
            org.setCareerPageKey(key);
            org.setCareerPageKeyActive(true);
            session.saveOrUpdate(org);
            return key;
        }

        @Override
	@Transactional(rollbackFor = { Exception.class })
        public int changeCareerPageKeyStatus(Long id) throws Exception {
            Session session = this.sessionFactory.getCurrentSession();
            Org org = session.get(Org.class, id);
            org.setCareerPageKeyActive(!org.getCareerPageKeyActive());
            session.saveOrUpdate(org);
            return org.getCareerPageKeyActive() ? 1 : 0;
        }

	@Override
	@Transactional
	public List<Org> getAllOpenJobsOrgsStartsWithAlphabet(String alphabet)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		if ("Others".equalsIgnoreCase(alphabet)) {
			query = ("from Org org where org.orgName <= '@' or org.orgName >= '{'");
		}
		else {
			query = ("from Org org where org.orgName like '" + alphabet + "%'");
		}
		List<Org> locationList = session.createQuery(query.toString()).list();
		return locationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<Org> getDynamicEnrolledOrgs(Long orgId, OrgDTO orgDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
        ResponseList<Org> response = new ResponseList<>();
        
           StringBuilder query1 = new StringBuilder("");
			if(orgDTO.getSearchValue() !=null && !"".equalsIgnoreCase(orgDTO.getSearchValue())) {
			 query1.append("and (org.orgName like '%" + orgDTO.getSearchValue() + "%' ")
			       .append("OR (user.email like '%" + orgDTO.getSearchValue() + "%' )")
				   .append("OR user.mobilePhone like '%" + orgDTO.getSearchValue() + "%' )");
			}
		
		
			List<Org> orgsList = null;
			String userStateSubQuery = "";
			if (!orgDTO.getUserState().isEmpty()  && !orgDTO.getUserState().equalsIgnoreCase("ALL")) {
				
				String subQuery = "";
				if(orgDTO.getUserState().equalsIgnoreCase("ACTIVE")) {
					subQuery = " and org.orgType = 'Company' and org.user IS NOT NULL ";
				}else if(orgDTO.getUserState().equalsIgnoreCase("NOT_LOGGEDIN")) {
					subQuery = " and org.orgType IN('Company','Guest') ";
				}else {
					subQuery = " and org.orgType IN('Company','Guest') ";
				}
				
				
				userStateSubQuery = "and org.id IN (select orgLoactions.org.id from OrgLocations orgLoactions where orgLoactions.isPrimary = 'Y' and "
						+ " orgLoactions.user.id IN (select userRole.user.id from UserRole userRole where userRole.userState = '" + orgDTO.getUserState() + "' ) and "
								+ " orgLoactions.id IN (select userRole.orgLocations.id from UserRole userRole where userRole.userState = '" + orgDTO.getUserState() + "' ) "+subQuery+" )";
			}
			
			//org list query
			String query = "from Org org left join fetch org.user user where org.id <> :orgId " + userStateSubQuery + " "+query1+"";
		    orgsList = session.createQuery(query.toString()).setLong( "orgId", orgId).setFirstResult(orgDTO.getStart()).setMaxResults(orgDTO.getLength()).list();
		    
		    //org count query
			 String queryCount = "Select count (*) from Org org where org.id <> :orgId " + userStateSubQuery + " "+query1+"" ;
			 countlist = session.createQuery(queryCount.toString()).setLong( "orgId", orgId).list();
				
			 int totalCount = 0;
				if (orgDTO != null) {
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
				
				response.setData(orgsList);
				return response;
	}
	

	
	
	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Org> getEnrolledOrgs(Long orgId,String userState,String searchByName,Integer offset) throws Exception {

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
		    orgsList = session.createQuery(query.toString()).setLong( "orgId", orgId).setString("orgName", withoutWhitespace + "%").list();
		}else{
			String query = "from Org org where org.id <> :orgId " + userStateSubQuery + " " + orgNameSubQuery + " ";
		    orgsList = session.createQuery(query.toString()).setLong( "orgId", orgId).list();
		}
		
		if(orgsList.size() >= offset){
			if(orgsList.size() > offset + 20){
				return   orgsList.subList(offset, (offset+20));
			}else{
				return   orgsList.subList(offset, (  offset + (orgsList.size() - offset)));
			}
		}else{
			return  null;
		}
	}*/

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public long savePaymentAccount(PaymentAccountDTO account) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            PaymentAccount pay = new PaymentAccount();
            boolean isNew = true;
            if (account.getId() != 0) {
                pay = session.get(PaymentAccount.class, account.getId());
                isNew = false;
                //updated
            } else {
                //created
            }
            String query = "from PaymentAccount p where p.org.id = :orgId";
            query += " and p.orgLocation.id = :orgLocationId";
            query += " and p.accountType = :accountType";
            query += " and p.email = :email";
            Query queryObj = session.createQuery(query);
            queryObj.setLong("orgId", account.getOrgId());
            queryObj.setString("accountType", account.getAccountType());
            queryObj.setString("email", account.getEmail());
            queryObj.setLong("orgLocationId", account.getOrgLocationId());
            List<PaymentAccount> list = queryObj.list();
            if (list != null && !list.isEmpty()
                    && (isNew || (!isNew && list.get(0).getId() != account.getId()))) {
                return -1;
            }
            
            if(!isNew && PaymentAccountType.valueOf(account.getAccountType()) == PaymentAccountType.WEPAY) {
                pay.setAccess(account.isAccess());
            }
            
            pay.setAccess(account.isAccess());
            pay.setAccessToken(account.getAccessToken());
            pay.setAccountId(account.getAccountId());
            pay.setUserId(account.getUserId());
            pay.setAccountType(account.getAccountType());
            pay.setOrg(session.get(Org.class, account.getOrgId()));
            pay.setOrgLocation(session.get(OrgLocations.class, account.getOrgLocationId()));
            pay.setFirstName(account.getFirstName());
            pay.setLastName(account.getLastName());
            pay.setEmail(account.getEmail());
            pay.setBankName(account.getBankName());
            pay.setBsrNumber(account.getBsrNumber());
            pay.setMicrCode(account.getMicrCode());
            pay.setAccountNumber(account.getAccountNumber());
            pay.setRoutingNumber(account.getRoutingNumber());
            if(account.getAssociateUserId() > 0) {
            	Candidate candidate = candidateDao.getCandidateByConsultantUserId(account.getAssociateUserId(), account.getOrgLocationId());
            	pay.setCandidateId(candidate);
            }else {
            	pay.setCandidateId(session.get(Candidate.class,account.getCandidateId()));
            }
            pay.setDefaultAccount(account.isDefaultAccount());
            pay.setDisabled(account.isDisabled());
            session.saveOrUpdate(pay);
            return pay.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional
    public List<PaymentAccountDTO> getPaymentAccountList(long orgId, long orgLocactionId, boolean onlyEnabledAccounts) {
        List<PaymentAccountDTO> resultList = new ArrayList<>();
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String query = "from PaymentAccount p where p.org.id = :orgId";
            if (orgLocactionId > 0) {
                query += " and p.orgLocation.id = :orgLocationId";
            }
            if(onlyEnabledAccounts) {
            	query += " and p.disabled = 0";
            }
            Query queryObj = session.createQuery(query);
            queryObj.setLong("orgId", orgId);
            if (orgLocactionId > 0) {
                queryObj.setLong("orgLocationId", orgLocactionId);
            }
            List<PaymentAccount> list = queryObj.list();
            for (PaymentAccount p : list) {
                PaymentAccountDTO pay = new PaymentAccountDTO();
                applyEntityDataToDTO(p, pay);
                resultList.add(pay);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public long changePaymentAccountToAccess(long id, long orgId) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            PaymentAccount pay = session.get(PaymentAccount.class, id);
            if (Objects.equals(pay.getOrg().getId(), orgId)) {
                pay.setAccess(true);
            } else {
                return -1;
            }
            session.saveOrUpdate(pay);
            return pay.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional
    public PaymentAccountDTO getPaymentAccount(long id, Long orgId) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            PaymentAccount pay = session.get(PaymentAccount.class, id);
            PaymentAccountDTO account = new PaymentAccountDTO();
            if (orgId == null || Objects.equals(pay.getOrg().getId(), orgId)) {
                applyEntityDataToDTO(pay, account);
            } else {
                return null;
            }
            session.saveOrUpdate(pay);
            return account;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int changeDefaultPaymentAccount(long id, long orgLocationId, boolean defaultAccount) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            PaymentAccount pay = session.get(PaymentAccount.class, id);
            if (Objects.equals(pay.getOrgLocation().getId(), orgLocationId)) {
                pay.setDefaultAccount(defaultAccount);
                if(defaultAccount) {
                	pay.setDisabled(false);
                    String query = "from PaymentAccount p where ";
                    query += "  p.orgLocation.id = :orgLocationId and p.id != :pid";
                    Query queryObj = session.createQuery(query);
                    queryObj.setLong("pid", pay.getId());
                    queryObj.setLong("orgLocationId", pay.getOrgLocation().getId());
                    List<PaymentAccount> list = queryObj.list();
                    for (PaymentAccount p : list) {
                        p.setDefaultAccount(false);
                        session.saveOrUpdate(p);
                    }
                }
                session.saveOrUpdate(pay);
                return 1;
            } else {
                return -1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    private void applyEntityDataToDTO(PaymentAccount p, PaymentAccountDTO pay) {
        pay.setId(p.getId());
        pay.setAccess(p.isAccess());
        pay.setAccessToken(p.getAccessToken());
        pay.setAccountId(p.getAccountId());
        pay.setAccountType(p.getAccountType());
        pay.setDefaultAccount(p.isDefaultAccount());
        pay.setOrgLocationId(p.getOrgLocation().getId());
        if(p.getCandidateId() != null) {
            pay.setCandidateId(p.getCandidateId().getId());
        }
        pay.setFirstName(p.getFirstName());
        pay.setBankName(p.getBankName());
        pay.setLastName(p.getLastName());
        pay.setBsrNumber(p.getBsrNumber());
        pay.setMicrCode(p.getMicrCode());
        pay.setAccountNumber(p.getAccountNumber());
        pay.setRoutingNumber(p.getRoutingNumber());
        pay.setEmail(p.getEmail());
        pay.setDisabled(p.isDisabled());
        pay.setOrgLocationName(p.getOrgLocation().getOrgLocationName());
    }

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int changePaymentAccountStatus(long id, long orgLocationId) {
		try {
            Session session = this.sessionFactory.getCurrentSession();
            PaymentAccount pay = session.get(PaymentAccount.class, id);
            if (Objects.equals(pay.getOrgLocation().getId(), orgLocationId)) {
            	pay.setDisabled(!pay.isDisabled());
            	if(pay.isDisabled())
            		pay.setDefaultAccount(false);
                session.saveOrUpdate(pay);
                return 1;
            } else {
                return -1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
	}

	@Override
	 @Transactional
	public List<PaymentAccountDTO> getPaymentAccountListByUserId(long userId, boolean onlyEnabledAccounts) {
		List<PaymentAccountDTO> resultList = new ArrayList<>();
		try {
            Session session = this.sessionFactory.getCurrentSession();
            String query = "from PaymentAccount p where p.associateUser.id = :userId";
            if(onlyEnabledAccounts) {
            	query += " and p.disabled = 0";
            }
            Query queryObj = session.createQuery(query);
            queryObj.setLong("userId", userId);
            List<PaymentAccount> list = queryObj.list();
            for (PaymentAccount p : list) {
                PaymentAccountDTO pay = new PaymentAccountDTO();
                applyEntityDataToDTO(p, pay);
                resultList.add(pay);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public List<PaymentAccountDTO> getPaymentAccountListByCandidateId(long candidateId, boolean onlyEnabledAccounts) {

		List<PaymentAccountDTO> resultList = new ArrayList<>();
		try {
            Session session = this.sessionFactory.getCurrentSession();
            String query = "from PaymentAccount p where p.candidateId.id = :candidateId";
            if(onlyEnabledAccounts) {
            	query += " and p.disabled = 0";
            }
            Query queryObj = session.createQuery(query);
            queryObj.setLong("candidateId", candidateId);
            List<PaymentAccount> list = queryObj.list();
            for (PaymentAccount p : list) {
                PaymentAccountDTO pay = new PaymentAccountDTO();
                applyEntityDataToDTO(p, pay);
                resultList.add(pay);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
	
	}
	
	
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public PaymentAccount getCandidateAcitivatePaymentAccount(long candidateId) {
            Session session = this.sessionFactory.getCurrentSession();
            String query = "from PaymentAccount pa where pa.candidateId.id = :candidateId and pa.disabled ='0' and  pa.defaultAccount = '1'";
            List<PaymentAccount> accountList = session.createQuery(query.toString()).setLong("candidateId", candidateId).list();
            PaymentAccount paymentAcoount = null;
    		if (accountList != null && accountList.size() > 0) {
    			paymentAcoount = (PaymentAccount) accountList.iterator().next();
    		}
    		return paymentAcoount;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int changeAssociateDefaultPaymentAccount(long id, long candidateId, boolean defaultAccount) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            PaymentAccount pay = session.get(PaymentAccount.class, id);
            if (Objects.equals(pay.getCandidateId().getId(), candidateId)) {
                pay.setDefaultAccount(defaultAccount);
                if(defaultAccount) {
                	pay.setDisabled(false);
                    String query = "from PaymentAccount p where ";
                    query += "  p.candidateId.id = :candidateId and p.id != :id";
                    Query queryObj = session.createQuery(query);
                    queryObj.setLong("id", pay.getId());
                    queryObj.setLong("candidateId", pay.getCandidateId().getId());
                    List<PaymentAccount> list = queryObj.list();
                    for (PaymentAccount p : list) {
                        p.setDefaultAccount(false);
                        session.saveOrUpdate(p);
                    }
                }
                session.saveOrUpdate(pay);
                return 1;
            } else {
                return -1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
	 
}

