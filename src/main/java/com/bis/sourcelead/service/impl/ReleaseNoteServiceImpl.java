package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.dao.ReleaseNoteDao;
import com.bis.sourcelead.dto.ReleaseNoteDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReleaseNote;
import com.bis.sourcelead.service.ReleaseNoteService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class ReleaseNoteServiceImpl implements ReleaseNoteService{
	
	 @Autowired
	 HttpServletRequest request;
	 
	 @Autowired
	 ReleaseNoteDao releaseNoteDao;
	
	 @Autowired
	 private CommonUtil commonUtil;

	@Override
	public ReleaseNote saveReleaseNote(ReleaseNote releaseNote) throws Exception {
		return releaseNoteDao.saveReleaseNote(releaseNote);
	}

	@Override
	public ReleaseNote getReleaseNoteByUUId(String releaseNoteUUID) throws Exception {
		return releaseNoteDao.getReleaseNoteByUUId(releaseNoteUUID);
	}

	@Override
	public List<ReleaseNote> getAllReleaseNote() throws Exception {
		return releaseNoteDao.getAllReleaseNote();
	}

	@Override
	public String saveReleaseNoteDTO(ReleaseNoteDTO releaseNoteDTO, OrgLocations orgLocations) throws Exception {
		ReleaseNote releaseNote = null;
		if(releaseNoteDTO.getReleaseUUID() != null && releaseNoteDTO.getReleaseUUID() != "" && releaseNoteDTO.getReleaseUUID() != "undefined") {
			releaseNote = releaseNoteDao.getReleaseNoteByUUId(releaseNoteDTO.getReleaseUUID());
		}else {
			 releaseNote = new ReleaseNote();
			 releaseNote.setReleaseUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			 releaseNote.setCreatedDate(new Date());
		}
		releaseNote.setReleaseName(releaseNoteDTO.getReleaseName());
		releaseNote.setReleaseDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),releaseNoteDTO.getReleaseDate()));
		releaseNote.setPublishDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),releaseNoteDTO.getPublishDate()));
		releaseNote.setReleaseNotes(releaseNoteDTO.getReleaseNotes());
		releaseNote.setShortDescrption(releaseNoteDTO.getReleaseShortDescription());
		releaseNote.setReleaseDescrption(releaseNoteDTO.getReleaseDescrption());
		releaseNote.setStatus(1);
		releaseNoteDao.saveReleaseNote(releaseNote);
		return "success";
	}

	@Override
	public Boolean validateReleaseNoteName(String releaseNoteName) {
		return releaseNoteDao.validateReleaseNoteName(releaseNoteName);
	}

	@Override
	public ReleaseNote validateReleaseNoteNameByReleaseUUIDAndReleaseNoteName(String releaseNoteName, String releaseUUID) {
		return releaseNoteDao.validateReleaseNoteNameByReleaseUUIDAndReleaseNoteName(releaseNoteName, releaseUUID);
	}

	 
}
