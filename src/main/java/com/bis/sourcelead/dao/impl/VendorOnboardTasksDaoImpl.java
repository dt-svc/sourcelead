package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.VendorOnboardTasksDao;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class VendorOnboardTasksDaoImpl implements VendorOnboardTasksDao{
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(VendorOnboardTasksDaoImpl.class);


	@Override
	@Transactional
	public List<VendorOnboardTasks> getVendorOnboardTasksByTemplateId(Long templateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from VendorOnboardTasks tasks left join fetch tasks.vendorOnboardingTemplateId where tasks.vendorOnboardingTemplateId.id = :templateId "
	    		+ " and tasks.status = '1' ";
	    List<VendorOnboardTasks> tasksList = session.createQuery(query.toString()).setLong("templateId", templateId).list();
        return tasksList;
	}


	@Override
	@Transactional
	public VendorOnboardTasks saveVendorOnboardTasks(VendorOnboardTasks vendorOnboardTasks) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (vendorOnboardTasks != null) {
				session.saveOrUpdate(vendorOnboardTasks);
			}
			logger.debug("onboardTasks Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"VENDOR_ONBOARD_TASKS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return vendorOnboardTasks;
	}


	@Override
	@Transactional
	public VendorOnboardTasks getVendorOnboardTasksByTaskId(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<VendorOnboardTasks> vendorOnboardTasks = session.createQuery("from VendorOnboardTasks tasks where tasks.id =:id" ).setLong( "id", Id ).list();
		if (vendorOnboardTasks.size() > 0) {
			return vendorOnboardTasks.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public List<VendorOnboardTasks> getVendorOnboardTasksByTemplateIdsList(List<Long> templateIdsList)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from VendorOnboardTasks tasks left join fetch tasks.vendorOnboardingTemplateId where tasks.vendorOnboardingTemplateId.id IN (:templateIdsList) "
	    		+ " and tasks.status = '1' ";
	    List<VendorOnboardTasks> tasksList = session.createQuery(query.toString()).setParameterList("templateIdsList", templateIdsList).list();
        return tasksList;
	}


	@Override
	@Transactional
	public List<VendorOnboardTasks> getVendorOnboardTasksByVendorOnboardId(Long vendorOnboardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from VendorOnboardTasks tasks left join fetch tasks.vendorOnboardingId where tasks.vendorOnboardingId.id = :vendorOnboardingId "
	    		+ " and tasks.status = '1' ";
	    List<VendorOnboardTasks> tasksList = session.createQuery(query.toString()).setLong("vendorOnboardingId", vendorOnboardingId).list();
        return tasksList;
	}


	@Override
	@Transactional
	public Integer getTemplateTasksCountByTemplateId(Long templateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from VendorOnboardTasks tasks where tasks.vendorOnboardingTemplateId.id = :templateId and  tasks.status = '1'";
		List<Long> tasksCount = session.createQuery(query.toString()).setLong("templateId", templateId).list();
			Integer count = 0;
			if(tasksCount != null && !tasksCount.isEmpty()){
					count = tasksCount.get(0).intValue();
			}
			return count;
	}

}
