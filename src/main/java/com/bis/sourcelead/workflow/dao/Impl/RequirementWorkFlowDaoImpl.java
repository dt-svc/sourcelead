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
import com.bis.sourcelead.entity.ImmigrationTasks;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.workflow.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireFeedbackTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTasksDTO;
import com.bis.sourcelead.workflow.dto.RequirementQuestionnaireTemplateDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowTasksDTO;
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
			StringBuilder queryMidBuilder = new StringBuilder("");
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
				queryMidBuilder.append(" and ((rq.name like '%" + searchValue + "%' )");
				queryMidBuilder.append(" or (rq.description like '%" + searchValue + "%' )");
				queryMidBuilder.append(" or (rq.createdBy.lastName like '%" + searchValue + "%' ))");
				queryMidBuilder.append(" or (rq.createdBy.firstName like '%" + searchValue + "%' ))");
				//query1.append(" or (tempView.status like '%" + searchValue + "%' )");
			}
			String query = " from RequirementQuestionnaireTemplate rq where rq.orgLocationId.id=:orgLocationId";
			String countQuery = "select count(*) from RequirementQuestionnaireTemplate rq where rq.orgLocationId.id=:orgLocationId";
			Query query1 = session.createQuery(query + queryMidBuilder + queryEndBuilder).setLong("orgLocationId", orgLocationId);
			Query query2 = session.createQuery(countQuery + queryMidBuilder + queryEndBuilder).setLong("orgLocationId", orgLocationId);
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
	public RequirementWorkFlow getRequirementDefaultWorkflow() {
		logger.debug("Running getRequirementDefaultWorkflow DAO");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementWorkFlow requirementWorkFlow where requirementWorkFlow.workFlowName=:defaultWorkflowName and requirementWorkFlow.configureType=:default and requirementWorkFlow.orgLocationId.id is NULL ";
			RequirementWorkFlow requirementWorkFlow = (RequirementWorkFlow)session.createQuery(query).setString("defaultWorkflowName",Constants.DEFAULT_JOB_WORKFLOW).setString("default",Constants.DEFAULT).uniqueResult();
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
					  workFlowTasksDTO.setUuid(workFlow.getUuid());
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
					+ " and requirementWorkFlowStages.status = 0 ";//and requirementWorkFlowStages.configureType='"+Constants.INACTIVE+"'
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
				put("Source", "createdUser"); put("Submitted Questionnaire", "submittedQuestionrUuid");  put("Education", "education"); 
				
			}
		};
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "select chosenColumns from RequirementWorkFlowStages stage where stage.orgLocationId.id = :orgLocationId and stage.stageShortCode = :shortCode and stage.requirementWorkFlowId.id =:workflowId ";
			String commaSeparatedColumns = (String) session.createQuery(queryString).setLong("workflowId", workflowId).setLong("orgLocationId", orgLocationId).setString("shortCode", shortCode).uniqueResult();
			chosenColumnsArr = commaSeparatedColumns.split(",");
		   // preparing column headers and entity column mappings as map and returns as response.
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
					Session session = this.sessionFactory.getCurrentSession();       // below query used to enable or disable work flow name in requirement edit section.                                                  
					String queryString = "from RequirementSubmission req where req.requirement.id=:requirementId and  req.orgLocations.id = :orgLocationId ";
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
	public RequirementQuestionnaireFeedbackTemplateDTO getQuestionnaireTemplateFeedbackList(String uuid, String workflowStageUuid, String submissionId)
			throws Exception {
		RequirementQuestionnaireFeedbackTemplateDTO quesFeedtemplateDTO = null;
		String query = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if(workflowStageUuid == null && submissionId == null) {
				query ="SELECT JSON_STRING AS jsonString, JSON_STRING_VALUES AS jsonStringValues, UUID AS uuid, SUBMITTED_POINTS AS submittedPoints, TOTAL_POINTS AS totalPoints, "
						+" TIME_LIMIT_MINUTES AS timeLimitMinutes, TIME_LIMIT_SECONDS AS timeLimitSeconds, "
						+" TIME_MINUTES_REMAINED AS timeMinutesRemained, TIME_SECONDS_REMAINED AS timeSecondsRemained  "
						+ " from REQUIREMENT_QUESTIONNAIRE_FEEDBACK_TEMPLATE qtemplate WHERE qtemplate.uuid=:uuid";
			}else {
				query = "SELECT JSON_STRING AS jsonString, JSON_STRING_VALUES AS jsonStringValues, UUID AS uuid, SUBMITTED_POINTS AS submittedPoints, TOTAL_POINTS AS totalPoints, "
						+" TIME_LIMIT_MINUTES AS timeLimitMinutes, TIME_LIMIT_SECONDS AS timeLimitSeconds, "
						+" TIME_MINUTES_REMAINED AS timeMinutesRemained, TIME_SECONDS_REMAINED AS timeSecondsRemained  "
						+ " from REQUIREMENT_QUESTIONNAIRE_FEEDBACK_TEMPLATE qtemplate WHERE qtemplate.REQUIREMENT_QUESTIONNAIRE_TEMPLATE_UUID=:uuid"
						
						+ " AND qtemplate.REQUIREMENT_WORKFLOW_STAGES_UUID =:workflowStageUuid AND  qtemplate.REQUIREMENT_SUBMISSION_ID =:submissionId ";
			}
				Query obj = session.createSQLQuery(query)
					.addScalar("jsonString", StandardBasicTypes.STRING)
					.addScalar("jsonStringValues", StandardBasicTypes.STRING)
					.addScalar("uuid", StandardBasicTypes.STRING)
					.addScalar("submittedPoints", StandardBasicTypes.INTEGER)
					.addScalar("totalPoints", StandardBasicTypes.INTEGER)
					.addScalar("timeLimitMinutes", StandardBasicTypes.INTEGER)
					.addScalar("timeLimitSeconds", StandardBasicTypes.INTEGER)
					.addScalar("timeMinutesRemained", StandardBasicTypes.INTEGER)
					.addScalar("timeSecondsRemained", StandardBasicTypes.INTEGER);
				
			obj.setResultTransformer(Transformers.aliasToBean(RequirementQuestionnaireFeedbackTemplateDTO.class));
			if(workflowStageUuid == null && submissionId == null) {
				obj.setString("uuid", uuid);
			}else {
				obj.setString("uuid", uuid).setString("workflowStageUuid", workflowStageUuid).setString("submissionId", submissionId);
			}
			quesFeedtemplateDTO = (RequirementQuestionnaireFeedbackTemplateDTO) obj.uniqueResult();
			
		} catch (Exception e) {
			logger.error(e +" in getColumnsMapbyWorkflowStage DAO");
			e.printStackTrace();
			return null;
		}
		return quesFeedtemplateDTO;
	}



