package com.bis.sourcelead.clockinout.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.clockinout.dao.ClockInOutConfigDao;
import com.bis.sourcelead.clockinout.dto.ClockInOutAssociateReportSummaryDTO;
import com.bis.sourcelead.clockinout.dto.ClockInOutFiltersDTO;
import com.bis.sourcelead.clockinout.dto.ClockInOutTimerDTO;
import com.bis.sourcelead.clockinout.dto.ClockInOutUserTimeEntryDTO;
import com.bis.sourcelead.clockinout.dto.ProjectDetailsDTO;
import com.bis.sourcelead.clockinout.entity.Breaks;
import com.bis.sourcelead.clockinout.entity.ClockInOut;
import com.bis.sourcelead.clockinout.entity.ClockInOutConfig;
import com.bis.sourcelead.clockinout.util.ClockInOutUtil;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.ProjectClientDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.CommonUtil;

@SuppressWarnings("unchecked")
@Repository
public class ClockInOutConfigDaoImpl implements ClockInOutConfigDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ClockInOutConfigDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	private ClockInOutUtil clockInOutUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	ProjectClientDao projectClientDao;

	@Override
	@Transactional
	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfig clockInOut) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (clockInOut != null) {
				session.saveOrUpdate(clockInOut);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return clockInOut;
		
	}

	@Override
	@Transactional
	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId) {
		
		List<ClockInOutConfig> clkList = new ArrayList<>();
		ClockInOutConfig clk = new ClockInOutConfig();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from ClockInOutConfig clockInOut where clockInOut.orgLocationId.id = :orgLocationId";
			clkList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(clkList != null && clkList.size() > 0) {
			clk = clkList.get(0);
		}
		
		return clk;
	}
	
	@Override
	@Transactional
	public ClockInOutConfig getClockInOutConfigBasedOnId(Long clockInOutId) {
		
		ClockInOutConfig clockInOut = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from ClockInOutConfig clockInOut where clockInOut.id = :clockInOutId";
			 clockInOut = (ClockInOutConfig) session.createQuery(query).setLong("clockInOutId", clockInOutId).uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clockInOut;
	}

	@Override
	@Transactional
	public ClockInOut saveClockInOutTime(ClockInOut clockInOut) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (clockInOut != null) {
				session.saveOrUpdate(clockInOut);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return clockInOut;
	}
	
	@Override
	@Transactional
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId,String customDashboard)
	{

		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> jobsList = null;
		try {
			User user = userDao.getUserById(userId);

			Map<String, Object> params = new HashMap<String, Object>();

			/* String subQueryRole = ""; */
			String from = "from Requirement requirement left join fetch requirement.parentRequirement ";
			String subQuery = "";
			if (!StringUtils.equalsIgnoreCase(userType, "ADMIN")) {

				String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId and rah.status <> '0')   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
				params.put("userId", userId);

				String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
				params.put("managerUserId", userId);

				String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
				params.put("userId", userId);
				
				String subQuery4 = " requirement.primaryRecruiterUserId.id =:userId ";
				params.put("userId", userId);
				
				/*String subQuery5 = "requirement.id IN ( select approver.requirementId from RequirementApproverHierarchy approver where approver.approverUserId.id = :approverUserId )";
				params.put("approverUserId", userId);*/
				
				String subQuery5 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ";
				params.put("userId", user.getId());
				params.put("level", 1);

				String subQuery6 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) )  )";
				params.put("approverUserId", user.getId());
				params.put("userId", user.getId());
				params.put("level1", 1);
				params.put("level2", 2);

				if (subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 !="") {
					subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1 + ") or ("
							+ subQuery2 + ")  or (" + subQuery3 + ")  or (" + subQuery4 + ") or (" + subQuery5 + ") or ("+subQuery6+")) ";
					params.put("orgLocationId", orgLocationId);
				}
			} else {
				
				String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT' and rah.status <> '0') ";

				String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
				params.put("managerUserId", userId);

				if (subQuery1 != "" || subQuery2 != "") {
					subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1 + ") or ("
							+ subQuery2 + ")) ";
					params.put("orgLocationId", orgLocationId);
				}
			}
			
			/*subQuery += " and requirement.walkIn=:isWalkIn";
			params.put("isWalkIn", isWalkIn);
			if(isWalkIn) {
				subQuery += " and requirement.walkInDays like '%" + walkinDay + "%'";
			}*/
			
			String whereCondition = subQuery;
			Query query = session.createQuery(from + " " + whereCondition + " ORDER BY requirement.id DESC");
