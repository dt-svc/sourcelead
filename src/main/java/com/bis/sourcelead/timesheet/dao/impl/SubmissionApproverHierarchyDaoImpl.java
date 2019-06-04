package com.bis.sourcelead.timesheet.dao.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimesheetApprovalSubmissionDetailsDTO;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimesheetActivities;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.timesheet.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.timesheet.dao.TimesheetEntriesDao;
import com.bis.sourcelead.timesheet.service.TimesheetActivityService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;

@Repository
public class SubmissionApproverHierarchyDaoImpl implements SubmissionApproverHierarchyDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ProjectMembersDao projectMembersDao;
	@Autowired
	UserDao userDao;
	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;
	@Autowired
	TimeSheetDayEntriesDao timeSheetDayEntriesDao;
	@Autowired
	private ProjectsDao projectsDao;
	@Autowired
	private OrgLocationsDao orgLocationsDao;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	@Autowired
	private TimesheetActivityService timesheetActivityService;
	@Autowired
	private ProjectMembersService projectMembersService;
	@Autowired
	private AppliedLeaveDateService appliedLeaveDateService;
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
	private static final Logger logger = LoggerFactory.getLogger(SubmissionApproverHierarchyDaoImpl.class);
	
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(submissionApproverHierarchy);
			/*TimesheetActivities sahh = CommonUtil.getSubmissionApproverHierarchyHistory(submissionApproverHierarchy);
			if(sahh != null) {
				session.saveOrUpdate(sahh);
			}*/
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "SUBMISSION_APPROVE_HIERARCHY"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return submissionApproverHierarchy;
	}
	
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		if (submissionApproverHierarchy != null) {
			session.saveOrUpdate(submissionApproverHierarchy);
			/*TimesheetActivities sahh = CommonUtil.getSubmissionApproverHierarchyHistory(submissionApproverHierarchy);
			if(sahh != null && sahh.getSubmissionApproverHierarchyId() != null) {
				session.saveOrUpdate(sahh);
			}*/
		}
		if (submissionApproverHierarchy == null) {
			StringBuilder message = new StringBuilder(MessageFormat.format(statusUpdate, "SUBMISSION_APPROVE_HIERARCHY"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return submissionApproverHierarchy;
	}
	
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission left join fetch submission.projectMembers  where sah.id = :id");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("id", Long.parseLong(id)).list();
		SubmissionApproverHierarchy sah = null;
		if (null != submissionApproverHierarchyList && !submissionApproverHierarchyList.isEmpty()) {
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}
	
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteSubmissionApproverHierarchy(String id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		try {
			if (id != null) {
				SubmissionApproverHierarchy submissionApproverHierarchy = (SubmissionApproverHierarchy) session.get(SubmissionApproverHierarchy.class, id);
				submissionApproverHierarchy.setStatus("INACTIVE");;
				session.saveOrUpdate(submissionApproverHierarchy);
				isDeleted = true;
				logger.info("Successfully deleted..!");
			}
			else {
				isDeleted = false;
			}
		} catch (Exception e) {
			StringBuilder message = new StringBuilder(MessageFormat.format(statusDelete, "SUBMISSION_APPROVE_HIERARCHY"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return isDeleted;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies() throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = null;
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy");
		submissionApproverHierarchyList = submissionApproverHierarchyListQuery.list();;
		return submissionApproverHierarchyList;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId where sah.submissionId.id = :submissionId");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).list();
		// return submissionApproverHierarchyList;
		Boolean isRejected = false;
		Boolean isSUBMITTED = false;
		Boolean isNEW = false;
		Boolean isDRAFT = false;
		SubmissionApproverHierarchy submissionApproverHierarchy = null;
		for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
			if (sah.getSubmissionStatus() == Submission_Status.REJECTED) {
				isRejected = true;
				submissionApproverHierarchy = sah;
			}
			else if (!isRejected && sah.getSubmissionStatus() == Submission_Status.NEW) {
				isNEW = true;
				submissionApproverHierarchy = sah;
			}
			else if (!isRejected && !isNEW
					&& sah.getSubmissionStatus() == Submission_Status.DRAFT) {
				isDRAFT = true;
				submissionApproverHierarchy = sah;
			}
			else if (!isRejected && !isNEW && !isDRAFT
					&& sah.getSubmissionStatus() == Submission_Status.SUBMITTED) {
				isSUBMITTED = true;
				submissionApproverHierarchy = sah;
			}
			else if (!isRejected && !isNEW && !isDRAFT && !isSUBMITTED
					&& sah.getSubmissionStatus() == Submission_Status.APPROVED) {
				submissionApproverHierarchy = sah;
			}
		}
		return submissionApproverHierarchy;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm where pm.id = :projectMemberId and sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).list();
		// return submissionApproverHierarchyList;
		Boolean isRejected = false;
		Boolean isSUBMITTED = false;
		Boolean isNEW = false;
		Boolean isDRAFT = false;
		SubmissionApproverHierarchy submissionApproverHierarchy = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (sah.getSubmissionStatus() == Submission_Status.REJECTED) {
					isRejected = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && sah.getSubmissionStatus() == Submission_Status.NEW) {
					isNEW = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW
						&& sah.getSubmissionStatus() == Submission_Status.DRAFT) {
					isDRAFT = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW && !isDRAFT
						&& sah.getSubmissionStatus() == Submission_Status.SUBMITTED) {
					isSUBMITTED = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW && !isDRAFT && !isSUBMITTED
						&& sah.getSubmissionStatus() == Submission_Status.APPROVED) {
					submissionApproverHierarchy = sah;
				}
				// manager 1 Approved Scenario
				else if (!isRejected && !isNEW && !isDRAFT
						&& sah.getSubmissionStatus() == Submission_Status.APPROVED) {
					submissionApproverHierarchy = sah;
				}
			}
		}
		else {
			submissionApproverHierarchy = new SubmissionApproverHierarchy();
			submissionApproverHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("new"));
			submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
		}
		return submissionApproverHierarchy;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchiesByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId, Long locationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		ProjectMembers projectMembers = projectMembersDao.getProjectMemberById(projectMemberId);
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient  left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.masterCandidateId mc left join fetch submission.projectMembers pm left join fetch submission.projects projects left join fetch pm.projects project left join fetch project.orgLocationsId "
				+ " where projects.id =:projectId and mc.id = :masterCandidateId and  sah.frequencyStartDate =:sqlStartDate and  sah.frequencyEndDate =:sqlEndDate and pclient.orgLocationsId.id = :locationId "
				+ " and sah.hierarchyId.level IN(select Max(subsah.hierarchyId.level) from SubmissionApproverHierarchy subsah  where sah.submissionId.projects.id = :projectId and  sah.submissionId.masterCandidateId.id = :masterCandidateId and subsah.frequencyStartDate =:sqlStartDate and subsah.frequencyEndDate =:sqlEndDate and subsah.hierarchyId.projectClientsId.orgLocationsId.id = :locationId and subsah.hierarchyId.status != '0')");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("locationId", locationId).setLong("projectId", projectMembers.getProjects().getId()).setLong("masterCandidateId", projectMembers.getMasterCandidateId().getId()).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).setCacheable(true).list();
		LinkedList<SubmissionApproverHierarchy> finalSahList = new LinkedList<>();
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			SubmissionApproverHierarchy finalSah = null;
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				finalSah = sah;
				projectMemberId = sah.getSubmissionId().getProjectMembers().getId();
				float weekTotalHours = 0f;
				float weekBillableTotalHours = 0f;
				float weekNonBillableTotalHours = 0f;
				if (Submission_ApprovalStatus.APPROVED.getValue().equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())) {
					List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), sah.getSubmissionId().getStartDate(), sah.getSubmissionId().getEndDate());
					if (tdeList != null && !tdeList.isEmpty()) {
						for (TimeSheetDayEntries tde : tdeList) {
							float totalHours = 0f;
							float totalBillableHours = 0f;
							float totalNonBillableHours = 0f;
							if (StringUtils.isNotEmpty(tde.getTotalHours())) {
								totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
							}
							weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
							if (StringUtils.isNotEmpty(tde.getBillableHours())) {
								totalBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getBillableHours().replaceAll(":", ".")), totalBillableHours);
							}
							weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, totalBillableHours);
							if (StringUtils.isNotEmpty(tde.getNonBillableHours())) {
								totalNonBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getNonBillableHours().replaceAll(":", ".")), totalNonBillableHours);
							}
							weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours, totalNonBillableHours);
						}
					}
					float totalHours = 0f;
					float billableTotalHours = 0f;
					float nonBillableTotalHours = 0f;
					if (finalSah.getTotalHours() != null) {
						totalHours = finalSah.getTotalHours();
					}
					if (finalSah.getBillableHours() != null) {
						billableTotalHours = finalSah.getBillableHours();
					}
					if (finalSah.getNonBillableHours() != null) {
						nonBillableTotalHours = finalSah.getNonBillableHours();
					}
					finalSah.setTotalHours(CommonUtil.getCombinedHours(totalHours, weekTotalHours));
					finalSah.setBillableHours(CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours));
					finalSah.setNonBillableHours(CommonUtil.getCombinedHours(nonBillableTotalHours, weekNonBillableTotalHours));
				}
				else {
					finalSah.setTotalHours(0f);
					finalSah.setBillableHours(0f);
					finalSah.setNonBillableHours(0f);
				}
				finalSahList.add(finalSah);
			}
		}
		return finalSahList;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(Date frequencyStartDate, Date frequencyEndDate, Long projectId, Long masterCandidateId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm left join fetch submission.projects pj left join fetch submission.masterCandidateId mc  where pj.id = :projectId and mc.id = :masterCandidateId and sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate and sah.status = '1'");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).list();
		// return submissionApproverHierarchyList;
		Boolean isRejected = false;
		Boolean isSUBMITTED = false;
		Boolean isNEW = false;
		Boolean isDRAFT = false;
		SubmissionApproverHierarchy submissionApproverHierarchy = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (sah.getSubmissionStatus() == Submission_Status.REJECTED) {
					isRejected = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && sah.getSubmissionStatus() == Submission_Status.NEW) {
					isNEW = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW
						&& sah.getSubmissionStatus() == Submission_Status.DRAFT) {
					isDRAFT = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW && !isDRAFT
						&& sah.getSubmissionStatus() == Submission_Status.SUBMITTED) {
					isSUBMITTED = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW && !isDRAFT && !isSUBMITTED
						&& sah.getSubmissionStatus() == Submission_Status.APPROVED) {
					submissionApproverHierarchy = sah;
				}
			}
		}
		else {
			submissionApproverHierarchy = new SubmissionApproverHierarchy();
			submissionApproverHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("new"));
			submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
		}
		return submissionApproverHierarchy;
	}
	
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByProjectIdAndMasterCandidateId(Long projectId, Long masterCandidateId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm left join fetch submission.projects pj left join fetch submission.masterCandidateId mc  where pj.id = :projectId and mc.id = :masterCandidateId and  sah.status = '1' group by concat(pj.id,mc.id,sah.frequencyStartDate,sah.frequencyEndDate) order by sah.hierarchyId.level desc");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).list();
		return submissionApproverHierarchyList;
	}
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeBySubmissionId(Long submissionId, Long masterCandidateId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm left join fetch submission.projects pj left join fetch submission.masterCandidateId mc  where submission.id = :submissionId and mc.id = :masterCandidateId");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setLong("masterCandidateId", masterCandidateId).list();
		// return submissionApproverHierarchyList;
		Boolean isRejected = false;
		Boolean isSUBMITTED = false;
		Boolean isNEW = false;
		Boolean isDRAFT = false;
		SubmissionApproverHierarchy submissionApproverHierarchy = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (sah.getSubmissionStatus() == Submission_Status.REJECTED) {
					isRejected = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && sah.getSubmissionStatus() == Submission_Status.NEW) {
					isNEW = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW
						&& sah.getSubmissionStatus() == Submission_Status.DRAFT) {
					isDRAFT = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW && !isDRAFT
						&& sah.getSubmissionStatus() == Submission_Status.SUBMITTED) {
					isSUBMITTED = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW && !isDRAFT && !isSUBMITTED
						&& sah.getSubmissionStatus() == Submission_Status.APPROVED) {
					submissionApproverHierarchy = sah;
				}
			}
		}
		else {
			submissionApproverHierarchy = new SubmissionApproverHierarchy();
			submissionApproverHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("new"));
			submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
		}
		return submissionApproverHierarchy;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean getCronRemainderForEmployeeByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate, Long masterCandidateId,Long projectId)
	{ 
		Boolean flag = true;
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm where pm.masterCandidateId.id = :masterCandidateId  and (sah.submissionStatus = 'SUBMITTED' OR sah.submissionStatus = 'APPROVED')  and  ((sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate) OR (submission.startDate = :sqlStartDate and submission.endDate = :sqlEndDate))");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("masterCandidateId", masterCandidateId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).list();
		if (null != submissionApproverHierarchyList && !submissionApproverHierarchyList.isEmpty()) {
			flag = false;
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isMissingTimesheetPerProjectByMasterCandidateIdAndProjectId(Date frequencyStartDate, Date frequencyEndDate, Long masterCandidateId,Long projectId)
	{ 
		Boolean flag = true;
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission left join fetch submission.projects proj  left join fetch submission.projectMembers pm where proj.id = :projectId and pm.masterCandidateId.id = :masterCandidateId  and (sah.submissionStatus = 'SUBMITTED' OR sah.submissionStatus = 'APPROVED')  and  ((sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate) OR (submission.startDate = :sqlStartDate and submission.endDate = :sqlEndDate))");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).list();
		if (null != submissionApproverHierarchyList && !submissionApproverHierarchyList.isEmpty()) {
			flag = false;
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId where sah.submissionId.id = :submissionId and sah.hierarchyId.level IN(select min(subsah.hierarchyId.level) from SubmissionApproverHierarchy subsah where subsah.submissionId.id = :submissionId)");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).list();
		// return submissionApproverHierarchyList;
		SubmissionApproverHierarchy submissionApproverHierarchy = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			submissionApproverHierarchy = submissionApproverHierarchyList.iterator().next();
		}
		return submissionApproverHierarchy;
	}
	
	@SuppressWarnings("unchecked")  
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId, Long approverUserId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient left join fetch sah.submissionId submission left join fetch submission.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch hierarchy.approverUserId left join fetch sah.submissionId where sah.submissionId.id = :submissionId and "
				+ " (hierarchy.approverUserId.id = :approverUserId "
				+ " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.id = :submissionId ) and "
				+ Constants.loginUserAsAdminWithMaxHeirarchyLevelForTs + "))" + "");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setLong("userId", approverUserId).setLong("approverUserId", approverUserId).list();
		SubmissionApproverHierarchy sah = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}
	
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId, Long approverUserId, Long submissionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and sah.submissionId.id = :submissionId and "
				/*
				 * logged in user as approver in project_approver_hierarchy
				 * (Either Admins/project_managers/team leads ...etc)
				 */
				+ "    ((hierarchy.approverUserId.id = :approverUserId ) "
				/*
				 * logged in user as admin but not in
				 * project_approver_hierarchy(hint:logged in user is admin but
				 * not assigned to project as approver)
				 */
				+ " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.projectMembers.employee.id = :empId  and subsah.submissionId.id = :submissionId) and "
				+ Constants.loginUserAsAdminWithMaxHeirarchyLevelForTs + "))" + ")";
		@SuppressWarnings("unchecked")
		List<SubmissionApproverHierarchy> sahList = session.createQuery(query).setLong("empId", empId).setLong("approverUserId", approverUserId).setLong("userId", approverUserId).setParameter("submissionId", submissionId).list();
		return sahList;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateSubmissionStatusByProjectMemberIdStartDateAndEndDate(Long projectMemberId, Date startDate, Date endDate, String status)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		Query submissionApproverHierarchyListQuery = session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pm.id = :projectMemberId and  (ts.startDate between :startDate and :endDate OR ts.startDate = :startDate)");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (status.equalsIgnoreCase("Draft")) {
					sah.setSubmissionStatus(Submission_Status.DRAFT);
					sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
				}
				else if (status.equalsIgnoreCase("Submit")) {
					sah.setSubmissionStatus(Submission_Status.SUBMITTED);
					sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
				}
				try {
					session.saveOrUpdate(sah);
				} catch (Exception e) {
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(Long projectId, Long masterCandidateId, Date startDate, Date endDate, String status)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		Query submissionApproverHierarchyListQuery = session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch ts.projects pj  left join fetch ts.masterCandidateId mc  left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pj.id = :projectId and mc.id = :masterCandidateId and  (ts.startDate between :startDate and :endDate OR ts.startDate = :startDate)");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (status.equalsIgnoreCase("Draft")) {
					sah.setSubmissionStatus(Submission_Status.DRAFT);
					sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
				}
				else if (status.equalsIgnoreCase("Submit")) {
					sah.setSubmissionStatus(Submission_Status.SUBMITTED);
					sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
				}
				try {
					session.saveOrUpdate(sah);
				} catch (Exception e) {
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(Long projectMemberId, Date startDate, Date endDate, String reason, User loginUser, String type, String isSuperUser)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		Query submissionApproverHierarchyListQuery = session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.submissionId submission left join fetch submission.masterCandidateId mc left join fetch submission.projects projects left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where project.id IN( select subPm.projects.id from ProjectMembers subPm where subPm.id = :projectMemberId ) and mc.id IN( select subPm.masterCandidateId.id from ProjectMembers subPm where subPm.id = :projectMemberId )  and  (ts.startDate between :startDate and :endDate OR ts.startDate = :startDate)");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			TimesheetActivities ta = null;
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (null != type && "notify".equalsIgnoreCase(type) && null != isSuperUser
						&& isSuperUser.equalsIgnoreCase(Constants.YES)) {
					sah.setReopenReason(reason);
					sah.setReopenedUser(loginUser);
					sah.setSubmissionStatus(Submission_Status.DRAFT);
					sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
					sah.setReopenRejectedReason(null);
				}
				else {
					if (null != type && "notify".equalsIgnoreCase(type)) {
						sah.setReopenReason(reason);
						sah.setReopenedUser(loginUser);
						sah.setReopenRejectedReason(null);
					}
					else if (null != type && "reject".equalsIgnoreCase(type)) {
						sah.setReopenRejectedReason(reason);
						sah.setReopenReason(null);
					}
					else {
						sah.setSubmissionStatus(Submission_Status.DRAFT);
						sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
						sah.setReopenRejectedReason(null);
						sah.setReopenReason(null);
					}
				}
				
				try {
					session.saveOrUpdate(sah);
					ta =   new TimesheetActivities();
	                ta.setActivityStatus(type);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(ta != null) {
			 timesheetActivityService.saveTimesheetActivity(ta);
			}
			return submissionApproverHierarchyList.get(0).getReopenedUser();
		}
		else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateApprovalStatusByApproverForProjectMemberId(Long projectMemberId, Long approverUserId, Date startDate, Date endDate, String status, String rejectedReason, OrgLocations orglocations,String isGlobalUser) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		User loginUser = userDao.getUserById(approverUserId);
		ProjectMembers projectMembers = projectMembersDao.getProjectMemberById(projectMemberId);
		String actionPerformedBy = null;
		Long actionPerformedByORgLocationId = null;
		if (projectMembers != null && projectMembers.getProjectMemberCandidteId() != null
				&& projectMembers.getProjectMemberCandidteId().getOrgLocationId() != null
				&& StringUtils.isNotEmpty(projectMembers.getProjectMemberCandidteId().getOrgLocationId().getOrgLocationName())) {
			actionPerformedBy = loginUser.getFullName()+"("+projectMembers.getProjectMemberCandidteId().getOrgLocationId().getOrgLocationName()+")";
			actionPerformedByORgLocationId = projectMembers.getProjectMemberCandidteId().getOrgLocationId().getId();
		}
		else {
			actionPerformedBy = loginUser.getFullName();
			actionPerformedByORgLocationId = orglocations.getId();
		}
		  String globalUserQuery = "";
          if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
        	  globalUserQuery =   "(sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationsId)) ";
          }else {
        	  globalUserQuery = " (sah.hierarchyId.projectClientsId.orgLocationsId.id = :orgLocationsId OR (:orgLocationsId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.status != 0) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationsId)))";
          }
		/*
		 * get submissionapproverhierarchy list by using empId and logged in
		 * user id as approver(either user may exist in project hierarchy or
		 * user may be admin)
		 */
		String query = " from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient  left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.masterCandidateId mc left join fetch submission.projectMembers pm left join fetch submission.projects projects left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " where projects.id =:projectId and mc.id = :masterCandidateId and  sah.frequencyStartDate =:startDate and  sah.frequencyEndDate =:endDate  "
				+ "  and  ("
				/*
				 * logged in user as approver in project_approver_hierarchy
				 * (Either Admins/project_managers/team leads ...etc)
				 */
				+ "    hierarchy.approverUserId.id = :approverUserId  "
				
				+ "  OR  ("
				+ "        :approverUserId NOT IN("
				+ "                select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.projects.id = :projectId and subsah.hierarchyId.projectClientsId.orgLocationsId.id =:orgLocationsId and subsah.submissionId.masterCandidateId.id = :masterCandidateId  and subsah.submissionApprovalStatus != 'APPROVED' and subsah.frequencyStartDate = :startDate and subsah.frequencyEndDate = :endDate "
				+ "           ) and sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser IS NOT NULL "
				+ "       and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser.id) and "
				+ "      sah.hierarchyId.level IN(select MIN(pah.level) from ProjectApproverHierarchy pah where ((pah.projectId.projectApprovalType  ='"+Constants.PARALLEL+"'  and pah.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationsId)) OR (pah.projectId.projectApprovalType  ='"+Constants.SEQUENTIAL+"' and "+globalUserQuery+"))  and pah.status != '0')  "
				+ "            and ((sah.hierarchyId.projectId.projectApprovalType  ='"+Constants.PARALLEL+"'  and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationsId)) OR (sah.hierarchyId.projectId.projectApprovalType  ='"+Constants.SEQUENTIAL+"' and "+globalUserQuery+")))"
				/*
				 * logged in user as admin but not in
				 * project_approver_hierarchy(hint:logged in user is admin but
				 * not assigned to project as approver)
				 */
				+ " OR (:approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.projects.id = :projectId and subsah.hierarchyId.projectClientsId.orgLocationsId.id =:orgLocationsId and subsah.submissionId.masterCandidateId.id = :masterCandidateId  and subsah.submissionApprovalStatus != 'APPROVED' and subsah.frequencyStartDate = :startDate and subsah.frequencyEndDate = :endDate ) "
				+ "  and " + Constants.loginUserAsAdminWithMaxHeirarchyLevelForTs + ")" 
				+ ")";
		List<SubmissionApproverHierarchy> sahMainList = session.createQuery(query).setLong("projectId", projectMembers.getProjects().getId()).setLong("masterCandidateId", projectMembers.getMasterCandidateId().getId()).setLong("approverUserId", approverUserId).setLong("orgLocationsId", orglocations.getId()).setLong("userId", approverUserId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		if (sahMainList != null && !sahMainList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : sahMainList) {
				if (status != null && status.equalsIgnoreCase("REJECTED")) {
					sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
					sah.setSubmissionStatus(Submission_Status.REJECTED);
					sah.setActionPerformedBy("" + actionPerformedBy);
					sah.setActionPerformedByOrgLocationId(actionPerformedByORgLocationId);
					sah.setRejectedReason(rejectedReason);
					sah.setReopenReason(null);
					sah.setReopenedUser(null);
					sah.setReopenRejectedReason(null);
				}
				else if (status != null && status.equalsIgnoreCase("APPROVED")) {
					sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
					sah.setSubmissionStatus(Submission_Status.APPROVED);
					sah.setReopenReason(null);
					sah.setReopenedUser(null);
					sah.setReopenRejectedReason(null);
					sah.setActionPerformedBy("" + actionPerformedBy);
					sah.setActionPerformedByOrgLocationId(actionPerformedByORgLocationId);
				}
				try {
					  updateSubmissionApproverHierarchy(sah);
			           TimesheetActivities ta = new TimesheetActivities();
	                   ta.setActivityStatus(status);
			          timesheetActivityService.saveTimesheetActivity(ta);
				} catch (Exception e) {
				}
				// Get Max Level in Current Location
				Query queryForMaxLevel = session.createQuery("select Max(sah.hierarchyId.level) from SubmissionApproverHierarchy sah  where  sah.submissionId.id = :submissionId and sah.hierarchyId.projectClientsId.id = :projectClientsId and sah.hierarchyId.status != '0' ");
				List<Integer> maxList = queryForMaxLevel.setLong("projectClientsId", sah.getHierarchyId().getProjectClientsId().getId()).setLong("submissionId", sah.getSubmissionId().getId()).list();
				Integer maxLevel = 0;
				if (maxList != null && !maxList.isEmpty()) {
					maxLevel = maxList.iterator().next();
				}
				String queryForAllApprovers = " from SubmissionApproverHierarchy sah left join fetch "
						+ " sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch loc.org org  left join fetch hierarchy.approverUserId left join fetch sah.submissionId submission left join fetch submission.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where sah.id <> :id and sah.submissionId = :submissionId";
				List<SubmissionApproverHierarchy> approversList = session.createQuery(queryForAllApprovers).setLong("submissionId", sah.getSubmissionId().getId()).setLong("id", sah.getId()).list();
				if (approversList != null && !approversList.isEmpty()) {
					for (SubmissionApproverHierarchy approver : approversList) {
						if (status != null && status.equalsIgnoreCase("REJECTED")) {
							approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
							approver.setSubmissionStatus(Submission_Status.REJECTED);
							approver.setActionPerformedBy(" " + actionPerformedBy);
							approver.setActionPerformedByOrgLocationId(actionPerformedByORgLocationId);
							approver.setRejectedReason(rejectedReason);
							approver.setReopenReason("");
							approver.setReopenedUser(null);
							approver.setReopenRejectedReason("");
						}
						else if (status != null && status.equalsIgnoreCase("APPROVED")) {
							if (approver.getHeirarchyId().getProjectClientsId() != null
									&& approver.getHeirarchyId().getProjectClientsId().getOrgLocationsId() != null
									&& approver.getHeirarchyId().getProjectClientsId().getOrgLocationsId().getOrg() != null
									&& approver.getHeirarchyId().getProjectClientsId().getOrgLocationsId().getOrg().getOrgType().equals(Constants.GUEST)) {
								approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
								approver.setSubmissionStatus(Submission_Status.APPROVED);
								approver.setActionPerformedBy(" " + actionPerformedBy);
								approver.setActionPerformedByOrgLocationId(actionPerformedByORgLocationId);
								approver.setReopenReason("");
								approver.setReopenedUser(null);
								approver.setReopenRejectedReason("");
							}
							else if ((approver.getHeirarchyId().getProjectClientsId().getLevel() > sah.getHeirarchyId().getProjectClientsId().getLevel())
									|| (approver.getHeirarchyId().getProjectClientsId().getLevel() == sah.getHeirarchyId().getProjectClientsId().getLevel()
											&& sah.getHeirarchyId().getLevel() == (maxLevel))) {
								approver.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
								approver.setSubmissionStatus(Submission_Status.APPROVED);
								approver.setActionPerformedBy(" " + actionPerformedBy);
								approver.setActionPerformedByOrgLocationId(actionPerformedByORgLocationId);
								approver.setReopenReason("");
								approver.setReopenedUser(null);
								approver.setReopenRejectedReason("");
							}
						}
						try {
							updateSubmissionApproverHierarchy(approver);
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")  
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId, String approvalStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId where sah.submissionId.id = :submissionId and sah.submissionApprovalStatus = :approvalStatus");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).setString("approvalStatus", approvalStatus).list();
		SubmissionApproverHierarchy sah = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}
	
	@SuppressWarnings("unchecked")  
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId, String approvalStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = " hierarchy.approverUserId.id = :userId ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "
				+ subQueryForUserIdChecking
				+ " and pm.projects.id = :projectId and  sah.submissionApprovalStatus = :submissionApprovalStatus)";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId", projectId).setString("submissionApprovalStatus", approvalStatus).list();
		Float totalHours = 0.0F;
		if (tslist != null) {
			for (int i = 0; i < tslist.size(); i++) {
				List<TimeSheetDayEntries> tdeList = null;
				try {
					tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(tslist.get(i).getSubmissionId().getId(), tslist.get(i).getSubmissionId().getStartDate(), tslist.get(i).getSubmissionId().getEndDate());
				} catch (Exception e) {
				}
				if (tdeList != null && !tdeList.isEmpty()) {
					for (TimeSheetDayEntries tde : tdeList) {
						totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
					}
				}
			}
		}
		return totalHours;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId, String submissionStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = " hierarchy.approverUserId.id = :userId ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "
				+ subQueryForUserIdChecking
				+ " and pm.projects.id = :projectId and  sah.submissionStatus = :submissionStatus)";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId", projectId).setString("submissionStatus", submissionStatus).list();
		Float totalHours = 0.0F;
		if (tslist != null) {
			for (int i = 0; i < tslist.size(); i++) {
				List<TimeSheetDayEntries> tdeList = null;
				try {
					tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(tslist.get(i).getSubmissionId().getId(), tslist.get(i).getSubmissionId().getStartDate(), tslist.get(i).getSubmissionId().getEndDate());
				} catch (Exception e) {
				}
				if (tdeList != null && !tdeList.isEmpty()) {
					for (TimeSheetDayEntries tde : tdeList) {
						totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
					}
				}
			}
		}
		return totalHours;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId, Long projectId, String approvalStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		// Getting List for which employee is Assigned
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects left join fetch ts.masterCandidateId mc left join fetch ts.projectMembers pm  where mc.id = :masterCandidateId and projects.id = :projectId ";
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.submissionId.masterCandidateId.id = mc.id and subsah.submissionId.projects.id = projects.id and subsah.hierarchyId.projectClientsId.level = '1' ";
		String query = subQueryForList + " and  ( hierarchy.level IN(" + subQueryForMaxLevel
				+ ")) and pc.level = '1' and  sah.submissionApprovalStatus = :approvalStatus";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setLong("projectId", projectId).setString("approvalStatus", approvalStatus).list();
		Float totalHours = 0.0F;
		if (tslist != null && !tslist.isEmpty()) {
			for (SubmissionApproverHierarchy sah : tslist) {
				List<TimeSheetDayEntries> tdeList = null;
				try {
					tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), sah.getSubmissionId().getStartDate(), sah.getSubmissionId().getEndDate());
				} catch (Exception e) {
				}
				if (tdeList != null && !tdeList.isEmpty()) {
					for (TimeSheetDayEntries tde : tdeList) {
						if (tde.getTotalHours() != null && !tde.getTotalHours().isEmpty()) {
							totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
						}
					}
				}
			}
		}
		float tsHours = totalHours;
		float tsHoursValue = (int) tsHours;
		float tsMinutesValue = tsHours - tsHoursValue;
		float totalMinutes = (tsHoursValue * 60) + (tsMinutesValue * 100);
		float actualHours = (int) (totalMinutes / 60);
		float actualMinutes = (totalMinutes % 60) / 100;
		String actualTimeString = String.format("%.2f", (actualHours + actualMinutes));
		return Float.parseFloat(actualTimeString);
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId, Long projectId, String submissionStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		// Getting List for which employee is Assigned
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects left join fetch ts.projectMembers pm left join fetch ts.masterCandidateId mc  where mc.id = :masterCandidateId and projects.id = :projectId ";
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select Max(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.hierarchyId.projectClientsId.level = '1' and  subsah.submissionId.masterCandidateId.id = mc.id and subsah.submissionId.projects.id = projects.id ";
		String query = subQueryForList + " and  ( hierarchy.level IN(" + subQueryForMaxLevel
				+ "))   and pc.level = '1' and sah.submissionStatus = :submissionStatus";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setLong("projectId", projectId).setString("submissionStatus", submissionStatus).list();
		Float totalHours = 0.0F;
		if (tslist != null && !tslist.isEmpty()) {
			for (SubmissionApproverHierarchy sah : tslist) {
				List<TimeSheetDayEntries> tdeList = null;
				try {
					tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), sah.getSubmissionId().getStartDate(), sah.getSubmissionId().getEndDate());
				} catch (Exception e) {
				}
				if (tdeList != null && !tdeList.isEmpty()) {
					for (TimeSheetDayEntries tde : tdeList) {
						if (tde.getTotalHours() != null && !tde.getTotalHours().isEmpty()) {
							totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
						}
					}
				}
			}
		}
		float tsHours = totalHours;
		float tsHoursValue = (int) tsHours;
		float tsMinutesValue = tsHours - tsHoursValue;
		float totalMinutes = (tsHoursValue * 60) + (tsMinutesValue * 100);
		float actualHours = (int) (totalMinutes / 60);
		float actualMinutes = (totalMinutes % 60) / 100;
		String actualTimeString = String.format("%.2f", (actualHours + actualMinutes));
		return Float.parseFloat(actualTimeString);
	}
	
		@SuppressWarnings("unchecked")   @Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndProjectIdForEmployee(Long userId, Long projectId, String submissionStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		// Getting List for which employee is Assigned
		String subQueryForUserIdChecking = " (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "
				+ subQueryForUserIdChecking + " and pm.projects.id = :projectId ";
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForUserIdCheckingInMaxLevel = " (subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForMaxLevel = " select Min(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "
				+ subQueryForUserIdCheckingInMaxLevel + " and pm.projects.id = :projectId ";
		String query = subQueryForList + " and  ( hierarchy.level IN(" + subQueryForMaxLevel
				+ "))  and  sah.submissionStatus = :submissionStatus ";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId", projectId).setString("submissionStatus", submissionStatus).list();
		return tslist;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndProjectIdForEmployee(Long userId, Long projectId, String approvalStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		// Getting List for which employee is Assigned
		String subQueryForUserIdChecking = " (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "
				+ subQueryForUserIdChecking + " and pm.projects.id = :projectId ";
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForUserIdCheckingInMaxLevel = " (subsah.submissionId.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) ";
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where "
				+ subQueryForUserIdCheckingInMaxLevel + " and pm.projects.id = :projectId ";
		String query = subQueryForList + " and  ( hierarchy.level IN(" + subQueryForMaxLevel
				+ "))  and  sah.submissionApprovalStatus = :approvalStatus ";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId", projectId).setString("approvalStatus", approvalStatus).list();
		return tslist;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndProjectIdForApprover(Long userId, Long projectId, String approvalStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = " hierarchy.approverUserId.id = :userId ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "
				+ subQueryForUserIdChecking
				+ " and pm.projects.id = :projectId and  sah.submissionStatus = :approvalStatus)";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId", projectId).setString("approvalStatus", approvalStatus).list();
		return tslist;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByProjectApproverHierarchyId(Long projectApproverHierarchyId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where hierarchy.id = :projectApproverHierarchyId)";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("projectApproverHierarchyId", projectApproverHierarchyId).list();
		return tslist;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectApproverHierarchyIdAndSubmissionId(Long projectApproverHierarchyId, Long submissionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where hierarchy.id = :projectApproverHierarchyId)";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("projectApproverHierarchyId", projectApproverHierarchyId).list();
		SubmissionApproverHierarchy sah = null;
		if (tslist != null && !tslist.isEmpty()) {
			sah = tslist.iterator().next();
		}
		return sah;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndProjectIdForApprover(Long userId, Long projectId, String submissionStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = " hierarchy.approverUserId.id = :userId ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "
				+ subQueryForUserIdChecking
				+ " and pm.projects.id = :projectId and  sah.submissionStatus = :submissionStatus)";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("projectId", projectId).setString("submissionStatus", submissionStatus).list();
		return tslist;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId, String submissionStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		// Getting List for which employee is Assigned
		String subQueryForList = " from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy  left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects left join fetch ts.masterCandidateId mc left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user ";
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.submissionId.id = ts.id and subsah.hierarchyId.projectClientsId.level = '1' ";
		String query = subQueryForList
				+ "  where mc.id = :masterCandidateId   and ( hierarchy.level IN("
				+ subQueryForMaxLevel
				+ ")) and  sah.submissionApprovalStatus = :submissionStatus and pc.level = '1' GROUP BY ts.id";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setString("submissionStatus", submissionStatus).list();
		return tslist;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLoginCount(Long masterCandidateId, String submissionStatus, Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		// Getting Max Hierarchy Level for which employee is Assigned
		
		String userQuery = "  (sah.submissionId.projectMembers.projectClientsId.orgLocationsId.id = :orgLocationId OR ( :orgLocationId NOT IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projectMembers.projects.id and subpm.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id) and sah.submissionId.projectMembers.projectMemberCandidteId.orgLocationId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projectMembers.projects.id and subpm.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"')  and sah.submissionId.projectMembers.projectMemberCandidteId.orgLocationId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)))";
	
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.submissionId.id = sah.submissionId.id and "+userQuery;
		String query = " Select Count (*) from  SubmissionApproverHierarchy sah  where sah.submissionId.masterCandidateId.id = :masterCandidateId   and ( sah.hierarchyId.level IN("
				+ subQueryForMaxLevel
				+ ")) and  sah.submissionApprovalStatus = :submissionStatus and "+userQuery+" GROUP BY CONCAT(sah.submissionId.projects.id,sah.frequencyStartDate,sah.frequencyEndDate)";
		List<Long> tslist = session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setLong("orgLocationId", orgLocationId).setString("submissionStatus", submissionStatus).list();
		Integer count = 0;
		if (tslist != null && !tslist.isEmpty()) {
			count = tslist.size();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId, String submissionStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.submissionId.id = ts.id and subsah.hierarchyId.projectClientsId.level = '1' ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy  left join fetch hierarchy.projectClientsId pc "
				+ " left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects "
				+ " left join fetch ts.masterCandidateId mc left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user"
				+ "  where mc.id = :masterCandidateId   and ( hierarchy.level IN("
				+ subQueryForMaxLevel
				+ ")) and  sah.submissionStatus = :submissionStatus  and pc.level = '1' GROUP BY ts.id";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setString("submissionStatus", submissionStatus).list();
		return tslist;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLoginCount(Long masterCandidateId, String submissionStatus, Long orgLocationId,Long orgId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		 String locationQuery = " and (sah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId OR (:locationsId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.status != 0) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)))";
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from  SubmissionApproverHierarchy subsah  where subsah.submissionId.id = sah.submissionId.id  "
			                         +locationQuery;
		//	+ " (subsah.hierarchyId.projectClientsId.orgLocationsId.id = :orgLocationId OR (subsah.hierarchyId.projectClientsId.id IN( select pm.projectClientsId.id  from ProjectMembers pm where pm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"' and pm.projectClientsId.orgId.id = :orgId ) )) ";
		String query = "Select Count(*) from  SubmissionApproverHierarchy sah where sah.submissionId.masterCandidateId.id = :masterCandidateId   and ( sah.hierarchyId.level IN("
				+ subQueryForMaxLevel
				+ ")) and  sah.submissionStatus = :submissionStatus  "
				+locationQuery
				+ " GROUP BY CONCAT(sah.submissionId.projects.id,sah.frequencyStartDate,sah.frequencyEndDate)";
		List<Long> tslist = session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setLong("locationsId", orgLocationId).setString("submissionStatus", submissionStatus).list();
		Integer count = 0;
		if (tslist != null && !tslist.isEmpty()) {
			count = tslist.size();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<SubmissionApproverHierarchy> getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginWithSearchValue(Long userId, Long orgLocationId, String submissionStatus, String searchValue, int start, int length,String isGlobalUser)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<SubmissionApproverHierarchy> response = new ResponseList<>();
		StringBuilder searchCountQuery = new StringBuilder();
			 
          String globalUserQuery = "";
          if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
        	  globalUserQuery =   " sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId) ";
          }else {
        	  globalUserQuery = " (sah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId OR (:locationsId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.status != 0) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)))";
          }
		if (StringUtils.isNotEmpty(searchValue) && searchValue != null) {
			searchCountQuery.append(" and (sah.submissionId.projectMembers.projectMemberCandidteId.firstName like '%"
					+ searchValue
					+ "%' ").append("OR sah.submissionId.projectMembers.projectMemberCandidteId.lastName like '%"
							+ searchValue
		        	+ "%' ").append("OR concat(sah.submissionId.projectMembers.projectMemberCandidteId.firstName,' ',sah.submissionId.projectMembers.projectMemberCandidteId.lastName) like '%"
					+ searchValue.trim().replaceAll(" +", " ")
					+ "%' ")
			       .append("OR sah.submissionId.projectMembers.projectMemberCandidteId.email like '%"
									+ searchValue
									+ "%' ").append("OR sah.submissionId.projectMembers.projects.projectName like '%"
											+ searchValue
											+ "%' ").append("OR sah.submissionId.projectMembers.projects.invoiceFrequency like '%"
													+ searchValue + "%')");
		}
		StringBuilder searchQuery = new StringBuilder();
		if (StringUtils.isNotEmpty(searchValue) && searchValue != null) {
			searchQuery.append(" and( sah.submissionId.projectMembers.projectMemberCandidteId.firstName like '%"
					+ searchValue
					+ "%' ").append("OR sah.submissionId.projectMembers.projectMemberCandidteId.lastName like '%"
					+ searchValue
					+ "%' ").append("OR concat(sah.submissionId.projectMembers.projectMemberCandidteId.firstName,' ',sah.submissionId.projectMembers.projectMemberCandidteId.lastName) like '%"
					+ searchValue.trim().replaceAll(" +", " ")		
					+ "%' ").append("OR sah.submissionId.projectMembers.projectMemberCandidteId.email like '%"
									+ searchValue
									+ "%' ").append("OR  sah.submissionId.projectMembers.projects.projectName like '%"
											+ searchValue
											+ "%' ").append("OR  sah.submissionId.projectMembers.projects.invoiceFrequency like '%"
													+ searchValue + "%')");
		}
		String fillingOptionQuery = " and (sah.submissionApprovalStatus <> '"
				+ Submission_ApprovalStatus.NEW + "' OR (sah.submissionApprovalStatus = '"
				+ Submission_ApprovalStatus.NEW
				+ "' and sah.hierarchyId.projectClientsId.id IN(select pc.id from ProjectClients pc where pc.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = sah.submissionId.projectMembers.projects.id and subPm.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id and subPm.projectClientsId.orgLocationsId.org.orgType <> '"
				+ Constants.GUEST
				+ "') and pc.projectId.id = sah.submissionId.projectMembers.projects.id)))";
		
		String query = "from  SubmissionApproverHierarchy sah "
				+ " where  "+globalUserQuery+"  "
				+ fillingOptionQuery + " and ( "
				/*
				 * logged in user as approver in project_approver_hierarchy
				 * (Either Admins/project_managers/team leads ...etc)
				 */
			     + "  (" + "    sah.hierarchyId.approverUserId.id = :userId  " 
				 + "            and  (sah.hierarchyId.projectId.projectApprovalType  ='"+Constants.PARALLEL+"'"
				 +"                   OR (sah.hierarchyId.projectId.projectApprovalType  ='"+Constants.SEQUENTIAL+"' and "
				 + "                          (sah.hierarchyId.level = '1' and sah.hierarchyId.projectClientsId.level IN(select Max(subsah.hierarchyId.projectClientsId.level) from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.submissionId.projectMembers.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id and subsah.hierarchyId.projectClientsId.orgLocationsId.org.orgType <> 'GUEST' and subsah.hierarchyId.projectClientsId.status != 0))"
				 + "                             OR  (sah.hierarchyId.level= '1' and (sah.hierarchyId.projectClientsId.level+1) IN ( select subsah.hierarchyId.projectClientsId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.submissionId.masterCandidateId.id = subsah.submissionId.masterCandidateId.id and subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate))"
				 + "                             OR  (sah.hierarchyId.level= '2' and '1' IN (select subsah.hierarchyId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and sah.submissionId.masterCandidateId.id = subsah.submissionId.masterCandidateId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate)) "
				 + "                     )          "
				 + "             ))"
				/*
				 * logged in user as admin but not in
				 * project_approver_hierarchy(hint:logged in user is admin but
				 * not assigned to project as approver)
				 */
				 + " OR (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :orgLocationId ) and (sah.hierarchyId.projectId.projectApprovalType = '"+Constants.PARALLEL+"' OR (sah.hierarchyId.projectId.projectApprovalType = '"+Constants.SEQUENTIAL+"' and ((1 IN(select Max(pah.level) from ProjectApproverHierarchy pah where  pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id)) OR (1 NOT IN(select Max(pah.level) from ProjectApproverHierarchy pah where  pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id) and  '1' IN (select subsah.hierarchyId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate))))))"
				 +" OR  ((sah.hierarchyId.level = '1' and  sah.hierarchyId.status = '1' and sah.submissionId.projectMembers.projects.isReportingManagerAsApprover = 'Y'  and sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser.id)))"
				 + "   ) "
				+ " and sah.status != '0' and  sah.submissionStatus = :submissionStatus and sah.submissionId.status = '1'";
		String queryCount = " Select Count (*) from  SubmissionApproverHierarchy sah  where  "+globalUserQuery+"  "
				+ fillingOptionQuery + " and ( "
				/*
				 * logged in user as approver in project_approver_hierarchy
				 * (Either Admins/project_managers/team leads ...etc)
				 */
				 + "  (" + "    sah.hierarchyId.approverUserId.id = :userId  " 
				 + "            and  (sah.hierarchyId.projectId.projectApprovalType  ='"+Constants.PARALLEL+"'"
				 +"                   OR (sah.hierarchyId.projectId.projectApprovalType  ='"+Constants.SEQUENTIAL+"' and "
				 + "                          (sah.hierarchyId.level = '1' and sah.hierarchyId.projectClientsId.level IN(select Max(subsah.hierarchyId.projectClientsId.level) from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.submissionId.projectMembers.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id and subsah.hierarchyId.projectClientsId.orgLocationsId.org.orgType <> 'GUEST' and subsah.hierarchyId.projectClientsId.status != 0))"
				 + "                             OR  (sah.hierarchyId.level= '1' and (sah.hierarchyId.projectClientsId.level+1) IN (select subsah.hierarchyId.projectClientsId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.submissionId.masterCandidateId.id = subsah.submissionId.masterCandidateId.id and subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate))"
				 + "                             OR  (sah.hierarchyId.level= '2' and '1' IN (select subsah.hierarchyId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and sah.submissionId.masterCandidateId.id = subsah.submissionId.masterCandidateId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate)) "
				 + "                     )          "
				 + "             ))"
 
				/*
				 * logged in user as admin but not in
				 * project_approver_hierarchy(hint:logged in user is admin but
				 * not assigned to project as approver)
				 */
				 + " OR (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :orgLocationId ) and (sah.hierarchyId.projectId.projectApprovalType = '"+Constants.PARALLEL+"' OR (sah.hierarchyId.projectId.projectApprovalType = '"+Constants.SEQUENTIAL+"' and ((1 IN(select Max(pah.level) from ProjectApproverHierarchy pah where  pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id)) OR (1 NOT IN(select Max(pah.level) from ProjectApproverHierarchy pah where  pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id) and  '1' IN (select subsah.hierarchyId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate))))))"
				 +"  OR  ((sah.hierarchyId.level = '1' and  sah.hierarchyId.status = '1' and sah.submissionId.projectMembers.projects.isReportingManagerAsApprover = 'Y'  and sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser.id)))"
				 + "   ) "
				+ " and sah.status != '0' and sah.submissionStatus = :submissionStatus and sah.submissionId.status = '1'";
		String soringQuery = "GROUP BY CONCAT(sah.submissionId.projectMembers.id,sah.frequencyStartDate,sah.frequencyEndDate)order by sah.submissionId.projectMembers.id ASC)";
		String sortingCount = " GROUP BY CONCAT(sah.submissionId.projectMembers.id,sah.frequencyStartDate,sah.frequencyEndDate))";
		Query query1 = session.createQuery(query + " " + searchQuery.toString() + " "
				+ soringQuery).setFirstResult(start).setMaxResults(length);
		// Query query1 = session.createQuery(query+" "+searchQuery.toString());
		List<SubmissionApproverHierarchy> tslist = query1.setLong("userId", userId).setLong("orgLocationId", orgLocationId).setLong("locationsId", orgLocationId).setString("submissionStatus", submissionStatus).list();
		response.setData(tslist);
		Query countQ = session.createQuery(queryCount + " " + searchCountQuery.toString() + " "
				+ sortingCount);
		// Query countQ = session.createQuery(queryCount+"
		// "+searchCountQuery.toString());
		countQ.setLong("userId", userId).setLong("orgLocationId", orgLocationId).setLong("locationsId", orgLocationId).setString("submissionStatus", submissionStatus);
		List<Long> countList = null;
		if (countQ != null) {
			countList = countQ.list();
		}
		int recordsTotal = 0;
		if (countList != null && !countList.isEmpty()) {
			recordsTotal = countList.size();
		}
		response.setRecordsTotal(recordsTotal);
		int recordsFiltered = 0;
		if (tslist != null && !tslist.isEmpty()) {
			recordsFiltered = tslist.size();
		}
		if (searchQuery.toString() != null && !searchQuery.toString().isEmpty()) {
			response.setRecordsFiltered(recordsFiltered);
		}
		else {
			response.setRecordsFiltered(recordsTotal);
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId, Long orgLocationId, String submissionStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projectMemberCandidteId can left join fetch pm.projects left join fetch pm.employee emp left join fetch ts.projects project left join fetch ts.masterCandidateId mc "
				+ " where  pclient.orgLocationsId.id = :orgLocationId and " + " ( "
				/*
				 * logged in user as approver in project_approver_hierarchy
				 * (Either Admins/project_managers/team leads ...etc)
				 */
				 + "  (" + "   hierarchy.approverUserId.id = :userId  " + "   )  "
				 
				/*+ "  (" + "    hierarchy.approverUserId.id = :userId and " + "      ("
				+ "        ( hierarchy.level = '2' and emp.user.id NOT IN(select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectClientsId.orgLocationsId.id = pclient.orgLocationsId.id and subsah.submissionId.id = sah.submissionId.id and subsah.hierarchyId.level = '2')) "
				+ "        OR (hierarchy.level = '1' and emp.user.id NOT IN(select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectClientsId.orgLocationsId.id = pclient.orgLocationsId.id and subsah.submissionId.id = sah.submissionId.id) ) "
				+ "     ) " + "   ) "*/
				/*
				 * logged in user as admin but not in
				 * project_approver_hierarchy(hint:logged in user is admin but
				 * not assigned to project as approver)
				 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectClientsId.orgLocationsId.id = pclient.orgLocationsId.id and subsah.submissionId.id = sah.submissionId.id ) and "
				+ Constants.loginUserAsAdminWithMaxHeirarchyLevelForTs + ") " + "   ) "
				+ " and  sah.submissionStatus = :submissionStatus and sah.submissionId.status = '1'  GROUP BY CONCAT(pm.id,sah.frequencyStartDate,sah.frequencyEndDate)order by pm.projects.id ASC)";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setString("submissionStatus", submissionStatus).list();
		return tslist;
	}
	
	/*@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(Long approverUserId,Boolean isAdmin,Long locationsId,Long orgId,String submissionStatus,String isGlobalUser)
	{
			      Session session = this.sessionFactory.getCurrentSession();
			     For Parallel Approver Mechanism
				   StringBuilder parallelApproverCheckQuery= new StringBuilder();  
					                      parallelApproverCheckQuery
							              //isApprover
							              .append( "( PAH.APPROVER_USER_ID = '"+approverUserId+"'  AND PAH.STATUS != '0' AND PAH.PROJECT_CLIENTS_ID = PC.ID)   " )
							              .append("       OR   ")
							               //isAdmin
							              .append(" (").append(isAdmin).append(" AND PAH.LEVEL IN(SELECT MAX(PAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID) AND PAH.PROJECT_CLIENTS_ID = PC.ID)")
							    		  .append("       OR   ")   
							    		  //isReportingManager
							    		  .append(" ( PAH.LEVEL = '1' AND PROJ.IS_REPORTING_MANAGER_AS_APPROVER = 'Y'  AND UR.USER_ID = U.ID AND UR.REPORTING_MANAGER_ID = '"+approverUserId+"' AND PAH.PROJECT_CLIENTS_ID = PC.ID)");
               
	             //checking with level2 manager approver in same org                          
	         	  StringBuilder sequentiaLCheckForLevel2Approval= new StringBuilder();
	         	                           sequentiaLCheckForLevel2Approval
	         	                           .append("1 IN ( ")
	         	                           .append("    SELECT SUBPAH.LEVEL FROM SUBMISSION_APPROVER_HIERARCHY SUBSAH ")
	         	                           .append("     INNER JOIN (SELECT ID,LEVEL FROM PROJECT_APPROVER_HIERARCHY) AS SUBPAH ON SUBPAH.ID = SUBSAH.HIERARCHY_ID ")
	         	                           .append("      WHERE ")
	         	                            //checking with mastercandidateId,projectClientId(i.e;locatioonId),frequencystartDate and frequencyEndDate
	         	                           .append("     SUBSAH.PROJECT_CLIENT_ID = SAH.PROJECT_CLIENT_ID ")
	         	                           .append("     AND SUBSAH.SUBMISSION_APPROVALSTATUS = '"+Submission_ApprovalStatus.APPROVED+"' ")
	         	                           .append("     AND SUBSAH.MASTER_CANDIDATE_ID = SAH.MASTER_CANDIDATE_ID ")
	         	                           .append("     AND SUBSAH.FREQUENCY_START_DATE = SAH.FREQUENCY_START_DATE ")
	         	                           .append("     AND SUBSAH.FREQUENCY_END_DATE = SAH.FREQUENCY_END_DATE ")
	         	                           .append(" )");
	         	                           
	         	                           
                  //checking with low-level(vendor),highest level manager approval                      
         	     StringBuilder sequentiaLCheckForVendorApproval= new StringBuilder();                   
         		                           sequentiaLCheckForVendorApproval
         		                           .append(" ( CASE ")
         		                           .append("   WHEN PC.LEVEL < (")
         		                           .append( "             SELECT MAX(SUBPC.LEVEL) FROM PROJECT_MEMBERS SUBPM ")
         		                           .append( "              INNER JOIN(SELECT ID,ORG_ID,LEVEL,PROJECT_ID,STATUS FROM PROJECT_CLIENTS) AS SUBPC ON SUBPC.ID = SUBPM.PROJECT_CLIENTS_ID ")
         		   		   		           .append( "              INNER JOIN (SELECT ID,ORG_TYPE FROM ORG) AS OG ON OG.ID = SUBPC.ORG_ID     ")	   
         		   		   		           .append("               WHERE SUBPM.MASTER_CANDIDATE_ID = SAH.MASTER_CANDIDATE_ID AND SUBPC.PROJECT_ID = SAH.PROJECT_ID AND OG.ORG_TYPE != 'GUEST' AND SUBPC.STATUS != '0'")
         		   		   		           .append( "            ) THEN  ")
         		   		   		           .append( "            (    ")
         		   		   		           .append( "              (PC.LEVEL+1) IN (  ")
         		   		   		           .append("                               SELECT SUBPC.LEVEL FROM SUBMISSION_APPROVER_HIERARCHY  SUBSAH " + 
         		   		   		        		"                                  INNER JOIN (SELECT ID,MASTER_CANDIDATE_ID FROM PROJECT_MEMBERS) AS SUBPM ON SUBPM.ID = SUBSAH.PROJECT_MEMBER_ID " +
         		   		   		           		"                                  INNER JOIN (SELECT ID,LEVEL,PROJECT_CLIENTS_ID FROM PROJECT_APPROVER_HIERARCHY) AS SUBPAH ON SUBPAH.ID = SUBSAH.HIERARCHY_ID " + 
         		   		   		           		"                                  INNER JOIN (SELECT ID,LEVEL FROM PROJECT_CLIENTS) AS SUBPC ON SUBPC.ID = SUBSAH.PROJECT_CLIENT_ID " + 
         		   		   		           		"                                  WHERE SUBSAH.SUBMISSION_APPROVALSTATUS =  '"+Submission_ApprovalStatus.APPROVED+"' AND " + 
         		   		   		           		                                   //checking with subsequent vendor 
         		   		   		           		"                                  SUBPM.MASTER_CANDIDATE_ID = SAH.MASTER_CANDIDATE_ID AND SUBSAH.FREQUENCY_START_DATE = SAH.FREQUENCY_START_DATE  AND SUBSAH.FREQUENCY_END_DATE = SAH.FREQUENCY_END_DATE " + 
         		   		   		                                                   //checking with subsequent vendor's highest level of approval mechanism
         		   		   		        	    "                                  AND SUBPAH.LEVEL IN (SELECT MAX(INNERPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY INNERPAH WHERE INNERPAH.PROJECT_CLIENTS_ID = SUBPAH.PROJECT_CLIENTS_ID)")
         		   		   		          .append("                              )")
         		   		               	   .append( "            ) ELSE  true ")
         		   		   		           .append( "   END )      ");	         
	         	  
	         	  StringBuilder sequentiaLCheckForNonNewStatus= new StringBuilder(); 
	         	                          sequentiaLCheckForNonNewStatus
	         	                          .append("(")
	         	                           //isApprover
	         	                          .append("    (")
	         	                          .append("        PAH.APPROVER_USER_ID = '"+approverUserId+"'  AND PAH.STATUS != '0' AND PAH.PROJECT_CLIENTS_ID = PC.ID ")
	         	                          .append("        AND ")
	         	                         .append("       ( CASE ")
	         	                                           //checking with low-level(vendor),highest level manager approval
	         	 				    	 .append( "        WHEN (PAH.LEVEL = '1')  " )
	         	 				    	 .append("         THEN ( ").append(sequentiaLCheckForVendorApproval).append(")")
	         	 				    	                   //checking with level2 manager approver in same org
	         	 				    	 .append( "        WHEN (PAH.LEVEL = '2') " )
	         	 				    	 .append("         THEN ( ").append(sequentiaLCheckForLevel2Approval).append(")")
	         	 				    	 .append("        ELSE  '' ")
	         	 				    	 .append("        END ")
	         	 				    	 .append("        )")
	         	 				    	 .append("       )")
	         	 				    	 .append("  OR     ")
	         	 				    	  //isAdmin
	         	 				    	 .append("      (")
	         	                         .append("").append(isAdmin).append("")
	         	                         .append("    AND    ")
	         	                         .append("         ( CASE ")
	         	 				    	 .append( "          WHEN (1 IN(SELECT MAX(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID AND SUBPAH.status != '0') AND PAH.PROJECT_CLIENTS_ID = PC.ID)  " )
	         	 				    	 .append("           THEN ( ").append(sequentiaLCheckForVendorApproval).append(")")
	         	 				    	 .append( "          WHEN (2 IN(SELECT MAX(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID AND SUBPAH.status != '0') AND PAH.PROJECT_CLIENTS_ID = PC.ID) " )
	         	 				    	 .append("           THEN ( ").append(sequentiaLCheckForLevel2Approval).append(")")
	         	 				    	 .append("           ELSE  '' ")
	         	 				    	 .append("           END ")
	         	 				    	 .append("         )")
	         	 				    	 .append("      )")
	         	 				    	 .append("  OR     ")
	         	 				    	 //isReportingManager
	         	 				    	 .append("      (")
	         	 				    	 .append(" ( PAH.LEVEL = '1' AND PROJ.IS_REPORTING_MANAGER_AS_APPROVER = 'Y' AND UR.USER_ID = U.ID AND UR.REPORTING_MANAGER_ID = '"+approverUserId+"' AND PAH.PROJECT_CLIENTS_ID = PC.ID)")
	         	 				    	 .append("      )")
	         	 				    	 .append(")");
	         	                          
                                     
	         	                          
			 
		       StringBuilder query1= new StringBuilder(); 
		       query1.append(" SELECT Q1.* FROM (SELECT SAH.ID FROM USER_ROLE UR,SUBMISSION_APPROVER_HIERARCHY SAH ");
		      
		       Getting Projects
		       query1.append("  INNER JOIN (SELECT ID,INVOICE_FREQUENCY,PROJECT_APPROVAL_TYPE,PROJECT_NAME,IS_REPORTING_MANAGER_AS_APPROVER, WEEK_START_DATE,IS_PROJECT_FOR_TIMESHEETS FROM PROJECTS ) AS PROJ ON PROJ.ID = SAH.PROJECT_ID");
		       
		       //To get current  orglocation details 
		        if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
			         query1.append(" INNER JOIN (SELECT ID,PROJECT_ID,ORG_LOCATIONS_ID,ORG_ID,ORG_RELATION_ID,LEVEL FROM PROJECT_CLIENTS) AS PC ON PC.ID = SAH.PROJECT_CLIENT_ID AND (PC.ORG_LOCATIONS_ID = '"+locationsId+"' OR  (PC.ORG_LOCATIONS_ID != '"+locationsId+"' AND PC.ORG_ID = '"+orgId+"'))");
		       }else {
		    	     query1.append(" INNER JOIN (SELECT ID,PROJECT_ID,ORG_LOCATIONS_ID,ORG_ID,ORG_RELATION_ID,LEVEL FROM PROJECT_CLIENTS) AS PC ON PC.ID = SAH.PROJECT_CLIENT_ID AND PC.ORG_LOCATIONS_ID = '"+locationsId+"'");
		        } 
			   
		        Getting Approver Hierarchy
		        query1.append("  INNER JOIN (SELECT ID,APPROVER_USER_ID,PROJECT_CLIENTS_ID,STATUS,LEVEL FROM PROJECT_APPROVER_HIERARCHY) AS PAH ON PAH.ID = SAH.HIERARCHY_ID ");
		      
		        Getting consultant User
		        query1.append("  LEFT JOIN (SELECT ID,FILE_URL FROM USER) AS U ON U.ID = SAH.CONSULTANT_USER_ID ");
				
		        query1.append(" WHERE ");
			    query1.append( " SAH.STATUS != '0' AND SAH.SUBMISSION_STATUS = '" + submissionStatus+"'  ");
			    if(StringUtils.isNotEmpty(submissionStatus) && !submissionStatus.equalsIgnoreCase("DRAFT")) {
			    	//For Non Draft Count
			    	query1.append(" AND ")
				     .append("   ( CASE ")
			    	 .append( "        WHEN PROJ.PROJECT_APPROVAL_TYPE ='"+Constants.PARALLEL+"' THEN " )
			    	 .append("         ( ").append(parallelApproverCheckQuery).append(")")
			    	 .append( "        WHEN PROJ.PROJECT_APPROVAL_TYPE ='"+Constants.SEQUENTIAL+"' THEN " )
			    	 .append("         ( ").append(sequentiaLCheckForNonNewStatus).append(")")
			    	 .append("        ELSE  '' ")
			    	 .append("      END ")
			    	 .append("    )");
			    }else {
			    	//For Draft Count
			    	StringBuilder sequentialCheckForNewStatus= new StringBuilder(); 
				                    sequentialCheckForNewStatus
				                   .append("( PAH.LEVEL = '1' AND PAH.APPROVER_USER_ID = '"+approverUserId+"'  AND PAH.STATUS != '0' AND PAH.PROJECT_CLIENTS_ID = PC.ID ) ")
				                   .append("  OR     ")
				                   .append(" (").append(isAdmin).append(" AND 1 IN(SELECT MAX(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID) AND PAH.PROJECT_CLIENTS_ID = PC.ID)")
				                   .append("  OR     ")
				                   .append(" ( PAH.LEVEL = '1' AND PROJ.IS_REPORTING_MANAGER_AS_APPROVER = 'Y' AND   UR.USER_ID = U.ID AND UR.REPORTING_MANAGER_ID = '"+approverUserId+"' AND PAH.PROJECT_CLIENTS_ID = PC.ID)")
				                   .append("");
                    
			    	query1.append(" AND ")
				     .append("   ( CASE ")
			    	 .append( "        WHEN PROJ.PROJECT_APPROVAL_TYPE ='"+Constants.PARALLEL+"' THEN " )
			    	 .append("         ( ").append(parallelApproverCheckQuery).append(")")
			    	 .append( "        WHEN PROJ.PROJECT_APPROVAL_TYPE ='"+Constants.SEQUENTIAL+"' THEN " )
			    	 .append("         ( ").append(sequentiaLCheckForNonNewStatus).append(")")
			    	 .append("        ELSE  '' ")
			    	 .append("      END ")
			    	 .append("    )");
			    }
			    query1.append("  GROUP BY CONCAT(SAH.PROJECT_MEMBER_ID,SAH.FREQUENCY_START_DATE,SAH.FREQUENCY_END_DATE))Q1");
			    String  countQuery = "SELECT COUNT(*) AS TOTAL FROM (" + query1.toString() +")Q2";
			     //System.out.println(countQuery);
			    List<BigInteger> countlist =  session.createSQLQuery(countQuery).list();
			    Integer count = 0;
			    if(countlist != null && !countlist.isEmpty()) {
			    	count = countlist.iterator().next().intValue();
			    }
				
			  return count;
	}*/
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(Long userId,Boolean isAdmin,Long orgLocationId,Long orgId,String submissionStatus,String isGlobalUser)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String globalUserQuery = " ";
		if (StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
			globalUserQuery = " sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationId) ";
		} else {
			globalUserQuery = " (sah.hierarchyId.projectClientsId.orgLocationsId.id = :orgLocationId OR (:orgLocationId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.status != 0) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"
					+ Constants.OFFSHORE_C2C
					+ "') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)))";
		}

		String fillingOptionQuery = " and (sah.submissionApprovalStatus <> '" + Submission_ApprovalStatus.NEW
				+ "' OR (sah.submissionApprovalStatus = '" + Submission_ApprovalStatus.NEW
				+ "' and sah.hierarchyId.projectClientsId.id IN(select pc.id from ProjectClients pc where pc.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = sah.submissionId.projectMembers.projects.id and subPm.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id and subPm.projectClientsId.orgLocationsId.org.orgType <> '"
				+ Constants.GUEST + "') and pc.projectId.id = sah.submissionId.projectMembers.projects.id)))";

		String query = " Select Count (*) from SubmissionApproverHierarchy sah where " + globalUserQuery + " "
				+ fillingOptionQuery + " and " + " ( " + " (" + " sah.hierarchyId.approverUserId.id = :userId "
				+ " and (sah.hierarchyId.projectId.projectApprovalType ='" + Constants.PARALLEL + "'"
				+ " OR (sah.hierarchyId.projectId.projectApprovalType ='" + Constants.SEQUENTIAL + "' and "
				+ " (sah.hierarchyId.level = '1' and sah.hierarchyId.projectClientsId.level IN(select Max(subsah.hierarchyId.projectClientsId.level) from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.submissionId.projectMembers.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id and subsah.hierarchyId.projectClientsId.orgLocationsId.org.orgType <> 'GUEST' and subsah.hierarchyId.projectClientsId.status != 0))"
				+ " OR (sah.hierarchyId.level= '1' and (sah.hierarchyId.projectClientsId.level+1) IN (select subsah.hierarchyId.projectClientsId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"
				+ Submission_ApprovalStatus.APPROVED
				+ "' and sah.submissionId.masterCandidateId.id = subsah.submissionId.masterCandidateId.id and subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.frequencyStartDate = sah.frequencyStartDate and subsah.frequencyEndDate = sah.frequencyEndDate))"
				+ " OR (sah.hierarchyId.level= '2' and '1' IN (select subsah.hierarchyId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"
				+ Submission_ApprovalStatus.APPROVED
				+ "' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and subsah.frequencyStartDate = sah.frequencyStartDate and sah.submissionId.masterCandidateId.id = subsah.submissionId.masterCandidateId.id and subsah.frequencyEndDate = sah.frequencyEndDate)) "
				+ " ) " + " ))"
				+ " OR (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :orgLocationId ) and (sah.hierarchyId.projectId.projectApprovalType = '"
				+ Constants.PARALLEL + "' OR (sah.hierarchyId.projectId.projectApprovalType = '" + Constants.SEQUENTIAL
				+ "' and ((1 IN(select Max(pah.level) from ProjectApproverHierarchy pah where pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id)) OR (1 NOT IN(select Max(pah.level) from ProjectApproverHierarchy pah where pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id) and '1' IN (select subsah.hierarchyId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"
				+ Submission_ApprovalStatus.APPROVED
				+ "' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and subsah.frequencyStartDate = sah.frequencyStartDate and subsah.frequencyEndDate = sah.frequencyEndDate))))))"
				+ " OR ((sah.hierarchyId.level = '1' and sah.hierarchyId.status = '1' and sah.submissionId.projectMembers.projects.isReportingManagerAsApprover = 'Y' and sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser.id)))"
				+ " ) "
				+ " and sah.status != '0' and sah.submissionStatus = :submissionStatus and sah.submissionId.status = '1' GROUP BY CONCAT(sah.submissionId.projectMembers.id,sah.frequencyStartDate,sah.frequencyEndDate)order by sah.submissionId.projectMembers.projects.id ASC)";
		List<SubmissionApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId)
				.setLong("orgLocationId", orgLocationId).setString("submissionStatus", submissionStatus).list();
		Integer count = 0;
		if (tslist != null && !tslist.isEmpty()) {
			count = tslist.size();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyBySubmissionId(Long submissionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where sah.submissionId.id = :submissionId");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("submissionId", submissionId).list();
		return submissionApproverHierarchyList;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByApproverUserId(Long approverUserId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId approver left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where approver.id = :approverUserId");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("approverUserId", approverUserId).list();
		return submissionApproverHierarchyList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveAllSubmissionApproverHierarchy(List<SubmissionApproverHierarchy> submissionApproverHierarchyList) throws Exception
	{
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				addSubmissionApproverHierarchy(sah);
			}
		}
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectMember(Long projectMemberId, Date frequencyStartDate, Date frequencyEndDate, String approvalStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (frequencyStartDate != null) {
			sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (frequencyEndDate != null) {
			sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		}
		Query submissionApproverHierarchyListQuery = session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pm.id = :projectMemberId and  sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate and sah.submissionApprovalStatus = :approvalStatus");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setString("approvalStatus", approvalStatus).list();
		SubmissionApproverHierarchy sah = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(Long projectId, Long masterCandidateId, Date frequencyStartDate, Date frequencyEndDate, String approvalStatus)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (frequencyStartDate != null) {
			sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (frequencyEndDate != null) {
			sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		}
		Query submissionApproverHierarchyListQuery = session.createQuery("from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm  left join fetch ts.projects pj left join fetch ts.masterCandidateId mc left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user where pj.id = :projectId and mc.id = :masterCandidateId and sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate and sah.submissionApprovalStatus = :approvalStatus");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setString("approvalStatus", approvalStatus).list();
		SubmissionApproverHierarchy sah = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			sah = submissionApproverHierarchyList.iterator().next();
		}
		return sah;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId, Long locationsId, Long projectMemberId, Date startDate, Date endDate,String isGlobalUser) throws Exception
	{
		ProjectMembers projectMembers = projectMembersDao.getProjectMemberById(projectMemberId);
		Projects projects = projectsDao.getProjectById(projectMembers.getProjects().getId());
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String globalUserQuery = "";
	    if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
	    	globalUserQuery = " and pclient.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)";
	    }else {
	    	 globalUserQuery = " and (sah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId OR (:locationsId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.status != 0) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)))";
	    }
		String fillingOptionQuery = " and (sah.submissionApprovalStatus <> '"
				+ Submission_ApprovalStatus.NEW + "' OR (sah.submissionApprovalStatus = '"
				+ Submission_ApprovalStatus.NEW
				+ "' and sah.hierarchyId.projectClientsId.id IN(select pc.id from ProjectClients pc where pc.level IN(select Max(subPm.projectClientsId.level) from ProjectMembers subPm where subPm.projects.id = sah.submissionId.projectMembers.projects.id and subPm.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id and subPm.projectClientsId.orgLocationsId.org.orgType <> '"
				+ Constants.GUEST
				+ "') and pc.projectId.id = sah.submissionId.projectMembers.projects.id)))";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.masterCandidateId mc  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where   project.id = :projectId and mc.id = :masterCandidateId "
				+ " and (sah.frequencyStartDate = :startDate OR sah.frequencyEndDate = :endDate OR sah.frequencyStartDate BETWEEN :startDate and :endDate OR sah.frequencyEndDate BETWEEN :startDate and :endDate) and sah.submissionId.status = '1'"
				+ globalUserQuery
				+ fillingOptionQuery + "  and ( "
				/*
				 * logged in user as approver in project_approver_hierarchy
				 * (Either Admins/project_managers/team leads ...etc)
				 */
				+ "  hierarchy.approverUserId.id = :userId "
				
				+ "       OR  (sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser.id) and  sah.hierarchyId.level IN(select MIN(pah.level) from ProjectApproverHierarchy pah where pah.projectClientsId.id = pclient.id and pah.status != '0'))"
				+ "       OR  (sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.resourceManager.id from UserRole userRole where userRole.user.id = sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser.id) and  sah.hierarchyId.level IN(select MIN(pah.level) from ProjectApproverHierarchy pah where pah.projectClientsId.id = pclient.id and pah.status != '0'))"
				/*
				 * logged in user as admin but not in
				 * project_approver_hierarchy(hint:logged in user is admin but
				 * not assigned to project as approver)
				 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.status = '1' and subsah.submissionId.projectMembers.id = pm.id  and (sah.frequencyStartDate = :startDate OR sah.frequencyEndDate = :endDate OR sah.frequencyStartDate BETWEEN :startDate and :endDate OR sah.frequencyEndDate BETWEEN :startDate and :endDate)) and "
				+ "(:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = :locationsId and userRole.userState = 'ACTIVE') and  sah.hierarchyId.level IN(select MAX(pah.level) from ProjectApproverHierarchy pah where pah.projectClientsId.id = pclient.id and pah.status != '0'))"
				+ ")"
				+ " ) ";
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString()).setLong("locationsId", locationsId).setLong("userId", approverUserId).setLong("masterCandidateId", projectMembers.getMasterCandidateId().getId()).setLong("projectId", projectMembers.getProjects().getId()).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		SubmissionApproverHierarchy submissionApproverHierarchy = null;
		LinkedHashMap<Date, Submission_ApprovalStatus> weekApprovalStatusMap = new LinkedHashMap<>();
		LinkedHashMap<Date, Float> weekBillableHours = new LinkedHashMap<>();
		LinkedHashMap<Date, Float> weekNonBillableHours = new LinkedHashMap<>();
		LinkedHashMap<Date, Float> weekTotalHoursMap = new LinkedHashMap<>();
		Date monthStartDate = null;
		Date monthEndDate = null;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			int count = 0;
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (sah.getSubmissionId().getEndDate().before(startDate))
					continue;
				if (count == 0) {
					submissionApproverHierarchy = sah;
				}
				Date actualStartDate = null;
				Date actualEndDate = null;
				if (sah.getSubmissionId().getStartDate().before(startDate)
						|| sah.getSubmissionId().getStartDate().equals(startDate)) {
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String startStr = sdf.format(startDate);
					actualStartDate = sdf.parse(startStr);
				}
				else {
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String startStr = sdf.format(sah.getSubmissionId().getStartDate());
					actualStartDate = sdf.parse(startStr);
				}
				monthStartDate = CalendarUtil.getMonthStartDateByGivenDate(actualStartDate, Constants.CURRENT_MONTH).get("STARTDATE");
				monthEndDate = CalendarUtil.getMonthEndDateByGivenDate(actualStartDate, Constants.CURRENT_MONTH).get("ENDDATE");
				float weekTotalHours = 0f;
				float weekBillableTotalHours = 0f;
				float weekNonBillableTotalHours = 0f;
				SimpleDateFormat sf = new SimpleDateFormat("E");
				if (!sf.format(actualStartDate).equalsIgnoreCase("Mon")
						&& !monthStartDate.equals(actualStartDate)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					cal.setLenient(false);
					cal.setTime(actualStartDate);
					actualStartDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON", "yyyy-MM-dd").getTime();
					if (weekTotalHoursMap != null && !weekTotalHoursMap.isEmpty()
							&& weekTotalHoursMap.get(actualStartDate) != null
							&& weekBillableHours.get(actualStartDate) != null
							&& weekNonBillableHours.get(actualStartDate) != null) {
						weekTotalHours = weekTotalHoursMap.get(actualStartDate);
						weekBillableTotalHours = weekBillableHours.get(actualStartDate);
						weekNonBillableTotalHours = weekNonBillableHours.get(actualStartDate);
					}
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String startStr = sdf.format(actualStartDate);
					actualStartDate = sdf.parse(startStr);
				}
				if (sah.getSubmissionId().getEndDate().after(endDate)
						|| sah.getSubmissionId().getEndDate().equals(endDate)) {
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String endStr = sdf.format(endDate);
					actualEndDate = sdf.parse(endStr);
				}
				else {
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String endStr = sdf.format(sah.getSubmissionId().getEndDate());
					actualEndDate = sdf.parse(endStr);
				}
				if (!sf.format(actualEndDate).equalsIgnoreCase("Sun")
						&& !monthEndDate.equals(actualEndDate)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					cal.setLenient(false);
					cal.setTime(actualEndDate);
					actualEndDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN", "yyyy-MM-dd").getTime();
					if (weekTotalHoursMap != null && !weekTotalHoursMap.isEmpty()
							&& weekTotalHoursMap.get(actualStartDate) != null
							&& weekBillableHours.get(actualStartDate) != null
							&& weekNonBillableHours.get(actualStartDate) != null) {
						weekTotalHours = weekTotalHoursMap.get(actualStartDate);
						weekBillableTotalHours = weekBillableHours.get(actualStartDate);
						weekNonBillableTotalHours = weekNonBillableHours.get(actualStartDate);
					}
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String endStr = sdf.format(actualEndDate);
					actualEndDate = sdf.parse(endStr);
				}
				List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), actualStartDate, actualEndDate);
				if (tdeList != null && !tdeList.isEmpty()) {
					for (TimeSheetDayEntries tde : tdeList) {
						float totalHours = 0f;
						float billableHours = 0f;
						float nonBillableHours = 0f;
						if (StringUtils.isNotEmpty(tde.getTotalHours())) {
							totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
						}
						if (StringUtils.isNotEmpty(tde.getBillableHours())) {
							billableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getBillableHours().replaceAll(":", ".")), billableHours);
						}
						if (StringUtils.isNotEmpty(tde.getNonBillableHours())) {
							nonBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getNonBillableHours().replaceAll(":", ".")), nonBillableHours);
						}
						weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
						weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, billableHours);
						weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours, nonBillableHours);
					}
				}
				weekApprovalStatusMap.put(actualStartDate, sah.getSubmissionApprovalStatus());
				weekBillableHours.put(actualStartDate, weekBillableTotalHours);
				weekNonBillableHours.put(actualStartDate, weekNonBillableTotalHours);
				weekTotalHoursMap.put(actualStartDate, weekTotalHours);
				count++;
			}
			if(submissionApproverHierarchy == null) {
				submissionApproverHierarchy = new SubmissionApproverHierarchy();
				submissionApproverHierarchy.setTotalHours(0f);
				// DateFormat format = LoginSessionController.DATEFORMAT;
				Map<String, String> tabFrequencyDates = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(projects.getOrgLocationsId().getDateFormat(), startDate), commonUtil.dateStrFromOrgDateFormatter(projects.getOrgLocationsId().getDateFormat(), endDate), projects.getOrgLocationsId().getDateFormat());
				if (tabFrequencyDates != null && !tabFrequencyDates.isEmpty()) {
					tabFrequencyDates.forEach((k, v) -> {
						try {
							weekApprovalStatusMap.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(), k.replaceAll("/", "-")), Submission_ApprovalStatus.NEW);
							weekBillableHours.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(), k.replaceAll("/", "-")), 0f);
							weekNonBillableHours.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(), k.replaceAll("/", "-")), 0f);
							weekTotalHoursMap.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(), k.replaceAll("/", "-")), 0f);
						} catch (Exception e) {
						}
					});
				}
			}
			submissionApproverHierarchy.setWeekApprovalStatusMap(weekApprovalStatusMap);
			submissionApproverHierarchy.setWeekBillableHoursMap(weekBillableHours);
			submissionApproverHierarchy.setWeekNonBillableHoursMap(weekNonBillableHours);
			submissionApproverHierarchy.setWeekTotalHoursMap(weekTotalHoursMap);
		}
		else {
			submissionApproverHierarchy = new SubmissionApproverHierarchy();
			submissionApproverHierarchy.setTotalHours(0f);
			// DateFormat format = LoginSessionController.DATEFORMAT;
			Map<String, String> tabFrequencyDates = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(projects.getOrgLocationsId().getDateFormat(), startDate), commonUtil.dateStrFromOrgDateFormatter(projects.getOrgLocationsId().getDateFormat(), endDate), projects.getOrgLocationsId().getDateFormat());
			if (tabFrequencyDates != null && !tabFrequencyDates.isEmpty()) {
				tabFrequencyDates.forEach((k, v) -> {
					try {
						weekApprovalStatusMap.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(), k.replaceAll("/", "-")), Submission_ApprovalStatus.NEW);
						weekBillableHours.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(), k.replaceAll("/", "-")), 0f);
						weekNonBillableHours.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(), k.replaceAll("/", "-")), 0f);
						weekTotalHoursMap.put(commonUtil.orgLocationDateFormat(projects.getOrgLocationsId().getDateFormat(), k.replaceAll("/", "-")), 0f);
					} catch (Exception e) {
					}
				});
			}
			submissionApproverHierarchy.setWeekApprovalStatusMap(weekApprovalStatusMap);
			submissionApproverHierarchy.setWeekBillableHoursMap(weekBillableHours);
			submissionApproverHierarchy.setWeekNonBillableHoursMap(weekNonBillableHours);
			submissionApproverHierarchy.setWeekTotalHoursMap(weekTotalHoursMap);
		}
		LinkedHashMap<String, Date> previousMonthMap = CalendarUtil.getPreviousMonthStartDateAndEndDateFromGivenStartDateForTsReports(startDate);
		Map<String, Float> previousMonthHours = getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(projectMemberId, locationsId, previousMonthMap.get("STARTDATE"), previousMonthMap.get("ENDDATE"),isGlobalUser);
		submissionApproverHierarchy.setPreviousMonthBillableHours(previousMonthHours.get("BILLABLE_HOURS"));
		submissionApproverHierarchy.setPreviousMonthNonBillableHours(previousMonthHours.get("NONBILLABLE_HOURS"));
		submissionApproverHierarchy.setPreviousMonthTotalHours(previousMonthHours.get("TOTAL_HOURS"));
		Map<String, Float> projectToDateHours = getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(projectMemberId, locationsId, projectMembers.getProjects().getStartDate(), CommonUtil.currentDate(),isGlobalUser);
		submissionApproverHierarchy.setProjectToDateBillableHours(projectToDateHours.get("BILLABLE_HOURS"));
		submissionApproverHierarchy.setProjectToDateNonBillableHours(projectToDateHours.get("NONBILLABLE_HOURS"));
		submissionApproverHierarchy.setProjectToDateTotalHours(projectToDateHours.get("TOTAL_HOURS"));
		if(monthStartDate != null && monthEndDate != null) {
			Map<String, Float> currentMonthMap = getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(projectMemberId, locationsId, monthStartDate, monthEndDate,isGlobalUser);
			if(currentMonthMap != null && !currentMonthMap.isEmpty()) {
				submissionApproverHierarchy.setCurrentMonthBillableHours(currentMonthMap.get("BILLABLE_HOURS"));
				submissionApproverHierarchy.setCurrentMonthNonBillableHours(currentMonthMap.get("NONBILLABLE_HOURS"));
				submissionApproverHierarchy.setCurrentMonthTotalHours(currentMonthMap.get("TOTAL_HOURS"));
			}
		}
		return submissionApproverHierarchy;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Map<String, Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId, Long locationsId, Long projectMemberId, Date startDate, Date endDate) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where  pclient.orgLocationsId.id = :locationsId and pm.id = :projectMemberId and (sah.frequencyStartDate = :startDate OR sah.frequencyEndDate = :endDate OR sah.frequencyStartDate BETWEEN :startDate and :endDate OR sah.frequencyEndDate BETWEEN :startDate and :endDate) and sah.submissionId.status = '1'"
				+ "  and ( "
				/*
				 * logged in user as approver in project_approver_hierarchy
				 * (Either Admins/project_managers/team leads ...etc)
				 */
				+ "  hierarchy.approverUserId.id = :userId "
				/*
				 * logged in user as admin but not in
				 * project_approver_hierarchy(hint:logged in user is admin but
				 * not assigned to project as approver)
				 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from SubmissionApproverHierarchy subsah where subsah.submissionId.status = '1' and subsah.submissionId.projectMembers.id = pm.id  and subsah.frequencyStartDate = :startDate and subsah.frequencyEndDate = :endDate) and "
				+ Constants.loginUserAsAdminWithMaxHeirarchyLevelForTs + ") " + "   ) ";
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString()).setLong("userId", approverUserId).setLong("locationsId", locationsId).setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		LinkedHashMap<String, Float> monthTotals = new LinkedHashMap<>();
		float monthTotalHours = 0f;
		float monthBillableTotalHours = 0f;
		float monthNonBillableTotalHours = 0f;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				Date actualStartDate = null;
				Date actualEndDate = null;
				if (sah.getSubmissionId().getStartDate().before(startDate)
						|| sah.getSubmissionId().getStartDate().equals(startDate)) {
					actualStartDate = startDate;
				}
				else {
					actualStartDate = sah.getSubmissionId().getStartDate();
				}
				if (sah.getSubmissionId().getEndDate().after(endDate)
						|| sah.getSubmissionId().getEndDate().equals(endDate)) {
					actualEndDate = endDate;
				}
				else {
					actualEndDate = sah.getSubmissionId().getEndDate();
				}
				float weekTotalHours = 0f;
				float weekBillableTotalHours = 0f;
				float weekNonBillableTotalHours = 0f;
				List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), actualStartDate, actualEndDate);
				if (tdeList != null && !tdeList.isEmpty()) {
					for (TimeSheetDayEntries tde : tdeList) {
						float totalHours = 0f;
						float billableHours = 0f;
						float nonBillableHours = 0f;
						if (StringUtils.isNotEmpty(tde.getTotalHours())) {
							totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
						}
						if (StringUtils.isNotEmpty(tde.getBillableHours())) {
							billableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getBillableHours().replaceAll(":", ".")), billableHours);
						}
						if (StringUtils.isNotEmpty(tde.getNonBillableHours())) {
							nonBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getNonBillableHours().replaceAll(":", ".")), nonBillableHours);
						}
						weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
						weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, billableHours);
						weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours, nonBillableHours);
					}
				}
				monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
				monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours, weekBillableTotalHours);
				monthNonBillableTotalHours = CommonUtil.getCombinedHours(monthNonBillableTotalHours, weekNonBillableTotalHours);
			}
		}
		monthTotals.put("MONTH_TOTAL_HOURS", monthTotalHours);
		monthTotals.put("MONTH_BILLABLE_HOURS", monthBillableTotalHours);
		monthTotals.put("MONTH_NONBILLABLE_HOURS", monthNonBillableTotalHours);
		return monthTotals;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long approverUserId, Long projectId, Long pmMasterCandidateId, Long locationsId, Date startDate, Date endDate,String isGlobalUser) throws Exception
	{
		// ProjectMembers projectMembers =
		// projectMembersDao.getProjectMemberById(projectMemberId);
		OrgLocations orgLoc = orgLocationsDao.getOrgLocations(locationsId);
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		String globalUserQuery = "";
		if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
			globalUserQuery = "pclient.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)";
		}else {
			globalUserQuery = " (sah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId OR (:locationsId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.status != 0) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)))";
		}
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.masterCandidateId mc  left join fetch ts.projectMembers pm left join fetch ts.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where "+globalUserQuery+" and  project.id = :projectId and mc.id = :masterCandidateId and sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate and sah.submissionId.status = '1'"
				+ "  and ( "
				/*
				 * logged in user as approver in project_approver_hierarchy
				 * (Either Admins/project_managers/team leads ...etc)
				 */
				   + "  (" + "    sah.hierarchyId.approverUserId.id = :userId  " 
					 + "            and  (sah.hierarchyId.projectId.projectApprovalType  ='"+Constants.PARALLEL+"'"
					 +"                   OR (sah.hierarchyId.projectId.projectApprovalType  ='"+Constants.SEQUENTIAL+"' and "
					 + "                          (sah.hierarchyId.level = '1' and sah.hierarchyId.projectClientsId.level IN(select Max(subsah.hierarchyId.projectClientsId.level) from SubmissionApproverHierarchy subsah where subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.submissionId.projectMembers.masterCandidateId.id = sah.submissionId.projectMembers.masterCandidateId.id and subsah.hierarchyId.projectClientsId.orgLocationsId.org.orgType <> 'GUEST' and subsah.hierarchyId.projectClientsId.status != 0))"
					 + "                             OR  (sah.hierarchyId.level= '1' and  (sah.hierarchyId.projectClientsId.level+1) IN (select subsah.hierarchyId.projectClientsId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and sah.submissionId.masterCandidateId.id = subsah.submissionId.masterCandidateId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate))"
					 + "                             OR  (sah.hierarchyId.level= '2' and '1' IN (select subsah.hierarchyId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and sah.submissionId.masterCandidateId.id = subsah.submissionId.masterCandidateId.id and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate)) "
					 + "                     )          "
					 + "             ))"
					/*
					 * logged in user as admin but not in
					 * project_approver_hierarchy(hint:logged in user is admin but
					 * not assigned to project as approver)
					 */
					 + " OR (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :orgLocationId ) and (sah.hierarchyId.projectId.projectApprovalType = '"+Constants.PARALLEL+"' OR (sah.hierarchyId.projectId.projectApprovalType = '"+Constants.SEQUENTIAL+"' and ((1 IN(select Max(pah.level) from ProjectApproverHierarchy pah where  pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id)) OR (1 NOT IN(select Max(pah.level) from ProjectApproverHierarchy pah where  pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id) and '1' IN (select subsah.hierarchyId.level from SubmissionApproverHierarchy subsah where subsah.submissionApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and subsah.frequencyStartDate = sah.frequencyStartDate and  subsah.frequencyEndDate = sah.frequencyEndDate))))))"
					 +" OR  ((sah.hierarchyId.level = '1' and  sah.hierarchyId.status = '1' and sah.submissionId.projectMembers.projects.isReportingManagerAsApprover = 'Y'  and sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = sah.submissionId.projectMembers.projectMemberCandidteId.consultantUser.id)))"
					 + "   ) ";
		
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString()).setLong("userId", approverUserId).setLong("locationsId", locationsId).setLong("orgLocationId", locationsId).setLong("projectId", projectId).setLong("masterCandidateId", pmMasterCandidateId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		Boolean isRejected = false;
		Boolean isAPPROVED = false;
		Boolean isSUBMITTED = false;
		Boolean isNEW = false;
		SubmissionApproverHierarchy submissionApproverHierarchy = null;
		LinkedHashMap<Date, Float> weekTotalsMap = new LinkedHashMap<>();
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())) {
					List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), sah.getSubmissionId().getStartDate(), sah.getSubmissionId().getEndDate());
					float weekTotalHours = 0f;
					if (tdeList != null && !tdeList.isEmpty()) {
						for (TimeSheetDayEntries tde : tdeList) {
							float totalHours = 0f;
							if (StringUtils.isNotEmpty(tde.getTotalHours())) {
								totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
							}
							weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
						}
					}
					weekTotalsMap.put(sah.getSubmissionId().getStartDate(), weekTotalHours);
				}
				else {
					weekTotalsMap.put(sah.getSubmissionId().getStartDate(), 0f);
				}
				if (sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED) {
					isRejected = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected
						&& sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW) {
					isNEW = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW
						&& sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL) {
					isSUBMITTED = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW && !isSUBMITTED
						&& sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED) {
					isAPPROVED = true;
					submissionApproverHierarchy = sah;
				}
				else if (!isRejected && !isNEW && !isSUBMITTED && !isAPPROVED
						&& sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REOPENED) {
					submissionApproverHierarchy = sah;
				}
			}
			submissionApproverHierarchy.setWeekTotals(weekTotalsMap);
		}
		else {
			submissionApproverHierarchy = new SubmissionApproverHierarchy();
			submissionApproverHierarchy.setTotalHours(0f);
			DateFormat format = orgLoc.getLocFullFormDateFormatter();
			Map<String, String> tabFrequencyDates = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate), orgLoc.getDateFormat());
			if (tabFrequencyDates != null && !tabFrequencyDates.isEmpty()) {
				tabFrequencyDates.forEach((k, v) -> {
					try {
						weekTotalsMap.put(format.parse(k.replaceAll("/", "-")), 0f);
					} catch (Exception e) {
					}
				});
			}
			submissionApproverHierarchy.setSubmissionStatus(Submission_Status.NEW);
			submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
			submissionApproverHierarchy.setWeekTotals(weekTotalsMap);
		}
		return submissionApproverHierarchy;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Map<String, Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(Long projectMemberId, Long locationsId, Date startDate, Date endDate,String isGlobalUser) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String globalUserQuery = "";
		String subQueryForMaxLevel = "";
	    if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
	    	subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from SubmissionApproverHierarchy subsah where subsah.submissionId.id = ts.id and subsah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId";
	    	globalUserQuery = " and pclient.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)";
	    }else {
	    	globalUserQuery = " and (sah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId OR (:locationsId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.status != 0) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)))";
	        subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from SubmissionApproverHierarchy subsah where subsah.submissionId.id = ts.id and subsah.hierarchyId.projectClientsId.level IN(select Max(subpc.level) from ProjectClients subpc where subpc.projectId = :projectId)";
	    }
	    
		ProjectMembers projectMembers = projectMembersDao.getProjectMemberById(projectMemberId);
		// Getting Max Hierarchy Level for which employee is Assigned
		String query = "from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects project left join fetch ts.masterCandidateId mc left join fetch ts.projectMembers pm  left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " where  project.id = :projectId and mc.id = :masterCandidateId and (sah.submissionId.startDate = :startDate OR sah.submissionId.endDate = :endDate OR sah.submissionId.startDate BETWEEN :startDate and :endDate OR sah.submissionId.endDate BETWEEN :startDate and :endDate) and sah.submissionId.status = '1'"
				+ globalUserQuery
				/*
				 * For getting approved timesheet hours list
				 */
				+ " and ( hierarchy.level IN(" + subQueryForMaxLevel
				+ ")) and sah.submissionApprovalStatus = :approvalStatus";
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString()).setLong("projectId", projectMembers.getProjects().getId()).setLong("locationsId", locationsId).setLong("masterCandidateId", projectMembers.getMasterCandidateId().getId()).setParameter("approvalStatus", Submission_Status.APPROVED).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		LinkedHashMap<String, Float> monthTotals = new LinkedHashMap<>();
		float totalHours = 0f;
		float billableTotalHours = 0f;
		float nonBillableTotalHours = 0f;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				Date actualStartDate = null;
				Date actualEndDate = null;
				if (sah.getSubmissionId().getStartDate().before(startDate)
						|| sah.getSubmissionId().getStartDate().equals(startDate)) {
					actualStartDate = startDate;
				}
				else {
					actualStartDate = sah.getSubmissionId().getStartDate();
				}
				if (sah.getSubmissionId().getEndDate().after(endDate)
						|| sah.getSubmissionId().getEndDate().equals(endDate)) {
					actualEndDate = endDate;
				}
				else {
					actualEndDate = sah.getSubmissionId().getEndDate();
				}
				ProjectMembers projectMember = sah.getSubmissionId().getProjectMembers();
			    User consultantUser = null;
			    Long workLocationId = 0l;
				if(projectMember != null) {
					 ProjectMembers conPM =  projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
					  if(conPM != null) {
							 consultantUser = conPM.getProjectMemberCandidteId().getConsultantUser();
					  }
					  if(conPM.getProjectMemberCandidteId() != null && conPM.getProjectMemberCandidteId().getWorkLocation() != null && conPM.getProjectMemberCandidteId().getWorkLocation().getId() != null) {
						workLocationId = conPM.getProjectMemberCandidteId().getWorkLocation().getId();
					 }
			    }
				float weekTotalHours = 0f;
				float weekBillableTotalHours = 0f;
				float weekNonBillableTotalHours = 0f;
				Float leaveTotal = 0f;
    	    	Float leaveBillableTotal = 0f;
    	    	Float leaveNonBillableTotal = 0f;
	    	    if(consultantUser != null) {
	    	    	 Map<String,Float> leaveTotalMap = appliedLeaveDateService.getAppliedLeaveDateAndHolidayHoursByUserId(consultantUser.getId(), actualStartDate, actualEndDate,workLocationId);
		    	     if(leaveTotalMap != null && !leaveTotalMap.isEmpty()) {
		    	    	 leaveTotal = leaveTotalMap.get("TOTAL");
		    	    	 leaveBillableTotal = leaveTotalMap.get("BILLABLE");
		    	    	 leaveNonBillableTotal = leaveTotalMap.get("NONBILLABLE");
		    	    	 weekTotalHours = CommonUtil.getCombinedHours(leaveTotal, weekTotalHours);
		    	    	 weekBillableTotalHours = CommonUtil.getCombinedHours(leaveBillableTotal, weekBillableTotalHours);
		    	    	 weekNonBillableTotalHours = CommonUtil.getCombinedHours(leaveNonBillableTotal, weekNonBillableTotalHours);
		    	     }
		    	     
	    	    }
				List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), actualStartDate, actualEndDate);
				if (tdeList != null && !tdeList.isEmpty()) {
					for (TimeSheetDayEntries tde : tdeList) {
						float datyTotalHours = 0f;
						float dayBillableHours = 0f;
						float dayNonBillableHours = 0f;
						if (StringUtils.isNotEmpty(tde.getTotalHours())) {
							datyTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), datyTotalHours);
						}
						if (StringUtils.isNotEmpty(tde.getBillableHours())) {
							dayBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getBillableHours().replaceAll(":", ".")), dayBillableHours);
						}
						if (StringUtils.isNotEmpty(tde.getNonBillableHours())) {
							dayNonBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getNonBillableHours().replaceAll(":", ".")), dayNonBillableHours);
						}
						weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, datyTotalHours);
						weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, dayBillableHours);
						weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours, dayNonBillableHours);
					}
				}
				totalHours = CommonUtil.getCombinedHours(totalHours, weekTotalHours);
				billableTotalHours = CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours);
				nonBillableTotalHours = CommonUtil.getCombinedHours(nonBillableTotalHours, weekNonBillableTotalHours);
			}
		}
		monthTotals.put("TOTAL_HOURS", totalHours);
		monthTotals.put("BILLABLE_HOURS", billableTotalHours);
		monthTotals.put("NONBILLABLE_HOURS", nonBillableTotalHours);
		return monthTotals;
	}
	
	
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long projectId, Long orgLocationsId, Date frequencyStartDate, Date frequencyEndDate, String invoiceType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		String statusSubQuery = "";
		if (StringUtils.isNotEmpty(invoiceType)) {
			if (Constants.CLIENT_SOW.equalsIgnoreCase(invoiceType)) {
				statusSubQuery = " and sah.invoiceStatus IS NULL";
			}
			else {
				statusSubQuery = " and sah.billingStatus IS NULL";
			}
		}
		if (frequencyStartDate != null) {
			sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (frequencyEndDate != null) {
			sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		}
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(subsah.hierarchyId.level) from SubmissionApproverHierarchy subsah where subsah.submissionId.id = ts.id and subsah.hierarchyId.projectClientsId.orgLocationsId.id = :orgLocationsId ";
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy  left join fetch hierarchy.projectClientsId pc "
				+ " left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts left join fetch ts.projects projects "
				+ " left join fetch ts.masterCandidateId mc left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user "
				+ " where projects.id = :projectId and pc.orgLocationsId.id = :orgLocationsId and sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate  "
				+ statusSubQuery + " and ts.status = '1'"
				/*
				 * For getting approved timesheet hours list
				 */
				+ " and ( hierarchy.level IN(" + subQueryForMaxLevel
				+ ")) and pc.orgLocationsId.id = :orgLocationsId order by pm.id";
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString()).setLong("projectId", projectId).setLong("orgLocationsId", orgLocationsId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		LinkedList<SubmissionApproverHierarchy> finalSahList = new LinkedList<>();
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			Long projectMemberId = 0l;
			int count = 0;
			SubmissionApproverHierarchy finalSah = null;
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (projectMemberId == 0 || projectMemberId != sah.getSubmissionId().getProjectMembers().getId()) {
					if (projectMemberId != 0) {
						finalSahList.add(finalSah);
						count = 0;
						//projectMemberId = sah.getSubmissionId().getProjectMembers().getId();
					}
					finalSah = sah;
					projectMemberId = sah.getSubmissionId().getProjectMembers().getId(); //moved to if condition
					float weekTotalHours = 0f;
					float weekBillableTotalHours = 0f;
					float weekNonBillableTotalHours = 0f;
					if (Submission_ApprovalStatus.APPROVED.getValue().equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())) {
						List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), sah.getSubmissionId().getStartDate(), sah.getSubmissionId().getEndDate());
						if (tdeList != null && !tdeList.isEmpty()) {
							for (TimeSheetDayEntries tde : tdeList) {
								float totalHours = 0f;
								float totalBillableHours = 0f;
								float totalNonBillableHours = 0f;
								if (StringUtils.isNotEmpty(tde.getTotalHours())) {
									totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
								}
								weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
								if (StringUtils.isNotEmpty(tde.getBillableHours())) {
									totalBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getBillableHours().replaceAll(":", ".")), totalBillableHours);
								}
								weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, totalBillableHours);
								if (StringUtils.isNotEmpty(tde.getNonBillableHours())) {
									totalNonBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getNonBillableHours().replaceAll(":", ".")), totalNonBillableHours);
								}
								weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours, totalNonBillableHours);
							}
						}
						float totalHours = 0f;
						float billableTotalHours = 0f;
						float nonBillableTotalHours = 0f;
						if (finalSah.getTotalHours() != null) {
							totalHours = finalSah.getTotalHours();
						}
						if (finalSah.getBillableHours() != null) {
							billableTotalHours = finalSah.getBillableHours();
						}
						if (finalSah.getNonBillableHours() != null) {
							nonBillableTotalHours = finalSah.getNonBillableHours();
						}
						finalSah.setTotalHours(CommonUtil.getCombinedHours(totalHours, weekTotalHours));
						finalSah.setBillableHours(CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours));
						finalSah.setNonBillableHours(CommonUtil.getCombinedHours(nonBillableTotalHours, weekNonBillableTotalHours));
						count++;
					} /*
						 * else{ finalSah.setTotalHours(0f);
						 * finalSah.setBillableHours(0f);
						 * finalSah.setNonBillableHours(0f); }
						 */
				}
				else {
					if (finalSah == null)
						continue;
					float weekTotalHours = 0f;
					float weekBillableTotalHours = 0f;
					float weekNonBillableTotalHours = 0f;
					if (Submission_ApprovalStatus.APPROVED.getValue().equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())) {
						List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), sah.getSubmissionId().getStartDate(), sah.getSubmissionId().getEndDate());
						if (tdeList != null && !tdeList.isEmpty()) {
							for (TimeSheetDayEntries tde : tdeList) {
								float totalHours = 0f;
								float totalBillableHours = 0f;
								float totalNonBillableHours = 0f;
								if (StringUtils.isNotEmpty(tde.getTotalHours())) {
									totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
								}
								weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
								if (StringUtils.isNotEmpty(tde.getBillableHours())) {
									totalBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getBillableHours().replaceAll(":", ".")), totalBillableHours);
								}
								weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, totalBillableHours);
								if (StringUtils.isNotEmpty(tde.getNonBillableHours())) {
									totalNonBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getNonBillableHours().replaceAll(":", ".")), totalNonBillableHours);
								}
								weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours, totalNonBillableHours);
							}
						}
						float totalHours = 0f;
						float billableTotalHours = 0f;
						float nonBillableTotalHours = 0f;
						if (finalSah.getTotalHours() != null) {
							totalHours = finalSah.getTotalHours();
						}
						if (finalSah.getBillableHours() != null) {
							billableTotalHours = finalSah.getBillableHours();
						}
						if (finalSah.getNonBillableHours() != null) {
							nonBillableTotalHours = finalSah.getNonBillableHours();
						}
						finalSah.setTotalHours(CommonUtil.getCombinedHours(totalHours, weekTotalHours));
						finalSah.setBillableHours(CommonUtil.getCombinedHours(billableTotalHours, weekBillableTotalHours));
						finalSah.setNonBillableHours(CommonUtil.getCombinedHours(nonBillableTotalHours, weekNonBillableTotalHours));
						count++;
					} /*
						 * else{ finalSah.setTotalHours(0f);
						 * finalSah.setBillableHours(0f);
						 * finalSah.setNonBillableHours(0f); }
						 */
				}
			}
			if (count > 0) {
				finalSahList.add(finalSah);
			}
		}
		return finalSahList;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long submissionHierarchyId, String invoiceType, String status, Invoice invoice) throws Exception
	{
		SubmissionApproverHierarchy sah = getSubmissionApproverHierarchy(submissionHierarchyId.toString());
		java.sql.Date sqlStartDate = null;
		if (sah.getFrequencyStartDate() != null) {
			sqlStartDate = new java.sql.Date(sah.getFrequencyStartDate().getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (sah.getFrequencyEndDate() != null) {
			sqlEndDate = new java.sql.Date(sah.getFrequencyEndDate().getTime());
		}
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.submissionId ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee  emp  left join fetch emp.user "
				+ " where  sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate and pm.id = :projectMemberId ";
		List<SubmissionApproverHierarchy> sahList = session.createQuery(query.toString()).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).setLong("projectMemberId", sah.getSubmissionId().getProjectMembers().getId()).list();
		if (null != sahList && !sahList.isEmpty()) {
			for (SubmissionApproverHierarchy subSah : sahList) {
				if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
					subSah.setInvoiceStatus(status);
					subSah.setInvoiceId(invoice);
					subSah.setInvoiceDate(invoice.getInvoiceDate());	
				}
				else {
					subSah.setBillingStatus(status);
					subSah.setBillId(invoice);
					subSah.setBillDate(invoice.getInvoiceDate());
				}
			}
			saveAllSubmissionApproverHierarchy(sahList);
		}
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isReopenButtonVisible(Date frequencyStartDate, Date frequencyEndDate, Long projectId, Long masterCandidateId, Long locationId,Long orgId,String isGlobalAccess)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		String globalUserQuery = "";
	    if(StringUtils.isNotEmpty(isGlobalAccess) && isGlobalAccess.equalsIgnoreCase("Y")) {
	    	globalUserQuery = " and pc.orgLocationsId.org.id = :orgId ";
	    }else {
	    	globalUserQuery = " and (sah.hierarchyId.projectClientsId.orgLocationsId.id = :locationId OR (:locationId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.status != 0) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.submissionId.projects.id and subpm.masterCandidateId.id = sah.submissionId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId)))";
	    }
	    
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId hierarchy "
				+ " left join fetch hierarchy.projectClientsId pc left join fetch sah.submissionId submission  "
				+ "   left join fetch submission.projectMembers pm left join fetch submission.projects pj left join fetch submission.masterCandidateId mc  "
				+ "  where pj.id = :projectId and mc.id = :masterCandidateId and sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate"
				+globalUserQuery+" and  (pc.level IN(select max(subpc.level) from ProjectClients subpc where subpc.projectId.id = :projectId and subpc.orgLocationsId.org.orgType <> '"
				+ Constants.GUEST
				+ "') OR pc.level IN(select min(subpc.level) from ProjectClients subpc where subpc.projectId.id = :projectId and subpc.orgLocationsId.org.orgType <> '"
				+ Constants.GUEST + "'))" + " ");
		Query queryObj = submissionApproverHierarchyListQuery.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate);
		 if(StringUtils.isNotEmpty(isGlobalAccess) && isGlobalAccess.equalsIgnoreCase("Y")) {
			 queryObj .setLong("orgId", orgId);
		 }else {
			 queryObj.setLong("locationId", locationId);
		 }
		 List<SubmissionApproverHierarchy> sahList = queryObj.list();
		 boolean flag = false;
		if (sahList != null && !sahList.isEmpty()) {
			flag = true;
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean getPendingForApprovalTimesheetReportByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId)
	{
		Boolean flag = false;
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
		Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm where pm.id = :projectMemberId and (sah.submissionStatus = 'SUBMITTED')  and  sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate");
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).list();
		if (null != submissionApproverHierarchyList && !submissionApproverHierarchyList.isEmpty()) {
			flag = true;
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Map<String, SubmissionApproverHierarchy> getTotalHoursByProjectIdAndLocationIdApprovalStatusStartDateEndDate(Long loginOrgLocationId, Long projectId, Date startDate, Date endDate) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Map<String, SubmissionApproverHierarchy> resulMap = new ConcurrentHashMap<>();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		String query = "from  SubmissionApproverHierarchy sah  where "
				+ " sah.hierarchyId.projectClientsId.orgLocationsId.id = :loginOrgLocationId and sah.submissionId.projectMembers.projects.id = :projectId"
				+ "  and (sah.frequencyStartDate = :startDate OR sah.frequencyEndDate = :endDate OR sah.frequencyStartDate BETWEEN :startDate and :endDate OR sah.frequencyEndDate BETWEEN :startDate and :endDate) and sah.submissionId.status = '1' GROUP BY sah.submissionId.id";
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString()).setLong("loginOrgLocationId", loginOrgLocationId).setLong("projectId", projectId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		SubmissionApproverHierarchy submissionApproverHierarchy = null;
		Float actualTotalHours = 0f;
		Float acutalBillableTotalHours = 0f;
		Float actualNonBillableTotalHours = 0f;
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				submissionApproverHierarchy = resulMap.get(sah.getSubmissionApprovalStatus().toString());
				if (submissionApproverHierarchy == null) {
					submissionApproverHierarchy = sah;
					actualTotalHours = 0f;
					acutalBillableTotalHours = 0f;
					actualNonBillableTotalHours = 0f;
				}
				else {
					actualTotalHours = submissionApproverHierarchy.getTotalHours();
					acutalBillableTotalHours = submissionApproverHierarchy.getBillableHours();
					actualNonBillableTotalHours = submissionApproverHierarchy.getNonBillableHours();
				}
				Date actualStartDate = null;
				Date actualEndDate = null;
				if (sah.getSubmissionId().getStartDate().before(startDate)
						|| sah.getSubmissionId().getStartDate().equals(startDate)) {
					actualStartDate = startDate;
				}
				else {
					actualStartDate = sah.getSubmissionId().getStartDate();
				}
				if (sah.getSubmissionId().getEndDate().after(endDate)
						|| sah.getSubmissionId().getEndDate().equals(endDate)) {
					actualEndDate = endDate;
				}
				else {
					actualEndDate = sah.getSubmissionId().getEndDate();
				}
				List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), actualStartDate, actualEndDate);
				Float weekTotalHours = 0f;
				Float weekBillableTotalHours = 0f;
				Float weekNonBillableTotalHours = 0f;
				if (tdeList != null && !tdeList.isEmpty()) {
					for (TimeSheetDayEntries tde : tdeList) {
						float totalHours = 0f;
						float billableHours = 0f;
						float nonBillableHours = 0f;
						if (StringUtils.isNotEmpty(tde.getTotalHours())) {
							totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
						}
						if (StringUtils.isNotEmpty(tde.getBillableHours())) {
							billableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getBillableHours().replaceAll(":", ".")), billableHours);
						}
						if (StringUtils.isNotEmpty(tde.getNonBillableHours())) {
							nonBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getNonBillableHours().replaceAll(":", ".")), nonBillableHours);
						}
						weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
						weekBillableTotalHours = CommonUtil.getCombinedHours(weekBillableTotalHours, billableHours);
						weekNonBillableTotalHours = CommonUtil.getCombinedHours(weekNonBillableTotalHours, nonBillableHours);
					}
				}
				actualTotalHours = CommonUtil.getCombinedHours(actualTotalHours, weekTotalHours);
				acutalBillableTotalHours = CommonUtil.getCombinedHours(acutalBillableTotalHours, weekBillableTotalHours);
				actualNonBillableTotalHours = CommonUtil.getCombinedHours(actualNonBillableTotalHours, weekNonBillableTotalHours);
				submissionApproverHierarchy.setTotalHours(actualTotalHours);
				submissionApproverHierarchy.setBillableHours(acutalBillableTotalHours);
				submissionApproverHierarchy.setNonBillableHours(actualNonBillableTotalHours);
				resulMap.put(submissionApproverHierarchy.getSubmissionApprovalStatus().toString(), submissionApproverHierarchy);
			}
		}
		return resulMap;
	}
	
	
	
	/*============================================================== Query Change ================================================================*/
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<TimesheetApprovalSubmissionDetailsDTO> getSubmissionApproverHierarchyByTimesheetFrequencyOrgLocationsIdAndApproverUserId(Set<Long> projectIds,Boolean isAdmin,String timesheetFrequency,Date startDate,Date endDate,Long clientId,Long projectId,String status,Long locationsId,Long orgId, Long approverUserId,String searchValue,String projectName,int start, int length,int totalRows,String isGlobalUser,String tableFor,String dateFormat) throws Exception {
		long methodStartTime = System.nanoTime();
		List<BigInteger> countlist = null;  
		Integer filterCount = 0;
		ResponseList<TimesheetApprovalSubmissionDetailsDTO> response = new ResponseList<TimesheetApprovalSubmissionDetailsDTO>();
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String orgDateFormat = "";
		if("yyyy-MM-dd".equalsIgnoreCase(dateFormat)) {
			orgDateFormat = "\"%Y-%m-%d\"";
		}else if("dd-MM-yyyy".equalsIgnoreCase(dateFormat)) {
			orgDateFormat = "\"%d-%m-%Y\"";
		}else if("MM-dd-yyyy".equalsIgnoreCase(dateFormat)) {
			orgDateFormat = "\"%m-%d-%Y\"";
		}else if("yyyy-dd-MM".equalsIgnoreCase(dateFormat)) {
			orgDateFormat = "\"%Y-%d-%m\"";
		}
		StringBuilder searchQuery = new StringBuilder();
		if(StringUtils.isNotEmpty(searchValue) && searchValue != null) {
			searchQuery.append(" Where  ( Q1.employeeName like '%" + searchValue + "%' ")
					.append("OR Q1.employeeEmail like '%" + searchValue + "%' ")
					.append("OR Q1.employeePhoneNo like '%" + searchValue + "%' ")
					.append("OR Q1.designation like '%" + searchValue + "%' ")
					.append("OR Q1.project like '%" + searchValue + "%' ")
					.append("OR Q1.submissionStatus like '%" + searchValue + "%') ");

		}
		
		StringBuilder statusQuery = new StringBuilder();
		if(StringUtils.isNotEmpty(tableFor) && tableFor.equalsIgnoreCase("approver")) {
			if(StringUtils.isNotEmpty(status) && !(status.equalsIgnoreCase("NEW") || status.equalsIgnoreCase("All"))){
				statusQuery.append(" SAH.SUBMISSION_APPROVALSTATUS = '" + Submission_ApprovalStatus.getNameByValue(status) + "'");
			}else if(StringUtils.isNotEmpty(status) && (status.equalsIgnoreCase("All"))){
				statusQuery.append(" (SAH.SUBMISSION_APPROVALSTATUS = '" + Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL + "' OR SAH.SUBMISSION_APPROVALSTATUS = '" + Submission_ApprovalStatus.APPROVED + "'  OR SAH.SUBMISSION_APPROVALSTATUS = '" + Submission_ApprovalStatus.REJECTED + "')");
			}
		}else if(StringUtils.isNotEmpty(tableFor) && tableFor.equalsIgnoreCase("dashboard")) {
			statusQuery.append(" SAH.SUBMISSION_STATUS = '" + Submission_Status.getNameByValue(status) + "'");
		}
		
		StringBuilder filterQuery = new StringBuilder();
		if(StringUtils.isNotEmpty(projectName)) {
			filterQuery.append("  PROJ.PROJECT_NAME like '%" + projectName + "%'");
		}
		if(clientId != null){
			if(StringUtils.isNotEmpty(projectName)) {
				filterQuery.append(" AND ");
			}
			filterQuery.append(" PC.ORG_RELATION_ID = " + clientId + "");
		}
		
		StringBuilder outputFieldQuery= new StringBuilder();
		outputFieldQuery.append(" PM.ID as projectMemberId,")
		                .append(" PROJ.ID as projectId,") 
		                .append(" PM.MASTER_CANDIDATE_ID as masterCandidateId,")
		                .append(" CONCAT(IF(CAN.FIRST_NAME IS NOT NULL, CONCAT(CAN.FIRST_NAME,' '),'') ,IF(CAN.LAST_NAME IS NOT NULL, CONCAT(CAN.LAST_NAME),'')) as employeeName,")
		                .append(" U.FILE_URL as profileImageFileUrl ,")
		                .append(" CAN.EMAIL as employeeEmail,")
		                .append(" CAN.MOBILE_PHONE as employeePhoneNo,")
		                .append(" CAN.DESIGNATION_TYPE  as designation,")
		                .append(" PROJ.PROJECT_NAME as project,")
		                .append("  (CASE " )
        			    .append( "      WHEN PC.ORG_RELATION_ID IS NOT NULL ")
        			    .append( "      THEN PC.ORG_RELATION_ID ")
        				.append("      ELSE  NULL ")
        			    .append( "      END")
        			    .append( "  ) AS clientId,")
        			    .append(" PROJ.INVOICE_FREQUENCY AS dashboardInvoiceFrequency,");
        			    if(sqlStartDate != null) {
        			    	outputFieldQuery
        			    	.append(" DATE_FORMAT('"+sqlStartDate+"',"+orgDateFormat+") as actualStartDate,");
                         }else {
                        	 outputFieldQuery
 					    	.append(" DATE_FORMAT(SAH.FREQUENCY_START_DATE,"+orgDateFormat+") as actualStartDate,");
                         }
		
						if(sqlEndDate != null) {
							outputFieldQuery
					    	.append(" DATE_FORMAT('"+sqlEndDate+"',"+orgDateFormat+") as actualEndDate,");
				         }else {
				        	 outputFieldQuery
						    	.append(" DATE_FORMAT(SAH.FREQUENCY_END_DATE,"+orgDateFormat+") as actualEndDate,");
				         }
						outputFieldQuery
		                .append("  '"+tableFor+"' AS tableFor, ")
		                .append("   U.ID AS consultantUserId,")
		                .append("   CAN.WORK_LOCATION AS workLocationId,");
		        			    
		StringBuilder parallelApproverCheckQuery= new StringBuilder();  
		                   parallelApproverCheckQuery
		                  //isApprover
		                  .append( "( PAH.APPROVER_USER_ID = '"+approverUserId+"'  AND PAH.STATUS != '0' AND PAH.PROJECT_CLIENTS_ID = PC.ID)   " )
		                  .append("       OR   ")
		                   //isAdmin
		                  .append(" (").append(isAdmin).append(" AND PAH.LEVEL IN(SELECT MAX(PAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID) AND PAH.PROJECT_CLIENTS_ID = PC.ID)")
		        		  .append("       OR   ")   
		        		  //isReportingManager
		        		  .append(" ( PAH.LEVEL = '1' AND PROJ.IS_REPORTING_MANAGER_AS_APPROVER = 'Y'  AND UR.USER_ID = U.ID AND UR.REPORTING_MANAGER_ID = '"+approverUserId+"' AND PAH.PROJECT_CLIENTS_ID = PC.ID)");
		
	  
	     //checking with low-level(vendor),highest level manager approval                      
	     StringBuilder sequentiaLCheckForVendorApproval= new StringBuilder();                   
		                           sequentiaLCheckForVendorApproval
		                           .append(" ( CASE ")
		                           .append("   WHEN PC.LEVEL < (")
		                           .append( "             SELECT MAX(SUBPC.LEVEL) FROM PROJECT_MEMBERS SUBPM ")
		                           .append( "              INNER JOIN(SELECT ID,ORG_ID,LEVEL,PROJECT_ID,STATUS FROM PROJECT_CLIENTS) AS SUBPC ON SUBPC.ID = SUBPM.PROJECT_CLIENTS_ID ")
		   		   		           .append( "              INNER JOIN (SELECT ID,ORG_TYPE FROM ORG) AS OG ON OG.ID = SUBPC.ORG_ID     ")	   
		   		   		           .append("               WHERE SUBPM.MASTER_CANDIDATE_ID = PM.MASTER_CANDIDATE_ID AND SUBPC.PROJECT_ID = PROJ.ID AND OG.ORG_TYPE != 'GUEST' AND SUBPC.STATUS != '0'")
		   		   		           .append( "            ) THEN  ")
		   		   		           .append( "            (    ")
		   		   		           .append( "              (PC.LEVEL+1) IN (  ")
		   		   		           .append("                               SELECT SUBPC.LEVEL FROM SUBMISSION_APPROVER_HIERARCHY  SUBSAH " + 
		   		   		        		"                                  INNER JOIN (SELECT ID,MASTER_CANDIDATE_ID FROM PROJECT_MEMBERS) AS SUBPM ON SUBPM.ID = SUBSAH.PROJECT_MEMBER_ID " +
		   		   		           		"                                  INNER JOIN (SELECT ID,LEVEL,PROJECT_CLIENTS_ID FROM PROJECT_APPROVER_HIERARCHY) AS SUBPAH ON SUBPAH.ID = SUBSAH.HIERARCHY_ID " + 
		   		   		           		"                                  INNER JOIN (SELECT ID,LEVEL FROM PROJECT_CLIENTS) AS SUBPC ON SUBPC.ID = SUBSAH.PROJECT_CLIENT_ID " + 
		   		   		           		"                                  WHERE SUBSAH.SUBMISSION_APPROVALSTATUS =  '"+Submission_ApprovalStatus.APPROVED+"' AND " + 
		   		   		           		                                   //checking with subsequent vendor 
		   		   		           		"                                  SUBPM.MASTER_CANDIDATE_ID = PM.MASTER_CANDIDATE_ID AND SUBSAH.FREQUENCY_START_DATE = '"+sqlStartDate+"' AND SUBSAH.FREQUENCY_END_DATE = '"+sqlEndDate+"' " + 
		   		   		                                                   //checking with subsequent vendor's highest level of approval mechanism
		   		   		        	    "                                  AND SUBPAH.LEVEL IN (SELECT MAX(INNERPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY INNERPAH WHERE INNERPAH.PROJECT_CLIENTS_ID = SUBPAH.PROJECT_CLIENTS_ID)")
		   		   		          .append("                              )")
		   		               	   .append( "            ) ELSE  true ")
		   		   		           .append( "   END )      ");	   
		                         
		   		   		          
		                           
      //checking with level2 manager approver in same org                          
	  StringBuilder sequentiaLCheckForLevel2Approval= new StringBuilder();
	                           sequentiaLCheckForLevel2Approval
	                           .append("1 IN ( ")
	                           .append("    SELECT SUBPAH.LEVEL FROM SUBMISSION_APPROVER_HIERARCHY SUBSAH ")
	                           .append("     INNER JOIN (SELECT ID,LEVEL FROM PROJECT_APPROVER_HIERARCHY) AS SUBPAH ON SUBPAH.ID = SUBSAH.HIERARCHY_ID ")
	                           .append("      WHERE ")
	                            //checking with mastercandidateId,projectClientId(i.e;locatioonId),frequencystartDate and frequencyEndDate
	                           .append("     SUBSAH.PROJECT_CLIENT_ID = SAH.PROJECT_CLIENT_ID ")
	                           .append("     AND SUBSAH.SUBMISSION_APPROVALSTATUS = '"+Submission_ApprovalStatus.APPROVED+"' ")
	                           .append("     AND SUBSAH.MASTER_CANDIDATE_ID = SAH.MASTER_CANDIDATE_ID ")
	                           .append("     AND SUBSAH.FREQUENCY_START_DATE = SAH.FREQUENCY_START_DATE ")
	                           .append("     AND SUBSAH.FREQUENCY_END_DATE = SAH.FREQUENCY_END_DATE ")
	                           .append(" )");
	  
	  StringBuilder sequentiaLCheckForNonNewStatus= new StringBuilder(); 
	                          sequentiaLCheckForNonNewStatus
	                          .append("(")
	                           //isApprover
	                          .append("    (")
	                          .append("        PAH.APPROVER_USER_ID = '"+approverUserId+"'  AND PAH.STATUS != '0' AND PAH.PROJECT_CLIENTS_ID = PC.ID ")
	                          .append("        AND ")
	                         .append("       ( CASE ")
	                                           //checking with low-level(vendor),highest level manager approval
	 				    	 .append( "        WHEN (PAH.LEVEL = '1')  " )
	 				    	 .append("         THEN ( ").append(sequentiaLCheckForVendorApproval).append(")")
	 				    	                   //checking with level2 manager approver in same org
	 				    	 .append( "        WHEN (PAH.LEVEL = '2') " )
	 				    	 .append("         THEN ( ").append(sequentiaLCheckForLevel2Approval).append(")")
	 				    	 .append("        ELSE  '' ")
	 				    	 .append("        END ")
	 				    	 .append("        )")
	 				    	 .append("       )")
	 				    	 .append("  OR     ")
	 				    	  //isAdmin
	 				    	 .append("      (")
	                         .append("").append(isAdmin).append("")
	                         .append("    AND    ")
	                         .append("         ( CASE ")
	 				    	 .append( "          WHEN (1 IN(SELECT MAX(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID AND SUBPAH.status != '0') AND PAH.PROJECT_CLIENTS_ID = PC.ID)  " )
	 				    	 .append("           THEN ( ").append(sequentiaLCheckForVendorApproval).append(")")
	 				    	 .append( "          WHEN (2 IN(SELECT MAX(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID AND SUBPAH.status != '0') AND PAH.PROJECT_CLIENTS_ID = PC.ID) " )
	 				    	 .append("           THEN ( ").append(sequentiaLCheckForLevel2Approval).append(")")
	 				    	 .append("           ELSE  '' ")
	 				    	 .append("           END ")
	 				    	 .append("         )")
	 				    	 .append("      )")
	 				    	 .append("  OR     ")
	 				    	 //isReportingManager
	 				    	 .append("      (")
	 				    	 .append(" ( PAH.LEVEL = '1' AND PROJ.IS_REPORTING_MANAGER_AS_APPROVER = 'Y' AND UR.USER_ID = U.ID AND UR.REPORTING_MANAGER_ID = '"+approverUserId+"' AND PAH.PROJECT_CLIENTS_ID = PC.ID)")
	 				    	 .append("      )")
	 				    	 .append(")");
	                          
	                           
	                         
		 
		StringBuilder newStatusQuery= new StringBuilder();     
		if(StringUtils.isNotEmpty(status) && (status.equalsIgnoreCase("All") || status.equalsIgnoreCase("NEW"))) {
			newStatusQuery.append("  ( SELECT ")
            .append(outputFieldQuery)
            .append("  'NEW' AS submissionStatus,")
            .append(" NULL AS actionPerformedBy,")
            .append(" NULL AS rejectedReason")
            
            .append("  FROM USER_ROLE UR,PROJECT_MEMBERS PM ")
           
            /*Getting Projects*/
            .append("  INNER JOIN (SELECT ID,INVOICE_FREQUENCY,PROJECT_APPROVAL_TYPE,PROJECT_NAME,IS_REPORTING_MANAGER_AS_APPROVER, WEEK_START_DATE,IS_PROJECT_FOR_TIMESHEETS FROM PROJECTS ) AS PROJ ON PROJ.ID = PM.PROJECT_ID")
            
            /*Getting candidate*/
			.append("  LEFT JOIN (SELECT ID,MASTER_CANDIDATE_ID,DESIGNATION_TYPE,CONSULTANT_USER_ID,FIRST_NAME,LAST_NAME,EMAIL,MOBILE_PHONE,WORK_LOCATION FROM CANDIDATE) AS CAN ON CAN.ID = PM.CANDIDATE_ID")
           
            /*Getting consultant user*/
            .append("  LEFT JOIN (SELECT ID,FILE_URL FROM USER) AS U ON U.ID = CAN.CONSULTANT_USER_ID ");
			 //To get current  orglocation details 
		    if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
		    	newStatusQuery.append(" INNER JOIN (SELECT ID,PROJECT_ID,ORG_LOCATIONS_ID,ORG_ID,ORG_RELATION_ID,LEVEL FROM PROJECT_CLIENTS) AS PC ON PC.ID = PM.PROJECT_CLIENTS_ID  AND (PC.ORG_LOCATIONS_ID = '"+locationsId+"' OR  (PC.ORG_LOCATIONS_ID != '"+locationsId+"' AND PC.ORG_ID = '"+orgId+"'))");
		    }else {
		    	newStatusQuery.append(" INNER JOIN (SELECT ID,PROJECT_ID,ORG_LOCATIONS_ID,ORG_ID,ORG_RELATION_ID,LEVEL FROM PROJECT_CLIENTS) AS PC ON PC.ID = PM.PROJECT_CLIENTS_ID  AND PC.ORG_LOCATIONS_ID = '"+locationsId+"'");
		    			
		    }
		    //getting project approver hierarchy
		    newStatusQuery.append(" INNER JOIN (SELECT ID,PROJECT_ID,PROJECT_CLIENTS_ID,APPROVER_USER_ID,STATUS,LEVEL FROM PROJECT_APPROVER_HIERARCHY) AS PAH ON PAH.PROJECT_ID = PM.PROJECT_ID AND PAH.PROJECT_CLIENTS_ID = PM.PROJECT_CLIENTS_ID ");
		    StringBuilder projectsQuery = new StringBuilder();
			if(projectId != null){
				projectsQuery.append(" PM.PROJECT_ID  = " + projectId + "");
			}else {
				String projectIdString = "";
				if(projectIds != null && !projectIds.isEmpty()) {
					projectIdString = StringUtils.join(projectIds, ',');
				}
				projectsQuery.append(" PM.PROJECT_ID IN( " + projectIdString + " )");
			}
		    newStatusQuery.append(" WHERE ")
		                 .append(projectsQuery)
		                 .append(" AND PROJ.ID = PM.PROJECT_ID")
		 		         .append(" AND PROJ.IS_PROJECT_FOR_TIMESHEETS = 'Y' ");
		 		       
		                 //should be a low-level org in the case of client-vendor relationship
		 		        newStatusQuery
		                .append(" AND PC.LEVEL IN(SELECT MAX(SUBPC.LEVEL) FROM PROJECT_MEMBERS SUBPM ")
		                .append( "              INNER JOIN(SELECT ID,ORG_ID,LEVEL,PROJECT_ID,STATUS FROM PROJECT_CLIENTS) AS SUBPC ON SUBPC.ID = SUBPM.PROJECT_CLIENTS_ID ")
		                .append( "              INNER JOIN (SELECT ID,ORG_TYPE FROM ORG) AS OG ON OG.ID = SUBPC.ORG_ID     ")
		                .append("               WHERE SUBPM.MASTER_CANDIDATE_ID = PM.MASTER_CANDIDATE_ID AND SUBPC.PROJECT_ID = PM.PROJECT_ID AND OG.ORG_TYPE != 'GUEST' AND SUBPC.STATUS != '0' ) ");
		    
		                //ProjectMember should be available with in the frequency or not
		                newStatusQuery
		                 .append(" AND PM.ID IN( SELECT PMH.PROJECT_MEMBERS_ID FROM PROJECT_MEMBERS_HISTORY PMH WHERE ")
		                .append( " (PMH.PROJECT_TERMINATION_DATE IS NOT NULL AND (PMH.PROJECT_ASSIGNED_DATE >='"+sqlStartDate+"'  AND PMH.PROJECT_TERMINATION_DATE <'"+sqlStartDate+"')) OR ")
		                .append( " (PMH.PROJECT_TERMINATION_DATE IS NOT NULL AND (PMH.PROJECT_ASSIGNED_DATE >='"+sqlEndDate+"'  AND PMH.PROJECT_TERMINATION_DATE <'"+sqlEndDate+"')) OR ")
		                .append( " (PMH.PROJECT_TERMINATION_DATE IS NOT NULL AND PMH.PROJECT_TERMINATION_DATE BETWEEN  '"+sqlStartDate+"' AND '"+sqlEndDate+"') OR ")
		                .append( " (PMH.PROJECT_TERMINATION_DATE IS NULL AND (PMH.PROJECT_ASSIGNED_DATE <'"+sqlEndDate+"')) ")
		                .append( " ) ");
		    
					    if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
					    	newStatusQuery.append(" AND DATEDIFF(PROJ.WEEK_START_DATE,'"+ sqlStartDate+"')%14=0 ");
						}
			
		                
		                if(StringUtils.isNotEmpty(filterQuery)) {
		                	newStatusQuery.append(" AND ").append(filterQuery);
					    }
		                
		                StringBuilder sequentialCheckForNewStatus= new StringBuilder(); 
                        sequentialCheckForNewStatus
                       .append("( PAH.LEVEL = '1' AND PAH.APPROVER_USER_ID = '"+approverUserId+"'  AND PAH.STATUS != '0' AND PAH.PROJECT_CLIENTS_ID = PC.ID ) ")
                       .append("  OR     ")
                       .append(" (").append(isAdmin).append(" AND 1 IN(SELECT MAX(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID) AND PAH.PROJECT_CLIENTS_ID = PC.ID)")
                       .append("  OR     ")
                       .append(" ( PAH.LEVEL = '1' AND PROJ.IS_REPORTING_MANAGER_AS_APPROVER = 'Y' AND   UR.USER_ID = U.ID AND UR.REPORTING_MANAGER_ID = '"+approverUserId+"' AND PAH.PROJECT_CLIENTS_ID = PC.ID)")
                       .append("");
		                
		                //projectmember should  not present in submissionapprover hierarchy 
		                newStatusQuery.append(" AND  PM.ID NOT IN(SELECT SAH.PROJECT_MEMBER_ID FROM SUBMISSION_APPROVER_HIERARCHY SAH WHERE SAH.PROJECT_CLIENT_ID = PC.ID AND SAH.FREQUENCY_START_DATE = '"+sqlStartDate+"' AND  SAH.FREQUENCY_END_DATE = '"+sqlEndDate+"' AND SAH.STATUS != 0 AND SAH.SUBMISSION_APPROVALSTATUS IN('"+Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL+"','"+Submission_ApprovalStatus.APPROVED+"','"+Submission_ApprovalStatus.REJECTED+"'))");
		                newStatusQuery.append(" AND ")
				    	 .append("   ( CASE ")
				    	 .append( "        WHEN PROJ.PROJECT_APPROVAL_TYPE ='"+Constants.PARALLEL+"' THEN " )
				    	 .append("         ( ").append(parallelApproverCheckQuery).append(")")
				    	 .append( "        WHEN PROJ.PROJECT_APPROVAL_TYPE ='"+Constants.SEQUENTIAL+"' THEN " )
				    	 .append("         ( ").append(sequentialCheckForNewStatus).append(")")
				    	 .append("        ELSE  '' ")
				    	 .append("      END ")
				    	 .append("    )")
		                .append(")");
		}
		
			    
	   StringBuilder query= new StringBuilder(); 
	           query.append("SELECT Q1.* FROM ( ");
	   if(StringUtils.isNotEmpty(status) && !status.equalsIgnoreCase("NEW")) {
		         query.append(" ( SELECT ")
		            .append(outputFieldQuery)
		            .append("  (CASE ")
					      .append(" WHEN SAH.SUBMISSION_APPROVALSTATUS = 'SUBMITTEDFORAPPROVAL' ")
				          .append("      THEN  'SUBMITTED FOR APPROVAL'")
				          .append("      ELSE  SAH.SUBMISSION_APPROVALSTATUS")
				          .append("      END")
				          .append( "  ) AS submissionStatus,")
		            .append(" SAH.ACTIONPERFORMEDBY AS actionPerformedBy,")
		            .append(" SAH.REJECTED_REASON AS rejectedReason")
			    
		            .append("  FROM USER_ROLE UR,SUBMISSION_APPROVER_HIERARCHY SAH ")
		           
		            /*Getting Projects*/
		            .append("  INNER JOIN (SELECT ID,INVOICE_FREQUENCY,PROJECT_APPROVAL_TYPE,PROJECT_NAME,IS_REPORTING_MANAGER_AS_APPROVER, WEEK_START_DATE,IS_PROJECT_FOR_TIMESHEETS FROM PROJECTS ) AS PROJ ON PROJ.ID = SAH.PROJECT_ID")
		            
		            /*Getting ProjectMember*/
		            .append("  INNER JOIN (SELECT ID,CANDIDATE_ID,MASTER_CANDIDATE_ID,PROJECT_CLIENTS_ID FROM PROJECT_MEMBERS ) AS PM ON PM.ID = SAH.PROJECT_MEMBER_ID ")
		            /*Getting consultant UserId*/
					.append("  LEFT JOIN (SELECT ID,FILE_URL FROM USER) AS U ON U.ID = SAH.CONSULTANT_USER_ID ")
					
					 /*Getting location based candidate*/
					.append("  LEFT JOIN (SELECT ID,MASTER_CANDIDATE_ID,DESIGNATION_TYPE,CONSULTANT_USER_ID,FIRST_NAME,LAST_NAME,EMAIL,MOBILE_PHONE,WORK_LOCATION FROM CANDIDATE) AS CAN ON CAN.ID = SAH.CANDIDATE_ID")
		           
					/*Getting Approver Hierarchy*/
					.append("  INNER JOIN (SELECT ID,APPROVER_USER_ID,PROJECT_CLIENTS_ID,STATUS,LEVEL FROM PROJECT_APPROVER_HIERARCHY) AS PAH ON PAH.ID = SAH.HIERARCHY_ID ");
					 /*Getting Project Client*/
				    if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
				    	query.append(" INNER JOIN (SELECT ID,PROJECT_ID,ORG_LOCATIONS_ID,ORG_ID,ORG_RELATION_ID,LEVEL FROM PROJECT_CLIENTS) AS PC ON PC.ID = SAH.PROJECT_CLIENT_ID AND (PC.ORG_LOCATIONS_ID = '"+locationsId+"' OR  (PC.ORG_LOCATIONS_ID != '"+locationsId+"' AND PC.ORG_ID = '"+orgId+"'))");
				    }else {
				    	query.append(" INNER JOIN (SELECT ID,PROJECT_ID,ORG_LOCATIONS_ID,ORG_ID,ORG_RELATION_ID,LEVEL FROM PROJECT_CLIENTS) AS PC ON PC.ID = SAH.PROJECT_CLIENT_ID AND PC.ORG_LOCATIONS_ID = '"+locationsId+"'");
				    			
				    }    
				    StringBuilder projectsQuery = new StringBuilder();
					if(projectId != null){
						projectsQuery.append(" SAH.PROJECT_ID  = " + projectId + " AND ");
					}else if(projectIds != null && !projectIds.isEmpty()) {
						String projectIdString = StringUtils.join(projectIds, ',');
						projectsQuery.append(" SAH.PROJECT_ID IN( " + projectIdString + " ) AND ");
					}
				    
				    query.append("  WHERE  ")
				         .append(projectsQuery)
				         /*.append(" AND PROJ.ID = SAH.PROJECT_ID")
				    	 .append(" AND SAH.FREQUENCY_START_DATE = '"+sqlStartDate+"' AND  SAH.FREQUENCY_END_DATE = '"+sqlEndDate+"' AND SAH.STATUS != 0   ")
				    	 .append(" AND SAH.PROJECT_CLIENT_ID = PC.ID AND "+statusQuery+"  AND PROJ.INVOICE_FREQUENCY = '"+timesheetFrequency+"'" );*/
					  
					    .append("  PROJ.ID = SAH.PROJECT_ID ");
				         if(StringUtils.isNotEmpty(tableFor) && tableFor.equalsIgnoreCase("approver")) {
				        	 query.append(" AND SAH.FREQUENCY_START_DATE = '"+sqlStartDate+"' AND  SAH.FREQUENCY_END_DATE = '"+sqlEndDate+"' AND PROJ.INVOICE_FREQUENCY = '"+timesheetFrequency+"'" );
				         }
				         query.append(" AND SAH.PROJECT_CLIENT_ID = PC.ID AND ("+statusQuery+") AND SAH.STATUS != '0' ");
				    
				    
				        if(StringUtils.isNotEmpty(filterQuery)) {
					    	query.append(" AND ").append(filterQuery);
					    }
				    query.append(" AND ")
					     .append("   ( CASE ")
				    	 .append( "        WHEN PROJ.PROJECT_APPROVAL_TYPE ='"+Constants.PARALLEL+"' THEN " )
				    	 .append("         ( ").append(parallelApproverCheckQuery).append(")")
				    	 .append( "        WHEN PROJ.PROJECT_APPROVAL_TYPE ='"+Constants.SEQUENTIAL+"' THEN " )
				    	 .append("         ( ").append(sequentiaLCheckForNonNewStatus).append(")")
				    	 .append("        ELSE  '' ")
				    	 .append("      END ")
				    	 .append("    )")
				    	 .append(" )");
				         if(StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("ALL")) {
				        	 query.append("  UNION ALL ")
				        	      .append(newStatusQuery);
				         }
	      } else {
	    	  query.append(newStatusQuery);
	      }
	      query.append(" )Q1 "+searchQuery+" GROUP BY CONCAT(Q1.projectId,Q1.masterCandidateId,Q1.actualStartDate,Q1.actualEndDate) ");
	     //System.out.println(query);
	     
		 Query result = session.createSQLQuery(query.toString())
				      .addScalar("projectMemberId", StandardBasicTypes.LONG)
					  .addScalar("projectId", StandardBasicTypes.LONG)
					  .addScalar("masterCandidateId", StandardBasicTypes.LONG)
			  		  .addScalar("employeeName", StandardBasicTypes.STRING)
			  		  .addScalar("profileImageFileUrl", StandardBasicTypes.STRING)
			  		  .addScalar("employeeEmail", StandardBasicTypes.STRING)
			  		  .addScalar("employeePhoneNo", StandardBasicTypes.STRING)
			  		  .addScalar("designation", StandardBasicTypes.STRING)
			  		  .addScalar("project", StandardBasicTypes.STRING)
			  		  .addScalar("clientId", StandardBasicTypes.LONG)
			  		  
			  		  .addScalar("dashboardInvoiceFrequency", StandardBasicTypes.STRING)
			  		
			  		  .addScalar("actualStartDate", StandardBasicTypes.STRING)
			  		  .addScalar("actualEndDate", StandardBasicTypes.STRING)
			  		  .addScalar("tableFor", StandardBasicTypes.STRING)
			  		  .addScalar("consultantUserId", StandardBasicTypes.LONG)
			  		  .addScalar("workLocationId", StandardBasicTypes.LONG)
			  		  
			  		  .addScalar("submissionStatus", StandardBasicTypes.STRING)
			  		  .addScalar("actionPerformedBy", StandardBasicTypes.STRING)
			  		  .addScalar("rejectedReason", StandardBasicTypes.STRING)
		  		     .setFirstResult(start).setMaxResults(length);
		List<TimesheetApprovalSubmissionDetailsDTO> reportList  = result.setResultTransformer(Transformers.aliasToBean(TimesheetApprovalSubmissionDetailsDTO.class)).list();
		
		
		int totalCount = 0;
		if(totalRows <=0) {
			String  countQuery = "SELECT COUNT(*) AS TOTAL FROM (" + query.toString() +")Q2;" ;
			countlist = session.createSQLQuery(countQuery).list();
			filterCount = countlist.iterator().next().intValue();
			
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		}else {
			totalCount = totalRows;
			filterCount = totalRows;
		}
		response.setRecordsTotal(totalCount);
		if(query.toString() != null && ! query.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		response.setData(reportList);
	    
		long methodEndTime = System.nanoTime();
		//System.out.println("Query Execution Time>>>>>>>>>>>>>>>>>>>"+TimeUnit.SECONDS.convert((qEndTime - qStartTime), TimeUnit.NANOSECONDS));
		//System.out.println("Query Count Execution Time>>>>>>>>>>>>>>>>>>>"+TimeUnit.SECONDS.convert((countEndTime - countstartTime), TimeUnit.NANOSECONDS));
		//System.out.println("Query Method Execution Time>>>>>>>>>>>>>>>>>>>"+TimeUnit.SECONDS.convert((methodEndTime - methodStartTime), TimeUnit.NANOSECONDS));
		return response;
     }
	
	@SuppressWarnings("unchecked")   
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LinkedHashMap<Date, Float> getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long projectId, Long pmMasterCandidateId, Date startDate, Date endDate,DateFormat format,String dateFormat) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		String query = "from  SubmissionApproverHierarchy sah  "
				+ " where  sah.projectId.id = :projectId and sah.masterCandidateId = :masterCandidateId and sah.frequencyStartDate = :startDate and sah.frequencyEndDate = :endDate group by sah.submissionId ";
		
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = session.createQuery(query.toString()).setLong("projectId", projectId).setLong("masterCandidateId", pmMasterCandidateId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		LinkedHashMap<Date, Float> weekTotalsMap = new LinkedHashMap<>();
		if (submissionApproverHierarchyList != null && !submissionApproverHierarchyList.isEmpty()) {
			for (SubmissionApproverHierarchy sah : submissionApproverHierarchyList) {
				if (StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())) {
					List<TimeSheetDayEntries> tdeList = timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(sah.getSubmissionId().getId(), sah.getSubmissionId().getStartDate(), sah.getSubmissionId().getEndDate());
					float weekTotalHours = 0f;
					if (tdeList != null && !tdeList.isEmpty()) {
						for (TimeSheetDayEntries tde : tdeList) {
							float totalHours = 0f;
							if (StringUtils.isNotEmpty(tde.getTotalHours())) {
								totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
							}
							weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
						}
					}
					weekTotalsMap.put(sah.getSubmissionId().getStartDate(), weekTotalHours);
				}
				else {
					weekTotalsMap.put(sah.getSubmissionId().getStartDate(), 0f);
				}
		  }
		}
		else {
			Map<String, String> tabFrequencyDates = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate),dateFormat);
			if (tabFrequencyDates != null && !tabFrequencyDates.isEmpty()) {
				tabFrequencyDates.forEach((k, v) -> {
					try {
						weekTotalsMap.put(format.parse(k.replaceAll("/", "-")), 0f);
					} catch (Exception e) {
					}
				});
			}
		}
		return weekTotalsMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForPendinApproveTimesheetsByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionApproverHierarchy> submissionApproverHierarchyList = null;
		try {
			java.sql.Date sqlStartDate = new java.sql.Date(frequencyStartDate.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(frequencyEndDate.getTime());
			Query submissionApproverHierarchyListQuery = session.createQuery("from SubmissionApproverHierarchy sah left join fetch sah.hierarchyId left join fetch sah.submissionId submission  left join fetch submission.projectMembers pm where pm.id = :projectMemberId and sah.frequencyStartDate = :sqlStartDate and sah.frequencyEndDate = :sqlEndDate");
			submissionApproverHierarchyList = submissionApproverHierarchyListQuery.setLong("projectMemberId", projectMemberId).setParameter("sqlStartDate", sqlStartDate).setParameter("sqlEndDate", sqlEndDate).list();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return submissionApproverHierarchyList;
	}	
}
