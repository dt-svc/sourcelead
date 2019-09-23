package com.bis.sourcelead.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.BlogSubscriptionDao;
import com.bis.sourcelead.entity.BlogCategory;
import com.bis.sourcelead.entity.BlogSubscription;
import com.bis.sourcelead.entity.SourceleadBlogs;

@Repository
public class BlogSubscriptionDaoImpl implements BlogSubscriptionDao{

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
	
	private static final Logger logger = LoggerFactory.getLogger(BlogSubscriptionDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BlogSubscription addBlogSubscription(BlogSubscription blogSubscription) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (blogSubscription != null) {
				session.saveOrUpdate(blogSubscription);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return blogSubscription;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BlogSubscription getBlogSubscriptionByBlogId(Long blogSubscriptionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from BlogSubscription bs where bs.id = :blogSubscriptionId";
		BlogSubscription blogSubscription = null;
		List<BlogSubscription> blogSubscriptionList = session.createQuery(query).setLong("blogSubscriptionId", blogSubscriptionId).setCacheable(false).list();
		if(blogSubscriptionList !=null && blogSubscriptionList.size() >0){
			blogSubscription =  blogSubscriptionList.get(0);
		}
		return blogSubscription;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BlogSubscription validateBlogSubscriptionByEmailAndSubscriptionUUID(String email, String subscriptionUUID) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from BlogSubscription bs  where bs.subscriptionUUID =:subscriptionUUID and bs.email =:email ";
		List<BlogSubscription> blogSubscriptionList =session.createQuery(query) .setString("email", email).setString("subscriptionUUID", subscriptionUUID).list();
		BlogSubscription blogSubscription = null;
		if(blogSubscriptionList != null && !blogSubscriptionList.isEmpty())
		{
			blogSubscription = (BlogSubscription) blogSubscriptionList.iterator().next();
		}
		return blogSubscription;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateBlogSubscriptionByEmail(String email) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from BlogSubscription bs where bs.email =:email";
				Collection<BlogSubscription> blogSubscriptionList = session.createQuery(query) .setString("email", email).list();
				if (blogSubscriptionList != null && blogSubscriptionList.size() > 0) {
					flag = true;
				}
		     return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BlogSubscription getBlogSubscriptionBySubscriptionUUID(String subscriptionUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from BlogSubscription bs  where bs.subscriptionUUID = :subscriptionUUID";
		BlogSubscription blogSubscription = null;
		List<BlogSubscription> blogSubscriptionList = session.createQuery(query).setString("subscriptionUUID", subscriptionUUID).setCacheable(false).list();
		if(blogSubscriptionList !=null && blogSubscriptionList.size() >0){
			blogSubscription =  blogSubscriptionList.get(0);
		}
		return blogSubscription;
	}
	
	

}
