package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.BlogCategoryDao;
import com.bis.sourcelead.dto.BlogCategoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.BlogCategory;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class BlogCategoryDaoImpl implements BlogCategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CommonUtil commonUtil;

	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;

	private static final Logger logger = LoggerFactory.getLogger(BlogCategoryDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<BlogCategory> getBlogCategoryListByStatus() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from BlogCategory bc where bc.status = 1";
		List<BlogCategory> blogcatList = session.createQuery(query).list();
		return blogcatList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BlogCategory addBlogCategory(BlogCategory blogCategory) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (blogCategory != null) {
				session.saveOrUpdate(blogCategory);
			}
		} catch (ConstraintViolationException exception) {
		}
		return blogCategory;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BlogCategory getBlogCategoryById(Long blogCategoryId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from BlogCategory bc where bc.id = :blogCategoryId";
		BlogCategory blogCategory = null;
		List<BlogCategory> bcList = session.createQuery(query).setLong("blogCategoryId", blogCategoryId)
				.setCacheable(false).list();
		if (bcList != null && bcList.size() > 0) {
			blogCategory = bcList.get(0);
		}
		return blogCategory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<BlogCategory> getAllBlogCategoryByStatus(BlogCategoryDTO blogCategoryDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Integer filterCount = 0;
		ResponseList<BlogCategory> response = new ResponseList<>();
		List<Long> countlist = null;
		StringBuilder query1 = new StringBuilder("");
		if (blogCategoryDTO.getSearchValue() != null && !"".equalsIgnoreCase(blogCategoryDTO.getSearchValue())) {
			   query1.append("and( (bc.createdBy.firstName like '" + blogCategoryDTO.getSearchValue() + "%' OR bc.createdBy.lastName like '%" + blogCategoryDTO.getSearchValue() + "' )")
		      .append("OR (concat(bc.createdBy.firstName,bc.createdBy.lastName) like '%" + blogCategoryDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')" )
	          .append("OR bc.categoryName like '%" + blogCategoryDTO.getSearchValue() + "%' ")
		      .append("OR bc.description like '%" + blogCategoryDTO.getSearchValue() + "%') ) ");
		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(blogCategoryDTO.getSortColumn())) {
				sortingQry = " order by bc."+ blogCategoryDTO.getSortColumn() +" "+blogCategoryDTO.getSortOrder();
		}
		String query = "from BlogCategory bc where bc.status = 1 ";
		List<BlogCategory> blogCategoryList = session.createQuery(query.toString()+" "+ query1+sortingQry)
				.setFirstResult(blogCategoryDTO.getStart()).setMaxResults(blogCategoryDTO.getLength()).list();

		String countQuery = "select count (*) from BlogCategory bc where bc.status = 1 " + query1 + "";
		Query listCountQuery = session.createQuery(countQuery);
		countlist = listCountQuery.list();

		int totalCount = 0;
		if (blogCategoryDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		}
		response.setRecordsTotal(totalCount);

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			response.setRecordsFiltered(filterCount);
		} else {
			response.setRecordsFiltered(totalCount);
		}

		response.setData(blogCategoryList);
		return response;
	}

	@Transactional(rollbackFor = { Exception.class })
	public BlogCategory findByBlogCategoryName(String categoryName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BlogCategory> blogCategory = new ArrayList<BlogCategory>();
		logger.debug("findByCategoryName start");
		try {
			blogCategory = session
					.createQuery("from BlogCategory bc where bc.categoryName=:categoryName  and bc.status = '1' ")
					.setString("categoryName", categoryName).list();

			logger.debug("findByCategoryName end");
			if (blogCategory.size() > 0) {
				return blogCategory.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BlogCategory getBlogCategoryByCategoryUUID(String categoryUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from BlogCategory bc  where bc.categoryUUID = :categoryUUID";
		BlogCategory blogCategory = null;
		List<BlogCategory> blogCategoryList = session.createQuery(query).setString("categoryUUID", categoryUUID)
				.setCacheable(false).list();
		if (blogCategoryList != null && blogCategoryList.size() > 0) {
			blogCategory = blogCategoryList.get(0);
		}
		return blogCategory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<BlogCategoryDTO> getBlogCategoryListByActiveBlogs() throws Exception {
		List<BlogCategoryDTO> blogCategoryList = new LinkedList<>();
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select distinct sb.categoryId from SourceleadBlogs sb where sb.status=1";
		List<BlogCategory> bcList = session.createQuery(query).list();
		if (bcList != null && bcList.size() > 0) {
			
			for (BlogCategory b : bcList) {
				BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();
				blogCategoryDTO.setCategoryName(b.getCategoryName());
				blogCategoryDTO.setHref(b.getHref());
				blogCategoryDTO.setBlogCount(b.getBlogList().size());
				blogCategoryList.add(blogCategoryDTO);
			}
		}
		return blogCategoryList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BlogCategory validateBlogCategoryCategoryNameByCategoryNameAndCategoryUUID(String categoryName,
			String categoryUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from BlogCategory bc  where bc.categoryUUID =:categoryUUID and bc.categoryName =:categoryName and bc.status=1";
		List<BlogCategory> blogCategoryList =session.createQuery(query) .setString("categoryName", categoryName).setString("categoryUUID", categoryUUID).list();
		BlogCategory blogCategory = null;
		if(blogCategoryList != null && !blogCategoryList.isEmpty())
		{
			blogCategory = (BlogCategory) blogCategoryList.iterator().next();
		}
		return blogCategory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateBlogCategoryCategoryNameByCategoryName(String categoryName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from BlogCategory bc where bc.categoryName =:categoryName and bc.status=1";
				Collection<BlogCategory> blogCategoryList = session.createQuery(query) .setString("categoryName", categoryName).list();
				if (blogCategoryList != null && blogCategoryList.size() > 0) {
					flag = true;
				}
		     return flag;
	}

}
