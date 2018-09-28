package com.bis.sourcelead.immigration.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.LcaDTO;
import com.bis.sourcelead.dto.LcaDetailsDTO;
import com.bis.sourcelead.dto.LcaListViewDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationActivityHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationDashboardView;
import com.bis.sourcelead.entity.ImmigrationListExportView;
import com.bis.sourcelead.entity.ImmigrationListView;
import com.bis.sourcelead.entity.ImmigrationTasks;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.LcaDocumentHistory;
import com.bis.sourcelead.entity.LcaHistory;
import com.bis.sourcelead.entity.LcaListExportView;
import com.bis.sourcelead.entity.LcaListView;
import com.bis.sourcelead.entity.LcaPlaceOfEployment;
import com.bis.sourcelead.entity.LcaVisaClarifications;
import com.bis.sourcelead.entity.MyImmigrationView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Srinivas.Sabbu
 *
 */
@Repository
public class ImmigrationCommonDaoImpl implements ImmigrationCommonDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	CandidateDao candidateDao;

	@Autowired
	private CommonUtil commonUtil;

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

	@Value("${jdbc.schema}")
	private String schema;
	
	private static final Logger logger = LoggerFactory.getLogger(ImmigrationCommonDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations) {
		logger.debug("Saving Template Details-->");
		List<ImmigrationTemplate> templateLst = new ArrayList<ImmigrationTemplate>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			logger.debug(" before query execute templateLst size -->" + templateLst.size());
			String queryString = "from ImmigrationTemplate it where it.status = 1 and it.orgLocations.id =:orgLocationId";
			templateLst = session.createQuery(queryString).setLong("orgLocationId", orgLocations.getId()).list();
			logger.debug("tempalte Details loaded Successfully templateLst size -->" + templateLst.size());

			logger.debug("tempalte Details loaded Successfully-->");
		} catch (Exception exception) {

			exception.printStackTrace();
		}
		return templateLst;
	}
	
//	Old code

