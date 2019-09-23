package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ReleaseNoteDao;
import com.bis.sourcelead.entity.ReleaseNote;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class ReleaseNoteDaoImpl implements ReleaseNoteDao{
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ReleaseNoteDaoImpl.class);

	@Override
	public ReleaseNote saveReleaseNote(ReleaseNote releaseNote) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (releaseNote != null) {
				session.saveOrUpdate(releaseNote);
			}
			logger.debug("Saving Department");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Department"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return releaseNote;
	}

	@Override
	public ReleaseNote getReleaseNoteByUUId(String releaseNoteUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ReleaseNote releasNote  where releasNote.releaseUUID =:releaseNoteUUID ";
		List<ReleaseNote> releaseNoteList =session.createQuery(query) .setString("releaseNoteUUID", releaseNoteUUID).list();
		ReleaseNote rn = null;
		if(releaseNoteList != null && !releaseNoteList.isEmpty())
		{
			rn = (ReleaseNote) releaseNoteList.iterator().next();
		}
		return rn;
	}

	
	@Override
	public List<ReleaseNote> getAllReleaseNote() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ReleaseNote releaseNote  where releaseNote.status = '1' "; 
		List<ReleaseNote> releaseNoteList = session.createQuery(query).list();
		return releaseNoteList;
	}

	@Override
	public Boolean validateReleaseNoteName(String releaseNoteName) {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(releaseNoteName != null){
				String query = "from ReleaseNote rn where rn.releaseName = :releaseNoteName and rn.status = '1' ";
				Collection<ReleaseNote> releaseNoteList = session.createQuery(query.toString()).setString("releaseNoteName", releaseNoteName).list();
				if (releaseNoteList != null && releaseNoteList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}
	
	@Override
	public ReleaseNote validateReleaseNoteNameByReleaseUUIDAndReleaseNoteName(String releaseNoteName,
			String releaseUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ReleaseNote rn where rn.releaseName = :releaseNoteName and rn.releaseUUID =:releaseUUID  and rn.status = '1' ";
		List<ReleaseNote> releaseNoteList = session.createQuery(query.toString()).setString("releaseNoteName", releaseNoteName).setString("releaseUUID", releaseUUID).list();
		ReleaseNote rn = null;
		if(releaseNoteList != null && !releaseNoteList.isEmpty())
		{
			rn = (ReleaseNote) releaseNoteList.iterator().next();
		}
		return rn;
	}

	 
}
