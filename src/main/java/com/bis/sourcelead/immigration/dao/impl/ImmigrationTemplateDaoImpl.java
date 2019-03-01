package com.bis.sourcelead.immigration.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
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
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateTaskDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.User;
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
    @Transactional(rollbackFor = {Exception.class})
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
    @Transactional(rollbackFor = {Exception.class})
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
    @Transactional(rollbackFor = {Exception.class})
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
    @Transactional(rollbackFor = {Exception.class})
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
    @Transactional(rollbackFor = {Exception.class})
    public void deletePetitionTemplateDocDetails(Long remainingDocsIds[]) {
        logger.debug("Delete Template Docs-->");
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from ImmigrationTemplateDocs docs where docs.id in (:templateIds)").setParameterList("templateIds", remainingDocsIds);
        int count = query.executeUpdate();
        logger.debug("Number of records deleted-->" + count);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deletePetitionTemplateFormDetails(Long remainingFormIds[]) {
        logger.debug("Delete Template Docs-->");
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from ImmigrationTemplateForms itf where itf.id in (:templateIds)").setParameterList("templateIds", remainingFormIds);
        int count = query.executeUpdate();
        logger.debug("Number of records deleted-->" + count);
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
    @Transactional(rollbackFor = {Exception.class})
    public ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO, String searchValue, Long orgLocationId,String[] countriesList) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Long> countlist = null;
        Integer filterCount = 0;
        List<ImmigrationTemplateView> immigrationTemplateViewLst = null;
        ResponseList<ImmigrationTemplateListDTO> responseList = new ResponseList<>();

        StringBuilder query1 = new StringBuilder("");
        if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
            query1.append(" and ((tempView.name like '%" + searchValue + "%' )");
            query1.append(" or (tempView.description like '%" + searchValue + "%' )");
            query1.append(" or (tempView.country like '%" + searchValue + "%' )");
            query1.append(" or (tempView.petitionType like '%" + searchValue + "%' ))");
            //query1.append(" or (tempView.status like '%" + searchValue + "%' )");
        }
        StringBuilder query2 = new StringBuilder("");
        if (countriesList != null && countriesList.length !=0) {
        	query2 = query2.append(" and (tempView.country IN ( :countriesList ))");
        }
        // usertList sorting order
        String sortingQry = "";
        if (StringUtils.isNotBlank(templateListDTO.getSortColumn())) {
            sortingQry = " order by tempView." + templateListDTO.getSortColumn() + " " + templateListDTO.getSortOrder();
        } /*else {
            sortingQry = " order by tempView.name ";
        }
*/
        String listQuery = "from ImmigrationTemplateView tempView  where tempView.orgLocationId=:orgLocationId " + (templateListDTO.getStatus() >= 0 ? "and tempView.status =" + templateListDTO.getStatus() + " " : " ");
        Query hqlQuery = session.createQuery(listQuery + query1 + query2 + sortingQry);
        if (countriesList != null && countriesList.length !=0) {
        	hqlQuery.setParameterList("countriesList", countriesList);
        }
        immigrationTemplateViewLst = hqlQuery.setLong("orgLocationId", orgLocationId).list();
        String listQueryCount = "select count(*) from ImmigrationTemplateView tempView where tempView.orgLocationId=:orgLocationId " + (templateListDTO.getStatus() >= 0 ? "and tempView.status =" + templateListDTO.getStatus() + " " : " ");
        countlist = session.createQuery(listQueryCount + query1).setLong("orgLocationId", orgLocationId).setFirstResult(templateListDTO.getStart())
                .setMaxResults(templateListDTO.getLength()).list();
        int totalCount = 0;
        if (templateListDTO != null) {
            if (countlist != null && !countlist.isEmpty()) {
                totalCount = countlist.iterator().next().intValue();
                filterCount = totalCount;
            }
        }
        responseList.setRecordsTotal(totalCount);
        if (query1.toString() != null && !query1.toString().isEmpty()) {
            responseList.setRecordsFiltered(filterCount);
        } else {
            responseList.setRecordsFiltered(totalCount);
        }
        List<ImmigrationTemplateListDTO> tempalteDTOList = new ArrayList<ImmigrationTemplateListDTO>();
        for (ImmigrationTemplateView source : immigrationTemplateViewLst) {
            //System.out.println(" DocsCount"+source.getDocsCount());
            ImmigrationTemplateListDTO target = new ImmigrationTemplateListDTO();
            BeanUtils.copyProperties(source, target);
         //    System.out.println(" Tempale name is  target"+source.getName());
            tempalteDTOList.add(target);
        }
        responseList.setData(tempalteDTOList);
        return responseList;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ImmigrationTemplateForms getImmigrationTemplateFormsById(Long immigrationId) {
        Session session = this.sessionFactory.getCurrentSession();
        ImmigrationTemplateForms immigrationTemplateForms = (ImmigrationTemplateForms) session.get(ImmigrationTemplateForms.class, immigrationId);
        return immigrationTemplateForms;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User getUserById(Long Id) throws Exception {

        Session session = this.sessionFactory.getCurrentSession();
        List<User> users = new ArrayList<User>();
        logger.debug("getUserById start");
        users = session.createQuery("from User where id=:id").setLong("id", Id).list();
        logger.debug("getUserById end");
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseList<ImmigrationTemplateTaskDTO> getTemplateTaskList(ImmigrationTemplateTaskDTO templateDTO,
            String searchValue) throws Exception {
        Session session = this.sessionFactory.getCurrentSession();
        List<Long> countlist = null;
        Integer filterCount = 0;
        List<ImmigrationTemplateTasks> immigrationTemplateTasks = null;
        ResponseList<ImmigrationTemplateTaskDTO> response = new ResponseList<>();

        StringBuilder query1 = new StringBuilder("");
        if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
            query1.append(" AND ((itt.taskName like '%" + searchValue + "%' )")
                    .append("OR (itt.priority like '%" + searchValue + "%')")
                    .append("OR (itt.taskRepeat like '" + searchValue + "%'))");

        }
        //usertList sorting order
        String sortingQry = "";
        if (StringUtils.isNotBlank(templateDTO.getSortColumn())) {
            sortingQry = " order by itt." + templateDTO.getSortColumn() + " " + templateDTO.getSortOrder();
        }
        String listQuery = "from ImmigrationTemplateTasks itt where status = 1 AND itt.immigrationTempId.id = :templateId";
        immigrationTemplateTasks = session.createQuery(listQuery + query1 + sortingQry).setLong("templateId", templateDTO.getTemplateId()).list();
        String listQueryCount = "select count(*) from ImmigrationTemplateTasks itt where status = 1 AND itt.immigrationTempId.id = :templateId";
        countlist = session.createQuery(listQueryCount+query1+sortingQry).setLong("templateId", templateDTO.getTemplateId()).setFirstResult(templateDTO.getStart()).setMaxResults(templateDTO.getLength()).list();
        int totalCount = 0;
        if (templateDTO != null) {
            if (countlist != null && !countlist.isEmpty()) {
                totalCount = countlist.iterator().next().intValue();
            }
        }
        response.setRecordsTotal(totalCount);
        if (query1.toString() != null && !query1.toString().isEmpty()) {
            response.setRecordsFiltered(filterCount);
        } else {
            response.setRecordsFiltered(totalCount);
        }
        List<ImmigrationTemplateTaskDTO> maintenanceDTOs = new ArrayList<ImmigrationTemplateTaskDTO>();
        for (ImmigrationTemplateTasks source : immigrationTemplateTasks) {
        	ImmigrationTemplateTaskDTO target = new ImmigrationTemplateTaskDTO();
            BeanUtils.copyProperties(source, target);
            target.setTemplateId(source.getImmigrationTempId().getId());
            if (source.getAssignToCandidate() != null) {
                target.setAssignedTo(Constants.CANDIDATE);
            } else if (source.getAssignToUsers() != null) {
                String userIds[] = source.getAssignToUsers().split(",");
                StringBuilder userNames = new StringBuilder();
                for (int i = 0; i < userIds.length; i++) {

                    User user = getUserById(Long.parseLong(userIds[i]));
                    userNames.append(user.getFullName());
                    if (!(i == (userIds.length - 1))) {
                        userNames.append(", ");
                    }
                }
                target.setAssignedTo(userNames.toString());
            }
            maintenanceDTOs.add(target);
        }
        response.setData(maintenanceDTOs);
        return response;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ImmigrationTemplateTasks editImmigrationTaskList(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from ImmigrationTemplateTasks itt where itt.id=:taskId";
        return (ImmigrationTemplateTasks) session.createQuery(query).setLong("taskId", Long.parseLong(id.trim())).uniqueResult();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ImmigrationTemplateDTO getPetitionTemplateDetails(Long templateId) {
        Session session = this.sessionFactory.getCurrentSession();
        ImmigrationTemplateDTO templateDto = null;
        logger.debug(" DAO IMPL  templateId  check value is -->" + templateId);
        try {
            // loading template information.
            ImmigrationTemplate entityObj = (ImmigrationTemplate) session.get(ImmigrationTemplate.class, templateId);
            templateDto = new ImmigrationTemplateDTO();
            templateDto.setId(entityObj.getId());
            templateDto.setTemplateName(entityObj.getName());
            templateDto.setTemplateDescription(entityObj.getDescription());
            templateDto.setCountry(entityObj.getCountry());
            templateDto.setPetitionType(entityObj.getPetitionType().getId().toString());
            templateDto.setUuid(entityObj.getUuid());
            // 

        } catch (Exception e) {
            logger.error("Exception caught in while getting tempalte details with templateId -->" + templateId + " Mesaage is " + e.getMessage());
            e.printStackTrace();
        }
        return templateDto;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<ImmigrationTemplateDocDTO> getImmigrationTemplateDocsListById(Long templateId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<ImmigrationTemplateDocDTO> immiDocsDtoLst = new ArrayList<ImmigrationTemplateDocDTO>();
        String queryString = "from ImmigrationTemplateDocs docs where docs.immigrationTempId = :templateId";
        //System.out.println("template docs list templateId"+templateId);
        logger.debug("template docs list templateId" + templateId);
        try {
            List<ImmigrationTemplateDocs> docsList = session.createQuery(queryString).setLong("templateId", templateId).list();
            for (ImmigrationTemplateDocs entityObj : docsList) {
                ImmigrationTemplateDocDTO docDto = new ImmigrationTemplateDocDTO();
                docDto.setId(entityObj.getId());
                docDto.setDocumentType(entityObj.getDocumentType().getId());
                docDto.setDocumentCategory(entityObj.getDocumentCategory());
                docDto.setDescription(entityObj.getDescription());
                docDto.setIsRequired(entityObj.getIsRequired());
                docDto.setMultiple(entityObj.getIsMultipleRequired());
                docDto.setOtherType(entityObj.getDocumentOtherType());
                if(StringUtils.isNotBlank(entityObj.getDocumentOtherType())){
                docDto.setDescriptionOther(entityObj.getDescription());
                }
                immiDocsDtoLst.add(docDto);
            }

        } catch (Exception e) {
            //s System.out.println("extption caught while getting template docs list"+e.getMessage());
            logger.error("extption caught while getting template docs list" + e.getMessage());
            e.printStackTrace();
        }
        //System.out.println("immiDocsDtoLst size is -->"+immiDocsDtoLst.size());
        logger.debug("immiDocsDtoLst size is -->" + immiDocsDtoLst.size());
        return immiDocsDtoLst;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<ImmigrationTemplateFormDTO> getImmigrationTemplateformsListById(Long templateId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<ImmigrationTemplateFormDTO> immiFormsDtoLst = new ArrayList<ImmigrationTemplateFormDTO>();
        String queryString = "from ImmigrationTemplateForms f where f.immigrationTempId = :templateId";
        //System.out.println("template froms list templateId"+templateId);
        logger.debug("template froms list templateId" + templateId);
        try {
            List<ImmigrationTemplateForms> formsList = session.createQuery(queryString).setLong("templateId", templateId).list();
            for (ImmigrationTemplateForms entityObj : formsList) {
                ImmigrationTemplateFormDTO formDto = new ImmigrationTemplateFormDTO();
                formDto.setId(entityObj.getId());
                formDto.setFormCategory(entityObj.getFormCategory());
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
        logger.debug("immiFormsDtoLsts size is -->" + immiFormsDtoLst.size());
        return immiFormsDtoLst;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteAllPetitionTemplateDetailsById(Long templateId, String reason) {
        // only Soft delete operation, just status change in the Database.
        Session session = this.sessionFactory.getCurrentSession();
        if (templateId != null) {
            // update ImmigrationTemplate
            //System.out.println(" updated template id -->: " + templateId);
            logger.debug(" updated template id -->: " + templateId);

            String queryString = "UPDATE ImmigrationTemplate SET status = 0, rejectReason =:reason where id =:templateId";
            Query query = session.createQuery(queryString);
            query.setLong("templateId", templateId);
            query.setString("reason", reason);
            int result = query.executeUpdate();
            /*//System.out.println(" updated ImmigrationTemplate records count : " + result);
            logger.debug(" updated ImmigrationTemplate records count : " + result);
            // update ImmigrationTemplateDocs
            queryString = "UPDATE ImmigrationTemplateDocs SET status = 0  where immigrationTempId = :templateId";
            query = session.createQuery(queryString);
            query.setLong("templateId", templateId);
            result = query.executeUpdate();
            //System.out.println(" updated ImmigrationTemplateDocs records count : " + result);
            logger.debug(" updated ImmigrationTemplateDocs records count : " + result);
            // update ImmigrationTemplateForms
            queryString = "UPDATE ImmigrationTemplateForms SET status = 0  where immigrationTempId = :templateId";
            query = session.createQuery(queryString);
            query.setLong("templateId", templateId);
            result = query.executeUpdate();
            //System.out.println(" updated ImmigrationTemplateForms records count : " + result);
            logger.debug(" updated ImmigrationTemplateForms records count : " + result);*/
        }
    }
    
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void reactiveImmigrationTemplateById(Long templateId) {
        // only Soft delete operation, just status change in the Database.
        Session session = this.sessionFactory.getCurrentSession();
        if (templateId != null) {
            // update ImmigrationTemplate
        	String queryString = "UPDATE IMMIGRATION_TEMPLATE SET STATUS = CASE WHEN (ACTIVE_STATUS != 1) THEN 2 ELSE 1 END WHERE ID =:templateId  ";
        	Query query = session.createSQLQuery(queryString);
            query.setLong("templateId", templateId);
            int result = query.executeUpdate();
            
            }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteImmigrationTaskList(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        String queryString = "UPDATE ImmigrationTemplateTasks SET status = 0 where id= :templateId";
        Query query = session.createQuery(queryString);
        query.setLong("templateId", Long.parseLong(id.trim()));
        int result = query.executeUpdate();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int savePetitionTemplateDetailsValues(String templateId) {
        Session session = this.sessionFactory.getCurrentSession();
        String queryString = "UPDATE ImmigrationTemplate SET status = 1, active_status = 1 where id= :templateId";
        Query query = session.createQuery(queryString);
        query.setLong("templateId", Long.parseLong(templateId));
        int result = query.executeUpdate();
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<CandidateEmployers> getPetitionTemplateEmployers(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from CandidateEmployers ce where ce.candidateId.id = :candidateId";
        List<CandidateEmployers> candidateEmployers = session.createQuery(query).setLong("candidateId", id).list();
        return candidateEmployers;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<LookUpData> getLookupDataByType(String type) {
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from LookUpData lud where lud.category = :category";
        List<LookUpData> candidateEmployers = session.createQuery(query).setString("category", type).list();
        return candidateEmployers;
    }

    // old code 
    /*@Override
    @Transactional(rollbackFor = {Exception.class})
    public void savePetitionDetails(Immigration immigration, Candidate candidate,
            CandidateIdendities candidateIdendities, CandidateResumes candidateResume,
            List<CandidateLcaI94Summary> lcaSummaryLst) throws Exception {
        logger.debug("saveConnection");
        Session session = this.sessionFactory.getCurrentSession();
        try {
            if (candidate != null) {
                session.saveOrUpdate(candidate);
            }
            if (immigration != null) {
                session.saveOrUpdate(immigration);
            }
            if (candidateIdendities != null) {
                session.saveOrUpdate(candidateIdendities);
            }
            if (candidateResume != null && candidateResume.getFileURL() != null) {
                session.saveOrUpdate(candidateResume);
            }
            if (lcaSummaryLst.size() != 0) {
                for (CandidateLcaI94Summary candidateLcaI94Summary : lcaSummaryLst) {
                    session.saveOrUpdate(candidateLcaI94Summary);
                }
            }
        } catch (Exception exception) {
            StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "Immigration"));
            throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
        }
    }*/
    
    // new code
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void savePetitionDetails(Immigration immigration, Candidate candidate,
            CandidateIdendities candidateIdendities, CandidateResumes candidateResume,
            CandidateLcaJobDetails candidateLcaJobDetails) throws Exception {
        logger.debug("saveConnection");
        Session session = this.sessionFactory.getCurrentSession();
        try {
            if (candidate != null) {
                session.saveOrUpdate(candidate);
            }
            if (immigration != null) {
                session.saveOrUpdate(immigration);
            }
            if (candidateIdendities != null) {
                session.saveOrUpdate(candidateIdendities);
            }
            if (candidateResume != null && candidateResume.getFileURL() != null) {
                session.saveOrUpdate(candidateResume);
            }
          //  old code
            /*if (lcaSummaryLst.size() != 0) {
                for (CandidateLcaI94Summary candidateLcaI94Summary : lcaSummaryLst) {
                    session.saveOrUpdate(candidateLcaI94Summary);
                }
            }*/
            
            // new code
            if (candidateLcaJobDetails != null) {
                    session.saveOrUpdate(candidateLcaJobDetails);
            }
            
        } catch (Exception exception) {
            StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "Immigration"));
            throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean validateTemplateNameExistance(String templateName, Long orgLocationId) {
        Session  session = this.sessionFactory.openSession();
            String query = "from ImmigrationTemplate iTemplate where iTemplate.name= :name and iTemplate.orgLocations.id =:orgLocationId";
            List<ImmigrationTemplate> templateList = session.createQuery(query).setString("name", templateName).setLong("orgLocationId", orgLocationId).list();
            
            if (templateList != null && !templateList.isEmpty()) {
                return true;
            }else{
            	 return false;
            }
        
    }

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public ImmigrationTemplate getImmigrationTemplateById(Long templateId) {
		ImmigrationTemplate immigrationTemplate= null;
        try {
        	Session session = this.sessionFactory.getCurrentSession();
			immigrationTemplate = (ImmigrationTemplate)session.get(ImmigrationTemplate.class,templateId);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->"+e.getMessage());
			e.printStackTrace();
		}
		return immigrationTemplate;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public List<LookUpData> getLookupDataByTypeAndLocation(String petitionType, String locationVal) {
		Session session = this.sessionFactory.getCurrentSession();
        String query = "from LookUpData lud where lud.category = :category and location = :location";
        List<LookUpData> petitionTypeLookup = session.createQuery(query).setString("category", petitionType).setString("location", locationVal).list();
        return petitionTypeLookup;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public List<ImmigrationTemplateDocs> getImmigrationTemplateDocsList(Long templateId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationTemplateDocs> immigrationTemplateDocsList = new ArrayList<ImmigrationTemplateDocs>();
			String queryString = "from ImmigrationTemplateDocs td where td.immigrationTempId.id=:templateId order by td.updatedDate desc";
			immigrationTemplateDocsList = session.createQuery(queryString).setLong("templateId", templateId).list();
		
		return immigrationTemplateDocsList;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public List<ImmigrationTemplateForms> getImmigrationTemplateFormsList(Long templateId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationTemplateForms> immigrationTemplateFormsList = new ArrayList<ImmigrationTemplateForms>();
			String queryString = "from ImmigrationTemplateForms td where td.immigrationTempId.id=:templateId order by td.updatedDate desc";
			immigrationTemplateFormsList = session.createQuery(queryString).setLong("templateId", templateId).list();
		
		return immigrationTemplateFormsList;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public ImmigrationTemplateTasks getImmigratonTemplateTasksById(Long id) {
		 Session session = this.sessionFactory.getCurrentSession();
		 ImmigrationTemplateTasks immigrationTemplateTasks = (ImmigrationTemplateTasks) session.get(ImmigrationTemplateTasks.class, id);
	     return immigrationTemplateTasks;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public List<ImmigrationTemplateTasks> getImmigratonTemplateTasksByTemplateId(Long templateId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationTemplateTasks> ImmigrationTemplateTasksList = new ArrayList<ImmigrationTemplateTasks>();
			String queryString = "from ImmigrationTemplateTasks td where td.immigrationTempId.id=:templateId and td.status=1";
			ImmigrationTemplateTasksList = session.createQuery(queryString).setLong("templateId", templateId).list();
		return ImmigrationTemplateTasksList;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public Long getTemplateIdByUUID(String immiTemplateUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select temp.id from ImmigrationTemplate temp where temp.uuid = :templateUUID";
		Long tempId = (Long) session.createQuery(query).setParameter("templateUUID", immiTemplateUUID).uniqueResult();
		return tempId;
	}

	@Override
	public boolean validateTemplateTaskNameExistance(String templateTaskName, Long templateId, Long immiTaskId) {
		Session session = null;
		boolean returnVal = false;
        try {
            session = this.sessionFactory.openSession();
            String query = null;
            query = " from ImmigrationTemplateTasks iTemplate where iTemplate.taskName= :name and iTemplate.status = 1 and iTemplate.immigrationTempId.id = :templateId";
            if(immiTaskId != 0) {
                query += " and iTemplate.id <> :templateTaskId";
            }
            Query hqlQuery = session.createQuery(query);
            if(immiTaskId != 0) {
            	hqlQuery.setLong("templateTaskId", immiTaskId);
            }
            List<ImmigrationTemplate> templateList = hqlQuery.setString("name", templateTaskName).setLong("templateId",templateId).list();
            if (templateList != null && !templateList.isEmpty()) {
                returnVal = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
		return returnVal;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public ImmigrationTemplate getImmigrationTemplateByIdAndLocationId(String templateName,Long templateId, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ImmigrationTemplate immiTemplate where immiTemplate.id =:templateId and immiTemplate.name = :templateName and immiTemplate.orgLocations.id =:orgLocationId";
		Query finalQuery = session.createQuery(query);
		List<ImmigrationTemplate> templateList = finalQuery.setParameter("templateName", templateName).setParameter("templateId", templateId).setParameter("orgLocationId", orgLocationId).list();
		if(templateList.size()>0){
		return 	templateList.get(0);
		}else{
			return null;	
		}
	}
	
	
}
