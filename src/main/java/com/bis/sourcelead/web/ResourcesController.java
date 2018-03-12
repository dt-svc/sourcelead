package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
			//	Date date = sourceleadBlog.getPublishOn();  
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				DateFormat dateFormatResult = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
				String publishon = dateFormatResult.format(dateFormat.parse(sourceleadBlog.getPublishOn().toString()));
				
				String[] keywordsArray = null;
				if(sourceleadBlog != null && StringUtils.isNotEmpty(sourceleadBlog.getKeywords())) {
					keywordsArray = sourceleadBlog.getKeywords().split(",");
				}
				model.addObject("keywordsArray", keywordsArray);
				model.addObject("publishon", publishon);
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
				model.addObject("sourceleadBlog", sourceleadBlog);
			}
			
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
	 
}
