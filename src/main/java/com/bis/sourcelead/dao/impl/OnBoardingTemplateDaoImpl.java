package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OnBoardingTemplateDao;
import com.bis.sourcelead.entity.OnBoardingTemplate;

@Repository
public class OnBoardingTemplateDaoImpl implements OnBoardingTemplateDao{

	
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
	

	private static final Logger logger = LoggerFactory.getLogger(OnBoardingTemplateDaoImpl.class);
	
	

	@Override
	@Transactional
	public List<OnBoardingTemplate> getOnBoardingTemplatesList(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OnBoardingTemplate template left join fetch template.orgLocationId where template.orgLocationId.id =:orgLocationId ";
	    List<OnBoardingTemplate> onBoardingTemplateList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
        return onBoardingTemplateList;
	}

}
