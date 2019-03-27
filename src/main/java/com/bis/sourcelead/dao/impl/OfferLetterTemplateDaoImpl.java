package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OfferLetterTemplateDao;
import com.bis.sourcelead.dto.OfferLetterTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OfferLetterTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class OfferLetterTemplateDaoImpl implements OfferLetterTemplateDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(OfferLetterTemplateDaoImpl.class);
	
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
	public OfferLetterTemplate saveOfferLetterTemplate(OfferLetterTemplate offerLetterTemplate) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (offerLetterTemplate != null) {
				session.saveOrUpdate(offerLetterTemplate);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"OFFER_LETTER_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return offerLetterTemplate;
	}


	@Override
	public ResponseList<OfferLetterTemplate> getAllOfferLetterTemplatesByOrgLocationid(OfferLetterTemplateDTO offerLetterTemplateDTO) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<OfferLetterTemplate> response = new ResponseList<>();
		
		StringBuilder query1 = new StringBuilder("");
		if(offerLetterTemplateDTO.getSearchValue() !=null && !"".equalsIgnoreCase(offerLetterTemplateDTO.getSearchValue())) {
		query1.append(" and (offerTemplete.templateName like '%" + offerLetterTemplateDTO.getSearchValue() + "%' ")
				.append("OR offerTemplete.letterHeadId.letterHeadName like '%" + offerLetterTemplateDTO.getSearchValue() + "%' ")
				.append("OR offerTemplete.description like '%" + offerLetterTemplateDTO.getSearchValue() + "%' )");
		}
			
		 String queryStr = "from OfferLetterTemplate offerTemplete  where offerTemplete.orgLocationId.id =:orgLocationId and offerTemplete.templateStatus ='1'";
		 String queryStrForCount = "Select count (*) from OfferLetterTemplate offerTemplete  where offerTemplete.orgLocationId.id =:orgLocationId and offerTemplete.templateStatus ='1'";
		 
		 Query query = session.createQuery(queryStr+query1);
		 Query countQuery = session.createQuery(queryStrForCount+query1);
		 
		 List<OfferLetterTemplate> offerLetterTemplateList = null;

		 offerLetterTemplateList = query.setLong("orgLocationId", offerLetterTemplateDTO.getOrgLocationId()).list();
		 countlist = countQuery.setLong("orgLocationId", offerLetterTemplateDTO.getOrgLocationId()).list();
		 filterCount = countlist.iterator().next().intValue();
		
		 int totalCount = 0;
			if (offerLetterTemplateDTO != null) {
				if(countlist != null && !countlist.isEmpty()) {
					totalCount = countlist.iterator().next().intValue();
				}
			} 
			response.setRecordsTotal(totalCount);
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				response.setRecordsFiltered(filterCount);
			}else {
				response.setRecordsFiltered(totalCount);
			}
		
			response.setData(offerLetterTemplateList);
			return response;
	}


	@Override
	public OfferLetterTemplate getOfferLetterTemplateDetailsByTemplateUUID(String templateUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		OfferLetterTemplate  offerLetterTemplate =  null;
		List<OfferLetterTemplate>  offerLetterTemplateList = session.createQuery("from OfferLetterTemplate oft where oft.templateUUID =:templateUUID").setString("templateUUID", templateUUID).list();
		if(null != offerLetterTemplateList && offerLetterTemplateList.size() >0){
			offerLetterTemplate = offerLetterTemplateList.get(0);
		}
		return offerLetterTemplate;
	}


	@Override
	public OfferLetterTemplate validateOfferLetterTemplateByTemplateUUIDAndTemplateName(String templateUUID, String templateName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OfferLetterTemplate offerLetter  where offerLetter.templateUUID =:templateUUID and offerLetter.templateName =:templateName and offerLetter.templateStatus = '1'";
		List<OfferLetterTemplate> oferLetterTemplateList =session.createQuery(query) .setString("templateUUID", templateUUID).setString("templateName", templateName).list();
		OfferLetterTemplate oferLetterTemplate = null;
		if(oferLetterTemplateList != null && !oferLetterTemplateList.isEmpty())
		{
			oferLetterTemplate = (OfferLetterTemplate) oferLetterTemplateList.iterator().next();
		}
		return oferLetterTemplate;
	}


	@Override
	public Boolean validateOfferLetterTemplateByOrgLocationIdAndTemplateName(Long orgLocationId, String templateName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from OfferLetterTemplate offerLetter where offerLetter.orgLocationId.id =:orgLocationId and offerLetter.templateName =:templateName and offerLetter.templateStatus = '1'";
				Collection<OfferLetterTemplate> oferLetterTemplateList = session.createQuery(query) .setString("templateName", templateName).setLong("orgLocationId", orgLocationId).list();
				if (oferLetterTemplateList != null && oferLetterTemplateList.size() > 0) {
					flag = true;
				}
		return flag;
	}


	@Override
	public List<OfferLetterTemplate> getOfferLetterTemplateListByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<OfferLetterTemplate>  offerLetterTemplateList = session.createQuery("from OfferLetterTemplate oft where oft.orgLocationId.id =:orgLocationId and oft.templateStatus = '1'").setLong("orgLocationId", orgLocationId).list();
		return offerLetterTemplateList;
	}


	@Override
	public OfferLetterTemplate getOfferLetterTemplateDetailsById(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return (OfferLetterTemplate) session.get(OfferLetterTemplate.class, Id);
	}
 
}
