package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectPlanningRequestDao;
import com.bis.sourcelead.dao.ProjectPlanningResourceRequestDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
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
	ProjectPlanningResourceRequestDao projectPlanningResourceRequestDao;
	
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
		if(projectPlanningRequest.getId() != null){
			 resourceRequestIds = projectPlanningResourceRequestDao.getProjectPlanningResourceRequestIdsByRequestId(projectPlanningRequest.getId());
		}
		Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = getResourceRequestList(jsonObj,loginUser,projectPlanningRequest,resourceRequestIds);
		projectPlanningRequest.setProjectPlanningResourceRequestList(projectPlanningResourceRequestList);
		projectPlanningRequest.setRequestedResources(projectPlanningResourceRequestList.size());
		projectPlanningRequest.setAllocatedResources(0);
		
		if(!isNullOrEmpty(saveType) && saveType.equalsIgnoreCase("Draft")){
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_DRAFT);
		}else{
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL);
		}
		
		projectPlanningRequestDao.saveProjectPlanningRequest(projectPlanningRequest);
		
	}
	
	
	private Set<ProjectPlanningResourceRequest> getResourceRequestList(JSONObject jsonObj, User loginUser,ProjectPlanningRequest projectPlanningRequest,List<Long> resourceRequestIds) throws Exception {
		
		 Set<ProjectPlanningResourceRequest> resourceRequestSet = new HashSet<>();
		 
		 String pattern = "dd-MM-yyyy";
	     SimpleDateFormat format = new SimpleDateFormat(pattern);
		 
		 int maxRowCount = Integer.parseInt(jsonObj.getString("maxTsRowNum"));
		 
		 for(int i=1;i<=maxRowCount;i++){
			 if(jsonObj.has(i+"candidateId") && !isNullOrEmpty(jsonObj.getString(i+"candidateId"))){
				 ProjectPlanningResourceRequest resourceRequest = new ProjectPlanningResourceRequest();
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
				 }
				 
				 if(jsonObj.has(i+"requestEndDate") && !isNullOrEmpty(jsonObj.getString(i+"requestEndDate"))){
					 Date requestEndDate = format.parse(jsonObj.getString(i+"requestEndDate"));
					 resourceRequest.setEndDate(requestEndDate);
				 }
				 
				 if(jsonObj.has(i+"consultingType") && !isNullOrEmpty(jsonObj.getString(i+"consultingType"))){
					 resourceRequest.setConsultingType(jsonObj.getString(i+"consultingType"));
				 }
				 
				 if(jsonObj.has(i+"estHours") && !isNullOrEmpty(jsonObj.getString(i+"estHours"))){
					 resourceRequest.setEstimatedHours(Double.valueOf(jsonObj.getString(i+"estHours")));
				 }
				 
				 if(jsonObj.has("estimatedBudgetCurrencyType") && !isNullOrEmpty(jsonObj.getString("estimatedBudgetCurrencyType"))){
					 resourceRequest.setEstimatedBudgetCurrency(jsonObj.getString("estimatedBudgetCurrencyType"));
				 }
				 
				 if(jsonObj.has(i+"estBudget") && !isNullOrEmpty(jsonObj.getString(i+"estBudget"))){
					 resourceRequest.setEstimatedBudget(Double.valueOf(jsonObj.getString(i+"estBudget")));
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

}
