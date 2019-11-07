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
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.reports.dao.ClientReportDao;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;

@Repository
public class ClientReportDaoImpl implements ClientReportDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Transactional
	public boolean isUserAdminByOrgLocationId(Long userId, Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		userRole = session.createQuery("from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user  where (role.roleName = 'ROLE_ADMIN' OR  role.roleName = 'ROLE_SOURCELEAD_ADMIN') and userRole.user =:userId and userRole.orgLocations.id = :orgLocationId").setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
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
			clientsActive = clientsActiveCount.setLong("orgLocationId", orgLocationId).list();
		}
		else {
			clientsActiveCount = session.createQuery("select count(*) from OrgRelations orgrelation where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.status = 'ACTIVE' and orgrelation.createdBy IN(select user.createdBy from User user where user.id =:userId)");
			clientsActive = clientsActiveCount.setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
		}
		
		if (clientsActive != null && !clientsActive.isEmpty()) {
			clientCount.put("activeClient", clientsActive.get(0).intValue());
		}
		
		Query clientsInActiveCount;
		List<Long> clientsInActive;
		if (isadmin) {
			clientsInActiveCount = session.createQuery("select count(*) from OrgRelations orgrelation where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.status = 'In-Active'");
			clientsInActive = clientsInActiveCount.setLong("orgLocationId", orgLocationId).list();
		}
		else {
			clientsInActiveCount = session.createQuery("select count(*) from OrgRelations orgrelation where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.status = 'In-Active' and orgrelation.createdBy IN(select user.createdBy from User user where user.id =:userId)");
			clientsInActive = clientsInActiveCount.setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
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
			activespocList = query.setLong("orgRelationId", orgRelationId).list();
		}else{
			Query query= session.createQuery("select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.vendorUser.id = :userId and spoc.status <> '0' ");
			activespocList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).list();
		}
		
		if (activespocList != null && !activespocList.isEmpty()) {
			spocCount.put("spocActiveCount", activespocList.get(0).intValue());
		}
		
		List<Long> inactivespocList = null;
		if (isadmin) {
			Query query= session.createQuery("select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.status <> '1' ");
			inactivespocList = query.setLong("orgRelationId", orgRelationId).list();
		}else{
			Query query= session.createQuery("select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.vendorUser.id = :userId and spoc.status <> '1' ");
			inactivespocList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).list();
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
			orgRelationList = session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.locationId left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')");
			clients = orgRelationList.setLong("orgLocationId", orgLocationId).list();
		}
		else {
			orgRelationList = session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.locationId left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor')) and orgrelation.createdBy IN(select user.createdBy from User user where user.id =:userId)");
			clients = orgRelationList.setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
		}
		
		if (clients.size() > 0)
			return clients;
		else
			return null;
	}

	@Override
	@Transactional
	public List<Contracts> getContractsListOFAllClients(OrgLocations orgLocations, User user,Long orgRelationId,String userRoleName,ContractReportsDTO contractReportsDTO) throws Exception
	{
		String subQuery = "";
		Date[] freqDates = new Date[2];
		if((contractReportsDTO.getFromDate() != null && !"".equalsIgnoreCase(contractReportsDTO.getFromDate()) && contractReportsDTO.getToDate() != null && !"".equalsIgnoreCase(contractReportsDTO.getToDate())) || contractReportsDTO.getFrequency() != null && contractReportsDTO.getFrequency() != ""){
		if(contractReportsDTO.getFromDate() != null && !"".equalsIgnoreCase(contractReportsDTO.getFromDate()) && contractReportsDTO.getToDate() != null && !"".equalsIgnoreCase(contractReportsDTO.getToDate())){
			subQuery += " and ( contracts.updatedDate >= :startDate and contracts.updatedDate <= :endDate )";
			freqDates[0] = commonUtil.getDateFormat(contractReportsDTO.getFromDate());
			freqDates[1] = commonUtil.getDateFormat(contractReportsDTO.getToDate());
		}else{
			freqDates = reportsUtil.getJobFilterDate(contractReportsDTO.getFrequency(),orgLocations);
			subQuery += " and ( contracts.updatedDate >= :startDate and contracts.updatedDate <= :endDate )";
		}
		}
		String userRoleCondition = "";
		if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
			userRoleCondition = "";
		}else{
			userRoleCondition = " and (contracts.approverUser.id =:userId OR contracts.ownerUserId.id =:userId OR contracts.createdBy =:userCode) ";
		}
		
		String spocCondition = "";
		if(contractReportsDTO.getContactUser() != 0){
			spocCondition = " and contracts.contactUser.id = "+contractReportsDTO.getContactUser()+" ";
		}
		
		String submissionStatus = "";
		if(contractReportsDTO.getTemplateSubmissionStatus() != null && contractReportsDTO.getTemplateSubmissionStatus() != ""){
			submissionStatus = " and contracts.templateSubmissionStatus = "+contractReportsDTO.getTemplateSubmissionStatus()+" ";
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
					+ "where contracts.orgLocationId.id = :orgLocationId and contracts.orgRelations.id =:orgRelationId and contracts.contractType like '%Client%' "+submissionStatus+userRoleCondition+subQuery+spocCondition ;
			if(freqDates[0] != null && freqDates[1] != null) {
				if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
					ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("orgRelationId", orgRelationId).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).list();
				}else{
					ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("orgRelationId", orgRelationId).setLong("userId", user.getId()).setString("userCode", user.getUserCode()).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).list();
				}
			}else {
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("orgRelationId", orgRelationId).list();
			}
		
		if (ContractsList.size() > 0)
			return ContractsList;
		else
			return null;
	}

	@Override
	@Transactional
	public List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocId, String invoiceType,Long contactUserId,Date[] freqDates) {
		String subQuery = "";
		if(invoiceType.equalsIgnoreCase("payables")){
			subQuery = " and invoice.invoiceType = 'Payables' ";
		}else{
			subQuery = " and invoice.invoiceType = 'Receivable' ";
		}
		
		String subQuery1 = "";
		subQuery1 += " and ( invoice.createdDate >= :startDate and invoice.createdDate <= :endDate )";
		
		List<Invoice> clientInvoiceList = null;
		Session session = this.sessionFactory.getCurrentSession();
		if(freqDates != null) {
			if(contactUserId != null) {
				String query = "from Invoice invoice  where invoice.orgRelationID.id = :orgRelationId and invoice.contactUser.id = :contactUserId and invoice.orgLocation.id = :orgLocationId and invoice.linkInvoiceReferenceId IS NULL and invoice.invoiceStatus <> 'Linked'"+subQuery+subQuery1+"";
				clientInvoiceList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocId).setLong("orgRelationId", orgRelationId).setLong("contactUserId", contactUserId).setParameter("startDate", freqDates[0]).setParameter("endDate",freqDates[1]).list();
			}else {
				String query = "from Invoice invoice  where invoice.orgRelationID.id = :orgRelationId and invoice.orgLocation.id = :orgLocationId and invoice.linkInvoiceReferenceId IS NULL and invoice.invoiceStatus <> 'Linked'"+subQuery+subQuery1+"";
				clientInvoiceList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocId).setLong("orgRelationId", orgRelationId).setParameter("startDate", freqDates[0]).setParameter("endDate",freqDates[1]).list();
			}
		}else {
			if(contactUserId != null) {
				String query = "from Invoice invoice  where invoice.orgRelationID.id = :orgRelationId and invoice.contactUser.id = :contactUserId and invoice.orgLocation.id = :orgLocationId and invoice.linkInvoiceReferenceId IS NULL and invoice.invoiceStatus <> 'Linked'"+subQuery+"";
				clientInvoiceList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocId).setLong("orgRelationId", orgRelationId).setLong("contactUserId", contactUserId).list();
			}else {
				String query = "from Invoice invoice  where invoice.orgRelationID.id = :orgRelationId and invoice.orgLocation.id = :orgLocationId and invoice.linkInvoiceReferenceId IS NULL and invoice.invoiceStatus <> 'Linked'"+subQuery+"";
				clientInvoiceList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocId).setLong("orgRelationId", orgRelationId).list();
			}
		}
		return clientInvoiceList;
	}
	
	@Override
	@Transactional
	public List<OrgSpoc> getClientSpocListByOrgRelationid(Long orgRelationId, Long orgLocationId, Long userId,Date[] freqDates)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Boolean isadmin = isUserAdminByOrgLocationId(userId, orgLocationId);
		
		String subQuery = "";
		subQuery += " and ( spoc.createdDate >= :startDate and spoc.createdDate <= :endDate )";
		
		List<OrgSpoc> spocList = null;
		if (isadmin) {
			Query query= session.createQuery("from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId "+subQuery);
			spocList = query.setLong("orgRelationId", orgRelationId).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).list();
		}else{
			Query query= session.createQuery("from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.vendorUser.id = :userId "+subQuery);
			spocList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).list();
		}
		return spocList;
	}

	@Override
	@Transactional
	public List<Requirement> getClientJobsByOrgRelationId(Long orgRelationId, Long orgLocationId, Long userId, Date[] freqDates,Long spocId)
	{
		Session session = this.sessionFactory.getCurrentSession();
//		Boolean isadmin = isUserAdminByOrgLocationId(userId, orgLocationId);
		
		String subQuery = "";
		subQuery += " and ( requirement.createdDate >= :startDate and requirement.createdDate <= :endDate )";
		
		String subQuery1 = "";
		if(spocId != null){
			subQuery1 = " and requirement.spocId =:spocId ";
		}
		
		List<Requirement> reqList = null;
		if(freqDates != null) {
		if(spocId != null){
			Query query= session.createQuery("from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.clientId.id =:orgRelationId and requirement.isClientJob ='Y' and requirement.orgLocationId.id =:orgLocationId "+subQuery+subQuery1);
			reqList = query.setLong("orgRelationId", orgRelationId).setLong("orgLocationId", orgLocationId).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).setLong("spocId", spocId).list();	
		}else{
			Query query= session.createQuery("from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.clientId.id =:orgRelationId and requirement.isClientJob ='Y' and requirement.orgLocationId.id =:orgLocationId "+subQuery);
			reqList = query.setLong("orgRelationId", orgRelationId).setLong("orgLocationId", orgLocationId).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).list();
		}
		}else {
			Query query= session.createQuery("from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.clientId.id =:orgRelationId and requirement.orgLocationId.id =:orgLocationId and requirement.isClientJob ='Y'");
			reqList = query.setLong("orgRelationId", orgRelationId).setLong("orgLocationId", orgLocationId).list();
		}

		return reqList;
	}

	@Override
	@Transactional
	public Map<String, Integer> getSubmissionListByRequirementId(Long requirementId, Long orgLocationId, Long userId, Date[] freqDates)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Map<String, Integer> subCount = new HashMap<>();
