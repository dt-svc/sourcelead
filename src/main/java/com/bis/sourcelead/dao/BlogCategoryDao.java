package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.BlogCategoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.BlogCategory;

public interface BlogCategoryDao {
	
	BlogCategory addBlogCategory(BlogCategory blogCategory) throws Exception;
	
	public List<BlogCategory> getBlogCategoryListByStatus() throws Exception;
	
	BlogCategory getBlogCategoryById(Long id) throws Exception;
	
	public ResponseList<BlogCategory> getAllBlogCategoryByStatus(BlogCategoryDTO blogCategoryDTO) throws Exception;
	
	public BlogCategory findByBlogCategoryName(String categoryName) throws Exception;
	
	BlogCategory getBlogCategoryByCategoryUUID(String categoryUUID);
	
	public List<BlogCategoryDTO> getBlogCategoryListByActiveBlogs() throws Exception;
	
	public BlogCategory validateBlogCategoryCategoryNameByCategoryNameAndCategoryUUID(String categoryName,String categoryUUID) throws Exception;

	Boolean validateBlogCategoryCategoryNameByCategoryName(String categoryName) throws Exception;
	
}
