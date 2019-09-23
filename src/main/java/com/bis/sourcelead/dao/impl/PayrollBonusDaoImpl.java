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

import com.bis.sourcelead.dao.PayrollBonusDao;
import com.bis.sourcelead.dto.PayrollBonusDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationBankingDetails;
import com.bis.sourcelead.entity.PayrollBonus;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class PayrollBonusDaoImpl implements PayrollBonusDao{

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

	private static final Logger logger = LoggerFactory.getLogger(PayrollBonusDaoImpl.class);

	@Override
	@Transactional
	public PayrollBonus savePayrollBonus(PayrollBonus payrollBonus) throws Exception {
		
		logger.debug("Adding payrollBonus");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payrollBonus != null) {
				session.saveOrUpdate(payrollBonus);
			}
			logger.debug("payrollBonus Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"PAYROLL_BONUS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return payrollBonus;
	}

	@Override
	@Transactional
	public ResponseList<PayrollBonusDTO> getPayrollBonusList(PayrollBonusDTO payrollBonusDTO,OrgLocations orgLocations) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<PayrollBonusDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<PayrollBonus> payrollBonusList = null;
		 java.sql.Date sqlDate = null;
		if(payrollBonusDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollBonusDTO.getSearchValue())) {
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		   Matcher matcher = pattern.matcher(payrollBonusDTO.getSearchValue());
		    if(matcher.matches() == true) {
						if(payrollBonusDTO.getSearchValue().contains("/")||(payrollBonusDTO.getSearchValue().contains("-"))) {
							String replacedValue = payrollBonusDTO.getSearchValue().replaceAll("/", "-");
								SimpleDateFormat format = new SimpleDateFormat(orgLocations.getDateFormat());
						        java.util.Date utilDate = format.parse(replacedValue);
						        sqlDate = new java.sql.Date(utilDate.getTime());
						}
		    }
		}
		
		
		StringBuilder query1 = new StringBuilder("");
		if(payrollBonusDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollBonusDTO.getSearchValue())) {
			 query1.append(" bonus.fromMonth like '" + payrollBonusDTO.getSearchValue() + "%' OR bonus.toMonth like '%" + payrollBonusDTO.getSearchValue() + "' ")
			    .append("OR bonus.paymentMonth like '%" + payrollBonusDTO.getSearchValue() + "%' ")
			    .append("OR bonus.paymentYear like '%" + payrollBonusDTO.getSearchValue() + "%' ")
				.append("OR bonus.minPercentage like '%" + payrollBonusDTO.getSearchValue() + "%' ")
				.append("OR bonus.maxPercentage like '%" + payrollBonusDTO.getSearchValue() + "%' ");
			
			 
		     if(sqlDate != null) {
		    	query1.append("OR bonus.paymentDate like '%" + sqlDate + "%' ");
			 }
					
		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(payrollBonusDTO.getSortColumn())) {
				sortingQry = " order by bonus."+ payrollBonusDTO.getSortColumn() +" "+payrollBonusDTO.getSortOrder();
		}
		
		query = "from PayrollBonus bonus where bonus.orgLocationId.id = :orgLocationsId and bonus.status = '1' ";
		if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
		}else{
			 queryObj = session.createQuery(query + query1.toString()+" "+sortingQry+" ");
		}
		
		queryObj.setFirstResult(payrollBonusDTO.getStart());
		queryObj.setMaxResults(payrollBonusDTO.getLength());
		queryObj.setLong("orgLocationsId", orgLocations.getId()).list();
		payrollBonusList = queryObj.list();
		if(query1.toString() != null && !query1.toString().isEmpty()){
			countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationsId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}else {
			countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationsId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}
		
		
		int totalCount = 0;

		
		if (payrollBonusDTO != null) {
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
		
		response.setData(getPayrollBounsList(payrollBonusList,orgLocations));
		return response;
		
	}
	
	
	private List<PayrollBonusDTO> getPayrollBounsList(List<PayrollBonus> payrollBonusList,OrgLocations orgLocations) throws Exception {
		List<PayrollBonusDTO> payrollBonusDTOList = new ArrayList<>();
		if(payrollBonusList != null && !payrollBonusList.isEmpty()) {
			for(PayrollBonus payRollBonus : payrollBonusList) {
				PayrollBonusDTO bonusDTO = new PayrollBonusDTO();
				bonusDTO.setUuid(payRollBonus.getUuid());
				bonusDTO.setPaymentDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payRollBonus.getPaymentDate()));
				bonusDTO.setPaymentMonth(payRollBonus.getPaymentMonth());
				bonusDTO.setPaymentYear(payRollBonus.getPaymentYear());
				bonusDTO.setMinPercentage(String.valueOf(payRollBonus.getMinPercentage()));
				bonusDTO.setMaxPercentage(String.valueOf(payRollBonus.getMaxPercentage()));
				bonusDTO.setFromMonth(payRollBonus.getFromMonth());
				bonusDTO.setToMonth(payRollBonus.getToMonth());
				payrollBonusDTOList.add(bonusDTO);
			}
		}
		return payrollBonusDTOList;
	}

	@Override
	@Transactional
	public PayrollBonus getPayrollBonusByUUID(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollBonus bonus where bonus.uuid = :uuid and bonus.status = '1' ";  
		List<PayrollBonus> payrollBonusList =  session.createQuery(query).setString("uuid", uuid).list();
		PayrollBonus payrollBonus = null;
		if (payrollBonusList != null && payrollBonusList.size() > 0) {
			payrollBonus = (PayrollBonus) payrollBonusList.iterator().next();
		}
		return payrollBonus;
	}
}
