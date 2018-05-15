package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.EmployeeVerificationTemplateDao;
import com.bis.sourcelead.dto.EmploymentVerificationTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;

@Repository
public class EmployeeVerificationTemplateDaoImpl implements EmployeeVerificationTemplateDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeVerificationTemplateDaoImpl.class);

	@Transactional
	@Override
	public EmployeeVerificationTemplate saveEmployeeVerificationTemplate(EmployeeVerificationTemplate employeeVerificationTemplate) {
		logger.debug("Adding Employee Verification Template");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (employeeVerificationTemplate != null) {
				session.saveOrUpdate(employeeVerificationTemplate);
			}
			logger.debug("Employee Verification Template Added Successfully");
		} catch (Exception exception) {
				logger.error(ExceptionUtils.getFullStackTrace(exception)); 
		}
		return employeeVerificationTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<EmployeeVerificationTemplate> getAllEmploymentVerificationListByOrgLocationId(Long orgLocationId,EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmployeeVerificationTemplate evt where evt.orgLocationId.id =:orgLocationId and evt.templateStatus = '1'";
		
		List<EmployeeVerificationTemplate> employmentVerificationTemplateList = null;
		ResponseList<EmployeeVerificationTemplate> response = new ResponseList<>();
		
		String qry = "";
		if(StringUtils.isNotBlank(employmentVerificationTemplateDTO.getSortColumn())) {
			qry = " order by evt."+ employmentVerificationTemplateDTO.getSortColumn() +" "+employmentVerificationTemplateDTO.getSortOrder();
		}

//		String countQuery = "From EmployeeVerificationTemplate evt where evt.orgLocationId.id =:orgLocationId";
	
		StringBuilder query1 = new StringBuilder("");
			if(employmentVerificationTemplateDTO.getSearchValue() !=null && !"".equalsIgnoreCase(employmentVerificationTemplateDTO.getSearchValue())) {
						
					query1.append(" and (evt.templateName like '%" + employmentVerificationTemplateDTO.getSearchValue() + "%' ")
					.append("OR evt.description like '%" + employmentVerificationTemplateDTO.getSearchValue() + "%' ")
					.append("OR evt.signatory.firstName like '%" + employmentVerificationTemplateDTO.getSearchValue() + "%' ")
//					.append("OR evt.signatory.lastName like '%" + employmentVerificationTemplateDTO.getSearchValue() + "%' ")
					.append("OR evt.signatory.lastName like '%" + employmentVerificationTemplateDTO.getSearchValue() + "%' )");
							
				
			}
		Query queryObj = session.createQuery(query + query1.toString()+qry);
		if (employmentVerificationTemplateDTO != null && employmentVerificationTemplateDTO.getLength() > 0) {
			queryObj.setFirstResult(employmentVerificationTemplateDTO.getStart());
			queryObj.setMaxResults(employmentVerificationTemplateDTO.getLength());
		}
		employmentVerificationTemplateList = queryObj.setLong("orgLocationId", orgLocationId).list();
		int count = 0;
		int filteredCount = 0;

		List<Long> countlist = session.createQuery("select count(*) " + query).setLong("orgLocationId", orgLocationId).list();

		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (employmentVerificationTemplateDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + query + query1.toString()).setLong("orgLocationId", orgLocationId).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} 
			response.setData(employmentVerificationTemplateList);
			return response;
}

	@Override
	@Transactional
	public EmployeeVerificationTemplate getEmploymentVerificationtemplateByTemplateUUID(String tempUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		EmployeeVerificationTemplate  empVerificationTemp =  null;
		String query ="from EmployeeVerificationTemplate evt where evt.templateUUID =:tempUUID";
		List<EmployeeVerificationTemplate> list = session.createQuery(query).setString("tempUUID", tempUUID).list();
		if(null != list && list.size() >0){
			empVerificationTemp = list.get(0);
		}
		return empVerificationTemp;
	}

	@Override
	@Transactional
	public EmployeeVerificationTemplate getEmploymentVerificationtemplateByLocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		EmployeeVerificationTemplate  empVerificationTemp =  null;
		String query ="from EmployeeVerificationTemplate evt where evt.orgLocationId.id =:orgLocationId";
		List<EmployeeVerificationTemplate> list = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if(null != list && list.size() >0){
			empVerificationTemp = list.get(0);
		}
		return empVerificationTemp;
	}

	@Override
	@Transactional
	public EmployeeVerificationTemplate updateEmployMentTemplate(EmployeeVerificationTemplate empTemp) {
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(empTemp);
	    logger.info("Successfully updated..!");
		return empTemp;
	}

}
