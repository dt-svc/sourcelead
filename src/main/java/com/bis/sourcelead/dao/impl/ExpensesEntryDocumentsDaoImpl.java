package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesEntryDocumentsDao;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
*
* @author  Pradeep Reddy
*          
*          |Ver_Number|  |Modified By|   |Modified On|  |Comments|
*          -----------   -------------   -------------  ----------
* @version    1.0        Pradeep Reddy     New_File       New_File
* 
* @since   2017-06-02
*/

@Repository
public class ExpensesEntryDocumentsDaoImpl implements ExpensesEntryDocumentsDao {
	private static final Logger logger = LoggerFactory.getLogger(ExpensesEntryDocumentsDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void removeExpensesEntryDocouments(Long id) throws Exception {
		logger.debug("Deleting ExpenseEntryDocuments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (null != id) {
				List<ExpensesEntryDocuments> eedList = session.createQuery("from ExpensesEntryDocuments eed where eed.id=:id" ).setLong( "id",id).list();
				if(null != eedList && !eedList.isEmpty()){
					ExpensesEntryDocuments eed = eedList.get(0);
					eed.setStatus(0);
					session.update(eed);
				}
			}
			logger.debug("Successfully deleted..!");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ExpenseEntryDocuments"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
	}

	@Override
	public void removeAllExpensesEntryDocoumentsByEntryId(Long entryId) throws Exception {
		logger.debug("Deleting ExpenseEntryDocuments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (null != entryId) {
				List<ExpensesEntryDocuments> eedList = session.createQuery("from ExpensesEntryDocuments eed left join fetch eed.expensesEntriesId where eed.expensesEntriesId.id=:entryId" ).setLong( "entryId",entryId).list();
				if(null != eedList && !eedList.isEmpty()){
					for(ExpensesEntryDocuments eed:eedList){
						session.delete(eed);
					}
					
				}
			}
			logger.debug("Successfully deleted..!");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ExpenseEntryDocuments"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void removeExpensesEntryDocoumentList(List<Long> ids) throws Exception {
		logger.debug("Deleting ExpenseEntryDocuments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (null != ids && !ids.isEmpty()) {
				List<ExpensesEntryDocuments> eedList = session.createQuery("from ExpensesEntryDocuments eed where eed.id IN "+ids+"" ).list();
				if(null != eedList && !eedList.isEmpty()){
					for(ExpensesEntryDocuments eed :eedList){
						//session.delete(eed);
					}
					
				}
			}
			logger.debug("Successfully deleted..!");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ExpenseEntryDocuments"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesEntryDocuments getExpensesEntryDocumentById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return (ExpensesEntryDocuments) session.get(ExpensesEntryDocuments.class, id);
	}
	

}

