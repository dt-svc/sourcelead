package com.bis.sourcelead.reports.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.reports.dao.ClientReportDao;

@Repository
public class ClientReportDaoImpl implements ClientReportDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public boolean isUserAdminByOrgLocationId(Long orgLocationId, Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		userRole = session.createQuery("from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user  where (role.roleName = 'ROLE_ADMIN' OR  role.roleName = 'ROLE_SOURCELEAD_ADMIN') and userRole.user =:userId and userRole.orgLocations.id = :orgLocationId").setLong("userId", userId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	@Transactional
	public Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long orgLocationId, Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Map<String, Integer> clientCount = new HashMap<>();
		Boolean isadmin = isUserAdminByOrgLocationId(userId, orgLocationId);
		Query clientsActiveCount;
		List<Long> clientsActive;
		if (isadmin) {
			clientsActiveCount = session.createQuery("select count(*) from OrgRelations orgrelation where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.status = 'ACTIVE'");
			clientsActive = clientsActiveCount.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}
		else {
			clientsActiveCount = session.createQuery("select count(*) from OrgRelations orgrelation where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.status = 'ACTIVE' and orgrelation.createdBy IN(select user.createdBy from User user where user.id =:userId)");
			clientsActive = clientsActiveCount.setLong("orgLocationId", orgLocationId).setLong("userId", userId).setCacheable(true).list();
		}
		
		if (clientsActive != null && !clientsActive.isEmpty()) {
			clientCount.put("activeClient", clientsActive.get(0).intValue());
		}
		
		Query clientsInActiveCount;
		List<Long> clientsInActive;
		if (isadmin) {
			clientsInActiveCount = session.createQuery("select count(*) from OrgRelations orgrelation where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.status = 'In-Active'");
			clientsInActive = clientsInActiveCount.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}
		else {
			clientsInActiveCount = session.createQuery("select count(*) from OrgRelations orgrelation where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.status = 'In-Active' and orgrelation.createdBy IN(select user.createdBy from User user where user.id =:userId)");
			clientsInActive = clientsInActiveCount.setLong("orgLocationId", orgLocationId).setLong("userId", userId).setCacheable(true).list();
		}
		
		if (clientsInActive != null && !clientsInActive.isEmpty()) {
			clientCount.put("inActiveClient", clientsInActive.get(0).intValue());
		}
		return clientCount;
	}
	
	@Override
	@Transactional
	public Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long orgRelationId ,Long orgLocationId, Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Boolean isadmin = isUserAdminByOrgLocationId(userId, orgLocationId);
		List<Long> activespocList = null;
		Map<String, Integer> spocCount = new HashMap<>();
		
		if (isadmin) {
			Query query= session.createQuery("select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' ");
			activespocList = query.setLong("orgRelationId", orgRelationId).setCacheable(true).list();
		}else{
			Query query= session.createQuery("select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.vendorUser.id = :userId and spoc.status <> '0' ");
			activespocList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setCacheable(true).list();
		}
		
		if (activespocList != null && !activespocList.isEmpty()) {
			spocCount.put("spocActiveCount", activespocList.get(0).intValue());
		}
		
		List<Long> inactivespocList = null;
		if (isadmin) {
			Query query= session.createQuery("select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.status <> '1' ");
			inactivespocList = query.setLong("orgRelationId", orgRelationId).setCacheable(true).list();
		}else{
			Query query= session.createQuery("select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.vendorUser.id = :userId and spoc.status <> '1' ");
			inactivespocList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setCacheable(true).list();
		}
		
		if (inactivespocList != null && !inactivespocList.isEmpty()) {
			spocCount.put("spocInactiveCount", inactivespocList.get(0).intValue());
		}
		return spocCount;
	}
	
	@Override
	@Transactional
	public List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long orgLocationId, Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Boolean isadmin = isUserAdminByOrgLocationId(userId, orgLocationId);
		Query orgRelationList;
		List<OrgRelations> clients;
		if (isadmin) {
			orgRelationList = session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')");
			clients = orgRelationList.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}
		else {
			orgRelationList = session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.createdBy IN(select user.createdBy from User user where user.id =:userId)");
			clients = orgRelationList.setLong("orgLocationId", orgLocationId).setLong("userId", userId).setCacheable(true).list();
		}
		
		if (clients.size() > 0)
			return clients;
		else
			return null;
	}

	@Override
	@Transactional
	public List<Contracts> getContractsListOFAllClients(Long orgLocationId, User user,Long orgRelationId,String userRoleName,Date startDate,Date endDate)
	{
		String userRoleCondition = "";
		if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
			userRoleCondition = "";
		}else{
			userRoleCondition = "and (contracts.approverUser.id =:userId OR contracts.ownerUserId.id =:userId OR contracts.createdBy =:userCode) ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts>  ContractsList = null;
			String query = "from Contracts contracts left join fetch contracts.contractsTemplate "
					+ "left join fetch contracts.requirementSubmission "
					+"left join fetch contracts.requirementSubmission.candidate "
					+ "left join fetch contracts.requirementSubmission.candidateResumes "
					+ "left join fetch contracts.ownerUserId "
					+ "left join fetch contracts.orgLocationId "
					+ "left join fetch contracts.orgRelations "
					+ "left join fetch contracts.parentContractId "
					+ "left join fetch contracts.contractOrgLocationId "
					+ "left join fetch contracts.contactUser "
					+ "left join fetch contracts.approverUser "
					+ "left join fetch contracts.candidate "
					+ "where contracts.orgLocationId.id = :orgLocationId and contracts.orgRelations.id =:orgRelationId and contracts.contractType like '%Client%' and contracts.templateSubmissionStatus = 'FINALIZED' "+userRoleCondition;
			if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setCacheable(true).list();
			}else{
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setLong("userId", user.getId()).setString("userCode", user.getUserCode()).setCacheable(true).list();
			}
		
		if (ContractsList.size() > 0)
			return ContractsList;
		else
			return null;
	}
}
