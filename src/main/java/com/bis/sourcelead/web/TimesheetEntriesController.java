package com.bis.sourcelead.web;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
 

@RestController 
public class TimesheetEntriesController {
	
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public TimesheetSubmissionsDao timesheetSubmissionsDao;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	
	@Autowired
	public ProjectTasksService projectTasksService;
	
	@Autowired
	public ProjectMembersService projectMembersService;
	
	@Autowired
	public TimesheetEntriesDao timesheetEntriesDao;
	
	
	@Autowired
	public ProjectsService projectsService;
	
	@Autowired
	public ProjectTasksService tasksService;
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;
	
	
	@RequestMapping(value = "/dynamicTimesheetpopUp", method = RequestMethod.GET )
	public @ResponseBody String dynamicTimesheetpopUp() throws Exception {
		
		Long projectMemberId = 1L;
		Long userId = 1L;
		Collection<TimesheetSubmissions>  timesheetSubmissionsList = timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(projectMemberId, userId);
		
		StringBuilder sb = new StringBuilder();	     
		sb.append("<div class='timesheet_table' id='dynamic'>");
        sb.append("able id='timesheet' cellpadding='0' cellspacing='0'>");
        sb.append("<tbody>");
        sb.append("	<tr>");
        sb.append("    	<th>Projects</th>");
        sb.append("        <th> ");
        sb.append("         <table id='1' cellpadding='0' cellspacing='0'>");
        sb.append("             <th>Tasks </th>");
        sb.append("        <th>Mon<i>15-06-2016</i></th>");
        sb.append("        <th>Mon<i>15-06-2016</i></th>");
        sb.append("        <th>Mon<i>15-06-2016</i></th>");
        sb.append("        <th>Mon<i>15-06-2016</i></th>");
        sb.append("        <th>Mon<i>15-06-2016</i></th>");
        sb.append("        <th>Mon<i>15-06-2016</i></th>");
        sb.append("        <th>Mon<i>15-06-2016</i></th>");
        sb.append("        <th>Total Hours</th>");
        sb.append("         </table>");
        sb.append("        </th> ");
        sb.append("    </tr>");
		
		
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
		    	
			sb.append("	<input type='hidden' name='submissionId'  value='"+ts.getId()+"'/>");
			sb.append("<tr id='timesheetRow1'>");
			sb.append("<td class='pname'> <div>"+ts.getProjectMembers().getProjects().getProjectName()+"</div> </td>");
			sb.append("<td>");
			sb.append("<input type='hidden' name='maxRowNumber${submission.id}'  class='initiateTimesheetMax' id='maxRowNumber${submission.id}'  value='2' />");
			
			Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
			 
			Collection<TimesheetEntries>  timesheetEntriesList =  timesheetEntriesService.getAllTimesheetEntriesBySubmissionId(ts.getId());
			for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
				  
				    
	               sb.append("       <table id='table${submission.id}' cellpadding='0' cellspacing='0'>");
	               sb.append("       <tr id='timesheetRow1submission${submission.id}'>");
	               sb.append("                 <td>");
	               sb.append("                      <div> ");
	               sb.append("                       <select id='1submission${submission.id}task1Id' name='1submission${submission.id}task1Id' >");
			       
			       for(ProjectTasks pt   : ProjectTasksList){
			               sb.append("<option value='${task.id}'>"+pt.getTaskName()+"</option>");   
			       }
	               sb.append(" </select>"); 
	               sb.append("<input type='button' value='' onClick='addTimeSheetRow(${submission.id})' class='addtaskfield'/>"); 
	               sb.append("                      </div>");
	               sb.append("                 </td> ");
				   sb.append("				<td> <div> ");   
	               sb.append(" <input type='text' onkeypress='return isNumber(event)' id='1submission${submission.id}timeSheetDay1Id' name='1submission${submission.id}timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	"); 
	               sb.append(" <div class='timesheet_comm'>"); 
	               sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment('1submission${submission.id}comment1Id');'></i>"); 
	               sb.append(" <span class='teskcomment' id='1submission${submission.id}comment1Id'><textarea name='1submission${submission.id}comment1Id' ></textarea></span></div>"); 
	               sb.append(" </div> </td>"); 
	               
				   sb.append(" <td> <div>"); 
	               sb.append(" <input type='text' onkeypress='return isNumber(event)' id='1submission${submission.id}timeSheetDay2Id' name='1submission${submission.id}timeSheetDay2Id' class='inline_field' placeholder='HH:MM' />	"); 
	               sb.append(" <div class='timesheet_comm'>"); 
	               sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment('1submission${submission.id}comment2Id');'></i>"); 
	               sb.append(" <span class='teskcomment' id='1submission${submission.id}comment2Id'><textarea name='1submission${submission.id}comment2Id'></textarea></span></div>"); 
	               sb.append(" </div> </td>"); 
	               
	 			   sb.append(" <td> <div>"); 
	               sb.append(" <input type='text' onkeypress='return isNumber(event)' id='1submission${submission.id}timeSheetDay3Id' name='1submission${submission.id}timeSheetDay3Id' class='inline_field' placeholder='HH:MM' />	"); 
	               sb.append(" <div class='timesheet_comm'>"); 
	               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment('1submission${submission.id}comment3Id');'></i>"); 
	               sb.append(" <span class='teskcomment' id='1submission${submission.id}comment3Id'><textarea name='1submission${submission.id}comment3Id'></textarea></span></div>"); 
	               sb.append(" </div> "); 
	               sb.append(" </td>"); 

	     		   sb.append(" <td> <div>"); 
	               sb.append(" <input type='text' onkeypress='return isNumber(event)' id='1submission${submission.id}timeSheetDay4Id' name='1submission${submission.id}timeSheetDay4Id' class='inline_field' placeholder='HH:MM' />"); 	
	               sb.append(" <div class='timesheet_comm'>"); 
	               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment('1submission${submission.id}comment4Id');'></i>"); 
	               sb.append(" <span class='teskcomment' id='1submission${submission.id}comment4Id' ><textarea name='1submission${submission.id}comment4Id'></textarea></span></div>"); 
	               sb.append(" </div> </td>"); 
	               
	               sb.append(" <input type='text' onkeypress='return isNumber(event)' id='1submission${submission.id}timeSheetDay5Id' name='1submission${submission.id}timeSheetDay5Id' class='inline_field' placeholder='HH:MM' />	"); 
	               sb.append(" <div class='timesheet_comm'>"); 
	               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment('1submission${submission.id}comment5Id');' ></i>"); 
	               sb.append(" <span class='teskcomment' id='1submission${submission.id}comment5Id'><textarea name='1submission${submission.id}comment5Id'></textarea></span></div>"); 
	               sb.append(" </div> </td>"); 
	               
				   sb.append("<td> <div>"); 
	               sb.append(" <input type='text' onkeypress='return isNumber(event)' id='1submission${submission.id}timeSheetDay6Id' name='1submission${submission.id}timeSheetDay6Id' class='inline_field' placeholder='HH:MM' />	"); 
	               sb.append(" <div class='timesheet_comm'>"); 
	               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment('1submission${submission.id}comment6Id');'></i>"); 
	               sb.append(" <span class='teskcomment' id='1submission${submission.id}comment6Id' ><textarea name='1submission${submission.id}comment6Id'></textarea></span></div>"); 
	               sb.append(" </div> </td>"); 
	               
				   sb.append("						<td> <div>"); 
	               sb.append(" <input type='text' onkeypress='return isNumber(event)' id='1submission${submission.id}timeSheetDay7Id' name='1submission${submission.id}timeSheetDay7Id' class='inline_field' placeholder='HH:MM' />	"); 
	               sb.append(" <div class='timesheet_comm'>"); 
	               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment('1submission${submission.id}comment7Id');'></i>"); 
	               sb.append(" <span class='teskcomment' id='1submission${submission.id}comment7Id' ><textarea name='1submission${submission.id}comment7Id' ></textarea></span></div>"); 
	               sb.append(" </div> </td>"); 
	               
				   sb.append("<td> <div>"); 
	               sb.append(" <input type='text' onkeypress='return isNumber(event)' id='1submission${submission.id}total1Id' name='1submission${submission.id}total1Id' class='inline_field' placeholder='HH:MM' />"); 	
	               sb.append(" <div class='timesheet_comm'>"); 
	               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment('1submission${submission.id}comment8Id');'></i>"); 
	               sb.append(" <span class='teskcomment' id='1submission${submission.id}comment8Id'><textarea name='1submission${submission.id}comment8Id'></textarea></span></div>"); 
	               sb.append(" </div> </td> ");                                         
	               sb.append(" </tr> ");            
	               sb.append("                </table>"); 
	               sb.append(" </td>   "); 
	               sb.append("   </tr>"); 
			}		                                                       
			sb.append("            </tbody>");
			sb.append("     </table>");
			sb.append(" </div>");
				
			}
			return sb.toString();
	 }

	@RequestMapping(value = "/timesheet")
	public ModelAndView registration(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			
			Long userId = user.getId();
			java.util.List<Long> projectMemberIds =  projectMembersService.getAllProjectMembersIdsByemployeeId(userId);
			Collection<TimesheetSubmissions>  timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissions(new Date(), new Date(), projectMemberIds);
			for(TimesheetSubmissions ts : timesheetSubmissionsList){
					Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
					ts.setProjectTasksList(ProjectTasksList);
			}
			
			model.addObject("sourcelead_timesheetSubmissionsList", timesheetSubmissionsList);
			
			Collection<Projects> projectsList =   projectsService.getAllProjects();
			model.addObject("sourcelead_projectsList", projectsList);
			
			
			Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
			model.addObject("sourcelead_tasksList", tasksList);
			
			model.setViewName("timesheet/timesheet");
			return model;

	}
	

	@RequestMapping(value = "/saveTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm,@RequestParam(value="json", required=false) String json,
			MultipartHttpServletRequest request) throws Exception {
											
		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    	TimesheetEntriesForm tForm = gson.fromJson(json, TimesheetEntriesForm.class);
    	
		String result = "";
		String resultJson = null;
    	
    	String[] submissionIds = tForm.getSubmissionId();
    	for(String submissionId : submissionIds){
    		
		    
	    	TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
	    	timesheetSubmissions.setId(Long.parseLong(submissionId)); 
			    
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxRowNumber"+submissionId);
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			int rowNum = 0;
			
			for (int c = 1; c < maxCount; c++) {
				if (isNullOrEmpty(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay1Id")))
					continue;
				
				TimesheetEntries timesheetEntries = new TimesheetEntries();
				timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
				timesheetEntries.setStartDate(new Date());
				timesheetEntries.setEndDate(new Date());
				timesheetEntries.setCreatedDate(new Date());
				timesheetEntries.setUpdatedDate(new Date());
				
				ProjectTasks projectTasks = new ProjectTasks();
				projectTasks.setId(Long.parseLong(jsonObj.getString(c+"submission"+submissionId+"task1Id")));
				timesheetEntries.setProjectTask(projectTasks);
				
				
			    timesheetEntries.setMonHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay1Id"));
				timesheetEntries.setMoncomments(jsonObj.getString(c+"submission"+submissionId+"comment1Id"));
				
				timesheetEntries.setTueHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay2Id"));
				timesheetEntries.setTuecomments(jsonObj.getString(c+"submission"+submissionId+"comment2Id"));

				timesheetEntries.setWedHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay3Id"));
				timesheetEntries.setWedcomments(jsonObj.getString(c+"submission"+submissionId+"comment3Id"));
				 
				timesheetEntries.setThuHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id"));
				timesheetEntries.setThucomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
				 
				 
				timesheetEntries.setFriHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id"));
				timesheetEntries.setFricomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
				 
				timesheetEntries.setSatHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id"));
				timesheetEntries.setSatcomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
				 
				timesheetEntries.setSunHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id"));
				timesheetEntries.setSuncomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
				
				
				timesheetEntries.setWeekcomments(jsonObj.getString("weekComment"));
				Iterator<String> itrator = request.getFileNames();
		        MultipartFile multiFile = request.getFile(itrator.next());
		        if(null != multiFile.getContentType()){
		    	  try {
		    		    timesheetEntries.setFileName(multiFile.getOriginalFilename());
		    	   		timesheetEntries.setFileContentType(multiFile.getContentType());
		    	   		timesheetEntries.setDocFile(multiFile.getBytes());
			       }catch (Exception e) {
		    	      	 e.printStackTrace();
					    }
		        }
				
				
				rowNum++;
				
				timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
				
			}
    	}
    	
		result =  "success";
		resultJson = toJSON(result);
		return resultJson;
	}
	
	
	@RequestMapping(value = "/dynamicTimesheet", method = RequestMethod.GET )
	public @ResponseBody String dynamicTimesheet() throws Exception {
											 
	
	String result = "";
	result = result +"<table id='timesheet' cellpadding='0' cellspacing=0'>";
	result = result +"<tbody>";
    result = result +"<tr>";
    result = result +"<th>Projects</th>";
    result = result +"   <th> ";
    result = result +"    <table id='1' cellpadding='0' cellspacing='0'>";
    result = result +"        <th>Tasks </th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Total Hours</th>";
    result = result +"    </table>";
    result = result +"   </th> ";
    result = result +"</tr>";
    
    Collection<Projects> projectsList =   projectsService.getAllProjects();
    for(Projects pro : projectsList){
    
      	result = result +"<input type='hidden' name='projectId'  value='"+pro.getId()+"'/>";	  
        result = result +"<tr id='timesheetRow1'>";
        result = result +"<td class='pname'> <div>"+pro.getProjectName()+"</div> </td>";
        result = result +"<td>";
        result = result +"<input type='hidden' name='maxRowNumber"+pro.getId()+"'  class='initiateTimesheetMax' id='maxRowNumber"+pro.getId()+"'  value='2' />";
        result = result +"<table id='table"+pro.getId()+"' cellpadding='0' cellspacing='0'>";
        result = result +"<tr id='timesheetRow1project"+pro.getId()+"'>";
        result = result +"<td>";
        result = result +"<div> ";
        result = result +"<select id='1project"+pro.getId()+"task1Id' name='1project"+pro.getId()+"task1Id' >";
        Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
        for(ProjectTasks task : tasksList){
        	   result = result +"<option value='"+task.getId()+"'>"+task.getTaskName()+"</option>";
        }
        result = result +"</select>";
        result = result +"<input type='button' value='' onClick='addTimeSheetRow("+pro.getId()+")' class='addtaskfield'/>";
        result = result +"</div>";
        result = result +"</td> ";
		result = result +"<td> <div> ";
		
		result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay1Id' name='1project"+pro.getId()+"timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment('1project"+pro.getId()+"comment1Id');'></i>";
        result = result +"  <span class='teskcomment' id='1project"+pro.getId()+"comment1Id'><textarea name='1project"+pro.getId()+"comment1Id' ></textarea></span></div>";
        result = result +"  </div> </td>";
        
        
       	result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay2Id' name='1project"+pro.getId()+"timeSheetDay2Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"  <div class='timesheet_comm'>";
        result = result +"  <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment('1project"+pro.getId()+"comment2Id');'></i>";
        result = result +"  <span class='teskcomment' id='1project"+pro.getId()+"comment2Id'><textarea name='1project"+pro.getId()+"comment2Id'></textarea></span></div>";
        result = result +"  </div> </td>";
                  
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay3Id' name='1project"+pro.getId()+"timeSheetDay3Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment3Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment3Id'><textarea name='1project"+pro.getId()+"comment3Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";  
        
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay4Id' name='1project"+pro.getId()+"timeSheetDay4Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment4Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment4Id'><textarea name='1project"+pro.getId()+"comment4Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";  
        
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay5Id' name='1project"+pro.getId()+"timeSheetDay5Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment5Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment5Id'><textarea name='1project"+pro.getId()+"comment5Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";  
        
        
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay6Id' name='1project"+pro.getId()+"timeSheetDay6Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment6Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment6Id'><textarea name='1project"+pro.getId()+"comment6Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";  
       
       
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay7Id' name='1project"+pro.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment7Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment7Id'><textarea name='1project"+pro.getId()+"comment7Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";   
                  
        
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"total1Id' name='1project"+pro.getId()+"total1Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment8Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment8Id'><textarea name='1project"+pro.getId()+"comment8Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>";                                         
        result = result +" </tr>";                             
               
               
        result = result +"</table>";
        result = result +"</td>";   
        result = result +"</tr> ";
        
    }
    /*add total row*/   
    result = result +"</tbody>";
     result = result +"</table>";  
        
   return result;
	}

	
	@RequestMapping(value = "/dateFormat", method = RequestMethod.GET )
	public @ResponseBody void dateFormat() throws Exception {
		  
		
	}
	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries addTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		
		ProjectTasks task = new ProjectTasks();
		/*Tasks task = new Tasks();
		task.setId(1l);
		timesheetEntries.setStartDate(new Date());
		timesheetEntries.setEndDate(new Date());
		timesheetEntries.setTotalHours(5);
		timesheetEntries.setSubmittedHours(10);
		timesheetEntries.setEntryStatus("X");
		timesheetEntries.setMonTask(task);
		timesheetEntries.setMonHours(15);
		timesheetEntries.setMoncomments("some how");
		timesheetEntries.setTueTask(task);
		timesheetEntries.setTueHours(5);
		timesheetEntries.setTuecomments("nothing");
		timesheetEntries.setWedTask(task);
		timesheetEntries.setWedHours(10);
		timesheetEntries.setWedcomments("some alone");
		timesheetEntries.setThuTask(task);
		timesheetEntries.setThuHours(12);
		timesheetEntries.setThucomments("every day");
		timesheetEntries.setFriTask(task);
		timesheetEntries.setFriHours(15);
		timesheetEntries.setFricomments("all");
		timesheetEntries.setSatTask(task);
		timesheetEntries.setSatHours(16);
		timesheetEntries.setSatcomments("saturady no comments");
		timesheetEntries.setSunTask(task);
		timesheetEntries.setSunHours(10);
		timesheetEntries.setSuncomments("sleep");
		timesheetEntries.setEntryType("open");
		timesheetEntries.setStatus("rejected");
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setCreatedBy("prsad k");
		timesheetEntries.setUpdatedBy("naidu l");
		timesheetEntries.setUpdatedDate(new Date());*/
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.addTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries updateTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		timesheetEntries.setId(1l);
		
		ProjectTasks task = new ProjectTasks();
		task.setId(1l);
		
		/*timesheetEntries.setStartDate(new Date());
		timesheetEntries.setEndDate(new Date());
		timesheetEntries.setTotalHours(5);
		timesheetEntries.setSubmittedHours(10);
		timesheetEntries.setEntryStatus("A");
		timesheetEntries.setMonTask(task);
		timesheetEntries.setMonHours(15);
		timesheetEntries.setMoncomments("some how");
		timesheetEntries.setTueTask(task);
		timesheetEntries.setTueHours(5);
		timesheetEntries.setTuecomments("nothing");
		timesheetEntries.setWedTask(task);
		timesheetEntries.setWedHours(10);
		timesheetEntries.setWedcomments("some alone");
		timesheetEntries.setThuTask(task);
		timesheetEntries.setThuHours(12);
		timesheetEntries.setThucomments("every day");
		timesheetEntries.setFriTask(task);
		timesheetEntries.setFriHours(15);
		timesheetEntries.setFricomments("all");
		timesheetEntries.setSatTask(task);
		timesheetEntries.setSatHours(16);
		timesheetEntries.setSatcomments("saturady no comments");
		timesheetEntries.setSunTask(task);
		timesheetEntries.setSunHours(10);
		timesheetEntries.setSuncomments("sleep");
		timesheetEntries.setEntryType("open");
		timesheetEntries.setStatus("rejected");
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setCreatedBy("prsad");
		timesheetEntries.setUpdatedBy("naidu");
		timesheetEntries.setUpdatedDate(new Date());*/
		
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.updateTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	
	
	@RequestMapping(value = "/getTimesheetEntries/{id}", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		
		Boolean status = timesheetEntriesService.deleteTimesheetEntries(id);
		return status; 
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> getAllTimesheetEntries() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
	
	
	@RequestMapping(value = "/saveAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> saveAll() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
     
	
	 
	@RequestMapping(value = "/getSum", method = RequestMethod.GET)
	public @ResponseBody String getDetails(@RequestParam String valueOne)
			 
	{
		 System.out.println("hlo1");
		 Integer sum = 0;
		 String[] tokens = valueOne.split(",");
         for(String s:tokens){
            sum = sum + Integer.parseInt(s);
         }
		 Integer result = sum ;
		 System.out.println("result"+result);
 	     return result.toString();
	}

	
	@RequestMapping(value = "/getSum", method = RequestMethod.POST)
	public @ResponseBody String getDetailss(@RequestBody String valueOne)
			 
	{
		 System.out.println("hlo2");
		 Integer sum = 0;
		 String[] tokens = valueOne.split(",");
         for(String s:tokens){
            sum = sum + Integer.parseInt(s);
         }
		 Integer result = sum ;
		 System.out.println("result"+result);
 	     return result.toString();
	}

	private String toJSON(Object obj){
	    XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	        public HierarchicalStreamWriter createWriter(Writer writer) {
	            return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	        }
	    });
	    return xstream.toXML(obj);
	}
	
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
 
}