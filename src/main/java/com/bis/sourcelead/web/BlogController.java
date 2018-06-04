package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.BlogCategoryDTO;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.BlogCategoryService;
import com.bis.sourcelead.service.SourceleadBlogService;
import com.bis.sourcelead.service.UserRoleService;

@Controller
@RequestMapping("/blog")
public class BlogController {
	@Autowired
	private SourceleadBlogService sourceleadBlogService;

	@Autowired
	private BlogCategoryService blogCategoryService;

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private StaticWebController staticWebController;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	/*
	 * @RequestMapping("/") public ModelAndView index(HttpServletRequest request) {
	 * return allBlogs(request); }
	 */

	@RequestMapping("/all")
	public ModelAndView allBlogs(HttpServletRequest request) throws Exception {

		ModelAndView modal = new ModelAndView();

		modal = staticWebController.setMetaProperties("all-blogs");

		List<SourceleadBlogs> allBlogs = sourceleadBlogService.getAllSourceleadBlogs();
		modal.addObject("allBlogs", allBlogs);
		modal.addObject("sourceleadUrl",sourceleadUrl);
		modal.setViewName("/resources/all-blogs");
		return modal;
	}

	@RequestMapping(value = "/{blogTitle}.html")
	public ModelAndView blogRedirect(@PathVariable String blogTitle, HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView = staticWebController.setMetaProperties("all-blogs");
		SourceleadBlogs blogDetails = sourceleadBlogService.getSourceleadBlogsByBlogTitle(blogTitle);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormatResult = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      
		if(blogDetails != null)
		{
		if (blogDetails.getCategoryId() != null) {
			List<SourceleadBlogs> relatedBlogs = sourceleadBlogService
					.getRelatedSourceleadBlogs(blogDetails.getId());
			modelAndView.addObject("relatedBlogs", relatedBlogs);
		}
       
       if (blogDetails.getPublishOn() != null) {
			String publishon = dateFormatResult.format(dateFormat.parse(blogDetails.getPublishOn().toString()));
			modelAndView.addObject("publishon", publishon);
		}
         modelAndView.addObject("blogDetails", blogDetails);
		}
		 
		List<BlogCategoryDTO> blogCategoryList = blogCategoryService.getBlogCategoryListByActiveBlogs();
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(blogDetails.getCreatedBy().getId(), 1l);

		String createdDate = dateFormatResult.format(dateFormat.parse(blogDetails.getCreatedDate().toString()));
		modelAndView.addObject("blogCategoryList", blogCategoryList);
		modelAndView.addObject("createdDate", createdDate);
		String sleadUrl = sourceleadUrl.replace("/app", "");
		modelAndView.addObject("sleadUrl", sleadUrl);
		modelAndView.addObject("userRoleDisplayName", userRole.getRoles().getDisplayName());
		modelAndView.setViewName("/resources/blog-details");

		return modelAndView;
	}

	@RequestMapping(value = "/topic/{category}.html")
	public ModelAndView blogCategoryRedirect(@PathVariable String category, HttpServletRequest request)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView = staticWebController.setMetaProperties("all-blogs");
		List<SourceleadBlogs> blogsByCategory = sourceleadBlogService.getAllSourceleadBlogsByCategoryName(category);
		modelAndView.addObject("allBlogs", blogsByCategory);
		String sleadUrl = sourceleadUrl.replace("/app", "");
		modelAndView.addObject("sleadUrl", sleadUrl);
		modelAndView.setViewName("/resources/all-blogs");
		return modelAndView;
	}

	// @RequestMapping(value = "/{category}")
	// public ModelAndView blogsByCategory(@PathVariable String category,
	// HttpServletRequest request) throws Exception {
	// ModelAndView modelAndView = new ModelAndView();
	// List<SourceleadBlogs> allBlogs =
	// sourceleadBlogService.getAllSourceleadBlogsByCategoryName(category);
	// modelAndView.addObject("allBlogs", allBlogs);
	// modelAndView.setViewName("/resources/all-blogs");
	// return modelAndView;
	// }

	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
