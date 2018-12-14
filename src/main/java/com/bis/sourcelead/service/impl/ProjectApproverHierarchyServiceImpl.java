package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpenseProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class ProjectApproverHierarchyServiceImpl implements ProjectApproverHierarchyService {

	@Autowired
	private  ProjectApproverHierarchyDao  projectApproverHierarchyDao;
	
	@Autowired
	ExpenseProjectApproverHierarchyDao expenseProjectApproverHierarchyDao;
	
	@Autowired
	private CommonUtil commonUtil;	
	
	@Override
	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)
			throws Exception {
		projectApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
		return projectApproverHierarchyDao.saveProjectApproverHierarchy(projectApproverHierarchy);
	}
	

	@Override
	public ProjectApproverHierarchy updateProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)
			throws Exception {
		projectApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
		return projectApproverHierarchyDao.updateProjectApproverHierarchy(projectApproverHierarchy);
	}

	
	@Override
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception {
		return projectApproverHierarchyDao.getLevelOneProjectApproverByProjectId(ProjectId);
	}
	
	
	@Override
	public List<ProjectApproverHierarchy> getAllApproverUsersByProjectIdList(List<Long> projectIds) throws Exception {
		return projectApproverHierarchyDao.getAllApproverUsersByProjectIdList(projectIds);
	}


	@Override
	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception {
		return projectApproverHierarchyDao.getApproverUsersByProjectId(Id);
		
	}
	
	@Override
	public List<ProjectApproverHierarchy> getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception
	{
		return projectApproverHierarchyDao.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMemberMasterCandidateId, projectId);
	}
	
	@Override
	public List<ProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception
	{
		return projectApproverHierarchyDao.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMemberMasterCandidateId, projectId);
	}
	
	
	
	@Override
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectId(long projectId,long userId) throws Exception {
		return projectApproverHierarchyDao.getAllProjectApproverHierarchyByProjectId(projectId,userId);
		
	}


	@Override
	public List<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId) {
		return projectApproverHierarchyDao.getProjectHierarchyListByLoginUserId(userId);
	}

	@Override
	public List<Long> getProjectIdsByLoginUserId(Long userId) {
		return projectApproverHierarchyDao.getProjectIdsByLoginUserId(userId);
	}
	
	
	@Override
	public ProjectApproverHierarchy getByProjectApproverHierarchyId(Long projectApproverHierarchy)
			throws Exception {
		return projectApproverHierarchyDao.getByProjectApproverHierarchyId(projectApproverHierarchy);
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	public Set<ProjectApproverHierarchy> prepareProjectApproverHierarchyListByJson(ProjectsDTO projectsDTO, HttpServletRequest request,
			Projects projects,ProjectClients projectClients) throws Exception {
		    
		    Long[] ids = new Long[1000];
			HashSet<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
	    	
			if(projectClients.getId() == null){
				
				  if(!isNullOrEmpty(projectsDTO.getLevel1Manager())){
				    		
				    		ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
					    	pah1.setProjectId(projects);
					    	pah1.setProjectClientsId(projectClients);
					    	User approverUserId1 = new User();
					    	String level1UserDetails[] = projectsDTO.getLevel1Manager().split(",");
				    		approverUserId1.setId(Long.parseLong(level1UserDetails[0]));
					    	pah1.setApproverUserId(approverUserId1);
					    	//pah1.setApprovalMechanismType(projectsDTO.getProjectApprovalTypeValue());
					    	pah1.setLevel(1);
					    	pah1.setStatus("1");
					    	//ProjectApproverHierarchy saveProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
					    	ids[0] = approverUserId1.getId();
					    	projectApproverHierarchyList.add(pah1);
			    	}
		    	//send mail to assigned Project Manager
				
				  if(!isNullOrEmpty(projectsDTO.getLevel2Manager())){	
						User approverUserId2 = new User();
						ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
						pah2.setProjectId(projects);
						pah2.setProjectClientsId(projectClients);
						approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2Manager()));
						pah2.setApproverUserId(approverUserId2);
						//pah2.setApprovalMechanismType(projectsDTO.getProjectApprovalTypeValue());
						pah2.setLevel(2);
						pah2.setStatus("1");
						//ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
						ids[1] = approverUserId2.getId();
						projectApproverHierarchyList.add(pah2);
				 }
			}else{
				Set<ProjectApproverHierarchy> approverHierarchyList = projectClients.getProjectApproverHierarchyList();
				for(ProjectApproverHierarchy approverHierarchy : approverHierarchyList){
					 if(!isNullOrEmpty(projectsDTO.getLevel1Manager())){
						 
						 if(approverHierarchy.getLevel().equals(1)){
							 approverHierarchy.setProjectId(projects);
							    approverHierarchy.setProjectClientsId(projectClients);
						    	User approverUserId1 = new User();
						    	String level1UserDetails[] = projectsDTO.getLevel1Manager().split(",");
					    		approverUserId1.setId(Long.parseLong(level1UserDetails[0]));
					    		approverHierarchy.setApproverUserId(approverUserId1);
					    		approverHierarchy.setLevel(1);
					    		approverHierarchy.setStatus("1");
					    		//approverHierarchy.setApprovalMechanismType(projectsDTO.getProjectApprovalTypeValue());
						    	ids[0] = approverUserId1.getId();
						    	projectApproverHierarchyList.add(approverHierarchy);
						 }
						   
					 }
					 
					 if(approverHierarchy.getLevel().equals(2)){
						 if(isNullOrEmpty(projectsDTO.getLevel2Manager())){	
							 projectApproverHierarchyDao.deleteProjectApproverHierarchy(approverHierarchy);
						 }else{
							    User approverUserId2 = new User();
							    approverHierarchy.setProjectId(projects);
							    approverHierarchy.setProjectClientsId(projectClients);
								approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2Manager()));
								approverHierarchy.setApproverUserId(approverUserId2);
								approverHierarchy.setLevel(2);
								approverHierarchy.setStatus("1");
								//approverHierarchy.setApprovalMechanismType(projectsDTO.getProjectApprovalTypeValue());
								ids[1] = approverUserId2.getId();
								projectApproverHierarchyList.add(approverHierarchy);
						 }
					 }
				}
				
				if(approverHierarchyList.size() == 1){
					if(!isNullOrEmpty(projectsDTO.getLevel2Manager())){	
						User approverUserId2 = new User();
						ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
						pah2.setProjectId(projects);
						pah2.setProjectClientsId(projectClients);
						approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2Manager()));
						pah2.setApproverUserId(approverUserId2);
						pah2.setLevel(2);
						pah2.setStatus("1");
						//pah2.setApprovalMechanismType(projectsDTO.getProjectApprovalTypeValue());
						ids[1] = approverUserId2.getId();
						projectApproverHierarchyList.add(pah2);
					}
				}
			}
	      
		 
		
		return projectApproverHierarchyList;
	}


	@Override
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectIdForManager(Long projectId,Long userId,int managerLevel) throws Exception {
		return projectApproverHierarchyDao.getAllProjectApproverHierarchyByProjectIdForManager(projectId,userId,managerLevel);
	}
	
	@Override
	public List<ProjectApproverHierarchy> getProjectApproversListByUserIdAndOrgLocationId(Long userId,
			Long orgLocationId) throws Exception {
		return projectApproverHierarchyDao.getProjectApproversListByUserIdAndOrgLocationId(userId, orgLocationId);
	}


	@Override
	public ProjectApproverHierarchy getApproverUsersByProjectIdLevel1(Long projectId, Long userId) {
		// TODO Auto-generated method stub
		return projectApproverHierarchyDao.getApproverUsersByProjectIdLevel1(projectId,userId);
	}


	@Override
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectIdlevel2Manager(Long projectId,
			Long userId) throws Exception {
		return projectApproverHierarchyDao.getAllProjectApproverHierarchyByProjectIdlevel2Manager(projectId,userId);
			}

	@Override
	public List<ProjectApproverHierarchy> getProjectsHierarchyListByProjectIdAndOrgLocationId(Long projectId,
			Long orgLocationId) throws Exception {
		return projectApproverHierarchyDao.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectId, orgLocationId);
	}


	@Override
	public List<ProjectApproverHierarchy> getApproverUserIdsByOrgLocationIdAndLevelAndFrequency(Long orgLocationId, String invoiceFrequency, int managerLevel) throws Exception {
		return projectApproverHierarchyDao.getApproverUserIdsByOrgLocationIdAndLevelAndFrequency(orgLocationId, invoiceFrequency,managerLevel);
	}


	@Override
	public void deleteProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception {
		 projectApproverHierarchyDao.deleteProjectApproverHierarchy(projectApproverHierarchy);
	}


	@Override
	public ProjectApproverHierarchy getProjectApproverByProjectId(Long ProjectId) throws Exception {
		return projectApproverHierarchyDao.getProjectApproverByProjectId(ProjectId);
	}


	@Override
	public ProjectApproverHierarchy getProjectApproverByProjectIdAndLevel(Long projectId, Integer level)
			throws Exception {
		return projectApproverHierarchyDao.getProjectApproverByProjectIdAndLevel(projectId, level);
	}


	@Override
	public ProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientId(Long projectId, Long projectClientId,Long orgId,String isGlobalAccess) throws Exception {
		return projectApproverHierarchyDao.getApproverUsersByProjectIdAndProjectClientId(projectId, projectClientId,orgId,isGlobalAccess);
	}


	@Override
	public ProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(Long approverUserId,
			Long projectClientId) throws Exception {
		return projectApproverHierarchyDao.getApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(approverUserId, projectClientId);
	}


	@Override
	public ProjectApproverHierarchy getProjectApproverByUserIdAndProjectIdOrgLocationId(Long userId, Long projectId, Long orgLocationId) throws Exception {
		return projectApproverHierarchyDao.getProjectApproverByUserIdAndProjectIdOrgLocationId(userId, projectId, orgLocationId);
	}


	@Override
	public List<ProjectApproverHierarchy> getProjectsHierarchyListByProjectClientId(Long projectClientId)
			throws Exception {
		return projectApproverHierarchyDao.getProjectsHierarchyListByProjectClientId(projectClientId);
	}


	@Override
	public Set<ExpenseProjectApproverHierarchy> prepareExpenseProjectApproverHierarchyList(ProjectsDTO projectsDTO,
			HttpServletRequest request, Projects projects, ProjectClients projectClients) throws Exception {
		
	    
	    Long[] ids = new Long[1000];
		HashSet<ExpenseProjectApproverHierarchy> expenseApproverHierarchyList = new HashSet<ExpenseProjectApproverHierarchy>();
    	
		if(projectClients.getId() == null){
			
			  if(!isNullOrEmpty(projectsDTO.getLevel1Manager()) || !isNullOrEmpty(projectsDTO.getLevel1ExpenseApprover())){
			    		
				        ExpenseProjectApproverHierarchy eah1 = new ExpenseProjectApproverHierarchy();
				        eah1.setProjectId(projects);
				        eah1.setProjectClientsId(projectClients);
				    	User approverUserId1 = new User();
				    	if(!isNullOrEmpty(projectsDTO.getLevel1ExpenseApprover())) {
				    		String level1UserDetails[] = projectsDTO.getLevel1ExpenseApprover().split(",");
				    		approverUserId1.setId(Long.parseLong(level1UserDetails[0]));
				    	}else {
				    		String level1UserDetails[] = projectsDTO.getLevel1Manager().split(",");
				    		approverUserId1.setId(Long.parseLong(level1UserDetails[0]));
				    	}
				    	
				    	eah1.setApproverUserId(approverUserId1);
				    	//eah1.setApprovalMechanismType(projectsDTO.getExpenseApprovalTypeValue());
				    	eah1.setLevel(1);
				    	eah1.setStatus("1");
				    	//ProjectApproverHierarchy saveProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
				    	ids[0] = approverUserId1.getId();
				    	expenseApproverHierarchyList.add(eah1);
		    	}
	    	//send mail to assigned Project Manager
			
			  if(isNullOrEmpty(projectsDTO.getLevel1ExpenseApprover()) && !isNullOrEmpty(projectsDTO.getLevel2Manager())){	
					User approverUserId2 = new User();
					ExpenseProjectApproverHierarchy eah2 = new ExpenseProjectApproverHierarchy();
					eah2.setProjectId(projects);
					eah2.setProjectClientsId(projectClients);
					if(!isNullOrEmpty(projectsDTO.getLevel2ExpenseApprover())) {
						approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2ExpenseApprover()));
					}else {
						approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2Manager()));
					}
					
					eah2.setApproverUserId(approverUserId2);
					//eah2.setApprovalMechanismType(projectsDTO.getExpenseApprovalTypeValue());
					eah2.setLevel(2);
					eah2.setStatus("1");
					//ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
					ids[1] = approverUserId2.getId();
					expenseApproverHierarchyList.add(eah2);
			 
			  }else if(!isNullOrEmpty(projectsDTO.getLevel2ExpenseApprover())){	
					User approverUserId2 = new User();
					ExpenseProjectApproverHierarchy eah2 = new ExpenseProjectApproverHierarchy();
					eah2.setProjectId(projects);
					eah2.setProjectClientsId(projectClients);
					approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2ExpenseApprover()));
					eah2.setApproverUserId(approverUserId2);
					eah2.setLevel(2);
					eah2.setStatus("1");
					//eah2.setApprovalMechanismType(projectsDTO.getExpenseApprovalTypeValue());
					//ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
					ids[1] = approverUserId2.getId();
					expenseApproverHierarchyList.add(eah2);
			 }
		}else{
			Set<ExpenseProjectApproverHierarchy> expApproverHierarchyList = projectClients.getExpenseProjectApproverHierarchyList();
			for(ExpenseProjectApproverHierarchy expenseHierarchy : expApproverHierarchyList){
				 if(!isNullOrEmpty(projectsDTO.getLevel1Manager()) || !isNullOrEmpty(projectsDTO.getLevel1ExpenseApprover())){
					 
					 if(expenseHierarchy.getLevel().equals(1)){
						 expenseHierarchy.setProjectId(projects);
						 expenseHierarchy.setProjectClientsId(projectClients);
					    	User approverUserId1 = new User();
					    	if(!isNullOrEmpty(projectsDTO.getLevel1ExpenseApprover())) {
					    		String level1UserDetails[] = projectsDTO.getLevel1ExpenseApprover().split(",");
					    		approverUserId1.setId(Long.parseLong(level1UserDetails[0]));
					    	}else {
					    		String level1UserDetails[] = projectsDTO.getLevel1Manager().split(",");
					    		approverUserId1.setId(Long.parseLong(level1UserDetails[0]));
					    	}
					    	
				    		expenseHierarchy.setApproverUserId(approverUserId1);
				    		//expenseHierarchy.setApprovalMechanismType(projectsDTO.getExpenseApprovalTypeValue());
				    		expenseHierarchy.setLevel(1);
				    		expenseHierarchy.setStatus("1");
					    	ids[0] = approverUserId1.getId();
					    	expenseApproverHierarchyList.add(expenseHierarchy);
					 }
					   
				 }
				 
				 if(expenseHierarchy.getLevel().equals(2)){
					 if(isNullOrEmpty(projectsDTO.getLevel2ExpenseApprover())){	
						 expenseProjectApproverHierarchyDao.deleteProjectExpenseProjectApproverHierarchy(expenseHierarchy);
					 }else{
						    User approverUserId2 = new User();
						    expenseHierarchy.setProjectId(projects);
						    expenseHierarchy.setProjectClientsId(projectClients);
							approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2ExpenseApprover()));
							expenseHierarchy.setApproverUserId(approverUserId2);
							expenseHierarchy.setLevel(2);
							expenseHierarchy.setStatus("1");
							//expenseHierarchy.setApprovalMechanismType(projectsDTO.getExpenseApprovalTypeValue());
							ids[1] = approverUserId2.getId();
							expenseApproverHierarchyList.add(expenseHierarchy);
					 }
				 }
			}
			
			if(expenseApproverHierarchyList.size() == 1){
				  if(!isNullOrEmpty(projectsDTO.getLevel2ExpenseApprover())){	
					User approverUserId2 = new User();
					ExpenseProjectApproverHierarchy eah2 = new ExpenseProjectApproverHierarchy();
					eah2.setProjectId(projects);
					eah2.setProjectClientsId(projectClients);
					if(!isNullOrEmpty(projectsDTO.getLevel2ExpenseApprover())) {
						approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2ExpenseApprover()));
					}else {
						approverUserId2.setId(Long.parseLong(projectsDTO.getLevel2Manager()));
					}
					eah2.setApproverUserId(approverUserId2);
					//eah2.setApprovalMechanismType(projectsDTO.getExpenseApprovalTypeValue());
					eah2.setLevel(2);
					eah2.setStatus("1");
					ids[1] = approverUserId2.getId();
					expenseApproverHierarchyList.add(eah2);
				}
			}
		}
      
	 
	
	return expenseApproverHierarchyList;

	}


	@Override
	public String getApprovalMechanismTypeByProjectClientId(Long projectClientId) throws Exception {
		return projectApproverHierarchyDao.getApprovalMechanismTypeByProjectClientId(projectClientId);
	}
	
}
