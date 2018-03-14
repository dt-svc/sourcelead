package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.UserManualAttachment;
import com.bis.sourcelead.entity.UserManualLevelOne;
import com.bis.sourcelead.entity.UserManualLevelThree;
import com.bis.sourcelead.entity.UserManualLevelTwo;
import com.bis.sourcelead.service.UserManualService;
import com.bis.sourcelead.util.CommonUtil;

@Controller
@RequestMapping("/app")
public class ResourcesController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserManualService userManualService;
	
	
	 @PreAuthorize("hasAnyRole('ROLE_ADD_BLOG','ROLE_PUBLISH_BLOG','ROLE_VIEW_EDIT_BLOG','ROLE_DELETE_BLOG')")
	 @RequestMapping(value = {"/blogs", "/rest/blogs"}, method = RequestMethod.GET)
		public ModelAndView blogs(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.Blogs, request);
		    
			ModelAndView model = new ModelAndView();
			model.setViewName("resources/blogs");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }
	 
	 @PreAuthorize("hasAnyRole('ROLE_ADD_USER_MANUAL','ROLE_PUBLISH_USER_MANUAL','ROLE_VIEW_EDIT_USER_MANUAL','ROLE_DELETE_USER_MANUAL')")
	 @RequestMapping(value = {"/userManuals", "/rest/userManuals"}, method = RequestMethod.GET)
		public ModelAndView userManuals(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.UserManuals, request);
		    
			ModelAndView model = new ModelAndView();
			model.setViewName("resources/userManuals");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }
	 
	 @RequestMapping(value = {"/addUserManual", "/rest/userManuals"}, method = RequestMethod.GET)
		public ModelAndView addUserManuals(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.UserManuals, request);
		    
			ModelAndView model = new ModelAndView();
			model.setViewName("resources/addUserManual");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }
	 
	 @RequestMapping(value = {"/editUserManual", "/rest/userManuals"}, method = RequestMethod.GET)
		public ModelAndView editUserManual(@RequestParam(value="levelOneUUID", required=true) String levelOneUUID,@RequestParam(value="levelName", required=true) String levelName,HttpServletRequest request)throws Exception {
		 UserManualLevelOne userManualLevelOne=null;	
		 UserManualLevelTwo userManualLevelTwo=null;
		 UserManualLevelThree userManualLevelThree=null;
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 DateFormat dateFormatResult = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");

		 ModelAndView model = new ModelAndView();
		 if(levelName.equalsIgnoreCase("Level-1")){
		 		 userManualLevelOne=userManualService.getLevelOneByUUId(levelOneUUID);
		 		model.addObject("userManualLevel", userManualLevelOne);
		 	}else if(levelName.equalsIgnoreCase("Level-2")){
		 		userManualLevelTwo=userManualService.getLevelTwoByUUId(levelOneUUID);
		 		model.addObject("userManualLevel", userManualLevelTwo);
		 		model.addObject("levelOneValue", userManualLevelTwo.getUserManualLevelOneId().getId());
		 	}else{
		 		userManualLevelThree=userManualService.getLevelThreeByUUId(levelOneUUID);
		 		String publishon = dateFormatResult.format(dateFormat.parse(userManualLevelThree.getPublishOn().toString()));
		 		model.addObject("userManualLevel", userManualLevelThree);
		 		model.addObject("levelOneValue", userManualLevelThree.getUserManualLevelTwoId().getUserManualLevelOneId().getId());
		 		model.addObject("levelTwoValue", userManualLevelThree.getUserManualLevelTwoId().getId());
		 		model.addObject("publishon", publishon);
		 		model.addObject("uploadUrl", userManualLevelThree.getUploadUrl());
		 		
		 		List<UserManualAttachment> userManualAttachmentList = userManualService.getUsermanualAttachmentList(userManualLevelThree.getId());
			 		model.addObject("userManualAttachmentList", userManualAttachmentList);
		 		
		 	}
		    commonUtil.highLightMenu(Constants.UserManuals, request);
		    
			model.addObject("levelName", levelName);
			model.setViewName("resources/addUserManual");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }
}
