package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
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

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dao.SourceleadBlogDao;
import com.bis.sourcelead.dto.BlogCategoryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadBlogDTO;
import com.bis.sourcelead.entity.BlogCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.BlogCategoryService;
import com.bis.sourcelead.service.OrgLocationService;
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
	
	@Autowired
	OrgLocationService orgLocationService;

	/**
	 * PrasadK this method for Add Blog
	 * 
	 * @param sourceleadBlogDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_BLOG')")
	@RequestMapping(value = { "/addBlog", "/rest/addBlog" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> addBlog(SourceleadBlogDTO sourceleadBlogDTO, HttpServletRequest request) throws Exception {

		Response<Boolean> response = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
		try {
			if (loginUser != null) {

				if (Validator.validate(sourceleadBlogDTO)) {
					sourceleadBlogDTO.setStaticPath(staticPath);
					SourceleadBlogs sourceleadBlogs = sourceleadBlogService.addBlog(sourceleadBlogDTO, loginUser,orgLocation.getDateFormat());
					response.setObject(true);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}

			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * Prasad K get the Blogs List
	 * 
	 * @param sourceleadBlogDTO
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = { "/blogsList", "rest/blogsList" }, method = RequestMethod.POST)
	public ResponseList<SourceleadBlogDTO> blogsList(SourceleadBlogDTO sourceleadBlogDTO,HttpServletRequest request) throws Exception {
		ResponseList<SourceleadBlogDTO> responseList = new ResponseList<>();
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		try {
			String[] tableColumns = {"blogTitle", "categoryId", "createdBy"};
				int sortColumnIndex = 5; //by default sorting using updated date
				String sortColumnOrder = "asc"; //by default descending order
				
				if(sourceleadBlogDTO.getOrder() != null && !sourceleadBlogDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(sourceleadBlogDTO.getOrder().get(0).get("column"));
					sortColumnOrder = sourceleadBlogDTO.getOrder().get(0).get("dir");
				}
				sourceleadBlogDTO.setSortColumn(tableColumns[sortColumnIndex]);
				sourceleadBlogDTO.setSortOrder(sortColumnOrder);
				sourceleadBlogDTO.setOrgLocationDateFormate(orgLocations.getDateFormat());
			
			responseList = sourceleadBlogService.getAllSourceleadBlogsByStatus(sourceleadBlogDTO);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}

	@RequestMapping(value = "/removeSourceleadBlogByBlogId")
	public Response<String> removeSourceleadBlogByBlogId(@RequestParam(value = "blogId", required = false) Long blogId,HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		try {
			if (loginUser != null) {
			if(blogId >0) {
			sourceleadBlogService.removeSourceleadBlogByBlogId(blogId);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());	
			}
			}else {
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

	/**
	 * saving add Blog Category
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = { "/addBlogCategory", "/rest/addBlogCategory" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> addBlogCategory(BlogCategoryDTO blogCategoryDTO, HttpServletRequest request) throws Exception {
         
		Response<Boolean> response = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if (loginUser != null) {

				if (Validator.validate(blogCategoryDTO)) {
					BlogCategory blogCategory = blogCategoryService.addBlogCategory(blogCategoryDTO, loginUser);
					response.setObject(true);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}

			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = { "/blogCategoryList", "rest/blogCategoryList" }, method = RequestMethod.POST)
	public ResponseList<BlogCategoryDTO> blogCategoryList(BlogCategoryDTO blogCategoryDTO,HttpServletRequest request) throws Exception  {

		ResponseList<BlogCategoryDTO> responseList = new ResponseList<>();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		try {
			
			 
	   	   String[] tableColumns = {"categoryName", "description", "blogCount", "createdBy"};
			int sortColumnIndex = 5; //by default sorting using updated date
			String sortColumnOrder = "asc"; //by default descending order
			
			if(blogCategoryDTO.getOrder() != null && !blogCategoryDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(blogCategoryDTO.getOrder().get(0).get("column"));
				sortColumnOrder = blogCategoryDTO.getOrder().get(0).get("dir");
			}
			blogCategoryDTO.setSortColumn(tableColumns[sortColumnIndex]);
			blogCategoryDTO.setSortOrder(sortColumnOrder);
			blogCategoryDTO.setOrgLocationDateFormate(orgLocations.getDateFormat());
			
			responseList = blogCategoryService.getAllBlogCategoryByStatus(blogCategoryDTO);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}

	@RequestMapping(value = "/removeBlogCategoryByBlogId")
	public Response<String> removeBlogCategoryByBlogId(
			@RequestParam(value = "blogCategoryId", required = false) Long blogCategoryId, HttpServletRequest request) throws Exception {

		Response<String> response = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		try {
			if (loginUser != null) {
			if(blogCategoryId >0) {
			blogCategoryService.removeBlogCategoryByBlogId(blogCategoryId);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());	
			}
			}else {
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

	@RequestMapping(value = { "/getBlogCategoryById", "/rest/getBlogCategoryById" }, method = RequestMethod.POST)
	public BlogCategoryDTO getBlogCategoryById(BlogCategoryDTO blogCategoryDTO) throws Exception {

		BlogCategoryDTO bcDTO = new BlogCategoryDTO();
		if (blogCategoryDTO.getCategoryUUID() != null) {
			BlogCategory blogCategory = blogCategoryService
					.getBlogCategoryByCategoryUUID(blogCategoryDTO.getCategoryUUID());

			if (blogCategory.getCategoryName() != null) {
				bcDTO.setCategoryName(blogCategory.getCategoryName());
			}

			if (blogCategory.getDescription() != null) {
				bcDTO.setDescription(blogCategory.getDescription());
			}
		}
		return bcDTO;
	}

	@RequestMapping(value = "/deleteBlogImg", method = RequestMethod.POST)
	public @ResponseBody Response<String> deleteBlogImg(@RequestParam(value = "blogId", required = false) Long blogId,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		try {
			if (loginUser != null) {
			if(blogId >0) {
			SourceleadBlogs sourceleadBlogs = sourceleadBlogService.getSourceleadBlogsByBlogId(blogId);
			if (sourceleadBlogs != null) {
				sourceleadBlogs.setImgFileUrl(null);
				sourceleadBlogs.setFileName(null);
				sourceleadBlogs.setFileContentType(null);
				sourceleadBlogDao.addBlog(sourceleadBlogs);
			}
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());	
			}
			}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());	
			}
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return response;
		
		
		
		/*SourceleadBlogs sourceleadBlogs = sourceleadBlogService.getSourceleadBlogsByBlogId(blogId);
		if (sourceleadBlogs != null) {
			sourceleadBlogs.setImgFileUrl(null);
			sourceleadBlogs.setFileName(null);
			sourceleadBlogs.setFileContentType(null);
			sourceleadBlogDao.addBlog(sourceleadBlogs);
		}
		return "success";*/
	}

	/**
	 * To validate Blog Title
	 * 
	 * @param blogTitle
	 * @param blogId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateBlogTitle", method = RequestMethod.POST)
	public @ResponseBody Boolean validateBlogTitle(@RequestParam("blogTitle") String blogTitle,
			@RequestParam("blogUUID") String blogUUID) throws Exception {
		SourceleadBlogs sourceleadBlogs = null;
		if (blogUUID != null) {
			sourceleadBlogs = sourceleadBlogService.validateSourceleadBlogTitleByBlogTitleAndBlogId(blogTitle,
					blogUUID);
		}
		Boolean flag = false;
		if (null == sourceleadBlogs) {
			flag = sourceleadBlogService.validateSourceleadBlogTitleByBlogTitle(blogTitle);
		}
		return flag;
	}

	/**
	 * Validate categoryName
	 * 
	 * @param categoryName
	 * @param categoryUUID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateBlogCategoryName", method = RequestMethod.POST)
	public @ResponseBody Boolean validateBlogCategoryName(@RequestParam("categoryName") String categoryName,
			@RequestParam("categoryUUID") String categoryUUID) throws Exception {
		BlogCategory blogCategory = null;
		if (categoryUUID != null) {
			blogCategory = blogCategoryService
					.validateBlogCategoryCategoryNameByCategoryNameAndCategoryUUID(categoryName, categoryUUID);
		}
		Boolean flag = false;
		if (null == blogCategory) {
			flag = blogCategoryService.validateBlogCategoryCategoryNameByCategoryName(categoryName);
		}
		return flag;
	}
	
	@RequestMapping(value = "/publishBlog", method = RequestMethod.POST)
	 public @ResponseBody  boolean publishBlog(@RequestParam(value = "blogId", required = false) Long blogId,HttpServletRequest request) throws Exception {
		return sourceleadBlogService.publishBlog(blogId);
	 }
	

}
