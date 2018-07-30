package com.bis.sourcelead.payroll.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
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

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.USPaysheetDao;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

@Repository
public class USPaysheetDaoImpl implements USPaysheetDao{

	private static final Logger logger = LoggerFactory.getLogger(USPaysheetDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public PaysheetFrequency addPaysheetFrequency(PaysheetFrequency paysheetFrequency)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (paysheetFrequency != null) {
				session.saveOrUpdate(paysheetFrequency);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return paysheetFrequency;
	}

	@Override
	@Transactional
	public ResponseList<PaysheetFrequency> getPaySheetFrequencyListByOrgLocationId(OrgLocations orgLocation,PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from PaysheetFrequency pf where pf.orgLocation.id =:orgLocationId and pf.status = '1' and pf.type = 'PAYROLL'";
		
		List<PaysheetFrequency> freqList = null;
		ResponseList<PaysheetFrequency> response = new ResponseList<>();
		StringBuilder query1 = new StringBuilder("");
		if(StringUtils.isNotBlank(paysheetFrequencyDTO.getSearchValue())){
			if(paysheetFrequencyDTO.getSearchValue().contains("/") || paysheetFrequencyDTO.getSearchValue().contains("-")){
			String convertedDate = paysheetFrequencyDTO.getSearchValue();
			if(paysheetFrequencyDTO.getSearchValue().contains("/")){
				convertedDate = paysheetFrequencyDTO.getSearchValue().replaceAll("/", "-");
			}
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newDate = parsedDate.format(f2);
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
            String queryDate = "from PaysheetFrequency pf where pf.orgLocation.id =:orgLocationId and ( pf.frequencyStartDate =:sqlDate or pf.frequencyEndDate =:sqlDate or pf.payCheckDate =:sqlDate)";
            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
            response.setData(queryObjDate.list());
			}else{
				if(paysheetFrequencyDTO.getSearchValue() !=null && !"".equalsIgnoreCase(paysheetFrequencyDTO.getSearchValue())) {
				query1.append(" and (pf.userGroup.payFrequency like '%" + paysheetFrequencyDTO.getSearchValue() + "%' ")
				.append("OR pf.type like '%" + paysheetFrequencyDTO.getSearchValue() + "%' )");
						
			}
			}
		}
			
			Query queryObj = session.createQuery(qry + query1.toString());
			/*if (invoiceDTO != null && invoiceDTO.getLength() > 0) {
				queryObj.setFirstResult(invoiceDTO.getStart());
				queryObj.setMaxResults(invoiceDTO.getLength());
//				queryObj.setLong("orgLocationId", orgLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
			}*/
			
			freqList = queryObj.setLong("orgLocationId", orgLocation.getId()).list();
			/*int count = 0;
			int filteredCount = 0;

			List<Long> countlist = session.createQuery("select count(*) " + qry).setLong("orgLocationId", orgLocation.getId()).list();

			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (paysheetFrequencyDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + qry + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} */
		response.setData(freqList);
		return response;
	}

	@Override
	@Transactional
	public PaysheetFrequency getPayFrequencyByUUID(String paySheetFrequencyUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from PaysheetFrequency pf where pf.paySheetFrequencyUUID =:paySheetFrequencyUUID";
		List<PaysheetFrequency> list = session.createQuery(qry).setParameter("paySheetFrequencyUUID", paySheetFrequencyUUID).list();
		if(list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}
}
