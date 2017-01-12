package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@PropertySource("${propertyLocation:app.properties}")
public class TaskRestController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	TaskService taskService;
	
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 
	 @RequestMapping(value = "/storeTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String storeTasks(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		    String json1 = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json1);
		    String tasks =  taskService.saveTasks(jsonObj);
		    return tasks;		
			 
	   }
	 
	 
	 @RequestMapping(value = "/getAssignedTasksList", method = RequestMethod.POST)
		public @ResponseBody String getAssignedTasksList(HttpServletRequest request) throws Exception {
			
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<Tasks> taklist = taskService.assignedTasksListByIdANDOrgLocationId(orgLocations.getId(),user.getId());
			for(Tasks tk :taklist){
				tk.setSubject(tk.getSubject());
				tk.setTaskDateStr(Constants.DMY.format(tk.getTaskDate()));
				tk.setTaskTime(tk.getTaskTime());
				tk.setDescription(tk.getDescription());
				tk.setAlertType(tk.getAlertType());
				tk.setTaskStatus(tk.getTaskStatus());
				
				if( null != tk.getAssignedUserId()){
					StringBuilder assignedName= new StringBuilder();
					if(null != tk.getAssignedUserId().getLastName()){
						tk.setAssignedName(assignedName.append(tk.getAssignedUserId().getFirstName()+" "+tk.getAssignedUserId().getLastName()).toString());
					}else{
						tk.setAssignedName(assignedName.append(tk.getAssignedUserId().getFirstName()).toString());
					}
				}
					
			}
			return commonUtil.toJSON(taklist);
		}
	 
	   protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
}
