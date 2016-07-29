package com.bis.sourcelead.dao.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.exception.NoRecordsFoundException;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.exception.SourceLeadObjectStatusException;

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
		}catch(ConstraintViolationException exception)
			{
				StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORG"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
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
		Org org = null;
		if (id != null) {
			org = session.get(Org.class, id);
		}
		if(org == null)
		{
			StringBuffer message= new StringBuffer(MessageFormat.format(notFound,"ORG", id));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return org;
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
        Org orgList =null;
                Session session = this.sessionFactory.getCurrentSession();
                Criteria crit = session.createCriteria(Org.class);
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
                        flag ="Invalid Website";
                    }
                   
                    //for Blocked Websites Domains
                    String[] SourceLead_BLOCKED_SOCIAL_DOMAINS = sourceleadBLockedwebsites.split(",");
                    if(Arrays.asList(SourceLead_BLOCKED_SOCIAL_DOMAINS).contains(host)){
                        flag="Website Duplicated";
                     }
                    boolean isFuond = isDuplicateWebsite(host);
                    if(isFuond){
                         flag ="Website Duplicated";
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
		if(orgList.isEmpty())
		{
			StringBuffer message= new StringBuffer(MessageFormat.format(noRecords," Orgs ","ORG"));
			throw new NoRecordsFoundException(message.toString());
		}
		return orgList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Org getOrgByOrgCode(String orgCode) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Org> orgList = new ArrayList<Org>();
		logger.debug("getOrgByOrgCode start");
		orgList = session.createQuery("from Org where orgCode=:orgCode" ).setString( "orgCode", orgCode ).list();
		logger.debug("getOrgByOrgCode end");
		if (orgList.size() > 0) {
			return orgList.get(0);
		} else {
			return null;
		}
   	}
	
	private boolean isDuplicateWebsite(String host) {
        boolean isWebSiteFound = false;
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from Org org where org.webSite = :host";
         List<Org> orglist = session.createQuery(query.toString()).setString("host", host).setCacheable(true).list();
            if (orglist != null && orglist.size() >0) {
                isWebSiteFound = true;
            }
            return isWebSiteFound;
    }

}
