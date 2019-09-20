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
import org.hibernate.type.StandardBasicTypes;
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
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.LcaDTO;
import com.bis.sourcelead.dto.LcaDetailsDTO;
import com.bis.sourcelead.dto.LcaListViewDTO;
import com.bis.sourcelead.dto.LcaReportViewDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.CandidatePassport;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateVisa;
import com.bis.sourcelead.entity.CandidateVisaHistory;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationActivityHistory;
import com.bis.sourcelead.entity.ImmigrationAttorneyReportView;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationDashboardView;
import com.bis.sourcelead.entity.ImmigrationListExportView;
import com.bis.sourcelead.entity.ImmigrationListView;
import com.bis.sourcelead.entity.ImmigrationReportView;
import com.bis.sourcelead.entity.ImmigrationTasks;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.LcaDocumentHistory;
import com.bis.sourcelead.entity.LcaHistory;
import com.bis.sourcelead.entity.LcaListExportView;
import com.bis.sourcelead.entity.LcaListView;
import com.bis.sourcelead.entity.LcaPlaceOfEployment;
import com.bis.sourcelead.entity.LcaReportView;
import com.bis.sourcelead.entity.LcaVisaClarifications;
import com.bis.sourcelead.entity.MyImmigrationView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
import com.bis.sourcelead.payroll.dto.ImmigrationReportViewDTO;
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
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations, Long status) {
		logger.debug("Saving Template Details-->");
		List<ImmigrationTemplate> templateLst = new ArrayList<ImmigrationTemplate>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			logger.debug(" before query execute templateLst size -->" + templateLst.size());
			if(status == null) {
			String queryString = "from ImmigrationTemplate it where it.orgLocations.id =:orgLocationId";
			templateLst = session.createQuery(queryString).setLong("orgLocationId", orgLocations.getId()).list();
			}else {
				String queryString = "from ImmigrationTemplate it where it.status =:status and it.orgLocations.id =:orgLocationId";
				templateLst = session.createQuery(queryString).setLong("orgLocationId", orgLocations.getId()).setLong("status", status).list();
			}
			logger.debug("tempalte Details loaded Successfully templateLst size -->" + templateLst.size());

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
	public CandidateVisa saveVisaDetails(CandidateVisa candidateVisa) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		logger.debug("Storing ImmigrationVisa");
		try {
			if (candidateVisa != null) {
				session.saveOrUpdate(candidateVisa);
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
		return candidateVisa;

	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidatePassport savePassportDetails(CandidatePassport candidatePassport) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		logger.debug("Storing ImmigrationPassportVisa");
		try {
			if (candidatePassport != null) {
				session.saveOrUpdate(candidatePassport);
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
		return candidatePassport;

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

	
	// visa List
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationVisaDTO> getCandidateVisaDetails(Long candidateId,String searchValue) {
		List<ImmigrationVisaDTO> visaList = new ArrayList<ImmigrationVisaDTO>();
		logger.debug("Getting  getCandidateVisaDetails --->  "+candidateId);
		Session session = this.sessionFactory.getCurrentSession();
		if (!"undefined".equalsIgnoreCase(searchValue) && StringUtils.isNotBlank(searchValue) && searchValue != "") {
			//String queryString = "from CandidateVisa cv where cv.candidate.id=:candidateId and (cv.visaNumber like :searchValue or cv.country like :searchValue or cv.visaType like :searchValue)  order by cv.updatedDate desc";
			String sqlQueryString = "select cvh.ID as id,  cv.CANDIDATE_ID as candidateId, cvh.VISA_ID as candidateVisaId , "+
											"cv.VISA_NUMBER as visaNumber, cv.VISA_TYPE as visaType, cvh.status as status," + 
											" cv.COUNTRY as country, CAST(cvh.VALID_FROM_DATE AS char) as validFrom," + 
											" CAST(cvh.VALID_TO_DATE AS char) as validTo " + 
											"  from CANDIDATE_VISA cv, CANDIDATE_VISA_HISTORY cvh  Where cv.ID= cvh.VISA_ID  "+
											" and (cv.VISA_NUMBER like :searchValue or cv.COUNTRY like :searchValue or cv.VISA_TYPE like :searchValue) "+
											" and cv.CANDIDATE_ID=:candidateId  order by cvh.UPDATED_DATE desc";
			System.out.println("candidateId is if "+candidateId);
			visaList = session.createSQLQuery(sqlQueryString)
					   .addScalar("id", StandardBasicTypes.LONG)
					   .addScalar("candidateId", StandardBasicTypes.LONG)
					   .addScalar("candidateVisaId", StandardBasicTypes.LONG)
					   .addScalar("visaNumber", StandardBasicTypes.STRING)
					   .addScalar("country", StandardBasicTypes.STRING)
					   .addScalar("visaType", StandardBasicTypes.STRING)
					   .addScalar("status", StandardBasicTypes.INTEGER)
					   .addScalar("validFrom", StandardBasicTypes.STRING)
					   .addScalar("validTo", StandardBasicTypes.STRING)
					  .setLong("candidateId", candidateId)
					  .setString("searchValue", "%" + searchValue + "%").setResultTransformer(Transformers.aliasToBean(ImmigrationVisaDTO.class)).list();;
		} else {
			//String queryString = "from CandidateVisa cv where cv.candidate.id=:candidateId  order by cv.updatedDate desc";
			String sqlQueryString = "select cvh.ID as id,  cv.CANDIDATE_ID as candidateId, cvh.VISA_ID as candidateVisaId ,"+
											" cv.VISA_NUMBER as visaNumber, cv.VISA_TYPE as visaType, cvh.status as status ," + 
											" cv.COUNTRY as country, CAST(cvh.VALID_FROM_DATE AS char) as validFrom," + 
											" CAST(cvh.VALID_TO_DATE AS char) as validTo " + 
	  								"  from CANDIDATE_VISA cv, CANDIDATE_VISA_HISTORY cvh  Where cv.ID= cvh.VISA_ID  and cv.CANDIDATE_ID=:candidateId order by cvh.UPDATED_DATE desc";
			
			System.out.println("candidateId is else "+candidateId);
			visaList = session.createSQLQuery(sqlQueryString)
					   .addScalar("id", StandardBasicTypes.LONG)
					   .addScalar("candidateId", StandardBasicTypes.LONG)
					   .addScalar("candidateVisaId", StandardBasicTypes.LONG)
					   .addScalar("visaNumber", StandardBasicTypes.STRING)
					   .addScalar("country", StandardBasicTypes.STRING)
					   .addScalar("visaType", StandardBasicTypes.STRING)
					   .addScalar("status", StandardBasicTypes.INTEGER)
					   .addScalar("validFrom", StandardBasicTypes.STRING)
					   .addScalar("validTo", StandardBasicTypes.STRING)
					   .setLong("candidateId", candidateId).setResultTransformer(Transformers.aliasToBean(ImmigrationVisaDTO.class)).list();
		}
		return visaList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationVisaDTO getCandidateVisaDetailsByCandidateIdAndVisaNumber(Long candidateId,
			String visaNumber) {
		ImmigrationVisaDTO candRecentVisaDetails =null;
		logger.debug("Getting  getCandidateVisaDetailsByCandidateIdAndVisaNumber --->  "+visaNumber);
		Session session = this.sessionFactory.getCurrentSession();
		if (StringUtils.isNotBlank(visaNumber)) {

			String sqlQueryString = "select cvh.ID as id,  cv.CANDIDATE_ID as candidateId, cvh.VISA_ID as candidateVisaId , "+
											"cv.VISA_NUMBER as visaNumber, cv.VISA_TYPE as visaType, cvh.status as status," + 
											" cv.COUNTRY as country, CAST(cvh.VALID_FROM_DATE AS char) as validFrom," + 
											" CAST(cvh.VALID_TO_DATE AS char) as validTo " + 
											"  from CANDIDATE_VISA cv, CANDIDATE_VISA_HISTORY cvh  Where cv.ID= cvh.VISA_ID  "+
											" and cv.CANDIDATE_ID=:candidateId and cv.VISA_NUMBER=:visaNumber order by cvh.ID desc LIMIT 1";
			candRecentVisaDetails = (ImmigrationVisaDTO) session.createSQLQuery(sqlQueryString)
					   .addScalar("id", StandardBasicTypes.LONG)
					   .addScalar("candidateId", StandardBasicTypes.LONG)
					   .addScalar("candidateVisaId", StandardBasicTypes.LONG)
					   .addScalar("visaNumber", StandardBasicTypes.STRING)
					   .addScalar("country", StandardBasicTypes.STRING)
					   .addScalar("visaType", StandardBasicTypes.STRING)
					   .addScalar("status", StandardBasicTypes.INTEGER)
					   .addScalar("validFrom", StandardBasicTypes.STRING)
					   .addScalar("validTo", StandardBasicTypes.STRING)
					   .setLong("candidateId", candidateId)
					   .setString("visaNumber", visaNumber)
					   .setResultTransformer(Transformers.aliasToBean(ImmigrationVisaDTO.class)).uniqueResult();
		} 
		return candRecentVisaDetails;
	}
	
	//Passport List
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidatePassport> getCandidatePassportDetails(Long candidateId,
			String searchValue) {
		List<CandidatePassport> passportList = new ArrayList<CandidatePassport>();
		logger.debug("Getting  CandidatepassportDetails --->  ");
		Session session = this.sessionFactory.getCurrentSession();
		if (!"undefined".equalsIgnoreCase(searchValue) && StringUtils.isNotBlank(searchValue) && searchValue != "") {
			String queryString = "from Candidat"
					+ "ePassport cp where cp.candidate.id=:candidateId  and (cp.passportNumber like :searchValue or cp.country like :searchValue)  order by cp.updatedDate desc";
			passportList = session.createQuery(queryString).setLong("candidateId", candidateId)
					.setString("searchValue", "%" + searchValue + "%").list();
		} else {
			String queryString = "from CandidatePassport cp where cp.candidate.id=:candidateId   order by cp.updatedDate desc";
			passportList = session.createQuery(queryString).setLong("candidateId", candidateId)
					.list();
		}
		return passportList;
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

	
	//visa delete
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public void removeCandidateVisaDetails(Long visaHistoryId){
			try {
				Session session = this.sessionFactory.getCurrentSession();
				CandidateVisaHistory visaHistory = (CandidateVisaHistory) session.load(CandidateVisaHistory.class, visaHistoryId);
				//CandidateVisa CandidateVisa= visaHistory.getVisaId();
				Long visaId = visaHistory.getVisaId().getId();
				session.delete(visaHistory);
				session.flush();
				// below query used to check find all child records are using parent visa id.
				Long recordsCount = (Long)session.
						            createSQLQuery("SELECT COUNT(*) as recordsCount FROM CANDIDATE_VISA_HISTORY WHERE VISA_ID=:visaId")
						            .addScalar("recordsCount", StandardBasicTypes.LONG)
						            .setLong("visaId",visaId).uniqueResult();
				// if recordsCount ==1 means child records are exist in visa history table if returns 0 then no child records in history table.
				if(recordsCount == 0) {
					CandidateVisa visa= (CandidateVisa) session.load(CandidateVisa.class, visaId);
					session.delete(visa);
					session.flush();
				}
				
			} catch (HibernateException e) {
				logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
				e.printStackTrace();
			}
			logger.debug("Deleted record id -->" + visaHistoryId);

		}
	//passport delete
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void removeCandidatePassportDetails(Long candidateId, Long id, User user, OrgLocations orgLocations){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CandidatePassport passportEntity = (CandidatePassport) session.load(CandidatePassport.class,
					id);
			session.delete(passportEntity);
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
	public CandidateVisa getCandidateVisaDetailsById(Long id) {
		logger.debug("get CandidateVisa id ---> " + id);
		CandidateVisa visaObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			visaObj = (CandidateVisa) session.get(CandidateVisa.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return visaObj;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidatePassport getCandidatePassportDetailsById(Long id) {
		logger.debug("get CandidatePassport id ---> " + id);
		CandidatePassport passportObj = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			passportObj = (CandidatePassport) session.get(CandidatePassport.class, id);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("got the record id -->" + id);
		return passportObj;
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
		// if beneficiary logged in show only beneficiary Documents(Document Type)
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
			// if beneficiary logged in show only beneficiary forms(Document Type)
			if(myImmigration.equalsIgnoreCase("Y")) {
				query.setParameter("formCategory", "Document Type");
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
			resultQuery = listQuery + query1 + query2+sortingQry;
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
			resultQuery = listQuery + query1+ sortingQry;
			resultCountQuery = listQueryCount + query1;
			hqlQuery = session.createQuery(resultQuery).setLong("orgLocationId", orgLocations.getId());
			hqlQuery1 = session.createQuery(resultCountQuery).setLong("orgLocationId", orgLocations.getId());
		}
		ImmigrationViewLst = hqlQuery.setFirstResult(immigrationListDTO.getStart()).setMaxResults(immigrationListDTO.getLength()).list();

		countlist = hqlQuery1.list();
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
			
           deleteLcaHistoryDetailsByImmigrationId(immigrationId);
           deleteImmigrationNotesByImmigrationId(immigrationId);
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

	/*@Override
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
	}*/
	
	//Visa
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateCandidateVisa(Long candidateId,String idNumVal) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from CandidateVisa canVisa where canVisa.candidate.id = :candidateId  and canVisa.visaNumber =:idNumVal";
		List<CandidateVisa> visaList = session.createQuery(query).setLong("candidateId", candidateId).setString("idNumVal", idNumVal).list();
		if (visaList != null && visaList.size() > 0) {
			flag = true;
			return flag;
		}
		else {
			return flag;
		}
	}
		
	//passport
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateCandidatePassport(Long candidateId,String idNumVal) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from CandidatePassport cPassport where cPassport.candidate.id = :candidateId  and cPassport.passportNumber = :idNumVal";
		List<CandidatePassport> passportList = session.createQuery(query).setLong("candidateId", candidateId).setString("idNumVal", idNumVal).list();
		if (passportList != null && passportList.size() > 0) {
			flag = true;
			return flag;
		}
		else {
			return flag;
		}
	}

	
	//Visa
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateVisa getCandidateVisaDetailsByIdNumberAndId(String idNumber,
			Long candidateId,Long id) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from CandidateVisa canVisa where canVisa.visaNumber = :idNumber and canVisa.candidate.id = :candidateId and  canVisa.id = :id and canVisa.status = 1";
		List<CandidateVisa> visaList =  session.createQuery(query) .setString("idNumber", idNumber).setLong("candidateId", candidateId).setLong("id", id).list();
		CandidateVisa candidateVisa = null;
		if(visaList != null && !visaList.isEmpty())
		{
			candidateVisa = (CandidateVisa) visaList.iterator().next();
		}
		return candidateVisa;
	}
	
	//passport
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidatePassport getCandidatePassportDetailsByIdNumberAndId(String idNumber,
			Long candidateId,Long id) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from CandidatePassport cPassport where cPassport.passportNumber = :idNumber and cPassport.candidate.id = :candidateId and  cPassport.id = :id and cPassport.status = 1";
		List<CandidatePassport> passportList =  session.createQuery(query) .setString("idNumber", idNumber).setLong("candidateId", candidateId).setLong("id", id).list();
		CandidatePassport candidatePassport = null;
		if(passportList != null && !passportList.isEmpty())
		{
			candidatePassport = (CandidatePassport) passportList.iterator().next();
		}
		return candidatePassport;
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
		return listExportViews;
	}
	
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
				query = "SELECT LCA_NO as label, CONVERT(ID, CHAR) as value from LCA lca where (lca.ID NOT IN (SELECT LCA_ID FROM LCA_HISTORY where CANDIDATE_ID =:candidateId)) and lca.LCA_NO like :lcaNo and lca.ORG_LOCATION_ID =:orgLocationId  and lca.END_DATE >= CURDATE() and lca.status=1 and lca.AVAILABLE_POSITIONS > 0"; 
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
		return listExportViews;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Lca> getAllLcaList(Date currentDate) {
		Session session = this.sessionFactory.getCurrentSession();
		String listQuery = "from Lca lca where  lca.endDate=:currentDate order by lca.createdBy.id";
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
	public List<LcaHistory> getLcaHistoryDetails(Long immigrationId, String yes) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LcaHistory lh  left join fetch lh.lcaId where lh.immigrationId.id = :immigrationId and lh.isCurrent = :yes ORDER BY lh.id DESC";
		List<LcaHistory> lhList = session.createQuery(query).setLong("immigrationId", immigrationId).setString("yes", yes).setMaxResults(0).list();
		if (lhList != null && lhList.size() > 0) {
			lhList.get(0);
		}
		return lhList;
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
					/*" lpe.LOCATION as location, " + */
					" GROUP_CONCAT(lpe.LOCATION SEPARATOR ' & ') AS location, "+
					" l.PREVAILING_WAGE as prevailingWage, " + 
					" l.LCA_STATUS as lcaStatus, " + 
					" CAST(l.NO_OF_POSITIONS AS CHAR(5))as noOfPositions, " + 
					" CAST(l.AVAILABLE_POSITIONS AS CHAR(5))as availablePositions, " + 
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
					
					" l.id = lh.LCA_ID AND l.id = lpe.LCA_ID AND l.id = lvc.LCA_ID " + 
					" AND lh.IMMIGRATION_ID = :immigrationId "+
					" GROUP BY l.ID";
			lcaList = session.createSQLQuery(queryString).setParameter("immigrationId", immigrationId).setResultTransformer(Transformers.aliasToBean(LcaDTO.class)).list();
		    return lcaList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getAllExpiredCandidatesFromLcaHistoryByLcaId(Long lcaId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<String> employeeList = new ArrayList<String>();
		String queryString ="SELECT DISTINCT CONCAT(CAN.FIRST_NAME, IFNULL(CAN.LAST_NAME,''))  AS FULLNAME " + 
				" FROM CANDIDATE CAN, " + 
				" LCA_HISTORY LH " + 
				" WHERE LH.CANDIDATE_ID = CAN.ID AND LH.LCA_ID=:lcaId";
		employeeList = session.createSQLQuery(queryString).setParameter("lcaId", lcaId).list();
		employeeList.size();
	
	//	System.out.println("employeeList.size() :: ========"+employeeList.size());
	    return employeeList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<LcaDetailsDTO> getEmployeeByLcaId(Long lcaId,OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<LcaDetailsDTO> empList = new ArrayList<LcaDetailsDTO>();
		String queryString = "SELECT " + "IMM.GENERATED_ID AS petitionId, " + "IMM.IMMIGRATION_UUID AS immigrationUUID,"
				+ "(SELECT " + "CONCAT(FIRST_NAME, ' ', IFNULL(LAST_NAME, '')) " + "FROM " + " CANDIDATE " + " WHERE "
				+ "ID = LH.CANDIDATE_ID) AS empName, " + "(SELECT " + "CANDIDATE_UUID " + "FROM " + "CANDIDATE "
				+ "WHERE " + "ID = LH.CANDIDATE_ID) AS candidateUUID, " + "(SELECT " + "NAME " + "FROM "
				+ "LOOKUP_DATA " + "WHERE " + "ID = IMM.PETITION_TYPE) AS petitionType, " + "(SELECT " + "NAME "
				+ "FROM " + "LOOKUP_DATA " + "WHERE " + "ID = IMM.CURRENT_USCIS_STATUS) AS status, "
				+ " DATE_FORMAT(IMM.OPEN_DATE ,'%Y-%m-%d') AS filedOn, " + "(SELECT " + "O.ORG_NAME " + "FROM "
				+ "ORG_LOCATIONS OL, " + "ORG O " + "WHERE " + "OL.ORG_ID = O.ID "
				+ "AND OL.ID = IMM.ORG_LOCATION_ID) AS client, " + "(SELECT " + "O.ORG_NAME " + "FROM "
				+ "ORG_LOCATIONS OL, " + " ORG O " + "WHERE " + " OL.ORG_ID = O.ID "
				+ " AND OL.ID = IMM.ATTORNEY_LOCATION_ID) AS vendor, " + "(SELECT " + "CONVERT(ORG_LOCATION_ID,CHAR) "
				+ "FROM " + "LCA " + "WHERE " + "ID= LH.LCA_ID) AS orgLocationId, " + "(SELECT  " + "IT.UUID " + "FROM "
				+ "IMMIGRATION_TEMPLATE IT " + "WHERE " + "IT.ID = IMM.IMMIGRATION_TEMPLATE_ID) AS immiTemplateUUID "
				+ "FROM " + "IMMIGRATION IMM, " + "LCA_HISTORY LH " + "WHERE " + "IMM.ID = LH.IMMIGRATION_ID "
				+ "AND LH.LCA_ID =:lcaId";
		empList = session.createSQLQuery(queryString).setParameter("lcaId", lcaId).setResultTransformer(Transformers.aliasToBean(LcaDetailsDTO.class)).list();
		for(LcaDetailsDTO obj : empList) {
			if(orgLocations.getId().equals(obj.getOrgLocationId())) {
			obj.setAttorneyName(obj.getClient());
			}else {
				obj.setAttorneyName(obj.getVendor());
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int deleteLcaHistoryDetailsByImmigrationId(Long immigrationId) {
		int count =0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from LcaHistory lh  where lh.immigrationId =:immigrationId";
			 count= session.createQuery(query.toString()).setLong("immigrationId", immigrationId).executeUpdate();
			return count;
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int deleteImmigrationNotesByImmigrationId(Long immigrationId) {
		int count =0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from SourceleadNotes sn  where sn.immigration.id =:immigrationId";
			 count= session.createQuery(query.toString()).setLong("immigrationId", immigrationId).executeUpdate();
			return count;
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ImmigrationReportViewDTO> getImmigrationReports(ImmigrationReportViewDTO immigrationReportDTO, OrgLocations orgLocations) {
		
		Session session = this.sessionFactory.getCurrentSession();
        ResponseList<ImmigrationReportViewDTO> responseList = new ResponseList<>();
        List<ImmigrationReportView> immReportViewList = null;
        Query hqlQuery = null;
        Query hqlCountQuery = null;
        
       /* java.sql.Date startDate = new java.sql.Date(immigrationReportDTO.getFreqDates()[0].getTime());
		java.sql.Date endDate = new java.sql.Date(immigrationReportDTO.getFreqDates()[1].getTime());*/
		
        Date startDate = new Date(immigrationReportDTO.getFreqDates()[0].getTime());
		Date endDate = new Date(immigrationReportDTO.getFreqDates()[1].getTime());
		startDate.setHours(00);
		startDate.setMinutes(00);
		startDate.setSeconds(00);
		
		endDate.setHours(23);
		endDate.setMinutes(59);
		endDate.setSeconds(59);
		
		List<Long> countlist = null;
		List<String> petitionTypeList = immigrationReportDTO.getPetitionType();
		List<String> statusList = immigrationReportDTO.getPetitionStatus();
		List<String> attorneyList = immigrationReportDTO.getAttorneyId();
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(immigrationReportDTO.getSortColumn())) {
			if(immigrationReportDTO.getSortColumn().equalsIgnoreCase("displayName")) {
				sortingQry = " order by immRepView."+ immigrationReportDTO.getSortColumn() +" "+immigrationReportDTO.getSortOrder();
			} 
			
		}
		
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotBlank(immigrationReportDTO.getSearchValue())) {
			query1.append(" and ((immRepView.generatedId like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.petitionType like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.vendor like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.firstName like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.email like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.phone like '%" + immigrationReportDTO.getSearchValue() + "%' ))");
		}
		
		String query = null;
		if(statusList.size() >0 && attorneyList.size()>0 && !attorneyList.contains("0")) {
			query = "from ImmigrationReportView immRepView  where  immRepView.orgLocationId =:orgLocationId and immRepView.currUscisStatus IN (:statusList) and immRepView.petitionType IN (:petitionTypeList) and immRepView.vendor IN (:attorneyList) and immRepView.openDate between :startDate and :endDate"+query1+" "+sortingQry+" ";
			
			hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameterList("statusList", statusList).setParameterList("petitionTypeList", petitionTypeList).setParameterList("attorneyList", attorneyList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else if (statusList !=null && statusList.size()>0) {
			query = "from ImmigrationReportView immRepView  where  immRepView.orgLocationId =:orgLocationId and immRepView.currUscisStatus IN (:statusList) and immRepView.petitionType IN (:petitionTypeList) and immRepView.openDate between :startDate and :endDate"+query1+" "+sortingQry+" ";
			
			hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameterList("statusList", statusList).setParameterList("petitionTypeList", petitionTypeList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else if (attorneyList.size()>0 && !attorneyList.contains("0")) {
			query = "from ImmigrationReportView immRepView  where  immRepView.orgLocationId =:orgLocationId and immRepView.petitionType IN (:petitionTypeList) and immRepView.vendor IN (:attorneyList) and immRepView.openDate between :startDate and :endDate"+query1+" "+sortingQry+"";

			hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameterList("petitionTypeList", petitionTypeList).setParameterList("attorneyList", attorneyList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else {
			query = "from ImmigrationReportView immRepView  where  immRepView.orgLocationId =:orgLocationId and immRepView.petitionType IN (:petitionTypeList) and immRepView.openDate between :startDate and :endDate"+query1+" "+sortingQry+"";

			hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameterList("petitionTypeList", petitionTypeList).setDate("startDate", startDate).setDate("endDate",endDate);
		}
		String petitionQueryCount = null;
		
		if(statusList.size() >0 && attorneyList.size()>0 && !attorneyList.contains("0")) {
			petitionQueryCount = "select count(*) from ImmigrationReportView immRepView  where  immRepView.orgLocationId =:orgLocationId and immRepView.currUscisStatus IN (:statusList) and immRepView.petitionType IN (:petitionTypeList) and immRepView.vendor IN (:attorneyList) and immRepView.openDate between :startDate and :endDate"+query1+" "+sortingQry+" ";
			
		    hqlCountQuery=session.createQuery(petitionQueryCount).setLong("orgLocationId", orgLocations.getId()).setParameterList("statusList", statusList).setParameterList("petitionTypeList", petitionTypeList).setParameterList("attorneyList", attorneyList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else if(statusList.size()>0){
			petitionQueryCount = "select count(*) from ImmigrationReportView immRepView  where  immRepView.orgLocationId =:orgLocationId and immRepView.currUscisStatus IN (:statusList) and immRepView.petitionType IN (:petitionTypeList) and immRepView.openDate between :startDate and :endDate"+query1+" "+sortingQry+"";
			
			hqlCountQuery=session.createQuery(petitionQueryCount).setLong("orgLocationId", orgLocations.getId()).setParameterList("statusList", statusList).setParameterList("petitionTypeList", petitionTypeList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else if(attorneyList.size()>0 && !attorneyList.contains("0")){
			petitionQueryCount = "select count(*) from ImmigrationReportView immRepView  where  immRepView.orgLocationId =:orgLocationId and immRepView.petitionType IN (:petitionTypeList) and immRepView.vendor IN (:attorneyList) and immRepView.openDate between :startDate and :endDate"+query1+" "+sortingQry+" ";	
			
		   hqlCountQuery=session.createQuery(petitionQueryCount).setLong("orgLocationId", orgLocations.getId()).setParameterList("petitionTypeList", petitionTypeList).setParameterList("attorneyList", attorneyList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else {
			petitionQueryCount = "select count(*) from ImmigrationReportView immRepView  where  immRepView.orgLocationId =:orgLocationId and immRepView.petitionType IN (:petitionTypeList) and immRepView.openDate between :startDate and :endDate"+query1+" "+sortingQry+"";
			
		    hqlCountQuery=session.createQuery(petitionQueryCount).setLong("orgLocationId", orgLocations.getId()).setParameterList("petitionTypeList", petitionTypeList).setDate("startDate", startDate).setDate("endDate",endDate);
		}
		immReportViewList = hqlQuery.setFirstResult(immigrationReportDTO.getStart()).setMaxResults(immigrationReportDTO.getLength()).list();
        int count =0;
		countlist=hqlCountQuery.list();
		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		responseList.setRecordsTotal(count);
		responseList.setRecordsFiltered(count);
		
		List<ImmigrationReportViewDTO> immRepViewDTOList = new ArrayList<ImmigrationReportViewDTO>();
        for (ImmigrationReportView source : immReportViewList) {
        	
        	ImmigrationReportViewDTO target = new ImmigrationReportViewDTO();
        	target.setId(source.getGeneratedId());
        	target.setImmigrationUUID(source.getImmigrationUUID());
        	target.setImmiTemplateUUID(source.getImmiTemplateUUID());
        	target.setCandidateUUID(source.getCandidateUUID());
        	target.setPetitionType(Arrays.asList(source.getPetitionType()));
        	if(source.getCurrUscisStatus() != null) {
        	target.setCurrUscisStatus(source.getCurrUscisStatus());
        	}
        	target.setPetitionTypeName(source.getPetitionType());
        	target.setEmail(source.getEmail());
        	target.setPhoneNumber(source.getPhone());
        	target.setName(source.getFirstName());
        	target.setCountry(source.getCountry());
        	
        	target.setOpenDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getOpenDate()));
			target.setLcaStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaStartDate()));
			target.setLcaEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaEndDate()));
			
			target.setOrgName(source.getVendor());
			
			if (orgLocations.getId().equals(source.getOrgLocationId())) {
				target.setOrgName(source.getVendor());
				target.setOrgNameSel(ImmigrationConstants.ORG_NAME_SELECTED_ORG2);
				//target.setSpocName(source.getSpocNameVendor());
			}
			
            BeanUtils.copyProperties(source, target);
            immRepViewDTOList.add(target);
        }
        responseList.setData(immRepViewDTOList);
        return responseList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<LcaReportViewDTO> getLcaReportList(LcaReportViewDTO lcaReportViewDTO, String status,
			String lcaStatus, String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal,
			String filterByDays,String positionsAvaialble) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
        List<Long> countlist = null;
        Integer filterCount = 0;
        List<LcaReportView> lcaRepViewList = null;
        ResponseList<LcaReportViewDTO> responseList = new ResponseList<LcaReportViewDTO>();
        String subQuery1 ="";
        String subQuery2 = ""; 
        String subQuery3 = "";
        String subQuery4 = ""; 
        
        StringBuilder query1 = new StringBuilder("");
        if (CommonUtil.isValidDate(searchValue)) {
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat())
					.toFormatter();
			LocalDate parsedDate = LocalDate.parse(searchValue, orgFormat);
			DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String newDate = parsedDate.format(f2);
			Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
			java.sql.Date sqlDate1 = new java.sql.Date(searchDate.getTime());
			logger.debug("---test-----" + sqlDate1);
			query1.append(" and ((lcaRepView.startDate like '%" + sqlDate1 + "%' )");
			query1.append(" or (lcaRepView.endDate like '%" + sqlDate1 + "%' ))");
		} else if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
			query1.append(" and ((lcaRepView.lcaNo like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.lcaTitle like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.noOfPositions like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.location like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.wagesLevel like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.privilingWageCurrency like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.prevailingWage like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.privilingWageRateType like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			
			query1.append(" or (lcaRepView.socCode like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.socOccupationTitle like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.jobTitle like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.lcaStatus like '%" + lcaReportViewDTO.getSearchValue() + "%' )");
			query1.append(" or (lcaRepView.status like '%" + lcaReportViewDTO.getSearchValue() + "%' ))");
		}
        
        String sortingQry = "";
		if (StringUtils.isNotBlank(lcaReportViewDTO.getSortColumn())) {
			String sortColumnName = lcaReportViewDTO.getSortColumn();
			sortingQry = "order by lcaRepView." + sortColumnName + " " +lcaReportViewDTO.getSortOrder();
		} 
		
		if(status != null && !status.isEmpty()){
			String[] statusFilter = status.split(",");
			
			if (Arrays.asList(statusFilter).contains("All")) {
			//	subQuery1 = subQuery1 + "lcaRepView.status = '1' and lcaRepView.status = '0' ";
				subQuery1 = subQuery1 +"lcaRepView.status = '1' or lcaRepView.status = '0' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Active")) {
				subQuery1 = subQuery1 + "lcaRepView.status = '1' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Inactive")) {
				subQuery1 = subQuery1 + "lcaRepView.status = '0' or ";
			}
			
			subQuery1 = "and (" + subQuery1.substring(0, subQuery1.length() - 4) + ")";
		}
        
		if(lcaStatus != null && !lcaStatus.isEmpty()){
			String[] statusFilter = lcaStatus.split(",");
			if (Arrays.asList(statusFilter).contains("Initiated")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'Initiated' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("In progress")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'In progress' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Certified")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'Certified' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Denied")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'Denied' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Withdrawn")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'Withdrawn' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Voided")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'Voided' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Invalidated")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'Invalidated' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Rejected")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'Rejected' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Expired")) {
				subQuery2 = subQuery2 + "lcaRepView.lcaStatus = 'Expired' or ";
			}
			
			subQuery2 = "and (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
		}
		
		Date daysFilter = null;
		boolean isDateFileterFound= false;
		if(StringUtils.isNotBlank(filterByDays) && !filterByDays.equalsIgnoreCase("All")) {
		
			//String[] filterDaysOptions = filterByDays.split(",");      
			isDateFileterFound = true;
			Calendar cal = Calendar.getInstance();
			/*for (String filteredByDays: filterDaysOptions) {   */
			cal.add(Calendar.DATE, Integer.parseInt(filterByDays));
			daysFilter = cal.getTime();
			/*}*/
			subQuery3 = "and lcaRepView.endDate <=:daysFilter"; 
		}
		
		if(positionsAvaialble != null && positionsAvaialble !="" && !positionsAvaialble.equalsIgnoreCase("All")){
			
            String[] positionAvaiable = positionsAvaialble.split(",");
			if (Arrays.asList(positionAvaiable).contains("Available")) {
				subQuery4 = subQuery4 + "lcaRepView.availablePositions <=lcaRepView.noOfPositions or ";
			}
			if (Arrays.asList(positionAvaiable).contains("Not Available")) {
				subQuery4 = subQuery4 + "lcaRepView.availablePositions = 0 or ";
			}
			subQuery4 = "and (" + subQuery4.substring(0, subQuery4.length() - 4) + ")";
		}
		
		String query = "from LcaReportView lcaRepView  where  lcaRepView.orgLocationId =:orgLocationId "+query1+"  "+subQuery1+"  "+subQuery2+"  "+subQuery3+" "+subQuery4+" "+sortingQry+"";
		String listQueryCount = "select count(*) from LcaReportView lcaRepView  where  lcaRepView.orgLocationId =:orgLocationId "+query1+"  "+subQuery1+"  "+subQuery2+"  "+subQuery3+" "+subQuery4+" "+sortingQry+"";
		
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
		
		lcaRepViewList = hqlQuery.setFirstResult(lcaReportViewDTO.getStart()).setMaxResults(lcaReportViewDTO.getLength()).list();
        
        countlist = hqlQuery1.list();
		int totalCount = 0;
		if (lcaReportViewDTO != null) {
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
       
        List<LcaReportViewDTO> tempalteDTOList = new ArrayList<LcaReportViewDTO>();
        for (LcaReportView source : lcaRepViewList) {
        	
        	LcaReportViewDTO target = new LcaReportViewDTO();
        	
        	target.setLcaNo(source.getLcaNo());
        	target.setLcaUuid(source.getLcaUuid());
        	target.setLcaTitle(source.getLcaTitle());
        	target.setNoOfPositions(source.getNoOfPositions());
        	target.setSocCode(source.getSocCode());
        	target.setSocOccupationTitle(source.getSocOccupationTitle());
        	target.setJobTitle(source.getJobTitle());
        	target.setPrevailingWage(source.getPrevailingWage());
        	target.setWagesLevel(source.getWagesLevel());
        	target.setLcaStatus(source.getLcaStatus());
        	target.setPrivilingWageCurrency(source.getPrivilingWageCurrency());
        	target.setPrivilingWageRateType(source.getPrivilingWageRateType());
        	target.setExpired(String.valueOf(source.getExpired()));
        	target.setLocationsList(Arrays.asList(source.getLocation().split("\n")));
        	target.setLocation(source.getLocation().replaceAll("", ""));
        	
        	if(source.getStatus().equals(1)) {
        	target.setStatus("Active");
        	}else {
        		target.setStatus("Inactive");
        	}
        	target.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),source.getStartDate()));
        	target.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),source.getEndDate()));
        	
        	target.setLocationsList(Arrays.asList(source.getLocation()));
        	target.setLocation(source.getLocation().replaceAll("", ""));
            tempalteDTOList.add(target);
        }
        responseList.setData(tempalteDTOList);
        return responseList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Lca updateAvailablePositionCountByLcaId(Long lcaId) throws Exception {
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
	public ResponseList<ImmigrationReportViewDTO> getImmigrationAttorneyReports(ImmigrationReportViewDTO immigrationReportDTO, OrgLocations orgLocations) {
		
		Session session = this.sessionFactory.getCurrentSession();
        ResponseList<ImmigrationReportViewDTO> responseList = new ResponseList<>();
        List<ImmigrationAttorneyReportView> immReportViewList = null;
        Query hqlQuery = null;
        Query hqlCountQuery = null;
		
        Date startDate = new Date(immigrationReportDTO.getFreqDates()[0].getTime());
		Date endDate = new Date(immigrationReportDTO.getFreqDates()[1].getTime());
		startDate.setHours(00);
		startDate.setMinutes(00);
		startDate.setSeconds(00);
		
		endDate.setHours(23);
		endDate.setMinutes(59);
		endDate.setSeconds(59);
		
		List<Long> countlist = null;
		List<String> petitionTypeList = immigrationReportDTO.getPetitionType();
		List<String> statusList = immigrationReportDTO.getPetitionStatus();
		List<String> attorneyList = immigrationReportDTO.getAttorneyId();
		
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotBlank(immigrationReportDTO.getSearchValue())) {
			query1.append(" and ((immRepView.generatedId like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.petitionType like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.client like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.firstName like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.email like '%" + immigrationReportDTO.getSearchValue() + "%' )");
			query1.append(" or (immRepView.phone like '%" + immigrationReportDTO.getSearchValue() + "%' ))");
		}
		
		String query = null;
		if(petitionTypeList.size()>0 && !petitionTypeList.contains("null") && statusList.size() >0) {
			query = "from ImmigrationAttorneyReportView immRepView  where  immRepView.attorneyOrgLocationId =:orgLocationId and immRepView.currUscisStatus IN (:statusList) and immRepView.petitionType IN (:petitionTypeList) and immRepView.client IN (:attorneyList) and immRepView.openDate between :startDate and :endDate"+query1+"";
			
			hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameterList("statusList", statusList).setParameterList("petitionTypeList", petitionTypeList).setParameterList("attorneyList", attorneyList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else if (!petitionTypeList.contains("null") && petitionTypeList.size()>0) {
			query = "from ImmigrationAttorneyReportView immRepView  where  immRepView.attorneyOrgLocationId =:orgLocationId and immRepView.client IN (:attorneyList) and immRepView.petitionType IN (:petitionTypeList) and immRepView.openDate between :startDate and :endDate"+query1+"";
			
			hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameterList("attorneyList", attorneyList).setParameterList("petitionTypeList", petitionTypeList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else if (statusList.size()>0 && !statusList.contains("null")) {
			query = "from ImmigrationAttorneyReportView immRepView  where  immRepView.attorneyOrgLocationId =:orgLocationId and immRepView.client IN (:attorneyList) and immRepView.currUscisStatus IN (:statusList) and immRepView.openDate between :startDate and :endDate"+query1+"";

			hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameterList("attorneyList", attorneyList).setParameterList("statusList", statusList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else {
			query = "from ImmigrationAttorneyReportView immRepView  where  immRepView.attorneyOrgLocationId =:orgLocationId and immRepView.client IN (:attorneyList) and immRepView.openDate between :startDate and :endDate"+query1;

			hqlQuery = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameterList("attorneyList", attorneyList).setDate("startDate", startDate).setDate("endDate",endDate);
		}
		String petitionQueryCount = null;
		
		if(petitionTypeList.size()>0 && !petitionTypeList.contains("null") && statusList.size() >0) {
			petitionQueryCount = "select count(*) from ImmigrationAttorneyReportView immRepView  where  immRepView.attorneyOrgLocationId =:orgLocationId and immRepView.currUscisStatus IN (:statusList) and immRepView.petitionType IN (:petitionTypeList) and immRepView.client IN (:attorneyList) and immRepView.openDate between :startDate and :endDate"+query1+"";
			
		    hqlCountQuery=session.createQuery(petitionQueryCount).setLong("orgLocationId", orgLocations.getId()).setParameterList("statusList", statusList).setParameterList("petitionTypeList", petitionTypeList).setParameterList("attorneyList", attorneyList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else if (!petitionTypeList.contains("null") && petitionTypeList.size()>0) {
			
			petitionQueryCount = "select count(*) from ImmigrationAttorneyReportView immRepView  where  immRepView.attorneyOrgLocationId =:orgLocationId and immRepView.client IN (:attorneyList) and immRepView.petitionType IN (:petitionTypeList) and immRepView.openDate between :startDate and :endDate"+query1+"";
			
			hqlCountQuery=session.createQuery(petitionQueryCount).setLong("orgLocationId", orgLocations.getId()).setParameterList("attorneyList", attorneyList).setParameterList("petitionTypeList", petitionTypeList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else if (statusList.size()>0 && !statusList.contains("null")) {
			petitionQueryCount = "select count(*) from ImmigrationAttorneyReportView immRepView  where  immRepView.attorneyOrgLocationId =:orgLocationId and immRepView.client IN (:attorneyList) and immRepView.currUscisStatus IN (:statusList) and immRepView.openDate between :startDate and :endDate"+query1+"";	
			
		   hqlCountQuery=session.createQuery(petitionQueryCount).setLong("orgLocationId", orgLocations.getId()).setParameterList("attorneyList", attorneyList).setParameterList("statusList", statusList).setDate("startDate", startDate).setDate("endDate",endDate);
		}else {
			petitionQueryCount = "select count(*) from ImmigrationAttorneyReportView immRepView  where  immRepView.attorneyOrgLocationId =:orgLocationId and immRepView.client IN (:attorneyList) and immRepView.openDate between :startDate and :endDate"+query1+"";
			
		    hqlCountQuery=session.createQuery(petitionQueryCount).setLong("orgLocationId", orgLocations.getId()).setParameterList("attorneyList", attorneyList).setDate("startDate", startDate).setDate("endDate",endDate);
		}
		immReportViewList = hqlQuery.setFirstResult(immigrationReportDTO.getStart()).setMaxResults(immigrationReportDTO.getLength()).list();
        int count =0;
		countlist=hqlCountQuery.list();
		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		responseList.setRecordsTotal(count);
		responseList.setRecordsFiltered(count);
		
		List<ImmigrationReportViewDTO> immRepViewDTOList = new ArrayList<ImmigrationReportViewDTO>();
        for (ImmigrationAttorneyReportView source : immReportViewList) {
        	
        	ImmigrationReportViewDTO target = new ImmigrationReportViewDTO();
        	target.setId(source.getGeneratedId());
        	target.setImmigrationUUID(source.getImmigrationUUID());
        	target.setCandidateUUID(source.getCandidateUUID());
        	target.setImmiTemplateUUID(source.getImmiTemplateUUID());
        	target.setPetitionType(Arrays.asList(source.getPetitionType()));
        	if(source.getCurrUscisStatus() != null) {
        	target.setCurrUscisStatus(source.getCurrUscisStatus());
        	}
        	target.setPetitionTypeName(source.getPetitionType());
        	target.setEmail(source.getEmail());
        	target.setPhoneNumber(source.getPhone());
        	target.setName(source.getFirstName());
        	target.setCountry(source.getCountry());
        	
        	target.setOpenDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getOpenDate()));
			target.setLcaStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaStartDate()));
			target.setLcaEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getLcaEndDate()));
			
		//	target.setOrgName(source.getVendor());
			
			if (orgLocations.getId().equals(source.getAttorneyOrgLocationId())) {
				target.setOrgName(source.getClient());
				target.setOrgNameSel(ImmigrationConstants.ORG_NAME_SELECTED_ORG1);
			}
			
            BeanUtils.copyProperties(source, target);
            immRepViewDTOList.add(target);
        }
        responseList.setData(immRepViewDTOList);
        return responseList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateVisaHistory saveVisaHistoaryDetails(CandidateVisaHistory visaHistory) {
		logger.debug("Storing LcaActivityHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(visaHistory != null) {
				session.saveOrUpdate(visaHistory);
				session.flush();
			}
		}catch(Exception e) {
			logger.error("Exception caught while saving candidate visa history details in Databse "+e.getMessage());
		}
		return visaHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateVisa getCandidateVisaDetailsByVisaNumber(String visaNumber) {
		logger.debug("Storing LcaActivityHistory");
		Session session = this.sessionFactory.getCurrentSession();
		CandidateVisa  canVisa = null;
		try {
			String hqlQuery = "from CandidateVisa cv where cv.visaNumebr=:visaNumber";
			canVisa =(CandidateVisa) session.createQuery(hqlQuery).setParameter("visaNumber", visaNumber).uniqueResult();
			
		}catch(Exception e) {
			logger.error("Exception caught while saving getting canidate visa details by visaNumber "+e.getMessage());
		}
		return canVisa;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateVisaHistory getCandidateVisaHistoryByStatus(Long visaId, String isCurrent) {
		Session session = this.sessionFactory.getCurrentSession();
		CandidateVisaHistory canVisaHistory = null;
		try {
			if (visaId != null) {
				logger.debug(" before query execute to get lcaHistory " + visaId);
				String queryString = "from CandidateVisaHistory cvh  where cvh.status=1 and cvh.isCurrent =:isCurrent and  cvh.visaId = :visaId order by cvh.id desc ";
				canVisaHistory = (CandidateVisaHistory) session.createQuery(queryString).setLong("visaId", visaId)
																		  .setString("isCurrent", isCurrent).setMaxResults(1).uniqueResult();
			}
		} catch (Exception exception) {
			logger.error("Exception caught while  lcaHistory information " + exception.getMessage());
			exception.printStackTrace();
		}
		return canVisaHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationVisaDTO getCandidateVisaDetailsByVisaHistoryId(Long visaHistoryId) {
		Session session = this.sessionFactory.getCurrentSession();
		ImmigrationVisaDTO visaDTO= null;
		try {
			if (visaHistoryId != null) {
				logger.debug(" before query execute to get  visa details by visa number  " + visaHistoryId);
				String sqlQueryString = "select cvh.ID as id,  cv.CANDIDATE_ID as candidateId, cvh.VISA_ID as candidateVisaId ,"+
						" cv.VISA_NUMBER as visaNumber, cv.VISA_TYPE as visaType, cvh.IS_CURRENT as isCurrent, " + 
						" cv.COUNTRY as country, CAST(cvh.VALID_FROM_DATE AS char) as validFrom," + 
						" CAST(cvh.VALID_TO_DATE AS char) as validTo " + 
						"  from CANDIDATE_VISA cv, CANDIDATE_VISA_HISTORY cvh  Where cv.ID= cvh.VISA_ID  "+
						"  and cvh.status = 1 and cvh.ID=:visaHistoryId ";
				visaDTO = (ImmigrationVisaDTO) session.createSQLQuery(sqlQueryString)
										   .addScalar("id", StandardBasicTypes.LONG)
										   .addScalar("candidateId", StandardBasicTypes.LONG)
										   .addScalar("candidateVisaId", StandardBasicTypes.LONG)
										   .addScalar("visaNumber", StandardBasicTypes.STRING)
										   .addScalar("country", StandardBasicTypes.STRING)
										   .addScalar("visaType", StandardBasicTypes.STRING)
										   .addScalar("isCurrent", StandardBasicTypes.STRING)
										   .addScalar("validFrom", StandardBasicTypes.STRING)
										   .addScalar("validTo", StandardBasicTypes.STRING)
										   .setLong("visaHistoryId", visaHistoryId)
										   .setResultTransformer(Transformers.aliasToBean(ImmigrationVisaDTO.class)).uniqueResult();
		  }
		} catch (Exception exception) {
			logger.error("Exception caught while  getting Visa History details >>>>>>>>" + exception.getMessage());
			exception.printStackTrace();
		}
		return visaDTO;
	}
	
	//getVisanumber
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationVisaDTO getCandidateActiveVisaDetailsByVisaNumberAndCountry(Long candidateId, String visaNumber, String country) {
		Session session = this.sessionFactory.getCurrentSession();
		ImmigrationVisaDTO visaDTO= null;
		try {
			if (visaNumber != null) {
				logger.debug(" before query execute to get  visa details by visa number  " + visaNumber);
				String sqlQueryString = "select cvh.ID as id,  cv.CANDIDATE_ID as candidateId, cvh.VISA_ID as candidateVisaId ,"+
						" cv.VISA_NUMBER as visaNumber, cv.VISA_TYPE as visaType, cvh.IS_CURRENT as isCurrent, " + 
						" cv.COUNTRY as country, CAST(cvh.VALID_FROM_DATE AS char) as validFrom," + 
						" CAST(cvh.VALID_TO_DATE AS char) as validTo " + 
						"  from CANDIDATE_VISA cv, CANDIDATE_VISA_HISTORY cvh  Where cv.ID= cvh.VISA_ID  "+
						"  and  cvh.IS_CURRENT  = 'Y' and cvh.status = 1 and cv.CANDIDATE_ID =:candidateId and cv.VISA_NUMBER=:visaNumber and cv.COUNTRY =:country order by cvh.id desc ";
				visaDTO = (ImmigrationVisaDTO) session.createSQLQuery(sqlQueryString)
										   .addScalar("id", StandardBasicTypes.LONG)
										   .addScalar("candidateId", StandardBasicTypes.LONG)
										   .addScalar("candidateVisaId", StandardBasicTypes.LONG)
										   .addScalar("visaNumber", StandardBasicTypes.STRING)
										   .addScalar("country", StandardBasicTypes.STRING)
										   .addScalar("visaType", StandardBasicTypes.STRING)
										   .addScalar("isCurrent", StandardBasicTypes.STRING)
										   .addScalar("validFrom", StandardBasicTypes.STRING)
										   .addScalar("validTo", StandardBasicTypes.STRING)
										   .setLong("candidateId", candidateId)
										   .setString("visaNumber", visaNumber)
										   .setString("country", country)
										   .setMaxResults(1)
										   .setResultTransformer(Transformers.aliasToBean(ImmigrationVisaDTO.class)).uniqueResult();
		  }
		} catch (Exception exception) {
			logger.error("Exception caught while  getting Active visa number and details  >>>>>>>>" + exception.getMessage());
			exception.printStackTrace();
		}
		return visaDTO;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationVisaDTO getCandidateVisaDetailsByVisaNumberAndCountry(Long candidateId, String visaNumber, String country) {
		Session session = this.sessionFactory.getCurrentSession();
		ImmigrationVisaDTO visaDTO= null;
		try {
			if (visaNumber != null) {
				logger.debug(" before query execute to get  visa details by visa number  " + visaNumber);
				String sqlQueryString = "select cvh.ID as id,  cv.CANDIDATE_ID as candidateId, cvh.VISA_ID as candidateVisaId ,"+
						" cv.VISA_NUMBER as visaNumber, cv.VISA_TYPE as visaType, cvh.IS_CURRENT as isCurrent, " + 
						" cv.COUNTRY as country, CAST(cvh.VALID_FROM_DATE AS char) as validFrom," + 
						" CAST(cvh.VALID_TO_DATE AS char) as validTo " + 
						"  from CANDIDATE_VISA cv, CANDIDATE_VISA_HISTORY cvh  Where cv.ID= cvh.VISA_ID  "+
						"  and cvh.status = 1 and cv.CANDIDATE_ID =:candidateId and cv.VISA_NUMBER=:visaNumber and cv.COUNTRY =:country order by cvh.id desc ";
				visaDTO = (ImmigrationVisaDTO) session.createSQLQuery(sqlQueryString)
										   .addScalar("id", StandardBasicTypes.LONG)
										   .addScalar("candidateId", StandardBasicTypes.LONG)
										   .addScalar("candidateVisaId", StandardBasicTypes.LONG)
										   .addScalar("visaNumber", StandardBasicTypes.STRING)
										   .addScalar("country", StandardBasicTypes.STRING)
										   .addScalar("visaType", StandardBasicTypes.STRING)
										   .addScalar("isCurrent", StandardBasicTypes.STRING)
										   .addScalar("validFrom", StandardBasicTypes.STRING)
										   .addScalar("validTo", StandardBasicTypes.STRING)
										   .setLong("candidateId", candidateId)
										   .setString("visaNumber", visaNumber)
										   .setString("country", country)
										   .setMaxResults(1)
										   .setResultTransformer(Transformers.aliasToBean(ImmigrationVisaDTO.class)).uniqueResult();
		  }
		} catch (Exception exception) {
			logger.error("Exception caught while  getting Active visa number and details  >>>>>>>>" + exception.getMessage());
			exception.printStackTrace();
		}
		return visaDTO;
	}
	@Override
	@Transactional
	public int updateCanVisaHistoryDetails(Long visaId, Long visaHistoryId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update CandidateVisaHistory cvh set cvh.isCurrent='N', cvh.status=0 where cvh.id !=:visaHistoryId and cvh.visaId =:visaId");
	    query.setLong("visaHistoryId", visaHistoryId);  
	    query.setLong("visaId", visaId);  
	    int result = query.executeUpdate();
	    return result;
	}
	
	@Override
	@Transactional
	public Lca updateLcaAvailablePositionsById(Lca lca)
	{
		Session session = this.sessionFactory.getCurrentSession();
		if(lca.getId() != null ) {
			session.saveOrUpdate(lca);
	   }
	    return lca;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Lca getLcaNumber(String lcaNo) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Lca lca where lca.lcaNo = :lcaNo and lca.status = 1";
		List<Lca> lcaNumbersList =  session.createQuery(query) .setString("lcaNo", lcaNo).list();
		Lca lca = null;
		if(lcaNumbersList != null && !lcaNumbersList.isEmpty())
		{
			lca = (Lca) lcaNumbersList.iterator().next();
		}
		return lca;
		
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidatePassport> getAllPassportList(Date expectedExpiryDate) {
		try{
		Session session = this.sessionFactory.getCurrentSession();
		String listQuery = "from CandidatePassport cp where  cp.validToDate=:expectedExpiryDate and cp.status = "+Constants.RECORD_ACTIVE+" order by cp.createdBy.id";
		List<CandidatePassport> candidatePassportList = session.createQuery(listQuery).setDate("expectedExpiryDate",expectedExpiryDate).list();
		return candidatePassportList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateVisaHistory> getAllVisaHistoryList(Date expectedExpiryDate) {
		try{
			Session session = this.sessionFactory.getCurrentSession();
			String listQuery = "from CandidateVisaHistory cvh where  cvh.validToDate=:expectedExpiryDate and cvh.status = "+Constants.RECORD_ACTIVE+" order by cvh.createdBy.id";
			List<CandidateVisaHistory> candidateVisaHistoryList = session.createQuery(listQuery).setDate("expectedExpiryDate",expectedExpiryDate).list();
			return candidateVisaHistoryList;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}
}	
