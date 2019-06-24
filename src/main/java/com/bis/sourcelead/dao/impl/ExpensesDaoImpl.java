
package com.bis.sourcelead.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.nio.reactor.ExceptionEvent;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ExpensePendingApproveReportView;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimesheetSubmissions;

@SuppressWarnings("rawtypes")
@Repository
public class ExpensesDaoImpl implements ExpensesDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ProjectMembersDao projectMembersDao;

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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<Expenses> getAllExpenses(Long masterCandidateId, String expenseStatus, Date startDateUTC,
			Date endDateUTC, String searchValue, int start, int length, OrgLocations orgloc) throws ParseException {
		ResponseList<Expenses> response = new ResponseList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		String[] expStatus = expenseStatus.split(",");
		String subQuery2 = "";
		if (Arrays.asList(expStatus).contains("ALL") || StringUtils.isEmpty(expenseStatus)) {
			subQuery2 = subQuery2
					+ " expenses.expenseStatus = 'APPROVED' or expenses.expenseStatus = 'SUBMITTED' or expenses.expenseStatus = 'DRAFT' or expenses.expenseStatus = 'REJECTED' or expenses.expenseStatus = 'REOPENED' ";
		} else {
			if (Arrays.asList(expStatus).contains("APPROVED")) {
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'APPROVED' ";
			}
			if (Arrays.asList(expStatus).contains("SUBMITTED")) {
				if (StringUtils.isNotEmpty(subQuery2)) {
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'SUBMITTED' ";
			}
			if (Arrays.asList(expStatus).contains("DRAFT")) {
				if (StringUtils.isNotEmpty(subQuery2)) {
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'DRAFT' ";
			}

			if (Arrays.asList(expStatus).contains("REOPENED")) {
				if (StringUtils.isNotEmpty(subQuery2)) {
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'REOPENED' ";
			}

			if (Arrays.asList(expStatus).contains("REJECTED")) {
				if (StringUtils.isNotEmpty(subQuery2)) {
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'REJECTED' ";
			}
		}

		String startDateSubQuery = "";
		if (null != startDateUTC) {
			startDateSubQuery = "and (expenses.startDate >= :startDateUTC)";
			params.put("startDateUTC", startDateUTC);
		}

		String endDateSubQuery = "";
		if (null != endDateUTC) {
			endDateSubQuery = "and (expenses.endDate <= :endDateUTC)";
			params.put("endDateUTC", endDateUTC);
		}

		/*
		 * if (expStatus[0] != null && expStatus[0].length() > 0) { subQuery2 = "and ("
		 * + subQuery2.substring(0, subQuery2.length() - 4) + ")"; } else if (subQuery2
		 * != null && !"".equals(subQuery2)) { subQuery2 = " (" + subQuery2.substring(0,
		 * subQuery2.length() - 4) + ")"; }
		 */

		StringBuilder query1 = new StringBuilder();
		if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
			Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
			Matcher matcher = pattern.matcher(searchValue);
			query1.append(" and (expenses.expenseId like '%" + searchValue + "%' ")
					.append("OR expenses.purpose like '" + searchValue + "%' ")
					.append("OR expenses.expenseStatus like '" + searchValue + "%' ");
			if (matcher.matches() == false) {
				query1.append("OR expenses.projects.projectName like '%" + searchValue + "%')");
			}
			if (matcher.matches() == true) {
				java.sql.Date sqlDate = null;
				if (searchValue.contains("/") || (searchValue.contains("-"))) {
					String replacedValue = searchValue.replaceAll("/", "-");
					SimpleDateFormat format = new SimpleDateFormat(orgloc.getDateFormat());
					java.util.Date utilDate = format.parse(replacedValue);
					sqlDate = new java.sql.Date(utilDate.getTime());
				}
				query1.append("OR expenses.projects.projectName like '%" + searchValue + "%'")
						.append("OR expenses.startDate like '" + sqlDate + "' ")
						.append("OR expenses.endDate like '" + sqlDate + "' )");

			}

		}

		Session session = this.sessionFactory.getCurrentSession();
		String from = "from Expenses expenses left join fetch expenses.orgLocationId left join fetch expenses.projectMembers pm left join fetch pm.projects projects left join fetch pm.masterCandidateId mc left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc "
				+ " where projects.id IN( Select pm.projects.id from ProjectMembers pm where pm.masterCandidateId.id = :masterCandidateId and (pm.projectClientsId.orgLocationsId.id = :locationId OR ( :locationId NOT IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = pm.projects.id and subpm.masterCandidateId.id = pm.masterCandidateId.id) and pm.projectMemberCandidteId.orgLocationId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = pm.projects.id and subpm.masterCandidateId.id = pm.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"
				+ Constants.OFFSHORE_C2C
				+ "') and pm.projectMemberCandidteId.orgLocationId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId)) )) and mc.id = :masterCandidateId and ( "
				+ subQuery2 + " ) " + startDateSubQuery + " " + endDateSubQuery + " " + query1
				+ " and expenses.status = '1' order by expenses.id desc ";
		params.put("masterCandidateId", masterCandidateId);
		params.put("locationId", orgloc.getId());

		String countFrom = "";
		if (length > 0) {
			countFrom = "Select count(*) from Expenses expenses where expenses.projectMembers.projects.id IN( Select pm.projects.id from ProjectMembers pm where pm.masterCandidateId.id = :masterCandidateId and (pm.projectClientsId.orgLocationsId.id = :locationId OR ( :locationId NOT IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = pm.projects.id and subpm.masterCandidateId.id = pm.masterCandidateId.id) and pm.projectMemberCandidteId.orgLocationId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = pm.projects.id and subpm.masterCandidateId.id = pm.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"
					+ Constants.OFFSHORE_C2C
					+ "') and pm.projectMemberCandidteId.orgLocationId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId)) )) and expenses.projectMembers.masterCandidateId.id = :masterCandidateId and ( "
					+ subQuery2 + " ) " + startDateSubQuery + " " + endDateSubQuery + " " + query1
					+ " and expenses.status = '1' order by expenses.id desc";
			params.put("masterCandidateId", masterCandidateId);
			params.put("locationId", orgloc.getId());
		}

		Query query = session.createQuery(from);

		Query countQuery = null;
		if (length > 0) {
			countQuery = session.createQuery(countFrom);
		}

		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("masterCandidateId")) {
				query.setParameter(str, (long) params.get(str));
				if (countQuery != null)
					countQuery.setParameter(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("startDateUTC")) {
				query.setParameter(str, (Date) params.get(str));
				if (countQuery != null)
					countQuery.setParameter(str, (Date) params.get(str));
			}
			if (str.equalsIgnoreCase("locationId")) {
				query.setLong(str, (Long) params.get(str));
				if (countQuery != null)
					countQuery.setLong(str, (Long) params.get(str));
			}
			if (str.equalsIgnoreCase("endDateUTC")) {
				query.setParameter(str, (Date) params.get(str));
				if (countQuery != null)
					countQuery.setParameter(str, (Date) params.get(str));
			}
			if (length > 0) {
				query.setFirstResult(start).setMaxResults(length);
			}
		}

		List<Expenses> tslist = query.list();
		response.setData(tslist);

		List<Long> countList = null;
		if (countQuery != null) {
			countList = countQuery.list();
		}
		int recordsTotal = 0;
		if (countList != null && !countList.isEmpty()) {
			recordsTotal = countList.iterator().next().intValue();
		}
		response.setRecordsTotal(recordsTotal);

		int recordsFiltered = 0;
		if (tslist != null && !tslist.isEmpty()) {
			recordsFiltered = tslist.size();
		}
		if (query1.toString() != null && !query1.toString().isEmpty()) {
			response.setRecordsFiltered(recordsFiltered);
		} else {
			response.setRecordsFiltered(recordsTotal);
		}

		return response;
		/*
		 * List<Expenses> expensesList =session.createQuery(query).setLong("orgLocId",
		 * orgLocId).setLong("loginUserId", loginUserId).list(); return expensesList;
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expenses> getIndividualExpense(Long orgLocId, Long loginUserId, String expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		Long expenseIdLong = Long.parseLong(expenseId);
		String query = "from Expenses expenses left join fetch expenses.orgLocationId left join fetch expenses.projectMembers pm left join fetch pm.employee emp left join fetch emp.user user where expenses.orgLocationId.id =:orgLocId and expenses.id =:expenseIdLong";
		List<Expenses> expensesList = session.createQuery(query).setLong("orgLocId", orgLocId)
				.setLong("expenseIdLong", expenseIdLong).list();
		return expensesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesEntries> getExpensesEntries(String expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		Long expenseIdLong = Long.parseLong(expenseId);
		String query = "from ExpensesEntries expensesEntries left join fetch expensesEntries.expensesCategoryId left join fetch expensesEntries.expensesId ex where ex.id =:expenseIdLong";
		List<ExpensesEntries> expensesEntriesList = session.createQuery(query).setLong("expenseIdLong", expenseIdLong)
				.list();
		return expensesEntriesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Expenses getExpenseById(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Expenses expenses where expenses.id =:id";
		List<Expenses> expenses = session.createQuery(query).setLong("id", id).list();
		Expenses expensesList = null;
		if (expenses != null && expenses.size() > 0) {
			expensesList = expenses.get(0);
		}
		return expensesList;
	}

	@Override
	@Transactional
	public Expenses getExpenseByExpenseUniqueId(String expenseId, Long OrgLocationID, String isGlobalUser)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Expenses expense = null;

		String globalUserQuery = "";
		if (StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
			globalUserQuery = " expenses.orgLocationId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)";
		} else {
			globalUserQuery = " expenses.orgLocationId.id = :orgLocationId";
		}

		List<Expenses> expensesList = session.createQuery(
				"from Expenses expenses left join fetch expenses.orgLocationId orglocId left join fetch expenses.projectMembers pm "
						+ " left join fetch pm.projectMemberCandidteId can left join fetch pm.employee emp left join fetch emp.user user "
						+ " where expenses.expenseId = :expenseId and " + globalUserQuery + " ")
				.setString("expenseId", expenseId).setLong("orgLocationId", OrgLocationID).list();
		if (null != expensesList && expensesList.size() > 0) {
			expense = expensesList.get(0);
		}
		return expense;
	}

	@Override
	@Transactional
	public List<Expenses> getExpensesByProjectIdAndMasterCandidateId(Long projectId, Long masterCandidateId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Expenses expenses left join fetch expenses.orgLocationId left join fetch expenses.projectMembers pm left join fetch pm.projects projects left join fetch pm.masterCandidateId mc left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc where projects.id = :projectId and mc.id = :masterCandidateId ";
		List<Expenses> expenses = session.createQuery(query).setLong("masterCandidateId", masterCandidateId)
				.setLong("projectId", projectId).list();
		return expenses;
	}

	@Override
	@Transactional
	public List getExpensesByProjectMemberId(Long projectMemberId, String invoiceType, Long expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		Long masterCandidateId = 0l;
		Long projectId = 0l;
		if (projectMemberId != null) {
			try {
				ProjectMembers pm = projectMembersDao.getProjectMemberById(projectMemberId);
				masterCandidateId = pm.getMasterCandidateId().getId();
				projectId = pm.getProjects().getId();
			} catch (Exception e) {
			}

		}
		String subQuery = "";
		if (expenseId > 0) {
			if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
				subQuery = "expenses.billingRecievableStatus IS NULL OR expenses.billingRecievableStatus = 'SENT'";
			} else {
				subQuery = "expenses.billingPayableStatus IS NULL OR expenses.billingPayableStatus = 'SENT'";
			}
		} else {
			if (Constants.RECEIVABLE.equalsIgnoreCase(invoiceType)) {
				subQuery = "expenses.billingRecievableStatus IS NULL";
			} else {
				subQuery = "expenses.billingPayableStatus IS NULL";
			}
		}
		List<Expenses> expensesList = session.createQuery(
				"from Expenses expenses left join fetch expenses.orgLocationId orglocId left join fetch expenses.projects project left join fetch expenses.masterCandidateId mc left join fetch expenses.projectMembers pm left join fetch pm.employee emp left join fetch emp.user user where project.id =:projectId and mc.id = :masterCandidateId and "
						+ subQuery)
				.setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).list();
		return expensesList;
	}

	@Override
	@Transactional
	public List<Expenses> getAllExpensesByOrgLocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Expenses expenses left join fetch expenses.orgLocationId orgLocation where orgLocation = :orgLocationId";
		List<Expenses> expensesList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return expensesList;
	}

	@Override
	@Transactional
	public List<Expenses> getAllExpensesByProjectIdOrgLocationIdStartDateAndEndDate(Long projectId, Long orglocationId,
			Date startDate, Date endDate) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}

		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		String query = "from Expenses expenses where expenses.projects.id =:projectId and expenses.orgLocationId.id =:orglocationId and"
				+ "(expenses.startDate = :startDate OR expenses.endDate = :endDate OR expenses.endDate BETWEEN :startDate and :endDate) ";
		List<Expenses> expensesList = session.createQuery(query).setLong("orglocationId", orglocationId)
				.setLong("projectId", projectId).setParameter("startDate", sqlStartDate)
				.setParameter("endDate", sqlEndDate).list();
		return expensesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expenses> getAllExpenses() {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Expenses expenses";
		List<Expenses> expenses = session.createQuery(query).list();
		return expenses;
	}

	@Transactional(rollbackFor = { Exception.class })
	public BigDecimal getExpenseAmountByCandidateIdAndExpenseStartAndEndDateForClaimedExpenses(Long candidateId,
			Date startDate, Date endDate) {
		Session session = this.sessionFactory.getCurrentSession();
		BigDecimal expenseAmount = BigDecimal.ZERO;
		try {
			java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
			String query = "from ExpensesEntries entry left join fetch entry.expensesId expenses left join fetch expenses.projectMembers proMembers left join fetch expenses.projects project left join fetch proMembers.projectMemberCandidteId candidate where expenses.expenseStatus = 'APPROVED' and expenses.status = '1' and entry.paidBy = 'Self' and entry.billable = 1 and candidate.id = :candidateId and (:startDate<= date(expenses.updatedDate) AND date(expenses.updatedDate)<= :endDate)";
			List<ExpensesEntries> expenseCategories = session.createQuery(query).setLong("candidateId", candidateId)
					.setDate("startDate", sqlStartDate).setDate("endDate", sqlEndDate).list();
			if (expenseCategories != null && expenseCategories.size() > 0) {
				for (ExpensesEntries entry : expenseCategories) {
					expenseAmount = expenseAmount.add(entry.getAmount());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expenseAmount;
	}

	@Transactional
	public boolean changeExpenseBillingStatus(long expenseId, String billingStatus) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Expenses expenseBillingStatus = session.get(Expenses.class, expenseId);
			if (expenseBillingStatus != null) {
				expenseBillingStatus.setBillingStatus(billingStatus);
				
				session.saveOrUpdate(expenseBillingStatus);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	@Transactional
	@Override
	public List<ExpensePendingApproveReportView> getPendingForApprovalExpenseReportByProjectIds(String projectIds, Date frequencyStartDate, Date frequencyEndDate) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from ExpensePendingApproveReportView e where e.projectId in (" + projectIds + ") and DATE_FORMAT(e.submittedDate,'%Y-%m-%d') between :startDate and :endDate";
			List<ExpensePendingApproveReportView> expensesList = session.createQuery(query).setDate("startDate", frequencyStartDate).setDate("endDate", frequencyEndDate).list();
			return expensesList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