//			query.setMaxResults(10);
			for (String str : query.getNamedParameters()) {
				if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")) {
					query.setLong(str, (long) params.get(str));
				} else {
					query.setParameter(str, params.get(str));
				}

			}
			if(customDashboard!=null && customDashboard.equalsIgnoreCase(Constants.Dashboard)) {
				jobsList = query.setMaxResults(5).list();
			}else {
				jobsList = query.list();	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  jobsList;
	
	}

	@Override
	@Transactional
	public ResponseList<ClockInOutTimerDTO> getProjectsListByUserId(ProjectsDTO projectsDTO, Long userId, Long orgLocationId, String userRole, Long candidateId,String dateFormat)
	{
		ResponseList<ClockInOutTimerDTO> response = new ResponseList<>();
		List<ClockInOutTimerDTO> projectsTimerList = new CopyOnWriteArrayList<ClockInOutTimerDTO>();
		List<Projects> projectsList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String approverQuery = "";
			String projectModeQuery = "";
			String query = null;
			Query queryObj = null;
		
			if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
				approverQuery = " and (pc.id IN (select pah.projectClientsId.id from ProjectApproverHierarchy pah where pah.approverUserId = "+userId+" and pah.status ='1') OR "
						+ " pc.id IN (select pex.projectClientsId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId = "+userId+" and pex.status = '1') OR pc.createdBy.id = "+userId+" "
						+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.id = "+candidateId+" and pm.status = '1') "
								+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.parentCandidateId.id = "+candidateId+" and pm.status = '1')) ";
			}
			
			
			projectModeQuery = " and (ps.orgLocationsId.org.id = :orgId  OR ps.projectMode = 'Finished') and (ps.projectStatus = 'INPROGRESS' OR ps.projectStatus = 'ONHOLD') ";
			
			
					String projectAccessQuery = "";
					
					if(userRole.equalsIgnoreCase("ROLE_ADMIN") && (StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES))){
								 projectAccessQuery =  " and ("
					                       + "  ps.id IN(select distinct subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.approverUserId ='"+userId+"' and subpah.status !='0')"
					                       +" OR "
					                       + "  ps.id IN( select subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.status = '1' and subpah.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationId+"'))"
				                           +" OR "
				                           + "  ps.id IN(select distinct epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.approverUserId ='"+userId+"' and epx.status !='0')"
					                       +" OR "
					                       + "  ps.id IN( select epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.status = '1' and epx.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationId+"'))"
					                       +" )";
					}else if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
						 projectAccessQuery =  " and ("
			                       + "  ps.id IN( select subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.status = '1' and subpah.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationId+"'))"
		                           +" OR "
			                       + "  ps.id IN( select epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.status = '1' and epx.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationId+"'))"
			                       +" )";
					}
					
					
					query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.org.id = :orgId and pc.status = 1 "+approverQuery+" ) "+projectModeQuery+"  ";
			        query = query+projectAccessQuery;
			
			         queryObj = session.createQuery(query);
			
			queryObj.setLong("orgId", projectsDTO.getOrgId()).list();
			
			projectsList = queryObj.list();
			if(projectsList != null && !projectsList.isEmpty()) {
				for(Projects projects : projectsList) {
					ClockInOutTimerDTO timerDTO = new ClockInOutTimerDTO();
					timerDTO.setId(BigInteger.valueOf(projects.getId()));
					timerDTO.setProjectName(projects.getProjectName());
					projectsTimerList.add(timerDTO);
				}
			}
			
			response.setData(projectsTimerList);
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return response;
	}

	@Transactional
	@Override
	public List<ProjectTasks> getProjectsTasksByProjectId(Long projectId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.createQuery("from ProjectTasks pt where pt.projectId.id = :projectId and pt.status = '1' and pt.taskType.name <> 'Done'").setLong("projectId", projectId).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public List<Tasks> getGeneralTasks(Long orgLocationId, Long userId) {
		
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.createQuery("from Tasks tasks where tasks.assignedUserId =:userId and ((tasks.ownerOrglocationId =:orgLocationId) OR (tasks.assignedOrglocationId =:orgLocationId)) and  tasks.taskStatus = 'Open' ORDER BY tasks.createdDate DESC")
								   .setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public ClockInOut getExistedClockTime(Long orgLocationId, Long userId) {
		
		List<ClockInOut> clockList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			clockList = session.createQuery("from ClockInOut clk where clk.orgLocations.id = :orgLocationId and clk.userId.id = :userId and clk.clockOutTime is NULL")
			.setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(clockList != null && clockList.size() > 0) {
			return clockList.get(0);
		}else {
			return null;
		}
	}

	@Transactional
	@Override
	public ClockInOut getClockInOutById(Long clockInOutId) {
		
		List<ClockInOut> clockList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			clockList = session.createQuery("from ClockInOut clk where clk.id = :clockInOutId")
			.setLong("clockInOutId", clockInOutId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(clockList != null && clockList.size() > 0) {
			return clockList.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public Breaks saveBreakTime(Breaks breaks) {
	Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (breaks != null) {
				session.saveOrUpdate(breaks);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return breaks;
	}

	@Override
	@Transactional
	public Breaks getBreakById(Long breakId) {
		List<Breaks> breaksList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			breaksList = session.createQuery("from Breaks brk where brk.id = :breakId")
			.setLong("breakId", breakId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(breaksList != null && breaksList.size() > 0) {
			return breaksList.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public Breaks getExistedBreakTime(Long clockInOutId) {
		List<Breaks> breaksList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			breaksList = session.createQuery("from Breaks brk where brk.clockInOutId = :clockInOutId and brk.breakEndTime is NULL")
			.setLong("clockInOutId", clockInOutId).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(breaksList != null && breaksList.size() > 0) {
			return breaksList.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public ResponseList<ClockInOutUserTimeEntryDTO> getUserTimeEntries(Long orgLocationId, Long userId,Date[] dates) {
		
		ResponseList<ClockInOutUserTimeEntryDTO> resList = new ResponseList<>();
		List<ClockInOutUserTimeEntryDTO> clockInOutList = null;
		
		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT clk.CLOCK_IN_TIME as clockInTime, ") 
			  .append("clk.ID as id, ")
			  .append("clk.CLOCK_OUT_TIME as clockOutTime, ")
			  .append("clk.CLOCK_IN_DATE as clockInDate, ")
			  .append("clk.CLOCK_OUT_DATE as clockOutDate, ")
			  .append("clk.TOTAL_TIME as totalTime, ")
			  .append("clk.GEO_LOCATION_DATA as geoLocationData, ")
			  .append("task.TASK_NAME as projectTaskName, ")
			  .append("DATE_FORMAT(clk.CLOCK_IN_DATE, '%Y-%m') as yearMonth, ")
			  .append("clk.COMMENTS as comments ")
			  .append("FROM CLOCK_INOUT clk ")
			  .append("LEFT JOIN PROJECT_TASKS task ON clk.PROJECTS_TASK_ID = task.ID ")
			  .append("where clk.USER_ID = :userId ")
			  .append("AND clk.ORG_LOCATION_ID = :orgLocationId ")
			  .append("AND clk.CLOCK_IN_DATE >= :firstDate ")
			  .append("AND clk.CLOCK_IN_DATE <= :lastDate ")
			  .append("AND clk.TOTAL_TIME is not null ");
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setLong("userId", userId);
			queryObj.setDate("firstDate", dates[0]);
			queryObj.setDate("lastDate", dates[1]);
			queryObj.setResultTransformer(Transformers.aliasToBean(ClockInOutUserTimeEntryDTO.class));
			clockInOutList = queryObj.list();
			resList.setData(clockInOutList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resList;
		
	}

	@Override
	@Transactional
	public List<ClockInOutUserTimeEntryDTO> getTimeReports(Date[] dates ,OrgLocations loginOrgLocation, User user) {
		
		List<ClockInOutUserTimeEntryDTO> clockInOutList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT clk.TOTAL_TIME as totalTime, ") 
			  .append("clk.ID as id ")
			  .append("FROM CLOCK_INOUT clk ")
			  .append("where clk.USER_ID = :userId ")
			  .append("AND clk.ORG_LOCATION_ID = :orgLocationId ")
			  .append("AND clk.CLOCK_IN_DATE >= :firstDate ")
			  .append("AND clk.CLOCK_IN_DATE <= :lastDate ")
			  .append("AND clk.TOTAL_TIME IS NOT NULL ");
			
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("orgLocationId", loginOrgLocation.getId());
			queryObj.setLong("userId", user.getId());
			queryObj.setDate("firstDate", dates[0]);
			queryObj.setDate("lastDate", dates[1]);
			queryObj.setResultTransformer(Transformers.aliasToBean(ClockInOutUserTimeEntryDTO.class));
			clockInOutList = queryObj.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clockInOutList;
	}

	@Override
	@Transactional
	public List<ClockInOut> getWorkingTimeByClockId(OrgLocations loc, User user) {
		
		List<ClockInOut> clkList = null;
		
		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from ClockInOut clk where clk.orgLocations.id =:orgLocationId and clk.userId.id =:user and clk.clockOutDate is NULL";
			clkList = session.createQuery(query).setLong("orgLocationId", loc.getId()).setLong("user", user.getId()).list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clkList;
	}

	@Override
	@Transactional
	public List<Breaks> getBreakListByClockInId(Long clockInId) {
		
		List<Breaks> breakList = null;
		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Breaks brk where brk.clockInOutId =:clockInId";
			breakList = session.createQuery(query).setLong("clockInId", clockInId).list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakList;
	}

	@Override
	@Transactional
	public ClockInOut getPreviousClockInData(OrgLocations loginOrgLocation, User user) {
		List<ClockInOut> clockList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			clockList = session.createQuery("from ClockInOut clk where clk.userId.id = :userId and clk.orgLocations.id =:orgLocationId and clk.clockOutDate is NOT NULL ORDER BY clk.id DESC ")
			.setLong("userId", user.getId())
			.setLong("orgLocationId", loginOrgLocation.getId())
			.setMaxResults(1).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(clockList != null && clockList.size() > 0) {
			return clockList.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public ResponseList<ClockInOutUserTimeEntryDTO> getEmployeeTimeEntriesByProjectIdTaskId(Long locationId, Long userId, Date[] dates, Long projectId, Long projectTaskId)
	{
		ResponseList<ClockInOutUserTimeEntryDTO> resList = new ResponseList<>();
		List<ClockInOutUserTimeEntryDTO> clockInOutList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT clk.CLOCK_IN_TIME as clockInTime, ") 
			  .append("clk.ID as id, ")
			  .append("clk.CLOCK_OUT_TIME as clockOutTime, ")
			  .append("clk.CLOCK_IN_DATE as clockInDate, ")
			  .append("clk.CLOCK_OUT_DATE as clockOutDate, ")
			  .append("clk.TOTAL_TIME as totalTime, ")
			  .append("clk.GEO_LOCATION_DATA as geoLocationData, ")
			  .append("task.TASK_NAME as projectTaskName, ")
			  .append("DATE_FORMAT(clk.CLOCK_IN_DATE, '%Y-%m') as yearMonth, ")
			  .append("clk.COMMENTS as comments ")
			  .append("FROM CLOCK_INOUT clk ")
			  .append("LEFT JOIN PROJECT_TASKS task ON clk.PROJECTS_TASK_ID = task.ID ")
			  .append("where clk.USER_ID = :userId ")
			  .append("AND clk.ORG_LOCATION_ID = :orgLocationId ")
			  .append("AND clk.PROJECT_ID = :projectId ")
			  .append("AND clk.PROJECTS_TASK_ID = :projectTaskId ")
			  .append("AND clk.CLOCK_OUT_DATE >= :clockOutDate ")
			  .append("AND clk.TOTAL_TIME is not null ");
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("orgLocationId", locationId);
			queryObj.setLong("projectId", projectId);
			queryObj.setLong("projectTaskId", projectTaskId);
			queryObj.setLong("userId", userId);
			queryObj.setDate("clockOutDate", dates[0]);//  
			queryObj.setResultTransformer(Transformers.aliasToBean(ClockInOutUserTimeEntryDTO.class));
			clockInOutList = queryObj.list();
			resList.setData(clockInOutList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resList;
	}

	@Override
	@Transactional
	public List<ClockInOutAssociateReportSummaryDTO> getAssociateClockInOutReportSummary(ClockInOutFiltersDTO clockInOutFiltersDTO, OrgLocations orgLocation, Date sendDate) {
		
		List<ClockInOutAssociateReportSummaryDTO> associateReportSummaryList = new ArrayList<ClockInOutAssociateReportSummaryDTO>();

		List<Long> deptList = new ArrayList<>();
		List<Long> userRolesList = new ArrayList<>();
		List<Long> jobsList = new ArrayList<>();
		List<Long> projectList = new ArrayList<>();
		List<Long> taskList = new ArrayList<>();
		List<Long> generalTaskList = new ArrayList<>();
		List<Long> associateList = new ArrayList<>();
		List<Long> teamsList = new ArrayList<>();
		java.sql.Date dateRangeList[] = new java.sql.Date[2];
		java.sql.Date sqlCurrDate = null;
		
		String subQuery = "";
		
		try {
			Session session = this.sessionFactory.getCurrentSession();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT clk.ID as id,")
			.append("clk.CLOCK_IN_TIME as clockInTime,")
			.append("clk.CLOCK_OUT_TIME as clockOutTime,")
			.append("clk.CLOCK_IN_DATE as clockInDate,")
			.append("DATE_FORMAT(clk.CLOCK_IN_DATE, \"%Y-%m\") as yearDate,")
			.append("clk.USER_ID as userId,")
			.append("usr.FIRST_NAME as firstName,")
			.append("usr.LAST_NAME as lastName,")
			.append("uRole.USER_TYPE as roleType,")
			.append("uRole.ROLE_ID as roleId,")
			.append("emp.DEPARTMENT_ID as depId,")
			.append("clk.CLOCK_OUT_DATE as clockOutDate,")
			.append("clk.TOTAL_TIME as totalTime,")
			//.append("task.TASK_NAME as projectTaskName,")
			.append("clk.COMMENTS as comments ")
			.append("FROM CLOCK_INOUT clk LEFT JOIN USER usr ON clk.USER_ID = usr.ID ")
			.append("LEFT JOIN USER_ROLE uRole ON clk.USER_ID = uRole.USER_ID ")
			.append("LEFT JOIN EMPLOYEE emp ON clk.USER_ID = emp.USER_ID ")
			.append("LEFT JOIN TEAM_MEMBERS tm ON clk.USER_ID = tm.MEMBER_USER_ID ")
			.append("where clk.ORG_LOCATION_ID = :orgLocationID ")
			.append("AND uRole.ORG_LOCATIONS_ID = :orgLocationID ")
			.append("AND emp.ORG_LOCATIONS_ID = :orgLocationID ")
			.append("AND clk.CLOCK_OUT_DATE is NOT NULL");
			
			if(clockInOutFiltersDTO.getOrgDepartments() != null && !clockInOutFiltersDTO.getOrgDepartments().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getOrgDepartments())) {
				String[] depts = clockInOutFiltersDTO.getOrgDepartments().split(",");
				for(int i=0; i < depts.length; i++) {
					deptList.add(Long.parseLong(depts[i]));
				}
				subQuery = subQuery + " AND emp.DEPARTMENT_ID IN (:deptList)";
			}
			
			if(clockInOutFiltersDTO.getUserRole() != null && !clockInOutFiltersDTO.getUserRole().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getUserRole())) {
				String[] userRoles = clockInOutFiltersDTO.getUserRole().split(",");
				for(int i=0; i < userRoles.length; i++) {
					userRolesList.add(Long.parseLong(userRoles[i]));
				}
				subQuery = subQuery + " AND uRole.ROLE_ID IN (:userRolesList)";
			}
			
			if(clockInOutFiltersDTO.getJobsList() != null && !clockInOutFiltersDTO.getJobsList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getJobsList())) {
				String[] jobs = clockInOutFiltersDTO.getJobsList().split(",");
				for(int i=0; i < jobs.length; i++) {
					jobsList.add(Long.parseLong(jobs[i]));
				}
				subQuery = subQuery + " AND clk.JOBS_TASK_ID IN (:jobsList)";
			}
			if(clockInOutFiltersDTO.getProjectList() != null && !clockInOutFiltersDTO.getProjectList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getProjectList())) {
				String[] projects = clockInOutFiltersDTO.getProjectList().split(",");
				for(int i=0; i < projects.length; i++) {
					projectList.add(Long.parseLong(projects[i]));
				}
				subQuery = subQuery + " AND clk.PROJECT_ID IN (:projectList)";
			}
			if(clockInOutFiltersDTO.getTasksList() != null && !clockInOutFiltersDTO.getTasksList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getTasksList())) {
				String[] tasks = clockInOutFiltersDTO.getTasksList().split(",");
				for(int i=0; i < tasks.length; i++) {
					taskList.add(Long.parseLong(tasks[i]));
				}
				subQuery = subQuery + " AND clk.PROJECTS_TASK_ID IN (:taskList)";
			}
			if(clockInOutFiltersDTO.getGeneralTasksList() != null && !clockInOutFiltersDTO.getGeneralTasksList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getGeneralTasksList())) {
				String[] generalTask = clockInOutFiltersDTO.getGeneralTasksList().split(",");
				for(int i=0; i < generalTask.length; i++) {
					generalTaskList.add(Long.parseLong(generalTask[i]));
				}
				subQuery = subQuery + " AND clk.GENERAL_TASK_ID IN (:generalTaskList)";
			}
			if(clockInOutFiltersDTO.getAssociateList() != null && !clockInOutFiltersDTO.getAssociateList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getAssociateList())) {
				String[] associates = clockInOutFiltersDTO.getAssociateList().split(",");
				for(int i=0; i < associates.length; i++) {
					associateList.add(Long.parseLong(associates[i]));
				}
				subQuery = subQuery + " AND clk.USER_ID IN (:associateList)";
			}
			if(clockInOutFiltersDTO.getTeamsList() != null && !clockInOutFiltersDTO.getTeamsList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getTeamsList())) {
				String[] teams = clockInOutFiltersDTO.getTeamsList().split(",");
				for(int i=0; i < teams.length; i++) {
					teamsList.add(Long.parseLong(teams[i]));
				}
 				subQuery = subQuery + " AND tm.TEAM_ID IN (:teamsList)";
			}
			
			if(clockInOutFiltersDTO.getCurrentDate() != null && !clockInOutFiltersDTO.getCurrentDate().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getCurrentDate())) {
				String[] currDates = clockInOutFiltersDTO.getCurrentDate().split("To");
				for(int i=0; i < currDates.length; i++) {
			        SimpleDateFormat sdf1 = new SimpleDateFormat(orgLocation.getDateFormat());
			        java.util.Date date = sdf1.parse(currDates[i]);
			        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
					dateRangeList[i] = sqlStartDate;
				}
 				subQuery = subQuery + " AND clk.CLOCK_IN_DATE BETWEEN (:dateRangeStart) AND (:dateRangeEnd)";
			}
			
//			if(sendDate != null ) {
//			        sqlCurrDate = new java.sql.Date(sendDate.getTime());
//			        subQuery = subQuery + " AND clk.CLOCK_IN_DATE =:dateCurrent";
//			}
 				
			Query queryObj = session.createSQLQuery(sb.toString()+subQuery);
			queryObj.setLong("orgLocationID", orgLocation.getId());
			
			if(clockInOutFiltersDTO.getOrgDepartments() != null && !clockInOutFiltersDTO.getOrgDepartments().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getOrgDepartments()))
				queryObj.setParameterList("deptList", deptList);
			if(clockInOutFiltersDTO.getUserRole() != null && !clockInOutFiltersDTO.getUserRole().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getUserRole()))
				queryObj.setParameterList("userRolesList", userRolesList);
			if(clockInOutFiltersDTO.getJobsList() != null && !clockInOutFiltersDTO.getJobsList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getJobsList()))
				queryObj.setParameterList("jobsList", jobsList);
			if(clockInOutFiltersDTO.getProjectList() != null && !clockInOutFiltersDTO.getProjectList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getProjectList()))
				queryObj.setParameterList("projectList", projectList);
			if(clockInOutFiltersDTO.getTasksList() != null && !clockInOutFiltersDTO.getTasksList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getTasksList()))
				queryObj.setParameterList("taskList", taskList);
			if(clockInOutFiltersDTO.getGeneralTasksList() != null && !clockInOutFiltersDTO.getGeneralTasksList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getGeneralTasksList()))
				queryObj.setParameterList("generalTaskList", generalTaskList);
			if(clockInOutFiltersDTO.getAssociateList() != null && !clockInOutFiltersDTO.getAssociateList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getAssociateList()))
				queryObj.setParameterList("associateList", associateList);
			if(clockInOutFiltersDTO.getTeamsList() != null && !clockInOutFiltersDTO.getTeamsList().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getTeamsList()))
				queryObj.setParameterList("teamsList", teamsList);
			if(clockInOutFiltersDTO.getCurrentDate() != null && !clockInOutFiltersDTO.getCurrentDate().isEmpty() && !"null".equalsIgnoreCase(clockInOutFiltersDTO.getCurrentDate())) {
				queryObj.setParameter("dateRangeStart", dateRangeList[0]);
				if(dateRangeList[1] !=null)
					queryObj.setParameter("dateRangeEnd", dateRangeList[1]);
				else
					queryObj.setParameter("dateRangeEnd", dateRangeList[0]);
			}
			
