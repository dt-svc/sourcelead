package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONException;
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

@Service
public class SourceleadNotesServiceImpl implements SourceleadNotesService {
	
	@Autowired
	private SourceleadNotesDao sourceleadNotesDao;
	
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
		sourceleadNotes.setUserId(user);
		sourceleadNotes.setCreatedDate(CommonUtil.currentDate());
		sourceleadNotes.setUpdatedDate(CommonUtil.currentDate());
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
			notes.setCreatedDateString(Constants.DMY.format(notes.getCreatedDate()));
			notes.setUpdatedDateString(Constants.DMY.format(notes.getUpdatedDate()));
		}
		return notesList;
	}
	
	
}
