package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.BlogCategoryDao;
import com.bis.sourcelead.dto.BlogCategoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.BlogCategory;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.BlogCategoryService;
import com.bis.sourcelead.service.SourceleadBlogService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class BlogCategoryServiceImpl implements BlogCategoryService{

	@Autowired
	BlogCategoryDao blogCategoryDao;
	
	@Autowired
	SourceleadBlogService sourceleadBlogService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public List<BlogCategory> getBlogCategoryListByStatus() throws Exception {
		return blogCategoryDao.getBlogCategoryListByStatus();
	}

	@Override
	public BlogCategory addBlogCategory(BlogCategoryDTO blogCategoryDTO, User user) throws Exception {
		
		BlogCategory blogCategory = null;
		
		if(blogCategoryDTO.getCategoryUUID() != null && blogCategoryDTO.getCategoryUUID() !="" && !blogCategoryDTO.getCategoryUUID().equalsIgnoreCase("undefined")) {
			blogCategory = blogCategoryDao.getBlogCategoryByCategoryUUID(blogCategoryDTO.getCategoryUUID());
		}else {
			blogCategory = new BlogCategory();
			
			blogCategory.setCategoryUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			blogCategory.setCreatedBy(user);
			blogCategory.setCreatedDate(commonUtil.currentDate());
			blogCategory.setUpdatedBy(user);

		}
		blogCategory.setCategoryName(blogCategoryDTO.getCategoryName());
		blogCategory.setHref(blogCategoryDTO.getCategoryName().toLowerCase().replaceAll(" ", "-").replaceAll(" & ", "-and-"));
		blogCategory.setDescription(blogCategoryDTO.getDescription());
		blogCategory.setUpdatedDate(commonUtil.currentDate());
		blogCategory.setStatus(Constants.RECORD_ACTIVE);
		blogCategory.setIsDefaultCategory(Constants.NO);
		
		return blogCategoryDao.addBlogCategory(blogCategory);
	}

	@Override
	public BlogCategory getBlogCategoryById(Long id) throws Exception {
		return blogCategoryDao.getBlogCategoryById(id);
	}
	
	@Override
	public ResponseList<BlogCategoryDTO> getAllBlogCategoryByStatus(BlogCategoryDTO blogCategoryDTO) throws Exception {
		
		ResponseList<BlogCategory> blogCategoryList =  blogCategoryDao.getAllBlogCategoryByStatus(blogCategoryDTO);
		ResponseList<BlogCategoryDTO> bcList = new ResponseList<>();
		List<BlogCategoryDTO> cbDTOList = new ArrayList<>();
		for(BlogCategory bc : blogCategoryList.getData()){
			BlogCategoryDTO bcDTO = new BlogCategoryDTO();
			
			bcDTO.setId(bc.getId());
			bcDTO.setCategoryName(bc.getCategoryName());
			
			// this list is for blogs count for category
			List<SourceleadBlogs> blogsList = sourceleadBlogService.getAllSourceleadBlogsByCategoryId(bc.getId());
			bcDTO.setBlogCount(blogsList.size());
			
			bcDTO.setDescription(bc.getDescription());
			bcDTO.setCreatedBy(bc.getCreatedBy().getUserName());
			bcDTO.setStatus(bc.getStatus());
			bcDTO.setCategoryUUID(bc.getCategoryUUID());
			bcDTO.setIsDefaultCategory(bc.getIsDefaultCategory());
			
			cbDTOList.add(bcDTO);
		}
		
		bcList.setRecordsFiltered(blogCategoryList.getRecordsFiltered());
		bcList.setRecordsTotal(blogCategoryList.getRecordsTotal());
		bcList.setData(cbDTOList);
		return bcList;
	}

	@Override
	public BlogCategory removeBlogCategoryByBlogId(Long blogCategoryId) throws Exception {
		BlogCategory blogCategory = blogCategoryDao.getBlogCategoryById(blogCategoryId);
		blogCategory.setStatus(Constants.RECORD_INACTIVE);
		blogCategory =  blogCategoryDao.addBlogCategory(blogCategory);
		
		List<SourceleadBlogs> blogsList = sourceleadBlogService.getSourceleadBlogsByCategoryId(blogCategoryId);
		if(blogsList != null && !blogsList.isEmpty()) {
			BlogCategory defCategoy = new BlogCategory();
			defCategoy.setId(1L);
			for(SourceleadBlogs blogs : blogsList) {
				blogs.setCategoryId(defCategoy);
				sourceleadBlogService.saveOrUpdateBlog(blogs);
			}
		}
        return blogCategory;
	}

	@Override
	public BlogCategory findByBlogCategoryName(String categoryName) throws Exception {
		return blogCategoryDao.findByBlogCategoryName(categoryName);
	}

	@Override
	public BlogCategory getBlogCategoryByCategoryUUID(String categoryUUID) {
		return blogCategoryDao.getBlogCategoryByCategoryUUID(categoryUUID);
	}

	@Override
	public List<BlogCategoryDTO> getBlogCategoryListByActiveBlogs() throws Exception {
		return blogCategoryDao.getBlogCategoryListByActiveBlogs();
	}

	@Override
	public BlogCategory validateBlogCategoryCategoryNameByCategoryNameAndCategoryUUID(String categoryName,
			String categoryUUID) throws Exception {
		return blogCategoryDao.validateBlogCategoryCategoryNameByCategoryNameAndCategoryUUID(categoryName, categoryUUID);
	}

	@Override
	public Boolean validateBlogCategoryCategoryNameByCategoryName(String categoryName) throws Exception {
		return blogCategoryDao.validateBlogCategoryCategoryNameByCategoryName(categoryName);
	}

}
