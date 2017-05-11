package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.pdfbox.util.StringUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ContractsTemplateDaoImpl implements ContractsTemplateDao{
	@Autowired
	private SessionFactory sessionFactory;
	
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
		contractsTemplateList = session.createQuery("from ContractsTemplate ct where id=:contractTemplateId " ).setLong( "contractTemplateId", contractTemplateId ).setCacheable(true).list();
		logger.debug("getOrgSpocById end");
		if (contractsTemplateList.size() > 0) {
			return contractsTemplateList.get(0);
		} else {
			return null;
		}
	}*/

	@Override
	@Transactional
	public List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation) throws Exception
	{
		Long orgLocationId = orgLocation.getId();
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractsTemplate>  contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.orgLocationId.id =:orgLocationId and ct.templateStatus = '0'" ).setLong("orgLocationId", orgLocationId ).setCacheable(true).list();
			return contractsTemplateList;
		}

	@Override
	@Transactional
	public List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractsTemplate>  contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.orgLocationId.id =:orgLocationId and ct.templateType = :contractType and ct.templateStatus = '0'" ).setLong("orgLocationId", orgLocationId ).setString("contractType", contractType).setCacheable(true).list();
		return contractsTemplateList;
	}

	@Override
	@Transactional
	public ContractsTemplate getContractTemplateById(long templateId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		ContractsTemplate  contractsTemplate =  null;
		List<ContractsTemplate>  contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.id =:templateId").setLong("templateId", templateId).setCacheable(true).list();
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
	public List<Contracts> getAllContractsByLocationIdAndStatus(OrgLocations orgLocation, String submissionType,String tabType, String contractsType,String contractStatus) throws Exception
	{
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
				if(type[i].equalsIgnoreCase("NCA")){
					subQuery = subQuery + "contracts.contractType like '%NCA%' OR ";
				}
			}
			if(null != subQuery && "" != subQuery){
				subQuery = subQuery.substring(0, subQuery.length() - 3);
				subQuery = "and (" +subQuery+ ")";
			}
		}
		
		String subQuery1 = "";
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
					+ "left join fetch contracts.ownerUserId "
					+ "left join fetch contracts.orgLocationId "
					+ "left join fetch contracts.orgRelations "
					+ "left join fetch contracts.parentContractId "
					+ "left join fetch contracts.contractOrgLocationId "
					+ "left join fetch contracts.contactUser "
					+ "left join fetch contracts.approverUser "
					+ "where contracts.orgLocationId.id = :orgLocationId and contracts.contractType like '%"+tabType+"%' "+subQuery+" and contracts.templateSubmissionStatus IN (:contractStatus)";
			ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setParameterList("contractStatus", statusList).setCacheable(true).list();
		}else{
			String query = "from Contracts contracts left join fetch contracts.contractsTemplate "
					+ "left join fetch contracts.requirementSubmission "
					+ "left join fetch contracts.ownerUserId "
					+ "left join fetch contracts.orgLocationId "
					+ "left join fetch contracts.orgRelations "
					+ "left join fetch contracts.parentContractId "
					+ "left join fetch contracts.contractOrgLocationId "
					+ "left join fetch contracts.contactUser "
					+ "left join fetch contracts.approverUser "
					+ "where contracts.orgLocationId.id = :orgLocationId and contracts.templateSubmissionStatus = :submissionType and contracts.contractType like '%"+tabType+"%' "+subQuery;
			ContractsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setString("submissionType", submissionType).setCacheable(true).list();
		}
		
		return ContractsList;
	}

	@Override
	@Transactional
	public Contracts getContractById(Long contractId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Contracts  contracts =  null;
		List<Contracts>  contractsList = session.createQuery("from Contracts ct where ct.id =:contractId").setLong("contractId", contractId).setCacheable(true).list();
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
		List<Contracts>  contractsList = session.createQuery("from Contracts ct where ct.parentContractId.id =:contractId").setLong("contractId", contractId).setCacheable(true).list();
		if(null != contractsList && contractsList.size() >0){
			contracts = contractsList.get(0);
		}
		return contracts;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ContractsTemplate findByContractTemplateName(String templateName,String contractType, Long orgLocationId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<ContractsTemplate> contractsTemp = new ArrayList<ContractsTemplate>();
				contractsTemp = session.createQuery("from ContractsTemplate conTemp where conTemp.templateName=:templateName and conTemp.templateType=:contractType and conTemp.orgLocationId.id = :orgLocationId" )
						.setString( "templateName", templateName ).setString("contractType", contractType).setLong("orgLocationId", orgLocationId ).setCacheable(true).list();
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
		List<Contracts>  contractsList = session.createQuery("from Contracts ct where ct.typeOfContract = 'New' and ct.parentContractId.id IS NULL and ct.contractStatus != 'completed'").setCacheable(true).list();
		return contractsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Contracts findContractTitleByOrgLocationAndContractType(String contractTitle, String contractType,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts> contracts = new ArrayList<Contracts>();
			contracts = session.createQuery("from Contracts contracts where contracts.contractTitle=:contractTitle and contracts.contractType = :contractType and contracts.orgLocationId.id = :orgLocationId" )
					.setString( "contractTitle", contractTitle ).setString("contractType", contractType).setLong("orgLocationId", orgLocationId ).setCacheable(true).list();
		if (contracts.size() > 0) {
			return contracts.get(0);
		} else {
			return null;
		}
	}

}
