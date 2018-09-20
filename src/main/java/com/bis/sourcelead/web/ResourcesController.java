package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Evaluator.IsEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;

import com.bis.sourcelead.entity.BlogCategory;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.service.BlogCategoryService;
import com.bis.sourcelead.service.SourceleadBlogService;


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

	BlogCategoryService blogCategoryService;
	
	@Autowired
	SourceleadBlogService sourceleadBlogService;

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
	 

	 @PreAuthorize("hasRole('ROLE_ADD_BLOG')")
	 @RequestMapping(value = {"/addBlog", "/rest/addBlog"}, method = RequestMethod.GET)
		public ModelAndView addBlog(HttpServletRequest request,@RequestParam(value="blogUUID", required=false)String blogUUID) throws Exception {
		 
		    commonUtil.highLightMenu(Constants.Blogs, request);
			ModelAndView model = new ModelAndView();
			SourceleadBlogs sourceleadBlog;
			if(null != blogUUID && !blogUUID.isEmpty()){
			
				sourceleadBlog = sourceleadBlogService.getSourceleadBlogsByBlogUUID(blogUUID);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				DateFormat dateFormatResult = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
				
				if(sourceleadBlog.getPublishOn() != null) {
				String publishon = dateFormatResult.format(dateFormat.parse(sourceleadBlog.getPublishOn().toString()));
				model.addObject("publishon", publishon);
				}
				
				String[] keywordsArray = null;
				if(sourceleadBlog != null && StringUtils.isNotEmpty(sourceleadBlog.getKeywords())) {
					keywordsArray = sourceleadBlog.getKeywords().split(",");
				}
				model.addObject("keywordsArray", keywordsArray);
				
				model.addObject("sourceleadBlog", sourceleadBlog);
			}
			
			model.setViewName("resources/addBlog");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			List<BlogCategory> blogCatslist = blogCategoryService.getBlogCategoryListByStatus();
			model.addObject("blogCatslist", blogCatslist);
			return model;
	 }
	 
	 @PreAuthorize("hasRole('ROLE_PUBLISH_BLOG')")
	 @RequestMapping(value = {"/blogPreview", "/rest/blogPreview"}, method = RequestMethod.GET)
		public ModelAndView blogPreview(HttpServletRequest request,@RequestParam(value="blogUUID", required=false)String blogUUID) throws Exception {
		    
		    ModelAndView model = new ModelAndView();
		    commonUtil.highLightMenu(Constants.Blogs, request);
		    SourceleadBlogs sourceleadBlog;
			if(null != blogUUID && !blogUUID.isEmpty()){
			
				sourceleadBlog = sourceleadBlogService.getSourceleadBlogsByBlogUUID(blogUUID);
				if(sourceleadBlog.getImgFileUrl() != null) {
				sourceleadBlog.setBlogImage(sourceleadBlog.getImgFileUrl());
			     }
				model.addObject("sourceleadBlog", sourceleadBlog);
			}
			model.setViewName("resources/blogPreview");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	 }
	 
	 @PreAuthorize("hasAnyRole('ROLE_ADD_BLOG_CATEGORY','ROLE_VIEW_EDIT_BLOG_CATEGORY','ROLE_DELETE_BLOG_CATEGORY')")
	 @RequestMapping(value = {"/categoryList", "/rest/categoryList"}, method = RequestMethod.GET)
		public ModelAndView categoryList(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.Blogs, request);
			ModelAndView model = new ModelAndView();
			model.setViewName("resources/categoryList");
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
		 		String publishon=null;
		 		if(userManualLevelThree.getPublishOn() !=null){
		 			 publishon = dateFormatResult.format(dateFormat.parse(userManualLevelThree.getPublishOn().toString()));
		 		}
		 		
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
	 @RequestMapping(value = {"/userManualPublish"}, method = { RequestMethod.GET, RequestMethod.POST })
		public ModelAndView userManualPublish(HttpServletRequest request) {
			ModelAndView model = new ModelAndView();
			model.setViewName("resources/userManualPublish");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }

}
