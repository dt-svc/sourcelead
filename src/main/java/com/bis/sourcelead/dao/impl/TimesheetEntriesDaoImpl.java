package com.bis.sourcelead.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.TimesheetEntries;

@Repository
public class TimesheetEntriesDaoImpl implements TimesheetEntriesDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(TimesheetEntriesDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetEntries addTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(timesheetEntries);
		}
		catch(HibernateException exception)	{
		}
		return timesheetEntries;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetEntries updateTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(timesheetEntries);
	    return timesheetEntries;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetEntries getTimesheetEntries(String id) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return (TimesheetEntries) session.get(TimesheetEntries.class, Long.valueOf(id));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTimesheetEntries(String id) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		TimesheetEntries timesheetEntries = (TimesheetEntries)session.get(TimesheetEntries.class, Long.valueOf(id));
		timesheetEntries.setStatus("1");
        System.out.println("in timesheetEntriesdddddd "+timesheetEntries.getId());        
		session.saveOrUpdate(timesheetEntries);
		logger.info("Successfully deleted !!!");
		return null;
		/*Query query = session.createQuery("delete Cities where id = :ID");
		query.setParameter("ID", new Long(18));
		 
		int result = query.executeUpdate();
		return null;*/
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetEntries> getAllTimesheetEntries() throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<TimesheetEntries> adItemsList = null;
		Query citiesListQuery= session.createQuery("from TimesheetEntries");
		adItemsList=citiesListQuery.list();;
		return adItemsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimesheetEntries te left join fetch te.projectTask pt  where te.timesheetSubmissions.Id =:submissionId and (te.status = '0' or te.status = '' or te.status IS NULL)  ";
		List<TimesheetEntries> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).list();	
		return timesheetEntriesList;
	}

	
	

}
