package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@RequestMapping("/slead")
public class DepartmentRestController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentRestController.class);
	
	@Autowired
	public DepartmentService departmentService;
	
	@Autowired
	public UserService userService;
	
	 @Autowired
	 CommonUtil commonUtil;
	

    @RequestMapping(value = "/storeDepartment", method = RequestMethod.GET)
	public @ResponseBody  void storeDepartment(@RequestParam(value="json", required=false) String json,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	 String json1 = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json1);
    	  String department =  departmentService.saveDepartment(jsonObj);
	}
   
    @RequestMapping(value = "/getDepartmentList", method = RequestMethod.GET)
	public @ResponseBody  String getDepartmentList(HttpServletRequest request,@RequestParam(value="departmentStatus", required=false) String departmentStatus, HttpServletResponse response) throws Exception
	{
    	String resultJson;
    	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	List<Department> deparmentList = departmentService.getDepartmentByOrgLocationIdAndStatus(loginOrgLocation.getId(),departmentStatus);
    	for(Department dept :deparmentList ){
    		dept.setDepartmentName(dept.getDepartmentName());
    		dept.setDescription(dept.getDescription());
    	}
	  resultJson = commonUtil.toJSON(deparmentList);
	  return resultJson;
	}
    

    @RequestMapping(value = "/saveDepartmentReason", method = RequestMethod.GET)
	public @ResponseBody  String updateDepartment(@RequestParam(value="departmentId", required=false) Long departmentId) throws Exception
	{
	    Department department = departmentService.getDepartmentById(departmentId);
		department.setStatus(Constants.RECORD_INACTIVE);
		department.setUpdatedDate(commonUtil.currentDate());
		departmentService.saveDepartment(department);
		return "success";
	}
    
    @RequestMapping(value = "/getEditDepartment", method = RequestMethod.GET)
   	public @ResponseBody  String getEditDepartment(@RequestParam(value="departmentId", required=false) Long departmentId) throws Exception
   	{
    	String resultJson;
   	    Department department = departmentService.getDepartmentById(departmentId);
   	   resultJson = commonUtil.toJSON(department);
	  return resultJson;
   	}
    
    @RequestMapping(value = "/updateDepartment", method = RequestMethod.GET)
	public @ResponseBody  String updateDepartment(@RequestParam(value="json", required=false) String json,@RequestParam(value="departmentId", required=false) Long departmentId) throws Exception
	{
    	String  json1 = "{"+json+"}";	
	    JSONObject jsonObj = new JSONObject(json1);
	    Department department = departmentService.getDepartmentById(departmentId);
	    if(jsonObj.has("departmentName") && !"".equalsIgnoreCase(jsonObj.getString("departmentName"))){
			department.setDepartmentName(jsonObj.getString("departmentName"));
		}
		if(jsonObj.has("departmentDesc")){
			department.setDescription(jsonObj.getString("departmentDesc"));
		}
		department.setUpdatedDate(commonUtil.currentDate());
		departmentService.saveDepartment(department);
		
		 return "success";
	}
    
    @RequestMapping(value = "/updateDelateDepartment", method = RequestMethod.GET)
	public @ResponseBody  String updateDelateDepartment(@RequestParam(value="json", required=false) String json,@RequestParam(value="departmentId", required=false) Long departmentId) throws Exception
	{
    	String  json1 = "{"+json+"}";	
	    JSONObject jsonObj = new JSONObject(json1);
	    Department department = departmentService.getDepartmentById(departmentId);
	    if(jsonObj.has("departmentName") && !"".equalsIgnoreCase(jsonObj.getString("departmentName"))){
			department.setDepartmentName(jsonObj.getString("departmentName"));
		}
		if(jsonObj.has("departmentDesc") && !"".equalsIgnoreCase(jsonObj.getString("departmentDesc"))){
			department.setDescription(jsonObj.getString("departmentDesc"));
		}
		department.setStatus(Constants.RECORD_ACTIVE);
		department.setUpdatedDate(commonUtil.currentDate());
		departmentService.saveDepartment(department);
		
		 return "success";
	}
    
    @SuppressWarnings("unused")
    @RequestMapping(value = "/validateDepartmentname" , method = RequestMethod.GET)
	 public @ResponseBody  Boolean validateDepartmentname(@RequestParam(value="departmentName", required=false) String departmentName,
			 @RequestParam(value="departmentId", required=false) Long departmentId,HttpServletRequest request) throws Exception {
    	
    	   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations"); 
		   Department dept = null;
    	   if(null != departmentId){
    		   dept = departmentService.validateDepartmentnameByDepartmentIdAndDepartmentName(departmentId, departmentName);
    	   }
		  Boolean flag = false;
    	   if(null == dept){
    		   flag = departmentService.validateDepartmentnameByOrgLocationIdAndDepartmentName(loginOrgLocation.getId(), departmentName);
    	   }
		return flag;
	   }

    
}
