package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.SourceleadNotesDao;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.SourceleadNotesService;
import com.bis.sourcelead.util.CommonUtil;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class SourceleadNotesServiceImpl implements SourceleadNotesService {
	
	@Autowired
	private SourceleadNotesDao sourceleadNotesDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	private static final Logger logger = LoggerFactory.getLogger(SourceleadNotesServiceImpl.class);
	
	
	
	@Override
	public SourceleadNotes storeComments(JSONObject jsonObj, User user) throws Exception
	{
		SourceleadNotes sourceleadNotes = new SourceleadNotes();
		if (jsonObj.has("noteDescription") && jsonObj.getString("noteDescription") != null) {
			sourceleadNotes.setComment(jsonObj.getString("noteDescription"));
		}
		if (jsonObj.has("noteType") && jsonObj.getString("noteType") != null) {
			sourceleadNotes.setNoteType(jsonObj.getString("noteType"));
		}
		if (jsonObj.has("orgRelationId") && !"".equalsIgnoreCase(jsonObj.getString("orgRelationId"))) {
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("orgRelationId")));
			sourceleadNotes.setOrgRelationID(orgRelation);
		}
		else {
			sourceleadNotes.setOrgRelationID(null);
		}
		if (jsonObj.has("allowAll")) {
			sourceleadNotes.setCheckBox(jsonObj.getString("allowAll"));
		}else{
			sourceleadNotes.setCheckBox(Constants.NO);

		}
		sourceleadNotes.setUserId(user);
		sourceleadNotes.setCreatedDate(commonUtil.currentDate());
		sourceleadNotes.setUpdatedDate(commonUtil.currentDate());
		sourceleadNotes.setCreatedBy(user.getUserCode());
		sourceleadNotes.setUpdatedBy(user.getUserCode());
		return sourceleadNotesDao.storeComments(sourceleadNotes);
	}


	@Override
	public List<SourceleadNotes> getNotesListByOrglocationIdAndType(Long orgrelationId) throws Exception
	{
		List<SourceleadNotes> notesList = sourceleadNotesDao.getNotesListByOrglocationIdAndType(orgrelationId);
		for (SourceleadNotes notes : notesList) {
			notes.setComment(notes.getComment());
			notes.setUserName(notes.getUserId().getFirstName());
			
			// date time format conversion.
			long diff = commonUtil.currentDate().getTime() - notes.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				notes.setCreatedDateString(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			}else if(diffDays == 0 && diffHours == 0){
				notes.setCreatedDateString(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			}else if(diffDays == 0){
				notes.setCreatedDateString(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
			}else if(diffDays < 7){
				notes.setCreatedDateString(timeBuilder.append(diffDays).append( " days ").append(diffHours).append(" hours ago").toString());
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(notes.getCreatedDate());
				notes.setCreatedDateString(commonDateFormat.toString());
			}
			
		}
		return notesList;
	}
	
	
}
