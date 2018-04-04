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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;


/**
 * @author Srinivas.Sabbu
 *
 */
@Repository
public class ImmigrationCommonDaoImpl implements ImmigrationCommonDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	CandidateDao candidateDao;

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

	private static final Logger logger = LoggerFactory.getLogger(ImmigrationCommonDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations) {
		logger.debug("Saving Template Details-->");
		List<ImmigrationTemplate> templateLst = new ArrayList<ImmigrationTemplate>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			System.out.println(" before query execute templateLst size -->"+templateLst.size());
			String queryString  = "from ImmigrationTemplate it where it.status = 1 and it.orgLocations.id =:orgLocationId";
			templateLst= session.createQuery(queryString).setLong("orgLocationId", orgLocations.getId()).list();
			System.out.println("tempalte Details loaded Successfully templateLst size -->"+templateLst.size());
			
			logger.debug("tempalte Details loaded Successfully-->");
		} catch (Exception exception) {
			
				exception.printStackTrace();
		}
		return templateLst;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration savePetitionDetails(Immigration immigration, Candidate candidate,
			CandidateIdendities candidateIdendities, CandidateResumes candidateResume,
			List<CandidateLcaI94Summary> lcaSummaryLst, List<CandidateResidence> residenceList) throws Exception {
		logger.debug("savePetitionDetails starts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidate != null) {
					session.saveOrUpdate(candidate);
			}
			if (immigration != null) {
				session.saveOrUpdate(immigration);
			}
			if(candidateIdendities != null) {
				session.saveOrUpdate(candidateIdendities);
			}
			if(candidateResume!= null && candidateResume.getFileURL() != null) {
				session.saveOrUpdate(candidateResume);
			}
			if(lcaSummaryLst.size() != 0) {
				for(CandidateLcaI94Summary candidateLcaI94Summary : lcaSummaryLst) {
					session.saveOrUpdate(candidateLcaI94Summary);
				}
			}
			if(residenceList != null && residenceList.size() != 0) { // saving multiple residence for candidates only for immigration
				for(CandidateResidence residence : residenceList) {
					session.saveOrUpdate(residence);
				}
			}
		} catch (Exception exception) {
		    logger.error("Exception caught while saving immigration information "+exception.getMessage());
		    exception.printStackTrace();
		}
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration getImmigrationViewList(String immigrationId) {
		logger.debug("getImmigrationViewList starts");
		Session session = this.sessionFactory.getCurrentSession();
		Immigration immigration =  (Immigration) session.get(Immigration.class, Long.parseLong(immigrationId));
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getPetitionTypeById(Long petitionTypeId, String petitionTypeConstant) {
		Session session = this.sessionFactory.getCurrentSession();
        String query = "select lud.name from LookUpData lud where lud.category = :category and lud.id = :lookupId";
        String name = (String) session.createQuery(query).setString("category", petitionTypeConstant).setLong("lookupId", petitionTypeId).uniqueResult();
		return name;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateLcaI94Summary getCandidateLCADetails(Long candidateId, String yes) {
		
		 Session session = this.sessionFactory.getCurrentSession();
	        String query = "from CandidateLcaI94Summary clis left join fetch clis.requirementId where clis.candidateId.id = :candidateId and clis.isCurrent = :yes";
	        CandidateLcaI94Summary lcaI94Summary = (CandidateLcaI94Summary) session.createQuery(query).setLong("candidateId", candidateId).setString("yes", yes).uniqueResult();
		return lcaI94Summary;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String saveCandidateFamilyMemberDetails(CandidateFamilyDetails familyDetails) {
		try {
		Session session = this.sessionFactory.getCurrentSession();
		  session.saveOrUpdate(familyDetails);
		}catch(Exception e) {
			logger.error("Exception caught while saving family details -->"+e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}


    @Override
    @Transactional(rollbackFor = { Exception.class })
	public CandidatePassportVisa savePassportDetails(CandidatePassportVisa candidatePassportVisa) throws Exception
	{
   Session session = this.sessionFactory.getCurrentSession();
  
		logger.debug("Storing ImmigrationPassportVisa");
		try {
			if (candidatePassportVisa != null) {
				session.saveOrUpdate(candidatePassportVisa);
			}
			logger.debug("candidateCertifications Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidatePassport"));
			    try {
	                throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
	            } catch (SourceLeadConstraintViolationException e) {
	                e.printStackTrace();
	            }
		}
		return candidatePassportVisa;

}
    
    @Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateResidence saveResidenceDetails(CandidateResidence candidateResidence) throws Exception {
		
		logger.debug("Adding CandidateResidence");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(candidateResidence);
			logger.debug("CandidateResidence Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CANDIDATE_RESIDENCE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return candidateResidence;
	}

    @SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId) {
    	logger.debug("Getting  CandidateFamilyDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateFamilyDetails> familyList = new ArrayList<CandidateFamilyDetails>();
		try {
			
			String queryString  ="from CandidateFamilyDetails cd where cd.candidate.id=:candidateId order by cd.updatedDate desc";
			
			familyList = session.createQuery(queryString).setLong("candidateId", candidateId).list();
			
		}catch(Exception e) {
		  logger.error("Exeption caught while getting dao layer --->"+e.getMessage());
		  e.printStackTrace();
		}
		return familyList;
	}

    @Override
	@Transactional(rollbackFor = { Exception.class })
	public String deletetCandidateFamilyDetails(Long id) {
		  System.out.println("Delete deletetCandidateFamilyDetails candidateId ---> "+id);
		  
	        try {
				Session session = this.sessionFactory.getCurrentSession();
				CandidateFamilyDetails familyEntity = (CandidateFamilyDetails)session.load(CandidateFamilyDetails.class,id);
				session.delete(familyEntity);
				session.flush();
			} catch (HibernateException e) {
				logger.error("SQL Exception occured while doing delete -->"+e.getMessage());
				e.printStackTrace();
			}
	        System.out.println("Deleted record id -->"+ id);
		return "success";
	}
    
    @SuppressWarnings("unchecked")
   	@Override
   	@Transactional(rollbackFor = { Exception.class })
   	public List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId) {
   		logger.debug("Getting  CandidateResidenceDetails --->  ");
   		Session session = this.sessionFactory.getCurrentSession();
   		List<CandidateResidence> candidatesResidenceList = new ArrayList<CandidateResidence>();
   		try {
   			
   			String queryString  ="from CandidateResidence cr where cr.candidate.id=:candidateId order by cr.updatedDate desc";
   			
   			candidatesResidenceList = session.createQuery(queryString).setLong("candidateId", candidateId).list();
   			
   		}catch(Exception e) {
   		  logger.error("Exeption caught while getting dao layer --->"+e.getMessage());
   		  e.printStackTrace();
   		}
   		return candidatesResidenceList;
   	}
    @SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidatePassportVisa> getCandidatePassportVisaDetails(Long candidateId, String idType, String searchValue)
	{
		List<CandidatePassportVisa> passportvisaList = new ArrayList<CandidatePassportVisa>();
		logger.debug("Getting  CandidatepassportDetails --->  ");  
		Session session = this.sessionFactory.getCurrentSession();
		if (!"undefined".equalsIgnoreCase(searchValue) && StringUtils.isNotBlank(searchValue)
				&& searchValue != "") {
			String queryString = "from CandidatePassportVisa cp where cp.candidate.id=:candidateId and cp.idType=:idType and (cp.idNumber =:searchValue or cp.country =:searchValue)  order by cp.updatedDate desc";
			passportvisaList = session.createQuery(queryString).setLong("candidateId", candidateId).setString("idType", idType).setString("searchValue", searchValue).list();
		}
		else {
			String queryString = "from CandidatePassportVisa cp where cp.candidate.id=:candidateId and cp.idType=:idType  order by cp.updatedDate desc";
			passportvisaList = session.createQuery(queryString).setLong("candidateId", candidateId).setString("idType", idType).list();
		}
		return passportvisaList;
	}
    @Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateFamilyDetails getCandidateFamilyDetailsById(Long id) {
		System.out.println("get CandidateFamilyDetails id ---> "+id);
		CandidateFamilyDetails familyObj= null;
        try {
			Session session = this.sessionFactory.getCurrentSession();
			familyObj = (CandidateFamilyDetails)session.get(CandidateFamilyDetails.class,id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->"+e.getMessage());
			e.printStackTrace();
		}
        System.out.println("got the record id -->"+ id);
		return familyObj;
	}

   
	@Override
   	@Transactional(rollbackFor = { Exception.class })
	public void removeCandidatePassportVisaDetails(Long candidateId, Long id, User user, OrgLocations orgLocations, String idType)
	{
		 try {
				Session session = this.sessionFactory.getCurrentSession();
				CandidatePassportVisa PassportVisaEntity = (CandidatePassportVisa)session.load(CandidatePassportVisa.class,id);
				session.delete(PassportVisaEntity);
				session.flush();
			} catch (HibernateException e) {
				logger.error("SQL Exception occured while doing delete -->"+e.getMessage());
				e.printStackTrace();
			}
	        System.out.println("Deleted record id -->"+ id);
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteCandidateResidenceDetails(Long id,Long candidateId) {
		
		System.out.println("Delete deletetCandidateFamilyDetails candidateId ---> "+id);
		  
        try {
			Session session = this.sessionFactory.getCurrentSession();
			CandidateResidence res = (CandidateResidence)session.load(CandidateResidence.class,id);
			session.delete(res);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->"+e.getMessage());
			e.printStackTrace();
		}
        System.out.println("Deleted record id -->"+ id);
        
		return "success";
		
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateResidence getCandidateResidenceDetailsById(Long id) {
		System.out.println("get CandidateResidenceDetails id ---> "+id);
		CandidateResidence residenceObj= null;
        try {
			Session session = this.sessionFactory.getCurrentSession();
			residenceObj = (CandidateResidence)session.get(CandidateResidence.class,id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->"+e.getMessage());
			e.printStackTrace();
		}
        System.out.println("got the record id -->"+ id);
		return residenceObj;
	}
	
	  @Override
			@Transactional(rollbackFor = { Exception.class })
			public CandidatePassportVisa getCandidatePassportVisaDetailsById(Long id) {
				System.out.println("get CandidateFamilyDetails id ---> "+id);
				CandidatePassportVisa passportVisaObj= null;
		        try {
					Session session = this.sessionFactory.getCurrentSession();
					passportVisaObj = (CandidatePassportVisa)session.get(CandidatePassportVisa.class,id);
				} catch (HibernateException e) {
					logger.error("SQL Exception occured while doing getting record -->"+e.getMessage());
					e.printStackTrace();
				}
		        System.out.println("got the record id -->"+ id);
				return passportVisaObj;
			}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationCandidateDocuments saveImmigrationCandidateDocs(ImmigrationCandidateDocuments immigrationCandidateDocuments)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		  
		logger.debug("Storing ImmigrationPassportVisa");
		try {
			if (immigrationCandidateDocuments != null) {
				session.saveOrUpdate(immigrationCandidateDocuments);
			}
			logger.debug("ImmigrationCandidateDocuments Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ImmigrationCandidateDocuments"));
			    try {
	                throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
	            } catch (SourceLeadConstraintViolationException e) {
	                e.printStackTrace();
	            }
		}
		return immigrationCandidateDocuments;
}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration  getImmigrationById(Long id) {
		System.out.println("get CandidateFamilyDetails id ---> "+id);
		Immigration immigration= null;
        try {
			Session session = this.sessionFactory.getCurrentSession();
			immigration = (Immigration)session.get(Immigration.class,id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->"+e.getMessage());
			e.printStackTrace();
		}
        System.out.println("got the record id -->"+ id);
		return immigration;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration getImmigrationByImmigrationId(Long immigrationId) throws Exception {
		Immigration immigration= null;
        try {
			Session session = this.sessionFactory.getCurrentSession();
			immigration = (Immigration)session.get(Immigration.class,immigrationId);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->"+e.getMessage());
			e.printStackTrace();
		}
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationCandidateDocuments> getImmigrationCandidateDocs(Long immigrationId, Long templateId,
			Long candidateId) {
		logger.debug("Getting  CandidatepassportDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationCandidateDocuments> immigrationCandidateDocumentsList = new ArrayList<ImmigrationCandidateDocuments>();
			String queryString = "from ImmigrationCandidateDocuments cd where cd.candidateId.id=:candidateId and cd.immigrationId.id=:immigrationId and cd.immigrationTemplateId.id=:templateId  order by cd.updatedDate desc";
			immigrationCandidateDocumentsList = session.createQuery(queryString).setLong("candidateId", candidateId).setLong("templateId", templateId).setLong("immigrationId", immigrationId).list();
		
		return immigrationCandidateDocumentsList;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationCandidateForms saveImmigrationCandidateForms(ImmigrationCandidateForms immigrationCandidateForms)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		  
		logger.debug("Storing ImmigrationCandidateForms");
		try {
			if (immigrationCandidateForms != null) {
				session.saveOrUpdate(immigrationCandidateForms);
			}
			logger.debug("ImmigrationCandidateForms Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ImmigrationCandidateForms"));
			    try {
	                throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
	            } catch (SourceLeadConstraintViolationException e) {
	                e.printStackTrace();
	            }
		}
		return immigrationCandidateForms;
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationCandidateForms> getCandidateImmigartionFormsDetails(Long candidateId, Long templateId,
			Long immigrationId) {
		List<ImmigrationCandidateForms> candidateFormLst= null;
		 try {
				Session session = this.sessionFactory.getCurrentSession();
                String queryString ="from ImmigrationCandidateForms icf where  icf.immigrationTempId.id= :templateId and icf.immigration.id=:immigrationId and icf.candidate=:candidateId";
                candidateFormLst =session.createQuery(queryString)
                		                                     .setLong("templateId", templateId)
                		                                     .setLong("immigrationId", immigrationId)
                		                                     .setLong("candidateId", candidateId).list();
                System.out.println("candidateFormLst size is ---> "+candidateFormLst.size());
		 } catch (HibernateException e) {
				logger.error("SQL Exception occured while doing getting ImmigrationCandidateForms records -->"+e.getMessage());
				e.printStackTrace();
			}
		return candidateFormLst;
	}
	
	
	}
	