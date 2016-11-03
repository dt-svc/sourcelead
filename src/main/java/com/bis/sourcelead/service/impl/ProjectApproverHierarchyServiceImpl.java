package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ProjectApproverHierarchyServiceImpl implements ProjectApproverHierarchyService {

	@Autowired
	private  ProjectApproverHierarchyDao  projectApproverHierarchyDao;
	
	@Autowired
	private UserService userService;

	
	@Override
	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)
			throws Exception {
		return projectApproverHierarchyDao.saveProjectApproverHierarchy(projectApproverHierarchy);
	}
	

	@Override
	public ProjectApproverHierarchy updateProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)
			throws Exception {
		return projectApproverHierarchyDao.updateProjectApproverHierarchy(projectApproverHierarchy);
	}

	
	@Override
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception {
		return projectApproverHierarchyDao.getLevelOneProjectApproverByProjectId(ProjectId);
	}


	@Override
	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception {
		return projectApproverHierarchyDao.getApproverUsersByProjectId(Id);
		
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


	@Override
	public Set<ProjectApproverHierarchy> prepareProjectApproverHierarchyListByJson(JSONObject jsonObj, HttpServletRequest request,
			Projects projects) throws Exception {
		    
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    Long[] ids = new Long[1000];
			HashSet<ProjectApproverHierarchy> projectMemberList = new HashSet<ProjectApproverHierarchy>();
	    	
	        if(jsonObj.has("level1Manager")){
			    	if(jsonObj.getString("level1Manager") != null && !jsonObj.getString("level1Manager").isEmpty()){
			    		
			    		ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
				    	pah1.setProjectId(projects);
				    	User approverUserId1 = new User();
			    		approverUserId1.setId(Long.parseLong(jsonObj.getString("level1Manager")));
				    	pah1.setApproverUserId(approverUserId1);
				    	if(jsonObj.has("level2Manager") && !jsonObj.getString("level2Manager").isEmpty()){
				    			pah1.setLevel(1);
				    	}else{
				    		pah1.setLevel(2);
				    	}
				    	
				    	//ProjectApproverHierarchy saveProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
				    	ids[0] = approverUserId1.getId();
				    	projectMemberList.add(pah1);
			    	}
		    	}
	    	//send mail to assigned Project Manager
			if(jsonObj.has("level2Manager")){ 	
				if(!jsonObj.getString("level2Manager").isEmpty()){
					User approverUserId2 = new User();
					ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
					pah2.setProjectId(projects);
					approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
					pah2.setApproverUserId(approverUserId2);
					pah2.setLevel(2);
					//ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
					ids[1] = approverUserId2.getId();
					projectMemberList.add(pah2);
				}
			 }
		 
		//admin as approver hierarchy ..
		/*Collection<User> uLsit = userService.getUsersByUserRoles(loginOrgLocation.getId());
		CopyOnWriteArrayList<User> userLsit = new CopyOnWriteArrayList<User>(uLsit);
		if(userLsit != null && !userLsit.isEmpty()){
			for(User userRole : userLsit){
				  if(ids[0] != null &&  ids[0].equals(userRole.getId())){
					  userLsit.remove(userRole);
				  }
				  else if (ids[1] != null && ids[1].equals(userRole.getId())) {
					  userLsit.remove(userRole);
				  }else{
					    ProjectApproverHierarchy pah3 = new ProjectApproverHierarchy();
					    pah3.setProjectId(projects);
						pah3.setApproverUserId(userRole);
						pah3.setLevel(2);
						projectMemberList.add(pah3);
					    //ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah3);
				  } 
				  
			}
		}*/
		return projectMemberList;
	}
	
}
