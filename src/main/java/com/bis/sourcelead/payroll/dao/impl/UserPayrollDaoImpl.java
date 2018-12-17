package com.bis.sourcelead.payroll.dao.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.bis.sourcelead.dto.ChecksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.UserPayrollDao;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

@Repository
public class UserPayrollDaoImpl implements UserPayrollDao{
	private static final Logger logger = LoggerFactory.getLogger(UserPayrollDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserPayroll saveUserPayroll(UserPayroll userPayroll) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(userPayroll);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return userPayroll;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserPayroll> getUserPayrollsBystartDateEndDte(Long orgLocationID, Date startDate, Date endDate)throws Exception {
		String subQuery = "";
		Map<String, Object> params = new HashMap<String,Object>();
		if(null != orgLocationID){
			params.put("orgLocationId", orgLocationID);
		}
		
		if(null != startDate && null != endDate){
			java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
			params.put("startDate", sqlStartDate);
			params.put("endDate", sqlEndDate);
			subQuery = "and (userPayroll.payrollStartDate = :startDate OR userPayroll.payrollEndDate = :endDate)";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserPayroll userPayroll left join fetch userPayroll.userId left join fetch userPayroll.orgLocationId  where userPayroll.orgLocationId.id = :orgLocationId "+subQuery;
		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") ){
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if(str.equalsIgnoreCase("startDate") ){
				qry.setDate(str, (Date) params.get("startDate"));
			}
			if(str.equalsIgnoreCase("endDate") ){
				qry.setDate(str, (Date) params.get("endDate"));
			}
        }
		return qry.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserPayroll> getPayByUserId(Long userId, Long orgLocationId,Date startDay, Date endDay) throws Exception {
		String subQuery = "";
		Session session = this.sessionFactory.getCurrentSession();
		List<UserPayroll> userPayrollList = null;
		if(startDay != null && endDay != null){
			subQuery = "and userPayroll.payrollStartDate >=  :startDay and userPayroll.payrollEndDate <=  :endDay";
		}
		String query = "from UserPayroll userPayroll  left join fetch userPayroll.userId where userPayroll.orgLocationId.id =:orgLocationId and userPayroll.userId.id = :userId "+subQuery+"";
		if(startDay != null && endDay != null){
			userPayrollList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setParameter("startDay", startDay).setParameter("endDay", endDay).list();
		}else{
			userPayrollList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
		}
		if(userPayrollList != null && userPayrollList.size() >0)
		{
			return userPayrollList;
		}
		return userPayrollList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<Checks> getChecksList(ChecksDTO checksDTO, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Checks> checksList = null;
		ResponseList<Checks> response = new ResponseList<>();
		
		String qry = "";
		if(StringUtils.isNotBlank(checksDTO.getSortColumn())) {
			qry = " order by checks."+ checksDTO.getSortColumn() +" "+checksDTO.getSortOrder();
		}
		
		String query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId and (checks.status IS NULL or checks.status <> '0')";
		String countQuery = "from Checks checks  where checks.orgLocationId.id =:orgLocationId and (checks.status IS NULL or checks.status <> '0')";
		
		StringBuilder query1 = new StringBuilder("");
			if(checksDTO.getSearchValue() !=null && !"".equalsIgnoreCase(checksDTO.getSearchValue())) {
			query1.append(" and checks.empId like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.empName like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.date like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.typeOfExpense like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.bankName like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.month like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.legalFees like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.advance like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.generalExpenses like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.vendorPayments like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.electronicWithdrawals like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.payrollChecks like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.amount like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.checkNo like '%" + checksDTO.getSearchValue() + "%' ");
						
			}
		Query queryObj = session.createQuery(query + query1.toString()+qry);
		if (checksDTO != null && checksDTO.getLength() > 0) {
			queryObj.setFirstResult(checksDTO.getStart());
			queryObj.setMaxResults(checksDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocationId).list();
		}
		checksList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocationId).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (checksDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocationId).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

			response.setData(checksList);
			return response;
		
		// TODO do not delete @Rajashekar
		/*Session session = this.sessionFactory.getCurrentSession();
		List<Checks> checkslList = null;
		String query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId";
		checkslList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if (checkslList != null && checkslList.size() > 0) {
			return checkslList;
		}
		return checkslList;*/
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Checks> getChecksListByEmployeeId(Long empId, OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Checks> checkslList = null;
		String query = "";
		if(empId != null){
			query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId and checks.empId = :empId";
			checkslList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).list();
			if (checkslList != null && checkslList.size() > 0) {
				return checkslList;
			}
		}else{
			query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId";
			checkslList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
			if (checkslList != null && checkslList.size() > 0) {
				return checkslList;
			}
		}
		
		return checkslList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Checks getUniqueChecksListByEmployeeId(String empId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Checks checks = null;
		String query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId and checks.empId = :empId";
		List<Checks> checkslList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setParameter("empId", empId).list();
		if (checkslList != null && checkslList.size() > 0) {
			checks =  checkslList.get(0);
		}
		return checks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Object deletePayrollEntitiesByUUIDAndEntityType(Object obj)
	{
		Session session = this.sessionFactory.getCurrentSession();
		if(obj instanceof EmployerPayrollCost){
			EmployerPayrollCost empCost = (EmployerPayrollCost) obj;
		}else if(obj instanceof InvoiceData){
			InvoiceData invData = (InvoiceData) obj;
		}else if(obj instanceof WageTaxSummary){
			WageTaxSummary wts = (WageTaxSummary) obj;
		}else if(obj instanceof Checks){
			Checks cks = (Checks) obj;
		}else if(obj instanceof HrDeductions){
			HrDeductions hrd = (HrDeductions) obj;
		}else if(obj instanceof AccountDeductions){
			AccountDeductions acd = (AccountDeductions) obj;
		}
		try {
			session.saveOrUpdate(obj);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return obj;
	}

	@Override
	@Transactional
	public Object getPayrollEntitiesByUUIDAndEntityType(String uuid, String entityType)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Object obj = null;
		String subQuery = "";
		if("EmployeePayrollCost".equalsIgnoreCase(entityType)){
			subQuery = "from EmployerPayrollCost ";
		}else if("InvoiceData".equalsIgnoreCase(entityType)){
			subQuery = "from InvoiceData ";
		}else if("WageTaxSummary".equalsIgnoreCase(entityType)){
			subQuery = "from WageTaxSummary ";
		}else if("Checks".equalsIgnoreCase(entityType)){
			subQuery = "from Checks checks";
		}else if("HrDeductions".equalsIgnoreCase(entityType)){
			subQuery = "from HrDeductions ";
		}else if("AccountDeductions".equalsIgnoreCase(entityType)){
			subQuery = "from AccountDeductions ";
		}
		String query = subQuery + "ent where ent.uuid =:uuid";
		List<Object> objList = session.createQuery(query).setString("uuid", uuid).list();
		if (objList != null && objList.size() > 0) {
			obj =  objList.get(0);
		}
		return obj;
	}

	@Override
	@Transactional
	public ResponseList<Checks> getChecksListByEmployeeId(OrgLocations orgLocations, ChecksDTO checksDTO) {
		ResponseList<Checks> response = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<Checks> checkslList = null;
			String qry = "";
			String query = "";
			if(StringUtils.isNotBlank(checksDTO.getSortColumn())) {
				qry = " order by checks."+ checksDTO.getSortColumn() +" "+checksDTO.getSortOrder();
			}
			if(StringUtils.isNoneBlank(checksDTO.getEmpId())){
				query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId and checks.empId = :empId";
				checkslList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameter("empId", checksDTO.getEmpId()).list();
			}else{
				query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId";
				checkslList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
			}
//			String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0')";
			String countQuery = "from Checks checks  where checks.orgLocationId.id =:orgLocationId and checks.empId = :empId  and (checks.status IS NULL or checks.status <> '0')";
			
			if(StringUtils.isNotBlank(checksDTO.getSearchValue()) && (checksDTO.getSearchValue().contains("/") || checksDTO.getSearchValue().contains("-"))){
				String convertedDate=checksDTO.getSearchValue();
				if(checksDTO.getSearchValue().contains("/")){
					convertedDate = checksDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat()).toFormatter();
	            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
	            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            String newDate = parsedDate.format(f2);
	            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
	            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
	            String queryDate = "from Checks checks where checks.orgLocationId.id =:orgLocationId and (checks.status IS NULL or checks.status <> '0') and ( checks.date =:sqlDate or checks.lastmodified =:sqlDate or checks.dueDate =:sqlDate)";
	            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocations.getId());
	            response.setData(queryObjDate.list());

			}else{
			StringBuilder query1 = new StringBuilder("");
				if(checksDTO.getSearchValue() !=null && !"".equalsIgnoreCase(checksDTO.getSearchValue())) {
//					query1 = " and wts.employeeId like %%";
				query1.append(" and (checks.empId like '%" + checksDTO.getSearchValue() + "%' ")
						.append("OR checks.empName like '%" + checksDTO.getSearchValue() + "%' ")
						.append("OR checks.typeOfExpense like '%" + checksDTO.getSearchValue() + "%' ")
						.append("OR checks.bankName like '%" + checksDTO.getSearchValue() + "%' ")
						.append("OR checks.month like '%" + checksDTO.getSearchValue() + "%' ")
						.append("OR checks.legalFees like '%" + checksDTO.getSearchValue() + "%' ")
//						.append("OR checks.advanceStr like '%" + checksDTO.getSearchValue() + "%' ")
//						.append("OR checks.generalExpensesStr like '%" + checksDTO.getSearchValue() + "%' ")
//						.append("OR checks.vendorPaymentsStr like '%" + checksDTO.getSearchValue() + "%' ")
						.append("OR checks.electronicWithdrawals like '%" + checksDTO.getSearchValue() + "%' ")
//						.append("OR checks.payrollChecksStr like '%" + checksDTO.getSearchValue() + "%' ")
//						.append("OR checks.amountStr like '%" + checksDTO.getSearchValue() + "%' ")
				        .append("OR checks.checkNo like '%" + checksDTO.getSearchValue() + "%') ");
							/*+ "OR wts.checkDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%'";*/
				}
			Query queryObj = session.createQuery(query + query1.toString()+qry);
			if (checksDTO != null && checksDTO.getLength() > 0) {
				queryObj.setFirstResult(checksDTO.getStart());
				queryObj.setMaxResults(checksDTO.getLength());
			}
			queryObj.setLong("orgLocationId", orgLocations.getId())
			.setLong("empId", Long.parseLong(checksDTO.getEmpId()));
			checkslList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setParameter("empId", checksDTO.getEmpId()).setLong("orgLocationId", orgLocations.getId()).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (checksDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocations.getId()).setParameter("empId", checksDTO.getEmpId()).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(checkslList);
			}
				
		}catch (Exception e) {

		e.printStackTrace();
		}
		return response;
		
	}

}
