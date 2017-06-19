package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntryDocumentsService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class AddExpensesRestController {
	private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ProjectsService projectsService; 
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	ExpensesEntryDocumentsService expensesEntryDocumentsService;
	
	@RequestMapping(value = "/getClientByProjectMemberId", method = RequestMethod.GET)
	public @ResponseBody String getClientByProjectMemberId(@RequestParam(value = "projectMemberId", required = false) String projectMemberId) throws Exception {
		if(null != projectMemberId && !"".equalsIgnoreCase(projectMemberId)){
			ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(Long.parseLong(projectMemberId));
			if(null != projectMembers.getProjects()){
				Projects project = projectMembers.getProjects();
				if(null != project.getOrgRelationId()){
					 OrgRelations orgRelation = orgRelationsService.getOrgRelationById(project.getOrgRelationId().getId());
					 project.setExpensesClientName(orgRelation.getResponderLocationId().getOrgLocationName());
					}
					 project.setStartDateStr(Constants.DMY.format(project.getStartDate()));
					 if(null != project.getEndDate() && !"".equalsIgnoreCase(project.getEndDate().toString())){
					 project.setEndDateStr(Constants.DMY.format(project.getEndDate()));
					 }
				 return commonUtil.toJSON(project);
			}
	    }
		return projectMemberId;
    }
	
	@RequestMapping(value = "/withDrawExpense", method = RequestMethod.POST)
	public @ResponseBody void withDrawExpense(@RequestParam(value="expenseId", required=false) Long expenseId) throws Exception {
			if(null != expenseId){
				expensesApprovalService.updateExpenseStatusByExpenseId(expenseId,Submission_Status.DRAFT.getValue());
			}
	}
	
	
	@RequestMapping(value = "/acceptOrRejectReopenExpenseBySuperUser", method = RequestMethod.POST)
	public @ResponseBody void acceptOrRejectReopenExpenseBySuperUser(@RequestParam(value="expenseId", required=false) Long expenseId,
			@RequestParam(value="reOpenedUserId", required=false) Long reOpenedUserId,
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) throws Exception {
			
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User reopenedUser = userService.getUserById(reOpenedUserId);
			if(expenseId != null && null != type && "accept".equalsIgnoreCase(type)){
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,type,Constants.YES);
			    //send mail & notification logic
			  	//submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,type,reopenedUser);
			  	
			}else if(expenseId != null &&  null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason)){
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,type,Constants.YES);
			    //send mail & notification logic
			  	//submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,type,reopenedUser);
			}
		
	}
	
	
	@RequestMapping(value = "/notifyReopenExpense", method = RequestMethod.POST)
	public @ResponseBody void notifyReopenExpense(@RequestParam(value="expenseId", required=false) Long expenseId,
			@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) throws Exception {
			
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    
		    String isSuperUser = Constants.NO;
		    if(null != orgLoc.getUser() && null != orgLoc.getUser().getId() && orgLoc.getUser().getId().longValue() == loginUser.getId().longValue()){
		    	isSuperUser = Constants.YES;
		    }
		    
			DateFormat format = Constants.DF_DMY;
			if(expenseId != null){
			 	
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,"notify",isSuperUser);
			
			    //send mail & notification logic
			  	/*if(isSuperUser.equalsIgnoreCase(Constants.NO)){
			  		submissionApproverHierarchyService.sendReopenTimeSheetMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,totalHours);
			  	}*/
			  	
			}
		
	}
	
	@RequestMapping(value = "/getClientByProjectId", method = RequestMethod.GET)
	public @ResponseBody String getClientByProjectId(@RequestParam(value = "projectId", required = false) String projectId) throws Exception {
		if(null != projectId && !"".equalsIgnoreCase(projectId)){
			Projects project =	projectsService.getProjectById(Long.parseLong(projectId));
			if(null != project){
				if(null != project.getOrgRelationId()){
					 OrgRelations orgRelation = orgRelationsService.getOrgRelationById(project.getOrgRelationId().getId());
					 project.setExpensesClientName(orgRelation.getResponderLocationId().getOrgLocationName());
					}
					 project.setStartDateStr(Constants.DMY.format(project.getStartDate()));
					 if(null != project.getEndDate() && !"".equalsIgnoreCase(project.getEndDate().toString())){
					 project.setEndDateStr(Constants.DMY.format(project.getEndDate()));
					 }
				 return commonUtil.toJSON(project);
			}
	    }
		return projectId;
    }
	
	
	@RequestMapping(value = "/storeAddExpensesData",method = RequestMethod.POST)
	public @ResponseBody String storeAddExpensesData(MultipartHttpServletRequest request,@RequestParam(value="status", required=false) String status) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		addExpensesService.storeAddExpensesData(jsonObj,loginUser,orgLoc,request,status);
		return "DataSaved";
}
	
	 @RequestMapping(value = "/getMembersForSelectedProject",  method = RequestMethod.GET)
	 public @ResponseBody  String getMembersForSelectedProject(
			@RequestParam(value="projectId", required=false) Long projectId, HttpServletRequest request) throws Exception {
	 	
		 	Collection<ProjectMembers> projectMembers = null;
		 	if(projectId != null){
				projectMembers = projectMembersService.getAllProjectMembersByProjectId(projectId);
				
				if(null != projectMembers && projectMembers.size() > 0){
					for(ProjectMembers pm : projectMembers){
						if(null != pm.getEmployee().getUser()){
							StringBuilder eName = new StringBuilder();
							
							if(null != pm.getEmployee().getUser() && (pm.getEmployee().getUser().getFirstName() != null && pm.getEmployee().getUser().getFirstName() != "" && pm.getEmployee().getUser().getLastName() != null && pm.getEmployee().getUser().getLastName() != "" )){
								pm.setEmpName(eName.append(pm.getEmployee().getUser().getFirstName()).append(" ").append(pm.getEmployee().getUser().getLastName()).toString());
							}else{
								pm.setEmpName(eName.append(pm.getEmployee().getUser().getFirstName()).toString());
							}
							
						}
					}
				}
		 	}
		 	return commonUtil.toJSON(projectMembers);
	}
	 
	 
	 @RequestMapping(value = "/downloadExpensesEntryDocument", method = RequestMethod.GET)
	    public void downloadExpensesEntryDocument(@RequestParam(value="entryDocumentId", required=false) Long entryDocumentId ,HttpServletResponse response) throws Exception {
			
			
		 ExpensesEntryDocuments	expensesEntryDocument =	expensesEntryDocumentsService.getExpensesEntryDocumentById(entryDocumentId);	
			    
			    response.setContentType("application/octet-stream");
			    StringBuilder fileName = new StringBuilder();
		        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(expensesEntryDocument.getFileName()).append("\"").toString());
				InputStream inputStream = new ByteArrayInputStream(expensesEntryDocument.getDocFile());
				try {
					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();

				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				} 

		} 
	
}
