package com.bis.sourcelead.immigration.dao.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationDashboardView;
import com.bis.sourcelead.entity.ImmigrationListView;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.MyImmigrationView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;
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
	private UserRoleDao userRoleDao;

	@Autowired
	CandidateDao candidateDao;

	@Autowired
	private ImmigrationTemplateDao immigrationTemplateDao;

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

	private static final Logger logger = LoggerFactory.getLogger(ImmigrationCommonDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations) {
		logger.debug("Saving Template Details-->");
		List<ImmigrationTemplate> templateLst = new ArrayList<ImmigrationTemplate>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			System.out.println(" before query execute templateLst size -->" + templateLst.size());
			String queryString = "from ImmigrationTemplate it where it.status = 1 and it.orgLocations.id =:orgLocationId";
			templateLst = session.createQuery(queryString).setLong("orgLocationId", orgLocations.getId()).list();
			System.out.println("tempalte Details loaded Successfully templateLst size -->" + templateLst.size());

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
					System.out.println("The residence id is: +++++++++++++++ :" + residence.getId());
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateLcaI94Summary getCandidateLCADetails(Long candidateId, String yes) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateLcaI94Summary clis left join fetch clis.requirementId where clis.candidateId.id = :candidateId and clis.isCurrent = :yes";
		CandidateLcaI94Summary lcaI94Summary = (CandidateLcaI94Summary) session.createQuery(query)
				.setLong("candidateId", candidateId).setString("yes", yes).uniqueResult();
		return lcaI94Summary;
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
		if ((orgLocations.getIsAccountingFirm() != null && orgLocations.getIsAccountingFirm().equalsIgnoreCase("Y"))
				&& (orgLocations.getIsLawFirm() != null && orgLocations.getIsLawFirm().equalsIgnoreCase("Y"))) {
			query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'" + generatedIdCount
					+ "') AS UNSIGNED)) FROM sourcelead.IMMIGRATION WHERE ATTORNEY_LOCATION_ID = '"
					+ orgLocations.getId() + "'";
		} else {
			query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'" + generatedIdCount
					+ "') AS UNSIGNED)) FROM sourcelead.IMMIGRATION WHERE ORG_LOCATION_ID = '" + orgLocations.getId()
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
		System.out.println("Delete deletetCandidateFamilyDetails candidateId ---> " + id);

		try {
			Session session = this.sessionFactory.getCurrentSession();
			CandidateFamilyDetails familyEntity = (CandidateFamilyDetails) session.load(CandidateFamilyDetails.class,
					id);
			session.delete(familyEntity);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Deleted record id -->" + id);
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
		// System.out.println(" inside canCurrResiCand -->"+canCurrResiCand);
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
		// System.out.println(" inside candidatesResidenceList size
		// -->"+candidatesResidenceList.size());
		candidatesMasterResidenceList.addAll(candidatesResidenceList);
		// System.out.println(" inside candidatesMasterResidenceList size
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
		System.out.println("get CandidateFamilyDetails id ---> " + id);
		CandidateFamilyDetails familyObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			familyObj = (CandidateFamilyDetails) session.get(CandidateFamilyDetails.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("got the record id -->" + id);
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
		System.out.println("Deleted record id -->" + id);

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteCandidateResidenceDetails(Long id, Long candidateId) {

		System.out.println("Delete deletetCandidateFamilyDetails candidateId ---> " + id);

		try {
			Session session = this.sessionFactory.getCurrentSession();
			CandidateResidence res = (CandidateResidence) session.load(CandidateResidence.class, id);
			session.delete(res);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Deleted record id -->" + id);

		return "success";

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateResidence getCandidateResidenceDetailsById(Long id) {
		System.out.println("get CandidateResidenceDetails id ---> " + id);
		CandidateResidence residenceObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			residenceObj = (CandidateResidence) session.get(CandidateResidence.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("got the record id -->" + id);
		return residenceObj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidatePassportVisa getCandidatePassportVisaDetailsById(Long id) {
		System.out.println("get CandidateFamilyDetails id ---> " + id);
		CandidatePassportVisa passportVisaObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			passportVisaObj = (CandidatePassportVisa) session.get(CandidatePassportVisa.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("got the record id -->" + id);
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
		System.out.println("get CandidateFamilyDetails id ---> " + id);
		Immigration immigration = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			immigration = (Immigration) session.get(Immigration.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("got the record id -->" + id);
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
			Long candidateId, String searchVal) {
		logger.debug("Getting  CandidatepassportDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotEmpty(searchVal) && searchVal != null) {
			query1.append(" and ((cd.documentType.name like :searchVal )");
			query1.append(" or (cd.documentStatus like :searchVal )");
			query1.append(" or (cd.description like :searchVal ))");
		}
		List<ImmigrationCandidateDocuments> immigrationCandidateDocumentsList = new ArrayList<ImmigrationCandidateDocuments>();
		String queryString = "from ImmigrationCandidateDocuments cd where cd.candidateId.id=:candidateId and cd.immigrationId.id=:immigrationId and cd.immigrationTemplateId.id=:templateId ";
		String orderByString = " order by cd.updatedDate desc";
		Query query = session.createQuery(queryString + query1 + orderByString).setLong("candidateId", candidateId)
				.setLong("templateId", templateId).setLong("immigrationId", immigrationId);
		if (StringUtils.isNotEmpty(searchVal) && searchVal != null) {
			query.setParameter("searchVal", "%" + searchVal + "%");
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
			Long immigrationId, String searchVal) {
		List<ImmigrationCandidateForms> candidateFormLst = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder query1 = new StringBuilder("");
			if (StringUtils.isNotEmpty(searchVal) && searchVal != null) {
				query1.append(" and ((icf.formName like :searchVal )");
				query1.append(" or (icf.formStatus like :searchVal ))");
			}
			String queryString = "from ImmigrationCandidateForms icf where  icf.immigrationTempId.id= :templateId and icf.immigration.id=:immigrationId and icf.candidate=:candidateId";
			Query query = session.createQuery(queryString + query1).setLong("templateId", templateId)
					.setLong("immigrationId", immigrationId).setLong("candidateId", candidateId);
			if (StringUtils.isNotEmpty(searchVal) && searchVal != null) {
				query.setParameter("searchVal", "%" + searchVal + "%");
			}
			candidateFormLst = query.list();
			System.out.println("candidateFormLst size is ---> " + candidateFormLst.size());
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
		if (searchValue.contains("-") || searchValue.contains("/")) {
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat())
					.toFormatter();
			LocalDate parsedDate = LocalDate.parse(searchValue, orgFormat);
			DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String newDate = parsedDate.format(f2);
			Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
			java.sql.Date sqlDate1 = new java.sql.Date(searchDate.getTime());
			System.out.println("---test-----" + sqlDate1);
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
			if (orgLocations.getId() == immigrationListDTO.getOrgLocationId()) {
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
				if (orgLocations.getId() == immigrationListDTO.getOrgLocationId()) {
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

		System.out.println(" latest code deployed 6666 --->" + orgLocations.getId());
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
			List<LookUpData> caEmployerDTOs = immigrationTemplateDao
					.getLookupDataByType(ImmigrationConstants.PETITION_STATUS);
			for (ImmigrationListView source : ImmigrationViewLst) {
				// System.out.println(" DocsCount"+source.getDocsCount());
				ImmigrationListViewDTO target = new ImmigrationListViewDTO();
				BeanUtils.copyProperties(source, target);
				target.setCurrUsicisStatusList(caEmployerDTOs);
				// System.out.println(" DocsCount
				// target"+target.getDocsCount());
				target.setOpenDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getOpenDate()));
				target.setLcaStartDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaStartDate()));
				target.setLcaEndDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaEndDate()));
				if (orgLocations.getId() == source.getOrgLocationId()) {
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

		System.out.println(" after dao impl  ---responseList getRecordsTotal is -->" + responseList.getRecordsTotal());
		return responseList;
	}

	@Transactional(rollbackFor = { Exception.class })
	private List<String> getOrgNamesById(Long[] longs, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgRelations orgr  where orgr.locationId = :currOrgLocationId and orgr.id IN ( :relationIds ) ";
		List<OrgRelations> orgRelations = session.createQuery(query).setLong("currOrgLocationId", orgLocationId)
				.setParameterList("relationIds", longs).list();
		List<String> orgNames = new ArrayList<String>();
		for (OrgRelations orgRelation : orgRelations) {
			orgNames.add(orgRelation.getResponderLocationId().getOrg().getOrgName());
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
	public String updateUscisTypeDropDown(Long templateId, Long uscisId) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Immigration immigration = session.get(Immigration.class, templateId);
			LookUpData uscisStatusId = new LookUpData();
			uscisStatusId.setId(uscisId);
			immigration.setCurrUscisStauts(uscisStatusId);
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
		ImmigrationDashboardView dashboardObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "from ImmigrationDashboardView dbview where dbview.orgLocationId=:orgLocationId";
			dashboardObj = (ImmigrationDashboardView) session.createQuery(queryString)
					.setLong("orgLocationId", orgLocations.getId()).uniqueResult();
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
			System.out.println("Exception caught  while getting result of entity " + entityName);
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
			System.out.println("Exception caught  while getting result of template entity " + entityName);
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
			System.out.println("Exception caught  while updating immigration entity " + immigration.getId());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<MyImmigrationViewDTO> getMyImmigrationsList(MyImmigrationViewDTO immigrationListDTO,
			OrgLocations orgLocations, Long userId) {
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

		listQuery = "from MyImmigrationView immView  where immView.status = 1  and immView.candidateUserId =:candidateUserId and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId) ";
		String listQueryCount = "select count(*) from MyImmigrationView immView where  immView.status = 1  and immView.candidateUserId =:candidateUserId and (immView.orgLocationId=:orgLocationId or immView.attorneyOrgLocationId =:orgLocationId)  ";
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
				// System.out.println(" DocsCount"+source.getDocsCount());
				MyImmigrationViewDTO target = new MyImmigrationViewDTO();
				BeanUtils.copyProperties(source, target);
				// System.out.println(" DocsCount
				// target"+target.getDocsCount());
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

		System.out.println(
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
}
