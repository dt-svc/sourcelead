package com.bis.sourcelead.payroll.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

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
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;

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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<USPayPeriod> getPayPeriodListByOrgLocationId(OrgLocations orgLocation, PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from USPayPeriod up where up.orgLocation.id =:orgLocationId and up.status = '1' ";
		String SubString = "order by up.id desc";
		List<USPayPeriod> periodList = null;
		ResponseList<USPayPeriod> response = new ResponseList<>();
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotBlank(paysheetFrequencyDTO.getSearchValue())) {
			if (paysheetFrequencyDTO.getSearchValue().contains("/")
					|| paysheetFrequencyDTO.getSearchValue().contains("-")) {
				String convertedDate = paysheetFrequencyDTO.getSearchValue();
				if (paysheetFrequencyDTO.getSearchValue().contains("/")) {
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
			}
			else {
				if (paysheetFrequencyDTO.getSearchValue() != null && !"".equalsIgnoreCase(paysheetFrequencyDTO.getSearchValue())) {
					query1.append(" and (up.paysheetFrequency.userGroup.payFrequency like '%"
							+ paysheetFrequencyDTO.getSearchValue()
							+ "%' ").append("OR up.paysheetFrequency.userGroup.groupName like '%"
							+ paysheetFrequencyDTO.getSearchValue()
							+ "%' ").append("OR up.approverStatus like '%"
							+ paysheetFrequencyDTO.getSearchValue()
							+ "%' ").append("OR up.payPeriodNum like '%"
							+ paysheetFrequencyDTO.getSearchValue()
							+ "%' ").append("OR up.paysheetFrequency.type like '%"
							+ paysheetFrequencyDTO.getSearchValue()
							+ "%' )");
				}
			}
		}
		Query queryObj = session.createQuery(qry + query1 + SubString);
		periodList = queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		response.setData(periodList);
		return response;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int updatedPayperiod(String payPeriodNum)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("UPDATE USPayPeriod pp set pp.approverStatus = :approveStatus where pp.payPeriodNum = :payPeriodNum");
		query.setString("approveStatus", PayrollConstant.APPROVE);
		query.setInteger("payPeriodNum", Integer.parseInt(payPeriodNum));
		int result = query.executeUpdate();
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public USPayPeriod getPayPeriodByOrglocationId(OrgLocations orgLocation,Long payfrequencyId) {
		
		USPayPeriod payPeriod = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from USPayPeriod usPay where usPay.orgLocation.id =:orgLocationId and usPay.paysheetFrequency.id =:paysheetFrequencyId";
			List<USPayPeriod> payPeriodList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setLong("paysheetFrequencyId", payfrequencyId).list();	
			if(payPeriodList != null && payPeriodList.size() > 0) {
				payPeriod = payPeriodList.get(0);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return payPeriod;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<EmployeePayroll> getEmployeePayrollListByPayperiodNum(Integer payPeriodNum) {
		List<EmployeePayroll> empList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmployeePayroll emp where emp.payPeriodNum =:payPeriodNum";
		empList = session.createQuery(query).setInteger("payPeriodNum", payPeriodNum).list();
		return empList;
	}

}
