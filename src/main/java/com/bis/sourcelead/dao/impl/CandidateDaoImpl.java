package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.CandidateSourceDTO;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.MarketplaceWalletDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateImportLog;
import com.bis.sourcelead.entity.CandidateMarketCart;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.CandidateMarketPlacePaymentTransactionHistory;
import com.bis.sourcelead.entity.CandidateMarketWallet;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
import com.bis.sourcelead.entity.CandidateSource;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.MarketplaceSalesOrder;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecruiterReportCandidateCountView;
import com.bis.sourcelead.entity.SourceleadDeletedCandidates;
import com.bis.sourcelead.entity.SubmissionCandidateListView;
import com.bis.sourcelead.entity.TicketType;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.ReportsUtil;

/**
 * 
 * @author Prasad K
 * @date 29th Mar 2016
 *
 */
@Repository
public class CandidateDaoImpl implements CandidateDao {

	private static final Logger logger = LoggerFactory.getLogger(CandidateDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ProjectsDao ProjectsDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private CandidateResumesDao candidateResumesDao;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	CandidateService candidateService;

	@Autowired
	private RequirementSubmissionDao requirementSubmissionDao;

	
	@Autowired
	private OrgLocationService orgLocationService;
	
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

	@Autowired
	private ReportsUtil reportsUtil;

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	/**
	 * @author prasad k
	 * @since sep 1 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save candidate in candidate table.
	 *          1.internalley store/save in candidateEducation in
	 *          candidateEducation table . 2.internalley store/save in
	 *          candidateSkills in candidateSkills table . 3.internalley
	 *          store/save in candidateResumes in candidateResumes table .
	 *          4.internalley store/save in candidateEmployers in
	 *          candidateEmployers table .
	 * 
	 * @param candidate
	 *            This is the first paramter to storeCandidate method
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointer
	 */

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate storeCandidate(Candidate candidate) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidate != null) {
				session.saveOrUpdate(candidate);
			}
			logger.debug("Candidate Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "Candidate"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
		return candidate;
	}