@Override
public void copyWorkflowStagesAndTasks(Tasks tasks) {
	Session session = this.sessionFactory.getCurrentSession();
	try {
		if (tasks != null) {
				session.save(tasks);
		}
	} catch (Exception exception) {
		logger.error("Exception caught while coping workflow information " + exception.getMessage());
		exception.printStackTrace();
	}
}

@Override
public List<RequirementWorkFlowTasks> getRequirementWorkFlowTaskListById(Long stageId) {
	List<RequirementWorkFlowTasks> requirementWorkFlowTasks = new ArrayList<RequirementWorkFlowTasks>();
	try {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementWorkFlowTasks requirementWorkFlowTasks where requirementWorkFlowTasks.requirementWorkFlowStagesId.id =:stageId";
		requirementWorkFlowTasks = session.createQuery(query).setLong("stageId", stageId).list();
		return requirementWorkFlowTasks;
	} catch (Exception e) {
		logger.error(e + " in getRequirementListByWorkflowIdAndOrgLocationId DAO");
		e.printStackTrace();
		return null;
	}
}


	@Override
	public List<RequirementSubmission> validateWorkflowUpdate(Long workflowId, String workflowUuid, Long orgLocationId) {
		List<RequirementSubmission> subList = null;
	try {
		Session session = this.sessionFactory.getCurrentSession();
		String queryString = null;
		if(workflowId != null) {                                                       // req.closedDate is null means requirement is not yet closed. if closed allow to edit
		  queryString = "select distinct reqSub from RequirementSubmission reqSub where reqSub.requirement.id in (select req.id from  Requirement req where req.workflowId.id = :workflowId and  req.orgLocationId.id = :orgLocationId and req.closedDate is null )";
			subList =  session.createQuery(queryString).setLong("workflowId", workflowId).setLong("orgLocationId", orgLocationId).setMaxResults(1).list();
		}else {
			queryString = "select distinct reqSub from RequirementSubmission reqSub where reqSub.requirement.id in (select req.id from  Requirement req where req.workflowId.uuid = :workflowUuid and  req.orgLocationId.id = :orgLocationId and req.closedDate is null )";
			subList =  session.createQuery(queryString).setString("workflowUuid", workflowUuid).setLong("orgLocationId", orgLocationId).setMaxResults(1).list();
		}
		System.out.println(queryString);
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
			
			  String query = "from RequirementWorkFlowStages workflowstages where workflowstages.requirementWorkFlowId.id =:workflowId "
						 		+ "and workflowstages.seqOrder >= (select ws.seqOrder from RequirementWorkFlowStages ws where ws.stageShortCode =:stageShortCode and ws.requirementWorkFlowId.id =:workflowId and ws.orgLocationId.id=:orgLocationId) "
							    +" and workflowstages.orgLocationId.id=:orgLocationId ";
			   // Below condition is added not show show interview and offer stage in drop down list added by SRINIVAS SABBU ON 25-SEP-2019.
			    if (stageShortCode.equalsIgnoreCase("INTERVIEW")) {
					query = query + " and workflowstages.stageShortCode not in ('OFFERMADE')";
				} else if (stageShortCode.equalsIgnoreCase("OFFERMADE")) {
					query = query + " and workflowstages.stageShortCode not in ('INTERVIEW') ";
				} else {
					query = query + " and workflowstages.stageShortCode not in ('INTERVIEW', 'OFFERMADE')";
				}
			     query =query	+ " order by workflowstages.seqOrder";
			     if(stageShortCode.equalsIgnoreCase("SELECTED_ON_INTERVIEW") || stageShortCode.equalsIgnoreCase("CLIENT_INTERVIEW")|| stageShortCode.equalsIgnoreCase("INTERVIEW_ACCEPT")) {
			    	 workflowStagesList = session.createQuery(query).setLong("workflowId", workflowId).setString("stageShortCode", "INTERVIEW").setLong("orgLocationId", orgLocations.getId()).list();
			     }else if(stageShortCode.equalsIgnoreCase("OFFERACCEPTED") || stageShortCode.equalsIgnoreCase("CLIENT_OFFERACCEPTED")) {
			    	 workflowStagesList = session.createQuery(query).setLong("workflowId", workflowId).setString("stageShortCode", "OFFERMADE").setLong("orgLocationId", orgLocations.getId()).list(); 
			     }
			     else {
			         workflowStagesList = session.createQuery(query).setLong("workflowId", workflowId).setString("stageShortCode", stageShortCode).setLong("orgLocationId", orgLocations.getId()).list();
			     }
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

	@Override
	public RequirementQuestionnaireTasksDTO getQuestionnaireIdAssignedToStage(Long workflowId, String stageName, Long orgLocationId) {
		RequirementQuestionnaireTasksDTO questionnaireTasksDTO= null;
		try {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT  question.id AS questionnaireId, question.NAME AS questionnaireName, "+
				       "          question.UUID AS questionnaireUuid, stages.ID AS stageId, stages.STAGE_NAME AS stageName, stages.uuid AS stageUuid, COUNT(tasks.id) AS tasksCount " + 
		
						"            FROM  REQUIREMENT_WORKFLOW_STAGES stages, " + 
						"                  REQUIREMENT_QUESTIONNAIRE_TEMPLATE question, "+
						"                  REQUIREMENT_WORKFLOW_TASKS tasks " + 
						
						"		   WHERE  stages.REQUIREMENT_QUESTIONNAIRE_TEMPLATE_ID=question.ID" + 
						"                 AND  tasks.REQUIREMENT_WORKFLOW_STAGES_ID=stages.ID "+
						"                 AND  stages.STAGE_SHORT_CODE =:stageName" + 
						"                 AND  stages.REQUIREMENT_WORKFLOW_ID =:workflowId" + 
						"                 AND  stages.ORG_LOCATION_ID =:orgLocationId " +
						
				        "                          GROUP BY stages.STAGE_SHORT_CODE";
		questionnaireTasksDTO = (RequirementQuestionnaireTasksDTO) session.createSQLQuery(query)
										.addScalar("questionnaireId", StandardBasicTypes.STRING)
										.addScalar("questionnaireName", StandardBasicTypes.STRING)
										.addScalar("questionnaireUuid", StandardBasicTypes.STRING)
										.addScalar("stageId", StandardBasicTypes.STRING)
										.addScalar("stageName", StandardBasicTypes.STRING)
										.addScalar("stageUuid", StandardBasicTypes.STRING)
										.addScalar("tasksCount", StandardBasicTypes.STRING)
										.setResultTransformer(Transformers.aliasToBean(RequirementQuestionnaireTasksDTO.class))
										.setString("stageName", stageName).setLong("workflowId", workflowId).setLong("orgLocationId", orgLocationId).uniqueResult();
		} catch (Exception e) {
			logger.error(e +" in getQuestionnaireIdAssignedToStage DAO");
			e.printStackTrace();
			return null;
		}
	  return questionnaireTasksDTO;
	}

	@Override
	public Candidate getCandidateByRequirementSubmissionId(Long submissionId) {
		Candidate candidate = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select submission.candidate from RequirementSubmission submission where submission.id=:submissionId";
			candidate =  (Candidate) session.createQuery(query).setLong("submissionId", submissionId).uniqueResult();
		} catch (Exception e) {
			logger.error(e +" in getQuestionnaireIdAssignedToStage DAO");
			e.printStackTrace();
			return null;
		}
		return candidate;
	}
	public Integer updateDefaultWorkflowInRequirementProcedure(){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "call UPDATE_DEFAULT_WORKFLOW_IN_REQUIREMENT_PROC()";
			Integer updatedRequirementCount = Integer.parseInt(session.createSQLQuery(query).uniqueResult().toString());
			return updatedRequirementCount;
		} catch (Exception e) {
			logger.error(e +" in updateDefaultWorkflowInRequirementProcedure DAO");
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public RequirementQuestionnaireFeedbackTemplate getSubmittedQuestionnaireUuidByWorkflowStage(String stageUuid, Long submissionId, Long orgLocationId) {
		RequirementQuestionnaireFeedbackTemplate feedbackTemp = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = " from RequirementQuestionnaireFeedbackTemplate feedbackTemp where  feedbackTemp.requirementWorkFlowStagesUuId=:stageUuid"
					    + " and feedbackTemp.requirementSubmissionId.id=:submissionId and feedbackTemp.orgLocationId.id=:orgLocationId";
			feedbackTemp = (RequirementQuestionnaireFeedbackTemplate) session.createQuery(query).setString("stageUuid", stageUuid).setLong("submissionId", submissionId).setLong("orgLocationId", orgLocationId).uniqueResult();
		} catch (Exception e) {
			logger.error(e +" in getSubmittedQuestionnaireUuidByWorkflowStage DAO");
			e.printStackTrace();
			return null;
		}
		 return feedbackTemp;
	}
	
	@Override
		public RequirementWorkFlowTasks getRequirementWorkFlowTasksByUuid(String uuid) {
			logger.debug("Running getRequirementWorkFlowTasksBycurrentStageUuid DAO");
			RequirementWorkFlowTasks requirementWorkFlowTasks = null;
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String query = "from RequirementWorkFlowTasks requirementWorkFlowTasks where requirementWorkFlowTasks.uuid =:uuid ";
				requirementWorkFlowTasks =  (RequirementWorkFlowTasks) session.createQuery(query).setParameter("uuid", uuid).uniqueResult();
				return  requirementWorkFlowTasks;
			} catch (Exception e) {
				logger.error(e+" in requirementWorkFlowTasksListByUuid DAO");
				e.printStackTrace();
				return null;
			}
		}
	
		@Override
		public String deleteRequirementWorkFlowtasks(Long id) {
			logger.debug("Running deleteRequirementWorkFlowtasks DAO");
			Session session = this.sessionFactory.getCurrentSession();
			Object workFlowTasks = null;
			RequirementWorkFlowTasks requirementWorkFlowtasks = null;
			try {
				workFlowTasks = session.get(RequirementWorkFlowTasks.class, id);
				requirementWorkFlowtasks = (RequirementWorkFlowTasks) workFlowTasks;
				session.delete(requirementWorkFlowtasks);
			} catch (HibernateException e) {
				logger.error(e+" in deleteRequirementWorkFlowtasks DAO");
				e.printStackTrace();
			}
			logger.debug("Deleted record id -->" + id);
			return "status";
			
		}

		@Override
		public String updateFeedbackQuestionnaireTimer(String questTemplUuid, Long stageId, Long submissionId,
				Integer minutes, Integer seconds) {
			logger.debug("Running deleteRequirementWorkFlowtasks DAO");
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String query = "UPDATE  REQUIREMENT_QUESTIONNAIRE_FEEDBACK_TEMPLATE SET TIME_MINUTES_REMAINED =:minutes , TIME_SECONDS_REMAINED=:seconds"
						                  + " WHERE REQUIREMENT_SUBMISSION_ID =:submissionId AND REQUIREMENT_WORKFLOW_STAGES_ID =:stageId  AND REQUIREMENT_QUESTIONNAIRE_TEMPLATE_UUID =:questTemplUuid" ;
				int count = session.createSQLQuery(query).setInteger("minutes", minutes).setInteger("seconds", seconds).setLong("submissionId", submissionId).setLong("stageId", stageId)
			                                               .setString("questTemplUuid",questTemplUuid).executeUpdate();
				if(count != 0 ) {
					return Constants.SUCCESS;
				}
				return Constants.FAILED;
			} catch (Exception e) {
				logger.error(e+" in requirementWorkFlowTasksListByUuid DAO");
				e.printStackTrace();
				return Constants.FAILED;
			}
		}

		@Override
		public String getWorkflowStageNameByShortCode(String shortCode)  {
			logger.debug("Running getWorkflowStageNameByShortCode DAO");
			try{
				RequirementWorkFlowStages stage = null;
				Session session = this.sessionFactory.getCurrentSession();
				String query = "from RequirementWorkFlowStages requirementWorkFlowStages where requirementWorkFlowStages.stageShortCode =:shortCode ";
				stage =  (RequirementWorkFlowStages) session.createQuery(query).setParameter("shortCode", shortCode).uniqueResult();
				return stage.getStageName();
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			
		}
		
		
	 }

