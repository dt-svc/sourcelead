package com.bis.sourcelead.clockInOutDaoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.bis.sourcelead.clockInOutDTO.ClockInOutAssociateReportSummaryDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutFiltersDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutUserTimeEntryDTO;
import com.bis.sourcelead.clockInOutDao.ClockInOutConfigDao;
import com.bis.sourcelead.clockInOutEntity.Breaks;
import com.bis.sourcelead.clockInOutEntity.ClockInOut;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;
import com.bis.sourcelead.clockInOutUtil.ClockInOutUtil;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.OrgLocationService;
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
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId)
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
			jobsList = query.list();	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  jobsList;
	
	}

	@Override
	@Transactional
	public ResponseList<Projects> getProjectsListByUserId(ProjectsDTO projectsDTO, Long userId, String clients, String values, Long orgLocationId, String userRole, Long candidateId)
	{
		ResponseList<Projects> response = new ResponseList<>();
		List<Projects> projectsList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String approverQuery = "";
			String projectModeQuery = "";
			String query = null;
			List<Long> countlist = null;
			Integer filterCount = 0;
			Query queryObj = null;
	
			
			StringBuilder query1 = new StringBuilder("");
			if(projectsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(projectsDTO.getSearchValue())) {
				
				
				
				Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
			   Matcher matcher = pattern.matcher(projectsDTO.getSearchValue());
			   java.sql.Date sqlDate = null;
			    if(matcher.matches() == true) {
							if(projectsDTO.getSearchValue().contains("/")||(projectsDTO.getSearchValue().contains("-"))) {
								String replacedValue = projectsDTO.getSearchValue().replaceAll("/", "-");
								    OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationId);
									SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
							        java.util.Date utilDate = format.parse(replacedValue);
							        sqlDate = new java.sql.Date(utilDate.getTime());
							}
			    }
				
				
			 query1.append(" ps.projectSequeneNumber like '%" + projectsDTO.getSearchValue().replace("'", "") + "%' ")
					.append("OR ps.projectName like '%" + projectsDTO.getSearchValue().replace("'", "") + "%' ")
					.append("OR ps.invoiceFrequency like '%" + projectsDTO.getSearchValue().replace("'", "") + "%' ")
					.append("OR ps.id IN (select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.firstName like '" + projectsDTO.getSearchValue().replace("'", "") + "%' OR pah.approverUserId.lastName like '%" + projectsDTO.getSearchValue().replace("'", "") + "' ) ")
					.append("OR ps.id IN (select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id IN (select user.id from User user where (concat(user.firstName,user.lastName) like '%" + projectsDTO.getSearchValue().replace("'", "").replaceAll("[\\s]+", "").trim() + "%')))" )
					.append("OR ps.id IN (select pex.projectId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId.firstName like '" + projectsDTO.getSearchValue().replace("'", "") + "%' OR pex.approverUserId.lastName like '%" + projectsDTO.getSearchValue().replace("'", "") + "' ) ")
					.append("OR ps.id IN (select pex.projectId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId.id IN (select user.id from User user where (concat(user.firstName,user.lastName) like '%" + projectsDTO.getSearchValue().replace("'", "").replaceAll("[\\s]+", "").trim() + "%')))" )
					.append("OR ps.projectMode like '%" + projectsDTO.getSearchValue().replace("'", "") + "%' ");
			 
				 if(projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("in-progress") || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("in progress") 
						 || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("inprogress")) {
					 query1.append("OR ps.projectStatus like '%INPROGRESS%' ");
				 }else if(projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("on-hold") || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("on hold") 
						 || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("onhold")) {
					 query1.append("OR ps.projectStatus like '%ONHOLD%' ");
				 }else if(projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("not started") || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("notstarted")) {
					 query1.append("OR ps.projectStatus like '%NOTSTARTED%' ");
				 }else {
					 query1.append("OR ps.projectStatus like '%" + Project_Status.getNameByValue(projectsDTO.getSearchValue()) + "%' ");
				 }
				 
			     if(sqlDate != null) {
			    	query1.append("OR ps.startDate like '%" + sqlDate + "%' OR ps.endDate like '%" + sqlDate + "%' ");
				 }
				
						
			}
			
			
			List<Long> clientIdsList = new ArrayList<>();
			if(clients != null && !clients.isEmpty()){
				String[] client = clients.split(",");
				for(int i=0; i<client.length; i++){
					clientIdsList.add(Long.valueOf(client[i]));
				}
			}else {
				clientIdsList.add(0L);
			}
			
			List<Long> managerIdsList = new ArrayList<>();
			if(projectsDTO.getProjectManagers() != null && !projectsDTO.getProjectManagers().isEmpty()){
				String[] manager = projectsDTO.getProjectManagers().split(",");
				for(int i=0; i<manager.length; i++){
					managerIdsList.add(Long.valueOf(manager[i]));
				}
			}else {
				managerIdsList.add(0L);
			}
			
			
			List<String> tagsList = new ArrayList<>();
			if(projectsDTO.getTags() != null && !projectsDTO.getTags().isEmpty()) {
				String[] tags = projectsDTO.getTags().split(",");
				for(int i=0; i<tags.length; i++){
					tagsList.add(tags[i]);
				}
			}else {
				tagsList.add("");
			}
			
			if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
				approverQuery = " and (pc.id IN (select pah.projectClientsId.id from ProjectApproverHierarchy pah where pah.approverUserId = "+userId+" and pah.status ='1') OR "
						+ " pc.id IN (select pex.projectClientsId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId = "+userId+" and pex.status = '1') OR pc.createdBy.id = "+userId+" "
						+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.id = "+candidateId+" and pm.status = '1') "
								+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.parentCandidateId.id = "+candidateId+" and pm.status = '1')) ";
			}
			
			/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
				projectModeQuery = " and (ps.orgLocationsId.org.id = :orgId  OR ps.projectMode = 'Finished')";
			}else {
				projectModeQuery = " and (ps.orgLocationsId = :orgLocationsId OR ps.sharedLocationId = :orgLocationsId  OR ps.projectMode = 'Finished')";
			}*/
			
			projectModeQuery = " and (ps.orgLocationsId.org.id = :orgId  OR ps.projectMode = 'Finished')";
			
			String subQuery2 = " and ((:varZero  IN (:clientIdsList)) OR (:varZero NOT IN (:clientIdsList) and pc.orgRelationId.id IN (:clientIdsList)))";
			
			String subQuery3 ="";
			if(projectsDTO.getProjectType() != null && !projectsDTO.getProjectType().isEmpty()){
				String[] projectsType = projectsDTO.getProjectType().split(",");
				if (Arrays.asList(projectsType).contains("Internal")) {
					/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
						subQuery3 = subQuery3 + "ps.orgLocationsId.org.id = :orgId or ";
					}else {
						subQuery3 = subQuery3 + "ps.orgLocationsId = :orgLocationsId or ";
					}*/
					
					subQuery3 = subQuery3 + "ps.orgLocationsId.org.id = :orgId or ";
					
				}
				if (Arrays.asList(projectsType).contains("Client")) {
					subQuery3 = subQuery3 + "ps.orgLocationsId <> '"+orgLocationId+"' or ";
				}
				subQuery3 = "and (" + subQuery3.substring(0, subQuery3.length() - 4) + ")";
			}
			
			String subQuery4 = " and ((:varZero  IN (:managerIdsList)) OR (:varZero NOT IN (:managerIdsList) and "
					+ " (pc.id IN (select pah.projectClientsId.id from ProjectApproverHierarchy pah where pah.approverUserId.id IN (:managerIdsList)) OR "
					+ " pc.id IN (select pex.projectClientsId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId.id IN (:managerIdsList))))) ";
			
			String subQuery6 = " and ((:varString  IN (:tagsList)) OR (:varString NOT IN (:tagsList) and pc.projectId.id IN (select st.entityId from SourceleadTags st where st.tagName IN (:tagsList) and st.status ='1' ))) ";

			
			String subQuery5 ="";
			if(projectsDTO.getInvoiceFrequency() != null && !projectsDTO.getInvoiceFrequency().isEmpty()){
				String[] frequencyFilter = projectsDTO.getInvoiceFrequency().split(",");
				if (Arrays.asList(frequencyFilter).contains("Weekly")) {
					subQuery5 = subQuery5 + "ps.invoiceFrequency = 'Weekly' or ";
				}
				
				if (Arrays.asList(frequencyFilter).contains("Bi-Weekly")) {
					subQuery5 = subQuery5 + "ps.invoiceFrequency = 'Bi-Weekly' or ";
				}
				
				if (Arrays.asList(frequencyFilter).contains("Semi-Monthly")) {
					subQuery5 = subQuery5 + "ps.invoiceFrequency = 'Semi-Monthly' or ";
				}
				
				if (Arrays.asList(frequencyFilter).contains("Monthly")) {
					subQuery5 = subQuery5 + "ps.invoiceFrequency = 'Monthly' or ";
				}
				
				subQuery5 = "and (" + subQuery5.substring(0, subQuery5.length() - 4) + ")";
			}
			
			
			
			//usertList shorting order
					String sortingQry = "";
					if(StringUtils.isNotBlank(projectsDTO.getSortColumn())) {
						sortingQry = " order by ps."+ projectsDTO.getSortColumn() +" "+projectsDTO.getSortOrder();
					}
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
					
					
					
					
			
					/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
						query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.org.id = :orgId "+subQuery2+" "+approverQuery+" "+subQuery4+" "+subQuery6+") "+projectModeQuery+" "+subQuery1+" "+subQuery3+" "+subQuery5+" ";
					}else {
						query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId = :orgLocationsId "+subQuery2+" "+approverQuery+" "+subQuery4+" "+subQuery6+") "+projectModeQuery+" "+subQuery1+" "+subQuery3+" "+subQuery5+" ";
					}*/
					
					query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.org.id = :orgId and pc.status = 1 "+subQuery2+" "+approverQuery+" "+subQuery4+" "+subQuery6+") "+projectModeQuery+" "+subQuery3+" "+subQuery5+" ";
			         query = query+projectAccessQuery;
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+sortingQry);
			}else{
				 queryObj = session.createQuery(query + query1.toString()+sortingQry);
			}
			
			queryObj.setFirstResult(projectsDTO.getStart());
			queryObj.setMaxResults(projectsDTO.getLength());
			/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
				queryObj.setLong("orgId", projectsDTO.getOrgId()).setString("varString", "").setLong("varZero", 0).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			}else {
				queryObj.setLong("orgLocationsId", orgLocationsId).setString("varString", "").setLong("varZero", 0).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			}*/
			
			queryObj.setLong("orgId", projectsDTO.getOrgId()).setString("varString", "").setLong("varZero", 0).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			
			projectsList = queryObj.list();
			
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
					countlist = session.createQuery("select count(*) " + query +projectAccessQuery + "and "+"("+query1.toString()+")").setString("varString", "").setLong("varZero", 0).setLong("orgId", projectsDTO.getOrgId()).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
				}else {
					countlist = session.createQuery("select count(*) " + query +projectAccessQuery + "and "+"("+query1.toString()+")").setString("varString", "").setLong("varZero", 0).setLong("orgLocationsId", orgLocationsId).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
				}*/
				
				countlist = session.createQuery("select count(*) " + query +projectAccessQuery + "and "+"("+query1.toString()+")").setString("varString", "").setLong("varZero", 0).setLong("orgId", projectsDTO.getOrgId()).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
				
				
				filterCount = countlist.iterator().next().intValue();
			}else {
				/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
					countlist = session.createQuery("select count(*) " + query +projectAccessQuery).setString("varString", "").setLong("varZero", 0).setLong("orgId", projectsDTO.getOrgId()).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
				}else {
					countlist = session.createQuery("select count(*) " + query +projectAccessQuery).setString("varString", "").setLong("varZero", 0).setLong("orgLocationsId", orgLocationsId).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
				}*/
				
				countlist = session.createQuery("select count(*) " + query +projectAccessQuery).setString("varString", "").setLong("varZero", 0).setLong("orgId", projectsDTO.getOrgId()).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
				
				filterCount = countlist.iterator().next().intValue();
			}
			
			
			int totalCount = 0;

			
			if (projectsDTO != null) {
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
			
			response.setData(projectsList);
			
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
			
//		    long millis=System.currentTimeMillis();  
//	        java.sql.Date date = new java.sql.Date(millis);
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
			.append("where clk.ORG_LOCATION_ID = :clkOrgLocationID ")
			.append("AND uRole.ORG_LOCATIONS_ID = :uRoleOrgLocationID ")
			.append("AND emp.ORG_LOCATIONS_ID = :empOrgLocationID");
			
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
			queryObj.setLong("clkOrgLocationID", orgLocation.getId());
			queryObj.setLong("uRoleOrgLocationID", orgLocation.getId());
			queryObj.setLong("empOrgLocationID", orgLocation.getId());
			
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
	public List<ClockInOut> getListOfClockDataBasedOnDate(String selectedDate, OrgLocations loginOrgLocation, Long userId) {
		List<ClockInOut> clockList = null;
		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			Date date = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), selectedDate);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT clk.CLOCK_IN_TIME as clockInTime,")
			.append("clk.CLOCK_IN_DATE as clockInDate,")
			.append("task.TASK_NAME as projectTaskName,")
			.append("clk.TOTAL_TIME as totalTime,")
			.append("clk.COMMENTS as comments,")
			.append("clk.CLOCK_OUT_TIME as clockOutTime, ")
			.append("clk.CLOCK_OUT_DATE as clockOutDate ")
			.append("FROM CLOCK_INOUT clk INNER JOIN PROJECT_TASKS task ")
			.append("ON clk.PROJECTS_TASK_ID = task.ID and clk.USER_ID =:userId and clk.ORG_LOCATION_ID =:orgLocationId and clk.CLOCK_IN_DATE =:sqlDate and clk.CLOCK_OUT_DATE is NOT NULL");
			
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

}
