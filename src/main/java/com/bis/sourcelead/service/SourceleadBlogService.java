package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadBlogDTO;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.entity.User;

public interface SourceleadBlogService {

	SourceleadBlogs addBlog(SourceleadBlogDTO sourceleadBlogDTO, User user,String dateFormat) throws Exception;

	SourceleadBlogs saveOrUpdateBlog(SourceleadBlogs sourceleadBlogs) throws Exception;

	SourceleadBlogs getSourceleadBlogsByBlogUUID(String blogUUID);

	SourceleadBlogs getSourceleadBlogsByBlogId(Long blogId) throws Exception;

	public ResponseList<SourceleadBlogDTO> getAllSourceleadBlogsByStatus(SourceleadBlogDTO sourceleadBlogDTO)
			throws Exception;

	public SourceleadBlogs removeSourceleadBlogByBlogId(Long blogId) throws Exception;

	public List<SourceleadBlogs> getAllSourceleadBlogsByCategoryId(Long categoryId) throws Exception;

	public List<SourceleadBlogs> getAllSourceleadBlogsByCategoryName(String categoryName) throws Exception;

	public List<SourceleadBlogs> getSourceleadBlogsByCategoryId(Long categoryId) throws Exception;

	public List<SourceleadBlogs> getAllSourceleadBlogs() throws Exception;

	SourceleadBlogs getSourceleadBlogsByBlogTitle(String blogTitle) throws Exception;

	public List<SourceleadBlogs> getRelatedSourceleadBlogs(Long categoryId) throws Exception;
	
	public SourceleadBlogs validateSourceleadBlogTitleByBlogTitleAndBlogId(String blogTitle,String blogUUID) throws Exception;

	Boolean validateSourceleadBlogTitleByBlogTitle(String blogTitle) throws Exception;
	
	boolean publishBlog(Long id) throws Exception;

}
