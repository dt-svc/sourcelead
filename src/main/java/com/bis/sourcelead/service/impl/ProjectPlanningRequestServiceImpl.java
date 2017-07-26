package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectPlanningNewResourceRequestDao;
import com.bis.sourcelead.dao.ProjectPlanningRequestDao;
import com.bis.sourcelead.dao.ProjectPlanningResourceRequestDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectPlanningRequestService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class ProjectPlanningRequestServiceImpl implements ProjectPlanningRequestService{
	
	@Autowired 
	ProjectsDao projectsDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ProjectPlanningRequestDao projectPlanningRequestDao;
	
	@Autowired
	RolesServiceImpl rolesServiceImpl;

	@Autowired
	ProjectPlanningResourceRequestDao projectPlanningResourceRequestDao;

	@Autowired
	ProjectPlanningNewResourceRequestDao projectPlanningNewResourceRequestDao;
	
	protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}

	@Override
	public void saveProjectPlanningRequest(JSONObject jsonObj, User loginUser, OrgLocations orgLocations,
			String projectUUID,String saveType) throws JSONException, Exception {
		
		ProjectPlanningRequest projectPlanningRequest  = null;
		if(jsonObj.has("savedPlanningRequestUUID") && !"".equalsIgnoreCase(jsonObj.getString("savedPlanningRequestUUID"))){
			projectPlanningRequest = projectPlanningRequestDao.getProjectPlanningRequestByRequestUUID(jsonObj.getString("savedPlanningRequestUUID"));
		}else{
			projectPlanningRequest = new ProjectPlanningRequest();
			projectPlanningRequest.setRequestType(Constants.RESOURCE_REQUEST_ASSOCIATE_REQUEST);
		}
		
		if(jsonObj.has("requestId") && !"".equalsIgnoreCase(jsonObj.getString("requestId"))){
			projectPlanningRequest.setRequestId(jsonObj.getString("requestId"));
		}
		
		if(!isNullOrEmpty(projectUUID)){
			Projects projects = projectsDao.getProjectByProjectUUID(projectUUID);
			projectPlanningRequest.setProjectId(projects);
		}
		
		if(jsonObj.has("description") && !"".equalsIgnoreCase(jsonObj.getString("description"))){
			projectPlanningRequest.setDescription(jsonObj.getString("description"));
		}
		
		if(jsonObj.has("approver") && !"".equalsIgnoreCase(jsonObj.getString("approver"))){
			User approverUser = userDao.getUserById(Long.valueOf(jsonObj.getString("approver")));
			projectPlanningRequest.setApproverUser(approverUser);
			projectPlanningRequest.setApproverName(approverUser.getFullName());
			
		}
		
		
		if(jsonObj.has("estimatedHrs") && !"".equalsIgnoreCase(jsonObj.getString("estimatedHrs"))){
			projectPlanningRequest.setEstimatedHours(Double.valueOf(jsonObj.getString("estimatedHrs")));
		}
		
		if(jsonObj.has("estimatedBudget") && !"".equalsIgnoreCase(jsonObj.getString("estimatedBudget"))){
			projectPlanningRequest.setEstimatedBudget(Double.valueOf(jsonObj.getString("estimatedBudget")));
		}
		
		if(jsonObj.has("estimatedBudgetCurrencyType") && !"".equalsIgnoreCase(jsonObj.getString("estimatedBudgetCurrencyType"))){
			projectPlanningRequest.setEstimatedBudgetCurrency(jsonObj.getString("estimatedBudgetCurrencyType"));
		}
		
		projectPlanningRequest.setOrgLocationId(orgLocations);
		projectPlanningRequest.setRequestedUser(loginUser.getFullName());
		projectPlanningRequest.setCreatedBy(loginUser);
		projectPlanningRequest.setUpdatedBy(loginUser);
		projectPlanningRequest.setStatus(Constants.RECORD_ACTIVE);
		projectPlanningRequest.setRequestUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		projectPlanningRequest.setCreatedDate(new Date());
		projectPlanningRequest.setUpdatedDate(new Date());
		
		List<Long> resourceRequestIds = null;
		List<Long> approvedResources = new ArrayList<>();
		if(projectPlanningRequest.getId() != null){
			 resourceRequestIds = projectPlanningResourceRequestDao.getProjectPlanningResourceRequestIdsByRequestId(projectPlanningRequest.getId());
			 Set<ProjectPlanningResourceRequest> approvedResourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
			 for(ProjectPlanningResourceRequest resourceRequest : approvedResourceRequestList){
				 if(!isNullOrEmpty(resourceRequest.getResourceStatus()) && resourceRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_APPROVED)){
					 approvedResources.add(resourceRequest.getId());
				 }
			 }
		}
		Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = getResourceRequestList(jsonObj,loginUser,projectPlanningRequest,resourceRequestIds);
		projectPlanningRequest.setProjectPlanningResourceRequestList(projectPlanningResourceRequestList);
		projectPlanningRequest.setRequestedResources(projectPlanningResourceRequestList.size()+approvedResources.size());
		projectPlanningRequest.setAllocatedResources(0);
		
		if(!isNullOrEmpty(saveType) && saveType.equalsIgnoreCase("Draft")){
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_DRAFT);
		}else{
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL);
		}
		
		projectPlanningRequestDao.saveProjectPlanningRequest(projectPlanningRequest);
		
		if(null != resourceRequestIds && resourceRequestIds.size() > 0){
			for(Long resourceRequestId : resourceRequestIds){
				ProjectPlanningResourceRequest resourceRequest = projectPlanningResourceRequestDao.getProjectPlanningResourceRequestById(resourceRequestId);
				projectPlanningResourceRequestDao.deleteProjectPlanningResourceRequest(resourceRequest);
			}
		}
		
	}
	
	@Override
	public void saveProjectNewResourceRequest(JSONObject jsonObj, User loginUser, OrgLocations orgLocations,String projectUUID, String saveType) throws JSONException, Exception {
		
		ProjectPlanningRequest projectPlanningRequest  = null;
		if(jsonObj.has("savedPlanningRequestUUID") && !"".equalsIgnoreCase(jsonObj.getString("savedPlanningRequestUUID"))){
			projectPlanningRequest = projectPlanningRequestDao.getProjectPlanningRequestByRequestUUID(jsonObj.getString("savedPlanningRequestUUID"));
		}else{
			projectPlanningRequest = new ProjectPlanningRequest();
			projectPlanningRequest.setRequestType(Constants.RESOURCE_REQUEST_NEW_REQUEST);
		}
		
		if(jsonObj.has("requestId") && !"".equalsIgnoreCase(jsonObj.getString("requestId"))){
			projectPlanningRequest.setRequestId(jsonObj.getString("requestId"));
		}
		
		if(!isNullOrEmpty(projectUUID)){
			Projects projects = projectsDao.getProjectByProjectUUID(projectUUID);
			projectPlanningRequest.setProjectId(projects);
		}
		if(jsonObj.has("description") && !"".equalsIgnoreCase(jsonObj.getString("description"))){
			projectPlanningRequest.setDescription(jsonObj.getString("description"));
		}
		if(jsonObj.has("approver") && !"".equalsIgnoreCase(jsonObj.getString("approver"))){
			User approverUser = userDao.getUserById(Long.valueOf(jsonObj.getString("approver")));
			projectPlanningRequest.setApproverUser(approverUser);
			projectPlanningRequest.setApproverName(approverUser.getFullName());
			
		}
		
		if(jsonObj.has("totalResourcesValue") && !"".equalsIgnoreCase(jsonObj.getString("totalResourcesValue"))){
			projectPlanningRequest.setRequestedResources(jsonObj.getInt("totalResourcesValue"));
		}
		
		if(jsonObj.has("totalEstHours") && !"".equalsIgnoreCase(jsonObj.getString("totalEstHours"))){
			projectPlanningRequest.setEstimatedHours(Double.valueOf(jsonObj.getString("totalEstHours")));
		}
		
		if(jsonObj.has("totalEstBudget") && !"".equalsIgnoreCase(jsonObj.getString("totalEstBudget"))){
			projectPlanningRequest.setEstimatedBudget(Double.valueOf(jsonObj.getString("totalEstBudget")));
		}
		
		if(jsonObj.has("estimatedBudgetCurrencyType") && !"".equalsIgnoreCase(jsonObj.getString("estimatedBudgetCurrencyType"))){
			projectPlanningRequest.setEstimatedBudgetCurrency(jsonObj.getString("estimatedBudgetCurrencyType"));
		}
		
		projectPlanningRequest.setOrgLocationId(orgLocations);
		projectPlanningRequest.setRequestedUser(loginUser.getFullName());
		projectPlanningRequest.setCreatedBy(loginUser);
		projectPlanningRequest.setUpdatedBy(loginUser);
		projectPlanningRequest.setStatus(Constants.RECORD_ACTIVE);
		projectPlanningRequest.setRequestUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		projectPlanningRequest.setCreatedDate(new Date());
		projectPlanningRequest.setUpdatedDate(new Date());
		
		List<Long> resourceRequestIds = null;
		if(projectPlanningRequest.getId() != null){
			 resourceRequestIds = projectPlanningNewResourceRequestDao.getProjectPlanningNewResourceRequestIdsByRequestId(projectPlanningRequest.getId());
		}
		
		Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList = getNewResourceRequestList(jsonObj,loginUser,projectPlanningRequest,resourceRequestIds);
		projectPlanningRequest.setProjectPlanningNewResourceRequestList(projectPlanningNewResourceRequestList);
//		projectPlanningRequest.setRequestedResources(projectPlanningNewResourceRequestList.size());
		projectPlanningRequest.setAllocatedResources(0);
		
		if(!isNullOrEmpty(saveType) && saveType.equalsIgnoreCase("Draft")){
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_DRAFT);
		}else{
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL);
		}
		projectPlanningRequest.setRequestType(Constants.RESOURCE_REQUEST_NEW_REQUEST);
		projectPlanningRequestDao.saveProjectPlanningRequest(projectPlanningRequest);
		
		if(null != resourceRequestIds && resourceRequestIds.size() > 0){
			for(Long resourceRequestId : resourceRequestIds){
				ProjectPlanningNewResourceRequest newResourceRequest = projectPlanningNewResourceRequestDao.getProjectPlanningNewResourceRequestById(resourceRequestId);
				projectPlanningNewResourceRequestDao.deleteProjectPlanningNewResourceRequest(newResourceRequest);
			}
		}
		
	}
	
	private Set<ProjectPlanningResourceRequest> getResourceRequestList(JSONObject jsonObj, User loginUser,ProjectPlanningRequest projectPlanningRequest,List<Long> resourceRequestIds) throws Exception {
		
		 Set<ProjectPlanningResourceRequest> resourceRequestSet = new HashSet<>();
		 List<Long> updatedResourceRequests = new ArrayList<Long>();
		 
		 String pattern = "dd-MM-yyyy";
	     SimpleDateFormat format = new SimpleDateFormat(pattern);
		 
		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxTsRowNum"));
		 
		 for(int i=1;i<=maxRowCount;i++){
			 if(jsonObj.has(i+"candidateId") && !isNullOrEmpty(jsonObj.getString(i+"candidateId"))){
				 ProjectPlanningResourceRequest resourceRequest = new ProjectPlanningResourceRequest();
				 
				 if(jsonObj.has(i+"resourceRequestId") &&  null != jsonObj.getString(i+"resourceRequestId") && !"".equalsIgnoreCase(jsonObj.getString(i+"resourceRequestId"))){
					 resourceRequest.setId(Long.valueOf(jsonObj.getString(i+"resourceRequestId")));
					 ProjectPlanningResourceRequest savedresourceRequest = projectPlanningResourceRequestDao.getProjectPlanningResourceRequestById(Long.valueOf(jsonObj.getString(i+"resourceRequestId")));
					 resourceRequest.setRequestUUID(savedresourceRequest.getRequestUUID());
					 resourceRequest.setRejectedReason(savedresourceRequest.getRejectedReason());
					 
					 if(!isNullOrEmpty(savedresourceRequest.getResourceStatus()) && savedresourceRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_APPROVED)){
						 resourceRequest.setResourceStatus(savedresourceRequest.getResourceStatus());
					 }
					 
					 updatedResourceRequests.add(Long.valueOf(jsonObj.getString(i+"resourceRequestId")));
				 }else{
					 resourceRequest.setRequestUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				 }
				 
				 
				 Candidate candidate = new Candidate();
				 candidate.setId(Long.valueOf(jsonObj.getString(i+"candidateId")));
				 resourceRequest.setCandidateId(candidate);
				 
				 
				 
				 if(jsonObj.has(i+"candidateFullName") && !isNullOrEmpty(jsonObj.getString(i+"candidateFullName"))){
					 resourceRequest.setCandidateName(jsonObj.getString(i+"candidateFullName"));
				 }
				 
				 if(jsonObj.has(i+"candidatePayRate") && !isNullOrEmpty(jsonObj.getString(i+"candidatePayRate"))){
					 resourceRequest.setPayRate(Double.valueOf(jsonObj.getString(i+"candidatePayRate")));
				 }
				 
				 if(jsonObj.has(i+"candidatePayRateCurrency") && !isNullOrEmpty(jsonObj.getString(i+"candidatePayRateCurrency"))){
					 resourceRequest.setPayRateCurrency(jsonObj.getString(i+"candidatePayRateCurrency"));
				 }
				 
				 if(jsonObj.has(i+"candidatePayRateType") && !isNullOrEmpty(jsonObj.getString(i+"candidatePayRateType"))){
					 resourceRequest.setPayRateType(jsonObj.getString(i+"candidatePayRateType"));
				 }
				 
				 if(jsonObj.has(i+"billRate") && !isNullOrEmpty(jsonObj.getString(i+"billRate"))){
					 resourceRequest.setBillRate(Double.valueOf(jsonObj.getString(i+"billRate")));
				 }else{
					 resourceRequest.setBillRate(null);
				 }
				 
				 if(jsonObj.has(i+"billRateCurrency") && !isNullOrEmpty(jsonObj.getString(i+"billRateCurrency"))){
					 resourceRequest.setBillRateCurrency(jsonObj.getString(i+"billRateCurrency"));
				 }
				 
				 if(jsonObj.has(i+"billRateRateType") && !isNullOrEmpty(jsonObj.getString(i+"billRateRateType"))){
					 resourceRequest.setBillRateType(jsonObj.getString(i+"billRateRateType"));
				 }
				 
				 if(jsonObj.has(i+"requestStartDate") && !isNullOrEmpty(jsonObj.getString(i+"requestStartDate"))){
					 Date requestStartDate = format.parse(jsonObj.getString(i+"requestStartDate"));
					 resourceRequest.setStartDate(requestStartDate);
				 }else{
					 resourceRequest.setStartDate(null);
				 }
				 
				 if(jsonObj.has(i+"requestEndDate") && !isNullOrEmpty(jsonObj.getString(i+"requestEndDate"))){
					 Date requestEndDate = format.parse(jsonObj.getString(i+"requestEndDate"));
					 resourceRequest.setEndDate(requestEndDate);
				 }else{
					 resourceRequest.setEndDate(null);
				 }
				 
				 if(jsonObj.has(i+"consultingType") && !isNullOrEmpty(jsonObj.getString(i+"consultingType"))){
					 resourceRequest.setConsultingType(jsonObj.getString(i+"consultingType"));
				 }else{
					 resourceRequest.setConsultingType(null);
				 }
				 
				 if(jsonObj.has(i+"estHours") && !isNullOrEmpty(jsonObj.getString(i+"estHours"))){
					 resourceRequest.setEstimatedHours(Double.valueOf(jsonObj.getString(i+"estHours")));
				 }else{
					 resourceRequest.setEstimatedHours(null);
				 }
				 
				 if(jsonObj.has("estimatedBudgetCurrencyType") && !isNullOrEmpty(jsonObj.getString("estimatedBudgetCurrencyType"))){
					 resourceRequest.setEstimatedBudgetCurrency(jsonObj.getString("estimatedBudgetCurrencyType"));
				 }
				 
				 if(jsonObj.has(i+"estBudget") && !isNullOrEmpty(jsonObj.getString(i+"estBudget"))){
					 resourceRequest.setEstimatedBudget(Double.valueOf(jsonObj.getString(i+"estBudget")));
				 }else{
					 resourceRequest.setEstimatedBudget(null);
				 }
				 
				 resourceRequest.setProjectPlanningRequestId(projectPlanningRequest);
				 resourceRequest.setStatus(Constants.RECORD_ACTIVE);
				 resourceRequest.setCreatedBy(loginUser);
				 resourceRequest.setUpdatedBy(loginUser);
				 resourceRequest.setCreatedDate(new Date());
				 resourceRequest.setUpdatedDate(new Date());
				 resourceRequestSet.add(resourceRequest);
			 }
		 }
		 
		 
		 if(null != projectPlanningRequest.getId()){
			 updatedResourceRequests.retainAll(resourceRequestIds);
			 resourceRequestIds.removeAll(updatedResourceRequests);
		 }
		 
		 return resourceRequestSet;
	}

	
	private Set<ProjectPlanningNewResourceRequest> getNewResourceRequestList(JSONObject jsonObj, User loginUser,ProjectPlanningRequest projectPlanningRequest,List<Long> resourceRequestIds) throws Exception {
		 Set<ProjectPlanningNewResourceRequest> resourceRequestSet = new HashSet<>();
		 List<Long> updatedNewResourceRequests = new ArrayList<Long>();
		 String pattern = "dd-MM-yyyy";
	     SimpleDateFormat format = new SimpleDateFormat(pattern);
		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxRecCount"));
		 for(int i=1;i<=maxRowCount;i++){
			 if(jsonObj.has("noOfResources"+i) && !isNullOrEmpty(jsonObj.getString("noOfResources"+i))){
				 ProjectPlanningNewResourceRequest resourceRequest = new ProjectPlanningNewResourceRequest();
				 resourceRequest.setNoOfResource(jsonObj.getInt("noOfResources"+i));
				 
				 if(jsonObj.has("resourceRequestId"+i) &&  null != jsonObj.getString("resourceRequestId"+i) && !"".equalsIgnoreCase(jsonObj.getString("resourceRequestId"+i))){
					 resourceRequest.setId(Long.valueOf(jsonObj.getString("resourceRequestId"+i)));
					 updatedNewResourceRequests.add(Long.valueOf(jsonObj.getString("resourceRequestId"+i)));	 
				 }
				 
				if(jsonObj.getString("role"+i).equalsIgnoreCase("Other")){
					resourceRequest.setRoleId(null);
					 resourceRequest.setRoleName(jsonObj.getString("role"+i));
					 if(jsonObj.has("customRole"+i) && !isNullOrEmpty(jsonObj.getString("customRole"+i))){
						 resourceRequest.setOtherRole(jsonObj.getString("customRole"+i));
					 }
					 
				}else if(jsonObj.has("role"+i) && !isNullOrEmpty(jsonObj.getString("role"+i))){
					if(!jsonObj.getString("role"+i).equalsIgnoreCase("Other")){
						Roles roles = rolesServiceImpl.getRolesById(Long.parseLong(jsonObj.getString("role"+i)));
						 resourceRequest.setRoleId(roles);
						 resourceRequest.setRoleName(roles.getDisplayName());	
					}
					 
				 }
			
				 if(jsonObj.has("min"+i) && !isNullOrEmpty(jsonObj.getString("min"+i))){
					 resourceRequest.setMinExperience(jsonObj.getInt("min"+i));
				 }
				 if(jsonObj.has("max"+i) && !isNullOrEmpty(jsonObj.getString("max"+i))){
					 resourceRequest.setMaxExperience(jsonObj.getInt("max"+i));
				 }
				 if(jsonObj.has("primarySkill"+i) && !isNullOrEmpty(jsonObj.getString("primarySkill"+i))){
					 resourceRequest.setSkills(jsonObj.getString("primarySkill"+i));
				 }
				 
				 if(jsonObj.has("currencyCode"+i) && !isNullOrEmpty(jsonObj.getString("currencyCode"+i))){
					 resourceRequest.setRateCurrency(jsonObj.getString("currencyCode"+i));
				 }
				 if(jsonObj.has("billRate"+i) && !isNullOrEmpty(jsonObj.getString("billRate"+i))){
					 resourceRequest.setBillRate(jsonObj.getString("billRate"+i));
				 }
				 if(jsonObj.has("payRate"+i) && !isNullOrEmpty(jsonObj.getString("payRate"+i))){
					 resourceRequest.setPayRate(jsonObj.getString("payRate"+i));
				 }
				 if(jsonObj.has("rateType"+i) && !isNullOrEmpty(jsonObj.getString("rateType"+i))){
					 resourceRequest.setRateType(jsonObj.getString("rateType"+i));
				 }
				 if(jsonObj.has("startDate"+i) && !isNullOrEmpty(jsonObj.getString("startDate"+i))){
					 Date startDate = format.parse(jsonObj.getString("startDate"+i));
					 resourceRequest.setStartDate(startDate);
				 }
				 if(jsonObj.has("endDate"+i) && !isNullOrEmpty(jsonObj.getString("endDate"+i))){
					 Date endDate = format.parse(jsonObj.getString("endDate"+i));
					 resourceRequest.setEndDate(endDate);
				 }
				 if(jsonObj.has("estimatedHours"+i) && !isNullOrEmpty(jsonObj.getString("estimatedHours"+i))){
					 resourceRequest.setEstimatedHours(jsonObj.getDouble("estimatedHours"+i));
				 }
				 if(jsonObj.has("estimatedBudget"+i) && !isNullOrEmpty(jsonObj.getString("estimatedBudget"+i))){
					 resourceRequest.setEstimatedBudget(jsonObj.getDouble("estimatedBudget"+i));
				 }
				 if(jsonObj.has("budgetCurrency"+i) && !isNullOrEmpty(jsonObj.getString("budgetCurrency"+i))){
					 resourceRequest.setEstimatedBudgetCurrency(jsonObj.getString("budgetCurrency"+i));
				 }
				 if(jsonObj.has("consulting"+i) && !isNullOrEmpty(jsonObj.getString("consulting"+i))){
					 resourceRequest.setConsultingType(jsonObj.getString("consulting"+i));
				 }
				 resourceRequest.setProjectPlanningRequestId(projectPlanningRequest);
				 resourceRequest.setStatus(Constants.RECORD_ACTIVE);
				 resourceRequest.setCreatedBy(loginUser);
				 resourceRequest.setUpdatedBy(loginUser);
				 resourceRequest.setCreatedDate(new Date());
				 resourceRequest.setUpdatedDate(new Date());
				 resourceRequestSet.add(resourceRequest);
				 }
		 }
		 if(null != projectPlanningRequest.getId()){
			 updatedNewResourceRequests.retainAll(resourceRequestIds);
			 resourceRequestIds.removeAll(updatedNewResourceRequests);
		 }
		 return resourceRequestSet;
	}
	@Override
	public List<ProjectPlanningRequest> getProjectPlanningRequestByProjectId(Long projectId) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestByProjectId(projectId);
	}

	@Override
	public Integer getProjectPlanningRequestCountByProjectId(Long projectId) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestCountByProjectId(projectId);
	}

	@Override
	public ProjectPlanningRequest getProjectPlanningRequestByRequestUUID(String requestUUID) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestByRequestUUID(requestUUID);
	}

	@Override
	public ProjectPlanningRequest getProjectPlanningRequestById(Long id) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestById(id);
	}

	@Override
	public List<ProjectPlanningRequest> getProjectPlanningRequestByApproverId(Long approverUserId, Long orgLocationId,String requestStatus)
			throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestByApproverId(approverUserId, orgLocationId,requestStatus);
	}

	@Override
	public ProjectPlanningRequest storeProjectPlanningRequest(ProjectPlanningRequest projectPlanningRequest)
			throws Exception {
		return projectPlanningRequestDao.saveProjectPlanningRequest(projectPlanningRequest);
	}

	@Override
	public List<ProjectPlanningRequest> getProjectPlanningRequestByApproverIdAndOrgLocationId(Long approverUserId,
			Long orgLocationId) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestByApproverIdAndOrgLocationId(approverUserId, orgLocationId);
	}
}
