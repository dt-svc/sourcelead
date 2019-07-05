package com.bis.sourcelead.rest.performance;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.AppraisalJobsSubmissionDetailsDTO;
import com.bis.sourcelead.dto.performance.AppraisalProjectsTasksDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackCategoryDTO;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackDTO;
import com.bis.sourcelead.dto.performance.PerformanceGeneralTasksDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsAssignToDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceJobsDetailsDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.performance.PerformanceAppraisalService;
import com.bis.sourcelead.service.performance.PerformanceAppraisalUserFeedbackService;
import com.bis.sourcelead.service.performance.PerformanceFeedbackService;
import com.bis.sourcelead.service.performance.PerformanceGoalsAssignToService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class PerformanceAppraisalRestController {

	@Autowired
	PerformanceAppraisalService performanceAppraisalService;
	
	@Autowired
	PerformanceAppraisalUserFeedbackService performanceAppraisalUserFeedbackService;
	
	@Autowired
	PerformanceGoalsAssignToService performanceGoalsAssignToService;
	
	@Autowired
	PerformanceFeedbackService performanceFeedbackService;
	
	@Value("${static.path}")
	private String staticPath;
	
	@RequestMapping(value = "/getAssignedMyAppraisalList", method = RequestMethod.GET)
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,HttpServletRequest request) throws Exception {
		
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		 
		String[] tableColumns = { "name", "appraisalStartDate", "appraisalProcessStartDate", "selfAppraisalStartDate", "status" };
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (performanceAppraisalTemplateDTO.getOrder() != null && !performanceAppraisalTemplateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(performanceAppraisalTemplateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = performanceAppraisalTemplateDTO.getOrder().get(0).get("dir");
		}
		performanceAppraisalTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		performanceAppraisalTemplateDTO.setSortOrder(sortColumnOrder);
		ResponseList<PerformanceAppraisalTemplateDTO> myAppraisalAssignedList = performanceAppraisalService.getAssignedMyAppraisalList(performanceAppraisalTemplateDTO, user.getId(), orgLocation.getId(), orgLocation.getDateFormat());
		return myAppraisalAssignedList;
	}
	
	
	@RequestMapping(value = "/getAssignedPeersAppraisalList", method = RequestMethod.GET)
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedPeersAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,HttpServletRequest request) throws Exception {
		
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		 
		String[] tableColumns = { "appraisalAssignedUser", "name", "appraisalStartDate", "appraisalProcessStartDate", "peerReviewStartDate", "status" };
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (performanceAppraisalTemplateDTO.getOrder() != null && !performanceAppraisalTemplateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(performanceAppraisalTemplateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = performanceAppraisalTemplateDTO.getOrder().get(0).get("dir");
		}
		performanceAppraisalTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		performanceAppraisalTemplateDTO.setSortOrder(sortColumnOrder);
		ResponseList<PerformanceAppraisalTemplateDTO> myAppraisalAssignedList = performanceAppraisalService.getAssignedPeersAppraisalList(performanceAppraisalTemplateDTO, user.getId(), orgLocation.getId(), orgLocation.getDateFormat());
		return myAppraisalAssignedList;
	}
	
	
        @RequestMapping(value = "/getSelfAppraisalTemplateDetails", method = RequestMethod.GET)
		public Response<PerformanceAppraisalDetailsDTO> getSelfAppraisalTemplateDetails(HttpServletRequest request, 
				@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
        	
        	 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     		 
			 Response<PerformanceAppraisalDetailsDTO> resObj = new Response<>();
			 try {
				 PerformanceAppraisalDetailsDTO appraisalDetailsDTO =  performanceAppraisalService.getSelfAppraisalTemplateDetails(assignedUUID, orgLocation.getDateFormat());
				 resObj.setObject(appraisalDetailsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
        
        
        

	    @RequestMapping(value = "/getAssignedFeedbackTemplateList", method = RequestMethod.GET)
		public ResponseList<PerformanceFeedbackTemplateDTO> getAssignedFeedbackTemplateList(HttpServletRequest request, 
				@RequestParam(value = "assignedUUID", required = true) String assignedUUID,@RequestParam(value = "templateIds", required = true) String templateIds){
	    	 ResponseList<PerformanceFeedbackTemplateDTO> resObj = new ResponseList<>();
			 try {
				 List<PerformanceFeedbackTemplateDTO> feedbackTemplateDTOList =  performanceAppraisalService.getPerformanceAssignedFeedbackTemplateDTOList(assignedUUID,templateIds);
				 resObj.setData(feedbackTemplateDTOList);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	    
		
	    @RequestMapping(value = "/loadPerformanceFeedbackTemplate" , method = RequestMethod.GET)
	    public PerformanceFeedbackTemplateDTO loadPerformanceFeedbackTemplate(@RequestParam(value="templateUUID", required=false) String templateUUID,
	    		@RequestParam(value="assignedUUID", required=false) String assignedUUID,HttpServletRequest request) throws Exception {
	 		
	       User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	   PerformanceFeedbackTemplateDTO templateDTO  = performanceAppraisalService.getPerformanceSubmittedFeedbackTemplateDTOByUUID(templateUUID,assignedUUID,user.getId());
	 	   return templateDTO;
	     
	    }
	    
	    
	    @RequestMapping(value = { "/saveSubmittedFeedbackTemplate", "/rest/saveSubmittedFeedbackTemplate" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceFeedbackCategoryDTO> storeProject(PerformanceFeedbackCategoryDTO performanceFeedbackCategoryDTO,HttpServletRequest request) throws Exception {
			Response<PerformanceFeedbackCategoryDTO> response = new  Response<>();
			try {
				
				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 		 
		 		 performanceFeedbackCategoryDTO.setOrgId(org.getId());
		 		 performanceFeedbackCategoryDTO.setUserId(user.getId());
		 		 performanceFeedbackCategoryDTO.setOrgLocationId(orgLocation.getId());
				 performanceFeedbackCategoryDTO.setStaticPath(staticPath);
				 performanceAppraisalUserFeedbackService.storePerformanceAppraisalUserFeedback(performanceFeedbackCategoryDTO);
				 response.setResponse(Result.SUCCESS.name());
				 response.setResponseMessage(Result.SUCCESS.getValue());
			
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}
	    
	    @RequestMapping(value = "/getAssignedGoalsDetailsList", method = RequestMethod.GET)
		public Response<PerformanceGoalsDTO> getAssignedGoalsDetailsList(HttpServletRequest request, 
				@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
	    	 Response<PerformanceGoalsDTO> resObj = new  Response<>();
			 try {
				 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 PerformanceGoalsDTO performanceGoalsDTO =  performanceAppraisalService.getperformanceGoalsDetails(assignedUUID, user.getId());
				 resObj.setObject(performanceGoalsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	    
	    @RequestMapping(value = "/getAssignedFeedbackProjectTasksDetailsList", method = RequestMethod.GET)
		public Response<ProjectassignTasksDTO> getAssignedFeedbackProjectTasksDetailsList(HttpServletRequest request, 
				@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
	    	 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    	Response<ProjectassignTasksDTO> resObj = new  Response<>();
	    	 ProjectassignTasksDTO taskDTO = null;
			 try {
				 List<ProjectassignTasksDTO> performanceTasksDTOList =  performanceAppraisalService.getPerformanceAssignedProjectAssignedTasksDTOList(assignedUUID,orgLocation.getDateFormat());
				 if(performanceTasksDTOList != null && !performanceTasksDTOList.isEmpty()) {
					 Map<String, List<ProjectassignTasksDTO>> map = performanceTasksDTOList.stream().collect(Collectors.groupingBy(ProjectassignTasksDTO::getTaskStatus,Collectors.toList()));
					  Map<BigInteger, List<ProjectassignTasksDTO>> canMap = performanceTasksDTOList.stream().collect(Collectors.groupingBy(ProjectassignTasksDTO::getCandidateId,Collectors.toList()));
						  for (Map.Entry<BigInteger, List<ProjectassignTasksDTO>> entry : canMap.entrySet())  {
							  taskDTO = new ProjectassignTasksDTO();
							  Map<String,String> innerMap = new HashMap<>();
							  for (Map.Entry<String, List<ProjectassignTasksDTO>> canEntry : map.entrySet())  {
							  List<ProjectassignTasksDTO> s = entry.getValue().stream().filter(pd->canEntry.getKey().equalsIgnoreCase(pd.getTaskStatus())).collect(Collectors.toList());
							  innerMap.put(canEntry.getKey(), String.valueOf(s.size()));
							  }
							  taskDTO.setAssignMembersMap(innerMap);
							  taskDTO.setTotalTasks(performanceTasksDTOList.size());
						  }
						  
						  
						  Map<String, List<ProjectassignTasksDTO>> projectMap = performanceTasksDTOList.stream().collect(Collectors.groupingBy(ProjectassignTasksDTO::getTaskStatus,Collectors.toList()));
						  Map<BigInteger, List<ProjectassignTasksDTO>> projMap = performanceTasksDTOList.stream().collect(Collectors.groupingBy(ProjectassignTasksDTO::getProjectId,Collectors.toList()));
						  List<AppraisalProjectsTasksDTO> projectTasksList = new CopyOnWriteArrayList<AppraisalProjectsTasksDTO>(); 
						  for (Map.Entry<BigInteger, List<ProjectassignTasksDTO>> entry : projMap.entrySet())  {
								  AppraisalProjectsTasksDTO projectDTO = new AppraisalProjectsTasksDTO();
								  Map<String,String> innerMap = new HashMap<>();
								  ProjectassignTasksDTO dto = entry.getValue().get(0);
								  projectDTO.setProjectName(dto.getProjectName());
								  projectDTO.setTotalProjectTasks(entry.getValue().size());
								  projectDTO.setStartDate(dto.getFromDate());
								  projectDTO.setEndDate(dto.getToDate());
								  for (Map.Entry<String, List<ProjectassignTasksDTO>> projectEntry : projectMap.entrySet())  {
								  List<ProjectassignTasksDTO> s = entry.getValue().stream().filter(pd->projectEntry.getKey().equalsIgnoreCase(pd.getTaskStatus())).collect(Collectors.toList());
								  innerMap.put(projectEntry.getKey(), String.valueOf(s.size()));
								  }
								  projectDTO.setAssignProjectsMap(innerMap);
								  projectTasksList.add(projectDTO);
							  }
						  taskDTO.setAppraisalProjectsTasksList(projectTasksList); 
				 }
				 
				 resObj.setObject(taskDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 e.printStackTrace();
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	    
	    
	    
	    @RequestMapping(value = "/getAssignedFeedbackJobsDetailsList", method = RequestMethod.GET)
		public Response<PerformanceJobsDetailsDTO> getAssignedFeedbackJobsDetailsList(HttpServletRequest request, 
				@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
	    	 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    	Response<PerformanceJobsDetailsDTO> resObj = new  Response<PerformanceJobsDetailsDTO>();
	    	 PerformanceJobsDetailsDTO performanceJobsDetailsDTO = null;
			 try {
				 List<PerformanceJobsDetailsDTO> performanceJobsDetailsDTOList =  performanceAppraisalService.getPerformanceJobsDetails(assignedUUID,orgLocation.getDateFormat());
				 if(performanceJobsDetailsDTOList != null && !performanceJobsDetailsDTOList.isEmpty()) {
					 performanceJobsDetailsDTO = new PerformanceJobsDetailsDTO();	
						
						 Integer submittedCandidatesCount = 0;
						 Integer offerMadeCount = 0;
						 Integer clientOfferMadeCount = 0;
						 Integer offerAcceptedCount = 0;
						 Integer clientOfferAcceptedCount = 0;
						 Integer joinedCount = 0;
						
					 for(PerformanceJobsDetailsDTO jobsDTO : performanceJobsDetailsDTOList) {
						if(jobsDTO.getSubmissionStage().equalsIgnoreCase("SUBMISSION")) {
							submittedCandidatesCount = submittedCandidatesCount+1;
						}
						else if(jobsDTO.getSubmissionStage().equalsIgnoreCase("OFFERMADE")) {
							offerMadeCount = offerMadeCount+1;
						}
						else if(jobsDTO.getSubmissionStage().equalsIgnoreCase("CLIENT_OFFERMADE")) {
							clientOfferMadeCount = clientOfferMadeCount+1;
						}
						else if(jobsDTO.getSubmissionStage().equalsIgnoreCase("OFFERACCEPTED")) {
							offerAcceptedCount = offerAcceptedCount+1;
						}
						else if((jobsDTO.getFromSubmissionStage().equalsIgnoreCase("client_offer_made") || jobsDTO.getFromSubmissionStage().equalsIgnoreCase("CLIENT_OFFERMADE")) 
								&& jobsDTO.getSubmissionStage().equalsIgnoreCase("CLIENT_OFFERACCEPTED")) {
							clientOfferAcceptedCount = clientOfferAcceptedCount+1;
						}
						else if(jobsDTO.getSubmissionStage().equalsIgnoreCase("JOINED")) {
							joinedCount = joinedCount+1;
						}
					}
					 performanceJobsDetailsDTO.setSubmittedCandidatesCount(submittedCandidatesCount);
					 performanceJobsDetailsDTO.setOfferMadeCount(offerMadeCount);
					 performanceJobsDetailsDTO.setOfferAcceptedCount(offerAcceptedCount);
					 performanceJobsDetailsDTO.setJoinedCount(joinedCount);
					 performanceJobsDetailsDTO.setClientOfferMadeCount(clientOfferMadeCount);
					 performanceJobsDetailsDTO.setClientOfferAcceptedCount(clientOfferAcceptedCount);
					 
					 Map<BigInteger, List<PerformanceJobsDetailsDTO>> requirementMap = performanceJobsDetailsDTOList.stream().collect(Collectors.groupingBy(PerformanceJobsDetailsDTO::getRequirementId,Collectors.toList()));
					 List<AppraisalJobsSubmissionDetailsDTO> jobDetailsList = new CopyOnWriteArrayList<AppraisalJobsSubmissionDetailsDTO>(); 
					 for (Map.Entry<BigInteger, List<PerformanceJobsDetailsDTO>> entry : requirementMap.entrySet())  {
						 AppraisalJobsSubmissionDetailsDTO jobsDetailsDTO = new AppraisalJobsSubmissionDetailsDTO();
						  List<PerformanceJobsDetailsDTO> jobEntrydtoList = entry.getValue();
						  PerformanceJobsDetailsDTO dto = entry.getValue().get(0);
						  jobsDetailsDTO.setRequirementName(dto.getRequirementName());
						  jobsDetailsDTO.setClientName(dto.getClientName());
						  if(jobEntrydtoList != null && !jobEntrydtoList.isEmpty()) {
							  
							  submittedCandidatesCount = 0;
							  offerMadeCount = 0;
							  offerAcceptedCount = 0;
							  joinedCount = 0;
							  clientOfferMadeCount = 0;
							  clientOfferAcceptedCount = 0;
							   
							  for(PerformanceJobsDetailsDTO jobDetails : jobEntrydtoList) {
								  							  
								  if(jobDetails.getSubmissionStage().equalsIgnoreCase("SUBMISSION")) {
										submittedCandidatesCount = submittedCandidatesCount+1;
									}
									else if(jobDetails.getSubmissionStage().equalsIgnoreCase("OFFERMADE")) {
										offerMadeCount = offerMadeCount+1;
									}
									else if(jobDetails.getSubmissionStage().equalsIgnoreCase("CLIENT_OFFERMADE")) {
										clientOfferMadeCount = clientOfferMadeCount+1;
									}
									else if(jobDetails.getSubmissionStage().equalsIgnoreCase("OFFERACCEPTED")) {
										offerAcceptedCount = offerAcceptedCount+1;
									}
									else if((jobDetails.getFromSubmissionStage().equalsIgnoreCase("client_offer_made") || jobDetails.getFromSubmissionStage().equalsIgnoreCase("CLIENT_OFFERMADE")) 
											&& jobDetails.getSubmissionStage().equalsIgnoreCase("CLIENT_OFFERACCEPTED")) {
										clientOfferAcceptedCount = clientOfferAcceptedCount+1;
									}
									else if(jobDetails.getSubmissionStage().equalsIgnoreCase("JOINED")) {
										joinedCount = joinedCount+1;
									}
								  
							    }
							    
							     jobsDetailsDTO.setSubmittedCandidatesCount(submittedCandidatesCount);
								 jobsDetailsDTO.setOfferMadeCount(offerMadeCount);
								 jobsDetailsDTO.setOfferAcceptedCount(offerAcceptedCount);
								 jobsDetailsDTO.setJoinedCount(joinedCount);
								 jobsDetailsDTO.setClientOfferMadeCount(clientOfferMadeCount);
								 jobsDetailsDTO.setClientOfferAcceptedCount(clientOfferAcceptedCount);
								 
								 jobDetailsList.add(jobsDetailsDTO);
						  }
						     
					 }
					 performanceJobsDetailsDTO.setAppraisalJobDetailsList(jobDetailsList);
				 }
				 
				
				 
				 resObj.setObject(performanceJobsDetailsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 e.printStackTrace();
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	    
	    
	    
	    
	    @RequestMapping(value = "/getAssignedFeedbackGeneralTaskDetailsList", method = RequestMethod.GET)
	  		public Response<PerformanceGeneralTasksDetailsDTO> getAssignedFeedbackGeneralTaskDetailsList(HttpServletRequest request, 
	  				@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
	    
	    	 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		     Response<PerformanceGeneralTasksDetailsDTO> resObj = new  Response<PerformanceGeneralTasksDetailsDTO>();
		     try {
				PerformanceGeneralTasksDetailsDTO taskDetailsDTO = performanceAppraisalService.getGeneralTasksDetails(assignedUUID, orgLocation.getDateFormat());
				 resObj.setObject(taskDetailsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
		     } catch (Exception e) {
				e.printStackTrace();
			}
	    	return resObj;
	    }
	    
	    @RequestMapping(value = { "/saveAssignedPerformanceFeedback", "/rest/saveAssignedPerformanceFeedback" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceFeedbackDTO> saveAssignedPerformanceGoalsScore(PerformanceFeedbackDTO performanceFeedbackDTO,HttpServletRequest request) throws Exception {
	    	 Response<PerformanceFeedbackDTO> resObj = new  Response<PerformanceFeedbackDTO>();
	    	try {
				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 		 performanceFeedbackDTO.setOrgId(org.getId());
		 		 performanceFeedbackDTO.setUserId(user.getId());
		 		 performanceFeedbackDTO.setOrgLocationId(orgLocation.getId());
		 		 performanceFeedbackService.saveAssignedPerformanceFeedback(performanceFeedbackDTO);
		 		 resObj.setResponse(Result.SUCCESS.name());
		 		 resObj.setResponseMessage(Result.SUCCESS.getValue());
			
			}catch (Exception e) {
				e.printStackTrace();
				resObj.setResponse(Result.AWKWARD.name());
				resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			return resObj;
		}
	    
	    
	    @RequestMapping(value = "/getAppraisalFeedbackDetails", method = RequestMethod.GET)
  		public Response<PerformanceFeedbackDTO> getAppraisalFeedbackDetails(HttpServletRequest request, 
  				@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
    
    	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	     Response<PerformanceFeedbackDTO> resObj = new  Response<PerformanceFeedbackDTO>();
	     try {
	    	 PerformanceFeedbackDTO performanceFeedbackDTO = performanceAppraisalService.getPerformanceFeedbackDetails(assignedUUID,user.getId());
	    	 resObj.setObject(performanceFeedbackDTO);
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
	     } catch (Exception e) {
			e.printStackTrace();
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
    	return resObj;
    }
	    
	    
    @RequestMapping(value = "/getJsonCategoriesString", method = RequestMethod.GET)
  		public String getJsonCategoriesString(@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
    
	     Response<PerformanceFeedbackDTO> resObj = new  Response<PerformanceFeedbackDTO>();
	     String jsonCategoriesString = null;
	     try {
	    	 jsonCategoriesString = performanceAppraisalService.getJsonCategoriesString(assignedUUID);
	     } catch (Exception e) {
			e.printStackTrace();
		}
    	return jsonCategoriesString;
    }
}
