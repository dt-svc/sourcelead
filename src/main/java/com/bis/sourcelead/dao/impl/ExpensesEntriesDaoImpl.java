package com.bis.sourcelead.dao.impl;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ExpensesEntriesDao;
import com.bis.sourcelead.dao.ExpensesEntryDocumentsDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ExpensesEntriesDaoImpl implements ExpensesEntriesDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ExpensesEntryDocumentsDao expensesEntryDocumentsDao;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectTasksDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesEntries addExpensesEntries(ExpensesEntries entries) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(entries);
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"EXPENSES_ENTRIES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return entries;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesEntries> getExpensesEntriesByExpenseId(long expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ExpensesEntries expensesEntries left join fetch expensesEntries.expensesCategoryId ec left join fetch expensesEntries.expensesId expenses where expenses.id =:expenseId";
		List<ExpensesEntries> expensesEntryList = session.createQuery(query).setLong("expenseId", expenseId).list();
		return expensesEntryList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getExpensesEntryIdsByExpenseId(long expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "Select expensesEntries.id from ExpensesEntries expensesEntries  where expensesEntries.expensesId.id =:expenseId";
		List<Long> expensesEntryList = session.createQuery(query).setLong("expenseId", expenseId).list();
		return expensesEntryList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesEntries getExpensesEntriesById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ExpensesEntries expensesEntries left join fetch expensesEntries.expensesId left join fetch expensesEntries.expensesCategoryId  where expensesEntries.id =:id";
		List<ExpensesEntries> expensesEntryList = session.createQuery(query).setLong("id", id).list();
		ExpensesEntries expensesEntries = null;
		if(expensesEntryList != null && !expensesEntryList.isEmpty()){
			expensesEntries = expensesEntryList.iterator().next();
		}
		return expensesEntries;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void removeAllExpenseEntries(List<Long> entryIds) throws Exception {
		logger.debug("Deleting ExpenseEntries");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(entryIds != null && !entryIds.isEmpty()){
				for(Long entryId : entryIds){
					expensesEntryDocumentsDao.removeAllExpensesEntryDocoumentsByEntryId(entryId);
					ExpensesEntries expenseEntries = getExpensesEntriesById(entryId);
					session.delete(expenseEntries);
				}
			}
			logger.debug("Successfully deleted..!");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ExpenseEntryDocuments"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public int dumpExpenceCategoryAmountToOldRecords() {
		int count = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Expenses";
			List<Expenses> expensesList = session.createQuery(query).list();
			if(expensesList != null && !expensesList.isEmpty()) {
				for(Expenses expenses: expensesList) {
					double totalSubmittedAmount = 0;
					Set<ExpensesEntries> expensesEntriesSet = expenses.getExpensesEntries();
					if(expensesEntriesSet != null && !expensesEntriesSet.isEmpty()) {
						for(ExpensesEntries expensesEntries : expensesEntriesSet) {
							totalSubmittedAmount += expensesEntries.getSubmittedamount().doubleValue();
						}
					}
					expenses.setTotalsubmittedAmount(new BigDecimal(totalSubmittedAmount));
					session.saveOrUpdate(expenses);
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	@Transactional
	public int changeDocumentNameWithWhiteSpacesToUnderScore(String staticPath) {
		int count = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<ExpensesEntryDocuments> expensesEntryDocumentList = session.createQuery("from ExpensesEntryDocuments").list();
			if (expensesEntryDocumentList != null && !expensesEntryDocumentList.isEmpty()) {
				for (ExpensesEntryDocuments expensesEntryDocuments : expensesEntryDocumentList) {
					String fileName = expensesEntryDocuments.getFileURL().replace("/slead-static", "");
					if(fileName != null && !fileName.isEmpty() && fileName.contains(" ")) {
						String folder = fileName.substring(0, fileName.lastIndexOf("/"));
						String name = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
						File file = new File(staticPath + fileName.substring(0, fileName.lastIndexOf("/")), name);
						if(file.exists()) {
							File newFile = new File(staticPath + fileName.substring(0, fileName.lastIndexOf("/")), name.replaceAll(" ", "_"));
							Files.move(file.toPath(), newFile.toPath());
							expensesEntryDocuments.setFileURL("/slead-static" + fileName.substring(0, fileName.lastIndexOf("/")) + "/" + name.replaceAll(" ", "_"));
							count++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}