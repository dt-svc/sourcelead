package com.bis.sourcelead.dao.impl;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadBlogDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadBlogDTO;
import com.bis.sourcelead.entity.SourceleadBlogs;

@Repository
public class SourceleadBlogDaoImpl implements SourceleadBlogDao {

	@Autowired
	private SessionFactory sessionFactory;

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

	private static final Logger logger = LoggerFactory.getLogger(SourceleadBlogDaoImpl.class);

	@Override
	@Transactional
	public SourceleadBlogs addBlog(SourceleadBlogs sourceleadBlogs) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (sourceleadBlogs != null) {
				session.saveOrUpdate(sourceleadBlogs);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return sourceleadBlogs;
	}

	@Override
	@Transactional
	public SourceleadBlogs getSourceleadBlogsByBlogUUID(String blogUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadBlogs blogs  where blogs.blogUUID = :blogUUID";
		SourceleadBlogs sourceleadBlogs = null;
		List<SourceleadBlogs> blogList = session.createQuery(query).setString("blogUUID", blogUUID).setCacheable(false)
				.list();
		if (blogList != null && blogList.size() > 0) {
			sourceleadBlogs = blogList.get(0);
		}
		return sourceleadBlogs;
	}

	@Override
	@Transactional
	public SourceleadBlogs getSourceleadBlogsByBlogId(Long blogId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadBlogs blogs where blogs.id = :blogId";
		SourceleadBlogs sourceleadBlogs = null;
		List<SourceleadBlogs> blogList = session.createQuery(query).setLong("blogId", blogId).setCacheable(false)
				.list();
		if (blogList != null && blogList.size() > 0) {
			sourceleadBlogs = blogList.get(0);
		}
		return sourceleadBlogs;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<SourceleadBlogs> getAllSourceleadBlogsByStatus(SourceleadBlogDTO sourceleadBlogDTO)
			throws ParseException {
		Session session = this.sessionFactory.getCurrentSession();
		Integer filterCount = 0;
		ResponseList<SourceleadBlogs> response = new ResponseList<>();

		List<Long> countlist = null;
		StringBuilder query1 = new StringBuilder("");
		if (sourceleadBlogDTO.getSearchValue() != null && !"".equalsIgnoreCase(sourceleadBlogDTO.getSearchValue())) {
			query1.append("and( (sb.createdBy.firstName like '" + sourceleadBlogDTO.getSearchValue()
					+ "%' OR sb.createdBy.lastName like '%" + sourceleadBlogDTO.getSearchValue() + "' )")
					.append("OR (concat(sb.createdBy.firstName,sb.createdBy.lastName) like '%"
							+ sourceleadBlogDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')")
					.append("OR sb.blogTitle like '%" + sourceleadBlogDTO.getSearchValue() + "%' ")
					.append("OR sb.categoryId.categoryName like '%" + sourceleadBlogDTO.getSearchValue() + "%' ")
					.append("OR sb.publishOn like '%" + sourceleadBlogDTO.getSearchValue() + "%' ")
					.append("OR sb.blogStatus like '%" + sourceleadBlogDTO.getSearchValue() + "%') ) ");

		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(sourceleadBlogDTO.getSortColumn())) {
				sortingQry = " order by sb."+ sourceleadBlogDTO.getSortColumn() +" "+sourceleadBlogDTO.getSortOrder();
		}
		

		String query = "from SourceleadBlogs sb left join fetch sb.createdBy user where sb.status = 1 " ;
		List<SourceleadBlogs> sourceleadBlogsList = session.createQuery(query.toString()+" "+ query1+sortingQry)
				.setFirstResult(sourceleadBlogDTO.getStart()).setMaxResults(sourceleadBlogDTO.getLength()).list();

		String countQuery = "select count (*) from SourceleadBlogs sb where sb.status = 1 " + query1 + "";
		Query listCountQuery = session.createQuery(countQuery+sortingQry);
		countlist = listCountQuery.list();

		int totalCount = 0;
		if (sourceleadBlogDTO != null) {
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

		response.setData(sourceleadBlogsList);
		return response;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadBlogs> getAllSourceleadBlogsByCategoryName(String categoryName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from SourceleadBlogs sb left join fetch sb.categoryId cat where cat.href =:categoryName and cat.status = 1 and  sb.status = 1 ORDER BY sb.id DESC";
		List<SourceleadBlogs> sourceleadBlogsList = session.createQuery(query.toString())
				.setString("categoryName", categoryName).list();
		return sourceleadBlogsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadBlogs> getAllSourceleadBlogsByCategoryId(Long categoryId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from SourceleadBlogs sb left join fetch sb.categoryId cat where cat.id =:categoryId and cat.status= 1 and  sb.status = 1 ORDER BY sb.id DESC";
		List<SourceleadBlogs> sourceleadBlogsList = session.createQuery(query.toString())
				.setLong("categoryId", categoryId).list();
		return sourceleadBlogsList;
	}

	@Override
	@Transactional
	public List<SourceleadBlogs> getSourceleadBlogsByCategoryId(Long categoryId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadBlogs sb left join fetch sb.categoryId cat where cat.id =:categoryId ORDER BY sb.id DESC";
		List<SourceleadBlogs> sourceleadBlogsList = session.createQuery(query.toString())
				.setLong("categoryId", categoryId).list();
		return sourceleadBlogsList;

	}

	@Override
	@Transactional
	public List<SourceleadBlogs> getAllSourceleadBlogs() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadBlogs sb where sb.blogStatus = 'published' and sb.status = '1' ORDER BY sb.id DESC";
		List<SourceleadBlogs> allBlogs = session.createQuery(query.toString()).list();
		return allBlogs;
	}

	@Override
	@Transactional
	public SourceleadBlogs getSourceleadBlogsByBlogTitle(String blogTitle) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadBlogs sb where sb.href =:blogTitle";
		SourceleadBlogs sourceleadBlogs = null;
		List<SourceleadBlogs> blogList = session.createQuery(query).setString("blogTitle", blogTitle)
				.setCacheable(false).list();
		if (blogList != null && blogList.size() > 0) {
			sourceleadBlogs = blogList.get(0);
		}
		return sourceleadBlogs;
	}

	@Override
	@Transactional
	public List<SourceleadBlogs> getRelatedSourceleadBlogs(Long blogId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadBlogs sb where sb.status = 1 and sb.id not in(" + blogId
				+ ") ORDER BY sb.id desc";
		Query queryObj = session.createQuery(query);
		queryObj.setMaxResults(5);
		List<SourceleadBlogs> sourceleadBlogsList = queryObj.list();
		return sourceleadBlogsList;
	}

	@Override
	@Transactional
	public SourceleadBlogs validateSourceleadBlogTitleByBlogTitleAndBlogId(String blogTitle, String blogUUID)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadBlogs sb  where sb.blogUUID =:blogUUID and sb.blogTitle =:blogTitle and sb.status = 1";
		List<SourceleadBlogs> sourceleadBlogList = session.createQuery(query).setString("blogTitle", blogTitle)
				.setString("blogUUID", blogUUID).list();
		SourceleadBlogs sourceleadBlogs = null;
		if (sourceleadBlogList != null && !sourceleadBlogList.isEmpty()) {
			sourceleadBlogs = (SourceleadBlogs) sourceleadBlogList.iterator().next();
		}
		return sourceleadBlogs;
	}

	@Override
	@Transactional
	public Boolean validateSourceleadBlogTitleByBlogTitle(String blogTitle) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from SourceleadBlogs sb where sb.blogTitle =:blogTitle and sb.status = 1";
		Collection<SourceleadBlogs> sourceleadBlogList = session.createQuery(query).setString("blogTitle", blogTitle)
				.list();
		if (sourceleadBlogList != null && sourceleadBlogList.size() > 0) {
			flag = true;
		}
		return flag;
	}

}
