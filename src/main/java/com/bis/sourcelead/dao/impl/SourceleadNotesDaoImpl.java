package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadNotesDao;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.SourceleadNotesService;

@Repository
public class SourceleadNotesDaoImpl implements SourceleadNotesDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.106}")
	private String duplicateValue;
	

	private static final Logger logger = LoggerFactory.getLogger(SourceleadNotesDaoImpl.class);


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SourceleadNotes storeComments(SourceleadNotes sourceleadNotes) throws JSONException, Exception
	{
		
		logger.debug("Storing SourceleadNotes");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (sourceleadNotes != null) {
				session.saveOrUpdate(sourceleadNotes);
			}
			logger.debug("Requirement Stored Successfully");
		} catch (Exception exception) {
				StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SourceleadNotes"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return  sourceleadNotes;

	}
}
