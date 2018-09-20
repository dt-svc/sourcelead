package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadBlogDTO;
import com.bis.sourcelead.entity.SourceleadBlogs;

public interface SourceleadBlogDao {

	SourceleadBlogs addBlog(SourceleadBlogs sourceleadBlogs) throws Exception;

	SourceleadBlogs getSourceleadBlogsByBlogUUID(String blogUUID);

	SourceleadBlogs getSourceleadBlogsByBlogId(Long blogId) throws Exception;

	public ResponseList<SourceleadBlogs> getAllSourceleadBlogsByStatus(SourceleadBlogDTO sourceleadBlogDTO)
			throws Exception;

	public List<SourceleadBlogs> getAllSourceleadBlogsByCategoryName(String categoryName) throws Exception;

	public List<SourceleadBlogs> getAllSourceleadBlogsByCategoryId(Long categoryId) throws Exception;

	public List<SourceleadBlogs> getSourceleadBlogsByCategoryId(Long categoryId) throws Exception;

	public List<SourceleadBlogs> getAllSourceleadBlogs() throws Exception;

	SourceleadBlogs getSourceleadBlogsByBlogTitle(String blogTitle) throws Exception;

	public List<SourceleadBlogs> getRelatedSourceleadBlogs(Long categoryId) throws Exception;
	
	public SourceleadBlogs validateSourceleadBlogTitleByBlogTitleAndBlogId(String blogTitle,String blogUUID) throws Exception;

	Boolean validateSourceleadBlogTitleByBlogTitle(String blogTitle) throws Exception;

}
