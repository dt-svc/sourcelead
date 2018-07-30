package com.bis.sourcelead.payroll.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.USPayPeriodDao;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;

@Repository
public class USPayPeriodDaoImpl implements USPayPeriodDao{

	
	private static final Logger logger = LoggerFactory.getLogger(USPayPeriodDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public USPayPeriod getPayPeriodNumber(Long payFrequencyId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		USPayPeriod uSPayPeriod = null;
		try {
			String query = "from USPayPeriod ppn where ppn.payPeriodNum = (select MAX(paypn.payPeriodNum) from USPayPeriod paypn)";
			uSPayPeriod = (USPayPeriod) session.createQuery(query).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uSPayPeriod;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public USPayPeriod savePayPeriodNumber(USPayPeriod uSPayPeriod)
	{
		Session session = this.sessionFactory.getCurrentSession();
		if (uSPayPeriod != null) {
			session.saveOrUpdate(uSPayPeriod);
		}
		return uSPayPeriod;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<USPayPeriod> getPayPeriodListByOrgLocationId(OrgLocations orgLocation,
			PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from USPayPeriod up where up.orgLocation.id =:orgLocationId and up.status = '1'";
		
		
		List<USPayPeriod> periodList = null;
		ResponseList<USPayPeriod> response = new ResponseList<>();
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
            String queryDate = "from USPayPeriod up where up.orgLocation.id =:orgLocationId and ( up.paysheetFrequency.frequencyStartDate =:sqlDate or up.paysheetFrequency.frequencyEndDate =:sqlDate or up.paysheetFrequency.payCheckDate =:sqlDate)";
            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
            response.setData(queryObjDate.list());
            return response;
			}else{
				if(paysheetFrequencyDTO.getSearchValue() !=null && !"".equalsIgnoreCase(paysheetFrequencyDTO.getSearchValue())) {
				query1.append(" and (up.paysheetFrequency.userGroup.payFrequency like '%" + paysheetFrequencyDTO.getSearchValue() + "%' ")
				.append("OR up.paysheetFrequency.userGroup.groupName like '%" + paysheetFrequencyDTO.getSearchValue() + "%' ")
				.append("OR up.approverStatus like '%" + paysheetFrequencyDTO.getSearchValue() + "%' ")
				.append("OR up.paysheetFrequency.type like '%" + paysheetFrequencyDTO.getSearchValue() + "%' )");
						
			}
			}
		}
		
		Query queryObj = session.createQuery(qry + query1.toString());
		/*if (invoiceDTO != null && invoiceDTO.getLength() > 0) {
			queryObj.setFirstResult(invoiceDTO.getStart());
			queryObj.setMaxResults(invoiceDTO.getLength());
//			queryObj.setLong("orgLocationId", orgLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		}*/
		
		periodList = queryObj.setLong("orgLocationId", orgLocation.getId()).list();
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
	response.setData(periodList);
	return response;
			
		
		
//		List<USPayPeriod> payPeriodList = session.createQuery(qry).setLong("orgLocationId", orgLocation.getId()).list();
//		paySheet.setData(payPeriodList);
//		return paySheet;
	}
}
