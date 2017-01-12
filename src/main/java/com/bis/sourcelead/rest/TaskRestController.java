package com.bis.sourcelead.rest;

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

import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.util.CommonUtil;


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
	 
	   protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
}