/*	@Override
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
			
			if (residenceList != null && residenceList.size() != 0) { // saving
																		// multiple
																		// residence
																		// for
																		// candidates
																		// only
																		// for
																		// immigration
				for (CandidateResidence residence : residenceList) {
					session.saveOrUpdate(residence);
					logger.debug("The residence id is: +++++++++++++++ :" + residence.getId());
				}
			}
		} catch (Exception exception) {
			logger.error("Exception caught while saving immigration information " + exception.getMessage());
			exception.printStackTrace();
		}
		return immigration;
	}*/
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration savePetitionDetails(Immigration immigration, Candidate candidate,
			CandidateIdendities candidateIdendities, CandidateResumes candidateResume,
			CandidateLcaJobDetails candidateLcaJobDetails, List<CandidateResidence> residenceList) throws Exception {
		logger.debug("savePetitionDetails starts");
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
			
		//	old code
			
			/*if (lcaSummaryLst.size() != 0) {
				for (CandidateLcaI94Summary candidateLcaI94Summary : lcaSummaryLst) {
					session.saveOrUpdate(candidateLcaI94Summary);
				}
			}*/
			
			/*if (candidateLcaJobDetails != null) {
					session.saveOrUpdate(candidateLcaJobDetails);
			}*/
			
			if (residenceList != null && residenceList.size() != 0) { // saving multipl residence for candidates only for immigration
				for (CandidateResidence residence : residenceList) {
					session.saveOrUpdate(residence);
					logger.debug("The residence id is: +++++++++++++++ :" + residence.getId());
				}
			}
		} catch (Exception exception) {
			logger.error("Exception caught while saving immigration information " + exception.getMessage());
			exception.printStackTrace();
		}
		
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration getImmigrationDetails(String immigrationId) {
		logger.debug("getImmigrationDetails starts");
		Session session = this.sessionFactory.getCurrentSession();
		Immigration immigration = (Immigration) session.get(Immigration.class, Long.parseLong(immigrationId));
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getPetitionTypeById(Long petitionTypeId, String petitionTypeConstant) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select lud.name from LookUpData lud where lud.category = :category and lud.id = :lookupId";
		String name = (String) session.createQuery(query).setString("category", petitionTypeConstant)
				.setLong("lookupId", petitionTypeId).uniqueResult();
		return name;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationCandidateDocuments getImmigrationCandidateDocsById(Long id, Long candidateId, Long immigrationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ImmigrationCandidateDocuments  ciDoc where ciDoc.id=:id and ciDoc.candidateId.id = :candidateId and ciDoc.immigrationId.id =:immigrationId";
		ImmigrationCandidateDocuments canImgDoc = (ImmigrationCandidateDocuments) session.createQuery(query)
				.setLong("id", id).setLong("candidateId", candidateId).setLong("immigrationId", immigrationId)
				.uniqueResult();
		return canImgDoc;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationCandidateForms getImmigrationCandidateFormsById(Long id, Long candidateId, Long immigrationId) {
		ImmigrationCandidateForms canform = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from ImmigrationCandidateForms  ciForm where ciForm.id=:id and ciForm.candidate.id = :candidateId and ciForm.immigration.id =:immigrationId";
			canform = (ImmigrationCandidateForms) session.createQuery(query).setLong("id", id)
					.setLong("candidateId", candidateId).setLong("immigrationId", immigrationId).uniqueResult();

		} catch (Exception e) {
			logger.error("Exception caught in Database while getting ImmigrationCandidateForms" + e.getMessage());
			e.printStackTrace();
		}
		return canform;
	}

	//Old Code
	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateLcaI94Summary getCandidateLCADetails(Long candidateId) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateLcaI94Summary clis left join fetch clis.requirementId where clis.candidateId.id = :candidateId";
		CandidateLcaI94Summary lcaI94Summary = (CandidateLcaI94Summary) session.createQuery(query).setLong("candidateId", candidateId).uniqueResult();
		return lcaI94Summary;
	}*/
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateLcaJobDetails getCandidateLcaJobDetails(Long immigrationId) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateLcaJobDetails cljs left join fetch cljs.requirementId where cljs.immigration.id = :immigrationId";
		CandidateLcaJobDetails candLcaJobDetails = null;
		candLcaJobDetails = (CandidateLcaJobDetails) session.createQuery(query).setLong("immigrationId", immigrationId).uniqueResult();
		return candLcaJobDetails;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String saveCandidateFamilyMemberDetails(CandidateFamilyDetails familyDetails) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(familyDetails);
		} catch (Exception e) {
			logger.error("Exception caught while saving family details -->" + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidatePassportVisa savePassportDetails(CandidatePassportVisa candidatePassportVisa) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		logger.debug("Storing ImmigrationPassportVisa");
		try {
			if (candidatePassportVisa != null) {
				session.saveOrUpdate(candidatePassportVisa);
			}
			logger.debug("candidateCertifications Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "CandidatePassport"));
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
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "CANDIDATE_RESIDENCE"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}

		return candidateResidence;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId, String searchVal) {
		logger.debug("Getting  CandidateFamilyDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateFamilyDetails> familyList = new ArrayList<CandidateFamilyDetails>();
		try {

			String queryString = "from CandidateFamilyDetails cd where cd.candidate.id=:candidateId ";
			if (!StringUtils.isEmpty(searchVal)) {
				queryString = queryString + " and (cd.firstName like :searchVal or cd.relation like :searchVal) ";
			}
			queryString = queryString + " order by cd.updatedDate desc";

			Query query = session.createQuery(queryString).setLong("candidateId", candidateId);
			if (!StringUtils.isEmpty(searchVal)) {
				query.setParameter("searchVal", "%" + searchVal + "%");
			}
			familyList = query.list();

		} catch (Exception e) {
			logger.error("Exeption caught while getting dao layer --->" + e.getMessage());
			e.printStackTrace();
		}
		return familyList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getImmigrationGeneratedIdByOrgLocationId(OrgLocations orgLocations, Integer generatedIdCount) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		if(orgLocations.getIsLawFirm() != null && orgLocations.getIsLawFirm().equalsIgnoreCase("Y")) {
			query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'" + generatedIdCount
					+ "') AS UNSIGNED)) FROM "+schema+".IMMIGRATION WHERE ATTORNEY_LOCATION_ID = '"
					+ orgLocations.getId() + "'";
		} else {
			query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'" + generatedIdCount
					+ "') AS UNSIGNED)) FROM "+schema+".IMMIGRATION WHERE ORG_LOCATION_ID = '" + orgLocations.getId()
					+ "'";
		}
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deletetCandidateFamilyDetails(Long id) {
		logger.debug("Delete deletetCandidateFamilyDetails candidateId ---> " + id);

		try {
			Session session = this.sessionFactory.getCurrentSession();
		CandidateFamilyDetails familyEntity = (CandidateFamilyDetails) session.load(CandidateFamilyDetails.class,id);
			session.delete(familyEntity);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Deleted record id -->" + id);
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId,
			String searchValue) {
		logger.debug("Getting  CandidateResidenceDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateResidence> candidatesResidenceList = new ArrayList<CandidateResidence>();
		List<CandidateResidence> candidatesMasterResidenceList = new ArrayList<CandidateResidence>();

		Candidate canCurrResiCand = null;

		String queryStringCand = null;
		if (!"undefiend".equalsIgnoreCase(searchValue) && StringUtils.isNotBlank(searchValue)) {
			queryStringCand = "from Candidate cr where cr.id=:candidateId and (cr.location like :searchValue or cr.country like :searchValue)";
			canCurrResiCand = (Candidate) session.createQuery(queryStringCand).setLong("candidateId", candidateId)
					.setString("searchValue", "%" + searchValue + "%").uniqueResult();
		} else {
			queryStringCand = "from Candidate cr where cr.id=:candidateId";
			canCurrResiCand = (Candidate) session.createQuery(queryStringCand).setLong("candidateId", candidateId)
					.uniqueResult();
		}
		// logger.debug(" inside canCurrResiCand -->"+canCurrResiCand);
		CandidateResidence currResidence = new CandidateResidence();
		if (canCurrResiCand != null) {
			currResidence.setLocation(canCurrResiCand.getLocation());
			currResidence.setCountry(canCurrResiCand.getCountry());
			currResidence.setState(canCurrResiCand.getState());
			currResidence.setCity(canCurrResiCand.getCity());
			currResidence.setStreetAddress(canCurrResiCand.getAddress());
			currResidence.setZipcode(canCurrResiCand.getZipCode());
			currResidence.setStartDate(canCurrResiCand.getResidenceStartDate());
			currResidence.setIsCurrAddress("Y");// used for edit functionality.
			currResidence.setId(canCurrResiCand.getId());
		}
		candidatesMasterResidenceList.add(currResidence);
		if (!"undefiend".equalsIgnoreCase(searchValue) && StringUtils.isNotBlank(searchValue)) {
			String queryString = "from CandidateResidence cr where cr.candidate.id=:candidateId and (cr.location like :searchValue or cr.country like :searchValue) order by cr.updatedDate desc";
			candidatesResidenceList = session.createQuery(queryString).setLong("candidateId", candidateId)
					.setString("searchValue", "%" + searchValue + "%").list();
		} else {
			String queryString = "from CandidateResidence cr where cr.candidate.id=:candidateId order by cr.updatedDate desc";
			candidatesResidenceList = session.createQuery(queryString).setLong("candidateId", candidateId).list();
		}
		// logger.debug(" inside candidatesResidenceList size
		// -->"+candidatesResidenceList.size());
		candidatesMasterResidenceList.addAll(candidatesResidenceList);
		// logger.debug(" inside candidatesMasterResidenceList size
		// -->"+candidatesMasterResidenceList.size());
		return candidatesMasterResidenceList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidatePassportVisa> getCandidatePassportVisaDetails(Long candidateId, String idType,
			String searchValue) {
		List<CandidatePassportVisa> passportvisaList = new ArrayList<CandidatePassportVisa>();
		logger.debug("Getting  CandidatepassportDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		if (!"undefined".equalsIgnoreCase(searchValue) && StringUtils.isNotBlank(searchValue) && searchValue != "") {
			String queryString = "from CandidatePassportVisa cp where cp.candidate.id=:candidateId and cp.idType=:idType and (cp.idNumber like :searchValue or cp.country like :searchValue or cp.visaType like :searchValue)  order by cp.updatedDate desc";
			passportvisaList = session.createQuery(queryString).setLong("candidateId", candidateId)
					.setString("idType", idType).setString("searchValue", "%" + searchValue + "%").list();
		} else {
			String queryString = "from CandidatePassportVisa cp where cp.candidate.id=:candidateId and cp.idType=:idType  order by cp.updatedDate desc";
			passportvisaList = session.createQuery(queryString).setLong("candidateId", candidateId)
					.setString("idType", idType).list();
		}
		return passportvisaList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateFamilyDetails getCandidateFamilyDetailsById(Long id) {
		logger.debug("get CandidateFamilyDetails id ---> " + id);
		CandidateFamilyDetails familyObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			familyObj = (CandidateFamilyDetails) session.get(CandidateFamilyDetails.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return familyObj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void removeCandidatePassportVisaDetails(Long candidateId, Long id, User user, OrgLocations orgLocations,
			String idType) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CandidatePassportVisa PassportVisaEntity = (CandidatePassportVisa) session.load(CandidatePassportVisa.class,
					id);
			session.delete(PassportVisaEntity);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Deleted record id -->" + id);

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteCandidateResidenceDetails(Long id, Long candidateId) {

		logger.debug("Delete deletetCandidateFamilyDetails candidateId ---> " + id);

		try {
			Session session = this.sessionFactory.getCurrentSession();
			CandidateResidence res = (CandidateResidence) session.load(CandidateResidence.class, id);
			session.delete(res);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Deleted record id -->" + id);

		return "success";

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateResidence getCandidateResidenceDetailsById(Long id) {
		logger.debug("get CandidateResidenceDetails id ---> " + id);
		CandidateResidence residenceObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			residenceObj = (CandidateResidence) session.get(CandidateResidence.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return residenceObj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidatePassportVisa getCandidatePassportVisaDetailsById(Long id) {
		logger.debug("get CandidateFamilyDetails id ---> " + id);
		CandidatePassportVisa passportVisaObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			passportVisaObj = (CandidatePassportVisa) session.get(CandidatePassportVisa.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return passportVisaObj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationCandidateDocuments saveImmigrationCandidateDocs(
			ImmigrationCandidateDocuments immigrationCandidateDocuments) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		logger.debug("Storing ImmigrationPassportVisa");
		try {
			if (immigrationCandidateDocuments != null) {
				session.saveOrUpdate(immigrationCandidateDocuments);
			}
			logger.debug("ImmigrationCandidateDocuments Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(
					MessageFormat.format(duplicateValue, "ImmigrationCandidateDocuments"));
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
	public ImmigrationCandidateForms getImmigrationCandidateFormById(Long id) {
		ImmigrationCandidateForms formObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			formObj = (ImmigrationCandidateForms) session.get(ImmigrationCandidateForms.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		return formObj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deletetImmigrationCandidateFormsById(Long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			ImmigrationCandidateForms forms = (ImmigrationCandidateForms) session.load(ImmigrationCandidateForms.class,
					id);
			session.delete(forms);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deletetImmigrationCandidateDocsById(Long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			ImmigrationCandidateDocuments doc = (ImmigrationCandidateDocuments) session
					.load(ImmigrationCandidateDocuments.class, id);
			session.delete(doc);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationCandidateDocuments getImmigrationCandidateDocumentById(Long id) {
		ImmigrationCandidateDocuments docObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			docObj = (ImmigrationCandidateDocuments) session.get(ImmigrationCandidateDocuments.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		return docObj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration getImmigrationById(Long id) {
		Immigration immigration = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			immigration = (Immigration) session.get(Immigration.class, id);
			
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration getImmigrationByImmigrationId(Long immigrationId) throws Exception {
		Immigration immigration = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			immigration = (Immigration) session.get(Immigration.class, immigrationId);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationCandidateDocuments> getImmigrationCandidateDocs(Long immigrationId, Long templateId,
			Long candidateId, String searchVal, String myImmigration) {
		logger.debug("Getting  CandidatepassportDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotEmpty(searchVal) && searchVal != null) {
			query1.append(" and ((cd.documentType.name like :searchVal )");
			query1.append(" or (cd.documentStatus like :searchVal )");
			query1.append(" or (cd.description like :searchVal ))");
		}
		StringBuilder myImmiQuery = new StringBuilder("");
		if(myImmigration.equalsIgnoreCase("Y")) {
			myImmiQuery.append(" and cd.docCategory = :docCategory");
		}
		List<ImmigrationCandidateDocuments> immigrationCandidateDocumentsList = new ArrayList<ImmigrationCandidateDocuments>();
		String queryString = "from ImmigrationCandidateDocuments cd where cd.candidateId.id=:candidateId and cd.immigrationId.id=:immigrationId and cd.immigrationTemplateId.id=:templateId ";
		String orderByString = " order by cd.updatedDate desc";
		Query query = session.createQuery(queryString + query1 + myImmiQuery + orderByString).setLong("candidateId", candidateId)
				.setLong("templateId", templateId).setLong("immigrationId", immigrationId);
		if (StringUtils.isNotEmpty(searchVal) && searchVal != null) {
			query.setParameter("searchVal", "%" + searchVal + "%");
		}
		
		if(myImmigration.equalsIgnoreCase("Y")) {
			query.setParameter("docCategory", "Document Type");
		}
		immigrationCandidateDocumentsList = query.list();
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
			StringBuilder message = new StringBuilder(
					MessageFormat.format(duplicateValue, "ImmigrationCandidateForms"));
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
			Long immigrationId, String searchVal, String myImmigration) {
		List<ImmigrationCandidateForms> candidateFormLst = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder query1 = new StringBuilder("");
			if (StringUtils.isNotEmpty(searchVal) && searchVal != null) {
				query1.append(" and ((icf.formName like :searchVal )");
				query1.append(" or (icf.formStatus like :searchVal ))");
			}
			String queryString = "from ImmigrationCandidateForms icf where  icf.immigrationTempId.id= :templateId and icf.immigration.id=:immigrationId and icf.candidate=:candidateId";
			if(myImmigration.equalsIgnoreCase("Y")) {
				queryString = queryString + " and icf.formCategory = :formCategory";
			}
			
			Query query = session.createQuery(queryString + query1).setLong("templateId", templateId)
					.setLong("immigrationId", immigrationId).setLong("candidateId", candidateId);
			if (StringUtils.isNotEmpty(searchVal) && searchVal != null) {
				query.setParameter("searchVal", "%" + searchVal + "%");
			}
			if(myImmigration.equalsIgnoreCase("Y")) {
				query.setParameter("formCategory", "PetitionerCategory");
			}
			candidateFormLst = query.list();
			logger.debug("candidateFormLst size is ---> " + candidateFormLst.size());
		} catch (HibernateException e) {
			logger.error(
					"SQL Exception occured while doing getting ImmigrationCandidateForms records -->" + e.getMessage());
			e.printStackTrace();
		}
		return candidateFormLst;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ImmigrationListViewDTO> getImmigrationsList(ImmigrationListViewDTO immigrationListDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		List<ImmigrationListView> ImmigrationViewLst = null;
		ResponseList<ImmigrationListViewDTO> responseList = new ResponseList<>();
		StringBuilder query1 = new StringBuilder("");
		if ((searchValue.contains("-") || searchValue.contains("/")) && (searchValue.matches("^[0-9 \\-]+$"))) {
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat()).toFormatter();
			LocalDate parsedDate = LocalDate.parse(searchValue, orgFormat);
			DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String newDate = parsedDate.format(f2);
			Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
			java.sql.Date sqlDate1 = new java.sql.Date(searchDate.getTime());
			logger.debug("---test-----" + sqlDate1);
			// listQuery = "from ImmigrationListView immView where
			// immView.openDate =:sqlDate1";
			query1.append(" and ((immView.openDate like '%" + sqlDate1 + "%' )");
			query1.append(" or (immView.lcaStartDate like '%" + sqlDate1 + "%' )");
			query1.append(" or (immView.lcaStartDate like '%" + sqlDate1 + "%' )");
			query1.append(" or (immView.lcaEndDate like '%" + sqlDate1 + "%' ))");
			// ImmigrationViewLst = session.createQuery(listQuery + query1 +
			// sortingQry).setDate("sqlDate1", sqlDate1).list();
		} else if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
			query1.append(" and ((immView.email like '%" + searchValue + "%' )");
			query1.append(" or (immView.firstName like '%" + searchValue + "%' )");
			query1.append(" or (immView.generatedId like '%" + searchValue + "%' )");
			query1.append(" or (immView.currUscisStatus like '%" + searchValue + "%' )");
			query1.append(" or (immView.country like '%" + searchValue + "%' )");
			query1.append(" or (immView.petitionType like '%" + searchValue + "%' )");
			if (orgLocations.getId().equals(immigrationListDTO.getOrgLocationId())) {
				query1.append(" or (immView.vendor like '%" + searchValue + "%' )");
			} else {
				query1.append(" or (immView.client like '%" + searchValue + "%' )");
			}
			query1.append(" or (immView.phone like '%" + searchValue + "%' ))");
		}
		StringBuilder query2 = new StringBuilder("");
		if (filterVal.size() != 0) {
			if (filterVal.get("type") != null) {
				query2.append(" and (immView.petitionTypeId IN ( :petitionTypeList ))");
			}
			if (filterVal.get("status") != null) {
				query2.append(" and (immView.currUscisStatusId IN ( :uscisStatusList ))");
			}
			if (filterVal.get("attorney") != null) {
				if (orgTypeVal.equalsIgnoreCase("Org2")) {
					query1.append(" and (immView.vendor IN ( :attorneyList )) ");
				} else {
					query1.append(" and (immView.client IN ( :attorneyList )) ");
				}
			}
			if (filterVal.get("duration") != null) {
				query2.append(
						" and (year(immView.openDate) <= :highestDuration AND year(immView.openDate) >= :lowestDuration) ");
			}
		}
		// usertList sorting order
		String sortingQry = "";
		if (StringUtils.isNotBlank(immigrationListDTO.getSortColumn())) {
			String sortColumnName = immigrationListDTO.getSortColumn();
			if (sortColumnName.equalsIgnoreCase("orgName")) {
				if (orgLocations.getId().equals(immigrationListDTO.getOrgLocationId())) {
					sortColumnName = "vendor";
				} else {
					sortColumnName = "client";
				}
			}
			sortingQry = " order by immView." + sortColumnName + " " + immigrationListDTO.getSortOrder();
		} else {
			sortingQry = " order by immView.updatedDate desc ";
		}
		String listQuery = "";

		logger.debug(" latest code deployed 6666 --->" + orgLocations.getId());
		listQuery = "from ImmigrationListView immView  where immView.status = 1  and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId) ";
		String listQueryCount = "select count(*) from ImmigrationListView immView where  immView.status = 1  and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId)  ";
		String resultQuery = null;
		String resultCountQuery = null;
		Query hqlQuery1 = null;
		Query hqlQuery = null;
		if (filterVal.size() != 0) {
			resultCountQuery = listQueryCount + query1 + query2;
			resultQuery = listQuery + query1 + query2 + sortingQry;
			hqlQuery = session.createQuery(resultQuery);
			hqlQuery1 = session.createQuery(resultCountQuery);
			if (filterVal.get("type") != null) {
				hqlQuery.setParameterList("petitionTypeList", filterVal.get("type"));
				hqlQuery1.setParameterList("petitionTypeList", filterVal.get("type"));
			}
			if (filterVal.get("status") != null) {
				hqlQuery.setParameterList("uscisStatusList", filterVal.get("status"));
				hqlQuery1.setParameterList("uscisStatusList", filterVal.get("status"));
			}
			if (filterVal.get("attorney") != null) {
				List<String> orgNameList = getOrgNamesById(filterVal.get("attorney"), orgLocations.getId());
				hqlQuery.setParameterList("attorneyList", orgNameList);
				hqlQuery1.setParameterList("attorneyList", orgNameList);
			}
			if (filterVal.get("duration") != null) {
				List<Long> durationDates = new ArrayList<Long>();
				for (Long date : filterVal.get("duration")) {
					durationDates.add(date);
				}
				Integer highestDate = (int) (long) Collections.max(durationDates);
				Integer lowestDate = (int) (long) Collections.min(durationDates);
				hqlQuery.setParameter("highestDuration", highestDate);
				hqlQuery.setParameter("lowestDuration", lowestDate);
				hqlQuery1.setParameter("highestDuration", highestDate);
				hqlQuery1.setParameter("lowestDuration", lowestDate);

			}
			hqlQuery = hqlQuery.setLong("orgLocationId", orgLocations.getId());
			hqlQuery1 = hqlQuery1.setLong("orgLocationId", orgLocations.getId());
		} else {
			resultQuery = listQuery + query1 + sortingQry;
			resultCountQuery = listQueryCount + query1;
			hqlQuery = session.createQuery(resultQuery).setLong("orgLocationId", orgLocations.getId());
			hqlQuery1 = session.createQuery(resultCountQuery).setLong("orgLocationId", orgLocations.getId());
		}
		ImmigrationViewLst = hqlQuery.list();

		countlist = hqlQuery1.setFirstResult(immigrationListDTO.getStart())
				.setMaxResults(immigrationListDTO.getLength()).list();
		int totalCount = 0;
		if (immigrationListDTO != null) {
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
		// opendate

		List<ImmigrationListViewDTO> ImmigarionDTOList = new ArrayList<ImmigrationListViewDTO>();
		if (!ImmigrationViewLst.isEmpty()) {
			for (ImmigrationListView source : ImmigrationViewLst) {
				// logger.debug(" DocsCount"+source.getDocsCount());
				ImmigrationListViewDTO target = new ImmigrationListViewDTO();
				BeanUtils.copyProperties(source, target);
				// logger.debug(" DocsCount
				// target"+target.getDocsCount());
				target.setOpenDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getOpenDate()));
				target.setLcaStartDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaStartDate()));
				target.setLcaEndDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaEndDate()));
				if (orgLocations.getId().equals(source.getOrgLocationId())) {
					target.setOrgName(source.getVendor());
					target.setOrgNameSel(ImmigrationConstants.ORG_NAME_SELECTED_ORG2);
					target.setSpocName(source.getSpocNameVendor());
				} else {
					target.setOrgName(source.getClient());
					target.setOrgNameSel(ImmigrationConstants.ORG_NAME_SELECTED_ORG1);
					target.setSpocName(source.getSpocNameClient());
				}
				ImmigarionDTOList.add(target);
			}
		}
		responseList.setData(ImmigarionDTOList);

		logger.debug(" after dao impl  ---responseList getRecordsTotal is -->" + responseList.getRecordsTotal());
		return responseList;
	}

	@Transactional(rollbackFor = { Exception.class })
	private List<String> getOrgNamesById(Long[] longs, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgRelations orgr  where (orgr.locationId = :currOrgLocationId or orgr.responderLocationId = :currOrgLocationId) and orgr.id IN ( :relationIds ) ";
		List<OrgRelations> orgRelations = session.createQuery(query).setLong("currOrgLocationId", orgLocationId)
				.setParameterList("relationIds", longs).list();
		List<String> orgNames = new ArrayList<String>();
		for (OrgRelations orgRelation : orgRelations) {
			if(orgLocationId.equals(orgRelation.getLocationId().getId())){
			orgNames.add(orgRelation.getResponderLocationId().getOrg().getOrgName());
			}if(orgLocationId.equals(orgRelation.getResponderLocationId().getId())){
			orgNames.add(orgRelation.getLocationId().getOrg().getOrgName());
			}
		}
		return orgNames;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationCandidateDocumentsHistory saveImmigrationCandidateDocumentHistory(
			ImmigrationCandidateDocumentsHistory immigrationCandidateDocumentsHistory) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		try {
			if (immigrationCandidateDocumentsHistory != null) {
				session.saveOrUpdate(immigrationCandidateDocumentsHistory);
			}
			logger.debug("candidateCertifications Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(
					MessageFormat.format(duplicateValue, "immigrationCandidateDocumentsHistory"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		return immigrationCandidateDocumentsHistory;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String updateUscisTypeDropDown(Immigration immigration) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigration != null)
				session.update(immigration);
			logger.debug("updateUscisTypeDropDown updated Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(
					MessageFormat.format(duplicateValue, "immigrationCandidateDocumentsHistory"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
			return "failed";
		}
		return "success";
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationDashboardView getImmigrationDashBoardDetails(OrgLocations orgLocations) {
		ImmigrationDashboardView dashboardObj = new ImmigrationDashboardView();
		
		List<ImmigrationDashboardView> dashboardObjLst= new ArrayList<ImmigrationDashboardView>();
		dashboardObj.setDraftsCount(BigDecimal.ZERO);  // set default values as zero.
		dashboardObj.setReqSentCount(BigDecimal.ZERO);
		dashboardObj.setInProgressCount(BigDecimal.ZERO);
		dashboardObj.setApporovedCount(BigDecimal.ZERO);
		dashboardObj.setRejectedCount(BigDecimal.ZERO);
		dashboardObj.setRfeCount(BigDecimal.ZERO);
		dashboardObj.setDeniedCount(BigDecimal.ZERO);
		dashboardObj.setOthersCount(BigDecimal.ZERO);
		
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "from ImmigrationDashboardView immDview where immDview.orgLocationId = :orgLocationsId or "
					+ "immDview.attorneyLocationId = :attorneyLocationId";
			dashboardObjLst = session.createQuery(queryString).setLong("orgLocationsId", orgLocations.getId()).setLong("attorneyLocationId", orgLocations.getId()).list();
			for(ImmigrationDashboardView Obj: dashboardObjLst) {
				dashboardObj.setDraftsCount(dashboardObj.getDraftsCount().add(Obj.getDraftsCount()));
				dashboardObj.setReqSentCount(dashboardObj.getReqSentCount().add(Obj.getReqSentCount()));
				dashboardObj.setInProgressCount(dashboardObj.getInProgressCount().add(Obj.getInProgressCount()));
				dashboardObj.setApporovedCount(dashboardObj.getApporovedCount().add(Obj.getApporovedCount()));
				dashboardObj.setRejectedCount(dashboardObj.getRejectedCount().add(Obj.getRejectedCount()));
				dashboardObj.setRfeCount(dashboardObj.getRfeCount().add(Obj.getRfeCount()));
				dashboardObj.setDeniedCount(dashboardObj.getDeniedCount().add(Obj.getDeniedCount()));
				dashboardObj.setOthersCount(dashboardObj.getOthersCount().add(Obj.getOthersCount()));
			  }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dashboardObj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryList(Long immigrationId,
			Long candidateId, Long immigrationCanDocid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ImmigrationCandidateDocumentsHistory cdh where  cdh.immigrationId.id =:immigrationId and cdh.immigrationCandidateDocumentId.id =:immigrationCanDocid and cdh.candidateId.id = :candidateId  ORDER BY cdh.id DESC";
		List<ImmigrationCandidateDocumentsHistory> candidateEducation = session.createQuery(query)
				.setLong("immigrationId", immigrationId).setLong("candidateId", candidateId)
				.setLong("immigrationCanDocid", immigrationCanDocid).list();
		return candidateEducation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteDocumentHistoryRecordById(Long id) {
		String deletedFileUrl = "";
		try {
			Session session = this.sessionFactory.getCurrentSession();
			ImmigrationCandidateDocumentsHistory doc = (ImmigrationCandidateDocumentsHistory) session
					.load(ImmigrationCandidateDocumentsHistory.class, id);
			session.delete(doc);
			session.flush();
			deletedFileUrl = doc.getUplodedFileUrl();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		return deletedFileUrl;

	}

	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryListByImmigrationId(
			Long immigrationId, Long docId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<ImmigrationCandidateDocumentsHistory> immigrationCandidateDocumentHistoryList = null;
		if (docId == null) {
			query = "from ImmigrationCandidateDocumentsHistory cdh where  cdh.immigrationId.id =:immigrationId ORDER BY cdh.id DESC";
			immigrationCandidateDocumentHistoryList = session.createQuery(query).setLong("immigrationId", immigrationId)
					.list();
		} else if (docId != null && immigrationId != null) {
			query = "from ImmigrationCandidateDocumentsHistory cdh where  cdh.immigrationCandidateDocumentId =:docId ORDER BY cdh.id DESC";
			immigrationCandidateDocumentHistoryList = session.createQuery(query).setLong("docId", docId).list();
		}

		return immigrationCandidateDocumentHistoryList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteImmigrationDetailsById(Long immigrationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String lcaNumberStatus = "Y";
			LcaHistory lh = getLcaHistoryDetails(immigrationId, Constants.YES);
			if(lh != null && lcaNumberStatus.equals("Y")) {
				 
				   session.delete(lh);
				   session.flush(); 
			}
			
		   CandidateLcaJobDetails candLcaJobDetails = getCandidateLcaJobDetails(immigrationId);
			   if(candLcaJobDetails != null) {
			   session.delete(candLcaJobDetails);
			   session.flush(); 
			   }
			Immigration immigration = session.get(Immigration.class, immigrationId);
			session.delete(immigration);
			session.flush(); 
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationCandidateForms> getImmigrationCandidateFormsByImmigrationId(Long immigrationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ImmigrationCandidateForms icf where icf.immigration.id = :immigrationId ORDER BY icf.id DESC";
		List<ImmigrationCandidateForms> candidateForms = session.createQuery(query)
				.setLong("immigrationId", immigrationId).list();
		return candidateForms;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getTableRecordCountByCandidateId(String entityName, Long candidateId, String condition) {
		Session session = this.sessionFactory.getCurrentSession();
		String queryString = "";
		try {
			if (StringUtils.isNotEmpty(entityName) && candidateId != null && StringUtils.isNotEmpty(condition)) {
				queryString = "select 1 from " + entityName + " " + condition;
				Integer count = (Integer) session.createQuery(queryString).setLong("candidateId", candidateId)
						.setMaxResults(1).uniqueResult();
				return count;
			}
		} catch (Exception e) {
			logger.debug("Exception caught  while getting result of entity " + entityName);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getTableRecordCountByTemplateId(String entityName, Long templateId, String condition) {
		Session session = this.sessionFactory.getCurrentSession();
		String queryString = "";
		try {
			if (StringUtils.isNotEmpty(entityName) && templateId != null && StringUtils.isNotEmpty(condition)) {
				queryString = "select 1 from " + entityName + " " + condition;
				Integer count = (Integer) session.createQuery(queryString).setLong("templateId", templateId)
						.setMaxResults(1).uniqueResult();
				return count;
			}
		} catch (Exception e) {
			logger.debug("Exception caught  while getting result of template entity " + entityName);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration updateImigrationDetatils(Immigration immigration) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(immigration);
			return immigration;
		} catch (Exception e) {
			logger.debug("Exception caught  while updating immigration entity " + immigration.getId());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<MyImmigrationViewDTO> getMyImmigrationsList(MyImmigrationViewDTO immigrationListDTO,
			OrgLocations orgLocations, Long userId) {
		logger.debug("inside getMyImmigrationsList -->");
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		List<MyImmigrationView> ImmigrationViewLst = null;
		ResponseList<MyImmigrationViewDTO> responseList = new ResponseList<>();

		// usertList sorting order
		String sortingQry = "";
		if (StringUtils.isNotBlank(immigrationListDTO.getSortColumn())) {
			sortingQry = " order by immView." + immigrationListDTO.getSortColumn() + " "
					+ immigrationListDTO.getSortOrder();
		} else {
			sortingQry = " order by immView.updatedDate desc ";
		}
		String listQuery = "";
		listQuery = "from MyImmigrationView immView  where immView.status = 1 and UPPER(immView.currUscisStatus)<>'DRAFT'  and immView.candidateUserId =:candidateUserId and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId) ";
		String listQueryCount = "select count(*) from MyImmigrationView immView where  immView.status = 1 and UPPER(immView.currUscisStatus)<>'DRAFT' and immView.candidateUserId =:candidateUserId and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId)  ";
		String resultQuery = null;
		String resultCountQuery = null;
		Query hqlQuery = null;
		Query hqlQuery1 = null;

		resultCountQuery = listQueryCount + sortingQry;
		resultQuery = listQuery + sortingQry;
		hqlQuery = session.createQuery(resultQuery);
		hqlQuery1 = session.createQuery(resultCountQuery);

		hqlQuery = session.createQuery(resultQuery).setLong("orgLocationId", orgLocations.getId())
				.setLong("candidateUserId", userId);
		hqlQuery1 = session.createQuery(resultCountQuery).setLong("orgLocationId", orgLocations.getId())
				.setLong("candidateUserId", userId);
		ImmigrationViewLst = hqlQuery.list();

		countlist = hqlQuery1.setFirstResult(immigrationListDTO.getStart())
				.setMaxResults(immigrationListDTO.getLength()).list();
		int totalCount = 0;
		if (immigrationListDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
				filterCount = totalCount;
			}
		}
		responseList.setRecordsTotal(totalCount);
		responseList.setRecordsFiltered(filterCount);
		// opendate

		List<MyImmigrationViewDTO> ImmigarionDTOList = new ArrayList<MyImmigrationViewDTO>();
		if (!ImmigrationViewLst.isEmpty()) {
			for (MyImmigrationView source : ImmigrationViewLst) {
				MyImmigrationViewDTO target = new MyImmigrationViewDTO();
				BeanUtils.copyProperties(source, target);
				target.setOpenDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getOpenDate()));
				target.setLcaStartDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaStartDate()));
				target.setLcaEndDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaEndDate()));
				ImmigarionDTOList.add(target);
			}
		}
		responseList.setData(ImmigarionDTOList);

		logger.debug(
				" after dao impl  ---getMyImmigrationsList getRecordsTotal is -->" + responseList.getRecordsTotal());
		return responseList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationCandidateDocumentsHistory getImmigrationCandidateDocumentsHistoryById(Long docHistoryId)
			throws Exception {
		ImmigrationCandidateDocumentsHistory immigrationCandidateDocumentsHistory = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			immigrationCandidateDocumentsHistory = (ImmigrationCandidateDocumentsHistory) session
					.get(ImmigrationCandidateDocumentsHistory.class, docHistoryId);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		return immigrationCandidateDocumentsHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveImmigrationTasksAndTasks(List<ImmigrationTasks> immigrationTasksList, List<Tasks> tasksList) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (!immigrationTasksList.isEmpty()) {
				for (ImmigrationTasks immigrationTasks : immigrationTasksList) {
					session.save(immigrationTasks);
				}
			}
			if (!tasksList.isEmpty()) {
				for (Tasks tasks : tasksList) {
					session.save(tasks);
				}
			}
		} catch (Exception exception) {
			logger.error("Exception caught while saving immigration information " + exception.getMessage());
			exception.printStackTrace();
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationActivityHistory storeImmigrationActivities(ImmigrationActivityHistory immigrationActivityHistory) throws Exception {
		logger.debug("Storing ImmigrationActivityHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationActivityHistory != null) {
				session.saveOrUpdate(immigrationActivityHistory);
			}
			logger.debug("ImmigrationActivityHistory Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ImmigrationActivityHistory"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return immigrationActivityHistory;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationActivityHistory> getImmigrationActivitiesList(Long immigrationId, Long orgLocationId) throws Exception
	{
		logger.debug("Fetching Candidate Activities");
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationActivityHistory> immigrationActivityHistory = null;
		if (immigrationId != null) {
			String query = "from ImmigrationActivityHistory iah  where iah.orgLocationId.id =:orgLocationId and iah.immigrationId.id =:immigrationId ORDER BY iah.createdDate DESC";
			immigrationActivityHistory = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("immigrationId", immigrationId).list();
		}
		logger.debug("Successfully Fetched Candidate Activities");
		return immigrationActivityHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getTableRecordCountByImmigrationId(String entityName, Long immigrationId, String condition) {
		Session session = this.sessionFactory.getCurrentSession();
		String queryString = "";
		try {
			if (StringUtils.isNotEmpty(entityName) && immigrationId != null && StringUtils.isNotEmpty(condition)) {
				queryString = "select 1 from " + entityName + " " + condition;
				Integer count = (Integer) session.createQuery(queryString).setLong("immigrationId", immigrationId)
						.setMaxResults(1).uniqueResult();
				return count;
			}
		} catch (Exception e) {
			logger.debug("Exception caught  while getting result of entity " + entityName);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateCandidatePassportVisa(Long candidateId, String idType,String idNumVal) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from CandidatePassportVisa cPassportVisa where cPassportVisa.candidate.id = :candidateId and cPassportVisa.idType = :idType and cPassportVisa.idNumber = :idNumVal";
		List<CandidatePassportVisa> passportVisaList = session.createQuery(query).setLong("candidateId", candidateId).setString("idType", idType).setString("idNumVal", idNumVal).list();
		if (passportVisaList != null && passportVisaList.size() > 0) {
			flag = true;
			return flag;
		}
		else {
			return flag;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidatePassportVisa getCandidatePassportVisaDetailsByIdNumberAndId(String idNumber, String idType,
			Long candidateId,Long id) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from CandidatePassportVisa cPassportVisa where cPassportVisa.idNumber = :idNumber and cPassportVisa.candidate.id = :candidateId and cPassportVisa.idType = :idType and cPassportVisa.id = :id and cPassportVisa.status = 1";
		List<CandidatePassportVisa> passportVisaList =  session.createQuery(query) .setString("idNumber", idNumber).setString("idType", idType).setLong("candidateId", candidateId).setLong("id", id).list();
		CandidatePassportVisa candidatePassportVisa = null;
		if(passportVisaList != null && !passportVisaList.isEmpty())
		{
			candidatePassportVisa = (CandidatePassportVisa) passportVisaList.iterator().next();
		}
		return candidatePassportVisa;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration getImmigrationByUUID(String immigrationUUID) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Immigration immi where immi.immigrationUuid = :immigrationUUID";
		Immigration immigration = (Immigration) session.createQuery(query).setString("immigrationUUID", immigrationUUID).uniqueResult();
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getImmigrationIdByUUID(String immigrationUUID) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "select immi.id from Immigration immi where immi.immigrationUuid = :immigrationUUID";
		Long immigrationId = (Long) session.createQuery(query).setString("immigrationUUID", immigrationUUID).uniqueResult();
		return immigrationId;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidateIdByUUID(String candidateUUID) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "select candidate.id from Candidate candidate where candidate.candidateUUID = :candidateUUID";
		Long candidateId = (Long) session.createQuery(query).setString("candidateUUID", candidateUUID).uniqueResult();
		return candidateId;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ImmigrationListViewDTO> getImmigrationsListByCountsLink(
			ImmigrationListViewDTO immigrationListDTO, String searchValue, OrgLocations orgLocations) throws Exception {
		List<String> paramList = new ArrayList<String>();
		Integer filterCount = 0;
		List<Long> countlist = null;
		
		List<ImmigrationListView> ImmigrationViewLst = null;
		ResponseList<ImmigrationListViewDTO> responseList = new ResponseList<>();
		
		if(StringUtils.isNotBlank(searchValue) && searchValue.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_INPROGRESS_STATUS)){
			paramList.addAll(ImmigrationConstants.IMMIGRATION_INPROGRESS_STATUS_LIST)	;
		}else if(StringUtils.isNotBlank(searchValue) && searchValue.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_DRAFT_STATUS)){
			paramList.add(ImmigrationConstants.IMMIGRATION_DRAFT_STATUS);
		}
		else if(StringUtils.isNotBlank(searchValue) && searchValue.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_APPROVED_STATUS)){
			paramList.add(ImmigrationConstants.IMMIGRATION_APPROVED_STATUS);
		}
		else if(StringUtils.isNotBlank(searchValue) && searchValue.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_OTHERS_STATUS)){
			paramList.addAll(ImmigrationConstants.IMMIGRATION_OTHER_STATUS_LIST);
		}
		else if(StringUtils.isNotBlank(searchValue) && searchValue.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_REQUEST_TO_FILL_STATUS)){
			paramList.add(ImmigrationConstants.IMMIGRATION_REQUEST_TO_FILL_STATUS);
		}
		else if(StringUtils.isNotBlank(searchValue) && searchValue.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_RFE_STATUS)){
			paramList.addAll(ImmigrationConstants.IMMIGRATION_RFE_STATUS_LIST);
		}
		else if(StringUtils.isNotBlank(searchValue) && searchValue.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_DENIED_STATUS)){
			paramList.add(ImmigrationConstants.IMMIGRATION_DENIED_STATUS);
		}
		else if(StringUtils.isNotBlank(searchValue) && searchValue.equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_REJECTED_STATUS)){
			paramList.add(ImmigrationConstants.IMMIGRATION_REJECTED_STATUS);
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		String resultQuery = "from ImmigrationListView immView  where immView.status = 1  and immView.currUscisStatus IN (:paramList) and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId) ";
		String listQueryCount = "select count(*) from ImmigrationListView immView where  immView.status = 1  and immView.currUscisStatus IN (:paramList) and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId)  ";
		Query hqlQuery = session.createQuery(resultQuery);
		Query hqlQuery1 = session.createQuery(listQueryCount);
		hqlQuery = hqlQuery.setLong("orgLocationId", orgLocations.getId());
		hqlQuery1 = hqlQuery1.setLong("orgLocationId", orgLocations.getId());
		hqlQuery = hqlQuery.setParameterList("paramList", paramList);
		hqlQuery1 = hqlQuery1.setParameterList("paramList", paramList);
		
		ImmigrationViewLst = hqlQuery.list();

		countlist = hqlQuery1.setFirstResult(immigrationListDTO.getStart())
				.setMaxResults(immigrationListDTO.getLength()).list();
		int totalCount = 0;
		if (immigrationListDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
				filterCount = totalCount;
			}
		}
		responseList.setRecordsTotal(totalCount);
		if (resultQuery.toString() != null && !resultQuery.toString().isEmpty()) {
			responseList.setRecordsFiltered(filterCount);
		} else {
			responseList.setRecordsFiltered(totalCount);
		}
		
		List<ImmigrationListViewDTO> ImmigarionDTOList = new ArrayList<ImmigrationListViewDTO>();
		if (!ImmigrationViewLst.isEmpty()) {
			for (ImmigrationListView source : ImmigrationViewLst) {
			
				ImmigrationListViewDTO target = new ImmigrationListViewDTO();
				BeanUtils.copyProperties(source, target);
				target.setOpenDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getOpenDate()));
				target.setLcaStartDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaStartDate()));
				target.setLcaEndDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaEndDate()));
				if (orgLocations.getId().equals(source.getOrgLocationId())) {
					target.setOrgName(source.getVendor());
					target.setOrgNameSel(ImmigrationConstants.ORG_NAME_SELECTED_ORG2);
					target.setSpocName(source.getSpocNameVendor());
				} else {
					target.setOrgName(source.getClient());
					target.setOrgNameSel(ImmigrationConstants.ORG_NAME_SELECTED_ORG1);
					target.setSpocName(source.getSpocNameClient());
				}
				ImmigarionDTOList.add(target);
			}
		}
		responseList.setData(ImmigarionDTOList);
		return responseList;
			}
	@Override
	@Transactional(rollbackFor = { Exception.class })		
	public ImmigrationCandidateForms getImmigrationCandidateFormsDetailsByFormNameAndId(String formName,
			 Long candidateId,Long immigrationId, Long id) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from ImmigrationCandidateForms immCandidateForms where immCandidateForms.formName = :formName and immCandidateForms.candidate.id = :candidateId and immCandidateForms.immigration.id = :immigrationId and immCandidateForms.id = :id and immCandidateForms.status = 1";
		List<ImmigrationCandidateForms> immigrationCandidateFormsList =  session.createQuery(query) .setString("formName", formName).setLong("candidateId", candidateId).setLong("immigrationId", immigrationId).setLong("id", id).list();
		ImmigrationCandidateForms immigrationCandidateForms = null;
		if(immigrationCandidateFormsList != null && !immigrationCandidateFormsList.isEmpty())
		{
			immigrationCandidateForms = (ImmigrationCandidateForms) immigrationCandidateFormsList.iterator().next();
		}
		return immigrationCandidateForms;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateImmigrationCandidateForms(String formName, Long candidateId, Long immigrationId)
			throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from ImmigrationCandidateForms immCandidateForms where immCandidateForms.formName = :formName and immCandidateForms.candidate.id = :candidateId and immCandidateForms.immigration.id = :immigrationId";
		List<ImmigrationCandidateForms> immigrationCandidateFormsList = session.createQuery(query).setString("formName", formName).setLong("candidateId", candidateId).setLong("immigrationId", immigrationId).list();
		if (immigrationCandidateFormsList != null && immigrationCandidateFormsList.size() > 0) {
			flag = true;
			return flag;
		}
		else {
			return flag;
		}
			}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationListExportView> getImmigrationListExportDetails(OrgLocations orgLocations) {
		Session session = this.sessionFactory.getCurrentSession();
		String listQuery = "from ImmigrationListExportView immView  where immView.status = 1  and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId) ";
		List<ImmigrationListExportView> listExportViews = session.createQuery(listQuery).setParameter("orgLocationId", orgLocations.getId()).list();
		System.out.println(listExportViews.size());
		return listExportViews;
	}

	//old method
	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getTemplateDetailsByNameAndOrgLoacationId(String templateName, OrgLocations orgLocations) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select temp.id from ImmigrationTemplate temp where temp.name = :templateName and temp.orgLocations.id= :orgLocationId";
		Long tempId = (Long) session.createQuery(query).setString("templateName", templateName).setLong("orgLocationId", orgLocations.getId()).uniqueResult();
		return tempId;
	}*/
	
	// new method
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getTemplateDetailsByNameAndOrgLoacationId(String templateName, OrgLocations orgLocations) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select temp.id from ImmigrationTemplate temp where temp.name = :templateName and temp.orgLocations.id= :orgLocationId";
		List<Long> tempId = (List<Long>) session.createQuery(query).setString("templateName", templateName).setLong("orgLocationId", orgLocations.getId()).list();
		if (tempId.size() > 0) {
			return tempId.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Immigration> getImmigrationsByOrgId(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String listQuery = "from Immigration immi  where immi.status = 1  and (immi.orgLocationId.id = :id or immi.attorneyOrgLocationId.id = :id)";
		List<Immigration> immigrations = session.createQuery(listQuery).setParameter("id", id).list();
		return immigrations;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Lca saveLcaDetails(Lca lca,List<LcaPlaceOfEployment> lcaPlaceOfEploymentList,List<LcaDocumentHistory> lcaDocumentList) throws Exception {
		
		logger.debug("saveLcaDetails starts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			
			if (lca != null) {
				session.saveOrUpdate(lca);
			}
			
		if (lcaPlaceOfEploymentList != null && lcaPlaceOfEploymentList.size() != 0) { // saving
			
				for (LcaPlaceOfEployment lcaPlaceOfEployment : lcaPlaceOfEploymentList) {
				session.saveOrUpdate(lcaPlaceOfEployment);
				logger.debug("The lcaPlaceOfEployment id is: +++++++++++++++ :" + lcaPlaceOfEployment.getId());
				}
			
           }
		
        if (lcaDocumentList != null && lcaDocumentList.size() != 0) { // saving
			
				for (LcaDocumentHistory lcaDocumentHistory : lcaDocumentList) {
				session.saveOrUpdate(lcaDocumentHistory);
				logger.debug("The LcaDocumentHistory id is: +++++++++++++++ :" + lcaDocumentHistory.getId());
				}
			
           }
			
		} catch (Exception exception) {
			logger.error("Exception caught while saving lca information " + exception.getMessage());
			exception.printStackTrace();
		}
		return lca;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Lca getLcaById(Long id) {
		Lca lca = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			lca = (Lca) session.get(Lca.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return lca;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaVisaClarifications saveLcaVisaClarifications(LcaVisaClarifications lcaVisaClarifications)
			throws Exception {
		logger.debug("saveLcaVisaClarifications starts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			
			if (lcaVisaClarifications != null) {
				session.saveOrUpdate(lcaVisaClarifications);
			}
			
		} catch (Exception exception) {
			logger.error("Exception caught while saving LcaVisaClarifications information " + exception.getMessage());
			exception.printStackTrace();
		}
		return lcaVisaClarifications;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaPlaceOfEployment saveLcaPlaceOfEployment(LcaPlaceOfEployment lcaPlaceOfEployment) throws Exception {
		logger.debug("saveLcaPlaceOfEployment starts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			
			if (lcaPlaceOfEployment != null) {
				session.saveOrUpdate(lcaPlaceOfEployment);
			}
			
		} catch (Exception exception) {
			logger.error("Exception caught while saving LcaPlaceOfEployment information " + exception.getMessage());
			exception.printStackTrace();
		}
		return lcaPlaceOfEployment;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaDocumentHistory saveLcaDocumentHistory(LcaDocumentHistory lcaDocumentHistory) throws Exception {
		
		logger.debug("saveLcaDocumentHistory starts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			
			if (lcaDocumentHistory != null) {
				session.saveOrUpdate(lcaDocumentHistory);
			}
			
		} catch (Exception exception) {
			logger.error("Exception caught while saving LcaPlaceOfEployment information " + exception.getMessage());
			exception.printStackTrace();
		}
		return lcaDocumentHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LcaDocumentHistory> getLcaDocumentHistoryListById(Long lcaId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<LcaDocumentHistory> lcaDocumentList = new ArrayList<LcaDocumentHistory>();
			String queryString = "from LcaDocumentHistory ldh where  ldh.lcaId.id=:lcaId order by ldh.updatedDate desc";
			lcaDocumentList = session.createQuery(queryString).setLong("lcaId", lcaId).list();
		
		return lcaDocumentList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LcaPlaceOfEployment> getLcaPlaceOfEploymentListById(Long lcaId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<LcaPlaceOfEployment> lcaPlaceOfEploymentList = new ArrayList<LcaPlaceOfEployment>();
			String queryString = "from LcaPlaceOfEployment lpoe where lpoe.lcaId.id=:lcaId order by lpoe.updatedDate desc";
			lcaPlaceOfEploymentList = session.createQuery(queryString).setLong("lcaId", lcaId).list();
		
		return lcaPlaceOfEploymentList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaPlaceOfEployment getLcaPlaceOfEploymentDetailsById(Long id) {
		
		logger.debug("getLcaPlaceOfEploymentDetailsById id ---> " + id);
		LcaPlaceOfEployment lcaPlaceOfEploymentObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			lcaPlaceOfEploymentObj = (LcaPlaceOfEployment) session.get(LcaPlaceOfEployment.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return lcaPlaceOfEploymentObj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaDocumentHistory getLcaDocumentHistoryDetailsById(Long docId,Long lcaId) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LcaDocumentHistory  ldh left join fetch ldh.lcaId  where ldh.id = :docId and ldh.lcaId.id = :lcaId";
		List<LcaDocumentHistory> docList = session.createQuery(query.toString()).setLong("docId", docId).setLong("lcaId", lcaId).list();
		LcaDocumentHistory docs = null;
		if (docList != null && docList.size() > 0) {
			docs = (LcaDocumentHistory) docList.iterator().next();
		}
		return docs;
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<LcaListViewDTO> getLcaList(LcaListViewDTO lcaListViewDTO,String status,String lcaStatus, String searchValue,
			OrgLocations orgLocations, Map<String, Long[]> filterVal,String filterByDays) throws Exception {
		
		
		    Session session = this.sessionFactory.getCurrentSession();
	        List<Long> countlist = null;
	        Integer filterCount = 0;
	        List<LcaListView> lcaViewList = null;
	        ResponseList<LcaListViewDTO> responseList = new ResponseList<>();
            String subQuery1 ="";
            String subQuery2 = ""; 
            String subQuery3 = ""; 
            
            StringBuilder query1 = new StringBuilder("");
            if (!searchValue.toLowerCase().contains("I-".toLowerCase()) && (searchValue.contains("-") || searchValue.contains("/"))) {
    			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat())
    					.toFormatter();
    			LocalDate parsedDate = LocalDate.parse(searchValue, orgFormat);
    			DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    			String newDate = parsedDate.format(f2);
    			Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
    			java.sql.Date sqlDate1 = new java.sql.Date(searchDate.getTime());
    			logger.debug("---test-----" + sqlDate1);
    			query1.append(" and ((lcaView.startDate like '%" + sqlDate1 + "%' )");
    			query1.append(" or (lcaView.endDate like '%" + sqlDate1 + "%' ))");
    		} else if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
    			query1.append(" and ((lcaView.lcaNo like '%" + searchValue + "%' )");
    			query1.append(" or (lcaView.lcaTitle like '%" + searchValue + "%' )");
    			query1.append(" or (lcaView.lcaStatus like '%" + searchValue + "%' )");
    			query1.append(" or (lcaView.location like '%" + searchValue + "%' )");
    			query1.append(" or (lcaView.prevailingWage like '%" + searchValue + "%' )");
    			query1.append(" or (lcaView.status like '%" + searchValue + "%' ))");
    		}
            
            String sortingQry = "";
    		if (StringUtils.isNotBlank(lcaListViewDTO.getSortColumn())) {
    			String sortColumnName = lcaListViewDTO.getSortColumn();
    			sortingQry = "order by lcaView." + sortColumnName + " " + lcaListViewDTO.getSortOrder();
    		} 
    		
			if(status != null && !status.isEmpty()){
				String[] statusFilter = status.split(",");
				
			/*	if (Arrays.asList(statusFilter).contains("all")) {
					subQuery1 = subQuery1 + "lcaView.status = '1' or lcaView.status = '0' ";
				}*/
				
				if (Arrays.asList(statusFilter).contains("Active")) {
					subQuery1 = subQuery1 + "lcaView.status = '1' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("InActive")) {
					subQuery1 = subQuery1 + "lcaView.status = '0' or ";
				}
				
				subQuery1 = "and (" + subQuery1.substring(0, subQuery1.length() - 4) + ")";
			}
	        
			if(lcaStatus != null && !lcaStatus.isEmpty()){
				String[] statusFilter = lcaStatus.split(",");
				if (Arrays.asList(statusFilter).contains("Initiated")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'Initiated' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("In progress")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'In progress' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("Certified")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'Certified' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("Denied")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'Denied' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("Withdrawn")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'Withdrawn' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("Voided")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'Voided' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("Invalidated")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'Invalidated' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("Rejected")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'Rejected' or ";
				}
				
				if (Arrays.asList(statusFilter).contains("Expired")) {
					subQuery2 = subQuery2 + "lcaView.lcaStatus = 'Expired' or ";
				}
				
				
				subQuery2 = "and (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
			}
			
			Date daysFilter = null;
			boolean isDateFileterFound= false;
			if(StringUtils.isNotBlank(filterByDays) && !filterByDays.equalsIgnoreCase("All")) {
			
			isDateFileterFound = true;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt(filterByDays));
			daysFilter = cal.getTime();
			subQuery3 = "and lcaView.endDate <=:daysFilter"; 
			
			}
			
			String query = "from LcaListView lcaView  where  lcaView.orgLocationId =:orgLocationId"+query1+"  "+subQuery1+"  "+subQuery2+"  "+subQuery3+" "+sortingQry+"";
			
			String listQueryCount = "select count(*) from LcaListView lcaView  where  lcaView.orgLocationId =:orgLocationId"+query1+"  "+subQuery1+"  "+subQuery2+"  "+subQuery3+" "+sortingQry+"";
			
			System.out.println(listQueryCount);
			Query hqlQuery1 = session.createQuery(listQueryCount);
			hqlQuery1 = hqlQuery1.setLong("orgLocationId", orgLocations.getId());
			
			// Filter By Date
			if(isDateFileterFound) {
				hqlQuery1 = hqlQuery1.setLong("orgLocationId", orgLocations.getId()).setDate("daysFilter", daysFilter);
			}
			
			Query hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId());
			
			// Filter By Date
			if(isDateFileterFound) {
				hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setDate("daysFilter", daysFilter);
			}
			
	        lcaViewList = hqlQuery.setFirstResult(lcaListViewDTO.getStart()).setMaxResults(lcaListViewDTO.getLength()).list();
	        
	        countlist = hqlQuery1.list();
			int totalCount = 0;
			if (lcaListViewDTO != null) {
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
	       
	        List<LcaListViewDTO> tempalteDTOList = new ArrayList<LcaListViewDTO>();
	        for (LcaListView source : lcaViewList) {
	        	
	        	LcaListViewDTO target = new LcaListViewDTO();
	        	target.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),source.getStartDate()));
	        	target.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),source.getEndDate()));
	        	
	        	target.setLocationsList(Arrays.asList(source.getLocation().split("\n")));
	        	target.setLocation(source.getLocation().replaceAll("", ""));
	        	target.setLcaUuid(source.getLcaUuid());
	        	if(source.getStatus() == 1) {
	        		target.setStatus(1);
	        	}else {
	        		target.setStatus(0);
	        	}
	            BeanUtils.copyProperties(source, target);
	            tempalteDTOList.add(target);
	        }
	        responseList.setData(tempalteDTOList);
	        return responseList;
		
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getLcaIdByUUID(String lcaUuid) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "select lca.id from Lca lca where lca.lcaUuid = :lcaUuid";
		Long lcaId = (Long) session.createQuery(query).setString("lcaUuid", lcaUuid).uniqueResult();
		return lcaId;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteLcaDetailsById(Long lcaId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Lca lca = session.get(Lca.class, lcaId);
			lca.setStatus(0);
			session.saveOrUpdate(lca);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Map<String, String>> getSocCodeWithSOCOccupationTitleList(String name,String socType) {
		
		Session session = this.sessionFactory.getCurrentSession(); // below query is used for auto populate in UI.
		String query = null;
		if(StringUtils.isNotEmpty(socType) && socType.equalsIgnoreCase("socTitle")){
			
			query = "SELECT SOC_TITLE as label, CONVERT(ID, CHAR) as value from OES_SOC_OCCS where SOC_TITLE like :name"; 
			return session.createSQLQuery(query).setString("name", name+"%").setMaxResults(10).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
		   
		}else {
			 query = "SELECT SOC_CODE as label, CONVERT(ID, CHAR) as value from OES_SOC_OCCS where SOC_CODE like :name"; 
			    return session.createSQLQuery(query).setString("name", name+"%").setMaxResults(10).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		
	}
	
	
	//LCA Number An Previous LCA Number Auto Completes In Petition Details Jsp.
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Map<String, String>> getLcaNumbersList(String lcaNo,String orgLocationId,String statusLca,String candidateId) {
		
 			Session session = this.sessionFactory.getCurrentSession(); // below query is used for auto populate in UI.
			String query = null;
			if(statusLca.equals("1")) {
				query = "SELECT LCA_NO as label, CONVERT(ID, CHAR) as value from LCA lca where (lca.ID NOT IN (SELECT LCA_ID FROM LCA_HISTORY where CANDIDATE_ID =:candidateId)) and lca.LCA_NO like :lcaNo and lca.ORG_LOCATION_ID =:orgLocationId  and lca.END_DATE >= CURDATE() and lca.status=1"; 
				return session.createSQLQuery(query).setString("lcaNo", lcaNo+"%").setString("orgLocationId", orgLocationId).setString("candidateId", candidateId).setMaxResults(10).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	        }else {
	        	query = "SELECT LCA_NO as label, CONVERT(ID, CHAR) as value from LCA lca where (lca.ID NOT IN (SELECT LCA_ID FROM LCA_HISTORY where CANDIDATE_ID =:candidateId)) and lca.LCA_NO like :lcaNo and lca.ORG_LOCATION_ID =:orgLocationId and lca.END_DATE < CURDATE()"; 
				return session.createSQLQuery(query).setString("lcaNo", lcaNo+"%").setString("orgLocationId", orgLocationId).setString("candidateId", candidateId).setMaxResults(10).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	        }
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaVisaClarifications getLcaVisaClarificationsByIDAndLcaId(Long lcaId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LcaVisaClarifications lcaVisaClfs left join fetch lcaVisaClfs.lcaId  where lcaVisaClfs.lcaId.id =:lcaId  ORDER BY lcaVisaClfs.updatedDate DESC";  
		List<LcaVisaClarifications> lcaVisaClfsList = session.createQuery(query).setLong("lcaId", lcaId).list();
		LcaVisaClarifications lcaVisaClfs = null;
		if(lcaVisaClfsList != null && !lcaVisaClfsList.isEmpty()){
			lcaVisaClfs = lcaVisaClfsList.iterator().next();
		}
		return lcaVisaClfs;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LcaPlaceOfEployment> getLcaPlaceOfEploymentListByIdAndLcaID(Long lcaId) {
		
		logger.debug("Getting  LcaPlaceOfEploymentDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		List<LcaPlaceOfEployment> lcaPlaceOfEploymentList = new ArrayList<LcaPlaceOfEployment>();

	    String queryStringCand = null;
		queryStringCand = "from LcaPlaceOfEployment cr where cr.lcaId=:lcaId";
		lcaPlaceOfEploymentList = session.createQuery(queryStringCand).setLong("lcaId", lcaId).list();
		
		return lcaPlaceOfEploymentList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String reActiveLcaById(Long lcaId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Lca lca = session.get(Lca.class, lcaId);
			lca.setStatus(1);
			session.saveOrUpdate(lca);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		
		return "success";
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getOesSocCodeById(Long id, String socType) {
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = null;
		if(StringUtils.isNotEmpty(socType) && socType.equalsIgnoreCase("socTitle")){
			query = "SELECT SOC_TITLE from OES_SOC_OCCS where id = :id"; 
			return (String) session.createSQLQuery(query).setLong("id", id).uniqueResult();
		}else {
			query = "SELECT SOC_CODE from OES_SOC_OCCS where id = :id"; 
			return (String) session.createSQLQuery(query).setLong("id", id).uniqueResult();
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LcaListExportView> getLcaListExportDetails(OrgLocations orgLocations) {
		Session session = this.sessionFactory.getCurrentSession();
		String listQuery = "from LcaListExportView lcaExportView  where  lcaExportView.orgLocationId=:orgLocationId";
		List<LcaListExportView> listExportViews = session.createQuery(listQuery).setParameter("orgLocationId", orgLocations.getId()).list();
		System.out.println(listExportViews.size());
		return listExportViews;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Lca> getAllLcaList(Date currentDate) {
		Session session = this.sessionFactory.getCurrentSession();
		String listQuery = "from Lca lca where  lca.endDate<:currentDate order by lca.createdBy.id";
		List<Lca> lcaList = session.createQuery(listQuery).setDate("currentDate",currentDate).list();
		return lcaList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String updateLcaStatusWithExpired(Long lcaId) {
		Session session = this.sessionFactory.getCurrentSession();
		Lca lca = session.get(Lca.class, lcaId);
		lca.setLcaStatus(ImmigrationConstants.LCA_STATUS_EXPIRED);
		session.saveOrUpdate(lca);
		session.flush();
		return "success";
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Lca getLcaDetailsByLcaNoAndId(String lcaNo, Long lcaId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Lca lca where lca.lcaNo = :lcaNo and lca.id=:lcaId and lca.status = 1";
		List<Lca> lcaNumbersList =  session.createQuery(query) .setString("lcaNo", lcaNo).setLong("lcaId", lcaId).list();
		Lca lca = null;
		if(lcaNumbersList != null && !lcaNumbersList.isEmpty())
		{
			lca = (Lca) lcaNumbersList.iterator().next();
		}
		return lca;
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateLcaNumber(String lcaNo, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from Lca lca where lca.lcaNo = :lcaNo and lca.orgLocationId=:orgLocationId";
		List<Lca> lcaNumbersList = session.createQuery(query).setString("lcaNo", lcaNo).setLong("orgLocationId", orgLocationId).list();
		if (lcaNumbersList != null && lcaNumbersList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Lca getLcaDetailsByLcaTitleAndId(String lcaTitle, Long lcaId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Lca lca where lca.lcaTitle = :lcaTitle and lca.id=:lcaId and lca.status = 1";
		List<Lca> lcaNumbersList =  session.createQuery(query) .setString("lcaTitle", lcaTitle).setLong("lcaId", lcaId).list();
		Lca lca = null;
		if(lcaNumbersList != null && !lcaNumbersList.isEmpty())
		{
			lca = (Lca) lcaNumbersList.iterator().next();
		}
		return lca;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateLcaTitle(String lcaTitle, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from Lca lca where lca.lcaTitle = :lcaTitle and lca.orgLocationId=:orgLocationId";
		List<Lca> lcaNumbersList = session.createQuery(query).setString("lcaTitle", lcaTitle).setLong("orgLocationId", orgLocationId).list();
		if (lcaNumbersList != null && lcaNumbersList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaHistory saveLcaHistory(LcaHistory lcaHistory) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(lcaHistory != null) {
				session.saveOrUpdate(lcaHistory);
			}
		} catch (Exception exception) {
			logger.error("Exception caught while  saveLcaHistory information " + exception.getMessage());
			exception.printStackTrace();
		}
		return lcaHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Lca getLcaByLcaId(Long lcaId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Lca lca where lca.id =:lcaId";
		List<Lca> lcaList = session.createQuery(query).setLong("lcaId", lcaId).list();
		Lca lca = null;
		if(lcaList != null && !lcaList.isEmpty())
		{
			
			lca = (Lca) lcaList.iterator().next();
		}
		return lca;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaHistory getLcaHistoryDetails(Long immigrationId, String yes) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LcaHistory lh  left join fetch lh.lcaId where lh.immigrationId.id = :immigrationId and lh.isCurrent = :yes";
		LcaHistory lh = (LcaHistory) session.createQuery(query).setLong("immigrationId", immigrationId).setString("yes", yes).uniqueResult();
		return lh;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LcaDTO> getLcaHistoryDetailsInfo(Long immigrationId) {
			
		    List<LcaDTO> lcaList = new ArrayList<LcaDTO>();
			logger.debug("Getting  LcaHistoryDetails --->  ");
			Session session = this.sessionFactory.getCurrentSession();
		
			String queryString = "SELECT " + 
					" l.LCA_NO as lcaNo, " + 
					" l.LCA_TITLE as lcaTitle, " + 
					" lpe.LOCATION as location, " + 
					" l.PREVAILING_WAGE as prevailingWage, " + 
					" l.LCA_STATUS as lcaStatus, " + 
					" CAST(l.NO_OF_POSITIONS AS CHAR(5))as noOfPositions, " + 
					" CAST(l.STATUS AS CHAR(1))as status, " + 
					" DATE_FORMAT(l.START_DATE, '%Y-%m-%d') as startDate, " + 
					" DATE_FORMAT(l.END_DATE, '%Y-%m-%d') as endDate, " + 
					
					" lvc.NEW_EMPLOYMENT as newEmployment, " + 
                    " CAST(lvc.NEW_EMPLOYMENT_COUNT AS CHAR(5))as newEmploymentCount " + 
					
					" FROM " + 
					" LCA l, " + 
					" LCA_HISTORY lh, " + 
					" LCA_PLACE_OF_EMPLOYMENT lpe, " + 
					" LCA_VISA_CLARIFICATIONS lvc" +
					" WHERE " + 
					" l.id = lh.LCA_ID AND l.id = lpe.LCA_ID AND l.id = lvc.LCA_ID" + 
					" AND lh.IMMIGRATION_ID = :immigrationId";
			lcaList = session.createSQLQuery(queryString).setParameter("immigrationId", immigrationId).setResultTransformer(Transformers.aliasToBean(LcaDTO.class)).list();
		    return lcaList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LcaDetailsDTO> getEmployeeByLcaId(Long lcaId,OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<LcaDetailsDTO> empList = new ArrayList<LcaDetailsDTO>();
		String queryString = "SELECT " + 
				"IMM.GENERATED_ID AS petitionId, " + 
				"IMM.IMMIGRATION_UUID AS immigrationUUID,"+
				                                 "(SELECT " + 
			                      	                       "CONCAT(FIRST_NAME, ' ', IFNULL(LAST_NAME, '')) " + 
				                                           "FROM " + 
				                                               " CANDIDATE " + 
				                                                          " WHERE " + 
				                                                               "ID = LH.CANDIDATE_ID) AS empName, " + 
				                                                               "(SELECT "+
				                                                                    "CANDIDATE_UUID "+
				                                                                  "FROM "+
				                                                                       "CANDIDATE " + 
				                                                                   "WHERE "+
				                                                                       "CANDIDATE_UUID = LH.CANDIDATE_ID) AS candidateUUID, "+        
				                                       "(SELECT " + 
				                                                    "NAME " + 
				                                                         "FROM " + 
				                                                             "LOOKUP_DATA " + 
				                                                                       "WHERE " + 
				                                                                         "ID = IMM.PETITION_TYPE) AS petitionType, " + 
				                                        "(SELECT " + 
				                                                      "NAME " + 
				                                                              "FROM " + 
				                                                                  "LOOKUP_DATA " + 
				                                                                                "WHERE " + 
				                                                                                         "ID = IMM.CURRENT_USCIS_STATUS) AS status, " + 
				                                                                                         " DATE_FORMAT(IMM.OPEN_DATE ,'%Y-%m-%d') AS filedOn, " + 
				                                      "(SELECT " + 
				                                             "O.ORG_NAME " + 
				                                                         "FROM " + 
				                                                             "ORG_LOCATIONS OL, " + 
				                                                                                 "ORG O " + 
				                                                                                      "WHERE " + 
				                                                                                         "OL.ORG_ID = O.ID " + 
				                                                                                              "AND OL.ID = IMM.ORG_LOCATION_ID) AS client, " + 
				                                       "(SELECT " + 
				                                                    "O.ORG_NAME " + 
				                                                                  "FROM " + 
				                                                                            "ORG_LOCATIONS OL, " + 
				                                                                                 " ORG O " + 
				                                                                                     "WHERE " + 
				                                                                                        " OL.ORG_ID = O.ID " + 
				                                                                                           " AND OL.ID = IMM.ATTORNEY_LOCATION_ID) AS vendor, " + 
				                                         "(SELECT " +
			                                                         "CONVERT(ORG_LOCATION_ID,CHAR) "+
			                                                                                    "FROM "+
			                                                                                          "LCA "+
			                                                                                               "WHERE "+
			                                                                                                   "ID= LH.LCA_ID) AS orgLocationId, "+
			                                           "(SELECT  "+
			                                             "IT.UUID "+
			                                                   "FROM "+
			                                                         "IMMIGRATION_TEMPLATE IT "+
			                                                                  "WHERE "+
			                                                                        "IT.ID = IMM.IMMIGRATION_TEMPLATE_ID) AS immiTemplateUUID "+
				                                                  "FROM " + 
				                                                          "IMMIGRATION IMM, " + 
				                                                              "LCA_HISTORY LH " + 
				                                                                   "WHERE " + 
				                                                                        "IMM.ID = LH.IMMIGRATION_ID " +
				                                                                            "AND LH.LCA_ID =:lcaId";
		empList = session.createSQLQuery(queryString).setParameter("lcaId", lcaId).setResultTransformer(Transformers.aliasToBean(LcaDetailsDTO.class)).list();
		for(LcaDetailsDTO obj : empList) {
			if(orgLocations.getId().equals(obj.getOrgLocationId())) {
			obj.setAttorneyName(obj.getVendor());
			}else {
				obj.setAttorneyName(obj.getClient());
			}
		}
		
		return empList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteLcaPlaceOfEmpmntDetails(Long remaininglpoeIds[]) {
		    logger.debug("Delete LcaPlaceOfEployment ids-->");
	        Session session = this.sessionFactory.getCurrentSession();
	        Query query = session.createQuery("delete from LcaPlaceOfEployment lpoe where lpoe.id in (:plsempmntId)").setParameterList("plsempmntId", remaininglpoeIds);
	        int count = query.executeUpdate();
	        logger.debug("Number of records deleted-->" + count);
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateLcaJobDetails saveCandidateLcaJobDetails(CandidateLcaJobDetails candidateLcaJobDetails) {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(candidateLcaJobDetails != null) {
				session.saveOrUpdate(candidateLcaJobDetails);
			}
		} catch (Exception exception) {
			logger.error("Exception caught while  saveCandidateLcaJobDetails information " + exception.getMessage());
			exception.printStackTrace();
		}
		return candidateLcaJobDetails;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaHistory getLcaHistoryByStatus(Long immigrationId,Long candidateId, String isCurrent) {

		Session session = this.sessionFactory.getCurrentSession();
		LcaHistory lcaHistory = null;
		try {
			if (immigrationId != null) {
				logger.debug(" before query execute to get lcaHistory " + immigrationId);
				String queryString = "from LcaHistory lh  where lh.isCurrent =:isCurrent and lh.immigrationId.id=:immigrationId and lh.candidate.id = :candidateId";
				lcaHistory = (LcaHistory) session.createQuery(queryString).setLong("immigrationId", immigrationId).setLong("candidateId", candidateId)
																		  .setString("isCurrent", isCurrent).uniqueResult();
			}
		} catch (Exception exception) {
			logger.error("Exception caught while  lcaHistory information " + exception.getMessage());
			exception.printStackTrace();
		}
		return lcaHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteLcaDocumentsDetails(Long[] remaininglcaDocIds) {
		    logger.debug("Delete LcaDocumentHistory ids-->");
	        Session session = this.sessionFactory.getCurrentSession();
	        Query query = session.createQuery("delete from LcaDocumentHistory ldoc where ldoc.id in (:docId)").setParameterList("docId", remaininglcaDocIds);
	        int count = query.executeUpdate();
	        logger.debug("Number of records deleted-->" + count);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int updateLcaHistoryStatusByLcaNumber(Long lhId, Long immigrationId, Long candidateId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		int result=0;
		try {
			if (immigrationId != null) {
				logger.debug(" update lcaHistory " + immigrationId);
				String queryString = "UPDATE LcaHistory lh  SET lh.isCurrent ='N' where lh.immigrationId.id=:immigrationId and lh.candidate.id = :candidateId and lh.id<:lhId order by date(creationDate) desc";
				 result=session.createQuery(queryString).setLong("immigrationId", immigrationId).setLong("candidateId", candidateId).setLong("lhId", lhId).executeUpdate();
			   
			}
		} catch (Exception exception) {
			logger.error("Exception caught while  lcaHistory information " + exception.getMessage());
			exception.printStackTrace();
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LcaVisaClarifications getLcaVisaClarificationsDetailsById(Long visaClsId, Long lcaId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LcaVisaClarifications lcaVisaClfs left join fetch lcaVisaClfs.lcaId  where lcaVisaClfs.id = :visaClsId and lcaVisaClfs.lcaId.id = :lcaId";
		List<LcaVisaClarifications> visaClarificatinsList = session.createQuery(query.toString()).setLong("visaClsId", visaClsId).setLong("lcaId", lcaId).list();
		LcaVisaClarifications visaClarifications = null;
		if (visaClarificatinsList != null && visaClarificatinsList.size() > 0) {
			visaClarifications = (LcaVisaClarifications) visaClarificatinsList.iterator().next();
		}
		return visaClarifications;
	}
	
}