//		Boolean isadmin = isUserAdminByOrgLocationId(userId, orgLocationId);
		
		String subQuery = "";
		subQuery += " and ( reqSub.createdDate >= :startDate and reqSub.createdDate <= :endDate )";
		List<RequirementSubmission> reqSubList = null;
		List<RequirementSubmission> hiredList = null;
		
		int applicantsCount = 0;
		int shortlistedCount = 0;
 		int submitToClientCount = 0;
		int interviewsCount = 0;
		int offeredCount = 0;
		int offeracceptedCount = 0;
		int applicantRejectedCount = 0;
		
		if(freqDates != null) {
			
			Query query= session.createQuery("from RequirementSubmission reqSub left join fetch reqSub.requirement req where reqSub.orgLocations.id =:orgLocationId and reqSub.requirement.id =:requirementId "+subQuery);
			reqSubList = query.setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).list();
			if(reqSubList != null)	
				subCount.put("submissionCount", reqSubList.size());
			
			Query hiredQuery= session.createQuery("from RequirementSubmission reqSub left join fetch reqSub.requirement req where reqSub.orgLocations.id =:orgLocationId and reqSub.requirement.id =:requirementId and reqSub.submissionStatus = 'OFFERACCEPTED' "+subQuery);
			hiredList = hiredQuery.setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).list();
			if(hiredList != null)
				subCount.put("hiredCount", hiredList.size());
		}else {
			Query reqSubQuery= session.createQuery("from RequirementSubmission reqSub left join fetch reqSub.requirement req where reqSub.orgLocations.id =:orgLocationId and reqSub.requirement.id =:requirementId");
			reqSubList = reqSubQuery.setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).list();
			if(reqSubList != null && reqSubList.size() > 0) {
				for(RequirementSubmission reqSub : reqSubList){
					if("submission".equalsIgnoreCase(reqSub.getSubmissionStatus().toString())){
						applicantsCount += 1;	
					}else if("shortList".equalsIgnoreCase(reqSub.getSubmissionStatus().toString())){
						shortlistedCount += 1;
					}else if("submit_to_client".equalsIgnoreCase(reqSub.getSubmissionStatus().toString())){
						submitToClientCount += 1;
					}else if("interview".equalsIgnoreCase(reqSub.getSubmissionStatus().toString())){
						interviewsCount += 1;
					}else if("offermade".equalsIgnoreCase(reqSub.getSubmissionStatus().toString())){
						offeredCount += 1;
					}else if("offeraccepted".equalsIgnoreCase(reqSub.getSubmissionStatus().toString())){
						offeracceptedCount += 1;
					}else if("rejected".equalsIgnoreCase(reqSub.getSubmissionStatus().toString())){
						applicantRejectedCount += 1;
					}
				}
			}
			subCount.put("submissionCount",applicantsCount);
			subCount.put("shortListCount",shortlistedCount);
			subCount.put("submitToClientCount",submitToClientCount);
			subCount.put("interviewedCount",interviewsCount);
			subCount.put("offermadeCount",offeredCount);
			subCount.put("offeracceptedCount",offeracceptedCount);
			subCount.put("rejectedCount",applicantRejectedCount);
		}
		
		return subCount;
	}
}
