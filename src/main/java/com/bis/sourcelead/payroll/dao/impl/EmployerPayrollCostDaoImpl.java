package com.bis.sourcelead.payroll.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.EmployerPayrollCostDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.EmployerPayrollCostDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.util.ConversionUtil;
import com.ibm.icu.text.DecimalFormat;

@Repository
public class EmployerPayrollCostDaoImpl implements EmployerPayrollCostDao{

	private static final Logger logger = LoggerFactory.getLogger(EmployerPayrollCostDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmployerPayrollCost saveEmployerPayrollCost(EmployerPayrollCost employerPayrollCost) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(employerPayrollCost);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return employerPayrollCost;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<EmployerPayrollCost> loadEmployerPayrollCostByOrgLocationId(OrgLocations orgLocation,EmployerPayrollCostDTO employerPayrollCostDTO) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<EmployerPayrollCost> employerPayrollCostList = null;
		ResponseList<EmployerPayrollCost> response = new ResponseList<>();
		
		String qry = "";
		if(StringUtils.isNotBlank(employerPayrollCostDTO.getSortColumn())) {
			qry = " order by epc."+ employerPayrollCostDTO.getSortColumn() +" "+employerPayrollCostDTO.getSortOrder();
		}
		
		String query = "from EmployerPayrollCost epc  left join fetch epc.orgLocationId where epc.orgLocationId.id =:orgLocationId and (epc.status IS NULL or epc.status <> '0')";
		String countQuery = "from EmployerPayrollCost epc  where epc.orgLocationId.id =:orgLocationId and (epc.status IS NULL or epc.status <> '0')";
		
		if(employerPayrollCostDTO.getSearchValue().contains("/") || employerPayrollCostDTO.getSearchValue().contains("-")){
			String convertedDate=employerPayrollCostDTO.getSearchValue();
			if(employerPayrollCostDTO.getSearchValue().contains("/")){
				convertedDate = employerPayrollCostDTO.getSearchValue().replaceAll("/", "-");
			}
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newDate = parsedDate.format(f2);
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
            String queryDate = "from EmployerPayrollCost epc where epc.orgLocationId.id =:orgLocationId and and (epc.status IS NULL or epc.status <> '0') and ( epc.fromDate =:sqlDate or epc.toDate =:sqlDate )";
            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
            response.setData(queryObjDate.list());

		}else{
		StringBuilder query1 = new StringBuilder("");
			if(employerPayrollCostDTO.getSearchValue() !=null && !"".equalsIgnoreCase(employerPayrollCostDTO.getSearchValue())) {
			query1.append(" and epc.fromDate like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.toDate like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.employeeId like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.employeeName like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.ssn like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.totalHrs like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.grossWages like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.fica like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.futa like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.sui like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.erLocals like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.wcCost like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.adminFee like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.totalErCost like '%" + employerPayrollCostDTO.getSearchValue() + "%' ");
			}
		Query queryObj = session.createQuery(query + query1.toString()+qry);
		if (employerPayrollCostDTO != null && employerPayrollCostDTO.getLength() > 0) {
			queryObj.setFirstResult(employerPayrollCostDTO.getStart());
			queryObj.setMaxResults(employerPayrollCostDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}
		employerPayrollCostList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (employerPayrollCostDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

			response.setData(employerPayrollCostList);
		}
			return response;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmployerPayrollCost getEmployeePayrollCostById(String EmpId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<EmployerPayrollCost> empCostList = null;
		String query = "from EmployerPayrollCost empPayroll  left join fetch empPayroll.orgLocationId where empPayroll.employeeId =:empId";
	    empCostList = session.createQuery(query).setString("empId", EmpId).list();
		if(empCostList != null && empCostList.size() >0)
		{
			return empCostList.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getEmployerPayrollCostVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(ep.version)  FROM EmployerPayrollCost ep where ep.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0);
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<EmployerPayrollCost> getEmployerPayrollCostByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmployerPayrollCost ep where ep.orgLocationId.id =:orgLocationId";
		List<EmployerPayrollCost> list = session.createQuery(query).setLong("orgLocationId", locationId).list();
		return list;
	}

}
