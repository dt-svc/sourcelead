package com.bis.sourcelead.immigration.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;


/**
 * @author Srinivas.Sabbu
 *
 */
@Repository
public class ImmigrationTemplateDaoImpl implements ImmigrationTemplateDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserRoleDao userRoleDao;

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

	private static final Logger logger = LoggerFactory.getLogger(ImmigrationTemplateDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long savePetitionTemplateDetails(ImmigrationTemplate immigrationTemplate, String templateId) {
		logger.debug("Saving Template Details-->");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if (immigrationTemplate != null) {
					session.saveOrUpdate(immigrationTemplate);
			}
			logger.debug("tempalte Details Added Successfully-->");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "immigrationTemplate"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		return immigrationTemplate.getId();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationTemplateDocs savePetitionTemplateDocDetails(ImmigrationTemplateDocs immigrationTemplateDocs) {
		logger.debug("Saving Temlate document Details-->");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationTemplateDocs != null) {
				session.saveOrUpdate(immigrationTemplateDocs);
			}
			
			logger.debug("Template documents added Successfully-->");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "immigrationTemplateDocs"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		return immigrationTemplateDocs;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void savePetitionTemplateTaskDetails(ImmigrationTemplateTasks immigrationTemplateTask) {
		logger.debug("Saving Temlate Task Details-->");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationTemplateTask != null) {
				session.saveOrUpdate(immigrationTemplateTask);
			}
			logger.debug("Template task details added Successfully-->");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "immigrationTemplateTask"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationTemplateForms savePetitionTemplateFormDetails(ImmigrationTemplateForms immigrationTemplateForms) {
		logger.debug("Saving Temlate Task Details-->");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationTemplateForms != null) {
				session.saveOrUpdate(immigrationTemplateForms);
			}
			logger.debug("Template form details added Successfully-->");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "immigrationTemplateForms"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		
		return immigrationTemplateForms;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deletePetitionTemplateDocDetails(String remainingDocsIds) {
		logger.debug("Delete Template Docs-->");
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from ImmigrationTemplateDocs docs where docs.id in (:templateIds)").setString("templateIds", remainingDocsIds);
		int count = query.executeUpdate();
		logger.debug("Number of records deleted-->"+count);
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deletePetitionTemplateFormDetails(String remainingFormIds) {
		logger.debug("Delete Template Docs-->");
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from ImmigrationTemplateForms itf where itf.id in (:templateIds)").setString("templateIds", remainingFormIds);
		int count = query.executeUpdate();
		logger.debug("Number of records deleted-->"+count);
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO, String searchValue) {
		logger.debug("fetching template details-->");
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationTemplateListDTO>  templateDtoList= null;
		try {
			  Query query= session.createQuery("from ImmigrationTemplateView");
			  templateDtoList = query.list();
			 
		} catch (Exception exception) {
			logger.error("Error Occured  while fetching template details-->");
			   exception.printStackTrace();
			}
		return templateDtoList;
	}

*/
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO, String searchValue,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		List<ImmigrationTemplateView> ImmigrationTemplateViewLst = null;
		ResponseList<ImmigrationTemplateListDTO> responseList = new ResponseList<>();

		StringBuilder query1 = new StringBuilder("");
		if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
			query1.append(" and (tempView.name like '%" + searchValue + "%' )");
			query1.append(" or (tempView.description like '%" + searchValue + "%' )");
			//query1.append(" or (tempView.status like '%" + searchValue + "%' )");
		}
		// usertList sorting order
		String sortingQry = "";
		if (StringUtils.isNotBlank(templateListDTO.getSortColumn())) {
			sortingQry = " order by tempView." + templateListDTO.getSortColumn() + " " + templateListDTO.getSortOrder();
		} else {
			sortingQry = " order by tempView.name ";
		}
		
		String listQuery = "from ImmigrationTemplateView tempView  where tempView.orgLocationId=:orgLocationId ";
		
		ImmigrationTemplateViewLst = session.createQuery(listQuery + query1 + sortingQry).setLong("orgLocationId", orgLocationId).list();
		String listQueryCount = "select count(*) from ImmigrationTemplateView tempView where tempView.orgLocationId=:orgLocationId"; 
		countlist = session.createQuery(listQueryCount + query1).setLong("orgLocationId", orgLocationId).setFirstResult(templateListDTO.getStart())
				.setMaxResults(templateListDTO.getLength()).list();
		int totalCount = 0;
		if (templateListDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		}
		responseList.setRecordsTotal(totalCount);
		if (query1.toString() != null && !query1.toString().isEmpty()) {
			responseList.setRecordsFiltered(filterCount);
		} else {
			responseList.setRecordsFiltered(totalCount);
		}
		List<ImmigrationTemplateListDTO> tempalteDTOList = new ArrayList<ImmigrationTemplateListDTO>();
		for (ImmigrationTemplateView source : ImmigrationTemplateViewLst) {
			//System.out.println(" DocsCount"+source.getDocsCount());
			ImmigrationTemplateListDTO target = new ImmigrationTemplateListDTO();
			BeanUtils.copyProperties(source, target);
			//System.out.println(" DocsCount target"+target.getDocsCount());
			tempalteDTOList.add(target);
		}
		responseList.setData(tempalteDTOList);
		return responseList;
}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationTemplateForms getImmigrationTemplateFormsById(Long immigrationId) {
		Session session = this.sessionFactory.getCurrentSession();
		ImmigrationTemplateForms immigrationTemplateForms = (ImmigrationTemplateForms) session.get(ImmigrationTemplateForms.class, immigrationId);
		return immigrationTemplateForms;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ImmigrationTemplateDTO> getTemplateTaskList(ImmigrationTemplateDTO templateDTO,
			String searchValue) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		List<ImmigrationTemplateTasks> immigrationTemplateTasks = null;
		ResponseList<ImmigrationTemplateDTO> response = new ResponseList<>();
		
		StringBuilder query1 = new StringBuilder("");
		if(searchValue !=null && !"".equalsIgnoreCase(searchValue)) {
		 query1.append(" where (itt.taskName like '%" + searchValue + "%' )")
		 	.append("OR (itt.priority like '%" + searchValue + "%')")
			.append("OR (itt.taskRepeat like '" + searchValue + "%')");
			
		}
		//usertList sorting order
		String sortingQry = "";
		if(StringUtils.isNotBlank(templateDTO.getSortColumn())) {
				sortingQry = " order by itt."+ templateDTO.getSortColumn() +" "+templateDTO.getSortOrder();
		}
		String listQuery = "from ImmigrationTemplateTasks itt";
		immigrationTemplateTasks = session.createQuery(listQuery+query1+sortingQry).list();
		String listQueryCount = "select count(*) from ImmigrationTemplateTasks itt";
		countlist = session.createQuery(listQueryCount+query1).setFirstResult(templateDTO.getStart()).setMaxResults(templateDTO.getLength()).list();
		int totalCount = 0;
		if (templateDTO != null) {
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
		List<ImmigrationTemplateDTO> maintenanceDTOs = new ArrayList<ImmigrationTemplateDTO>();
		for(ImmigrationTemplateTasks source : immigrationTemplateTasks) {
			ImmigrationTemplateDTO target = new ImmigrationTemplateDTO();
			BeanUtils.copyProperties(source, target);
			if(source.getAssignToCandidate() != null) {
				target.setAssignedTo(Constants.CANDIDATE);
			}else if(source.getAssignToUsers() != null) {
				target.setAssignedTo(source.getAssignToUsers());
			}
			maintenanceDTOs.add(target);
		}
		response.setData(maintenanceDTOs);
		return response;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationTemplateTasks editImmigrationTaskList(String id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ImmigrationTemplateTasks itt where itt.id=:taskId";
		return (ImmigrationTemplateTasks) session.createQuery(query).setLong("taskId", Long.parseLong(id.trim())).uniqueResult();
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public  ImmigrationTemplateDTO getPetitionTemplateDetails(Long templateId) {
		Session session = this.sessionFactory.getCurrentSession();
		ImmigrationTemplateDTO templateDto= null;
		logger.debug(" DAO IMPL  templateId  check value is -->" + templateId);
		try {
			// loading template information.
			ImmigrationTemplate entityObj = (ImmigrationTemplate) session.get(ImmigrationTemplate.class, templateId);
			templateDto = new ImmigrationTemplateDTO();
			templateDto.setId(entityObj.getId());
			templateDto.setTemplateName(entityObj.getName());
			templateDto.setTemplateDescription(entityObj.getDescription());
			// 
			
			
		} catch (Exception e) {
			logger.error("Exception caught in while getting tempalte details with templateId -->"+templateId+" Mesaage is "+e.getMessage());
			e.printStackTrace();
		}
		return templateDto;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationTemplateDocDTO> getImmigrationTemplateDocsListById(Long templateId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationTemplateDocDTO> immiDocsDtoLst = new ArrayList<ImmigrationTemplateDocDTO>();
		String queryString = "from ImmigrationTemplateDocs docs where docs.immigrationTempId = :templateId";
		//System.out.println("template docs list templateId"+templateId);
		logger.debug("template docs list templateId"+templateId);
		try {
			List<ImmigrationTemplateDocs> docsList = session.createQuery(queryString).setLong("templateId",templateId).list();
					for(ImmigrationTemplateDocs entityObj : docsList ) {
						ImmigrationTemplateDocDTO docDto = new ImmigrationTemplateDocDTO();
						docDto.setId(entityObj.getId());
						docDto.setDocumentType(entityObj.getDocumentType());
						docDto.setDescription(entityObj.getDescription());
						docDto.setIsRequired(entityObj.getIsRequired());
						docDto.setMultiple(entityObj.getIsMultipleRequired());
						immiDocsDtoLst.add(docDto);
					}
			
		}catch(Exception e) {
		//s	System.out.println("extption caught while getting template docs list"+e.getMessage());
			logger.error("extption caught while getting template docs list"+e.getMessage());
			e.printStackTrace();
		}
		//System.out.println("immiDocsDtoLst size is -->"+immiDocsDtoLst.size());
		logger.debug("immiDocsDtoLst size is -->"+immiDocsDtoLst.size());
		return immiDocsDtoLst;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationTemplateFormDTO> getImmigrationTemplateformsListById(Long templateId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationTemplateFormDTO> immiFormsDtoLst = new ArrayList<ImmigrationTemplateFormDTO>();
		String queryString = "from ImmigrationTemplateForms f where f.immigrationTempId = :templateId";
		//System.out.println("template froms list templateId"+templateId);
		logger.debug("template froms list templateId"+templateId);
		try {
			List<ImmigrationTemplateForms> formsList = session.createQuery(queryString).setLong("templateId", templateId).list();
			for (ImmigrationTemplateForms entityObj : formsList) {
				ImmigrationTemplateFormDTO formDto = new ImmigrationTemplateFormDTO();
				formDto.setId(entityObj.getId());
				formDto.setFormName(entityObj.getFormName());
				formDto.setFormDescription(entityObj.getDescription());
				formDto.setFileName(entityObj.getFileName());
				formDto.setFileUrl(entityObj.getFileUrl());
				formDto.setIsRequired(entityObj.getIsRequired());
				immiFormsDtoLst.add(formDto);
			}

		} catch (Exception e) {
			//System.out.println("extption caught while getting template forms list" + e.getMessage());
			logger.error("extption caught while getting template forms list" + e.getMessage());
			e.printStackTrace();
		}
		//sSystem.out.println("immiDocsDtoLst size is -->"+immiFormsDtoLst.size());
		logger.debug("immiFormsDtoLsts size is -->"+immiFormsDtoLst.size());
		return immiFormsDtoLst;
	}
}
