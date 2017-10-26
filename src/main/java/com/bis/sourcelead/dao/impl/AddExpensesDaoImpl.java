package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class AddExpensesDaoImpl implements AddExpensesDao {

	@Autowired
	SessionFactory sessionFactory;
	
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
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Expenses storeAddExpensesData(Expenses expenses) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (expenses != null) {
				session.saveOrUpdate(expenses);
				if(expenses.getExpensesEntries() != null && !expenses.getExpensesEntries().isEmpty()) {
					for(ExpensesEntries ee :expenses.getExpensesEntries()) {
						ee.setExpensesId(expenses);
						session.saveOrUpdate(ee);
						if(ee.getExpensesEntryDocuments() != null && !ee.getExpensesEntryDocuments().isEmpty()) {
							for(ExpensesEntryDocuments documents:ee.getExpensesEntryDocuments()) {
								  documents.setExpensesEntriesId(ee);
										session.saveOrUpdate(documents);
								}
						}
					}
				}
			}
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"expenses"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return expenses;
		
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Expenses checkExpensIdNumberInDatebase(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Expenses expenses left join fetch expenses.projectMembers proMembers left join fetch proMembers.employee left join fetch expenses.orgLocationId  where  expenses.id =(SELECT MAX(exp.id)  FROM Expenses exp where exp.orgLocationId.id =:orgLocationId)";  
		List<Expenses> expenses  =  session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if(expenses.isEmpty()){
			return null;
		}
		return expenses.iterator().next();		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteDocumentByDocumentId(Long documentId) {
		Session session = this.sessionFactory.getCurrentSession();
		ExpensesEntryDocuments expensesEntryDocuments = this.sessionFactory.getCurrentSession().get(ExpensesEntryDocuments.class, documentId);
		expensesEntryDocuments.setStatus(0);
		session.saveOrUpdate(expensesEntryDocuments);
	}

	
}
