package com.bis.sourcelead.workflow.dao.Impl;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.workflow.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.entity.RequirementDefaultWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;


@Repository
public class RequirementWorkFlowDaoImpl implements RequirementWorkFlowDao {
	
@Autowired
private SessionFactory sessionFactory;

@Value("${SourceLead.106}")
private String duplicateValue;

private static final Logger logger = LoggerFactory.getLogger(RequirementWorkFlowDaoImpl.class);

@Override
@Transactional(rollbackFor = { Exception.class })
public RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlow requirementWorkFlow,
		OrgLocations orgLocations) {
	logger.debug("saveRequirementWorkFlowDetails starts");
	Session session = this.sessionFactory.getCurrentSession();
	try {
		
		if (requirementWorkFlow != null) {
			session.saveOrUpdate(requirementWorkFlow);
		}
		
	} catch (Exception exception) {
		logger.error("Exception caught while saving requirementWorkFlow information " + exception.getMessage());
		exception.printStackTrace();
	}
	return requirementWorkFlow;
}

@Override
@Transactional(rollbackFor = { Exception.class })
public RequirementWorkFlow getRequirementWorkFlowById(Long id) {
	
	RequirementWorkFlow requirementWorkFlow = null;
	try {
		Session session = this.sessionFactory.getCurrentSession();
		requirementWorkFlow = (RequirementWorkFlow) session.get(RequirementWorkFlow.class, id);
	} catch (HibernateException e) {
		logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
		e.printStackTrace();
	}
	logger.debug("got the record id -->" + id);
	return requirementWorkFlow;
}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlow requirementWorkFlow where requirementWorkFlow.orgLocationId.id = :orgLocationId";
			List<RequirementWorkFlow> requirementWorkFlow = session.createQuery(query)
					.setParameter("orgLocationId", orgLocationId).list();
			return requirementWorkFlow;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementDefaultWorkFlowStages> getRequirementDefaultWorkFlowStages() {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementDefaultWorkFlowStages requirementDefaultWorkFlowStages";
			List<RequirementDefaultWorkFlowStages> requirementDefaultWorkFlowStages = session.createQuery(query).list();
			return requirementDefaultWorkFlowStages;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveRequirementWorkFlowStagesByOrgLocationId(RequirementWorkFlowStages requirementWorkFlowStages){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(requirementWorkFlowStages);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}     
       
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowsByOrgLocationId(
			RequirementWorkFlowDTO workFlowDTO, OrgLocations orgLocations) throws Exception {
		try{
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> countlist = null;
			Integer filterCount = 0;
			StringBuilder queryEndBuilder = new StringBuilder("");
			RequirementWorkFlowDTO requirementWorkFlowDTO = null;
			List<RequirementWorkFlowDTO> requirementWorkFlowDTOList = new ArrayList<>();
			ResponseList<RequirementWorkFlowDTO> responseList = new ResponseList<>();
			if(workFlowDTO != null){
				if (StringUtils.isNotBlank(workFlowDTO.getSortColumn())) {
					String sortColumnName = workFlowDTO.getSortColumn();
					if(sortColumnName.equalsIgnoreCase("totalResumePrice")){
						queryEndBuilder.append(" order by requirementWorkFlow.updatedDate "+workFlowDTO.getSortOrder());
					}
				}
				}
			String query = "from RequirementWorkFlow requirementWorkFlow where requirementWorkFlow.orgLocationId.id = :orgLocationId and requirementWorkFlow.status= 1 ORDER BY requirementWorkFlow.id DESC";
			String countQuery = "select count(*) from RequirementWorkFlow requirementWorkFlow  where  requirementWorkFlow.orgLocationId.id =:orgLocationId";
			Query query1 = session.createQuery(query + queryEndBuilder).setLong("orgLocationId", orgLocations.getId());
			Query query2 = session.createQuery(countQuery + queryEndBuilder).setLong("orgLocationId", orgLocations.getId());
			countlist = query2.list();
			int totalCount = 0;
			if (workFlowDTO != null) {
				if (countlist != null && !countlist.isEmpty()) {
					totalCount = countlist.iterator().next().intValue();
					filterCount = totalCount;
				}
			}
			responseList.setRecordsTotal(totalCount);
			if (query.toString() != null && !query.toString().isEmpty()) {
				responseList.setRecordsFiltered(filterCount);
			} else {
				responseList.setRecordsFiltered(totalCount);
			}
			List<RequirementWorkFlow> requirementWorkFlow = query1.list();
			if(requirementWorkFlow != null){
				for(RequirementWorkFlow workFlow : requirementWorkFlow){
					requirementWorkFlowDTO = new RequirementWorkFlowDTO();
					requirementWorkFlowDTO.setWorkFlowName(workFlow.getWorkFlowName());
					requirementWorkFlowDTO.setNoOfStages(workFlow.getNoOfStages().toString());
					requirementWorkFlowDTO.setStatus(workFlow.getStatus());
					requirementWorkFlowDTO.setUuid(workFlow.getUuid());
					requirementWorkFlowDTOList.add(requirementWorkFlowDTO);
					}
				}
			responseList.setData(requirementWorkFlowDTOList);
			return responseList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementWorkFlow getRequirementWorkFlowByUuid(String uuid) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlow requirementWorkFlow where requirementWorkFlow.uuid =:uuid";
			RequirementWorkFlow requirementWorkFlow = (RequirementWorkFlow)session.createQuery(query).setParameter("uuid", uuid).uniqueResult();
			return requirementWorkFlow;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
	}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate requirementQuestionnaireTemplate) throws Exception {
		logger.debug("Adding requirementQuestionnaireTemplate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementQuestionnaireTemplate != null) {
				session.saveOrUpdate(requirementQuestionnaireTemplate);
			}
			logger.debug("RequirementQuestionnaireTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_QUESTIONNAIRE_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementQuestionnaireTemplate;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementWorkFlowStages> getRequirementWorkflowStagesByUuid(String uuid) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:uuid";
			List<RequirementWorkFlowStages> requirementWorkFlowStages = session.createQuery(query).setParameter("uuid", uuid).list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
	}
	}   
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(RequirementQuestionnaireTemplateDTO questionnaireDto 
			                                                ,Long orgLocationId) {
		try{
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> countlist = null;
			Integer filterCount = 0;
			StringBuilder queryEndBuilder = new StringBuilder("");
			List<RequirementQuestionnaireTemplateDTO> dtoList = new ArrayList<>();
			ResponseList<RequirementQuestionnaireTemplateDTO> responseList = new ResponseList<>();
	/*		if(questionnaireDto != null){
				if (StringUtils.isNotBlank(questionnaireDto.getSortColumn())) {
					String sortColumnName = questionnaireDto.getSortColumn();
					if(sortColumnName.equalsIgnoreCase("totalResumePrice")){
						queryEndBuilder.append(" order by requirementWorkFlow.updatedDate "+questionnaireDto.getSortOrder());
					}
				}
				}*/
			String query = " from RequirementQuestionnaireTemplate rq where rq.orgLocationId.id=:orgLocationId";
			String countQuery = "select count(*)  from RequirementQuestionnaireTemplate rq where rq.orgLocationId.id=:orgLocationId";
			Query query1 = session.createQuery(query + queryEndBuilder).setLong("orgLocationId", orgLocationId);
			Query query2 = session.createQuery(countQuery + queryEndBuilder).setLong("orgLocationId", orgLocationId);
			countlist = query2.list();
			int totalCount = 0;
			if (questionnaireDto != null) {
				if (countlist != null && !countlist.isEmpty()) {
					totalCount = countlist.iterator().next().intValue();
					filterCount = totalCount;
				}
			}
			responseList.setRecordsTotal(totalCount);
			if (query.toString() != null && !query.toString().isEmpty()) {
				responseList.setRecordsFiltered(filterCount);
			} else {
				responseList.setRecordsFiltered(totalCount);
			}
			List<RequirementQuestionnaireTemplate> questinnarieList = query1.list();
			if(questionnaireDto != null){
				for(RequirementQuestionnaireTemplate source: questinnarieList ) {
					RequirementQuestionnaireTemplateDTO target = new RequirementQuestionnaireTemplateDTO();
					target.setName(source.getName());
					target.setDescription(source.getDescription());
					target.setCreatedBy(source.getCreatedBy().getFullName());
					target.setUuid(source.getUuid());
					dtoList.add(target);
				}
			}
			responseList.setData(dtoList);
			return responseList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
   }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId, String templateName) {
		try {
			Session session = this.sessionFactory.getCurrentSession(); 
			List<RequirementQuestionnaireTemplate> questionnaireTemplate = new ArrayList<RequirementQuestionnaireTemplate>();
			logger.debug("getQuestionnaireTemplateByLabelName start");
			questionnaireTemplate = session.createQuery("from RequirementQuestionnaireTemplate rq where rq.orgLocationId =:orgLocationId and rq.name like '%"
																+ templateName + "%' ").setLong("orgLocationId", orgLocationId).list();
			logger.debug("getQuestionnaireTemplateByLabelName end");
			if (questionnaireTemplate.size() > 0) {
				return questionnaireTemplate.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught while getting template list from the database");
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementWorkFlow getRequirementDefaultWorkflow()  {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlow requirementWorkFlow ORDER BY requirementWorkFlow.id ASC";
			RequirementWorkFlow requirementWorkFlow = (RequirementWorkFlow)session.createQuery(query).setMaxResults(1).uniqueResult();
			return requirementWorkFlow;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
	
	}
}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteQuestionnaireTemplateById(String templateUuid) {
		logger.debug("Delete RequirementQuestionnaireTemplate templateUuid ---> " + templateUuid);

		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from RequirementQuestionnaireTemplate rq  where rq.uuid =:templateUuid";
			 int count= session.createQuery(query.toString()).setString("templateUuid", templateUuid).executeUpdate();
			 logger.debug("Record deleted count  -->" + count);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Deleted record id -->" + templateUuid);
		return "success";
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid) {
		// TODO Auto-generated method stub
		logger.debug("Delete RequirementQuestionnaireTemplate templateUuid ---> " + templateUuid);
		RequirementQuestionnaireTemplate template= null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = " from RequirementQuestionnaireTemplate rq  where rq.uuid =:templateUuid";
			template= (RequirementQuestionnaireTemplate) session.createQuery(query).setString("templateUuid", templateUuid).uniqueResult();
			 logger.debug("loading RequirementQuestionnaireTemplate  -->" + template.getName());
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while getting  RequirementQuestionnaireTemplate -->" + e.getMessage());
			e.printStackTrace();
		}
		return template;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementWorkFlowStages getRequirementWorkFlowStageByUuidAndWorkflowUuid(String uuid, String workflowUuid) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:workflowUuid and requirementWorkFlowStages.uuid =:uuid";
			RequirementWorkFlowStages requirementWorkFlowStages = (RequirementWorkFlowStages)session.createQuery(query).setParameter("workflowUuid", workflowUuid).setParameter("uuid", uuid).uniqueResult();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
	}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementWorkFlowStages> getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(String workflowUuid, Integer id) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:workflowUuid "
					+ "and  requirementWorkFlowStages.seqOrder > "+id+" order by requirementWorkFlowStages.seqOrder ASC "; 
			List<RequirementWorkFlowStages> requirementWorkFlowStages = session.createQuery(query).setParameter("workflowUuid", workflowUuid).list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
	}
	}
	
}