package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dto.AccountDeductionsDTO;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.ContractTemplateDTO;
import com.bis.sourcelead.dto.ContractsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ContractEditHistory;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Repository
public class ContractsTemplateDaoImpl implements ContractsTemplateDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(ContractsTemplateDaoImpl.class);
	
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
	@Transactional
	public ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception {
		logger.debug("Adding OrgSpoc");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contractsTemplate != null) {
				session.saveOrUpdate(contractsTemplate);
			}
			logger.debug("contractsTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_SPOC"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return contractsTemplate;
	}

	/*@Override
	@Transactional
	public ContractsTemplate saveContractTemplate(Long contractTemplateId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractsTemplate> contractsTemplateList = new ArrayList<>();
		logger.debug("getOrgSpocById start");
		contractsTemplateList = session.createQuery("from ContractsTemplate ct where id=:contractTemplateId " ).setLong( "contractTemplateId", contractTemplateId ).list();
		logger.debug("getOrgSpocById end");
		if (contractsTemplateList.size() > 0) {
			return contractsTemplateList.get(0);
		} else {
			return null;
		}
	}*/

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation, ContractTemplateDTO contractTemplateDTO) throws Exception
	{
		
		List<String> statusList = new ArrayList<>();
		if(contractTemplateDTO.getTemplateType() != null){
			for(int i=0;i<contractTemplateDTO.getTemplateType().size();i++){
				
				if("Vendor NSA".equalsIgnoreCase(contractTemplateDTO.getTemplateType().get(i))){
					statusList.add("NSA");
				}else if("Vendor NDA".equalsIgnoreCase(contractTemplateDTO.getTemplateType().get(i))){
					statusList.add("NDA");
				}else{
					statusList.add(contractTemplateDTO.getTemplateType().get(i));
				}
			}
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractsTemplate> templateList = null;
		ResponseList<ContractsTemplate> response = new ResponseList<>();
		String query = "";
		String countQuery = "";
		if(statusList != null && !statusList.isEmpty()){
			query = "from ContractsTemplate ct  left join fetch ct.orgLocationId where ct.orgLocationId.id =:orgLocationId and ct.templateType IN (:statusList)";
			countQuery = "from ContractsTemplate ct  where ct.orgLocationId.id =:orgLocationId and ct.templateType IN (:statusList)";
		}else{
			query = "from ContractsTemplate ct  left join fetch ct.orgLocationId where ct.orgLocationId.id =:orgLocationId";
			countQuery = "from ContractsTemplate ct  where ct.orgLocationId.id =:orgLocationId";
		}
		
		
		StringBuilder query1 = new StringBuilder("");
			if(contractTemplateDTO.getSearchValue() !=null && !"".equalsIgnoreCase(contractTemplateDTO.getSearchValue())) {
			query1.append(" and (ct.templateName like '%" + contractTemplateDTO.getSearchValue() + "%' ")
					.append("OR ct.templateType like '%" + contractTemplateDTO.getSearchValue() + "%' ")
					.append("OR ct.description like '%" + contractTemplateDTO.getSearchValue() + "%' )");
			}
		Query queryObj = session.createQuery(query + query1.toString());
		if (contractTemplateDTO != null && contractTemplateDTO.getLength() > 0) {
			queryObj.setFirstResult(contractTemplateDTO.getStart());
			queryObj.setMaxResults(contractTemplateDTO.getLength());
			if(statusList != null && !statusList.isEmpty()){
				queryObj.setParameterList("statusList", statusList);
			}
			queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}
		templateList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = null;
		if(statusList != null && !statusList.isEmpty()){
			countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setParameterList("statusList", statusList).list();
		}else{
			countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).list();
		}
		
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (contractTemplateDTO != null) {
			List<Long> filteredCountlist = null;
			if(statusList != null && !statusList.isEmpty()){
			 filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).setParameterList("statusList", statusList).list();
			}else{
			 filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
			}
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

			response.setData(getTemplateListSorting(templateList));
	
			return response;
}
		
		private List<ContractsTemplate> getTemplateListSorting(List<ContractsTemplate> templateList)
	{
			
			Collections.sort(templateList, new Comparator() {  
	            @Override  
	            public int compare(Object obj1, Object obj2) {  
	            	ContractsTemplate contractsTemplate1 = (ContractsTemplate)obj1;  
	            	ContractsTemplate contractsTemplate2 = (ContractsTemplate)obj2; 
	            	return contractsTemplate1.getTemplateName().compareToIgnoreCase(contractsTemplate2.getTemplateName());  
	            }  
	        });
			return templateList;
		
	}
	
	
	
	@Override
	@Transactional
	public List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType) throws Exception
	{
		
		String[] template = null;
		if(null != contractType && !"".equals(contractType)){	
			template = contractType.split(",");
		}
		List<String> statusList = new ArrayList<>();
		if(template != null){
			for(int i=0;i<template.length;i++){
				
				if("Vendor NSA".equalsIgnoreCase(template[i])){
					statusList.add("NSA");
				}else if("Vendor NDA".equalsIgnoreCase(template[i])){
					statusList.add("NDA");
				}else{
					statusList.add(template[i]);
				}
			}
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractsTemplate>  contractsTemplateList = null;
		if(null != statusList && !statusList.isEmpty()){
			contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.orgLocationId.id =:orgLocationId and ct.templateType IN (:statusList) and ct.templateStatus = '0'" ).setLong("orgLocationId", orgLocationId ).setParameterList("statusList", statusList).list();
		}else{
			contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.orgLocationId.id =:orgLocationId and ct.templateStatus = '0'" ).setLong("orgLocationId", orgLocationId ).list();
		}
		
		return contractsTemplateList;
	}

	@Override
	@Transactional
	public ContractsTemplate getContractTemplateById(long templateId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		ContractsTemplate  contractsTemplate =  null;
		List<ContractsTemplate>  contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.id =:templateId").setLong("templateId", templateId).list();
		if(null != contractsTemplateList && contractsTemplateList.size() >0){
			contractsTemplate = contractsTemplateList.get(0);
		}
		return contractsTemplate;
	}


	@Override
	@Transactional
	public Contracts saveNewContract(Contracts contracts) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contracts != null) {
			session.saveOrUpdate(contracts);
			}
			logger.debug("contractsTemplate Added Successfully");
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception)); 
//			   StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_SPOC"));
//				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return contracts;
	}

	@Override
	@Transactional
	public List<Contracts> getAllContractsByLocationIdAndStatus(OrgLocations orgLocation, User user, ContractsDTO contractsDTO) throws Exception
	{
		String userRoleCondition = "";
		Long userId = user.getId();
		String userCode = user.getUserCode();
		if(contractsDTO.getUserRoleName() != null && "ROLE_ADMIN".equalsIgnoreCase(contractsDTO.getUserRoleName()) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(contractsDTO.getUserRoleName())){
			userRoleCondition = "";
		}else{
			userRoleCondition = "and (contracts.approverUser.id =:userId OR contracts.createdBy =:userCode or (contracts.id IN (select coh.contract.id from ContractOwnerHierarchy coh where coh.ownerUserId.id =:userId)) or contracts.ownerUserId.id = :userId) ";
		}
		
		/*String ownerCondition = "";
		if(!(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName))){
			ownerCondition = "or (contracts.id IN (select coh.contract.id from ContractOwnerHierarchy coh where coh.ownerUserId.id =:userId))";
		}*/
		
		String subQuery = "";
		if(StringUtils.isNotBlank(contractsDTO.getContractsType())){
			String[] type = contractsDTO.getContractsType().split(",");
			if(type.length > 0){
				for(int i=0; i<type.length; i++){
					
					if(type[i].equalsIgnoreCase("MSA")){
						subQuery = subQuery + "contracts.contractType like '%MSA%' OR ";
					}
					if(type[i].equalsIgnoreCase("SOW")){
						subQuery = subQuery + "contracts.contractType like '%SOW%' OR ";
					}
					if(type[i].equalsIgnoreCase("NDA")){
						subQuery = subQuery + "contracts.contractType like '%NDA%' OR ";
					}
					if(type[i].equalsIgnoreCase("NSA")){
						subQuery = subQuery + "contracts.contractType like '%NSA%' OR ";
					}
				}
				if(null != subQuery && "" != subQuery){
					subQuery = subQuery.substring(0, subQuery.length() - 3);
					subQuery = "and (" +subQuery+ ")";
				}
			}
		}
		
		
		StringBuilder query1 = new StringBuilder("");
		if(contractsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(contractsDTO.getSearchValue())) {
			query1.append("and (contracts.contractTitle like '" + contractsDTO.getSearchValue() + "%')");
			
		}
		
		
		String[] status = null;
		if(null != contractsDTO.getContractStatus() && !"".equals(contractsDTO.getContractStatus())){	
			status = contractsDTO.getContractStatus().split(",");
		}
		List<String> statusList = new ArrayList<>();
		if(status != null){
			for(int i=0;i<status.length;i++){
				statusList.add(status[i]);
			}
			statusList.add(contractsDTO.getContractSubmissionType());
		}
		
		String subquery1 = "";
		if("WAITING_FOR_APPROVER".equalsIgnoreCase(contractsDTO.getContractSubmissionType())){
			subquery1 += " (contracts.templateSubmissionStatus = 'WAITING_FOR_APPROVER' or contracts.templateSubmissionStatus = 'WAITING_TO_SEND') ";
		}else if("WAITING_TO_BE_SIGNED".equalsIgnoreCase(contractsDTO.getContractSubmissionType())){
			subquery1 += " (contracts.templateSubmissionStatus = 'WAITING_TO_BE_SIGNED' or contracts.templateSubmissionStatus = 'PENDING') ";
		}else{
			subquery1 += " contracts.templateSubmissionStatus = '"+contractsDTO.getContractSubmissionType()+"'";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts>  ContractsList = null;
		if(null != statusList && !statusList.isEmpty()){
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
					+ "where contracts.orgLocationId.id = :orgLocationId and contracts.contractType like '%"+contractsDTO.getTabType()+"%' "+subQuery+" "+userRoleCondition+" and contracts.templateSubmissionStatus IN (:contractStatus)";
			if(contractsDTO.getUserRoleName() != null && "ROLE_ADMIN".equalsIgnoreCase(contractsDTO.getUserRoleName()) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(contractsDTO.getUserRoleName())){
				ContractsList = session.createQuery(query +query1.toString()).setLong("orgLocationId", orgLocation.getId()).setParameterList("contractStatus", statusList).list();
			}else{
				ContractsList = session.createQuery(query +query1.toString()).setLong("orgLocationId", orgLocation.getId()).setParameterList("contractStatus", statusList).setLong("userId", userId).setString("userCode", userCode).list();
			}
		}else{
			String query = "from Contracts contracts left join fetch contracts.contractsTemplate "
					+ "left join fetch contracts.requirementSubmission "
					+"left join fetch contracts.requirementSubmission.candidate "
					+"left join fetch contracts.requirementSubmission.candidateResumes "
					+ "left join fetch contracts.ownerUserId "
					+ "left join fetch contracts.orgLocationId "
					+ "left join fetch contracts.orgRelations "
					+ "left join fetch contracts.parentContractId "
					+ "left join fetch contracts.contractOrgLocationId "
					+ "left join fetch contracts.contactUser "
					+ "left join fetch contracts.approverUser "
					+ "left join fetch contracts.candidate "
					+ "where contracts.orgLocationId.id = :orgLocationId and "+subquery1+" and contracts.contractType like '%"+contractsDTO.getTabType()+"%' "+subQuery+" "+userRoleCondition;
			if(contractsDTO.getUserRoleName() != null && "ROLE_ADMIN".equalsIgnoreCase(contractsDTO.getUserRoleName()) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(contractsDTO.getUserRoleName())){
				ContractsList = session.createQuery(query +query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
			}else{
				ContractsList = session.createQuery(query +query1.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("userId", userId).setString("userCode", userCode).list();
			}
		}
		
		return ContractsList;
	}
	
	
	@Override
	@Transactional
	public Integer getAllContractsCountByLocationIdAndStatus(OrgLocations orgLocation, String submissionType,String tabType, String contractsType,String contractStatus, User user, String userRoleName) throws Exception
	{
		String userRoleCondition = "";
		Long userId = user.getId();
		String userCode = user.getUserCode();
		if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
			userRoleCondition = "";
		}else{
			userRoleCondition = "and (contracts.approverUser.id =:userId OR contracts.ownerUserId.id =:userId OR contracts.createdBy =:userCode) ";
		}
		String[] type = contractsType.split(",");
		String subQuery = "";
		if(type.length > 0){
			for(int i=0; i<type.length; i++){
				
				if(type[i].equalsIgnoreCase("MSA")){
					subQuery = subQuery + "contracts.contractType like '%MSA%' OR ";
				}
				if(type[i].equalsIgnoreCase("SOW")){
					subQuery = subQuery + "contracts.contractType like '%SOW%' OR ";
				}
				if(type[i].equalsIgnoreCase("NDA")){
					subQuery = subQuery + "contracts.contractType like '%NDA%' OR ";
				}
				if(type[i].equalsIgnoreCase("NSA")){
					subQuery = subQuery + "contracts.contractType like '%NSA%' OR ";
				}
			}
			if(null != subQuery && "" != subQuery){
				subQuery = subQuery.substring(0, subQuery.length() - 3);
				subQuery = "and (" +subQuery+ ")";
			}
		}
		/*String subQuery1 = "";
		String[] status = null;
		if(null != contractStatus && !"".equals(contractStatus)){	
			status = contractStatus.split(",");
		}*/
		List<String> statusList = new ArrayList<>();
		/*if(status != null){
			for(int i=0;i<status.length;i++){
				statusList.add(status[i]);
			}
		}*/
		
		String subquery1 = "";
		if("WAITING_FOR_APPROVER".equalsIgnoreCase(contractStatus) || "WAITING_TO_SEND".equalsIgnoreCase(contractStatus)){
			subquery1 += " (contracts.templateSubmissionStatus = 'WAITING_FOR_APPROVER' or contracts.templateSubmissionStatus = 'WAITING_TO_SEND') ";
		}else if("WAITING_TO_BE_SIGNED".equalsIgnoreCase(contractStatus) || "PENDING".equalsIgnoreCase(contractStatus)){
			subquery1 += " (contracts.templateSubmissionStatus = 'WAITING_TO_BE_SIGNED' or contracts.templateSubmissionStatus = 'PENDING') ";
		}else{
			subquery1 += " contracts.templateSubmissionStatus = '"+contractStatus+"'";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts>  ContractsList = null;
		if(null != statusList && !statusList.isEmpty()){
			String query = "from Contracts contracts left join fetch contracts.contractsTemplate "
					+ "left join fetch contracts.requirementSubmission "
					+"left join fetch contracts.requirementSubmission.candidate "
					+ "left join fetch contracts.requirementSubmission.candidateResumes "
					+"left join fetch contracts.requirementSubmission.requirement "
					+ "left join fetch contracts.ownerUserId "
					+ "left join fetch contracts.orgLocationId "
					+ "left join fetch contracts.orgRelations "
					+ "left join fetch contracts.parentContractId "
					+ "left join fetch contracts.contractOrgLocationId "
					+ "left join fetch contracts.contactUser "
					+ "left join fetch contracts.approverUser "
					+ "left join fetch contracts.candidate "
					+ "where contracts.orgLocationId.id = :orgLocationId and contracts.contractType like '%"+tabType+"%' "+subQuery+" "+userRoleCondition+" and contracts.templateSubmissionStatus IN (:contractStatus)";
			if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setParameterList("contractStatus", statusList).list();
			}else{
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setParameterList("contractStatus", statusList).setLong("userId", userId).setString("userCode", userCode).list();
			}
		}else{
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
					+ "where contracts.orgLocationId.id = :orgLocationId and "+subquery1+" and contracts.contractType like '%"+tabType+"%' "+subQuery+" "+userRoleCondition;
			if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).list();
			}else{
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setLong("userId", userId).setString("userCode", userCode).list();
			}
		}
		return ContractsList.size();
	}
	

	@Override
	@Transactional
	public Contracts getContractById(Long contractId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Contracts  contracts =  null;
		List<Contracts>  contractsList = session.createQuery("from Contracts ct left join fetch ct.parentContractId where ct.id =:contractId").setLong("contractId", contractId).list();
		if(null != contractsList && contractsList.size() >0){
			contracts = contractsList.get(0);
		}
		return contracts;
	}
	
	@Override
	@Transactional
	public Contracts getContractByContractUUID(String contractUUID)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Contracts  contracts =  null;
		List<Contracts>  contractsList = session.createQuery("from Contracts ct left join fetch ct.contractsTemplate left join fetch ct.requirementSubmission reqsub left join fetch reqsub.requirement left join fetch ct.ownerUserId left join fetch ct.orgLocationId left join fetch ct.orgRelations left join fetch ct.parentContractId left join fetch ct.contractOrgLocationId left join fetch ct.contactUser left join fetch ct.approverUser where ct.contractUUID =:contractUUID").setString("contractUUID", contractUUID).list();
		if(null != contractsList && contractsList.size() >0){
			contracts = contractsList.get(0);
		}
		return contracts;
	}

	@Override
	@Transactional
	public Contracts getChildContractById(Long contractId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Contracts  contracts =  null;
		List<Contracts>  contractsList = session.createQuery("from Contracts ct where ct.parentContractId.id =:contractId").setLong("contractId", contractId).list();
		if(null != contractsList && contractsList.size() >0){
			contracts = contractsList.get(0);
		}
		return contracts;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContractsTemplate findByContractTemplateName(String templateName,String contractType, Long orgLocationId, String templateUUID) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<ContractsTemplate> contractsTemp = new ArrayList<ContractsTemplate>();
			if(StringUtils.isNotBlank(templateUUID)) {
				contractsTemp = session.createQuery("from ContractsTemplate conTemp where conTemp.templateName=:templateName and conTemp.templateType=:contractType and conTemp.orgLocationId.id = :orgLocationId and conTemp.templateUUID != :templateUUID" )
						.setString( "templateName", templateName ).setString("contractType", contractType).setLong("orgLocationId", orgLocationId ).setString("templateUUID", templateUUID ).list();
			}else {
				contractsTemp = session.createQuery("from ContractsTemplate conTemp where conTemp.templateName=:templateName and conTemp.templateType=:contractType and conTemp.orgLocationId.id = :orgLocationId" )
						.setString( "templateName", templateName ).setString("contractType", contractType).setLong("orgLocationId", orgLocationId ).list();
			}
				
				if (contractsTemp.size() > 0) {
					return contractsTemp.get(0);
				} else {
					return null;
				}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Contracts> getListOfContractsEnvId() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts>  contractsList = session.createQuery("from Contracts ct left join fetch ct.candidate where (ct.typeOfContract = 'New' or ct.typeOfContract = 'Existing' or ct.typeOfContract = 'Custom') and ct.templateSubmissionStatus != 'EXPIRED' and ct.parentContractId.id IS NULL").list();
		return contractsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Contracts findContractTitleByOrgLocationAndContractType(String contractTitle, String contractType,Long orgLocationId,Long contractId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts> contracts = new ArrayList<Contracts>();
		String subQuery = "";
		if(Constants.NDA.equalsIgnoreCase(contractType) || Constants.NSA.equalsIgnoreCase(contractType)){
			subQuery = " contracts.contractType like '%"+contractType+"%' ";
		}else{
			subQuery = " contracts.contractType='"+contractType+"'";
		}
		if(null != contractId){
			contracts = session.createQuery("from Contracts contracts where contracts.contractTitle=:contractTitle and "+subQuery+" and contracts.orgLocationId.id = :orgLocationId and contracts.id <> :contractId " )
			.setString( "contractTitle", contractTitle ).setLong("orgLocationId", orgLocationId ).setLong("contractId", contractId ).list();
		}else{
			contracts = session.createQuery("from Contracts contracts where contracts.contractTitle=:contractTitle and "+subQuery+" and contracts.orgLocationId.id = :orgLocationId" )
					.setString( "contractTitle", contractTitle ).setLong("orgLocationId", orgLocationId ).list();
		}
			
		if (contracts.size() > 0) {
			return contracts.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContractsTemplate getContractTemplateByTemplateUUID(String tempUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		ContractsTemplate  contractsTemplate =  null;
		List<ContractsTemplate>  contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.templateUUID =:tempUUID").setString("tempUUID", tempUUID).list();
		if(null != contractsTemplateList && contractsTemplateList.size() >0){
			contractsTemplate = contractsTemplateList.get(0);
		}
		return contractsTemplate;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContractsHistory saveContractsHistory(ContractsHistory contractsHistory) throws Exception {
		logger.debug("Adding OrgSpoc");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contractsHistory != null) {
				session.saveOrUpdate(contractsHistory);
			}
			logger.debug("contractsHistory Added Successfully");
		} catch (Exception exception) {
			throw exception;
		}
		return contractsHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Contracts> getAllContractsBasedOnTimeFrequency(Date startDateUTC, Date endDateUTC)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts>  contractsList = session.createQuery("from Contracts ct left join fetch ct.contactUser where ct.templateSubmissionStatus <> 'DRAFT' and ( ct.endDate >= :startDateUTC and ct.endDate <= :endDateUTC )").setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		return contractsList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Contracts> getAllClientOrVendorContractsBasedOnLocationId(OrgLocations orgLocations, String responderType, User user, String userRoleName,String contractStatus,Long orgRelationId) throws Exception {
		String userRoleCondition = "";
		Long userId = user.getId();
		String userCode = user.getUserCode();
		if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
			userRoleCondition = "";
		}else{
			userRoleCondition = "and (contracts.approverUser.id =:userId OR contracts.ownerUserId.id =:userId OR contracts.createdBy =:userCode) ";
		}
		
		String[] status = null;
		if(null != contractStatus && !"".equals(contractStatus)){	
			status = contractStatus.split(",");
		}
		List<String> statusList = new ArrayList<>();
		if(status != null){
			for(int i=0;i<status.length;i++){
				statusList.add(status[i]);
			}
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts>  ContractsList = null;
		if(null != statusList && !statusList.isEmpty()){
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
					+ "where contracts.orgLocationId.id = :orgLocationId and contracts.orgRelations.id =:orgRelationId and contracts.contractType like '%"+responderType+"%' "+userRoleCondition+"and contracts.templateSubmissionStatus IN (:contractStatus)";
			if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("orgRelationId", orgRelationId).setParameterList("contractStatus", statusList).list();
			}else{
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("orgRelationId", orgRelationId).setParameterList("contractStatus", statusList).setLong("userId", userId).setString("userCode", userCode).list();
			}
		}else{
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
					+ "where contracts.orgLocationId.id = :orgLocationId and contracts.orgRelations.id =:orgRelationId and contracts.contractType like '%"+responderType+"%' "+userRoleCondition;
			if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("orgRelationId", orgRelationId).list();
			}else{
				ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("orgRelationId", orgRelationId).setLong("userId", userId).setString("userCode", userCode).list();
			}
		}
			
		
		return ContractsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContractsHistory getAllContractsHistoryByContractId(Long contractId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ContractsHistory  contractsHistory =  null;
		List<ContractsHistory>  contractsHistoryList = session.createQuery("from ContractsHistory ch where ch.contractsId =:contractId and ch.toStage ='EXPIRED' and ch.contractsId.templateSubmissionStatus='EXPIRED'")
				.setLong("contractId", contractId).list();
		if(null != contractsHistoryList && contractsHistoryList.size() >0){
			contractsHistory = contractsHistoryList.get(0);
		}
		return contractsHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContractsHistory getContractsHistoryByContractId(Long contractId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ContractsHistory  contractsHistory =  null;
		List<ContractsHistory>  contractsHistoryList = session.createQuery("from ContractsHistory ch where ch.id = (SELECT MAX(id) FROM ContractsHistory where ch.contractsId =:contractId)")
				.setLong("contractId", contractId).list();
		if(null != contractsHistoryList && contractsHistoryList.size() >0){
			contractsHistory = contractsHistoryList.get(0);
		}
		return contractsHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Contracts getContractByReqSubmissionSowTypeAndOrgLocId(Long reqSub, OrgLocations loginOrgLocation, String contractType)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts> contracts = new ArrayList<Contracts>();
		if(null != reqSub){
			contracts = session.createQuery("from Contracts contracts where contracts.requirementSubmission.id =:reqSub and contracts.contractType = :contractType and contracts.orgLocationId.id = :orgLocationId " )
			.setString("contractType", contractType).setLong("orgLocationId", loginOrgLocation.getId() ).setLong("reqSub", reqSub ).list();
		}
		if (contracts.size() > 0) {
			return contracts.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean getVendorMsaCreatedOrNot(Long vendorLocationId, Long loginLocatonId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts> contracts = null;
		contracts = session.createQuery("from Contracts contracts where contracts.orgLocationId.id =:loginLocatonId and contracts.contractOrgLocationId.id = :vendorLocationId and contracts.contractType = 'Vendor MSA' and contracts.templateSubmissionStatus <> 'EXPIRED' " )
				.setLong("loginLocatonId", loginLocatonId).setLong("vendorLocationId", vendorLocationId ).list();
		if (contracts != null && contracts.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public List<Contracts> getContractByLocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts> contractsList = session.createQuery("from Contracts contracts where contracts.orgLocationId.id =:orgLocationId").setLong("orgLocationId", orgLocationId).list();
		if (contractsList != null && contractsList.size() > 0) {
		return contractsList;
		}
		return null;
	}

	@Override
	@Transactional
	public List<User> getSigningAuthoritiesForContract(Long orgLocationsId, Long tabId)
	{
		Session session = this.sessionFactory.getCurrentSession();
	/*	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(roles.getActionId()!=null){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 //for (String s : roles.getActionId().split(",")){
	        		// roleActionsList.add(Long.parseLong(s));
	        	 //}
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);

	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
		 
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) and user.id IN (Select ols.userId.id from OrglocationSignatory ols where ols.authorizedType LIKE '%Contracts%' and ols.status='1')";
	     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).list();
	     
		
//		//String Query = "SELECT U.* FROM SOURCELEAD_ACTIONS SA,ROLES R "
//						+ " INNER JOIN  USER_ROLE UR ON UR.ROLE_ID = R.ID AND UR.USER_STATE != 'INACTIVE' AND UR.ORG_LOCATIONS_ID = R.ORG_LOCATION_ID "
//						+ " INNER JOIN  USER U ON U.ID = UR.USER_ID  "
//						+ " WHERE R.ORG_LOCATION_ID = :locationId AND SA.TAB_ID = :tabId "
//						+ " AND (R.ID LIKE CONCAT(SA.ID,'%') OR R.ID LIKE CONCAT('%,',SA.ID,',%') OR R.ID LIKE CONCAT('%,',SA.ID)) "
//						+"  AND U.ID IN(SELECT SO.USER_ID FROM ORGLOCATION_SIGNATORY SO WHERE SO.AUTHORIZED_TYPE LIKE '%Contracts%' AND SO.STATUS = 1 AND SO.ORGLOCATION_ID = :locationId) ";
*/	String Query ="SELECT * FROM USER u where u.ID IN (SELECT so.USER_ID from ORGLOCATION_SIGNATORY so where so.AUTHORIZED_TYPE LIKE '%Contracts%' and so.ORGLOCATION_ID=:ORGLOCATION_ID and so.STATUS=1 )";
		List<User> userRoleList = session.createSQLQuery(Query).addEntity(User.class).setLong( "ORGLOCATION_ID", orgLocationsId).list();
	     return userRoleList;
	}

	@Override
	@Transactional
	public List<Contracts> validateAlreadyGeneratedContract(ContractsDTO contractsDTO, OrgLocations loginOrgLocation) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = outputFormatter1.print(commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),contractsDTO.getStartDate()).getTime());
		String startDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, startDate);
		Date startDateUTC = ts.parse(startDateString);
		String endDate = outputFormatter.print(commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),contractsDTO.getEndDate()).getTime());
		String endDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, endDate);
		Date endDateUTC = ts.parse(endDateString);
		
		String query = "";
		List<Contracts> contractsList = null;
		if("Client SOW".equalsIgnoreCase(contractsDTO.getContractType()) || "Vendor SOW".equalsIgnoreCase(contractsDTO.getContractType())){
			query = "from Contracts contracts where contracts.orgLocationId.id =:orgLocationId and contracts.contractType =:contractType and contracts.requirementSubmission.id = :submissionId and ((contracts.startDate <= :startDate and contracts.endDate >= :startDate) or (contracts.startDate <= :endDate and contracts.endDate >= :endDate) or (contracts.startDate >= :startDate and contracts.endDate <= :endDate))";
			contractsList = session.createQuery(query).setLong("orgLocationId", loginOrgLocation.getId()).setLong("submissionId", Long.parseLong(contractsDTO.getSubmissionOffersList())).setString("contractType", contractsDTO.getContractType()).setParameter("startDate", startDateUTC).setParameter("endDate", endDateUTC).list();
		}else{
			query = "from Contracts contracts where contracts.orgLocationId.id =:orgLocationId and contracts.orgRelations.id =:orgRelationId and contracts.contractType =:contractType and ((contracts.startDate <= :startDate and contracts.endDate >= :startDate) or (contracts.startDate <= :endDate and contracts.endDate >= :endDate) or (contracts.startDate >= :startDate and contracts.endDate <= :endDate))";
			contractsList = session.createQuery(query).setLong("orgLocationId", loginOrgLocation.getId()).setLong("orgRelationId", Long.parseLong(contractsDTO.getOrgRelationId())).setString("contractType", contractsDTO.getContractType()).setParameter("startDate", startDateUTC).setParameter("endDate", endDateUTC).list();
		}
		
		
		if (contractsList != null && contractsList.size() > 0) {
			return contractsList;
		}
		else
			return null;
	}

	@Override
	@Transactional
	public ContractEditHistory saveContractEditHistory(ContractEditHistory contractEditHistory)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contractEditHistory != null) {
				session.saveOrUpdate(contractEditHistory);
			}
			logger.debug("contractEditHistory Added Successfully");
		} catch (Exception exception) {
			
		}
		return contractEditHistory;
	}

	@Override
	@Transactional
	public Contracts getMaxNoOfContract(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts> contractsList = null;
		String query = "from Contracts con where con.id = (SELECT MAX(id) FROM Contracts ch where ch.orgLocationId.id =:locationId) ";
		contractsList = session.createQuery(query).setLong("locationId", locationId).list();
		if (contractsList != null && contractsList.size() > 0)
			return contractsList.get(0);
		else
			return null;
	}

	@Override
	@Transactional
	public List<Contracts> getContractsBylocationIdForReminder(Long locationId, String status, Date date)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts> contractList = null;
		String query = "from Contracts contracts where contracts.orgLocationId.id =:orgLocationId and contracts.templateSubmissionStatus =:status and contracts.id IN (select contractHistory.contractsId.id from ContractsHistory contractHistory where contractHistory.contractsId.id = contracts.id and contractHistory.createdDate <= :date)";
		contractList = session.createQuery(query).setLong("orgLocationId", locationId).setString("status", status).setParameter("date", date).list();
		return contractList;
	}

	@Override
	@Transactional
	public Integer deleteContractOwnerHierarchyListByContractId(Long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Integer status = null;
		Query q = session.createQuery("delete ContractOwnerHierarchy coh where coh.contract.id =:id").setLong("id", id);
		status = q.executeUpdate();
		return status;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Contracts> getAllContractsBasedOnConfigureDate(Date updatedDate) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		Calendar c = Calendar.getInstance(); 
		c.setTime(updatedDate); 
		c.add(Calendar.DATE, 1);
		Date nextUpdatedDate = c.getTime();
		
		@SuppressWarnings("unchecked")
		List<Contracts>  contractsList = session.createQuery("from Contracts ct left join fetch ct.contactUser where ct.endDate BETWEEN :updatedDate and :nextUpdatedDate ").setParameter("updatedDate", updatedDate).setParameter("nextUpdatedDate", nextUpdatedDate).list();
		return contractsList;
	}
}