	/**
	 * to Candidates Count by orgLocationId and candidate status for candidate
	 * elastic search
	 * 
	 * @author Naresh Bolisetty
	 * 
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId, String candidateStatus) {
		logger.debug("..getCandidatesCount() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = null;
		if (null != candidateStatus && !candidateStatus.isEmpty()
				&& candidateStatus.equalsIgnoreCase(Constants.ACTIVE)) {
			subQuery = "and candidate.status = '1'";
		} else if (null != candidateStatus && !candidateStatus.isEmpty()
				&& candidateStatus.equalsIgnoreCase(Constants.INACTIVE)) {
			subQuery = "and candidate.status = '0'";
		} else {
			subQuery = "and (candidate.status = '1' or candidate.status = '0')";
		}

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId "
				+ subQuery + " ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidatesCountByOrgLocationIDAndStatus(Long orgLocationId, String candidateStatus,
			String isAssociate) {
		logger.debug("..getCandidatesCount() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = null;
		if (null != candidateStatus && !candidateStatus.isEmpty()
				&& candidateStatus.equalsIgnoreCase(Constants.ACTIVE)) {
			subQuery = "and candidate.status = '1'";
		} else if (null != candidateStatus && !candidateStatus.isEmpty()
				&& candidateStatus.equalsIgnoreCase(Constants.INACTIVE)) {
			subQuery = "and candidate.status = '0'";
		} else {
			subQuery = "and (candidate.status = '1' or candidate.status = '0')";
		}

		String subQuery1 = null;
		if (isAssociate.equalsIgnoreCase(Constants.YES)) {
			subQuery1 = "and candidate.isAssociate = 'Y'";
		} else {
			subQuery1 = "and candidate.isAssociate = 'Y' OR candidate.isAssociate = 'N'";
		}

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId "
				+ subQuery1 + "  " + subQuery + " ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
	}

	/**
	 * to Candidates List by OrgCode OrgLocationCode
	 * 
	 * @author David Sammeta
	 * 
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId, Long orgId, Integer candidateState,
			String updatedDateRange, String candidateType, String isAssociate, String candidateFilterSkills,
			String availability, String candidateTypeFilter, String createdByFilter, User user) throws Exception {

		String subQuery = null;
		Integer dateRange;
		String updatedDate = "";
		Date currentDate = commonUtil.currentDate();
		List<Candidate> candidateList;
		java.sql.Date sqlCurrentDate = null;

		// for current Date Converting to Sql Date Format
		if (currentDate != null) {
			sqlCurrentDate = new java.sql.Date(currentDate.getTime());
		}

		// Checking for Candidate Status
		Session session = this.sessionFactory.getCurrentSession();
		String dateLimit = "";
		if (candidateState.equals(Constants.CANDIDATE_ACTIVE)) {
			subQuery = "(cnadidate.status = 1)";
		} else if (candidateState.equals(Constants.CANDIDATE_ALL)) {
			subQuery = "(cnadidate.status = 1 or cnadidate.status = 0)";
		} else {
			subQuery = "(cnadidate.status = 0)";
		}

		String candidateTypeSubQuery = "";

		if (candidateType != null && !candidateType.isEmpty() && candidateType.equalsIgnoreCase("Favourite")) {
			candidateTypeSubQuery = " and (cnadidate.isFavourite = '1') ";

		}

		String createdBySubQuery = "";
		if (createdByFilter != null && !createdByFilter.isEmpty() && createdByFilter.equalsIgnoreCase("myCandidates")) {
			createdBySubQuery = " and cnadidate.createdBy = '" + user.getUserCode() + "' ";
		}

		List<String> skillsList = new ArrayList<>();
		if (StringUtils.isNotBlank(candidateFilterSkills)) {
			String[] skills = candidateFilterSkills.split(",");
			for (int i = 0; i < skills.length; i++) {
				skillsList.add(skills[i]);
			}

		} else {
			skillsList.add("0");
		}

		List<String> avalibilityList = new ArrayList<>();
		if (StringUtils.isNotBlank(availability)) {
			String[] availabilityStr = availability.split(",");
			for (int i = 0; i < availabilityStr.length; i++) {
				avalibilityList.add(availabilityStr[i]);
			}

		} else {
			avalibilityList.add("0");
		}

		String candidateTypeFilterSubQuery = "";
		if (StringUtils.isNotBlank(candidateTypeFilter)) {
			String[] candidateTypeFilterStr = candidateTypeFilter.split(",");

			if (candidateTypeFilterStr.length == 2) {
				candidateTypeFilterSubQuery = " and (cnadidate.isAssociate = 'N' OR cnadidate.isAssociate = 'Y' )";
			} else {
				if (candidateTypeFilter.equalsIgnoreCase("Associates")) {
					candidateTypeFilterSubQuery = " and cnadidate.isAssociate = 'Y'";
				} else if (candidateTypeFilter.equalsIgnoreCase("Candidates")) {
					candidateTypeFilterSubQuery = " and cnadidate.isAssociate = 'N'";
				}
			}
		}

		String candidateSkillsSubQuery = " and ((:varZero  IN (:skillsList)) OR (:varZero NOT IN (:skillsList) and cnadidate.skillsName IN (:skillsList)))";
		String avaliabilitySubQuery = " and ((:varZero  IN (:avalibilityList)) OR (:varZero NOT IN (:avalibilityList) and cnadidate.noticePeriod IN (:avalibilityList)))";

		if (StringUtils.isNotEmpty(updatedDateRange) && !"null".equalsIgnoreCase(updatedDateRange)
				&& !updatedDateRange.equalsIgnoreCase("undefined")) {

			// This Code is for getting the sql enddate based on range
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // Now use today date.
			dateRange = Integer.parseInt(updatedDateRange);
			c.add(Calendar.DATE, -dateRange); // Subtracting dateRange days
			updatedDate = dateFormat.format(c.getTime());
			Date parsed = dateFormat.parse(updatedDate);
			java.sql.Date endDate = new java.sql.Date(parsed.getTime());

			String query = "from Candidate cnadidate left join fetch cnadidate.orgLocationId orglocation where  cnadidate.orgLocationId.id =:orgLocationId and  orglocation.org.id =:orgId and "
					+ subQuery + " " + candidateTypeSubQuery + " " + candidateSkillsSubQuery + " "
					+ avaliabilitySubQuery + " " + candidateTypeFilterSubQuery + " " + createdBySubQuery
					+ " and ( cnadidate.updatedDate >= :endDate) ORDER BY cnadidate.id DESC ";
			candidateList = session.createQuery(query).setDate("endDate", endDate)
					.setLong("orgLocationId", orgLocationId).setString("varZero", "0")
					.setParameterList("skillsList", skillsList).setParameterList("avalibilityList", avalibilityList)
					.setLong("orgId", orgId)

					.list();
		} else {

			String query = "from Candidate cnadidate  left join fetch cnadidate.orgLocationId orglocation where cnadidate.orgLocationId.id =:orgLocationId and  orglocation.org.id =:orgId and "
					+ subQuery + " " + candidateTypeSubQuery + " " + candidateSkillsSubQuery + " "
					+ avaliabilitySubQuery + " " + candidateTypeFilterSubQuery + " " + createdBySubQuery
					+ " ORDER BY cnadidate.id DESC";
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgId", orgId)
					.setString("varZero", "0").setParameterList("skillsList", skillsList)
					.setParameterList("avalibilityList", avalibilityList).list();
		}

		return candidateList;
	}

	/**
	 * to get Candidate By CandidateId
	 * 
	 * @author Venkat Kesav
	 * 
	 * @param candidateId
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  " + "left join fetch cnadidate.consultantUser "
				+ "left join fetch cnadidate.teamId " + "left join fetch cnadidate.recruiterUserId "
				+ "left join fetch cnadidate.masterCandidateId " + "left join fetch cnadidate.parentCandidateId "
				+ "left join fetch cnadidate.orgRelationId left "
				+ "join fetch cnadidate.spocId left join fetch cnadidate.orgLocationId left join fetch cnadidate.orgLocationId.user where cnadidate.id =:candidateId";
		List candidateList = session.createQuery(query).setLong("candidateId", candidateId).list();
		Candidate candidate = null;
		if (candidateList != null && !candidateList.isEmpty()) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}

	/**
	 * @author David Raju Sammeta
	 * 
	 *         This Method is for getting Candidate by Candidate Id OrgId and
	 *         OrgLocationID
	 * 
	 * @return Candidate
	 * 
	 * @exception NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate left join fetch cnadidate.parentCandidateId  where cnadidate.id = :candidateId and cnadidate.orgLocationId.id =:orgLocationId";
		List<Candidate> candidateList = session.createQuery(query).setLong("candidateId", candidateId)
				.setLong("orgLocationId", orgLocationId).list();
		Candidate candidate = null;
		if (candidateList != null && !candidateList.isEmpty()) {
			candidate = candidateList.iterator().next();
		}
		return candidate;

	}

	/**
	 * @author prasad.k
	 * 
	 *         This mothod is used to get CandidateEmail
	 * 
	 * @param email
	 * 
	 * @return CandidateEmail
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate findByCandidateEmail(String email, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("findByCandidateEmail start");
		try {
			candidate = session
					.createQuery(
							"from Candidate can left join fetch can.consultantUser left join fetch can.orgRelationId left join fetch can.parentCandidateId left join fetch can.masterCandidateId where can.email=:email and can.orgLocationId.id = :orgLocationId ORDER BY can.id DESC")
					.setString("email", email).setLong("orgLocationId", orgLocationId).list();

			logger.debug("findByCandidateEmail end");
			if (candidate.size() > 0) {
				return candidate.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId, Long requirementId, String candidateType)
			throws Exception {
		logger.debug("In getCandidatesByOrgLocationID method");
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidatesList = null;
		Query candidateListQuery = session.createQuery(
				"from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.candidateType = :candidateType "
						+ "and candidate.status = '1' and candidate.id NOT IN (select submission.candidate.id from RequirementSubmission submission "
						+ "where submission.requirement.id = :requirementId) ORDER BY candidate.id DESC ");
		candidatesList = candidateListQuery.setLong("orgLocationId", orgLocationId)
				.setLong("requirementId", requirementId).setString("candidateType", candidateType).list();
		return candidatesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId ORDER BY cnadidate.id DESC";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<Candidate> getAllCandidatesByCreatedByUserCode(CandidateDTO candidateDTO,
			OrgLocations orgLocations, String userCode, Date[] searchDates) {
		Session session = this.sessionFactory.getCurrentSession();

		List<Candidate> candidateList = null;
		ResponseList<Candidate> response = new ResponseList<>();
		Query queryObj = null;
		List<Long> countlist = null;

		StringBuilder query1 = new StringBuilder("");
		if (candidateDTO.getSearchValue() != null && !"".equalsIgnoreCase(candidateDTO.getSearchValue())) {
			query1.append(" and (cnadidate.email like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR cnadidate.mobilePhone like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR (concat(cnadidate.firstName,cnadidate.lastName) like '%"
							+ candidateDTO.getSearchValue().trim() + "%')")
					.append("OR cnadidate.designationType like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR cnadidate.location like '%" + candidateDTO.getSearchValue() + "%' )");

		}

		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId and cnadidate.createdBy =:userCode "
				+ query1
				+ " and (date(cnadidate.createdDate) BETWEEN :fromDate AND :toDate) ORDER BY cnadidate.id DESC";
		queryObj = session.createQuery(query);

		queryObj.setFirstResult(candidateDTO.getStart());
		queryObj.setMaxResults(candidateDTO.getLength());

		candidateList = queryObj.setLong("orgLocationId", orgLocations.getId()).setString("userCode", userCode)
				.setParameter("fromDate", searchDates[0]).setParameter("toDate", searchDates[1]).list();

		countlist = session.createQuery("select count(*) " + query).setLong("orgLocationId", orgLocations.getId())
				.setString("userCode", userCode).setParameter("fromDate", searchDates[0])
				.setParameter("toDate", searchDates[1]).list();

		int totalCount = 0;
		if (candidateDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		}
		response.setRecordsTotal(totalCount);
		response.setRecordsFiltered(totalCount);
		for (Candidate c : candidateList) {
			try {
				CandidateResumes canResume = candidateResumesDao.getCandidateResumeByCandidateId(c.getId());
				Integer submittedJobCount = requirementSubmissionDao
						.getRequirementSubmissionCountByCandiateIdAndOrgLocationId(c.getId(), orgLocations.getId());
				if (submittedJobCount > 0) {
					c.setCandidateAssignedJobsCount(submittedJobCount.toString());
				}
				if (canResume != null) {
					c.setCandidateResume(canResume.getFileURL());
				}
				c.setCreatedDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), c.getCreatedDate()));
				// c.setCreatedDateStr(createdDateStr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		response.setData(candidateList);

		return response;
	}

	@Override
	@Transactional
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId, String candidateType)
			throws Exception {

		// Checking for Candidate Status
		Session session = this.sessionFactory.getCurrentSession();

		String candidateTypeSubQuery = "";
		if (candidateType.equalsIgnoreCase("All")) {
			candidateTypeSubQuery = " and (candidate.candidateType = 'Candidate' OR candidate.candidateType = 'Consultant')";
		} else {
			candidateTypeSubQuery = " and candidate.candidateType = '" + candidateType + "' ";
		}

		String query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.isFavourite = '1' and candidate.teamId IS NULL "
				+ candidateTypeSubQuery + " ";
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();

		return candidateList;
	}

	@Override
	@Transactional
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId) throws Exception {

		logger.debug("In getAllRequestedCandidatesForCampaign method");
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Candidate candidate  where candidate.id IN (:candidateId)";
		List<Candidate> candidateList = session.createQuery(query).setParameterList("candidateId", candidateId).list();
		return candidateList;
	}

	@Override
	@Transactional
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(*) from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.teamId.id = :teamId ";
		List<Long> favouriteCount = session.createQuery(query.toString()).setLong("teamId", teamId)
				.setLong("orgLocationId", orgLocationId).list();
		Integer count = 0;
		if (favouriteCount != null && !favouriteCount.isEmpty()) {
			count = favouriteCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId, Long orgLocationId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and candidate.teamId.id = :teamId  ";
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId)
				.setLong("teamId", teamId).list();
		return candidateList;
	}

	@Override
	@Transactional
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId, String type) throws Exception {
		logger.debug("In getAllActiveCandidatesCountForCampaignByLocation method");
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = null;
		if (type.equalsIgnoreCase(Constants.ASSOCIATE)) {
			subQuery = "candidate.isAssociate = 'Y' ";
		} else {
			subQuery = "candidate.isAssociate = 'N' ";
		}

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and "
				+ subQuery
				+ " and candidate.status = '1' and (candidate.isFavourite = '0' OR candidate.isFavourite IS NULL) ";
		List<Long> candidatesCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.list();
		Integer count = 0;
		if (candidatesCount != null && !candidatesCount.isEmpty()) {
			count = candidatesCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId) throws Exception {

		logger.debug("In getAllHotlistCandidatesCountNotAssignedToTeamByLocation method");
		Session session = this.sessionFactory.getCurrentSession();

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1' and (candidate.isFavourite = '1' OR candidate.isFavouriteAssociate = '1') and candidate.teamId IS NULL ";
		List<Long> candidatesCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.list();
		Integer count = 0;
		if (candidatesCount != null && !candidatesCount.isEmpty()) {
			count = candidatesCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId, String type, Long orgLocationId)
			throws Exception {

		logger.debug("In getAllCandidatesCountForCampaignByTeam method");
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = null;
		if (type.equalsIgnoreCase(Constants.ASSOCIATE)) {
			subQuery = "candidate.isAssociate = 'Y' ";
		} else {
			subQuery = "candidate.isAssociate = 'N' ";
		}

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and "
				+ subQuery + " and candidate.status = '1' and candidate.teamId.id IN (:teamId) ";
		List<Long> candidatesCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.setParameterList("teamId", teamId).list();
		Integer count = 0;
		if (candidatesCount != null && !candidatesCount.isEmpty()) {
			count = candidatesCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId, Integer dateRange,
			Long reportingManagerUserId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = null;
		String updatedDate = "";
		Date currentDate = commonUtil.currentDate();
		List<Candidate> candidateList;
		java.sql.Date sqlCurrentDate = null;

		// for current Date Converting to Sql Date Format
		if (currentDate != null) {
			sqlCurrentDate = new java.sql.Date(currentDate.getTime());
		}

		// This Code is for getting the sql enddate based on range
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		dateRange = dateRange;
		c.add(Calendar.DATE, +dateRange); // Adding The DateRange days
		updatedDate = dateFormat.format(c.getTime());
		Date parsed = dateFormat.parse(updatedDate);
		java.sql.Date endDate = new java.sql.Date(parsed.getTime());

		subQuery = " from Candidate candidate where candidate.orgLocationId.id =:orgLocationId  and candidate.consultantUser.id IN (select userRole.user.id  from UserRole  userRole where userRole.reportingManager.id =:reportingManagerUserId )  and  candidate.candidateType = 'Consultant' and candidate.isFavourite <> '1'  and candidate.consultantUser.id IN (select emp.user.id from Employees emp where emp.id IN (select pm.employee.id from ProjectMembers pm where pm.id IN ( select pmh.projectMemberId.id from ProjectMembersHistory pmh  where  pmh.projectTerminationDate BETWEEN :sqlCurrentDate AND :endDate)  ) )";

		candidateList = session.createQuery(subQuery).setDate("sqlCurrentDate", sqlCurrentDate)
				.setDate("endDate", endDate).setLong("reportingManagerUserId", reportingManagerUserId)
				.setLong("orgLocationId", orgLocationId)

				.list();
		return candidateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getProfessionalInfoByLoginUserIdAndCandidateType(Long userId, String candidateType)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.consultantUser =:userId and candidate.candidateType =:candidateType ";
		List<Candidate> candidateList = session.createQuery(query).setLong("userId", userId)
				.setString("candidateType", candidateType).list();
		Candidate candidate = null;
		if (candidateList != null && candidateList.size() > 0) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByEmailAndRecruiterUserId(String email, Long recruiterUserId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.email = :email and candidate.candidateType = 'Candidate' and candidate.recruiterUserId.id = :recruiterUserId ";
		List<Candidate> candidateList = session.createQuery(query).setLong("recruiterUserId", recruiterUserId)
				.setString("email", email).list();
		Candidate candidate = null;
		if (candidateList != null && candidateList.size() > 0) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesByOrgLocationIDAndSearchText(Long orgLocationId, Long requirementId,
			String candidateType, String searchApplicant) throws Exception {
		logger.debug("In getCandidatesByOrgLocationID method");
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidatesList = null;
		String subQuery = "";
		if (!isNullOrEmpty(searchApplicant)) {
			subQuery = "and (candidate.firstName LIKE '" + searchApplicant + "%') OR (candidate.lastName LIKE '"
					+ searchApplicant + "%')";
		}

		Query candidateListQuery = session.createQuery(
				"from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.candidateType = :candidateType "
						+ "and candidate.status = '1' and candidate.id NOT IN (select submission.candidate.id from RequirementSubmission submission "
						+ "where submission.requirement.id = :requirementId) " + subQuery
						+ " ORDER BY candidate.id DESC ");
		candidatesList = candidateListQuery.setLong("orgLocationId", orgLocationId)
				.setLong("requirementId", requirementId).setString("candidateType", candidateType).list();
		return candidatesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(OrgLocations orgLocations,
			User user) throws Exception {

		java.sql.Date sqlCurrentDate = null;
		Date currentDate = commonUtil.currentDate();
		// for current Date Converting to Sql Date Format
		if (currentDate != null) {
			sqlCurrentDate = new java.sql.Date(currentDate.getTime());
		}

		List<Long> candidatesList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(*)  from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId  and cnadidate.createdBy =:userCode and  DATE(cnadidate.createdDate) = :sqlCurrentDate   ORDER BY cnadidate.id DESC";
		candidatesList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId())
				.setString("userCode", user.getUserCode()).setDate("sqlCurrentDate", sqlCurrentDate)

				.list();

		Integer count = 0;
		if (candidatesList != null && !candidatesList.isEmpty()) {
			count = candidatesList.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidatesCountByCandidateTypeOrgLocationID(Long userId, Long orgLocationId, String candidateType) {

		logger.debug("..getCandidatesCount() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = null;
		if (null != candidateType && !candidateType.isEmpty() && candidateType.equalsIgnoreCase(Constants.CONSULTANT)) {
			subQuery = "and candidateType = 'Consultant'";
		} else {
			subQuery = "and candidateType = 'Candidate'";
		}

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId "
				+ subQuery + " ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidateReports(Long orgLocationId, String candidateType, String filterDate,
			Date startDateUTC, Date endDateUTC, String ownersList) {

		List<String> userCodesList = new ArrayList<String>();
		if (null != ownersList && !"".equals(ownersList)) {
			for (String s : ownersList.split(",")) {
				userCodesList.add(s);
			}
		}

		Session session = this.sessionFactory.getCurrentSession();
		Map<String, Object> params = new HashMap<String, Object>();

		String subQuery1 = "";
		if (userCodesList != null && userCodesList.size() > 0) {
			subQuery1 = " and candidate.createdBy IN (:userCodesList)";
			params.put("userCodesList", userCodesList);
		}

		String dateSubQuery = "";
		if (null != startDateUTC && null != endDateUTC) {
			dateSubQuery = "and (candidate.createdDate >= :startDateUTC and candidate.createdDate <= :endDateUTC)";
			params.put("startDateUTC", startDateUTC);
			params.put("endDateUTC", endDateUTC);
		}

		String subQuery = "";
		if (null != candidateType && !candidateType.isEmpty() && candidateType.equalsIgnoreCase(Constants.CONSULTANT)) {
			subQuery = "and candidateType = 'Consultant'";
		} else {
			subQuery = "and candidateType = 'Candidate'";
		}

		String from = "from Candidate candidate where candidate.orgLocationId.id =:orgLocationId " + subQuery + " "
				+ subQuery1 + " " + dateSubQuery;
		params.put("orgLocationId", orgLocationId);

		Query query = session.createQuery(from);
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId")) {
				query.setParameter(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("startDateUTC")) {
				query.setParameter(str, (Date) params.get(str));
			}
			if (str.equalsIgnoreCase("endDateUTC")) {
				query.setParameter(str, (Date) params.get(str));
			}
			if (str.equalsIgnoreCase("userCodesList")) {
				query.setParameterList("userCodesList", userCodesList);
			}

		}

		return query.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByOrglocationId(Long orgLocationId, String employeementType)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<Candidate> candidateList = null;

		String subQuery = " and candidate.id NOT IN (select onboarding.candidateId.id from CandidateOnBoarding onboarding where onboarding.orgLocationId.id = :orgLocationId and onboarding.candidateId IS NOT NULL )";

		if (!employeementType.equalsIgnoreCase("All")) {
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1'  and candidate.designationType =:employeementType and candidate.consultantUser IS NULL "
					+ subQuery + " ";
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId)
					.setString("employeementType", employeementType).list();
		} else {
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1'  and candidate.consultantUser IS NULL "
					+ subQuery + " ";
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}

		return candidateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getOwnersByCandidates(Long orgLocationId, String candidateType, String ownersList) {

		List<String> userCodesList = new ArrayList<String>();
		if (!"".equalsIgnoreCase(ownersList) && ownersList != null) {
			for (String s : ownersList.split(",")) {
				userCodesList.add(s);
			}
		}

		Session session = this.sessionFactory.getCurrentSession();
		Map<String, Object> params = new HashMap<String, Object>();

		String subQuery1 = null;
		if (userCodesList != null && userCodesList.size() > 0) {
			subQuery1 = " and candidate.createdBy IN (:userCodesList)";
			params.put("userCodesList", userCodesList);
		}

		String subQuery = null;
		if (null != candidateType && !candidateType.isEmpty() && candidateType.equalsIgnoreCase(Constants.CONSULTANT)) {
			subQuery = "and candidateType = 'Consultant'";
		} else {
			subQuery = "and candidateType = 'Candidate'";
		}

		String from = "from Candidate candidate where candidate.orgLocationId.id =:orgLocationId " + subQuery + "  "
				+ subQuery1;
		params.put("orgLocationId", orgLocationId);

		Query query = session.createQuery(from);
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId")) {
				query.setParameter(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("userCodesList")) {
				query.setParameterList("userCodesList", userCodesList);
			}
		}

		return query.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidateReportsListByOrgLocationID(Long orgLocationId, String candidateType)
			throws Exception {

		// Checking for Candidate Status
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = null;
		if (null != candidateType && !candidateType.isEmpty() && candidateType.equalsIgnoreCase(Constants.CONSULTANT)) {
			subQuery = "and candidateType = 'Consultant'";
		} else {
			subQuery = "and candidateType = 'Candidate'";
		}

		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId " + subQuery + " ";
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();

		return candidateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getHotCandidateListByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.teamId.id =:teamId and candidate.orgLocationId.id =:orgLocationId  ";
		List<Candidate> candidateHotList = session.createQuery(query.toString()).setLong("teamId", teamId)
				.setLong("orgLocationId", orgLocationId).list();
		return candidateHotList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String timePeriod)
			throws Exception {

		String query = "select count(*)from Candidate candidate where candidate.orgLocationId.id =:orgLocationId and candidate.createdBy =:userCode";
		Session session = this.sessionFactory.getCurrentSession();
		long count = ((Long) session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.setString("userCode", user.getUserCode()).uniqueResult()).intValue();

		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveCandidatesCountForCampaignByLocationAndStatus(Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.isFavouriteAssociate = '1' and candidate.isAssociate ='Y' and candidate.teamId IS NULL  ";
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();

		return candidateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateById(Long candidateId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("getCandidateById start");
		candidate = session
				.createQuery(
						"from Candidate candidate left join fetch candidate.consultantUser where candidate.id =:candidateId")
				.setLong("candidateId", candidateId).list();
		logger.debug("getCandidateById end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public BigInteger getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		BigInteger count = null;

		if (StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {

			query = "SELECT" + " COUNT(CAND.ID) AS CAND_COUNT" + " FROM" + " CANDIDATE CAND," + " ORG_LOCATIONS OL"

					+ " WHERE  OL.ID = CAND.ORG_LOCATION_ID AND " + " CAND.CREATED_BY = :userCode"
					+ " AND DATE_FORMAT(CAND.CREATED_DATE ,'%Y-%m-%d') BETWEEN '" + startDateUTC + "' AND '"
					+ endDateUTC + "'" + " AND  OL.ORG_ID = :orgId"
					+ " GROUP BY CAND.CREATED_BY, OL.ORG_ID, CAND.ORG_LOCATION_ID";

			Query queryString = session.createSQLQuery(query);
			queryString.setParameter("userCode", user.getUserCode()).setParameter("orgId", orgId);
			count = (BigInteger) queryString.uniqueResult();

		} else {

			query = "SELECT" + " COUNT(CAND.ID) AS CAND_COUNT" + " FROM" + " CANDIDATE CAND " + " WHERE"
					+ " CAND.CREATED_BY = :userCode" + " AND DATE_FORMAT(CAND.CREATED_DATE ,'%Y-%m-%d') BETWEEN '"
					+ startDateUTC + "' AND '" + endDateUTC + "'" + " AND CAND.ORG_LOCATION_ID = :orgLocationId"
					+ " GROUP BY CAND.CREATED_BY, CAND.ORG_LOCATION_ID";

			Query queryString = session.createSQLQuery(query);
			queryString.setParameter("userCode", user.getUserCode()).setParameter("orgLocationId", orgLocationId);
			count = (BigInteger) queryString.uniqueResult();

		}

		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByUUID(String candidateUUID) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("getCandidateById start");
		candidate = session.createQuery("from Candidate candidate where candidate.candidateUUID = :candidateUUID")
				.setString("candidateUUID", candidateUUID).list();
		logger.debug("getCandidateById end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveCandidatesByLocation(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId and cnadidate.status ='1' and cnadidate.consultantUser !=null ORDER BY cnadidate.id DESC";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByLocation(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.status ='1' and candidate.isAssociate = 'Y' ORDER BY candidate.id DESC";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Map<String, BigInteger>> getAllActiveCandidatesByLocationName(Long orgLocationId, String name) {
		Session session = this.sessionFactory.getCurrentSession(); // below
																	// query is
																	// used for
																	// auto
																	// populate
																	// in UI.
		String query = "SELECT LOWER(CONCAT(FIRST_NAME,' ',LAST_NAME,'(',EMAIL,')')) as label, ID as value FROM CANDIDATE WHERE STATUS ='1' AND CONSULTANT_USER_ID IS NOT NULL AND ORG_LOCATION_ID =:orgLocationId AND ( EMAIL LIKE :name  OR LOWER(CONCAT(FIRST_NAME,LAST_NAME)) LIKE :name OR LOWER(CONCAT(FIRST_NAME,' ',LAST_NAME)) LIKE :name) ORDER BY FIRST_NAME";
		return session.createSQLQuery(query).setString("name", name + "%").setLong("orgLocationId", orgLocationId)
				.setMaxResults(10).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByLocationAndAssoicatesList(Long orgLocationId,
			List<Long> associatesIds) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.id NOT IN (:associatesIds) ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId)
				.setParameterList("associatesIds", associatesIds).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByLocationAndAssoicatesList(Long orgLocationId, Long spocId,
			List<Long> associatesIds) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.spocId.id = :spocId and candidate.id NOT IN (:associatesIds) ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("spocId", spocId)
				.setParameterList("associatesIds", associatesIds).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByLocation(Long orgLocationId, Long spocId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.spocId.id = :spocId  ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("spocId", spocId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(Long vendorOrgRelationId,
			List<Long> associatesIds) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgRelationId.id =:vendorOrgRelationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.id NOT IN (:associatesIds) ";
		return session.createQuery(query).setLong("vendorOrgRelationId", vendorOrgRelationId)
				.setParameterList("associatesIds", associatesIds).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocation(Long vendorOrgRelationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgRelationId.id =:vendorOrgRelationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' ";
		return session.createQuery(query).setLong("vendorOrgRelationId", vendorOrgRelationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByConsultantUserIdOrgRelationId(Long consultantUserId, Long orgLocationId,
			Long orgRelationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Candidate candidate = null;
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.consultantUser.id= :consultantUserId and candidate.orgRelationId.id= :orgRelationId";
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId)
				.setLong("consultantUserId", consultantUserId).setLong("orgRelationId", orgRelationId).list();
		if (null != candidateList && candidateList.size() > 0) {
			candidate = candidateList.get(0);
		}
		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByConsultantUserId(Long consultantUserId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Candidate candidate = null;
		try {
			String query = "from Candidate candidate left join fetch candidate.orgRelationId left join fetch candidate.masterCandidateId "
					+ " where candidate.orgLocationId.id =:orgLocationId and candidate.consultantUser.id= :consultantUserId";
			List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId)
					.setLong("consultantUserId", consultantUserId).list();
			if (null != candidateList && candidateList.size() > 0) {
				candidate = candidateList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidatesCountByCandidateTypeOrgLocationIDAndUserId(Long orgLocationId, Long userId,
			String candidateType) throws Exception {

		logger.debug("getCandidatesCountByCandidateTypeOrgLocationIDAndUserId() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.recruiterUserId.id = :userId and candidate.candidateType = :candidateType ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId)
				.setLong("userId", userId).setString("candidateType", candidateType).list();
		logger.debug("getCandidatesCountByCandidateTypeOrgLocationIDAndUserId() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationIDAndUserId(Long orgLocationId, Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.recruiterUserId.id = :userId ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationIdAndOrgRelationId(Long orgLocationId, Long orgRelationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.orgLocationId.id =:orgLocationId and candidate.orgRelationId.id = :orgRelationId and candidate.isAssociate = 'Y'";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId)
				.setLong("orgRelationId", orgRelationId).list();
	}

	@SuppressWarnings("null")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CandidateDTO> getRequestedCandidatesList(CandidateDTO candidateDTO, Long orgLocationsId,
			List<Long> roleIds, List<Long> teamIds, String resourceType, List<Long> associatesIds, Long projectId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		Integer filterCount = 0;
		List<Candidate> candidateList = new ArrayList<>();
		ResponseList<CandidateDTO> response = new ResponseList<>();
		List<CandidateDTO> candidatesDTOList = new ArrayList<>();
		List<Long> countlist = null;
		StringBuilder query1 = new StringBuilder("");
		if (candidateDTO.getSearchValue() != null && !"".equalsIgnoreCase(candidateDTO.getSearchValue())) {
			query1.append(" and ").append("(")
					.append(" candidate.firstName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.lastName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR (candidate.middelName like '%" + candidateDTO.getSearchValue().replace("'", "") + "%' ) ")
					.append("OR (case when candidate.middelName is not null and candidate.lastName is not null"
							+ " then (concat(candidate.firstName,' ',candidate.middelName,' ',candidate.lastName))"
							+ " else ((case when candidate.middelName is not null and candidate.lastName is null"
							+ "				then concat(candidate.firstName,' ',candidate.middelName)"
							+ "				else ((case when candidate.middelName is null and candidate.lastName is not null"
							+ "							then concat(candidate.firstName,' ',candidate.lastName)"
							+ "							else (candidate.firstName) "
							+ "						end)) "
							+ "			end)) "
						    + "end)" 
						    + " like '%"+candidateDTO.getSearchValue().replace("'", "") + "%' ")
					
					.append("OR candidate.primarySkill like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.email like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.designationType like '%" + candidateDTO.getSearchValue() + "%' ").append(")");

		}

		String subQuery1 = "";
		String subQuery2 = "";
		String subQuery3 = "";
		String subQuery4 = "";
		String subQuery6 = "";
		Query queryObj = null;

		if (associatesIds != null && associatesIds.size() == 0) {
			associatesIds = null;
		}

		subQuery1 = " and ((:varZero  IN(:roleIds)) OR (:varZero NOT IN(:roleIds) and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.roles.id IN (:roleIds))))";
		subQuery4 = " and ((:varZero  IN(:associatesIds)) OR (:varZero NOT IN(:associatesIds) and candidate.id NOT IN (:associatesIds)))";
		subQuery6 = " and ((:varZero  IN(:teamIds)) OR (:varZero NOT IN(:teamIds) and  candidate.consultantUser.id IN (select teamMembers.memberUserId.id from TeamMembers teamMembers where teamMembers.status = '1' and teamMembers.teamId.id IN (:teamIds))))";

		if (roleIds == null) {
			roleIds = new ArrayList<>();
			roleIds.add(0L);
		}

		if (associatesIds == null) {
			associatesIds = new ArrayList<>();
			associatesIds.add(0L);
		}

		if (teamIds == null) {
			teamIds = new ArrayList<>();
			teamIds.add(0L);
		}

		if (!isNullOrEmpty(resourceType)) {
			String[] type = resourceType.split(",");
			if (type.length > 1) {
				subQuery2 = " and ((candidate.isFavourite = '1') OR (candidate.consultantUser.id IN (select pm.projectMemberCandidteId.id from ProjectMembers pm where pm.projectMemberCandidteId.orgLocationId.id = :orgLocationsId and pm.status = '1' )))";
			} else {
				if (type[0].equalsIgnoreCase("hotlist")) {
					subQuery2 = " and candidate.isFavourite = '1' ";
				} else {
					subQuery2 = " and candidate.consultantUser.id IN (select pm.projectMemberCandidteId.consultantUser.id from ProjectMembers pm where pm.projectMemberCandidteId.orgLocationId.id = :orgLocationsId and pm.status = '1' )  and candidate.orgLocationId.id = :orgLocationsId ";
				}
			}
		}

		subQuery3 = " and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id = :orgLocationsId and userRole.userState != 'INACTIVE')";

		String subQuery5 = " and candidate.consultantUser.id NOT IN (select pm.projectMemberCandidteId.consultantUser.id from ProjectMembers pm where pm.projectClientsId.orgLocationsId.id = :orgLocationsId and pm.projectClientsId.projectId.id = :projectId and pm.projectMemberCandidteId.consultantUser IS NOT NULL ) and candidate.orgLocationId.id = :orgLocationsId ";

		// Dont delete this logic., It will be used further
		/*
		 * String subQuery6 =
		 * " and candidate.consultantUser.id NOT IN (select pah.approverUserId.id from ProjectApproverHierarchy pah where pah.projectId.id = :projectId) and candidate.orgLocationId.id = :orgLocationsId "
		 * ; String query =
		 * "from Candidate candidate  where candidate.status = '1' and candidate.isAssociate = 'Y' and candidate.orgLocationId.id = :orgLocationsId "
		 * +subQuery1+" "+subQuery2+" "+subQuery3+" "+subQuery4+" " +subQuery5+
		 * " "+subQuery6+" ";
		 */

