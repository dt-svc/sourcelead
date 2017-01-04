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

import com.bis.sourcelead.dao.BoUploadedFilesDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.BoUploadedFiles;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class BoUploadedFilesDaoImpl implements BoUploadedFilesDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(BoUploadedFilesDaoImpl.class);

	@Override
	@Transactional
	public BoUploadedFiles saveBoUploadedFiles(BoUploadedFiles boUploadedFiles) throws Exception {
		logger.debug("Saving BoUploadedFiles");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (boUploadedFiles != null) {
				session.saveOrUpdate(boUploadedFiles);
			}
			logger.debug("Saving BoUploadedFiles End");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"BoUploadedFiles"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return boUploadedFiles;
	}

	@Override
	@Transactional
	public List<BoUploadedFiles> getBoUploadedFilesByFinancialInstitueName(String bankName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

	    String query = "from BoUploadedFiles bo where bo.bankName = :bankName  ";

	    List<BoUploadedFiles> boUploadedFilesList = session.createQuery(query.toString()).setString( "bankName", bankName).setCacheable(true).list();
        return boUploadedFilesList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BoUploadedFiles getBoUploadedFileById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from BoUploadedFiles bouploadfile where bouploadfile.id = :id" ;
		List<BoUploadedFiles> boUploadedFilesList =session.createQuery(query).setLong("id", id).setCacheable(true).list(); 
		BoUploadedFiles boUploadedFiles = null;
		if(boUploadedFilesList != null && !boUploadedFilesList.isEmpty()){
			boUploadedFiles = boUploadedFilesList.iterator().next();
		}
		return boUploadedFiles;
	}

}
