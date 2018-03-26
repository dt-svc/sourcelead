package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dao.SourceleadBlogDao;
import com.bis.sourcelead.dto.BlogCategoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadBlogDTO;
import com.bis.sourcelead.entity.BlogCategory;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.BlogCategoryService;
import com.bis.sourcelead.service.SourceleadBlogService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ResourcesRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourcesRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
    @Autowired
    private SourceleadBlogService sourceleadBlogService;
    
    @Autowired
    private BlogCategoryService blogCategoryService;
    
    @Autowired
    SourceleadBlogDao sourceleadBlogDao;
    
    /**
     * PrasadK
     * this method for Add Blog
     * 
     * @param sourceleadBlogDTO
     * @param request
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADD_BLOG')")
	@RequestMapping(value = {"/addBlog","/rest/addBlog"}, method = RequestMethod.POST)
	@ResponseBody
	public boolean addBlog(SourceleadBlogDTO sourceleadBlogDTO, HttpServletRequest request) throws Exception {

		boolean isInserted = false;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		sourceleadBlogDTO.setStaticPath(staticPath);
		SourceleadBlogs sourceleadBlogs = sourceleadBlogService.addBlog(sourceleadBlogDTO,loginUser);
		if (sourceleadBlogs != null) {
			isInserted = true;
		}
		return isInserted;
	}
    
   /**
    *  Prasad K
    *  get the Blogs List
    * @param sourceleadBlogDTO
    * @return
    * @throws Exception
    */
    
    @RequestMapping(value = {"/blogsList","rest/blogsList"},method=RequestMethod.POST)
	public ResponseList<SourceleadBlogDTO> blogsList(SourceleadBlogDTO sourceleadBlogDTO) throws Exception
	{
		ResponseList<SourceleadBlogDTO> sourceleadBlogsList = sourceleadBlogService.getAllSourceleadBlogsByStatus(sourceleadBlogDTO);
		return sourceleadBlogsList;
	}
    
    
    @RequestMapping(value = "/removeSourceleadBlogByBlogId")
	public String removeSourceleadBlogByBlogId(@RequestParam(value = "blogId", required = false) Long blogId) throws Exception {
    	sourceleadBlogService.removeSourceleadBlogByBlogId(blogId);
		return "success";
	}
    
    /**
	 * saving add Blog Category 
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = {"/addBlogCategory","/rest/addBlogCategory"}, method = RequestMethod.POST)
	@ResponseBody
	public boolean addBlogCategory(BlogCategoryDTO blogCategoryDTO, HttpServletRequest request) throws Exception {

		boolean isInserted = false;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		BlogCategory blogCategory = blogCategoryService.addBlogCategory(blogCategoryDTO, loginUser);
		if (blogCategory != null) {
			isInserted = true;
		}
		return isInserted;
	}
	
	 @RequestMapping(value = {"/blogCategoryList","rest/blogCategoryList"},method=RequestMethod.POST)
		public ResponseList<BlogCategoryDTO> blogCategoryList(BlogCategoryDTO blogCategoryDTO) throws Exception
		{
			ResponseList<BlogCategoryDTO> blogCategoryList = blogCategoryService.getAllBlogCategoryByStatus(blogCategoryDTO);
			return blogCategoryList;
		}
	 
	 @RequestMapping(value = "/removeBlogCategoryByBlogId")
		public String removeBlogCategoryByBlogId(@RequestParam(value = "blogCategoryId", required = false) Long blogCategoryId) throws Exception {
		 blogCategoryService.removeBlogCategoryByBlogId(blogCategoryId);
		 return "success";
		}
	 
	 
	 @RequestMapping(value = {"/getBlogCategoryById","/rest/getBlogCategoryById"}, method = RequestMethod.POST)
		public BlogCategoryDTO getBlogCategoryById(BlogCategoryDTO blogCategoryDTO) throws Exception {
			
			     BlogCategoryDTO bcDTO = new BlogCategoryDTO();
			     if(blogCategoryDTO.getCategoryUUID() != null) {
			        BlogCategory  blogCategory = blogCategoryService.getBlogCategoryByCategoryUUID(blogCategoryDTO.getCategoryUUID());
			    
				    if(blogCategory.getCategoryName() != null){
				    	bcDTO.setCategoryName(blogCategory.getCategoryName());
				    }
				    
				    if(blogCategory.getDescription() != null){
				    	bcDTO.setDescription(blogCategory.getDescription());
				    }
			     }
				return bcDTO;
		}
	 
	    @RequestMapping(value = "/deleteBlogImg", method = RequestMethod.POST)
		public @ResponseBody String deleteBlogImg(@RequestParam(value = "blogId", required = false) Long blogId,HttpServletRequest request) throws Exception{
			
			SourceleadBlogs sourceleadBlogs = sourceleadBlogService.getSourceleadBlogsByBlogId(blogId);
			if(sourceleadBlogs != null){
			sourceleadBlogs.setImgFileUrl(null);
			sourceleadBlogs.setFileName(null);
			sourceleadBlogs.setFileContentType(null);
			sourceleadBlogDao.addBlog(sourceleadBlogs);
			}
			return "success";
		}
	    
	    /**
		 * To validate Blog Title
		 * 
		 * @param name
		 * @return
		 */
		@RequestMapping(value = "/validateBlogTitle", method = RequestMethod.POST)
		public @ResponseBody String validateBlogTitle(@RequestParam(value = "name", required = false) String name, HttpServletRequest request) throws Exception
		{
			String result = null;
			SourceleadBlogs sourceleadBlogs = sourceleadBlogService.findByBlogTitle(name);
			if (null != sourceleadBlogs) {
				result = "true";
			}
			else {
				result = "false";
			}
			return result;
		}
		
		/**
		 * To validate Blog Title
		 * 
		 * @param name
		 * @return
		 */
		@RequestMapping(value = "/validateBlogCategoryName", method = RequestMethod.POST)
		public @ResponseBody String validateBlogCategoryName(@RequestParam(value = "categoryName", required = false) String categoryName, HttpServletRequest request) throws Exception
		{
			String result = null;
			BlogCategory  blogCategory = blogCategoryService.findByBlogCategoryName(categoryName);
			if (null != blogCategory) {
				result = "true";
			}
			else {
				result = "false";
			}
			return result;
		}
}
