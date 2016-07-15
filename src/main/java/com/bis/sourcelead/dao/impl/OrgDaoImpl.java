package com.bis.sourcelead.dao.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */
@Repository
public class OrgDaoImpl implements OrgDao {
	private static final Logger logger = LoggerFactory.getLogger(OrgDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org addOrganization(Org org) throws Exception
	{
		logger.debug("Adding Organization");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (org != null) {
				session.save(org);
			}
			logger.debug("Organization Added Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return org;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org updateOrg(Org org) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		if (org != null) {
			session.saveOrUpdate(org);
		}
		return org;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org findOrgByID(Long id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		logger.info("Record fetched successfully..!");
		if (id != null) {
			Org org1 = session.get(Org.class, id);
		}
		return session.get(Org.class, id);
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteOrg(Long id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		if (id != null) {
			Org org = (Org) session.get(Org.class, id);
			org.setStatus(Status.INACTIVE);
			session.saveOrUpdate(org);
			isDeleted = true;
			logger.info("Successfully deleted..!");
		}
		return isDeleted;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String websiteValidation(String webSite,String sourceleadBLockedwebsites) {
		Collection<Org> orgList =null;
				Session session = this.sessionFactory.getCurrentSession();
				Criteria crit = session.createCriteria(Org.class);
				 orgList = crit.list();
				  String flag = null;
				  
				 if(!webSite.startsWith("http") && !webSite.startsWith("https")){
						webSite = "http://" + webSite;
				    }     
					String status = null;
				    URL netUrl = null;
					try {
						netUrl = new URL(webSite);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    String host = netUrl.getHost();
				    if(host.startsWith("www")){
				    	if(host.length()>3){
				    		host = host.substring("www".length()+1);
				    	}else{
				    		flag="Website Duplicated";
				    	}
				    }else{
				    	host =null;
				    }
				    
				    //for Blocked Websites Domains
				    String[] SourceLead_BLOCKED_SOCIAL_DOMAINS = sourceleadBLockedwebsites.split(",");
				    if(Arrays.asList(SourceLead_BLOCKED_SOCIAL_DOMAINS).contains(host)){
				    	flag="Website Duplicated";
		 	    	}
					if (orgList != null && orgList.size() > 0 && host !=null) {
					    for(Org org :orgList){
					    	if((org.getWebSite()!= null && (org.getWebSite().equalsIgnoreCase(host)))){
								   flag ="Website Duplicated"; 
						    }
					    }
					}
		
		return flag;
	}	
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Org> getOrgs() throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Org.class);
		List<Org> orgList = crit.list();
		return orgList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org getOrgByOrgCode(String orgCode) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Org> org = new ArrayList<Org>();
		logger.debug("getOrgByOrgCode start");
		org = session.createQuery("from Org where orgCode=:orgCode" ).setString( "orgCode", orgCode ).list();
		logger.debug("getOrgByOrgCode end");
		if (org.size() > 0) {
			return org.get(0);
		} else {
			return null;
		}
   	}
}
