package com.bis.sourcelead.workflow.dao.Impl;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.workflow.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireFeedbackTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowTasksDTO;
import com.bis.sourcelead.workflow.entity.RequirementDefaultWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireFeedbackTemplate;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowTasks;


@Repository
@Transactional
public class RequirementWorkFlowDaoImpl implements RequirementWorkFlowDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${SourceLead.106}")
	private String duplicateValue;

	private static final Logger logger = LoggerFactory.getLogger(RequirementWorkFlowDaoImpl.class);

	@Override
	public RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlow requirementWorkFlow,
			OrgLocations orgLocations) {
		logger.debug("saveRequirementWorkFlowDetails starts");

		try {
			Session session = this.sessionFactory.getCurrentSession();
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
	public RequirementWorkFlow getRequirementWorkFlowById(Long id) {
		logger.debug("Running getRequirementWorkFlowById DAO");
		RequirementWorkFlow requirementWorkFlow = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			requirementWorkFlow = (RequirementWorkFlow) session.get(RequirementWorkFlow.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e);
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return requirementWorkFlow;
	}


	@Override
	public List<RequirementWorkFlow> getRequirementWorkFlowByOrgLocationId(Long orgLocationId) {
		logger.debug("Running getRequirementWorkFlowByOrgLocationId DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlow requirementWorkFlow where (requirementWorkFlow.orgLocationId.id = :orgLocationId or requirementWorkFlow.orgLocationId.id is NULL) and requirementWorkFlow.status=1";
			List<RequirementWorkFlow> requirementWorkFlow = session.createQuery(query)
					.setParameter("orgLocationId", orgLocationId).list();
			return requirementWorkFlow;
		} catch (Exception e) {
			logger.error(e+" in getRequirementWorkFlowByOrgLocationId DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RequirementDefaultWorkFlowStages> getRequirementDefaultWorkFlowStages() {
		logger.debug("Running getRequirementDefaultWorkFlowStages DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementDefaultWorkFlowStages requirementDefaultWorkFlowStages";
			List<RequirementDefaultWorkFlowStages> requirementDefaultWorkFlowStages = session.createQuery(query).list();
			return requirementDefaultWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in getRequirementDefaultWorkFlowStages DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RequirementWorkFlowStages saveRequirementWorkFlowStagesByOrgLocationId(RequirementWorkFlowStages requirementWorkFlowStages){
		logger.debug("Running saveRequirementWorkFlowStagesByOrgLocationId DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(requirementWorkFlowStages);
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in saveRequirementWorkFlowStagesByOrgLocationId DAO");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ResponseList<RequirementWorkFlowDTO> getRequirementWorkFlowsByOrgLocationId(
			RequirementWorkFlowDTO workFlowDTO, OrgLocations orgLocations) {
		logger.debug("Running getRequirementWorkFlowsByOrgLocationId DAO");
		try{
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> countlist = null;
			Integer filterCount = 0;
			RequirementWorkFlowDTO requirementWorkFlowDTO = null;
			List<RequirementWorkFlowDTO> requirementWorkFlowDTOList = new ArrayList<>();
			ResponseList<RequirementWorkFlowDTO> responseList = new ResponseList<>();

			//sorting order
			StringBuilder queryEndBuilder = new StringBuilder("");
			if(StringUtils.isNotBlank(workFlowDTO.getSortColumn())) {
				String colName = workFlowDTO.getSortColumn();
				  if(workFlowDTO.getSortColumn().equalsIgnoreCase("workFlowName")){
					   queryEndBuilder.append(" ORDER BY  requirementWorkFlow."+" "+colName+" "+workFlowDTO.getSortOrder()); 
				  } else {
						queryEndBuilder.append(" ORDER BY requirementWorkFlow."+" "+colName+" "+workFlowDTO.getSortOrder());
				  }
				}
			
			StringBuilder searchQuery = new StringBuilder("");
			String searchValue = workFlowDTO.getSearchValue();
			if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
				searchQuery.append(" and ((requirementWorkFlow.workFlowName like '%" + searchValue + "%' )");
				//queryEndBuilder.append(" or (requirementWorkFlow.noOfStages like '%" + searchValue + "%' )");
				searchQuery.append(" or (requirementWorkFlow.noOfStages like '%" + searchValue + "%' ))");
			}
			
			String query = "from RequirementWorkFlow requirementWorkFlow where (requirementWorkFlow.orgLocationId.id = :orgLocationId or requirementWorkFlow.orgLocationId.id is NULL)";
			String countQuery = "select count(*) from RequirementWorkFlow requirementWorkFlow where (requirementWorkFlow.orgLocationId.id =:orgLocationId or requirementWorkFlow.orgLocationId.id is NULL)";
			Query query1 = session.createQuery(query +searchQuery+ queryEndBuilder).setLong("orgLocationId", orgLocations.getId());
			Query query2 = session.createQuery(countQuery +searchQuery+ queryEndBuilder).setLong("orgLocationId", orgLocations.getId());
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
				StringBuilder userName = new StringBuilder("");	
				for(RequirementWorkFlow workFlow : requirementWorkFlow){
					requirementWorkFlowDTO = new RequirementWorkFlowDTO();
					requirementWorkFlowDTO.setWorkFlowName(workFlow.getWorkFlowName());
					requirementWorkFlowDTO.setNoOfStages(workFlow.getNoOfStages().toString());
					requirementWorkFlowDTO.setStatus(workFlow.getStatus());
					requirementWorkFlowDTO.setUuid(workFlow.getUuid());
					requirementWorkFlowDTO.setDescription(workFlow.getDescription());
					/*userName.append(workFlow.getUpdatedBy().getFirstName()+""+workFlow.getUpdatedBy().getLastName());
					requirementWorkFlowDTO.setUpdatedBy(userName.toString());*/
					userName.setLength(0);
					requirementWorkFlowDTO.setConfigureType(workFlow.getConfigureType());
					requirementWorkFlowDTOList.add(requirementWorkFlowDTO);
				}
			}
			responseList.setData(requirementWorkFlowDTOList);
			return responseList;
		}catch(Exception e){
			logger.error(e+" in getRequirementWorkFlowsByOrgLocationId DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RequirementWorkFlow getRequirementWorkFlowByUuid(String uuid, Long orgLocationId) {
		logger.debug("Running getRequirementWorkFlowByUuid DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlow requirementWorkFlow where requirementWorkFlow.uuid =:uuid and (requirementWorkFlow.orgLocationId.id =:orgLocationId or requirementWorkFlow.orgLocationId.id is NULL)";
			RequirementWorkFlow requirementWorkFlow = (RequirementWorkFlow)session.createQuery(query).setParameter("uuid", uuid).setLong("orgLocationId", orgLocationId).uniqueResult();
			return requirementWorkFlow;
		} catch (Exception e) {
			logger.error(e+" in getRequirementWorkFlowByUuid DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RequirementQuestionnaireTemplate saveQuestionnaireTemplate(RequirementQuestionnaireTemplate requirementQuestionnaireTemplate) throws Exception {
		logger.debug("Running saveQuestionnaireTemplate DAO");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementQuestionnaireTemplate != null) {
				session.saveOrUpdate(requirementQuestionnaireTemplate);
			}
			logger.debug("RequirementQuestionnaireTemplate Added Successfully");
		} catch (Exception exception) {
			logger.error(exception+" in saveQuestionnaireTemplate DAO");
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_QUESTIONNAIRE_TEMPLATE"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementQuestionnaireTemplate;

	}
	
	
	@Override
	public RequirementQuestionnaireTemplate getQuestionnaireByTemplateId(long templateId) throws Exception {
		RequirementQuestionnaireTemplate template = null;
		logger.info("inside getting template by id " + templateId);
		try {
			Session session = this.sessionFactory.getCurrentSession();
			template = (RequirementQuestionnaireTemplate) session.get(RequirementQuestionnaireTemplate.class,templateId);
		} catch (Exception e) {
			logger.error("Exception caught while getting template by id " + templateId);
			e.printStackTrace();
		}
		return template;
	}

	//and requirementWorkFlowStages.configureType <> '"+Constants.INACTIVE+"'
	@Override
	public List<RequirementWorkFlowStages> getRequirementWorkflowStagesListByUuid(String uuid, Long orgLocationId) {
		logger.debug("Running getRequirementWorkflowStagesListByUuid DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:uuid "
					+ "and (requirementWorkFlowStages.orgLocationId.id =:orgLocationId or requirementWorkFlowStages.orgLocationId.id is NULL)"
					+ " and requirementWorkFlowStages.status = 1 ORDER BY requirementWorkFlowStages.seqOrder ASC";
			List<RequirementWorkFlowStages> requirementWorkFlowStages = session.createQuery(query).setParameter("uuid", uuid).setLong("orgLocationId", orgLocationId).list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in getRequirementWorkflowStagesListByUuid DAO");
			e.printStackTrace();
			return null;
		}
	} 
	@SuppressWarnings("unchecked")
	@Override
	public ResponseList<RequirementQuestionnaireTemplateDTO> getQuestionnaireTemplateListByOrgLocationId(RequirementQuestionnaireTemplateDTO questionnaireDto 
			,String searchValue, Long orgLocationId) {
		logger.debug("Running getQuestionnaireTemplateListByOrgLocationId DAO");
		try{
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> countlist = null;
			Integer filterCount = 0;

			List<RequirementQuestionnaireTemplateDTO> dtoList = new ArrayList<>();
			ResponseList<RequirementQuestionnaireTemplateDTO> responseList = new ResponseList<>();
// 
		//Questionnaire sorting	
			StringBuilder queryEndBuilder = new StringBuilder("");
			if(StringUtils.isNotBlank(questionnaireDto.getSortColumn())) {
				String colName = questionnaireDto.getSortColumn();
				  if(questionnaireDto.getSortColumn().equalsIgnoreCase("name")){
					   queryEndBuilder.append(" ORDER BY  rq."+" "+colName+" "+questionnaireDto.getSortOrder()); 
				  } else {
						queryEndBuilder.append(" ORDER BY rq."+" "+colName+" "+questionnaireDto.getSortOrder());
				  }
				}
			  
			if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
				queryEndBuilder.append(" and ((rq.name like '%" + searchValue + "%' )");
				queryEndBuilder.append(" or (rq.description like '%" + searchValue + "%' )");
				queryEndBuilder.append(" or (rq.createdBy.lastName like '%" + searchValue + "%' ))");
				queryEndBuilder.append(" or (rq.createdBy.firstName like '%" + searchValue + "%' ))");
				//query1.append(" or (tempView.status like '%" + searchValue + "%' )");
			}
			String query = " from RequirementQuestionnaireTemplate rq where rq.orgLocationId.id=:orgLocationId";
			String countQuery = "select count(*) from RequirementQuestionnaireTemplate rq where rq.orgLocationId.id=:orgLocationId";
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
			logger.error(e+" in getQuestionnaireTemplateListByOrgLocationId DAO");
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RequirementQuestionnaireTemplate getQuestionnaireTemplateByLabelName(Long orgLocationId, String templateName) {
		logger.debug("Running getQuestionnaireTemplateByLabelName DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession(); 
			List<RequirementQuestionnaireTemplate> questionnaireTemplate = new ArrayList<RequirementQuestionnaireTemplate>();
			logger.debug("getQuestionnaireTemplateByLabelName start");
			questionnaireTemplate = session.createQuery("from RequirementQuestionnaireTemplate rq where rq.orgLocationId =:orgLocationId and rq.name like '"
					+ templateName + "' ").setLong("orgLocationId", orgLocationId).list();
			logger.debug("getQuestionnaireTemplateByLabelName end");
			if (questionnaireTemplate.size() > 0) {
				return questionnaireTemplate.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e+" in getQuestionnaireTemplateByLabelName DAO");
		}
		return null;
	}

	@Override
	public RequirementWorkFlow getRequirementDefaultWorkflow(Long orgLocationId) {
		logger.debug("Running getRequirementDefaultWorkflow DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlow requirementWorkFlow where (requirementWorkFlow.orgLocationId.id =:orgLocationId or requirementWorkFlow.orgLocationId.id is NULL) and requirementWorkFlow.configureType=:default";
			RequirementWorkFlow requirementWorkFlow = (RequirementWorkFlow)session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("default",Constants.DEFAULT).uniqueResult();
			return requirementWorkFlow;
		} catch (Exception e) {
			logger.error(e+" in getRequirementDefaultWorkflow DAO");
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public String deleteQuestionnaireTemplateById(Long id) {
		logger.debug("Running deleteQuestionnaireTemplateById DAO");
		Session session = this.sessionFactory.getCurrentSession();
		Object questionnaire = null;
		RequirementQuestionnaireTemplate requirementQuestionnaireTemplate = null;
		try {
			questionnaire = session.get(RequirementQuestionnaireTemplate.class, id);
			requirementQuestionnaireTemplate = (RequirementQuestionnaireTemplate) questionnaire;
			session.delete(requirementQuestionnaireTemplate);
		} catch (HibernateException e) {
			logger.error(e+" in deleteQuestionnaireTemplateById DAO");
			e.printStackTrace();
		}
		logger.debug("Deleted record id -->" + id);
		return "success";
	}

	@Override
	public RequirementQuestionnaireTemplate getQuestionnaireTemplateById(String templateUuid) {
		// TODO Auto-generated method stub
		logger.debug("Running getQuestionnaireTemplateById DAO");
		RequirementQuestionnaireTemplate template= null;
		if(StringUtils.isNotBlank(templateUuid)) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = " from RequirementQuestionnaireTemplate rq where rq.uuid =:templateUuid";
			template= (RequirementQuestionnaireTemplate) session.createQuery(query).setString("templateUuid", templateUuid).uniqueResult();
			logger.debug("loading RequirementQuestionnaireTemplate -->" + template.getName());
		} catch (HibernateException e) {
			logger.error(e+" in getQuestionnaireTemplateById DAO");
			e.printStackTrace();
		}
		}
		return template;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementWorkFlowStages getRequirementWorkFlowStageByUuidAndWorkflowUuid(String uuid, String workflowUuid, Long orgLocationId) {
		logger.debug("Running getRequirementWorkFlowStageByUuidAndWorkflowUuid DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:workflowUuid and requirementWorkFlowStages.uuid =:uuid and requirementWorkFlowStages.orgLocationId.id =:orgLocationId";
			RequirementWorkFlowStages requirementWorkFlowStages = (RequirementWorkFlowStages)session.createQuery(query).setParameter("workflowUuid", workflowUuid).setParameter("uuid", uuid).setLong("orgLocationId", orgLocationId).uniqueResult();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in getRequirementWorkFlowStageByUuidAndWorkflowUuid DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementWorkFlowStages> getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid(String workflowUuid, Integer id, Long orgLocationId) {
		logger.debug("Running getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:workflowUuid and requirementWorkFlowStages.orgLocationId.id =:orgLocationId "
					+ " and requirementWorkFlowStages.seqOrder >= "+id+" order by requirementWorkFlowStages.seqOrder ASC "; 
			List<RequirementWorkFlowStages> requirementWorkFlowStages = session.createQuery(query).setParameter("workflowUuid", workflowUuid).setLong("orgLocationId", orgLocationId).list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in getRequirementWorkflowStagesBySeqOrderAndWorkflowUuid DAO");
			e.printStackTrace();
			return null;
		}
	}

	//delete workflow stage
	@Override
	public RequirementWorkFlowStages getRequirementWorkFlowStagesByUuid(String uuid, Long orgLocationId) {
		logger.debug("Running getRequirementWorkFlowStagesByUuid DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages stages where stages.uuid =:uuid and stages.orgLocationId.id =:orgLocationId";
			RequirementWorkFlowStages requirementWorkFlowStages = (RequirementWorkFlowStages)session.createQuery(query).setParameter("uuid", uuid).setLong("orgLocationId", orgLocationId).uniqueResult();
			return requirementWorkFlowStages;
		}catch(Exception e) {
			logger.error(e+" in getRequirementWorkFlowStagesByUuid DAO");
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public String deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid(String uuid) {
		logger.debug("Running deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			RequirementWorkFlowStages stage = session.get(RequirementWorkFlowStages.class, uuid);
			stage.setStatus(0);
			session.saveOrUpdate(stage);
			session.flush();
		} catch (HibernateException e) {
			logger.error(e+" in deleteRequirementWorkFlowStagesByOrgLocationIdAndUuid DAO");
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	public RequirementWorkFlowStages saveRequirementWorkFlowStages(RequirementWorkFlowStages requirementWorkFlowStages){
		logger.debug("Running saveRequirementWorkFlowStages DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if(requirementWorkFlowStages != null) {
				session.saveOrUpdate(requirementWorkFlowStages);
			}
		} catch (Exception exception) {
			logger.error("Exception caught while saveRequirementWorkFlowStages information " + exception.getMessage());
			exception.printStackTrace();
		}
		return requirementWorkFlowStages;
	}

	@Override
	public List<RequirementWorkFlowStages> getRequirementWorkFlowStagesListByOrgLocationId(Long orgLocationId) {
		logger.debug("Running getRequirementWorkFlowStagesListByOrgLocationId DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.orgLocationId.id =:orgLocationId and requirementWorkFlowStages.status = 1 ";
			List<RequirementWorkFlowStages> requirementWorkFlowStages = session.createQuery(query).setParameter("orgLocationId", orgLocationId).list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in getRequirementWorkFlowStagesListByOrgLocationId DAO");
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void deleteRequirementWorkFlowStagesByuuid(Long id) {
		logger.debug("Running deleteRequirementWorkFlowStagesByuuid DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Object workflowStageObject = session.get(RequirementWorkFlowStages.class, id);
			RequirementWorkFlowStages stage = (RequirementWorkFlowStages) workflowStageObject;
			session.delete(stage);
		} catch (Exception e) {
			logger.error(e+" in deleteRequirementWorkFlowStagesByuuid DAO");
			e.printStackTrace();
		}
	}

	@Override
	public List<RequirementQuestionnaireTemplate> getRequirementQuestionnaireTemplates(Long id) {
		logger.debug("Running getRequirementQuestionnaireTemplates DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementQuestionnaireTemplate requirementQuestionnaireTemplate where requirementQuestionnaireTemplate.orgLocationId.id =:id and requirementQuestionnaireTemplate.status = 1 ";
			List<RequirementQuestionnaireTemplate> requirementQuestionnaireTemplate = session.createQuery(query).setParameter("id", id).list();
			return requirementQuestionnaireTemplate;
		} catch (Exception e) {
			logger.error(e+" in getRequirementQuestionnaireTemplates DAO");
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public void saveRequirementWorkFlowTask(RequirementWorkFlowTasks requirementWorkFlowTasks) {
		logger.debug("Running saveRequirementWorkFlowTask DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(requirementWorkFlowTasks);
		} catch (Exception e) {
			logger.error(e+" in saveRequirementWorkFlowTask DAO");
			e.printStackTrace();
		} 
	}

	@Override
	public ResponseList<RequirementWorkFlowTasksDTO> getRequirementWorkFlowTaskList(
			RequirementWorkFlowTasksDTO requirementWorkFlowTasksDTO, OrgLocations orgLocations, User user){
		logger.debug("Running getRequirementWorkFlowTaskList DAO");
		try{
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> countlist = null;
			Integer filterCount = 0;
			StringBuilder queryEndBuilder = new StringBuilder("");
			StringBuilder stageQuery = new StringBuilder("");
			RequirementWorkFlowTasksDTO workFlowTasksDTO = null;
			List<RequirementWorkFlowTasksDTO> requirementWorkFlowTasksDTOList = new ArrayList<>();
			ResponseList<RequirementWorkFlowTasksDTO> responseList = new ResponseList<>();
			if(requirementWorkFlowTasksDTO != null){
				queryEndBuilder.append(" order by requirementWorkFlowTasks.updatedDate "+requirementWorkFlowTasksDTO.getSortOrder());
			}
			/* if(requirementWorkFlowTasksDTO.getStageId() != null) {
stageQuery.append(" and requirementWorkFlowTasks.requirementWorkFlowStagesId.id = :StageId ");
}
			 */ String query = "from RequirementWorkFlowTasks requirementWorkFlowTasks where requirementWorkFlowTasks.orgLocationId.id = :orgLocationId and requirementWorkFlowTasks.status= 1 and requirementWorkFlowTasks.requirementWorkFlowStagesId.id like '"+requirementWorkFlowTasksDTO.getStageId()+"'";
			 String countQuery = "select count(*) from RequirementWorkFlowTasks requirementWorkFlowTasks where requirementWorkFlowTasks.orgLocationId.id =:orgLocationId and requirementWorkFlowTasks.status= 1 and requirementWorkFlowTasks.requirementWorkFlowStagesId.id like '"+requirementWorkFlowTasksDTO.getStageId()+"'";
			 Query query1 = session.createQuery(query + queryEndBuilder + stageQuery).setLong("orgLocationId", orgLocations.getId());
			 Query query2 = session.createQuery(countQuery + queryEndBuilder + stageQuery).setLong("orgLocationId", orgLocations.getId());
			 /* if(requirementWorkFlowTasksDTO.getStageId() != null) {
query1.setLong("StageId", requirementWorkFlowTasksDTO.getStageId());
query2.setLong("StageId", requirementWorkFlowTasksDTO.getStageId());
}
			  */ countlist = query2.list();
			  int totalCount = 0;
			  if (requirementWorkFlowTasksDTO != null) {
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
			  List<RequirementWorkFlowTasks> requirementWorkFlow = query1.list();
			  if(requirementWorkFlow != null){
				  for(RequirementWorkFlowTasks workFlow : requirementWorkFlow){
					  StringBuilder userName = new StringBuilder("");
					  userName.append(workFlow.getAssignTo().getFirstName()+" "+workFlow.getAssignTo().getLastName());
					  workFlowTasksDTO = new RequirementWorkFlowTasksDTO();
					  workFlowTasksDTO.setWorkFlowTaskName(workFlow.getWorkFlowTaskName());
					  workFlowTasksDTO.setAssignToUserId(userName.toString());
					  workFlowTasksDTO.setPriority(workFlow.getPriority());
					  workFlowTasksDTO.setWorkFlowTaskDescription(workFlow.getDescription());
					  requirementWorkFlowTasksDTOList.add(workFlowTasksDTO);
				  }
			  }
			  responseList.setData(requirementWorkFlowTasksDTOList);
			  return responseList;
		}catch(Exception e){
			logger.error(e+" in getRequirementWorkFlowTaskList DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean deleteApplicationWorkFlowByuuid(Long workflowId) {
		logger.debug("Running deleteApplicationWorkFlowByuuid DAO");
		Boolean istrue = false;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Object workflowObject = session.get(RequirementWorkFlow.class, workflowId);
			RequirementWorkFlow workFlow = (RequirementWorkFlow) workflowObject;
			session.delete(workFlow);
			istrue = true;
			return istrue;
		} catch (Exception e) {
			logger.error(e+" in deleteApplicationWorkFlowByuuid DAO");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<RequirementWorkFlowTasks> getworkflowTasksBystagesId(Long workFlowStagesId) {
		logger.debug("Running getworkflowTasksBystagesId DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowTasks requirementWorkFlowTasks where requirementWorkFlowTasks.requirementWorkFlowStagesId.id =:workFlowStagesId ";
			List<RequirementWorkFlowTasks> requirementWorkFlowTasks = session.createQuery(query).setParameter("workFlowStagesId", workFlowStagesId).list();
			return requirementWorkFlowTasks;
		} catch (Exception e) {
			logger.error(e+" in getworkflowTasksBystagesId DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RequirementWorkFlowStages> getNextRequirementWorkflowStagesListByUuid(
			RequirementWorkFlowDTO requirementWorkFlowDTO, Long id) {
		logger.debug("Running getNextRequirementWorkflowStagesListByUuid DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			/*String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:uuid and requirementWorkFlowStages.orgLocationId.id =:orgLocationId "
+ " and requirementWorkFlowStages.uuid <> '"+requirementWorkFlowDTO.getCurrentStageUuid()+"' ORDER BY requirementWorkFlowStages.seqOrder ASC";
			 */
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:uuid and requirementWorkFlowStages.orgLocationId.id =:orgLocationId "
					+ " and requirementWorkFlowStages.status = 1 ORDER BY requirementWorkFlowStages.seqOrder ASC";

			List<RequirementWorkFlowStages> requirementWorkFlowStages = session.createQuery(query).setParameter("uuid", requirementWorkFlowDTO.getUuid()).setLong("orgLocationId", id).list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in getNextRequirementWorkflowStagesListByUuid DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RequirementWorkFlowStages> getInactiveRequirementWorkflowStagesListByUuidAndOrgLocationId(String uuid, Long id) {
		logger.debug("Running getInactiveRequirementWorkflowStagesListByUuidAndOrgLocationId DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.uuid =:uuid "
					+ "and (requirementWorkFlowStages.orgLocationId.id =:orgLocationId  or requirementWorkFlowStages.orgLocationId.id is NULL) "
					+ " and requirementWorkFlowStages.status = 0 and requirementWorkFlowStages.configureType='"+Constants.INACTIVE+"'";
			List<RequirementWorkFlowStages> requirementWorkFlowStages = session.createQuery(query).setParameter("uuid", uuid).setLong("orgLocationId", id).list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in getInactiveRequirementWorkflowStagesListByUuidAndOrgLocationId DAO");
			e.printStackTrace();
			return null;
		}
	} 

	@Override
	public void deleteInactiveRequirementWorkflowStagesByStageId(List<RequirementWorkFlowStages> requirementWorkFlowStage) {
		logger.debug("Running deleteInactiveRequirementWorkflowStagesByStageId DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			RequirementWorkFlowStages workFlowStage = null;
			Object workflowObject = null;
			for(int i = 0; i < requirementWorkFlowStage.size(); i++) {
				workflowObject = session.get(RequirementWorkFlowStages.class, requirementWorkFlowStage.get(i).getId());
				workFlowStage = (RequirementWorkFlowStages) workflowObject;
				session.delete(workFlowStage);
			}
		}catch(Exception e) {
			logger.error(e+" in deleteInactiveRequirementWorkflowStagesByStageId DAO");
			e.printStackTrace();
		}
	}

	@Override
	public List<RequirementWorkFlowStages> getRequirementworkflowStageByQuestionnaireId(Long id, Long orgLocationId) {
		logger.debug("Running getRequirementworkflowStageByQuestionnaireId DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementQuestionnaireTemplateId.id = :id and "
					+ " requirementWorkFlowStages.orgLocationId.id = :orgLocationId";
			List<RequirementWorkFlowStages> requirementWorkFlowStages = session.createQuery(query).setParameter("id", id).setParameter("orgLocationId", orgLocationId).list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e+" in getRequirementworkflowStageByQuestionnaireId DAO");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<RequirementWorkFlow> validateWorkflowName(Long id, String name, String uuid){
		logger.debug("Running validateWorkflowName DAO");
		StringBuilder queryBuilder1 = new StringBuilder("");
		List<RequirementWorkFlow> workflow = new ArrayList<RequirementWorkFlow>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if(uuid != null && !StringUtils.isBlank(uuid)) {
				queryBuilder1.append(" and requirementWorkFlow.uuid <> :uuid");
			}
			String queryString = "from RequirementWorkFlow requirementWorkFlow where requirementWorkFlow.orgLocationId.id = :id and requirementWorkFlow.workFlowName = :name";
			Query query = session.createQuery(queryString + queryBuilder1);
			if(uuid != null && !StringUtils.isBlank(uuid)) {
				query.setParameter("uuid", uuid);
			}
			workflow = query.setParameter("id", id).setParameter("name", name).list();
			return workflow;
		} catch (Exception e) {
			logger.error(e +" in validateWorkflowName DAO");
			e.printStackTrace();
			return workflow;
		}
		
	}

	@Override
	public List<RequirementWorkFlowStages> validateWorkflowStageName(Long id, String name, Long workflowId,
			String stageUuid) throws Exception {
		logger.debug("Running validateWorkflowName DAO");
		List<RequirementWorkFlowStages> workflowstages = new ArrayList<RequirementWorkFlowStages>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "from RequirementWorkFlowStages stage where stage.orgLocationId.id = :id and stage.stageName = :name and "
					+ " stage.requirementWorkFlowId.id = :workflowId and stage.uuid <> :stageUuid";
			workflowstages = session.createQuery(queryString).setParameter("id", id).setParameter("name", name).setParameter("workflowId", workflowId).setParameter("stageUuid", stageUuid).list();
			return workflowstages;
		} catch (Exception e) {
			logger.error(e +" in validateWorkflowName DAO");
			e.printStackTrace();
			return workflowstages;
		}
	}

	@Override
	public RequirementWorkFlow getRequirementWorkFlowByOrgLocId(Long orgLocationId) throws Exception {
		logger.debug("Running getRequirementWorkFlowByOrgLocId DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlow requirementWorkFlow where requirementWorkFlow.orgLocationId.id =:id order by id asc";
			RequirementWorkFlow requirementWorkFlow = (RequirementWorkFlow)session.createQuery(query).setParameter("id", orgLocationId).setMaxResults(1).uniqueResult();
			return requirementWorkFlow;
		} catch (Exception e) {
			logger.error(e+" in getRequirementWorkFlowByOrgLocId DAO");
			e.printStackTrace();
			return null;
	
	}
	}

	@Override
	public List<RequirementWorkFlowStages> getRequirementWorkflowStagesByWorkflowIdAndOrglocations(Long workflowId,
			OrgLocations orgLocations) {
		logger.debug("Running getRequirementWorkflowStagesByWorkflowIdAndOrglocations DAO");
		List<RequirementWorkFlowStages> workflowStagesList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages worflowstages where worflowstages.requirementWorkFlowId.id =:workflowId and worflowstages.orgLocationId.id=:orgLocationId order by worflowstages.seqOrder";
			workflowStagesList = session.createQuery(query).setLong("workflowId", workflowId).setLong("orgLocationId", orgLocations.getId()).list();
		} catch (Exception e) {
			logger.error(e + " in getRequirementWorkflowStagesByWorkflowIdAndOrglocations DAO");
			e.printStackTrace();
		}
		return workflowStagesList;
	}

	@Override
	public List<Requirement> getRequirementListByWorkflowIdAndOrgLocationId(String uuid, Long id) throws Exception {
		logger.debug("Running getRequirementListByWorkflowIdAndOrgLocationId DAO");
		List<Requirement> requirementList = new ArrayList<Requirement>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Requirement requirement where requirement.workflowId.uuid =:uuid and requirement.orgLocationId.id=:id";
			requirementList = session.createQuery(query).setString("uuid", uuid).setLong("id", id).list();
			return requirementList;
		} catch (Exception e) {
			logger.error(e + " in getRequirementListByWorkflowIdAndOrgLocationId DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RequirementWorkFlowStages> getRequirementWorkflowStagesListByUuidAndOrgLocationId(String uuid, Long id, String stageUuid){
		logger.debug("Running getRequirementWorkflowStagesListByUuidAndOrgLocationId DAO");
		List<RequirementWorkFlowStages> workflowstages = new ArrayList<RequirementWorkFlowStages>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "from RequirementWorkFlowStages stage where stage.orgLocationId.id = :id and stage.requirementWorkFlowId.uuid = :uuid "
					+ " and stage.uuid <> :stageUuid";
			workflowstages = session.createQuery(queryString).setString("uuid", uuid).setLong("id", id).setString("stageUuid", stageUuid).list();
			return workflowstages;
		} catch (Exception e) {
			logger.error(e +" in getRequirementWorkflowStagesListByUuidAndOrgLocationId DAO");
			e.printStackTrace();
			return workflowstages;
		}
	}

	@Override
	public LinkedHashMap<String, String> getColumnsMapbyWorkflowStage(String shortCode, Long workflowId, Long orgLocationId) {
		logger.debug("Running getColumnsMapbyWorkflowStage DAO");
		LinkedHashMap<String, String> stageColumnMap = new LinkedHashMap<String, String> ();
		String[] chosenColumnsArr;
		// below list column headers and entity column mappings for all the user chosen column at stage UI. 
		//Total 14 = 13(chosen list) + 1(Default status column) columns are providing for the user to select at add stage UI.
		LinkedHashMap<String, String> allColumnsMap = new LinkedHashMap<String, String> (){
			{
				put("Applicant Name", "candidateName"); 	put("Applicant Mail Id", "candidateEmail"); put("Phone Number","phoneNumber");
				put("Alternate Number", "alternateNumber"); 	put("location", "location"); put("DOB","dateOfBirthStr");
				put("Experience", "workExperience"); 	put("Current CTC", "currentCtc"); put("Rate [Expected CTC]","expectedCTC");
				put("Score", "candidateScore"); 	put("Notice Period", "noticePeriod"); put("Applied on","createdDateStr");
				put("Source", "createdUser"); 
				
			}
		};
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "select chosenColumns from RequirementWorkFlowStages stage where stage.orgLocationId.id = :orgLocationId and stage.stageShortCode = :shortCode and stage.requirementWorkFlowId.id =:workflowId ";
			String commaSeparatedColumns = (String) session.createQuery(queryString).setLong("workflowId", workflowId).setLong("orgLocationId", orgLocationId).setString("shortCode", shortCode).uniqueResult();
			chosenColumnsArr = commaSeparatedColumns.split(",");
		   // preparing column headers and entity colmn mappings as map and returns as response.
			if(chosenColumnsArr != null && chosenColumnsArr.length>0) {
				for(String column: chosenColumnsArr) {
					stageColumnMap.put(column, allColumnsMap.get(column));
				}
				stageColumnMap.put("Status", "submissionStatus"); // Status is adding for every stage as Default column for every dynamic stage.
			}
			return stageColumnMap;
		} catch (Exception e) {
			logger.error(e +" in getColumnsMapbyWorkflowStage DAO");
			e.printStackTrace();
			return stageColumnMap;
		}
	}

	@Override
	public RequirementWorkFlowStages getRequirementWorkFlowStagesDetails(String shortCode, Long workflowId,
			Long orgLocationId) {
		// TODO Auto-generated method stub
		RequirementWorkFlowStages workflowSatge = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "from RequirementWorkFlowStages stage where stage.orgLocationId.id = :orgLocationId and stage.stageShortCode = :shortCode and stage.requirementWorkFlowId.id =:workflowId ";
			workflowSatge = (RequirementWorkFlowStages) session.createQuery(queryString).setLong("workflowId", workflowId).setLong("orgLocationId", orgLocationId).setString("shortCode", shortCode).uniqueResult();
		} catch (Exception e) {
			logger.error(e +" in getColumnsMapbyWorkflowStage DAO");
			e.printStackTrace();
			
		}
		return workflowSatge;
	}

	@Override
	public RequirementSubmission validateWorkflowEditable(Long requirementId, Long orgLocationId) {
		RequirementSubmission RrequirementSubmission = null;
				try {
					Session session = this.sessionFactory.getCurrentSession();
					String queryString = "from RequirementSubmission req where req.requirement.id=:requirementId and  req.orgLocations.id = :orgLocationId";
					RrequirementSubmission =  (RequirementSubmission) session.createQuery(queryString).setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).setMaxResults(1).uniqueResult();
				} catch (Exception e) {
					logger.error(e +" in validateWorkflowEditabele DAO");
					e.printStackTrace();
					
				}
				return RrequirementSubmission;
	}
	@Override
	public RequirementQuestionnaireFeedbackTemplate saveQuestionnaireFeedbackTemplate(
			RequirementQuestionnaireFeedbackTemplate qemplate) throws Exception {
		logger.debug("Running saveQuestionnaireFeedbackTemplate DAO");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (qemplate != null) {
				session.saveOrUpdate(qemplate);
			}
		} catch (Exception e) {
			logger.error(e +" in saveQuestionnaireFeedbackTemplate DAO");
		}
		return qemplate;
	}

	@Override
	public RequirementQuestionnaireFeedbackTemplateDTO getQuestionnaireTemplateFeedbackList(String uuid)
			throws Exception {
		RequirementQuestionnaireFeedbackTemplateDTO qemplateDTO = null;
		List<RequirementQuestionnaireFeedbackTemplateDTO> qTemplateDTOList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select JSON_STRING as jsonString, JSON_STRING_VALUES as jsonStringValues, uuid as uuid from REQUIREMENT_QUESTIONNAIRE_FEEDBACK_TEMPLATE qtemplate where qtemplate.uuid =:uuid";
			Query obj = session.createSQLQuery(query)
					.addScalar("jsonString", StandardBasicTypes.STRING)
					.addScalar("jsonStringValues", StandardBasicTypes.STRING)
					.addScalar("uuid", StandardBasicTypes.STRING);
			obj.setResultTransformer(Transformers.aliasToBean(RequirementQuestionnaireFeedbackTemplateDTO.class));
			qTemplateDTOList = obj.setString("uuid", uuid).list();
			qemplateDTO = qTemplateDTOList.get(0);
			return qemplateDTO;
		} catch (Exception e) {
			logger.error(e +" in getColumnsMapbyWorkflowStage DAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RequirementSubmission> validateWorkflowUpdate(Long workflowId, Long orgLocationId) {
		List<RequirementSubmission> subList = null;
	try {
		Session session = this.sessionFactory.getCurrentSession();
		String queryString = "select distinct reqSub from RequirementSubmission reqSub where reqSub.requirement.id in (select req.id from  Requirement req where req.workflowId.id = :workflowId and  req.orgLocationId.id = :orgLocationId)";
		System.out.println(queryString);
		subList =  session.createQuery(queryString).setLong("workflowId", workflowId).setLong("orgLocationId", orgLocationId).setMaxResults(1).list();
	} catch (Exception e) {
		logger.error(e +" in validateWorkflowEditabele DAO");
		e.printStackTrace();
		
	}
	return subList;
	}
	public RequirementQuestionnaireFeedbackTemplate getQuestionnaireTemplateFeedbackByUuid(String uuid)
			throws Exception {
		RequirementQuestionnaireFeedbackTemplate requirementQuestionnaireFeedbackTemplate = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "from RequirementQuestionnaireFeedbackTemplate temp where temp.uuid = :uuid";
			requirementQuestionnaireFeedbackTemplate = (RequirementQuestionnaireFeedbackTemplate) session.createQuery(queryString).setString("uuid", uuid).uniqueResult();
		} catch (Exception e) {
			logger.error(e +" in getQuestionnaireTemplateFeedbackByUuid DAO");
			e.printStackTrace();
			
		}
		return requirementQuestionnaireFeedbackTemplate;
	}

	@Override
	public List<RequirementWorkFlowStages> getRequirementWorkflowStagesBySeqOrderWorkflowIdAndStageName(Long workflowId,
			String stageShortCode, OrgLocations orgLocations) {
		logger.debug("Running getRequirementWorkflowStagesByWorkflowIdAndOrglocations DAO");
		List<RequirementWorkFlowStages> workflowStagesList = null;
		
		try {
			if(!stageShortCode.equalsIgnoreCase("REQUEST_SENT")) {
			  Session session = this.sessionFactory.getCurrentSession();
			 String query = "from RequirementWorkFlowStages worflowstages where worflowstages.requirementWorkFlowId.id =:workflowId and worflowstages.seqOrder >= (select ws.seqOrder from RequirementWorkFlowStages ws where ws.stageShortCode =:stageShortCode and ws.requirementWorkFlowId.id =:workflowId and ws.orgLocationId.id=:orgLocationId) and worflowstages.orgLocationId.id=:orgLocationId order by worflowstages.seqOrder";
			 workflowStagesList = session.createQuery(query).setLong("workflowId", workflowId).setString("stageShortCode", stageShortCode).setLong("orgLocationId", orgLocations.getId()).list();
			}else {
				//we are adding manually two stage for REQUEST_SENT because we don't have default stage workflow stages.
				workflowStagesList = new ArrayList<RequirementWorkFlowStages>();
				RequirementWorkFlowStages requestSent = new RequirementWorkFlowStages();
				requestSent.setStageShortCode("REQUEST_SENT");
				requestSent.setStageName("Request Sent");
				
				RequirementWorkFlowStages reject = new RequirementWorkFlowStages();
				reject.setStageShortCode("REJECTED");
				reject.setStageName("Reject");
				
				workflowStagesList.add(requestSent);
				workflowStagesList.add(reject);
			}
		} catch (Exception e) {
			logger.error(e + " in getRequirementWorkflowStagesByWorkflowIdAndOrglocations DAO");
			e.printStackTrace();
		}
		return workflowStagesList;
	}
	
	@Override
	public List<RequirementWorkFlowStages> getDefaultWorkFlowStages(){
		List<RequirementWorkFlowStages> requirementWorkFlowStages = new ArrayList();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.requirementWorkFlowId.id = 1 order by requirementWorkFlowStages.seqOrder ";
			Query obj = session.createQuery(query);
			requirementWorkFlowStages = obj.list();
			return requirementWorkFlowStages;
		} catch (Exception e) {
			logger.error(e +" in getDefaultWorkFlowStages DAO");
			e.printStackTrace();
			return null;
		}
	}
}
	