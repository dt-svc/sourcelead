package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
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

import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class PayrollPayheadDaoImpl implements PayrollPayheadDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
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

	private static final Logger logger = LoggerFactory.getLogger(PayrollPayheadDaoImpl.class);

	@Override
	@Transactional
	public PayrollPayhead savePayrollPayhead(PayrollPayhead payrollPayhead) throws Exception {
		logger.debug("Adding payrollPayhead");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payrollPayhead != null) {
				session.saveOrUpdate(payrollPayhead);
			}
			logger.debug("payrollPayhead Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"PAYROLL_PAYHEAD"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return payrollPayhead;
	}

	@Override
	@Transactional
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadList(PayrollPayheadDTO payrollPayheadDTO,
			OrgLocations orgLocations) throws Exception {
		ResponseList<PayrollPayheadDTO> payrollPayheadDTOList = new ResponseList<>();
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPayhead ph  where (ph.orgLocationId = '1' or ph.orgLocationId =:orgLocationId) and ph.status = '1' ";  
		List<PayrollPayhead> payrollPayheadList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
		
		payrollPayheadDTOList.setData(setPayrollPayheadList(payrollPayheadList,orgLocations));
		return payrollPayheadDTOList;
	}
	private List<PayrollPayheadDTO> setPayrollPayheadList(List<PayrollPayhead> payrollPayheadList,OrgLocations orgLocations) throws Exception {
		List<PayrollPayheadDTO> payrollPayheadDTOList = new ArrayList<>();
		if(payrollPayheadList != null && !payrollPayheadList.isEmpty()) {
			for(PayrollPayhead payRollPayhead : payrollPayheadList) {
				PayrollPayheadDTO PayheadDTO = new PayrollPayheadDTO();
				PayheadDTO.setEffectiveDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payRollPayhead.getEffectiveDate()));
				PayheadDTO.setId(payRollPayhead.getId());
				PayheadDTO.setName(payRollPayhead.getName());
				PayheadDTO.setShortCode(payRollPayhead.getShortCode());
				PayheadDTO.setPayType(payRollPayhead.getPayType().getName());
				PayheadDTO.setTax(payRollPayhead.getTax());
				payrollPayheadDTOList.add(PayheadDTO);
			}
		}
		return payrollPayheadDTOList;
	}
	
	@Override
	@Transactional
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO,OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<PayrollPayheadDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<PayrollPayhead> payrollPayheadList = null;
		 java.sql.Date sqlDate = null;
		if(payrollPayheadDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollPayheadDTO.getSearchValue())) {
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		   Matcher matcher = pattern.matcher(payrollPayheadDTO.getSearchValue());
		    if(matcher.matches() == true) {
						if(payrollPayheadDTO.getSearchValue().contains("/")||(payrollPayheadDTO.getSearchValue().contains("-"))) {
							String replacedValue = payrollPayheadDTO.getSearchValue().replaceAll("/", "-");
								SimpleDateFormat format = new SimpleDateFormat(orgLocations.getDateFormat());
						        java.util.Date utilDate = format.parse(replacedValue);
						        sqlDate = new java.sql.Date(utilDate.getTime());
						}
		    }
		}
		
		StringBuilder query1 = new StringBuilder("");
		if(payrollPayheadDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollPayheadDTO.getSearchValue())) {
			 query1.append(" payhead.name like '" + payrollPayheadDTO.getSearchValue() + "%' ")
			    .append("OR payhead.shortCode like '%" + payrollPayheadDTO.getSearchValue() + "%' ")
			    .append("OR payhead.payType.name like '%" + payrollPayheadDTO.getSearchValue() + "%' ")
				.append("OR payhead.tax like '%" + payrollPayheadDTO.getSearchValue() + "%' ");
		}
		
		String sortingQry = "";
		/*if(StringUtils.isNotBlank(payrollPayheadDTO.getSortColumn())) {
				sortingQry = " order by payhead."+ payrollPayheadDTO.getSortColumn() +" "+payrollPayheadDTO.getSortOrder();
		}*/
		
		query = "from PayrollPayhead payhead where (payhead.orgLocationId = '1' or payhead.orgLocationId =:orgLocationId) and payhead.status = '1' ";
		if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
		}else{
			 queryObj = session.createQuery(query + query1.toString()+" "+sortingQry+" ");
		}
		
		queryObj.setFirstResult(payrollPayheadDTO.getStart());
		queryObj.setMaxResults(payrollPayheadDTO.getLength());
		queryObj.setLong("orgLocationId", orgLocations.getId()).list();
		payrollPayheadList = queryObj.list();
		if(query1.toString() != null && !query1.toString().isEmpty()){
			countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}else {
			countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}
		
		int totalCount = 0;
		
		if (payrollPayheadDTO != null) {
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
		
		response.setData(setPayrollPayheadList(payrollPayheadList,orgLocations));
		return response;
	}
	
	
}