//			if(sendDate != null ) {
//		        queryObj.setParameter("dateCurrent", sqlCurrDate);
//			}
				
			queryObj.setResultTransformer(Transformers.aliasToBean(ClockInOutAssociateReportSummaryDTO.class));
			associateReportSummaryList = queryObj.list();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return associateReportSummaryList;
	}
	
	@Override
	@Transactional
	public List<ClockInOutUserTimeEntryDTO> getListOfClockDataBasedOnDate(String selectedDate, OrgLocations loginOrgLocation, Long userId) {
		List<ClockInOutUserTimeEntryDTO> clockList = null;
		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			Date date = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), selectedDate);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT clk.CLOCK_IN_TIME as clockInTime,")
			.append("clk.CLOCK_IN_DATE as clockInDate,")
			.append("proj.PROJECT_NAME as projectName,")
			.append("task.TASK_NAME as projectTaskName,")
			.append("NULL as jobTitle,")
			.append("NULL as taskName,")
			.append("clk.TOTAL_TIME as totalTime,")
			.append("clk.COMMENTS as comments,")
			.append("clk.CLOCK_OUT_TIME as clockOutTime, ")
			.append("clk.CLOCK_OUT_DATE as clockOutDate ")
			.append("FROM CLOCK_INOUT clk INNER JOIN PROJECT_TASKS task ON clk.PROJECTS_TASK_ID = task.ID ")
			.append("INNER JOIN PROJECTS proj ON clk.PROJECT_ID = proj.ID ")
			.append(" and clk.USER_ID =:userId and clk.ORG_LOCATION_ID =:orgLocationId and clk.CLOCK_IN_DATE =:sqlDate and clk.CLOCK_OUT_DATE is NOT NULL ")
			
			.append(" UNION ALL ")
			
			.append("SELECT clk.CLOCK_IN_TIME as clockInTime,")
			.append("clk.CLOCK_IN_DATE as clockInDate,")
			.append("NULL as projectName,")
			.append("NULL as projectTaskName,")
			.append("req.JOB_TITLE as jobTitle,")
			.append("NULL as taskName,")
			.append("clk.TOTAL_TIME as totalTime,")
			.append("clk.COMMENTS as comments,")
			.append("clk.CLOCK_OUT_TIME as clockOutTime, ")
			.append("clk.CLOCK_OUT_DATE as clockOutDate ")
			.append("FROM CLOCK_INOUT clk ")
			.append("INNER JOIN REQUIREMENT req ON  clk.JOBS_TASK_ID = req.ID AND clk.JOBS_TASK_ID IS NOT NULL ")
			.append(" and clk.USER_ID =:userId and clk.ORG_LOCATION_ID =:orgLocationId and clk.CLOCK_IN_DATE =:sqlDate and clk.CLOCK_OUT_DATE is NOT NULL ")
			
			
			.append(" UNION ALL ")
			
			.append("SELECT clk.CLOCK_IN_TIME as clockInTime,")
			.append("clk.CLOCK_IN_DATE as clockInDate,")
			.append("NULL as projectName,")
			.append("NULL as projectTaskName,")
			.append("NULL as jobTitle,")
			.append("gt.SUBJECT as taskName,")
			.append("clk.TOTAL_TIME as totalTime,")
			.append("clk.COMMENTS as comments,")
			.append("clk.CLOCK_OUT_TIME as clockOutTime, ")
			.append("clk.CLOCK_OUT_DATE as clockOutDate ")
			.append("FROM CLOCK_INOUT clk ")
			.append("INNER JOIN TASKS gt ON clk.GENERAL_TASK_ID = gt.ID AND clk.GENERAL_TASK_ID IS NOT NULL ")
			.append(" and clk.USER_ID =:userId and clk.ORG_LOCATION_ID =:orgLocationId and clk.CLOCK_IN_DATE =:sqlDate and clk.CLOCK_OUT_DATE is NOT NULL ")
			
			.append(" UNION ALL ")
			
			.append("SELECT clk.CLOCK_IN_TIME as clockInTime,")
			.append("clk.CLOCK_IN_DATE as clockInDate,")
			.append("NULL as projectName,")
			.append("NULL as projectTaskName,")
			.append("NULL as jobTitle,")
			.append("NULL as taskName,")
			.append("clk.TOTAL_TIME as totalTime,")
			.append("clk.COMMENTS as comments,")
			.append("clk.CLOCK_OUT_TIME as clockOutTime, ")
			.append("clk.CLOCK_OUT_DATE as clockOutDate ")
			.append("FROM CLOCK_INOUT clk ")
			.append("WHERE ")
			.append("clk.PROJECT_ID IS NULL AND ")
			.append("clk.PROJECTS_TASK_ID IS NULL AND ")
			.append("clk.JOBS_TASK_ID IS NULL AND ")
			.append("clk.GENERAL_TASK_ID IS NULL AND ")
			.append("clk.USER_ID =:userId and clk.ORG_LOCATION_ID =:orgLocationId and clk.CLOCK_IN_DATE =:sqlDate and clk.CLOCK_OUT_DATE is NOT NULL ");
			
			clockList = session.createSQLQuery(sb.toString())
			.setLong("userId", userId)
			.setLong("orgLocationId", loginOrgLocation.getId())
			.setDate("sqlDate", sqlDate)
			.setResultTransformer(Transformers.aliasToBean(ClockInOutUserTimeEntryDTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clockList;
	}

	@Override
	@Transactional
	public List<ClockInOutTimerDTO> getProjectsTasksListForClockInTimerByUserId(Long userId, Long projectId, Long orgLocationId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String projectTaskQuery = " SELECT ptasks.TASK_NAME as taskName, " + 
				   " ptasks.ID as id " + 
			       " FROM PROJECT_ASSIGN_TASK assignTask,PROJECT_TASKS ptasks,PROJECTS proj,PROJECT_MEMBERS projMem,CANDIDATE can, PROJECT_CLIENTS pc, USER user " + 
			       " where assignTask.PROJECT_TASK_ID = ptasks.ID AND ptasks.PROJECT_ID = proj.ID  " + 
			       " AND projMem.ID = assignTask.PROJECT_MEMBER_ID " + 
			       " AND can.ID = projMem.CANDIDATE_ID " + 
			       " AND can.CONSULTANT_USER_ID = user.ID " + 
			       " AND ptasks.PROJECT_ID = :projectId "+
			       " AND can.ORG_LOCATION_ID = pc.ORG_LOCATIONS_ID "+
			       " AND pc.ORG_LOCATIONS_ID = :orgLocationId AND user.ID = :userId group by proj.ID" +
		
				    " UNION ALL "+
				    
 				  " SELECT ptasks.TASK_NAME as taskName, " + 
				  " ptasks.ID as id " + 
				  " FROM PROJECT_TASKS ptasks,LOOKUP_DATA lData " + 
				  " where lData.ID = ptasks.TASK_TYPE   " + 
				  " AND ptasks.PROJECT_ID = :projectId " + 
				  " AND lData.NAME = 'General' ";
			       
			        Query projectQueryObj = session.createSQLQuery(projectTaskQuery);
			        projectQueryObj.setLong("projectId", projectId);
			        projectQueryObj.setLong("orgLocationId", orgLocationId);
			        projectQueryObj.setLong("userId", userId);
			        projectQueryObj.setResultTransformer(Transformers.aliasToBean(ClockInOutTimerDTO.class));
			        List<ClockInOutTimerDTO> projectsList = projectQueryObj.list();
					return projectsList;
	}

	@Override
	@Transactional
	public ResponseList<ProjectDetailsDTO> getAssignedProjectsListByUserId(ProjectsDTO projectsDTO, Long userId,
			Long orgLocationId, String userRole, Long candidateId) {
		ResponseList<ProjectDetailsDTO> response = new ResponseList<>();
		List<Projects> projectsList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String approverQuery = "";
			String projectModeQuery = "";
			String query = null;
			Query queryObj = null;
		
			if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
				approverQuery = " and (pc.id IN (select pah.projectClientsId.id from ProjectApproverHierarchy pah where pah.approverUserId = "+userId+" and pah.status ='1') OR "
						+ " pc.id IN (select pex.projectClientsId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId = "+userId+" and pex.status = '1') OR pc.createdBy.id = "+userId+" "
						+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.id = "+candidateId+" and pm.status = '1') "
								+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.parentCandidateId.id = "+candidateId+" and pm.status = '1')) ";
			}
			
			
			projectModeQuery = " and (ps.orgLocationsId.org.id = :orgId  OR ps.projectMode = 'Finished') and (ps.projectStatus = 'INPROGRESS' OR ps.projectStatus = 'ONHOLD') ";
			
			
					String projectAccessQuery = "";
					
					if(userRole.equalsIgnoreCase("ROLE_ADMIN") && (StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES))){
								 projectAccessQuery =  " and ("
					                       + "  ps.id IN(select distinct subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.approverUserId ='"+userId+"' and subpah.status !='0')"
					                       +" OR "
					                       + "  ps.id IN( select subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.status = '1' and subpah.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationId+"'))"
				                           +" OR "
				                           + "  ps.id IN(select distinct epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.approverUserId ='"+userId+"' and epx.status !='0')"
					                       +" OR "
					                       + "  ps.id IN( select epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.status = '1' and epx.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationId+"'))"
					                       +" )";
					}else if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
						 projectAccessQuery =  " and ("
			                       + "  ps.id IN( select subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.status = '1' and subpah.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationId+"'))"
		                           +" OR "
			                       + "  ps.id IN( select epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.status = '1' and epx.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationId+"'))"
			                       +" )";
					}
					
					
					query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.org.id = :orgId and pc.status = 1 "+approverQuery+" ) "+projectModeQuery+"  ";
			        query = query+projectAccessQuery;
			
			         queryObj = session.createQuery(query);
			
			queryObj.setLong("orgId", projectsDTO.getOrgId()).list();
			
			projectsList = queryObj.list();
			
			if(projectsList != null && !projectsList.isEmpty()) {
				response.setData(getProjectClientAndManagerDetails(projectsList,orgLocationId,userRole,projectsDTO));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return response;
	}
	
	
	private List<ProjectDetailsDTO> getProjectClientAndManagerDetails(List<Projects> projectsList,Long orgLocationsId,String userRole,ProjectsDTO projectDTO) throws Exception {
		OrgLocations orglo = orgLocationsDao.getOrgLocations(orgLocationsId);
		List<ProjectDetailsDTO> projectsDTOList = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");  
		for (Projects projects :projectsList ){
			ProjectClients projectClients = null;
				if(StringUtils.isNotBlank(projects.getIsProjectShared()) && projects.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
					 projects = projectsService.getProjectById(projects.getId());
					 if(projects.getSharedLocationId().getId().equals(orglo.getId())) {
						 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getSharedLocationId().getId());
					 }else{
						 if(projects.getOrgLocationsId().getId().equals(orglo.getId())) {
							 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getOrgLocationsId().getId());
						 }else {
							 ProjectClients clientProjectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orglo.getId());
							 if(clientProjectClients == null) {
								 clientProjectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getSharedLocationId().getId());
							 }
							 if(clientProjectClients != null && clientProjectClients.getOrgRelationId() != null && !clientProjectClients.getOrgRelationId().getResponderLocationId().getId().equals(orglo.getId())) {
								 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientProjectClients.getOrgRelationId().getLocationId().getId());
							 }else {
								 if(clientProjectClients.getOrgRelationId() != null) {
									 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientProjectClients.getOrgRelationId().getResponderLocationId().getId());
								 }else {
									 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocationsId);
								 }
							 }
						 }
						 
					 }
					
				}else {
					 projects = projectsService.getProjectById(projects.getId());
					 
					 if(projects.getOrgLocationsId().getId().equals(orglo.getId())) {
						 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocationsId);
					 }else {
						 if(projects.getOrgLocationsId().getOrg().getId().equals(orglo.getOrg().getId())) {
							 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getOrgLocationsId().getId());
						 }else {
							 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocationsId);
							 if(projectClients != null && projectClients.getOrgRelationId() != null) {
								 OrgRelations orgRel = projectClients.getOrgRelationId();
								 OrgLocations clientOrgLocation = null;
								 if(!orgRel.getResponderLocationId().getId().equals(orglo.getId())) {
									 clientOrgLocation = orgRel.getLocationId();
								 }else {
									 clientOrgLocation = orgRel.getResponderLocationId();
								 } 
								 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientOrgLocation.getId());
							 }else {
								 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgId(projects.getId(), projectDTO.getOrgId());
							 }
						 }
						
					 }
					
					
				}
			    	 if(projectClients != null){
			    		 
			    		 ProjectDetailsDTO projectsDTO = new ProjectDetailsDTO();
			    			    
			    			 projectsDTO.setProjectStartDate(formatter.format(projects.getStartDate()));
							    if(projects.getEndDate() != null){
							    	projectsDTO.setProjectEndDate(formatter.format(projects.getEndDate()));
							    }else {
							    	projectsDTO.setProjectEndDate("");
							    }
			    			 
			    			 if(projectClients.getOrgRelationId() != null){
					    		 
					    			if(null != projectClients.getOrgRelationId().getLocationId() && projectClients.getOrgRelationId().getLocationId().getId().longValue() == orgLocationsId.longValue()){
					    				projectsDTO.setClient(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
									}else if(null != projectClients.getOrgRelationId().getResponderLocationId() && projectClients.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocationsId.longValue()){
										projectsDTO.setClient(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
									}else {
										projectsDTO.setClient("");
									}
									
					    	  }else {
					    		  projectsDTO.setClient("");
					    	  }
					    	 
				    	    Set<ProjectApproverHierarchy> projectApproverHierarchies = projectClients.getProjectApproverHierarchyList();
						    for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
						    	if(approverHierarchy.getLevel() == 1 && approverHierarchy.getStatus().equalsIgnoreCase("1")){
						    		StringBuilder manager1 = new StringBuilder();
						    		if(approverHierarchy.getApproverUserId().getLastName() != null){
						    			projectsDTO.setManagerLevel1(manager1.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());	
						    		}else{
						    			projectsDTO.setManagerLevel1(manager1.append(approverHierarchy.getApproverUserId().getFirstName()).toString());
						    		}
						    		
						    		if(StringUtils.isNotBlank(approverHierarchy.getApproverUserId().getFileUrl())) {
						    			projectsDTO.setManagerLevel1ImgUrl(approverHierarchy.getApproverUserId().getFileUrl());
						    		}else {
						    			projectsDTO.setManagerLevel1ImgUrl("");
						    		}
						    		
						    	}
						    	
						    	if(approverHierarchy.getLevel() == 2 && approverHierarchy.getStatus().equalsIgnoreCase("1")){
						    		StringBuilder manager2 = new StringBuilder();
						    		if(approverHierarchy.getApproverUserId().getLastName() != null){
						    			projectsDTO.setManagerLevel2(manager2.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());	
						    		}else{
						    			projectsDTO.setManagerLevel2(manager2.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").toString());
						    		}
						    		
						    		if(StringUtils.isNotBlank(approverHierarchy.getApproverUserId().getFileUrl())) {
						    			projectsDTO.setManagerLevel2ImgUrl(approverHierarchy.getApproverUserId().getFileUrl());
						    		}else {
						    			projectsDTO.setManagerLevel2ImgUrl("");
						    		}
						    		
						    	}else {
						    		projectsDTO.setManagerLevel2("");
						    		projectsDTO.setManagerLevel2ImgUrl("");
						    	}
						    }
						    projectsDTO.setProjectName(projects.getProjectName());
						    projectsDTO.setProjectTimeSheetFrequency(projects.getTimesheetFrequency());
						    projectsDTO.setProjectStatus(projects.getProjectStatus().getValue().toString());
						    projectsDTOList.add(projectsDTO);
			    		 
			    	 }
			    	
		}
		
		return projectsDTOList;
	
	}

}
