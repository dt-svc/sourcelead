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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class OrgLocationsDaoImpl implements OrgLocationsDao {

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

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationsDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations addOrgLocations(OrgLocations orgLocations) throws Exception {
		logger.debug("Adding orgLocations");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgLocations != null) {
				session.save(orgLocations);
			}
			logger.debug("orgLocations Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgLocations;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations updateOrgLocations(OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(orgLocations);
	    logger.info("Successfully updated..!");
		return orgLocations;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations getOrgLocations(Long id) throws Exception {
		/*Session session = this.sessionFactory.getCurrentSession();
		OrgLocations orgLocations = session.get(OrgLocations.class, id);
		return orgLocations;*/
		   Session session = this.sessionFactory.getCurrentSession();
	       String query = ("from OrgLocations orgLocations left join fetch orgLocations.user left join fetch orgLocations.org org "
	       		+ " left join fetch org.user left join fetch orgLocations.timezone where orgLocations.id =:id ");
		  
	       List<OrgLocations> orgLocations = session.createQuery(query.toString()).setLong("id", id).list();
	       OrgLocations orgLocation = null;
			if(orgLocations != null && !orgLocations.isEmpty())
			{
				orgLocation = (OrgLocations) orgLocations.iterator().next();
			}
			
			return orgLocation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isLoginUserIsSuperUser(Long userId,Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isSuperUser = false;
		List<OrgLocations> orgLocations = new ArrayList<OrgLocations>();
		try{
			String query = ("from OrgLocations orgLocations where orgLocations.user.id = :userId and orgLocations.id = :orgLocationId");
			orgLocations = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
			if (null != orgLocations && !orgLocations.isEmpty() && orgLocations.size() > 0) {
				isSuperUser = true;
			} 
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ORGLOCATIONS"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		
		return isSuperUser;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteOrgLocations(Long id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		try{
			if (id != null) {
				OrgLocations orgLocations = (OrgLocations) session.get(OrgLocations.class, id);
				orgLocations.setStatus(Status.INACTIVE);
				session.saveOrUpdate(orgLocations);
				isDeleted = true;
				logger.info("Successfully deleted..!");
			}
			else {
				isDeleted = false;
			}
			
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ORGLOCATIONS"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		
		return isDeleted;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getAllOrgLocations() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from OrgLocations orgloc left join fetch orgloc.user left join fetch orgloc.org org left join fetch org.user  ";
		
		List<OrgLocations> locationList = session.createQuery(query).list();
		return locationList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations getOrgLocationsByLocationCode(String locationCode) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgLocations orgLocations left join fetch orgLocations.org  where orgLocations.locationCode =:locationCode");
		  
	       List<OrgLocations> orgLocations = session.createQuery(query.toString()).setString("locationCode", locationCode).list();
	     
	        OrgLocations orgLocation = null;
			if(orgLocations != null && !orgLocations.isEmpty())
			{
				orgLocation = (OrgLocations) orgLocations.iterator().next();
			}
			
			return orgLocation;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public OrgLocations findByEmail(String email, Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLocations> orgLocations = new ArrayList<OrgLocations>();
		logger.debug("findByEmail start");
		try{
			orgLocations = session.createQuery("from OrgLocations ol where ol.email=:email and ol.id(select orgRelations.responderLocationId.org.id from OrgRelations orgRelations"+" where orgRelations.locationId.id = :orgLocationId)" ).setString( "email", email ).setLong("orgLocationId", orgLocationId).list();
			
			logger.debug("findByEmail end");
			if (orgLocations.size() > 0) {
				return orgLocations.get(0);
			} else {
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
		
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getOrgsListForClientAndVendorOrgSearch(String orgCode)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from OrgLocations orgLocations where orgLocations.org.orgCode <> :orgCode and orgLocations.org.orgCode != 'slead' ";
		
		List orgList = session.createQuery(query).setString("orgCode", orgCode).list();
		return orgList;
		}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getAllOrgLocationsByOrgId(Long orgId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from OrgLocations orgLocations where orgLocations.org.id IN (select org.id from Org org where org.id = :orgId and org.orgCode != 'slead') order by  orgLocations.city";
		
		List orgList = session.createQuery(query).setLong("orgId", orgId).list();
		return orgList;
	}


	@Override
	@Transactional
	public List<OrgLocations> getAllOrgsStartsWithAlphabet(String alphabet) {
		Session session = this.sessionFactory.getCurrentSession();
	      String query = "";
	      if("Others".equalsIgnoreCase(alphabet)){
	    	  query = ("from OrgLocations orgLocations left join fetch orgLocations.org where orgLocations.org.orgName IN (select org.orgName from Org org where org.orgName <= '@' or org.orgName >= '{')");
	      }else{
	    	  query = ("from OrgLocations orgLocations left join fetch orgLocations.org where orgLocations.org.orgName IN (select org.orgName from Org org where org.orgName like '"+alphabet+"%')");
	      }
	      List<OrgLocations> locationList = session.createQuery(query.toString()).list();
		return locationList;
	}

	@Override
	@Transactional
	public List<OrgLocations> getAllRegisteredOrgLocations() throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLocations> orgLocationsList = null;
		Query orgLocsList= session.createQuery("from OrgLocations orgloc left join fetch orgloc.org org where org.orgType = 'Company' ");
		orgLocationsList = orgLocsList.list();
		return orgLocationsList;
	}

	@Override
	@Transactional
	public List<OrgLocations> getAllRegisteredOrgLocationsByOrgId(Long orgId,Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from OrgLocations orgLocations left join fetch orgLocations.user where orgLocations.org.id = :orgId and orgLocations.id <> :orgLocationId ";
		
		List orgList = session.createQuery(query).setLong("orgId", orgId).setLong("orgLocationId", orgLocationId).list();
		return orgList;
	}

	@Override
	@Transactional
	public OrgLocations getPrimaryOrgLocationByOrgId(Long orgId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgLocations orgLocations left join fetch orgLocations.user where  orgLocations.org.id IN (select org.id from Org org where org.id = :orgId) and "
	      		+ " orgLocations.isPrimary = 'Y' ");
		  
	       List<OrgLocations> orgLocations = session.createQuery(query.toString()).setLong("orgId", orgId).list();
	       
	       OrgLocations orgLocation = null;
			if(orgLocations != null && !orgLocations.isEmpty())
			{
				orgLocation = (OrgLocations) orgLocations.iterator().next();
			}
			
			return orgLocation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations saveOrgLocationsOrgShortCode(OrgLocations orgLocation) throws SourceLeadConstraintViolationException {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgLocation != null) {
				session.update(orgLocation);
			}
			logger.debug("orgLocations Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgLocation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean validateOrgShortCode(String orgShortId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgLocations orgLocations where  orgLocations.generatedUserId =:orgShortId";
		List<OrgLocations> orgLocations = session.createQuery(query.toString()).setString("orgShortId", orgShortId).list();
		if(orgLocations != null && !orgLocations.isEmpty()){
			return true;
		}
		return false;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getAllRegisteredOrgLocationsForClientProject(List<Long> orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgLocations orgLocations left join fetch orgLocations.user where orgLocations.org.id <> '1' and orgLocations.id NOT IN (:orgLocationId) ";
		List orgLocList = session.createQuery(query).setParameterList("orgLocationId", orgLocationId).list();
		return orgLocList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> userList(Long orgId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
			String query = "from OrgLocations orgLocations where orgLocations.org.id = :orgId";
			List<OrgLocations> orgLocations = session.createQuery(query.toString()).setLong("orgId", orgId).list();
		return orgLocations;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations getOrgLocationsByOrgLocationName(String orgLocationName) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgLocations orgLocations where  orgLocations.orgLocationName = :orgLocationName");
		  
	       List<OrgLocations> orgLocations = session.createQuery(query.toString()).setString("orgLocationName", orgLocationName).list();
	     
	        OrgLocations orgLocation = null;
			if(orgLocations != null && !orgLocations.isEmpty())
			{
				orgLocation = (OrgLocations) orgLocations.iterator().next();
			}
			
			return orgLocation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isTwoStepVerificationEnabledForAllEnrolledOrgsByUserId(Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = ("from UserRole userRole left join fetch userRole.orgLocations loc where userRole.user.id = :userId and loc.loginSecurity = 'Y'");
		List<UserRole> userRole = session.createQuery(query.toString()).setLong("userId", userId).list();
		if (userRole != null && userRole.size() > 0)
			return true;
		else
			return false;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getAllRegisteredOrgLocationsExceptLoggedInLocation(Long orgLocationId,Long orgId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from OrgLocations orgLocations where orgLocations.org.id IN (select org.id from Org org where org.id = :orgId and org.orgCode != 'slead') "
				+ " and orgLocations.id <> :orgLocationId ";
		
		List orgList = session.createQuery(query).setLong("orgId", orgId).setLong("orgLocationId", orgLocationId).list();
		return orgList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getRegisteredOrgLoctionsCountByOrgId(Long orgId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from OrgLocations orgLocations where orgLocations.org.id = :orgId ";
		List<Long> orgLocCount = session.createQuery(query.toString()).setLong("orgId", orgId).list();
			Integer count = 0;
			if(orgLocCount != null && !orgLocCount.isEmpty()){
					count = orgLocCount.get(0).intValue();
			}
			return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getAllOrgLocationsByOrgTypeCompany() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgLocations orgloc left join fetch orgloc.org  org where org.orgType = 'Company' and orgloc.id <> '1' ";
		List<OrgLocations> locationList = session.createQuery(query).list();
		return locationList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getOrgLocationsIdsListByOrgIdAndCountry(Long orgId, String country) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select orgLocations.id from OrgLocations orgLocations where orgLocations.org.id = :orgId and orgLocations.country = :country " ;
	    List<Long> orgLocationIdsList = session.createQuery(query).setLong( "orgId", orgId).setString("country", country).list();
        return orgLocationIdsList;
        
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getAllCurrentRegisteredOrgLocationsByOrgIdAndCountry(Long orgId,String country) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgLocations orgloc left join fetch orgloc.org  org where org.id = :orgId and orgloc.country = :country ";
		List<OrgLocations> locationList = session.createQuery(query).setLong("orgId", orgId).setString("country", country).list();
		return locationList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getAllRegisteredWorkLocationsByOrgId(Long orgId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from OrgLocations orgLocations left join fetch orgLocations.org org left join fetch orgLocations.user left join fetch orgLocations.businessType "
				+ " left join fetch orgLocations.timezone left join fetch org.user where orgLocations.org.id = :orgId";
		
		List orgList = session.createQuery(query).setLong("orgId", orgId).list();
		return orgList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations getOrgLocationById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OrgLocations orgLocations where orgLocations.id = :id";
		
		List<OrgLocations> locationsList = session.createQuery(query).setLong("id", id).list();
		OrgLocations locations = null;
		if(locationsList != null && !locationsList.isEmpty())
			locations = locationsList.iterator().next();
		return locations;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations getOrgLocationByOrgIdAndCountry(Long orgId, String country) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OrgLocations orgLocations where orgLocations.org = :orgId and orgLocations.country = :country";
		List<OrgLocations> locationsList = session.createQuery(query).setLong("orgId", orgId).setString("country", country).list();
		OrgLocations locations = null;
		if(locationsList != null && !locationsList.isEmpty())
			locations = locationsList.iterator().next();
		return locations;
	}
	 
}