		String query = "from Candidate candidate  where candidate.status = '1' and candidate.isAssociate = 'Y' and candidate.orgLocationId.id = :orgLocationsId "
				+ subQuery1 + " " + subQuery6 + " " + subQuery2 + " " + subQuery3 + " " + subQuery4 + " " + subQuery5
				+ " " + query1.toString() + " ";

		queryObj = session.createQuery(query);
		queryObj.setFirstResult(candidateDTO.getStart());
		queryObj.setMaxResults(candidateDTO.getLength());
		queryObj.setLong("orgLocationsId", orgLocationsId).setLong("projectId", projectId)
				.setParameterList("roleIds", roleIds).setParameterList("associatesIds", associatesIds)
				.setParameterList("teamIds", teamIds).setLong("varZero", 0).list();
		candidateList = queryObj.list();

		countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId)
				.setLong("projectId", projectId).setParameterList("roleIds", roleIds)
				.setParameterList("associatesIds", associatesIds).setParameterList("teamIds", teamIds)
				.setLong("varZero", 0).list();

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			filterCount = candidateList.size();
		}

		if (candidateList != null && candidateList.size() > 0) {
			for (Candidate candidate : candidateList) {
				CandidateDTO candDTO = new CandidateDTO();
				Integer projectsCount = ProjectsDao.getUserAssignedProjectsCount(candidate.getConsultantUser().getId(),
						candidateDTO.getOrgLocationId());
				candDTO.setAssignedProjectsCount(projectsCount);

				UserRole userRole = userRoleDao.getUserDetailsByUserIdAndLocationId(
						candidate.getConsultantUser().getId(), candidateDTO.getOrgLocationId());
				candDTO.setUserRole(userRole.getRoles().getDisplayName());
				if (userRole.getReportingManager() != null) {
					StringBuilder reportingManager = new StringBuilder();
					if (!isNullOrEmpty(userRole.getReportingManager().getLastName())) {
						reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName())
								.append(" ").append(userRole.getReportingManager().getLastName());
					} else {
						reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName());
					}
					candDTO.setReportingManager(reportingManager.toString());
				} else {
					candDTO.setReportingManager("----");
				}

				CandidateResumes candidateResumes = candidateResumesDao
						.getCandidateResumeByCandidateId(candidate.getId());
				if (candidateResumes != null) {
					candDTO.setResumeUUID(candidateResumes.getResumeUUID());
					candDTO.setFileUrl(candidateResumes.getFileURL());
					candDTO.setFileName(candidateResumes.getFileName());
				}

				candDTO.setId(candidate.getId());
				candDTO.setFirstName(candidate.getFirstName());
				candDTO.setLastName(candidate.getLastName());
				candDTO.setMiddleName(candidate.getMiddelName());
				candDTO.setFullName(candidate.getFullName());
				candDTO.setPrimarySkill(candidate.getPrimarySkill());
				candDTO.setDesignationType(candidate.getDesignationType());
				candDTO.setDesignationOtherType(candidate.getDesignationOtherType());
				candDTO.setCurrentRate(candidate.getCurrentRate());
				candDTO.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
				candDTO.setCurrentRateType(candidate.getCurrentRateType());
				if(candidate.getConsultantUser() != null) {
					candDTO.setEmail(candidate.getConsultantUser().getEmail());
				}else {
					candDTO.setEmail(candidate.getEmail());
				}
				candDTO.setMobilePhone(candidate.getMobilePhone());
				candidatesDTOList.add(candDTO);
			}
		}

		int totalCount = 0;

		if (candidateDTO != null) {
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

		response.setData(candidatesDTOList);
		return response;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate checkCandidateIdNumberInDatebase(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.id =(SELECT MAX(candi.id) FROM Candidate candi where candi.orgLocationId.id =:orgLocationId)";
		List<Candidate> candidate = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if (candidate.isEmpty()) {
			return null;
		}
		return candidate.iterator().next();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidateGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdcount) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'" + generatedIdcount + "') AS UNSIGNED)) FROM "
				+ schema + ".CANDIDATE WHERE ORG_LOCATION_ID = '" + orgLocationId + "'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		if (count == 0) {
			query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'7') AS UNSIGNED)) FROM " + schema
					+ ".CANDIDATE WHERE ORG_LOCATION_ID = '" + orgLocationId + "'";
			maxCountList = session.createSQLQuery(query).list();
			if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
				count = (Long) maxCountList.iterator().next().longValue();
			}
		}

		return count;

	}

	@Override
	@Transactional
	public List<Candidate> getAssociatesListForSow(Long locationId, String sowType) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> associatesList = null;
		if (Constants.Vendor_SOW.equalsIgnoreCase(sowType)) {
			String query = "from Candidate can where can.orgLocationId.id =:locationId and can.isAssociate = 'Y' and can.orgRelationId <> NULL and can.spocId <> NULL and can.designationType IN ('C2C' , 'C2C-1099')";
			associatesList = session.createQuery(query).setLong("locationId", locationId).list();
		} else if (Constants.Client_SOW.equalsIgnoreCase(sowType)) {
			String query = "from Candidate can where can.orgLocationId.id =:locationId and can.isAssociate = 'Y'";
			associatesList = session.createQuery(query).setLong("locationId", locationId).list();
		}
		return associatesList;
	}

	@Override
	@Transactional
	public Candidate getCandidateByParentCandidateId(Long parentCandidateId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		candidate = session
				.createQuery(
						"from Candidate candidate left join fetch candidate.orgLocationId left join fetch candidate.orgRelationId where candidate.parentCandidateId.id =:parentCandidateId")
				.setLong("parentCandidateId", parentCandidateId).list();
		logger.debug("getCandidateByParentCandidateId end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public void storeCandidateImportLog(CandidateImportLog candidateImportLog) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(candidateImportLog);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	@Transactional
	public List<CandidateImportLog> getCandidateImportLog(String uploadId, boolean all) {
		List<CandidateImportLog> importLog = new ArrayList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			importLog = session.createQuery(
					"from CandidateImportLog c where c.uploadId=" + uploadId + (all ? "" : " and c.read = false"))
					.list();
			if (importLog != null && !all) {
				for (CandidateImportLog c : importLog) {
					c.setRead(true);
					session.update(c);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return importLog;
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByShortURL(String shortURL) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select Candidate from Candidate r where r.Candidate =:shortURL";
		List<Candidate> Candidate = session.createQuery(query).setString("shortURL", shortURL).list();
		if (!Candidate.isEmpty()) {
			return Candidate.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getCandidatesCountByLocation(Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(*) from Candidate candidate where candidate.orgLocationId.id = :orgLocationId ";
		List<Long> candidateCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.list();
		Integer count = 0;
		if (candidateCount != null && !candidateCount.isEmpty()) {
			count = candidateCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllAssociatesByOrglocationIdAndEmploymentType(Long orgLocationId, String employeementType)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<Candidate> candidateList = null;

		if (employeementType != null && employeementType != "" && employeementType != "undefined") {
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1' and candidate.isAssociate ='Y'  and candidate.designationType =:employeementType and candidate.consultantUser IS NOT NULL  ";
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId)
					.setString("employeementType", employeementType).list();
		} else {
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1' and candidate.isAssociate ='Y' and candidate.consultantUser IS NOT NULL ";
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}

		return candidateList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CandidateDTO> getLocationCandidatesList(CandidateDTO candidateDTO, Long orgLocationsId,
			List<Long> roleIds, List<Long> associatesIds, Long loginOrgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		Integer filterCount = 0;
		List<Candidate> candidateList = new ArrayList<>();
		ResponseList<CandidateDTO> response = new ResponseList<>();
		List<CandidateDTO> candidatesDTOList = new ArrayList<>();
		List<Long> countlist = null;
		StringBuilder query1 = new StringBuilder("");
		if (candidateDTO.getSearchValue() != null && !"".equalsIgnoreCase(candidateDTO.getSearchValue())) {
			query1.append(" and ").append("(")
					.append(" candidate.firstName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.lastName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR (candidate.consultantUser.id IN (select ur.user.id from UserRole ur where ur.roles.displayName like '%"
							+ candidateDTO.getSearchValue() + "%' and ur.orgLocations.id = :orgLocationsId)) ")
					.append("OR (candidate.consultantUser.id IN (select ur.user.id from UserRole ur where (( ur.reportingManager.firstName LIKE '"
							+ candidateDTO.getSearchValue() + "%') OR (ur.reportingManager.lastName LIKE '"
							+ candidateDTO.getSearchValue() + "%')) and ur.orgLocations.id = :orgLocationsId))")
					.append("OR candidate.email like '%" + candidateDTO.getSearchValue() + "%' ").append(")");

		}

		String subQuery1 = "";
		String subQuery2 = "";
		String subQuery3 = "";
		String subQuery4 = "";
		Query queryObj = null;

		if (associatesIds != null && associatesIds.size() == 0) {
			associatesIds = null;
		}

		if (roleIds != null) {
			subQuery1 = " and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.roles.id IN (:roleIds) )";
		}

		subQuery2 = " and candidate.consultantUser.id NOT IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id = :loginOrgLocationId  )";

		subQuery3 = " and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id = :orgLocationsId and userRole.userState != 'INACTIVE')";

		if (associatesIds != null) {
			subQuery4 = " and candidate.id NOT IN (:associatesIds)";
		}

		String query = "from Candidate candidate  where candidate.status = '1' and candidate.isAssociate = 'Y' and candidate.orgLocationId.id = :orgLocationsId "
				+ subQuery1 + " " + subQuery2 + " " + subQuery3 + " " + subQuery4 + "  " + query1.toString() + " ";

		if (roleIds != null && associatesIds == null) {
			queryObj = session.createQuery(query);
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId)
					.setParameterList("roleIds", roleIds).list();
			candidateList = queryObj.list();

			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId)
					.setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("roleIds", roleIds).list();

		} else if (associatesIds != null && roleIds == null) {

			queryObj = session.createQuery(query);
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId)
					.setParameterList("associatesIds", associatesIds).list();
			candidateList = queryObj.list();

			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId)
					.setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("associatesIds", associatesIds)
					.list();
		} else if (associatesIds != null && roleIds != null) {

			queryObj = session.createQuery(query);
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId)
					.setParameterList("roleIds", roleIds).setParameterList("associatesIds", associatesIds).list();
			candidateList = queryObj.list();

			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId)
					.setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("roleIds", roleIds)
					.setParameterList("associatesIds", associatesIds).list();
		} else {
			queryObj = session.createQuery(query);
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).list();
			candidateList = queryObj.list();
			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId)
					.setLong("loginOrgLocationId", loginOrgLocationId).list();
		}

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			filterCount = candidateList.size();
		}

		if (candidateList != null && candidateList.size() > 0) {
			for (Candidate candidate : candidateList) {
				CandidateDTO candDTO = new CandidateDTO();

				UserRole userRole = userRoleDao.getUserDetailsByUserIdAndLocationId(
						candidate.getConsultantUser().getId(), candidateDTO.getOrgLocationId());
				candDTO.setUserRole(userRole.getRoles().getDisplayName());
				if (userRole.getReportingManager() != null) {
					StringBuilder reportingManager = new StringBuilder();
					if (!isNullOrEmpty(userRole.getReportingManager().getLastName())) {
						reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName())
								.append(" ").append(userRole.getReportingManager().getLastName());
					} else {
						reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName());
					}
					candDTO.setReportingManager(reportingManager.toString());
				} else {
					candDTO.setReportingManager("----");
				}

				CandidateResumes candidateResumes = candidateResumesDao
						.getCandidateResumeByCandidateId(candidate.getId());
				if (candidateResumes != null) {
					candDTO.setResumeUUID(candidateResumes.getResumeUUID());
					candDTO.setFileUrl(candidateResumes.getFileURL());
					candDTO.setFileName(candidateResumes.getFileName());
				}

				candDTO.setId(candidate.getId());
				StringBuilder candidateName = new StringBuilder();
				if (StringUtils.isNotBlank(candidate.getLastName())) {
					candidateName = candidateName.append(candidate.getFirstName()).append(" ")
							.append(candidate.getLastName());
				} else {
					candidateName = candidateName.append(candidate.getFirstName());
				}

				candDTO.setCandidateName(candidateName.toString());
				candDTO.setCurrentRate(candidate.getCurrentRate());
				candDTO.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
				candDTO.setCurrentRateType(candidate.getCurrentRateType());
				candDTO.setEmail(candidate.getEmail());

				candidatesDTOList.add(candDTO);
			}
		}

		int totalCount = 0;

		if (candidateDTO != null) {
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

		response.setData(candidatesDTOList);
		return response;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByEmailAndOrgLocationId(String email, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.email = :email and candidate.candidateType = 'Candidate' and candidate.orgLocationId.id = :orgLocationId ";
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId)
				.setString("email", email).list();
		Candidate candidate = null;
		if (candidateList != null && candidateList.size() > 0) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByEmail(String email) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.email = :email  ";
		List<Candidate> candidateList = session.createQuery(query).setString("email", email).list();
		Candidate candidate = null;
		if (candidateList != null && candidateList.size() > 0) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })

	public CandidateIdendities getCandidateIdentitiesNumberByCandidateId(Long id, String idType) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from CandidateIdendities ci  where ci.candidateId.id = :candidateId and ci.idType = :idType ";
		CandidateIdendities candidateIdendities = (CandidateIdendities) session.createQuery(query)
				.setLong("candidateId", id).setString("idType", idType).uniqueResult();
		return candidateIdendities;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByParentCandidateIdAndOrgLocationId(Long parentCandidateId, Long orgLocationId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		candidate = session
				.createQuery(
						"from Candidate candidate left join fetch candidate.orgLocationId left join fetch candidate.orgRelationId "
								+ " where candidate.parentCandidateId.id =:parentCandidateId and candidate.orgLocationId.id = :orgLocationId")
				.setLong("parentCandidateId", parentCandidateId).setLong("orgLocationId", orgLocationId).list();
		logger.debug("getCandidateByParentCandidateId end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getAllActiveCandidatesByLocationEmail(String email, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId and cnadidate.status ='1' and cnadidate.consultantUser !=null and cnadidate.email = :email ORDER BY cnadidate.id DESC";
		return (Candidate) session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("email", email)
				.uniqueResult();
	}

	@Override
	@Transactional
	public List<RecruiterReportCandidateCountView> getCandidateCountsCreatedUserList(Long orgLocationId,
			List<String> userCodesList, String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<RecruiterReportCandidateCountView> candidatesViewList = null;
		try {

			if (StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {

				query = "SELECT " + " USER_CODE AS associateUserCode,SUM(CAND_COUNT) AS candCount" + " FROM"
						+ " RECRUITER_REPORT_CANDIDATE_COUNT_VIEW" + " WHERE"
						+ " ORG_ID = :orgId AND USER_CODE IN (:userCodesList)" + " AND CREATED_DATE BETWEEN '"
						+ startDateUTC + "' AND '" + endDateUTC + "'" + " GROUP BY USER_CODE";

				Query queryObj = session.createSQLQuery(query);
				queryObj.setParameterList("userCodesList", userCodesList).setParameter("orgId", orgId);
				queryObj.setResultTransformer(Transformers.aliasToBean(RecruiterReportCandidateCountView.class));
				candidatesViewList = queryObj.list();

			} else {
				query = "SELECT " + " USER_CODE AS associateUserCode,SUM(CAND_COUNT) AS candCount" + " FROM"
						+ " RECRUITER_REPORT_CANDIDATE_COUNT_VIEW" + " WHERE"
						+ " ORG_LOCATION_ID = :orgLocationId AND USER_CODE IN (:userCodesList)"
						+ " AND CREATED_DATE BETWEEN '" + startDateUTC + "' AND '" + endDateUTC + "'"
						+ " GROUP BY USER_CODE";

				Query queryObj = session.createSQLQuery(query);
				queryObj.setParameterList("userCodesList", userCodesList).setParameter("orgLocationId", orgLocationId);
				queryObj.setResultTransformer(Transformers.aliasToBean(RecruiterReportCandidateCountView.class));
				candidatesViewList = queryObj.list();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return candidatesViewList;

	}

	@Override
	@Transactional
	public ResponseList<SubmissionCandidateListView> getSubmissionCandidateListView(CandidateDTO candidateDTO,
			OrgLocations orgLocations) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<SubmissionCandidateListView> response = new ResponseList<>();
		Query queryObj = null;
		String querySeachStr = "";
		StringBuilder advSeach = new StringBuilder("");
		String advSeachStr = "";
		List<SubmissionCandidateListView> candidateList = null;
		boolean flag = false;

		StringBuilder query1 = new StringBuilder("");
		if (candidateDTO.getSearchValue() != null && !"".equalsIgnoreCase(candidateDTO.getSearchValue())) {
			query1.append(" Q1.candidateName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR Q1.email like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR Q1.employementType like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR Q1.vendorName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR Q1.updatedDate like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR Q1.noticePeriod like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR Q1.primarySkill like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR Q1.country like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR Q1.location like '%" + candidateDTO.getSearchValue() + "%' ");

		}

		if (StringUtils.isNotBlank(candidateDTO.getPrimarySkill())) {
			String primarySkills[] = candidateDTO.getPrimarySkill().split(",");
			String primarySkillsStr = "'" + StringUtils.join(primarySkills, "','") + "'";
			advSeach.append(" Q1.primarySkill IN (" + primarySkillsStr + ") ");
			flag = true;
		}

		if (StringUtils.isNotBlank(candidateDTO.getDesignationType())) {
			String designationTypes[] = candidateDTO.getDesignationType().split(",");
			String designationTypesStr = "'" + StringUtils.join(designationTypes, "','") + "'";
			if (flag == false) {
				advSeach.append(" Q1.employementType IN (" + designationTypesStr + ") ");
				flag = true;
			} else {
				advSeach.append(" AND Q1.employementType IN (" + designationTypesStr + ") ");
			}

		}

		if (candidateDTO.getNoticePeriod() != null && !candidateDTO.getNoticePeriod().isEmpty()) {
			String noticePeriod[] = candidateDTO.getNoticePeriod().split(",");
			String noticePeriodStr = "'" + StringUtils.join(noticePeriod, "','") + "'";
			if (flag == false) {
				advSeach.append(" Q1.noticePeriod IN (" + noticePeriodStr + ") ");
				flag = true;
			} else {
				advSeach.append(" AND Q1.noticePeriod IN (" + noticePeriodStr + ") ");
			}

		}

		if (StringUtils.isNotBlank(candidateDTO.getVendor())) {
			String orgRelationIds[] = candidateDTO.getVendor().split(",");
			String orgRelationIdStr = "'" + StringUtils.join(orgRelationIds, "','") + "'";
			if (flag == false) {
				advSeach.append(" Q1.orgRelationId IN (" + orgRelationIdStr + ") ");
				flag = true;
			} else {
				advSeach.append(" AND Q1.orgRelationId IN (" + orgRelationIdStr + ") ");
			}

		}

		if (StringUtils.isNotBlank(candidateDTO.getCreatedDate())
				&& !candidateDTO.getCreatedDate().equalsIgnoreCase("All")) {
			Date[] searchDates = reportsUtil.getJobFilterDate(candidateDTO.getCreatedDate(), orgLocations);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDate = sdf.format(searchDates[0]);
			String toDate = sdf.format(searchDates[1]);

			if (flag == false) {
				advSeach.append(" (Q1.updatedDate BETWEEN '" + fromDate + "' AND '" + toDate + "') ");
				flag = true;
			} else {
				advSeach.append(" AND (Q1.updatedDate BETWEEN '" + fromDate + "' AND '" + toDate + "') ");
			}

		}

		if (StringUtils.isNotBlank(candidateDTO.getCandidateSkills())) {
			String skills[] = candidateDTO.getCandidateSkills().split(",");
			String skillsStr = "'" + StringUtils.join(skills, "','") + "'";
			if (flag == false) {
				advSeach.append(" Q1.skills IN (" + skillsStr + ") ");
				flag = true;
			} else {
				advSeach.append(" AND Q1.skills IN (" + skillsStr + ") ");
			}

		}

		if (StringUtils.isNotBlank(candidateDTO.getCity())) {
			String cities[] = candidateDTO.getCity().split(",");
			String citiesStr = "'" + StringUtils.join(cities, "','") + "'";
			if (flag == false) {
				advSeach.append(" Q1.city IN (" + citiesStr + ") ");
				flag = true;
			} else {
				advSeach.append(" AND Q1.city IN (" + citiesStr + ") ");
			}

		}

		String sortingQry = "";
		if (StringUtils.isNotBlank(candidateDTO.getSortColumn())) {
			sortingQry = " order by Q1." + candidateDTO.getSortColumn() + " " + candidateDTO.getSortOrder();
		}

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			querySeachStr = "where " + "(" + query1.toString() + ")";
		}

		if ((query1.toString() == null || query1.toString().isEmpty()) && advSeach.toString() != null
				&& !advSeach.toString().isEmpty()) {
			advSeachStr = "where " + "(" + advSeach.toString() + ")";
		} else if (query1.toString() != null && !query1.toString().isEmpty() && advSeach.toString() != null
				&& !advSeach.toString().isEmpty()) {
			advSeachStr = "and " + "(" + advSeach.toString() + ")";
		}

		try {
			query = "SELECT Q1.* " + "FROM (" + "SELECT " + "CAND.ID AS id, "
					+ "CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName,"
					+ "CAND.GENERATED_ID AS generatedId, " + "CAND.EMAIL AS email, "
					+ "CAND.DESIGNATION_TYPE AS employementType, " + "CAND.PRIMARY_SKILL AS primarySkill, "
					+ "CAND.SKILLS_NAME AS skills, " + "CAND.ADDRESS AS location, " + "CAND.COUNTRY AS country, "
					+ "CAND.CITY AS city, " + "CAND.NOTICE_PERIOD AS noticePeriod, "
					+ "CAND.IS_ASSOCIATE AS isAssociate, "
					+ "DATE_FORMAT(CAND.CREATED_DATE ,\"%Y-%m-%d\") AS createdDate, "
					+ "DATE_FORMAT(CAND.UPDATED_DATE ,\"%Y-%m-%d\") AS updatedDate, "
					+ "CAND.ORG_LOCATION_ID AS orgLocationId, " + "CAND.ORG_RELATION_ID AS orgRelationId, "
					+ "OL.ORG_ID AS orgId, " + "(SELECT " + "ORG_NAME " + "FROM "
					+ "ORG_LOCATIONS OL,ORG_RELATIONS ORL " + "WHERE "
					+ "(OL.ID = ORL.LOCATION_ID OR OL.ID = ORL.RESPONDER_LOCATION_ID) AND OL.ID != :orgLocationId AND ORL.ID = CAND.ORG_RELATION_ID) AS vendorName "
					+ "FROM " + "CANDIDATE CAND, " + "ORG_LOCATIONS OL "
					+ "WHERE  CAND.ORG_LOCATION_ID = OL.ID  AND CAND.STATUS = '1' "
					+ "AND CAND.ORG_LOCATION_ID = :orgLocationId "
					+ "AND CAND.ID NOT IN (SELECT SUB.CANDIDATE_ID FROM REQUIREMENT_SUBMISSION SUB WHERE SUB.REQUIREMENT_ID = :requirementId) "
					+ "AND CAND.PROFILE_PERCENTAGE >= (SELECT REQ.PROFILE_PERCENTAGE FROM REQUIREMENT REQ WHERE REQ.ID = :requirementId) "
					+ "GROUP BY CAND.ID,  " + "DATE_FORMAT(CAND.CREATED_DATE ,\"%Y-%m-%d\"))Q1 " + querySeachStr
					+ advSeachStr + sortingQry;

			String countQuery = "SELECT COUNT(*) AS TOTAL" + " FROM" + "(" + query + ")Q2;";

			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("orgLocationId", orgLocations.getId());
			queryObj.setParameter("requirementId", candidateDTO.getRequirementId());
			queryObj.setResultTransformer(Transformers.aliasToBean(SubmissionCandidateListView.class));
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			candidateList = queryObj.list();

			countlist = session.createSQLQuery(countQuery).setLong("orgLocationId", orgLocations.getId())
					.setLong("requirementId", candidateDTO.getRequirementId()).list();
			filterCount = countlist.iterator().next().intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int totalCount = 0;

		if (candidateDTO != null) {
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

		response.setData(candidateList);
		return response;
	}

	@Override
	@Transactional
	public CandidateDTO getCandidateDTOByCandidateId(Long candidateId) throws Exception {

		List<CandidateDTO> candidateDTOList = null;
		CandidateDTO candidateDTO = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT " + " CAND.ID AS candId, " + " CAND.FIRST_NAME AS firstName, " + " CAND.LAST_NAME AS lastName, "
				+ " CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName,"
				+ " CAND.MOBILE_PHONE AS phoneNumber, " + " CAND.EMAIL AS email, " + " CAND.LOCATION AS location, "
				+ " CAND.COUNTRY AS country, " + " CAND.STATE AS state, " + " CAND.CITY AS city, "
				+ " CAND.NOTICE_PERIOD AS noticePeriod, " + " CAND.PROFILE_PERCENTAGE AS profilePercentage, "
				+ " CAND.CURRENT_RATE AS currentRate, " + " CAND.CURRENT_RATE_TYPE AS currentRateType, "  + " CAND.CURRENT_RATE_CURRENCY AS currentRateCurrency, "
				+ " CAND.EXPECTED_RATE AS expectedRate, " + " CAND.RATE_TYPE AS rateType, "  + " CAND.EXPECTED_RATE_CURRENCY AS expectedRateCurrencey, "
				+ " CAND.EXPERIENCE_YEARS AS experienceYears, " + " CAND.EXPERIENCE_MONTHS AS experienceMonths "
				+ " FROM CANDIDATE CAND " + " WHERE " + " CAND.ID = :candidateId ";

		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("candidateId", candidateId);
		queryObj.setResultTransformer(Transformers.aliasToBean(CandidateDTO.class));
		candidateDTOList = queryObj.list();
		if (candidateDTOList != null && !candidateDTOList.isEmpty()) {
			candidateDTO = candidateDTOList.get(0);
		}

		return candidateDTO;

	}

	@Override
	@Transactional
	public List<CandidateDTO> getCandidateListForAddApplicant(OrgLocations orgLcoations, Long requirementId,
			String skills, String candidateDetails, Integer profilePercentage) throws Exception {

		List<CandidateDTO> candidateDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		String querySeachStr = "";

		StringBuilder query1 = new StringBuilder("");
		if (candidateDetails != null && !"".equalsIgnoreCase(candidateDetails)) {
			query1.append(" CAND.FIRST_NAME like '%" + candidateDetails + "%' ")
					.append("OR CAND.LAST_NAME like '%" + candidateDetails + "%' ")
					.append("OR CAND.EMAIL like '%" + candidateDetails + "%' ")
					.append("OR CAND.MOBILE_PHONE like '%" + candidateDetails + "%' ");

		}

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			querySeachStr = " AND " + "(" + query1.toString() + ")";
		}

		if (StringUtils.isNotBlank(skills)) {
			String requirementSkills[] = skills.split(",");
			// String requirementSkillsStr = "'" +
			// StringUtils.join(requirementSkills,"','") + "'";
			// subQuery = " AND CAND.SKILLS_NAME IN ("+requirementSkillsStr+")";

			int i = 0;
			int j = requirementSkills.length;
			for (String rSkill : requirementSkills) {
				i++;
				if (i == 1) {
					subQuery = subQuery + " AND (";
				} else {
					subQuery = subQuery + " OR ";
				}
				subQuery = subQuery + " CAND.SKILLS_NAME LIKE '%" + rSkill + "%' ";
				if (i == j) {
					subQuery = subQuery + " )";
				}
			}
		}

		String query = "SELECT " + " CAND.ID AS candId, "
				+ " CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName,"
				+ " CAND.IS_ASSOCIATE AS isAssociate " + " FROM CANDIDATE CAND " + " WHERE CAND.STATUS = '1' "
				+ " AND CAND.ORG_LOCATION_ID = :orgLocationId " + " AND CAND.PROFILE_PERCENTAGE >= :profilePercentage "
				+ subQuery
				+ " AND CAND.ID NOT IN (SELECT SUB.CANDIDATE_ID FROM REQUIREMENT_SUBMISSION SUB WHERE SUB.REQUIREMENT_ID = :requirementId) "
				+ " ORDER BY CAND.ID DESC ";

		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLcoations.getId());
		queryObj.setLong("requirementId", requirementId);
		queryObj.setInteger("profilePercentage", profilePercentage);
		queryObj.setMaxResults(5000);
		queryObj.setResultTransformer(Transformers.aliasToBean(CandidateDTO.class));
		candidateDTOList = queryObj.list();
		return candidateDTOList;

	}

	@Override
	@Transactional
	public List<CandidateDTO> getAllActiveCandidatesListByOrglocationId(Long orgLocationId, String employeementType)
			throws Exception {

		List<CandidateDTO> candidateDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;

		if (!employeementType.equalsIgnoreCase("All")) {
			query = "SELECT CAND.ID AS candId,"
					+ "CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName"
					+ " FROM CANDIDATE CAND " + " WHERE CAND.ORG_LOCATION_ID = :orgLocationId"
					+ " AND CAND.DESIGNATION_TYPE = '" + employeementType
					+ "' AND CAND.STATUS = '1' AND CAND.CONSULTANT_USER_ID IS NULL"
					+ " AND CAND.ID NOT IN (SELECT ONBOARDING.CANDIDATE_ID FROM CANDIDATE_ON_BOARDING ONBOARDING WHERE ONBOARDING.ORG_LOCATION_ID = :orgLocationId AND ONBOARDING.CANDIDATE_ID IS NOT NULL)";
		} else {
			query = "SELECT CAND.ID AS candId,"
					+ "CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName"
					+ " FROM CANDIDATE CAND " + " WHERE CAND.ORG_LOCATION_ID = :orgLocationId"
					+ " AND CAND.STATUS = '1' AND CAND.CONSULTANT_USER_ID IS NULL"
					+ " AND CAND.ID NOT IN (SELECT ONBOARDING.CANDIDATE_ID FROM CANDIDATE_ON_BOARDING ONBOARDING WHERE ONBOARDING.ORG_LOCATION_ID = :orgLocationId AND ONBOARDING.CANDIDATE_ID IS NOT NULL)";
		}

		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(CandidateDTO.class));
		candidateDTOList = queryObj.list();
		return candidateDTOList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CandidateMarketPlaceDTO> getGlobalMarketCandidateList(
			CandidateMarketPlaceDTO candidateMarketPlaceDTO, String searchValue) {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug(" inside getGlobalMarketCandidateList searchValue is: " + searchValue);
		List<Long> countlist = null;
		Integer filterCount = 0;

		List<CandidateMarketPlace> globalMarketCandList = null;
		ResponseList<CandidateMarketPlaceDTO> responseList = new ResponseList<>();
		try {

			StringBuilder query1 = new StringBuilder("");
			if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
				query1.append(" and ((concat(marketplace.candidateId.firstName,' ',marketplace.candidateId.lastName) like '%" + searchValue + "%' )");
				query1.append(" or (marketplace.candidateId.skillsName like '%" + searchValue + "%' )");
				query1.append(" or (concat(marketplace.candidateId.currentRateCurrency,' ',marketplace.candidateId.currentRate) like '%"
						+ searchValue + "%' )");
				query1.append(" or (concat(marketplace.candidateId.expectedRateCurrencey,' ',marketplace.candidateId.expectedRate) like '%"
						+ searchValue + "%' )");
				query1.append(" or (concat(marketplace.candidateId.experienceYears,'.',marketplace.candidateId.experienceMonths) like '%"
						+ searchValue + "%' )");
				query1.append(" or (concat(marketplace.marketRateCurrencey,' ',marketplace.marketPrice) like '%"
						+ searchValue + "%' )");
				query1.append(" or (marketplace.candidateId.designation like '%" + searchValue + "%' )");
				query1.append(" or (marketplace.candidateId.location like '%" + searchValue + "%' )");
				query1.append(" or (marketplace.candidateId.candidateProfilePercentage like '%" + searchValue + "%' )) ");
			}
			StringBuilder query2 = new StringBuilder("");
			String sortingQry = "";
			if (StringUtils.isNotBlank(candidateMarketPlaceDTO.getSortColumn())) {
				String sortColumnName = candidateMarketPlaceDTO.getSortColumn();
				if(sortColumnName.equalsIgnoreCase("marketPrice")){
					sortingQry = " order by marketplace." + sortColumnName + " " + candidateMarketPlaceDTO.getSortOrder();
				}else{
					sortingQry = " order by marketplace.candidateId." + sortColumnName + " " + candidateMarketPlaceDTO.getSortOrder();
				}
			}
			String listQuery = "";
			String listQueryCount = "";
			if (StringUtils.isNotBlank(candidateMarketPlaceDTO.getOrgLocationId())) {
				listQuery = "from CandidateMarketPlace marketplace  where  marketplace.status = 1 and marketplace.orgLocationId = "
						+ candidateMarketPlaceDTO.getOrgLocationId() + " and marketplace.candidateMarketStatus IN ( '"
						+ Constants.ACTIVE + "') " + query1 + " " + sortingQry;
				listQueryCount = "select count(*) from CandidateMarketPlace marketplace  where  marketplace.status = 1  and marketplace.orgLocationId = "
						+ candidateMarketPlaceDTO.getOrgLocationId() + " and marketplace.candidateMarketStatus IN ( '"
						+ Constants.ACTIVE + "') " + query1 + " " + sortingQry;
			} else {
				listQuery = "from CandidateMarketPlace marketplace  where  marketplace.status = 1 and marketplace.candidateMarketStatus IN ( '"
						+ Constants.ACTIVE + "') " + query1 + " " + sortingQry;
				listQueryCount = "select count(*) from CandidateMarketPlace marketplace  where  marketplace.status = 1  and marketplace.candidateMarketStatus  IN ( '"
						+ Constants.ACTIVE + "') " + query1 + " " + sortingQry;
			}
			Query hqlQuery = session.createQuery(listQuery);
			globalMarketCandList = hqlQuery.setFirstResult(candidateMarketPlaceDTO.getStart())
					.setMaxResults(candidateMarketPlaceDTO.getLength()).list();
			Query hqlQuery1 = session.createQuery(listQueryCount);
			countlist = hqlQuery1.list();
			int totalCount = 0;
			if (candidateMarketPlaceDTO != null) {
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
			List<CandidateMarketPlaceDTO> globalMarketCandDTOList = new ArrayList<CandidateMarketPlaceDTO>();
			for (CandidateMarketPlace source : globalMarketCandList) {
				CandidateMarketPlaceDTO target = new CandidateMarketPlaceDTO();
				BeanUtils.copyProperties(source, target);
				target.setFullName(source.getCandidateId().getFullName());
				target.setLocation(source.getCandidateId().getLocation());
				target.setSkillsName(source.getCandidateId().getSkillsName());
				target.setCurrentRate(source.getCandidateId().getCurrentRate());
				target.setCurrentRateCurrency(source.getCandidateId().getCurrentRateCurrency());
				target.setExpectedRate(source.getCandidateId().getExpectedRate());
				target.setExpectedRateCurrencey(source.getCandidateId().getExpectedRateCurrencey());
				target.setProfilePercentage(source.getCandidateId().getCandidateProfilePercentage());
				target.setMarketPrice(source.getMarketPrice());
				target.setMarketRateCurrencey(source.getMarketRateCurrencey());
				globalMarketCandDTOList.add(target);
			}
			logger.debug(" After executing query result  list size is :::>>" + globalMarketCandList.size());
			responseList.setData(globalMarketCandDTOList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	@Transactional
	public List<Candidate> getAllCandidatesList() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidates  ";
		List<Candidate> candidatesList = session.createQuery(query).list();
		return candidatesList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveMarketPlace(CandidateMarketPlace marketPlace) throws Exception {

		logger.debug("Storing MarketPlace");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (marketPlace != null) {
				if (marketPlace.getId() != null) {
					session.update(marketPlace);
				} else {
					session.save(marketPlace);
				}
			}
			logger.debug("Marketplace Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "CandidateMarketPlace"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CandidateDTO> getCandidatesByCandidateIds(List<String> candidateIds) {
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<CandidateDTO> responseList = new ResponseList<>();
		List<Long> countlist = null;
		Integer filterCount = 0;
		String query = "from Candidate candidate where candidate.id IN ( :candidateIds )";
		List<Candidate> candidates = session.createQuery(query).setParameterList("candidateIds", candidateIds).list();
		String listQueryCount = "select count(candidate) from Candidate candidate where candidate.id IN ( :candidateIds )";
		countlist = session.createQuery(query).setParameterList("candidateIds", candidateIds).list();
		int totalCount = 0;
		if (countlist != null && !countlist.isEmpty()) {
			totalCount = countlist.size();
			filterCount = totalCount;
		}
		responseList.setRecordsTotal(totalCount);
		// if (query1.toString() != null && !query1.toString().isEmpty()) {
		responseList.setRecordsFiltered(filterCount);
		// } else {
		// responseList.setRecordsFiltered(totalCount);
		// }
		List<CandidateDTO> candidateDTOList = new ArrayList<>();
		for (Candidate candidate : candidates) {
			CandidateDTO dto = new CandidateDTO();
			BeanUtils.copyProperties(candidate, dto);
			candidateDTOList.add(dto);
		}

		responseList.setData(candidateDTOList);
		return responseList;
	}

	@Override
	@Transactional
	public Long getCandidateIdByEmailAndOrgLocationId(String email, Long orgLocationId) throws Exception {

		Long candidateId = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT " + " CAND.ID " + " FROM CANDIDATE CAND " + " WHERE "
				+ " CAND.EMAIL = :email AND CAND.ORG_LOCATION_ID = :orgLocationId";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("email", email);
		queryObj.setLong("orgLocationId", orgLocationId);
		BigInteger id = (BigInteger) queryObj.uniqueResult();
		if (id != null) {
			candidateId = id.longValue();
		}

		return candidateId;
	}

	@Override
	@Transactional
	public void saveBulkCandidates(List<Candidate> candidatesList, Long orgLocationId) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		for (Candidate candidate : candidatesList) {
			Long candidateId = getCandidateIdByEmailAndOrgLocationId(candidate.getEmail().trim(), orgLocationId);
			if (candidateId == null) {
				session.save(candidate);
				candidateService.saveOrUpdateCandidateElkDocument(candidate);
			}

		}
	}

	@Override
	@Transactional
	public List<String> getCandidateEmailsByOrgLocationId(Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT " + " CAND.EMAIL " + " FROM CANDIDATE CAND " + " WHERE "
				+ " CAND.ORG_LOCATION_ID = :orgLocationId";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		List<String> emailsList = (List<String>) queryObj.list();

		return emailsList;
	}

	@Override
	@Transactional

	public List<Long> getAddedMarketplaceCandidates(List<Long> candidateIdsVal) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select marketplace.candidateId.id from CandidateMarketPlace marketplace where marketplace.candidateId.id IN ( :candidateIds ) and marketplace.candidateMarketStatus = :marketplaceStatus and marketplace.status = :status";
		List<Long> marketplaceCandidates = session.createQuery(query).setParameterList("candidateIds", candidateIdsVal)
				.setString("marketplaceStatus", Constants.ACTIVE).setString("status", Constants.MEMBER_ACTIVE).list();
		return marketplaceCandidates;
	}

	@Override
	@Transactional
	public CandidateMarketPlace getCandidateMarketPlaceByCandidateId(Long candidateId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateMarketPlace marketplace where marketplace.candidateId.id = :candidateId";
		CandidateMarketPlace marketplaceCandidates = (CandidateMarketPlace) session.createQuery(query)
				.setParameter("candidateId", candidateId).uniqueResult();
		return marketplaceCandidates;
	}

	@Override
	@Transactional
	public void updateMarketPlacePrice(CandidateMarketPlace marketPlace) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(marketPlace);
	}

	@Override
	@Transactional
	public Integer getCandidateProfilePercentageByCandidateId(Long id) throws Exception {

		Integer profilePercentage = 0;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT " + " CAND.PROFILE_PERCENTAGE " + " FROM CANDIDATE CAND " + " WHERE " + " CAND.ID = :id";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("id", id);
		Integer percentageId = (Integer) queryObj.uniqueResult();
		if (percentageId != null) {
			profilePercentage = percentageId;
		}

		return profilePercentage;
	}

	@Override
	@Transactional
	public CandidateMarketPlace getCandidateMarketPlaceById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(CandidateMarketPlace.class, id);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateMarketPlace getCandidateMarketPlaceDetailsByIdAndOrgLocationId(Long id, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateMarketPlace marketplace where marketplace.id =:id and marketplace.orgLocationId = :orgLocationId";
		CandidateMarketPlace marketPlaceCandidate = (CandidateMarketPlace) session.createQuery(query).setLong("id", id)
				.setLong("orgLocationId", orgLocationId).uniqueResult();
		return marketPlaceCandidate;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateMarketPlace> getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(List<Long> id, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateMarketPlace marketplace where marketplace.id IN(:id) and marketplace.orgLocationId = :orgLocationId";
		List<CandidateMarketPlace> marketPlaceCandidateList = session.createQuery(query).setParameterList("id", id).setLong("orgLocationId", orgLocationId).list();
		return marketPlaceCandidateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void withdrawCandidateFromMarketplace(CandidateMarketPlace marketplace) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(marketplace);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateMarketPlace> getGlobalMarketplaceCandListGridCard(String[] searchCandidateIds, Long orgId,
			String candidateLocation, String searchValue, CandidateMarketPlaceDTO candidateMarketPlaceDTO,
			Map<String, Date> dateValue,Date[] checkedDateArray) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuilder query1 = new StringBuilder("");
		List<CandidateMarketPlace> queryList = null;

		// creating filter queries..
		StringBuilder statusQuery = new StringBuilder("");
		StringBuilder dateQuery = new StringBuilder("");
		StringBuilder employmentTypeQuery = new StringBuilder("");
		StringBuilder noticePeriodQuery = new StringBuilder("");
		StringBuilder skillsQuery = new StringBuilder("");
		List<String> employmentList = new ArrayList<>();
		List<String> noticePeriodList = new ArrayList<>();
		List<String> skillsList = new ArrayList<>();
		StringBuilder typeQuery = new StringBuilder("");
		StringBuilder candidateTypeQuery = new StringBuilder("");
		StringBuilder createdByQuery = new StringBuilder("");
		if (candidateMarketPlaceDTO != null) {
			if (StringUtils.isNotBlank(candidateMarketPlaceDTO.getEmploymentType())) {
				String[] emplymentStr = candidateMarketPlaceDTO.getEmploymentType().split(",");
				for (int i = 0; i < emplymentStr.length; i++) {
					employmentList.add(emplymentStr[i]);
				}
			} 
			if (StringUtils.isNotBlank(candidateMarketPlaceDTO.getNoticePeriod())) {
				String[] noticePeriodStr = candidateMarketPlaceDTO.getNoticePeriod().split(",");
				for (int i = 0; i < noticePeriodStr.length; i++) {
					noticePeriodList.add(noticePeriodStr[i]);
				}
			}
			if (StringUtils.isNotBlank(candidateMarketPlaceDTO.getSkillsName())) {
				String[] skillsStr = candidateMarketPlaceDTO.getSkillsName().split(",");
				for (int i = 0; i < skillsStr.length; i++) {
					skillsList.add(skillsStr[i]);
				}
			}
			if (candidateMarketPlaceDTO.getStatus() != null
					&& candidateMarketPlaceDTO.getStatus().equals("" + Constants.CANDIDATE_ACTIVE)) {
				statusQuery.append(" and (marketplace.candidateId.status = 1) ");
			}
			if (candidateMarketPlaceDTO.getStatus() != null
					&& candidateMarketPlaceDTO.getStatus().equals("" + Constants.CANDIDATE_IN_ACTIVE)) {
				statusQuery.append(" and (marketplace.candidateId.status = 0) ");
			}
			if (candidateMarketPlaceDTO.getStatus() != null
					&& candidateMarketPlaceDTO.getStatus().equals("" + Constants.CANDIDATE_ALL)) {
				statusQuery.append(" and ((marketplace.candidateId.status = 0) or (marketplace.candidateId.status = 1)) ");
			}
			if (checkedDateArray != null && checkedDateArray.length > 0) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String customStartdate = dateFormat.format(checkedDateArray[0]);
				String customEnddate = dateFormat.format(checkedDateArray[1]);
				dateQuery.append("and ( DATE_FORMAT(marketplace.updatedDate, '%Y-%m-%d') between '"
						+ customStartdate + "' and '" + customEnddate + "') ");
			}
			if (!dateValue.isEmpty() && checkedDateArray.length == 0) {
				dateQuery.append(
						" and (marketplace.updatedDate BETWEEN :endDate AND :startDate) OR (marketplace.updatedDate = :endDate) OR (marketplace.updatedDate = :startDate) ");
			}
			if (candidateMarketPlaceDTO != null && !candidateMarketPlaceDTO.getEmploymentType().equals("")) {
				employmentTypeQuery.append(" and (marketplace.candidateId.designationType IN (:employmentList)) ");
			}
			if (candidateMarketPlaceDTO != null && !candidateMarketPlaceDTO.getNoticePeriod().equals("")) {
				noticePeriodQuery.append(" and (marketplace.candidateId.noticePeriod IN (:noticePeriodList)) ");
			}
			if (candidateMarketPlaceDTO != null && !candidateMarketPlaceDTO.getSkillsName().equals("")) {
				skillsQuery.append(" and (marketplace.candidateId.skillsName IN (:skillsList)) ");
			}
			if (candidateMarketPlaceDTO != null
					&& candidateMarketPlaceDTO.getIsFavourite().equals(Constants.ALL_RECORDS)) {
				typeQuery.append(" and (marketplace.candidateId.isFavourite = 0) ");
			}
			if (candidateMarketPlaceDTO != null
					&& candidateMarketPlaceDTO.getIsFavourite().equals(Constants.Favourite)) {
				typeQuery.append(" and (marketplace.candidateId.isFavourite = 1) ");
			}
			if (candidateMarketPlaceDTO != null
					&& candidateMarketPlaceDTO.getIsAssociate().equals(Constants.Candidates)) {
				candidateTypeQuery.append(" and (marketplace.candidateId.isAssociate = 'N') ");
			}
			if (candidateMarketPlaceDTO != null
					&& candidateMarketPlaceDTO.getIsAssociate().equals(Constants.Associates)) {
				candidateTypeQuery.append(" and (marketplace.candidateId.isAssociate = 'Y') ");
			}
		}

		if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
			searchValue = searchValue.trim();
			query1.append(
					" and ((concat(marketplace.candidateId.firstName,' ',marketplace.candidateId.lastName) like '%"
							+ searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.skillsName like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.currentRate like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.expectedRate like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.designation like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.summary like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.candidateProfilePercentage like '%" + searchValue + "%' )) ");
		}
		StringBuilder query2 = new StringBuilder("");
		if (candidateLocation != null && !"".equalsIgnoreCase(candidateLocation)) {
			query2.append(" and ((marketplace.candidateId.location like '%" + candidateLocation + "%' ))");
		}
		List<Long> searchCandiIdsLong = new ArrayList<>();
		if (searchCandidateIds.length != 0 && !searchCandidateIds[0].equals("undefined")) {
			query2.append(" and marketplace.candidateId.id IN ( :searchCandidateIds ) ");
			for (String s : searchCandidateIds) {
				searchCandiIdsLong.add(Long.valueOf(s));
			}
		}
		if (searchCandidateIds.length == 0 && orgId != 0 && candidateLocation.equals("") && searchValue.equals("")) {
			queryList = new ArrayList<>();
		} else {
			String listQuery = "from CandidateMarketPlace marketplace  where  (marketplace.status = 1) and (marketplace.candidateMarketStatus = 'Active') and (marketplace.orgLocationId.id <> :candidateOrgLocId) and (marketplace.candidateId.isSharedInMarketplace = 'Y') ";
			String emailQuery = "and (marketplace.candidateId.email not in (select Email from CandidateMarketCart cart where cart.targetOrgLocationId = :candidateOrgLocId and cart.cartStatus = 'Purchased'))";	
			Query queryObject = session.createQuery(listQuery + query1 + query2 + statusQuery + emailQuery
					 + dateQuery + employmentTypeQuery + noticePeriodQuery + skillsQuery
					 + typeQuery + candidateTypeQuery ).setParameter("candidateOrgLocId", orgId);

			if (searchCandidateIds.length != 0 && !searchCandidateIds[0].equals("undefined")) {
				queryObject.setParameterList("searchCandidateIds", searchCandiIdsLong);
			}
			if (candidateMarketPlaceDTO != null) {
				if (!candidateMarketPlaceDTO.getEmploymentType().equals("")) {
					queryObject.setParameterList("employmentList", employmentList);
				}
				if (!candidateMarketPlaceDTO.getNoticePeriod().equals("")) {
					queryObject.setParameterList("noticePeriodList", noticePeriodList);
				}
				if (!candidateMarketPlaceDTO.getSkillsName().equals("")) {
					queryObject.setParameterList("skillsList", skillsList);
				}
				if (!dateValue.isEmpty()) {
					Iterator it = dateValue.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry) it.next();
						if (pair.getKey().equals("fromDate")) {
							queryObject.setDate("startDate", dateValue.get("fromDate"));
						}
						if (pair.getKey().equals("toDate")) {
							queryObject.setDate("endDate", dateValue.get("toDate"));
						}
					}
				}
			}
			queryList = queryObject.list();
		}
		return queryList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateMarketPlace getCandidateMarketPlaceDetailsByUUId(String uuid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateMarketPlace marketplace where marketplace.marketplaceUUID =:uuid";
		CandidateMarketPlace marketPlaceCandidate = (CandidateMarketPlace) session.createQuery(query)
				.setParameter("uuid", uuid).uniqueResult();
		return marketPlaceCandidate;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int updateMarketplaceCandidates(OrgLocations orgLocations, String companyNameFlag) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "UPDATE CandidateMarketPlace cmp SET cmp.showCompanyName =:setShowCompanyName WHERE cmp.orgLocationId =:orgLocationId";
		int count= session.createQuery(query).setString("setShowCompanyName", companyNameFlag).setLong("orgLocationId", orgLocations.getId()).executeUpdate();
	    return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void addCandidateToMarketCart(CandidateMarketCart marketcart) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(marketcart);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getMarketCartStatusByMarketIdOrgId(Long marketPlaceId, Long orgId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(marketplaceCart) from CandidateMarketCart marketplaceCart where marketplaceCart.targetOrgLocationId.id = :orgId and marketplaceCart.marketplaceId.id = :marketplaceId and marketplaceCart.cartStatus <> :cartStatusVal";
		int count = ((Long) session.createQuery(query).setParameter("orgId", orgId)
				.setParameter("marketplaceId", marketPlaceId).setParameter("cartStatusVal", Constants.PURCHASED).uniqueResult()).intValue();
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteCandidateMarketCartByMarketUUID(String candidatgeMarketUUID) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from CandidateMarketCart marketplaceCart where marketplaceCart.marketplaceUUID = :marketUUID";
			session.createQuery(query).setParameter("marketUUID", candidatgeMarketUUID).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Object[] updateMarketplaceCartCountAndPrice(Long targetOrgLocationId, String userRole, User user) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "";
			String createdByQuery = " ";
			if (!userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
				createdByQuery = " and marketcart.createdBy=:createdByuser";
			}
			query = "select COUNT(marketplaceId) AS count, SUM(amount) AS SUM from CandidateMarketCart marketcart where marketcart.targetOrgLocationId =:targetOrgLocationId and marketcart.cartStatus IN (:cartStatus) ";

			Query queryObj = session.createQuery(query + createdByQuery)
					.setLong("targetOrgLocationId", targetOrgLocationId)
					.setParameterList("cartStatus", new ArrayList<String>(Arrays.asList(Constants.ADDED_CART)));
			if (!userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
				queryObj.setLong("createdByuser", user.getId());
			}
			Object[] result = (Object[]) queryObj.uniqueResult();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateMarketPlaceCartView> getCandidateDetailsFromMarketcart(User user, String userRole,
			OrgLocations orgLocation, String creationStartDate, String creationEndDate, Date[] checkedDateArray,
			String searchValue) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "";
			String createdByQuery = " ";
			if (!userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
				createdByQuery = " and marketCartview.createdBy=:createdByuser";
			}
			StringBuilder query1 = new StringBuilder("");
			if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
				query1.append(" and ((marketCartview.fullName like '%" + searchValue + "%' )");
				query1.append(" or (marketCartview.skillsName like '%" + searchValue + "%' ))");
			}
			StringBuilder dateQuery = new StringBuilder("");
			if (creationStartDate != null && creationEndDate != null && !"".equalsIgnoreCase(creationStartDate)
					&& !"".equalsIgnoreCase(creationEndDate)) {
				if (creationStartDate.contains("/") || creationEndDate.contains("/")) {
					creationStartDate = creationStartDate.replaceAll("/", "-");
					creationEndDate = creationEndDate.replaceAll("/", "-");
				}
				Date formattedStartdate = new ConversionUtil().getDateFromString(creationStartDate);
				Date formattedEnddate = new ConversionUtil().getDateFromString(creationEndDate);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String customStartdate = dateFormat.format(formattedStartdate);
				String customEnddate = dateFormat.format(formattedEnddate);
				dateQuery.append(" and ( DATE_FORMAT(marketCartview.createdDate, '%Y-%m-%d') between '"
						+ customStartdate + "' and '" + customEnddate + "') ");
			}
			StringBuilder checkBoxQuery = new StringBuilder("");
			if (checkedDateArray != null && checkedDateArray.length > 0) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String customStartdate = dateFormat.format(checkedDateArray[0]);
				String customEnddate = dateFormat.format(checkedDateArray[1]);
				checkBoxQuery.append("and ( DATE_FORMAT(marketCartview.createdDate, '%Y-%m-%d') between '"
						+ customStartdate + "' and '" + customEnddate + "') ");
			}
			query = "from CandidateMarketPlaceCartView marketCartview where marketCartview.targetOrgLocationId =:orgLocationId and marketCartview.cartStatus = 'In_Cart'";
			Query queryObj = session.createQuery(query + createdByQuery + dateQuery + checkBoxQuery + query1)
					.setLong("orgLocationId", orgLocation.getId());
			if (!userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
				queryObj.setLong("createdByuser", user.getId());
			}
			@SuppressWarnings("unchecked")
			List<CandidateMarketPlaceCartView> candidatesList = queryObj.list();
			if (candidatesList != null && !candidatesList.isEmpty()) {
				for (CandidateMarketPlaceCartView source : candidatesList) {
					source.setCreatedDatestr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
							source.getCreatedDate()));
				}
			}
			return candidatesList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteCandidateFromMarketcartByCandidateUuid(String candidateUuid) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from CandidateMarketCart marketCart where marketCart.candidateUUID = :candidateUuid";
			session.createQuery(query).setParameter("candidateUuid", candidateUuid).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CandidateMarketPlaceDTO> getGlobalDataRecommendations(
			CandidateMarketPlaceDTO candidateMarketPlaceDTO, OrgLocations orgLocations, String searchValue) {
		List<CandidateMarketPlace> queryList = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<CandidateMarketPlaceDTO> responseList = new ResponseList<>();
		Session session = this.sessionFactory.getCurrentSession();
		StringBuilder query1 = new StringBuilder();
		if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
			query1.append(" and ((concat(marketplace.candidateId.firstName,' ',marketplace.candidateId.lastName) like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.skillsName like '%" + searchValue + "%' )");
			query1.append(" or (concat(marketplace.candidateId.currentRateCurrency,' ',marketplace.candidateId.currentRate) like '%"
					+ searchValue + "%' )");
			query1.append(" or (concat(marketplace.candidateId.expectedRateCurrencey,' ',marketplace.candidateId.expectedRate) like '%"
					+ searchValue + "%' )");
			query1.append(" or (concat(marketplace.candidateId.experienceYears,'.',marketplace.candidateId.experienceMonths) like '%"
					+ searchValue + "%' )");
			query1.append(" or (concat(marketplace.marketRateCurrencey,' ',marketplace.marketPrice) like '%"
					+ searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.designation like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.location like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.summary like '%" + searchValue + "%' )");
			query1.append(" or (marketplace.candidateId.candidateProfilePercentage like '%" + searchValue + "%' )) ");
		}
		String sortingQry = "";
		if (StringUtils.isNotBlank(candidateMarketPlaceDTO.getSortColumn())) {
			String sortColumnName = candidateMarketPlaceDTO.getSortColumn();
			if(sortColumnName.equalsIgnoreCase("marketPrice")){
				sortingQry = " order by marketplace." + sortColumnName + " " + candidateMarketPlaceDTO.getSortOrder();
			}else{
				sortingQry = " order by marketplace.candidateId." + sortColumnName + " " + candidateMarketPlaceDTO.getSortOrder();
			}
		}
		List<Long> searchCandiIdsLong = new ArrayList<>();
		String listQuery = "from CandidateMarketPlace marketplace  where  marketplace.status = 1 and marketplace.candidateMarketStatus = 'Active' and marketplace.orgLocationId.id <> :candidateOrgLocId and marketplace.candidateId.isSharedInMarketplace = 'Y'";
		Query queryObject = session.createQuery(listQuery + query1 + sortingQry).setParameter("candidateOrgLocId",
				orgLocations.getId());
		queryList = queryObject.list();
		String countListQuery = "select count(marketplace) ";
		Query countQueryObject = session.createQuery(countListQuery + listQuery + query1 + sortingQry)
				.setParameter("candidateOrgLocId", orgLocations.getId());
		countlist = countQueryObject.list();
		int totalCount = 0;
		if (countlist != null && !countlist.isEmpty()) {
			totalCount = countlist.iterator().next().intValue();
			filterCount = totalCount;
		}
		responseList.setRecordsTotal(totalCount);
		if (query1.toString() != null && !query1.toString().isEmpty()) {
			responseList.setRecordsFiltered(filterCount);
		} else {
			responseList.setRecordsFiltered(totalCount);
		}
		List<CandidateMarketPlaceDTO> candidateMarketPlaceDTOs = new ArrayList<>();
		for (CandidateMarketPlace candidateMarketPlace : queryList) {
			CandidateMarketPlaceDTO marketPlaceDTO = new CandidateMarketPlaceDTO();
			BeanUtils.copyProperties(candidateMarketPlace, marketPlaceDTO);
			marketPlaceDTO.setFullName(candidateMarketPlace.getCandidateId().getFullName());
			marketPlaceDTO.setDesignation(candidateMarketPlace.getCandidateId().getDesignation());
			marketPlaceDTO.setSkillsName(candidateMarketPlace.getCandidateId().getSkillsName());
			marketPlaceDTO.setCurrentRate(candidateMarketPlace.getCandidateId().getCurrentRate());
			marketPlaceDTO.setCurrentRateCurrency(candidateMarketPlace.getCandidateId().getCurrentRateCurrency());
			marketPlaceDTO.setCurrentRateType(candidateMarketPlace.getCandidateId().getCurrentRateType());
			marketPlaceDTO.setExpectedRate(candidateMarketPlace.getCandidateId().getExpectedRate());
			marketPlaceDTO.setExpectedRateCurrencey(candidateMarketPlace.getCandidateId().getExpectedRateCurrencey());
			marketPlaceDTO.setExpectedRateType(candidateMarketPlace.getCandidateId().getRateType());
			marketPlaceDTO.setExperienceYears(candidateMarketPlace.getCandidateId().getExperienceYears());
			marketPlaceDTO.setExperienceMonths(candidateMarketPlace.getCandidateId().getExperienceMonths());
			marketPlaceDTO.setLocation(candidateMarketPlace.getCandidateId().getLocation());
			marketPlaceDTO.setProfilePercentage(candidateMarketPlace.getCandidateId().getCandidateProfilePercentage());
			int count = getMarketCartStatusByMarketIdOrgId(candidateMarketPlace.getId(),
					orgLocations.getId());
			if (count != 0) {
				marketPlaceDTO.setMarketCartStatus(Constants.ADDED_CART);
			}
			candidateMarketPlaceDTOs.add(marketPlaceDTO);
		}
		responseList.setData(candidateMarketPlaceDTOs);
		return responseList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateMarketCart> getMarketCartIdsFromMarketcart(Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CandidateMarketCart marketCart where marketCart.targetOrgLocationId = :orgLocationId and marketCart.cartStatus =:cartStatus";
			List<CandidateMarketCart> marketcardIds = session.createQuery(query).setLong("orgLocationId", orgLocationId).setParameter("cartStatus", Constants.ADDED_CART).list();
			return marketcardIds;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateMarketPlacePaymentTransactionHistory getMarketplaceTransactionDetails(Long orgLocationId) throws Exception{
		CandidateMarketPlacePaymentTransactionHistory transactionDetails = null;
		try{
			Session session = this.sessionFactory.getCurrentSession();	
			String query = "from CandidateMarketPlacePaymentTransactionHistory transaction where transaction.orgLocationId = :orgLocationId and transaction.id in(select max(transaction.id) from CandidateMarketPlacePaymentTransactionHistory transaction)" ;
		    transactionDetails =  (CandidateMarketPlacePaymentTransactionHistory) session.createQuery(query).setLong("orgLocationId", orgLocationId).uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return transactionDetails;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getUserDetailsBySourceOrgLocationId(Long sourceOrgLocationId)throws Exception{
		
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from User us where us.id IN (select user.id from OrgLocations OL where OL.id=:sourcelocationId) ";
			List<User> userDetails = session.createQuery(query).setLong("sourcelocationId", sourceOrgLocationId).list();
			User user = null;
			if (userDetails != null && userDetails.size() > 0) {
				user = (User) userDetails.iterator().next();
			}
			return user;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateMarketPlace getCandidateDetailsByCandidateId(Long candidateId) throws Exception{
		CandidateMarketPlace candidateDetails = null;
		try{
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CandidateMarketPlace marketplace where marketplace.candidateId.id IN (:candidateId)";
			candidateDetails = (CandidateMarketPlace) session.createQuery(query).setLong("candidateId",candidateId).uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return candidateDetails;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateDTO> getCandidatesListByIds(List<Long> Ids) {
		try{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.id IN (:Ids)";
		List<Candidate> newcandidate = session.createQuery(query).setParameterList("Ids", Ids).list();
		List<CandidateDTO> candidateDTOList = new ArrayList<>();
		for (Candidate candidate : newcandidate) {
			CandidateDTO dto = new CandidateDTO();
			BeanUtils.copyProperties(candidate, dto);
			candidateDTOList.add(dto);
		}
			return candidateDTOList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean updateMarketCartStatus(List<Long> marketCartIds, Long orgLocationId)  {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "update CandidateMarketCart marketCart set marketCart.cartStatus =:cartStatus where marketCart.id IN (:marketCartIds) and marketCart.targetOrgLocationId = :orgLocationId";
			session.createQuery(query).setParameter("cartStatus", Constants.PURCHASED)
					.setParameterList("marketCartIds", marketCartIds).setLong("orgLocationId", orgLocationId).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveCandidateMarketplacePaymentTranscationHistory(
			CandidateMarketPlacePaymentTransactionHistory tranHistory) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if (tranHistory != null) {
				session.save(tranHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateMarketPlace> getGlobalMarketplaceDataByOrgId(Long id, Long marketplaceId) {
		Session session = this.sessionFactory.getCurrentSession();
		String listQuery = "from CandidateMarketPlace marketplace  where  marketplace.status = 1 and marketplace.candidateMarketStatus = 'Active' and marketplace.orgLocationId.id <> :candidateOrgLocId and marketplace.candidateId.isSharedInMarketplace = 'Y' and marketplace.id <> :marketplaceId";
		List<CandidateMarketPlace> candidateMarketplaceCandidates = session.createQuery(listQuery).setParameter("candidateOrgLocId", id).setParameter("marketplaceId", marketplaceId).list();
		return candidateMarketplaceCandidates;
	}
	
	
	
	//  new method 

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveToMarketplacePurchaseOrder(MarketplacePurchaseOrder newMarketplacePurchaseorder) throws Exception {
		          logger.debug("Storing MarketPlace Purchase order details");
	         try {
	        	 Session session = this.sessionFactory.getCurrentSession();
	        	 if(newMarketplacePurchaseorder !=null)
	        	 {
	        		 session.saveOrUpdate(newMarketplacePurchaseorder);
	        	 }
	         }
	         catch(Exception e) {
	        	 e.printStackTrace();
	         }
	         }

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveToMarketplaceSalesOrder(MarketplaceSalesOrder newMarketplaceSalesorder) throws Exception {
		      logger.debug("Storing MarketPlace Sales order details");
		 try {
        	 Session session = this.sessionFactory.getCurrentSession();
        	 if(newMarketplaceSalesorder !=null)
        	 {
        		 session.saveOrUpdate(newMarketplaceSalesorder);
        	 }
         }
         catch(Exception e) {
        	 e.printStackTrace();
         }
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateMarketPlacePaymentTransactionHistory getOrderNo(Long id) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query="from CandidateMarketPlacePaymentTransactionHistory cmpth where cmpth.orgLocationId.id=:ids and id = (select max(id) from CandidateMarketPlacePaymentTransactionHistory) ";
			CandidateMarketPlacePaymentTransactionHistory transactionHistory = (CandidateMarketPlacePaymentTransactionHistory)session.createQuery(query).setLong("ids", id).uniqueResult();
			return transactionHistory;
		}
		catch(Exception e) {
       	 e.printStackTrace();
       	 return null;
        }
		
	}
	


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteAllCandidatesFromMarketcart(Long orgLocationId) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from CandidateMarketCart marketCart where marketCart.targetOrgLocationId.id = :orgLocationId";
			session.createQuery(query).setParameter("orgLocationId", orgLocationId).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<MarketplacePurchaseSalesDTO> getMarketplacePurchaseOrder(Long id, MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO, OrgLocations orgLocations, String searchValue,String purchaseOrderFromDate, String purchaseOrderToDate, Map<String, Date> dateValue) throws Exception{
		
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder queryBuilder1 = new StringBuilder();
			StringBuilder queryEndBuilder2 = new StringBuilder();
			StringBuilder dateQuery1 = new StringBuilder();
			ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
			List<Long> countlist = null;
			Integer filterCount = 0;
			List<MarketplacePurchaseSalesDTO> purchaseOrderDtoList = new ArrayList<>();
			MarketplacePurchaseSalesDTO purchaseOrderDto = null;
			String formattedDate =  null;
			if(searchValue != null && !"".equalsIgnoreCase(searchValue)){
				queryBuilder1.append(" and (cmpth.orderNo like '%"+searchValue+"%')) ");
			}
			if(!dateValue.isEmpty()){
				dateQuery1.append(" and (cmpth.orderDate BETWEEN :endDate AND :startDate) OR (cmpth.orderDate = :endDate) OR (cmpth.orderDate = :startDate) ");
			}
			if(marketplacePurchaseSalesDTO != null){
			if (StringUtils.isNotBlank(marketplacePurchaseSalesDTO.getSortColumn())) {
				String sortColumnName = marketplacePurchaseSalesDTO.getSortColumn();
				if(sortColumnName.equalsIgnoreCase("totalPrice")){
					queryEndBuilder2.append(" order by cmpth.totalPrice "+marketplacePurchaseSalesDTO.getSortOrder());
				}else{
					queryEndBuilder2.append(" order by cmpth." + sortColumnName + " "+marketplacePurchaseSalesDTO.getSortOrder());
				}
			}
			}
			/*
			 * Pattern pattern =
			 * Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
			 * Matcher matcher =
			 * pattern.matcher(marketplacePurchaseSalesDTO.getSearchValue()); java.sql.Date
			 * sqlDate = null; if(matcher.matches() == true) {
			 * if(marketplacePurchaseSalesDTO.getSearchValue().contains("/")||(
			 * marketplacePurchaseSalesDTO.getSearchValue().contains("-"))) { String
			 * replacedValue = marketplacePurchaseSalesDTO.getSearchValue().replaceAll("/",
			 * "-"); OrgLocations orgLoc =
			 * orgLocationService.getOrgLocations(marketplacePurchaseSalesDTO.
			 * getOrglocationId()); SimpleDateFormat format = new
			 * SimpleDateFormat(orgLoc.getDateFormat()); java.util.Date utilDate =
			 * format.parse(replacedValue); sqlDate = new java.sql.Date(utilDate.getTime());
			 * } }
			 * 
			 * 
			 * if(!dateValue.isEmpty()){ dateQuery1.
			 * append(" and (cmpth.orderDate BETWEEN :endDate AND :startDate) OR (cmpth.orderDate = :endDate) OR (cmpth.orderDate = :startDate) "
			 * ); }
			 * 
			 * StringBuilder query1 = new StringBuilder("");
			 * if(marketplacePurchaseSalesDTO.getSearchValue() !=null &&
			 * !"".equalsIgnoreCase(marketplacePurchaseSalesDTO.getSearchValue())) {
			 * query1.append("and (cmpth.orderNo like '%" +
			 * marketplacePurchaseSalesDTO.getSearchValue() + "%' ")
			 * .append("OR cmpth.companyName like '%" +
			 * marketplacePurchaseSalesDTO.getSearchValue() + "%' ")
			 * .append("OR cmpth.totalPrice like '%" +
			 * marketplacePurchaseSalesDTO.getSearchValue() + "%' ")
			 * .append("OR cmpth.serviceFee like '%" +
			 * marketplacePurchaseSalesDTO.getSearchValue() + "%' ")
			 * .append("OR cmpth.paymentStatus like '%" +
			 * marketplacePurchaseSalesDTO.getSearchValue() + "%' ")
			 * .append("OR cmpth.discount like '%" +
			 * marketplacePurchaseSalesDTO.getSearchValue() + "%' )"); if(sqlDate != null) {
			 * query1.append("OR cmpth.orderDate like '%" + sqlDate + "%' "); }
			 * 
			 * }
			 */
				
			String query="from MarketplacePurchaseOrder cmpth where cmpth.orgLocationId.id = :orgLocationId ";
			String countQuery = "select count(*) from MarketplacePurchaseOrder cmpth  where  cmpth.orgLocationId =:orgLocationId ";
			/*
			 * Query qeury = session.createQuery(query +
			 * dateQuery1.toString()).setLong("orgLocationId", id); Query q =
			 * session.createQuery(countQuery +
			 * dateQuery1.toString()).setLong("orgLocationId", id);
			 */
			Query qeury = session.createQuery(query + queryBuilder1.toString() + dateQuery1.toString() + queryEndBuilder2.toString()).setLong("orgLocationId",  orgLocations.getId());
			Query q = session.createQuery(countQuery + queryBuilder1.toString() + dateQuery1.toString() + queryEndBuilder2.toString()).setLong("orgLocationId",  orgLocations.getId());
						if(!dateValue.isEmpty()){
							Iterator it = dateValue.entrySet().iterator();
						    while (it.hasNext()) {
						        Map.Entry pair = (Map.Entry)it.next();
						        if(pair.getKey().equals("fromDate")){
						        	qeury.setDate("startDate", dateValue.get("fromDate"));
						        	q.setDate("startDate", dateValue.get("fromDate"));
						        }
						        if(pair.getKey().equals("toDate")){
						        	qeury.setDate("endDate", dateValue.get("toDate"));
						        	q.setDate("endDate", dateValue.get("toDate"));
						        }
						    }
						}
						List<MarketplacePurchaseOrder>  marketplacePurchaseOrder = qeury.list();
						countlist = q.list();
			int totalCount = 0;
			if (marketplacePurchaseSalesDTO != null) {
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
			if(marketplacePurchaseOrder != null){
				for(MarketplacePurchaseOrder purchase : marketplacePurchaseOrder){
					purchaseOrderDto = new MarketplacePurchaseSalesDTO();
					BeanUtils.copyProperties(purchase, purchaseOrderDto);
					formattedDate =  commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), purchase.getOrderDate());
					purchaseOrderDto.setOrderDate(formattedDate);
					purchaseOrderDtoList.add(purchaseOrderDto);
					}
				}
			responseList.setData(purchaseOrderDtoList);
			return responseList;
		}
		catch(Exception e) {
       	 e.printStackTrace();
       	 return null;
        }
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public MarketplacePurchaseOrder getPurchaseDetailByPurchaseUuid(String purchaseUuid, Long id) throws Exception {
		      logger.debug("fetching single record from marketplace purchase table using uuid");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query="from MarketplacePurchaseOrder cmpth where cmpth.purchaseUuid = :purchaseUuid";
			MarketplacePurchaseOrder marketplacePurchaseOrder=  (MarketplacePurchaseOrder) session.createQuery(query).setString("purchaseUuid", purchaseUuid).uniqueResult();
			return  marketplacePurchaseOrder;
		}
		catch(Exception e) {
       	 e.printStackTrace();
       	 return null;
        }
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<MarketplacePurchaseSalesDTO> getMarketplaceSalesOrderList(MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO, OrgLocations orgLocations, 
			String searchValue, String salesOrderFromDate, String salesOrderToDate, Map<String, Date> dateValue) {
		try{
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder queryBuilder1 = new StringBuilder();
			StringBuilder queryEndBuilder2 = new StringBuilder();
			StringBuilder dateQuery1 = new StringBuilder();
			List<Long> countlist = null;
			Integer filterCount = 0;
			List<MarketplacePurchaseSalesDTO> salesOrderDtoList = new ArrayList<>();
			MarketplacePurchaseSalesDTO salesOrderDto = null;
			String formattedDate =  null;
			ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
			if(searchValue != null && !"".equalsIgnoreCase(searchValue)){
				queryBuilder1.append(" and (salesOrder.orderNo like '%"+searchValue+"%')) ");
			}
			if(!dateValue.isEmpty()){
				dateQuery1.append(" and (salesOrder.orderDate BETWEEN :endDate AND :startDate) OR (salesOrder.orderDate = :endDate) OR (salesOrder.orderDate = :startDate) ");
			}
			if(marketplacePurchaseSalesDTO != null){
			if (StringUtils.isNotBlank(marketplacePurchaseSalesDTO.getSortColumn())) {
				String sortColumnName = marketplacePurchaseSalesDTO.getSortColumn();
				if(sortColumnName.equalsIgnoreCase("totalPrice")){
					queryEndBuilder2.append(" order by salesOrder.totalPrice "+marketplacePurchaseSalesDTO.getSortOrder());
				}else{
					queryEndBuilder2.append(" order by salesOrder." + sortColumnName + " "+marketplacePurchaseSalesDTO.getSortOrder());
				}
			}
			}
			String query = "from MarketplaceSalesOrder salesOrder where salesOrder.orgLocationId.id = :orgLocationId";
			String countQuery = "select count(*) from MarketplaceSalesOrder salesOrder  where  salesOrder.orgLocationId =:orgLocationId";
			Query query1 = session.createQuery(query + queryBuilder1.toString() + dateQuery1.toString() + queryEndBuilder2.toString()).setLong("orgLocationId", orgLocations.getId());
			Query query2 = session.createQuery(countQuery + queryBuilder1.toString() + dateQuery1.toString() + queryEndBuilder2.toString()).setLong("orgLocationId", orgLocations.getId());
			
			if(!dateValue.isEmpty()){
				Iterator it = dateValue.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        if(pair.getKey().equals("fromDate")){
			        	query1.setDate("startDate", dateValue.get("fromDate"));
			        	query2.setDate("startDate", dateValue.get("fromDate"));
			        }
			        if(pair.getKey().equals("toDate")){
			        	query1.setDate("endDate", dateValue.get("toDate"));
			        	query2.setDate("endDate", dateValue.get("toDate"));
			        }
			    }
			}
			countlist = query2.list();
			int totalCount = 0;
			if (marketplacePurchaseSalesDTO != null) {
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
			List<MarketplaceSalesOrder> salesOrderList = query1.list();
			if(salesOrderList != null){
			for(MarketplaceSalesOrder sales : salesOrderList){
				salesOrderDto = new MarketplacePurchaseSalesDTO();
				BeanUtils.copyProperties(sales, salesOrderDto);
				formattedDate =  commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), sales.getOrderDate());
				salesOrderDto.setOrderDate(formattedDate);
				salesOrderDto.setTotalresume(sales.getNoOfResumes());
				salesOrderDto.setTotalPrice(sales.getTotalResumePrice());
				salesOrderDto.setCandidateName(sales.getCandidateMarketPlace().getCandidateId().getFullName());
				salesOrderDtoList.add(salesOrderDto);
				}
			}
			responseList.setData(salesOrderDtoList);
			return responseList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public MarketplaceSalesOrder getMarketplaceSalesOrderBySalesUuid(Long orgLocationId, String salesUuid) {
		try{
		Session session = this.sessionFactory.getCurrentSession();
		MarketplaceSalesOrder marketplaceSalesOrder = null;
		String query = "from MarketplaceSalesOrder salesOrder where salesOrder.orgLocationId.id =:orgLocationId and salesOrder.salesUuid =:salesUuid";
		marketplaceSalesOrder = (MarketplaceSalesOrder) session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("salesUuid", salesUuid).uniqueResult();
		return marketplaceSalesOrder;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<MarketplaceWalletDTO> getWalletList(MarketplaceWalletDTO marketplaceWalletDTO,
			String searchValue, String transactionType, Map<String, Date> dateValue) {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug(" inside getWalletList searchValue is: " + searchValue);
		List<Long> countlist = null;
		Integer filterCount = 0;

		List<CandidateMarketWallet> candidateMarketWallets = null;
		ResponseList<MarketplaceWalletDTO> responseList = new ResponseList<>();
		try {

			StringBuilder query1 = new StringBuilder("");
			if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
				query1.append(" and (cmwallet.transactionId like '%" + searchValue + "%' )");
			}
			StringBuilder transactionTypeQuery = new StringBuilder("");
			if(transactionType != null && !"".equalsIgnoreCase(transactionType)){
				List<String> tranTypeList = Arrays.asList(transactionType.split(","));
				transactionTypeQuery.append(" and (cmwallet.paymentStatus IN ( :transactionType ) )");
			}
			StringBuilder dateValueTypeQuery = new StringBuilder("");
			if(!dateValue.isEmpty()){
				dateValueTypeQuery.append(" and (cmwallet.transactionDate BETWEEN :endDate AND :startDate) OR (cmwallet.transactionDate = :endDate) OR (cmwallet.transactionDate = :startDate) ");
			}
			
			String sortingQry = "";
			if (StringUtils.isNotBlank(marketplaceWalletDTO.getSortColumn())) {
				String sortColumnName = marketplaceWalletDTO.getSortColumn();
				if(sortColumnName.equalsIgnoreCase("marketPrice")){
					sortingQry = " order by cmwallet." + sortColumnName + " " + marketplaceWalletDTO.getSortOrder();
				}
			}
			String  listQuery = "from CandidateMarketWallet cmwallet  where  cmwallet.orgLocationId.id =:orgLocationId  "+query1+"  "+transactionTypeQuery+" "+dateValueTypeQuery+" "+sortingQry+"";
			String 	listQueryCount = "select count(*) from CandidateMarketWallet cmwallet  where  cmwallet.orgLocationId.id =:orgLocationId  "+query1+" "+transactionTypeQuery+" "+dateValueTypeQuery+" "+sortingQry+"";
			Query hqlQuery = session.createQuery(listQuery).setFirstResult(marketplaceWalletDTO.getStart()).setMaxResults(marketplaceWalletDTO.getLength()).setLong("orgLocationId", marketplaceWalletDTO.getOrgLocationId());
			Query hqlQuery1 = session.createQuery(listQueryCount).setLong("orgLocationId", marketplaceWalletDTO.getOrgLocationId());
			if(transactionType != null && !"".equalsIgnoreCase(transactionType)){
				List<String> tranTypeList = Arrays.asList(transactionType.split(","));
				hqlQuery.setParameterList("transactionType", tranTypeList);
				hqlQuery1.setParameterList("transactionType", tranTypeList);
			}
			if(!dateValue.isEmpty()){
				Iterator it = dateValue.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        if(pair.getKey().equals("fromDate")){
			        	hqlQuery.setDate("startDate", dateValue.get("fromDate"));
			        	hqlQuery1.setDate("startDate", dateValue.get("fromDate"));
			        }
			        if(pair.getKey().equals("toDate")){
			        	hqlQuery.setDate("endDate", dateValue.get("toDate"));
			        	hqlQuery1.setDate("endDate", dateValue.get("toDate"));
			        }
			    }
			}
			countlist = hqlQuery1.list();
			candidateMarketWallets = hqlQuery.list();
			int totalCount = 0;
			if (marketplaceWalletDTO != null) {
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
			List<MarketplaceWalletDTO> globalMarketCandDTOList = new ArrayList<MarketplaceWalletDTO>();
			Double totalSum =0d;
			for (CandidateMarketWallet source : candidateMarketWallets) {
				MarketplaceWalletDTO target = new MarketplaceWalletDTO();
				BeanUtils.copyProperties(source, target);
				target.setTransactionDate(commonUtil.dateStrFromOrgDateFormatter(source.getOrgLocationId().getDateFormat(), source.getTransactionDate()));
				globalMarketCandDTOList.add(target);
			}
			responseList.setData(globalMarketCandDTOList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
 }

	@Override
	@Transactional
	public Candidate getCandidateByMasterCandidateId(Long masterCandidateId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Candidate c where c.masterCandidateId.id=:masterCandidateId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("masterCandidateId", masterCandidateId);
			List<Candidate> candidateList = queryObj.list();
			if(candidateList != null && !candidateList.isEmpty()) {
				return candidateList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<MarketplacePurchaseOrder> getPurchaseCandidateListByOrglocation(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from MarketplacePurchaseOrder mpo where mpo.orgLocationId.id=:rajat";
		List<MarketplacePurchaseOrder> list = session.createQuery(query).setLong("rajat", id).list();
		return list;
	}
	public List<Candidate> getCandidateDetailsListById(List<Long> Ids) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Candidate candidate where candidate.id IN (:Ids)";
			List<Candidate> candidates = session.createQuery(query).setParameterList("Ids", Ids).list();
			return candidates;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveCandidateMarketPlaceWallet(CandidateMarketWallet wallet) throws Exception {
		logger.debug("Storing CandidateMarketWallet");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (wallet != null) {
				session.save(wallet);
			}
			logger.debug("Marketplace Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "CandidateMarketWallet"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Double getBalanceAmountFromWallet(Long id) {
		logger.debug("getBalanceAmountFromWallet");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select wallet.totalBalanceAmount from CandidateMarketWallet wallet where wallet.orgLocationId.id = :orgLocationId";
		Double totalAmount = (Double) session.createQuery(query).setFirstResult(0).setMaxResults(1).setParameter("orgLocationId", id).uniqueResult();
		return totalAmount;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateMarketWalletAmount(Double balanceAmountValue, Long id) {
		logger.debug("updateMarketWalletAmount");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "update CandidateMarketWallet wallet set wallet.totalBalanceAmount = :totalAmountValue where wallet.orgLocationId.id = :orgLocationId";
		int count = session.createQuery(query).setParameter("totalAmountValue", balanceAmountValue).setParameter("orgLocationId", id).executeUpdate();
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateMarketCart getCandidateFromMarketCartByMarketplaceId(Long marketplaceId) {
		// TODO Auto-generated method stub
		try{
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CandidateMarketCart marketcart where marketcart.marketplaceId.id = :marketplaceId";
			CandidateMarketCart marketcart = (CandidateMarketCart) session.createQuery(query)
					.setParameter("marketplaceId", marketplaceId).uniqueResult();
			return marketcart;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteCandidateFromMarketplaceByCandidateUuid(String candidateUuid) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from CandidateMarketPlace marketPlace where marketPlace.candidateUUID = :candidateUuid";
			session.createQuery(query).setParameter("candidateUuid", candidateUuid).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllAssociateByLocation(Long orgLocationId) {	
		try {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candi  where candi.orgLocationId.id =:orgLocationId  and candi.isAssociate ='Y' ";
		List<Candidate> can = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return can;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
      }
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getCandidatePanNumberByUserId(long userId, Long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Candidate c where c.consultantUser.id=:userId and c.orgLocationId.id =:id";
						Candidate pan = (Candidate) session.createQuery(query).setLong("userId", userId).setLong("id", id).uniqueResult();
						if (pan != null) {
							return pan.getPanNumber();
						}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateDetailsByPanNumberAndId(String panNumber, Long orgLocationId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Candidate can where can.panNumber = :panNumber and can.orgLocationId=:orgLocationId and can.status = 1";
		List<Candidate> panNumberList =  session.createQuery(query) .setString("panNumber", panNumber).setLong("orgLocationId", orgLocationId).list();
		Candidate can = null;
		if(panNumberList != null && !panNumberList.isEmpty())
		{
			can = (Candidate) panNumberList.iterator().next();
		}
		return can;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validatePanNumber(String panNumber, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from Candidate can where can.panNumber = :panNumber and can.orgLocationId=:orgLocationId";
		List<Candidate> panNumbersList = session.createQuery(query).setString("panNumber", panNumber).setLong("orgLocationId", orgLocationId).list();
		if (panNumbersList != null && panNumbersList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateDetailsByAadharNoAndId(String aadharNumber, Long orgLocationId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Candidate can where can.aadharNumber = :aadharNumber and can.orgLocationId=:orgLocationId and can.status = 1";
		List<Candidate> aadharNumberList =  session.createQuery(query) .setString("aadharNumber", aadharNumber).setLong("orgLocationId", orgLocationId).list();
		Candidate can = null;
		if(aadharNumberList != null && !aadharNumberList.isEmpty())
		{
			can = (Candidate) aadharNumberList.iterator().next();
		}
		return can;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateAadharNumber(String aadharNumber, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from Candidate can where can.aadharNumber = :aadharNumber and can.orgLocationId=:orgLocationId";
		List<Candidate> aadharNumberList = session.createQuery(query).setString("aadharNumber", aadharNumber).setLong("orgLocationId", orgLocationId).list();
		if (aadharNumberList != null && aadharNumberList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateDetailsBySsnNoAndId(String ssnNumber, Long orgLocationId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Candidate can where can.ssnNumber = :ssnNumber and can.orgLocationId=:orgLocationId and can.status = 1";
		List<Candidate> ssnNumberList =  session.createQuery(query) .setString("ssnNumber", ssnNumber).setLong("orgLocationId", orgLocationId).list();
		Candidate can = null;
		if(ssnNumberList != null && !ssnNumberList.isEmpty())
		{
			can = (Candidate) ssnNumberList.iterator().next();
		}
		return can;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateSsnNumber(String ssnNumber, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from Candidate can where can.ssnNumber = :ssnNumber and can.orgLocationId=:orgLocationId";
		List<Candidate> ssnNumberList = session.createQuery(query).setString("ssnNumber", ssnNumber).setLong("orgLocationId", orgLocationId).list();
		if (ssnNumberList != null && ssnNumberList.size() > 0) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	@Transactional
	public String getCandidateDateOfBirthByUserId(long userId, OrgLocations orgLocation) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Candidate c where c.consultantUser.id=:userId and c.orgLocationId.id =:orgLocationId";
			Candidate candidate = (Candidate) session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocation.getId()).uniqueResult();
			if (candidate != null) {
				return commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), candidate.getDateOfBirth());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesForUpdateWorkLocation(Long masterCandidateId, Long orgId) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Candidate candidate left join fetch candidate.masterCandidateId left join fetch candidate.orgLocationId orgLoc left join fetch orgLoc.org where "
					+ " candidate.masterCandidateId.id = :masterCandidateId and orgLoc.org.id = :orgId ";
			List<Candidate> candidatesList = session.createQuery(query).setLong("masterCandidateId", masterCandidateId).setLong("orgId", orgId).list();
			return candidatesList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getConsultantUserByCandidateIdOrMasterCandidateId(Long candidateId,Long masterCandidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		User user = null;
		String query = "select can.consultantUser from  Candidate can where can.id = :candidateId";
		List<User> consultantUserList = session.createQuery(query).setLong("candidateId", candidateId).list();
		if (consultantUserList != null && !consultantUserList.isEmpty()) {
			user = consultantUserList.get(0);
		}else {
		    query = "select can.consultantUser from  Candidate can where can.masterCandidateId.id = :masterCandidateId and can.consultantUser IS NOT NULL and can.consultantUser != 0";
		    consultantUserList = session.createQuery(query).setLong("masterCandidateId", masterCandidateId).list();
		    if (consultantUserList != null && !consultantUserList.isEmpty()) {
		    	user = consultantUserList.get(0);
		    }
		}
		return user;
		
	}

	@Override
	@Transactional
	public CandidateVerificationConfig saveDuplicateCheckConfig(CandidateVerificationConfig candidateVerificationConfig) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (candidateVerificationConfig != null) {
				session.saveOrUpdate(candidateVerificationConfig);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return candidateVerificationConfig;
		
	}
//	@Override
//	@Transactional(rollbackFor = { Exception.class })
//	public Candidate getCandidateDetailsByAadharNoAndId(String aadharNumber, Long orgLocationId) throws Exception {
//		Session session =  this.sessionFactory.getCurrentSession();
//		String query = "from Candidate can where can.aadharNumber = :aadharNumber and can.orgLocationId=:orgLocationId and can.status = 1";
//		List<Candidate> aadharNumberList =  session.createQuery(query) .setString("aadharNumber", aadharNumber).setLong("orgLocationId", orgLocationId).list();
//		Candidate can = null;
//		if(aadharNumberList != null && !aadharNumberList.isEmpty())
//		{
//			can = (Candidate) aadharNumberList.iterator().next();
//		}
//		return can;
//	}
	@Override
	@Transactional
	public CandidateVerificationConfig getCandidateVerificationConfigBy(Long orgLocationId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from CandidateVerificationConfig can where can.orgLocationId=:orgLocationId and can.status = 1";
		CandidateVerificationConfig candidateVerificationConfig = (CandidateVerificationConfig) session.createQuery(query).setLong("orgLocationId", orgLocationId).uniqueResult();
		return candidateVerificationConfig;
	}

	@Override
	@Transactional
	public CandidateVerificationConfig getCandidateVerificationConfigByIdAndOrglocationId(Long orgLocationId, Long id) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from CandidateVerificationConfig can where can.orgLocationId=:orgLocationId and can.status = 1 and can.id=:id";
		CandidateVerificationConfig candidateVerificationConfig = (CandidateVerificationConfig) session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("id", id).uniqueResult();
		return candidateVerificationConfig;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateSource> getCandidateSourceListByOrgLocationId(Long orgLocationId, int status, String searchValue,CandidateSourceDTO candidateSourceDTO) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CandidateSource t where t.orgLocations.id=" + orgLocationId;
			if(status >= 0) {
				query += " and t.status=" + status;
			}
			if(searchValue != null && !searchValue.isEmpty()) {
				query += " and (t.name like '%" + searchValue + "%' or t.description like '%" + searchValue + "%')";
			}
			String qry = "";
			if (StringUtils.isNotBlank(candidateSourceDTO.getSortColumn())) {
				String sortColumnName = candidateSourceDTO.getSortColumn();
					qry = " order by t." + sortColumnName + " " + candidateSourceDTO.getSortOrder();
			}
			String finalQuery = query+qry;
			return session.createQuery(finalQuery).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public long saveCandidateSource(CandidateSource candidateSource) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(candidateSource);
			return candidateSource.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateSource getCandidateSourceById(Long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(CandidateSource.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public long getCandidateSourceByOrgLocationId(String name,long userId, long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query= "select cs.id from CandidateSource cs where cs.name=:name and cs.orgLocations.id=:orgLocationsId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("name", name);
			queryObj.setLong("orgLocationsId", orgLocationId);
			List<Long> candidateSourceIdlist = queryObj.list();
			if(candidateSourceIdlist != null && !candidateSourceIdlist.isEmpty()) 
				return candidateSourceIdlist.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean deleteCandidateSourceById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CandidateSource candidateSource = session.get(CandidateSource.class, id);
			if (null != candidateSource) {
				session.delete(candidateSource);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;


	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveSourceleadDeletedCandidate(SourceleadDeletedCandidates sourceleadDeletedCandidates)
			throws Exception {
		logger.debug("Storing SourceleadDeletedCandidates");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (sourceleadDeletedCandidates != null) {
				session.saveOrUpdate(sourceleadDeletedCandidates);
			}
			logger.debug("SourceleadDeletedCandidates Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "SourceleadDeletedCandidates"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateSource> getCandidateSourceListByOrgLocationIdAndStatus(Long orgLocationId, int status)throws Exception {
		try {
			Session session =  this.sessionFactory.getCurrentSession();
			String query = "from CandidateSource cs where cs.orgLocations.id = :orgLocationId and cs.status =:status";
			
			List<CandidateSource> candidateSource = session.createQuery(query).setLong("orgLocationId", orgLocationId).setParameter("status", status).list();
			return candidateSource;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getOffshoreCandidatesByParentCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate can where can.parentCandidateId.id=:candidateId and can.designationType = '"+Constants.OFFSHORE_C2C+"'";
		List<Candidate> list = session.createQuery(query).setLong("candidateId", candidateId).list();
		return list;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandiateByConsultantUserIdAndOrgLocationId(Long userId, Long orgLocationId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and "
				+ "candidate.consultantUser.id = :userId  ";
		List<Candidate> candidateList =  session.createQuery(query) .setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
		Candidate canidate = null;
		if(candidateList != null && !candidateList.isEmpty())
		{
			canidate = (Candidate) candidateList.iterator().next();
		}
		return canidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getGlobalCandidateIdByCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query =  "SELECT "
				+" CAND.CANDIDATE_GLOBAL_ID "           
				+" FROM CANDIDATE CAND "
				+" WHERE CAND.ID = :candidateId ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("candidateId", candidateId);
		BigInteger candidateGlobalId = (BigInteger) queryObj.uniqueResult();
		
		return candidateGlobalId.longValue();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByCandidateGlobalId(Long candidateGlobalId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  " + "left join fetch cnadidate.consultantUser "
				+ "left join fetch cnadidate.teamId " + "left join fetch cnadidate.recruiterUserId "
				+ "left join fetch cnadidate.masterCandidateId " + "left join fetch cnadidate.parentCandidateId "
				+ "left join fetch cnadidate.orgRelationId left "
				+ "join fetch cnadidate.spocId left join fetch cnadidate.orgLocationId left join fetch cnadidate.orgLocationId.user where cnadidate.candidateGlobalId.id =:candidateGlobalId";
		List candidateList = session.createQuery(query).setLong("candidateGlobalId", candidateGlobalId).list();
		Candidate candidate = null;
		if (candidateList != null && !candidateList.isEmpty()) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	
	}

	@Override
	@Transactional
	public Long getCandidateIdByGlobalCandidateId(Long globalCandidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Long candidateId = 0L;
		String query =  "SELECT "
				+" CAND.ID "           
				+" FROM CANDIDATE CAND "
				+" WHERE CAND.CANDIDATE_GLOBAL_ID = :globalCandidateId ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("globalCandidateId", globalCandidateId);
		BigInteger candidateGlobalId = (BigInteger) queryObj.uniqueResult();
		if(candidateGlobalId != null) {
			candidateId = candidateGlobalId.longValue();
		}
		return candidateId;
	}


}
