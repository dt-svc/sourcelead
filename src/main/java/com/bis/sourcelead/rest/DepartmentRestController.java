package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.DepartmentDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@RequestMapping("/app")
public class DepartmentRestController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentRestController.class);
	
	@Autowired
	public DepartmentService departmentService;
	
	@Autowired
	public UserService userService;
	
	 @Autowired
	 CommonUtil commonUtil;
	

    @RequestMapping(value = "/storeDepartment", method = RequestMethod.POST)
    @ResponseBody
	public   Response<String> storeDepartment(DepartmentDTO  departmentDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    	Response<String> respons=new Response<>();
    	try{
    		if(user !=null){
    			if(Validator.validate(departmentDTO)){
    				String department =  departmentService.saveDepartmentDTO(departmentDTO);
    				respons.setResponse(Result.SUCCESS.name());
    				respons.setResponseMessage(Result.SUCCESS.getValue());
    			}else{
    				respons.setObject("DataMissing");
					respons.setResponse(Result.INVALID_DATA.name());
					respons.setResponseMessage(Result.INVALID_DATA.getValue());
    			}
    		}else{
    			respons.setResponse(Result.SESSION_TIMEDOUT.name());
				respons.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
    		}
    		
    	}catch (Exception e) {
    		respons.setResponse(Result.AWKWARD.name());
			respons.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return respons;
  
	}
   
    @RequestMapping(value = "/getDepartmentList", method = RequestMethod.GET)
	public @ResponseBody  ResponseList<Department> getDepartmentList(HttpServletRequest request,@RequestParam(value="departmentStatus", required=false) String departmentStatus, HttpServletResponse response) throws Exception
	{
    	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	ResponseList<Department> respons =new ResponseList<>();
    	try{
    		if(loginOrgLocation !=null){
				List<Department> deparmentList = departmentService.getDepartmentByOrgLocationIdAndStatus(loginOrgLocation.getId(),departmentStatus);
		    	for(Department dept :deparmentList ){
		    		dept.setDepartmentName(dept.getDepartmentName());
		    		dept.setDescription(dept.getDescription());
		    	}
		    	respons.setData(deparmentList);
				respons.setResponse(Result.SUCCESS.name());
				respons.setResponseMessage(Result.SUCCESS.getValue());
    		}else{
    			respons.setResponse(Result.SESSION_TIMEDOUT.name());
				respons.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
    		}
    	}catch (Exception e) {
    		respons.setResponse(Result.AWKWARD.name());
			respons.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	  return respons;
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
    
    @RequestMapping(value = "/updateDepartment", method = RequestMethod.POST)
    @ResponseBody
	public   Response<String> updateDepartment(DepartmentDTO departmentDTO,HttpServletRequest request,@RequestParam(value="departmentId", required=false) Long departmentId) throws Exception
	{
    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    	Response<String> response=new Response<>();
    	try{
    	if(user != null){
    		if(Validator.validate(departmentDTO)){
    			   Department department = departmentService.getDepartmentById(departmentId);
    			    if(StringUtils.isNotBlank(departmentDTO.getDepartmentName())){
    					department.setDepartmentName(departmentDTO.getDepartmentName());
    				}
    			    if(StringUtils.isNotBlank(departmentDTO.getDepartmentDesc())){
    					department.setDescription(departmentDTO.getDepartmentDesc());
    				}
    				department.setUpdatedDate(commonUtil.currentDate());
    				departmentService.saveDepartment(department);
    			response.setResponse(Result.SUCCESS.name());
    			response.setResponseMessage(Result.SUCCESS.getValue());
    		}else{
    			response.setObject("DataMissing");
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
    		}
    		
    	}	else{
    		response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
    	}
    	}catch (Exception e) {
    		response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
    	
		 return response;
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
